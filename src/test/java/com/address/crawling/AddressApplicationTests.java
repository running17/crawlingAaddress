package com.address.crawling;

import com.address.crawling.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
class AddressApplicationTests {

    @Autowired
    ProvinceService provinceService;

    @Autowired
    CityService cityService;

    @Autowired
    CountyService countyService;

    @Autowired
    TownService townService;

    @Autowired
    VillageService villageService;

    @Autowired
    DistrictsService districtsService;

    @Test
    void provinceLoads() {
        provinceService.crawlingProvinceData();
    }

    @Test
    void cityLoads(){
        cityService.crawlingCityData();
    }

    @Test
    void countyLoads(){
        countyService.crawlingCountyData();
    }

    @Test
    void townLoads(){
        townService.crawlingTownData();
    }

    @Test
    void villageLoads(){
        villageService.crawlingVillageData();
    }

    @Test
    void districtLoads(){
        districtsService.buildDistrictsData();
    }

    @Test
    void addressLoads(){
        System.out.println("---省份数据");
        provinceLoads();
        sleep();
        System.out.println("---城市数据");
        cityLoads();
        sleep();
        System.out.println("---县区数据");
        countyLoads();
        sleep();
        System.out.println("---乡镇数据");
        townLoads();
        sleep();
        System.out.println("---村委会数据");
        villageLoads();
        System.out.println("---组装区域");
        districtLoads();
    }

    private void sleep(){
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
