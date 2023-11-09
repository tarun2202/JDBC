package com.sunbeam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDao implements AutoCloseable{
	
	private Connection con;
	private PreparedStatement insertStmt;
	private PreparedStatement displayAllStmt;
	private PreparedStatement deleteByIdStmt;
	private PreparedStatement changeStatusByIdStmt;
	private PreparedStatement changeNameDobByIdStmt;
	
	public UserDao() throws Exception{
		
		con = DbUtil.getConnection();
		
		String q1 = "INSERT into users VALUES (default, ?, ?, ?, ?, ?, ?, ?)";
		insertStmt = con.prepareStatement(q1);
		
		String q2 = "SELECT * FROM users";
		displayAllStmt = con.prepareStatement(q2);
		
		String q3 = "DELETE FROM users WHERE id = ?";
		deleteByIdStmt = con.prepareStatement(q3);
		
		String q4 = "UPDATE users SET status = 1 WHERE id = ?";
		changeStatusByIdStmt = con.prepareStatement(q4);
		
		String q5 = "UPDATE users SET first_name=?, last_name=?, dob=? WHERE id=?";
		changeNameDobByIdStmt = con.prepareStatement(q5);
	}
	
	@Override
	public void close() throws Exception {
		try {
			if(changeNameDobByIdStmt != null)
				changeNameDobByIdStmt.close();
			
			if(changeStatusByIdStmt != null)
				changeStatusByIdStmt.close();
			
			if(deleteByIdStmt != null)
				deleteByIdStmt.close();
			
			if(displayAllStmt != null)
				displayAllStmt.close();
			
			if(insertStmt != null)
				insertStmt.close();
			
			if(con != null)
				con.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	//Case 1: Insert new candidate
	public int insert(User u) throws Exception {
		insertStmt.setString(1, u.getFname());
		insertStmt.setString(2, u.getLname());
		insertStmt.setString(3, u.getEmail());
		insertStmt.setString(4, u.getPassword());
		java.sql.Date sDate = new java.sql.Date(u.getDob().getTime());
		insertStmt.setDate(5, sDate);
		//insertStmt.setString(5, u.getDob());
		insertStmt.setShort(6, u.getStatus());
		insertStmt.setString(7, u.getRole());
			
		int count = insertStmt.executeUpdate();
		return count;
	}
	
	//Case2: Display all candidates
	public List<User> displayAll() throws Exception {
		List<User> list = new ArrayList<User>();
		try(ResultSet rs = displayAllStmt.executeQuery()){
			while(rs.next()) {
				int id = rs.getInt("id");
				String fname = rs.getString("first_name");
				String lname = rs.getString("last_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				java.sql.Date SDate = rs.getDate("dob");
//				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//				String dateStr = sdf.format(SDate);
				//String dob = rs.getString("dob");
				short status = rs.getShort("status");
				String role = rs.getString("role");
				User u = new User(id, fname, lname, email, password, SDate, status, role);
				list.add(u);
			}
		}
		return list;
	}
	//Change the name and birth date of voter of given ID
	//case 3: Delete voter with the given ID
	public int deleteById(int id) throws Exception{
		deleteByIdStmt.setInt(1,id);
		int count = deleteByIdStmt.executeUpdate();
		return count;
	}
	
	//Case 4: Change the status of voter with given ID to true
	public int changeStatusById(int id) throws Exception{
		//q4 = "UPDATE users SET status = 1 WHERE id = ?";
		changeStatusByIdStmt.setInt(1, id);
		int count = changeStatusByIdStmt.executeUpdate();
		return count;
	}
	
	//Case 5: Change the name and birth date of voter of given ID
	public int changeNameDobById(int modId) throws Exception {
		Scanner sc = new Scanner (System.in);
		System.out.print("Enter first name : ");
		String fname = sc.next();
		System.out.print("Enter last name : ");
		String lname = sc.next();
		System.out.print("Enter dob : ");
		String dob = sc.next();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date uDate = sdf.parse(dob);
		java.sql.Date SDate;
		SDate = new java.sql.Date(uDate.getTime());

		changeNameDobByIdStmt.setString(1, fname);
		changeNameDobByIdStmt.setString(2, lname);
		changeNameDobByIdStmt.setDate(3, SDate);
		changeNameDobByIdStmt.setInt(4, modId);
		int count = changeNameDobByIdStmt.executeUpdate();
		return count;
		
		
	}

}
