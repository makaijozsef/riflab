package hu.bme.mit.cjwc0f.labor5.akka;

import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.data.SocialResult;
import hu.bme.mit.cjwc0f.labor5.workflow.Util;

import java.io.Serializable;
import java.util.Queue;

import akka.actor.UntypedActor;

public class ReceiverActor extends UntypedActor {

	private Queue<Serializable> queue;

	public ReceiverActor(Queue<Serializable> queue) {
		this.queue = queue;
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		
		Serializable deserialized = Util.deserialize((byte[])message);
		
		if(deserialized instanceof ApplicationData | deserialized instanceof SocialResult){
			queue.add(deserialized);	
			
		}
		else {
			unhandled(message);
		}
	}
	
}
