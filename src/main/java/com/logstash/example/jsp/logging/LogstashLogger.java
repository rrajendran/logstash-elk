package com.logstash.example.jsp.logging;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Ramesh Rajendran
 */
public class LogstashLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger("logstash");
    //public static final Logger LOGGER = getLogger(LogstashLogger.class);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        AtomicInteger count = new AtomicInteger(1);


        try {

            Callable<String> callableTask = () -> {
                Map<String, String> headers = ImmutableMap.of("a", "aa", "b", "bb", "c", "cc");
                LogData logData = new LogData(UUID.randomUUID().toString(),count.getAndIncrement(),headers,"Main body - ᚠᛇᚻ᛫ᛒᛦᚦ᛫ᚠᚱᚩᚠᚢᚱ᛫ᚠᛁᚱᚪ᛫ᚷᛖᚻᚹᛦᛚᚳᚢᛗ\n" +
                        "ᛋᚳᛖᚪᛚ᛫ᚦᛖᚪᚻ᛫ᛗᚪᚾᚾᚪ᛫ᚷᛖᚻᚹᛦᛚᚳ᛫ᛗᛁᚳᛚᚢᚾ᛫ᚻᛦᛏ᛫ᛞᚫᛚᚪᚾ\n" +
                        "ᚷᛁᚠ᛫ᚻᛖ᛫ᚹᛁᛚᛖ᛫ᚠᚩᚱ᛫ᛞᚱᛁᚻᛏᚾᛖ᛫ᛞᚩᛗᛖᛋ᛫ᚻᛚᛇᛏᚪᚾ᛬");

                LOGGER.info(logData.toString());
                return null;
            };

            List<Callable<String>> callableTasks = new ArrayList<>();


           while(true) {
               int i;
               for (i = 0; i < 10; i++) {
                   callableTasks.add(callableTask);
               }
               executorService.invokeAll(callableTasks);
               System.out.println("");
               System.out.println("");
               Thread.sleep(5000);
           }
        } catch(Exception ex) {
            LOGGER.error("Error = " , ex);
        }finally {
            executorService.shutdown();
        }
    }
}
