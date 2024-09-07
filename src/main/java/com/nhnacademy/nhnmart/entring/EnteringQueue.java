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

package com.nhnacademy.nhnmart.entring;

import com.nhnacademy.customer.domain.Customer;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

/**
 * NHNMart 입장 대기열을 구현 합니다.
 * 대기열은 최대 100명까지 가능 합니다.
 */

@Slf4j
public class EnteringQueue {

    //queue를 이용해서 mart 입장 대기열을 구현 합니다.
    private final Queue<Customer> queue;

    //기본 대기열 QueueSize = 100명
    private static final int DEFAULT_CAPACITY = 100;
    private final int capacity;
    public EnteringQueue(){
        //TODO#3-1 기본 기본생성자 구현, capacity = DEFAULT_CAPACITY 입니다.
        this(DEFAULT_CAPACITY);
    }

    public EnteringQueue(int capacity) {
        //TODO#3-2 capacity <=0 IllegalArgumentException이 발생 합니다.
        if(capacity <= 0){
            throw new IllegalArgumentException();
        }

        //TODO#3-3 capacity 와 queue를 초기화 합니다.
        this.capacity = capacity;
        queue = new LinkedList<>();
    }

    public synchronized void addCustomer(Customer customer){
        /*TODO#3-4 대기열에 고객을 추가하는 method를 구현 합니다.
           - queue.size() >= capacity 이면 대기할 수 있도록 구현합니다.
        */

        while(queue.size() >= capacity){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //TODO#3-5 queue에 고객을 추가하고 대기하고 있는 Thread를 깨웁니다.
        queue.add(customer);

        notifyAll();
    }

    public synchronized Customer getCustomer(){
        //TODO#3-6 queue가 비워져 있다면 대기 합니다.
        while (queue.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //TODO#3-7 queue 에서 customer를 반납 합니다, 대기하고 있던 thread를 깨웁니다.
        notifyAll();
        return queue.poll();
    }

    //TODO#3-8 queue size를 반환 합니다.
    public int getQueueSize(){
        return queue.size();
    }

}
