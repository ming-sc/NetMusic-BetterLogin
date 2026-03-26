package com.github.img.netmusicbetterlogin.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.github.img.netmusicbetterlogin.NetMusicBetterLogin;
import com.github.img.netmusicbetterlogin.api.NeteaseMusicLevel;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.IConfigEvent;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GeneralConfig {
    public static Method setConfigDataMethod;
    public static Method fireEventMethod;
    public static ForgeConfigSpec.ConfigValue<String> COOKIE;
    public static ForgeConfigSpec.EnumValue<NeteaseMusicLevel> LEVEL;

    static {
        Class<ModConfig> modConfigClass = ModConfig.class;
        try {
            setConfigDataMethod = modConfigClass.getDeclaredMethod("setConfigData", CommentedConfig.class);
            fireEventMethod = modConfigClass.getDeclaredMethod("fireEvent", IConfigEvent.class);
            setConfigDataMethod.setAccessible(true);
            fireEventMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            NetMusicBetterLogin.LOGGER.error("Failed to reflect ModConfig methods", e);
        }
    }

    public static ForgeConfigSpec init() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("general");

        builder.comment("Cookie for Netease Music");
        COOKIE = builder.define("Cookie", "");

        builder.comment("Music quality level");
        LEVEL = builder.defineEnum("Level", NeteaseMusicLevel.HIGHER);

        builder.pop();
        return builder.build();
    }

    public static void reload() throws InvocationTargetException, IllegalAccessException {
        ModConfig config = NetMusicBetterLogin.CONFIG;
        if (config != null) {
            CommentedFileConfig configData = config.getHandler().reader(FMLPaths.CONFIGDIR.get()).apply(config);
            setConfigDataMethod.invoke(config, configData);
            fireEventMethod.invoke(config, IConfigEvent.reloading(config));
        }
    }
}
