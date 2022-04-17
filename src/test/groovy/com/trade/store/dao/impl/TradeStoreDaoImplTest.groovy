package com.trade.store.dao.impl

import java.time.LocalDate

import javax.sql.DataSource

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.dao.QueryTimeoutException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles

import com.trade.store.controller.TradeStoreController
import com.trade.store.dao.ITradeStoreDao
import com.trade.store.model.TradeStore

import spock.lang.Specification

@ActiveProfiles("test")
@WebMvcTest(controllers = TradeStoreController.class)
@ComponentScan(lazyInit=true,basePackages=["com.trade.store"])
@SpringBootTest
class TradeStoreDaoImplTest extends Specification{

	@SpringBean
	ITradeStoreDao iTradeStoreDao = Mock(iTradeStoreDao.class)
	
	@SpringBean
	@Qualifier("dataSource")
	DataSource dataSource = Mock(DataSource.class)
	
	@SpringBean
	@Qualifier("h2JdbcTemplate")
	JdbcTemplate jdbcTemplate = Mock(JdbcTemplate.class)

	def"Test case to save data in database"(){
		given :
		TradeStore tradeStore = new TradeStore()
		tradeStore.setTradeId("t1")
		tradeStore.setVersion("2")
		tradeStore.setCounterPartyId("cp1")
		tradeStore.setBookId("b1")
		tradeStore.setExpiry("N")
		tradeStore.setMaturityDate(new LocalDate().plusDays(1))
		
		jdbcTemplate.save(*_) >> 1
		
		when :
		def res = iTradeStoreDao.save(tradeStore)
		
		then :
		res != null
		res == 1
		
		}
		
		def"Test case to save data with Data access exception"(){
			given :
			TradeStore tradeStore = new TradeStore()
			tradeStore.setTradeId("t1")
			tradeStore.setVersion("2")
			tradeStore.setCounterPartyId("cp1")
			tradeStore.setBookId("b1")
			tradeStore.setExpiry("N")
			tradeStore.setMaturityDate(new LocalDate().plusDays(1))
			iTradeStoreDao.save(*_) >> {
				throw new QueryTimeoutException() 
			}
			
			when :
			def res = iTradeStoreDao.save(tradeStore)
			
			then :
			thrown(QueryTimeoutException)			
			
			}
			
			def"Test case to save data with  exception"(){
				given :
				TradeStore tradeStore = new TradeStore()
				tradeStore.setTradeId("t1")
				tradeStore.setVersion("2")
				tradeStore.setCounterPartyId("cp1")
				tradeStore.setBookId("b1")
				tradeStore.setExpiry("N")
				tradeStore.setMaturityDate(new LocalDate().plusDays(1))
				iTradeStoreDao.save(*_) >> {
					throw new Exception()
				}
				
				when :
				def res = iTradeStoreDao.save(tradeStore)
				
				then :
				thrown(Exception)
				}
}
