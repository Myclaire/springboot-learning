package com.chsy.esjd.utils;

import com.chsy.esjd.pojo.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class HtmlParseUtil {
    public static void main(String[] args) throws Exception {
        new HtmlParseUtil().parseJD("码出高效").forEach(System.out::println);
    }

    public List<Content> parseJD(String keywords) throws Exception {
        //获取请求 https://search.jd.com/Search?keyword=java
        //前提需要联网
        String url = "https://search.jd.com/Search?keyword=" + keywords;
        //解析网页, jsoup返回document就是document对象
        Document document = Jsoup.parse(new URL(url), 30000);
        //所有在js中可以使用的方法，这里都可以使用
        Element element = document.getElementById("J_goodsList");
//        System.out.println(element.html());
        //获取所有的li元素
        Elements elements = element.getElementsByTag("li");

        ArrayList<Content> goodsList = new ArrayList<>();

        //获取元素中的内容,el为每一个li标签
        for (Element el : elements) {
            //关于这种图片多的网站，所有的图片都是延迟加载的
            // source-data-lazy-img
            String img = el.getElementsByTag("img").eq(0).attr("src");
//            String img = el.getElementsByTag("img").eq(0).attr("source-data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();

            goodsList.add(new Content(title, img, price));
        }
        return goodsList;
    }
}
