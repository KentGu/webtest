package com.lombardrisk.util.feed.trade;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedTrade;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for trade feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class TradeFeed implements IFeedLogic<FeedTrade> {

    @Override
    public String convertListToXML(List<FeedTrade> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setTrade(list);
        return JaxbHelper.marshal(collection);
    }
}
