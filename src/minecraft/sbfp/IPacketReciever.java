package sbfp;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;

public interface IPacketReciever{

	public void handleData(INetworkManager network, int packetTypeID, Packet250CustomPayload packet, EntityPlayer entityPlayer, ByteArrayDataInput data);

}
