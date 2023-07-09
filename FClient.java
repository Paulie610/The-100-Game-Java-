/*Paul Akalski
 * 5/12/2023
 * This project is a version of Nim called the 100 game that is played over a network.
 * This Java file is the client.
 */

package application;

import java.io.*;
import java.net.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;

public class FClient extends Application{
	//create data streams
	private DataInputStream fromServer;
	private DataOutputStream toServer;
	
	//create GUI components
	private Button one = new Button("1");
	private Button two = new Button("2");
	private Button three = new Button("3");
	private Button four = new Button("4");
	private Button five = new Button("5");
	private Button six = new Button("6");
	private Button seven = new Button("7");
	private Button eight = new Button("8");
	private Button nine = new Button("9");
	private Button ten = new Button("10");
	private Text sumDisplay = new Text("0");
	private Text gameText = new Text("");
	private Button showRules = new Button("Rules");
	private Text rulesText = new Text("The sum starts at 0. Both players take turns adding an integer"
			+ " from one to ten to the sum. The player who makes the sum go above 99 wins.");
	
	//create other instance variables
	private boolean myTurn;
	private int myNumber;
	private int sum = 0;
	private int won = 0;
	
	//override the start method
	@Override
	public void start(Stage stage) throws Exception {
		//set stage properties
		stage.setWidth(1600);
		stage.setHeight(200);
		stage.setTitle("100 Game");
		
		//make an HBox and add the buttons to it
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		hbox.getChildren().add(one);
		hbox.getChildren().add(two);
		hbox.getChildren().add(three);
		hbox.getChildren().add(four);
		hbox.getChildren().add(five);
		hbox.getChildren().add(six);
		hbox.getChildren().add(seven);
		hbox.getChildren().add(eight);
		hbox.getChildren().add(nine);
		hbox.getChildren().add(ten);
		
		//modify the text and add it
		gameText.setFont(Font.font("comic sans ms",50));
		sumDisplay.setFont(Font.font("comic sans ms",70));
		sumDisplay.setFill(Color.GREEN);
		hbox.getChildren().add(sumDisplay);
		hbox.getChildren().add(gameText);
		
		//add the rules button
		hbox.getChildren().add(showRules);
		
		//create scene and add it to the stage
		Scene scene = new Scene(hbox);
		stage.setScene(scene);
		
		//show the stage
		stage.show();
		
		//create rules menu
		Stage rules = new Stage();
		
		//make a vbox and add the rules to it
		VBox vbox = new VBox();
		vbox.getChildren().add(rulesText);
		
		//create a scene
		Scene rScene = new Scene(vbox);
		rules.setScene(rScene);
		
		new Thread(()->{
			try {
				//create socket
				Socket socket = new Socket("localhost",7000);
				
				//create an input stream to receive data from server
				fromServer = new DataInputStream(socket.getInputStream());
				
				//create an output stream to send data to server
				toServer = new DataOutputStream(socket.getOutputStream());
				
				//get player number
				myNumber = fromServer.readInt();
				if(myNumber==1) {
					myTurn = true;
					gameText.setText("You are Player 1. You will go first.");
				}else {
					myTurn = false;
					gameText.setText("You are Player 2. Your opponent will go first.");
				}
				
				//run the game
				while(true) {
					//rules menu button functionality
					showRules.setOnAction(e ->{
						//show the rules when the rules button is pressed
						rules.show();
					});
					
					//update the sum
					sumDisplay.setText(sum + "");
					
					//check if either player won
					won = fromServer.readInt();
					if(won == 1) {
						gameText.setText("Game over. You win!");
						break;
					}
					if(won == -1) {
						gameText.setText("Game over. Your opponent wins.");
						break;
					}
					
					//This if statement determines what the client does during the player's turn and the else block is performed during the opponent's turn
					if(myTurn == true) {
						//update text to notify the player that it's their turn
						if(!gameText.getText().equals("You are Player 1. You will go first.")) {
							gameText.setText("It is your turn.");
						}
						
						//Button functionality
						one.setOnAction(e ->{
							try {
								//increase the sum by 1 and notify the server of the new sum
								sum++;
								toServer.writeInt(sum);
								toServer.flush();
								
								//update turn
								myTurn = false;
								
								//update the sum
								sumDisplay.setText(sum + "");
							}catch(IOException ex) {
								System.out.println(ex.toString());
							}
						});
						
						two.setOnAction(e ->{
							try {
								//increase the sum by 2 and notify the server of the new sum
								sum +=2 ;
								toServer.writeInt(sum);
								toServer.flush();
								
								//update turn
								myTurn = false;
								
								//update the sum
								sumDisplay.setText(sum + "");
							}catch(IOException ex) {
								System.out.println(ex.toString());
							}
						});
						
						three.setOnAction(e ->{
							try {
								//increase the sum by 3 and notify the server of the new sum
								sum += 3;
								toServer.writeInt(sum);
								toServer.flush();
								
								//update turn
								myTurn = false;
								
								//update the sum
								sumDisplay.setText(sum + "");
							}catch(IOException ex) {
								System.out.println(ex.toString());
							}
						});
						
						four.setOnAction(e ->{
							try {
								//increase the sum by 4 and notify the server of the new sum
								sum += 4;
								toServer.writeInt(sum);
								toServer.flush();
								
								//update turn
								myTurn = false;
								
								//update the sum
								sumDisplay.setText(sum + "");
							}catch(IOException ex) {
								System.out.println(ex.toString());
							}
						});
						
						five.setOnAction(e ->{
							try {
								//increase the sum by 5 and notify the server of the new sum
								sum += 5;
								toServer.writeInt(sum);
								toServer.flush();
								
								//update turn
								myTurn = false;
								
								//update the sum
								sumDisplay.setText(sum + "");
							}catch(IOException ex) {
								System.out.println(ex.toString());
							}
						});
						
						six.setOnAction(e ->{
							try {
								//increase the sum by 6 and notify the server of the new sum
								sum += 6;
								toServer.writeInt(sum);
								toServer.flush();
								
								//update turn
								myTurn = false;
								
								//update the sum
								sumDisplay.setText(sum + "");
							}catch(IOException ex) {
								System.out.println(ex.toString());
							}
						});
						
						seven.setOnAction(e ->{
							try {
								//increase the sum by 7 and notify the server of the new sum
								sum += 7;
								toServer.writeInt(sum);
								toServer.flush();
								
								//update turn
								myTurn = false;
								
								//update the sum
								sumDisplay.setText(sum + "");
							}catch(IOException ex) {
								System.out.println(ex.toString());
							}
						});
						
						eight.setOnAction(e ->{
							try {
								//increase the sum by 8 and notify the server of the new sum
								sum += 8;
								toServer.writeInt(sum);
								toServer.flush();
								
								//update turn
								myTurn = false;
								
								//update the sum
								sumDisplay.setText(sum + "");
							}catch(IOException ex) {
								System.out.println(ex.toString());
							}
						});
						
						nine.setOnAction(e ->{
							try {
								//increase the sum by 9 and notify the server of the new sum
								sum += 9;
								toServer.writeInt(sum);
								toServer.flush();
								
								//update turn
								myTurn = false;
								
								//update the sum
								sumDisplay.setText(sum + "");
							}catch(IOException ex) {
								System.out.println(ex.toString());
							}
						});
						
						ten.setOnAction(e ->{
							try {
								//increase the sum by 10 and notify the server of the new sum
								sum += 10;
								toServer.writeInt(sum);
								toServer.flush();
								
								//update turn
								myTurn = false;
								
								//update the sum
								sumDisplay.setText(sum + "");
							}catch(IOException ex) {
								System.out.println(ex.toString());
							}
						});
					}else {
						//cancel button functionality
						one.setOnAction(e->{
						});
						two.setOnAction(e->{
						});
						three.setOnAction(e->{
						});
						four.setOnAction(e->{
						});
						five.setOnAction(e->{
						});
						six.setOnAction(e->{
						});
						seven.setOnAction(e->{
						});
						eight.setOnAction(e->{
						});
						nine.setOnAction(e->{
						});
						ten.setOnAction(e->{
						});
						
						//update text to notify the client that it's the opponent's turn
						if(!gameText.getText().equals("You are Player 2. Your opponent will go first.")) {
							gameText.setText("It is your opponent's turn.");
						}
						
						//receive the sum
						sum = fromServer.readInt();
						
						//update turn
						myTurn = true;
					}
				}
				
			}catch(IOException ex) {
				System.out.println(ex.toString());
			}
		}).start();
	}
	
	//main method to enable use in IDEs with limited javafx support
	public static void main(String[] args) {
		launch(args);
	}
}
