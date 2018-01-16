package com.lombardrisk.pages.collateral;

import com.lombardrisk.data.ExposureManagementMenu;
import com.lombardrisk.pojo.*;

import com.lombardrisk.util.Biz;

import org.openqa.selenium.Keys;
import org.yiwan.webcore.locator.Locator;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class InventoryManagementPage extends PageBase {
    public InventoryManagementPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void expandTicks() throws Exception {
        element("em.expand.tick").click();
    }

    public void collapseTicks() throws Exception {
        element("em.collapse.tick").click();
    }

    public void autoSendPartialSet() throws Exception {
        element("em.auto.send.partial.set").click();
    }

    public void autoSendAll() throws Exception {
        element("em.auto.send.all").click();
    }

    public void autoPopulateCounterpartyAmount() throws Exception {
        element("em.populateCtpyAmt").click();
    }

    public void enterBulkBooking() throws Exception {
        element("em.bulk.booking").click();
    }

    public void enterInstrumentBooking() throws Exception {
        element("em.instr.booking").click();
    }

    public void enterGroupBooking() throws Exception {
        element("em.book.asset").click();
    }

    public void showLetterLog() throws Exception{
        element("em.show.letter.log").click();
    }

    public void showReformProcessSummary() throws Exception{
        element("em.reform.process.summary").click();
    }

    public void showReformException() throws Exception{
        element("em.reform.exception").click();
    }

    public void showExposureManagement() throws Exception{
        element("em.action.exposure.management").click();
    }

    public void autoBooking() throws Exception{
        element("em.action.exposure.management").click();
    }

    public void searchEvent(ExposureEventSearch ees) throws Exception{
        if (ees.getAgrId() != null && !ees.getAgrId().isEmpty()){
            StringBuffer value = new StringBuffer();
            for (int i=0; i<ees.getAgrId().size();i++){
                StringType agrId = ees.getAgrId().get(i);
                value = value.append(agrId.getRealValue());
                if(i != ees.getAgrId().size()-1)
                    value = value.append(",");
            }
            element("em.searchevent.agreement.id").setValue(value.toString());
            element("em.searchevent.agreement.id").type(Keys.ENTER);
        }

        if (ees.getPrincipal() != null && !ees.getPrincipal().isEmpty()){
            StringBuffer value = new StringBuffer();
            for (int i=0; i<ees.getPrincipal().size();i++){
                StringType principal = ees.getPrincipal().get(i);
                value = value.append(principal.getRealValue());
                if(i != ees.getPrincipal().size()-1)
                    value = value.append(",");
            }
            element("em.searchevent.principal").setValue(value.toString());
            element("em.searchevent.principal").type(Keys.ENTER);
        }

        if (ees.getCounterparty() != null && !ees.getCounterparty().isEmpty()) {
            StringBuffer value = new StringBuffer();
            for (int i=0; i<ees.getCounterparty().size();i++){
                StringType counterparty = ees.getCounterparty().get(i);
                value = value.append(counterparty.getRealValue());
                if(i != ees.getCounterparty().size()-1)
                    value = value.append(",");
            }
            element("em.searchevent.counterparty").setValue(value.toString());
            element("em.searchevent.counterparty").type(Keys.ENTER);
        }

        if (ees.getExternalId() != null && !ees.getExternalId().getRealValue().isEmpty()) {
            element("em.searchevent.external.id").setValue(ees.getExternalId().getRealValue());
            element("em.searchevent.external.id").type(Keys.ENTER);
        }

        if (ees.getEventDate() != null && !ees.getEventDate().getRealValue().isEmpty()) {
            element("em.searchevent.event.date").setValue(ees.getEventDate().getRealValue());
            element("em.searchevent.event.date").type(Keys.ENTER);
        }

        if (ees.getDescription() != null && !ees.getDescription().isEmpty()) {
            StringBuffer value = new StringBuffer();
            for (int i=0; i<ees.getDescription().size();i++){
                StringType description = ees.getDescription().get(i);
                value = value.append(description.getRealValue());
                if(i != ees.getDescription().size()-1)
                    value = value.append(",");
            }
            element("em.searchevent.description").setValue(value.toString());
            element("em.searchevent.description").type(Keys.ENTER);
        }

        if (ees.getCallStatus() != null)
            element("em.searchevent.callstatus").selectByVisibleText(ees.getCallStatus().value());
    }

    /**
     * Click navigate filters on the left navigation panel of exposure
     * management
     *
     * @param filter a sample as "Margin Filters>Substitution" filter
     */
    public void navigate(ExposureManagementMenu filter) throws Exception {
        navigate(filter.getName());
    }

    /**
     * navigate to such EM by menu on the left panel of report list
     *
     * @param exposureManagementMenu
     */
    private void navigate(String exposureManagementMenu) throws Exception {
        String[] layer = exposureManagementMenu.split(Biz.SEPARATOR);
        element("em.menu", layer[0]).click();
        if (layer.length < 3) {
            element("em.menu2", layer[0], layer[1]).click();
        } else {

        }
    }

    /**
     * set event id into the test data by specified condition
     *
     * @param em
     */
    private void setEventId(ExposureEventDetail em) throws Exception {
        String id = element("em.event.cell", getEventAttributes(em)).getInnerText();
        //String id = getMaxEventId(em);
        if (em.getEventId() == null) {
            StringType value = new StringType(id);
            em.setEventId(value);
        } else {
            em.getEventId().setValue(id);
        }

        //TODO get max id if multiple record matched
//        // click + image if it exists for non-cash booking
//        element("ag.booking.expand2", attributes.toString()).clickSmartly();
//        // get the id column index
//        long index = element("ag.booking.id.col").getCellColumn();
//        // get the specified booking id row
//        Locator locator = locator("ag.booking.result.cell", attributes.toString(), String.valueOf(index));
//        long row = element(locator).getCellRow();
//        // set max booking id for cash or non-cash booking
//        while (element(locator).isDisplayed()) {
//            if (em.getEventId() == null
//                    || (new Integer(em.getBookingId().getRealValue())).compareTo(new Integer(element(locator).getInnerText().trim())) < 0) {
//                String id = element(locator).getInnerText().trim();
//                if (em.getEventId() != null)
//                    em.getEventId().setValue(id);
//                else
//                    em.setEventId(new StringType(id));
//            }
//
//            row++;
//            locator = locator("ag.booking.result.cell", "[" + row + "]" + attributes.toString(), String.valueOf(index));
//        }
    }

    @Deprecated
    private String getMaxEventId(ExposureEventDetail exposureEventDetail) throws Exception{
        int i,eventId;
        i = (int) element("em.result.table").getRowCount()-1;
        eventId = 0;
        String xpath = "//table[@id='jqGrid']//tr" + getEventAttributes(exposureEventDetail);
        do {
//            if(!element("em.event.row",xpath + "[" + i + "]").isDisplayed() &&
//                    element("em.event.expand",getEventAttributes(exposureEventDetail)).getAttribute("class").contains("add"))
//                element("em.event.expand",getEventAttributes(exposureEventDetail)).click();
            String v = element("em.event.row",xpath + "[" + i + "]").getAttribute("id");
            if(eventId < Integer.parseInt(v))
                eventId = Integer.parseInt(v);
            i--;
        }while (i!=1 && i>0);

        if(eventId == 0){
            throw new Exception();
        }else{
            return String.valueOf(eventId);
        }
    }

    /**
     * Get event attributes string by specified condition
     *
     * @param result
     * @return
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private String getEventAttributes(ExposureEventDetail result) throws Exception {
        StringBuffer attributes = new StringBuffer();
        Method[] methods = result.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !method.getName().equals("getId")
                    && !method.getName().equals("getRef") && method.invoke(result) != null) {
                String value;
                if (method.getReturnType().equals(CallStatusType.class)) {
                    value = ((CallStatusType) method.invoke(result)).value();
                } else {// return StringType
                    value = ((StringType) method.invoke(result)).getRealValue();
                }
//                attributes.append("[normalize-space(td[@aria-describedby='"
//                        + getHeaderId(method.getName().replaceAll("^get", "")) + "'])='" + value + "']");
                attributes.append("[normalize-space(td[@aria-describedby='")
                        .append(getHeaderId(method.getName().replaceAll("^get", "")))
                        .append("'])='").append(value).append("']");
            } else if (method.getName().equals("isStmtStatus") && method.invoke(result) != null) {
                Boolean stmtStatus = (Boolean) method.invoke(result);
                if (stmtStatus) {
//                    attributes.append("[td[@aria-describedby='" + getHeaderId("StmtStatus")
//                            + "']/img[contains(@src,'tickg')]]");
                    attributes.append("[td[@aria-describedby='").append(getHeaderId("StmtStatus")).append("']/img[contains(@src,'tickg')]]");
                } else {
//                    attributes.append("[td[@aria-describedby='" + getHeaderId("StmtStatus")
//                            + "']/img[contains(@src,'crossg')]]");
                    attributes.append("[td[@aria-describedby='").append(getHeaderId("StmtStatus")).append("']/img[contains(@src,'crossg')]]");
                }
            }
        }
        return attributes.toString();
    }

    /**
     * get exposure management search result header html id by header name
     *
     * @param header
     * @return
     */
    private String getHeaderId(String header) {
        String attribute = (new StringBuffer()).append(Character.toLowerCase(header.charAt(0)))
                .append(header.substring(1)).toString();
        switch (attribute) {
            case "Ticked":
                return "jqGrid_cb";
            case "Expand":
                return "jqGrid_treeIcon";
            case "agrId":
                return "jqGrid_agreementId";
            case "eventId":
                return "jqGrid_workflowId";
            case "externalId":
                return "jqGrid_externalId";
            case "movementRequired":
                return "jqGrid_movementRequiredId";
            case "deadline":
                return "jqGrid_deadline";
            case "letterAction":
                return "jqGrid_letterAction";
            case "eventDate":
                return "jqGrid_eventDate";
            case "principalId":
                return "jqGrid_principalId";
            case "prcCode":
                return "jqGrid_principalCode";
            case "prcShortName":
                return "jqGrid_principalName";
            case "principal":
                return "jqGrid_principalLongName";
            case "counterpartyId":
                return "jqGrid_counterpartyId";
            case "cptyCode":
                return "jqGrid_counterpartyCode";
            case "cptyShortName":
                return "jqGrid_counterpartyName";
            case "counterparty":
                return "jqGrid_counterpartyLongName";
            case "description":
                return "jqGrid_description";
            case "stmtStatus":
                return "jqGrid_statementStatus";
            case "wrkStatus":
                return "jqGrid_workflowStatus";
            case "ca":
                return "jqGrid_calcAgent";
            case "callStatus":
                return "jqGrid_marginCallStatus";
            case "udcStatus":
                return "jqGrid_userDefinedCallStatus";
            case "marginType":
                return "jqGrid_nonNet";
            case "rbc":
                return "jqGrid_released";
            case "sdb":
                return "jqGrid_sysDraftPresent";
            case "clb":
                return "jqGrid_clBreached";
            case "action":
                return "jqGrid_movementRequired";
            case "agreementRequirement":
                return "jqGrid_marginCall";
            case "counterpartyAmount":
                return "jqGrid_marginDelvCtpy";
            case "counterpartyAmountUmbrellaBaseCcy":
                return "jqGrid_marginDelvCtpyUmbBaseCcy";
            case "cev":
                return "jqGrid_verified";
            case "stdAmount":
                return "jqGrid_stdAmount";
            case "sysCalculatedAmount":
                return "jqGrid_agreedAmount";
            case "issuedValue":
                return "jqGrid_issuedValue";
            case "issuedDate":
                return "jqGrid_issueDate";
            case "userAgreedAmount":
                return "jqGrid_userAgreedAmount";
            case "overwrite":
                return "jqGrid_overwrite";
            case "agrBaseCcy":
                return "jqGrid_currency";
            case "disputeAge":
                return "jqGrid_disputeAge";
            case "disputeValue":
                return "jqGrid_disputedValue";
            case "comments":
                return "jqGrid_comments";
            case "totalExposureAmount":
                return "jqGrid_totalExposure";
            case "cptyExposure":
                return "jqGrid_cptyExposure";
            case "adjustedCollateralValue":
                return "jqGrid_adjColValue";
            case "collateralManager":
                return "jqGrid_colManager";
            case "reciprocity":
                return "jqGrid_reciprocity";
            case "type":
                return "jqGrid_agreementType";
            case "sysRequirement":
                return "jqGrid_systemReq";
            case "sysBaseCcy":
                return "jqGrid_systemBaseCcy";
            case "lrfp":
                return "jqGrid_legalFrequencyPeriod";
            case "lrfd":
                return "jqGrid_legalFrequencyDays";
            case "notifyBy":
                return "jqGrid_notifyBy";
            case "resolveBy":
                return "jqGrid_resolveBy";
            case "creditRatings":
                return "jqGrid_creditRating";
            case "eventSource":
                return "jqGrid_eventSource";
            case "initTab":
                return "jqGrid_eeInitTab";
            case "historical":
                return "jqGrid_historical";
            case "deliveryStatus":
                return "jqGrid_deliveryStatus";
            case "group":
                return "jqGrid_group";
            case "agreementStatus":
                return "jqGrid_agreementStatus";
            case "intraDay":
                return "jqGrid_intraDay";
            case "marketDataId":
                return "jqGrid_marketDataId";
            case "ccp":
                return "jqGrid_ccp";
            case "linkageSet":
                return "jqGrid_linkageSet";
            case "statementSet":
                return "jqGrid_statementSet";
            case "agreementEventDate":
                return "jqGrid_agreementEventDate";
            case "autoBookSet":
                return "jqGrid_autoBookingApplicability";
            case "tsa":
                return "jqGrid_TSA";
            case "variationMargin":
                return "jqGrid_variationMargin";
            case "accruedCoupon":
                return "jqGrid_accruedCoupon";
            case "vmNetED":
                return "jqGrid_vmNetED";
            case "imRequirement":
                return "jqGrid_imRequirement";
            case "imNetED":
                return "jqGrid_imNetED";
            case "cashNetExcess":
                return "jqGrid_cashNetExcess";
            case "netED":
                return "jqGrid_netED";
            case "vmCashBalance":
                return "jqGrid_vmCashBalance";
            case "vmSecuritiesBalance":
                return "jqGrid_vmSecuritiesBalance";
            case "imCashBalance":
                return "jqGrid_imCashBalance";
            case "imSecuritiesBalance":
                return "jqGrid_imSecuritiesBalance";
            case "notificationStatus":
                return "jqGrid_notificationStatus";
            case "custodian":
                return "jqGrid_custodian";
            case "region":
                return "jqGrid_region";
            case "valuationDate":
                return "jqGrid_valuationDate";
            case "exposureProfile":
                return "jqGrid_exposureProfile";
            case "businessLine":
                return "jqGrid_businessLine";
            case "model":
                return "jqGrid_modelName";
            case "modelId":
                return "jqGrid_modelId";
            case "distributionMedium":
                return "jqGrid_distributionMedium";
            case "receivedCounterpartyIA":
                return "jqGrid_receivedCounterpartyIA";
            case "cptyCollateralValue":
                return "jqGrid_cptyCollateralValue";
            case "interfaceSystem":
                return "jqGrid_interfaceSystem";
            case "interfaceEventId":
                return "jqGrid_interfaceEventId";
            case "interfaceStatus":
                return "jqGrid_interfaceStatus";
            case "eventDateMinute":
                return "jqGrid_eventDateMinute";
            case "interfaceReasonCodes":
                return "jqGrid_interfaceReasonCodes";
            case "instrumentID":
                return "jqGrid_instrumentIds";
            case "bookedAmountFirstLeg":
                return "jqGrid_bookedAmtFirstLeg";
            case "bookedAmountSecondLeg":
                return "jqGrid_bookedAmtSecondLeg";
            case "source":
                return "jqGrid_subEventDue";
            case "sub":
                return "jqGrid_substitution";
            case "receivedPortfolioIA":
                return "jqGrid_ctpyPortfolioIa";
            case "grossCalc":
                return "jqGrid_nettingCalc";
            case "adjustedCollateralValuePrcVM":
                return "jqGrid_adjPrincColVal";
            case "adjustedCollateralValuePrcIM":
                return "jqGrid_prcAdjIaHeld";
            case "adjustedCollateralValueCptyVM":
                return "jqGrid_adjCtpyColVal";
            case "adjustedCollateralValueCptyIM":
                return "jqGrid_cptyAdjIaHeld";
            case "reportingAgreementRequirement":
                return "jqGrid_repAgrReq";
            case "disputeReportingValue":
                return "jqGrid_disRepValue";
            case "bookedAmount":
                return "jqGrid_bookedAmt";
            case "portfolioIA":
                return "jqGrid_portfolioIa";
            case "cashComponent":
                return "jqGrid_cashComponentValue";
            case "fee":
                return "jqGrid_feeValue";
            case "reasonForSub":
                return "jqGrid_subReason";
            default:
                return (new StringBuffer()).append("jqGrid_").append(attribute).toString();
        }
    }

    public void expandEvent(ExposureEventDetail em) {

    }

    public void collapaseEvent(ExposureEventDetail em) {

    }

    /**
     * change ExposureManagement data on the EM page
     *
     * @param em1 used to identify the event
     * @param em2 used to change the event's attributes
     */
    public void changeExposureManagement(ExposureEventDetail em1, ExposureEventDetail em2) throws Exception {
        // find event id
        if (em1.getEventId() == null || em1.getEventId().getRealValue().isEmpty())
            setEventId(em1);
        em2.setEventId(em1.getEventId());
        if (em2.getCallStatus() != null){
            element("em.call.status",em2.getEventId().getRealValue()).click();
            element("em.call.status.select",em2.getEventId().getRealValue()).selectByVisibleText(
                    em2.getCallStatus().value());
        }

        if (em2.getCounterpartyAmount() != null) {
            if (em2.getCounterpartyAmount() != null) {
                element("em.cpty.amount", em2.getEventId().getRealValue()).click();
                element("em.cpty.amount.input", em2.getEventId().getRealValue()).input(
                        em2.getCounterpartyAmount().getRealValue());
                element("em.cpty.amount.input", em2.getEventId().getRealValue()).type(Keys.ENTER);
                // accept alert
                if (alert().isPresent())
                    alert().accept();
            }
        }

        if (em2.getComments() != null){
            element("em.comments",em2.getEventId().getRealValue()).click();
            element("em.comments.input",em2.getEventId().getRealValue()).input(
                    em2.getComments().getRealValue());
        }
    }

    /**
     * assert the value of EM page
     *
     * @param em
     */
    public void assertExposureManagement(ExposureEventDetail em) throws Exception {
        // find event id
        if (em.getEventId() == null || em.getEventId().getRealValue().isEmpty())
            setEventId(em);
        if (em.getCallStatus() != null)
            assertThat("em.call.status", em.getEventId().getRealValue()).innerText().isEqualTo(em.getCallStatus().value());
    }

    /**
     * Enter bulk booking page from exposure management page
     *
     * @param em
     * @author Kelly Huang
     */
    public void enterBulkBooking(ExposureEventDetail em) throws Exception {
        // tick check box
        tickEvent(em, true);

        // click make booking element
        enterBulkBooking();
    }



    /**
     * Enter instrument booking page from EM page
     *
     * @param em
     */
    public void enterInstrumentBooking(ExposureEventDetail em) throws Exception {
        // tick check box
        tickEvent(em, true);
        // click make booking element
        enterInstrumentBooking();
    }

    /**
     * make instrument booking from EM page
     *
     * @author kent gu
     */
    public void makeInstrumentBooking(BookingDetail bookingDetail) throws Exception{
        element("em.instrument.booking.id").setAttribute("value", bookingDetail.getInstrumentSearch().getInstrumentId().getLinkText().getRealValue());
        element("em.instrument.booking.ok").click();
    }



    /**
     * Enter group booking page from exposure management page
     *
     * @param em
     * @author Kelly Huang
     */
    public void enterGroupBooking(ExposureEventDetail em) throws Exception {
        // tick check box
        tickEvent(em, true);
        // click make booking element
        enterGroupBooking();
    }

    /**
     * Enter create Letter page from exposure management page
     *
     * @param em
     */
    public void enterCreateLetter(ExposureEventDetail em) throws Exception {
        // tick check box
        tickEvent(em, true);
        // click make booking element
        enterCreateLetter();
    }

    /**
     * Enter create Letter page from exposure management page without parameter
     *
     * @author kent gu
     */
    public void enterCreateLetter() throws Exception {
        element("em.create.letter").click();
    }

    /**
     * Enter auto send Letter page from exposure management page
     *
     * @param em
     */
    public void enterAutoSendLetter(ExposureEventDetail em) throws Exception {
        // tick check box
        tickEvent(em, true);
        // click make booking element
        enterAutoSendLetter();
    }

    /**
     * Enter auto send Letter page from exposure management page without parameter
     * @author kent gu
     */
    public void enterAutoSendLetter() throws Exception{
        element("em.auto.send").click();
    }

    /**
     * Enter settlement Letter page from exposure management page
     *
     * @param em
     */
    public void enterSettlement(ExposureEventDetail em) throws Exception {
        // tick check box
        tickEvent(em, true);
        // click make booking element
        enterSettlement();
    }

    /**
     * Enter settlement Letter page from exposure management page withour parameter
     *
     * @author kent gu
     */
    public void enterSettlement() throws Exception {
        element("em.settlement").click();
    }

    /**
     * Enter agreement page from exposure management page
     *
     * @param em
     */
    public void enterAgreement(ExposureEventDetail em) throws Exception {
        // tick check box
        tickEvent(em, true);
        // click make booking element
        enterAgreement();
    }

    /**
     * Enter agreement page from exposure management page without parameter
     *
     * @author kent gu
     */
    public void enterAgreement() throws Exception {
        element("em.agreement").click();
    }

    /**
     * click save changes icon from exposure management page
     *
     * @param em
     */
    public void enterSaveChanges(ExposureEventDetail em) throws Exception {
        // tick check box
        tickEvent(em, true);
        // click make booking element
        enterSaveChanges();
    }

    /**
     * click save changes icon from exposure management page without parameter
     *
     * @author kent gu
     */
    public void enterSaveChanges() throws Exception {
        element("em.save.changes").click();
    }

    /**
     * tick checkbox on or off for specified event on exposure management Page
     *
     * @param exposureEventDetail
     * @param b
     * @author Kelly Huang
     */
    public void tickEvent(ExposureEventDetail exposureEventDetail, boolean b) throws Exception {
        if (exposureEventDetail.getEventId() == null || exposureEventDetail.getEventId().getRealValue().isEmpty())
            setEventId(exposureEventDetail);
        element("em.event.cb", exposureEventDetail.getEventId().getRealValue()).check(b);
    }


    /**
     * assert event exists or not in exposure management search results
     *
     * @param em
     */
    public void assertEvent(ExposureEventDetail em, Boolean exists) throws Exception {
        String xpath = "//tr" + getEventAttributes(em);
        assertThat("em.event.row", xpath).displayed().isEqualTo(exists);
    }

    /**
     * enter agreement statement page from exposure management page
     *
     * @param em
     */
    public void enterStatement(ExposureEventDetail em) throws Exception {
        // tick check box
        tickEvent(em, true);
        // click statement
        enterStatement();
    }

    /**
     * enter agreement statement page from exposure management page withour parameter
     *
     * @author kent gu
     */
    public void enterStatement() throws Exception {
        element("em.statement").click();
    }

    public void enterArchiveStatement() throws Exception {
        element("em.archive.statement").click();
    }


    /**
     * assert bulk bookings exists or not on bulk booking page
     *
     * @param booking
     * @param exists
     */
    public void assertBulkBooking(BookingDetail booking, Boolean exists) throws Exception {
        StringBuffer xpath = new StringBuffer("//div[@id='divEventBookingPanelSet']//table[@class='displaytagTable']");
        if (booking.getInstrumentSearch().getInstrument().getFilter() != null) {
//            xpath.append("//td[@class='c_InstrumentID']//input[contains(value, '"
//                    + booking.getInstrumentSearch().getInstrument().getFilter().getRealValue() + "')]");
            xpath.append("//td[@class='c_InstrumentID']//input[contains(value, '").append(booking.getInstrumentSearch().getInstrument().getFilter().getRealValue()).append("')]");
        }
        if (booking.getInstrumentSearch().getLinkText() != null) {
//            xpath.append("//td[@class='c_AssetName']//input[contains(value, '"
//                    + booking.getInstrumentSearch().getLinkText() + "')]");
            xpath.append("//td[@class='c_AssetName']//input[contains(value, '").append(booking.getInstrumentSearch().getLinkText()).append("')]");
        }
        assertThat("em.event.row", xpath.toString()).displayed().isEqualTo(exists);
        element("em.cancel.booking").click();
    }

    /**
     * enter BulkBooking page edit
     *
     * @param list
     */
    public void makeBulkBooking(List<BookingDetail> list) throws Exception {
        int rowCount = (int) element("em.bulk.booking.table").getRowCount();
        if (list != null && list.size() > 0) {
            int row = 1;
            for (BookingDetail booking : list) {
                int count = 0;
//                if (row > rowCount)
//                    element("em.add.booking").click();
                Locator locator = locator("em.instru.id", String.valueOf(row));
                if (booking.getInstrumentSearch() != null) {
                    element(locator).input(booking.getInstrumentSearch().getInstrument().getFilter().getRealValue());
                    //element(locator).fireEvent("keyup");
                    //element(locator).fireEvent("blur");
                    if (booking.getInstrumentSearch().getAssetType() != null) {
                        element(locator).input(booking.getInstrumentSearch().getAssetType().getRealValue());
                    }
                }
                // there is a bug when bookingInformationDetails has more than 1 booking
                List<BookingInformationDetail> bookingInformationDetails = booking.getBookingInformation().getBookingInformationDetail();
                if (bookingInformationDetails != null && bookingInformationDetails.size() > 0) {
                    for (BookingInformationDetail bookingInformationDetail : bookingInformationDetails) {
                        if (bookingInformationDetail.getNominalAmount() != null)
                            element("em.nominal.amt",String.valueOf(row)).click();
                            element("em.nominal.amt", String.valueOf(row)).input(bookingInformationDetail.getNominalAmount().getRealValue());
                        if (bookingInformationDetail.getVmAmount() != null)
                            element("em.vm.amt", String.valueOf(row)).input(bookingInformationDetail.getVmAmount().getRealValue());
                        if (bookingInformationDetail.getImAmount() != null)
                            element("em.im.amt", String.valueOf(row)).input(bookingInformationDetail.getImAmount().getRealValue());
                        if (bookingInformationDetail.getMovement() != null){
                            element("em.movement", String.valueOf(row)).input(bookingInformationDetail.getMovement().value());
                        }
                        if (booking.getBookingInformation().getSettlementDate() != null){
                            element("em.settlement.date", String.valueOf(row)).setAttribute("value", booking.getBookingInformation().getSettlementDate().getRealValue());
                        }
                        if (booking.getPrincipalPaymentInstructions() != null){
                        	if (booking.getPrincipalPaymentInstructions().getCapitalPayInstr() != null){
                        		element("em.prc.ssi",String.valueOf(row)).selectByVisibleText(booking.getPrincipalPaymentInstructions().getCapitalPayInstr().getRealValue());
                        	}
                        }
                        if (booking.getCounterpartyPaymentInstructions() != null){
                        	if (booking.getCounterpartyPaymentInstructions().getCapitalPayInstr() != null){
                        		element("em.cpty.ssi",String.valueOf(row)).selectByVisibleText(booking.getCounterpartyPaymentInstructions().getCapitalPayInstr().getRealValue());
                        	}
                        }
                        if (bookingInformationDetail.getValue() != null){
                        	element("em.value", String.valueOf(row)).input(bookingInformationDetail.getValue().getRealValue());
                        }
                        if (bookingInformationDetail.getRequiredAmount() != null){
                        	element("em.required.amount",String.valueOf(row)).input(bookingInformationDetail.getRequiredAmount().getRealValue());
                        }
                        if (bookingInformationDetail.getAssetAvailable() != null){
                        	element("em.asset.available",String.valueOf(row)).input(bookingInformationDetail.getAssetAvailable().getRealValue());
                        }
                    }

                }
                if (count < list.size()){
                    element("em.add.bookingInformation",String.valueOf(row)).clickByJavaScript();
                }
                count ++;
                row++;
            }
        }
        element("em.save.booking").click();
//        waitBlockUI();
    }
    
//    /**
//     * @param list
//     * @throws Exception
//     */
//    public void makeBulkBooking(List<BookingDetail> list) throws Exception{
//    	int rowCount = (int) element("em.bulk.booking.table").getRowCount();
//    	String agreementName = null;
//    	List<String> agreementNameList=null;
//    	if(list != null && list.size() > 0){
//    			for (int i = 0; i < list.size(); i++) {
//					if (list.get(i).getBookingInformation().getBookingInformationDetail() != null
//							&& list.get(i).getBookingInformation().getBookingInformationDetail().size() >0){
//							for (int j = 0; j < list.get(i).getBookingInformation().getBookingInformationDetail().size(); j++) {
//								if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getAgreementName() != null){
//									agreementName = list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getAgreementName().getRealValue();
//									agreementNameList.add(agreementName);
//										if (agreementName != null &&
//												(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getAgreementDiscription() == null
//												|| list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getPrincipalShortName() == null
//												|| list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getCounterpartyShortName() == null
//												|| list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getAgreementId() == null)){
//											throw new IllegalArgumentException("you must prepara agreement name.");
//
//								}
//							}
//						}
//				}
//    		}
//    	if (list != null){
//    		if(list.size() > 0){
//    			int row = 1;
//    			for (int i = 0; i < list.size(); i++) {
//    				if (list.get(i).getInstrumentSearch() != null){
//    					if (list.get(i).getInstrumentSearch().getInstrument() != null){
//    						if (list.get(i).getInstrumentSearch().getInstrument().getFilter() != null){
//    							//bookingDetail instrument id
//    							element("em.instru.id",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getInstrumentSearch().getInstrument().getFilter().getRealValue());
//    						}
//    					}
//    					if (list.get(i).getInstrumentSearch().getAssetType() != null){
//    						//bookingDetail assetName
//    						element("em.asset.name",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getInstrumentSearch().getAssetType().getRealValue());
//    					}
//    					if (list.get(i).getPrincipalPaymentInstructions() != null){
//    						//bookingDetail prcSSI
//    						if (list.get(i).getPrincipalPaymentInstructions().getCapitalPayInstr() != null){
//    							element("em.prc.ssi",agreementNameList.get(i),String.valueOf(row)).selectByVisibleText(list.get(i).getPrincipalPaymentInstructions().getCapitalPayInstr().getRealValue());
//    						}
//    					}
//    					if (list.get(i).getCounterpartyPaymentInstructions() != null){
//    						//bookingDetail cptySSI
//    						if (list.get(i).getCounterpartyPaymentInstructions().getCapitalPayInstr() != null){
//    							element("em.cpty.ssi",agreementNameList.get(i),String.valueOf(row)).selectByVisibleText(list.get(i).getCounterpartyPaymentInstructions().getCapitalPayInstr().getRealValue());
//    						}
//    					}
//    				}
//    				if (list.get(i).getBookingInformation() != null){
//    					//bookingInformation settlementDate
//    					if (list.get(i).getBookingInformation().getSettlementDate() != null){
//    						element("em.settlement.date",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getBookingInformation().getSettlementDate().getRealValue());
//    					}
//    					if (list.get(i).getBookingInformation().getBookingInformationDetail() != null){
//    						if (list.get(i).getBookingInformation().getBookingInformationDetail().size() > 0){
//    							for (int j = 0; j < list.get(i).getBookingInformation().getBookingInformationDetail().size(); j++) {
//    								//bookingInformationDetail notional
//									if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getNominalAmount() != null){
//										element("em.nominal.amt",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getNominalAmount().getRealValue());
//									}
//									//bookingInformationDetail vm notional
//									if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getVmAmount() != null){
//										element("em.vm.amt",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getVmAmount().getRealValue());
//									}
//									//bookingInformationDetail im notional
//									if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getImAmount() != null){
//										element("em.im.amt",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getImAmount().getRealValue());
//									}
//									//bookingInformationDetail value
//									if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getValue() != null){
//										element("em.value",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getValue().getRealValue());
//									}
//									//bookingInformationDetail vm value
//									if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getVmValue() != null){
//										element("em.vm.value",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getVmValue().getRealValue());
//									}
//									//bookingInformationDetail im value
//									if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getImValue() != null){
//										element("em.im.value",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getImValue().getRealValue());
//									}
//									//bookingInformationDeatail requirement
//									if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getRequiredAmount() != null){
//										element("em.required.amount",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getRequiredAmount().getRealValue());
//									}
//									//bookingInformationDeatail vm requirement
//									if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getVmRequiredAmount() != null){
//										element("em.vm.required.amount",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getVmRequiredAmount().getRealValue());
//									}
//									//bookingInformationDeatail im requirement
//									if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getImRequiredAmount() != null){
//										element("em.im.required.amount",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getImRequiredAmount().getRealValue());
//									}
//									//bookingInformationDeatail asset available
//									if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getAssetAvailable() != null){
//										element("em.asset.available",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getAssetAvailable().getRealValue());
//									}
//									//bookingInformationDeatail  vm asset available
//									if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getVmAssetAvailable() != null){
//										element("em.vm.asset.available",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getVmAssetAvailable().getRealValue());
//									}
//									//bookingInformationDeatail  im asset available
//									if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getImAssetAvailable() != null){
//										element("em.im.asset.available",agreementNameList.get(i),String.valueOf(row)).input(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).getImAssetAvailable().getRealValue());
//									}
//									//bookingInformationDetail apply
//									if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).isIsApply() != null){
//										if (list.get(i).getBookingInformation().getBookingInformationDetail().get(j).isIsApply()){
//											element("em.apply",agreementNameList.get(i),String.valueOf(row)).check(list.get(i).getBookingInformation().getBookingInformationDetail().get(j).isIsApply());
//										}
//									}
//								}
//    						}
//    					}
//    				}
//    				//after set all parameters of each click + button
//        			if (i != list.size()-1){
//        				element("em.add.booking").click();
//        			}
//        			//after click + button , row++ tr[1] tr[2]
//        			row++;
//				}
//
//    		}
//    	}
//    }
//  }
}