package com.lombardrisk.util.feed.org;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedOrganisation;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for organisation feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class OrganisationFeed implements IFeedLogic<FeedOrganisation> {

    @Override
    public String convertListToXML(List<FeedOrganisation> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setOrganisation(list);
        return JaxbHelper.marshal(collection);
    }
}
