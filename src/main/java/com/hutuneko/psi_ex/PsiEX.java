package com.hutuneko.psi_ex;
import com.hutuneko.psi_ex.compat.CompatModule;
import com.hutuneko.psi_ex.item.ModItems;
import com.hutuneko.psi_ex.spell.selector.PieceSelector_ScrollData;
import com.hutuneko.psi_ex.spell.selector.PieceSelector_data;
import com.hutuneko.psi_ex.spell.trick.*;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import vazkii.psi.api.PsiAPI;

import java.util.ArrayList;
import java.util.List;

@Mod(PsiEX.MOD_ID)
public class PsiEX {
    public static final String MOD_ID = "psi_ex";
    private static final Logger LOGGER = LogUtils.getLogger();

    public PsiEX() {
        Config.registerConfig();
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::registerSpellPieces);
        ModItems.register(modBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        if (Config.items != null) {
            Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
        }

        // 互換モジュールを手動で登録
        CompatModule compat = new CompatModule();
        compat.registerRawModules();
    }

    private void registerSpellPieces(final FMLCommonSetupEvent event) {
        LOGGER.info("🔧 registerSpellPieces が呼ばれました");
        event.enqueueWork(() -> {
            PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "pieceselector_data"), PieceSelector_data.class);
            PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "copy"), PieceTrick_copy.class);
            PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "eidos_renewal"), eidos_renewal.class);
            PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "coordinate_eidos_renewal"), PieceTrick_coordinate_eidos_renewal.class);
            PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "pieceselector_scrolldata"), PieceSelector_ScrollData.class);
            PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "piecetrick_offhandattack"), PieceTrick_OffhandAttack.class);
            PsiAPI.registerSpellPieceAndTexture(new ResourceLocation(PsiEX.MOD_ID, "pieceselector_oredouble"), PieceTrick_OreDouble.class);
        });
    }

    public static List<String> listAllAttributeNames() {
        List<String> names = new ArrayList<>();
        for (ResourceLocation rl : ForgeRegistries.ATTRIBUTES.getKeys()) {
            names.add(rl.toString());
        }
        return names;
    }
}
