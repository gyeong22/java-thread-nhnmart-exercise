/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy.customer.generator;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.person.Person;
import com.nhnacademy.customer.domain.Customer;
import com.nhnacademy.nhnmart.entring.EnteringQueue;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 회원생성 후 대기열에 등록 합니다.
 */
@Slf4j
public class CustomerGenerator implements Runnable {

    //NhnMart 입장 대기열
    private final EnteringQueue enteringQueue;

    //회원 번호 Id 생성
    private final AtomicLong atomicId;

    //회원이 보유한 default money
    private final static int DEFAULT_MONEY=100_0000;

    public CustomerGenerator(EnteringQueue enteringQueue) {
        //enteringQueue null 이면 'IllegalArgumentException' 발생하는지 검증 합니다.
        if(Objects.isNull(enteringQueue)){
            throw new IllegalArgumentException("enteringQueue is null!");
        }

        //enteringQueue, atomicId 를 0으로 초기화 합니다.
        this.enteringQueue = enteringQueue;
        atomicId=new AtomicLong(0);

    }

    @Override
    public void run() {

        /*generate() method를 이용해서 customer를 생성하고 enteringQueue에 등록 합니다.
            - while 조건을 수정하세요.
        */
        while (!Thread.currentThread().isInterrupted()){
            try {
                //1초 간격으로 회원을 entringQueue의 대기열에 등록 합니다.
                Thread.sleep(1000);

                Customer customer = generate();
                enteringQueue.addCustomer(customer);
                log.debug("generate-customer:{}",customer);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private Customer generate(){

        /*Customer 객체를 생성 후 반환 합니다.
            - customer->id 는 atomicId를 사용하여 구현
            - 회원이름은 random으로 생성 됩니다.
               - 회원이름 생성시 https://github.com/Devskiller/jfairy 이용해서 구현 합니다.
         */

        Fairy fairy = Fairy.create();
        Person person = fairy.person();
        long id = atomicId.incrementAndGet();
        String name = person.getFullName();
        Customer customer = new Customer(id,name,DEFAULT_MONEY);
        return customer;
    }
}
