package hu.bme.mit.riflab3.cjwc0f.detaverage;

public class DetAverageMain {

	public static void main(String[] args) {
		if(args.length == 0){
			System.out.println("You have to provide the RabbitMQ host machine.");
			System.exit(0);
		}
		
		String mqHost = args[0];
		
		DetermineAverageWindow window = new DetermineAverageWindow(mqHost);
		window.pack();
		window.setVisible(true);

	}

}
