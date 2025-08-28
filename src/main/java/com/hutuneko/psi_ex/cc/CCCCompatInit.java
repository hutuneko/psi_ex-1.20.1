package com.hutuneko.psi_ex.cc;

import dan200.computercraft.api.ComputerCraftAPI;

public class CCCCompatInit {
    public static void init(){
        ComputerCraftAPI.registerAPIFactory(PsiGlobalAPI::new);
    }
}
