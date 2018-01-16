package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.CashMovementBookingType;
import com.lombardrisk.pojo.CashMovementDetail;
import com.lombardrisk.pojo.SettlementStatusType;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class CashMovementEditorPage extends PageBase {
    public CashMovementEditorPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * make interest booking on such cash asset if the current page is not cash
     * movement summary page please use the "viewCashMovementsSummary" method
     *
     * @param booking
     */
    public void setCashEditor(CashMovementDetail booking) throws Exception {
        if (booking.getBookingType() != null) {
            if(booking.getBookingType().value().toLowerCase().equals("tsa") || booking.getBookingType().value().toLowerCase().equals("cashflow"))
                element("cm.book.type").selectByVisibleText(CashMovementBookingType.CASHFLOW.value());
            else
                element("cm.book.type").selectByVisibleText(booking.getBookingType().value());
        }
        if (booking.getAmount() != null)
            element("cm.interest.amount").input(booking.getAmount().getRealValue());
        if (booking.getWhtAmount() != null)
            element("cm.wht.amount").input(booking.getWhtAmount().getRealValue());
        if (booking.getMovementType() != null)
            element("cm.movement.type").selectByVisibleText(booking.getMovementType().value());
        if (booking.getSettlementDate() != null) {
            element("cm.settlement.date").input(booking.getSettlementDate().getRealValue());
            element("cm.settlement.date").fireEvent(Biz.FIRE_EVENT_ONCHANGE);
        }
        if (booking.getResetDate() != null)
            element("cm.reset.date").input(booking.getResetDate().getRealValue());
//        if (booking.getSettlementStatus() != null)
//            element("cm.settlement.status").selectByVisibleText(booking.getSettlementStatus().value());
        if (booking.getEvent() != null)
            element("cm.event").selectByVisibleText(booking.getEvent().value());
        if (booking.getComments() != null)
            element("cm.comments").input(booking.getComments().getRealValue());
        if (booking.getAssetNotes1() != null)
            element("cm.asset.notes1").input(booking.getAssetNotes1().getRealValue());
        if (booking.getAssetNotes2() != null)
            element("cm.asset.notes2").input(booking.getAssetNotes2().getRealValue());
        if (booking.getAssetNotes3() != null)
            element("cm.asset.notes3").input(booking.getAssetNotes3().getRealValue());
        if (booking.isExcludeFromStatementCalculation() != null) {
            element("cm.excluedForCalc").check(booking.isExcludeFromStatementCalculation());
        }
        if (booking.getPrincipalBucket() != null)
            element("cm.prin.bucket").selectByVisibleText(booking.getPrincipalBucket().getRealValue());
        if (booking.getCountpartyBucket() != null)
            element("cm.cpty.bucket").selectByVisibleText(booking.getCountpartyBucket().getRealValue());
        if (booking.getAttachment() != null)
            element("cm.attachment").type(booking.getAttachment().getRealValue());

        if (booking.getComponent() != null)
            element("cm.component").selectByVisibleText(booking.getComponent().value());
//        if (booking.isExcludedInStatementCalc())
//            element("cm.excludedInStatementCalc").check(true);
//        element("cm.save").clickByJavaScript();
//        element("cm.save").clickByJavaScript();
        if (booking.getAlertHandling() != null && !booking.getAlertHandling().isEmpty())
            PageHelper.handleAlters(booking.getAlertHandling());
    }

    public void changeSettlementStatus(SettlementStatusType settlementStatusType) throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("cm.settlement.status").selectByVisibleText(settlementStatusType.value());
    }

    public void save(CashMovementDetail cashMovementDetail) throws Exception {
        save();
        if (cashMovementDetail.getAlertHandling() != null && !cashMovementDetail.getAlertHandling().isEmpty())
            PageHelper.handleAlters(cashMovementDetail.getAlertHandling());
    }

    public void save() throws Exception {
        element("cm.save").clickByJavaScript();
    }

    public void assertCashMovementDetail(CashMovementDetail cashMovementDetail) throws Exception {
        if (cashMovementDetail.getBookingType() != null) {
            if (element("cm.book.type").isDisplayed()) {
                assertThat(locator("cm.book.type")).selectedText().isEqualToIgnoringWhitespace(cashMovementDetail.getBookingType().value());
            } else {
                assertThat(locator("cm.book.type.value")).innerText().contains(cashMovementDetail.getBookingType().value());
            }
        }
        if (cashMovementDetail.getAmount() != null) {
            if (element("cm.interest.amount").isDisplayed()) {
                assertThat(locator("cm.interest.amount")).attributeValueOf("value").isEqualToIgnoringWhitespace(cashMovementDetail.getAmount().getRealValue());
            } else {
                assertThat(locator("cm.interest.amount.value")).innerText().isEqualToIgnoringWhitespace(cashMovementDetail.getAmount().getRealValue());
            }
        }
        if (cashMovementDetail.getWhtAmount() != null) {
            if (element("cm.wht.amount").isDisplayed()) {
                assertThat(locator("cm.wht.amount")).attributeValueOf("value").isEqualToIgnoringWhitespace(cashMovementDetail.getWhtAmount().getRealValue());
            } else {
                assertThat(locator("cm.wht.amount.value")).innerText().isEqualToIgnoringWhitespace(cashMovementDetail.getWhtAmount().getRealValue());
            }
        }
        if (cashMovementDetail.getMovementType() != null) {
            if (element("cm.movement.type").isDisplayed()) {
                assertThat(locator("cm.movement.type")).selectedText().isEqualToIgnoringWhitespace(cashMovementDetail.getMovementType().value());
            } else {
                assertThat(locator("cm.movement.type.value")).innerText().isEqualToIgnoringWhitespace(cashMovementDetail.getMovementType().value());
            }
        }
//        if (cashMovementDetail.getSettlementDate() != null) {
//            if (element("cm.settlement.date").isDisplayed()) {
//                assertThat(locator("cm.settlement.date")).attributeValueOf("value").isEqualToIgnoringWhitespace(cashMovementDetail.getSettlementDate().getRealValue());
//            } else {
//                assertThat(locator("cm.settlement.date.value")).innerText().isEqualToIgnoringWhitespace(cashMovementDetail.getSettlementDate().getRealValue());
//            }
//        }
        if (cashMovementDetail.getSettlementDate() != null) {
            if ((cashMovementDetail.getSettlementDate().getRealValue().contains("t")) || (cashMovementDetail.getSettlementDate().getRealValue().contains("T"))) {
                String settlementDate = Biz.convertDate(getServerTime(), cashMovementDetail.getSettlementDate().getRealValue(), "M/d/yyyy");
                if (element("cm.settlement.date").isDisplayed()) {
                    assertThat(locator("cm.settlement.date")).attributeValueOf("value").isEqualTo(settlementDate);
                } else {
                    assertThat(locator("cm.settlement.date.value")).innerText().isEqualTo(settlementDate);
                }
            } else {
                if (element("cm.settlement.date").isDisplayed()) {
                    assertThat(locator("cm.settlement.date")).attributeValueOf("value").isEqualTo(cashMovementDetail.getSettlementDate().getRealValue());
                } else {
                    assertThat(locator("cm.settlement.date.value")).innerText().isEqualToIgnoringWhitespace(cashMovementDetail.getSettlementDate().getRealValue());
                }
            }
        }

        if (cashMovementDetail.getOriginalSettlementDate() != null)
            assertThat(locator("cm.original.settlement.date")).innerText().isEqualToIgnoringWhitespace(cashMovementDetail.getOriginalSettlementDate().getRealValue());
        if (cashMovementDetail.getResetDate() != null) {
            if (element("cm.reset.date").isDisplayed()) {
                assertThat(locator("cm.reset.date")).attributeValueOf("value").isEqualToIgnoringWhitespace(cashMovementDetail.getResetDate().getRealValue());
            } else {
                assertThat(locator("cm.reset.date.value")).innerText().isEqualToIgnoringWhitespace(cashMovementDetail.getResetDate().getRealValue());
            }
        }
        if (cashMovementDetail.getSettlementStatus() != null && cashMovementDetail.getSettlementStatus().size() > 0)
            assertThat(locator("cm.settlement.status")).selectedText().isEqualToIgnoringWhitespace(cashMovementDetail.getSettlementStatus().get(0).value());
        if (cashMovementDetail.getBookingSource() != null)
            assertThat(locator("cm.book.source")).innerText().isEqualToIgnoringWhitespace(cashMovementDetail.getBookingSource().getRealValue());
        if (cashMovementDetail.getEvent() != null) {
            if (element("cm.event").isDisplayed()) {
                assertThat(locator("cm.event")).selectedText().isEqualToIgnoringWhitespace(cashMovementDetail.getEvent().value());
            } else {
                assertThat(locator("cm.event.value")).allInnerTexts().contains(cashMovementDetail.getEvent().value());
            }
        }
        if (cashMovementDetail.getComponent() != null)
            assertThat(locator("cm.component2")).innerText().isEqualToIgnoringCase(cashMovementDetail.getComponent().value());
        if (cashMovementDetail.isExcludeFromStatementCalculation() != null && cashMovementDetail.isExcludeFromStatementCalculation()) {
            assertThat("cm.excluedForCalc").attributeValueOf("checked").isEqualToIgnoringCase("true");
            assertThat("cm.excluedForCalc").attributeValueOf("disabled").isEqualToIgnoringCase("true");
        }

        if (cashMovementDetail.getEventId() != null ) {
            assertThat(locator("cm.event.id")).innerText().isEqualToIgnoringWhitespace(cashMovementDetail.getEventId().getRealValue());
        }
        if (cashMovementDetail.getComments() != null)
            assertThat(locator("cm.comments")).attributeValueOf("value").isEqualToIgnoringWhitespace(cashMovementDetail.getComments().getRealValue());
        if (cashMovementDetail.getPlaceOfSettlement() != null)
            assertThat(locator("cm.place.of.settlement")).selectedText().isEqualToIgnoringWhitespace(cashMovementDetail.getPlaceOfSettlement().getRealValue());
        if (cashMovementDetail.getAssetNotes1() != null)
            assertThat(locator("cm.asset.notes1")).attributeValueOf("value").isEqualToIgnoringWhitespace(cashMovementDetail.getAssetNotes1().getRealValue());
        if (cashMovementDetail.getAssetNotes2() != null)
            assertThat(locator("cm.asset.notes2")).attributeValueOf("value").isEqualToIgnoringWhitespace(cashMovementDetail.getAssetNotes2().getRealValue());
        if (cashMovementDetail.getAssetNotes3() != null)
            assertThat(locator("cm.asset.notes3")).attributeValueOf("value").isEqualToIgnoringWhitespace(cashMovementDetail.getAssetNotes3().getRealValue());
        if (cashMovementDetail.getPrincipalBucket() != null)
            assertThat(locator("cm.prin.bucket")).selectedText().isEqualToIgnoringWhitespace(cashMovementDetail.getPrincipalBucket().getRealValue());
        if (cashMovementDetail.getCountpartyBucket() != null)
            assertThat(locator("cm.cpty.bucket")).selectedText().isEqualToIgnoringWhitespace(cashMovementDetail.getCountpartyBucket().getRealValue());
//        if(cashMovementDetail.getAccountNumber() != null)
//            assertThat(locator("")).innerText().isEqualToIgnoringWhitespace(cashMovementDetail.getAccountNumber().getRealValue());
//        if(cashMovementDetail.getBeneficiaryBank() != null)
//            assertThat(locator("")).innerText().isEqualToIgnoringWhitespace(cashMovementDetail.getBeneficiaryBank().getRealValue());
        if (cashMovementDetail.getAttachment() != null)
            assertThat(locator("cm.attachment")).attributeValueOf("value").isEqualToIgnoringWhitespace(cashMovementDetail.getAttachment().getRealValue());
        if (cashMovementDetail.getGroupSettlementId() != null)
            assertThat(locator("cm.groupSettlementId")).innerText().isEqualToIgnoringWhitespace(cashMovementDetail.getGroupSettlementId().getRealValue());


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
}