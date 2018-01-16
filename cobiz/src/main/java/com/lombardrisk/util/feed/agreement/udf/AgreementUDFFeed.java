package com.lombardrisk.util.feed.agreement.udf;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedAgreementUDF;
import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for agreement UDF feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class AgreementUDFFeed implements IFeedLogic<FeedAgreementUDF> {

    @Override
    public String convertListToXML(List<FeedAgreementUDF> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setAgreementUDF(list);
        return JaxbHelper.marshal(collection);
    }
}
