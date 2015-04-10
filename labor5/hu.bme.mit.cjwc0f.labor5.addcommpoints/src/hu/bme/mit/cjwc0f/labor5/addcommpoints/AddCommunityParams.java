package hu.bme.mit.cjwc0f.labor5.addcommpoints;

import org.kohsuke.args4j.Option;

public class AddCommunityParams {
	
	@Option(name="--bind-adress", usage="IP address to bind this process", required=true)
    private String bindAddress = "";
	
	@Option(name="--assignroom-host", usage="IP address of the machine that runs AssignRoomNumber process", required=true)
    private String assignRoomHost = "";
	
	@Option(name="--finalresult-host", usage="IP address of the machine that runs FinalResult process", required=true)
    private String finalResultHost = "";
	
	public String getBindAddress() {
		return bindAddress;
	}
	
	public String getAssignRoomHost() {
		return assignRoomHost;
	}
	
	public String getFinalResultHost() {
		return finalResultHost;
	}

}
