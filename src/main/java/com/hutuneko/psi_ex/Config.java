package com.hutuneko.psi_ex;

import com.hutuneko.psi_ex.compat.CompatModule;
import moffy.addonapi.AddonModuleRegistry;
import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new Common(builder);
        COMMON_SPEC = builder.build();
    }

    public static class Common {
        public final ForgeConfigSpec.BooleanValue spellgeat;
        Common(ForgeConfigSpec.Builder builder) {
            builder.push("features");

            spellgeat = builder
                    .define("spellgeat", true);
            builder.pop();
        }
    }
    public static void registerConfig() {
        final ForgeConfigSpec.Builder COMMON = new ForgeConfigSpec.Builder();
        AddonModuleRegistry.INSTANCE.LoadModule(new CompatModule(), COMMON);
    }
}
