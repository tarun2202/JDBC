package com.sunbeam;


public class AccountType implements AutoCloseable{
	private int id;
	private int count;
	
	public AccountType() {
		// TODO Auto-generated constructor stub
	}

	public AccountType(int id, int count) {
		this.id = id;
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "AccountType [id=" + id + ", count=" + count + "]";
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}

