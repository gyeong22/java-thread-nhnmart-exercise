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

package com.nhnacademy.thread.customer;

import com.nhnacademy.customer.cart.Cart;
import com.nhnacademy.customer.cart.CartItem;
import com.nhnacademy.customer.domain.Customer;
import com.nhnacademy.nhnmart.entring.EnteringQueue;
import com.nhnacademy.nhnmart.product.domain.Product;
import com.nhnacademy.nhnmart.product.service.ProductService;
import com.nhnacademy.thread.scc.SelfCheckoutRequest;
import com.nhnacademy.thread.util.RequestChannel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;

import java.util.Objects;
import java.util.Random;
import java.util.random.RandomGenerator;

/**
 * 고객(Customer)이 쇼핑을 합니다. 쇼핑이 완료된 고객은 checkoutChannel을 통해서 결제 대기열에 등록 합니다.
 */
@Slf4j
public class CustomerShoppingHandler implements Runnable {

    //마트 입장 대기열 Queue
    private final EnteringQueue enteringQueue;

    //제품 관련 service
    private final ProductService productService;

    //random generator
    //https://docs.oracle.com/en/java/javase/21/docs//api/java.base/java/util/random/RandomGenerator.html
    private final RandomGenerator generator;

    //고객이 쇼핑 완료 후 결제를 위한 대기 channel
    private final RequestChannel checkoutChannel;

    public CustomerShoppingHandler(EnteringQueue enteringQueue, ProductService productService, RequestChannel checkoutChannel) {

        //TODO#9-1-1 enteringQueue, productService, checkoutChannel null check, IllegalArgumentException 발생
        if(Objects.isNull(enteringQueue) || Objects.isNull(productService) || Objects.isNull(checkoutChannel)){
            throw new IllegalArgumentException();
        }

        //TODO#9-1-2 enteringQueue, productService,checkoutChannel,generator 초기화 합니다.
        this.enteringQueue = enteringQueue;
        this.productService = productService;
        this.checkoutChannel = checkoutChannel;
        this.generator = new Random();
    }

    @Override
    public void run() {

        while(!Thread.currentThread().isInterrupted()){
            try {

                /*TODO#9-1-3 enteringQueue 대기열에 있는 고객이 마트에 입장 합니다.
                   CartManager.initialize를 호출해서 해당 Thread내에서 Customer(고객), Cart(장바구니) 공유될 수 있도록 설정 합니다.
                 */

                //enteringQueue(입장 대기열) 부터 입장시킬 고객 얻기
                Customer customer = enteringQueue.getCustomer();

                //CartLocal CartLocal.initialize() 호출하여 초기화 합니다.
                CartLocal.initialize(customer);


                //TODO#9-1-4 1~10초 랜덤하게 sleep 합니다.
                int s = generator.nextInt(1,11);
                Thread.sleep(s*1000);

                //shopping() method를 구현 합니다.
                shopping();

                //쇼핑 후 결제 대기열 등록
                joinCheckoutChannel();

            }catch (InterruptedException ie){
                Thread.currentThread().interrupt();
            }catch (Exception e){
                if(e.getMessage().contains(InterruptedException.class.getName())) {
                    Thread.currentThread().interrupt();
                }
                log.debug("shopping : {}",e.getMessage(),e);
            }finally {
                //TODO#9-1-5 해당 Thread는 checkoutChannel(결제 대기열)에 등록 후 customerLocal, cartLocal 초기화
                CartLocal.reset();
            }
        }
    }

    private void shopping(){
        /*TODO#9-1-6 shopping method 구현
            - 제품 : getProductIdByRand()
            - 쇼핑 횟수 : getShoppingCountByRand()
            - 장바구에 담을 제품의 수량 : getBuyCountByRand()

            제품을 장바구니에 추가 합니다.
        */

        //CartLocal 부터 고객의 장바구니 얻기
        Cart cart = CartLocal.getCart();

        //쇼핑 횟수(랜덤) 만큼 쇼핑 합니다.
        for(int i=0; i<getShoppingCountByRand(); i++) {

            long productId = getProductIdByRand();
            Product product = productService.getProduct(productId);
            int buyCount = getBuyCountByRand();

            //구매 수량보다 제품의 수량이 부족하다면 해당 제품은 카트에 담지 않습니다
            if(buyCount > product.getQuantity()){
                continue;
            }

            CartItem cartItem = new CartItem(productId,buyCount);
            try {
                cart.tryAddItem(cartItem);
                productService.pickProduct(productId, buyCount);
            }catch (Exception e){
                //exception이 발생 하면 .. 로그 남기기
                log.debug("장바구니 error : {}",e.getMessage(),e);
            }
        }

    }

    public void joinCheckoutChannel(){
        /*TODO#9-1-7 쇼핑 후 결제 대기열 등록
            - checkoutChannel을 이용해서 SelfCheckoutRequest 요청을 등록 합니다.
         */

        Cart cart = CartLocal.getCart();
        Cart newCart = SerializationUtils.clone(cart);
        Customer customer = CartLocal.getCustomer();
        checkoutChannel.addRequest(new SelfCheckoutRequest(CartLocal.getCustomer(),newCart,productService));
    }

    private int getBuyCountByRand(){
        //TODO#9-1-8 장바구니에 담는 제품의 수량 1-5 random 숫자 반환
        return generator.nextInt(1,6);
    }

    private int getShoppingCountByRand(){
        //TODO#9-1-9 장바구니에 담는 제품의 개수 1-10 random 숫자 반환
        return generator.nextInt(1,11);
    }

    private long getProductIdByRand(){
        //TODO#9-1-10 쇼핑할 제품의 id - 1 ~ productService.gettotalCount() 범위의 random 숫자 반환
        long productCount = productService.getTotalCount();
        return generator.nextLong(1l,productCount+1);
    }

}
