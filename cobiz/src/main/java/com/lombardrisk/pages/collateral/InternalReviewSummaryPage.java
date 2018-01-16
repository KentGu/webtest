package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.CallStatusType;
import com.lombardrisk.pojo.ExposureEventDetail;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.web.IWebDriverWrapper;


import org.yiwan.webcore.web.PageBase;

import com.lombardrisk.pojo.InternalReviewSummary;

import java.lang.reflect.Method;


/**
 * @author Kenny Wang
 */
public final class InternalReviewSummaryPage extends PageBase {

    public InternalReviewSummaryPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void returnToFilter() throws Exception{
    	element("irls.returnToFilter").click();
    }
    
    public void switchToCallAndRecallTab() throws Exception{
    	element("irls.callAndRecalls").click();
    }
    
    public void switchToDeliveriesAndReturnTab() throws Exception{
    	element("irls.deliveriesAndReturns").click();
    }
    
    public void switchToNoCallsTab() throws Exception{
    	element("irls.noCalls").click();
    }
    public void longView() throws Exception{
    	element("irls.longView").clickByJavaScript();
		waitThat().document().toBeReady();
		waitThat().jQuery().toBeInactive();
	}

    public void  assertInternalReviewSummary(InternalReviewSummary irs) throws Exception {
		if (irs.getVariation() != null && irs.getVariation().equalsIgnoreCase("noRecord")) {
			assertThat("irls.searchResult.message").displayed();
		}else {
			StringBuilder xpath = locateInternalReviewEvent(irs);
			if (irs.isApply() != null && ! irs.isApply()) {
				assertThat(locator("irls.event.row", xpath.toString())).displayed().isFalse();
			} else {
				assertThat(locator("irls.event.row", xpath.toString())).displayed().isTrue();
			}
		}
	}

	private StringBuilder locateInternalReviewEvent(InternalReviewSummary irs) throws Exception {
		StringBuilder xpath = new StringBuilder();
		Method[] methods = irs.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("get") && !method.getName().equals("getId")
					&& !method.getName().equals("getRef") && !method.getName().equals("getName")
					&& !method.getName().equals("getAlertHandling")
					&& method.invoke(irs) != null) {
				String columnName = Biz.getColumnName(method.getName().substring(3), "(?<!^)(?=[A-Z])");
				if (columnName.contains("Requirement In Agreement Base Ccy"))
					columnName = columnName.replace("Requirement In Agreement Base Ccy", "Requirement in");
				if (columnName.contains("Total Exposure Amount"))
					columnName = columnName.replace("Total Exposure Amount", "Total Exposure");
				if (columnName.contains("Adjusted Collateral Value"))
					columnName = columnName.replace("Adjusted Collateral Value", "Adjusted Collateral");
				if (columnName.contains("Lrfp"))
					columnName = columnName.replace("Lrfp", "*LRFP");
				if (columnName.contains("Pcso"))
					columnName = columnName.replace("Pcso", "*PCSO");
				if (columnName.contains("Lrfd"))
					columnName = columnName.replace("Lrfd", "*LRFD");
				if (columnName.contains("Lrfd"))
					columnName = columnName.replace("Lrfd", "*LRFD");
				if (columnName.contains("Notify By"))
					columnName = columnName.replace("Notify By", "Notify");
				if (columnName.contains("Resolve By"))
					columnName = columnName.replace("Resolve By", "Resolve");
				StringType stringType = null;
				String value = "";
				if (method.getReturnType().equals(StringType.class)) {
					stringType = ((StringType) method.invoke(irs));
					value = stringType.getRealValue();
				} else if (method.getReturnType().equals(CallStatusType.class))
					value = ((CallStatusType) method.invoke(irs)).value();

				if (columnName.equals("Requirement in")){
					xpath.append("[td[count(//th[a[contains(text(),'").append(columnName).append("')]]/preceding-sibling::th)+1]");
				} else if (columnName.equals("Total Exposure")){
					xpath.append("[td[count(//th[a[contains(text(),'").append(columnName).append("')]]/preceding-sibling::th)+1]");
				} else if (columnName.equals("Adjusted Collateral") || columnName.equals("Notify") || columnName.equals("Resolve")){
					xpath.append("[td[count(//th[contains(text(),'").append(columnName).append("')]/preceding-sibling::th)+1]");
				} else if (columnName.equals("Action") || columnName.equals("Principal") || columnName.equals("Counterparty") || columnName.equals("Description")){
					xpath.append("[td[count(//th[a[contains(text(),'").append(columnName).append("')]]/preceding-sibling::th)+1]");
				} else {
					xpath.append("[td[count(//th[text()='").append(columnName).append("']/preceding-sibling::th)+1]");
				}

				if (value != null && !value.equals("")) {
					xpath.append("[contains(text(),'").append(value).append("')]");
				}
				xpath.append("]");
			}
		}
		return xpath;

	}
    public void setLinks(InternalReviewSummary irs) throws Exception{
    	
    	StringBuffer xpath = new StringBuffer("//tr");
    	//tr[td='QTPPrincipal'][td='QTPCounterparty'][td='qtp_desc_1432264310629']//select[@id='linkselect']
    	if (irs.getPrincipal() != null){
//    		xpath.append("[td='"+irs.getPrincipal().getRealValue()+"']");
			xpath.append("[td='").append(irs.getPrincipal().getRealValue()).append("']");
    	}
    	if (irs.getCounterparty() != null){
//    		xpath.append("[td='"+irs.getCounterparty().getRealValue()+"']");
			xpath.append("[td='").append(irs.getCounterparty().getRealValue()).append("']");
    	}
    	if (irs.getDescription() != null){
//    		xpath.append("[td='"+irs.getDescription().getRealValue()+"']");
			xpath.append("[td='").append(irs.getDescription().getRealValue()).append("']");
    	}
//    	if (irs.getBusinessLine() != null){
//    		xpath.append("[td='"+irs.getBusinessLine().getRealValue()+"']");
//    	}
//    	if (irs.getStatementStatus() != null){
//    		xpath.append("[td='"+irs.getStatementStatus().getRealValue()+"']");
//    	}
//    	if (irs.getAction() != null){
//    		xpath.append("[td='"+irs.getAction().getRealValue()+"']");
//    	}
//    	if (irs.getReciprocity() != null){
//    		xpath.append("[td='"+irs.getReciprocity().getRealValue()+"']");
//    	}
//    	if (irs.getLrfp() != null){
//    		xpath.append("[td='"+irs.getLrfp().getRealValue()+"']");
//    	}
//    	if (irs.getPcso() != null){
//    		xpath.append("[td='"+irs.getPcso().getRealValue()+"']");
//    	}
//    	if (irs.getLrfd() != null){
//    		xpath.append("[td='"+irs.getLrfd().getRealValue()+"']");
//    	}
//    	if (irs.getType() != null){
//    		xpath.append("[td='"+irs.getType().getRealValue()+"']");
//    	}
//    	if (irs.getPrcThreshold() != null){
//    		xpath.append("[td='"+irs.getPrcThreshold().getRealValue()+"']");
//    	}
//    	if (irs.getPrcMta() != null){
//    		xpath.append("[td='"+irs.getPrcMta().getRealValue()+"']");
//    	}
//    	if (irs.getRequirementInAgreementBaseCcy() != null){
//    		xpath.append("[td='"+irs.getRequirementInAgreementBaseCcy().getRealValue()+"']");
//    	}
    	if (irs.getLinks() != null){
    		element("ims.link",xpath.toString()).selectByVisibleText(irs.getLinks().getRealValue());
    	}
    }

}