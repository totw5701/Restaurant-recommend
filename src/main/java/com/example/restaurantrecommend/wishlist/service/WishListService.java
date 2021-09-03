package com.example.restaurantrecommend.wishlist.service;

import com.example.restaurantrecommend.naver.NaverClient;
import com.example.restaurantrecommend.naver.dto.SearchImageReq;
import com.example.restaurantrecommend.naver.dto.SearchLocalReq;
import com.example.restaurantrecommend.wishlist.dto.WishListDto;
import com.example.restaurantrecommend.wishlist.entity.WishListEntity;
import com.example.restaurantrecommend.wishlist.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListService {


    private final NaverClient naverClient;
    private final WishListRepository wishListRepository;

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


    public WishListDto add(WishListDto wishListDto) {
        // Memory DB에 저장.
        var entity = dtoToEntity(wishListDto);
        var saved = wishListRepository.save(entity);
        return entityToDto(saved);
    }

    private WishListDto entityToDto(WishListEntity wishListEntity){
        var dto = new WishListDto();

        dto.setIndex(wishListEntity.getIndex());
        dto.setImageLink( wishListEntity.getImageLink() );
        dto.setAddress( wishListEntity.getAddress() );
        dto.setTitle( wishListEntity.getTitle() );
        dto.setCategory(wishListEntity.getCategory());
        dto.setLastVisitDate(wishListEntity.getLastVisitDate());
        dto.setRoadAddress(wishListEntity.getRoadAddress());
        dto.setVisit(wishListEntity.isVisit());
        dto.setVisitCount(wishListEntity.getVisitCount());
        dto.setHomePageLink(wishListEntity.getHomePageLink());

        return dto;
    }

    private WishListEntity dtoToEntity(WishListDto wishListDto){
        var entity = new WishListEntity();

        entity.setIndex(wishListDto.getIndex());
        entity.setImageLink( wishListDto.getImageLink());
        entity.setAddress( wishListDto.getAddress() );
        entity.setTitle( wishListDto.getTitle() );
        entity.setCategory(wishListDto.getCategory());
        entity.setLastVisitDate(wishListDto.getLastVisitDate());
        entity.setRoadAddress(wishListDto.getRoadAddress());
        entity.setVisit(wishListDto.isVisit());
        entity.setVisitCount(wishListDto.getVisitCount());
        entity.setHomePageLink(wishListDto.getHomePageLink());

        return entity;
    }

    public List<WishListDto> findAll() {
        var listEntity= wishListRepository.findAll();
        // var listDto = new ArrayList<WishListDto>();
        // listEntity.stream().forEach(entity -> listDto.add(entityToDto(entity)));
        return listEntity.stream().map(it -> entityToDto(it)).collect(Collectors.toList());
        // stream에서 map은 요소들을 특정 조건에 해당하는 값으로 변환해준다. 가공이 끝나면 collect로 리턴.

    }

    public void delete(int index) {
        wishListRepository.deleteById(index);
    }

    public void addVisite(int index){
        var wishItem = wishListRepository.findById(index);
        if(wishItem.isPresent()){
            var item = wishItem.get();
            item.setVisit(true);
            item.setVisitCount(item.getVisitCount()+1);
        }
    }


}

