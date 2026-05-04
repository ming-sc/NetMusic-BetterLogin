package com.github.img.netmusicbetterlogin.mixin;

import com.github.img.netmusicbetterlogin.network.NetworkHandler;
import com.github.img.netmusicbetterlogin.network.message.RawMusicToClientMessage;
import com.github.img.netmusicbetterlogin.util.MusicMessageUtil;
import com.github.tartaricacid.netmusic.item.ItemMusicCD;
import com.github.tartaricacid.netmusic.tileentity.TileEntityMusicPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.concurrent.Executor;

@Mixin(value = TileEntityMusicPlayer.class, remap = false)
public abstract class TileEntityMusicPlayerMixin extends BlockEntity {
    @Shadow
    public abstract void markDirty();

    @Shadow
    private boolean isPlay;

    @Shadow
    public abstract void setCurrentTime(int time);

    public TileEntityMusicPlayerMixin(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Inject(method = "setPlayToClient", at = @At("HEAD"), cancellable = true)
    public void netmusicbetterlogin$setPlayToClient(ItemMusicCD.SongInfo info, CallbackInfo ci) {
        if (level != null && !level.isClientSide) {
            MusicMessageUtil.buildMusicMessage(info, (rawSongInfo) -> new RawMusicToClientMessage(worldPosition, info.songUrl, rawSongInfo.songTime, info.songName, rawSongInfo.songUrl))
                    .thenAccept(msg -> {
                        if (!this.isPlay) {
                            this.setCurrentTime(msg.getTimeSecond() * 20 + 64);
                            this.isPlay = true;
                            NetworkHandler.sendToNearby(level, worldPosition, msg);
                        }
                    })
                    .thenRunAsync(this::markDirty, Optional.ofNullable(getLevel())
                                    .map(Level::getServer)
                                    .map(server -> (Executor) server)
                                    .orElse(runnable -> {}));
        }
        ci.cancel();
    }
}
