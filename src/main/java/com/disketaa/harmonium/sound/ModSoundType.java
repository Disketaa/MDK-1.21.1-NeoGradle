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

	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_BREAK = registerSoundEvent("block.tin.break");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_STEP = registerSoundEvent("block.tin.step");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_PLACE = registerSoundEvent("block.tin.place");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_HIT = registerSoundEvent("block.tin.hit");
	public static final DeferredHolder<SoundEvent, SoundEvent> TIN_FALL = registerSoundEvent("block.tin.fall");

	public static final DeferredSoundType TIN = new DeferredSoundType(
		1.0f, 1.0f,
		TIN_BREAK, TIN_STEP, TIN_PLACE, TIN_HIT, TIN_FALL
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