package com.example.restaurantrecommend.wishlist.service;

import com.example.restaurantrecommend.naver.NaverClient;
import com.example.restaurantrecommend.naver.dto.SearchImageReq;
import com.example.restaurantrecommend.naver.dto.SearchLocalReq;
import com.example.restaurantrecommend.naver.dto.SearchLocalRes;
import com.example.restaurantrecommend.wishlist.dto.WishListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListService {


    private final NaverClient naverClient;

    public WishListDto search(String query){
        // 지역 검색
        var searchLocalReq = new SearchLocalReq();
        searchLocalReq.setQuery(query);

        var searchLocalRes = naverClient.searchLocal(searchLocalReq);


        if(searchLocalRes.getTotal() > 0){
            var localItem = searchLocalRes.getItems().stream().findFirst().get();

        // 이미지 검색
            var imageQuery = localItem.getTitle().replaceAll("<[^>]*>", "");
            var searchImageReq = new SearchImageReq();
            searchImageReq.setQuery(imageQuery);
            var searchImageRes = naverClient.searchImage(searchImageReq);

            if(searchImageRes.getTotal() > 0){
                var imageItem = searchImageRes.getItems().stream().findFirst().get();
        // 두개 합친 결과
                var result = new WishListDto();
                result.setTitle(localItem.getTitle());
                result.setCategory(localItem.getCategory());
                result.setAddress(localItem.getAddress());
                result.setHomePageLink(localItem.getLink());
                result.setRoadAddress(localItem.getRoadAddress());
                result.setImageLink(imageItem.getLink());

                return result;
            }
        }
        return new WishListDto();
    }


}

