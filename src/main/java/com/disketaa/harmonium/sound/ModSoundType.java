package com.disketaa.harmonium.sound;

import com.disketaa.harmonium.Harmonium;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModSoundType {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
		DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Harmonium.MOD_ID);

	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_BREAK = registerSoundEvent("block.bronze.break");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_STEP = registerSoundEvent("block.bronze.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_PLACE = registerSoundEvent("block.bronze.place");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_HIT = registerSoundEvent("block.bronze.hit");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_FALL = registerSoundEvent("block.bronze.fall");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_GRATE_BREAK = registerSoundEvent("block.bronze_grate.break");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_GRATE_STEP = registerSoundEvent("block.bronze_grate.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_GRATE_PLACE = registerSoundEvent("block.bronze_grate.place");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_GRATE_HIT = registerSoundEvent("block.bronze_grate.hit");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_GRATE_FALL = registerSoundEvent("block.bronze_grate.fall");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_BULB_BREAK = registerSoundEvent("block.bronze_bulb.break");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_BULB_STEP = registerSoundEvent("block.bronze_bulb.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_BULB_PLACE = registerSoundEvent("block.bronze_bulb.place");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_BULB_HIT = registerSoundEvent("block.bronze_bulb.hit");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_BULB_FALL = registerSoundEvent("block.bronze_bulb.fall");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_BULB_TURN_ON = registerSoundEvent("block.bronze_bulb.turn_on");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_BULB_TURN_OFF = registerSoundEvent("block.bronze_bulb.turn_off");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_DOOR_CLOSE = registerSoundEvent("block.bronze_door.close");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_DOOR_OPEN = registerSoundEvent("block.bronze_door.open");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_TRAPDOOR_CLOSE = registerSoundEvent("block.bronze_trapdoor.close");
	public static final DeferredHolder<SoundEvent, SoundEvent> BRONZE_TRAPDOOR_OPEN = registerSoundEvent("block.bronze_trapdoor.open");
	public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SLAG_BREAK = registerSoundEvent("block.nether_slag.break");
	public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SLAG_STEP = registerSoundEvent("block.nether_slag.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SLAG_PLACE = registerSoundEvent("block.nether_slag.place");
	public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SLAG_HIT = registerSoundEvent("block.nether_slag.hit");
	public static final DeferredHolder<SoundEvent, SoundEvent> NETHER_SLAG_FALL = registerSoundEvent("block.nether_slag.fall");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_BREAK = registerSoundEvent("block.tin.break");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_STEP = registerSoundEvent("block.tin.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_PLACE = registerSoundEvent("block.tin.place");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_HIT = registerSoundEvent("block.tin.hit");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_FALL = registerSoundEvent("block.tin.fall");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_GRATE_BREAK = registerSoundEvent("block.tin_grate.break");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_GRATE_STEP = registerSoundEvent("block.tin_grate.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_GRATE_PLACE = registerSoundEvent("block.tin_grate.place");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_GRATE_HIT = registerSoundEvent("block.tin_grate.hit");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_GRATE_FALL = registerSoundEvent("block.tin_grate.fall");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_BULB_BREAK = registerSoundEvent("block.tin_bulb.break");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_BULB_STEP = registerSoundEvent("block.tin_bulb.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_BULB_PLACE = registerSoundEvent("block.tin_bulb.place");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_BULB_HIT = registerSoundEvent("block.tin_bulb.hit");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_BULB_FALL = registerSoundEvent("block.tin_bulb.fall");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_SOLDIER_BREAK = registerSoundEvent("block.tin_soldier.break");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_SOLDIER_STEP = registerSoundEvent("block.tin_soldier.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_SOLDIER_PLACE = registerSoundEvent("block.tin_soldier.place");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_SOLDIER_HIT = registerSoundEvent("block.tin_soldier.hit");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_SOLDIER_FALL = registerSoundEvent("block.tin_soldier.fall");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_DOOR_CLOSE = registerSoundEvent("block.tin_door.close");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_DOOR_OPEN = registerSoundEvent("block.tin_door.open");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_TRAPDOOR_CLOSE = registerSoundEvent("block.tin_trapdoor.close");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_TRAPDOOR_OPEN = registerSoundEvent("block.tin_trapdoor.open");
	public static final DeferredHolder<SoundEvent, SoundEvent> ARMOR_EQUIP_BRONZE = registerSoundEvent("item.armor.equip_bronze");
	public static final DeferredHolder<SoundEvent, SoundEvent> BUCKLER_BLOCK = registerSoundEvent("item.buckler.block");
	public static final DeferredHolder<SoundEvent, SoundEvent> BUCKLER_EQUIP = registerSoundEvent("item.buckler.equip");

	public static final DeferredSoundType BRONZE = new DeferredSoundType(
		1.0f, 1.0f,
		BRONZE_BREAK, BRONZE_STEP, BRONZE_PLACE, BRONZE_HIT, BRONZE_FALL
	);

	public static final DeferredSoundType BRONZE_GRATE = new DeferredSoundType(
		1.0f, 1.0f,
		BRONZE_GRATE_BREAK, BRONZE_GRATE_STEP, BRONZE_GRATE_PLACE, BRONZE_GRATE_HIT, BRONZE_GRATE_FALL
	);

	public static final DeferredSoundType BRONZE_BULB = new DeferredSoundType(
		1.0f, 1.0f,
		BRONZE_BULB_BREAK, BRONZE_BULB_STEP, BRONZE_BULB_PLACE, BRONZE_BULB_HIT, BRONZE_BULB_FALL
	);

	public static final DeferredSoundType NETHER_SLAG = new DeferredSoundType(
		1.0f, 1.0f,
		NETHER_SLAG_BREAK, NETHER_SLAG_STEP, NETHER_SLAG_PLACE, NETHER_SLAG_HIT, NETHER_SLAG_FALL
	);

	public static final DeferredSoundType TIN = new DeferredSoundType(
		1.0f, 1.0f,
		TIN_BREAK, TIN_STEP, TIN_PLACE, TIN_HIT, TIN_FALL
	);

	public static final DeferredSoundType TIN_GRATE = new DeferredSoundType(
		1.0f, 1.0f,
		TIN_GRATE_BREAK, TIN_GRATE_STEP, TIN_GRATE_PLACE, TIN_GRATE_HIT, TIN_GRATE_FALL
	);

	public static final DeferredSoundType TIN_BULB = new DeferredSoundType(
		1.0f, 1.0f,
		TIN_BULB_BREAK, TIN_BULB_STEP, TIN_BULB_PLACE, TIN_BULB_HIT, TIN_BULB_FALL
	);

	public static final DeferredSoundType TIN_SOLDIER = new DeferredSoundType(
		1.0f, 1.0f,
		TIN_SOLDIER_BREAK, TIN_SOLDIER_STEP, TIN_SOLDIER_PLACE, TIN_SOLDIER_HIT, TIN_SOLDIER_FALL
	);

	private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
		return SOUND_EVENTS.register(name, () ->
			SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Harmonium.MOD_ID, name))
		);
	}

	public static void register(IEventBus eventBus) {
		SOUND_EVENTS.register(eventBus);
	}
}