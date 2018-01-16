package com.lombardrisk.util.feed.security;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedSecurityTemplate;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for security template feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class SecurityTemplateFeed implements IFeedLogic<FeedSecurityTemplate> {

    @Override
    public String convertListToXML(List<FeedSecurityTemplate> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setAsset(list);
        return JaxbHelper.marshal(collection);
    }
}
