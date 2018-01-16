package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.InterestEventDetail;
import com.lombardrisk.pojo.InterestEventSearch;
import com.lombardrisk.pojo.InterestLetter;
import com.lombardrisk.pojo.StringType;
import org.yiwan.webcore.locator.Locator;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Kenny Wang
 */
public final class InterestManagerArchiveSearchResultPage extends PageBase {

    public InterestManagerArchiveSearchResultPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    // export to csv
    public void exportCsv(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("ah.export.csv").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

    // export to excel
    public void exportExcel(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("ah.export.excel").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

    // export to xml
    public void exportXml(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("ah.export.xml").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

    // export to pdf
    public void exportPdf(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("ah.export.pdf").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }
    
    public void resetAll() throws Exception{
    	element("").click();
    }
    
    public void autoEmailAll() throws Exception{
    	element("").click();
    	if(alert().isPresent()){
    		alert().accept();
    	}
    }
    
    public void saveChange() throws Exception{
    	element("").click();
    }
    
    public void getResultInMsExcel() throws Exception{
    	element("").click();
    }
    
    public void reset() throws Exception{
    	element("").click();
    }
    
    public void autoEmail() throws Exception{
    	element("").click();
    }
    
    public void applyInterestMovement() throws Exception{
    	element("").click();
    }
    
    public void saveChangeTicked() throws Exception{
    	element("").click();
    }
    
    public void switchToPayTab() throws Exception{
    	element("imasr.pay").click();
    }
    public void switchToReceiveTab() throws Exception{
    	element("imasr.receive").click();
    }
    public void switchToCapitalisePayTab() throws Exception{
    	element("imasr.capitalisePay").click();
    }
    public void switchToCapitaliseReceiveTab() throws Exception{
    	element("imasr.capitaliseReceive").click();
    }
    public void switchToGrossInterestTab() throws Exception{
    	element("imasr.grossInterest").click();
    }
    
    /**
     * assert interest manager result value on the interest manager page
     *
     * @param result
     */
    public void assertInterestManagerResult(InterestEventDetail result) throws Exception {
        element("im.receive.tab").click();
        if (result.getEventId() == null || result.getEventId().getRealValue().isEmpty()) {
            setInterestEventDetailId(result);
        }
        if (result.getAgreedInterestAmount() != null) {
            assertThat("im.agreed.interest.amt", result.getEventId().getRealValue()).innerText().isEqualTo(
                    result.getAgreedInterestAmount().getRealValue());
        }
        if (result.getAgreedWHTAmount() != null) {
            assertThat("im.agreed.wht.amt", result.getEventId().getRealValue()).innerText().isEqualTo(
                    result.getAgreedWHTAmount().getRealValue());
        }
    }

    /**
     * set InterestManagerSearchResult eventId on Interest Manager Search Result page
     *
     * @param interestManagerResult
     */
    private void setInterestEventDetailId(InterestEventDetail interestManagerResult) throws Exception {
        StringBuffer attributes = new StringBuffer();
        Method[] methods = interestManagerResult.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !method.getName().equals("getName")
                    && method.invoke(interestManagerResult) != null) {
                attributes.append("[td[contains(text(),'").append(method.invoke(interestManagerResult).toString()).append("')]]");
            }
        }
        long index = element("im.event.id").getCellColumn();
        // get event id row
        Locator locator = locator("im.event", attributes.toString(), String.valueOf(index));
        long row = element(locator).getCellRow();
        while (element(locator).isDisplayed()) {
            if (interestManagerResult.getEventId() == null
                    || (new Integer(interestManagerResult.getEventId().getRealValue()))
                    .compareTo(new Integer(element(locator).getInnerText().trim())) < 0)
                interestManagerResult.getEventId().setValue(element(locator).getInnerText().trim());
            row++;
            locator = locator("im.event", "[" + row + "]" + attributes.toString(), String.valueOf(index));
        }
    }

    /**
     * get the interestManagerResult of eventId
     *
     * @param interestManagerResult
     */
    public void getSearchInterestManagerResult(InterestEventDetail interestManagerResult) throws Exception {
        setInterestEventDetailId(interestManagerResult);
    }

    /**
     * Select different value according to InterestManagerResult's links skip
     * different pages
     *
     * @param result
     * @param letter
     * @author Kelly Huang
     */
    public void viewDifferentPages(InterestEventSearch search, InterestEventDetail result, InterestLetter letter) throws Exception {
        logger.debug("enter interest manager result page.");
        if (result.getTabName() != null) {
            element("im.cash.movement", result.getTabName().getRealValue()).click();
            if (search.getDescription() != null) {
                if (search.getDescription().getFilter() != null) {
                    element("im.links", search.getDescription().getFilter().getRealValue()).selectByValue(result.getLinks().toString());
                }
                switch (result.getLinks().toString()) {
                    case "View Interest Movement":
                        element("cm.interest.manager").click();
                        break;
                    case "Reset":
                        if (alert().isPresent())
                            alert().accept();
                        break;
                    case "Create Letter":
                        if (alert().isPresent())
                            alert().accept();
                        break;
                    case "View Details":
                        element("im.get.excel").click();
                }
            }
        }

    }

    /**
     * click different button to realize Different operation in the
     * InterestManagerResult page
     *
     * @param search
     * @author Kelly Huang
     */
    public void enterDifferentPages(InterestEventSearch search) throws Exception {
        logger.debug("enter Interest Manager Search Result Page ...");
        // click ticked
        element("im.cb", search.getDescription().getFilter().getRealValue()).click();
        // click auto Email
        element("im.auto.email").click();
        // click Apply Interest Manager
        element("im.apply").click();
        // click save
        element("im.save").click();
    }
    
    public void tickInterestManagerSearchResult(InterestEventDetail ied) throws Exception{
    	StringBuffer xpath = new StringBuffer("//tr");
    	if (ied.getEventId() != null){
    		xpath.append("[td='").append(ied.getEventId().getRealValue()).append("']");
    	}
    	if (ied.getPrincipal() != null){
    		xpath.append("[td='").append(ied.getEventId().getRealValue()).append("']");
    	}
    	if (ied.getCounterparty() != null){
    		xpath.append("[td='").append(ied.getCounterparty().getRealValue()).append("']");
    	}
    	if (ied.getDescription() != null){
    		xpath.append("[td='").append(ied.getDescription().getRealValue()).append("']");
    	}
    	if (ied.getBusinessLine() != null){
    		xpath.append("[td='").append(ied.getBusinessLine().getRealValue()).append("']");
    	}
    	if (ied.getModel() != null){
    		xpath.append("[td='").append(ied.getModel().getRealValue()).append("']");
    	}
    	if (ied.getCashAssetType() != null){
    		xpath.append("[td='").append(ied.getCashAssetType().getRealValue()).append("']");
    	}
    	if (ied.getInterestRule()  != null){
    		xpath.append("[td='").append(ied.getInterestRule().getRealValue()).append("']");
    	}
    	if (ied.getStartDate() != null){
    		xpath.append("[td='").append(ied.getStartDate().getRealValue()).append("']");
    	}
    	if (ied.getEndDate() != null){
    		xpath.append("[td='").append(ied.getEndDate().getRealValue()).append("']");
    	}
    	if (ied.getCalculatedInterestAmount() != null){
    		xpath.append("[td='").append(ied.getCalculatedInterestAmount().getRealValue()).append("']");
    	}
    	if (ied.getWhtAmount() != null){
    		xpath.append("[td='").append(ied.getWhtAmount().getRealValue()).append("']");
    	}
    	if (ied.getInterestSource() != null){
    		xpath.append("[td='").append(ied.getInterestSource().getRealValue()).append("']");
    	}
    	if (ied.getAgreedInterestAmount() != null){
    		xpath.append("[td='").append(ied.getAgreedInterestAmount().getRealValue()).append("']");
    	}
    	if (ied.getAgreedWHTAmount() != null){
    		xpath.append("[td='").append(ied.getAgreedWHTAmount().getRealValue()).append("']");
    	}
    	if (ied.getSettlementDate() != null){
    		xpath.append("[td='").append(ied.getSettlementDate().getRealValue()).append("']");
    	}
    	if (ied.getResetDate() != null){
    		xpath.append("[td='").append(ied.getResetDate().getRealValue()).append("']");
    	}
    	if (ied.getInterestMovementStatus() != null){
    		xpath.append("[td='").append(ied.getInterestMovementStatus().getRealValue()).append("']");
    	}
    	if (ied.getLetterEmailStatus() != null){
    		xpath.append("[td='").append(ied.getLetterEmailStatus().getRealValue()).append("']");
    	}
    	if (ied.getDeliveryStatus() != null){
    		xpath.append("[td='").append(ied.getDeliveryStatus().getRealValue()).append("']");
    	}
    	element("",xpath.toString()).click();
    }
    
    public void tickInterestManagerSearchResults(List<InterestEventDetail> list) throws Exception{
    	if (list != null && list.size() > 0){
    		for (InterestEventDetail interestEventDetail : list) {
				tickInterestManagerSearchResult(interestEventDetail);
			}
    	}
    }
    
    public void inputInterestManagerSearchResult(InterestEventDetail ied) throws Exception{
    	StringBuffer xpath = new StringBuffer("//tr");
    	if (ied.getChangeDate() != null){
    		xpath.append("[td='").append(ied.getChangeDate().getRealValue()).append("']");
    	}
    	if (ied.getEventId() != null){
    		xpath.append("[td='").append(ied.getEventId().getRealValue()).append("']");
    	}
    	if (ied.getPrincipal() != null){
    		xpath.append("[td='").append(ied.getEventId().getRealValue()).append("']");
    	}
    	if (ied.getCounterparty() != null){
    		xpath.append("[td='").append(ied.getCounterparty().getRealValue()).append("']");
    	}
    	if (ied.getDescription() != null){
    		xpath.append("[td='").append(ied.getDescription().getRealValue()).append("']");
    	}
    	if (ied.getBusinessLine() != null){
    		xpath.append("[td='").append(ied.getBusinessLine().getRealValue()).append("']");
    	}
    	if (ied.getModel() != null){
    		xpath.append("[td='").append(ied.getModel().getRealValue()).append("']");
    	}
    	if (ied.getCashAssetType() != null){
    		xpath.append("[td='").append(ied.getCashAssetType().getRealValue()).append("']");
    	}
    	if (ied.getInterestRule()  != null){
    		xpath.append("[td='").append(ied.getInterestRule().getRealValue()).append("']");
    	}
    	if (ied.getStartDate() != null){
    		xpath.append("[td='").append(ied.getStartDate().getRealValue()).append("']");
    	}
    	if (ied.getEndDate() != null){
    		xpath.append("[td='").append(ied.getEndDate().getRealValue()).append("']");
    	}
    	if (ied.getCalculatedInterestAmount() != null){
    		xpath.append("[td='").append(ied.getCalculatedInterestAmount().getRealValue()).append("']");
    	}
    	if (ied.getWhtAmount() != null){
    		xpath.append("[td='").append(ied.getWhtAmount().getRealValue()).append("']");
    	}
    	if (ied.getInterestSource() != null){
    		xpath.append("[td='").append(ied.getInterestSource().getRealValue()).append("']");
    	}
    	if (ied.getAgreedInterestAmount() != null){
    		element("",xpath.toString()).input(ied.getAgreedInterestAmount().getRealValue());
    	}
    	if (ied.getAgreedWHTAmount() != null){
    		element("",xpath.toString()).input(ied.getAgreedWHTAmount().getRealValue());
    	}
    	if (ied.getSettlementDate() != null){
    		element("",xpath.toString()).input(ied.getSettlementDate().getRealValue());
    	}
    	if (ied.getResetDate() != null){
    		element("",xpath.toString()).input(ied.getResetDate().getRealValue());
    	}
    	if (ied.getLinks() != null){
    		element("",xpath.toString()).selectByVisibleText(ied.getLinks().value());
    	}
    }
    
    public void inputInterestManagerSearchResults(List<InterestEventDetail> list) throws Exception{
    	if (list != null && list.size() > 0){
    		for (InterestEventDetail ied : list) {
				inputInterestManagerSearchResult(ied);
			}
    	}
    }
    
}
