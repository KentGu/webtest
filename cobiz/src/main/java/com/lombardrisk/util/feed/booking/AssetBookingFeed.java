package com.lombardrisk.util.feed.booking;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedAssetBooking;
import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for asset booking feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class AssetBookingFeed implements IFeedLogic<FeedAssetBooking> {

    @Override
    public String convertListToXML(List<FeedAssetBooking> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setAssetBooking(list);
        return JaxbHelper.marshal(collection);
    }
}
