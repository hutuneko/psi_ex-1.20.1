package com.hutuneko.psi_ex.compat;

import com.hutuneko.psi_ex.cc.PsiGlobalAPI;
import com.hutuneko.psi_ex.system.CCCevent;
import dan200.computercraft.api.ComputerCraftAPI;
import moffy.addonapi.AddonModule;
import net.minecraftforge.common.MinecraftForge;

public class CCCuriosModule extends AddonModule {
    public CCCuriosModule(){
        MinecraftForge.EVENT_BUS.addListener(CCCevent::onCommonSetup);
        ComputerCraftAPI.registerAPIFactory(PsiGlobalAPI::new);
    }
}

