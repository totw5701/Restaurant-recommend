package com.example.restaurantrecommend.wishlist.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WishListServiceTest {

    @Autowired
    private WishListService wishListService;


    @Test
    public void searchTest(){

        // 지금은 간단하게 정보를 받아오는지만 확인했으나, 제대로 테스트하기 위해서는 WushListService를 목킹 처리로 특정한 값을 내리도록 세팅한 후 진행해야 한다.

        var result = wishListService.search("소막창");
        System.out.println(result);
        Assertions.assertNotNull(result);

    }



}
