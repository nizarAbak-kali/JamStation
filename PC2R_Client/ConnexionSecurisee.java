package music;

import java.io.IOException;

public class ConnexionSecurisee{
	private static boolean isRegistered = false;
	private final String name = "myname";
	private final String password = "12345";
	public ConnexionSecurisee(){
		if (!isRegistered){
			String message = "REGISTER/"+name+"/"+password+"/";
			Client.output.println(message);
			try {
				SocketPrincipale.response = Client.buff.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(SocketPrincipale.response.matches("ACCESSDENIED"))
				System.err.println("Connexion denied");
			else{
				isRegistered=true;
				System.out.println("OK");
			}
		}
		
		else{
			String message = "LOGIN/"+name+"/"+password+"/";
			Client.output.println(message);
			try {
				SocketPrincipale.response = Client.buff.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(SocketPrincipale.response.matches("ACCESSDENIED"))
				System.err.println("Connexion denied");
			else
				System.out.println("OK");
		}
	}
}
