package com.trade.store.controller

import java.awt.PageAttributes.MediaType
import java.time.LocalDate

import javax.sql.DataSource

import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

import com.trade.store.dao.ITradeStoreDao
import com.trade.store.model.TradeStore
import com.trade.store.model.TradeStoreResponse
import com.trade.store.service.ITradeStoreService

import groovy.json.JsonSlurper
import spock.lang.Specification

@ActiveProfiles("test")
@WebMvcTest(controllers = TradeStoreController.class)
@ComponentScan(lazyInit=true,basePackages=["com.trade.store"])
@SpringBootTest
class TradeStoreControllerTest extends Specification{

	@Autowired
	MockMvc = mvc
	
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

	def"Test case for save api"(){
		given :
		def requestUrl = getClass().getResource("/InputJson/saveQueryInput.json")
		def requestFile = new File(requestUrl.toURI())

		TradeStoreResponse response = new TradeStoreResponse();
		response.setId(1);
		response.setMessage("Data saved successfully!!!");
		
		TradeStore existingTradeStore = new TradeStore()
		existingTradeStore.setTradeId("t2")
		existingTradeStore.setVersion("1")
		existingTradeStore.setCounterPartyId("cp1")
		existingTradeStore.setBookId("b1")
		existingTradeStore.setExpiry("N")
		existingTradeStore.setMaturityDate(new LocalDate().plusDays(1))
	
		iTradeStoreDao.save(*_) >> 1
		iTradeStoreService.save(*_) >> responses
		iTradeStoreDao.findByTradeId(*_) >> existingTradeStore
		
		expect :
		def httpRespose = mvc.perform(MockMvcRequestBuilders.post("/api/save")
								.contentType(MediaType.APPLICATION_JSON)
								.content(requestFile.text))
								.andExpect(MockMvcResultMatchers.status().isOk())
								.andReturn().response
			
		def httpResponseObject = getJsonObect(httpRespose.contentAsString)
		httpResponseObject != null
		
	}
	
	def"Test case for save api with 4xx exception"(){
		given :
		def requestUrl = getClass().getResource("/InputJson/saveQueryInput.json")
		def requestFile = new File(requestUrl.toURI())

		expect :
		def httpRespose = mvc.perform(MockMvcRequestBuilders.post("/api/save_1")
								.contentType(MediaType.APPLICATION_JSON)
								.content(requestFile.text))
								.andExpect(MockMvcResultMatchers.status().is4xxClientError())
								.andReturn().response
			
		def httpResponseObject = getJsonObect(httpRespose.contentAsString)
		httpResponseObject != null
		
	}

	
	def"Test case for save api with 5xx exception"(){
		given :
		def requestUrl = getClass().getResource("/InputJson/saveQueryInput.json")
		def requestFile = new File(requestUrl.toURI())

		iTradeStoreService.save(*_) >> {
			throw new Exception()
		}
		
		expect :
		def httpRespose = mvc.perform(MockMvcRequestBuilders.post("/api/save")
								.contentType(MediaType.APPLICATION_JSON)
								.content(requestFile.text))
								.andExpect(MockMvcResultMatchers.status().is4xxClientError())
								.andReturn().response
			
		def httpResponseObject = getJsonObect(httpRespose.contentAsString)
		httpResponseObject != null
		
	}

	
	def"getJsonObect"(def jsonContent){
		def jsonParser = new JsonSlurper()
		return jsonParser.parseText(jsonContent)
	}
}
