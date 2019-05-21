package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import Bean.BeanBook;
import Bean.BeanOrder;
import Bean.BeanOrderDetail;
import cart.ShoppingCart;
import cart.ShoppingCartItem;

import exception.BooksNotFoundException;


public class OrderDao {
	ArrayList<BeanOrder>orders;
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public  void AddOrder(String userid,ShoppingCart cart,String address) throws Exception {
    	Date date =new Date(System.currentTimeMillis());
    	try {
    		double price=0;
    		for(ShoppingCartItem item:cart.getItems()){
    			price+=item.getQuantity()*item.getItem().getBook_price();
    			int inventory=item.getItem().getInventory();
    			if(inventory>=item.getQuantity());
    			else throw new Exception("书名为"+item.getItem().getTitle()+"的书库存不足,只剩"+inventory+"本");
    		}
    		String sql="insert into orderlist(user_id,order_time,price,address,state) VALUES(?,?,?,?,0)";
    		this.jdbcTemplate.update(sql,new Object[] {userid,date,price,address});
    		int orderid = 0;
			sql="select order_id from orderlist where user_id=? and order_time=?";
			BeanOrder orderList=this.jdbcTemplate.query(sql, new ResultSetExtractor<BeanOrder>() {
				BeanOrder orderList=new BeanOrder();
				@Override
				public BeanOrder extractData(ResultSet rs) throws SQLException, DataAccessException {
					// TODO Auto-generated method stub
					if(rs.next())
						orderList.setOrder_id(rs.getInt(1));
					return orderList;
				}
			},userid,date);		
			orderid=orderList.getOrder_id();
    		sql="insert into order_details(order_id,book_id,num,detail_price) VALUES(?,?,?,?)";    		
    		for(ShoppingCartItem item:cart.getItems()){
    			this.jdbcTemplate.update(sql,new Object[] {orderid,item.getItem().getBook_id(),item.getQuantity(),item.getItem().getBook_price()});
			}	
    		for(ShoppingCartItem item:cart.getItems()){
    			int inventory=0;
    			sql="select inventory from books where id=?";
    			BeanBook bookDetails=this.jdbcTemplate.query(sql, new ResultSetExtractor<BeanBook>() {
    				BeanBook bookDetails=new BeanBook();
    				@Override
    				public BeanBook extractData(ResultSet rs) throws SQLException, DataAccessException {
    					// TODO Auto-generated method stub
    					if(rs.next())
    						bookDetails.setInventory(rs.getInt(1));
    					return bookDetails;
    				}
    			},item.getItem().getBook_id());
    			inventory=bookDetails.getInventory();
    			sql="update books set inventory=? where book_id=?";
    			this.jdbcTemplate.update(sql,new Object[] {inventory-item.getQuantity(),item.getItem().getBook_id()});			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public Collection<BeanOrder> getOrders(String userid,boolean withDetails) throws BooksNotFoundException{
        orders= new ArrayList<>();
        String selectStatement = "select order_id,order_time,order_price,address,state from orderlist where user_id=?";
		this.jdbcTemplate.query(selectStatement, new ResultSetExtractor<BeanOrder>() {
			@Override
			public BeanOrder extractData(ResultSet rs) throws SQLException, DataAccessException {
				// TODO Auto-generated method stub
				while(rs.next()) {
					BeanOrder order=new BeanOrder();
					order.setOrder_id(rs.getInt(1));
					order.setOrder_time(rs.getDate(2));
					order.setUser_id(userid);
					order.setOrder_price(rs.getFloat(3));
					order.setAddress(rs.getString(4));
					order.setState(rs.getBoolean(5));
					orders.add(order);
				}
				return null;
			}
		},userid);
		if(withDetails&&orders.size()>0) {
			Map<Integer, BeanOrder>mp=new HashMap<>();
			for(BeanOrder o:orders) {
				mp.put(o.getOrder_id(), o);
			}
			String orderids="";
			for(int i=0;i<orders.size();i++) {
				if(i!=0)orderids+=",";
				orderids+=orders.get(i).getOrder_id();
			}
			selectStatement="select order_id,book_id,num,detail_price from order_details where order_id in ("+orderids+") order by order_id desc";		
			Set<Integer>bookids=new HashSet<>();
			this.jdbcTemplate.query(selectStatement, new ResultSetExtractor<BeanOrderDetail>() {
				@Override
				public BeanOrderDetail extractData(ResultSet rs) throws SQLException, DataAccessException {
					// TODO Auto-generated method stub
					while(rs.next()) {
						BeanOrderDetail orderDetails=new BeanOrderDetail();
						orderDetails.setOrder_id(rs.getInt(1));
						orderDetails.setBook_id(rs.getInt(2));
						orderDetails.setNum(rs.getInt(3));
						orderDetails.setDetail_price(rs.getFloat(4));
						bookids.add(rs.getInt(2));
						BeanOrder order=mp.get(orderDetails.getOrder_id());
						if(order!=null) {
							if(order.getOrderDetails()==null) {
								order.setOrderDetails(new ArrayList<>());
							}
							order.getOrderDetails().add(orderDetails);
						}
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
			for(BeanOrder orderList:orders) {
				for(BeanOrderDetail d:orderList.getOrderDetails()) {
					d.setBookDetails(bookMap.get(d.getBook_id()));
				}
			}
		}
        Collections.sort(orders);
        return orders;
    }
    public void finishOrder(int orderid) {
    	String sql="update orderlist set state=1 where order_id=?";
    	jdbcTemplate.update(sql, new Object[] {orderid});
    }
}
