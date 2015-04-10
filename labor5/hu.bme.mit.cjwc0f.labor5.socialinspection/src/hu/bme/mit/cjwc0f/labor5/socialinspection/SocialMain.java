package hu.bme.mit.cjwc0f.labor5.socialinspection;

import hu.bme.mit.cjwc0f.labor5.akka.ReceiverActorCreator;
import hu.bme.mit.cjwc0f.labor5.akka.SenderActor;
import hu.bme.mit.cjwc0f.labor5.akka.SenderActorCreator;
import hu.bme.mit.cjwc0f.labor5.data.SocialResult;
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

public class SocialMain {

	public static void main(String[] args) {
		
		// parameters: own host, FinalResult process host
		SocialParams params = new SocialParams();
		
		CmdLineParser parser = new CmdLineParser(params);
		
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			Logger.getGlobal().log(Level.SEVERE, "Could not parse arguments: " + e.getMessage());
			System.exit(1);
		}
		
		String bindAddress = params.getBindAddress();
		String finalResultHost = params.getFinalResultHost();
		
		Queue<Serializable> inQueue = new LinkedList<Serializable>();
		Queue<SocialResult> outQueue = new LinkedList<SocialResult>();
		
		String config = "akka {\r\n" + 
		 		"\r\n" + 
		 		"  actor {\r\n" + 
		 		"    provider = \"akka.remote.RemoteActorRefProvider\"\r\n" + 
		 		"  }\r\n" + 
		 		"\r\n" + 
		 		"  remote {\r\n" + 
		 		"    netty.tcp {\r\n" + 
		 		"      hostname = \"" + bindAddress +"\"\r\n" + 
		 		"      port = "+ IAkkaNames.SOCIAL_INSPECTION_PORT +"\r\n" + 
		 		"    }\r\n" + 
		 		"  }\r\n" + 
		 		"\r\n" + 
		 		"}";
		
		final ActorSystem system = ActorSystem.create(IAkkaNames.SOCIAL_SYSTEM,
		        ConfigFactory.parseString(config));
		
		final ReceiverActorCreator receiverCreator = new ReceiverActorCreator(inQueue);
		final SenderActorCreator creator = new SenderActorCreator(finalResultHost, IAkkaNames.FINAL_SOCIAL_ACTOR, IAkkaNames.FINAL_RESULT_PORT, IAkkaNames.FINAL_RESULT_SYSTEM, outQueue);
		
		system.actorOf(Props.create(creator), "sender");
		system.actorOf(Props.create(receiverCreator), IAkkaNames.SOCIAL_ACTOR);
		
		SocialInspectionWindow window = new SocialInspectionWindow(inQueue, outQueue);
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
