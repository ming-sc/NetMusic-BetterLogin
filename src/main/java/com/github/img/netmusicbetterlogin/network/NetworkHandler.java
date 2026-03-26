package com.github.img.netmusicbetterlogin.network;

import com.github.img.netmusicbetterlogin.NetMusicBetterLogin;
import com.github.img.netmusicbetterlogin.network.message.RawMusicToClientMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class NetworkHandler {
    private static final String VERSION = "1.0.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(NetMusicBetterLogin.MODID, "network"))
            .networkProtocolVersion(() -> VERSION)
            .clientAcceptedVersions(VERSION::equals)
            .serverAcceptedVersions(VERSION::equals)
            .simpleChannel();

    public static void init() {
        CHANNEL.registerMessage(0, RawMusicToClientMessage.class, RawMusicToClientMessage::encode, RawMusicToClientMessage::decode, RawMusicToClientMessage::handle);
    }

    public static void sendToNearby(Level world, BlockPos pos, Object toSend) {
        CHANNEL.send(PacketDistributor.NEAR.with(() ->
                new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), 96, world.dimension())
        ), toSend);
    }
}
