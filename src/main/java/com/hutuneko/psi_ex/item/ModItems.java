package com.hutuneko.psi_ex.item;

import com.hutuneko.psi_ex.PsiEX;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PsiEX.MOD_ID);

    public static final RegistryObject<Item> STORAGE = ITEMS.register("storage",
            () -> new ItemStorage(new Item.Properties()));
    public static final RegistryObject<Item> CAST_SCROLL = ITEMS.register("cast_scroll",
            () -> new Item(new Item.Properties())
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}

