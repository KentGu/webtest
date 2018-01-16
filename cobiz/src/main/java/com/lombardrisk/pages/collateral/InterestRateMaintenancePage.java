package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.DailyInterestRate;

import com.lombardrisk.util.Biz;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class InterestRateMaintenancePage extends PageBase {

    public InterestRateMaintenancePage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    
    public void addInterestRateMaintenance(DailyInterestRate dir) throws Exception {

		if (dir.getDate() != null){
			element("ir.date").input(dir.getDate().getRealValue());
		}
		if (dir.getRate() != null){
			element("ir.rate").input(dir.getRate().getRealValue());
		}
		clickSave();
    }

	public void editDailyInterestRate(DailyInterestRate dir) throws Exception{
		if(dir.isRemove() != null && dir.isRemove()){
			clickRemove(dir);
		}else{
			addInterestRateMaintenance(dir);
		}
	}

	private void clickSave() throws Exception{
		element("ir.save.dailyInterestRate").click();
	}
    
    private void clickRemove(DailyInterestRate dir) throws Exception{
    	StringBuffer xpath = new StringBuffer("//tr");
    	//tr[td='10%'][td='2016-6-22']//img[@title='remove']
    	if (dir.getDate() != null){
			String date = Biz.toDate(dir.getDate().getRealValue(), "MM/dd/yyyy", "M/d/yyyy");
//			xpath.append("[td='" + date + "']");
			xpath.append("[td='").append(date).append("']");
    	}
    	if (dir.getRate() != null){
			if(!dir.getRate().getRealValue().contains("%"))
				dir.getRate().setValue(dir.getRate().getRealValue() + "%");
//    		xpath.append("[td='" + dir.getRate().getRealValue() + "']");
			xpath.append("[td='").append(dir.getRate().getRealValue()).append("']");
    	}
    	element("ir.remove", xpath.toString()).clickByJavaScript();
    }
}
