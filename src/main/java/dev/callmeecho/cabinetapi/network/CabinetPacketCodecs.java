package dev.callmeecho.cabinetapi.network;

import com.mojang.datafixers.util.Pair;
import net.minecraft.network.codec.PacketCodec;

public final class CabinetPacketCodecs {
    public static <Buffer, F, S> PacketCodec<Buffer, Pair<F, S>> pair(
            PacketCodec<? super Buffer, F> firstCodec,
            PacketCodec<? super Buffer, S> secondCodec
    ) {
        return PacketCodec.tuple(
                firstCodec,
                Pair::getFirst,
                secondCodec,
                Pair::getSecond,
                Pair::of
        );
    }
}
