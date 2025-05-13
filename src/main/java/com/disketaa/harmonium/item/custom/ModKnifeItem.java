package com.disketaa.harmonium.item.custom;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModKnifeItem extends SwordItem {
	private static final ResourceLocation BASE_ATTACK_DAMAGE_ID = ResourceLocation.fromNamespaceAndPath("minecraft", "base_attack_damage");
	private static final ResourceLocation BASE_ATTACK_SPEED_ID = ResourceLocation.fromNamespaceAndPath("minecraft", "base_attack_speed");

	public ModKnifeItem(Tier tier, float attackDamage, float attackSpeed, Properties properties) {
		super(tier, properties
			.component(DataComponents.ATTRIBUTE_MODIFIERS, createAttributes(tier, attackDamage, attackSpeed))
			.component(DataComponents.TOOL, new Tool(
				List.of(
					Tool.Rule.minesAndDrops(List.of(Blocks.COBWEB), 15.0F),
					Tool.Rule.overrideSpeed(BlockTags.SWORD_EFFICIENT, 1.5F)
				),
				1.0F,
				2
			)));
	}

	public static @NotNull ItemAttributeModifiers createAttributes(Tier tier, float attackDamage, float attackSpeed) {
		var builder = ItemAttributeModifiers.builder();

		builder.add(
			Attributes.ATTACK_DAMAGE,
			new AttributeModifier(
				BASE_ATTACK_DAMAGE_ID,
				attackDamage + tier.getAttackDamageBonus(),
				AttributeModifier.Operation.ADD_VALUE
			),
			EquipmentSlotGroup.MAINHAND
		);

		builder.add(
			Attributes.ATTACK_SPEED,
			new AttributeModifier(
				BASE_ATTACK_SPEED_ID,
				attackSpeed,
				AttributeModifier.Operation.ADD_VALUE
			),
			EquipmentSlotGroup.MAINHAND
		);

		return builder.build();
	}
}