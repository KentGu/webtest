package com.lombardrisk.util.feed.fxrate;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedFxRate;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for fx-rate feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class FxRateFeed implements IFeedLogic<FeedFxRate> {

    @Override
    public String convertListToXML(List<FeedFxRate> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setFxrate(list);
        return JaxbHelper.marshal(collection);
    }
}
