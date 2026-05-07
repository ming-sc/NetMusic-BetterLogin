package com.github.img.netmusicbetterlogin.client.init;

import com.github.img.netmusicbetterlogin.NetMusicBetterLogin;
import com.github.img.netmusicbetterlogin.client.compat.cloth.MenuIntegration;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;

public class CompatRegistry {
    public static final String CLOTH_CONFIG = "cloth_config";

    @EventBusSubscriber(modid = NetMusicBetterLogin.MODID, value = Dist.CLIENT)
    public static class clothCompat {
        @SubscribeEvent
        public static void onEnqueue(final InterModEnqueueEvent event) {
            event.enqueueWork(() -> checkModLoad(CLOTH_CONFIG, MenuIntegration::registerModsPage));
        }
    }

    private static void checkModLoad(String modId, Runnable runnable) {
        if (ModList.get().isLoaded(modId)) {
            runnable.run();
        }
    }
}
