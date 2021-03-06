package hu.bme.mit.cjwc0f.labor5.enterdata;

import hu.bme.mit.cjwc0f.labor5.akka.SenderActor;
import hu.bme.mit.cjwc0f.labor5.akka.SenderActorCreator;
import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.names.IAkkaNames;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.ConfigFactory;

public class EnterMain {
	
	public static void main(String[] args) {
		
		// parameters: SocialInspection process host machine IP, DetermineAverage process host machine IP
		EnterDataParams params = new EnterDataParams();
		
		CmdLineParser parser = new CmdLineParser(params);
		
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			Logger.getGlobal().log(Level.SEVERE, "Could not parse arguments: " + e.getMessage());
			System.exit(1);
		}
		
		String socialHost = params.getSocialHost();
		String detAverageHost = params.getDetAverageHost();
		
		String config = "akka {\r\n" + 
		 		"\r\n" + 
		 		"  actor {\r\n" + 
		 		"    provider = \"akka.remote.RemoteActorRefProvider\"\r\n" + 
		 		"  }\r\n" + 
		 		"\r\n" + 
		 		"  remote {\r\n" + 
		 		"    netty.tcp {\r\n" + 
		 		"      hostname = \"127.0.0.1\"\r\n" + 
		 		"      port = 2551\r\n" + 
		 		"    }\r\n" + 
		 		"  }\r\n" + 
		 		"\r\n" + 
		 		"}";
		
		final ActorSystem system = ActorSystem.create(IAkkaNames.ENTERDATA_SYSTEM,
		        ConfigFactory.parseString(config));
		
		Queue<ApplicationData> socialQueue = new LinkedList<ApplicationData>();
		Queue<ApplicationData> detaverageQueue = new LinkedList<ApplicationData>();
		
		final SenderActorCreator creator = new SenderActorCreator(socialHost, IAkkaNames.SOCIAL_ACTOR, IAkkaNames.SOCIAL_INSPECTION_PORT, IAkkaNames.SOCIAL_SYSTEM, socialQueue);
		final SenderActorCreator creator2 = new SenderActorCreator(detAverageHost, IAkkaNames.DETAVERAGE_ACTOR, IAkkaNames.DET_AVERAGE_PORT, IAkkaNames.DETAVERAGE_SYSTEM, detaverageQueue);
		
		system.actorOf(Props.create(creator), "enteractor1");
		system.actorOf(Props.create(creator2), "enteractor2");
		
		EnterDataWindow enterDataWindow = new EnterDataWindow(socialQueue,detaverageQueue);
		enterDataWindow.pack();
		enterDataWindow.setVisible(true);
		
		Runnable r1 = new Runnable() {
			
			@Override
			public void run() {
				SenderActor actor = creator.getActor();
				try {
					actor.send();
				} catch (InterruptedException e) {
					Logger.getGlobal().log(Level.WARNING, e.getMessage());
				}
			}
		};

		Thread t1 = new Thread(r1);
		t1.setDaemon(true);
		
		Runnable r2 = new Runnable() {
			
			@Override
			public void run() {
				SenderActor actor = creator2.getActor();
				try {
					actor.send();
				} catch (InterruptedException e) {
					Logger.getGlobal().log(Level.WARNING, e.getMessage());
				}
			}
		};
		
		Thread t2 = new Thread(r2);
		t2.setDaemon(true);
		
		t1.start();
		t2.start();
		
	}

}
