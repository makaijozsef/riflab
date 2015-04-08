package hu.bme.mit.cjwc0f.labor5.akka;

import java.io.Serializable;
import java.util.Queue;

import akka.japi.Creator;

public class SenderActorCreator implements Creator<SenderActor>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String remoteHost;
	private String actorname;
	private int port;
	private String remoteSystem;
	private Queue<? extends Serializable> queue;
	
	private SenderActor actor;

	public SenderActorCreator(String remoteHost,
			String actorname, int port, String remoteSystem, Queue<? extends Serializable> queue) {
		this.remoteHost = remoteHost;
		this.actorname = actorname;
		this.port = port;
		this.remoteSystem = remoteSystem;
		this.queue = queue;
	}

	@Override
	public SenderActor create() throws Exception {
		actor = new SenderActor(remoteSystem, actorname, remoteHost, port, queue);
		return actor;
	}
	
	public SenderActor getActor(){
		return actor;
	}

}
