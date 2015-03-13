package hu.bme.mit.riflab3.cjwc0f.enterdata;

public class EnterMain {

	public static void main(String[] args) {
		if(args.length == 0){
			System.out.println("You have to provide the RabbitMQ host machine.");
			System.exit(0);
		}
		
		String mqHost = args[0];
		
		EnterDataWindow enterDataWindow = new EnterDataWindow(mqHost);
		enterDataWindow.pack();
		enterDataWindow.setVisible(true);
		
	}

}
