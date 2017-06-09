import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class OnlineNode {
	private InetAddress ip;
	private String username;
	private ObjectOutputStream output;  // The buffered output for this client
	
	public OnlineNode(Boolean test){
		if(test)
			generateTestData();
	}
	
	public OnlineNode(InetAddress ip, String username, ObjectOutputStream output){
		this.ip = ip;
		this.username = username;
		this.output = output;
	}

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ObjectOutputStream getOutput() {
		return output;
	}

	public void setOutput(ObjectOutputStream output) {
		this.output = output;
	}
	
	private void generateTestData(){
		try {
			
			ip = InetAddress.getByName("www.google.com");
			username = "username1";
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "OnlineNode [ip=" + ip + ", username=" + username + " output=" + output.toString() + "]";
	}
	
	
}
