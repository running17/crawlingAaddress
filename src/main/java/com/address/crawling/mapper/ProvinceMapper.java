package com.address.crawling.mapper;

import com.address.crawling.entity.Province;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProvinceMapper {

    void insert(@Param("list") List<Province> province);

    List<Province> selectAll();

    void deleteAll();
}
