package com.hutuneko.psi_ex;

import com.hutuneko.psi_ex.compat.CompatModule;
import moffy.addonapi.AddonModuleRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Updated configuration class, modeled after TicEXConfig.
 */
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

        // General settings
        COMMON.comment("General settings").push("general");
        LOG_DIRT_BLOCK = COMMON
                .comment("Whether to log the dirt block on common setup")
                .define("logDirtBlock", true);
        MAGIC_NUMBER = COMMON
                .comment("A magic number")
                .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);
        MAGIC_NUMBER_INTRODUCTION = COMMON
                .comment("Introduction message for the magic number")
                .define("magicNumberIntroduction", "The magic number is...");
        ITEM_STRINGS = COMMON
                .comment("A list of items to log on common setup.")
                .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);
        COMMON.pop();

        CLIENT.comment("Client settings").push("client");
        USE_SHADER = CLIENT
                .comment("Enable shader rendering for special effects")
                .define("useShader", true);
        CLIENT.pop();

        COMMON_SPEC = COMMON.build();
        CLIENT_SPEC = CLIENT.build();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
        AddonModuleRegistry.INSTANCE.LoadModule(new CompatModule(), COMMON);
    }

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String name && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(name));
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() == COMMON_SPEC) {
            logDirtBlock = LOG_DIRT_BLOCK.get();
            magicNumber = MAGIC_NUMBER.get();
            magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
            items = ITEM_STRINGS.get().stream()
                    .map(name -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(name)))
                    .collect(Collectors.toSet());
        } else if (event.getConfig().getSpec() == CLIENT_SPEC) {
            useShader = USE_SHADER.get();
        }
    }
}
