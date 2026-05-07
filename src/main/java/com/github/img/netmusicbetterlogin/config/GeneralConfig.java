package com.github.img.netmusicbetterlogin.config;

import com.github.img.netmusicbetterlogin.api.NeteaseMusicLevel;
import net.neoforged.neoforge.common.ModConfigSpec;

public class GeneralConfig {
    public static ModConfigSpec.ConfigValue<String> COOKIE;
    public static ModConfigSpec.EnumValue<NeteaseMusicLevel> LEVEL;

    public static ModConfigSpec init() {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("general");

        builder.comment("Cookie for Netease Music");
        COOKIE = builder.define("Cookie", "");

        builder.comment("Music quality level");
        LEVEL = builder.defineEnum("Level", NeteaseMusicLevel.HIGHER);

        builder.pop();
        return builder.build();
    }
}
