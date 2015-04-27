package music;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
/*Chaque commande est composée d’une suite de chaînes terminées par des caractères ’/’. La dernière chaîne est
suivie d’un retour chariot Unix \n*/
public class Client{
	
	protected static final int port=2013;
	protected static Socket socket,channel2;
	protected static InputStream input,input2;
	protected static PrintStream output,output2;
	protected static BufferedReader buff = new BufferedReader(new InputStreamReader(input));
	protected static BufferedReader buff2 = new BufferedReader(new InputStreamReader(input2));
	
	public static void main(String[] args)
	{
		SocketPrincipale sp = new SocketPrincipale();
		SocketSecondaire sc = new SocketSecondaire();
		sp.start();
		sc.start();
		try {
			sp.join();
			sc.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	

	
}
