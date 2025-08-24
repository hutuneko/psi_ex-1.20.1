package com.hutuneko.psi_ex.item;

import com.hutuneko.psi_ex.system.SocketableProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class PsiCuriosbullet extends Item implements ICurioItem {

    public static final String NBT_SOCKETS  = "psi_sockets";
    public static final String NBT_SELECTED = "psi_selected";

    public PsiCuriosbullet(Properties props) {
        super(props.stacksTo(1));
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new SocketableProvider(stack);
    }

    @Override
    public boolean shouldOverrideMultiplayerNbt() {
        return true;
    }

    public boolean canEquipFromUse(net.minecraft.world.inventory.Slot slot, ItemStack stack, ItemStack curio) {
        return true;
    }
    public net.minecraft.world.InteractionResult onEquipFromUse(
            net.minecraft.world.inventory.Slot slot, ItemStack stack, ItemStack curio) {
        return net.minecraft.world.InteractionResult.SUCCESS;
    }
}
