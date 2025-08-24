package com.hutuneko.psi_ex.compat;

import moffy.addonapi.AddonModule;

public class MekanismCompatModule extends AddonModule {
    public MekanismCompatModule() {
        PsiEXRegistry.PSI_GAS = PsiEXRegistry.GASES.register("psi_gas",0x8A2BE2);
    }

}

