package com.example.restaurantrecommend.wishlist.repository;

import com.example.restaurantrecommend.db.MemoryDbRepositoryAbstract;
import com.example.restaurantrecommend.wishlist.entity.WishListEntity;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository extends MemoryDbRepositoryAbstract<WishListEntity> {
}
