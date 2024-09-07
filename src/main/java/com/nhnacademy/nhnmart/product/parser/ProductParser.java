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

package com.nhnacademy.nhnmart.product.parser;

import com.nhnacademy.nhnmart.product.domain.Product;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/*
     ProductParser 인터페이스 입니다.
     - /src/main/resources/product_data.csv 파일을 파싱 합니다.
*/
public interface ProductParser extends Closeable {
    String PRODUCTS_DATA= "product_data.csv";
    List<Product> parse();
    default InputStream getProductsStream(){
        return this.getClass()
                .getClassLoader()
                .getResourceAsStream(PRODUCTS_DATA);
    }
}
