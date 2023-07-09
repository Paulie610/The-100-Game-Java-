/*Paul Akalski
 * 5/12/2023
 * This project is a version of Nim called the 100 game that is played over a network.
 * This Java file is the server.
 */
package application;

import java.io.*;
import java.net.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class FServer extends Application{
	//create a text area to display contents
	private TextArea txtArea = new TextArea();
	
	//override the start method
	@Override
	public void start(Stage stage) throws Exception {
		//set stage properties
		stage.setWidth(500);
		stage.setHeight(600);
		stage.setResizable(false);
		stage.setTitle("100 game server");
		
		//create scene and add it to stage
		Scene scene = new Scene(new ScrollPane(txtArea));
		stage.setScene(scene);
		
		//show the stage
		stage.show();
		
		new Thread(()->{
			try {
				//create a server socket
				ServerSocket serverSocket = new ServerSocket(7000);
				txtArea.appendText("Server started \n");
				
				//number a session
				int sessionNo = 1;
				
				//create a session for every 2 clients
				while(true) {
					//show session number in text area
					txtArea.appendText("Session "+sessionNo+" ready and awaiting player connection\n");
					
					//Connect to P1
					Socket p1 = serverSocket.accept();
					txtArea.appendText("Player 1 connected to session "+sessionNo+'\n');
					
					//notify player1
					new DataOutputStream(p1.getOutputStream()).writeInt(1);
					
					//Connect to P2
					Socket p2 = serverSocket.accept();
					txtArea.appendText("Player 2 connected to session "+sessionNo+'\n');
					
					//notify p2
					new DataOutputStream(p2.getOutputStream()).writeInt(2);
					
					//incriment sessionNo
					sessionNo++;
					
					//Create and start a new thread
					Session task = new Session(p1, p2);
					new Thread(task).start();
				}
			}catch(IOException ex) {
				System.err.println(ex);
			}
		}).start();
	}
	
	//main method to enable use in IDE with limited support for JavaFX
	public static void main(String[] args) {
		launch(args);
	}
}

//Define a class for handling a session
class Session implements Runnable{
	//create sockets
	private Socket p1;
	private Socket p2;
	
	//create sum variable
	private int sum = 0;
	
	//create data streams
	private DataInputStream fromP1;
	private DataOutputStream toP1;
	private DataInputStream fromP2;
	private DataOutputStream toP2;
	
	//constructor
	public Session(Socket p1, Socket p2) {
		this.p1=p1;
		this.p2=p2;
	}

	//override run method
	@Override
	public void run() {
		try {
			//create data streams
			fromP1 = new DataInputStream(p1.getInputStream());
			toP1 = new DataOutputStream(p1.getOutputStream());
			fromP2 = new DataInputStream(p2.getInputStream());
			toP2 = new DataOutputStream(p2.getOutputStream());
			
			//run the game
			while(true) {
				//check if P2 wins
				if(sum >= 100) {
					toP2.writeInt(1);
					toP2.flush();
					toP1.writeInt(-1);
					toP1.flush();
					break;
				}
				
				//notify both players that the game is still ongoing
				toP1.writeInt(0);
				toP1.flush();
				toP2.writeInt(0);
				toP2.flush();
				
				//receive the sum from P1 and send it to P2
				sum = fromP1.readInt();
				toP2.writeInt(sum);
				toP2.flush();
				
				//check if P1 wins
				if(sum >= 100) {
					toP1.writeInt(1);
					toP1.flush();
					toP2.writeInt(-1);
					toP2.flush();
					break;
				}
				
				//notify both players that the game is still ongoing
				toP1.writeInt(0);
				toP1.flush();
				toP2.writeInt(0);
				toP2.flush();
				
				//receive sum from P2
				sum = fromP2.readInt();
				toP1.writeInt(sum);
				toP1.flush();
			}
			
		}catch(IOException ex) {
			System.err.println(ex);
		}
	}
}