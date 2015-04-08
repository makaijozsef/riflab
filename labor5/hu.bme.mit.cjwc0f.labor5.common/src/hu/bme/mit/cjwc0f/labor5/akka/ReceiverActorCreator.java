package hu.bme.mit.cjwc0f.labor5.akka;

import java.io.Serializable;
import java.util.Queue;

import akka.japi.Creator;

public class ReceiverActorCreator implements Creator<ReceiverActor>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Queue<Serializable> queue;
	
	private ReceiverActor actor;

	public ReceiverActorCreator(Queue<Serializable> queue) {
		this.queue = queue;
	}

	@Override
	public ReceiverActor create() throws Exception {
		actor = new ReceiverActor(queue);
		return actor;
	}
	
	public ReceiverActor getActor(){
		return actor;
	}

}
