package com.hutuneko.psi_ex.mixin;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import vazkii.botania.common.block.block_entity.mana.ManaSpreaderBlockEntity;
import vazkii.botania.common.item.ManaBlasterItem;

import java.util.function.Predicate;

@Mixin({ManaSpreaderBlockEntity.class, ManaBlasterItem.class})
public class MixinLensTagOverride {
    @Unique
    private static final ResourceLocation CUSTOM_LENS_TAG = new ResourceLocation("psi_ex", "manalens");

    @Unique
    private static boolean psi_ex_1_20_1$redirectIsLensTag(ItemStack stack, ResourceLocation origTag) {
        if (stack.is((Predicate<Holder<Item>>) CUSTOM_LENS_TAG)) return true;
        return stack.is((Predicate<Holder<Item>>) origTag);
    }
}

