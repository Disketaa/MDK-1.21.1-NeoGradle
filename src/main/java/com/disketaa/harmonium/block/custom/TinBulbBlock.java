package com.disketaa.harmonium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class TinBulbBlock extends Block {
	public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);

	public TinBulbBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(LIGHT_LEVEL, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIGHT_LEVEL);
	}

	@Override
	public void onPlace(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
		if (!level.isClientSide) {
			this.updateLightLevel(state, level, pos);
		}
	}

	@Override
	public void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean movedByPiston) {
		if (!level.isClientSide) {
			this.updateLightLevel(state, level, pos);
		}
	}

	private void updateLightLevel(BlockState state, Level level, BlockPos pos) {
		int redstonePower = level.getBestNeighborSignal(pos);
		int currentLight = state.getValue(LIGHT_LEVEL);

		if (redstonePower != currentLight) {
			level.setBlock(pos, state.setValue(LIGHT_LEVEL, redstonePower), Block.UPDATE_ALL);
		}
	}

	@Override
	public boolean isSignalSource(@NotNull BlockState state) {
		return false;
	}

	@Override
	public int getSignal(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
		return 0;
	}
}