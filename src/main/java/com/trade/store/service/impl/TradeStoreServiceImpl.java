package com.trade.store.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.store.dao.ITradeStoreDao;
import com.trade.store.exception.InvalidInputReceivedException;
import com.trade.store.model.TradeStore;
import com.trade.store.model.TradeStoreResponse;
import com.trade.store.service.ITradeStoreService;
import com.trade.store.util.CommonUtils;

@Service
public class TradeStoreServiceImpl implements ITradeStoreService {

	Logger logger = LogManager.getLogger(TradeStoreServiceImpl.class);

	@Autowired
	ITradeStoreDao iTradeStoreDao;

	@Override
	public List<TradeStore> getAllTradeStores() {
		return iTradeStoreDao.getAllTradeStores();
	}

	@Override
	public TradeStore getTradeStoreById(int id) {
		return iTradeStoreDao.getTradeStoreById(id);
	}

	@Override
	public TradeStoreResponse save(TradeStore tradeStore) {
		TradeStoreResponse response = null;
		Integer id = null;
		// validate maturity date
		validateRequest(tradeStore);
		TradeStore existingTradeStore = iTradeStoreDao.findByTradeId(tradeStore.getTradeId());
		if (null != existingTradeStore && tradeStore.getVersion() < existingTradeStore.getVersion()) {
			logger.error("Incorrect version received");
			throw new InvalidInputReceivedException("Incorrect version received");
		} else if (null != existingTradeStore && tradeStore.getVersion() == existingTradeStore.getVersion()) {
			tradeStore.setId(existingTradeStore.getId());
			iTradeStoreDao.update(tradeStore);
			response = new TradeStoreResponse();
			response.setId(tradeStore.getId());
			response.setMessage("Data saved successfully!!!");
		} else {
			id = iTradeStoreDao.save(tradeStore);

			if (id != 0) {
				response = new TradeStoreResponse();
				response.setId(id);
				response.setMessage("Data saved successfully!!!");
			}

		}

		return response;
	}

	public void validateRequest(TradeStore tradeStore) {
		if (CommonUtils.validateMaturityDate(tradeStore)) {
			logger.error("Incorrect maturity date received");
			throw new InvalidInputReceivedException("Incorrect maturity date received");
		}
	}

}
