package com.github.img.netmusicbetterlogin;

import com.github.img.netmusicbetterlogin.api.NeteaseApi;
import com.github.img.netmusicbetterlogin.compat.netmusic.NeteaseVIPResolver;
import com.github.img.netmusicbetterlogin.config.GeneralConfig;
import com.github.tartaricacid.netmusic.api.resolver.MusicPlayResolverManager;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(NetMusicBetterLogin.MODID)
public class NetMusicBetterLogin {

    public static final String MODID = "netmusicbetterlogin";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static ModConfig CONFIG;

    public static NeteaseApi NETEASE_API;

    @SuppressWarnings("removal")
    public NetMusicBetterLogin() {
        NETEASE_API = new NeteaseApi();

        ModContainer container = ModLoadingContext.get().getContainer();
        CONFIG = new ModConfig(ModConfig.Type.COMMON, GeneralConfig.init(), container);
        container.addConfig(CONFIG);

        MusicPlayResolverManager.registerResolver(new NeteaseVIPResolver());
    }
}
