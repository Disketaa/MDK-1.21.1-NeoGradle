package com.disketaa.harmonium.sound;

import com.disketaa.harmonium.sound.ModSoundType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class ModBlockSetType {
	public static final BlockSetType TIN = BlockSetType.register(
		new BlockSetType(
			"harmonium:tin",
			true,
			true,
			false,
			BlockSetType.PressurePlateSensitivity.EVERYTHING,
			ModSoundType.TIN,
			ModSoundType.TIN_DOOR_CLOSE.get(),
			ModSoundType.TIN_DOOR_OPEN.get(),
			ModSoundType.TIN_TRAPDOOR_CLOSE.get(),
			ModSoundType.TIN_TRAPDOOR_OPEN.get(),
			SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF,
			SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON,
			SoundEvents.STONE_BUTTON_CLICK_OFF,
			SoundEvents.STONE_BUTTON_CLICK_ON
		)
	);

	public static final BlockSetType BRONZE = BlockSetType.register(
		new BlockSetType(
			"harmonium:bronze",
			true,
			true,
			false,
			BlockSetType.PressurePlateSensitivity.EVERYTHING,
			ModSoundType.BRONZE,
			ModSoundType.BRONZE_DOOR_CLOSE.get(),
			ModSoundType.BRONZE_DOOR_OPEN.get(),
			ModSoundType.BRONZE_TRAPDOOR_CLOSE.get(),
			ModSoundType.BRONZE_TRAPDOOR_OPEN.get(),
			SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF,
			SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON,
			SoundEvents.STONE_BUTTON_CLICK_OFF,
			SoundEvents.STONE_BUTTON_CLICK_ON
		)
	);
}