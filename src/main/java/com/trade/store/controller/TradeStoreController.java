package com.trade.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trade.store.exception.RecordNotFoundException;
import com.trade.store.model.TradeStore;
import com.trade.store.model.TradeStoreRequest;
import com.trade.store.model.TradeStoreResponse;
import com.trade.store.service.ITradeStoreService;

@RestController
@RequestMapping("/api")
public class TradeStoreController {

	@Autowired
	ITradeStoreService iTradeStoreService;
	
	  @GetMapping
	    public ResponseEntity<List<TradeStore>> getAllTradeStore() {
	        List<TradeStore> list = iTradeStoreService.getAllTradeStores();
	 
	        return new ResponseEntity<List<TradeStore>>(list, new HttpHeaders(), HttpStatus.OK);
	   }
	
	   @GetMapping("/{id}")
	    public ResponseEntity<TradeStore> getTradeStoreById(@PathVariable("id") int id)  {
		   TradeStore entity = iTradeStoreService.getTradeStoreById(id);
	 	        return new ResponseEntity<TradeStore>(entity, new HttpHeaders(), HttpStatus.OK);
	    }
	 
	   @PostMapping(value = "/save")
	   public ResponseEntity<TradeStoreResponse> saveData(@RequestBody TradeStore tradeStore){
		   TradeStoreResponse response = iTradeStoreService.save(tradeStore);
		   return new ResponseEntity<TradeStoreResponse>(response, new HttpHeaders(), HttpStatus.CREATED);   
	   }
}
