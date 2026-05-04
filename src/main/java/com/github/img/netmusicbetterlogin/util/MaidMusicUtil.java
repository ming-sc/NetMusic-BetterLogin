package com.github.img.netmusicbetterlogin.util;

import com.github.tartaricacid.netmusic.compat.tlm.message.MaidMusicToClientMessage;
import com.github.tartaricacid.netmusic.item.ItemMusicCD;
import com.github.tartaricacid.netmusic.network.NetworkHandler;
import com.github.tartaricacid.touhoulittlemaid.entity.passive.EntityMaid;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MaidMusicUtil {
    public static CompletableFuture<MaidMusicToClientMessage> playMusic(EntityMaid maid, ItemMusicCD.SongInfo songInfo) {
        return MusicMessageUtil.buildMusicMessage(songInfo, rawSongInfo -> new MaidMusicToClientMessage(maid.getId(), rawSongInfo.songUrl, rawSongInfo.songTime, songInfo.songName))
                .thenApplyAsync((msg) -> {
                    MaidMusicToClientMessage.showLyric(maid, songInfo.songUrl, songInfo.songName, songInfo.songTime);
                    NetworkHandler.sendToNearby(maid.level(), maid.blockPosition(), msg);
                    return msg;
                }, Optional.ofNullable((Executor) maid.getServer()).orElse(runnable -> {}));
    }
}