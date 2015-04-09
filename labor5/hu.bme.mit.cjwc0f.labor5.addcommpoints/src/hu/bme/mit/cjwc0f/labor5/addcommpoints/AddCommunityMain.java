package hu.bme.mit.cjwc0f.labor5.addcommpoints;

import hu.bme.mit.cjwc0f.labor5.akka.ReceiverActorCreator;
import hu.bme.mit.cjwc0f.labor5.akka.SenderActor;
import hu.bme.mit.cjwc0f.labor5.akka.SenderActorCreator;
import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.names.IAkkaNames;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.ConfigFactory;


public class AddCommunityMain {

	public static void main(String[] args) {
		
		String config = "akka {\r\n" + 
		 		"\r\n" + 
		 		"  actor {\r\n" + 
		 		"    provider = \"akka.remote.RemoteActorRefProvider\"\r\n" + 
		 		"  }\r\n" + 
		 		"\r\n" + 
		 		"  remote {\r\n" + 
		 		"    netty.tcp {\r\n" + 
		 		"      hostname = \"127.0.0.1\"\r\n" + 
		 		"      port = "+ IAkkaNames.ADD_COMM_POINTS_PORT +" \r\n" + 
		 		"    }\r\n" + 
		 		"  }\r\n" + 
		 		"\r\n" + 
		 		"}";
		
		final ActorSystem system = ActorSystem.create(IAkkaNames.ADDCOMMPOINTS_SYSTEM, ConfigFactory.parseString(config));
		
		Queue<Serializable> inQueue = new LinkedList<Serializable>();
		Queue<ApplicationData> outQueueAssign = new LinkedList<ApplicationData>();
		Queue<ApplicationData> outQueueFinal= new LinkedList<ApplicationData>();
		
		final ReceiverActorCreator receiverCreator = new ReceiverActorCreator(inQueue);
		final SenderActorCreator toAssignCreator = new SenderActorCreator("127.0.0.1", IAkkaNames.ASSIGNROOM_ACTOR, IAkkaNames.ASSIGN_ROOM_PORT, IAkkaNames.ASSIGNROOM_SYSTEM, outQueueAssign);
		final SenderActorCreator toFinalCreator = new SenderActorCreator("127.0.0.1", IAkkaNames.FINAL_STUDY_ACTOR, IAkkaNames.FINAL_RESULT_PORT, IAkkaNames.FINAL_RESULT_SYSTEM, outQueueFinal);
		
		system.actorOf(Props.create(toAssignCreator), "sender");
		system.actorOf(Props.create(toFinalCreator), "sender2");
		system.actorOf(Props.create(receiverCreator), IAkkaNames.ADDCOMMPOINTS_ACTOR);
		
		AddCommunityPointsWindow addCommunityPointsWindow = new AddCommunityPointsWindow(inQueue, outQueueAssign, outQueueFinal);
		addCommunityPointsWindow.pack();
		addCommunityPointsWindow.setVisible(true);
		
		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				SenderActor actor = toAssignCreator.getActor();
				try {
					actor.send();
				} catch (InterruptedException e) {
					
				}
			}
		};
		Thread t1 = new Thread(r1);
		t1.setDaemon(true);
		t1.start();
		
		Runnable r2 = new Runnable() {
			@Override
			public void run() {
				SenderActor actor = toFinalCreator.getActor();
				try {
					actor.send();
				} catch (InterruptedException e) {
					
				}
			}
		};
		Thread t2 = new Thread(r2);
		t2.setDaemon(true);
		t2.start();
	}

}
