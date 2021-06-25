package com.address.crawling.service.impl;

import com.address.crawling.constant.Constant;
import com.address.crawling.entity.Town;
import com.address.crawling.entity.Village;
import com.address.crawling.mapper.TownMapper;
import com.address.crawling.mapper.VillageMapper;
import com.address.crawling.service.VillageService;
import com.address.crawling.utils.JsoupUtils;
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

    public void crawlingVillageData(List<Town> towns) {
        List<Village> villages = new ArrayList<>();
        if(CollectionUtils.isEmpty(towns)){
            towns = townMapper.selectAll();
        }
        List<Town> errors = new ArrayList<>();
        for(Town town : towns){
            final String townCode = town.getCode();
            final String townUrl = town.getPath();
            try {
                Elements elements = JsoupUtils.getDataElements(townUrl, Constant.VILLAGE_CLASS_NAME);
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
                errors.add(town);
            }
            villageMapper.deleteByTownCode(townCode);
            if(!CollectionUtils.isEmpty(villages)){
                villageMapper.insert(villages);
                villages.clear();
            }
        }

        if(!CollectionUtils.isEmpty(errors)){
            crawlingVillageData(errors);
        }

    }

    @Override
    public void crawlingVillageData() {
        crawlingVillageData(null);
    }
}
