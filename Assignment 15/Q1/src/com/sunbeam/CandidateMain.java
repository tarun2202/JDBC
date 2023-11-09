package com.sunbeam;

import java.util.List;
import java.util.Scanner;

public class CandidateMain {

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
	
	public static Candidate accept(Scanner sc) {
//		System.out.println("Enter id: ");
//		int id = sc.nextInt();
		System.out.println("Enter name : ");
		String name = sc.next();
		System.out.println("Enter party: ");
		String party = sc.next();
		System.out.println("Enter votes : ");
		int votes = sc.nextInt();
		Candidate c = new Candidate(0, name, party, votes);
		return c;
	}
	
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);		
		while(true) {
			menu();
			System.out.print("Enter your choice : ");
			int choice = sc.nextInt();
			switch(choice) {
				case 1: //Insert Employee
						try(CandidateDao dao = new CandidateDao()) {
							Candidate c = accept(sc);
							int count = dao.insert(c);
							System.out.println("Rows Updated: " + count);
						} // dao.close();
						catch (Exception e) {
							e.printStackTrace();
						}
						break;
					
				case 2: //Display all Employee
						try(CandidateDao dao = new CandidateDao()) {
							List<Candidate> list = dao.displayAll();
							list.forEach(c -> System.out.println(c));
						} // dao.close();
						catch (Exception e) {
							e.printStackTrace();
						}
					
						break;
					
				case 3: //Increment votes of candidate with given ID
						System.out.println("Enter the id: ");
						int idToIncVote = sc.nextInt();
						try(CandidateDao dao = new CandidateDao())  {
							int rowUp = dao.incrementVoteByID(idToIncVote);
							System.out.println("ROws updated : "+ rowUp);
						}
						catch(Exception ex) {
							ex.printStackTrace();
						}
						
						break;
					
				case 4: //Delete candidate with given ID
					System.out.println("Enter the candidate id : ");
					int idToDelete = sc.nextInt();
					try(CandidateDao dao = new CandidateDao()){
						int rupdate = dao.deleteById(idToDelete);
						System.out.println("Rows affected : "+rupdate);
					}
					catch(Exception e) {
						e.printStackTrace();
					}
						
					break;
					
				case 5: //Find candidate of the given ID
						System.out.println("Enter candidate id: ");
						int idToFind = sc.nextInt();
						try(CandidateDao dao = new CandidateDao()){
							Candidate can = new Candidate();
							can = dao.findById(idToFind);
							System.out.println(can);
						}
						catch(Exception e) {
							e.printStackTrace();
						}
								
					break;
					
				case 6: //Find the Candidate of given party
						try(CandidateDao dao = new CandidateDao()) {
							System.out.print("Enter party: ");
							String party = sc.next();
							List<Candidate> list = dao.findByParty(party);
							list.forEach(c -> System.out.println(c));
						} // dao.close();
						catch (Exception e) {
							e.printStackTrace();
						}
						
						break;
					
				case 7: //Display total votes for each party
						try(CandidateDao dao = new CandidateDao()){
							List<PartyVoter> list = dao.displayTotVoteByParty();
							list.forEach(c-> System.out.println(c));
						}
						catch(Exception e) {
							e.printStackTrace();
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
	}	
}


