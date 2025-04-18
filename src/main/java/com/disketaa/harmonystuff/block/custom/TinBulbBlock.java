package com.disketaa.harmonystuff.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class TinBulbBlock extends Block {
	public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);
	private static final int FADE_INTERVAL = 1;
	private final int minEffectiveLightLevel;

	public TinBulbBlock(Properties properties) {
		this(properties, 10);
	}

	public TinBulbBlock(Properties properties, int minEffectiveLightLevel) {
		super(properties);
		this.minEffectiveLightLevel = Mth.clamp(minEffectiveLightLevel, 0, 15);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIGHT_LEVEL, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(LIGHT_LEVEL);
	}

	@Override
	public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
	                    @NotNull BlockState oldState, boolean isMoving) {
		if (!level.isClientSide && !state.is(oldState.getBlock())) {
			this.updatePower(state, level, pos);
		}
	}

	@Override
	public void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
	                            @NotNull Block block, @NotNull BlockPos fromPos, boolean isMoving) {
		if (!level.isClientSide) {
			this.updatePower(state, level, pos);
		}
	}

	private void updatePower(BlockState state, Level level, BlockPos pos) {
		int receivedPower = this.getExternalPower(level, pos);
		int currentLight = state.getValue(LIGHT_LEVEL);

		if (receivedPower > 0) {
			int newLight = Math.max(receivedPower, minEffectiveLightLevel);
			if (currentLight != newLight) {
				level.setBlock(pos, state.setValue(LIGHT_LEVEL, newLight), Block.UPDATE_ALL);
			}
		} else if (currentLight > 0 && !level.getBlockTicks().hasScheduledTick(pos, this)) {
			level.scheduleTick(pos, this, FADE_INTERVAL);
		}
	}

	private int getExternalPower(Level level, BlockPos pos) {
		int power = 0;
		for (Direction direction : Direction.values()) {
			BlockPos neighborPos = pos.relative(direction);
			BlockState neighborState = level.getBlockState(neighborPos);

			if (!(neighborState.getBlock() instanceof TinBulbBlock)) {
				power = Math.max(power, level.getSignal(neighborPos, direction));
			}
		}
		return power;
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos,
	                 @NotNull RandomSource random) {
		int currentLight = state.getValue(LIGHT_LEVEL);
		if (currentLight > 0 && this.getExternalPower(level, pos) == 0) {
			int newLight = currentLight > minEffectiveLightLevel ? currentLight - 1 : 0;
			level.setBlock(pos, state.setValue(LIGHT_LEVEL, newLight), Block.UPDATE_ALL);
			if (newLight > 0) {
				level.scheduleTick(pos, this, FADE_INTERVAL);
			}
		}
	}

	@Override
	public int getSignal(@NotNull BlockState state, @NotNull BlockGetter level,
	                     @NotNull BlockPos pos, @NotNull Direction direction) {
		return direction == Direction.DOWN ? state.getValue(LIGHT_LEVEL) : 0;
	}

	@Override
	public boolean isSignalSource(@NotNull BlockState state) {
		return state.getValue(LIGHT_LEVEL) > 0;
	}
}