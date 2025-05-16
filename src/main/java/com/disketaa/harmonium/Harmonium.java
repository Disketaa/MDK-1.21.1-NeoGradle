package com.disketaa.harmonium;

import com.disketaa.harmonium.block.ModBlocks;
import com.disketaa.harmonium.config.Config;
import com.disketaa.harmonium.config.ModConfigurationScreens;
import com.disketaa.harmonium.entity.ModEntityGearEvents;
import com.disketaa.harmonium.event.ModShieldBlockHandler;
import com.disketaa.harmonium.gui.ModCreativeTabItemRemover;
import com.disketaa.harmonium.gui.ModCreativeTabOrganizer;
import com.disketaa.harmonium.gui.ModCreativeTabs;
import com.disketaa.harmonium.item.ModArmorMaterials;
import com.disketaa.harmonium.item.ModItems;
import com.disketaa.harmonium.item.custom.ModInstrumentItem;
import com.disketaa.harmonium.item.custom.ModShieldItem;
import com.disketaa.harmonium.sound.ModSoundType;
import com.disketaa.harmonium.config.ModConditions;
import com.disketaa.harmonium.util.ModDataComponents;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.Map;

@Mod(Harmonium.MOD_ID)
public class Harmonium {
	public static final String MOD_ID = "harmonium";
	private static final Logger LOGGER = LogUtils.getLogger();

	public Harmonium(IEventBus modEventBus, ModContainer modContainer) {
		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
		modEventBus.addListener(this::registerConditions);

		NeoForge.EVENT_BUS.addListener(ModShieldBlockHandler::onShieldBlock);
		NeoForge.EVENT_BUS.register(this);

		modEventBus.addListener(ModCreativeTabOrganizer::onBuildCreativeModeTabContents);
		modEventBus.addListener(ModCreativeTabItemRemover::onBuildCreativeModeTabContents);

		ModDataComponents.register(modEventBus);
		ModCreativeTabs.register(modEventBus);
		ModBlocks.register(modEventBus);
		ModItems.register(modEventBus);
		ModArmorMaterials.register(modEventBus);
		ModEntityGearEvents.register();
		ModSoundType.register(modEventBus);

		ModConditions.register("config", ModConditions.ConfigValueCondition.CODEC);
	}

	private void registerConditions(RegisterEvent event) {
		event.register(NeoForgeRegistries.Keys.CONDITION_CODECS, helper -> {
			for (Map.Entry<String, MapCodec<? extends ICondition>> entry : ModConditions.SERIALIZERS.entrySet()) {
				ResourceLocation key = ResourceLocation.fromNamespaceAndPath(MOD_ID, entry.getKey());
				helper.register(key, entry.getValue());
			}
		});
	}

	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
	}

	@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
			event.enqueueWork(() -> {
				ModLoadingContext.get().registerExtensionPoint(
					IConfigScreenFactory.class,
					() -> (container, parent) -> new ModConfigurationScreens(parent));

				ItemProperties.registerGeneric(
					ResourceLocation.fromNamespaceAndPath(MOD_ID, "blocking"),
					(stack, level, entity, seed) ->
						entity != null &&
							entity.isUsingItem() &&
							entity.getUseItem() == stack &&
							stack.getItem() instanceof ModShieldItem ? 1.0F : 0.0F);

				ItemProperties.registerGeneric(
					ResourceLocation.fromNamespaceAndPath(MOD_ID, "playing"),
					(stack, level, entity, seed) ->
						entity != null &&
							entity.isUsingItem() &&
							entity.getUseItem() == stack &&
							entity.getUseItem().getItem() instanceof ModInstrumentItem ? 1.0F : 0.0F);
			});
		}
	}
}