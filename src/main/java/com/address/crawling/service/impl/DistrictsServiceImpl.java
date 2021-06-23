package com.address.crawling.service.impl;

import com.address.crawling.entity.*;
import com.address.crawling.mapper.*;
import com.address.crawling.service.DistrictsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DistrictsServiceImpl implements DistrictsService {

    @Autowired
    ProvinceMapper provinceMapper;

    @Autowired
    CityMapper cityMapper;

    @Autowired
    CountyMapper countyMapper;

    @Autowired
    TownMapper townMapper;

    @Autowired
    VillageMapper villageMapper;

    @Autowired
    DistrictsMapper districtsMapper;

    @Override
    public void buildDistrictsData() {
        List<Province> provinces = provinceMapper.selectAll();
        List<City> cities = cityMapper.selectAll();
        List<County> counties = countyMapper.selectAll();
        List<Town> towns = townMapper.selectAll();
        List<Village> villages = villageMapper.selectAll();

        AtomicInteger id = new AtomicInteger();
        List<Districts> districts = new ArrayList<>();
        for(Province province : provinces){
            Districts pro = Districts.builder().id(id.incrementAndGet()).code(province.getCode()).level(1)
                    .name(province.getName()).build();
            districts.add(pro);
            cities.stream().filter(c->c.getProvinceCode().equals(province.getCode())).forEach(city->{
                Districts cit = Districts.builder().id(id.incrementAndGet()).code(city.getCode()).level(2)
                        .name(city.getName()).parentId(pro.getId()).build();
                districts.add(cit);
                counties.stream().filter(c->c.getCityCode().equals(city.getCode())).forEach(county->{
                    Districts coun = Districts.builder().id(id.incrementAndGet()).code(county.getCode()).level(3)
                            .name(county.getName()).parentId(cit.getId()).build();
                    districts.add(coun);
                    towns.stream().filter(c->c.getCountyCode().equals(county.getCode())).forEach(town->{
                        Districts tow = Districts.builder().id(id.incrementAndGet()).code(town.getCode()).level(4)
                                .name(town.getName()).parentId(coun.getId()).build();
                        districts.add(tow);
                        villages.stream().filter(c->c.getTownCode().equals(town.getCode())).forEach(village->{
                            Districts vil = Districts.builder().id(id.incrementAndGet()).code(village.getCode()).level(4)
                                    .name(village.getName()).parentId(tow.getId()).build();
                            districts.add(vil);
                        });
                    });
                });

            });
            districtsMapper.insert(districts);
            districts.clear();
        }

    }
}
