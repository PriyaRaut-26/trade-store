package com.trade.store.service.impl

import java.time.LocalDate

import javax.sql.DataSource

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles

import com.trade.store.controller.TradeStoreController
import com.trade.store.dao.ITradeStoreDao
import com.trade.store.exception.InvalidInputReceivedException
import com.trade.store.model.TradeStore
import com.trade.store.model.TradeStoreResponse
import com.trade.store.service.ITradeStoreService

import spock.lang.Specification

@ActiveProfiles("test")
@WebMvcTest(controllers = TradeStoreController.class)
@ComponentScan(lazyInit=true,basePackages=["com.trade.store"])
@SpringBootTest
class TradeStoreServiceImplTest extends Specification {

	@SpringBean
	ITradeStoreService iTradeStoreService = Mock(iTradeStoreService.class)
	
	@SpringBean
	ITradeStoreDao iTradeStoreDao = Mock(iTradeStoreDao.class)
	
	@SpringBean
	@Qualifier("dataSource")
	DataSource dataSource = Mock(DataSource.class)
	
	@SpringBean
	@Qualifier("h2JdbcTemplate")
	JdbcTemplate jdbcTemplate = Mock(JdbcTemplate.class)

	def"Test case to save trade store"() {
		given :
		TradeStore tradeStore = new TradeStore()
		tradeStore.setTradeId("t1")
		tradeStore.setVersion("2")
		tradeStore.setCounterPartyId("cp1")
		tradeStore.setBookId("b1")
		tradeStore.setExpiry("N")
		tradeStore.setMaturityDate(new LocalDate().plusDays(1))
	
		TradeStore existingTradeStore = new TradeStore()
		existingTradeStore.setTradeId("t2")
		existingTradeStore.setVersion("1")
		existingTradeStore.setCounterPartyId("cp1")
		existingTradeStore.setBookId("b1")
		existingTradeStore.setExpiry("N")
		existingTradeStore.setMaturityDate(new LocalDate().plusDays(1))
	
		TradeStoreResponse response = new TradeStoreResponse();
		response.setId(1);
		response.setMessage("Data saved successfully!!!");
		
		iTradeStoreService.save(*_) >> response
		iTradeStoreDao.save(*_) >> 1
		iTradeStoreDao.findByTradeId(*_) >> existingTradeStore
		
		
		when :
		def res = iTradeStoreService.save(tradeStore)
		
		then :
		res != null
		res.getId() == 1 
		res.getMessage() == "Data saved successfully!!!"
	}	

	def"Test case to save data with trade store version validation"() {
		given :
		TradeStore tradeStore = new TradeStore()
		tradeStore.setTradeId("t1")
		tradeStore.setVersion("2")
		tradeStore.setCounterPartyId("cp1")
		tradeStore.setBookId("b1")
		tradeStore.setExpiry("N")
		tradeStore.setMaturityDate(new LocalDate().plusDays(1))
	
		TradeStore existingTradeStore = new TradeStore()
		existingTradeStore.setTradeId("t1")
		existingTradeStore.setVersion("1")
		existingTradeStore.setCounterPartyId("cp1")
		existingTradeStore.setBookId("b1")
		existingTradeStore.setExpiry("N")
		existingTradeStore.setMaturityDate(new LocalDate().plusDays(1))
	
		iTradeStoreDao.findByTradeId(*_) >> existingTradeStore
		
		when :
		def res = iTradeStoreService.save(tradeStore)
		
		then :
		def error = thrown(InvalidInputReceivedException)
		error.getMessage() == "Incorrect version received"
	}
	
	def"Test case to save data with incorrect maturity date"() {
		given :
		TradeStore tradeStore = new TradeStore()
		tradeStore.setTradeId("t1")
		tradeStore.setVersion("2")
		tradeStore.setCounterPartyId("cp1")
		tradeStore.setBookId("b1")
		tradeStore.setExpiry("N")
		tradeStore.setMaturityDate(new LocalDate().minusDays(1))
			
		when :
		def res = iTradeStoreService.save(tradeStore)
		
		then :
		def error = thrown(InvalidInputReceivedException)
		error.getMessage() == "Incorrect maturity date received"
	}

}
