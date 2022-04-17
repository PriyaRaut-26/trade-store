package com.trade.store.dao.impl;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.trade.store.config.H2DataSourceConfig;
import com.trade.store.constant.QueryConstants;
import com.trade.store.dao.ITradeStoreDao;
import com.trade.store.exception.RecordNotFoundException;
import com.trade.store.mapper.TradeStoreMapper;
import com.trade.store.model.TradeStore;

@Repository
public class TradeStoreDaoImpl implements ITradeStoreDao {

	Logger logger = LogManager.getLogger(TradeStoreDaoImpl.class);

	@Autowired
	H2DataSourceConfig h2DataSourceConfig;

	@Override
	public List<TradeStore> getAllTradeStores() {
		return h2DataSourceConfig.getH2JdbcTemplate().query(QueryConstants.GET_ALL_QUERY, new TradeStoreMapper());
	}

	@SuppressWarnings("deprecation")
	@Override
	public TradeStore getTradeStoreById(int id) {
		TradeStore tradeStore = null;
		try {
			tradeStore = h2DataSourceConfig.getH2JdbcTemplate().queryForObject(QueryConstants.GET_QUERY,
					new Object[] { id }, new TradeStoreMapper());

		} catch (EmptyResultDataAccessException e) {
			throw new RecordNotFoundException("Record not found in database");
		} catch (DataAccessException e) {
			throw new QueryTimeoutException("Exception in dao ", e);
		} catch (Exception e) {
			throw e;
		}
		return tradeStore;
	}

	@Override
	public Integer save(TradeStore tradeStore) {
		KeyHolder keyHolder = null;
		try {
			keyHolder = new GeneratedKeyHolder();
			h2DataSourceConfig.getH2JdbcTemplate().update(connection -> {
				PreparedStatement ps = connection.prepareStatement(QueryConstants.SAVE_QUERY,
						new String[] { "id" });
				ps.setString(1, tradeStore.getTradeId());
				ps.setInt(2, tradeStore.getVersion());
				ps.setString(3, tradeStore.getCounterPartyId());
				ps.setString(4, tradeStore.getBookId());
				ps.setObject(5, tradeStore.getMaturityDate());
				ps.setObject(6, LocalDate.now());
				ps.setString(7, tradeStore.getExpiry());
				return ps;
			}, keyHolder);
			logger.info("Data saved successfully !!!");

		} catch (DataAccessException e) {
			throw new QueryTimeoutException("Exception in dao ", e);
		} catch (Exception e) {
			throw e;
		}
		if (keyHolder.getKeyList().isEmpty()) {
			return 0;
		}
		return keyHolder.getKey().intValue();
	}

	@SuppressWarnings("deprecation")
	@Override
	public TradeStore findByTradeId(String tradeId) {
		TradeStore tradeStore = null;
		try {
			tradeStore = h2DataSourceConfig.getH2JdbcTemplate().queryForObject(
					QueryConstants.FIND_BY_TRADE_ID_QUERY, new Object[] { tradeId }, new TradeStoreMapper());

		} catch (EmptyResultDataAccessException e) {
		} catch (DataAccessException e) {
			throw new QueryTimeoutException("Exception in dao ", e);
		} catch (Exception e) {
			throw e;
		}
		return tradeStore;
	}

	@Override
	public void update(TradeStore tradeStore) {
		try {
			h2DataSourceConfig.getH2JdbcTemplate().update(QueryConstants.UPDATE_QUERY, ps -> {
				ps.setInt(1, tradeStore.getVersion());
				ps.setString(2, tradeStore.getCounterPartyId());
				ps.setString(3, tradeStore.getBookId());
				ps.setObject(4, tradeStore.getMaturityDate());
				ps.setObject(5, LocalDate.now());
				ps.setString(6, tradeStore.getExpiry());
				ps.setInt(7, tradeStore.getId());
				ps.setString(8, tradeStore.getTradeId());
			});
			logger.info("Data updated successfully !!!");

		} catch (DataAccessException e) {
			throw new QueryTimeoutException("Exception in dao ", e);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void updateExpiryFlag(TradeStore tradeStore) {

		try {
			h2DataSourceConfig.getH2JdbcTemplate().update(QueryConstants.EXPIRY_FLAG_UPDATE_QUERY, ps -> {
				ps.setString(1, tradeStore.getExpiry());
				ps.setInt(2, tradeStore.getId());
				ps.setString(3, tradeStore.getTradeId());
			});
			logger.info("Data updated successfully !!!");

		} catch (DataAccessException e) {
			throw new QueryTimeoutException("Exception in dao ", e);
		} catch (Exception e) {
			throw e;
		}

	}

}
