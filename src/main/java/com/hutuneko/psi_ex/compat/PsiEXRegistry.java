package com.hutuneko.psi_ex.compat;

import com.hutuneko.psi_ex.PsiEX;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PsiEXRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, PsiEX.MOD_ID);
    public static RegistryObject<Item> PSI_MANA_LENS = null;
    public static RegistryObject<Item> STORAGE = null;
    public static RegistryObject<Item> CAST_SCROLL = null;
}
