package com.lombardrisk.util.feed.booking;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedInventoryManager;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for inventory manager feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class InventoryManagerFeed implements IFeedLogic<FeedInventoryManager> {

    @Override
    public String convertListToXML(List<FeedInventoryManager> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setInventoryManager(list);
        return JaxbHelper.marshal(collection);
    }
}
