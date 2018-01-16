package com.lombardrisk.pages.report;




import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.*;

import org.yiwan.webcore.web.IWebDriverWrapper;


public final class AssetHoldingsAndValuationReportPage extends AbstractReportPage {


	public AssetHoldingsAndValuationReportPage(IWebDriverWrapper webDriverWrapper) {
		super(webDriverWrapper);
	}

	public void setAssetHoldingsAndValuationReport(AssetHoldingsAndValuationReportFilter reportFilter) throws Exception{
    	if (reportFilter.getPrincipal() != null && reportFilter.getPrincipal().size() > 0) {
			for (OrganisationSearch organisationSearch : reportFilter.getPrincipal()){
				setPrincipal(organisationSearch);
			}
        }
    	if (reportFilter.getCounterparty() != null && reportFilter.getCounterparty().size() > 0) {
			for (OrganisationSearch organisationSearch : reportFilter.getCounterparty()){
				setCounterParty(organisationSearch);
			}
		}

    	if (reportFilter.getIosco() != null){
    		element("rp.iosco").selectByVisibleText(reportFilter.getIosco().getRealValue());
    	}
    	 
    	if (reportFilter.getAssetType() != null && reportFilter.getAssetType().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getAssetType()){
				setAssetType(simpleSearch);
			}
		}

    	if (reportFilter.getPositionType() != null){
    		element("rp.positionType").selectByVisibleText(reportFilter.getPositionType().value());
    	}
    	if (reportFilter.getCallStatus() != null){
    		element("rp.callStatus").selectByVisibleText(reportFilter.getCallStatus().value());
    	}
    	if (reportFilter.getBookingCategory() != null && reportFilter.getBookingCategory().size() > 0){
    		for (int i = 0; i < reportFilter.getBookingCategory().size(); i++) {
				element("rp.bookingCategory").selectByVisibleText(reportFilter.getBookingCategory().get(i).value());
			}
    	}
    	if (reportFilter.getAgreementDescription() != null) {
			setAgreementDescription(reportFilter.getAgreementDescription());
        }
    	if (reportFilter.getBusinessLine() != null && reportFilter.getBusinessLine().size() > 0){
			setBusinessLine(reportFilter.getBusinessLine());
    	}
    	if (reportFilter.getCcp() != null && reportFilter.getCcp().size() > 0){
			for (SimpleSearch simpleSearch : reportFilter.getCcp()){
				setCcp(simpleSearch);
			}
    	}
    	if (reportFilter.getAgreementBaseCurrency() != null){
    		element("rp.agreement.baseCurrency").selectByVisibleText(reportFilter.getAgreementBaseCurrency().getRealValue());
    	}
    	if (reportFilter.getAgreementStatus() != null && reportFilter.getAgreementStatus().size() > 0){
    		setAgreementStatus(reportFilter.getAgreementStatus());
    	}
    	if (reportFilter.getReportingCurrency() != null){
    		element("rp.reporting.currency").selectByVisibleText(reportFilter.getReportingCurrency().getRealValue());
    	}
    	if (reportFilter.getMaturityDateFrom() != null){
    		element("rp.maturity.date.from").input(reportFilter.getMaturityDateFrom().getRealValue());
    	}
    	if (reportFilter.getMaturityDateTo() != null){
    		element("rp.maturity.date.to").input(reportFilter.getMaturityDateTo().getRealValue());
    	}
    	if (reportFilter.getSettlementDateFrom() != null){
    		element("rp.settlement.date.from").input(reportFilter.getSettlementDateFrom().getRealValue());
    	}
    	if (reportFilter.getSettlementDateTo() != null){
    		element("rp.settlement.date.to").input(reportFilter.getSettlementDateTo().getRealValue());
    	}
    	if (reportFilter.getCreationDateFrom() != null){
    		element("rp.creation.date.from").input(reportFilter.getCreationDateFrom().getRealValue());
    	}
    	if (reportFilter.getCreationDateTo() != null){
    		element("rp.creation.date.to").input(reportFilter.getCreationDateTo().getRealValue());
    	}
    	if (reportFilter.getRegion() != null){
    		element("rp.region").selectByVisibleText(reportFilter.getRegion().getRealValue());
    	}
    	if (reportFilter.getGroup() != null && reportFilter.getGroup().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getGroup()){
				setGroup(simpleSearch);
			}
    	}
    	if (reportFilter.getLinkageSet() != null && reportFilter.getLinkageSet().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getLinkageSet()){
				setLinkageSet(simpleSearch);
			}
    	}
    	if (reportFilter.getStatementSet() != null && reportFilter.getStatementSet().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getStatementSet()){
				setStatementSet(simpleSearch);
			}
    	}

    	if (reportFilter.getCustodian() != null && reportFilter.getCustodian().size() > 0){
			for (OrganisationSearch organisationSearch : reportFilter.getCustodian()){
				setCustodian(organisationSearch);
			}
    	}
    	//set ssi valus
    	if (reportFilter.getSsiValue() != null && reportFilter.getSsiValue().size() > 0){
    		for (SsiValue ssiValue : reportFilter.getSsiValue()){
				setSsiValue(ssiValue);
			}
    	}
    	
    	if (reportFilter.getAddititionalInfo1() != null){
    		setAdditionalInfo1(reportFilter.getAddititionalInfo1());
    	}
    	if (reportFilter.getAddititionalInfo2() != null){
			setAdditionalInfo2(reportFilter.getAddititionalInfo2());
    	}
    	
    	if (reportFilter.getMarginType() != null){
    		element("rp.margin.type").selectByVisibleText(reportFilter.getMarginType().value());
    	}
    	if (reportFilter.getNetBy() != null){
    		element("rp.net.by").selectByVisibleText(reportFilter.getNetBy().value());
    	}
    	if (reportFilter.getRehypothecated() != null){
    		element("rp.rehypothecated").selectByVisibleText(reportFilter.getRehypothecated().value());
    	}
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.subAgreements").check(reportFilter.isIncludeSubAgreement());
    	}
    }
	
	public void savaAsTemplate(AssetHoldingsAndValuationReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
	

    
}
