package com.lombardrisk.pages;


import com.lombardrisk.pojo.*;


import org.yiwan.webcore.web.IWebDriverWrapper;

import java.util.List;


public abstract class AbstractReportPage extends AbstractOutputParametersPage {

    public AbstractReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setOrganisation(OrganisationSearch organisationSearch) throws  Exception{
		element("rp.search.org").click();
		element("rp.search.org.frame").switchTo();
		searchOrganisation(organisationSearch);
		element("rp.frame.close").click();
		switchTo().defaultContent();
	}
    
    
    public void setOrganisationGroup(OrganisationSearch organisationSearch) throws  Exception{
		element("rp.search.orgGroup").click();
		element("rp.search.orgGroup.frame").switchTo();
		searchOrganisation(organisationSearch);
		element("rp.frame.close").click();
		switchTo().defaultContent();
	}
    public void setPrincipalDeliveryGroup(OrganisationSearch organisationSearch) throws  Exception{
  		element("rp.search.prinGroup").click();
  		element("rp.search.prinGroup.frame").switchTo();
  		searchOrganisation(organisationSearch);
  		element("rp.frame.close").click();
  		switchTo().defaultContent();
  	}
    
	public void setPrincipal(OrganisationSearch organisationSearch) throws  Exception{
		element("rp.search.prin").click();
		element("rp.search.prin.frame").switchTo();
		searchOrganisation(organisationSearch);
		element("rp.frame.close").click();
		switchTo().defaultContent();
	}
	
	public void setPrincipalExclusion(OrganisationSearch organisationSearch) throws  Exception{
		element("rp.search.prinExclusion").click();
		element("rp.search.prinExclusion.frame").switchTo();
		searchOrganisation(organisationSearch);
		element("rp.frame.close").click();
		switchTo().defaultContent();
	}
	
	public void setCounterParty(OrganisationSearch organisationSearch) throws Exception{
		element("rp.search.cpty").click();
        element("rp.search.cpty.frame").switchTo();
        searchOrganisation(organisationSearch);
        element("rp.frame.close").click();
        switchTo().defaultContent();
	}
	
	public void setCounterPartyExclusion(OrganisationSearch organisationSearch) throws Exception{
		element("rp.search.cptyExclusion").click();
        element("rp.search.cptyExclusion.frame").switchTo();
        searchOrganisation(organisationSearch);
        element("rp.frame.close").click();
        switchTo().defaultContent();
	}
	
	public void setIssuer(OrganisationSearch organisationSearch) throws Exception{
		element("rp.search.issuer").click();
        element("rp.search.issuer.frame").switchTo();
        searchOrganisation(organisationSearch);
        element("rp.frame.close").click();
        switchTo().defaultContent();
	}
	
	public void setCustodian(OrganisationSearch organisationSearch) throws Exception {
		element("rp.search.custodian").click();
		element("rp.search.custodian.frame").switchTo();
		searchOrganisation(organisationSearch);
		element("rp.frame.close").click();
		switchTo().defaultContent();
	}
	
	public void setAssetOwner(OrganisationSearch organisationSearch) throws Exception {
		element("rp.search.assetOwner").click();
		element("rp.search.assetOwner.frame").switchTo();
		searchOrganisation(organisationSearch);
		element("rp.frame.close").click();
		switchTo().defaultContent();
	}
	
	public void setPartyA(SimpleSearch simpleSearch) throws  Exception{
	  	element("rp.search.partyA").click();
	  	element("rp.search.partyA.frame").switchTo();
	  	searchPartyA(simpleSearch);
	  	element("rp.frame.close").click();
	  	switchTo().defaultContent();
	}
	
	public void setSourceSystem(SimpleSearch simpleSearch) throws Exception{
		element("rp.search.sourceSystem").click();
	  	element("rp.search.sourceSystem.frame").switchTo();
	  	searchSourceSystem(simpleSearch);
	  	element("rp.frame.close").click();
	  	switchTo().defaultContent();
	}
	
	public void setAssetClass(SimpleSearch simpleSearch) throws Exception {
		element("rp.search.assetClass").click();
		element("rp.search.assetClass.frame").switchTo();
		searchAssetClass(simpleSearch);
		element("rp.frame.close").click();
		switchTo().defaultContent();
	}
	
	
	
	public void setAssetType(SimpleSearch simpleSearch) throws Exception {
		element("rp.search.assetType").click();
		element("rp.search.assetType.frame").switchTo();
		searchAssetType(simpleSearch);
		element("rp.frame.close").click();
		switchTo().defaultContent();
	}
	public void setBookingType(List<ReportBookingType> bookingTypeList) throws Exception {
		for (ReportBookingType reportBookingType : bookingTypeList){
			element("rp.bookingType").selectByVisibleText(reportBookingType.value());
		}
	}
	
	public void setBookingSource(List<StringType> bookingSourceList) throws Exception{
		for (StringType stringType : bookingSourceList) {
			element("rp.bookingSource").selectByVisibleText(stringType.getRealValue());
		}
	}
	public void setRatingAgency(List<StringType> ratingAgencyList) throws Exception{
		for (StringType stringType : ratingAgencyList) {
			element("rp.ratingAgency").selectByVisibleText(stringType.getRealValue());
		}
	}
	public void setReferenceBy(List<StringType> referenceByList) throws Exception{
		for (StringType stringType : referenceByList) {
			element("rp.referenceBy").selectByVisibleText(stringType.getRealValue());
		}
	}
	public void setAction(List<StringType> actionList) throws Exception{
		for (StringType stringType : actionList) {
			element("rp.action").selectByVisibleText(stringType.getRealValue());
		}
	}
	
	public void setSettlementStatus(List<SettlementStatusType> settlementStatusList) throws Exception{
		for (SettlementStatusType settlementStatusType : settlementStatusList) {
			element("rp.settlementStatus").selectByVisibleText(settlementStatusType.value());
		}
	}
	
	public void setCallStatus(List<StringType> callStatusList) throws Exception{
		for (StringType stringType : callStatusList) {
			element("rp.callStatus").selectByVisibleText(stringType.getRealValue());
		}
	}
	
	
	
	
	public void setAgreementDescription(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.agmt.desc.criteria").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.agmt.desc.filter").input(simpleSearch.getFilter().getRealValue());
		}
	}
	
	public void setExternalId(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.external.id.criteria").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.external.id.filter").input(simpleSearch.getFilter().getRealValue());
		}
	}
	
	public void setInstrumentId(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.instrument.id.criteria").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.instrument.id.filter").input(simpleSearch.getFilter().getRealValue());
		}
	}
	
	public void setRuleName(SimpleSearch simpleSearch) throws Exception{
		if (simpleSearch.getType() != null){
			element("rp.rule.name.criteria").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.rule.name.filter").input(simpleSearch.getFilter().getRealValue());
		}
	}
	
	
	
	public void setBusinessLine(List<ReportBusinessLineType> reportBusinessLineTypeList) throws Exception {
		for (ReportBusinessLineType reportBusinessLineType : reportBusinessLineTypeList){
			element("rp.businessLine").selectByVisibleText(reportBusinessLineType.value());
		}
	}
	public void setCcp(SimpleSearch simpleSearch) throws Exception {
		element("rp.search.ccp").click();
		element("rp.search.ccp.frame").switchTo();
		searchCcp(simpleSearch);
		element("rp.ccpFrame.close").click();
		switchTo().defaultContent();
	}
	public void setAgreementStatus(List<ReportAgreementStatusType> reportAgreementStatusTypeList) throws Exception {
		for (ReportAgreementStatusType reportAgreementStatusType  : reportAgreementStatusTypeList){
			element("rp.agreement.status").selectByVisibleText(reportAgreementStatusType.value());
		}
	}
	
	public void setDistributionMedium(List<StringType> list) throws Exception{
		for (StringType stringType : list ){
			element("rp.distribution.medium").selectByVisibleText(stringType.getRealValue());
		}
	}
	
	public void setParentRole(List<StringType> list) throws Exception{
		for (StringType stringType : list ){
			element("rp.parent.role").selectByVisibleText(stringType.getRealValue());
		}
	}
	
	public void setRegion(List<StringType> list) throws Exception{
		for (StringType stringType : list ){
			element("rp.region").selectByVisibleText(stringType.getRealValue());
		}
	}
	
	public void setOrderType(List<StringType> list) throws Exception{
		for (StringType stringType : list ){
			element("rp.orderType").selectByVisibleText(stringType.getRealValue());
		}
	}
	
	public void setProductCategory(List<StringType> list) throws Exception{
		for (StringType stringType : list ){
			element("rp.productCategory").selectByVisibleText(stringType.getRealValue());
		}
	}
	
	public void setProductType(List<StringType> list) throws Exception{
		for (StringType stringType : list ){
			element("rp.productType").selectByVisibleText(stringType.getRealValue());
		}
	}
	public void setRole(List<StringType> roleList) throws Exception{
		for (StringType stringType : roleList ){
			element("rp.role").selectByVisibleText(stringType.getRealValue());
		}
	}
	public void setStatus(List<StringType> statusList) throws Exception{
		for (StringType stringType : statusList ){
			element("rp.status").selectByVisibleText(stringType.getRealValue());
		}
	}
	
	
	
	
	

	public void setGroup(SimpleSearch simpleSearch) throws Exception {
		element("rp.search.group").click();
		element("rp.search.group.frame").switchTo();
		searchGroup(simpleSearch);
		element("rp.groupFrame.close").click();
		switchTo().defaultContent();
	}

	public void setLinkageSet(SimpleSearch simpleSearch) throws Exception {
		element("rp.search.linkageSet").click();
		element("rp.search.linkageSet.frame").switchTo();
		searchLinkageSet(simpleSearch);
		element("rp.linkageSetFrame.close").click();
		switchTo().defaultContent();
	}

	public void setStatementSet(SimpleSearch simpleSearch) throws Exception {
		element("rp.search.statementSet").click();
		element("rp.search.statementSet.frame").switchTo();
		searchStatementSet(simpleSearch);
		element("rp.statementSetFrame.close").click();
		switchTo().defaultContent();
	}
	public void setTsaRule(SimpleSearch simpleSearch) throws Exception {
		element("rp.search.tsaRule").click();
		element("rp.search.tsaRule.frame").switchTo();
		searchTsaRule(simpleSearch);
		element("rp.tsaRule.close").click();
		switchTo().defaultContent();
	}
	public void setCashflowRule(SimpleSearch simpleSearch) throws Exception {
		element("rp.search.cashflowRule").click();
		element("rp.search.cashflowRule.frame").switchTo();
		searchCashflowRule(simpleSearch);
		element("rp.tsaRule.close").click();
		switchTo().defaultContent();
	}
	

	
	
	

	public void setSsiValue(SsiValue ssiValue) throws Exception {
		element("rp.ssiValue.searchButton").click();
		element("rp.ssiValue.frame").switchTo();
		searchSsiValue(ssiValue);
		element("rp.ssiValue.frame.apply").click();
		switchTo().defaultContent();
	}
	public void setAdditionalInfo1(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.additionInfo1.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.additionInfo1.searchFilter").selectByVisibleText(simpleSearch.getFilter().getRealValue());
		}
	}
	public void setAdditionalInfo2(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.additionInfo2.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.additionInfo2.searchFilter").selectByVisibleText(simpleSearch.getFilter().getRealValue());
		}
	}
	public void setPrivilege(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.privilege.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.privilege.searchFilter").selectByVisibleText(simpleSearch.getFilter().getRealValue());
		}
	}
	public void setUser(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.user.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.user.searchFilter").selectByVisibleText(simpleSearch.getFilter().getRealValue());
		}
	}
	
	
	
	public void setDataType(List<StringType> list) throws Exception{
		for(StringType string : list){
			element("rp.dataType").selectByVisibleText(string.getRealValue());
		}
	}


	public void searchOrganisation(List<OrganisationSearch> searches) throws Exception {
		for (OrganisationSearch search : searches) {
			searchOrganisation(search);
		}
	}

	public void searchOrganisation(OrganisationSearch search) throws Exception {
		if (search.getCriteria() != null)
			element("rp.frame.search.criteria").selectByVisibleText(search.getCriteria().value());
		if (search.getType() != null)
			element("rp.frame.search.type").selectByVisibleText(search.getType().value());
		if (search.getFilter() != null)
			element("rp.frame.search.filter").input(search.getFilter().getRealValue());
		element("rp.frame.search").click();
		if (search.getLinkText() != null)
			element("rp.frame.search.result", search.getLinkText().getRealValue()).clickByJavaScript();
	}

	public void searchAssrtType(List<SimpleSearch> simpleSearches) throws Exception {
		for (SimpleSearch simpleSearch : simpleSearches){
			searchAssetType(simpleSearch);
		}
	}
	public void searchAssetType(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.assetType.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.assetType.searchFilter").input(simpleSearch.getFilter().getRealValue());
		}
		element("rp.assetTypeFrame.searchButton").click();
		if (simpleSearch.getLinkText() != null){
			element("rp.assetTypeFrame.searchLinktext",simpleSearch.getLinkText().getRealValue()).clickByJavaScript();
		}
	}
	public void searchAssrtClass(List<SimpleSearch> simpleSearches) throws Exception {
		for (SimpleSearch simpleSearch : simpleSearches){
			searchAssetClass(simpleSearch);
		}
	}
	public void searchAssetClass(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.assetClass.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.assetClass.searchFilter").input(simpleSearch.getFilter().getRealValue());
		}
		element("rp.assetClassFrame.searchButton").click();
		if (simpleSearch.getLinkText() != null){
			element("rp.assetClassFrame.searchLinktext",simpleSearch.getLinkText().getRealValue()).clickByJavaScript();
		}
	}

	public void searchCcp(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.ccp.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.ccp.searchFilter").input(simpleSearch.getFilter().getRealValue());
		}
		element("rp.ccpFrame.searchButton").click();
		if (simpleSearch.getLinkText() != null){
			element("rp.ccpFrame.searchLinktext",simpleSearch.getLinkText().getRealValue()).clickByJavaScript();
		}
	}

	public void searchCcp(List<SimpleSearch> simpleSearches) throws Exception {
		for (SimpleSearch simpleSearch : simpleSearches){
			searchCcp(simpleSearch);
		}
	}

	public void searchGroup(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.group.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.group.searchFilter").input(simpleSearch.getFilter().getRealValue());
		}
		element("rp.groupFrame.searchButton").click();
		if (simpleSearch.getLinkText() != null){
			element("rp.groupFrame.searchLinktext",simpleSearch.getLinkText().getRealValue()).clickByJavaScript();
		}
	}

	public void searchGroup(List<SimpleSearch> simpleSearches) throws Exception {
		for (SimpleSearch simpleSearch : simpleSearches){
			searchGroup(simpleSearch);
		}
	}


	public void searchLinkageSet(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.linkageSet.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.linkageSet.searchFilter").input(simpleSearch.getFilter().getRealValue());
		}
		element("rp.linkageSetFrame.searchButton").click();
		if (simpleSearch.getLinkText() != null){
			element("rp.linkageSetFrame.searchLinktext",simpleSearch.getLinkText().getRealValue()).clickByJavaScript();
		}
	}

	public void searchLinkageSet(List<SimpleSearch> simpleSearches) throws Exception {
		for (SimpleSearch simpleSearch : simpleSearches){
			searchLinkageSet(simpleSearch);
		}
	}
	
	public void searchPartyA(SimpleSearch simpleSearch) throws Exception{
		if (simpleSearch.getType() != null){
			element("rp.partyA.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.partyA.searchFilter").input(simpleSearch.getFilter().getRealValue());
		}
		element("rp.partyAFrame.searchButton").click();
		if (simpleSearch.getLinkText() != null){
			element("rp.partyA.searchLinktext",simpleSearch.getLinkText().getRealValue()).clickByJavaScript();
		}
	}
	public void searchPartyA(List<SimpleSearch> simpleSearchs) throws Exception{
		for (SimpleSearch simpleSearch : simpleSearchs) {
			searchPartyA(simpleSearch);
		}
	}
	
	
	public void searchSourceSystem(SimpleSearch simpleSearch) throws Exception{
		if (simpleSearch.getType() != null){
			element("rp.sourceSystem.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.sourceSystem.searchFilter").input(simpleSearch.getFilter().getRealValue());
		}
		element("rp.sourceSystemFrame.searchButton").click();
		if (simpleSearch.getLinkText() != null){
			element("rp.sourceSystem.searchLinktext",simpleSearch.getLinkText().getRealValue()).clickByJavaScript();
		}
	}
	
	public void searchSourceSystem(List<SimpleSearch> simpleSearchs) throws Exception{
		for (SimpleSearch simpleSearch : simpleSearchs) {
			searchSourceSystem(simpleSearch);
		}
	}
	
	
	
	

	public void searchStatementSet(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.statementSet.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.statementSet.searchFilter").input(simpleSearch.getFilter().getRealValue());
		}
		element("rp.statementSetFrame.searchButton").click();
		if (simpleSearch.getLinkText() != null){
			element("rp.statementSetFrame.searchLinktext",simpleSearch.getLinkText().getRealValue()).clickByJavaScript();
		}
	}
	
	public void searchTsaRule(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.tsaRule.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.tsaRule.searchFilter").input(simpleSearch.getFilter().getRealValue());
		}
		element("rp.tsaRuleFrame.searchButton").click();
		if (simpleSearch.getLinkText() != null){
			element("rp.tsaRuleFrame.searchLinktext",simpleSearch.getLinkText().getRealValue()).clickByJavaScript();
		}
	}

	public void searchCashflowRule(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getType() != null){
			element("rp.tsaRule.searchType").selectByVisibleText(simpleSearch.getType().value());
		}
		if (simpleSearch.getFilter() != null){
			element("rp.tsaRule.searchFilter").input(simpleSearch.getFilter().getRealValue());
		}
		element("rp.cashflowRuleFrame.searchButton").click();
		if (simpleSearch.getLinkText() != null){
			element("rp.tsaRuleFrame.searchLinktext",simpleSearch.getLinkText().getRealValue()).clickByJavaScript();
		}
	}
	
	



	public void searchStatementSet(List<SimpleSearch> simpleSearches) throws Exception {
		for (SimpleSearch simpleSearch : simpleSearches){
			searchStatementSet(simpleSearch);
		}
	}



	public void searchSsiValue(SsiValue ssiValue) throws Exception {
		if (ssiValue.getSsiHeader() != null){
			if (ssiValue.getSsiValue() != null){
				element("rp.ssiValue.frame.value",ssiValue.getSsiHeader().getRealValue()).input(ssiValue.getSsiValue().getRealValue());
			}
			if (ssiValue.isCheck() != null){
				element("rp.ssiValue.frame.tick",ssiValue.getSsiHeader().getRealValue()).check(ssiValue.isCheck());
			}
		}
	}

	public void searchSsiValue(List<SsiValue> ssiValues) throws Exception {
		for (SsiValue ssiValue : ssiValues){
			searchSsiValue(ssiValue);
		}
	}

	public void savaAsTemplate(StringType stringType) throws Exception{
		element("rp.save.as.template.name").input(stringType.getRealValue());
		element("rp.save.as.template").click();
	}
	

    public void editOutputFormat() throws Exception {
        element("rp.format.edit").click();
    }

   
}
