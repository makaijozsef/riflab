package hu.bme.mit.cjwc0f.labor6.main;

import hu.bme.mit.cjwc0f.labor4.detaverage.DetermineAverageWindow;
import hu.bme.mit.cjwc0f.labor4.detfinalresult.DetermineFinalResultWindow;
import hu.bme.mit.cjwc0f.labor5.addcommpoints.AddCommunityPointsWindow;
import hu.bme.mit.cjwc0f.labor5.akka.ReceiverActorCreator;
import hu.bme.mit.cjwc0f.labor5.akka.SenderActor;
import hu.bme.mit.cjwc0f.labor5.akka.SenderActorCreator;
import hu.bme.mit.cjwc0f.labor5.assignroomnumber.AssignRoomNumberWindow;
import hu.bme.mit.cjwc0f.labor5.data.ApplicationData;
import hu.bme.mit.cjwc0f.labor5.data.SocialResult;
import hu.bme.mit.cjwc0f.labor5.enterdata.EnterDataWindow;
import hu.bme.mit.cjwc0f.labor5.names.IAkkaNames;
import hu.bme.mit.cjwc0f.labor5.socialinspection.SocialInspectionWindow;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.typesafe.config.ConfigFactory;

public class ProcessMain {

	public static void main(String[] args) {

		String config = "akka {\r\n" + 
		 		"\r\n" + 
		 		"  actor {\r\n" + 
		 		"    provider = \"akka.remote.RemoteActorRefProvider\"\r\n" + 
		 		"  }\r\n" +
		 		"}";
		
		final ActorSystem system = ActorSystem.create(IAkkaNames.FINAL_RESULT_SYSTEM, ConfigFactory.parseString(config));
		
		// Create queues
		Queue<ApplicationData> enterToSocialQueue = new LinkedList<ApplicationData>();
		Queue<ApplicationData> enterToDetaverageQueue = new LinkedList<ApplicationData>();
		
		Queue<Serializable> socialFromEnter = new LinkedList<Serializable>();
		Queue<SocialResult> socialToFinal = new LinkedList<SocialResult>();
		
		Queue<Serializable> detaverageFromEnter = new LinkedList<Serializable>();
		Queue<ApplicationData> detaverageToAddcommunity = new LinkedList<ApplicationData>();
		
		Queue<Serializable> addcommunityFromDetaverage = new LinkedList<Serializable>();
		Queue<ApplicationData> addcommunityToAssignroom = new LinkedList<ApplicationData>();
		Queue<ApplicationData> addcommunityToFinal = new LinkedList<ApplicationData>();
		
		Queue<Serializable> assignroomFromAddcommunity = new LinkedList<Serializable>();
		Queue<ApplicationData> assignroomToFinal = new LinkedList<ApplicationData>();
		
		Queue<Serializable> finalFromStudy = new LinkedList<Serializable>();
		Queue<Serializable> finalFromSocial = new LinkedList<Serializable>();

		
		final ReceiverActorCreator finalstudyReceiverCreator = new ReceiverActorCreator(finalFromStudy);
		final ReceiverActorCreator finalsocialReceiverCreator = new ReceiverActorCreator(finalFromSocial);
		ActorRef finalStudyReceiverActor = system.actorOf(Props.create(finalstudyReceiverCreator), IAkkaNames.FINAL_STUDY_ACTOR);
		ActorRef finalSocialReceiverActor = system.actorOf(Props.create(finalsocialReceiverCreator), IAkkaNames.FINAL_SOCIAL_ACTOR);
		
		final ReceiverActorCreator assignroomReceiverCreator = new ReceiverActorCreator(assignroomFromAddcommunity);
		final SenderActorCreator assignroomSenderCreator = new SenderActorCreator(finalStudyReceiverActor, assignroomToFinal);
		system.actorOf(Props.create(assignroomSenderCreator), "assignRoomSender");
		ActorRef assignroomReceiverActor = system.actorOf(Props.create(assignroomReceiverCreator), IAkkaNames.ASSIGNROOM_ACTOR);

		final ReceiverActorCreator addcommpointsReceiverCreator = new ReceiverActorCreator(addcommunityFromDetaverage);
		final SenderActorCreator addcommpointsSenderAssignCreator = new SenderActorCreator(assignroomReceiverActor, addcommunityToAssignroom);
		final SenderActorCreator addcommpointsSenderFinalCreator = new SenderActorCreator(finalStudyReceiverActor, addcommunityToFinal);
		system.actorOf(Props.create(addcommpointsSenderAssignCreator), "addcommToAssignSender");
		system.actorOf(Props.create(addcommpointsSenderFinalCreator), "addcommToFinalSender");
		ActorRef addcommReceiverActor = system.actorOf(Props.create(addcommpointsReceiverCreator), IAkkaNames.ADDCOMMPOINTS_ACTOR);
		
		final ReceiverActorCreator socialReceiverCreator = new ReceiverActorCreator(socialFromEnter);
		final SenderActorCreator socialSenderCreator = new SenderActorCreator(finalSocialReceiverActor, socialToFinal);
		system.actorOf(Props.create(socialSenderCreator), "socialSender");
		ActorRef socialReceiverActor = system.actorOf(Props.create(socialReceiverCreator), IAkkaNames.SOCIAL_ACTOR);
		
		final ReceiverActorCreator detaverageReceiverCreator = new ReceiverActorCreator(detaverageFromEnter);
		final SenderActorCreator detaverageSenderCreator = new SenderActorCreator(addcommReceiverActor, detaverageToAddcommunity);
		system.actorOf(Props.create(detaverageSenderCreator), "detaverageSender");
		ActorRef detaverageReceiverActor = system.actorOf(Props.create(detaverageReceiverCreator), IAkkaNames.DETAVERAGE_ACTOR);
		
		final SenderActorCreator enterDataToStudyCreator = new SenderActorCreator(detaverageReceiverActor, enterToDetaverageQueue);
		system.actorOf(Props.create(enterDataToStudyCreator), "enterDataToStudyCreator");
		final SenderActorCreator enterDataToSocialCreator = new SenderActorCreator(socialReceiverActor, enterToSocialQueue);
		system.actorOf(Props.create(enterDataToSocialCreator), "enterDataToSocialCreator");
		
		EnterDataWindow enterDataWindow = new EnterDataWindow(enterToSocialQueue,enterToDetaverageQueue);
		enterDataWindow.pack();
		enterDataWindow.setVisible(true);
		
		Runnable enterToSocialRunnable = new Runnable() {
			
			@Override
			public void run() {
				SenderActor actor = enterDataToSocialCreator.getActor();
				try {
					actor.send();
				} catch (InterruptedException e) {

				}
			}
		};

		Thread enterToSocialThread = new Thread(enterToSocialRunnable);
		enterToSocialThread.setDaemon(true);
		
		Runnable enterToStudyRunnable = new Runnable() {
			
			@Override
			public void run() {
				SenderActor actor = enterDataToStudyCreator.getActor();
				try {
					actor.send();
				} catch (InterruptedException e) {

				}
			}
		};
		
		Thread enterToStudyThread = new Thread(enterToStudyRunnable);
		enterToStudyThread.setDaemon(true);
		
		enterToSocialThread.start();
		enterToStudyThread.start();

		SocialInspectionWindow window = new SocialInspectionWindow(socialFromEnter, socialToFinal);
		window.pack();
		window.setVisible(true);
		
		Runnable socialRunnable = new Runnable() {

			@Override
			public void run() {
				SenderActor actor = socialSenderCreator.getActor();
				try {
					actor.send();
				} catch (InterruptedException e) {

				}
			}
		};

		Thread socialThread = new Thread(socialRunnable);
		socialThread.setDaemon(true);
		
		socialThread.start();
		
		
		DetermineAverageWindow detaverageWindow = new DetermineAverageWindow(detaverageFromEnter, detaverageToAddcommunity);
		detaverageWindow.pack();
		detaverageWindow.setVisible(true);

		Runnable detaverageRunnable = new Runnable() {
			@Override
			public void run() {
				SenderActor actor = detaverageSenderCreator.getActor();
				try {
					actor.send();
				} catch (InterruptedException e) {
					
				}
			}
		};
		Thread detaverageThread = new Thread(detaverageRunnable);
		detaverageThread.setDaemon(true);
		detaverageThread.start();
		
		
		AddCommunityPointsWindow addCommunityPointsWindow = new AddCommunityPointsWindow(addcommunityFromDetaverage, addcommunityToAssignroom, addcommunityToFinal);
		addCommunityPointsWindow.pack();
		addCommunityPointsWindow.setVisible(true);
		
		Runnable addcommRunnable2 = new Runnable() {
			@Override
			public void run() {
				SenderActor actor = addcommpointsSenderAssignCreator.getActor();
				try {
					actor.send();
				} catch (InterruptedException e) {
					
				}
			}
		};
		Thread addcommThread2 = new Thread(addcommRunnable2);
		addcommThread2.setDaemon(true);
		addcommThread2.start();
		
		Runnable addcommRunnable1 = new Runnable() {
			@Override
			public void run() {
				SenderActor actor = addcommpointsSenderFinalCreator.getActor();
				try {
					actor.send();
				} catch (InterruptedException e) {
					
				}
			}
		};
		Thread addcommThread1 = new Thread(addcommRunnable1);
		addcommThread1.setDaemon(true);
		addcommThread1.start();
		
		
		AssignRoomNumberWindow assignRoomNumberWindow = new AssignRoomNumberWindow(assignroomFromAddcommunity, assignroomToFinal);
		assignRoomNumberWindow.pack();
		assignRoomNumberWindow.setVisible(true);
		
		Runnable assignRoomRunnable = new Runnable() {
			@Override
			public void run() {
				SenderActor actor = assignroomSenderCreator.getActor();
				try {
					actor.send();
				} catch (InterruptedException e) {
					
				}
			}
		};
		Thread assignRoomThread = new Thread(assignRoomRunnable);
		assignRoomThread.setDaemon(true);
		assignRoomThread.start();
		
		DetermineFinalResultWindow detfinalWindow = new DetermineFinalResultWindow(finalFromStudy, finalFromSocial);
		detfinalWindow.pack();
		detfinalWindow.setVisible(true);
		
		
		/********************************/
		// Fill working memory
		
	}
	
}
