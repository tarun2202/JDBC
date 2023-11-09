package com.sunbeam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AccountHolderDao implements AutoCloseable {
		private Connection con;
		private PreparedStatement insertStmt;
		private PreparedStatement displayAllStmt;
		private PreparedStatement editDobNameStmt;
		private PreparedStatement displayAccGivenTypeStmt;
		private PreparedStatement deleteAccStmt;
		private PreparedStatement displayAccTypeWiseStmt;
		private PreparedStatement sortAccBalStmt;
		
		public AccountHolderDao() throws Exception {
			con = Db_Util.getConnection();
			
			String q1 = "INSERT into acc_holder_details VALUES (?, ?, ?, ?, ?, ?)";
			insertStmt = con.prepareStatement(q1);
			
			String q2 = "SELECT * FROM acc_holder_details";
			displayAllStmt = con.prepareStatement(q2);
			
			String q3 = "UPDATE acc_holder_details SET birthdate = ?, name = ? WHERE acc_no = ?";
			editDobNameStmt = con.prepareStatement(q3);
			
			String q4 = "SELECT * FROM acc_holder_details WHERE acctype_id = ?";
			displayAccGivenTypeStmt = con.prepareStatement(q4);
			
			String q5 = "DELETE FROM acc_holder_details WHERE acc_no = ?";
			deleteAccStmt = con.prepareStatement(q5);
			
			String q6 = "SELECT acctype_id, COUNT(acc_no) sum FROM acc_holder_details GROUP BY acctype_id;";
			displayAccTypeWiseStmt = con.prepareStatement(q6);
			
			String q7 = "SELECT * FROM acc_holder_details";
			sortAccBalStmt = con.prepareStatement(q7);
			
		}
		public void close() {
			try {
				if(sortAccBalStmt != null)
					sortAccBalStmt.close();
				
				if(displayAccTypeWiseStmt != null)
					displayAccTypeWiseStmt.close();
				
				if(deleteAccStmt != null)
					deleteAccStmt.close();
				
				if(displayAccGivenTypeStmt != null)
					displayAccGivenTypeStmt.close();
				
				if(editDobNameStmt != null)
					editDobNameStmt.close();
				
				if(displayAllStmt != null)
					displayAllStmt.close();
				
				if(insertStmt != null)
					insertStmt.close();
				
				if(con != null)
				con.close();
			} 
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		//case 1: insertStmt
		public int insert(AccountHolder c) throws Exception {
			//String q1 = "INSERT into acc_holder_details values VALUES (?, ?, ?, ?, ?, ?)";
			Scanner sc = new Scanner(System.in);
			insertStmt.setInt(1, c.getAccno());
			insertStmt.setString(2, c.getName());
			insertStmt.setString(3, c.getEmail());
			java.sql.Date SDate = new java.sql.Date(c.getDob().getTime());		
			insertStmt.setDate(4, SDate);
			insertStmt.setDouble(5, c.getBalance());
			insertStmt.setInt(6, c.getAccId());
				
			int count = insertStmt.executeUpdate();
			return count;
		}
		
		
		public Set<AccountHolder> displayAll() throws Exception {
			Set<AccountHolder> list = new HashSet<AccountHolder>();
			try(ResultSet rs = displayAllStmt.executeQuery()){
				while(rs.next()) {
					int id = rs.getInt("acc_no");
					String fname = rs.getString("name");
					String email = rs.getString("email");
					java.sql.Date SDate = rs.getDate("birthdate");
					double bal = rs.getDouble("balance");
					int act = rs.getInt("acctype_id");
					AccountHolder u = new AccountHolder(id, fname, email, SDate, bal, act);
					list.add(u);
				}
			}
			return list;
		}
		
		public int editDobName(int modId) throws Exception {
			Scanner sc = new Scanner (System.in);
			System.out.print("Enter dob : ");
			String dob = sc.next();
			System.out.print("Enter name : ");
			String fname = sc.next();
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			java.util.Date uDate = sdf.parse(dob);
			java.sql.Date SDate;
			SDate = new java.sql.Date(uDate.getTime());
			
			editDobNameStmt.setDate(1, SDate);
			editDobNameStmt.setString(2, fname);
			editDobNameStmt.setInt(3, modId);
			int count = editDobNameStmt.executeUpdate();
			return count;
		}
		
		public Set<AccountHolder>  displayAccGivenType(int modId) throws Exception {
			Set<AccountHolder> list = new HashSet<AccountHolder>();
			AccountHolder u = null;
			displayAccGivenTypeStmt.setInt(1, modId);
			try(ResultSet rs = displayAccGivenTypeStmt.executeQuery()){
				while(rs.next()) {
					int id = rs.getInt("acc_no");
					String fname = rs.getString("name");
					String email = rs.getString("email");
					java.sql.Date SDate = rs.getDate("birthdate");
					double bal = rs.getDouble("balance");
					int act = rs.getInt("acctype_id");
					u = new AccountHolder(id, fname, email, SDate, bal, act);
					list.add(u);
				}
			}
			return list;
		}
		
		public int deleteAcc(int modId) throws Exception {
			deleteAccStmt.setInt(1, modId);
			int count = deleteAccStmt.executeUpdate();
			return count;
		}
		
		public Set<AccountType> displayAccTypeWise() throws Exception {
			Set<AccountType> list = new HashSet<AccountType>();
			try(ResultSet rs = displayAccTypeWiseStmt.executeQuery()){
				while(rs.next()) {
					int id = rs.getInt("acctype_id");
					int sum = rs.getInt("sum");
					AccountType a = new AccountType(id, sum);
					list.add(a);
				}
			}
			return list;
		}
		
		public Set<AccountHolder> sortAccBal() throws Exception {
			Set<AccountHolder> list = new HashSet<AccountHolder>();
			try(ResultSet rs = displayAllStmt.executeQuery()){
				while(rs.next()) {
					int id = rs.getInt("acc_no");
					String fname = rs.getString("name");
					String email = rs.getString("email");
					java.sql.Date SDate = rs.getDate("birthdate");
					double bal = rs.getDouble("balance");
					int act = rs.getInt("acctype_id");
					AccountHolder u = new AccountHolder(id, fname, email, SDate, bal, act);
					list.add(u);
				}
			}
			return list;
		}	
}

