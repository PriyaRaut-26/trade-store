package com.trade.store.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import com.trade.store.model.TradeStore;

public class CommonUtils {

	public static boolean validateMaturityDate(TradeStore tradeStore) {
		if (!Objects.isNull(tradeStore.getMaturityDate())) {
			LocalDate currentDate = LocalDate.now();
			Date date = new Date(tradeStore.getMaturityDate().getTime());
			LocalDate tradeStoreDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			if (tradeStoreDate.isBefore(currentDate)) {
				return true;
			}
		}
		return false;
	}

}
