package com.lombardrisk.util.feed.workflow;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedCounterpartyAmount;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for counter-party amount feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class CounterpartyAmountFeed implements IFeedLogic<FeedCounterpartyAmount> {

    @Override
    public String convertListToXML(List<FeedCounterpartyAmount> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setCounterpartyAmount(list);
        return JaxbHelper.marshal(collection);
    }
}
