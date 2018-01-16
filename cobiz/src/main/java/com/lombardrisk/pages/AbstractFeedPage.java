package com.lombardrisk.pages;

import com.lombardrisk.pojo.DeltaModeType;
import com.lombardrisk.pojo.Feed;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by kent gu on 7/5/2016.
 */
public abstract class AbstractFeedPage extends PageBase {
    public AbstractFeedPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * make task manager feeds with specified file on collateral module
     *
     * @param feed
     */
    public void feed(Feed feed) throws Exception {
        if (feed.getFileName() != null)
            element("tm.uploadfile").type(feed.getFileName().getRealValue());
        if (feed.getDeltaMode() != null)
            if (feed.getDeltaMode().equals(DeltaModeType.FLUSH))
                element("tm.feed.trade.mode", "1").click();
            else if (feed.getDeltaMode().equals(DeltaModeType.DELTA))
                element("tm.feed.trade.mode", "2").click();
        if (element("tm.feed.csv.separator.comma").isDisplayed())
            element("tm.feed.csv.separator.comma").check(true);
        element("tm.upload").click();
        if(!element("tm.wrong.file.specified").isDisplayed())
            waitThat("tm.uploadComplete").toBeVisible();
    }

    public <T> void feedXlsFile(Feed feed, List<T> list) throws Exception {
        Date date = getServerTime();
        for(T obj : list){
            Biz.realize(obj, date);
        }
        if(feed.getFileName() != null) {
            feed.getFileName().setValue(Biz.getXlsFeedFile(list));
        }else{
            feed.setFileName(new StringType(Biz.getXlsFeedFile(list)));
        }
        feed(feed);
    }

    public <T> void feedXlsxFile(Feed feed, List<T> list) throws Exception {
        Date date = getServerTime();
        for(T obj : list){
            Biz.realize(obj, date);
        }
        if(feed.getFileName() != null) {
            feed.getFileName().setValue(Biz.getXlsxFeedFile(list));
        }else{
            feed.setFileName(new StringType(Biz.getXlsxFeedFile(list)));
        }
        feed(feed);
    }

    public <T> void feedXmlFile(Feed feed, List<T> list) throws Exception {
        Date date = getServerTime();
        for(T obj : list){
            Biz.realize(obj, date);
        }
        if(feed.getFileName() != null) {
            feed.getFileName().setValue(Biz.getXmlFeedFile(list));
        }else{
            feed.setFileName(new StringType(Biz.getXmlFeedFile(list)));
        }
        feed(feed);
    }

    public <T> void feedCsvFile(Feed feed, List<T> list) throws Exception {
        Date date = getServerTime();
        for(T obj : list){
            Biz.realize(obj, date);
        }
        if(feed.getFileName() != null) {
            feed.getFileName().setValue(Biz.getCsvFeedFile(list));
        }else{
            feed.setFileName(new StringType(Biz.getCsvFeedFile(list)));
        }
        feed(feed);
    }

    public Date getServerTime() throws Exception{
        Date date;
        String time;
        if(element("hm.server.time", "scrollingMessage").isDisplayed()){
            time = element("hm.server.time", "scrollingMessage").getInnerText();
        }else if(element("hm.server.time", "noNewScrollingMessage").isDisplayed()){
            time = element("hm.server.time", "noNewScrollingMessage").getInnerText();
        }else{
            time = element("hm.server.time", "nonScrollingMessage").getInnerText();
        }
        if(time == null || time.equals("")) {
            date = new Date();
        }else {
            try {
                date = new Date(time);
            }catch (IllegalArgumentException iae){
                SimpleDateFormat s = new SimpleDateFormat("EEEE, d MMMM yyyy");
                date = s.parse(time);
            }
        }
        return date;
    }
}
