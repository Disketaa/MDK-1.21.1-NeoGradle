package com.disketaa.harmonium.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UnreliableButtonBlock extends ButtonBlock {
	private static final int SHORT_PRESS = 0;
	private static final int LONG_PRESS = 20;
	private static final float SHORT_PRESS_CHANCE = 0.5f;
	private boolean isCurrentPressShort = false;

	public UnreliableButtonBlock(BlockSetType blockSetType, BlockBehaviour.Properties properties) {
		super(blockSetType, 20, properties);
	}

	@Override
	public void press(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @Nullable Player player) {
		if (!state.getValue(POWERED)) {
			this.isCurrentPressShort = level.getRandom().nextFloat() < SHORT_PRESS_CHANCE;
			int pressDuration = isCurrentPressShort ? SHORT_PRESS : LONG_PRESS;

			boolean shouldOutputSignal = !isCurrentPressShort;
			level.setBlock(pos, state.setValue(POWERED, shouldOutputSignal), 3);

			if (shouldOutputSignal) {
				this.updateNeighbors(state, level, pos);
			}

			level.scheduleTick(pos, this, pressDuration);
			this.playSound(player, level, pos, true);
			level.gameEvent(player, GameEvent.BLOCK_ACTIVATE, pos);
		}
	}

	@Override
	protected void tick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (state.getValue(POWERED) || isCurrentPressShort) {

			boolean wasPowered = state.getValue(POWERED);
			level.setBlock(pos, state.setValue(POWERED, false), 3);

			if (wasPowered) {
				this.updateNeighbors(state, level, pos);
			}

			this.playSound(null, level, pos, false);
			level.gameEvent(null, GameEvent.BLOCK_DEACTIVATE, pos);
		}
	}

	@Override
	public int getSignal(BlockState blockState, @NotNull BlockGetter blockAccess, @NotNull BlockPos pos, @NotNull Direction side) {
		return blockState.getValue(POWERED) ? 15 : 0;
	}

	private void updateNeighbors(BlockState state, Level level, BlockPos pos) {
		level.updateNeighborsAt(pos, this);
		level.updateNeighborsAt(pos.relative(getConnectedDirection(state).getOpposite()), this);
	}
}