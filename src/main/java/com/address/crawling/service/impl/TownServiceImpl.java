package com.address.crawling.service.impl;

import com.address.crawling.constant.Constant;
import com.address.crawling.entity.County;
import com.address.crawling.entity.Town;
import com.address.crawling.mapper.CountyMapper;
import com.address.crawling.mapper.TownMapper;
import com.address.crawling.service.TownService;
import com.address.crawling.utils.JsoupUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TownServiceImpl implements TownService {

    @Autowired
    CountyMapper countyMapper;

    @Autowired
    TownMapper townMapper;

    public void crawlingTownData(List<County> counties) {
        List<Town> towns = new ArrayList<>();
        if(CollectionUtils.isEmpty(counties)){
            counties = countyMapper.selectAll();
        }
        List<County> errors = new ArrayList<>();
        for(County county : counties){
            final String countyCode = county.getCode();
            final String townUrl = county.getPath();

            if(!StringUtils.hasLength(townUrl)){
                continue;
            }
            String urlPre = townUrl.substring(0, townUrl.lastIndexOf(Constant.URL_SEPARATOR) + 1);
            try {
                Elements elements = JsoupUtils.getDataElements(townUrl, Constant.TOWN_CLASS_NAME);
                elements.forEach(element -> {
                    Elements tds = element.getElementsByTag(Constant.TD);
                    Town town = new Town();
                    town.setCountyCode(countyCode);
                    JsoupUtils.buildCodeByTdElement(tds.get(0), town);
                    JsoupUtils.buildNameAndPathByTdElement(tds.get(1), town, urlPre);
                    towns.add(town);
                });
            } catch (IOException e) {
                e.printStackTrace();
                errors.add(county);
            }

        }
        if(!CollectionUtils.isEmpty(towns)){
            townMapper.insert(towns);
        }
        if(!CollectionUtils.isEmpty(errors)){
            townMapper.deleteByCountys(errors);
            crawlingTownData(errors);
        }
    }

    @Override
    public void crawlingTownData() {
        townMapper.deleteAll();
        crawlingTownData(null);
    }
}
