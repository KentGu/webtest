package com.lombardrisk.util.feed.other;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedHolidayCentre;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for holiday centre feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class HolidayCentreFeed implements IFeedLogic<FeedHolidayCentre> {

    @Override
    public String convertListToXML(List<FeedHolidayCentre> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setHoliday(list);
        return JaxbHelper.marshal(collection);
    }
}
