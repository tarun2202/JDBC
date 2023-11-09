package com.sunbeam;

import java.util.Date;

public class AccountHolder {
	private int accno;
	private String name;
	private String email;
	private Date dob;
	private double balance;
	private int accId;
	
	public AccountHolder() {
		// TODO Auto-generated constructor stub
	}

	public AccountHolder(int accno, String name, String email, Date dob, double balance, int accId) {
		super();
		this.accno = accno;
		this.name = name;
		this.email = email;
		this.dob = dob;
		this.balance = balance;
		this.accId = accId;
	}

	public int getAccno() {
		return accno;
	}

	public void setAccno(int accno) {
		this.accno = accno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getAccId() {
		return accId;
	}

	public void setAccId(int accId) {
		this.accId = accId;
	}

	@Override
	public String toString() {
		return "AccountHolder [accno=" + accno + ", name=" + name + ", email=" + email + ", dob=" + dob + ", balance="
				+ balance + ", accId=" + accId + "]";
	}
	
	
}
