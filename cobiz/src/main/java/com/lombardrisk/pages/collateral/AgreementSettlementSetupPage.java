package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.Agreement;
import com.lombardrisk.pojo.SettlementBucket;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.util.List;


/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementSettlementSetupPage extends PageBase {
    public AgreementSettlementSetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setupAgreementSettlement(Agreement agmt) throws Exception {
        if (agmt.getSettlement()!=null)
            element("ap.settlement").selectByVisibleText(agmt.getSettlement().value());
        if (agmt.getTsaLevel()!=null)
            element("ap.tsa.level").selectByVisibleText(agmt.getTsaLevel().value());
        else if (agmt.getCashflowLevel()!=null)
            element("ap.tsa.level").selectByVisibleText(agmt.getCashflowLevel().value());
        for (SettlementBucket settlementBucket : agmt.getSettlementBucket()) {
            if (settlementBucket.getBucket() != null && settlementBucket.getBucket().getLinkText() != null && settlementBucket.getBookingType() != null && settlementBucket.getDirection() != null) {
               if (settlementBucket.getModel()!=null && settlementBucket.getModel().getModelName()!=null&&settlementBucket.getModel().getModelCategory()!=null) {
                   List<IWebDriverWrapper.IWebElementWrapper> list = element("ap.bucket.model", settlementBucket.getBookingType().value(), settlementBucket.getDirection().value()).getAllMatchedElements();
                   for (int i=0; i<list.size();i++){
                       String newModelColumn = settlementBucket.getModel().getModelName().getRealValue() + " " + settlementBucket.getModel().getModelDecription().getRealValue() + " " + settlementBucket.getModel().getModelCategory().getRealValue();
                       if(list.get(i).getInnerText().equals(newModelColumn)){
                           element("ap.bucket", settlementBucket.getBookingType().value(), settlementBucket.getDirection().value()).getAllMatchedElements().get(i).setAttribute("value", settlementBucket.getBucket().getLinkText().getRealValue());
                       }
                   }
               }else if (settlementBucket.getModel()!=null && settlementBucket.getModel().getModelName()!=null&&settlementBucket.getModel().getModelCategory() == null){
                   List<IWebDriverWrapper.IWebElementWrapper> list = element("ap.bucket.model", settlementBucket.getBookingType().value(), settlementBucket.getDirection().value()).getAllMatchedElements();
                   for (int i=0; i<list.size();i++){
                       String newModelColumn = settlementBucket.getModel().getModelName().getRealValue()+" ";
                       if(list.get(i).getInnerText().equals(newModelColumn)){
                           element("ap.bucket", settlementBucket.getBookingType().value(), settlementBucket.getDirection().value()).getAllMatchedElements().get(i).setAttribute("value", settlementBucket.getBucket().getLinkText().getRealValue());
                       }
                   }
               }else
                   element("ap.bucket", settlementBucket.getBookingType().value(), settlementBucket.getDirection().value()).setAttribute("value", settlementBucket.getBucket().getLinkText().getRealValue());
            }
        }

        enterNextStage();
    }
    /**
     * enter next stage while setup agreement
     */
    public void enterNextStage() throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("ap.next").clickByJavaScript();
        PageHelper.d33640Workaround();
    }
}
