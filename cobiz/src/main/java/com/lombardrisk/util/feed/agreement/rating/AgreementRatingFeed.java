package com.lombardrisk.util.feed.agreement.rating;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedAgreementRating;
import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for agreement rating feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class AgreementRatingFeed implements IFeedLogic<FeedAgreementRating> {

    @Override
    public String convertListToXML(List<FeedAgreementRating> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setRating(list);
        return JaxbHelper.marshal(collection);
    }
}
