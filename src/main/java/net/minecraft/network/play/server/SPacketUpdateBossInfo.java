package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.BossInfo;

public class SPacketUpdateBossInfo implements Packet<INetHandlerPlayClient> {

    private UUID field_186911_a;
    private SPacketUpdateBossInfo.Operation field_186912_b;
    private ITextComponent field_186913_c;
    private float field_186914_d;
    private BossInfo.Color field_186915_e;
    private BossInfo.Overlay field_186916_f;
    private boolean field_186917_g;
    private boolean field_186918_h;
    private boolean field_186919_i;

    public SPacketUpdateBossInfo() {}

    public SPacketUpdateBossInfo(SPacketUpdateBossInfo.Operation packetplayoutboss_action, BossInfo bossbattle) {
        this.field_186912_b = packetplayoutboss_action;
        this.field_186911_a = bossbattle.func_186737_d();
        this.field_186913_c = bossbattle.func_186744_e();
        this.field_186914_d = bossbattle.func_186738_f();
        this.field_186915_e = bossbattle.func_186736_g();
        this.field_186916_f = bossbattle.func_186740_h();
        this.field_186917_g = bossbattle.func_186734_i();
        this.field_186918_h = bossbattle.func_186747_j();
        this.field_186919_i = bossbattle.func_186748_k();
    }

    public void func_148837_a(PacketBuffer packetdataserializer) throws IOException {
        this.field_186911_a = packetdataserializer.func_179253_g();
        this.field_186912_b = (SPacketUpdateBossInfo.Operation) packetdataserializer.func_179257_a(SPacketUpdateBossInfo.Operation.class);
        switch (this.field_186912_b) {
        case ADD:
            this.field_186913_c = packetdataserializer.func_179258_d();
            this.field_186914_d = packetdataserializer.readFloat();
            this.field_186915_e = (BossInfo.Color) packetdataserializer.func_179257_a(BossInfo.Color.class);
            this.field_186916_f = (BossInfo.Overlay) packetdataserializer.func_179257_a(BossInfo.Overlay.class);
            this.func_186903_a(packetdataserializer.readUnsignedByte());

        case REMOVE:
        default:
            break;

        case UPDATE_PCT:
            this.field_186914_d = packetdataserializer.readFloat();
            break;

        case UPDATE_NAME:
            this.field_186913_c = packetdataserializer.func_179258_d();
            break;

        case UPDATE_STYLE:
            this.field_186915_e = (BossInfo.Color) packetdataserializer.func_179257_a(BossInfo.Color.class);
            this.field_186916_f = (BossInfo.Overlay) packetdataserializer.func_179257_a(BossInfo.Overlay.class);
            break;

        case UPDATE_PROPERTIES:
            this.func_186903_a(packetdataserializer.readUnsignedByte());
        }

    }

    private void func_186903_a(int i) {
        this.field_186917_g = (i & 1) > 0;
        this.field_186918_h = (i & 2) > 0;
        this.field_186919_i = (i & 2) > 0;
    }

    public void func_148840_b(PacketBuffer packetdataserializer) throws IOException {
        packetdataserializer.func_179252_a(this.field_186911_a);
        packetdataserializer.func_179249_a((Enum) this.field_186912_b);
        switch (this.field_186912_b) {
        case ADD:
            packetdataserializer.func_179256_a(this.field_186913_c);
            packetdataserializer.writeFloat(this.field_186914_d);
            packetdataserializer.func_179249_a((Enum) this.field_186915_e);
            packetdataserializer.func_179249_a((Enum) this.field_186916_f);
            packetdataserializer.writeByte(this.func_186905_j());

        case REMOVE:
        default:
            break;

        case UPDATE_PCT:
            packetdataserializer.writeFloat(this.field_186914_d);
            break;

        case UPDATE_NAME:
            packetdataserializer.func_179256_a(this.field_186913_c);
            break;

        case UPDATE_STYLE:
            packetdataserializer.func_179249_a((Enum) this.field_186915_e);
            packetdataserializer.func_179249_a((Enum) this.field_186916_f);
            break;

        case UPDATE_PROPERTIES:
            packetdataserializer.writeByte(this.func_186905_j());
        }

    }

    private int func_186905_j() {
        int i = 0;

        if (this.field_186917_g) {
            i |= 1;
        }

        if (this.field_186918_h) {
            i |= 2;
        }

        if (this.field_186919_i) {
            i |= 2;
        }

        return i;
    }

    public void func_148833_a(INetHandlerPlayClient packetlistenerplayout) {
        packetlistenerplayout.func_184325_a(this);
    }

    public static enum Operation {

        ADD, REMOVE, UPDATE_PCT, UPDATE_NAME, UPDATE_STYLE, UPDATE_PROPERTIES;

        private Operation() {}
    }
}
