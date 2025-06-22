package com.hutuneko.psi_ex.spell;

import com.hutuneko.psi_ex.item.ItemStorage;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import vazkii.psi.api.spell.Spell;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.piece.PieceSelector;

public class PieceSelector_data extends PieceSelector {


    public PieceSelector_data(Spell spell) {
        super(spell);
    }

    @Override
    public Class<?> getEvaluationType() {
        return Double.class;
    }

    @Override
    public ListTag execute(SpellContext context) throws SpellRuntimeException {
        if (context.caster == null) {
            throw new SpellRuntimeException("Must be a player");
        }
        Player player = context.caster;

        // 手に持っている storage アイテムを取得
        ItemStack stack = player.getMainHandItem();
        if (stack.isEmpty() || stack.getItem() != com.hutuneko.psi_ex.item.ModItems.STORAGE.get()) {
            throw new SpellRuntimeException("No storage item in hand");
        }

        // ItemStorage ユーティリティで中身の ListTag をそのまま返す
        return ItemStorage.getStoredBlocks(stack);
    }

}



