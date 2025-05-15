package com.disketaa.harmonium.util;

import com.disketaa.harmonium.Harmonium;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;

public class ModDataComponents {
	public static final DeferredRegister<DataComponentType<?>> COMPONENTS =
		DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Harmonium.MOD_ID);

	public static final DataComponentType<Integer> TEMPO_INDEX = register(
		"tempo_index",
		DataComponentType.<Integer>builder()
			.persistent(Codec.INT)
			.networkSynchronized(ByteBufCodecs.INT)
			.build()
	);

	private static <T> DataComponentType<T> register(String name, DataComponentType<T> component) {
		COMPONENTS.register(name, () -> component);
		return component;
	}

	public static void register(IEventBus bus) {
		COMPONENTS.register(bus);
	}
}