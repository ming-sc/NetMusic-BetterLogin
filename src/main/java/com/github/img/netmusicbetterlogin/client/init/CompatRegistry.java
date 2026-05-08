package com.github.img.netmusicbetterlogin.client.init;

import com.github.img.netmusicbetterlogin.NetMusicBetterLogin;
import com.github.img.netmusicbetterlogin.client.compat.cloth.MenuIntegration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public class CompatRegistry {
    public static final String CLOTH_CONFIG = "cloth_config";

    @Mod.EventBusSubscriber(modid = NetMusicBetterLogin.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class clothCompat {
        @SubscribeEvent
        public static void onEnqueue(final InterModEnqueueEvent event) {
            // 下面这个 lambda 别化简成方法引用, 会导致 MenuIntegration 类加载, 进而导致 ClothConfig 加载
            event.enqueueWork(() -> checkModLoad(CLOTH_CONFIG, () -> MenuIntegration.registerModsPage()));
        }
    }

    private static void checkModLoad(String modId, Runnable runnable) {
        if (ModList.get().isLoaded(modId)) {
            runnable.run();
        }
    }
}
