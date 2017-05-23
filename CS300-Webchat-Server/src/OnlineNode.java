import java.net.InetAddress;
import java.net.UnknownHostException;

public class OnlineNode {
	private InetAddress ip;
	private String username;
	
	public OnlineNode(Boolean test){
		if(test)
			generateTestData();
	}
	
	public OnlineNode(InetAddress ip, String username){
		this.ip = ip;
		this.username = username;
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
		return "OnlineNode [ip=" + ip + ", username=" + username + "]";
	}
	
	
}
