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

		BlockingQueue<ApplicationData> queue1 = new ArrayBlockingQueue<ApplicationData>(Util.DATA_COUNT  + 20 + 20);
		BlockingQueue<ApplicationData> queue2 = new ArrayBlockingQueue<ApplicationData>(Util.DATA_COUNT  + 20);
		BlockingQueue<SocialResult> queue3 = new ArrayBlockingQueue<SocialResult>(Util.DATA_COUNT  + 20);
		BlockingQueue<ApplicationData> queue4 = new ArrayBlockingQueue<ApplicationData>(Util.DATA_COUNT  + 20);
		BlockingQueue<ApplicationData> queue5 = new ArrayBlockingQueue<ApplicationData>(Util.DATA_COUNT  + 20);
		// May not be correctly ordered!
		BlockingQueue<ApplicationData> queue6 = new ArrayBlockingQueue<ApplicationData>(Util.DATA_COUNT  + 20);

		// Final results
		BlockingQueue<ApplicationData> queue7 = new ArrayBlockingQueue<ApplicationData>(Util.DATA_COUNT  + 20);

		List<AbstractWindow> windows = new ArrayList<>();

		DetermineFinalResultWorker finalResultWorker = new DetermineFinalResultWorker(queue6, queue3, queue7);
		DetermineFinalResultWindow finalResultWindow = new DetermineFinalResultWindow(finalResultWorker);
		windows.add(finalResultWindow);

		AssignRoomNumberWorker assignRoomWorker = new AssignRoomNumberWorker(queue5, queue6, finalResultWorker);
		AssignRoomNumberWindow assignRoomWindow = new AssignRoomNumberWindow(assignRoomWorker);
		windows.add(assignRoomWindow);

		AddCommunityPointsWorker addCommunityPointsWorker = new AddCommunityPointsWorker(queue4, queue5, queue6,
				assignRoomWorker, finalResultWorker);
		AddCommunityPointsWindow addCommunityPointsWindow = new AddCommunityPointsWindow(addCommunityPointsWorker);
		windows.add(addCommunityPointsWindow);

		DetermineAverageWorker determineAverageWorker = new DetermineAverageWorker(queue1, queue4,
				addCommunityPointsWorker);
		DetermineAverageWindow determineAverageWindow = new DetermineAverageWindow(determineAverageWorker);
		windows.add(determineAverageWindow);

		SocialInspectionWorker socialInspectionWorker = new SocialInspectionWorker(queue2, queue3, finalResultWorker);
		SocialInspectionWindow socialInspectionWindow = new SocialInspectionWindow(socialInspectionWorker);
		windows.add(socialInspectionWindow);

		EnterDataWorker enterDataWorker = new EnterDataWorker(queue1, queue2, socialInspectionWorker,
				determineAverageWorker);
		EnterDataWindow enterDataWindow = new EnterDataWindow(enterDataWorker);
		windows.add(enterDataWindow);

		openAllWindows(windows);
	}

	private static void openAllWindows(List<AbstractWindow> windows) {
		for (AbstractWindow window : windows) {
			window.pack();
			window.setVisible(true);
		}

	}

}
