package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import Bean.BeanBook;
import Bean.BeanOrderDetail;
import exception.BookNotFoundException;

public class OrderDetailDao {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public Collection<BeanOrderDetail> getOrderDetails(int orderId) throws BookNotFoundException {
		ArrayList<BeanOrderDetail> orderDetails = new ArrayList<>();
		try {
			Set<Integer>bookids=new HashSet<>();
			String selectStatement = "select num,book_id from order_details where a.order_id = ?";
			this.jdbcTemplate.query(selectStatement, new Object[] { orderId }, new ResultSetExtractor<BeanOrderDetail>() {
				@Override
				public BeanOrderDetail extractData(ResultSet rs) throws SQLException, DataAccessException {
					// TODO Auto-generated method stub
					while (rs.next()) {
						BeanOrderDetail orderDetail = new BeanOrderDetail();
						orderDetail.setNum(rs.getInt(1));
						orderDetail.setBook_id(rs.getInt(2));
						orderDetail.setOrder_id(orderId);
						orderDetails.add(orderDetail);
						bookids.add(rs.getInt(2));
					}
					return null;
				}
			});
			String strbookids="";
			for(Integer bookid:bookids) {
				if(strbookids==null)strbookids=bookid+"";
				else strbookids+=","+bookid;
			}
			selectStatement="select book_id,title,author,publisher,publishing_time,book_price,category_id,inventory,description from books where book_id in ("+strbookids+") order by id desc";
			
			Map<Integer, BeanBook>bookMap=new HashMap<>();
			this.jdbcTemplate.query(selectStatement, new ResultSetExtractor<BeanBook>() {
				@Override
				public BeanBook extractData(ResultSet rs) throws SQLException, DataAccessException {
					// TODO Auto-generated method stub
					while(rs.next()) {
						BeanBook bookDetails=new BeanBook();
						bookDetails.setBook_id(rs.getInt(1)); 
						bookDetails.setTitle(rs.getString(2));
						bookDetails.setAuthor(rs.getString(3));
						bookDetails.setPublisher(rs.getString(4));
						bookDetails.setPublishing_time(rs.getDate(5));
						bookDetails.setBook_price(rs.getFloat(6));
						bookDetails.setCategory_id(rs.getInt(7));
						bookDetails.setInventory(rs.getInt(8));
						bookDetails.setDescription(rs.getString(9));
						bookMap.put(bookDetails.getBook_id(), bookDetails);
					}
					return null;
				}
			});	
			for(BeanOrderDetail d:orderDetails) {
				d.setBookDetails(bookMap.get(d.getBook_id()));
			}
		} finally {
			return orderDetails;
		}
	}

}
