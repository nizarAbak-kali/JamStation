package music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;
/*Chaque commande est composée d’une suite de chaînes terminées par des caractères ’/’. La dernière chaîne est
suivie d’un retour chariot Unix \n*/
public class Client{
	
	protected static final int port=2013;
	private static Socket socket;

	public static void main(String[] args)
	{
		InputStream input   = null;
		PrintStream output =null;
		String message;
	
		try
		{
			socket = new Socket("", port);

			input = socket.getInputStream();
			//PrintStream ps = new PrintStream(socket.getOutputStream());
			output = new PrintStream(socket.getOutputStream());
			message = "CONNECT/user/Amateur_1";
			output.println(message);
			
			String response = new BufferedReader(new InputStreamReader(input)).readLine();
			if(response.matches("WELCOME.+"))
				System.out.println("Server message: " + response);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				input.close();
				socket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
