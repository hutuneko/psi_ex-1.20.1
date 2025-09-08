package com.hutuneko.psi_ex.system;

import com.hutuneko.psi_ex.PsiEX;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

// ModEventHandlers.java
@Mod.EventBusSubscriber(modid = PsiEX.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventHandlers {
    @SubscribeEvent
    public static void onEntityAttributeMod(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, PsiEXAttributes.PSI_SPELL_RANGE.get());
        event.add(EntityType.PLAYER, PsiEXAttributes.PSI_PSION_POINT.get());
    }
}
