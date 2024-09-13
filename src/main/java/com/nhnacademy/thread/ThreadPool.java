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

package com.nhnacademy.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class ThreadPool {

    //thread-pool 기본 size
    private static final int DEFAULT_POOL_SIZE = 10;

    //thread pool size
    private final int poolSize;

    //thread에 의해서 실행될 Runnable 구현체
    private final Runnable runnable;

    //thread-pool에 생성된 thread list
    private final List<Thread> threadList;

    private final Lock mutex = new ReentrantLock();

    public ThreadPool(Runnable runnable){
        //TODO#8-1-1 default 생성자 구현, poolSize = DEFAULT_POOL_SIZE를 사용합니다.
        this.poolSize= DEFAULT_POOL_SIZE;
        this.runnable = runnable;
        this.threadList = new ArrayList<>();
    }

    public ThreadPool(int poolSize, Runnable runnable) {
        //TODO#8-1-2 thread pool size <0 다면 IllegalArgumentException이 발생 합니다.
        if (poolSize < 0)
            throw new IllegalArgumentException();

        //TODO#8-1-3 runable == null 이면 IllegalArgumentException 발생 합니다.
        if (runnable == null)
            throw new IllegalArgumentException();

        //TODO#8-1-4 runnable 이 Runnable의 구현체가 아니라면 IllegalArgumentException 발생 합니다.
        if (!(runnable instanceof Runnable)){
            throw new IllegalArgumentException();
        }


        //TODO#8-1-5 poolSize, runnable, threadList 초기화
        this.poolSize = poolSize;
        this.runnable = runnable;
        threadList = new ArrayList<>(poolSize);

        //thread를 미리 poolSize만큼 생성 합니다.
        createThread();
    }

    private void createThread(){
        for(int i=0; i<poolSize; i++){
            mutex.lock();
            threadList.add(new Thread(runnable));
            mutex.unlock();
        }
    }

    public synchronized void start(){
        /*TODO#8-1-6 thread를생성 -> threadList에 등록 합니다. -> 생성된 thread를 시작 합니다.
            - thread가 생성되는 과정은 동기화 되어야 합니다.
        * */


        for(int i=0; i<poolSize; i++){
            //구현
            threadList.get(i).start();
        }
        log.debug("threadList size : {}", threadList.size());
    }

    public synchronized void stop(){
        /*TODO#8-1-7 interrupt()를 실행해서 thread를 종료 합니다.
            - thread가 종료되는 과정에서 동기화 되어야 합니다.
            - 우선 모든 thread interrupt 호출
         */

        for(Thread thread : threadList){
            //구현
            thread.interrupt();
        }

        //TODO#8-1-8 join()를 이용해서 모든 thread가 종료될 떄 까지 대기 상태로 만듭니다.
        for(Thread thread : threadList){
            //궈현
            try {
                thread.join();
            }catch (InterruptedException e){

            }
        }
    }
}