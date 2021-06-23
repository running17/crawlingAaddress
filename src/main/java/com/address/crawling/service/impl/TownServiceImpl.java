package com.address.crawling.service.impl;

import com.address.crawling.constant.Constant;
import com.address.crawling.entity.County;
import com.address.crawling.entity.Town;
import com.address.crawling.mapper.CountyMapper;
import com.address.crawling.mapper.TownMapper;
import com.address.crawling.service.TownService;
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

    @Override
    public void crawlingTownData() {
        List<Town> towns = new ArrayList<>();

        List<County> counties = countyMapper.selectAll();
        for(County county : counties){
            final String countyCode = county.getCode();
            final String townUrl = county.getPath();

            if(!StringUtils.hasLength(townUrl)){
                continue;
            }
            String urlPre = townUrl.substring(0, townUrl.lastIndexOf(Constant.URL_SEPARATOR) + 1);
            try {
                Document document = Jsoup.connect(townUrl).get();
                Elements elements = document.getElementsByClass(Constant.TOWN_CLASS_NAME);
                elements.forEach(element -> {
                    Elements tds = element.getElementsByTag(Constant.TD);
                    Town town = new Town();
                    town.setCountyCode(countyCode);
                    Elements codeAs = tds.get(0).getElementsByTag(Constant.A);
                    Element codeA = codeAs.get(0);
                    town.setCode(codeA.text());

                    Elements nameAs = tds.get(1).getElementsByTag(Constant.A);
                    Element nameA = nameAs.get(0);
                    town.setName(nameA.text());
                    town.setPath(urlPre + nameA.attr(Constant.HREF));
                    towns.add(town);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        townMapper.deleteAll();
        if(!CollectionUtils.isEmpty(towns)){
            townMapper.insert(towns);
        }
    }
}
