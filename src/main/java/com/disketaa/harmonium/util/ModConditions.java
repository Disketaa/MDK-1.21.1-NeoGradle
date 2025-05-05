package com.disketaa.harmonium.util;

import com.disketaa.harmonium.Config;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.conditions.ICondition;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ModConditions {
	public static final Map<String, MapCodec<? extends ICondition>> SERIALIZERS = new HashMap<>();

	public static <T extends ICondition> MapCodec<T> register(String name, MapCodec<T> codec) {
		SERIALIZERS.put(name, codec);
		return codec;
	}

	public static final MapCodec<ConfigValueCondition> CONFIG_CODEC = register("config", ConfigValueCondition.CODEC);

	public static ConfigValueCondition config(ModConfigSpec.ConfigValue<?> value, String key, boolean inverted) {
		return new ConfigValueCondition(value, key, Maps.newHashMap(), inverted);
	}

	public static ConfigValueCondition config(ModConfigSpec.ConfigValue<?> value, String key) {
		return config(value, key, false);
	}

	public record ConfigValueCondition(ModConfigSpec.ConfigValue<?> value, String key,
	                                   Map<String, String> data, boolean inverted) implements ICondition {
		public static final MapCodec<ConfigValueCondition> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
			Codec.STRING.fieldOf("value").forGetter(ConfigValueCondition::key),
			Codec.unboundedMap(Codec.STRING, Codec.STRING).optionalFieldOf("data", Maps.newHashMap()).forGetter(ConfigValueCondition::data),
			Codec.BOOL.optionalFieldOf("inverted", false).forGetter(ConfigValueCondition::inverted)
		).apply(builder, (key, data, inverted) -> new ConfigValueCondition(null, key, data, inverted)));

		@Override
		public boolean test(@NotNull ICondition.IContext context) {
			if ("remove_stone_tools".equals(key)) {
				return inverted != Config.removeStoneTools;
			}

			if ("remove_flint_knife".equals(key)) {
				return inverted != Config.removeFlintKnife;
			}

			if (value != null && value.get() instanceof Boolean) {
				return inverted != (Boolean) value.get();
			}
			return inverted;
		}

		@Override
		public @NotNull MapCodec<? extends ICondition> codec() {
			return CODEC;
		}
	}
}