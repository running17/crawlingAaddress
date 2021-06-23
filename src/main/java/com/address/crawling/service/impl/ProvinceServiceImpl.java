package com.address.crawling.service.impl;

import com.address.crawling.constant.Constant;
import com.address.crawling.entity.Province;
import com.address.crawling.mapper.ProvinceMapper;
import com.address.crawling.service.ProvinceService;
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
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    public void crawlingProvinceData() {
        List<Province> provinces = new ArrayList<>();
        try {
            Document document = Jsoup.connect(Constant.INDEX_URL).get();
            Elements elements = document.getElementsByClass(Constant.PROVINCE_CLASS_NAME);
            String pathPre = Constant.INDEX_URL_PRE;
            elements.forEach(element -> {
                Elements aes = element.getElementsByTag(Constant.A);
                if(!aes.isEmpty()){
                    aes.forEach(ae->{
                        String path = ae.attr(Constant.HREF);
                        String name = ae.text();
                        String code = path.replace(Constant.URL_SUB,"");
                        path = pathPre + path;
                        Province province = Province.builder()
                                .code(code)
                                .name(name)
                                .path(path)
                                .build();
                        provinces.add(province);
                    });

                }

            });

            provinceMapper.deleteAll();
            if(!CollectionUtils.isEmpty(provinces)){
                provinceMapper.insert(provinces);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
