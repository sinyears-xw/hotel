package com.jiuzhe.hotel.service;

import java.util.Map;

public interface AlipayService {

	Map getOrder(String outtradeno, double amount, String body, String subject, String notify_url, boolean credit_forbidden);
	Map doTrans(String params);
	boolean rsaCheck(Map<String, String> params);
}