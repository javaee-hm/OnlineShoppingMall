package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import Bean.BeanUser;


public class UserDao {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public void useradd(String userid, String pwd,String username) throws Exception {
		String sql="select user_id from user where user_id=?";
		BeanUser user=this.jdbcTemplate.query(sql, new ResultSetExtractor<BeanUser>() {
			@Override
			public BeanUser extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				if(rs.next()) {
					BeanUser user=new BeanUser();
					user.setUser_id(rs.getString(1));
					return user;
				}
				else
					return null;
			}},userid);
		if(user==null) {
			sql="insert into tb_users(userid,pwd,username,ismanager) values(?,?,?,0)";
			this.jdbcTemplate.update(sql,new Object[] {userid,pwd,username});
		}
		else throw new Exception("用户id已被占用");
	}
	
	public String checkuser(String userid, String pwd,Boolean ismanager) throws Exception {
		if (userid == null && userid=="")
			throw new Exception("请输入用户名");
		if (pwd == null)
			pwd = "";
		String selectStatement = "select username,pwd,ismanager from user where userid=?"; // sql语句自己补充
		 BeanUser user=this.jdbcTemplate.query(selectStatement, new ResultSetExtractor<BeanUser>() {
			@Override
			public BeanUser extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				BeanUser user=new BeanUser();
				if (rs.next()) {
					user.setPwd(rs.getString("pwd"));
					user.setUser_name(rs.getString("username"));
					user.setIsmanager(rs.getBoolean("ismanager"));
					return user;
				}
				return null;
			}	
		}, userid);
		if(user==null)throw new Exception("用户不存在");
		if (!user.getPwd().equals(pwd))throw new Exception("密码错误");
		if (!user.isIsmanager()==ismanager)throw new Exception("身份选择错误");
		return user.getUser_name();
	}
}
