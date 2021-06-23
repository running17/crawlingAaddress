package com.address.crawling.mapper;

import com.address.crawling.entity.Village;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VillageMapper {

    void insert(@Param("list")List<Village> cities);

    void deleteAll();

    List<Village> selectAll();

    void deleteByTownCode(@Param("townCode") String townCode);
}
