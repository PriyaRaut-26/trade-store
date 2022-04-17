package com.trade.store;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.trade.store.dao.ITradeStoreDao;
import com.trade.store.model.TradeStore;
import com.trade.store.util.CommonUtils;

@SpringBootApplication
@EnableScheduling
public class TradeStoreDemoApplication {

	Logger logger = LogManager.getLogger(TradeStoreDemoApplication.class);
	
	@Autowired
	ITradeStoreDao iTradeStoreDao;
		
	public static void main(String[] args) {
		SpringApplication.run(TradeStoreDemoApplication.class, args);
	}
	
	@Scheduled(cron = "0 22 * * * *")
	public void run() {
		logger.info("cron job started...");
		List<TradeStore> list = iTradeStoreDao.getAllTradeStores();
		list.forEach(trade -> {
			if(CommonUtils.validateMaturityDate(trade) && trade.getExpiry().equals("N")) {
				trade.setExpiry("Y");
				iTradeStoreDao.updateExpiryFlag(trade);
			}
		});
	}
}
