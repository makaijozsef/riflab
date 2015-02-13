package hu.bme.mit.riflab1.cjwc0f;

import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;
import hu.bme.mit.riflab1.cjwc0f.window.AddCommunityPointsWindow;
import hu.bme.mit.riflab1.cjwc0f.window.AssignRoomNumberWindow;
import hu.bme.mit.riflab1.cjwc0f.window.DetermineAverageWindow;
import hu.bme.mit.riflab1.cjwc0f.window.EnterDataWindow;
import hu.bme.mit.riflab1.cjwc0f.window.SocialInspectionWindow;
import hu.bme.mit.riflab1.cjwc0f.workers.AddCommunityPointsWorker;
import hu.bme.mit.riflab1.cjwc0f.workers.AssignRoomNumberWorker;
import hu.bme.mit.riflab1.cjwc0f.workers.DetermineAverageWorker;
import hu.bme.mit.riflab1.cjwc0f.workers.EnterDataWorker;
import hu.bme.mit.riflab1.cjwc0f.workers.SocialInspectionWorker;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class WorkflowStarter {
	
	public static void main(String[] args) {
		
		BlockingQueue<ApplicationData> queue1 = new ArrayBlockingQueue<ApplicationData>(20);
		BlockingQueue<ApplicationData> queue2 = new ArrayBlockingQueue<ApplicationData>(20);
		BlockingQueue<SocialResult> queue3 = new ArrayBlockingQueue<SocialResult>(20);
		BlockingQueue<ApplicationData> queue4 = new ArrayBlockingQueue<ApplicationData>(20);
		BlockingQueue<ApplicationData> queue5 = new ArrayBlockingQueue<ApplicationData>(20);
		BlockingQueue<ApplicationData> queue6 = new ArrayBlockingQueue<ApplicationData>(20);
		BlockingQueue<ApplicationData> queue7 = new ArrayBlockingQueue<ApplicationData>(20);

		
		
		EnterDataWorker enterDataWorker = new EnterDataWorker(queue1, queue2);
		EnterDataWindow enterDataWindow = new EnterDataWindow(enterDataWorker);
		enterDataWindow.pack();
		enterDataWindow.setVisible(true);
		
		SocialInspectionWorker socialInspectionWorker = new SocialInspectionWorker(queue2, queue3);
		SocialInspectionWindow socialInspectionWindow = new SocialInspectionWindow(socialInspectionWorker);
		socialInspectionWindow.pack();
		socialInspectionWindow.setVisible(true);

		DetermineAverageWorker determineAverageWorker = new DetermineAverageWorker(queue1, queue4);
		DetermineAverageWindow determineAverageWindow = new DetermineAverageWindow(determineAverageWorker);
		determineAverageWindow.pack();
		determineAverageWindow.setVisible(true);

		AddCommunityPointsWorker addCommunityPointsWorker = new AddCommunityPointsWorker(queue4, queue5, queue6);
		AddCommunityPointsWindow addCommunityPointsWindow = new AddCommunityPointsWindow(addCommunityPointsWorker);
		addCommunityPointsWindow.pack();
		addCommunityPointsWindow.setVisible(true);
		
		AssignRoomNumberWorker assignRoomWorker = new AssignRoomNumberWorker(queue5,queue7);
		AssignRoomNumberWindow assignRoomWindow = new AssignRoomNumberWindow(assignRoomWorker);
		assignRoomWindow.pack();
		assignRoomWindow.setVisible(true);
		
		
	}

}
