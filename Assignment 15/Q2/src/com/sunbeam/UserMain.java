package com.sunbeam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserMain {

	public static void menu() {
		System.out.println("User Mangement System");
		System.out.println("=======================================================");
		System.out.println("1. Insert new User");
		System.out.println("2. Display all users");
		System.out.println("3. DElete voter with the given ID");
		System.out.println("4. Change the status of voter with given ID to true");
		System.out.println("5. Change the name and birth date of voter of given ID");
		System.out.println("6. EXIT");
		System.out.println("=======================================================");
	}
	
	public static User accept(Scanner sc) throws ParseException {
		System.out.print("Enter fname : ");
		String fname = sc.next();
		System.out.print("Enter lname : ");
		String lname = sc.next();
		System.out.print("Enter email : ");
		String email = sc.next();
		System.out.print("Enter password : ");
		String password = sc.next();
		System.out.print("Enter DOB : ");
		String dob = sc.next();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date uDate = sdf.parse(dob);
		java.sql.Date SDate = new java.sql.Date(uDate.getTime());
		//System.out.println(SDate);
		
		System.out.print("Enter status(0/1) : ");
		short status = sc.nextShort();
		System.out.print("Enter role : ");
		String role = sc.next();
		User u = new User(0, fname, lname, email, password, SDate, status, role);
		return u;
	}
	
	public static void main(String[] args) throws ParseException {
		Scanner sc = new Scanner(System.in);
		int choice;
		while(true) {
			menu();
			System.out.print("Enter your choice : ");
			choice = sc.nextInt();
			switch(choice) {
				case 1: //Insert new User
						try(UserDao dao = new UserDao()) {
							User u = accept(sc);
							int count = dao.insert(u);
							System.out.println("Rows Updated: " + count);
						} // dao.close();
						catch (Exception e) {
							e.printStackTrace();
						}
						break;
					
				case 2: //Display all users
						try(UserDao dao = new UserDao()){
							List<User> list = new ArrayList<User>();
							list = dao.displayAll();
							list.forEach(e->System.out.println(e));
						}
						catch(Exception ex) {
							ex.printStackTrace();
						}
						break;
					
				case 3: //Delete voter with the given ID
						System.out.println("Enter the user id: ");
						int idToDelete = sc.nextInt();
						try(UserDao dao = new UserDao()){
							int rupdate = dao.deleteById(idToDelete);
							System.out.println("Rows affected : "+rupdate);
						}
						catch(Exception e) {
							e.printStackTrace();
						}
						break;
				
				case 4: //Change the status of voter with given ID to true
						System.out.println("Enter the user id: ");
						int idToChange = sc.nextInt();
						try(UserDao dao = new UserDao()){
							int rupdate = dao.changeStatusById(idToChange);
							System.out.println("Rows affected : "+rupdate);
						}
						catch(Exception e) {
							e.printStackTrace();
						}
						break;
				
				case 5: //Change the name and birth date of voter of given ID
						System.out.print("Enter id of candidate to update : ");
						int id = sc.nextInt();
						try(UserDao dao = new UserDao()){
							int rupdate = dao.changeNameDobById(id);
							System.out.println("Rows affected : "+rupdate);
						}
						catch(Exception e) {
							e.printStackTrace();
						}
						break;
				
				case 6: //EXIT
						System.out.println("Thanks for using the Dashboard.");
						System.exit(0);
						break;
				
				default:
						System.out.println("\n\nEntered the Incorrect Choice!");
						break;	
			}
		}
	}
}
