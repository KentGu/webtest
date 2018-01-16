package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.CashMovementSummary;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class CashMovementSummaryPage extends PageBase {
    public CashMovementSummaryPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void addInsterestBooking() throws Exception {
        element("cm.add.book").clickByJavaScript();
    }

    /**
     * enter Interest Manager and according to conditional query
     *
     * @throws Exception
     */
    public void enterInterestManager() throws Exception {
        element("cm.interest.manager").click();
    }

    public void enterCashMovementEdit(CashMovementSummary cs) throws  Exception{
        StringBuffer xpath = new StringBuffer("//tr");
        //tr[td='Authorised']//img[@title='edit']
        if (cs.getStatus() != null){
//            xpath.append("[td[count(//td[b[text()='Status']]/preceding-sibling::td)+1][contains(text(),'"+cs.getStatus().getRealValue()+"')]]");
            xpath.append("[td[count(//td[b[text()='Status']]/preceding-sibling::td)+1][contains(text(),'").append(cs.getStatus().getRealValue()).append("')]]");
        }
        if (cs.getBookingType()!=null){
//            xpath.append("[td[count(//td[b[text()='Booking Type']]/preceding-sibling::td)+1][contains(text(),'"+cs.getBookingType().getRealValue()+"')]]");
            xpath.append("[td[count(//td[b[text()='Booking Type']]/preceding-sibling::td)+1][contains(text(),'").append(cs.getBookingType().getRealValue()).append("')]]");
        }
        if (cs.getAmount()!=null){
//            xpath.append("[td[count(//td[b[text()='Amount']]/preceding-sibling::td)+1][contains(text(),'"+cs.getAmount().getRealValue()+"')]]");
            xpath.append("[td[count(//td[b[text()='Amount']]/preceding-sibling::td)+1][contains(text(),'").append(cs.getAmount().getRealValue()).append("')]]");
        }
        if (cs.getMovement()!=null){
//            xpath.append("[td[count(//td[b[text()='Movement']]/preceding-sibling::td)+1][contains(text(),'"+cs.getMovement().getRealValue()+"')]]");
            xpath.append("[td[count(//td[b[text()='Movement']]/preceding-sibling::td)+1][contains(text(),'").append(cs.getMovement().getRealValue()).append("')]]");
        }
        if (cs.getBookingSource()!=null){
//            xpath.append("[td[count(//td[b[text()='Booking Source']]/preceding-sibling::td)+1][contains(text(),'"+cs.getBookingSource().getRealValue()+"')]]");
            xpath.append("[td[count(//td[b[text()='Booking Source']]/preceding-sibling::td)+1][contains(text(),'").append(cs.getBookingSource().getRealValue()).append("')]]");
        }
        element("cm.edit",xpath.toString()).click();
    }

    public void assertCashMovementSummary(CashMovementSummary cashMovementSummary) throws Exception {
        if (cashMovementSummary.getTickedId() == null ||(cashMovementSummary.getTickedId() != null && cashMovementSummary.getTickedId().getRealValue().isEmpty())){
            setCashMovementSummaryTickedId(cashMovementSummary);
        }
        if (cashMovementSummary.getTickedId() != null){
            assertThat("cm.check.ticked.id2",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    cashMovementSummary.getTickedId().getRealValue());
        }
        if (cashMovementSummary.getRefId() != null){
            assertThat("cm.check.ticked.ref.id",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    cashMovementSummary.getRefId().getRealValue());
        }
        if (cashMovementSummary.getStatementVersion() != null){
            assertThat("cm.check.ticked.statement.version",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    cashMovementSummary.getStatementVersion().getRealValue());
        }
        if (cashMovementSummary.getCalculationRule() != null){
            assertThat("cm.check.ticked.calculation.rule",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    cashMovementSummary.getCalculationRule().getRealValue());
        }
        if (cashMovementSummary.getAmount() != null){
            assertThat("cm.check.ticked.amount2",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    cashMovementSummary.getAmount().getRealValue());
        }
        if (cashMovementSummary.getWhtAmount() != null){
            assertThat("cm.check.ticked.wht.amount",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    cashMovementSummary.getWhtAmount().getRealValue());
        }
        if (cashMovementSummary.getMovement() != null){
            assertThat("cm.check.ticked.movement",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    cashMovementSummary.getMovement().getRealValue());
        }
        if (cashMovementSummary.getBookingType() != null){
            assertThat("cm.check.ticked.booking.type2",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    cashMovementSummary.getBookingType().getRealValue());
        }
        if (cashMovementSummary.getStatus() != null){
            assertThat("cm.check.ticked.status",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    cashMovementSummary.getStatus().getRealValue());
        }
        if (cashMovementSummary.getBookingSource() != null){
            assertThat("cm.check.ticked.booking.source",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    cashMovementSummary.getBookingSource().getRealValue());
        }
        if (cashMovementSummary.getPostApprovalStatus() != null){
            assertThat("cm.check.ticked.post.approval.status",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    cashMovementSummary.getPostApprovalStatus().getRealValue());
        }
        if (cashMovementSummary.getCreationDate() != null){
            if ((cashMovementSummary.getCreationDate().getRealValue().toLowerCase().contains("t")) || (cashMovementSummary.getCreationDate().getRealValue().contains("T"))){
                String creationDate = Biz.convertDate(getServerTime(), cashMovementSummary.getCreationDate().getRealValue(), "M/d/yyyy");
                assertThat("cm.check.ticked.creation.date",cashMovementSummary.getTickedId().getRealValue()).innerText().toString().trim().equals(creationDate);
            } else {
                assertThat("cm.check.ticked.creation.date",cashMovementSummary.getTickedId().getRealValue()).innerText().toString().trim().equals(cashMovementSummary.getCreationDate().getRealValue());
            }
        }
        if (cashMovementSummary.getSettlementDate() != null){
            if ((cashMovementSummary.getSettlementDate().getRealValue().toLowerCase().contains("t")) || (cashMovementSummary.getSettlementDate().getRealValue().contains("T"))){
                String settlementDate = Biz.convertDate(getServerTime(), cashMovementSummary.getSettlementDate().getRealValue(), "M/d/yyyy");
                assertThat("cm.check.ticked.settlement.date",cashMovementSummary.getTickedId().getRealValue()).innerText().toString().trim().equals(settlementDate);
            } else {
                assertThat("cm.check.ticked.settlement.date",cashMovementSummary.getTickedId().getRealValue()).innerText().toString().trim().equals(cashMovementSummary.getSettlementDate().getRealValue());
            }
//            assertThat("cm.check.ticked.settlement.date",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
//                    cashMovementSummary.getSettlementDate().getRealValue());
        }
        if (cashMovementSummary.getResetDate() != null){
            assertThat("cm.check.ticked.reset.date",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    cashMovementSummary.getResetDate().getRealValue());
        }

        if (cashMovementSummary.getModel() != null){
            assertThat("cm.check.ticked.booking.model",cashMovementSummary.getTickedId().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    cashMovementSummary.getModel().getRealValue());
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


    private void setCashMovementSummaryTickedId(CashMovementSummary cashMovementSummary) throws Exception {
        StringBuffer attributes = new StringBuffer("//tr");
        Method[] methods = cashMovementSummary.getClass().getDeclaredMethods();
        for (Method method : methods){
            if (method.getName().startsWith("get") &&
                    method.invoke(cashMovementSummary) != null &&
                    method.getReturnType() == StringType.class){
                StringType stringType = (StringType) method.invoke(cashMovementSummary);
                if (method.getName().equals("getBookingType") || method.getName().equals("getModel") || method.getName().equals("getAmount") || method.getName().equals("getMovement") ){
//                    attributes.append("[td[contains(text(),'" + stringType.getRealValue() + "')]]");
                    attributes.append("[td[normalize-space(text())='").append(stringType.getRealValue()).append("']]");
                }else if ((method.getName().equals("getSettlementDate")||(method.getName().equals("getCreationDate")))
                        && (stringType.getRealValue().contains("t") || stringType.getRealValue().contains("T"))){
//                    attributes.append("[td='"+stringType.getRealValue()+"']");
                    String settlementDate = Biz.convertDate(getServerTime(), stringType.getRealValue(), "M/d/yyyy");
                    attributes.append("[td='").append(settlementDate).append("']");
                } else if (method.getName().equals("getAssetType")) {
                    attributes.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                } else {
                    attributes.append("[td='").append(stringType.getRealValue()).append("']");
                }
            }
        }
        String tickedId = element("cm.ticked.id2",attributes.toString()).getInnerText().trim();
            if (cashMovementSummary.getTickedId() == null){
                cashMovementSummary.setTickedId(new StringType(tickedId));
            }else if(cashMovementSummary.getTickedId() != null && cashMovementSummary.getTickedId().getRealValue().isEmpty()) {
                cashMovementSummary.getTickedId().setValue(tickedId);
            }
//            cashMovementSummary.getTickedId().setValue(tickedId);

    }
}
