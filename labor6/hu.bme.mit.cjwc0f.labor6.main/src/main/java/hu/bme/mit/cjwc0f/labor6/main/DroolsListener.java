package hu.bme.mit.cjwc0f.labor6.main;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import hu.bme.mit.cjwc0f.events.HostelApplicationAbstractEvent;
import hu.bme.mit.cjwc0f.labor5.drools.EventQueue;
import hu.bme.mit.cjwc0f.labor5.drools.IDroolsListener;

public class DroolsListener implements IDroolsListener {
	
	private KieServices kie;
    private KieSession kSession;

	public DroolsListener() {
		kie = KieServices.Factory.get();
	    KieContainer kContainer = kie.getKieClasspathContainer();
    	kSession = kContainer.newKieSession("ksession-rules");
	}
	
	@Override
	public void process() {
		HostelApplicationAbstractEvent event = EventQueue.poll();
		kSession.insert(event);
		kSession.fireAllRules();
	}

}
