package com.sunbeam;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Users {
	public static void menu() {
		System.out.println("User Mangement System");
		System.out.println("=======================================================");
		System.out.println("1. Insert new User");
		System.out.println("2. Display all users");
		System.out.println("3. Delete voter with the given ID");
		System.out.println("4. Change the status of voter with given ID to true");
		System.out.println("5. Change the name and birth date of voter of given ID");
		System.out.println("6. EXIT");
		System.out.println("=======================================================");		
	}
	public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/DAC";
	public static final String DB_USER = "D1_TARUNK_80767";
	public static final String DB_PASSWORD = "1535";
	
	public static void main(String[] args) {
		try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
			Scanner sc = new Scanner(System.in);
				
				while(true){
					menu();
					System.out.print("Enter your choice :");
					int choice = sc.nextInt();
					switch(choice) {
						case 1: //Insert new User
							String q1 = "INSERT INTO users VALUES (default, ?, ?, ?, ? ,?, ?, ?)";
							try(PreparedStatement stmt = con.prepareStatement(q1)){
								System.out.print("Enter first name : ");
								String fname = sc.next();
								System.out.print("Enter last name : ");
								String lname = sc.next();
								System.out.print("Enter email : ");
								String email = sc.next();
								System.out.print("Enter password : ");
								String pass =  sc.next();
								System.out.print("Enter DOB : ");
								String dob = sc.next();
								System.out.print("Enter Status : ");
								int status = sc.nextInt();
								System.out.print("Enter role : ");
								String role = sc.next();
								
								//SimpleDateFormat sdf;
								SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
								java.util.Date udate = sdf.parse(dob);
								java.sql.Date sDate = new java.sql.Date(udate.getTime());
								
								stmt.setString(1, fname);
								stmt.setString(2, lname);
								stmt.setString(3, email);
								stmt.setString(4, pass);
								stmt.setDate(5, sDate);
								stmt.setInt(6, status);
								stmt.setString(7, role);
								
								int count = stmt.executeUpdate();
								System.out.println("Rows affected: " + count);
							}
							
							break;
						
						case 2: //Display all users
							String q2 = "SELECT * FROM users";
							try(PreparedStatement stmt = con.prepareStatement(q2)){
								try(ResultSet rs = stmt.executeQuery()){
									while (rs.next()) {
										int id = rs.getInt("id");
										String fname = rs.getString("first_name");
										String lname = rs.getString("last_name");
										String email = rs.getString("email");
										String pass = rs.getString("password");
										
										java.sql.Date SDate = rs.getDate("dob");
										SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
										String dateStr = sdf.format(SDate);
										
										boolean status = rs.getBoolean("status");
										String role = rs.getString("role");
										System.out.println(id + "     " + fname + " " + lname + "     " + email + "     "
												+ pass + "     " + dateStr + "     " + status + "     " + role);

									}
								}
							}
							break;
						
						case 3: //Delete voter with the given ID
							String q3 = "DELETE FROM users WHERE id = ?";
							try(PreparedStatement stmt = con.prepareStatement(q3)){
								System.out.print("Enter id to delete : ");
								int delID = sc.nextInt();
								stmt.setInt(1, delID);
								int count = stmt.executeUpdate();
								System.out.println("Rows affected " + count);
							}
							break;
						
						case 4: //Change the status of voter with given ID to true
							String q4 = "UPDATE users SET status = 1 where id = ?";
							try(PreparedStatement stmt = con.prepareStatement(q4)){
								System.out.println("Enter id : ");
								int inpID = sc.nextInt();
								stmt.setInt(1, inpID);
								int count = stmt.executeUpdate();
								System.out.println("Rows affected "+count);
							}
							break;
						
						case 5: //Change the name and birth date of voter of given ID
							String updateNameDob = "UPDATE users SET first_name = ?,last_name=?,dob=? WHERE id=?";
							try (PreparedStatement stmt = con.prepareStatement(updateNameDob)) {
								System.out.println("Enter id of candidate to update : ");
								int id = sc.nextInt();
								System.out.println("Enter first name : ");
								String fname = sc.next();
								System.out.println("Enter last name : ");
								String lname = sc.next();
								System.out.println("Enter dob : ");
								String dob = sc.next();
								SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
								java.util.Date uDate = sdf.parse(dob);
								java.sql.Date SDate;
								SDate = new java.sql.Date(uDate.getTime());
								stmt.setString(1, fname);
								stmt.setString(2, lname);
								stmt.setDate(3, SDate);
								stmt.setInt(4, id);
								int count = stmt.executeUpdate();
								System.out.println("Rows affected : " + count);
							}	
							break;
						
						case 6: //EXIT
							System.out.println("Thanks for using the APP!");
							System.exit(0);
							break;
						
						default:
							System.out.println("\n\nEntered the Incorrect Choice!");
							break;
					}
				}
			}
			catch(Exception e) { 
				e.printStackTrace();
			}
	}
}
