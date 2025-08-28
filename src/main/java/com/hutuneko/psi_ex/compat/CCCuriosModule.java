package com.hutuneko.psi_ex.compat;

import com.hutuneko.psi_ex.system.CCCevent;
import moffy.addonapi.AddonModule;
import net.minecraftforge.common.MinecraftForge;

public class CCCuriosModule extends AddonModule {
    public CCCuriosModule(){
        MinecraftForge.EVENT_BUS.addListener(CCCevent::onCommonSetup);
    }
}

