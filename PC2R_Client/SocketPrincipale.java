package music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class SocketPrincipale extends Thread{
	static String response=null;
	public void run(){
		begin();
		ConnexionSecurisee cs = new ConnexionSecurisee();
		try {
			while((response=Client.buff.readLine()) != null){
				protocole();
				jam();
				sync();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		exit();
	}

	private void sync() {
		if(response.matches("AUDIO_SYNC/.+/")){
			String[] tab = response.split("/");
			Client.tick = Integer.valueOf(tab[1]);
		}
	}

	private static void jam() {
		try{
			if(response.matches("EMPTY_SESSION")){
				String mystyle = "style1";
				int mytempo = 120;
				String message = "SET_OPTIONS/"+mystyle+"/"+mytempo+"/";
				Client.output.println(message);
				response = Client.buff.readLine();
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
				int mytempo = Integer.valueOf(tab[2]);
				int nbMusiciens = Integer.valueOf(tab[3]);
				String message = "SET_OPTIONS/"+mystyle+"/"+mytempo+"/";
				Client.output.println(message);
				response = Client.buff.readLine();
				if(response.matches("ACK_OPTS")){
					System.out.println("Server message: " + response);
				}
				else{
					System.err.println("Jammin went wrong!");
				}
			}
			
			else if(response.matches("FULL_SESSION")){
				double alea = Math.random();
				if(alea<0.5)
					exit();
				else
					spectateur();
			}
			
		
		}catch(IOException e){
			System.err.println("Jammin went wrong!");
			e.printStackTrace();
		}
	}
	
	private static void spectateur() {
		String message = "SPECTATOR";
		Client.output.println(message);
		while(!Client.socket.isClosed()){
			try {
				response=Client.buff.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(response!=null)
				System.out.println(response);
		}
	}

	private static void protocole() {
		try{
			if(response.matches("WELCOME.+")){
				System.out.println("Server message: " + response);
				response = Client.buff.readLine();
				if(response.matches("AUDIO_PORT/.+/")){
					String[] tab = response.split("/");
					String port2 = tab[1];
					Client.channel2 = new Socket("",Integer.valueOf(port2));
					Client.input2 = Client.channel2.getInputStream();
					Client.output2 = new PrintStream(Client.channel2.getOutputStream());

					response = Client.buff.readLine();
					if(!response.matches("AUDIO_OK"))
						System.err.println("Message error!");
					System.out.println("Server message: " + response);
					
					response = Client.buff.readLine();
					if(!response.matches("CONNECTED/.+/"))
						System.err.println("Message error!");
					System.out.println("Server message: " + response);
				}
			}
		}catch(IOException e){
			System.err.println("Protocole went wrong!");
			e.printStackTrace();
		}

	}

	private static void begin() {
		try {
			Client.socket = new Socket("", Client.port);
			Client.input = Client.socket.getInputStream();
			Client.output = new PrintStream(Client.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String message = "CONNECT/"+Client.name+"/";
		Client.output.println(message);
	}

	private static void exit() {
		String message = "EXIT/"+Client.name+"/";
		Client.output.println(message);
		try {
			Client.channel2.close();
			Client.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
