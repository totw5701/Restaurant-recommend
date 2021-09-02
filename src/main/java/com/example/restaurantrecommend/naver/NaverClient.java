package com.example.restaurantrecommend.naver;

import com.example.restaurantrecommend.naver.dto.SearchImageReq;
import com.example.restaurantrecommend.naver.dto.SearchImageRes;
import com.example.restaurantrecommend.naver.dto.SearchLocalReq;
import com.example.restaurantrecommend.naver.dto.SearchLocalRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.ParameterizedType;

@Component
public class NaverClient {

    @Value("${naver.client.id}")  // resources.application.yaml에서 정보를 받아옴,
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverClientSecret;

    @Value("${naver.url.search.local}")
    private String naverLocalSearchUrl;

    @Value("${naver.url.search.image}")
    private String naverImageSearchUrl;

    public SearchLocalRes searchLocal(SearchLocalReq searchLocalReq){   // 이 메서드들을 인터페이스로 관리하고 Api라는 클래스를 하나 더 만들어 그 안에 DI를 구현하면 네이버, 카카오, 구글 다양한 api를 사용할 수 있다.

        var uri = UriComponentsBuilder
                .fromUriString(naverLocalSearchUrl)
                .queryParams(searchLocalReq.toMultivalueMap())  // queryParam's'를 사용하면 multiValueMap을 통해 한번에 여러개 쿼리 파람들을 받아올 수 있다.
                .build()
                .encode()
                .toUri();


        var headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 저번에 했던 방식.
//        RequestEntity<Void> reqEntity = RequestEntity
//                .get(uri)
//                .headers(headers)
//                .build();

        var httpEntity = new HttpEntity<>(headers);
        var responseType = new ParameterizedTypeReference<SearchLocalRes>(){};

        var responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );

        return responseEntity.getBody();
    }

    public SearchImageRes searchImage(SearchImageReq searchImageReq){
        var uri = UriComponentsBuilder
                .fromUriString(naverImageSearchUrl)
                .queryParams(searchImageReq.toMultivalueMap())
                .build()
                .encode()
                .toUri();

        var headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverClientSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        var httpEntity = new HttpEntity<>(headers);
        var responseType = new ParameterizedTypeReference<SearchImageRes>(){};

        var responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );

        return responseEntity.getBody();

    }


}
