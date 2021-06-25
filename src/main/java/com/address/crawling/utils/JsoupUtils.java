package com.address.crawling.utils;

import com.address.crawling.constant.Constant;
import com.address.crawling.entity.BaseEntity;
import com.address.crawling.entity.Province;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Jsoup 标签内容读取
 */
public class JsoupUtils {

    public static AtomicLong CONNECT_NUMBER = new AtomicLong();

    /**
     * 获取数据的节点列表
     * @param url
     * @param dataClass
     * @return
     * @throws IOException
     */
    public static Elements getDataElements(String url, String dataClass) throws IOException {
        connectCheck();
        Document document = Jsoup.connect(url).get();
        Elements elements = document.getElementsByClass(dataClass);
        return elements;
    }

    /**
     * 防止请求太频繁导致 read timeout 出现
     */
    private static void connectCheck(){
        if(CONNECT_NUMBER.incrementAndGet() % 1000 == 0){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(CONNECT_NUMBER.get() % 1000000 == 0){
            CONNECT_NUMBER.set(0);
        }
    }

    /**
     * 读取code
     * @param tdElement
     * @param t
     * @param <T>
     */
    public static  <T extends BaseEntity> void buildCodeByTdElement(Element tdElement, T t){
        Elements codeAs = tdElement.getElementsByTag(Constant.A);
        if(codeAs.isEmpty()){
            t.setCode(tdElement.text());
        }else{
            Element codeA = codeAs.get(0);
            t.setCode(codeA.text());
        }
    }

    /**
     * 读取name和path
     * @param tdElement
     * @param t
     * @param urlPre
     * @param <T>
     */
    public static  <T extends Province> void buildNameAndPathByTdElement(Element tdElement, T t, String urlPre){
        Elements nameAs = tdElement.getElementsByTag(Constant.A);
        if(nameAs.isEmpty()){
            t.setName(tdElement.text());
        }else{
            Element nameA = nameAs.get(0);
            t.setName(nameA.text());
            if(nameA.hasAttr(Constant.HREF)){
                t.setPath(urlPre + nameA.attr(Constant.HREF));
            }
        }
    }
}
