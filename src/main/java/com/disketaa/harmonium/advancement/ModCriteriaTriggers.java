package com.disketaa.harmonium.advancement;

import com.disketaa.harmonium.advancement.trigger.AdjustInstrumentTrigger;
import com.disketaa.harmonium.Harmonium;
import net.minecraft.advancements.CriteriaTriggers;

public class ModCriteriaTriggers {
	public static final AdjustInstrumentTrigger ADJUST_INSTRUMENT = new AdjustInstrumentTrigger();

	public static void register() {
		CriteriaTriggers.register(
			Harmonium.MOD_ID + ":adjust_instrument",
			ADJUST_INSTRUMENT
		);
	}
}