package hu.bme.mit.cjwc0f.labor5.akka;

import hu.bme.mit.cjwc0f.labor5.workflow.Util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Queue;

import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class SenderActor extends UntypedActor {
	private ActorRef remotePair = null;

	private Queue<? extends Serializable> queue;

	public SenderActor(ActorRef remotePair, Queue<? extends Serializable> queue) {
		this.remotePair = remotePair;
		this.queue = queue;
	}

	@Override
	public void onReceive(Object message) throws Exception {
		synchronized (this) {
			if (message instanceof ActorIdentity) {
				remotePair = ((ActorIdentity) message).getRef();
				if (remotePair == null) {
					System.out.println("Remote actor not available");
				} else {
					getContext().watch(remotePair);
				}

			} else {
				System.out.println("Not ready yet");
			}
		}

	}

	public void send() throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (remotePair != null) {
					Serializable message = queue.poll();
					if (message != null) {
						byte[] serializedData = null;
						try {
							serializedData = Util.serialize(message);
						} catch (IOException e) {

						}

						remotePair.tell(serializedData, getSelf());
					}
				}
			}

			Thread.sleep(500);
		}
	}

}
