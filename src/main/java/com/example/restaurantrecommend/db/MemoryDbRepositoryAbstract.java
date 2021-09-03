package com.example.restaurantrecommend.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

abstract public class MemoryDbRepositoryAbstract<T extends MemoryDbEntity> implements MemoryDbRepositoryIfs<T>{

    private final List<T> db = new ArrayList<>();
    private int index = 0;

    @Override
    public Optional<T> findById(int index) {   // optional T 는 있을 수 도 없을 수 도 있다는 뜻.
        return db.stream().filter(it -> it.getIndex() == index).findFirst();
        // db라는 arrayList에서 각 원소들을 가지는 stream을 만들고 그 중 index를 get하여 그것이 this.index와 같은지 비교한다. 그리고 그중 첫번째 것을 리턴한다.
        // generic인 T는 지금 MemoryDbEntity를 상속받고있기 때문에 getIndex 메서드를 사용할 수 있다.
        // stream 중간연산 최종연산.
    }

    @Override
    public T save(T entity) {
        var optionalEntity = db.stream().filter(it -> it.getIndex() == entity.getIndex()).findFirst();

        if(optionalEntity.isEmpty()){
            // db에 데이터가 없는 경우.
            index++;
            entity.setIndex(index);
            db.add(entity);
            return entity;
        }else{
        // db에 이미 데이터가 있는 경우
            // 전에있던 데이터의 인덱스를 받아 새롭게 들어온 entity에 넣어준다. 기존 데이터 지우고 예전 index번호로 등록 할 예정.
            var preIndex = optionalEntity.get().getIndex();
            entity.setIndex(preIndex);

            deleteById(preIndex);
            db.add(entity);
            return entity;
        }
    }

    @Override
    public void deleteById(int index) {
        var optionalEntity = db.stream().filter(it -> it.getIndex() == index).findFirst();
        if(optionalEntity.isPresent()){
            db.remove(optionalEntity.get());
        }
    }

    @Override
    public List<T> findAll() {
        return db;
    }
}
