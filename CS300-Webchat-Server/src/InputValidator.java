import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {
	String passPatternString;
	Pattern passwordPattern;
	String userPatternString;
	Pattern usernamePattern;

	public InputValidator(){

		passPatternString = "[+A-Za-z0-9!_.-]";
		passwordPattern = Pattern.compile(passPatternString);
		
		userPatternString = "[A-Za-z0-9]";
		usernamePattern = Pattern.compile(userPatternString);
	}
	
	public boolean validatePassword(String password){
		if(password.isEmpty())
			return false;
		
		if(password.length() < 5 || password.length() > 128)
			return false;
		
		for(int i = 0; i < password.length(); ++i){
			
			System.out.println("Checking char " + password.charAt(i));
			
			Matcher m = passwordPattern.matcher("" + password.charAt(i));
			
			if(!m.find()){
				return false;
			}
		}
		
		return true;
	}
	
	public boolean validateUsername(String username){
		if(username.isEmpty())
			return false;
		
		if(username.length() < 1 || username.length() > 30)
			return false;
		
		for(int i = 0; i < username.length(); ++i){
			
			System.out.println("Checking char " + username.charAt(i));
			
			Matcher m = usernamePattern.matcher("" + username.charAt(i));
			
			if(!m.find()){
				return false;
			}
		}
		
		return true;
	}
}
