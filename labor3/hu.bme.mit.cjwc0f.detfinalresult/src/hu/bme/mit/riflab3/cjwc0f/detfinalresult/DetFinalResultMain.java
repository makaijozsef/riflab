package hu.bme.mit.riflab3.cjwc0f.detfinalresult;


public class DetFinalResultMain {

	public static void main(String[] args) {
		if(args.length == 0){
			System.out.println("You have to provide the RabbitMQ host machine.");
			System.exit(0);
		}
		
		String mqHost = args[0];
		
		DetermineFinalResultWindow window = new DetermineFinalResultWindow(mqHost);
		window.pack();
		window.setVisible(true);

	}

}
