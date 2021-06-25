package com.address.crawling.service.impl;

import com.address.crawling.entity.County;
import com.address.crawling.mapper.CountyMapper;
import com.address.crawling.service.CountyService;
import com.address.crawling.constant.Constant;
import com.address.crawling.entity.City;
import com.address.crawling.mapper.CityMapper;
import com.address.crawling.utils.JsoupUtils;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CountyServiceImpl implements CountyService {

    @Autowired
    CityMapper cityMapper;

    @Autowired
    CountyMapper countyMapper;

    public void crawlingCountyData(List<City> cities) {
        List<County> counties = new ArrayList<>();
        if(CollectionUtils.isEmpty(cities)){
            cities = cityMapper.selectAll();
        }
        List<City> errors = new ArrayList<>();
        for(City city : cities){
            final String cityCode = city.getCode();
            final String countyUrl = city.getPath();
            String urlPre = countyUrl.substring(0, countyUrl.lastIndexOf(Constant.URL_SEPARATOR) + 1);
            try {
                Elements elements = JsoupUtils.getDataElements(countyUrl, Constant.COUNTY_CLASS_NAME);
                elements.forEach(element -> {
                    Elements tds = element.getElementsByTag(Constant.TD);
                    County county = new County();
                    county.setCityCode(cityCode);
                    JsoupUtils.buildCodeByTdElement(tds.get(0), county);
                    JsoupUtils.buildNameAndPathByTdElement(tds.get(1), county, urlPre);
                    counties.add(county);
                });
            } catch (IOException e) {
                e.printStackTrace();
                errors.add(city);
            }
        }
        if(!CollectionUtils.isEmpty(counties)){
            countyMapper.insert(counties);
        }
        if(!CollectionUtils.isEmpty(errors)){
            countyMapper.deleteByCity(errors);
            crawlingCountyData(errors);
        }
    }

    @Override
    public void crawlingCountyData(){
        countyMapper.deleteAll();
        crawlingCountyData(null);
    }
}
