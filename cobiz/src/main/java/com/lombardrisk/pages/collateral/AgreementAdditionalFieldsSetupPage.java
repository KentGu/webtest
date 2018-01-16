package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.Agreement;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;


/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementAdditionalFieldsSetupPage extends PageBase {
    public AgreementAdditionalFieldsSetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setupAgreementAdditionalFields(Agreement agmt) throws Exception {
        if (agmt.getAdditionalField() != null && agmt.getAdditionalField().size() > 0) {
            for (int i = 0; i < agmt.getAdditionalField().size(); i++) {
                if (agmt.getAdditionalField().get(i).getFieldName() != null){
                    if (agmt.getAdditionalField().get(i).getValue() != null) {
                        if (element("ap.additional.field.input",agmt.getAdditionalField().get(i).getFieldName().getRealValue()).isDisplayed()){
                            element("ap.additional.field.input", agmt.getAdditionalField().get(i).getFieldName().getRealValue()).input(agmt.getAdditionalField().get(i).getValue().getRealValue());
                        }else{
                            element("ap.additional.field.select",agmt.getAdditionalField().get(i).getFieldName().getRealValue()).selectByVisibleText(agmt.getAdditionalField().get(i).getValue().getRealValue());
                        }
                    }
                }
            }
        }
        enterNextStage();
    }
    
    /**
     * enter next stage while setup agreement
     */
    public void enterNextStage() throws Exception {
        element("ap.next").clickByJavaScript();
        PageHelper.d33640Workaround();
    }
   
}
