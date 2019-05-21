package Service;

public interface UserService {
	public void useradd(String userid, String pwd,String username) throws Exception;
	public String checkuser(String userid, String pwd,Boolean ismanager) throws Exception;
	
}
