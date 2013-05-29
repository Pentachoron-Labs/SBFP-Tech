package sbfp.client;

import sbfp.SBCommonProxy;
import cpw.mods.fml.common.FMLLog;

public class SBClientProxy extends SBCommonProxy{

	@Override
	public void init(){
		FMLLog.fine("SBFP Client Proxy Loading");
	}
}
