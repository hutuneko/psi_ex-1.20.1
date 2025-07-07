package com.hutuneko.psi_ex.compat;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.spell.trick.PieceTrick_CastScroll;
import moffy.addonapi.AddonModule;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import vazkii.psi.api.PsiAPI;

public class IronsCompatModule extends AddonModule {
    public IronsCompatModule() {
        super();
    }
    public boolean shouldLoad() {
        return ModList.get().isLoaded("ars_nouveau");
    }

    public void init() {
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "piecetrick_castscroll"), PieceTrick_CastScroll.class);
    }
}

