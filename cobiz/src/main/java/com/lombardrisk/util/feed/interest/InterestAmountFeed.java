package com.lombardrisk.util.feed.interest;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedInterestAmount;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for interest amount feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class InterestAmountFeed implements IFeedLogic<FeedInterestAmount> {

    @Override
    public String convertListToXML(List<FeedInterestAmount> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setInterestAmount(list);
        return JaxbHelper.marshal(collection);
    }
}
