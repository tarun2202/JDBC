/*
 
 A. Use of appropriate POJOs and DAOs is mandatory.(3)

B. Use HashSet Collection.(5)

C. Operation 3 should allow modifying Account holder's name, dob for the given acc_no.

D. Operation 6 should display columns: account type, total number of Account Holders.

E. Operation 6 display using stream functions.

F. Operation 7 Sorting should be done using stream functions.
 
 */

package com.sunbeam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class MainClass {
	
	static class salDescComparator implements Comparator<AccountHolder> {

		@Override
		public int compare(AccountHolder a1, AccountHolder a2) {
			int diff = -Double.compare(a1.getBalance(),a2.getBalance());
			return diff;
		}
		
	}
	
	public static void menu() {
		System.out.println();
		System.out.println("                    Banking Management System");
		System.out.println("===============================================================");
		System.out.println("1. Insert new Account Holder");
		System.out.println("2. Display all Account Holder");
		System.out.println("3. Edit name & DOB for given A/C no");
		System.out.println("4. Dsiplay Account Details for given A/C type");
		System.out.println("5. DElete Account Holder for given A/C no");
		System.out.println("6. Display account type wise total number of Account Holders ");
		System.out.println("7. Sort Accoiunt Balance");
		System.out.println("8. EXIT");
		System.out.println("===============================================================");
		System.out.println();
	}
	
	public static AccountHolder accept(Scanner sc) throws ParseException {
		System.out.print("Enter Ac/c no : ");
		int id = sc.nextInt();
		
		System.out.print("Enter A/c holder name: ");
		String name = sc.next();
		
		System.out.print("Enter Email : ");
		String email = sc.next();
		
		System.out.print("Enter DOB : ");
		String dob = sc.next();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date uDate = sdf.parse(dob);
		java.sql.Date SDate = new java.sql.Date(uDate.getTime());
		
		System.out.print("Enter Balance : ");
		double balance = sc.nextDouble();
		
		System.out.print("Enter Account Type id : ");
		int accTypeId = sc.nextInt();
		
		AccountHolder a = new AccountHolder(id, name, email, SDate, balance, accTypeId);
		return a;
	} 
	
	public static void main(String args[]) {
		int choice;
		Scanner sc = new Scanner(System.in);
		while(true) {
			menu();
			System.out.print("Enter your choice (1-8) : ");
			choice =sc.nextInt();
			switch (choice) {
			case 1: //Insert new Account Holder
				try(AccountHolderDao dao = new AccountHolderDao()) {
					AccountHolder a = accept(sc);
					int count = dao.insert(a);
					System.out.println("Rows Updated: " + count);
				} // dao.close();
				catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case 2: //Display all Account Holder
				try(AccountHolderDao dao = new AccountHolderDao()) {
					Set<AccountHolder> col =  dao.displayAll();
					col.forEach(c -> System.out.println(c));
					
				}catch (Exception e) {
					e.printStackTrace();
				}
				break;

			case 3: //Edit name & DOB for given A/C no
				System.out.println("Enter the A/c no to edit:");
				int idMod = sc.nextInt();
				try(AccountHolderDao dao = new AccountHolderDao()) {
					int count = dao.editDobName(idMod);
					System.out.println("Rows Updated: " + count);
					
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				break;
			
			case 4: //Dsiplay Account Details for given A/C type
				System.out.println("Enter the A/c type to display:");
				int ac = sc.nextInt();
				try(AccountHolderDao dao = new AccountHolderDao()) {
					Set<AccountHolder> list= dao.displayAccGivenType(ac);
					list.forEach(c -> System.out.println(c));
					
				}catch (Exception e) {
					e.printStackTrace();
				}
				break;
			
			case 5: //DElete Account Holder for given A/C no
				System.out.println("Enter the A/c no to delete:");
				int accToDel = sc.nextInt();
				try(AccountHolderDao dao = new AccountHolderDao()) {
					int count = dao.deleteAcc(accToDel);
					System.out.println("Rows Updated: " + count);
					
				}catch (Exception e) {
					e.printStackTrace();
				}
				break;
			
			case 6: //Display account type wise total number of Account Holders
				try(AccountHolderDao dao = new AccountHolderDao()) {
					Set<AccountType> list = dao.displayAccTypeWise();
						list.forEach(c -> System.out.println(c));
					
				}catch (Exception e) {
					e.printStackTrace();
				}
				break;
			
			case 7: //Sort Account Balance
				try(AccountHolderDao dao = new AccountHolderDao()) {
					Set<AccountHolder> list = dao.sortAccBal();
					List<AccountHolder> t = new ArrayList<AccountHolder>(list); 
					Collections.sort(t, new salDescComparator());
					for(AccountHolder a : t)
						System.out.println(a);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				break;
			
			case 8: //EXIT
				System.out.println("Thankyou for using the dashboard!");
				System.exit(0);
				break;
			
			default: System.out.println("Entered the incorrect choice!");
				break;
			}
		}
		
	}
}

