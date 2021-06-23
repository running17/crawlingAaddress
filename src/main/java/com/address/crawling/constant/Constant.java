package com.address.crawling.constant;

/**
 * @author running
 * 常量数据
 */
public class Constant {

    /**
     * 统计数据首页URL
     */
    public static final String INDEX_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2020/index.html";

    /**
     * 城市、省份下级共用的路径前缀
     */
    public static final String INDEX_URL_PRE = INDEX_URL.substring(0, INDEX_URL.lastIndexOf(Constant.URL_SEPARATOR) + 1);

    /**
     * html页面省份的class名
     */
    public static final String PROVINCE_CLASS_NAME = "provincetr";

    /**
     * a便签
     */
    public static final String A = "a";

    /**
     * href属性
     */
    public static final String HREF = "href";

    /**
     * url的后缀
     */
    public static final String URL_SUB = ".html";

    /**
     * url分隔符
     */
    public static final String URL_SEPARATOR = "/";

    /**
     * 城市的class名
     */
    public static final String CITY_CLASS_NAME = "citytr";

    /**
     * td标签
     */
    public static final String TD = "td";

    /**
     * 县区的class名
     */
    public static final String COUNTY_CLASS_NAME = "countytr";

    /**
     * 乡镇的class名
     */
    public static final String TOWN_CLASS_NAME = "towntr";

    /**
     * 村、居委会的class名
     */
    public static final String VILLAGE_CLASS_NAME = "villagetr";
}
