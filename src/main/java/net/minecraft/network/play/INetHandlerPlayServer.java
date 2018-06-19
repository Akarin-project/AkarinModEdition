package net.minecraft.network.play;
import net.minecraft.network.INetHandler;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEnchantItem;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlaceRecipe;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketSeenAdvancements;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;


public interface INetHandlerPlayServer extends INetHandler {

    void func_175087_a(CPacketAnimation packetplayinarmanimation);

    void func_147354_a(CPacketChatMessage packetplayinchat);

    void func_147341_a(CPacketTabComplete packetplayintabcomplete);

    void func_147342_a(CPacketClientStatus packetplayinclientcommand);

    void func_147352_a(CPacketClientSettings packetplayinsettings);

    void func_147339_a(CPacketConfirmTransaction packetplayintransaction);

    void func_147338_a(CPacketEnchantItem packetplayinenchantitem);

    void func_147351_a(CPacketClickWindow packetplayinwindowclick);

    void func_194308_a(CPacketPlaceRecipe packetplayinautorecipe);

    void func_147356_a(CPacketCloseWindow packetplayinclosewindow);

    void func_147349_a(CPacketCustomPayload packetplayincustompayload);

    void func_147340_a(CPacketUseEntity packetplayinuseentity);

    void func_147353_a(CPacketKeepAlive packetplayinkeepalive);

    void func_147347_a(CPacketPlayer packetplayinflying);

    void func_147348_a(CPacketPlayerAbilities packetplayinabilities);

    void func_147345_a(CPacketPlayerDigging packetplayinblockdig);

    void func_147357_a(CPacketEntityAction packetplayinentityaction);

    void func_147358_a(CPacketInput packetplayinsteervehicle);

    void func_147355_a(CPacketHeldItemChange packetplayinhelditemslot);

    void func_147344_a(CPacketCreativeInventoryAction packetplayinsetcreativeslot);

    void func_147343_a(CPacketUpdateSign packetplayinupdatesign);

    void func_184337_a(CPacketPlayerTryUseItemOnBlock packetplayinuseitem);

    void func_147346_a(CPacketPlayerTryUseItem packetplayinblockplace);

    void func_175088_a(CPacketSpectate packetplayinspectate);

    void func_175086_a(CPacketResourcePackStatus packetplayinresourcepackstatus);

    void func_184340_a(CPacketSteerBoat packetplayinboatmove);

    void func_184338_a(CPacketVehicleMove packetplayinvehiclemove);

    void func_184339_a(CPacketConfirmTeleport packetplayinteleportaccept);

    void func_191984_a(CPacketRecipeInfo packetplayinrecipedisplayed);

    void func_194027_a(CPacketSeenAdvancements packetplayinadvancements);
}
