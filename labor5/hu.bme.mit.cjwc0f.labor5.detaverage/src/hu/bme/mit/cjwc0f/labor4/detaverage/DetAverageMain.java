package hu.bme.mit.cjwc0f.labor4.detaverage;

import hu.bme.mit.cjwc0f.labor5.akka.SenderActor;
import hu.bme.mit.cjwc0f.labor5.akka.SenderActorCreator;
import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.names.IAkkaNames;

import java.util.LinkedList;
import java.util.Queue;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.ConfigFactory;

public class DetAverageMain {

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
		 		"      port = "+ IAkkaNames.DET_AVERAGE_PORT +" \r\n" + 
		 		"    }\r\n" + 
		 		"  }\r\n" + 
		 		"\r\n" + 
		 		"}";
		
		final ActorSystem system = ActorSystem.create(IAkkaNames.DETAVERAGE_SYSTEM, ConfigFactory.parseString(config));
		
		Queue<ApplicationData> addCommPointsQueue = new LinkedList<ApplicationData>();
		
		final SenderActorCreator creator = new SenderActorCreator("localhost", IAkkaNames.ADDCOMMPOINTS_ACTOR, IAkkaNames.ADD_COMM_POINTS_PORT, IAkkaNames.ADDCOMMPOINTS_SYSTEM, addCommPointsQueue);
		
		system.actorOf(Props.create(creator), IAkkaNames.DETAVERAGE_ACTOR);
		
		
		DetermineAverageWindow window = new DetermineAverageWindow();
		window.pack();
		window.setVisible(true);

		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				SenderActor actor = creator.getActor();
				try {
					actor.send();
				} catch (InterruptedException e) {
					
				}
			}
		};
		Thread t1 = new Thread(r1);
		t1.setDaemon(true);
		t1.start();
		
		
	}

}