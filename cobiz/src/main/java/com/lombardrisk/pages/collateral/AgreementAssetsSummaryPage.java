package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.AssetBookingSummary;
import com.lombardrisk.pojo.BookingDetail;
import com.lombardrisk.pojo.SecuritySearch;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementAssetsSummaryPage extends PageBase {
    public AgreementAssetsSummaryPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void enterBookingEditor(BookingDetail booking) throws Exception {
        if (booking.isEnterBookingEditor() == null || booking.isEnterBookingEditor()) {
            if (booking.getInstrumentSearch() == null) {
                // make cash booking
                element("ag.add.book").clickByJavaScript();
            } else {
                // make security booking
                //this click method is not use under IE10 and nativeEvents = true
                //element("ag.existing.instru").click();

                element("ag.existing.instru").clickByJavaScript();
                element("ag.instru.frame").switchTo();
                waitThat("hm.frame.body").innerText().notToBe("false");
                searchInstrument(booking.getInstrumentSearch());
                switchTo().defaultContent();
            }
        } else if (!booking.isEnterBookingEditor()) {

        }
    }


    public void InstrumentSearch(BookingDetail booking) throws Exception {
        if (booking.isEnterBookingEditor() == null || booking.isEnterBookingEditor()) {
            if (booking.getInstrumentSearch() == null) {
                // make cash booking
                element("ag.add.book").clickByJavaScript();
            } else {
                element("ag.existing.instru").clickByJavaScript();
                element("ag.instru.frame").switchTo();
                waitThat("hm.frame.body").innerText().notToBe("false");
                searchInstrument(booking.getInstrumentSearch());

            }
        } else if (!booking.isEnterBookingEditor()) {


        }
    }


    /**
     * search instrument on the prompt frame
     *
     * @param search
     */
    public void searchInstrument(SecuritySearch search) throws Exception {
        waitThat("ag.frame.clear").toBeVisible();
        element("ag.frame.clear").click();
        if (search.getInstrumentId() != null) {
            if (search.getInstrumentId().getType() != null)
                element("ag.frame.criteria").selectByVisibleText(search.getInstrumentId().getType().value());
            if (search.getInstrumentId().getFilter() != null)
                element("ag.frame.filter").input(search.getInstrumentId().getFilter().getRealValue());
        }
        if (search.getDescription() != null) {
            if (search.getDescription().getType() != null)
                element("ag.frame.desc.criteria").selectByVisibleText(search.getDescription().getType().value());
            if (search.getDescription().getFilter() != null)
                element("ag.frame.desc.filter").input(search.getDescription().getFilter().getRealValue());
        }
        if (search.getMarket() != null) {
            if (search.getMarket().getType() != null)
                element("ag.frame.mkt.criteria").selectByVisibleText(search.getMarket().getType().value());
            if (search.getMarket().getFilter() != null)
                element("ag.frame.mkt.filter").input(search.getMarket().getFilter().getRealValue());
        }
        if (search.getMaturityDate() != null)
            element("ag.frame.maturity.date").input(search.getMaturityDate().getRealValue());
        element("ag.frame.search").click();
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        if (search.getLinkText() != null)
            element("ag.frame.link", search.getLinkText().getRealValue()).click();
            waitThat().document().toBeReady();
            waitThat().jQuery().toBeInactive();
    }




    /**
     * move to cash movement summary from cash summary
     */
    public void viewCashMovementsSummary() throws Exception {
        element("ag.cash.movement.smry").click();
    }

    /**
     * view statement
     */
    public void viewStatement() throws Exception {
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        element("ag.view.statement").click();
    }

    public void viewSimulation() throws Exception {
        element("ag.view.simulation").click();

    }

    /**
     * click back image
     */
    public void back() throws Exception {
        element("ag.back").click();
    }

    /**
     * expand the asset type for non-cash booking or directly enter booking
     * editor page by specified booking result in group booking page
     *
     * @param booking
     */
    public void enterGroupBookingEditor(AssetBookingSummary booking) throws Exception {
        if (booking.getBookingId() == null || booking.getBookingId().getRealValue().isEmpty())
            setGroupBookingId(booking);
        // click + image if it exists for non-cash booking
        logger.info("booking id is " + booking.getBookingId().getRealValue());
        if (!booking.getAssetType().getRealValue().toLowerCase().contains("cash"))
            element("ag.booking.expand", booking.getBookingId().getRealValue()).clickSmartly();
        // click edit image
        element("ag.booking.edit", booking.getBookingId().getRealValue()).clickByJavaScript();
    }



    /**
     * set booking id with the largest booking id on asset summary page for
     * specified instrument when try visit existing booking
     *
     * @param result
     */
    private void setGroupBookingId(AssetBookingSummary result) throws Exception {
        if (!result.getAssetType().getRealValue().toLowerCase().contains("cash"))
            expandAssetType(result);
        for (IWebDriverWrapper.IWebElementWrapper el : getGroupAssetBookingSummaryIdCell(result)) {
            if (result.getBookingId() == null
                    || (new Integer(result.getBookingId().getRealValue())).compareTo(new Integer(el.getInnerText().trim())) < 0) {
                String id = el.getInnerText().trim();
                if (result.getBookingId() != null)
                    result.getBookingId().setValue(id);
                else
                    result.setBookingId(new StringType(id));
            }
        }
    }

    private List<IWebDriverWrapper.IWebElementWrapper> getGroupAssetBookingSummaryIdCell(AssetBookingSummary result) throws Exception {
        Method[] methods = result.getClass().getDeclaredMethods();
        StringBuffer xpath = new StringBuffer();
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        long index = element("ag.booking.id.col").getCellColumn();
        if (!result.getAssetType().getRealValue().toLowerCase().contains("cash")) {
            for (Method method : methods) {
                if (method.getName().startsWith("get")
                        && method.invoke(result) != null
                        && method.getReturnType() == StringType.class) {
                    StringType stringType = (StringType) method.invoke(result);
                    if (method.getName().equals("getAssetType")
                            || method.getName().equals("getInstrumentId")
                            || method.getName().equals("getDenomCcy")
                            || method.getName().equals("getDirtyPrice")
                            || method.getName().equals("getCleanPrince")
                            || method.getName().equals("getValuationPercentage")
                            || method.getName().equals("getMaturityDate")) {
                        xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                    if (method.getName().equals("getNominalTotalAmount")
                            || method.getName().equals("getMarketValueTotalAmount")
                            || method.getName().equals("getCollateralValueTotalAmount")
                            || method.getName().equals("getCollateralValueBaseCcyTotalAmount")) {
                        xpath.append("[td/b[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                }
            }
            xpath.append("/following-sibling::tr");
            for (Method method : methods) {
                if (method.getName().startsWith("get")
                        && method.invoke(result) != null
                        && method.getReturnType() == StringType.class) {
                    StringType stringType = (StringType) method.invoke(result);
                    if (method.getName().equals("getNominalAmount")
                            || method.getName().equals("getMovement")
                            || method.getName().equals("getStatus")
                            || method.getName().equals("getBookingSource")
                            || method.getName().equals("getPostApprovalStatus")
                            || method.getName().equals("getValuation")
                            || method.getName().equals("getMarketValue")
                            || method.getName().equals("getCollateralValue")
                            || method.getName().equals("getCollateralValueBaseCCy")
                            || method.getName().equals("getDetails")
                            || method.getName().equals("getSettlementDate")) {
                        xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                }
            }
        } else {
            for (Method method : methods) {
                if (method.getName().startsWith("get")
                        && method.invoke(result) != null
                        && method.getReturnType() == StringType.class) {
                    StringType stringType = (StringType) method.invoke(result);
                    if (method.getName().equals("getAssetType")) {
                        xpath.append("[td[contains(text()[2],'").append(stringType.getRealValue()).append("')]]");
                    } else if (method.getName().equals("getValuationPercentage")
                            || method.getName().equals("getVmMarketValue")
                            || method.getName().equals("getImMarketValue")
                            || method.getName().equals("getVmCollateralValue")
                            || method.getName().equals("getImCollateralValue")
                            || method.getName().equals("getVmCollateralValueBaseCCy")
                            || method.getName().equals("getImCollateralValueBaseCCy")
                            || method.getName().equals("getNominalAmount")) {
                        try {
                            xpath.append("[td[contains(text(),'").append(format.format(Float.parseFloat(stringType.getRealValue()))).append("')]]");
                        } catch (NumberFormatException e) {
                            xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                        }
                    } else {
                        xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                }
            }
        }
        return element("ag.booking.result.cell", xpath.toString(), String.valueOf(index)).getAllMatchedElements();
    }

    /**
     * expand the asset type for non-cash booking or directly enter booking
     * editor page by specified booking result
     *
     * @param booking
     */
    public void enterBookingEditor(AssetBookingSummary booking) throws Exception {
        if (booking.getBookingId() == null || booking.getBookingId().getRealValue().isEmpty())
            setBookingId(booking);
        // click + image if it exists for non-cash booking
        logger.info("booking id is " + booking.getBookingId().getRealValue());
        if (!element("ag.booking.pageTitle").getInnerText().trim().contains("Cash"))
            element("ag.booking.expand", booking.getBookingId().getRealValue()).clickSmartly();
        // click edit image
        element("ag.booking.edit", booking.getBookingId().getRealValue()).clickByJavaScript();
    }

    /**
     * set booking id with the largest booking id on asset summary page for
     * specified instrument when try visit existing booking
     *
     * @param result
     */
    private void setBookingId(AssetBookingSummary result) throws Exception {
        if (result.getBookingId() == null
                || result.getBookingId().isApply() == null
                || result.getBookingId().isApply()) {
            if (!element("ag.booking.pageTitle").getInnerText().trim().contains("Cash"))
                expandAssetType(result);
            if (getAssetBookingSummaryIdCell(result) != null && !getAssetBookingSummaryIdCell(result).isEmpty()) {
                for (IWebDriverWrapper.IWebElementWrapper el : getAssetBookingSummaryIdCell(result)) {
                    if (result.getBookingId() == null
                            || (new Integer(result.getBookingId().getRealValue())).compareTo(
                            new Integer(el.getInnerText().trim())) < 0) {
                        String id = el.getInnerText().trim();
                        if (result.getBookingId() != null)
                            result.getBookingId().setValue(id);
                        else
                            result.setBookingId(new StringType(id));
                    }
                }
            }
        }
    }

    private List<IWebDriverWrapper.IWebElementWrapper> getAssetBookingSummaryIdCell(AssetBookingSummary result) throws Exception {
        long index = element("ag.booking.id.col").getCellColumn();
        return element("ag.booking.result.cell", getAssetBookingSummaryXpath(result), String.valueOf(index)).scrollIntoView().getAllMatchedElements();
    }

    private String getAssetBookingSummaryXpath(AssetBookingSummary assetBookingSummary) throws Exception {
        Method[] methods = assetBookingSummary.getClass().getDeclaredMethods();
        StringBuffer xpath = new StringBuffer();
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if (!element("ag.booking.pageTitle").getInnerText().trim().contains("Cash")) {
            for (Method method : methods) {
                if (method.getName().startsWith("get")
                        && method.invoke(assetBookingSummary) != null
                        && method.getReturnType() == StringType.class) {
                    StringType stringType = (StringType) method.invoke(assetBookingSummary);
                    if (method.getName().equals("getAssetType")
                            || method.getName().equals("getInstrumentId")
                            || method.getName().equals("getDenomCcy")
                            || method.getName().equals("getDirtyPrice")
                            || method.getName().equals("getCleanPrince")
                            || method.getName().equals("getValuationPercentage")
                            || method.getName().equals("getMaturityDate")) {
                        xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                    if (method.getName().equals("getNominalTotalAmount")
                            || method.getName().equals("getMarketValueTotalAmount")
                            || method.getName().equals("getCollateralValueTotalAmount")
                            || method.getName().equals("getCollateralValueBaseCcyTotalAmount")) {
                        xpath.append("[td/b[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                }
            }
            xpath.append("/following-sibling::tr");
            for (Method method : methods) {
                if (method.getName().startsWith("get")
                        && method.invoke(assetBookingSummary) != null
                        && method.getReturnType() == StringType.class) {
                    StringType stringType = (StringType) method.invoke(assetBookingSummary);
                    if (method.getName().equals("getNominalAmount")
                            || method.getName().equals("getMovement")
                            || method.getName().equals("getStatus")
                            || method.getName().equals("getBookingSource")
                            || method.getName().equals("getPostApprovalStatus")
                            || method.getName().equals("getValuation")
                            || method.getName().equals("getMarketValue")
                            || method.getName().equals("getCollateralValue")
                            || method.getName().equals("getCollateralValueBaseCCy")
                            || method.getName().equals("getDetails")
                            || method.getName().equals("getSettlementDate")) {
                        xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                }
            }
        } else {
            for (Method method : methods) {
                if (method.getName().startsWith("get")
                        && method.invoke(assetBookingSummary) != null
                        && method.getReturnType() == StringType.class) {
                    StringType stringType = (StringType) method.invoke(assetBookingSummary);
                    if (method.getName().equals("getAssetType")) {
                        xpath.append("[td[contains(text()[2],'").append(stringType.getRealValue()).append("')]]");
                    } else if (method.getName().equals("getValuationPercentage")
                            || method.getName().equals("getVmMarketValue")
                            || method.getName().equals("getImMarketValue")
                            || method.getName().equals("getVmCollateralValue")
                            || method.getName().equals("getImCollateralValue")
                            || method.getName().equals("getVmCollateralValueBaseCCy")
                            || method.getName().equals("getImCollateralValueBaseCCy")
                            || method.getName().equals("getNominalAmount")) {
                        xpath.append("[td[contains(text(),'");
                        try {
                            xpath.append(format.format(Float.parseFloat(stringType.getRealValue())));
                        } catch (NumberFormatException e) {
                            xpath.append(stringType.getRealValue());
                        }
                        xpath.append("')]]");
                    } else {
                        xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                }
            }
        }
        return xpath.toString();
    }

    public void expandOrCollapseAssetType(String expandOrCollapse, AssetBookingSummary result) throws Exception {
        if (expandOrCollapse == "expand"){
            expandAssetType(result);
        }else if(expandOrCollapse == "collapse"){
            collapseAssetType(result);
        }
    }

    private void collapseAssetType(AssetBookingSummary result) throws Exception {
        StringBuffer attributes = new StringBuffer();
        Method[] methods = result.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")
                    && method.invoke(result) != null
                    && method.getReturnType() == StringType.class) {
                StringType stringType = (StringType) method.invoke(result);
                if (method.getName().equals("getAssetType")
                        || method.getName().equals("getInstrumentId")
                        || method.getName().equals("getDirtyPrice")
                        || method.getName().equals("getCleanPrince")
                        || method.getName().equals("getValuationPercentage")
                        || method.getName().equals("getDenomCcy")
                        || method.getName().equals("getMaturityDate")
                        || method.getName().equals("")) {
                    attributes.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                } else if (method.getName().equals("getNominalTotalAmount")
                        || method.getName().equals("getMarketValueTotalAmount")
                        || method.getName().equals("getCollateralValueTotalAmount")
                        || method.getName().equals("getCollateralValueBaseCcyTotalAmount")) {
                    attributes.append("[td/b[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                }
//                else {
//                    attributes.append("[following-sibling::tr[td[contains(.,'" + stringType.getRealValue() + "')]]]");
//                }
            }
        }
        // click + image if it exists for non-cash booking
//        if(element("ag.booking.expand2", attributes.toString()).isDisplayed())
        element("ag.booking.collapse", attributes.toString()).click();
    }

    private void expandAssetType(AssetBookingSummary result) throws Exception {
        StringBuffer attributes = new StringBuffer();
        Method[] methods = result.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")
                    && method.invoke(result) != null
                    && method.getReturnType() == StringType.class) {
                StringType stringType = (StringType) method.invoke(result);
                if (method.getName().equals("getAssetType")
                        || method.getName().equals("getInstrumentId")
                        || method.getName().equals("getDirtyPrice")
                        || method.getName().equals("getCleanPrince")
                        || method.getName().equals("getValuationPercentage")
                        || method.getName().equals("getDenomCcy")
                        || method.getName().equals("getMaturityDate")
                        || method.getName().equals("")) {
                    attributes.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                } else if (method.getName().equals("getNominalTotalAmount")
                        || method.getName().equals("getMarketValueTotalAmount")
                        || method.getName().equals("getCollateralValueTotalAmount")
                        || method.getName().equals("getCollateralValueBaseCcyTotalAmount")) {
                    attributes.append("[td/b[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                }
//                else {
//                    attributes.append("[following-sibling::tr[td[contains(.,'" + stringType.getRealValue() + "')]]]");
//                }
            }
        }
        // click + image if it exists for non-cash booking
//        if(element("ag.booking.expand2", attributes.toString()).isDisplayed())
        element("ag.booking.expand2", attributes.toString()).click();
    }

    /**
     * set booking id with the largest booking id on asset summary page for
     * specified instrument when just created a new booking
     *
     * @param booking
     */
    private void setBookingId(BookingDetail booking) throws Exception {
        // get the id column index
        long index = element("ag.booking.id.col").getCellColumn();
        String id;
        if (!element("ag.cash.book.result").isDisplayed()) {
            // if (booking.getInstrumentSearch() != null) {
            if (booking.getInstrumentSearch().getLinkText() != null) {
                // click the + image to expand the booking tree
                element("ag.booking.expand", booking.getInstrumentSearch().getLinkText().getRealValue()).clickSmartly();
                // set non-cash booking id
                id = element("ag.booking.result.cell2",
                        booking.getInstrumentSearch().getLinkText().getRealValue(), String.valueOf(index)).getInnerText().trim();
            } else {
                throw new RuntimeException("instrument id must be specified while updating booking id");
            }
        } else {
            // set cash booking id
            id = element("ag.cash.booking.id.cell", String.valueOf(index)).getInnerText().trim();
        }
        if (booking.getBookingId() != null)
            booking.getBookingId().setValue(id);
        else
            booking.setBookingId(new StringType(id));
        logger.info("booking id is " + booking.getBookingId().getRealValue());
    }


    public void assertAssetHoldingDetailForCash(AssetBookingSummary booking) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if (booking.isApply() == null || booking.isApply()) {
            if (booking.getBookingId() == null || booking.getBookingId().getRealValue().isEmpty()) {
                setBookingId(booking);
            }
            logger.info("booking id is " + booking.getBookingId().getRealValue());
            if (booking.getNominalAmount() != null) {
                assertThat("ag.booking.check.nominal.amount.cash", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(booking.getNominalAmount().getRealValue());
            }
            if (booking.getMovement() != null) {
                assertThat("ag.booking.check.movement.cash", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(booking.getMovement().getRealValue());
            }
            if (booking.getStatus() != null) {
                assertThat("ag.booking.check.status.cash", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(booking.getStatus().getRealValue());
            }
            if (booking.getBookingSource() != null) {
                assertThat("ag.booking.check.booking.source.cash", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(booking.getBookingSource().getRealValue());
            }
            if (booking.getVmMarketValue() != null) {
                try {
                    assertThat("ag.booking.check.vm.market.cash", booking.getBookingId().getRealValue())
                            .innerText().isEqualToIgnoringWhitespace(
                            format.format(Double.parseDouble(booking.getVmMarketValue().getRealValue())));
                }catch (NumberFormatException nfe) {
                    assertThat("ag.booking.check.vm.market.cash", booking.getBookingId().getRealValue())
                            .innerText().isEqualToIgnoringWhitespace(booking.getVmMarketValue().getRealValue());
                }
            }

            if (booking.getImMarketValue() != null) {
                try {
                    assertThat("ag.booking.check.im.market.cash", booking.getBookingId().getRealValue())
                            .innerText().isEqualToIgnoringWhitespace(
                            format.format(Double.parseDouble(booking.getImMarketValue().getRealValue())));
                }catch (NumberFormatException nfe) {
                    assertThat("ag.booking.check.im.market.cash", booking.getBookingId().getRealValue())
                            .innerText().isEqualToIgnoringWhitespace(
                            booking.getImMarketValue().getRealValue());
                }
            }
            if (booking.getModel() != null) {
                assertThat("ag.booking.check.model.cash", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(booking.getModel().getRealValue());
            }
            if (booking.getCreationDate() != null){
                if ((booking.getCreationDate().getRealValue().contains("t")) || (booking.getCreationDate().getRealValue().contains("T"))){
                    String creationDate = Biz.convertDate(getServerTime(), booking.getCreationDate().getRealValue(), "M/d/yyyy");
                    assertThat("ag.booking.check.creation.date",booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(creationDate);
                } else {
                    assertThat("ag.booking.check.creation.date",booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(booking.getCreationDate().getRealValue());
                }
            }
        }else if (!booking.isApply()) {
            long index = element("ag.booking.id.col").getCellColumn();
            assertThat("ag.booking.result.cell", getAssetBookingSummaryXpath(booking), String.valueOf(index)).numberOfElements().isEqualTo(0);
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



    public void assertAssetHodlingDeatailForNonCash(AssetBookingSummary booking) throws Exception {
        if (booking.getInstrumentId() != null) {
            if (booking.getDirtyPrice() != null) {
                assertThat("ag.booking.check.dirty.price", booking.getInstrumentId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                        booking.getDirtyPrice().getRealValue());
            }
            if (booking.getCleanPrice() != null) {
                assertThat("ag.booking.check.clean.price", booking.getInstrumentId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                        booking.getCleanPrice().getRealValue());
            }
            if (booking.getValuationPercentage() != null) {
                assertThat("ag.booking.check.valuation.percentage", booking.getValuationPercentage().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                        booking.getValuationPercentage().getRealValue());
            }
            if (booking.getDenomCcy() != null) {
                assertThat("ag.booking.check.denom.ccy", booking.getInstrumentId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                        booking.getDenomCcy().getRealValue());
            }
            if (booking.getMaturityDate() != null) {
                assertThat("ag.booking.check.maturity.date", booking.getInstrumentId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                        booking.getMaturityDate().getRealValue());
            }
            if (booking.getNominalTotalAmount() != null) {
                assertThat("ag.booking.check.norminal.total.amount", booking.getInstrumentId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                        booking.getNominalTotalAmount().getRealValue());
            }
            if (booking.getMarketValueTotalAmount() != null) {
                assertThat("ag.booking.check.market.value.total.amount", booking.getInstrumentId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                        booking.getMarketValueTotalAmount().getRealValue());
            }
            if (booking.getCollateralValueTotalAmount() != null) {
                assertThat("ag.booking.check.collateral.value.total.amount", booking.getInstrumentId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                        booking.getCollateralValueTotalAmount().getRealValue());
            }
            if (booking.getCollateralValueBaseCcyTotalAmount() != null) {
                assertThat("ag.booking.check.collateral.value.baseccy.total.amount", booking.getInstrumentId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                        booking.getCollateralValueBaseCcyTotalAmount().getRealValue());
            }
        }

        if (booking.getBookingId() == null || booking.getBookingId().getRealValue().isEmpty()) {
            setBookingId(booking);
        }

        logger.info("booking id is " + booking.getBookingId().getRealValue());
        if (booking.getNominalAmount() != null) {
            assertThat("ag.booking.check.norminal.amount", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getNominalAmount().getRealValue());
        }
        if (booking.getSettlementDate() != null) {
            assertThat("ag.booking.check.settlement.date", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getSettlementDate().getRealValue());
        }
        if (booking.getActSettlementdate() != null) {
            assertThat("ag.booking.check.act.settlement.date", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getActSettlementdate().getRealValue());
        }
        if (booking.getCreationDate() != null) {
            assertThat("ag.booking.check.creation.date", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getCreationDate().getRealValue());
        }
        if (booking.getMovement() != null) {
            assertThat("ag.booking.check.movement", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getMovement().getRealValue());
        }
        if (booking.getStatus() != null) {
            assertThat("ag.booking.check.status", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getStatus().getRealValue());
        }
        if (booking.getBookingSource() != null) {
            assertThat("ag.booking.check.booking.source", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getBookingSource().getRealValue());
        }
        if (booking.getPostApprovalStatus() != null) {
            assertThat("ag.booking.check.post.approval.source", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getPostApprovalStatus().getRealValue());
        }
        if (booking.getValuation() != null) {
            assertThat("ag.booking.check.valuation", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getValuation().getRealValue());
        }
        if (booking.getMarketValue() != null) {
            assertThat("ag.booking.check.market.value", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getMarketValue().getRealValue());
        }
        if (booking.getCollateralValue() != null) {
            assertThat("ag.booking.check.collateral.value", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getCollateralValue().getRealValue());
        }
        if (booking.getCollateralValueBaseCCy() != null) {
            assertThat("ag.booking.check.collateral.value.baseccy", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getCollateralValueBaseCCy().getRealValue());
        }
        if (booking.getDetails() != null) {
            assertThat("ag.booking.check.details", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getDetails().getRealValue());
        }
    }

    public void assertAssetHoldingDetailForEquity(AssetBookingSummary booking) throws Exception {
        if (booking.getBookingId() == null || booking.getBookingId().getRealValue().isEmpty()) {
            setBookingId(booking);
        }
        logger.info("booking id is " + booking.getBookingId().getRealValue());

        if (booking.getNominalAmount() != null) {
            assertThat("ag.booking.check.nominal.amount.equity", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getNominalAmount().getRealValue());
        }
        if (booking.getMovement() != null) {
            assertThat("ag.booking.check.movement.equity", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getMovement().getRealValue());
        }
        if (booking.getStatus() != null) {
            assertThat("ag.booking.check.status.equity", booking.getBookingId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    booking.getStatus().getRealValue());
        }
    }




    public void assertAssetHoldingDetail(AssetBookingSummary booking) throws Exception {
        if (element("ag.booking.pageTitle").getInnerText().trim().contains("Cash")) {
            assertAssetHoldingDetailForCash(booking);
        } else if (element("ag.booking.pageTitle").getInnerText().trim().contains("Equity")) {
            assertAssetHoldingDetailForEquity(booking);
        } else {
            assertAssetHodlingDeatailForNonCash(booking);
        }
    }

    public void assertAssetBookingSummary(AssetBookingSummary summary) throws Exception {
        List<IWebDriverWrapper.IWebElementWrapper> elements = element("ag.booking.expand.all").getAllMatchedElements();
        for (IWebDriverWrapper.IWebElementWrapper elment : elements) {
            elment.click();
        }
        StringBuilder xpath = new StringBuilder();
        xpath.append("//tr");
        if (summary.getInstrumentId() != null) {
            xpath.append("[td[count(//td[b[contains(text(),'Instrument ID')][string-length(text())=string-length('Instrument ID')+4]]/preceding-sibling::td)+1][text()='").append(summary.getInstrumentId().getRealValue()).append("']]");
            if (summary.getDirtyPrice() != null)
                xpath.append("[td[count(//td[b[contains(text(),'Dirty Price')][string-length(text())=string-length('Dirty Price')+2]]/preceding-sibling::td)+1][contains(text(),'").append(summary.getDirtyPrice().getRealValue()).append("')]]");
            if (summary.getCleanPrice() != null)
                xpath.append("[td[count(//td[b[contains(text(),'Clean Price')][string-length(text())=string-length('Clean Price')+2]]/preceding-sibling::td)+1][contains(text(),'").append(summary.getCleanPrice().getRealValue()).append("')]]");
            if (summary.getValuationPercentage() != null)
                xpath.append("[td[count(//td[b[contains(text(),'Valuation %')][string-length(text())=string-length('Valuation %')]]/preceding-sibling::td)+1][text()='").append(summary.getValuationPercentage().getRealValue()).append("']]");
            if (summary.getDenomCcy() != null)
                xpath.append("[td[count(//td[b[contains(text(),'Denom Ccy')][string-length(text())=string-length('Denom Ccy')]]/preceding-sibling::td)+1][text()='").append(summary.getDenomCcy().getRealValue()).append("']]");
            if (summary.getMaturityDate() != null)
                xpath.append("[td[count(//td[b[contains(text(),'Maturity Date')][string-length(text())=string-length('Maturity Date')]]/preceding-sibling::td)+1][text()='").append(summary.getMaturityDate().getRealValue()).append("']]");
            if (summary.getNominalAmount() != null)
                xpath.append("[td[count(//td[b[contains(text(),'Nominal')][string-length(text())=string-length('Nominal')]]/preceding-sibling::td)+1][b[text()='").append(summary.getNominalAmount().getRealValue()).append("']]]");
            if (summary.getVmMarketValue() != null)
                xpath.append("[td[count(//td[b[contains(text(),'VM Market Value')][string-length(text())=string-length('VM Market Value')+12]]/preceding-sibling::td)+1][b[text()='").append(summary.getVmMarketValue().getRealValue()).append("']]]");
            if (summary.getImMarketValue() != null)
                xpath.append("[td[count(//td[b[contains(text(),'IM Market Value')][string-length(text())=string-length('IM Market Value')+12]]/preceding-sibling::td)+1][b[text()='").append(summary.getImMarketValue().getRealValue()).append("']]]");
            if (summary.getVmCollateralValue() != null)
                xpath.append("[td[count(//td[b[contains(text(),'VM Collateral')]]/preceding-sibling::td)][b[text()='").append(summary.getVmCollateralValue().getRealValue()).append("']]]");
            if (summary.getVmCollateralValueBaseCCy() != null)
                xpath.append("[td[count(//td[b[contains(text(),'VM Collateral')]]/preceding-sibling::td)]/following-sibling::td[1][b[text()='").append(summary.getVmCollateralValueBaseCCy().getRealValue()).append("']]]");
            if (summary.getImCollateralValue() != null)
                xpath.append("[td[count(//td[b[contains(text(),'IM Collateral')]]/preceding-sibling::td)][b[text()='").append(summary.getImCollateralValue().getRealValue()).append("']]]");
            if (summary.getImCollateralValueBaseCCy() != null)
                xpath.append("[td[count(//td[b[contains(text(),'IM Collateral')]]/preceding-sibling::td)]/following-sibling::td[1][b[text()='").append(summary.getImCollateralValueBaseCCy().getRealValue()).append("']]]");
        } else if (summary.getBookingId() != null) {
            xpath.append("[td[count(//td[b[contains(text(),'Id')][string-length(text())=string-length('Id')+1]]/preceding-sibling::td)+1][text()='").append(summary.getBookingId().getRealValue()).append("']]");
            if (summary.getNominalAmount() != null)
                xpath.append("[td[count(//td[b[contains(text(),'Nominal')][string-length(text())=string-length('Nominal')]]/preceding-sibling::td)+1][text()='").append(summary.getNominalAmount().getRealValue()).append("']]");
            if (summary.getSettlementDate() != null)
                xpath.append("[td[count(//td[b[contains(text(),'Settlement Date')][string-length(text())=string-length('Settlement Date')]]/preceding-sibling::td)+1][text()='").append(summary.getSettlementDate().getRealValue()).append("']]");
            if (summary.getMovement() != null)
                xpath.append("[td[count(//td[b[contains(text(),'Movement')][string-length(text())=string-length('Movement')]]/preceding-sibling::td)+1][text()='").append(summary.getMovement().getRealValue()).append("']]");
            if (summary.getStatus() != null)
                xpath.append("[td[count(//td[b[contains(text(),'Status')][string-length(text())=string-length('Status')]]/preceding-sibling::td)+1][text()='").append(summary.getStatus().getRealValue()).append("']]");
            if (summary.getBookingSource() != null)
                xpath.append("[td[count(//td[b[contains(text(),'Booking Source')][string-length(text())=string-length('Booking Source')]]/preceding-sibling::td)+1][text()='").append(summary.getBookingSource().getRealValue()).append("']]");
            if (summary.getValuation() != null)
                xpath.append("[td[count(//td[b[contains(text(),'Valuation')][string-length(text())=string-length('Valuation')]]/preceding-sibling::td)+1][text()='").append(summary.getValuation().getRealValue()).append("']]");
            if (summary.getVmMarketValue() != null)
                xpath.append("[td[count(//td[b[contains(text(),'VM Market Value')][string-length(text())=string-length('VM Market Value')+12]]/preceding-sibling::td)+1][contains(text(),'").append(summary.getVmMarketValue().getRealValue()).append("')]]");
            if (summary.getImMarketValue() != null)
                xpath.append("[td[count(//td[b[contains(text(),'IM Market Value')][string-length(text())=string-length('IM Market Value')+12]]/preceding-sibling::td)+1][contains(text(),'").append(summary.getImMarketValue().getRealValue()).append("')]]");
            if (summary.getVmCollateralValue() != null)
                xpath.append("[td[count(//td[b[contains(text(),'VM Collateral')]]/preceding-sibling::td)][contains(text(),'").append(summary.getVmCollateralValue().getRealValue()).append("')]]");
            if (summary.getVmCollateralValueBaseCCy() != null)
                xpath.append("[td[count(//td[b[contains(text(),'VM Collateral')]]/preceding-sibling::td)]/following-sibling::td[1][contains(text(),'").append(summary.getVmCollateralValueBaseCCy().getRealValue()).append("')]]");
            if (summary.getImCollateralValue() != null)
                xpath.append("[td[count(//td[b[contains(text(),'IM Collateral')]]/preceding-sibling::td)][contains(text(),'").append(summary.getImCollateralValue().getRealValue()).append("')]]");
            if (summary.getImCollateralValueBaseCCy() != null)
                xpath.append("[td[count(//td[b[contains(text(),'IM Collateral')]]/preceding-sibling::td)]/following-sibling::td[1][contains(text(),'").append(summary.getVmCollateralValueBaseCCy().getRealValue()).append("')]]");
        }
        assertThat("ag.eligible.breached.rule.detail", xpath.toString()).displayed();
    }
}
