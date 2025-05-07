package com.disketaa.harmonium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class BronzeBulbBlock extends Block {
	public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);
	public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public BronzeBulbBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any()
			.setValue(LIGHT_LEVEL, 0)
			.setValue(LIT, false)
			.setValue(POWERED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIGHT_LEVEL, LIT, POWERED);
	}

	@Override
	public void onPlace(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
		if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
			this.updateState(state, serverLevel, pos);
		}
	}

	@Override
	public void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean movedByPiston) {
		if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
			this.updateState(state, serverLevel, pos);
		}
	}

	private void updateState(BlockState state, ServerLevel level, BlockPos pos) {
		boolean hasPower = level.hasNeighborSignal(pos);
		boolean wasPowered = state.getValue(POWERED);
		boolean isLit = state.getValue(LIT);

		BlockState newState = state;

		if (hasPower != wasPowered) {
			newState = newState.setValue(POWERED, hasPower);

			if (hasPower && !wasPowered) {
				newState = newState.cycle(LIT);
				level.playSound(
					null, pos,
					newState.getValue(LIT) ? SoundEvents.COPPER_BULB_TURN_ON : SoundEvents.COPPER_BULB_TURN_OFF,
					SoundSource.BLOCKS, 1.0F, 1.0F
				);
			}
		}

		if (newState.getValue(LIT)) {
			if (hasPower) {
				int redstonePower = level.getBestNeighborSignal(pos);
				newState = newState.setValue(LIGHT_LEVEL, redstonePower);
			}
		} else {
			newState = newState.setValue(LIGHT_LEVEL, 0);
		}

		if (state != newState) {
			level.setBlock(pos, newState, 3);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		return state.getValue(LIT) ? state.getValue(LIGHT_LEVEL) : 0;
	}

	@Override
	public boolean isSignalSource(@NotNull BlockState state) {
		return false;
	}
}