package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.UmbrellaRule;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class UmbrellaRulesPage extends PageBase {

    public UmbrellaRulesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void addUmbrellaRule() throws Exception {
        element("ur.add").click();
    }

    public void inputUmbrellaRule(UmbrellaRule rule) throws Exception {
        if (rule.getUmbrellaRuleName() != null)
            element("ur.name").input(rule.getUmbrellaRuleName().getRealValue());
        if (rule.getRequirementCalculation() != null)
            element("ur.req.calc").selectByVisibleText(rule.getRequirementCalculation().value());
        if (rule.getCollateral() != null)
            element("ur.collateral").selectByVisibleText(rule.getCollateral().value());
        if (rule.getNotificationLetter() != null)
            element("ur.margin.letter").selectByVisibleText(rule.getNotificationLetter().value());
        if (rule.getInterestLetter() != null)
            element("ur.interest.letter").selectByVisibleText(rule.getInterestLetter().value());
        if (rule.isMarginLetterIncludeFund() != null)
            element("ur.margin.letter.include.fund").check(rule.isMarginLetterIncludeFund());
        if (rule.isInterestLetterIncludeFund() != null)
            element("ur.interest.letter.include.fund").check(rule.isInterestLetterIncludeFund());

    }

    public void saveUmbrellaRule() throws Exception {
        element("ur.save").click();
    }
    public void editUmbrellaRule(UmbrellaRule umbrellaRule) throws Exception {
        if (umbrellaRule.getUmbrellaRuleName() != null){
            element("ut.edit",umbrellaRule.getUmbrellaRuleName().getRealValue()).click();
        }
    }


    public void deleteUmbrellaRule(UmbrellaRule umbrellaRule) throws Exception {
        if (umbrellaRule.getUmbrellaRuleName() != null){
            element("ur.delete", umbrellaRule.getUmbrellaRuleName().getRealValue()).click();
        }
    }

    public void searchUmbrellaRule(UmbrellaRule umbrellaRule) throws Exception {
        if (umbrellaRule.getUmbrellaRuleName() != null){
            element("ur.name.search").selectByVisibleText(umbrellaRule.getUmbrellaRuleName().getRealValue());
        }
        element("ur.search.button").click();
    }
}
