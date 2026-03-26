package com.github.img.netmusicbetterlogin.mixin;

import com.github.tartaricacid.netmusic.block.BlockMusicPlayer;
import com.github.tartaricacid.netmusic.item.ItemMusicCD;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BlockMusicPlayer.class)
public abstract class BlockMusicPlayerMixin {

    @Redirect(method = "use", at = @At(value = "FIELD", target = "Lcom/github/tartaricacid/netmusic/item/ItemMusicCD$SongInfo;vip:Z", opcode = Opcodes.GETFIELD, remap = false))
    private boolean netmusicbetterlogin$redirectVip(ItemMusicCD.SongInfo songInfo) {
        // 取消所有 VIP 限制
        return false;
    }

}
