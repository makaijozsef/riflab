package hu.bme.mit.cjwc0f.labor5.assignroomnumber;

import hu.bme.mit.cjwc0f.labor5.akka.ReceiverActorCreator;
import hu.bme.mit.cjwc0f.labor5.akka.SenderActor;
import hu.bme.mit.cjwc0f.labor5.akka.SenderActorCreator;
import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.names.IAkkaNames;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.ConfigFactory;


public class AssignRoomMain {

	public static void main(String[] args) {
		
		AssignRoomParams params = new AssignRoomParams();
		
		CmdLineParser parser = new CmdLineParser(params);
		
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			Logger.getGlobal().log(Level.SEVERE, "Could not parse arguments: " + e.getMessage());
			System.exit(1);
		}
		
		String bindAddress = params.getBindAddress();
		String finalResultHost = params.getFinalResultHost();
		
		String config = "akka {\r\n" + 
		 		"\r\n" + 
		 		"  actor {\r\n" + 
		 		"    provider = \"akka.remote.RemoteActorRefProvider\"\r\n" + 
		 		"  }\r\n" + 
		 		"\r\n" + 
		 		"  remote {\r\n" + 
		 		"    netty.tcp {\r\n" + 
		 		"      hostname = \"" + bindAddress +"\"\r\n" + 
		 		"      port = "+ IAkkaNames.ASSIGN_ROOM_PORT +" \r\n" + 
		 		"    }\r\n" + 
		 		"  }\r\n" + 
		 		"\r\n" + 
		 		"}";
		
		final ActorSystem system = ActorSystem.create(IAkkaNames.ASSIGNROOM_SYSTEM, ConfigFactory.parseString(config));
		
		Queue<Serializable> inQueue = new LinkedList<Serializable>();
		Queue<ApplicationData> outQueue = new LinkedList<ApplicationData>();
		
		final ReceiverActorCreator receiverCreator = new ReceiverActorCreator(inQueue);
		final SenderActorCreator creator = new SenderActorCreator(finalResultHost, IAkkaNames.FINAL_STUDY_ACTOR, IAkkaNames.FINAL_RESULT_PORT, IAkkaNames.FINAL_RESULT_SYSTEM, outQueue);
		
		system.actorOf(Props.create(creator), "sender");
		system.actorOf(Props.create(receiverCreator), IAkkaNames.ASSIGNROOM_ACTOR);

		AssignRoomNumberWindow assignRoomNumberWindow = new AssignRoomNumberWindow(inQueue, outQueue);
		assignRoomNumberWindow.pack();
		assignRoomNumberWindow.setVisible(true);
		
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
