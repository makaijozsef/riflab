package hu.bme.mit.cjwc0f.labor4.detfinalresult;

import org.kohsuke.args4j.Option;

public class DetFinalParams {
	
	@Option(name="--bind-adress", usage="IP address to bind this process", required=true)
    private String bindAddress = "";
	
	public String getBindAddress() {
		return bindAddress;
	}

}
