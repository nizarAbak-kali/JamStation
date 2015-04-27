package music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class PeerToPeer extends Thread{
	public void run(){
		while(true){
			while(!SocketPrincipale.response.matches("CLIENT/.+/")){
				try {
					yield();
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			String[] tab = SocketPrincipale.response.split("/");
			int portClient = Integer.valueOf(tab[1]);
			//Devenir client pour le nouveau musicien
			try {
				Socket miniChannel = new Socket("", portClient);
				//Devenir serveur pour le nouveau musicien
				ServerSocket miniServer = new ServerSocket(portClient);
				//Accepter le nouveau musicien comme client
				Socket client = miniServer.accept();
				// Open output stream
				OutputStream miniOutput = client.getOutputStream();
				InputStream miniInput = client.getInputStream();
				// Show the server response
				String miniResponse = new BufferedReader(new InputStreamReader(miniInput)).readLine();
				System.out.println("Nouveau musicien : " + miniResponse);
				
				client.close();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
