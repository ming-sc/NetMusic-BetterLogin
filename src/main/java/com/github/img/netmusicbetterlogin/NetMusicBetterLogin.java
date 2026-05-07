package com.github.img.netmusicbetterlogin;

import com.github.img.netmusicbetterlogin.api.NeteaseApi;
import com.github.img.netmusicbetterlogin.compat.netmusic.NeteaseVIPResolver;
import com.github.img.netmusicbetterlogin.config.GeneralConfig;
import com.github.tartaricacid.netmusic.api.resolver.MusicPlayResolverManager;
import com.mojang.logging.LogUtils;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(NetMusicBetterLogin.MODID)
public class NetMusicBetterLogin {

    public static final String MODID = "netmusicbetterlogin";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static NeteaseApi NETEASE_API;

    public NetMusicBetterLogin(ModContainer modContainer) {
        NETEASE_API = new NeteaseApi();
        modContainer.registerConfig(ModConfig.Type.COMMON, GeneralConfig.init());
        MusicPlayResolverManager.registerResolver(new NeteaseVIPResolver());
    }
}
