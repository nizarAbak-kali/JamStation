package music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
/*Chaque commande est composée d’une suite de chaînes terminées par des caractères ’/’. La dernière chaîne est
suivie d’un retour chariot Unix \n*/
public class Client{
	
	protected static final int port=2013;
	private static Socket socket,channel2;
	private static InputStream input;
	private static PrintStream output;
	
	public static void main(String[] args)
	{
		try
		{
			BufferedReader buff = new BufferedReader(new InputStreamReader(input));
			begin();
			protocole(buff);
			jam(buff);
			exit();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		finally
		{
			exit();
			System.err.println("Dommage!");
		}
	}

	private static void jam(BufferedReader buff) {
		try{
			String response = buff.readLine();
			
			if(response.matches("EMPTY_SESSION")){
				String mystyle = "style1";
				double mytempo = 2.2;//Le tempo est constitué de deux numéros, donc on peut le representer par un double
				String message = "SET_OPTIONS/"+mystyle+"/"+mytempo+"/";
				output.println(message);
				response = buff.readLine();
				if(response.matches("ACK_OPTS")){
					System.out.println("Server message: " + response);
				}
				else{
					System.err.println("Jammin went wrong!");
				}
			}
			
			else if(response.matches("CURRENT_SESSION/.+/.+/.+/")){
				String[] tab = response.split("/");
				String mystyle = tab[1];
				double mytempo = Double.valueOf(tab[2]);//Le tempo est constitué de deux numéros, donc on peut le representer par un double
				int nbMusiciens = Integer.valueOf(tab[3]);
				String message = "SET_OPTIONS/"+mystyle+"/"+mytempo+"/";
				output.println(message);
				response = buff.readLine();
				if(response.matches("ACK_OPTS")){
					System.out.println("Server message: " + response);
				}
				else{
					System.err.println("Jammin went wrong!");
				}
			}
			
			else if(response.matches("FULL_SESSION")){
				exit();
				/**
				 * Ou se mettre en spectateur (pas encore ecrit)
				 */
			}
			
		
		}catch(IOException e){
			System.err.println("Jammin went wrong!");
			e.printStackTrace();
		}
	}

	private static void protocole(BufferedReader buff) {
		try{
			String response = buff.readLine();
			if(response.matches("WELCOME.+"))
				System.out.println("Server message: " + response);

			response = buff.readLine();
			System.out.println("Server message2: " + response);
			if(response.matches("AUDIO_PORT/.+/")){
				String[] tab = response.split("/");
				String port2 = tab[1];
				channel2 = new Socket("",Integer.valueOf(port2));
			}
			
			response = buff.readLine();
			if(!response.matches("AUDIO_OK"))
				System.err.println("Message error!");
			System.out.println("Server message: " + response);
			
			response = buff.readLine();
			if(!response.matches("CONNECTED/.+/"))
				System.err.println("Message error!");
			System.out.println("Server message: " + response);
			
		}catch(IOException e){
			System.err.println("Protocole went wrong!");
			e.printStackTrace();
		}

	}

	private static void begin() {
		try {
			socket = new Socket("", port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			input = socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			output = new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String message = "CONNECT/Amateur_1/";
		output.println(message);
		
	}

	private static void exit() {
		String message = "EXIT/Amateur_1/";
		output.println(message);
		try {
			channel2.close();
		} catch (IOException e1) {
			System.err.println("channel2.close() denied!");
			e1.printStackTrace(); 
		}
		try {
			socket.close();
		} catch (IOException e) {
			System.err.println("socket.close() denied!");
			e.printStackTrace();
		}
	}
}
