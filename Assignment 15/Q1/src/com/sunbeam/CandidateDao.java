package com.sunbeam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
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
 */

public class CandidateDao implements AutoCloseable{
	private Connection con;
	private PreparedStatement insertStmt;
	private PreparedStatement displayAllStmt;
	private PreparedStatement incrementVoteByIDStmt;
	private PreparedStatement deleteByIDStmt;
	private PreparedStatement findByIDStmt;
	private PreparedStatement findByPartyStmt;
	private PreparedStatement displayTotVoteByPartyStmt;
	
	public CandidateDao() throws Exception {
		con = DbUtil.getConnection();
		
		String q1 = "INSERT into candidates VALUES (default, ?, ?, ?)";
		insertStmt = con.prepareStatement(q1);
		
		String q2 = "SELECT * FROM candidates";
		displayAllStmt = con.prepareStatement(q2);
		
		String q3 = "UPDATE candidates SET votes = votes + 1 WHERE id = ?";
		incrementVoteByIDStmt = con.prepareStatement(q3);
		
		String q4 = "DELETE FROM candidates WHERE id = ?";
		deleteByIDStmt = con.prepareStatement(q4);
		
		String q5 = "SELECT * FROM candidates WHERE id = ?";
		findByIDStmt = con.prepareStatement(q5);
		
		String q6 = "SELECT * FROM candidates WHERE party = ?";
		findByPartyStmt = con.prepareStatement(q6);
		
		String q7 = "SELECT party, SUM(votes) total_votes FROM candidates GROUP BY party";
		displayTotVoteByPartyStmt = con.prepareStatement(q7);
		
	}
	public void close() {
		try {
			if(displayTotVoteByPartyStmt != null)
				displayTotVoteByPartyStmt.close();
			
			if(findByPartyStmt != null)
				findByPartyStmt.close();
			
			if(findByIDStmt != null)
				findByIDStmt.close();
			
			if(deleteByIDStmt != null)
				deleteByIDStmt.close();
			
			if(incrementVoteByIDStmt != null)
				incrementVoteByIDStmt.close();
			
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
	
	//case 1: insertStmt
	public int insert(Candidate c) throws Exception {
		//String q1 = "INSERT INTO candidates VALUES (default, ? , ?, ?)"; //parameterized query
		insertStmt.setString(1, c.getName());
		insertStmt.setString(2, c.getParty());
		insertStmt.setInt(3, c.getVotes());
			
		int count = insertStmt.executeUpdate();
		return count;
	}
	
	// case 2: Display all candidates
	public List<Candidate> displayAll() throws Exception {
		List<Candidate> list = new ArrayList<Candidate>();
		try(ResultSet rs = displayAllStmt.executeQuery()){
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String party = rs.getString("party");
				int vote = rs.getInt("votes");
				Candidate c = new Candidate(id, name, party, vote);
				list.add(c);
			}
		}
		return list;
	}
	
	//case 3: Increment votes by candidate ID
	public int incrementVoteByID(int updateVotes) throws Exception {
		//String q3 = "UPDATE candidates SET votes = votes + 1 WHERE id = ?";
		incrementVoteByIDStmt.setInt(1, updateVotes);
		int count = incrementVoteByIDStmt.executeUpdate();
		return count;
	}
	
	//case 4: Delete candidate by ID
	public int deleteById(int id) throws Exception {
		// String q4 = "DELETE FROM candidate WHERE id = ?";
		deleteByIDStmt.setInt(1,id);
		int count = deleteByIDStmt.executeUpdate();
		return count;
	}
	
	//case 5: Find candidate by ID
	public Candidate findById(int id) throws Exception {
		// String q5 = "SELECT * FROM candidates WHERE id = ?";
		findByIDStmt.setInt(1, id);
		try(ResultSet rs = findByIDStmt.executeQuery()){
			if(rs.next()) {
				int candId =rs.getInt("id");
				String cname = rs.getString("name");
				String cparty = rs.getString("party");
				int cvotes = rs.getInt("votes");
				Candidate c = new Candidate(candId, cname, cparty, cvotes);
				return c;
			}
		}
		return null;
	}
	
	//CASE 6: Find candidate by party name
	public List<Candidate> findByParty(String party) throws Exception {
		List<Candidate> list = new ArrayList<Candidate>();
		//String q6 = "SELECT * FROM candidates WHERE party = ?";
		findByPartyStmt.setString(1, party);
		try(ResultSet rs = findByPartyStmt.executeQuery()) {
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String cparty = rs.getString("party");
				int votes = rs.getInt("votes");
				Candidate c = new Candidate(id, name, cparty, votes);
				list.add(c);
			}
			return list;
		} // findByPartyStmt.close();
		
	}
	
	//case 7: Display party total votes by party name
	public List<PartyVoter> displayTotVoteByParty() throws Exception{
		List<PartyVoter> list = new ArrayList<PartyVoter>();
		// String q7 = "SELECT party, SUM(votes) total_votes FROM candidates GROUP BY party";
		try(ResultSet rs = displayTotVoteByPartyStmt.executeQuery()){
			while(rs.next()) {
				String party = rs.getString("party");
				int total = rs.getInt("total_votes");
				PartyVoter p = new PartyVoter(total, party);
				list.add(p);
			}
			return list;
		} //displayTotVoteByPartyStmt.close()
	}
	
}
