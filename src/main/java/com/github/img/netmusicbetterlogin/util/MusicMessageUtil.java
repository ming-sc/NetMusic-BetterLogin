package com.github.img.netmusicbetterlogin.util;

import com.github.img.netmusicbetterlogin.NetMusicBetterLogin;
import com.github.img.netmusicbetterlogin.api.pojo.NetEaseMusicPlayInfo;
import com.github.img.netmusicbetterlogin.config.GeneralConfig;
import com.github.tartaricacid.netmusic.item.ItemMusicCD;
import com.google.gson.Gson;
import io.netty.util.internal.StringUtil;
import net.minecraft.Util;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.tartaricacid.netmusic.client.audio.MusicPlayManager.MUSIC_163_URL;

public class MusicMessageUtil {
    private static final Gson GSON = new Gson();
    private static final Pattern PATTERN = Pattern.compile("^.*?\\?id=(\\d+)\\.mp3$");

    public static CompletableFuture<ItemMusicCD.SongInfo> getRawSongInfo(ItemMusicCD.SongInfo songInfo) {
        return CompletableFuture.supplyAsync(() -> {
            String songUrl = songInfo.songUrl;
            if (songUrl.startsWith(MUSIC_163_URL)) {
                Matcher matcher = PATTERN.matcher(songUrl);
                if (matcher.find()) {
                    long musicId = Long.parseLong(matcher.group(1));
                    try {
                        NetEaseMusicPlayInfo playInfo = GSON.fromJson(NetMusicBetterLogin.NETEASE_API.getPlayInfo(musicId, GeneralConfig.LEVEL.get()), NetEaseMusicPlayInfo.class);
                        Optional<NetEaseMusicPlayInfo.PlayInfo> info = playInfo.getPlayInfo();
                        Integer code = info.map(NetEaseMusicPlayInfo.PlayInfo::getCode)
                                .orElseThrow(() -> new RuntimeException(String.format("Failed to get play info for music id: %d, code is null", musicId)));
                        if (code != 200) {
                            NetMusicBetterLogin.LOGGER.error("Failed to get play info for music id: {}, code is {}. Please check vip status and song availability.", musicId, code);
                            return songInfo;
                        }
                        String url = info.map(NetEaseMusicPlayInfo.PlayInfo::getUrl).orElse(StringUtil.EMPTY_STRING);
                        int time = info.map(NetEaseMusicPlayInfo.PlayInfo::getTime).orElse(0) / 1000;
                        return new ItemMusicCD.SongInfo(url, songInfo.songName, time, false);
                    } catch (Exception e) {
                        NetMusicBetterLogin.LOGGER.error("Failed to get play info for music id: {}", musicId, e);
                        throw new RuntimeException(e);
                    }
                }
            }
            return songInfo;
        }, Util.backgroundExecutor());
    }

    public static <T> CompletableFuture<T> buildMusicMessage(ItemMusicCD.SongInfo songInfo, Function<ItemMusicCD.SongInfo, T> messageFactory) {
        return getRawSongInfo(songInfo).thenApply(messageFactory);
    }
}
