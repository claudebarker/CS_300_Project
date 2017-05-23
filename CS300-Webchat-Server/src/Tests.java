import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Tests {

	public void testOnlineNode(){

		OnlineNode test = new OnlineNode(true);
		System.out.println(test.toString());
	}
	
	public void testRequestNode(){
		RequestNode test = new RequestNode(true);
		System.out.println(test.toString());
	}

}
