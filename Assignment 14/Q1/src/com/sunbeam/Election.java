package com.sunbeam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Election {
	
	public static void menu() {
		System.out.println("Election Dashboard");
		System.out.println("===============================================");
		System.out.println("1. Insert Employee");
		System.out.println("2. Display all Employee");
		System.out.println("3. Increment votes of candidate with given ID");
		System.out.println("4. Delete candidate with given ID");
		System.out.println("5. Find candidate of the given ID");
		System.out.println("6. Find the Candidate of given party");
		System.out.println("7. Display total votes for each party");
		System.out.println("8. EXIT");
		System.out.println("===============================================");
	}
	
	public static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost:3306/DAC";
	public static final String DB_USER = "D1_TARUNK_80767";
	public static final String DB_PASSWORD = "1535";
	
	static {
		try {
			Class.forName(DB_DRIVER);
		}
		catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);		
		while(true) {
			menu();
			System.out.print("Enter your choice : ");
			int choice = sc.nextInt();
			try(Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)){
				switch(choice) {
					case 1: //Insert Employee
						String sql = "INSERT INTO candidates VALUES (default, ? , ?, ?)"; //parameterized query
						try(PreparedStatement stmt = con.prepareStatement(sql)){
							System.out.print("Enter Name: ");
							String name = sc.next();
							System.out.print("Enter Party:");
							String party = sc.next();
							System.out.print("Enter Votes");
							int votes = sc.nextInt();
							
							stmt.setString(1, name);
							stmt.setString(2, party);
							stmt.setInt(3, votes);
							
							int count = stmt.executeUpdate();
							System.out.println("Rows affected: " + count);
						}  //stmt.close();
						break;
					
					case 2: //Display all Employee
						String q2 = "SELECT * FROM candidates";
						try(PreparedStatement stmt = con.prepareStatement(q2)){
							try(ResultSet rs = stmt.executeQuery()){
								while(rs.next()) {
									int id = rs.getInt("id");
									String name = rs.getString("name");
									String party = rs.getString("party");
									int votes = rs.getInt("votes");
									System.out.printf("%d %s %s %d\n", id, name, party, votes);
								}
							}
						}
						break;
					
					case 3: //Increment votes of candidate with given ID
						String q3 = "UPDATE candidates SET votes = votes + ? WHERE id = ?";
						try(PreparedStatement stmt = con.prepareStatement(q3)){
							System.out.println("Enter the id of candidate : ");
							int upID = sc.nextInt();
							System.out.print("Enter the incremented votes : ");
							int incVotes = sc.nextInt();
							
							stmt.setInt(1, incVotes);
							stmt.setInt(2, upID);
							int count = stmt.executeUpdate();
							System.out.println("Row affected: "+count);
						}
						break;
					
					case 4: //Delete candidate with given ID
						String q4 = "DELETE FROM candidate WHERE id = ?";
						try(PreparedStatement stmt = con.prepareStatement(q4)){
							System.out.print("Enter the candidate ID: ");
							int inpID = sc.nextInt();
							stmt.setInt(1, inpID);
							int count = stmt.executeUpdate();
							System.out.println("Row Affected " + count);
						}
						break;
					
					case 5: //Find candidate of the given ID
						String q5 = "SELECT * FROM candidates where id = ?";
						try(PreparedStatement stmt = con.prepareStatement(q5)){
							System.out.print("Enter the candidate id: ");
							int findID = sc.nextInt();
							stmt.setInt(1, findID);
							try(ResultSet rs = stmt.executeQuery()){
								while(rs.next()) {
									int id = rs.getInt("id");
									String name = rs.getString("name");
									String party = rs.getString("party");
									int votes = rs.getInt("votes");
									System.out.printf("%d %s %s %d\n", id, name, party, votes);
								}
							}
						}
						break;
					
					case 6: //Find the Candidate of given party
						String q6 = "SELECT * FROM candidates WHERE party = ?";
						try(PreparedStatement stmt = con.prepareStatement(q6)){
							System.out.print("Enter the party name : ");
							String inpParty = sc.next();
							stmt.setString(1, inpParty);
							try(ResultSet rs = stmt.executeQuery()){
								while(rs.next()) {
									int id = rs.getInt("id");
									String name = rs.getString("name");
									String party = rs.getString("party");
									int votes = rs.getInt("votes");
									System.out.printf("%d %s %s %d\n", id, name, party, votes);
								}
							}
						}
						break;
					
					case 7: //Display total votes for each party
						String q7 = "SELECT party, SUM(votes) FROM candidates GROUP BY party";
						try(PreparedStatement stmt = con.prepareStatement(q7)){
							try(ResultSet rs = stmt.executeQuery()){
								while(rs.next()) {
									String party = rs.getString("party");
									int totVotes = rs.getInt("SUM(votes)");
									System.out.printf("%s %d\n", party, totVotes);
								}
							}
						}
						break;
					
					case 8: //EXIT
						System.out.println("Thanks for using the Dashboard.");
						System.exit(0);
						break;
					
					default:
						System.out.println("\n\nEntered the Incorrect Choice!");
						break;
				}
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
		}	
	}
}
