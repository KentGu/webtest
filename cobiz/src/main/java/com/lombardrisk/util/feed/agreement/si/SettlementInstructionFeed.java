package com.lombardrisk.util.feed.agreement.si;

import java.util.List;

import javax.xml.bind.JAXBException;

import org.yiwan.webcore.util.JaxbHelper;

import com.lombardrisk.pojo.FeedCollection;
import com.lombardrisk.pojo.FeedSettlementInstruction;
import com.lombardrisk.util.feed.IFeedLogic;

/**
 * Logic for settlement instruction feed.
 * Created by Lawrence Xu on 5/22/2017.
 */
public class SettlementInstructionFeed implements IFeedLogic<FeedSettlementInstruction> {

    @Override
    public String convertListToXML(List<FeedSettlementInstruction> list) throws JAXBException {
        FeedCollection collection = new FeedCollection();
        collection.setSettlementInstruction(list);
        return JaxbHelper.marshal(collection);
    }
}
