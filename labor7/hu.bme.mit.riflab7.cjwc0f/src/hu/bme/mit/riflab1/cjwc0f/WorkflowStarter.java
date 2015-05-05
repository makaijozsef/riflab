package hu.bme.mit.riflab1.cjwc0f;

import hu.bme.mit.riflab1.cjwc0f.data.ApplicationData;
import hu.bme.mit.riflab1.cjwc0f.data.SocialResult;
import hu.bme.mit.riflab1.cjwc0f.window.AbstractWindow;
import hu.bme.mit.riflab1.cjwc0f.window.AddCommunityPointsWindow;
import hu.bme.mit.riflab1.cjwc0f.window.AssignRoomNumberWindow;
import hu.bme.mit.riflab1.cjwc0f.window.DetermineAverageWindow;
import hu.bme.mit.riflab1.cjwc0f.window.DetermineFinalResultWindow;
import hu.bme.mit.riflab1.cjwc0f.window.EnterDataWindow;
import hu.bme.mit.riflab1.cjwc0f.window.SocialInspectionWindow;
import hu.bme.mit.riflab1.cjwc0f.workers.AddCommunityPointsWorker;
import hu.bme.mit.riflab1.cjwc0f.workers.AssignRoomNumberWorker;
import hu.bme.mit.riflab1.cjwc0f.workers.DetermineAverageWorker;
import hu.bme.mit.riflab1.cjwc0f.workers.DetermineFinalResultWorker;
import hu.bme.mit.riflab1.cjwc0f.workers.EnterDataWorker;
import hu.bme.mit.riflab1.cjwc0f.workers.SocialInspectionWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class WorkflowStarter {

	public static void main(String[] args) {

		BlockingQueue<ApplicationData> queue1 = new ArrayBlockingQueue<ApplicationData>(1020);
		BlockingQueue<ApplicationData> queue2 = new ArrayBlockingQueue<ApplicationData>(1020);
		BlockingQueue<SocialResult> queue3 = new ArrayBlockingQueue<SocialResult>(1020);
		BlockingQueue<ApplicationData> queue4 = new ArrayBlockingQueue<ApplicationData>(1020);
		BlockingQueue<ApplicationData> queue5 = new ArrayBlockingQueue<ApplicationData>(1020);
		// May not be correctly ordered!
		BlockingQueue<ApplicationData> queue6 = new ArrayBlockingQueue<ApplicationData>(1020);

		// Final results
		BlockingQueue<ApplicationData> queue7 = new ArrayBlockingQueue<ApplicationData>(20);

		List<AbstractWindow> windows = new ArrayList<>();

		EnterDataWorker enterDataWorker = new EnterDataWorker(queue1, queue2);
		EnterDataWindow enterDataWindow = new EnterDataWindow(enterDataWorker);
		windows.add(enterDataWindow);

		SocialInspectionWorker socialInspectionWorker = new SocialInspectionWorker(queue2, queue3);
		SocialInspectionWindow socialInspectionWindow = new SocialInspectionWindow(socialInspectionWorker);
		windows.add(socialInspectionWindow);

		DetermineAverageWorker determineAverageWorker = new DetermineAverageWorker(queue1, queue4);
		DetermineAverageWindow determineAverageWindow = new DetermineAverageWindow(determineAverageWorker);
		windows.add(determineAverageWindow);

		AddCommunityPointsWorker addCommunityPointsWorker = new AddCommunityPointsWorker(queue4, queue5, queue6);
		AddCommunityPointsWindow addCommunityPointsWindow = new AddCommunityPointsWindow(addCommunityPointsWorker);
		windows.add(addCommunityPointsWindow);

		AssignRoomNumberWorker assignRoomWorker = new AssignRoomNumberWorker(queue5, queue6);
		AssignRoomNumberWindow assignRoomWindow = new AssignRoomNumberWindow(assignRoomWorker);
		windows.add(assignRoomWindow);

		DetermineFinalResultWorker finalResultWorker = new DetermineFinalResultWorker(queue6, queue3, queue7);
		DetermineFinalResultWindow finalResultWindow = new DetermineFinalResultWindow(finalResultWorker);
		windows.add(finalResultWindow);

		openAllWindows(windows);
	}

	private static void openAllWindows(List<AbstractWindow> windows) {
		for (AbstractWindow window : windows) {
			window.pack();
			window.setVisible(true);
		}

	}

}
