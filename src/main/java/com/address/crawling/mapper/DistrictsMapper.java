package com.address.crawling.mapper;

import com.address.crawling.entity.Districts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DistrictsMapper {

    void insert(@Param("list")List<Districts> cities);

    void deleteAll();
}
