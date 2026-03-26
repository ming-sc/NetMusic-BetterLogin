package com.github.img.netmusicbetterlogin.network.message;

import com.github.tartaricacid.netmusic.NetMusic;
import com.github.tartaricacid.netmusic.api.lyric.LyricParser;
import com.github.tartaricacid.netmusic.api.lyric.LyricRecord;
import com.github.tartaricacid.netmusic.client.audio.MusicPlayManager;
import com.github.tartaricacid.netmusic.client.audio.NetMusicSound;
import com.github.tartaricacid.netmusic.config.GeneralConfig;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RawMusicToClientMessage {
    private static final Pattern PATTERN = Pattern.compile("^.*?\\?id=(\\d+)\\.mp3$");
    private final BlockPos pos;
    private final String url;
    private final int timeSecond;
    private final String songName;
    private final String rawUrl;

    public RawMusicToClientMessage(BlockPos pos, String url, int timeSecond, String songName, String rawUrl) {
        this.pos = pos;
        this.url = url;
        this.timeSecond = timeSecond;
        this.songName = songName;
        this.rawUrl = rawUrl;
    }

    public static RawMusicToClientMessage decode(FriendlyByteBuf buf) {
        return new RawMusicToClientMessage(BlockPos.of(buf.readLong()), buf.readUtf(), buf.readInt(), buf.readUtf(), buf.readUtf());
    }

    public static void encode(RawMusicToClientMessage message, FriendlyByteBuf buf) {
        buf.writeLong(message.pos.asLong());
        buf.writeUtf(message.url);
        buf.writeInt(message.timeSecond);
        buf.writeUtf(message.songName);
        buf.writeUtf(message.rawUrl);
    }

    public int getTimeSecond() {
        return timeSecond;
    }

    public static void handle(RawMusicToClientMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> CompletableFuture.runAsync(() -> onHandle(message), Util.backgroundExecutor()));
        }
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void onHandle(RawMusicToClientMessage message) {
        LyricRecord[] record = new LyricRecord[1];
        if (GeneralConfig.ENABLE_PLAYER_LYRICS.get() && message.url.startsWith("https://music.163.com/")) {
            Matcher matcher = PATTERN.matcher(message.url);
            if (matcher.find()) {
                long musicId = Long.parseLong(matcher.group(1));

                try {
                    String lyric = NetMusic.NET_EASE_WEB_API.lyric(musicId);
                    record[0] = LyricParser.parseLyric(lyric, message.songName);
                } catch (IOException var6) {
                    NetMusic.LOGGER.error(var6);
                }
            }
        }

        // 使用 rawUrl 播放
        MusicPlayManager.play(message.rawUrl, message.songName, (url) -> new NetMusicSound(message.pos, url, message.timeSecond, record[0]));
    }
}
