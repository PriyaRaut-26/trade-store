package com.trade.store.util

import java.time.LocalDate

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles

import com.trade.store.controller.TradeStoreController
import com.trade.store.model.TradeStore

import spock.lang.Specification


@ActiveProfiles("test")
@AutoConfigureMockMvc
@EnableAutoConfiguration
@SpringBootTest
class CommonUtilsTest extends Specification {
	
	def"validate maturity date to true"(){
		given :
		TradeStore tradeStore = new TradeStore()
		tradeStore.setMaturityDate(new LocalDate().minusDays(1))

		when :
		def res = CommonUtils.validateMaturityDate(tradeStore)
		
		then :
		res == true
						
	}

	def"validate maturity date to false"(){
		given :
		TradeStore tradeStore = new TradeStore()
		tradeStore.setMaturityDate(new LocalDate().plusDays(1))

		when :
		def res = CommonUtils.validateMaturityDate(tradeStore)
		
		then :
		res == false
						
	}
}
