package Impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import Dao.UserDao;
import Service.UserService;
@Service("UserServiceImpl")
public class UserServiceImpl implements UserService {
	@Resource(name="UserDAO")
	UserDao userDAO;
	
	public UserDao getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDao userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public void useradd(String userid, String pwd, String username) throws Exception {
		// TODO Auto-generated method stub
		userDAO.useradd(userid, pwd, username);
	}

	@Override
	public String checkuser(String userid, String pwd, Boolean ismanager) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.checkuser(userid, pwd, ismanager);
	}

}
