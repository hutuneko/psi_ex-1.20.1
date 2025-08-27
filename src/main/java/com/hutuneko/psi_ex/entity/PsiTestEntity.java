package com.hutuneko.psi_ex.entity;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class PsiTestEntity extends Zombie {
    public PsiTestEntity(EntityType<? extends LivingEntity> type, Level level) {
        super((EntityType<? extends Zombie>) type, level);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
    @Override
    public boolean isDeadOrDying(){
        return false;
    }
}
