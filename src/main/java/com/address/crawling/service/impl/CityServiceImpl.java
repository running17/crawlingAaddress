package com.address.crawling.service.impl;

import com.address.crawling.constant.Constant;
import com.address.crawling.entity.City;
import com.address.crawling.entity.Province;
import com.address.crawling.mapper.CityMapper;
import com.address.crawling.mapper.ProvinceMapper;
import com.address.crawling.service.CityService;
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
public class CityServiceImpl implements CityService {

    @Autowired
    ProvinceMapper provinceMapper;

    @Autowired
    CityMapper cityMapper;

    @Override
    public void crawlingCityData() {
        List<Province> provinceList = provinceMapper.selectAll();
        if(CollectionUtils.isEmpty(provinceList)){
            return;
        }
        List<City> cities = new ArrayList<>();
        for(Province province : provinceList){
            final String provinceCode = province.getCode();
            final String cityUrl = province.getPath();
            try {
                Document document = Jsoup.connect(cityUrl).get();
                Elements elements = document.getElementsByClass(Constant.CITY_CLASS_NAME);
                elements.forEach(element -> {
                    Elements tds = element.getElementsByTag(Constant.TD);
                    City city = new City();
                    city.setProvinceCode(provinceCode);
                    Elements codeAs = tds.get(0).getElementsByTag(Constant.A);
                    Element codeA = codeAs.get(0);
                    city.setCode(codeA.text());

                    Elements nameAs = tds.get(1).getElementsByTag(Constant.A);
                    Element nameA = nameAs.get(0);
                    city.setName(nameA.text());
                    city.setPath(Constant.INDEX_URL_PRE + nameA.attr(Constant.HREF));
                    cities.add(city);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        cityMapper.deleteAll();
        if(!CollectionUtils.isEmpty(cities)){
            cityMapper.insert(cities);
        }
    }
}
