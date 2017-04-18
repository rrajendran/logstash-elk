package com.logstash.example.jsp;

import org.junit.Test;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ramesh Rajendran
 */
public class LogAggregationTests {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);


    @Test
    public void test() throws InterruptedException {
        List<WebserviceCall> tasks = new ArrayList<>();
        tasks.add(new WebserviceCall("10005001", "1001"));
        tasks.add(new WebserviceCall("10005002", "1001"));
        tasks.add(new WebserviceCall("10005003", "1001"));
        tasks.add(new WebserviceCall("10005004", "1001"));
        tasks.add(new WebserviceCall("800101", "1001"));
        tasks.add(new WebserviceCall("800102", "1001"));
        tasks.add(new WebserviceCall("800103", "1001"));

        executorService.invokeAll(tasks);

    }


    public class WebserviceCall implements Callable<Integer>{
        private String serviceDeliveryId;
        private String activityId;


        public WebserviceCall(String serviceDeliveryId, String activityId) {
            this.serviceDeliveryId = serviceDeliveryId;
            this.activityId = activityId;
        }



        @Override
        public Integer call() throws Exception {
            System.out.println("Calling " + this.serviceDeliveryId);
            Response response = ClientBuilder
                    .newClient()
                    .target("http://localhost:8081/ipt-ms-decision-making-webui")
                    .path("/decisions/{serviceDeliveryId}/{activityId}")
                    .resolveTemplate("serviceDeliveryId", this.serviceDeliveryId)
                    .resolveTemplate("activityId", this.activityId)
                    .request(MediaType.APPLICATION_JSON).get();
        return response.getStatus();
          /*  final IPTResponse iptResponse = httpClient.get(anIPTRequest()
                    .url("http://localhost:8081/ipt-ms-decision-making-webui")
                    .path("/decisions/{serviceDeliveryId}/{activityId}")
                    .header("Content-type", APPLICATION_JSON)
                    .build(this.serviceDeliveryId, this.activityId));
            return iptResponse.getStatusCode();*/

        }
    }
}
