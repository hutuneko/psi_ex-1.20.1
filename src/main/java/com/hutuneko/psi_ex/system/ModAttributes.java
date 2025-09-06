package com.hutuneko.psi_ex.system;

import com.hutuneko.psi_ex.PsiEX;
import com.hutuneko.psi_ex.compat.PsiEXRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.minecraft.world.entity.monster.ZombifiedPiglin.createAttributes;

@Mod.EventBusSubscriber(modid = PsiEX.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ModAttributes {
    @SubscribeEvent
    public static void onEntityAttributeCreation(net.minecraftforge.event.entity.EntityAttributeCreationEvent e) {
        e.put(PsiEXRegistry.PSI_TEST_ENTITY.get(), createAttributes().build());
    }
}
