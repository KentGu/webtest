package com.lombardrisk.util.feed.trade.repo;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedRepoTrade;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for repo trade feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class RepoTradeFeed implements IFeedLogic<FeedRepoTrade> {

    @Override
    public String convertListToXML(List<FeedRepoTrade> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setTrade(list);
        return JaxbHelper.marshal(collection);
    }
}
