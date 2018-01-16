package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.InterestRate;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class InterestRatesPage extends PageBase {

    public InterestRatesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void updateInterestRate(InterestRate ir) throws Exception {
    	//tr[td='WHT'][td='qtp_WHT_Rate1'][td/a/b='qtp_WHT_Rate1']//img
    	StringBuffer xpath = new StringBuffer("//tr");
    	if (ir.getInterestRateName() != null){
//    		xpath.append("[td/a/b='"+ir.getInterestRateName().getRealValue()+"']");
            xpath.append("[td/a/b='").append(ir.getInterestRateName().getRealValue()).append("']");
        }
    	if (ir.getDescription() != null){
//    		xpath.append("[td='"+ir.getDescription().getRealValue()+"']");
            xpath.append("[td='").append(ir.getDescription().getRealValue()).append("']");
    	}
    	if (ir.getType() != null){
//    		xpath.append("[td='"+ir.getType().value()+"']");
            xpath.append("[td='").append(ir.getType().value()).append("']");
    	}
    	element("ir.update",xpath.toString()).click();
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
    
    public void addMember() throws Exception{
    	element("ir.add").click();
    }

    public void changeInterestRates(InterestRate ir) throws Exception {
    	//tr[td='WHT']//a[b='qtp_WHT_Rate1']
    	StringBuffer xpath = new StringBuffer("//tr");
    	if (ir.getType() != null){
//    		xpath.append("[td='"+ir.getType().value()+"']");
            xpath.append("[td='").append(ir.getType().value()).append("']");
    	}
    	if (ir.getInterestRateName() != null){
//    		xpath.append("//a[b='"+ir.getInterestRateName().getRealValue()+"']");
            xpath.append("//a[b='").append(ir.getInterestRateName().getRealValue()).append("']");
    	}
    	element("ir.change", xpath.toString()).click();
    }


}
