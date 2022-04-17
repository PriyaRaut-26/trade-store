package com.trade.store.constant;

public class QueryConstants {

	public static final String GET_QUERY = "select * from trade_store where id=?";
	
	public static final String GET_ALL_QUERY = "select * from trade_store";
	
	public static final String FIND_BY_TRADE_ID_QUERY = "select * from trade_store where trade_id=?";
	
	public static final String SAVE_QUERY = "INSERT INTO trade_store(trade_id,version,counter_party_id,book_id,maturity_date,create_date,expiry) VALUES (?,?,?,?,?,?,?) ";

	public static final String UPDATE_QUERY = "UPDATE trade_store SET version=? , counter_party_id = ? , book_id = ? , maturity_date=?, create_date =? , expiry=? WHERE id = ? AND trade_id=? ";

	public static final String EXPIRY_FLAG_UPDATE_QUERY = "UPDATE trade_store SET expiry=? WHERE id = ? AND trade_id=? ";

}
