package com.trade.store.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.trade.store.model.TradeStore;

public class TradeStoreMapper implements RowMapper<TradeStore> {

	@Override
	public TradeStore mapRow(ResultSet rs, int rowNum) throws SQLException {
		TradeStore tradeStore = new TradeStore();
		tradeStore.setId(rs.getInt("id"));
		tradeStore.setTradeId(rs.getString("trade_id"));
		tradeStore.setVersion(rs.getInt("version"));
		tradeStore.setCounterPartyId(rs.getString("counter_party_id"));
		tradeStore.setBookId(rs.getString("book_id"));
		tradeStore.setMaturityDate(rs.getDate("maturity_date"));
		tradeStore.setCreatedDate(rs.getDate("create_date"));
		tradeStore.setExpiry(rs.getString("expiry"));
		return tradeStore;
				
	}

}
