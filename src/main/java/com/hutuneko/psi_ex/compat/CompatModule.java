package com.hutuneko.psi_ex.compat;


import com.hutuneko.psi_ex.PsiEX;
import moffy.addonapi.AddonModuleProvider;
import net.minecraft.resources.ResourceLocation;

public class CompatModule extends AddonModuleProvider {
    @Override
    public void registerRawModules() {
        addRawModule(new ResourceLocation(PsiEX.MOD_ID, "defaultcompatmodule"),
        "Default Compat",
                DefaultCompatModule.class,
                new String[] { "psi", PsiEX.MOD_ID});
        addRawModule(new ResourceLocation(PsiEX.MOD_ID, "botaniacompat"),
                "Botania Compat",
                BotaniaCompatModule.class,
                new String[] { "psi", "botania" });
        addRawModule(new ResourceLocation(PsiEX.MOD_ID, "arscompat"),
                "Ars Nouveau Compat",
                ArsCompatModule.class,
                new String[] { "psi", "ars_nouveau" });
        addRawModule(new ResourceLocation(PsiEX.MOD_ID, "ironscompat"),
                "Iron's Compat",
                IronsCompatModule.class,
                new String[] { "psi", "irons_spellbooks" });
        addRawModule(new ResourceLocation(PsiEX.MOD_ID, "mekcompat"),
                "Mekanism Compat",
                MekanismCompatModule.class,
                new String[] { "psi", "mekanism" });
        addRawModule(new ResourceLocation(PsiEX.MOD_ID, "curioscompat"),
                "Curios Compat",
                CuriosCompatModule.class,
                new String[] { "psi", "curios" });
        addRawModule(new ResourceLocation(PsiEX.MOD_ID, "ccccompat"),
                "CCC Compat",
                CCCuriosModule.class,
                new String[] { "psi", "curios", "computercraft" });
    }
    @Override
    public String getModId() {
        return PsiEX.MOD_ID;
    }
}
