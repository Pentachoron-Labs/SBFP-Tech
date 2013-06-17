package sbfp.machines.crusher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;

import sbfp.machines.TileEntityProcessor;


public class TileEntityCrusher extends TileEntityProcessor{

	@Override
	public void handleData(INetworkManager network, int packetTypeID, Packet250CustomPayload packet, EntityPlayer entityPlayer, ByteArrayDataInput data){
		// TODO Auto-generated method stub
		
	}

}
