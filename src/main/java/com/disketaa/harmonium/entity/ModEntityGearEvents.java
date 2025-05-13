package com.disketaa.harmonium.entity;

import com.disketaa.harmonium.config.Config;
import com.disketaa.harmonium.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModEntityGearEvents {
	private static final List<ArmorSpawnEntry> ARMOR_ENTRIES = new ArrayList<>();
	private static final List<ToolSpawnEntry> TOOL_ENTRIES = new ArrayList<>();

	static {
		registerArmorSet(new ArmorSpawnEntry(
			ModItems.BRONZE_HELMET::get,
			ModItems.BRONZE_CHESTPLATE::get,
			ModItems.BRONZE_LEGGINGS::get,
			ModItems.BRONZE_BOOTS::get,
			0.07f, 0.05f, 0.06f, 0.08f
		));

		registerToolSet(new ToolSpawnEntry(
			ModItems.BRONZE_SWORD::get,
			ModItems.BRONZE_SHOVEL::get,
			0.03f, 0.02f
		));
	}

	public static void register() {
		NeoForge.EVENT_BUS.addListener(ModEntityGearEvents::onMobSpawn);
		NeoForge.EVENT_BUS.addListener(ModEntityGearEvents::onWitherSkeletonJoinLevel);
	}

	public static void registerArmorSet(ArmorSpawnEntry entry) {
		ARMOR_ENTRIES.add(entry);
	}

	public static void registerToolSet(ToolSpawnEntry entry) {
		TOOL_ENTRIES.add(entry);
	}

	private static void onMobSpawn(MobSpawnEvent.PositionCheck event) {
		Mob mob = event.getEntity();

		if (!(mob instanceof Zombie ||
			mob instanceof Skeleton ||
			mob instanceof WitherSkeleton ||
			mob instanceof Stray)) {
			return;
		}

		BlockPos pos = mob.blockPosition();
		RandomSource random = event.getEntity().getRandom();
		DifficultyInstance difficulty = event.getLevel().getCurrentDifficultyAt(pos);

		tryAddArmor(mob, random, difficulty);

		if (mob instanceof Zombie) {
			tryAddTools(mob, random, difficulty);
		}
	}

	private static void onWitherSkeletonJoinLevel(EntityJoinLevelEvent event) {
		if (Config.removeStoneTools && event.getEntity() instanceof WitherSkeleton witherSkeleton) {
			ItemStack mainHand = witherSkeleton.getItemBySlot(EquipmentSlot.MAINHAND);
			if (mainHand.isEmpty() || mainHand.is(Items.STONE_SWORD)) {
				witherSkeleton.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.BRONZE_SWORD.get()));
			}
		}
	}

	private static void tryAddArmor(LivingEntity mob, RandomSource random, DifficultyInstance difficulty) {
		float localDifficulty = difficulty.getSpecialMultiplier();
		float chanceMultiplier = (mob.level().getDifficulty() == Difficulty.HARD ? 1.5f : 1.0f);

		for (ArmorSpawnEntry entry : ARMOR_ENTRIES) {
			tryAddArmorPiece(mob, random, localDifficulty, chanceMultiplier,
				entry.helmet(), entry.helmetChance(), EquipmentSlot.HEAD);
			tryAddArmorPiece(mob, random, localDifficulty, chanceMultiplier,
				entry.chestplate(), entry.chestplateChance(), EquipmentSlot.CHEST);
			tryAddArmorPiece(mob, random, localDifficulty, chanceMultiplier,
				entry.leggings(), entry.leggingsChance(), EquipmentSlot.LEGS);
			tryAddArmorPiece(mob, random, localDifficulty, chanceMultiplier,
				entry.boots(), entry.bootsChance(), EquipmentSlot.FEET);
		}
	}

	private static void tryAddTools(LivingEntity mob, RandomSource random, DifficultyInstance difficulty) {
		float localDifficulty = difficulty.getSpecialMultiplier();
		float chanceMultiplier = (mob.level().getDifficulty() == Difficulty.HARD ? 1.5f : 1.0f);

		for (ToolSpawnEntry entry : TOOL_ENTRIES) {
			tryAddTool(mob, random, localDifficulty, chanceMultiplier,
				entry.sword(), entry.swordChance());
			tryAddTool(mob, random, localDifficulty, chanceMultiplier,
				entry.shovel(), entry.shovelChance());
		}
	}

	private static void tryAddArmorPiece(LivingEntity mob, RandomSource random,
	                                     float localDifficulty, float chanceMultiplier,
	                                     Supplier<Item> armorItem, float baseChance, EquipmentSlot slot) {
		float chance = baseChance * chanceMultiplier * (1 + localDifficulty);
		if (mob.getItemBySlot(slot).isEmpty() && random.nextFloat() < chance) {
			mob.setItemSlot(slot, new ItemStack(armorItem.get()));
		}
	}

	private static void tryAddTool(LivingEntity mob, RandomSource random,
	                               float localDifficulty, float chanceMultiplier,
	                               Supplier<Item> toolItem, float baseChance) {
		if (mob.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
			float chance = baseChance * chanceMultiplier * (1 + localDifficulty);
			if (random.nextFloat() < chance) {
				mob.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(toolItem.get()));
			}
		}
	}

	public record ArmorSpawnEntry(Supplier<Item> helmet, Supplier<Item> chestplate, Supplier<Item> leggings,
	                              Supplier<Item> boots, float helmetChance, float chestplateChance,
	                              float leggingsChance, float bootsChance) {
	}

	public record ToolSpawnEntry(Supplier<Item> sword, Supplier<Item> shovel,
	                             float swordChance, float shovelChance) {
	}
}