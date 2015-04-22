package hu.bme.mit.cjwc0f.labor6.main;

import hu.bme.mit.cjwc0f.events.HostelApplicationAbstractEvent;
import hu.bme.mit.cjwc0f.labor5.drools.EventQueue;
import hu.bme.mit.cjwc0f.labor5.drools.IDroolsListener;

public class DroolsListener implements IDroolsListener {

	public DroolsListener() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void process() {
		// TODO add this to the working memory
		HostelApplicationAbstractEvent event = EventQueue.poll();
		
	}

}
