package com.sunbeam;

public class PartyVoter {
	int vote;
	String party;
	
	public PartyVoter() {
		// TODO Auto-generated constructor stub
	}

	public PartyVoter(int vote, String party) {
		this.vote = vote;
		this.party = party;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	@Override
	public String toString() {
		return "PartyVotes [party=" + party + ", vote=" + vote + "]";
	}
	
	
}
