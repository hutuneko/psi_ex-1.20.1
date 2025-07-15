package com.hutuneko.psi_ex.compat;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.item.ItemStorage;
import com.hutuneko.psi_ex.spell.selector.PieceSelector_ScrollData;
import com.hutuneko.psi_ex.spell.selector.PieceSelector_data;
import com.hutuneko.psi_ex.spell.trick.*;
import moffy.addonapi.AddonModule;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import vazkii.psi.api.PsiAPI;

public class DefaultCompatModule extends AddonModule {
    public DefaultCompatModule() {
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "pieceselector_data"), PieceSelector_data.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "copy"), PieceTrick_copy.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "eidos_renewal"), eidos_renewal.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "coordinate_eidos_renewal"), PieceTrick_coordinate_eidos_renewal.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "pieceselector_scrolldata"), PieceSelector_ScrollData.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "piecetrick_offhandattack"), PieceTrick_OffhandAttack.class);
        PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "pieceselector_oredouble"), PieceTrick_OreDouble.class);
        //ここから上は登録できる。
        PsiEXRegistry.STORAGE = PsiEXRegistry.ITEMS.register("storage", () ->
                new ItemStorage(new Item.Properties().stacksTo(1))
        );
        PsiEXRegistry.CAST_SCROLL = PsiEXRegistry.ITEMS.register("cast_scroll", () ->
                new Item(new Item.Properties())
        );
    }
}
