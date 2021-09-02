package com.example.restaurantrecommend.naver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchLocalRes {

    private String lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private String category;
    private List<SearchLocalItem> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchLocalItem{
        private String title;
        private String link;
        private String description;
        private String telephone;
        private String address;
        private String roadAddress;
        private Integer mapx;
        private Integer mapy;

    }

}
