package com.lombardrisk.util.feed.interest.rate;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedInterestRate;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for interest rate feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class InterestRateFeed implements IFeedLogic<FeedInterestRate> {

    @Override
    public String convertListToXML(List<FeedInterestRate> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setInterestrate(list);
        return JaxbHelper.marshal(collection);
    }
}
