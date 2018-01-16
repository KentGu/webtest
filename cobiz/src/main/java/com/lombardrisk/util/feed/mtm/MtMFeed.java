package com.lombardrisk.util.feed.mtm;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedMtM;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for MtM feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class MtMFeed implements IFeedLogic<FeedMtM> {

    @Override
    public String convertListToXML(List<FeedMtM> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setMtM(list);
        return JaxbHelper.marshal(collection);
    }
}
