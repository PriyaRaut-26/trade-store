package com.trade.store.service;

import java.util.List;

import com.trade.store.model.TradeStore;
import com.trade.store.model.TradeStoreResponse;


public interface ITradeStoreService {
	List<TradeStore> getAllTradeStores();
	TradeStore getTradeStoreById(int id);
	TradeStoreResponse save(TradeStore tradeStore);
}
