package com.hutuneko.psi_ex.system;

import com.hutuneko.psi_ex.PsiEX;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = PsiEX.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FakePlayerProtection {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onChangeTarget(LivingChangeTargetEvent ev) {
        if (ev.getNewTarget() instanceof FakePlayer) {
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onLivingAttack(LivingAttackEvent ev) {
        if (ev.getEntity() instanceof FakePlayer) {
            ev.setCanceled(true);
        }
    }
}
