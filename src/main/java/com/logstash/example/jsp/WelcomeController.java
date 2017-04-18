/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.logstash.example.jsp;

import com.logstash.example.jsp.logging.LogData;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class WelcomeController {
	public static final Logger LOGGER = getLogger(WelcomeController.class);

	@Value("${application.message:Hello World}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model, HttpServletRequest request) {
		try {
			for (int i = 0; i < 10; i++) {
				LogData logData = new LogData(UUID.randomUUID().toString(),i,getHeaders(request),"Main body - ᚠᛇᚻ᛫ᛒᛦᚦ᛫ᚠᚱᚩᚠᚢᚱ᛫ᚠᛁᚱᚪ᛫ᚷᛖᚻᚹᛦᛚᚳᚢᛗ\n" +
						"ᛋᚳᛖᚪᛚ᛫ᚦᛖᚪᚻ᛫ᛗᚪᚾᚾᚪ᛫ᚷᛖᚻᚹᛦᛚᚳ᛫ᛗᛁᚳᛚᚢᚾ᛫ᚻᛦᛏ᛫ᛞᚫᛚᚪᚾ\n" +
						"ᚷᛁᚠ᛫ᚻᛖ᛫ᚹᛁᛚᛖ᛫ᚠᚩᚱ᛫ᛞᚱᛁᚻᛏᚾᛖ᛫ᛞᚩᛗᛖᛋ᛫ᚻᛚᛇᛏᚪᚾ᛬" + RandomStringUtils.randomNumeric(10000));
				LOGGER.info(logData.toString());
			}

			model.put("time", new Date());
			model.put("message", this.message);
		}catch (Exception e){
			LogData logData = new LogData(UUID.randomUUID().toString(),500,getHeaders(request),"Exception while accessing welcome page :" + ExceptionUtils.getFullStackTrace(e));
			LOGGER.error(logData.toString());
		}
		return "welcome";
	}

	public Map<String, String> getHeaders(HttpServletRequest req){

		Enumeration<String> headerNames = req.getHeaderNames();
		Map<String,String> headerMap = new HashMap<>();
		while (headerNames.hasMoreElements()) {

			String headerName = headerNames.nextElement();


			Enumeration<String> headers = req.getHeaders(headerName);
			List<String> headerValues = new ArrayList<>();
			while (headers.hasMoreElements()) {
				String headerValue = headers.nextElement();
				headerValues.add(headerValue);
			}
			headerMap.put(headerName,headerValues.toString());
		}
		return headerMap;
	}

}
