package hu.bme.mit.riflab3.cjwc0f.assignroomnumber;


public class AssignRoomMain {

	public static void main(String[] args) {
		if(args.length == 0){
			System.out.println("You have to provide the RabbitMQ host machine.");
			System.exit(0);
		}
		
		String mqHost = args[0];
		
		AssignRoomNumberWindow assignRoomNumberWindow = new AssignRoomNumberWindow(mqHost);
		assignRoomNumberWindow.pack();
		assignRoomNumberWindow.setVisible(true);
	}

}
