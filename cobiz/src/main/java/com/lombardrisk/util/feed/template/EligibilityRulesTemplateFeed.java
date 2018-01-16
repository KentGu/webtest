package com.lombardrisk.util.feed.template;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedEligibilityRulesTemplate;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for eligibility template feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class EligibilityRulesTemplateFeed implements IFeedLogic<FeedEligibilityRulesTemplate> {

    @Override
    public String convertListToXML(List<FeedEligibilityRulesTemplate> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setEligibilityTemplate(list);
        return JaxbHelper.marshal(collection);
    }
}
