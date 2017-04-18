package com.logstash.example.jsp.logging;

import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ramesh Rajendran
 */
public class LogData implements Comparable<LogData> {
    private String requestId;
    private Integer responseCode;
    private Map<String, String> headers = new HashMap<>();
    private String responseBody;

    public LogData(String requestId, Integer responseCode, Map<String, String> headers, String responseBody) {
        this.requestId = requestId;
        this.responseCode = responseCode;
        this.headers = headers;
        this.responseBody = responseBody;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {

        this.requestId = requestId;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {

        this.responseCode = responseCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        MDC.put("headers", headers.toString());
        this.headers = headers;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        MDC.put("responseBody", responseBody);
        this.responseBody = responseBody;
    }

    @Override
    public String toString() {
        MDC.put("responseCode", String.valueOf(responseCode));
        MDC.put("requestId", requestId);
        return String.format("%s,%s,%s,%s,", requestId,headers, responseCode, responseBody);
    }

    @Override
    public int compareTo(LogData o) {
        return this.responseCode.compareTo(o.responseCode);
    }
}
