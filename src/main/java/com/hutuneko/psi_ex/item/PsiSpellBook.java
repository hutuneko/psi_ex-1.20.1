package com.hutuneko.psi_ex.item;

import com.hutuneko.psi_ex.system.SocketableProvider;
import com.hutuneko.psi_ex.system.StackSocketable;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import vazkii.psi.api.PsiAPI;

public class PsiSpellBook extends SpellBook {
    private final int maxStackSize;
    public static int getIndex(ItemStack stack){
        int slot = -1;
        SpellSelectionManager spellSelectionManager = ClientMagicData.getSpellSelectionManager();
        if (stack.getItem() instanceof SpellBook book) {
            slot = spellSelectionManager.getSelectionIndex();
        }
        return slot;
    }
    public PsiSpellBook(int maxStackSize) {
        super(maxStackSize,SpellRarity.LEGENDARY,new Item.Properties().stacksTo(maxStackSize));
        this.maxStackSize = maxStackSize;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        var provider = new SocketableProvider(stack);
        provider.getCapability(PsiAPI.SOCKETABLE_CAPABILITY).ifPresent(cap -> {
            if (cap instanceof StackSocketable s) {
                s.setMaxSlots(maxStackSize);
            }
        });

        return provider;
    }
}
