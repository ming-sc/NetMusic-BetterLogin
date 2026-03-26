package com.github.img.netmusicbetterlogin.mixin.accessor;

import com.github.tartaricacid.netmusic.compat.tlm.message.MaidMusicToClientMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = MaidMusicToClientMessage.class, remap = false)
public interface MaidMusicToClientMessageAccessor {
    @Accessor("timeSecond")
    int getTimeSecond();
}
