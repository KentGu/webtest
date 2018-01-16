package com.lombardrisk.pages.collateral;

import com.lombardrisk.data.ExposureManagementMenu;
import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.openqa.selenium.Keys;
import org.yiwan.webcore.locator.Locator;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class ExposureManagementPage extends PageBase {
    public ExposureManagementPage(IWebDriverWrapper webDriverWrapper) {
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

    public void createSubstitution() throws Exception {
        element("em.create.substitution").scrollIntoView().click();
    }

    public void autoSendAll() throws Exception {
        element("em.auto.send.all").click();
    }

    public void autoPopulateCounterpartyAmount() throws Exception {
        element("em.populateCtpyAmt").click();
    }

    public void enterBulkBooking() throws Exception {
        element("em.bulk.booking").click();
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
    }


    public void enterInstrumentBooking() throws Exception {
        element("em.instr.booking").click();
    }

    public void enterMakeBooking() throws Exception {
        element("em.make.booking").click();
    }

    public void enterGroupBooking() throws Exception {
        element("em.book.asset").clickSmartly();
    }

    public void showLetterLog() throws Exception {
        element("em.show.letter.log").click();
    }

    public void showReformProcessSummary() throws Exception {
        element("em.reform.process.summary").click();
    }

    public void showReformException() throws Exception {
        element("em.reform.exception").click();
    }

    public void showExposureManagement() throws Exception {
        element("em.action.exposure.management").click();
    }

    public void autoBooking() throws Exception {
        element("em.action.exposure.management").click();
    }

    public void searchEvent(ExposureEventSearch ees) throws Exception {
        if (ees.getAgrId() != null) {
            StringBuffer value = new StringBuffer();
            for (int i = 0; i < ees.getAgrId().size(); i++) {
                StringType agrId = ees.getAgrId().get(i);
                value = value.append(agrId.getRealValue());
                if (i != ees.getAgrId().size() - 1)
                    value = value.append(",");
            }
            element("em.searchevent.agreement.id").scrollIntoView().setValue(value.toString());
            element("em.searchevent.agreement.id").type(Keys.ENTER);
            waitThat().document().toBeReady();
            waitThat().jQuery().toBeInactive();
            waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        }

        if (ees.getPrincipal() != null && !ees.getPrincipal().isEmpty()) {
            StringBuffer value = new StringBuffer();
            for (int i = 0; i < ees.getPrincipal().size(); i++) {
                StringType principal = ees.getPrincipal().get(i);
                value = value.append(principal.getRealValue());
                if (i != ees.getPrincipal().size() - 1)
                    value = value.append(",");
            }
            element("em.searchevent.principal").scrollIntoView().setValue(value.toString());
            element("em.searchevent.principal").type(Keys.ENTER);
        }

        if (ees.getCounterparty() != null && !ees.getCounterparty().isEmpty()) {
            StringBuffer value = new StringBuffer();
            for (int i = 0; i < ees.getCounterparty().size(); i++) {
                StringType counterparty = ees.getCounterparty().get(i);
                value = value.append(counterparty.getRealValue());
                if (i != ees.getCounterparty().size() - 1)
                    value = value.append(",");
            }
            element("em.searchevent.counterparty").scrollIntoView().setValue(value.toString());
            element("em.searchevent.counterparty").type(Keys.ENTER);
        }

        if (ees.getExternalId() != null) {
            element("em.searchevent.external.id").scrollIntoView().setValue(ees.getExternalId().getRealValue());
            element("em.searchevent.external.id").type(Keys.ENTER);
        }

        if (ees.getEventDate() != null) {
            element("em.searchevent.event.date").scrollIntoView().setValue(ees.getEventDate().getRealValue());
            element("em.searchevent.event.date").type(Keys.ENTER);
        }

        if (ees.getDescription() != null && !ees.getDescription().isEmpty()) {
            StringBuffer value = new StringBuffer();
            for (int i = 0; i < ees.getDescription().size(); i++) {
                StringType description = ees.getDescription().get(i);
                value = value.append(description.getRealValue());
                if (i != ees.getDescription().size() - 1)
                    value = value.append(",");
            }
            element("em.searchevent.description").scrollIntoView().setValue(value.toString());
            element("em.searchevent.description").type(Keys.ENTER);
        }

        if (ees.getCallStatus() != null)
            element("em.searchevent.callstatus").scrollIntoView().selectByVisibleText(ees.getCallStatus().value());

        if (ees.getModel() != null) {
            element("em.searchevent.model").scrollIntoView().setValue(ees.getModel().getRealValue());
            element("em.searchevent.model").type(Keys.ENTER);
        }

        if (ees.getAction() != null) {
            element("em.searchevent.action").scrollIntoView().setValue(ees.getAction().getRealValue());
            element("em.searchevent.action").type(Keys.ENTER);
        }

        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
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
     * Click navigate filters on the left navigation panel of user filters exposure
     * management
     *
     * @param filter a sample as "Margin Filters>Substitution" filter
     */
    public void navigate(ExposureManagementMenu filter, ExposureManagementFilter exposureManagementFilter) throws Exception {
        navigate(filter.getName(), exposureManagementFilter);
    }


    /**
     * navigate to such EM by menu on the left panel of report list
     *
     * @param exposureManagementMenu
     */
    private void navigate(String exposureManagementMenu) throws Exception {
        String[] layer = exposureManagementMenu.split(Biz.SEPARATOR);
        element("em.menu", layer[0]).scrollIntoView().click();
        if (layer.length < 3) {
            element("em.menu2", layer[0], layer[1]).click();
        } else {

        }
    }

    /**
     * navigate to such EM by menu on the left panel of user filter
     *
     * @param exposureManagementMenu
     * @param exposureManagementFilter
     */
    private void navigate(String exposureManagementMenu, ExposureManagementFilter exposureManagementFilter) throws Exception {
        String[] layer = exposureManagementMenu.split(Biz.SEPARATOR);
        element("em.menu", layer[0]).click();
        if (layer.length < 3) {
            String addedFilter = exposureManagementFilter.getFilter().getFilterName().getRealValue();
            layer[1] = addedFilter;
            element("em.menu2", layer[0], layer[1]).click();
        }
    }

    /**
     * set event id into the test data by specified condition
     *
     * @param em
     */
    private void setEventId(ExposureEventDetail em) throws Exception {
        //String id = element("em.event.cell", getEventAttributes(em)).getInnerText();
        if (em.getEventId() == null || Boolean.FALSE.equals(em.getEventId().isApply())
                || (em.getEventId() != null && em.getEventId().getRealValue().isEmpty())) {
            String id = getMaxEventId(em);
            if (em.getEventId() == null) {
                StringType value = new StringType(id);
                em.setEventId(value);
            } else {
                em.getEventId().setValue(id);
            }
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

    @SuppressWarnings("Annotator")
    private String getMaxEventId(ExposureEventDetail exposureEventDetail) throws Exception {
        String xpath = "(//table[@id='jqGrid']//tr[not(@class='jqgfirstrow')]" + locateEvent(exposureEventDetail).append(")").toString();

        String expandXpath = "//td[@aria-describedby='jqGrid_treeIcon']/span[@class='tree-button-add']";

        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();

        while (element("em.event.expand2", expandXpath).isDisplayed()){
            element("em.event.expand2", expandXpath).click();
        }

        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
//        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
//        if(element("em.event.expand2", expandXpath).isDisplayed())
//            element("em.event.expand2", expandXpath).clickCircularly();

        int eventId = 0;

        for (IWebDriverWrapper.IWebElementWrapper e : element("em.event.row", xpath).getAllMatchedElements()) {
            String v = e.getAttribute("id");
            if (eventId < Integer.parseInt(v))
                eventId = Integer.parseInt(v);
        }
        if (eventId == 0) {
            logger.error("No such event in the Exposure Management:\n" +
                    "Element " + locator("em.event.row", xpath).getId() + " with expression " + locator("em.event.row", xpath).getExpression() +
                    " By " + locator("em.event.row", xpath).getBy().value());
            throw new Exception();
        } else {
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
    @Deprecated
    private String getEventAttributes(ExposureEventDetail result) throws Exception {
        StringBuilder attributes = new StringBuilder();
        Method[] methods = result.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !method.getName().equals("getId")
                    && !method.getName().equals("getRef") && !method.getName().equals("getName")
                    && method.invoke(result) != null) {
                String value;
                if (method.getReturnType().equals(CallStatusType.class)) {
                    value = ((CallStatusType) method.invoke(result)).value();
                    attributes.append("[normalize-space(td[@aria-describedby='").append(
                            getHeaderId(method.getName().replaceAll("^get", ""))).append("'])='").append(value).append(
                            "']");
                } else if (method.getReturnType().equals(StringType.class)) {// return StringType
                    value = ((StringType) method.invoke(result)).getRealValue();
                    attributes.append("[normalize-space(td[@aria-describedby='").append(
                            getHeaderId(method.getName().replaceAll("^get", ""))).append("'])='").append(value).append(
                            "']");
                }
//                attributes.append("[normalize-space(td[@aria-describedby='"
//                        + getHeaderId(method.getName().replaceAll("^get", "")) + "'])='" + value + "']");
            } else if (method.getName().equals("isStmtStatus") && method.invoke(result) != null) {
                Boolean stmtStatus = (Boolean) method.invoke(result);
                if (stmtStatus) {
                    attributes.append("[td[@aria-describedby='").append(getHeaderId("StmtStatus")).append(
                            "']/img[contains(@src,'tickg')]]");
                } else {
                    attributes.append("[td[@aria-describedby='").append(getHeaderId("StmtStatus")).append(
                            "']/img[contains(@src,'crossg')]]");
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
            case "statementNotificationStatus":
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
            case "element":
                return "jqGrid_instrumentIds";
            case "modelCategory":
                return "jqGrid_modelCategoryName";
            default:
                return (new StringBuffer()).append("jqGrid_").append(attribute).toString();
        }
    }

    private StringBuilder locateEvent(ExposureEventDetail em) throws Exception {
        StringBuilder xpath = new StringBuilder();
        Method[] methods = em.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !method.getName().equals("getId")
                    && !method.getName().equals("getRef") && !method.getName().equals("getName")
                    && !method.getName().equals("getAlertHandling")
                    && method.invoke(em) != null) {
                String columnName = Biz.getColumnName(method.getName().substring(3), "(?<!^)(?=[A-Z])");
                if (columnName.contains("E D"))
                    columnName = columnName.replace("Vm", "VM").replace("Im", "IM").replace("E D", "E/D");
                if (columnName.contains("Lrf"))
                    columnName = columnName.replace("rfp", "RFP").replace("rfd", "RFD");
                if (columnName.contains("For"))
                    columnName = columnName.replace("For", "for");
                if (columnName.contains("I M"))
                    columnName = columnName.replace("I M", "IM");
                if (columnName.contains("V M"))
                    columnName = columnName.replace("V M", "VM");
                if (columnName.contains("I A"))
                    columnName = columnName.replace("I A", "IA");
                StringType stringType = null;
                String value = "";
                if (method.getReturnType().equals(StringType.class)) {
                    stringType = ((StringType) method.invoke(em));
                    value = stringType.getRealValue();
                } else if (method.getReturnType().equals(CallStatusType.class))
                    value = ((CallStatusType) method.invoke(em)).value();

                xpath.append("[td[count(//th[div[text()='").append(columnName).append("']]/preceding-sibling::th)+1]");
                if (value != null && !value.equals("")) {
                    if (columnName.contains("Element")
                            && (stringType.getVariation() != null && stringType.getVariation().equals("multiple"))) {
                        xpath.append("[contains(text(),'").append(value).append("')]");
                    } else {
                        xpath.append("[text()='").append(value).append("']");
                    }
                }
                xpath.append("]");
            }

        }
        return xpath;
    }


    private StringBuilder locateSubEvent(SubstitutionEventDetail em) throws Exception {
        StringBuilder xpath = new StringBuilder();
        Method[] methods = em.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !method.getName().equals("getId")
                    && !method.getName().equals("getRef") && !method.getName().equals("getName")
                    && !method.getName().equals("getAlertHandling")
                    && method.invoke(em) != null) {
                String columnName = Biz.getColumnName(method.getName().substring(3), "(?<!^)(?=[A-Z])");
                if (columnName.contains("V M"))
                    columnName = columnName.replace("V M", "VM");
                if (columnName.contains("I M"))
                    columnName = columnName.replace("I M", "IM");
                if (columnName.contains("Cash VM"))
                    columnName = columnName.replace("Cash VM", "Cash/VM");
                if (columnName.contains("D F"))
                    columnName = columnName.replace("D F", "DF");
                String value = "";
                if (method.getReturnType().equals(StringType.class)) {
                    value = ((StringType) method.invoke(em)).getRealValue();
                } else if (method.getReturnType().equals(CallStatusType.class))
                    value = ((CallStatusType) method.invoke(em)).value();

                xpath.append("[td[count(//th[div[text()='").append(columnName).append(
                        "'] and contains(@id,'adHocGridTable_')]/preceding-sibling::th)+1]");
                if (value != null && !value.equals(""))
                    xpath.append("[text()='").append(value).append("']");
                xpath.append("]");
            }

        }
        return xpath;
    }

    public void expandEvent(ExposureEventDetail em) throws Exception {
//        StringBuilder xpath = locateEvent(em);
        setEventId(em);
        if (em.getModelCategory() !=null && element("em.event.expand.model.category", em.getEventId().getRealValue()).isDisplayed()) {
            element("em.event.expand.model.category", em.getEventId().getRealValue()).clickCircularly();
            waitThat().document().toBeReady();
            waitThat().jQuery().toBeInactive();
        } else if (element("em.event.expand3", em.getEventId().getRealValue()).isDisplayed()){
            element("em.event.expand3", em.getEventId().getRealValue()).clickCircularly();
            waitThat().document().toBeReady();
            waitThat().jQuery().toBeInactive();
        }
//        waitThat().document().toBeReady();
//        waitThat().jQuery().toBeInactive();
    }

    public void collapseEvent(ExposureEventDetail em) {

    }

    private void assertExposureEventDetailColor(IWebDriverWrapper.IFluentLocatorAssertion validate, ExposureEventDetail exposureEventDetail) {
        String greyOutbackGroundPattern = "^(.*)background-color: rgb\\(189, 204, 218\\)(.*)$";
        String orangeOutbackGroundPattern = "^(.*)background-color: rgb\\(253, 130, 63\\)(.*)$";
        String blueOutbackGroundPattern = "^(.*)background-color: rgb\\(189, 204, 218\\)(.*)$";


        if (exposureEventDetail.getVariation() != null) {
            switch (exposureEventDetail.getVariation().toUpperCase()) {
                case "GREY":
                    validate.attributeValueOf("style").matches(greyOutbackGroundPattern);
                    break;
                case "ORANGE":
                    validate.attributeValueOf("style").matches(orangeOutbackGroundPattern);
                    break;
                case "BLUE":
                    validate.attributeValueOf("style").matches(blueOutbackGroundPattern);
                    break;

            }
        }
    }

    public void assertEmEventDetail(ExposureEventDetail detail) throws Exception {
        //check dropdownlist value of wrkstatus, check whether wrkstatus is disabled
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
        StringBuilder xpath = new StringBuilder();
        if (Boolean.FALSE.equals(detail.isApply())) {
            xpath = locateEvent(detail);
            assertThat(locator("em.event.row2", xpath.toString())).displayed().isEqualTo(detail.isApply());
        } else {
            setEventId(detail);
            xpath.append("[@id='").append(detail.getEventId().getRealValue()).append("']");

            assertThat(locator("em.event.row2", xpath.toString())).displayed().isEqualTo(true);

            if (detail.getVariation() != null) {
                assertExposureEventDetailColor(validateThat("em.event.row2", xpath.toString()), detail);
            }

            if (detail.getEventId() != null) {
                if (detail.getSub() != null && detail.getSub().getVariation().equalsIgnoreCase("circle")) {
                    assertThat(locator("em.event.sub.image", detail.getEventId().getRealValue()))
                            .attributeValueOf("src").contains("common/newEEImage/dot.png");
                }

                if (detail.getWrkStatus() != null) {
                    if (detail.getWrkStatus().isDisable() != null) {
                        element("em.wrkStatus", detail.getEventId().getRealValue()).click();
                        waitThat().document().toBeReady();
                        waitThat().jQuery().toBeInactive();
                        assertThat(
                                locator("em.wrkStatus.select", detail.getEventId().getRealValue())).enabled().isEqualTo(
                                !detail.getWrkStatus().isDisable());
                    }
                    if (detail.getWrkStatus().getVariation() != null & detail.getWrkStatus().isApply() != null) {
                        element("em.wrkStatus", detail.getEventId().getRealValue()).click();
                        waitThat("em.wrkStatus.select", detail.getEventId().getRealValue()).toBeVisible();
                        String list[] = detail.getWrkStatus().getVariation().split(",");
                        for (int i = 0; i < list.length; i++) {
                            if (detail.getWrkStatus().isApply()) {
                                assertThat(locator("em.wrkStatus.select",
                                        detail.getEventId().getRealValue())).allOptionTexts().contains(list[i]);
                            } else {
                                assertThat(locator("em.wrkStatus.select",
                                        detail.getEventId().getRealValue())).allOptionTexts().doesNotContain(list[i]);
                            }
                        }
                        this.navigate().refresh();
                        waitThat().jQuery().toBeInactive();
                        waitThat().document().toBeReady();
                        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
                    }
                }
            }
        }
    }

    public void assertSubEventDetail(SubstitutionEventDetail substitutionEventDetail) throws Exception {
        StringBuilder xpath = locateSubEvent(substitutionEventDetail);
        if (substitutionEventDetail.isApply() != null && !substitutionEventDetail.isApply()) {
            assertThat(locator("em.subEvent.row", xpath.toString())).displayed().isEqualTo(
                    substitutionEventDetail.isApply());
        } else {
            assertThat(locator("em.subEvent.row", xpath.toString())).displayed().isEqualTo(true);
        }
    }

    public void assertAction(ExposureEventDetail detail) throws Exception {
        if (detail.getAction() != null) {
            if (detail.getAction().isApply() == null || detail.getAction().isApply()) {
                assertThat(locator("em.action", detail.getAction().getRealValue())).displayed().isTrue();
            } else {
                assertThat(locator("em.action", detail.getAction().getRealValue())).displayed().isEqualTo(
                        detail.getAction().isApply());
            }
        }
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
        if (em2.getCallStatus() != null) {
            element("em.call.status", em2.getEventId().getRealValue()).click();
            element("em.call.status.select", em2.getEventId().getRealValue()).selectByVisibleText(
                    em2.getCallStatus().value());
        }

        if (em2.getWrkStatus() != null) {
            element("em.wrkStatus", em2.getEventId().getRealValue()).click();
            element("em.wrkStatus.select", em2.getEventId().getRealValue()).selectByVisibleText(
                    em2.getWrkStatus().getRealValue());
        }

        if (em2.isOverwrite() != null && em2.isOverwrite()) {
            element("em.overwrite", em2.getEventId().getRealValue()).check(em2.isOverwrite());

        }

        if (em2.getUserAgreedAmount() != null) {
            element("em.userAgreedAmount", em2.getEventId().getRealValue()).click();
            element("em.userAgreedAmount.input", em2.getEventId().getRealValue()).input(
                    em2.getUserAgreedAmount().getRealValue() + Keys.ENTER);//.type(Keys.ENTER);
        }

        if (em2.getCounterpartyAmount() != null) {
            element("em.cpty.amount", em2.getEventId().getRealValue()).click();
            if (em2.getAlertHandling() != null && !em2.getAlertHandling().isEmpty()) {
                alert().disable();
                element("em.cpty.amount.input", em2.getEventId().getRealValue()).input(
                        em2.getCounterpartyAmount().getRealValue() + Keys.ENTER);//.type(Keys.ENTER);
                alert().enable();
//                handleAlters(em2.getAlertHandling());
            } else {
                alert().disable();
                element("em.cpty.amount.input", em2.getEventId().getRealValue()).input(
                        em2.getCounterpartyAmount().getRealValue() + Keys.ENTER);
                alert().enable();
//                        .type(Keys.ENTER);
//                if (alert().isPresent())
//                    alert().accept();
            }
            // accept alert
//                if (alert().isPresent())
//                    alert().accept();
        }

        if (em2.getComments() != null) {
            element("em.comments", em2.getEventId().getRealValue()).click();
            element("em.comments.input", em2.getEventId().getRealValue()).input(
                    em2.getComments().getRealValue());
        }
        if (em2.getInstrumentID() != null) {
            element("em.element", em2.getEventId().getRealValue()).click();
            element("em.element.input", em2.getEventId().getRealValue()).input(
                    em2.getInstrumentID().getRealValue());
        }
        if (em2.getPortfolioIA() != null) {
            element("em.portfolioIa", em2.getEventId().getRealValue()).click();
            element("em.portfolioIa.edit", em2.getEventId().getRealValue()).click();
        }
    }

    /**
     * assert the value of EM page
     *
     * @param em
     */
    public void assertExposureManagement(ExposureEventDetail em) throws Exception {
        // find event id
        if (em.getEventId() == null || em.getEventId().getRealValue().isEmpty()) {
            setEventId(em);
        }
        if (em.getCallStatus() != null) {
            assertThat("em.call.status", em.getEventId().getRealValue()).innerText().isEqualTo(
                    em.getCallStatus().value());
        }


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
    public void makeInstrumentBooking(BookingDetail bookingDetail) throws Exception {
//        element("em.instrument.booking.id").setAttribute("value", bookingDetail.getInstrumentSearch().getInstrumentId().getLinkText().getRealValue());
        element("em.instrument.booking.id").input(
                bookingDetail.getInstrumentSearch().getInstrumentId().getLinkText().getRealValue());
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
     *
     * @author kent gu
     */
    public void enterAutoSendLetter() throws Exception {
        element("em.auto.send").click();
    }

    public void autoBookCash() throws Exception {
        element("em.auto.book.cash").click();
    }

    public void autoBookAll() throws Exception {
        element("em.auto.book.all").click();
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
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
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        if (exposureEventDetail.getEventId() == null || exposureEventDetail.getEventId().getRealValue().isEmpty())
            setEventId(exposureEventDetail);
        if (element("em.event.cb", exposureEventDetail.getEventId().getRealValue()).isChecked() != b)
            element("em.event.cb", exposureEventDetail.getEventId().getRealValue()).click().fireEvent(
                    Biz.FIRE_EVENT_ONCHANGE);
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);

    }


    /**
     * assert event exists or not in exposure management search results
     *
     * @param em
     */
    @Deprecated
    public void assertEvent(ExposureEventDetail em, Boolean exists) throws Exception {
        String xpath = "//tr" + getEventAttributes(em);
        assertThat("em.event.row", xpath).displayed().isEqualTo(exists);

        if (em.getEventId() == null || em.getExternalId().getRealValue().isEmpty()) {
            if (em.getExternalId() == null) {
                em.setEventId(new StringType(getMaxEventId(em)));
            } else {
                em.getExternalId().setValue(getMaxEventId(em));
            }
        }

        String row = "//tr[@id='" + em.getExternalId().getRealValue() + "']";
    }

    public void assertExposureManagermentMessage(ExposureManagementMessage exposureManagementMessage) throws Exception {
        if (exposureManagementMessage.getMessageSummary() != null) {
            if (exposureManagementMessage.getMessageSummary().getSuccessfulAccount() != null) {
                assertThat("em.message.message.summary").innerText().contains(
                        exposureManagementMessage.getMessageSummary().getSuccessfulAccount().getRealValue());
            }
            if (exposureManagementMessage.getMessageSummary().getUnsuccessfulAccount() != null) {
                assertThat("em.message.message.summary").innerText().contains(
                        "Unsuccessful: " + exposureManagementMessage.getMessageSummary().getUnsuccessfulAccount().getRealValue());
            }
        }

        if (exposureManagementMessage.getMessageDetail() != null && exposureManagementMessage.getMessageDetail().size() > 0) {
            for (int i = 0; i < exposureManagementMessage.getMessageDetail().size(); i++) {
                if (exposureManagementMessage.getMessageDetail().get(i).getTimeStamp() != null) {
                    assertThat("em.message.message.detail.timestamp",
                            String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                            exposureManagementMessage.getMessageDetail().get(i).getTimeStamp().getRealValue());
                }
//                if (exposureManagementMessage.getMessageDetail().get(i).getLevel() != null) {
//                    assertThat("em.message.message.detail.level", String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
//                            exposureManagementMessage.getMessageDetail().get(i).getLevel().getRealValue());
                if (exposureManagementMessage.getMessageDetail().get(i).getLevel() != null) {
                    assertThat("em.message.message.detail.level.update",
                            exposureManagementMessage.getMessageDetail().get(i).getMessage().getRealValue()).innerText()
                            .isEqualToIgnoringWhitespace(
                                    exposureManagementMessage.getMessageDetail().get(i).getLevel().getRealValue());
                }
                if (exposureManagementMessage.getMessageDetail().get(i).getSource() != null) {
                    assertThat("em.message.message.detail.source",
                            String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                            exposureManagementMessage.getMessageDetail().get(i).getSource().getRealValue());
                }

                if (exposureManagementMessage.getMessageDetail().get(i).getMessage().getVariation() != null &&
                        exposureManagementMessage.getMessageDetail().get(
                                i).getMessage().getVariation().equalsIgnoreCase("failLog")) {
                    assertThat("em.message.message.detail.failMessage",
                            exposureManagementMessage.getMessageDetail().get(i).getMessage().getRealValue()).present();
                } else {
                    assertThat("em.message.message.detail.message.update",
                            exposureManagementMessage.getMessageDetail().get(i).getMessage().getRealValue()).present();
                }

            }

        }
    }


    public void assertExposureManagementBookingMessage(ExposureManagementMessage exposureManagementMessage) throws Exception {
        waitThat("em.bulk.booking.err.msg.area").toBeVisible();
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);

//        approach implemented by yanlu

        if (exposureManagementMessage.getMessageDetail() != null && exposureManagementMessage.getMessageDetail().size() > 0) {
            for (int i = 0; i < exposureManagementMessage.getMessageDetail().size(); i++) {
                StringBuffer sb = new StringBuffer("//tr");
                if (exposureManagementMessage.getMessageDetail().get(i).getMessage() != null) {
                    sb.append("[td[contains(text(),'").append(
                            exposureManagementMessage.getMessageDetail().get(i).getMessage().getRealValue()).append(
                            "')]]");
                    if (exposureManagementMessage.getMessageDetail().get(i).getIndex() != null) {
//                        sb.append("[td='").append(exposureManagementMessage.getMessageDetail().get(i).getIndex().getRealValue()).append("']");
                        assertThat("em.booking.message.warnings.message", sb.toString()).displayed().isTrue();
                    } else {
//                        assertThat("em.booking.message.message").innerText().containsIgnoringCase(exposureManagementMessage.getMessageDetail().get(i).getMessage().getRealValue());
//                        StringBuffer MessageDetail = new StringBuffer("//div[@id='BookErrorWarining']/div[2]//tr");
//                        MessageDetail.append("[td[contains(text(),'" + exposureManagementMessage.getMessageDetail().get(i).getMessage().getRealValue() + "')]]");
                        if (exposureManagementMessage.getMessageDetail().get(i).getType() != null) {
                            sb.append("[td[contains(text(),'").append(exposureManagementMessage.getMessageDetail().get(
                                    i).getType().getRealValue()).append("')]]");
                        } else if (exposureManagementMessage.getMessageDetail().get(i).getMessage() != null
                                && exposureManagementMessage.getMessageDetail().get(
                                i).getMessage().getRealValue().contains("No errors or warnings found")) {
                            sb.delete(0, sb.length()).append(
                                    "//div[@id='BookErrorWarining']//font[contains(b,'No errors or warnings found.')]");
                        }
                        element("em.booking.message.warnings.message", sb.toString()).scrollIntoView();
                        assertThat("em.booking.message.warnings.message", sb.toString()).displayed().isTrue();

                    }

                }

            }
        }
    }

//        approach implemented by Kenny
//        if (exposureManagementMessage.getMessageDetail() != null && exposureManagementMessage.getMessageDetail().size() > 0){
//            for (int i=0;i<exposureManagementMessage.getMessageDetail().size();i++){
//                if (exposureManagementMessage.getMessageDetail().get(i).getMessage() != null){
//                    if (exposureManagementMessage.getMessageDetail().get(i).getIndex() != null){
//                        assertThat("em.bulk.booking.err.msg.by.index", exposureManagementMessage.getMessageDetail().get(i).getIndex().getRealValue()).allInnerTexts().contains(exposureManagementMessage.getMessageDetail().get(i).getMessage().getRealValue());
//                    }else {
//                        assertThat("em.booking.message.message").innerText().isEqualToIgnoringWhitespace(exposureManagementMessage.getMessageDetail().get(i).getMessage().getRealValue());
//                    }
//                }
//            }
//        }

    public void overrideWarningMeassage(ExposureManagementMessage exposureManagementMessage) throws Exception {
        if (exposureManagementMessage.getMessageDetail() != null && exposureManagementMessage.getMessageDetail().size() > 0) {
            for (int i = 0; i < exposureManagementMessage.getMessageDetail().size(); i++) {
                if (exposureManagementMessage.getMessageDetail().get(i).isOverrideWarning() != null) {
                    if (element("em.booking.message.override", String.valueOf(i + 1)).scrollIntoView().isChecked()
                            != exposureManagementMessage.getMessageDetail().get(i).isOverrideWarning())
                        element("em.booking.message.override",
                                String.valueOf(i + 1)).scrollIntoView().clickByJavaScript();
                }
            }
        }
    }

    public void assertBookingMessage() {
    }

    public void addAllColumns() throws Exception {
        element("em.columns").click();
        element("em.columns.add.all").click();
        element("em.columns.ok").click();
    }

//    public void assertColumnInSelectColumnsDialog (ExposureEventDetail exposureEventDetail, Boolean displayOrNot) throws Exception {
//        element("em.columns").click();
////        displayOrNot = exposureEventDetail.getColumn().isApply();
//        assertThat("em.column.delete",exposureEventDetail.getColumn().getRealValue()).displayed().isEqualTo(displayOrNot);
//        assertThat("em.column.add",exposureEventDetail.getColumn().getRealValue()).displayed().isEqualTo(!displayOrNot);
//        element("em.columns.ok").click();
//        waitThat().document().toBeReady();
//        waitThat().jQuery().toBeInactive();
//    }

    public void assertColumnInSelectColumnsDialog(ExposureEventDetail exposureEventDetail, Boolean LeftPanelDisplayOrNot, Boolean rightPanelDisplayOrNot) throws Exception {
        element("em.columns").click();
//        displayOrNot = exposureEventDetail.getColumn().isApply();
        assertThat("em.column.delete", exposureEventDetail.getColumn().getRealValue()).displayed().isEqualTo(
                LeftPanelDisplayOrNot);
        assertThat("em.column.add", exposureEventDetail.getColumn().getRealValue()).displayed().isEqualTo(
                !rightPanelDisplayOrNot);
        element("em.columns.ok").click();
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
    }

    public void addSomeColumns(ExposureEventDetail exposureEventDetail) throws Exception {
        element("em.columns").scrollIntoView().click();
        if (element("em.column.delete", exposureEventDetail.getColumn().getRealValue()).isDisplayed()) {
        } else {
            element("em.column.add", exposureEventDetail.getColumn().getRealValue()).click();
        }
        element("em.columns.ok").click();
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
    }


    public void assertExposureEventDetail(ExposureEventDetail eed, ExposureEventDetail assertEED) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");

        if (eed.getEventId() == null || !eed.getEventId().getRealValue().isEmpty()) {
            if (eed.getEventId() == null) {
                eed.setEventId(new StringType(getMaxEventId(eed)));
            } else {
                eed.getEventId().setValue(getMaxEventId(eed));
            }
        }
        String row = "//tr[@id='" + eed.getEventId().getRealValue() + "']";
        String xpath;
        Method[] methods = assertEED.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if ((method.getName().startsWith("get")
                    || method.getName().startsWith("is"))
                    && method.invoke(assertEED) != null) {
                if (method.getReturnType() == StringType.class) {
                    if (method.getName().equals("getAgrBaseCcy")) {
                        xpath = row + "/td[@aria-describedby='" + getHeaderId(
                                method.getName().replaceAll("^get", "")) + "']/font";
                    } else {
                        xpath = row + "/td[@aria-describedby='" + getHeaderId(
                                method.getName().replaceAll("^get", "")) + "']";
                    }
                    String value = ((StringType) method.invoke(assertEED)).getRealValue();
                    if (method.getName().equals("getAgreementRequirement")
                            || method.getName().equals("getCounterpartyAmount")
                            || method.getName().equals("getCounterpartyAmountUmbrellaBaseCcy")
                            || method.getName().equals("getStdAmount")
                            || method.getName().equals("getSysCalculatedAmount")
                            || method.getName().equals("getUserAgreedAmount")
                            || method.getName().equals("getDisputeValue")
                            || method.getName().equals("getTotalExposureAmount")
                            || method.getName().equals("getCptyExposure")
                            || method.getName().equals("getAdjustedCollateralValue")
                            || method.getName().equals("getSysRequirement")
                            || method.getName().equals("getTsa")
                            || method.getName().equals("getVariationMargin")
                            || method.getName().equals("getAccruedCoupon")
                            || method.getName().equals("getVmNetED")
                            || method.getName().equals("getImRequirement")
                            || method.getName().equals("getImNetED")
                            || method.getName().equals("getCashNetExcess")
                            || method.getName().equals("getNetED")
                            || method.getName().equals("getVmCashBalance")
                            || method.getName().equals("getVmSecuritiesBalance")
                            || method.getName().equals("getImCashBalance")
                            || method.getName().equals("getImSecuritiesBalance")
                            || method.getName().equals("getReceivedCounterpartyIA")
                            || method.getName().equals("getCptyCollateralValue")
                            || method.getName().equals("getBookedAmountFirstLeg")
                            || method.getName().equals("getBookedAmountSecondLeg")
                            || method.getName().equals("getAdjustedCollateralValuePrcVM")
                            || method.getName().equals("getAdjustedCollateralValuePrcIM")
                            || method.getName().equals("getAdjustedCollateralValueCptyVM")
                            || method.getName().equals("getAdjustedCollateralValueCptyIM")
                            || method.getName().equals("getReportingAgreementRequirement")
                            || method.getName().equals("getDisputeReportingValue")
                            || method.getName().equals("getBookedAmount")
                            || method.getName().equals("getCashComponent")
                            || method.getName().equals("getFee")
                            || method.getName().equals("getNotionalRequirement")
                            || method.getName().equals("getBookedNotionalForOneLeg")) {
                        try {
                            value = format.format(Float.parseFloat(value));
                        } catch (NumberFormatException e) {
                            value = ((StringType) method.invoke(assertEED)).getRealValue();
                        }
                    }
                    assertThat(locator("em.event.row", xpath)).innerText().isEqualTo(value);
                } else if (method.getReturnType() == CallStatusType.class) {
                    xpath = row + "/td[@aria-describedby='" + getHeaderId(
                            method.getName().replaceAll("^get", "")) + "']";
                    CallStatusType value = (CallStatusType) method.invoke(assertEED);
                    assertThat(locator("em.event.row", xpath)).innerText().isEqualTo(value.value());
                } else if (method.getName().equals("isStmtStatus")) {
                    boolean flag = (boolean) method.invoke(assertEED);
                    if (flag) {
                        xpath = row + "[td[@aria-describedby='" + getHeaderId(
                                "StmtStatus") + "']/img[contains(@src,'tickg')]]";
                    } else {
                        xpath = row + "[td[@aria-describedby='" + getHeaderId(
                                "StmtStatus") + "']/img[contains(@src,'crossg')]]";
                    }
                    assertThat(locator("em.event.row", xpath)).displayed();
                } else if (method.getName().equals("isOverwrite")) {
                    boolean flag = (boolean) method.invoke(assertEED);
                    xpath = row + "[td[@aria-describedby='" + getHeaderId(
                            method.getName().replaceAll("^get", "")) + "']/input";
                    assertThat(locator("em.event.row", xpath)).attributeValueOf("value").isEqualTo(flag);
                }
            }
        }
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

    public void cancelbooking() throws Exception {
        element("em.cancel.booking").click();
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
    }


    /**
     * assert bulk bookings exists or not on bulk booking page
     *
     * @param booking
     * @param exists
     */
    public void assertBulkBooking(BookingDetail booking, Boolean exists) throws Exception {
        StringBuffer xpath = new StringBuffer("//div[@id='divEventBookingPanelSet']//table[@class='displaytagTable']");
        if (booking.getInstrumentSearch().getInstrument().getFilter() != null)
            xpath.append("//td[@class='c_InstrumentID']//input[contains(value, '").append(
                    booking.getInstrumentSearch().getInstrument().getFilter().getRealValue()).append("')]");
        if (booking.getInstrumentSearch().getLinkText() != null)
            xpath.append("//td[@class='c_AssetName']//input[contains(value, '").append(
                    booking.getInstrumentSearch().getLinkText()).append("')]");
        assertThat("em.event.row", xpath.toString()).displayed().isEqualTo(exists);
        element("em.cancel.booking").click();
    }

    /**
     * assert BulkBookingInformation exists or not on bulk booking page
     *
     * @param bookingDetail
     */
    public void assertBulkBookingInformation(BookingDetail bookingDetail) throws Exception {
//        Boolean exists = false;
        BookingInformationDetail bookingInformationDetail = bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                0);
        String modelName = bookingInformationDetail.getModel().getRealValue();
        String bookingMovement = bookingInformationDetail.getBookingMovement().value();

        if (bookingInformationDetail.getModel().getRealValue() != null && bookingInformationDetail.getBookingMovement().value() != null) {
            assertThat("em.bulkBooking.panelTitle", modelName, bookingMovement).displayed().isEqualTo(false);

            if (bookingInformationDetail.getMovement().value() != null) {
                assertThat("em.bulkBooking.movement", modelName, bookingMovement).selectedText().isEqualToIgnoringCase(
                        bookingInformationDetail.getMovement().value());
            }
            if (bookingInformationDetail.getRequiredAmount().getRealValue() != null) {
                assertThat("em.bulkBooking.requiredAmount", modelName, bookingMovement).attributeValueOf(
                        "value").isEqualToIgnoringCase(bookingInformationDetail.getRequiredAmount().getRealValue());
            }
        } else if (bookingInformationDetail.getModel().getRealValue() == null && bookingInformationDetail.getBookingMovement().value() != null) {
            if (bookingInformationDetail.getMovement().value() != null) {
                assertThat("em.bulkBooking.movement", bookingMovement).selectedText().isEqualToIgnoringCase(
                        bookingInformationDetail.getMovement().value());
            }
            if (bookingInformationDetail.getRequiredAmount().getRealValue() != null) {
                assertThat("em.bulkBooking.requiredAmount", bookingMovement).attributeValueOf(
                        "value").isEqualToIgnoringCase(bookingInformationDetail.getRequiredAmount().getRealValue());
            }

        }
        // element("em.cancel.booking").click();
    }

    /**
     * assert ajax search check on bulk booking page
     *
     * @param oriBookingDetail,newBookingDetail
     */
    public void assertBulkBookingAjaxSearch(BookingDetail oriBookingDetail, BookingDetail newBookingDetail, boolean display) throws Exception {
        List<BookingInformationDetail> oriBookingInformationDetail = oriBookingDetail.getBookingInformation().getBookingInformationDetail();
        List<BookingInformationDetail> newBookingInformationDetail = newBookingDetail.getBookingInformation().getBookingInformationDetail();
        for (int j = 0; j < newBookingInformationDetail.size(); j++) {
            if (newBookingDetail.getInstrumentSearch() != null) {
                if (newBookingDetail.getInstrumentSearch().getInstrument() != null) {
                    if (newBookingDetail.getInstrumentSearch().getInstrument().getFilter() != null) {
                        element("em.instru.id.multiEvent.2",
                                locateBulkBooking(oriBookingInformationDetail.get(0))).clear();
                        element("em.instru.id.multiEvent.2",
                                locateBulkBooking(oriBookingInformationDetail.get(0))).input(
                                newBookingDetail.getInstrumentSearch().getInstrument().getFilter().getRealValue());
                        waitThat().jQuery().toBeInactive();
                        waitThat().document().toBeReady();
                        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
                        assertThat("em.instru.idLinktext", newBookingInformationDetail.get(
                                j).getInstrumentIdAjaxSearchList().getRealValue()).displayed().isEqualTo(display);
                        element("em.instru.id.multiEvent.2",
                                locateBulkBooking(oriBookingInformationDetail.get(0))).clear();
                    }
                }
            }
        }
    }

    /**
     * assert BulkBookingInformation exists or not on bulk booking page
     *
     * @param bookingDetail
     */
    public void assertBulkBookingInfo(BookingDetail bookingDetail) throws Exception {
        int size = bookingDetail.getBookingInformation().getBookingInformationDetail().size();
        for (int i = 0; i < size; i++) {
            BookingInformationDetail bookingInformationDetail = bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                    i);
            waitThat("em.bulkbooking.row", locateBulkbookingForAssert(bookingInformationDetail)).toBeAllVisible();
            assertThat("em.bulkbooking.row", locateBulkbookingForAssert(bookingInformationDetail)).displayed().isTrue();

            if (bookingInformationDetail.getAssetAvailable() != null) {
                assertThat("em.maxReturnValue", locateBulkBooking(bookingInformationDetail)).attributeValueOf(
                        "value").isEqualToIgnoringWhitespace(
                        bookingInformationDetail.getAssetAvailable().getRealValue());
            }
            if (bookingInformationDetail.getVmAssetAvailable() != null) {
                assertThat("em.maxVmReturnValue", locateBulkBooking(bookingInformationDetail)).attributeValueOf(
                        "value").isEqualToIgnoringWhitespace(
                        bookingInformationDetail.getVmAssetAvailable().getRealValue());
            }
            if (bookingInformationDetail.getImAssetAvailable() != null) {
                assertThat("em.maxImReturnValue", locateBulkBooking(bookingInformationDetail)).attributeValueOf(
                        "value").isEqualToIgnoringWhitespace(
                        bookingInformationDetail.getImAssetAvailable().getRealValue());
            }
            if (bookingInformationDetail.getInstrumentId() != null) {
                if (bookingInformationDetail.getInstrumentId().isDisable() != null && bookingInformationDetail.getInstrumentId().isDisable()) {
                    assertThat("em.InstrumentId", locateBulkBooking(bookingInformationDetail)).attributeValueOf(
                            "readonly").isEqualTo("true");
                }
            }

            if (bookingInformationDetail.getNominalAmount() != null) {
                if (bookingInformationDetail.getNominalAmount().isDisable() != null && bookingInformationDetail.getNominalAmount().isDisable()) {
                    assertThat("em.nominal.amt.multiEvent.2",
                            locateBulkBooking(bookingInformationDetail)).attributeValueOf("readonly").isEqualTo("true");
                }
                if (!bookingInformationDetail.getNominalAmount().getRealValue().isEmpty()) {
                    String a = element("em.nominal.amt.multiEvent.2",
                            locateBulkBooking(bookingInformationDetail)).getInputDisplayedValue();
                    assertThat().string().value(element("em.nominal.amt.multiEvent.2",
                            locateBulkBooking(bookingInformationDetail)).getInputDisplayedValue()).isEqualTo(
                            bookingInformationDetail.getNominalAmount().getRealValue());
                }

            }
            if (bookingInformationDetail.getVmAmount() != null) {
                if (bookingInformationDetail.getVmAmount().isDisable() != null && bookingInformationDetail.getVmAmount().isDisable()) {
                    assertThat("em.vm.amt.multiEvent.2", locateBulkBooking(bookingInformationDetail)).attributeValueOf(
                            "readonly").isEqualTo("true");
                }
            }
            if (bookingInformationDetail.getImAmount() != null) {
                if (bookingInformationDetail.getImAmount().isDisable() != null && bookingInformationDetail.getImAmount().isDisable()) {
                    assertThat("em.im.amt.multiEvent.2", locateBulkBooking(bookingInformationDetail)).attributeValueOf(
                            "readonly").isEqualTo("true");
                }
            }

            if (bookingDetail.getBookingInformation().getSettlementDate() != null) {
                if (bookingDetail.getBookingInformation().getSettlementDate().isDisable() != null && bookingDetail.getBookingInformation().getSettlementDate().isDisable()) {
                    assertThat("em.settlement.date.multiEvent.2",
                            locateBulkBooking(bookingInformationDetail)).attributeValueOf("disabled").isEqualTo("true");
                }
            }

            if (bookingInformationDetail.getPrcSSI() != null) {
                if (bookingInformationDetail.getPrcSSI().isDisable() != null && bookingInformationDetail.getPrcSSI().isDisable()) {
                    assertThat("em.prc.ssi.multiEvent.2", locateBulkBooking(bookingInformationDetail)).attributeValueOf(
                            "disabled").isEqualTo("true");
                }
            }

            if (bookingInformationDetail.getCptySSI() != null) {
                if (bookingInformationDetail.getCptySSI().isDisable() != null && bookingInformationDetail.getCptySSI().isDisable()) {
                    assertThat("em.cpty.ssi.multiEvent.2",
                            locateBulkBooking(bookingInformationDetail)).attributeValueOf("disabled").isEqualTo("true");
                }
            }

            if (bookingInformationDetail.getValue() != null) {
                if (bookingInformationDetail.getValue().isDisable() != null && bookingInformationDetail.getValue().isDisable()) {
                    assertThat("em.value.multiEvent.2", locateBulkBooking(bookingInformationDetail)).attributeValueOf(
                            "readonly").isEqualTo("true");
                }
            }

            if (bookingInformationDetail.getVmValue() != null) {
                if (bookingInformationDetail.getVmValue().isDisable() != null && bookingInformationDetail.getVmValue().isDisable()) {
                    assertThat("em.vm.value.multiEvent.2",
                            locateBulkBooking(bookingInformationDetail)).attributeValueOf("readonly").isEqualTo("true");
                }
            }

            if (bookingInformationDetail.getImValue() != null) {
                if (bookingInformationDetail.getImValue().isDisable() != null && bookingInformationDetail.getImValue().isDisable()) {
                    assertThat("em.im.value.multiEvent.2",
                            locateBulkBooking(bookingInformationDetail)).attributeValueOf("readonly").isEqualTo("true");
                }
            }

            if (bookingInformationDetail.getMovement() != null) {
                if (bookingInformationDetail.isMovementIsDisabled() != null && bookingInformationDetail.isMovementIsDisabled()) {
                    assertThat("em.movement.multiEvent.2",
                            locateBulkBooking(bookingInformationDetail)).attributeValueOf("disabled").isEqualTo("true");
                }
            }

            if (bookingInformationDetail.getComponent() != null) {
                if (bookingInformationDetail.isComponentIsDisabled() != null && bookingInformationDetail.isComponentIsDisabled()) {
                    assertThat("em.movement.component", locateBulkBooking(bookingInformationDetail)).attributeValueOf(
                            "disabled").isEqualTo("true");
                }
            }
            if (bookingInformationDetail.getAssetAvailableToolTipInfo() != null) {
                element("em.assetAvailable", locateBulkBooking(bookingInformationDetail)).scrollIntoView().moveTo();
                assertThat("em.bulkbooking.AssHoldingToolTip").innerText().contains(
                        bookingInformationDetail.getAssetAvailableToolTipInfo().getRealValue());
            }
            if (bookingInformationDetail.getImAssetAvailableToolTipInfo() != null) {
                element("em.imAssetAvailable", locateBulkBooking(bookingInformationDetail)).scrollIntoView().moveTo();
                assertThat("em.bulkbooking.imAssHoldingToolTip").innerText().contains(
                        bookingInformationDetail.getImAssetAvailableToolTipInfo().getRealValue());
            }

            if (bookingInformationDetail.getVmAssetAvailableToolTipInfo() != null) {
                element("em.vmAssetAvailable", locateBulkBooking(bookingInformationDetail)).scrollIntoView().moveTo();
                assertThat("em.bulkbooking.vmAssHoldingToolTip").innerText().contains(
                        bookingInformationDetail.getVmAssetAvailableToolTipInfo().getRealValue());
            }

//            if (bookingInformationDetail.getInstrumentIdAjaxSearchList() != null) {
//                assertThat("em.instru.idLinktext", bookingInformationDetail.getInstrumentIdAjaxSearchList().getRealValue()).displayed().isEqualTo(true);
//            }

            if (bookingInformationDetail.isPlusButtonIsDisplay() != null) {
                if (bookingInformationDetail.isPlusButtonIsDisplay()) {
                    element("em.plus", locateBulkBooking(bookingInformationDetail)).scrollIntoView();
                    assertThat(locator("em.plus", locateBulkBooking(bookingInformationDetail))).displayed().isEqualTo(
                            bookingInformationDetail.isPlusButtonIsDisplay());
                } else {
                    assertThat(locator("em.plus", locateBulkBooking(bookingInformationDetail))).displayed().isEqualTo(
                            bookingInformationDetail.isPlusButtonIsDisplay());
                }
            }

            if (bookingInformationDetail.isDeleteButtonIsDisplay() != null) {
                if (bookingInformationDetail.isDeleteButtonIsDisplay()) {
                    element("em.delete", locateBulkBooking(bookingInformationDetail)).scrollIntoView();
                    assertThat(locator("em.delete", locateBulkBooking(bookingInformationDetail))).displayed().isEqualTo(
                            bookingInformationDetail.isDeleteButtonIsDisplay());
                } else {
                    assertThat(locator("em.delete", locateBulkBooking(bookingInformationDetail))).displayed().isEqualTo(
                            bookingInformationDetail.isDeleteButtonIsDisplay());
                }

            }

            i++;
        }

    }

    private String locateBulkbookingForAssert(BookingInformationDetail bookingDetailInformation) {

        StringBuilder xpathArray = new StringBuilder();
        if (bookingDetailInformation.getEventId() != null){
            xpathArray.append("//div[@id='bulkBookingContent']//div[@id='B_").append(bookingDetailInformation.getEventId().getRealValue()).append("']//div[label[@class='lrs-panel-title']");
        }else{
            xpathArray.append("//div[@id='bulkBookingContent']//div[label[@class='lrs-panel-title']");
        }

        if (bookingDetailInformation.getModel() != null) {
            String value = bookingDetailInformation.getModel().getRealValue();
            xpathArray.append("[contains(text(),'").append(value).append("')]");
        }

        if (bookingDetailInformation.getBookingMovement() != null) {
            String value = bookingDetailInformation.getBookingMovement().value();
            xpathArray.append("[contains(text(),'").append(value).append("')]]/following-sibling::div[1]//tr");
        }

        if (bookingDetailInformation.getComponent() != null) {
            String value = bookingDetailInformation.getComponent().value();
            xpathArray.append("[td/select[@name='componentId']/option[text()='").append(value).append("']]");
        }

        if (bookingDetailInformation.getInstrumentId() != null) {
            String value = bookingDetailInformation.getInstrumentId().getRealValue();
            xpathArray.append("[td//input[@name='instrumentId'][@value='").append(value).append("']]");
        }

        if (bookingDetailInformation.getMovement() != null) {
            String value = bookingDetailInformation.getMovement().value();
            xpathArray.append("[td/select[@name='movement'][(option[text()='").append(value).append(
                    "' and @selected='selected']) or option[1][text()='").append(value).append("']]]");
        }

        if (bookingDetailInformation.getValue() != null) {
            String value = bookingDetailInformation.getValue().getRealValue();
            xpathArray.append("[td/input[(@name='colValue' or @name='tempColValue') and @value='").append(value).append(
                    "']]");
        }
        if (bookingDetailInformation.getRequiredAmount() != null) {
            String value = bookingDetailInformation.getRequiredAmount().getRealValue();
            xpathArray.append("[td/input[@name='requirement' and @value='").append(value).append("']]");
        }

        if (bookingDetailInformation.getVmValue() != null) {
            String value = bookingDetailInformation.getVmValue().getRealValue();
            xpathArray.append("[td/input[@name='vmColValue' and @value='").append(value).append("']]");
        }
        if (bookingDetailInformation.getImValue() != null) {
            String value = bookingDetailInformation.getImValue().getRealValue();
            xpathArray.append("[td/input[@name='imColValue' and @value='").append(value).append("']]");
        }


        if (bookingDetailInformation.getVmRequiredAmount() != null) {
            String value = bookingDetailInformation.getVmRequiredAmount().getRealValue();
            xpathArray.append("[td/input[@name='vmRequirement' and @value='").append(value).append("']]");
        }

        if (bookingDetailInformation.getBookingNumber() != null) {
            String value = bookingDetailInformation.getBookingNumber().getRealValue();
            xpathArray.append("[td[@class='c_BookingNumber' and text()='").append(value).append("']]");
        }

        return xpathArray.toString();
    }

    private String locateBulkBooking(BookingInformationDetail bookingDetailInformation) {

        StringBuilder xpathArray = new StringBuilder();
        if (bookingDetailInformation.getEventId() != null){
            xpathArray.append("//div[@id='bulkBookingContent']//div[@id='B_").append(bookingDetailInformation.getEventId().getRealValue()).append("']//div[label[@class='lrs-panel-title']");
        }else{
            xpathArray.append("//div[@id='bulkBookingContent']//div[label[@class='lrs-panel-title']");
        }

        if (bookingDetailInformation.getModel() != null) {
            String value = bookingDetailInformation.getModel().getRealValue();
            xpathArray.append("[contains(text(),'").append(value).append("')]");
        }

        if (bookingDetailInformation.getBookingMovement() != null) {
            String value = bookingDetailInformation.getBookingMovement().value();
            xpathArray.append("[contains(text(),'").append(value).append("')]");
        }
        xpathArray.append("]/following-sibling::div[1]//tr");

        if (bookingDetailInformation.getBookingNumber() != null) {
            String value = bookingDetailInformation.getBookingNumber().getRealValue();
            xpathArray.append("[td[@class='c_BookingNumber' and contains(text(),'").append(value).append("')]]");
        }

        if (bookingDetailInformation.getComponent() != null) {
            String value = bookingDetailInformation.getComponent().value();
            xpathArray.append("[td/select[@name='componentId']/option[text()='").append(value).append(
                    "' and @selected='selected']]");
        }

        if (bookingDetailInformation.getInstrumentId() != null) {
            String value = bookingDetailInformation.getInstrumentId().getRealValue();
            xpathArray.append("[td//input[@name='instrumentId'][@value='").append(value).append("']]");
        }
        if (bookingDetailInformation.getMovement() != null) {
            String value = bookingDetailInformation.getMovement().value();
            xpathArray.append("[td/select[@name='movement'][(option[text()='").append(value).append(
                    "' and @selected='selected']) or option[1][text()='").append(value).append("']]]");
        }

        if (bookingDetailInformation.getValueToLocateBooking() != null) {
            String value = bookingDetailInformation.getValueToLocateBooking().getRealValue();
            xpathArray.append("[td/input[(@name='colValue' or @name='tempColValue') and @value='").append(value).append(
                    "']]");
        }


        if (bookingDetailInformation.getRequiredAmount() != null) {
            String value = bookingDetailInformation.getRequiredAmount().getRealValue();
            xpathArray.append("[td/input[@name='requirement' and @value='").append(value).append("']]");
        }

        if (bookingDetailInformation.getVmValue() != null) {
            String value = bookingDetailInformation.getVmValue().getRealValue();
            xpathArray.append("[td/input[@name='vmColValue' and @value='").append(value).append("']]");
        }

        if (bookingDetailInformation.getVmRequiredAmount() != null) {
            String value = bookingDetailInformation.getVmRequiredAmount().getRealValue();
            xpathArray.append("[td/input[@name='vmRequirement' and @value='").append(value).append("']]");
        }
        xpathArray.append("[last()]");

        return xpathArray.toString();
    }

    private String locateBulkBookingWithMovementWithoutSelectAttribute(BookingInformationDetail bookingDetailInformation) {

        StringBuilder xpathArray = new StringBuilder();
        xpathArray.append("//div[@id='bulkBookingContent']//div[label[@class='lrs-panel-title']");
        if (bookingDetailInformation.getModel() != null) {
            String value = bookingDetailInformation.getModel().getRealValue();
            xpathArray.append("[contains(text(),'").append(value).append("')]");
        }

        if (bookingDetailInformation.getBookingMovement() != null) {
            String value = bookingDetailInformation.getBookingMovement().value();
            xpathArray.append("[contains(text(),'").append(value).append("')]");
        }
        xpathArray.append("]/following-sibling::div[1]//tr");

        if (bookingDetailInformation.getBookingNumber() != null) {
            String value = bookingDetailInformation.getBookingNumber().getRealValue();
            xpathArray.append("[td[@class='c_BookingNumber' and contains(text(),'").append(value).append("')]]");
        }

        if (bookingDetailInformation.getComponent() != null) {
            String value = bookingDetailInformation.getComponent().value();
            xpathArray.append("[td/select[@name='componentId']/option[text()='").append(value).append("']]");
        }

        if (bookingDetailInformation.getInstrumentId() != null) {
            String value = bookingDetailInformation.getInstrumentId().getRealValue();
            xpathArray.append("[td//input[@name='instrumentId'][@value='").append(value).append("']]");
        }

        if (bookingDetailInformation.getMovement() != null) {
            String value = bookingDetailInformation.getMovement().value();
            xpathArray.append("[td/select[@name='movement']/option[text()='").append(value).append("']]");
        }

        if (bookingDetailInformation.getValueToLocateBooking() != null) {
            String value = bookingDetailInformation.getValueToLocateBooking().getRealValue();
            xpathArray.append("[td/input[(@name='colValue' or @name='tempColValue') and @value='").append(value).append(
                    "']]");
        }


        if (bookingDetailInformation.getRequiredAmount() != null) {
            String value = bookingDetailInformation.getRequiredAmount().getRealValue();
            xpathArray.append("[td/input[@name='requirement' and @value='").append(value).append("']]");
        }

        if (bookingDetailInformation.getVmValue() != null) {
            String value = bookingDetailInformation.getVmValue().getRealValue();
            xpathArray.append("[td/input[@name='vmColValue' and @value='").append(value).append("']]");
        }

        if (bookingDetailInformation.getVmRequiredAmount() != null) {
            String value = bookingDetailInformation.getVmRequiredAmount().getRealValue();
            xpathArray.append("[td/input[@name='vmRequirement' and @value='").append(value).append("']]");
        }
        xpathArray.append("[last()]");

        return xpathArray.toString();
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
            int count = 1;
            for (BookingDetail booking : list) {
//                if (row > rowCount)
//                    element("em.add.booking").click();
                Locator locator = locator("em.instru.id", String.valueOf(row));
                if (booking.getInstrumentSearch() != null) {
                    StringBuffer xpath = new StringBuffer();
                    if (booking.getInstrumentSearch().getInstrument() != null) {
                        if (booking.getInstrumentSearch().getInstrument().getFilter() != null) {
                            element(locator).input(
                                    booking.getInstrumentSearch().getInstrument().getFilter().getRealValue());
                            xpath.append(booking.getInstrumentSearch().getInstrument().getFilter().getRealValue());
                        }
                    }
                    if (booking.getInstrumentSearch().getMarket() != null) {
                        if (booking.getInstrumentSearch().getMarket().getLinkText() != null) {
                            xpath.append(",").append(
                                    booking.getInstrumentSearch().getMarket().getLinkText().getRealValue());
                        }
                        element("em.instru.idLinktext", xpath.toString()).click();
                    }
                    if (booking.getInstrumentSearch().getAssetType() != null) {
                        element(locator).input(booking.getInstrumentSearch().getAssetType().getRealValue());
                    }
                }
                // there is a bug when bookingInformationDetails has more than 1 booking
                List<BookingInformationDetail> bookingInformationDetails = booking.getBookingInformation().getBookingInformationDetail();
                if (bookingInformationDetails != null && bookingInformationDetails.size() > 0) {
                    for (BookingInformationDetail bookingInformationDetail : bookingInformationDetails) {
                        if (bookingInformationDetail.getNominalAmount() != null) {
                            element("em.nominal.amt", String.valueOf(row)).click();
                            element("em.nominal.amt", String.valueOf(row)).input(
                                    bookingInformationDetail.getNominalAmount().getRealValue());
                            element("em.nominal.amt", String.valueOf(row)).fireEvent("onchange");
                        }

                        if (bookingInformationDetail.getVmAmount() != null) {
                            element("em.vm.amt", String.valueOf(row)).click();
                            element("em.vm.amt", String.valueOf(row)).input(
                                    bookingInformationDetail.getVmAmount().getRealValue());
                            element("em.vm.amt", String.valueOf(row)).fireEvent("onchange");
                        }
                        if (bookingInformationDetail.getImAmount() != null) {
                            element("em.im.amt", String.valueOf(row)).click();
                            element("em.im.amt", String.valueOf(row)).input(
                                    bookingInformationDetail.getImAmount().getRealValue());
                            element("em.im.amt", String.valueOf(row)).fireEvent("onchange");
                        }

                        if (bookingInformationDetail.getMovement() != null) {
                            element("em.movement", String.valueOf(row)).input(
                                    bookingInformationDetail.getMovement().value());
                        }
                        if (booking.getBookingInformation().getSettlementDate() != null) {
                            element("em.settlement.date", String.valueOf(row)).setAttribute("value",
                                    booking.getBookingInformation().getSettlementDate().getRealValue());
                        }
                        if (booking.getPrincipalPaymentInstructions() != null) {
                            if (booking.getPrincipalPaymentInstructions().getCapitalPayInstr() != null) {
                                element("em.prc.ssi", String.valueOf(row)).selectByVisibleText(
                                        booking.getPrincipalPaymentInstructions().getCapitalPayInstr().getRealValue());
                            }
                        }
                        if (booking.getCounterpartyPaymentInstructions() != null) {
                            if (booking.getCounterpartyPaymentInstructions().getCapitalPayInstr() != null) {
                                element("em.cpty.ssi", String.valueOf(row)).selectByVisibleText(
                                        booking.getCounterpartyPaymentInstructions().getCapitalPayInstr().getRealValue());
                            }
                        }
                        if (bookingInformationDetail.getValue() != null) {
                            element("em.value", String.valueOf(row)).input(
                                    bookingInformationDetail.getValue().getRealValue());
                        }
                        if (bookingInformationDetail.getRequiredAmount() != null) {
                            element("em.required.amount", String.valueOf(row)).input(
                                    bookingInformationDetail.getRequiredAmount().getRealValue());
                        }
                        if (bookingInformationDetail.getAssetAvailable() != null) {
                            element("em.asset.available", String.valueOf(row)).input(
                                    bookingInformationDetail.getAssetAvailable().getRealValue());
                        }
                    }

                }
                if (count < list.size()) {
                    element("em.add.bookingInformation", String.valueOf(row)).clickByJavaScript();
                }
                count++;
                row++;
            }
        }
//        element("em.save.booking").click();
//        saveBookings();
    }

    /**
     * enter BulkBooking page edit
     *
     * @param list
     */
    public void enterBulkBookingInfo(List<BookingDetail> list) throws Exception {
        int rowCount = (int) element("em.bulk.booking.table").getRowCount();
        if (list != null && list.size() > 0) {
            int row = 1;
            for (BookingDetail booking : list) {
//                if (row > rowCount)
//                    element("em.add.booking").click();
                if (row > rowCount) {
                    element("em.add.bookingInformation", String.valueOf(row - 1)).clickByJavaScript();
                }
                Locator locator = locator("em.instru.id", String.valueOf(row));
                if (booking.getInstrumentSearch() != null) {
                    StringBuffer xpath = new StringBuffer();
                    if (booking.getInstrumentSearch().getInstrument() != null) {
                        if (booking.getInstrumentSearch().getInstrument().getFilter() != null) {
                            element(locator).input(
                                    booking.getInstrumentSearch().getInstrument().getFilter().getRealValue());
                            xpath.append(booking.getInstrumentSearch().getInstrument().getFilter().getRealValue());
                        }
                    }
                    if (booking.getInstrumentSearch().getMarket() != null) {
                        if (booking.getInstrumentSearch().getMarket().getLinkText() != null) {
                            xpath.append(",").append(
                                    booking.getInstrumentSearch().getMarket().getLinkText().getRealValue());
                        }
                        element("em.instru.idLinktext", xpath.toString()).click();
                    }
                    if (booking.getInstrumentSearch().getAssetType() != null) {
                        element(locator).input(booking.getInstrumentSearch().getAssetType().getRealValue());
                    }
                }
                // there is a bug when bookingInformationDetails has more than 1 booking
                List<BookingInformationDetail> bookingInformationDetails = booking.getBookingInformation().getBookingInformationDetail();
                if (bookingInformationDetails != null && bookingInformationDetails.size() > 0) {
                    for (BookingInformationDetail bookingInformationDetail : bookingInformationDetails) {
                        if (bookingInformationDetail.getNominalAmount() != null) {
                            element("em.nominal.amt", String.valueOf(row)).click();
                            element("em.nominal.amt", String.valueOf(row)).input(
                                    bookingInformationDetail.getNominalAmount().getRealValue());
                            element("em.nominal.amt", String.valueOf(row)).fireEvent("onchange");
                        }

                        if (bookingInformationDetail.getVmAmount() != null) {
                            element("em.vm.amt", String.valueOf(row)).click();
                            element("em.vm.amt", String.valueOf(row)).input(
                                    bookingInformationDetail.getVmAmount().getRealValue());
                            element("em.vm.amt", String.valueOf(row)).fireEvent("onchange");
                        }
                        if (bookingInformationDetail.getImAmount() != null) {
                            element("em.im.amt", String.valueOf(row)).click();
                            element("em.im.amt", String.valueOf(row)).input(
                                    bookingInformationDetail.getImAmount().getRealValue());
                            element("em.im.amt", String.valueOf(row)).fireEvent("onchange");
                        }

                        if (bookingInformationDetail.getMovement() != null) {
                            element("em.movement", String.valueOf(row)).input(
                                    bookingInformationDetail.getMovement().value());
                        }
                        if (booking.getBookingInformation().getSettlementDate() != null) {
                            element("em.settlement.date", String.valueOf(row)).setAttribute("value",
                                    booking.getBookingInformation().getSettlementDate().getRealValue());
                        }
                        if (booking.getPrincipalPaymentInstructions() != null) {
                            if (booking.getPrincipalPaymentInstructions().getCapitalPayInstr() != null) {
                                element("em.prc.ssi", String.valueOf(row)).selectByVisibleText(
                                        booking.getPrincipalPaymentInstructions().getCapitalPayInstr().getRealValue());
                            }
                        }
                        if (booking.getCounterpartyPaymentInstructions() != null) {
                            if (booking.getCounterpartyPaymentInstructions().getCapitalPayInstr() != null) {
                                element("em.cpty.ssi", String.valueOf(row)).selectByVisibleText(
                                        booking.getCounterpartyPaymentInstructions().getCapitalPayInstr().getRealValue());
                            }
                        }
                        if (bookingInformationDetail.getValue() != null) {
                            element("em.value", String.valueOf(row)).input(
                                    bookingInformationDetail.getValue().getRealValue());
                        }
                        if (bookingInformationDetail.getRequiredAmount() != null) {
                            element("em.required.amount", String.valueOf(row)).input(
                                    bookingInformationDetail.getRequiredAmount().getRealValue());
                        }
                        if (bookingInformationDetail.getAssetAvailable() != null) {
                            element("em.asset.available", String.valueOf(row)).input(
                                    bookingInformationDetail.getAssetAvailable().getRealValue());
                        }
                    }

                }
//                count++;
                row++;
            }
        }
//        element("em.save.booking").click();
//        saveBookings();
    }

    /**
     * @param bookingDetailList
     * @throws Exception
     * @Author Yan Lu
     */
    public void makeBulkBookingForMultiEvent(List<BookingDetail> bookingDetailList) throws Exception {
        for (BookingDetail bookingDetail : bookingDetailList) {
            List<String> acturalNameList = new ArrayList<>();
            if (bookingDetail.getBookingInformation() != null) {
                if (bookingDetail.getBookingInformation().getBookingInformationDetail() != null &&
                        bookingDetail.getBookingInformation().getBookingInformationDetail().size() > 0) {
                    for (int i = 0; i < bookingDetail.getBookingInformation().getBookingInformationDetail().size(); i++) {
                        String acturalName = getActuralName(
                                bookingDetail.getBookingInformation().getBookingInformationDetail().get(i));
                        acturalNameList.add(acturalName);
                    }
                }
            }

            int row = 1;
            int count = 1;
            if (bookingDetail.getBookingInformation() != null) {
                if (bookingDetail.getBookingInformation().getBookingInformationDetail() != null &&
                        bookingDetail.getBookingInformation().getBookingInformationDetail().size() > 0) {
                    for (int j = 0; j < bookingDetail.getBookingInformation().getBookingInformationDetail().size(); j++) {
                        if (bookingDetail.getInstrumentSearch() != null) {
                            if (bookingDetail.getInstrumentSearch().getInstrument() != null) {
                                if (bookingDetail.getInstrumentSearch().getInstrument().getFilter() != null) {
                                    //div[label[contains(text(),'EM_3f5f825d1e9d46e7b23c291353298438/QTPPrincipal/QTPCounterparty/1686 - Return')]]
                                    // following-sibling::div//table[@class='displaytagTable']//tr[1]//td[@class='c_InstrumentID']//input
                                    element("em.instru.id.multiEvent", acturalNameList.get(row - 1),
                                            String.valueOf(row)).input(
                                            bookingDetail.getInstrumentSearch().getInstrument().getFilter().getRealValue());
                                }
//                                if (bookingDetail.getInstrumentSearch().getInstrument().getLinkText() != null){
//                                    element("em.instru.id.linktext.multiEvent",bookingDetail.getInstrumentSearch().getInstrument().getLinkText().getRealValue()).click();
//                                }
                            }
                        }
                        if (bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getNominalAmount() != null) {
                            element("em.nominal.amt.multiEvent", acturalNameList.get(row - 1),
                                    String.valueOf(row)).click();
                            element("em.nominal.amt.multiEvent", acturalNameList.get(row - 1),
                                    String.valueOf(row)).input(
                                    bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                            j).getNominalAmount().getRealValue());
                            element("em.nominal.amt.multiEvent", acturalNameList.get(row - 1),
                                    String.valueOf(row)).fireEvent("onchange");
                        }
                        if (bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getVmAmount() != null) {
                            element("em.vm.amt.multiEvent", acturalNameList.get(row - 1), String.valueOf(row)).input(
                                    bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                            j).getVmAmount().getRealValue());
                            element("em.vm.amt.multiEvent", acturalNameList.get(row - 1),
                                    String.valueOf(row)).fireEvent("onchange");
                        }
                        if (bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getImAmount() != null) {
                            element("em.im.amt.multiEvent", acturalNameList.get(row - 1), String.valueOf(row)).input(
                                    bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                            j).getImAmount().getRealValue());
                            element("em.im.amt.multiEvent", acturalNameList.get(row - 1),
                                    String.valueOf(row)).fireEvent("onchange");
                        }
                        if (bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getMovement() != null) {
                            element("em.movement.multiEvent", acturalNameList.get(row - 1), String.valueOf(row)).input(
                                    bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                            j).getMovement().value());
                        }
                        if (bookingDetail.getBookingInformation().getSettlementDate() != null) {
                            element("em.settlement.date.multiEvent", acturalNameList.get(row - 1),
                                    String.valueOf(row)).input(
                                    bookingDetail.getBookingInformation().getSettlementDate().getRealValue());
                        }
                        if (bookingDetail.getPrincipalPaymentInstructions() != null) {
                            if (bookingDetail.getPrincipalPaymentInstructions().getCapitalPayInstr() != null) {
                                element("em.prc.ssi.multiEvent", acturalNameList.get(row - 1),
                                        String.valueOf(row)).selectByVisibleText(
                                        bookingDetail.getPrincipalPaymentInstructions().getCapitalPayInstr().getRealValue());
                            }
                        }
                        if (bookingDetail.getCounterpartyPaymentInstructions() != null) {
                            if (bookingDetail.getCounterpartyPaymentInstructions().getCapitalPayInstr() != null) {
                                element("em.cpty.ssi.multiEvent", acturalNameList.get(row - 1),
                                        String.valueOf(row)).selectByVisibleText(
                                        bookingDetail.getCounterpartyPaymentInstructions().getCapitalPayInstr().getRealValue());
                            }
                        }
                        if (bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getValue() != null) {
                            element("em.value.multiEvent", acturalNameList.get(row - 1), String.valueOf(row)).input(
                                    bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                            j).getValue().getRealValue());
                        }
                        if (bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getRequiredAmount() != null) {
                            element("em.required.amount.multiEvent", acturalNameList.get(row - 1),
                                    String.valueOf(row)).input(
                                    bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                            j).getRequiredAmount().getRealValue());
                        }
                        if (count < bookingDetail.getBookingInformation().getBookingInformationDetail().size()) {
                            element("em.add.bookingInformation.multiEvent", acturalNameList.get(row - 1),
                                    String.valueOf(row)).clickByJavaScript();
                        }
                        row++;
                        count++;

                    }
                }
            }
        }
//        element("em.save.booking").click();

    }

    public void enterBulkBookingForMultiEvents(List<BookingDetail> bookingDetailList) throws Exception {
        for (BookingDetail bookingDetail : bookingDetailList) {
            enterBulkBookingForSingleBookingDetail(bookingDetail);
        }
    }

    public void enterBulkBookingForSingleBookingDetail(BookingDetail bookingDetail) throws Exception {
        List<BookingInformationDetail> bookingInformationDetail = bookingDetail.getBookingInformation().getBookingInformationDetail();
        for (int j = 0; j < bookingInformationDetail.size(); j++) {
            if (bookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getMovement() != null) {
                element("em.movement.multiEvent.option.attribute", locateBulkBookingWithMovementWithoutSelectAttribute(bookingInformationDetail.get(j)), bookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getMovement().value()).setAttribute("selected", "selected");
//                if (element("em.movement.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).getAttribute("disabled").equals("true")) {
//                } else {
//                    element("em.movement.multiEvent.option.attribute", locateBulkBookingWithMovementWithoutSelectAttribute(bookingInformationDetail.get(j)), bookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getMovement().value()).setAttribute("selected", "selected");
//                    element("em.movement.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).selectByVisibleText(bookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getMovement().value());
//                    element("em.movement.multiEvent.option.attribute", locateBulkBookingWithMovementWithoutSelectAttribute(bookingInformationDetail.get(j)), bookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getMovement().value()).setAttribute("selected", "selected");
//                }
            }
            if (bookingDetail.getInstrumentSearch() != null) {
                if (bookingDetail.getInstrumentSearch().getInstrument() != null) {
                    StringBuffer xpath = new StringBuffer();
                    if (bookingDetail.getInstrumentSearch().getInstrument().getFilter() != null) {
//                        element("em.instru.id.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).setAttribute("readonly","false");
                        element("em.instru.id.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).input(
                                bookingDetail.getInstrumentSearch().getInstrument().getFilter().getRealValue());
                        xpath.append(bookingDetail.getInstrumentSearch().getInstrument().getFilter().getRealValue());
                    }
                    if (bookingDetail.getInstrumentSearch().getInstrument().getLinkText() != null) {
                        StringType linkText = bookingDetail.getInstrumentSearch().getInstrument().getLinkText();
                        if (linkText.getRealValue().contains(","))
                            element("em.instru.idLinktext2",
                                    bookingDetail.getInstrumentSearch().getInstrument().getLinkText().getRealValue()).click();
                        else
                            element("em.instru.idLinktext2",
                                    bookingDetail.getInstrumentSearch().getInstrument().getLinkText().getRealValue() + ",").click();
                    } else if (bookingDetail.getInstrumentSearch().getMarket() != null) {
                        if (bookingDetail.getInstrumentSearch().getMarket().getLinkText() != null) {
                            xpath.append(",").append(
                                    bookingDetail.getInstrumentSearch().getMarket().getLinkText().getRealValue());
                        }
                        element("em.instru.idLinktext", xpath.toString()).click();
                    }

                    if (bookingDetail.getInstrumentSearch().getInstrument().getLinkText() != null && bookingDetail.getInstrumentSearch().getInstrument().getFilter() == null) {
                        waitThat().document().toBeReady();
                        waitThat().jQuery().toBeInactive();
                        element("em.instru.id.multiEvent.2",
                                locateBulkBooking(bookingInformationDetail.get(j))).clickByJavaScript();
//                        element("em.instru.id.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).type(Keys.ENTER);
                        waitThat().document().toBeReady();
                        waitThat().jQuery().toBeInactive();
                        element("em.instru.idLinktext",
                                bookingDetail.getInstrumentSearch().getInstrument().getLinkText().getRealValue()).click();
                    } else {
                        element("em.nominal.amt.multiEvent.2",
                                locateBulkBooking(bookingInformationDetail.get(j))).click();
                    }
                }
            }

            if (bookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getMovement() != null) {
                element("em.movement.multiEvent.option.attribute",
                        locateBulkBookingWithMovementWithoutSelectAttribute(bookingInformationDetail.get(j)),
                        bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getMovement().value()).setAttribute("selected", "selected");
//                if (element("em.movement.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).getAttribute("disabled").equals("true")) {
//                } else {
//                    element("em.movement.multiEvent.option.attribute", locateBulkBookingWithMovementWithoutSelectAttribute(bookingInformationDetail.get(j)), bookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getMovement().value()).setAttribute("selected", "selected");
//                    element("em.movement.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).selectByVisibleText(bookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getMovement().value());
//                    element("em.movement.multiEvent.option.attribute", locateBulkBookingWithMovementWithoutSelectAttribute(bookingInformationDetail.get(j)), bookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getMovement().value()).setAttribute("selected", "selected");
//                }
            }

            if (bookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getValue() != null) {
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
                element("em.value.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).click();
                element("em.value.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).input(
                        bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getValue().getRealValue()).fireEvent("onchange");
            }


            if (bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                    j).isClickApplyButton() != null && bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                    j).isClickApplyButton()) {
                waitThat("em.apply.button", locateBulkBooking(bookingInformationDetail.get(j))).toBeAllVisible();
                element("em.apply.button",
                        locateBulkBooking(bookingInformationDetail.get(j))).scrollIntoView().fireEvent(
                        Biz.FIRE_EVENT_ONCLICK);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (bookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getNominalAmount() != null) {
                waitThat("em.nominal.amt.multiEvent.2",
                        locateBulkBooking(bookingInformationDetail.get(j))).toBeAllVisible();
                element("em.nominal.amt.multiEvent.2",
                        locateBulkBooking(bookingInformationDetail.get(j))).click().input(
                        bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getNominalAmount().getRealValue());
                element("em.nominal.amt.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).fireEvent(
                        "onchange");
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (bookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getVmAmount() != null) {
                element("em.vm.amt.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).input(
                        bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getVmAmount().getRealValue());
                element("em.vm.amt.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).fireEvent(
                        "onchange");
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (bookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getImAmount() != null) {
                element("em.im.amt.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).input(
                        bookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getImAmount().getRealValue());
                element("em.im.amt.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).fireEvent(
                        "onchange");
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }

            if (bookingDetail.getBookingInformation().getSettlementDate() != null) {
//                    element("em.settlement.date.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).input(bookingDetail.getBookingInformation().getSettlementDate().getRealValue());
                String serverDate = Biz.convertDate(getServerTime(),
                        bookingDetail.getBookingInformation().getSettlementDate().getRealValue(), "M/d/yyyy");
                element("em.settlement.date.multiEvent.2",
                        locateBulkBooking(bookingInformationDetail.get(j))).scrollIntoView().clear().input(
                        serverDate).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                element("em.nominal.amt.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).click();
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();

            }
            if (bookingDetail.getPrincipalPaymentInstructions() != null) {
                if (bookingDetail.getPrincipalPaymentInstructions().getCapitalPayInstr() != null) {
                    element("em.prc.ssi.multiEvent.2",
                            locateBulkBooking(bookingInformationDetail.get(j))).selectByVisibleText(
                            bookingDetail.getPrincipalPaymentInstructions().getCapitalPayInstr().getRealValue());
                }
            }
            if (bookingDetail.getCounterpartyPaymentInstructions() != null) {
                if (bookingDetail.getCounterpartyPaymentInstructions().getCapitalPayInstr() != null) {
                    element("em.cpty.ssi.multiEvent.2",
                            locateBulkBooking(bookingInformationDetail.get(j))).selectByVisibleText(
                            bookingDetail.getCounterpartyPaymentInstructions().getCapitalPayInstr().getRealValue());
                }
            }

            if (bookingInformationDetail.get(j).isClickPlusButton() != null) {
                if (bookingInformationDetail.get(j).isClickPlusButton())
                    waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
                element("em.plus", locateBulkBooking(bookingInformationDetail.get(j))).scrollIntoView().click();
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }

            if (bookingInformationDetail.get(j).isClickDeleteButton() != null) {
                if (bookingInformationDetail.get(j).isClickDeleteButton())
                    waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
                element("em.delete", locateBulkBooking(bookingInformationDetail.get(j))).scrollIntoView().click();
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }

            if (bookingInformationDetail.get(j).getAlertHandling() != null && !bookingInformationDetail.get(
                    j).getAlertHandling().isEmpty())
                PageHelper.handleAlters(bookingInformationDetail.get(j).getAlertHandling());

        }


    }

    public void enterBulkBookingForComplexMultiEvents(List<BookingDetail> oldBookingDetailList, List<BookingDetail> newBookingDetailList) throws Exception {
        for (int j = 0; j < oldBookingDetailList.size(); j++) {
            enterBulkBookingForSingleBookingDetailWithSameColumnToLocate(oldBookingDetailList.get(j),
                    newBookingDetailList.get(j));
        }
    }

    public void enterBulkBookingForSingleBookingDetailWithSameColumnToLocate(BookingDetail oldBookingDetail, BookingDetail newBookingDetail) throws Exception {
        List<BookingInformationDetail> oldBookingInformationDetail = oldBookingDetail.getBookingInformation().getBookingInformationDetail();
        List<BookingInformationDetail> newBookingInformationDetail = newBookingDetail.getBookingInformation().getBookingInformationDetail();

        for (int j = 0; j < newBookingInformationDetail.size(); j++) {
            if (newBookingDetail.getInstrumentSearch() != null) {
                if (newBookingDetail.getInstrumentSearch().getInstrument() != null) {
                    StringBuffer xpath = new StringBuffer();
                    if (newBookingDetail.getInstrumentSearch().getInstrument().getFilter() != null) {
                        element("em.instru.id.multiEvent.2",
                                locateBulkBooking(oldBookingInformationDetail.get(j))).input(
                                newBookingDetail.getInstrumentSearch().getInstrument().getFilter().getRealValue());
                        xpath.append(newBookingDetail.getInstrumentSearch().getInstrument().getFilter().getRealValue());
                        element("em.nominal.amt.multiEvent.2",
                                locateBulkBooking(oldBookingInformationDetail.get(j))).click();
                    }
                    if (newBookingDetail.getInstrumentSearch().getMarket() != null) {
                        if (newBookingDetail.getInstrumentSearch().getMarket().getLinkText() != null) {
                            xpath.append(",").append(
                                    newBookingDetail.getInstrumentSearch().getMarket().getLinkText().getRealValue());
                        }
                        element("em.instru.idLinktext", xpath.toString()).click();
                    }

                    if (newBookingDetail.getInstrumentSearch().getInstrument().getLinkText() != null && newBookingDetail.getInstrumentSearch().getInstrument().getFilter() == null) {
                        waitThat().document().toBeReady();
                        waitThat().jQuery().toBeInactive();
                        element("em.instru.id.multiEvent.2",
                                locateBulkBooking(oldBookingInformationDetail.get(j))).clickByJavaScript();
                        waitThat().document().toBeReady();
                        waitThat().jQuery().toBeInactive();
                        element("em.instru.idLinktext",
                                newBookingDetail.getInstrumentSearch().getInstrument().getLinkText().getRealValue()).click();
                    }

                }
            }

            if (newBookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getMovement() != null) {
//                element("em.movement.multiEvent.option.attribute", locateBulkBookingWithMovementWithoutSelectAttribute(oldBookingInformationDetail.get(j)), oldBookingInformationDetail.get(j).getMovement().value()).setAttribute("selected", "selected");
                element("em.movement.multiEvent.2",
                        locateBulkBooking(oldBookingInformationDetail.get(j))).selectByVisibleText(
                        newBookingInformationDetail.get(j).getMovement().value());
//                element("em.movement.multiEvent.option.attribute", locateBulkBookingWithMovementWithoutSelectAttribute(newBookingInformationDetail.get(j)), newBookingInformationDetail.get(j).getMovement().value()).setAttribute("selected", "selected");
            }

            if (newBookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getValue() != null) {
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
                element("em.value.multiEvent.2", locateBulkBooking(oldBookingInformationDetail.get(j))).click();
                element("em.value.multiEvent.2", locateBulkBooking(oldBookingInformationDetail.get(j))).input(
                        newBookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getValue().getRealValue()).fireEvent("onchange");
            }


            if (newBookingDetail.getBookingInformation().getBookingInformationDetail().get(
                    j).isClickApplyButton() != null && newBookingDetail.getBookingInformation().getBookingInformationDetail().get(
                    j).isClickApplyButton()) {
                waitThat("em.apply.button", locateBulkBooking(oldBookingInformationDetail.get(j))).toBeAllVisible();
                element("em.apply.button",
                        locateBulkBooking(oldBookingInformationDetail.get(j))).scrollIntoView().click();
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (newBookingDetail.getBookingInformation().getBookingInformationDetail().get(
                    j).getNominalAmount() != null) {
                waitThat("em.nominal.amt.multiEvent.2",
                        locateBulkBooking(oldBookingInformationDetail.get(j))).toBeAllVisible();
                element("em.nominal.amt.multiEvent.2",
                        locateBulkBooking(oldBookingInformationDetail.get(j))).click().input(
                        newBookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getNominalAmount().getRealValue());
                element("em.nominal.amt.multiEvent.2", locateBulkBooking(oldBookingInformationDetail.get(j))).fireEvent(
                        "onchange");
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (newBookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getVmAmount() != null) {
                element("em.vm.amt.multiEvent.2", locateBulkBooking(oldBookingInformationDetail.get(j))).input(
                        newBookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getVmAmount().getRealValue());
                element("em.vm.amt.multiEvent.2", locateBulkBooking(oldBookingInformationDetail.get(j))).fireEvent(
                        "onchange");
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (newBookingDetail.getBookingInformation().getBookingInformationDetail().get(j).getImAmount() != null) {
                element("em.im.amt.multiEvent.2", locateBulkBooking(oldBookingInformationDetail.get(j))).input(
                        newBookingDetail.getBookingInformation().getBookingInformationDetail().get(
                                j).getImAmount().getRealValue());
                element("em.im.amt.multiEvent.2", locateBulkBooking(oldBookingInformationDetail.get(j))).fireEvent(
                        "onchange");
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }

            if (newBookingDetail.getBookingInformation().getSettlementDate() != null) {
//                    element("em.settlement.date.multiEvent.2", locateBulkBooking(bookingInformationDetail.get(j))).input(bookingDetail.getBookingInformation().getSettlementDate().getRealValue());
                String serverDate = Biz.convertDate(getServerTime(),
                        newBookingDetail.getBookingInformation().getSettlementDate().getRealValue(), "M/d/yyyy");
                element("em.settlement.date.multiEvent.2",
                        locateBulkBooking(oldBookingInformationDetail.get(j))).scrollIntoView().clear().input(
                        serverDate).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                element("em.nominal.amt.multiEvent.2", locateBulkBooking(oldBookingInformationDetail.get(j))).click();
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();

            }
            if (newBookingDetail.getPrincipalPaymentInstructions() != null) {
                if (newBookingDetail.getPrincipalPaymentInstructions().getCapitalPayInstr() != null) {
                    element("em.prc.ssi.multiEvent.2",
                            locateBulkBooking(oldBookingInformationDetail.get(j))).selectByVisibleText(
                            newBookingDetail.getPrincipalPaymentInstructions().getCapitalPayInstr().getRealValue());
                }
            }
            if (newBookingDetail.getCounterpartyPaymentInstructions() != null) {
                if (newBookingDetail.getCounterpartyPaymentInstructions().getCapitalPayInstr() != null) {
                    element("em.cpty.ssi.multiEvent.2",
                            locateBulkBooking(oldBookingInformationDetail.get(j))).selectByVisibleText(
                            newBookingDetail.getCounterpartyPaymentInstructions().getCapitalPayInstr().getRealValue());
                }
            }

            if (newBookingInformationDetail.get(j).isClickPlusButton() != null) {
                if (newBookingInformationDetail.get(j).isClickPlusButton())
                    waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
                element("em.plus", locateBulkBooking(oldBookingInformationDetail.get(j))).scrollIntoView().click();
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }

            if (newBookingInformationDetail.get(j).isClickDeleteButton() != null) {
                if (newBookingInformationDetail.get(j).isClickDeleteButton())
                    waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
                element("em.delete", locateBulkBooking(oldBookingInformationDetail.get(j))).scrollIntoView().click();
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }

            if (newBookingInformationDetail.get(j).getAlertHandling() != null && !newBookingInformationDetail.get(
                    j).getAlertHandling().isEmpty())
                PageHelper.handleAlters(newBookingInformationDetail.get(j).getAlertHandling());

        }


    }

    private Date getServerTime() throws Exception {
        Date date;
        String time;
        if (element("hm.server.time", "scrollingMessage").isDisplayed()) {
            time = element("hm.server.time", "scrollingMessage").getInnerText();
        } else if (element("hm.server.time", "noNewScrollingMessage").isDisplayed()) {
            time = element("hm.server.time", "noNewScrollingMessage").getInnerText();
        } else {
            time = element("hm.server.time", "nonScrollingMessage").getInnerText();
        }
        if (time == null || time.equals("")) {
            date = new Date();
        } else {
            try {
                date = new Date(time);
            } catch (IllegalArgumentException iae) {
                SimpleDateFormat s = new SimpleDateFormat("EEEE, d MMMM yyyy");
                date = s.parse(time);
            }
        }
        return date;
    }


    public String getActuralName(BookingInformationDetail bookingInformationDetail) {
        StringBuffer agreementName = new StringBuffer();
        if (bookingInformationDetail.getAgreementName() != null) {
            agreementName.append(bookingInformationDetail.getAgreementName().getRealValue());
        } else {
            if (bookingInformationDetail.getAgreementDiscription() != null) {
                agreementName.append(bookingInformationDetail.getAgreementDiscription().getRealValue()).append("/");
            }
            if (bookingInformationDetail.getPrincipalShortName() != null) {
                agreementName.append(bookingInformationDetail.getPrincipalShortName().getRealValue()).append("/");
            }
            if (bookingInformationDetail.getCounterpartyShortName() != null) {
                agreementName.append(bookingInformationDetail.getCounterpartyShortName().getRealValue()).append("/");
            }
            if (bookingInformationDetail.getAgreementId() != null) {
                agreementName.append(bookingInformationDetail.getAgreementId().getRealValue()).append(" - ");
            }
            if (bookingInformationDetail.getBookingMovement() != null) {
                agreementName.append(bookingInformationDetail.getBookingMovement().value());
            }
        }

        return agreementName.toString();
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

    //Substitution related
    public void setSubstitutionSearchCriteria(SubstitutionEventSearch substitutionEventSearch) throws Exception {
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
        if (substitutionEventSearch.getSubstitution() != null)
            element("em.sub.subsitution.type").selectByVisibleText(substitutionEventSearch.getSubstitution().value());
        if (substitutionEventSearch.getAgreementId() != null)
            element("em.sub.agrid").input(substitutionEventSearch.getAgreementId().getRealValue());
        if (substitutionEventSearch.getExternalId() != null) {
            SimpleSearch simpleSearch = substitutionEventSearch.getExternalId();
            if (simpleSearch.getFilter() != null)
                element("em.sub.externalid").input(simpleSearch.getFilter().getRealValue());
            if (simpleSearch.getLinkText() != null)
                element("em.sub.linktext", simpleSearch.getLinkText().getRealValue()).click();
        }
        if (substitutionEventSearch.getPrincipal() != null) {
            OrganisationSimpleSearch orgSimpleSearch = substitutionEventSearch.getPrincipal();
            SimpleSearch simpleSearch = null;
            if (orgSimpleSearch.getShortName() != null) {
                element("em.sub.principal.type").selectByVisibleText("Short Name");
                simpleSearch = orgSimpleSearch.getShortName();
            } else if (orgSimpleSearch.getLongName() != null) {
                element("em.sub.principal.type").selectByVisibleText("Long Name");
                simpleSearch = orgSimpleSearch.getLongName();
            } else if (orgSimpleSearch.getCode() != null) {
                element("em.sub.principal.type").selectByVisibleText("Code");
                simpleSearch = orgSimpleSearch.getCode();
            }
            if (simpleSearch != null) {
                if (simpleSearch.getFilter() != null)
                    element("em.sub.principal.filter").input(simpleSearch.getFilter().getRealValue());
                if (simpleSearch.getLinkText() != null)
                    element("em.sub.linktext", simpleSearch.getLinkText().getRealValue()).click();
            } else {
                logger.error("Simple search is null");
                throw new NullPointerException();
            }
        }
        if (substitutionEventSearch.getCounterparty() != null) {
            OrganisationSimpleSearch orgSimpleSearch = substitutionEventSearch.getCounterparty();
            SimpleSearch simpleSearch = null;
            if (orgSimpleSearch.getShortName() != null) {
                element("em.sub.counterparty.type").selectByVisibleText("Short Name");
                simpleSearch = orgSimpleSearch.getShortName();
            } else if (orgSimpleSearch.getLongName() != null) {
                element("em.sub.counterparty.type").selectByVisibleText("Long Name");
                simpleSearch = orgSimpleSearch.getLongName();
            } else if (orgSimpleSearch.getCode() != null) {
                element("em.sub.counterparty.type").selectByVisibleText("Code");
                simpleSearch = orgSimpleSearch.getCode();
            }
            if (simpleSearch != null) {
                if (simpleSearch.getFilter() != null)
                    element("em.sub.counterparty.filter").input(simpleSearch.getFilter().getRealValue());
                if (simpleSearch.getLinkText() != null)
                    element("em.sub.linktext", simpleSearch.getLinkText().getRealValue()).click();
            } else {
                logger.error("Simple search is null");
                throw new NullPointerException();
            }
        }
        if (substitutionEventSearch.getDescription() != null) {
            SimpleSearch simpleSearch = substitutionEventSearch.getDescription();
            if (simpleSearch.getFilter() != null)
                element("em.sub.description.filter").scrollIntoView().input(simpleSearch.getFilter().getRealValue());
            if (simpleSearch.getLinkText() != null)
                element("em.sub.linktext", simpleSearch.getLinkText().getRealValue()).click();
        }
        if (substitutionEventSearch.getInstrumentIds() != null && !substitutionEventSearch.getInstrumentIds().isEmpty()) {
            boolean first = true;
            for (SimpleSearch simpleSearch : substitutionEventSearch.getInstrumentIds()) {
                if (first) {
                    element("em.sub.instrumentid.filter").setValue("");
                    first = false;
                }

                if (simpleSearch.getFilter() != null)
                    element("em.sub.instrumentid.filter").type(simpleSearch.getFilter().getRealValue());
                if (simpleSearch.getLinkText() != null)
                    element("em.sub.linktext", simpleSearch.getLinkText().getRealValue()).click();
            }
        }
    }

    public void searchSubstitutionEvent() throws Exception {
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
        element("em.sub.search").click();
    }

    public void clearSubstitutionSearchCriteria() throws Exception {
        element("em.sub.clear").click();
    }


    public void tickSubstitutionEvent(SubstitutionEventDetail substitutionEventDetail) throws Exception {
        waitThat("em.sub.event.check", String.valueOf(locateSubEvent(substitutionEventDetail))).toBeVisible();
        element("em.sub.event.check", String.valueOf(locateSubEvent(substitutionEventDetail))).check(true);
    }

    public void expandSubstitutionEvent(SubstitutionEventDetail substitutionEventDetail) throws Exception {
        element("em.sub.event.expand", locateSubEvent(substitutionEventDetail).toString()).scrollIntoView().click();
    }

    public void collapseSubstitutionEvent(SubstitutionEventDetail substitutionEventDetail) throws Exception {
        element("em.sub.event.collapse", locateSubEvent(substitutionEventDetail).toString()).scrollIntoView().click();
    }

    public void tickAllSubsitutionEvent() throws Exception {
        element("em.sub.event.checkall").check(true);
    }

    public void saveSubstitutionCreation() throws Exception {
        element("em.sub.save").scrollIntoView().click();
    }

    public void cancelSubstitutionCreation() throws Exception {
        element("em.sub.cancel").click();
    }


    public void checkAlertMessage(BookingDetail bookingDetail) {
        assertThat().alert().text().isEqualToIgnoringCase(
                bookingDetail.getBookingInformation().getBookingInformationDetail().get(0).getAlertHandling().get(
                        0).getContent().getRealValue());
    }

//    public void checkAlertMessageBulkBooking(BookingDetail bookingDetail) {
//        assertThat().alert().text().isEqualToIgnoringCase(bookingDetail.getBookingInformation().getBookingInformationDetail().get(0).getAlertHandling().get(0)
//                .getContent().getRealValue());
//
//
//    }

//    private String getSubstitutionEventRow(SubstitutionEventDetail substitutionEventDetail) throws Exception {
//        StringBuffer xpath = new StringBuffer();
//        Method[] methods = substitutionEventDetail.getClass().getDeclaredMethods();
//        for (Method method : methods) {
//            if (method.getReturnType() == StringType.class && method.invoke(substitutionEventDetail) != null) {
//                StringType value = (StringType) method.invoke(substitutionEventDetail);
//                xpath.append("[td='").append(value.getRealValue()).append("']");
//            }
//        }
//        return xpath.toString();
//    }

    //Filter handling
    public void addFilter(ExposureManagementFilter filter) throws Exception {
        element("em.menu3", "User Filters").moveTo();
        element("filter.add").click();
        if (filter.getFilter() != null) {
            FilterType filterType = filter.getFilter();
            if (filterType.getFilterName() != null)
                element("filter.name").input(filterType.getFilterName().getRealValue());
            if (filterType.getFilterDescription() != null)
                element("filter.description").input(filterType.getFilterDescription().getRealValue());
            if (filter.getCopyFilterMenu() != null)
                element("filter.copy.category").selectByVisibleText(filter.getCopyFilterMenu().getRealValue());
            if (filterType.getCopyFilter() != null)
                element("filter.copy.filter").selectByVisibleText(filterType.getCopyFilter().getRealValue());
        }
        if (filter.getIcon() != null && !filter.getIcon().isEmpty()) {
            for (IconType iconType : filter.getIcon()) {
                element("filter.icon", iconType.value()).setAttribute("class",
                        iconType.value() + " ui-selectee ui-selected");
            }
        }
        element("filter.save").click();
    }

    public void changeBookings() throws Exception {
        element("em.change.bookings").clickByJavaScript();
    }

    public void validate() throws Exception {
        element("em.validate").clickByJavaScript();
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
    }

    public void saveBookings() throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("em.save.booking").clickByJavaScript();
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
    }

    public String getEventId(ExposureEventDetail ExposureEventDetail) throws Exception {
        String eventId;
        StringBuilder xpath = locateEvent(ExposureEventDetail);
        if (element("em.event.id", xpath.toString()).isDisplayed() &&
                ((ExposureEventDetail.isApply() != null) && ExposureEventDetail.isApply() || (ExposureEventDetail.isApply() == null))) {
            eventId = element("em.event.id", xpath.toString()).getInnerText().trim();
            return eventId;
        } else {
            return null;
        }
    }

    public void assertIaByPortfolioIaTypeInPortfolioIaDialog(IaByPortfolioIaType iaByPortfolioIaType) throws Exception{
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");

        //verify if category has related system for IA
        if ((iaByPortfolioIaType.getVariation() != null && iaByPortfolioIaType.getVariation().equalsIgnoreCase("hasCategory"))
                && (iaByPortfolioIaType.getName() != null)
                && (iaByPortfolioIaType.getIaCategory() != null)){
            assertThat("em.principal.ia.system",iaByPortfolioIaType.getIaCategory().getRealValue()).innerText().isEqualToIgnoringCase(iaByPortfolioIaType.getName());
        }

        //verify PrincipalIaAmount and if it is disabled
        if (iaByPortfolioIaType.getPrincipalIaAmount() != null){
            if (iaByPortfolioIaType.getIaCategory() != null){
                if (iaByPortfolioIaType.getPrincipalIaAmount().isDisable() != null && iaByPortfolioIaType.getPrincipalIaAmount().isDisable() ){
                    validateThat("em.principal.ia.amount.categoryLevel",iaByPortfolioIaType.getIaCategory().getRealValue()).attributeValueOf("readonly").isEqualToIgnoringWhitespace(
                            "true");
                }
                try {
                    validateThat("em.principal.ia.amount.categoryLevel",iaByPortfolioIaType.getIaCategory().getRealValue()).attributeValueOf("value").isEqualToIgnoringWhitespace(
                            format.format(Double.parseDouble(iaByPortfolioIaType.getPrincipalIaAmount().getRealValue())));
                } catch (NumberFormatException nfe) {
                    validateThat("em.principal.ia.amount.categoryLevel",iaByPortfolioIaType.getIaCategory().getRealValue()).attributeValueOf("value").isEqualToIgnoringWhitespace(
                            iaByPortfolioIaType.getPrincipalIaAmount().getRealValue());
                }
            }
            if (iaByPortfolioIaType.getIaSystem() != null){
                if (iaByPortfolioIaType.getVariation() != null && iaByPortfolioIaType.getVariation().equalsIgnoreCase("hasCategory")){
                    if (iaByPortfolioIaType.getPrincipalIaAmount().isDisable() != null && iaByPortfolioIaType.getPrincipalIaAmount().isDisable() ){
                        validateThat("em.principal.ia.amount.systemLevel.withCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).attributeValueOf("readonly").isEqualToIgnoringWhitespace(
                                "true");
                    }
                    try {
                        validateThat("em.principal.ia.amount.systemLevel.withCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).attributeValueOf("value").isEqualToIgnoringWhitespace(
                                format.format(Double.parseDouble(iaByPortfolioIaType.getPrincipalIaAmount().getRealValue())));
                    } catch (NumberFormatException nfe) {
                        validateThat("em.principal.ia.amount.systemLevel.withCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).attributeValueOf("value").isEqualToIgnoringWhitespace(
                                iaByPortfolioIaType.getPrincipalIaAmount().getRealValue());
                    }
                }else {
                    if (iaByPortfolioIaType.getPrincipalIaAmount().isDisable() != null && iaByPortfolioIaType.getPrincipalIaAmount().isDisable() ){
                        validateThat("em.principal.ia.amount.systemLevel.withoutCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).attributeValueOf("readonly").isEqualToIgnoringWhitespace(
                                "true");
                    }
                    try {
                        validateThat("em.principal.ia.amount.systemLevel.withoutCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).attributeValueOf("value").isEqualToIgnoringWhitespace(
                                format.format(Double.parseDouble(iaByPortfolioIaType.getPrincipalIaAmount().getRealValue())));
                    } catch (NumberFormatException nfe) {
                        validateThat("em.principal.ia.amount.systemLevel.withoutCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).attributeValueOf("value").isEqualToIgnoringWhitespace(
                                iaByPortfolioIaType.getPrincipalIaAmount().getRealValue());
                    }
                }
            }
        }

        //verify CounterpartyIaAmount and if it is disabled
        if (iaByPortfolioIaType.getCounterpartyIaAmount() != null){
            if (iaByPortfolioIaType.getIaCategory() != null){
                if (iaByPortfolioIaType.getCounterpartyIaAmount().isDisable() != null && iaByPortfolioIaType.getCounterpartyIaAmount().isDisable() ){
                    validateThat("em.counterparty.ia.amount.categoryLevel",iaByPortfolioIaType.getIaCategory().getRealValue()).attributeValueOf("readonly").isEqualToIgnoringWhitespace(
                            "true");
                }
                try {
                    validateThat("em.counterparty.ia.amount.categoryLevel",iaByPortfolioIaType.getIaCategory().getRealValue()).attributeValueOf("value").isEqualToIgnoringWhitespace(
                            format.format(Double.parseDouble(iaByPortfolioIaType.getCounterpartyIaAmount().getRealValue())));
                } catch (NumberFormatException nfe) {
                    validateThat("em.counterparty.ia.amount.categoryLevel",iaByPortfolioIaType.getIaCategory().getRealValue()).attributeValueOf("value").isEqualToIgnoringWhitespace(
                            iaByPortfolioIaType.getCounterpartyIaAmount().getRealValue());
                }
            }
            if (iaByPortfolioIaType.getIaSystem() != null){
                if (iaByPortfolioIaType.getVariation() != null && iaByPortfolioIaType.getVariation().equalsIgnoreCase("hasCategory")){
                    if (iaByPortfolioIaType.getCounterpartyIaAmount().isDisable() != null && iaByPortfolioIaType.getCounterpartyIaAmount().isDisable() ){
                        validateThat("em.counterparty.ia.amount.systemLevel.withCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).attributeValueOf("readonly").isEqualToIgnoringWhitespace(
                                "true");
                    }
                    try {
                        validateThat("em.counterparty.ia.amount.systemLevel.withCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).attributeValueOf("value").isEqualToIgnoringWhitespace(
                                format.format(Double.parseDouble(iaByPortfolioIaType.getCounterpartyIaAmount().getRealValue())));
                    } catch (NumberFormatException nfe) {
                        validateThat("em.counterparty.ia.amount.systemLevel.withCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).attributeValueOf("value").isEqualToIgnoringWhitespace(
                                iaByPortfolioIaType.getCounterpartyIaAmount().getRealValue());
                    }
                }else {
                    if (iaByPortfolioIaType.getCounterpartyIaAmount().isDisable() != null && iaByPortfolioIaType.getCounterpartyIaAmount().isDisable() ){
                        validateThat("em.counterparty.ia.amount.systemLevel.withoutCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).attributeValueOf("readonly").isEqualToIgnoringWhitespace(
                                "true");
                    }
                    try {
                        validateThat("em.counterparty.ia.amount.systemLevel.withoutCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).attributeValueOf("value").isEqualToIgnoringWhitespace(
                                format.format(Double.parseDouble(iaByPortfolioIaType.getCounterpartyIaAmount().getRealValue())));
                    } catch (NumberFormatException nfe) {
                        validateThat("em.counterparty.ia.amount.systemLevel.withoutCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).attributeValueOf("value").isEqualToIgnoringWhitespace(
                                iaByPortfolioIaType.getCounterpartyIaAmount().getRealValue());
                    }
                }
            }
        }
    }

    public void assertNetPortfolioIaInPortfolioIaDialog(AgreementExposureSummary summary) throws Exception{
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");

        if (summary.getNetPortfolioIa().getPrincipalIaAmount() != null){
            if (summary.getNetPortfolioIa().getPrincipalIaAmount().isDisable() != null && summary.getNetPortfolioIa().getPrincipalIaAmount().isDisable() ){
                validateThat("em.NetPortfolioIa.principalIa").attributeValueOf("readonly").isEqualToIgnoringWhitespace(
                        "true");
            }
            try {
                validateThat("em.NetPortfolioIa.principalIa").attributeValueOf("value").isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(summary.getNetPortfolioIa().getPrincipalIaAmount().getRealValue())));
            } catch (NumberFormatException nfe) {
                validateThat("em.NetPortfolioIa.principalIa").attributeValueOf("value").isEqualToIgnoringWhitespace(
                        summary.getNetPortfolioIa().getPrincipalIaAmount().getRealValue());
            }
        }

        if (summary.getNetPortfolioIa().getCounterpartyIaAmount() != null){
            if (summary.getNetPortfolioIa().getCounterpartyIaAmount().isDisable() != null && summary.getNetPortfolioIa().getCounterpartyIaAmount().isDisable() ){
                validateThat("em.NetPortfolioIa.counterpartyIa").attributeValueOf("readonly").isEqualToIgnoringWhitespace(
                        "true");
            }
            try {
                validateThat("em.NetPortfolioIa.counterpartyIa").attributeValueOf("value").isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(summary.getNetPortfolioIa().getCounterpartyIaAmount().getRealValue())));
            } catch (NumberFormatException nfe) {
                validateThat("em.NetPortfolioIa.counterpartyIa").attributeValueOf("value").isEqualToIgnoringWhitespace(
                        summary.getNetPortfolioIa().getCounterpartyIaAmount().getRealValue());
            }
        }
    }

    public void assertPortfolioIaDialog(AgreementExposureSummary summary) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");

        if (summary.getIaByPortfolioIaType() != null && summary.getIaByPortfolioIaType().size() > 0) {
            for (IaByPortfolioIaType iaByPortfolioIaType : summary.getIaByPortfolioIaType()) {
                assertIaByPortfolioIaTypeInPortfolioIaDialog(iaByPortfolioIaType);
            }
        }
        if (summary.getNetPortfolioIa() != null) {
            assertNetPortfolioIaInPortfolioIaDialog(summary);
        }

    }




}