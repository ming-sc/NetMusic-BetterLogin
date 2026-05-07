package com.github.img.netmusicbetterlogin.api;

public enum NeteaseMusicLevel {
    STANDARD,
    HIGHER,
    EXHIGH,
    LOSSLESS,
    HIRES;

    public String getTranslationKey() {
        return "command.netmusicbetterlogin.level." + this.toString().toLowerCase();
    }
}
