package net.minecraft.network.play.client;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class CPacketPlayerDigging implements Packet<INetHandlerPlayServer> {

    private BlockPos field_179717_a;
    private EnumFacing field_179716_b;
    private CPacketPlayerDigging.Action field_149508_e;

    public CPacketPlayerDigging() {}

    public void func_148837_a(PacketBuffer packetdataserializer) throws IOException {
        this.field_149508_e = (CPacketPlayerDigging.Action) packetdataserializer.func_179257_a(CPacketPlayerDigging.Action.class);
        this.field_179717_a = packetdataserializer.func_179259_c();
        this.field_179716_b = EnumFacing.func_82600_a(packetdataserializer.readUnsignedByte());
    }

    public void func_148840_b(PacketBuffer packetdataserializer) throws IOException {
        packetdataserializer.func_179249_a((Enum) this.field_149508_e);
        packetdataserializer.func_179255_a(this.field_179717_a);
        packetdataserializer.writeByte(this.field_179716_b.func_176745_a());
    }

    public void func_148833_a(INetHandlerPlayServer packetlistenerplayin) {
        packetlistenerplayin.func_147345_a(this);
    }

    public BlockPos func_179715_a() {
        return this.field_179717_a;
    }

    public EnumFacing func_179714_b() {
        return this.field_179716_b;
    }

    public CPacketPlayerDigging.Action func_180762_c() {
        return this.field_149508_e;
    }

    public static enum Action {

        START_DESTROY_BLOCK, ABORT_DESTROY_BLOCK, STOP_DESTROY_BLOCK, DROP_ALL_ITEMS, DROP_ITEM, RELEASE_USE_ITEM, SWAP_HELD_ITEMS;

        private Action() {}
    }
}
