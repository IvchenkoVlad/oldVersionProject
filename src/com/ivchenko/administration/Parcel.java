package com.ivchenko.administration;

import java.sql.Date;

public class Parcel {
	
	private int parcelID;
	private String trackingNumber;
	private Date receivedDate;
	private Date releaseDate;
	private String status;
	private String sender;
	private int customerFK;
	private int userFK;
	
	public Parcel(int parcelID, String trackingNumber, Date receivedDate, Date releaseDate, String status,
			String sender, int customerFK, int userFK) {
		super();
		this.parcelID = parcelID;
		this.trackingNumber = trackingNumber;
		this.receivedDate = receivedDate;
		this.releaseDate = releaseDate;
		this.status = status;
		this.sender = sender;
		this.customerFK = customerFK;
		this.userFK = userFK;
	}

	public Parcel(String trackingNuber, int userFK) { //userFK here used to represent pmb number in the form
		super();
		this.trackingNumber = trackingNuber;
		this.userFK = userFK;
	}

	public Parcel(String trackingNumber, String status) {
		super();
		this.trackingNumber = trackingNumber;
		this.status = status;
	}

	public int getParcelID() {
		return parcelID;
	}

	public void setParcelID(int parcelID) {
		this.parcelID = parcelID;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public int getCustomerFK() {
		return customerFK;
	}

	public void setCustomerFK(int customerFK) {
		this.customerFK = customerFK;
	}

	public int getUserFK() {
		return userFK;
	}

	public void setUserFK(int userFK) {
		this.userFK = userFK;
	}

	@Override
	public String toString() {
		return "Parcel [parcelID=" + parcelID + ", trackingNumber=" + trackingNumber + ", receivedDate=" + receivedDate
				+ ", releaseDate=" + releaseDate + ", status=" + status + ", sender=" + sender + ", customerFK="
				+ customerFK + ", userFK=" + userFK + "]";
	}
	
	

}
