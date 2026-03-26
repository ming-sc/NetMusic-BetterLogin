package com.github.img.netmusicbetterlogin.mixin;

import com.github.img.netmusicbetterlogin.mixin.accessor.MaidMusicToClientMessageAccessor;
import com.github.img.netmusicbetterlogin.util.MaidMusicUtil;
import com.github.tartaricacid.netmusic.compat.tlm.inventory.MusicPlayerBackpackContainer;
import com.github.tartaricacid.netmusic.item.ItemMusicCD;
import com.github.tartaricacid.touhoulittlemaid.inventory.container.AbstractMaidContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = MusicPlayerBackpackContainer.class, remap = false)
public abstract class MusicPlayerBackpackContainerMixin extends AbstractMaidContainer {
    @Shadow public abstract void setSoundTicks(int ticks);

    public MusicPlayerBackpackContainerMixin(@Nullable MenuType<?> type, int id, Inventory inventory, int entityId) {
        super(type, id, inventory, entityId);
    }

    @Inject(method = "playMusic", at = @At(value = "INVOKE", target = "Lcom/github/tartaricacid/netmusic/compat/tlm/inventory/MusicPlayerBackpackContainer;setSoundTicks(I)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void netmusicbetterlogin$injectPlayMusic(
            CallbackInfoReturnable<Boolean> cir, int slotId,
            CombinedInvWrapper availableInv,
            ItemStack stackInSlot, ItemMusicCD.SongInfo info
    ) {
        MaidMusicUtil.playMusic(this.maid, info).thenAccept(msg -> this.setSoundTicks(((MaidMusicToClientMessageAccessor) msg).getTimeSecond() * 20 + 64));
        cir.setReturnValue(true);
    }
}
