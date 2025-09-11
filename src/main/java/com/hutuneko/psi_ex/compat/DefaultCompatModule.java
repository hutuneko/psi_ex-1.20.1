package com.hutuneko.psi_ex.compat;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.entity.PsiArrowEntity;
import com.hutuneko.psi_ex.entity.PsiTestEntity;
import com.hutuneko.psi_ex.item.ItemStorage;
import com.hutuneko.psi_ex.item.Itemtestbullet;
import com.hutuneko.psi_ex.item.PsiArrowItem;
import com.hutuneko.psi_ex.spell.selector.PieceSelector_ScrollData;
import com.hutuneko.psi_ex.spell.selector.PieceSelector_data;
import com.hutuneko.psi_ex.spell.trick.*;
import moffy.addonapi.AddonModule;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;
import vazkii.psi.api.PsiAPI;
import vazkii.psi.common.item.base.ModItems;

public class DefaultCompatModule extends AddonModule {
    public DefaultCompatModule() {
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "pieceselector_data"), PieceSelector_data.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "copy"), PieceTrick_copy.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "eidos_renewal"), PieceTrick_Eidos_renewal.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "coordinate_eidos_renewal"), PieceTrick_coordinate_eidos_renewal.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "pieceselector_scrolldata"), PieceSelector_ScrollData.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "piecetrick_offhandattack"), PieceTrick_OffhandAttack.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "piecetrick_oredouble"), PieceTrick_OreDouble.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "piecetrick_ejection"), PieceTrick_Ejection.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "piecetrick_lunastrike"), PieceTrick_LunaStrike.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "pieceoperator_dirchange"), PieceTrick_DirChange.class);

        PsiEXRegistry.STORAGE = PsiEXRegistry.ITEMS.register("storage", () ->
                new ItemStorage(new Item.Properties().stacksTo(1))
        );
        PsiEXRegistry.CAST_SCROLL = PsiEXRegistry.ITEMS.register("cast_scroll", () ->
                new Item(new Item.Properties().stacksTo(1))
        );
        PsiEXRegistry.TESTBULLET = PsiEXRegistry.ITEMS.register("test_bullet", () ->
                new Itemtestbullet(new Item.Properties().stacksTo(1))
        );
        PsiEXRegistry.PSI_ARROW = PsiEXRegistry.ITEMS.register("psi_arrow", () ->
                new PsiArrowItem(new Item.Properties()));

        PsiEXRegistry.PSI_ARROW_ENTITY = PsiEXRegistry.ENTITIES.register("psi_arrow_entity", () ->
                EntityType.Builder.<PsiArrowEntity>of(PsiArrowEntity::new, MobCategory.MISC)
                        .sized(0.5F, 0.5F)
                        .clientTrackingRange(4)
                        .updateInterval(20)
                        .build("psi_arrow_entity"));

        PsiEXRegistry.PSI_TEST_ENTITY = PsiEXRegistry.ENTITIES.register("dummy_villager",
                () -> EntityType.Builder.of(PsiTestEntity::new, MobCategory.MISC)
                        .sized(0.6f, 1.95f)      // 村人サイズ
                        .clientTrackingRange(8)
                        .build(new ResourceLocation(PsiEX.MOD_ID, "dummy_villager").toString()));
        PsiEXRegistry.CREATIVE_TAB_ITEMS = PsiEXRegistry.TABS.register(PsiEX.MOD_ID, () ->
                CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.tab." + PsiEX.MOD_ID))
                        .icon(() -> new ItemStack(ModItems.cad))
                        .displayItems((params, output) -> {
                            for (RegistryObject<Item> regObj : PsiEXRegistry.ITEMS.getEntries()) {
                                output.accept(regObj.get());
                            }
                        })
                        .build()
        );
    }
}
