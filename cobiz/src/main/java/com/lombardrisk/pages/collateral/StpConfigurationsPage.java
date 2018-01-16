package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.StpConfiguration;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class StpConfigurationsPage extends PageBase {
    public StpConfigurationsPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void editStpConfiguation(StpConfiguration stpConfiguration) throws Exception {
        if (stpConfiguration.getEnableStp() != null){
            if (stpConfiguration.getEnableStp().value().equals("Yes")){
                element("stpconfig.enableStp.yes").click();
            }else if (stpConfiguration.getEnableStp().value().equals("No")){
                element("stpconfig.enableStp.no").click();
            }
        }
        if (stpConfiguration.getStpRule() != null && stpConfiguration.getStpRule().size() > 0){
            for (int i =0 ; i<stpConfiguration.getStpRule().size();i++){
                element("stpconfig.stpRule").selectByVisibleText(stpConfiguration.getStpRule().get(i).value());
            }
        }
        if (stpConfiguration.getInterLevelRuleLogic() != null){
            if (stpConfiguration.getInterLevelRuleLogic().value().equals("And")){
                element("stpconfig.inter.and").click();
            }else if (stpConfiguration.getInterLevelRuleLogic().value().equals("Or")){
                element("stpconfig.inter.no").click();
            }
        }
        if (stpConfiguration.getIntraLevelRuleLogic() != null){
            if (stpConfiguration.getIntraLevelRuleLogic().value().equals("And")){
                element("stpconfig.intra.and").click();
            }else if (stpConfiguration.getIntraLevelRuleLogic().value().equals("Or")){
                element("stpconfig.intra.no").click();
            }
        }
        if (stpConfiguration.getRuleEvaluationMechanism() != null){
            element("stpconfig.euleEvaluationMechanism",stpConfiguration.getRuleEvaluationMechanism().value()).click();
        }
    }

    public void save() throws Exception {
        element("stpconfig.save").click();
        PageHelper.d31489Workaround(element("hm.return.col"), this);
    }

}
