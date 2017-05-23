import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainClient {

	public static void main(String[] args) {

		String hostName = "localhost";
		int port = 60010;
		
		try(
				Socket socket = new Socket(hostName, port);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				){
			
			String fromServer, fromUser;
			
			while(true){
				fromServer = in.readLine();
				System.out.println("Server: " + fromServer);
					
				fromUser = stdIn.readLine();
				
				if(fromUser != null && !fromUser.equals("")){
					System.out.println("Client: " + fromUser);
					out.println(fromUser);
				}
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
