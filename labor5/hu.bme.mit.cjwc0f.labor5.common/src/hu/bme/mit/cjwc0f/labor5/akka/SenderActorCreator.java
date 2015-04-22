package hu.bme.mit.cjwc0f.labor5.akka;

import java.io.Serializable;
import java.util.Queue;

import akka.actor.ActorRef;
import akka.japi.Creator;

public class SenderActorCreator implements Creator<SenderActor>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ActorRef remotePair;
	private Queue<? extends Serializable> queue;
	
	private SenderActor actor;

	public SenderActorCreator(ActorRef remotePair, Queue<? extends Serializable> queue) {
		this.remotePair = remotePair;
		this.queue = queue;
	}

	@Override
	public SenderActor create() throws Exception {
		actor = new SenderActor(remotePair, queue);
		return actor;
	}
	
	public SenderActor getActor(){
		return actor;
	}

}
