package com.disketaa.harmonium.advancement.trigger;

import com.disketaa.harmonium.advancement.ModCriteriaTriggers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class AdjustInstrumentTrigger extends SimpleCriterionTrigger<AdjustInstrumentTrigger.TriggerInstance> {

	@Override
	public @NotNull Codec<TriggerInstance> codec() {
		return TriggerInstance.CODEC;
	}

	public void trigger(ServerPlayer player, ItemStack instrument) {
		this.trigger(player, instance -> instance.matches(instrument));
	}

	public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> instrument)
		implements SimpleCriterionTrigger.SimpleInstance {
		public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
				EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
				ItemPredicate.CODEC.optionalFieldOf("instrument").forGetter(TriggerInstance::instrument)
			).apply(instance, TriggerInstance::new)
		);

		public static Criterion<TriggerInstance> adjustedInstrument() {
			return ModCriteriaTriggers.ADJUST_INSTRUMENT.createCriterion(
				new TriggerInstance(Optional.empty(), Optional.empty())
			);
		}

		public static Criterion<TriggerInstance> adjustedInstrument(ItemPredicate instrument) {
			return ModCriteriaTriggers.ADJUST_INSTRUMENT.createCriterion(
				new TriggerInstance(Optional.empty(), Optional.of(instrument))
			);
		}

		public boolean matches(ItemStack instrument) {
			return this.instrument.isEmpty() || this.instrument.get().test(instrument);
		}
	}
}