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

package com.nhnacademy.nhnmart.product.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * mart에서 판매되는 제품
 */
public class Product {

    //product-id
    private final long id;

    //품목
    private final String item;

    //메이커
    private final String maker;

    //스펙
    private final String specification;

    //단위
    private final String unit;

    //가격
    private final int price;

    //수량
    private int quantity;

    public Product(long id, String item, String maker, String specification, String unit, int price, int quantity) {
        //product 생성자의 parameter 검증을 통과하지 못한다면 IllegalArgumentException이 발생 됩니다.
        if( id< 0 || price < 0 || quantity < 0 || StringUtils.isEmpty(item) || StringUtils.isEmpty(maker) || StringUtils.isEmpty(specification) || StringUtils.isEmpty(unit) ){
            throw new IllegalArgumentException();
        }

        //product attribute를 초기화 합니다.
        this.id = id;
        this.item = item;
        this.maker = maker;
        this.specification = specification;
        this.unit = unit;
        this.price = price;
        this.quantity = quantity;
    }

    public long getId() {
        //product id 반환
        return id;
    }

    public String getItem() {
        //item 반환
        return item;
    }

    public String getMaker() {
        //maker 반환
        return maker;
    }

    public String getSpecification() {
        //specification 반환
        return specification;
    }

    public String getUnit() {
        //unit 반환
        return unit;
    }

    public int getPrice() {
        //price 반환
        return price;
    }

    public int getQuantity() {
        //quantity 반환
        return quantity;
    }

    public void setQuantity(int quantity) {
        //qunatity 수정, quantity < 0 이면 IllegalArgumentException 발생
        if(quantity < 0 ){
            throw new IllegalArgumentException("quantity >=0");
        }
        this.quantity = quantity;
    }

    //equals를 구현 합니다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && price == product.price && quantity == product.quantity && Objects.equals(item, product.item) && Objects.equals(maker, product.maker) && Objects.equals(specification, product.specification) && Objects.equals(unit, product.unit);
    }

    //hashCode를 구현합니다.
    @Override
    public int hashCode() {
        return Objects.hash(id, item, maker, specification, unit, price, quantity);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", maker='" + maker + '\'' +
                ", specification='" + specification + '\'' +
                ", unit='" + unit + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
