package com.lombardrisk.util.feed.other;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedWeekend;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for weekend feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class WeekendFeed implements IFeedLogic<FeedWeekend> {

    @Override
    public String convertListToXML(List<FeedWeekend> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setWeekend(list);
        return JaxbHelper.marshal(collection);
    }
}
