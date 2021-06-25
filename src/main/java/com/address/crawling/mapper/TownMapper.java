package com.address.crawling.mapper;

import com.address.crawling.entity.County;
import com.address.crawling.entity.Town;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TownMapper {

    void insert(@Param("list")List<Town> cities);

    void deleteAll();

    List<Town> selectAll();

    void deleteByCountys(@Param("list")List<County> errors);
}
