package com.hutuneko.psi_ex;

import com.hutuneko.psi_ex.compat.CompatModule;
import moffy.addonapi.AddonModuleRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = PsiEX.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static ForgeConfigSpec.BooleanValue LOG_DIRT_BLOCK;
    public static ForgeConfigSpec.IntValue MAGIC_NUMBER;
    public static ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS;
    public static ForgeConfigSpec.BooleanValue USE_SHADER;

    public static ForgeConfigSpec COMMON_SPEC;
    public static ForgeConfigSpec CLIENT_SPEC;

    public static boolean logDirtBlock;
    public static int magicNumber;
    public static String magicNumberIntroduction;
    public static Set<Item> items;
    public static boolean useShader;

    public static void registerConfig() {
        final ForgeConfigSpec.Builder COMMON = new ForgeConfigSpec.Builder();
        ForgeConfigSpec.Builder CLIENT = new ForgeConfigSpec.Builder();
        CLIENT.comment("Client settings").push("client");
        USE_SHADER = CLIENT
                .comment("Enable shader rendering for special effects")
                .define("useShader", true);
        CLIENT.pop();
        AddonModuleRegistry.INSTANCE.LoadModule(new CompatModule(), COMMON);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON.build());
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT.build());
    }

//    private static boolean validateItemName(final Object obj) {
//        return obj instanceof String name && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(name));
//    }
//
//    @SubscribeEvent
//    public static void onLoad(final ModConfigEvent event) {
//        if (event.getConfig().getSpec() == COMMON_SPEC) {
//            logDirtBlock = LOG_DIRT_BLOCK.get();
//            magicNumber = MAGIC_NUMBER.get();
//            magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
//            items = ITEM_STRINGS.get().stream()
//                    .map(name -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(name)))
//                    .collect(Collectors.toSet());
//        } else if (event.getConfig().getSpec() == CLIENT_SPEC) {
//            useShader = USE_SHADER.get();
//        }
//    }
}
