package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.EligibleAssetSelectionDetail;
import com.lombardrisk.pojo.Message;
import com.lombardrisk.pojo.OrganisationSimpleSearch;
import com.lombardrisk.pojo.SimpleSearch;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.openqa.selenium.TimeoutException;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.util.List;

/**
	 * Created by Kenny Wang on 2/3/2016.
	 */
public class LetterDetailsEditPage extends PageBase {
	public LetterDetailsEditPage(IWebDriverWrapper webDriverWrapper) {
	        super(webDriverWrapper);
	    }

    
	public void setLetterDetail(EligibleAssetSelectionDetail eligibleAssetSelectionDetail) throws Exception{
		boolean frame = false;
		if(element("lde.frame").isDisplayed()) {
			element("lde.frame").switchTo();
			frame = true;
		}
		if(eligibleAssetSelectionDetail.isApplyBookingAmount() != null && eligibleAssetSelectionDetail.isApplyBookingAmount())
			element("lde.remainingAmount.apply").click();
		if(eligibleAssetSelectionDetail.getNorminalAmountRequired() != null)
			element("lde.nominalAmountRequired").input(eligibleAssetSelectionDetail.getNorminalAmountRequired().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
		if(eligibleAssetSelectionDetail.getVmNorminalAmountRequired() != null)
			element("lde.vm.nominalAmountRequired").input(eligibleAssetSelectionDetail.getVmNorminalAmountRequired().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
		if(eligibleAssetSelectionDetail.getImNorminalAmountRequired() != null)
			element("lde.im.nominalAmountRequired").input(eligibleAssetSelectionDetail.getImNorminalAmountRequired().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
		if(eligibleAssetSelectionDetail.getRemainingAmount() != null) {
			element("lde.remainingAmount").input(eligibleAssetSelectionDetail.getRemainingAmount().getRealValue());
			element("lde.remainingAmount.apply").click();
		}
		if(eligibleAssetSelectionDetail.getComments() != null)
			element("lde.comments").input(eligibleAssetSelectionDetail.getComments().getRealValue());
		if(eligibleAssetSelectionDetail.getSettlementDate() != null)
			element("lde.settlementDate").selectByVisibleText(eligibleAssetSelectionDetail.getSettlementDate().getRealValue());
		if(eligibleAssetSelectionDetail.isRehypothecated() != null)
			element("lde.rehypothecated").check(eligibleAssetSelectionDetail.isRehypothecated());
		if(eligibleAssetSelectionDetail.getAssetOwner() != null) {
			OrganisationSimpleSearch organisationSimpleSearch = eligibleAssetSelectionDetail.getAssetOwner();
			SimpleSearch simpleSearch = null;
			if (organisationSimpleSearch.getShortName() != null) {
				simpleSearch = organisationSimpleSearch.getShortName();
				element("lde.assetOwner.searchType").selectByVisibleText("Short Name");
			} else if (organisationSimpleSearch.getLongName() != null) {
				simpleSearch = organisationSimpleSearch.getLongName();
				element("lde.assetOwner.searchType").selectByVisibleText("Long Name");
			} else if (organisationSimpleSearch.getCode() != null) {
				simpleSearch = organisationSimpleSearch.getCode();
				element("lde.assetOwner.searchType").selectByVisibleText("Code");
			}
			if (simpleSearch != null) {
                if(simpleSearch.getFilter() != null)
			        element("lde.assetOwner.searchFilter").input(simpleSearch.getFilter().getRealValue());
                if(simpleSearch.getLinkText() != null)
                    element("lde.assetOwner.searchLinkText", simpleSearch.getLinkText().getRealValue()).click();
            }
		}


		saveLetterDetails(eligibleAssetSelectionDetail);
		if(frame) {
			switchTo().defaultContent();
		}
	}

	public void saveLetterDetails(EligibleAssetSelectionDetail eligibleAssetSelectionDetail) throws Exception{
		//click save button to return request Type & Asset Selection page
		if(eligibleAssetSelectionDetail.getAlertHandling() != null){
			element("lde.save").click();
			try {
				waitThat().alert().toBePresent();
			}catch(TimeoutException exception){
				logger.warn("alert doesn't display");
			}
			PageHelper.handleAlter(eligibleAssetSelectionDetail.getAlertHandling());
		}else {
			element("lde.save").clickByJavaScript();
		}
	}

	public void cancelLetterDetails() throws Exception{
		element("lde.cancel").click();
	}

	public void assertAssetSelectionDetail(EligibleAssetSelectionDetail eligibleAssetSelectionDetail) throws Exception{
		if(eligibleAssetSelectionDetail != null){
			if(eligibleAssetSelectionDetail.getInstrumentId() != null)
				assertThat("lde.instrument.id").innerText().isEqualToIgnoringWhitespace(eligibleAssetSelectionDetail.getInstrumentId().getRealValue());
			if(eligibleAssetSelectionDetail.getNorminalAmountBooked() != null)
				assertThat("lde.nor.amt.book").innerText().isEqualToIgnoringWhitespace(eligibleAssetSelectionDetail.getNorminalAmountBooked().getRealValue());
			if(eligibleAssetSelectionDetail.getNorminalAmountRequired() != null)
				assertThat("lde.nominalAmountRequired").attributeValueOf("value").isEqualToIgnoringWhitespace(eligibleAssetSelectionDetail.getNorminalAmountRequired().getRealValue());
			if(eligibleAssetSelectionDetail.getVmNorminalAmountRequired() != null)
				assertThat("lde.vm.nominalAmountRequired").attributeValueOf("value").isEqualToIgnoringWhitespace(eligibleAssetSelectionDetail.getVmNorminalAmountRequired().getRealValue());
			if(eligibleAssetSelectionDetail.getImNorminalAmountRequired() != null)
				assertThat("lde.im.nominalAmountRequired").attributeValueOf("value").isEqualToIgnoringWhitespace(eligibleAssetSelectionDetail.getImNorminalAmountRequired().getRealValue());
			if(eligibleAssetSelectionDetail.getBaseCurrency() != null)
				assertThat("lde.base.ccy").innerText().isEqualToIgnoringWhitespace(eligibleAssetSelectionDetail.getBaseCurrency().getRealValue());
			if(eligibleAssetSelectionDetail.getCounterpartyExposureAmount() != null)
				assertThat("lde.cpty.expo.amt").innerText().isEqualToIgnoringWhitespace(eligibleAssetSelectionDetail.getCounterpartyExposureAmount().getRealValue());
			if(eligibleAssetSelectionDetail.getTotalAmountBooked() != null)
				assertThat("lde.tol.amt.book").innerText().isEqualToIgnoringWhitespace(eligibleAssetSelectionDetail.getTotalAmountBooked().getRealValue());
			if(eligibleAssetSelectionDetail.getRemainingAmount() != null)
				assertThat("lde.remainingAmount").attributeValueOf("value").isEqualToIgnoringWhitespace(eligibleAssetSelectionDetail.getRemainingAmount().getRealValue());
			if(eligibleAssetSelectionDetail.getComments() != null)
				assertThat("lde.comments").attributeValueOf("value").isEqualToIgnoringWhitespace(eligibleAssetSelectionDetail.getComments().getRealValue());
			if(eligibleAssetSelectionDetail.getSettlementDate() != null)
				assertThat("lde.settlementDate").selectedText().isEqualToIgnoringWhitespace(eligibleAssetSelectionDetail.getSettlementDate().getRealValue());
			if(eligibleAssetSelectionDetail.isRehypothecated() != null)
				assertThat("lde.rehypothecated").selected().isEqualTo(eligibleAssetSelectionDetail.isRehypothecated());
			if(eligibleAssetSelectionDetail.getAssetOwner() != null){
				OrganisationSimpleSearch organisationSimpleSearch = eligibleAssetSelectionDetail.getAssetOwner();
				SimpleSearch simpleSearch = null;
				if(organisationSimpleSearch.getShortName() != null){
					simpleSearch = organisationSimpleSearch.getShortName();
				}else if(organisationSimpleSearch.getLongName() != null){
					simpleSearch = organisationSimpleSearch.getLongName();
				}else if(organisationSimpleSearch.getCode() != null){
					simpleSearch = organisationSimpleSearch.getCode();
				}
				if(simpleSearch != null && simpleSearch.getFilter() != null){
					assertThat("lde.assetOwner.searchFilter").attributeValueOf("value").isEqualToIgnoringWhitespace(simpleSearch.getFilter().getRealValue());
				}
			}
		}
	}


	
}
