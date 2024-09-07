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

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class RequestHandler implements Runnable {

    //요청 대기열
    private final RequestChannel channel;

    public RequestHandler(RequestChannel channel) {
        //RequestChannel == null 이면 IllegalArgumentException이 발생 합니다.
        if(Objects.isNull(channel)){
            throw new IllegalArgumentException();
        }
        //channel을 초기화 합니다.
        this.channel = channel;
    }

    @Override
    public void run() {
        /*while 조건문을 수정 하세요
            - interrupt가 발생하면 while문이 종료 되면서 thread를 빠져 나가게 됩니다.
         */
        while(!Thread.currentThread().isInterrupted()){
            try{
                //channel.getRequest()를 호출해서 execute() method를 실행 합니다.
                Executable executable = channel.getRequest();
                executable.execute();
                Thread.sleep(100);
            }catch (Exception e){
                //interruptedException이 발생하면 interrupted flag 값이 false 상태로 설정 됩니다. 'Thread.currentThread().interrupt()'를 호출하여 다시 true상태로 변경 합니다.
                // 상위 레벨의 다른 코드 또는 스레드가 이 스레드가 인터럽트 되었음을 인지 할 수 있습니다.
                if(e.getMessage().contains(InterruptedException.class.getName())){
                    Thread.currentThread().interrupt();
                }

                // 종료될 떄 필요한 코드가 있다면 작성 합니다.
                log.debug("RequestHandler error : {}",e.getMessage(),e);
            }
        }
    }
}
