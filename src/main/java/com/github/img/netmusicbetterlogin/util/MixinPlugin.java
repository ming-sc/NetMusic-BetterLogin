package com.github.img.netmusicbetterlogin.util;

import net.minecraftforge.fml.loading.FMLLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
    private static final String MIXIN_PACKAGE = "com.github.img.netmusicbetterlogin.mixin.";
    private static final String TLM_MOD_ID = "touhou_little_maid";
    private static final String S_CORE_MOD_ID = "sophisticatedcore";
    private static final Map<String, String> MIXIN_CLASS_TO_MOD_ID = Map.of(
            MIXIN_PACKAGE + "MusicPlayerBackpackContainerMixin", TLM_MOD_ID,
            MIXIN_PACKAGE + "MusicPlayerBackpackDataMixin", TLM_MOD_ID,
            MIXIN_PACKAGE + "accessor.MaidMusicToClientMessageAccessor", TLM_MOD_ID,
            MIXIN_PACKAGE + "NetMusicDiscHandlerMixin", S_CORE_MOD_ID
    );

    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        String modId = MIXIN_CLASS_TO_MOD_ID.get(mixinClassName);
        if (modId != null) {
            return FMLLoader.getLoadingModList().getModFileById(modId) != null;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
