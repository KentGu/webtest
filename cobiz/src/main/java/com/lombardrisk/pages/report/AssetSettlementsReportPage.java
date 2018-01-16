package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.AssetSettlementReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;

import org.yiwan.webcore.web.IWebDriverWrapper;



public final class AssetSettlementsReportPage extends AbstractReportPage {

    public AssetSettlementsReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setAssetSettlementsReport(AssetSettlementReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getIssuer() != null && reportFilter.getIssuer().size() > 0) {
			for (OrganisationSearch organisationSearch : reportFilter.getIssuer()){
				setIssuer(organisationSearch);
			}
		}
    	if (reportFilter.getCustodian() != null && reportFilter.getCustodian().size() > 0){
			for (OrganisationSearch organisationSearch : reportFilter.getCustodian()){
				setCustodian(organisationSearch);
			}
    	}
    	if (reportFilter.getEventCategory() != null){
    		element("rp.event.category").selectByVisibleText(reportFilter.getEventCategory().getRealValue());
    	}
    	
    	if (reportFilter.getAssetType() != null && reportFilter.getAssetType().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getAssetType()){
				setAssetType(simpleSearch);
			}
		}
    	
    	if (reportFilter.getBookingType() != null && reportFilter.getBookingType().size() > 0){
			setBookingType(reportFilter.getBookingType());
		}
    	
    	if (reportFilter.getBookingResource() != null && reportFilter.getBookingResource().size() > 0){
    		setBookingSource(reportFilter.getBookingResource());
    	}
    	if (reportFilter.getMovement() != null){
    		element("rp.movement").selectByVisibleText(reportFilter.getMovement().getRealValue());
    	}
    	if (reportFilter.getSettlementStatus() != null && reportFilter.getSettlementStatus().size() > 0){
    		setSettlementStatus(reportFilter.getSettlementStatus());
    	}
    	if (reportFilter.getAgreementDescription() != null){
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
    		element("rp.agreement.base.currency").selectByVisibleText(reportFilter.getAgreementBaseCurrency().getRealValue());
    	}
    	if (reportFilter.getPaymentInstructionsBucket() != null){
    		element("rp.payment.instructions.bucket").selectByVisibleText(reportFilter.getPaymentInstructionsBucket().getRealValue());
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
    	if (reportFilter.getAddititionalInfo1() != null){
    		setAdditionalInfo1(reportFilter.getAddititionalInfo1());
    	}
    	if (reportFilter.getAddititionalInfo2() != null){
			setAdditionalInfo2(reportFilter.getAddititionalInfo2());
    	}
    	if (reportFilter.getSettlementJms() != null){
    		element("rp.settlement.jms").selectByVisibleText(reportFilter.getSettlementJms().getRealValue());
    	}
		if (reportFilter.getReportingCurrency() != null){
			element("rp.reporting.currency").selectByVisibleText(reportFilter.getReportingCurrency().getRealValue());
		}
    	if (reportFilter.getNetBy() != null){
    		element("rp.net.by").selectByVisibleText(reportFilter.getNetBy().getRealValue());
    	}
    	if (reportFilter.getRehypothecated() != null){
    		element("rp.rehypothecated").selectByVisibleText(reportFilter.getRehypothecated().getRealValue());
    	}
    	if (reportFilter.getAssetOwner() != null && reportFilter.getAssetOwner().size() > 0){
    		for (OrganisationSearch organisationSearch : reportFilter.getPrincipal()){
    			setAssetOwner(organisationSearch);
    		}
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
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.subAgreements").check(reportFilter.isIncludeSubAgreement());
    	}
    }
    
    public void saveAsTemplate(AssetSettlementReportFilter reportFilter) throws Exception{
    	if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
   
}
