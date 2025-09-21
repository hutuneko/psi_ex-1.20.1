package com.hutuneko.psi_ex.compat;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.entity.PsiArrowEntity;
import com.hutuneko.psi_ex.entity.PsiBarrierEntity;
import com.hutuneko.psi_ex.entity.PsiNeedleDartEntity;
import com.hutuneko.psi_ex.entity.PsiTestEntity;
import com.hutuneko.psi_ex.system.attribute.AttributeEditorMenu;
import mekanism.common.registration.impl.GasDeferredRegister;
import mekanism.common.registration.impl.GasRegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PsiEXRegistry {
    public static final GasDeferredRegister GASES =
            new GasDeferredRegister(PsiEX.MOD_ID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PsiEX.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, PsiEX.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, PsiEX.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, PsiEX.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, PsiEX.MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, PsiEX.MOD_ID);
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, PsiEX.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB,PsiEX.MOD_ID);
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, PsiEX.MOD_ID);

    public static RegistryObject<Item> PSI_MANA_LENS = null;
    public static RegistryObject<Item> STORAGE = null;
    public static RegistryObject<Item> CAST_SCROLL = null;
    public static RegistryObject<Item> TESTBULLET = null;
    public static RegistryObject<Item> PSI_ARROW = null;
    public static RegistryObject<Item> PSI_CURIO_BULLET = null;
    public static RegistryObject<Item> PSI_SPELLBOOK = null;
    public static RegistryObject<Item> PSI_SPIRITS_EYE = null;
    public static RegistryObject<Item> PSI_NEEDLE_DART = null;
    public static RegistryObject<Item> PSI_BOW = null;

    public static GasRegistryObject PSI_GAS = null;

    public static RegistryObject<EntityType<PsiArrowEntity>> PSI_ARROW_ENTITY = null;
    public static RegistryObject<EntityType<PsiNeedleDartEntity>> PSI_NEEDLE_DARTENTITY = null;
    public static RegistryObject<EntityType<PsiBarrierEntity>> PSI_BRRIER_ENTITY = null;
    public static RegistryObject<EntityType<PsiTestEntity>> PSI_TEST_ENTITY = null;

    public static RegistryObject<MenuType<AttributeEditorMenu>> ATTRIBUTE_EDITOR = null;

    public static RegistryObject<CreativeModeTab> CREATIVE_TAB_ITEMS = null;
}
