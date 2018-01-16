package com.lombardrisk.util.feed.asset.rating;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedAssetRating;
import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for asset rating feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class AssetRatingFeed implements IFeedLogic<FeedAssetRating> {

    @Override
    public String convertListToXML(List<FeedAssetRating> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setAsset(list);
        return JaxbHelper.marshal(collection);
    }
}
