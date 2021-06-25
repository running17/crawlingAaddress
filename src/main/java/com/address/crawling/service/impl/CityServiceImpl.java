package com.address.crawling.service.impl;

import com.address.crawling.constant.Constant;
import com.address.crawling.entity.City;
import com.address.crawling.entity.Province;
import com.address.crawling.mapper.CityMapper;
import com.address.crawling.mapper.ProvinceMapper;
import com.address.crawling.service.CityService;
import com.address.crawling.utils.JsoupUtils;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    ProvinceMapper provinceMapper;

    @Autowired
    CityMapper cityMapper;

    public void crawlingCityData(List<Province> provinceList) {
        if(CollectionUtils.isEmpty(provinceList)){
            provinceList = provinceMapper.selectAll();
        }
        List<City> cities = new ArrayList<>();
        List<Province> errors = new ArrayList<>();
        for(Province province : provinceList){
            final String provinceCode = province.getCode();
            final String cityUrl = province.getPath();
            try {
                Elements elements = JsoupUtils.getDataElements(cityUrl, Constant.CITY_CLASS_NAME);
                elements.forEach(element -> {
                    Elements tds = element.getElementsByTag(Constant.TD);
                    City city = new City();
                    city.setProvinceCode(provinceCode);
                    JsoupUtils.buildCodeByTdElement(tds.get(0), city);
                    JsoupUtils.buildNameAndPathByTdElement(tds.get(1), city, Constant.INDEX_URL_PRE);
                    cities.add(city);
                });
            } catch (IOException e) {
                e.printStackTrace();
                errors.add(province);
            }
        }
        if(!CollectionUtils.isEmpty(cities)){
            cityMapper.insert(cities);
        }
        if(!CollectionUtils.isEmpty(errors)){
            cityMapper.deleteAllByProvinces(errors);
            crawlingCityData(errors);
        }
    }

    @Override
    public void crawlingCityData() {
        cityMapper.deleteAll();
        crawlingCityData(null);
    }
}
