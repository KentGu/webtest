package com.lombardrisk.util.feed.agreement.tsa;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedPortfolioTSA;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for portfolio tsa feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class PortfolioTSAFeed implements IFeedLogic<FeedPortfolioTSA> {

    @Override
    public String convertListToXML(List<FeedPortfolioTSA> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setPortfolioTSA(list);
        return JaxbHelper.marshal(collection);
    }
}
