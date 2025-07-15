package com.hutuneko.psi_ex.compat;

import moffy.addonapi.AddonModule;
import net.minecraft.world.item.Item;
import com.hutuneko.psi_ex.item.ItemPsiManaLens;

public class BotaniaCompatModule extends AddonModule {
    public BotaniaCompatModule() {
        PsiEXRegistry.PSI_MANA_LENS = PsiEXRegistry.ITEMS.register("psi_mana_lens", () ->
                new ItemPsiManaLens(new Item.Properties().stacksTo(1))
        );
    }
}

