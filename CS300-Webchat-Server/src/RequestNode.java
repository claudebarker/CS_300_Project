import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class RequestNode {
	private InetAddress ip;
	private int requestCode;
	private ArrayList<Object> requestData;
	
	// Create a dummy request node full of test data
	public RequestNode(Boolean test){
		if(test)
			generateTestData();
	}
	
	public RequestNode(InetAddress ip, int requestCode, ArrayList<Object> requestData) {
		this.ip = ip;
		this.requestCode = requestCode;
		this.requestData = requestData;
	}
	
	public InetAddress getIp() {
		return ip;
	}
	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
	public int getRequestCode() {
		return requestCode;
	}
	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}
	public ArrayList<Object> getRequestData() {
		return requestData;
	}
	public void setRequestData(ArrayList<Object> requestData) {
		this.requestData = requestData;
	}

	@Override
	public String toString() {
		return "RequestNode [ip=" + ip + ", requestCode=" + requestCode + ", requestData=" + requestData + "]";
	}
	
	private void generateTestData(){
		try {
			
			ip = InetAddress.getByName("www.google.com");
			requestCode = (int)(Math.random() * 5);
			requestData = new ArrayList<Object>();
			
			requestData.add("test1");
			requestData.add("test2");
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
