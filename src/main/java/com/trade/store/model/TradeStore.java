package com.trade.store.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.extern.jackson.Jacksonized;

@Jacksonized
public class TradeStore {
	@JsonIgnore
	private int id;
	private String tradeId;
	private Integer version;
	private String counterPartyId;
	private String bookId;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date maturityDate;
	@JsonIgnore
	private Date createdDate;
	private String expiry;
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getCounterPartyId() {
		return counterPartyId;
	}
	public void setCounterPartyId(String counterPartyId) {
		this.counterPartyId = counterPartyId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public Date getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "TradeStore [id=" + id + ", tradeId=" + tradeId + ", version=" + version + ", counterPartyId="
				+ counterPartyId + ", bookId=" + bookId + ", maturityDate=" + maturityDate + ", createdDate="
				+ createdDate + ", expiry=" + expiry + "]";
	}
	
	
	
	
	
}
