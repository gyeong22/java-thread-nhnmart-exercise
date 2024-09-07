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

package com.nhnacademy.nhnmart.product.service;

import com.nhnacademy.nhnmart.product.domain.Product;

public interface ProductService {
    //product 조회
    Product getProduct(long id);
    //product 등록 후 product id를 응답 한다.
    void saveProduct(Product product);
    //product 삭제
    void deleteProduct(long id);

    //전체 상품의 개수를 구합니다.
    long getTotalCount();

    //제품의 수량을 수정 한다.
    void updateQuantity(long id, int quantity);

    //제품을 들어서 카트에 담을 때 해당 제품의 수량을 차감 합니다.
    //quantity는 구매 수량 입니다.
    void pickProduct(long id, int quantity);

    //제품을 반납 합니다. 반납된 수량을 더한 수량 반환
    int returnProduct(long id, int quantity);

}
