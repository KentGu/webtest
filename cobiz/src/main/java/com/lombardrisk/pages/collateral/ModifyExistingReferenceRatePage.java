package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.InterestRate;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class ModifyExistingReferenceRatePage extends PageBase {

    public ModifyExistingReferenceRatePage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void inputInterestRate(InterestRate ir) throws Exception {
        if (ir.getInterestRateName() != null)
            element("ir.name").input(ir.getInterestRateName().getRealValue());
        if (ir.getDescription() != null)
            element("ir.desc").input(ir.getDescription().getRealValue());
        if (ir.getRefixFrequency() != null)
            element("ir.refix.freq").selectByVisibleText(ir.getRefixFrequency().value());
        if (ir.getStatus() != null)
            element("ir.status").selectByVisibleText(ir.getStatus().value());
        if (ir.getType() != null)
            element("ir.type").selectByVisibleText(ir.getType().value());
    }
    
    public void submitChanges() throws Exception{
    	element("ir.submit.changes").click();
    }



}
