package hu.bme.mit.cjwc0f.labor4.detaverage;

import org.kohsuke.args4j.Option;

public class DetAverageParams {
	
	@Option(name="--bind-adress", usage="IP address to bind this process", required=true)
    private String bindAddress = "";
	
	@Option(name="--addComm-host", usage="IP address of the machine that runs AddCommunityPoints process", required=true)
    private String addCommHost = "";
	
	public String getBindAddress() {
		return bindAddress;
	}
	
	public String getAddCommHost() {
		return addCommHost;
	}

}
