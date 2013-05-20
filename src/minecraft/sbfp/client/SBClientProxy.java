package sbfp.client;

import sbfp.SBCommonProxy;
import sbfp.sbfp;

public class SBClientProxy extends SBCommonProxy{

	@Override
	public void init(){
		sbfp.logger.fine("SBFP Client Proxy Loading");
	}
}
