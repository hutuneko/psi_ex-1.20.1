package com.hutuneko.psi_ex.compat;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.spell.trick.PieceTrick_ArsScrollCast;
import moffy.addonapi.AddonModule;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import vazkii.psi.api.PsiAPI;

public class ArsCompatModule extends AddonModule {
    public ArsCompatModule() {
        super();
    }
    public boolean shouldLoad() {
        return ModList.get().isLoaded("ars_nouveau");
    }

    public void init() {
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "piecetrick_arsscrollcast"), PieceTrick_ArsScrollCast.class);
    }
}

