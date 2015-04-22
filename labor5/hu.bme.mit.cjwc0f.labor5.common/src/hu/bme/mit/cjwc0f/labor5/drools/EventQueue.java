package hu.bme.mit.cjwc0f.labor5.drools;

import hu.bme.mit.cjwc0f.events.HostelApplicationAbstractEvent;

import java.util.concurrent.ConcurrentLinkedQueue;

public class EventQueue {

	private static ConcurrentLinkedQueue<HostelApplicationAbstractEvent> events;
	
	private static IDroolsListener listener;
	
	public static HostelApplicationAbstractEvent poll(){
		return events.poll();
	}
	
	public static void add(HostelApplicationAbstractEvent event){
		events.add(event);
		listener.process();
	}
	
	public static void setListener(IDroolsListener listener){
		EventQueue.listener = listener;
	}
	
}
