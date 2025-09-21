package com.hutuneko.psi_ex.item;

import com.hutuneko.psi_ex.system.capability.SocketableProvider;
import com.hutuneko.psi_ex.system.capability.StackSocketable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import vazkii.psi.api.PsiAPI;

import javax.annotation.Nullable;

public class PsiBow extends BowItem {
    public PsiBow(Properties properties) {
        super(properties);
    }
    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        var provider = new SocketableProvider(stack);
        provider.getCapability(PsiAPI.SOCKETABLE_CAPABILITY).ifPresent(cap -> {
            if (cap instanceof StackSocketable s) {
                s.setMaxSlots(3);
            }
        });
        return provider;
    }
}
