package com.lombardrisk.util.feed.other;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedStaticData;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for static data feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class StaticDataFeed implements IFeedLogic<FeedStaticData> {

    @Override
    public String convertListToXML(List<FeedStaticData> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setRefservicedata(list);
        return JaxbHelper.marshal(collection);
    }
}
