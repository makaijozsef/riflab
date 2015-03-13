package hu.bme.mit.riflab3.cjwc0f.addcommpoints;


public class AddCommunityMain {

	public static void main(String[] args) {
		if(args.length == 0){
			System.out.println("You have to provide the RabbitMQ host machine.");
			System.exit(0);
		}
		
		String mqHost = args[0];
		
		AddCommunityPointsWindow addCommunityPointsWindow = new AddCommunityPointsWindow(mqHost);
		addCommunityPointsWindow.pack();
		addCommunityPointsWindow.setVisible(true);
	}

}
