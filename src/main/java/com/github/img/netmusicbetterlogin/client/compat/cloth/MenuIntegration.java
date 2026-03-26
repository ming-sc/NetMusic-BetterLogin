package com.github.img.netmusicbetterlogin.client.compat.cloth;

import com.github.img.netmusicbetterlogin.api.NeteaseMusicLevel;
import com.github.img.netmusicbetterlogin.config.GeneralConfig;
import com.github.img.netmusicbetterlogin.event.CommonEvent;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

public class MenuIntegration {
    @SuppressWarnings("removal")
    public static void registerModsPage() {
        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () ->
                new ConfigScreenHandler.ConfigScreenFactory((client, parent) ->
                        getConfigBuilder().setParentScreen(parent).build()));
    }

    public static ConfigBuilder getConfigBuilder() {
        ConfigBuilder root = ConfigBuilder.create().setTitle(Component.translatable("itemGroup.netmusic"));
        root.setGlobalized(true);
        root.setGlobalizedExpanded(false);
        ConfigEntryBuilder entryBuilder = root.entryBuilder();
        generalConfig(root, entryBuilder);
        root.setSavingRunnable(CommonEvent::reloadCookie);
        return root;
    }

    private static void generalConfig(ConfigBuilder root, ConfigEntryBuilder entryBuilder) {
        ConfigCategory general = root.getOrCreateCategory(Component.translatable("config.netmusicbetterlogin.general"));

        general.addEntry(entryBuilder.startTextField(Component.translatable("config.netmusicbetterlogin.general.cookie"), GeneralConfig.COOKIE.get())
                .setTooltip(Component.translatable("config.netmusicbetterlogin.general.cookie.tooltip"))
                .setDefaultValue(GeneralConfig.COOKIE.getDefault())
                .setSaveConsumer(GeneralConfig.COOKIE::set)
                .build());

        general.addEntry(entryBuilder.startEnumSelector(Component.translatable("config.netmusicbetterlogin.general.level"), NeteaseMusicLevel.class, GeneralConfig.LEVEL.get())
                .setEnumNameProvider(level -> Component.translatable(((NeteaseMusicLevel)level).getTranslationKey()))
                .setTooltip(Component.translatable("config.netmusicbetterlogin.general.level.tooltip"))
                .setDefaultValue(GeneralConfig.LEVEL.getDefault())
                .setSaveConsumer(GeneralConfig.LEVEL::set)
                .build());
    }
}
