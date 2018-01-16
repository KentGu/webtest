package com.lombardrisk.util.feed.org.contact;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedOrganisationContact;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for organisation contact feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class OrganisationContactFeed implements IFeedLogic<FeedOrganisationContact> {

    @Override
    public String convertListToXML(List<FeedOrganisationContact> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setOrganisation(list);
        return JaxbHelper.marshal(collection);
    }
}
