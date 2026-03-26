package com.github.img.netmusicbetterlogin.event;

import com.github.img.netmusicbetterlogin.NetMusicBetterLogin;
import com.github.img.netmusicbetterlogin.command.ServerLoginCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NetMusicBetterLogin.MODID)
public class ServerEvent {

    @SubscribeEvent
    public static void onRegisterCommandsEvent(RegisterCommandsEvent event) {
        event.getDispatcher().register(ServerLoginCommand.get());
    }

}
