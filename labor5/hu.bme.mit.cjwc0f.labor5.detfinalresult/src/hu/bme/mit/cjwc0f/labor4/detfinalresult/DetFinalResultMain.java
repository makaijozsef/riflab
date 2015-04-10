package hu.bme.mit.cjwc0f.labor4.detfinalresult;

import hu.bme.mit.cjwc0f.labor5.akka.ReceiverActorCreator;
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


public class DetFinalResultMain {

	public static void main(String[] args) {
		
		DetFinalParams params = new DetFinalParams();
		
		CmdLineParser parser = new CmdLineParser(params);
		
		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			Logger.getGlobal().log(Level.SEVERE, "Could not parse arguments: " + e.getMessage());
			System.exit(1);
		}
		
		String bindAddress = params.getBindAddress();
		
		String config = "akka {\r\n" + 
		 		"\r\n" + 
		 		"  actor {\r\n" + 
		 		"    provider = \"akka.remote.RemoteActorRefProvider\"\r\n" + 
		 		"  }\r\n" + 
		 		"\r\n" + 
		 		"  remote {\r\n" + 
		 		"    netty.tcp {\r\n" + 
		 		"      hostname = \"" + bindAddress +"\"\r\n" + 
		 		"      port = "+ IAkkaNames.FINAL_RESULT_PORT +" \r\n" + 
		 		"    }\r\n" + 
		 		"  }\r\n" + 
		 		"\r\n" + 
		 		"}";
		
		final ActorSystem system = ActorSystem.create(IAkkaNames.FINAL_RESULT_SYSTEM, ConfigFactory.parseString(config));
		
		Queue<Serializable> inQueueStudy = new LinkedList<Serializable>();
		Queue<Serializable> inQueueSocial = new LinkedList<Serializable>();
		
		final ReceiverActorCreator studyReceiverCreator = new ReceiverActorCreator(inQueueStudy);
		final ReceiverActorCreator socialReceiverCreator = new ReceiverActorCreator(inQueueSocial);
		
		system.actorOf(Props.create(studyReceiverCreator), IAkkaNames.FINAL_STUDY_ACTOR);
		system.actorOf(Props.create(socialReceiverCreator), IAkkaNames.FINAL_SOCIAL_ACTOR);
		
		DetermineFinalResultWindow window = new DetermineFinalResultWindow(inQueueStudy, inQueueSocial);
		window.pack();
		window.setVisible(true);

	}

}
