package sbfp.network;

import java.util.EnumMap;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLIndexedMessageToMessageCodec;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import sbfp.modsbfp;
import sbfp.machines.processor.TileEntityProcessor;

public enum PacketHandler {
	
	INSTANCE;
	
	private EnumMap<Side, FMLEmbeddedChannel> channels;
	
	private PacketHandler(){
		this.channels = NetworkRegistry.INSTANCE.newChannel("SBFP", new SBFPCodec());
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
			addClientHandler();
		}
	}
	private void addClientHandler(){
		FMLEmbeddedChannel channel = this.channels.get(Side.CLIENT);
		
		String codec = channel.findChannelHandlerNameForType(SBFPCodec.class);
        channel.pipeline().addAfter(codec, "ClientHandler", new SBFPMessageHandler());
	}
	
	public static class SBFPMessage {
		int x;
		int y;
		int z;
		int size;
		ItemStack[] inventory;
	}
	
	private static class SBFPCodec extends FMLIndexedMessageToMessageCodec<SBFPMessage> {
		public SBFPCodec(){
			addDiscriminator(0, SBFPMessage.class);
		}
		@Override
		public void encodeInto(ChannelHandlerContext ctx, SBFPMessage msg, ByteBuf target) throws Exception {
			target.writeInt(msg.x);
			target.writeInt(msg.y);
			target.writeInt(msg.z);
			target.writeInt(msg.size);
			target.writeBoolean(msg.inventory != null);
			if(msg.inventory != null){
				for(ItemStack i:msg.inventory){
					ByteBufUtils.writeItemStack(target, i);
				}
			}
			
		}

		@Override
		public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, SBFPMessage msg) {
			
			msg.x = source.readInt();
			msg.y = source.readInt();
			msg.z = source.readInt();
			msg.size = source.readInt();
			msg.inventory = new ItemStack[0];
			if(source.readBoolean()){
				msg.inventory = new ItemStack[msg.size-1];
				for(int i = 0; i<msg.inventory.length; i++){
					msg.inventory[i] = ByteBufUtils.readItemStack(source);
				}
			}
		}

	}
	
	private class SBFPMessageHandler extends SimpleChannelInboundHandler<SBFPMessage> {

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, SBFPMessage msg) throws Exception {
			World world = modsbfp.proxy.getClientWorld();
            TileEntity te = world.getTileEntity(new BlockPos(msg.x, msg.y, msg.z));
            if (te instanceof TileEntityProcessor)
            {
                TileEntityProcessor machine = (TileEntityProcessor) te;
                
            }
			
		}

	}

}
