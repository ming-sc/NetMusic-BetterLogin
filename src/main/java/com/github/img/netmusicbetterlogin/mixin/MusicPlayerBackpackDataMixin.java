package com.github.img.netmusicbetterlogin.mixin;

import com.github.img.netmusicbetterlogin.mixin.accessor.MaidMusicToClientMessageAccessor;
import com.github.img.netmusicbetterlogin.util.MaidMusicUtil;
import com.github.tartaricacid.netmusic.compat.tlm.backpack.data.MusicPlayerBackpackData;
import com.github.tartaricacid.netmusic.item.ItemMusicCD;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = MusicPlayerBackpackData.class, remap = false)
public abstract class MusicPlayerBackpackDataMixin {

    @Shadow private int playTick;

    @Redirect(method = "playMusic", at = @At(value = "FIELD", target = "Lcom/github/tartaricacid/netmusic/compat/tlm/backpack/data/MusicPlayerBackpackData;playTick:I", opcode = Opcodes.PUTFIELD))
    public void netmusicbetterlogin$redirectPlayTick(MusicPlayerBackpackData instance, int value) {
        // 取消对 playTick 字段的修改
    }

    @Inject(method = "playMusic", at = @At(value = "INVOKE", target = "Lcom/github/tartaricacid/netmusic/compat/tlm/message/MaidMusicToClientMessage;<init>(ILjava/lang/String;ILjava/lang/String;)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void netmusicbetterlogin$injectPlayMusic(
            EntityMaid entityMaid, CombinedInvWrapper availableInv,
            int slotId, CallbackInfoReturnable<Boolean> cir, ItemStack stackInSlot,
            ItemMusicCD.SongInfo info
    ) {
        MaidMusicUtil.playMusic(entityMaid, info).thenAccept(msg -> {
            this.playTick = ((MaidMusicToClientMessageAccessor) msg).getTimeSecond() * 20 + 64;
        });
        cir.setReturnValue(true);
    }

}
