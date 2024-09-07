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
import com.nhnacademy.customer.domain.Customer;

import java.util.Objects;

public class CartLocal {
    private static final ThreadLocal<Customer> customerLocal = new ThreadLocal<>();
    private static final ThreadLocal<Cart> cartLocal = ThreadLocal.withInitial(()->new Cart());
    public static void initialize(Customer customer){
        customerLocal.set(customer);
    }

    public static void reset(){
        customerLocal.remove();
        Cart cart = getCart();
        if(Objects.nonNull(cart)) {
            cart.clear();
        }
    }

    public static Customer getCustomer(){
        return customerLocal.get();
    }

    public static Cart getCart(){
        return cartLocal.get();
    }
}
