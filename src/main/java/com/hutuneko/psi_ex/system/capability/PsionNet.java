package com.hutuneko.psi_ex.system.capability;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

// PsionNet.java
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class PsionNet {
    private static final String PROTOCOL = "1";
    public static SimpleChannel CHANNEL;
    private static int id = 0;

    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CHANNEL = NetworkRegistry.newSimpleChannel(
                    new ResourceLocation("psi_ex", "main"),
                    () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals
            );
            CHANNEL.messageBuilder(SyncPsionS2C.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                    .encoder(SyncPsionS2C::encode)
                    .decoder(SyncPsionS2C::decode)
                    .consumerMainThread(SyncPsionS2C::handle)
                    .add();
        });
    }
}
