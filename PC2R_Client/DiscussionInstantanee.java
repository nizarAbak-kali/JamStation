package music;

import java.io.IOException;

public class DiscussionInstantanee extends Thread{
	public void run(){
		while(true){
			String message = "TALK/"+TextAreaDemo.getText()+"/";
			Client.output.println(message);
			
			try {
				SocketPrincipale.response = Client.buff.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			while(!SocketPrincipale.response.matches("LISTEN/.+/.+/"));
			String[] tab = SocketPrincipale.response.split("/");
			System.out.println(tab[2]);
		}
	}
}
