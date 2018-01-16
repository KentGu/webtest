package com.lombardrisk.util.feed.agreement.nav;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedNAV;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for NAV feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class NAVFeed implements IFeedLogic<FeedNAV> {

    @Override
    public String convertListToXML(List<FeedNAV> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setNAV(list);
        return JaxbHelper.marshal(collection);
    }
}
