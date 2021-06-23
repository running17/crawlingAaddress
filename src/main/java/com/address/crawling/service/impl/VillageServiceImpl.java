package com.address.crawling.service.impl;

import com.address.crawling.constant.Constant;
import com.address.crawling.entity.Town;
import com.address.crawling.entity.Village;
import com.address.crawling.mapper.TownMapper;
import com.address.crawling.mapper.VillageMapper;
import com.address.crawling.service.VillageService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VillageServiceImpl implements VillageService {

    @Autowired
    VillageMapper villageMapper;

    @Autowired
    TownMapper townMapper;


    @Override
    public void crawlingVillageData() {
        List<Village> villages = new ArrayList<>();
        List<Town> towns = townMapper.selectAll();
        for(Town town : towns){
            final String townCode = town.getCode();
            final String townUrl = town.getPath();
            try {
                Document document = Jsoup.connect(townUrl).get();
                Elements elements = document.getElementsByClass(Constant.VILLAGE_CLASS_NAME);
                elements.forEach(element -> {
                    Elements tds = element.getElementsByTag(Constant.TD);
                    Village village = new Village();
                    village.setTownCode(townCode);
                    village.setCode(tds.get(0).text());
                    village.setTypeCode(tds.get(1).text());
                    village.setName(tds.get(2).text());
                    villages.add(village);
                });
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("出错url" + townUrl);
            }
            villageMapper.deleteByTownCode(townCode);
            if(!CollectionUtils.isEmpty(villages)){
                villageMapper.insert(villages);
                villages.clear();
            }
        }

    }
}
