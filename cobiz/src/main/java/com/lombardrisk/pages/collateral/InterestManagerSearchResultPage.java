package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.locator.Locator;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Kenny Wang
 */
public final class InterestManagerSearchResultPage extends PageBase {

    public InterestManagerSearchResultPage(IWebDriverWrapper webDriverWrapper) {
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
    	element("imsr.resetAll").click();
        if(alert().isPresent()){
            alert().accept();
        }
    }
    
    public void autoEmailAll() throws Exception{
    	element("imsr.autoEmailAll").click();
    	if(alert().isPresent()){
    		alert().accept();
    	}
    }
    
    public void saveChangeAll() throws Exception{
    	element("imsr.saveChangeAll").click();
    }
    
    public void getResultInMsExcel() throws Exception{
    	element("imsr.getResultInMsExcel").click();
    }
    
    public void reset() throws Exception{
    	element("imsr.reset").click();
    }
    
    public void autoEmail() throws Exception{
    	element("imsr.autoEmail").click();
    }
    
    public void applyInterestMovement() throws Exception{
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
    	element("imsr.applyInterestMovement").fireEvent(Biz.FIRE_EVENT_ONCLICK);
    }
    
    public void saveChangeTicked() throws Exception{
    	element("imsr.saveChangeTicked").click();
    }
    
    public void switchToPayTab() throws Exception{
    	element("imsr.pay").click();
    }

    public void switchToReceiveTab() throws Exception{
    	element("imsr.receive").click();
    }

    public void switchToCapitalisePayTab() throws Exception{
    	element("imsr.capitalisePay").click();
    }

    public void switchToCapitaliseReceiveTab() throws Exception{
    	element("imsr.capitaliseReceive").click();
    }

    public void switchToGrossInterestTab() throws Exception{
    	element("imsr.grossInterest").click();
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
        if(element("imsr.event.expand", "").isDisplayed())
            expandAllEvents();
        StringBuffer attributes = new StringBuffer();
        Method[] methods = interestManagerResult.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !method.getName().equals("getName")
                    && method.invoke(interestManagerResult) != null
                    && method.getReturnType() == StringType.class) {
                StringType value = (StringType) method.invoke(interestManagerResult);
                if(method.getName().equals("getPrincipal") || method.getName().equals("getCounterparty")){
                    attributes.append("[td[contains(text(),'").append(value.getRealValue()).append("')]]");
                }else if(!method.getName().equals("getAgreedInterestAmount")
                        && !method.getName().equals("getAgreedWHTAmount")
                        && !method.getName().equals("getSettlementDate")
                        && !method.getName().equals("getResetDate")
                        && !method.getName().equals("getInterestMovementStatus")
                        && !method.getName().equals("getEventId")){
                    attributes.append("[td='").append(value.getRealValue()).append("']");
                }
            }
        }
        long index = element("im.event.id").getCellColumn();
        // get event id row
        Locator locator = locator("im.event", attributes.toString(), String.valueOf(index));
        long row = element(locator).getCellRow();
        if (interestManagerResult.getEventId() == null)
            interestManagerResult.setEventId(new StringType("0"));
        else if (interestManagerResult.getEventId().getRealValue().isEmpty()
                || (!interestManagerResult.getEventId().getRealValue().isEmpty()
                && interestManagerResult.getEventId().isApply() != null
                && interestManagerResult.getEventId().isApply()))
            interestManagerResult.getEventId().setValue("0");
        while (element(locator).isDisplayed()) {
            if ((new Integer(interestManagerResult.getEventId().getRealValue()))
                    .compareTo(new Integer(element(locator).getInnerText().trim())) < 0)
                interestManagerResult.getEventId().setValue(element(locator).getInnerText().trim());
            row++;
            locator = locator("im.event", "[" + row + "]" + attributes.toString(), String.valueOf(index));
        }
    }

    public void assertInterestEventDetail(InterestEventDetail interestEventDetail, InterestEventDetail checkInterestEventDetail) throws Exception{
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if(interestEventDetail.getEventId() == null || interestEventDetail.getEventId().getRealValue().isEmpty()
                || (interestEventDetail.getEventId().isApply() != null
                && interestEventDetail.getEventId().isApply())){
            setInterestEventDetailId(interestEventDetail);
        }
        if(checkInterestEventDetail.getPrincipal() != null)
            assertThat(locator("im.event.principal", interestEventDetail.getEventId().getRealValue()))
                    .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getPrincipal().getRealValue());
        if(checkInterestEventDetail.getCounterparty() != null)
            assertThat(locator("im.event.counterparty", interestEventDetail.getEventId().getRealValue()))
                    .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getCounterparty().getRealValue());
        if(checkInterestEventDetail.getDescription() != null)
            assertThat(locator("im.event.description", interestEventDetail.getEventId().getRealValue()))
                    .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getDescription().getRealValue());
        if(checkInterestEventDetail.getBusinessLine() != null)
            assertThat(locator("im.event.business.line", interestEventDetail.getEventId().getRealValue()))
                    .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getBusinessLine().getRealValue());
        if(checkInterestEventDetail.getModel() != null)
            assertThat(locator("im.event.model", interestEventDetail.getEventId().getRealValue()))
                    .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getModel().getRealValue());
        if(checkInterestEventDetail.getCashAssetType() != null)
            assertThat(locator("im.event.cash.asset.type", interestEventDetail.getEventId().getRealValue()))
                    .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getCashAssetType().getRealValue());
        if(checkInterestEventDetail.getInterestRule() != null)
            assertThat(locator("im.event.interest.rule", interestEventDetail.getEventId().getRealValue()))
                    .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getInterestRule().getRealValue());
        if(checkInterestEventDetail.getStartDate() != null)
            assertThat(locator("im.event.start.date", interestEventDetail.getEventId().getRealValue()))
                    .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getStartDate().getRealValue());
        if(checkInterestEventDetail.getEndDate() != null)
            assertThat(locator("im.event.end.date", interestEventDetail.getEventId().getRealValue()))
                    .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getEndDate().getRealValue());
        if(checkInterestEventDetail.getCalculatedInterestAmount() != null) {
            try {
                assertThat(locator("im.event.cal.interest.amount", interestEventDetail.getEventId().getRealValue()))
                        .innerText().isEqualToIgnoringWhitespace(format.format(
                        Double.parseDouble(checkInterestEventDetail.getCalculatedInterestAmount().getRealValue())));
            }catch (NumberFormatException nfe) {
                assertThat(locator("im.event.cal.interest.amount", interestEventDetail.getEventId().getRealValue()))
                        .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getCalculatedInterestAmount().getRealValue());
            }
        }
        if(checkInterestEventDetail.getWhtAmount() != null)
            try {
                assertThat(locator("im.event.wht.amount", interestEventDetail.getEventId().getRealValue()))
                        .innerText().isEqualToIgnoringWhitespace(format.format(Double.parseDouble(checkInterestEventDetail.getWhtAmount().getRealValue())));
            }catch (NumberFormatException nfe) {
                assertThat(locator("im.event.wht.amount", interestEventDetail.getEventId().getRealValue()))
                        .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getWhtAmount().getRealValue());
            }
        if(checkInterestEventDetail.getInterestSource() != null)
            assertThat(locator("im.event.interest.source", interestEventDetail.getEventId().getRealValue()))
                    .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getInterestSource().getRealValue());
        if(checkInterestEventDetail.getAgreedInterestAmount() != null){
            if(element("im.event.agr.interest.amount", interestEventDetail.getEventId().getRealValue(),"/input").isDisplayed()){
                try {
                    assertThat(locator("im.event.agr.interest.amount", interestEventDetail.getEventId().getRealValue(), "/input"))
                            .attributeValueOf("value").isEqualToIgnoringWhitespace(format.format(Double.parseDouble(checkInterestEventDetail.getAgreedInterestAmount().getRealValue())));
                }catch (NumberFormatException nfe) {
                    assertThat(locator("im.event.agr.interest.amount", interestEventDetail.getEventId().getRealValue(), "/input"))
                            .attributeValueOf("value").isEqualToIgnoringWhitespace(checkInterestEventDetail.getAgreedInterestAmount().getRealValue());
                }
            }else{
                try {
                    assertThat(locator("im.event.agr.interest.amount", interestEventDetail.getEventId().getRealValue(), ""))
                            .innerText().isEqualToIgnoringWhitespace(format.format(Double.parseDouble(checkInterestEventDetail.getAgreedInterestAmount().getRealValue())));
                }catch (NumberFormatException nfe) {
                    assertThat(locator("im.event.agr.interest.amount", interestEventDetail.getEventId().getRealValue(), ""))
                            .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getAgreedInterestAmount().getRealValue());
                }
            }
        }
        if(checkInterestEventDetail.getAgreedWHTAmount() != null){
            if(element("im.event.agr.wht.amount", interestEventDetail.getEventId().getRealValue(), "/input").isDisplayed()){
                try {
                    assertThat(locator("im.event.agr.wht.amount", interestEventDetail.getEventId().getRealValue(), "/input"))
                            .attributeValueOf("value").isEqualToIgnoringWhitespace(format.format(Double.parseDouble(checkInterestEventDetail.getAgreedWHTAmount().getRealValue())));
                }catch (NumberFormatException nfe) {
                    assertThat(locator("im.event.agr.wht.amount", interestEventDetail.getEventId().getRealValue(), "/input"))
                            .attributeValueOf("value").isEqualToIgnoringWhitespace(checkInterestEventDetail.getAgreedWHTAmount().getRealValue());
                }
            }else{
                try {
                    assertThat(locator("im.event.agr.wht.amount", interestEventDetail.getEventId().getRealValue(), ""))
                            .innerText().isEqualToIgnoringWhitespace(format.format(Double.parseDouble(checkInterestEventDetail.getAgreedWHTAmount().getRealValue())));
                }catch (NumberFormatException nfe) {
                    assertThat(locator("im.event.agr.wht.amount", interestEventDetail.getEventId().getRealValue(), ""))
                            .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getAgreedWHTAmount().getRealValue());
                }
            }
        }
        if(checkInterestEventDetail.getSettlementDate() != null){
            if(element("im.event.settlement.date", interestEventDetail.getEventId().getRealValue(), "//input").isDisplayed()){
                assertThat(locator("im.event.settlement.date", interestEventDetail.getEventId().getRealValue(), "//input"))
                        .attributeValueOf("value").isEqualToIgnoringWhitespace(checkInterestEventDetail.getSettlementDate().getRealValue());
            }else{
                assertThat(locator("im.event.settlement.date", interestEventDetail.getEventId().getRealValue(), ""))
                        .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getSettlementDate().getRealValue());
            }
        }
        if(checkInterestEventDetail.getResetDate() != null){
            if(element("im.event.reset.date", interestEventDetail.getEventId().getRealValue(), "//input").isDisplayed()){
                assertThat(locator("im.event.reset.date", interestEventDetail.getEventId().getRealValue(), "//input"))
                        .attributeValueOf("value").isEqualToIgnoringWhitespace(checkInterestEventDetail.getResetDate().getRealValue());
            }else{
                assertThat(locator("im.event.reset.date", interestEventDetail.getEventId().getRealValue(), ""))
                        .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getResetDate().getRealValue());
            }
        }
        if(checkInterestEventDetail.getInterestMovementStatus() != null) {
            if (checkInterestEventDetail.getInterestMovementStatus().getRealValue().trim().equals("")){
                assertThat(locator("im.event.insterest.movement.status", interestEventDetail.getEventId().getRealValue()))
                        .innerText().isEqualToIgnoringCase(checkInterestEventDetail.getInterestMovementStatus().getRealValue().trim());
            }else {
                assertThat(locator("im.event.insterest.movement.status", interestEventDetail.getEventId().getRealValue()))
                        .innerText().matches("^Applied( |-)" + checkInterestEventDetail.getInterestMovementStatus().getRealValue() + "$");
            }
        }
        if(checkInterestEventDetail.getLetterEmailStatus() != null)
            assertThat(locator("im.event.letter.email.status", interestEventDetail.getEventId().getRealValue()))
                    .innerText().isEqualToIgnoringWhitespace(checkInterestEventDetail.getLetterEmailStatus().getRealValue());
    }

    public void isInterestEventDetailExsited(InterestEventDetail interestEventDetail, boolean flag) throws Exception{
        if (interestEventDetail.getTabName() != null)
            element("im.cash.movement", interestEventDetail.getTabName().getRealValue()).click();
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        assertThat("im.event.row", getInterestEventDetailRow(interestEventDetail)).displayed().isEqualTo(flag);
    }



    public void assertMessage(InterestEventDetail interestEventDetail) throws Exception{
        if(interestEventDetail.getErrorMessage() != null){
            Message message = interestEventDetail.getErrorMessage();
            boolean flag = false;
            for(int i = 0; i < element("im.event.msg").getNumberOfMatches(); i++){
                if(element("im.event.msg").getAllMatchedElements().get(i).getInnerText().matches(message.getContent().getRealValue())) {
                    flag = true;
                    break;
                }
            }
            if(flag)
                logger.debug(message.getContent().getRealValue() + " displays");
            assertThat(locator("im.event.msg")).displayed().isEqualTo(flag);
//            if(message.getContent() != null){
//                assertThat(locator("im.event.msg", message.getContent().getRealValue())).displayed().isTrue();
//            }
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
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
    	element("imsr.resultTick", getInterestEventDetailRow(ied)).check(true);
    }
    
    public void tickInterestManagerSearchResults(List<InterestEventDetail> list) throws Exception{
    	if (list != null && list.size() > 0){
    		for (InterestEventDetail interestEventDetail : list) {
				tickInterestManagerSearchResult(interestEventDetail);
			}
    	}
    }
    
    public void inputInterestManagerSearchResult(InterestEventDetail ied) throws Exception{
    	if (ied.getAgreedInterestAmount() != null){
    		element("imsr.agreementInterestAmount",getInputInterestEventDetailRow(ied)).input(ied.getAgreedInterestAmount().getRealValue());
    	}
    	if (ied.getAgreedWHTAmount() != null){
    		element("imsr.agreementWhtAmount",getInputInterestEventDetailRow(ied)).input(ied.getAgreedWHTAmount().getRealValue());
    	}
    	if (ied.getSettlementDate() != null){
    		element("imsr.settlementDate",getInputInterestEventDetailRow(ied)).input(ied.getSettlementDate().getRealValue());
    	}
    	if (ied.getResetDate() != null){
    		element("imsr.resetDate",getInputInterestEventDetailRow(ied)).input(ied.getResetDate().getRealValue());
    	}
    	if (ied.getLinks() != null){
    		element("imsr.links",getInputInterestEventDetailRow(ied)).selectByVisibleText(ied.getLinks().value());
    		if (alert().isPresent()){
    			alert().accept();
    		}
    	}
    }
    
    public void inputInterestManagerSearchResults(List<InterestEventDetail> list) throws Exception{
    	if (list != null && list.size() > 0){
    		for (InterestEventDetail ied : list) {
				inputInterestManagerSearchResult(ied);
			}
    	}
    }

    public void expandEvent(InterestEventDetail ied) throws Exception{
        element("imsr.event.expand", getInterestEventDetailRow(ied)).click();
    }

    public void expandAllEvents() throws Exception{
        List<IWebDriverWrapper.IWebElementWrapper> list = element("imsr.event.expand", "").getAllMatchedElements();
        for(IWebDriverWrapper.IWebElementWrapper e : list){
            e.click();
        }
    }

    private String getInterestEventDetailRow(InterestEventDetail ied) throws Exception{
        StringBuffer xpath = new StringBuffer("//tr");
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        Method[] methods = ied.getClass().getDeclaredMethods();
    	for(Method method : methods){
    	    if (method.getReturnType().equals(StringType.class)
                    && method.getName().startsWith("get")
                    && method.invoke(ied) != null
                    && !method.getName().equals("getTabName")
                    && !method.getName().equals("getMovement")
                    && !method.getName().equals("getChangeDate")){
    	        String value = ((StringType) method.invoke(ied)).getRealValue();
                if (method.getName().equals("getCalculatedInterestAmount")
                        || method.getName().equals("getWhtAmount")
                        || method.getName().equals("getAgreedInterestAmount")
                        || method.getName().equals("getAgreedWHTAmount")){
                    try {
                        value = format.format(Double.parseDouble(value));
                    }catch (NumberFormatException e){
                        logger.error(e.getMessage(), e);
                    }
                }
                if (method.getName().equals("getCounterparty")) {
                    xpath.append("[td[contains(normalize-space(.),'").append(value).append("')]]");
                } else if (method.getName().equals("getAgreedInterestAmount")
                        || method.getName().equals("getAgreedWHTAmount")
                        || method.getName().equals("getSettlementDate")
                        || method.getName().equals("getResetDate")) {
                    xpath.append("[td[normalize-space(.)='").append(value).append("'] or td/input[normalize-space(@value)='").append(value).append("']]");
                } else {
                    xpath.append("[td[normalize-space(.)='").append(value).append("']]");
                }
                if (method.getName().equals("getModel"))
                    xpath.append("[td[position() = 2 and not(img[@id='ca'])]]");
            }
        }
        return xpath.toString();
    }

    private String getInputInterestEventDetailRow(InterestEventDetail ied) throws Exception{
        StringBuffer xpath = new StringBuffer("//tr");
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        Method[] methods = ied.getClass().getDeclaredMethods();
        for(Method method : methods){
            if (method.getReturnType().equals(StringType.class)
                    && method.getName().startsWith("get")
                    && method.invoke(ied) != null
                    && !method.getName().equals("getTabName")
                    && !method.getName().equals("getMovement")
                    && !method.getName().equals("getChangeDate")){
                String value = ((StringType) method.invoke(ied)).getRealValue();
                if (method.getName().equals("getCalculatedInterestAmount")
                        || method.getName().equals("getWhtAmount")
                        || method.getName().equals("getAgreedInterestAmount")
                        || method.getName().equals("getAgreedWHTAmount")){
                    try {
                        value = format.format(Double.parseDouble(value));
                    }catch (NumberFormatException e){
                        logger.error(e.getMessage(), e);
                    }
                }
                if (method.getName().equals("getCounterparty")) {
                    xpath.append("[td[contains(normalize-space(.),'").append(value).append("')]]");
                } else if (method.getName().equals("getAgreedInterestAmount")
                        || method.getName().equals("getAgreedWHTAmount")
                        || method.getName().equals("getSettlementDate")
                        || method.getName().equals("getResetDate")) {

                } else {
                    xpath.append("[td[normalize-space(.)='").append(value).append("']]");
                }
                if (method.getName().equals("getModel"))
                    xpath.append("[td[position() = 2 and not(img[@id='ca'])]]");
            }
        }
        return xpath.toString();
    }

    public String getTime() throws Exception{
        return Biz.getCollineTime(getServerTime(), 0, "M/d/yyyy");
    }

    private Date getServerTime() throws Exception{
        Date date;
        String time;
        if(element("hm.server.time", "scrollingMessage").isDisplayed()){
            time = element("hm.server.time", "scrollingMessage").getInnerText();
        }else if(element("hm.server.time", "noNewScrollingMessage").isDisplayed()){
            time = element("hm.server.time", "noNewScrollingMessage").getInnerText();
        }else{
            time = element("hm.server.time", "nonScrollingMessage").getInnerText();
        }
        if(time == null || time.equals("")) {
            date = new Date();
        }else {
            try {
                date = new Date(time);
            }catch (IllegalArgumentException iae){
                SimpleDateFormat s = new SimpleDateFormat("EEEE, d MMMM yyyy");
                date = s.parse(time);
            }
        }
        return date;
    }
}
