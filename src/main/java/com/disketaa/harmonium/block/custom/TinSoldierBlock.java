package com.disketaa.harmonium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class TinSoldierBlock extends Block implements SimpleWaterloggedBlock {
	public static final int MAX_SOLDIERS = 4;
	public static final IntegerProperty SOLDIERS = IntegerProperty.create("soldiers", 1, MAX_SOLDIERS);
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	protected static final VoxelShape SHAPE_1_NS = Block.box(6.0, 0.0, 6.0, 10.0, 7.0, 10.0); // Same as NS since it's square
	protected static final VoxelShape SHAPE_2_NS = Block.box(6.0, 0.0, 4.0, 10.0, 7.0, 12.0);
	protected static final VoxelShape SHAPE_3_NS = Block.box(3.0, 0.0, 4.0, 13.0, 7.0, 12.0);
	protected static final VoxelShape SHAPE_4_NS = Block.box(3.0, 0.0, 4.0, 13.0, 7.0, 12.0);
	protected static final VoxelShape SHAPE_1_WE = Block.box(6.0, 0.0, 6.0, 10.0, 7.0, 10.0);
	protected static final VoxelShape SHAPE_2_WE = Block.box(4.0, 0.0, 6.0, 12.0, 7.0, 10.0);
	protected static final VoxelShape SHAPE_3_WE = Block.box(4.0, 0.0, 3.0, 12.0, 7.0, 13.0);
	protected static final VoxelShape SHAPE_4_WE = Block.box(4.0, 0.0, 3.0, 12.0, 7.0, 13.0);

	public TinSoldierBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any()
			.setValue(SOLDIERS, 1)
			.setValue(FACING, Direction.NORTH)
			.setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(SOLDIERS, FACING, WATERLOGGED);
	}

	@Override
	public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		Direction facing = state.getValue(FACING);
		boolean isNorthSouth = facing == Direction.NORTH || facing == Direction.SOUTH;
		return switch (state.getValue(SOLDIERS)) {
			case 1 -> isNorthSouth ? SHAPE_1_NS : SHAPE_1_WE;
			case 2 -> isNorthSouth ? SHAPE_2_NS : SHAPE_2_WE;
			case 3 -> isNorthSouth ? SHAPE_3_NS : SHAPE_3_WE;
			case 4 -> isNorthSouth ? SHAPE_4_NS : SHAPE_4_WE;
			default -> SHAPE_1_NS;
		};
	}

	public boolean canPlaceBlock(BlockState state, LevelReader level, BlockPos pos) {
		return Block.canSupportCenter(level, pos.below(), Direction.UP);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos pos = context.getClickedPos();
		Level level = context.getLevel();
		FluidState fluidstate = level.getFluidState(pos);

		if (!canPlaceBlock(defaultBlockState(), level, pos)) {
			return null;
		}

		BlockState blockstate = level.getBlockState(pos);
		if (blockstate.is(this)) {
			return blockstate.setValue(SOLDIERS, Math.min(MAX_SOLDIERS, blockstate.getValue(SOLDIERS) + 1))
				.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
		}

		return this.defaultBlockState()
			.setValue(FACING, context.getHorizontalDirection().getOpposite())
			.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
	}

	@Override
	public boolean canBeReplaced(@NotNull BlockState state, BlockPlaceContext context) {
		return !context.isSecondaryUseActive()
			&& context.getItemInHand().is(this.asItem())
			&& state.getValue(SOLDIERS) < MAX_SOLDIERS;
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		return true;
	}

	@Override
	public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	@Override
	public @NotNull FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean placeLiquid(@NotNull LevelAccessor level, @NotNull BlockPos pos, BlockState state, @NotNull FluidState fluidState) {
		if (!state.getValue(WATERLOGGED) && fluidState.getType() == Fluids.WATER) {
			level.setBlock(pos, state.setValue(WATERLOGGED, true), 3);
			level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
			return true;
		}
		return false;
	}

	@Override
	public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, Entity entity) {
		if (!entity.isSteppingCarefully()) {
			this.destroySoldier(level, state, pos, entity, 100);
		}
		super.stepOn(level, pos, state, entity);
	}

	@Override
	public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Entity entity, float fallDistance) {
		this.destroySoldier(level, state, pos, entity, 3);
		super.fallOn(level, state, pos, entity, fallDistance);
	}

	private void destroySoldier(Level level, BlockState state, BlockPos pos, Entity entity, int chance) {
		if (canDestroySoldier(level, entity)) {
			if (!level.isClientSide && level.random.nextInt(chance) == 0) {
				decreaseSoldiers(level, pos, state);
			}
		}
	}

	private void decreaseSoldiers(Level level, BlockPos pos, BlockState state) {
		level.playSound(null, pos, SoundEvents.COPPER_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + level.random.nextFloat() * 0.2F);
		int soldiers = state.getValue(SOLDIERS);
		if (soldiers <= 1) {
			level.destroyBlock(pos, false);
		} else {
			level.setBlock(pos, state.setValue(SOLDIERS, soldiers - 1), 2);
			level.gameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Context.of(state));
			level.levelEvent(2001, pos, Block.getId(state));
		}
	}

	private boolean canDestroySoldier(Level level, Entity entity) {
		return entity instanceof LivingEntity && !(entity instanceof Player)
			|| (entity instanceof Player && net.neoforged.neoforge.event.EventHooks.canEntityGrief(level, entity));
	}
}