package hu.bme.mit.cjwc0f.labor5.akka;

import static java.util.concurrent.TimeUnit.SECONDS;
import hu.bme.mit.cjwc0f.labor5.workflow.Util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Queue;

import scala.concurrent.duration.Duration;
import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import akka.actor.Identify;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;

public class SenderActor extends UntypedActor {
	private ActorRef remotePair = null;
	private String path;

	private Queue<? extends Serializable> queue;

	public SenderActor(String remoteSystem, String actorName,
			String remoteHost, int port, Queue<? extends Serializable> queue) {
		path = "akka.tcp://" + remoteSystem + "@" + remoteHost + ":" + port
				+ "/user/" + actorName;

		this.queue = queue;

		sendIdentifyRequest();
	}

	private void sendIdentifyRequest() {
		getContext().actorSelection(path).tell(new Identify(path), getSelf());
		getContext()
				.system()
				.scheduler()
				.scheduleOnce(Duration.create(3, SECONDS), getSelf(),
						ReceiveTimeout.getInstance(),
						getContext().dispatcher(), getSelf());
	}

	@Override
	public void onReceive(Object message) throws Exception {
		synchronized (this) {
			if (message instanceof ActorIdentity) {
				remotePair = ((ActorIdentity) message).getRef();
				if (remotePair == null) {
					System.out.println("Remote actor not available: " + path);
				} else {
					getContext().watch(remotePair);
				}

			} else if (message instanceof ReceiveTimeout) {
				sendIdentifyRequest();

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
