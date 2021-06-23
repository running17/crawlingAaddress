package com.address.crawling.service.impl;

import com.address.crawling.entity.County;
import com.address.crawling.mapper.CountyMapper;
import com.address.crawling.service.CountyService;
import com.address.crawling.constant.Constant;
import com.address.crawling.entity.City;
import com.address.crawling.mapper.CityMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

    @Override
    public void crawlingCountyData() {
        List<County> counties = new ArrayList<>();

        List<City> cities = cityMapper.selectAll();
        for(City city : cities){
            final String cityCode = city.getCode();
            final String countyUrl = city.getPath();
            String urlPre = countyUrl.substring(0, countyUrl.lastIndexOf(Constant.URL_SEPARATOR) + 1);
            try {
                Document document = Jsoup.connect(countyUrl).get();
                Elements elements = document.getElementsByClass(Constant.COUNTY_CLASS_NAME);
                elements.forEach(element -> {
                    Elements tds = element.getElementsByTag(Constant.TD);
                    County county = new County();
                    county.setCityCode(cityCode);
                    Elements codeAs = tds.get(0).getElementsByTag(Constant.A);
                    if(codeAs.isEmpty()){
                        county.setCode(tds.get(0).text());
                    }else{
                        Element codeA = codeAs.get(0);
                        county.setCode(codeA.text());
                    }


                    Elements nameAs = tds.get(1).getElementsByTag(Constant.A);
                    if(nameAs.isEmpty()){
                        county.setName(tds.get(1).text());
                    }else{
                        Element nameA = nameAs.get(0);
                        county.setName(nameA.text());
                        county.setPath(urlPre + nameA.attr(Constant.HREF));
                    }
                    counties.add(county);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        countyMapper.deleteAll();
        if(!CollectionUtils.isEmpty(counties)){
            countyMapper.insert(counties);
        }
    }
}
