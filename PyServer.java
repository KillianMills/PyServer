import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import java.awt.Desktop;
import java.net.URI;
import java.net.*; 

public class PyServer {

	private static String[] xdtCommands ={

		"xdotool mousemove_relative -- ",//0
		"xdotool click ", //1
		"xdotool type ", //2
		"xdotool key KP_Enter", //3
		"xdotool key Up", //4
		"xdotool key Down", //5
		"xdotool key Right", //6
		"xdotool key Left" //7
	};


	private static ServerSocket serverSocket;
	private static Socket clientSocket;
	private static InputStreamReader inputStreamReader;
	private static BufferedReader bufferedReader;
	private static String message;
	private static String oldMessage;

	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(4444); // Server socket


		} catch (IOException e) {
			System.out.println("Port 4444, not working");
		}

		System.out.println("Server on, port:4444");


		while (true) {
			try {
				clientSocket = serverSocket.accept();


				inputStreamReader = new InputStreamReader(clientSocket.getInputStream());

				bufferedReader = new BufferedReader(inputStreamReader);

				message= bufferedReader.readLine();
				String trimValue=message.substring(0,1); 
				message=message.substring(1);


				int holder = Integer.parseInt(trimValue);

				// Open the URL in the Browser
				if(holder == 8){

					//Runtime rt = Runtime.getRuntime();
					//Process process = rt.exec(new String[]{"chromium",message});

					if(Desktop.isDesktopSupported()){
						try{
							Desktop.getDesktop().browse(new URI(message));
						}
						catch(URISyntaxException e){ e.printStackTrace(); }
					}

				}


				// Do an xdotool command
				else{

					message=xdtCommands[holder]+message;

					inputStreamReader.close();
					clientSocket.close();
					Runtime runtime = Runtime.getRuntime();
					Process process = runtime.exec(message);
				}

			} catch (IOException ex) {
				System.out.println("Message reading error");
			}

		}
	}

}