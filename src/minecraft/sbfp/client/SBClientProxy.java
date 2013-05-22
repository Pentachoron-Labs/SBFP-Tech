package sbfp.client;

import sbfp.SBCommonProxy;
import sbfp.modsbfp;

public class SBClientProxy extends SBCommonProxy{

	@Override
	public void init(){
		modsbfp.logger.fine("SBFP Client Proxy Loading");
	}
}
