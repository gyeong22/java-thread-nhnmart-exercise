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

package com.nhnacademy.nhnmart.product.repository;

import com.nhnacademy.nhnmart.product.domain.Product;

import java.util.Optional;

public interface ProductRepository {
    //등록
    void save(Product product);
    //조회
    Optional<Product> findById(long id);

    //삭제
    void deleteById(long id);

    //product가 존재여부 체크
    boolean existById(long id);

    //prdocut 전체 count;
    long count();

    int countQuantityById(long id);

    void updateQuantityById(long id, int quantity);

}
