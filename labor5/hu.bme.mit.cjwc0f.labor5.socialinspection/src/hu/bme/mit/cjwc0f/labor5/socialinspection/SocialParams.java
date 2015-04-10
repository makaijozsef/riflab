package hu.bme.mit.cjwc0f.labor5.socialinspection;

import org.kohsuke.args4j.Option;

public class SocialParams {

	@Option(name="--bind-adress", usage="IP address to bind this process", required=true)
    private String bindAddress = "";
	
	@Option(name="--finalresult-host", usage="IP address of the machine that runs FinalResult process", required=true)
    private String finalResultHost = "";
	
	public String getBindAddress() {
		return bindAddress;
	}
	
	public String getFinalResultHost() {
		return finalResultHost;
	}
	
}
