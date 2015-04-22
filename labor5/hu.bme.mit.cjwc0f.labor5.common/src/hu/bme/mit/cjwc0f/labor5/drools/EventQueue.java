package hu.bme.mit.cjwc0f.labor5.drools;

import hu.bme.mit.cjwc0f.events.HostelApplicationAbstractEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class EventQueue {

	private static ConcurrentLinkedQueue<HostelApplicationAbstractEvent> events;
	
	public static HostelApplicationAbstractEvent poll(){
		return events.poll();
	}
	
	public static void add(HostelApplicationAbstractEvent event){
		events.add(event);
	}
	
}
