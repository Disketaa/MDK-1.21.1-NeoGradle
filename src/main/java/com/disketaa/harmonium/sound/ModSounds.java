package com.disketaa.harmonium.sound;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
		DeferredRegister.create(Registries.SOUND_EVENT, "harmonium");

	public static final DeferredHolder<SoundEvent, SoundEvent> CYMBAL = registerSoundEvent("note.cymbal");
	public static final DeferredHolder<SoundEvent, SoundEvent> HORN = registerSoundEvent("note.horn");
	public static final DeferredHolder<SoundEvent, SoundEvent> RIM = registerSoundEvent("note.rim");
	public static final DeferredHolder<SoundEvent, SoundEvent> WOODWIND = registerSoundEvent("note.woodwind");

	private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(String name) {
		return SOUND_EVENTS.register(name, () ->
			SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("harmonium", name))
		);
	}

	public static void register(IEventBus eventBus) {
		SOUND_EVENTS.register(eventBus);
	}
}