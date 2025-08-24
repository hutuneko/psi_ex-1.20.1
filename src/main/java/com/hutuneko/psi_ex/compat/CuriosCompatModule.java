package com.hutuneko.psi_ex.compat;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.item.PsiCuriosbullet;
import com.hutuneko.psi_ex.spell.operator.PieceOperator_getSpell;
import com.hutuneko.psi_ex.spell.selector.PieceSelector_data;
import com.hutuneko.psi_ex.spell.trick.PieceTrick_ExecuteSpell;
import moffy.addonapi.AddonModule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import vazkii.psi.api.PsiAPI;

public class CuriosCompatModule extends AddonModule {
    public CuriosCompatModule() {
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "pieceoperator_getspell"), PieceOperator_getSpell.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "piecetrick_executespell"), PieceTrick_ExecuteSpell.class);

        PsiEXRegistry.PSI_CURIO_BULLET = PsiEXRegistry.ITEMS.register("psi_curio_bullet", () ->
                new PsiCuriosbullet(new Item.Properties().stacksTo(1))
        );
    }
}

