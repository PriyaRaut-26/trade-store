package com.trade.store.dao;

import java.util.List;

import com.trade.store.model.TradeStore;

public interface ITradeStoreDao {
	List<TradeStore> getAllTradeStores();
	
	TradeStore getTradeStoreById(int id);
	
	Integer save(TradeStore tradeStore);
	
	TradeStore findByTradeId(String tradeId);
	
	void update(TradeStore tradeStore);

	void updateExpiryFlag(TradeStore tradeStore);
}
