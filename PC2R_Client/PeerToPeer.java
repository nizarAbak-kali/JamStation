package music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class PeerToPeer extends Thread{
	Socket miniChannel;
	ServerSocket miniServer;
	OutputStream miniOutput;
	InputStream miniInput;
	String miniResponse;
	BufferedReader miniBuffer = new BufferedReader(new InputStreamReader(miniInput));
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
				miniChannel = new Socket("", portClient);
				//Devenir serveur pour le nouveau musicien
				miniServer = new ServerSocket(portClient);
				//Accepter le nouveau musicien comme client
				Socket client = miniServer.accept();
				miniOutput = client.getOutputStream();
				miniInput = client.getInputStream();
				
				miniResponse = miniBuffer.readLine();
				System.out.println(miniResponse);
				TransAudio ta = new TransAudio();
				ta.start();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

private class TransAudio extends Thread{
	public void run() {
		while(true){
			AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
		    TargetDataLine microphone;
		    try {
		        microphone = AudioSystem.getTargetDataLine(format);
		        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		        microphone = (TargetDataLine) AudioSystem.getLine(info);
		        microphone.open(format);
		        int numBytesRead;
		        int CHUNK_SIZE = 1024;
		        byte[] data = new byte[microphone.getBufferSize() / 5];
		        microphone.start();
	
		        int bytesRead = 0;
		        while (bytesRead < 100000) {
		            numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
		            bytesRead += numBytesRead;
		            try {
		            	((PrintStream) miniOutput).print("AUDIO_CHUNK/"+Client.tick+"/");
						miniOutput.write(data);
						((PrintStream) miniOutput).print("/");
						Client.tick+=4;
						
						miniResponse = miniBuffer.readLine();
						
						if(miniResponse.matches("AUDIO_OK"))
							System.out.println("Server message: " + miniResponse);
						
						else if(miniResponse.matches("AUDIO_KO"))
							System.err.println("Problème de réception");
						
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
		        microphone.close();
		    } catch (LineUnavailableException e) {
		        e.printStackTrace();
		    } 
		}

	}
}
}
