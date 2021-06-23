package com.address.crawling.mapper;

import com.address.crawling.entity.County;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CountyMapper {

    void insert(@Param("list") List<County> counties);

    void deleteAll();

    List<County> selectAll();
}
