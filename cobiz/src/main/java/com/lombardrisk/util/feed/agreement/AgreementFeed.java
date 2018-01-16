package com.lombardrisk.util.feed.agreement;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedAgreement;
import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for agreement feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class AgreementFeed implements IFeedLogic<FeedAgreement> {

    @Override
    public String convertListToXML(List<FeedAgreement> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setAgreement(list);
        return JaxbHelper.marshal(collection);
    }
}
