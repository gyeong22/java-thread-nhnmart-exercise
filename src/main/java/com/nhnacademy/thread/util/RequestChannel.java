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

package com.nhnacademy.thread.util;

import java.util.LinkedList;
import java.util.Queue;

public class RequestChannel {

    //Executable type의 Queue
    private final Queue<Executable> requestQueue;
    //기본 Queue Size = 10
    private static final long DEFAULT_QUEUE_SIZE = 10;
    // queue size
    private final long queueSize;

    public RequestChannel(){
        //기본생성자 - DEFAULT_QUEUE_SIZE 기반으로 QUEUE를 생성 합니다.
        this(DEFAULT_QUEUE_SIZE);
    }

    public RequestChannel(long queueSize) {
        //queueSize<0 이면 IllegalArgumentException 발생 합니다.
        if(queueSize<0){
            throw new IllegalArgumentException();
        }

        //queueSize, requestQueue를 초기화 합니다.
        this.queueSize = queueSize;
        this.requestQueue = new LinkedList<>();
    }

    public synchronized void addRequest(Executable executable){
        //while 조건을 수정하세요.  requestQueue.size() >= queueSize 대기 합니다.
        while(requestQueue.size() >= queueSize){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //requestQueue에  executable(작업) 추가하고 대기하고 있는 thread를 깨웁니다.
        requestQueue.add(executable);
        notifyAll();
    }

    public synchronized Executable getRequest(){
        //requestQueue가 비어 있다면(작업할 것이 없다면) 대기 합니다.
        while(requestQueue.isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //requestQueue에서 Executable(작업)을 반환 하고 , 대기하고 있는 thread를 깨웁 니다.
        notifyAll();
        return requestQueue.poll();
    }

}