package com.nhnacademy.thread.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
class RequestHandlerTest {

    @Test
    @DisplayName("RequestChannel = null")
    void constructorTest(){
        //TODO#8-3-5 RequestHandler 객체생성시 channel이 null 이면 IllegalArgumentException 발생하는지 검증합니다.
        Assertions.assertThrows(IllegalArgumentException.class,()->{
           new RequestHandler(null);
        });
    }

    @Test
    @DisplayName("producer & consumer")
    void run(){
        //TODO#8-3-6 requestChannel과 requestHandler 객체를 생성합니다.
        RequestChannel requestChannel = new RequestChannel();
        RequestHandler requestHandler = new RequestHandler(requestChannel);

        AtomicInteger couter = new AtomicInteger();

        //TODO#8-3-7  couter.incrementAndGet(); 호출하는 countExecutable 구현 합니다.
        Executable countExecutable = new Executable() {
            @Override
            public void execute() throws InterruptedException {
                int count = couter.incrementAndGet();
                log.debug("count:{}",count);
            }
        };

        //TODO#8-3-8 생산자 requestChannel 실행할 작업을 1초에 한 번씩 총 5회 추가 합니다.
        Thread producer = new Thread(()->{
            for(int i=1; i<=5; i++){
                try {
                    Thread.sleep(1000);
                    requestChannel.addRequest(countExecutable);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        producer.start();

        //TODO#8-3-9 requestHandler를 이용해서 consumer thread를 생성하고 실행 합니다.
        Thread consumer= new Thread(requestHandler);
        consumer.start();

        //TODO#8-3-10 producer(생산자)의 작업이 끝나지 않았다면 테스트를 싱행하는 main Thread는 양보(대기) 합니다.
        while (producer.isAlive()){
            Thread.yield();
        }

        log.debug("counter:{}", couter.get());

        Assertions.assertEquals(5,couter.get());
    }

}