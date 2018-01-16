package com.lombardrisk.util.feed.trade.etf;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedETFTrade;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for etf trade feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class ETFTradeFeed implements IFeedLogic<FeedETFTrade> {

    @Override
    public String convertListToXML(List<FeedETFTrade> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setTrade(list);
        return JaxbHelper.marshal(collection);
    }
}
