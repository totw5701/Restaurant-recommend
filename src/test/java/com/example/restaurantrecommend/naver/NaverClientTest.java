package com.example.restaurantrecommend.naver;


import com.example.restaurantrecommend.naver.dto.SearchImageReq;
import com.example.restaurantrecommend.naver.dto.SearchLocalReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NaverClientTest {

    @Autowired
    private NaverClient naverClient;


    @Test
    public void searchLocalTest(){
        var search = new SearchLocalReq();
        search.setQuery("갈비집");

        var result = naverClient.searchLocal(search);
        System.out.println(result);

    }

    @Test
    public void searchImageTest(){
        var search = new SearchImageReq();
        search.setQuery("노가리슈퍼");

        var expected = naverClient.searchImage(search);
        System.out.println(expected);

    }


}
