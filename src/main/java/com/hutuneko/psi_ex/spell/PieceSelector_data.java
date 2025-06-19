package com.hutuneko.psi_ex.spell;

import com.hutuneko.psi_ex.item.ItemStorage;
import com.hutuneko.psi_ex.item.ModItems;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.piece.PieceSelector;

public class PieceSelector_data extends PieceSelector {


    public PieceSelector_data(Spell spell) {
        super(spell);
    }

    @Override
    public Class<?> getEvaluationType() {
        return Double.class;
    }

    public static ListTag findStorageDataInPlayerInv(Player player) {
        Inventory inv = player.getInventory();
        for (int slot = 0; slot < inv.getContainerSize(); slot++) {
            ItemStack stack = inv.getItem(slot);
            if (!stack.isEmpty() && stack.getItem() == ModItems.STORAGE.get()) {
                return ItemStorage.getStoredBlocks(stack);
            }
        }

        return PieceSelector_data.findStorageDataInPlayerInv(player);

    }
}

