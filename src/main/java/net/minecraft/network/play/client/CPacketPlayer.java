package net.minecraft.network.play.client;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class CPacketPlayer implements Packet<INetHandlerPlayServer> {

    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;
    protected boolean onGround;
    public boolean moving;
    public boolean rotating;

    public CPacketPlayer() {}

    @Override
    public void processPacket(INetHandlerPlayServer packetlistenerplayin) {
        packetlistenerplayin.processPlayer(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetdataserializer) throws IOException {
        this.onGround = packetdataserializer.readUnsignedByte() != 0;
    }

    @Override
    public void writePacketData(PacketBuffer packetdataserializer) throws IOException {
        packetdataserializer.writeByte(this.onGround ? 1 : 0);
    }

    public double getX(double d0) {
        return this.moving ? this.x : d0;
    }

    public double getY(double d0) {
        return this.moving ? this.y : d0;
    }

    public double getZ(double d0) {
        return this.moving ? this.z : d0;
    }

    public float getYaw(float f) {
        return this.rotating ? this.yaw : f;
    }

    public float getPitch(float f) {
        return this.rotating ? this.pitch : f;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public static class Rotation extends CPacketPlayer {

        public Rotation() {
            this.rotating = true;
        }

        @Override
        public void readPacketData(PacketBuffer packetdataserializer) throws IOException {
            this.yaw = packetdataserializer.readFloat();
            this.pitch = packetdataserializer.readFloat();
            super.readPacketData(packetdataserializer);
        }

        @Override
        public void writePacketData(PacketBuffer packetdataserializer) throws IOException {
            packetdataserializer.writeFloat(this.yaw);
            packetdataserializer.writeFloat(this.pitch);
            super.writePacketData(packetdataserializer);
        }
    }

    public static class Position extends CPacketPlayer {

        public Position() {
            this.moving = true;
        }

        @Override
        public void readPacketData(PacketBuffer packetdataserializer) throws IOException {
            this.x = packetdataserializer.readDouble();
            this.y = packetdataserializer.readDouble();
            this.z = packetdataserializer.readDouble();
            super.readPacketData(packetdataserializer);
        }

        @Override
        public void writePacketData(PacketBuffer packetdataserializer) throws IOException {
            packetdataserializer.writeDouble(this.x);
            packetdataserializer.writeDouble(this.y);
            packetdataserializer.writeDouble(this.z);
            super.writePacketData(packetdataserializer);
        }
    }

    public static class PositionRotation extends CPacketPlayer {

        public PositionRotation() {
            this.moving = true;
            this.rotating = true;
        }

        @Override
        public void readPacketData(PacketBuffer packetdataserializer) throws IOException {
            this.x = packetdataserializer.readDouble();
            this.y = packetdataserializer.readDouble();
            this.z = packetdataserializer.readDouble();
            this.yaw = packetdataserializer.readFloat();
            this.pitch = packetdataserializer.readFloat();
            super.readPacketData(packetdataserializer);
        }

        @Override
        public void writePacketData(PacketBuffer packetdataserializer) throws IOException {
            packetdataserializer.writeDouble(this.x);
            packetdataserializer.writeDouble(this.y);
            packetdataserializer.writeDouble(this.z);
            packetdataserializer.writeFloat(this.yaw);
            packetdataserializer.writeFloat(this.pitch);
            super.writePacketData(packetdataserializer);
        }
    }
}
