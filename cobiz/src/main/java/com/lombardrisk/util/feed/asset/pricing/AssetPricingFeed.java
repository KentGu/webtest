package com.lombardrisk.util.feed.asset.pricing;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedAssetPricing;
import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for asset pricing feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class AssetPricingFeed implements IFeedLogic<FeedAssetPricing> {

    @Override
    public String convertListToXML(List<FeedAssetPricing> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setAsset(list);
        return JaxbHelper.marshal(collection);
    }
}
