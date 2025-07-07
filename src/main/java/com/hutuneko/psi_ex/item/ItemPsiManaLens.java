package com.hutuneko.psi_ex.item;

import com.hutuneko.psi_ex.system.MySpellAcceptor;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import vazkii.botania.common.item.lens.LensItem;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.spell.ISpellAcceptor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemPsiManaLens extends LensItem {
    public ItemPsiManaLens(Properties props) {
        super(props,
                ItemPsiManaLensLogic.MY_PSI_LENS_BEHAVIOR,
                0x4F9BF9
        );
    }
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag unusedNbt) {
        LazyOptional<ISpellAcceptor> cap = LazyOptional.of(() -> new MySpellAcceptor(stack));
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> requested, @Nullable Direction side) {
                return PsiAPI.SPELL_ACCEPTOR_CAPABILITY.orEmpty(requested, cap);
            }
        };
    }
}

