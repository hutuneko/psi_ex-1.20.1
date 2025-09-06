package com.hutuneko.psi_ex.compat;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.item.PsiSpellBook;
import com.hutuneko.psi_ex.spell.trick.PieceTrick_CastScroll;
import moffy.addonapi.AddonModule;
import net.minecraft.resources.ResourceLocation;
import vazkii.psi.api.PsiAPI;

public class IronsCompatModule extends AddonModule {
    public IronsCompatModule() {
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "piecetrick_castscroll"), PieceTrick_CastScroll.class);
        PsiEXRegistry.PSI_SPELLBOOK = PsiEXRegistry.ITEMS.register("psi_spellbook", () -> new PsiSpellBook(12));
    }
}

