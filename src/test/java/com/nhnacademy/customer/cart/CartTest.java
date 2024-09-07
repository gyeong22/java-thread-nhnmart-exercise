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

package com.nhnacademy.customer.cart;

import com.nhnacademy.customer.exception.ProductAlreadyExistsException;
import lombok.Synchronized;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    Cart cart;
    @BeforeEach
    void setUp() throws ProductAlreadyExistsException {
        cart = new Cart();
        cart.tryAddItem(new CartItem(1l,1));
    }

    @Test
    @DisplayName("Serializable implements check")
    void constructorTest(){
        Assertions.assertInstanceOf(Serializable.class,cart);
    }

    @Test
    @DisplayName("장바구니(cart)에 제품(CartItem) 추가")
    void tryAddItem1() throws ProductAlreadyExistsException {
        CartItem cartItem = new CartItem(2l,1);
        cart.tryAddItem(cartItem);
        Assertions.assertAll(
                ()-> Assertions.assertEquals(cart.getCartItems().size(), 2),
                ()-> Assertions.assertEquals(cart.getCartItems().getLast(),cartItem)
        );
    }

    @Test
    @DisplayName("장바구니에 제품이 이미 추가되어 있따면 - ProductAlreadyExistsException 발생")
    void tryAddItem2() throws ProductAlreadyExistsException {
        //TODO#2-12 DisplayName에 작성된 요구사항이 만족하도록 검증 합니다.

    }

    @Test
    @DisplayName("Cart 비우기 - 초기화")
    void clear() {
        //TODO#2-13 DisplayName에 작성된 요구사항이 만족 하도록 검증 합니다.

    }

    @Test
    @DisplayName("Cart item 조회")
    void getCartItems() {
        //productId : 2 장바구니 추가
        cart.getCartItems().add(new CartItem(2l,1));

        List<CartItem> excepted = new ArrayList<>();
        excepted.add(new CartItem(1l,1));
        excepted.add(new CartItem(2l,1));

        Assertions.assertEquals(excepted,cart.getCartItems());
    }

}