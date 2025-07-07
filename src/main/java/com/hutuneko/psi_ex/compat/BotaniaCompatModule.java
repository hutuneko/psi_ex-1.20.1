package com.hutuneko.psi_ex.compat;

import com.hutuneko.psi_ex.PsiEX;
import moffy.addonapi.AddonModule;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.hutuneko.psi_ex.item.ItemPsiManaLens;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.RegistryObject;

public class BotaniaCompatModule extends AddonModule {
    public BotaniaCompatModule() {
        super();
    }
    public boolean shouldLoad() {
        return ModList.get().isLoaded("botania");
    }

    public void init() {
        final RegistryObject<Item> PSI_MANA_LENS =
                DeferredRegister.create(ForgeRegistries.ITEMS, PsiEX.MOD_ID).register("psi_mana_lens",
                        () -> new ItemPsiManaLens(new Item.Properties().stacksTo(1)));
    }
}

