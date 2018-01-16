package com.lombardrisk.util.feed.agreement.ia;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedExternalIA;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for external ia feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class ExternalIAFeed implements IFeedLogic<FeedExternalIA> {

    @Override
    public String convertListToXML(List<FeedExternalIA> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setExtIA(list);
        return JaxbHelper.marshal(collection);
    }
}
