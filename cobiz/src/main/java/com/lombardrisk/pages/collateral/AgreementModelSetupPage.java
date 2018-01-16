package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.Agreement;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;



/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementModelSetupPage extends PageBase {
    public AgreementModelSetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setupAgreementModel(Agreement agmt) throws Exception{

        if (agmt.getModel() != null){
            if (agmt.getModel().isTradeModelDeterminedByFeed() != null){
                element("ap.model.tradeModelDeterminedByFeed").check(agmt.getModel().isTradeModelDeterminedByFeed());
            }
            if (agmt.getModel().getModelEligibilityRule() != null
                    && agmt.getModel().getModelEligibilityRule().size() > 0){
                //loop click all tab
                for(int i=0;i<agmt.getModel().getModelEligibilityRule().size();i++){
                    if (agmt.getModel().getModelEligibilityRule().get(i).getTradeRule() != null
                            && agmt.getModel().getModelEligibilityRule().get(i).getTradeRule().size() > 0){
                        for (int j=0;j<agmt.getModel().getModelEligibilityRule().get(i).getTradeRule().size()-1;j++){
                            element("ap.model.addTradeRule",String.valueOf(i+1)).click();
                        }
                    }
                    if (i<agmt.getModel().getModelEligibilityRule().size()-1)
                        element("ap.model.addEligibilityRule").click();
                }
                //set model name and eligible currency
                for (int i=0;i<agmt.getModel().getModelEligibilityRule().size();i++){
                    if (agmt.getModel().getModelEligibilityRule().get(i).getModelName() != null){
                        element("ap.modelName",String.valueOf(i)).input(agmt.getModel().getModelEligibilityRule().get(i).getModelName().getRealValue());
                    }
                    if (agmt.getModel().getModelEligibilityRule().get(i).getEligibleCurrency() != null){
                        element("ap.model.ccy",String.valueOf(i)).selectByVisibleText(agmt.getModel().getModelEligibilityRule().get(i).getEligibleCurrency().getRealValue());
                    }
                }
                //set trade rule
                for (int i=0;i<agmt.getModel().getModelEligibilityRule().size();i++){
                    for (int j=0;j<agmt.getModel().getModelEligibilityRule().get(i).getTradeRule().size();j++){
                        if (agmt.getModel().getModelEligibilityRule().get(i).getTradeRule().get(j).getField() != null){
                            element("ap.model.field",String.valueOf(i),String.valueOf(j)).selectByVisibleText(agmt.getModel().getModelEligibilityRule().get(i).getTradeRule().get(j).getField().getRealValue());
                        }
                        if (agmt.getModel().getModelEligibilityRule().get(i).getTradeRule().get(j).getEligibilityRule() != null){
                            element("ap.model.eligibilityRule",String.valueOf(i),String.valueOf(j)).selectByVisibleText(agmt.getModel().getModelEligibilityRule().get(i).getTradeRule().get(j).getEligibilityRule().getRealValue());
                        }
                        if (agmt.getModel().getModelEligibilityRule().get(i).getTradeRule().get(j).getValue() != null){
                            element("ap.model.value",String.valueOf(i),String.valueOf(j)).selectByVisibleText(agmt.getModel().getModelEligibilityRule().get(i).getTradeRule().get(j).getValue().getRealValue());
                        }
                    }
                }
            }
            if (agmt.getModel().getSettlementLevel() != null){
                element("ap.settlement.level").selectByVisibleText(agmt.getModel().getSettlementLevel().value());
            }
        }

    	enterNextStage();
    }

    public void setupAgreementModelCategory(Agreement agmt) throws Exception{
        if (agmt.getModel()!=null && agmt.getModel().getModelCategory()!=null){
            for (int i=0; i<agmt.getModel().getModelCategory().size();i++){
                element("ap.modelName",String.valueOf(i)).input(agmt.getModel().getModelCategory().get(i).getModelName().getRealValue());
                element("ap.model.desc",String.valueOf(i)).input(agmt.getModel().getModelCategory().get(i).getModelDecription().getRealValue());
                element("ap.model.category",String.valueOf(i)).selectByVisibleText(agmt.getModel().getModelCategory().get(i).getModelCategory().getRealValue());
                element("ap.model.ccy",String.valueOf(i)).selectByVisibleText(agmt.getModel().getModelCategory().get(i).getModelBaseCurrency().getRealValue());
                element("ap.master.model",String.valueOf(i)).check(agmt.getModel().getModelCategory().get(i).isMasterModel().booleanValue());
                if (i<agmt.getModel().getModelCategory().size()-1)
                    element("ap.model.addEligibilityRule").click();
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
