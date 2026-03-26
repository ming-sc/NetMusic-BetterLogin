package com.github.img.netmusicbetterlogin.mixin;

import com.github.img.netmusicbetterlogin.util.MaidMusicUtil;
import com.github.tartaricacid.netmusic.api.search.SearchResponse;
import com.github.tartaricacid.netmusic.compat.tlm.ai.PlaySoundFunction;
import com.github.tartaricacid.netmusic.item.ItemMusicCD;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.net.http.HttpResponse;
import java.util.concurrent.CountDownLatch;

@Mixin(value = PlaySoundFunction.class, remap = false)
public abstract class PlaySoundFunctionMixin {
    @Inject(method = "tryToPlayMusic", at = @At(value = "INVOKE", target = "Lcom/github/tartaricacid/netmusic/compat/tlm/message/MaidMusicToClientMessage;<init>(ILjava/lang/String;ILjava/lang/String;)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void netmusicbetterlogin$injectNewMaidMusicMessage(
            EntityMaid maid, HttpResponse<String> response, Throwable throwable,
            String[] toolResponseText, String searchText, CountDownLatch latch,
            CallbackInfo ci, SearchResponse searchResponse, SearchResponse.Song songResult
    ) {
        MaidMusicUtil.playMusic(maid, new ItemMusicCD.SongInfo(songResult.getUrl(), songResult.getName(), songResult.getTimeSecond(), false));
        toolResponseText[0] = "Successfully started playing music: " + songResult.getName();
        ci.cancel();
    }
}
