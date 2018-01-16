package com.lombardrisk.util.feed.trade.repo;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedRepoSettlement;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for repo settlement feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class RepoSettlementFeed implements IFeedLogic<FeedRepoSettlement> {

    @Override
    public String convertListToXML(List<FeedRepoSettlement> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setRepoSettlement(list);
        return JaxbHelper.marshal(collection);
    }
}
