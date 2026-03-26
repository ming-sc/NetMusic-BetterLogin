package com.github.img.netmusicbetterlogin.mixin;

import com.github.img.netmusicbetterlogin.util.MusicMessageUtil;
import com.github.tartaricacid.netmusic.compat.sbackpack.NetMusicDiscHandler;
import com.github.tartaricacid.netmusic.compat.sbackpack.PlayNetMusicDiscMessage;
import com.github.tartaricacid.netmusic.item.ItemMusicCD;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.p3pp3rf1y.sophisticatedcore.network.PacketHandler;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.ServerStorageSoundHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.UUID;

@Mixin(value = NetMusicDiscHandler.class, remap = false)
public abstract class NetMusicDiscHandlerMixin {
    @Shadow
    public abstract Optional<ItemMusicCD.SongInfo> getSongInfo(ItemStack itemStack, Level level);

    @Shadow
    protected abstract int getMusicLengthInTicks(ItemMusicCD.SongInfo songInfo);

    @Inject(method = "playDisc(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Ljava/util/UUID;Lnet/minecraft/world/item/ItemStack;Ljava/lang/Runnable;)V",
            at = @At("HEAD"),
            cancellable = true)
    public void netmusicbetterlogin$injectPlayDiscWithBlockPos(
            ServerLevel serverLevel, BlockPos position,
            UUID storageUuid, ItemStack discItemStack,
            Runnable onFinished, CallbackInfo ci
    ) {
        getSongInfo(discItemStack, serverLevel).ifPresent(songInfo -> {
            Vec3 pos = Vec3.atCenterOf(position);
            long finishTime = serverLevel.getGameTime() + getMusicLengthInTicks(songInfo);
            ServerStorageSoundHandler.putSoundInfo(serverLevel, storageUuid, onFinished, pos, finishTime);
            MusicMessageUtil.buildMusicMessage(songInfo, rawSongInfo -> new PlayNetMusicDiscMessage(storageUuid, rawSongInfo, position))
                    .thenAccept(msg -> PacketHandler.INSTANCE.sendToAllNear(serverLevel.dimension(), pos, 128, msg));
        });
        ci.cancel();
    }

    @Inject(method = "playDisc(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;Ljava/util/UUID;Lnet/minecraft/world/item/ItemStack;ILjava/lang/Runnable;)V",
            at = @At("HEAD"),
            cancellable = true)
    public void netmusicbetterlogin$injectPlayDiscWithEntityId(
            ServerLevel serverLevel, Vec3 position,
            UUID storageUuid, ItemStack discItemStack,
            int entityId, Runnable onFinished, CallbackInfo ci
    ) {
        getSongInfo(discItemStack, serverLevel).ifPresent(songInfo -> {
            long finishTime = serverLevel.getGameTime() + getMusicLengthInTicks(songInfo);
            ServerStorageSoundHandler.putSoundInfo(serverLevel, storageUuid, onFinished, position, finishTime);
            MusicMessageUtil.buildMusicMessage(songInfo, rawSongInfo -> new PlayNetMusicDiscMessage(storageUuid, rawSongInfo, entityId))
                    .thenAccept(msg -> PacketHandler.INSTANCE.sendToAllNear(serverLevel.dimension(), position, 128, msg));
        });
        ci.cancel();
    }
}
