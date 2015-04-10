package hu.bme.mit.cjwc0f.labor5.enterdata;

import org.kohsuke.args4j.Option;

public class EnterDataParams {

	@Option(name="--social-host", usage="IP address of the machine that runs SocialInspection process", required=true)
    private String socialHost = "";
	
	@Option(name="--detaverage-host", usage="IP address of the machine that runs DetermineAverage process", required=true)
    private String detAverageHost = "";
	
	public String getSocialHost() {
		return socialHost;
	}
	
	public String getDetAverageHost() {
		return detAverageHost;
	}
	
}
