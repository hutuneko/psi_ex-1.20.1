package com.hutuneko.psi_ex.spell;

import com.hutuneko.psi_ex.PsiEX;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.api.spell.SpellContext;
import vazkii.psi.api.spell.SpellRuntimeException;
import vazkii.psi.api.spell.EnumPieceType;
import vazkii.psi.api.spell.piece.PieceSelector;

import java.util.List;

public class PieceSelector_ScrollData extends PieceSelector {

    public PieceSelector_ScrollData(vazkii.psi.api.spell.Spell spell) {
        super(spell);
    }

    @Override
    public EnumPieceType getPieceType() {
        return EnumPieceType.SELECTOR;
    }

    @Override
    public Class<ItemStack> getEvaluationType() {
        return ItemStack.class;
    }

    @Override
    public ItemStack execute(SpellContext context) throws SpellRuntimeException {
        Player player = context.caster;
        ItemStack held = player.getMainHandItem();
        if (held.isEmpty()) {
            throw new SpellRuntimeException("メインハンドにアイテムを持ってください");
        }

        List<ItemStack> items = player.getInventory().items;
        int idx = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == held) {
                idx = i;
                break;
            }
        }
        if (idx < 0) {
            throw new SpellRuntimeException("インベントリに手持ちアイテムが見つかりません");
        }
        int right = idx + 1;
        if (right >= items.size()) {
            throw new SpellRuntimeException("右隣にアイテムがありません");
        }
        ItemStack neighbor = items.get(right);
        if (neighbor.isEmpty()) {
            throw new SpellRuntimeException("右隣のアイテムが空です");
        }
        return neighbor;
    }

    public static void register() {
        PsiAPI.registerSpellPieceAndTexture(
                new ResourceLocation(PsiEX.MOD_ID, "scroll_data_selector"),
                PieceSelector_ScrollData.class
        );
    }
}
