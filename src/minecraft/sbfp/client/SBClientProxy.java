package sbfp.client;

import sbfp.SBCommonProxy;
import sbfp.secret.EntitySecret;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLLog;

public class SBClientProxy extends SBCommonProxy{

	@Override
	public void init(){
		FMLLog.fine("SBFP Client Proxy Loading");
		RenderingRegistry.registerEntityRenderingHandler(EntitySecret.class,new RenderSecret());
	}
}
