package com.github.img.netmusicbetterlogin.event;

import com.github.img.netmusicbetterlogin.NetMusicBetterLogin;
import com.github.img.netmusicbetterlogin.config.GeneralConfig;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;

@EventBusSubscriber(modid = NetMusicBetterLogin.MODID)
public class CommonEvent {
    private static final String CONFIG_NAME = NetMusicBetterLogin.MODID + "-common.toml";

    @SubscribeEvent
    public static void onConfigLoading(ModConfigEvent.Loading event) {
        String fileName = event.getConfig().getFileName();
        if (CONFIG_NAME.equals(fileName)) {
            reloadCookie();
        }
    }

    @SubscribeEvent
    public static void onConfigReloading(ModConfigEvent.Reloading event) {
        String fileName = event.getConfig().getFileName();
        if (CONFIG_NAME.equals(fileName)) {
            reloadCookie();
        }
    }

    public static void reloadCookie() {
        NetMusicBetterLogin.NETEASE_API.setCookie(GeneralConfig.COOKIE.get());
    }
}
