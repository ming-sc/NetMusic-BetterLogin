package com.github.img.netmusicbetterlogin.client.event;

import com.github.img.netmusicbetterlogin.NetMusicBetterLogin;
import com.github.img.netmusicbetterlogin.client.command.ClientLoginCommand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = NetMusicBetterLogin.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEvent {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(ClientLoginCommand.get());
    }

}
