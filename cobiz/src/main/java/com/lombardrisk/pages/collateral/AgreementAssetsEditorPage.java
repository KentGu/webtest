package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
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
public class AgreementAssetsEditorPage extends PageBase {
    public AgreementAssetsEditorPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * make booking from asset summary page without selecting asset type
     *
     * @param booking
     */
    public void makeBooking(BookingDetail booking) throws Exception {
        if (booking.getBookingInformation() != null) {
            inputBookingInformation(booking.getBookingInformation());
            List<BookingInformationDetail> bookingInformationDetails = booking.getBookingInformation().getBookingInformationDetail();
            if (bookingInformationDetails != null && bookingInformationDetails.size() > 0) {
                for (BookingInformationDetail bookingInformationDetail : bookingInformationDetails) {
                    inputBookingInformationDetail(bookingInformationDetail);
                }
            }
        }
        inputPaymentInstructions(booking);
        inputCouponCalculation(booking);
        inputExternalBookingInformation(booking);
        switch (booking.getSave()) {
            case SAVE:
                save();
                break;
            case SAVE_AND_COMPLETE:
                saveAndComplete();
                break;
            case SAVE_AND_NEXT:
                saveAndNext();
                break;
            case CANCEL:
                cancel();
                break;
            case NOT_SAVE_OR_CANCEL:
                break;
            default:
                throw new IllegalArgumentException("illegal argument " + booking.getSave().value() + " booking editor page");
        }
        handleAlters(booking.getAlertHandling());

        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
        PageHelper.d33640Workaround();
    }


    public void changeBooking(BookingDetail booking) throws Exception {
        changeBookingInformationDetail(booking);
//        if(booking.getBookingInformation() != null) {
//            List<BookingInformationDetail> bookingInformationDetails = booking.getBookingInformation().getBookingInformationDetail();
//            if (bookingInformationDetails != null && bookingInformationDetails.size() > 0) {
//                for (BookingInformationDetail bookingInformationDetail : bookingInformationDetails) {
//                    inputBookingInformationDetail(bookingInformationDetail);
//                }
//            }
//            inputBookingInformation(booking.getBookingInformation());
//        }
        inputBookingInformation(booking.getBookingInformation());
        inputPaymentInstructions(booking);
        inputCouponCalculation(booking);
        inputExternalBookingInformation(booking);
        switch (booking.getSave()) {
            case SAVE:
                save();
                break;
            case SAVE_AND_COMPLETE:
                saveAndComplete();
                break;
            case SAVE_AND_NEXT:
                saveAndNext();
                break;
            case CANCEL:
                cancel();
                break;
            case NOT_SAVE_OR_CANCEL:
                break;
            default:
                throw new IllegalArgumentException("illegal argument " + booking.getSave().value() + " booking editor page");
        }
        handleAlters(booking.getAlertHandling());

        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
    }

    public void changeBookingInformationDetail(BookingDetail bookingDetail) throws Exception {
        BookingInformationDetail booking = new BookingInformationDetail();
        if (bookingDetail.getBookingInformation() != null) {
            if (bookingDetail.getBookingInformation().getBookingInformationDetail() != null && bookingDetail.getBookingInformation().getBookingInformationDetail().size() > 0) {
                booking = bookingDetail.getBookingInformation().getBookingInformationDetail().get(0);
            }
        }
        if (booking.getAgreementName() == null && (booking.getAgreementDiscription() == null && booking.getPrincipalShortName() == null &&
                booking.getCounterpartyShortName() == null && booking.getExternalId() == null && booking.getAgreementId() == null)) {
            if (booking.getNominalAmount() != null) {
                if (bookingDetail.getBookingInformation().getSettlementStatusInfo() != null && bookingDetail.getBookingInformation().getSettlementStatusInfo().size() > 0) {
                    for (int i = 0; i < bookingDetail.getBookingInformation().getSettlementStatusInfo().size(); i++) {
                        if (bookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementStatus() == (SettlementStatusType.QUERY)) {
                            element("ag.bookingInfo.settlementStatus").selectByVisibleText(
                                    bookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementStatus().value());
                            if (bookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementComment() != null) {
                                element("ag.bookingInfo.settlementComment").input(
                                        bookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementComment().getRealValue());
                            }
                        }

                    }
                }
                element("ag.bookingInfo.norminalAmount").setValue("").input(booking.getNominalAmount().getRealValue());
            }
            if (booking.getRequiredAmount() != null) {
                if (booking.getRequiredAmount().isApply() != null && booking.getRequiredAmount().isApply()) {
                    element("ag.bookingInfo.requiredAmount").setValue("").input(booking.getRequiredAmount().getRealValue());
                    element("ag.apply").click();
                } else {
                    element("ag.bookingInfo.requiredAmount").setValue("").input(booking.getRequiredAmount().getRealValue());

                }
            }

            if (booking.getBondRequiredAmount() != null) {
                if (booking.getBondRequiredAmount().isApply() != null && booking.getBondRequiredAmount().isApply()) {
                    element("ag.bookingInfo.requiredAmountBond").setValue("").input(booking.getBondRequiredAmount().getRealValue());
                    element("ag.apply").click();

                } else {
                    element("ag.bookingInfo.requiredAmount").setValue("").input(booking.getBondRequiredAmount().getRealValue());

                }
            }
            if (booking.getMovement() != null) {
                element("ag.bookingInfo.movement").selectByVisibleText(booking.getMovement().value());
            }
            if (booking.getVmAmount() != null) {
//            element("ag.bookingInfo.vmAmount").clear();
//            element("ag.bookingInfo.vmAmount").setAttribute("value", booking.getVmAmount().getRealValue());
                if (bookingDetail.getBookingInformation().getSettlementStatusInfo() != null && bookingDetail.getBookingInformation().getSettlementStatusInfo().size() > 0) {
                    for (int i = 0; i < bookingDetail.getBookingInformation().getSettlementStatusInfo().size(); i++) {
                        if (bookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementStatus() == (SettlementStatusType.QUERY)) {
                            element("ag.bookingInfo.settlementStatus").selectByVisibleText(
                                    bookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementStatus().value());
                            if (bookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementComment() != null) {
                                element("ag.bookingInfo.settlementComment").input(
                                        bookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementComment().getRealValue());
                            }
                        }

                    }
                }
                element("ag.bookingInfo.vmAmount").setValue("");
                element("ag.bookingInfo.vmAmount").type(booking.getVmAmount().getRealValue())
                        .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getCashAmount() !=null) {
                element("ag.bookingInfo.vmAmount").setValue("");
                element("ag.bookingInfo.vmAmount").type(booking.getCashAmount().getRealValue())
                        .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getImAmount() != null) {
//            element("ag.bookingInfo.imAmount").clear();
//            element("ag.bookingInfo.imAmount").setAttribute("value", booking.getImAmount().getRealValue());
                if (bookingDetail.getBookingInformation().getSettlementStatusInfo() != null && bookingDetail.getBookingInformation().getSettlementStatusInfo().size() > 0) {
                    for (int i = 0; i < bookingDetail.getBookingInformation().getSettlementStatusInfo().size(); i++) {
                        if (bookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementStatus() == (SettlementStatusType.QUERY)) {
                            element("ag.bookingInfo.settlementStatus").selectByVisibleText(
                                    bookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementStatus().value());
                            if (bookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementComment() != null) {
                                element("ag.bookingInfo.settlementComment").input(
                                        bookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementComment().getRealValue());
                            }
                        }

                    }
                }
                element("ag.bookingInfo.imAmount").setValue("");
                element("ag.bookingInfo.imAmount").input(booking.getImAmount().getRealValue())
                        .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getVmRequiredAmount() != null) {
                element("ag.bookingInfo.vmRequiredAmount").input(booking.getVmRequiredAmount().getRealValue());
            }
            if (booking.getImRequiredAmount() != null) {
                element("ag.bookingInfo.imRequiredAmount").input(booking.getImRequiredAmount().getRealValue());
            }
        } else {
            String agreement = getActuralAgreementName(booking);
            if (booking.getNominalAmount() != null) {
                element("ag.bookingInfo.subAmount", agreement).setValue("");
                element("ag.bookingInfo.subAmount", agreement).input(booking.getNominalAmount().getRealValue())
                        .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getVmAmount() != null) {
                element("ag.bookingInfo.subVmAmount", agreement).setValue("");
                element("ag.bookingInfo.subVmAmount", agreement).input(booking.getVmAmount().getRealValue())
                        .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getVmRequiredAmount() != null) {
                element("ag.bookingInfo.subVmRequiredAmount", agreement).input(booking.getVmRequiredAmount().getRealValue());
            }
            if (booking.getImAmount() != null) {
                element("ag.bookingInfo.subImAmount", agreement).setValue("");
                element("ag.bookingInfo.subImAmount", agreement).input(booking.getImAmount().getRealValue())
                        .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getImRequiredAmount() != null) {
                element("ag.bookingInfo.subImRequiredAmount", agreement).input(booking.getImRequiredAmount().getRealValue());
            }
            if (booking.getMovement() != null) {
                element("ag.bookingInfo.subMovement", agreement).selectByVisibleText(booking.getMovement().value());
            }
        }
    }


    public void inputBookingInformationDetail(BookingInformationDetail booking) throws Exception {
        if (booking.getAgreementName() == null && (booking.getAgreementDiscription() == null && booking.getPrincipalShortName() == null &&
                booking.getCounterpartyShortName() == null && booking.getExternalId() == null && booking.getAgreementId() == null)) {
            if (booking.getNominalAmount() != null) {
                element("ag.bookingInfo.norminalAmount").setValue("").input(booking.getNominalAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getRequiredAmount() != null) {
                element("ag.bookingInfo.requiredAmount").setValue("").input(booking.getRequiredAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getMovement() != null) {
                element("ag.bookingInfo.movement").selectByVisibleText(booking.getMovement().value());
            }
            if (booking.getVmAmount() != null) {
//            element("ag.bookingInfo.vmAmount").clear();
//            element("ag.bookingInfo.vmAmount").setAttribute("value", booking.getVmAmount().getRealValue());
                if (booking.getVmAmount().isApply() != null && booking.getVmAmount().isApply()) {
                    element("ag.bookingInfo.vmAmount.apply").click();
                }else {
                    element("ag.bookingInfo.vmAmount").setValue("");
                    element("ag.bookingInfo.vmAmount").type(booking.getVmAmount().getRealValue())
                            .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                }

            }
            if (booking.getCashAmount() != null) {
                if (booking.getCashAmount().isApply() != null && booking.getCashAmount().isApply()) {
                    element("ag.bookingInfo.vmAmount.apply").click();
                }else {
                    element("ag.bookingInfo.vmAmount").setValue("");
                    element("ag.bookingInfo.vmAmount").type(booking.getCashAmount().getRealValue())
                            .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                }

            }
            if (booking.getDfAmount() != null) {
                if (booking.getDfAmount().isApply() != null && booking.getDfAmount().isApply()) {
                    element("ag.bookingInfo.dfAmount.apply").click();
                }else {
                    element("ag.bookingInfo.dfAmount").setValue("");
                    element("ag.bookingInfo.dfAmount").type(booking.getDfAmount().getRealValue())
                            .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                }

            }
            if (booking.getImAmount() != null) {
//            element("ag.bookingInfo.imAmount").clear();
//            element("ag.bookingInfo.imAmount").setAttribute("value", booking.getImAmount().getRealValue());
                if (booking.getImAmount().isApply() != null && booking.getImAmount().isApply()) {
                    element("ag.bookingInfo.imAmount.apply").click();
                }else {
                    element("ag.bookingInfo.imAmount").setValue("");
                    element("ag.bookingInfo.imAmount").input(booking.getImAmount().getRealValue())
                            .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                }

            }
            if (booking.getVmRequiredAmount() != null) {
                element("ag.bookingInfo.vmRequiredAmount").input(booking.getVmRequiredAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getImRequiredAmount() != null) {
                element("ag.bookingInfo.imRequiredAmount").input(booking.getImRequiredAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getDfRequiredAmount() != null) {
                element("ag.bookingInfo.dfRequiredAmount").input(booking.getDfRequiredAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
        } else {
            String agreement = getActuralAgreementName(booking);
            if (booking.getNominalAmount() != null) {
                element("ag.bookingInfo.subAmount", agreement).setValue("");
                element("ag.bookingInfo.subAmount", agreement).input(booking.getNominalAmount().getRealValue())
                        .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getVmAmount() != null) {
                element("ag.bookingInfo.subVmAmount", agreement).setValue("");
                element("ag.bookingInfo.subVmAmount", agreement).input(booking.getVmAmount().getRealValue())
                        .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getVmRequiredAmount() != null) {
                element("ag.bookingInfo.subVmRequiredAmount", agreement).input(booking.getVmRequiredAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getImAmount() != null) {
                element("ag.bookingInfo.subImAmount", agreement).setValue("");
                element("ag.bookingInfo.subImAmount", agreement).input(booking.getImAmount().getRealValue())
                        .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getImRequiredAmount() != null) {
                element("ag.bookingInfo.subImRequiredAmount", agreement).input(booking.getImRequiredAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            }
            if (booking.getMovement() != null) {
                element("ag.bookingInfo.subMovement", agreement).selectByVisibleText(booking.getMovement().value());
            }
        }
    }

    public void inputBookingInformation(BookingInformation booking) throws Exception {
        if (booking.getBookingType() != null) {
            element("ag.bookingInfo.bookingType").selectByVisibleText(booking.getBookingType().value());
        }

        if (booking.getBookingInformationDetail() != null && booking.getBookingInformationDetail().isEmpty())
            for (BookingInformationDetail BookingInformationDetail : booking.getBookingInformationDetail()) {
                if (BookingInformationDetail.getVmAmount() != null) {
                    element("ag.bookingInfo.vmAmount").setValue("");
                    element("ag.bookingInfo.vmAmount").type(booking.getBookingInformationDetail().get(0).getVmAmount().getRealValue())
                            .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                }
                if (BookingInformationDetail.getImAmount() != null) {
                    element("ag.bookingInfo.imAmount").setValue("");
                    element("ag.bookingInfo.imAmount").input(booking.getBookingInformationDetail().get(0).getImAmount().getRealValue())
                            .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                }
            }

        if (booking.getSettlementStatusInfo() != null && !booking.getSettlementStatusInfo().isEmpty()) {
            for (SettlementStatusInfoDetail settlementStatusInfoDetail : booking.getSettlementStatusInfo()) {
                if (settlementStatusInfoDetail.getSettlementStatus() != null) {
                    element("ag.bookingInfo.settlementStatus").selectByVisibleText(booking.getSettlementStatusInfo().get(0).getSettlementStatus().value());
                }

                if (booking.getSettlementStatusInfo().get(0).getSettlementComment() != null) {
                    element("ag.bookingInfo.settlementComment").input(booking.getSettlementStatusInfo().get(0).getSettlementComment().getRealValue());
                    //element("ag.bookingInfo.settlementComment").isEnabled()
                }
            }
        }
        if (booking.getSettlementDate() != null) {
            element("ag.bookingInfo.settlementDate").setValue("");
            element("ag.bookingInfo.settlementDate").type(booking.getSettlementDate().getRealValue());
            alert().disable();
            element("ag.bookingInfo.settlementDate").fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            alert().enable();
//            if (booking.getSettlementDateAlertHandling() != null && !booking.getSettlementDateAlertHandling().isEmpty()) {
//                handleAlters(booking.getSettlementDateAlertHandling());
//            }
        }
//        if (booking.getEvent() != null) {
//            element("ag.bookingInfo.event").selectByVisibleText(booking.getEvent().value());
//        }
//        if (booking.getEvent() != null){
//        	if (booking.getEvent().getEventId() == null){
//        		if (booking.getEvent().getEventType() != null){
//        			element("ag.bookingInfo.event").selectByVisibleText(booking.getEvent().getEventType().value());
//        		}
//        	}else{
//        		element("ag.bookingInfo.eventId").selectByValue(booking.getEvent().getEventId().getRealValue());
//        	}
//        }
        if (booking.getEvent() != null) {
            element("ag.bookingInfo.event").selectByVisibleText(booking.getEvent().value());
        }
        if (booking.getEventId() != null) {
            element("ag.bookingInfo.event").selectByValue(booking.getEventId().getRealValue());
        }

        if (booking.isRehypothecated() != null) {
            if (booking.isRehypothecated()) {
                element("ag.bookingInfo.rehypothecated").check(booking.isRehypothecated());
            }
        }
        if (booking.getAssetOwner() != null) {
            if (booking.getAssetOwner().getType() != null) {
                element("ag.bookingInfo.assetOwnerSearchType").selectByVisibleText(booking.getAssetOwner().getType().value());
            }
            if (booking.getAssetOwner().getFilter() != null) {
                element("ag.bookingInfo.assetOwnerSearchFilter").input(booking.getAssetOwner().getFilter().getRealValue());
            }
            if (booking.getAssetOwner().getLinkText() != null) {
                element("ag.bookingInfo.assetOwnerSearchLinktext", booking.getAssetOwner().getLinkText().getRealValue()).click();
            }
        }
        if (booking.getComment() != null) {
            element("ag.bookingInfo.comment").input(booking.getComment().getRealValue());
        }

        if (booking.getAssetNotes2() != null) {
            element("ag.bookingInfo.assetNotes2").input(booking.getAssetNotes2().getRealValue());
        }
        if (booking.getAssetNotes3() != null) {
            element("ag.bookingInfo.assetNotes3").input(booking.getAssetNotes3().getRealValue());
        }
        if (booking.getMovementDescription() != null) {
            element("ag.bookingInfo.movementDescription").selectByVisibleText(booking.getMovementDescription().getRealValue());
        }
        if (booking.isFlushed() != null) {
            element("ag.flushed").check(booking.isFlushed());
        }
        if (booking.getValuationPercentage() != null) {
            element("ag.bookingInfo.valuationPercentage").input(booking.getValuationPercentage().getRealValue());
        }
    }

    public void inputPaymentInstructions(BookingDetail booking) throws Exception {
        if (booking.getPrincipalPaymentInstructions() != null) {
            if (booking.getPrincipalPaymentInstructions().getCapitalPayInstr() != null) {
                element("ag.paymentInsr.principalCapitalPayInstr").selectByVisibleText(booking.getPrincipalPaymentInstructions().getCapitalPayInstr().getRealValue());
            }
        }
        if (booking.getCounterpartyPaymentInstructions() != null) {
            if (booking.getCounterpartyPaymentInstructions().getCapitalPayInstr() != null) {
                element("ag.paymentInsr.cptyCapitalPayInstr").selectByVisibleText(booking.getCounterpartyPaymentInstructions().getCapitalPayInstr().getRealValue());
            }
        }
        if (booking.getPlaceOfSettlement() != null) {
            if (booking.getPlaceOfSettlement().getCapitalPayInstr() != null) {
                element("ag.paymentInsr.placeOfSettlement").selectByVisibleText(booking.getPlaceOfSettlement().getCapitalPayInstr().getRealValue());
            }
        }
    }

    public void inputCouponCalculation(BookingDetail booking) throws Exception {
        if (booking.getCouponCalculation() != null) {
            if (booking.getCouponCalculation().getDaysAccrued() != null) {
                element("ag.couponCal.daysAccrued").input(booking.getCouponCalculation().getDaysAccrued().getRealValue());
            }
            if (booking.getCouponCalculation().getCurrentAccrual() != null) {
                element("ag.couponCal.currentAccrual").input(booking.getCouponCalculation().getCurrentAccrual().getRealValue());
            }
            if (booking.getCouponCalculation().getDaysinPeriod() != null) {
                element("ag.couponCal.daysInPeriod").input(booking.getCouponCalculation().getDaysinPeriod().getRealValue());
            }
            if (booking.getCouponCalculation().getNextCouponPayment() != null) {
                element("ag.couponCal.nextCouponPayment").input(booking.getCouponCalculation().getNextCouponPayment().getRealValue());
            }
        }
    }

    public void inputExternalBookingInformation(BookingDetail booking) throws Exception {
        if (booking.getExternalBookingInformation() != null) {
            if (booking.getExternalBookingInformation().getAttachment() != null) {
                element("ag.externalBookingInfo.attachment").input(booking.getExternalBookingInformation().getAttachment().getRealValue());
            }
        }
    }

    /**
     * click continue button to complete the booking
     */
    public void continueBooking() throws Exception {
        element("ag.continue").click();
    }

    /**
     * view statement
     */
    public void viewStatement() throws Exception {
        element("ag.view.statement").click();
    }

    /**
     * assert concentration limits breached on such collateral booking
     *
     * @param list
     */
    public void assertBookingConcentrationLimitsBreached(List<ConcentrationLimitBreached> list) throws Exception {
        for (ConcentrationLimitBreached clb : list) {
            StringBuffer xpath = new StringBuffer();
            xpath.append("//tr");
            Method[] methods = clb.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("get")// acquire all get methods
                        // exclude getName method
                        && !method.getName().equals("getName")
                        // exclude method that don't return String type
                        && method.getReturnType().equals(String.class)) {
                    if (method.invoke(clb) != null) {
                        xpath.append("[td/b[normalize-space(text())='");
                        xpath.append(method.invoke(clb));
                        xpath.append("']]");
                    }
                }
            }
            assertThat("ag.breached.rule", xpath.toString()).displayed().isEqualTo(true);
        }
    }

    /**
     * click cancel & next booking button on group booking page
     */
    public void cancelNextBooking() throws Exception {
        if (element("em.override").isDisplayed()) {
            // tick on checkbox
            element("em.override").check(true);
            element("ag.cancel.and.next").click();
        } else {
            element("ag.cancel.and.next").click();
        }
    }

    /**
     * click cancel button on booking editor page
     */
    public void cancelBooking() throws Exception {
        element("ag.cancel").click();
    }

    /**
     * assert Agreement Assets - Security Editor value on booking page
     */
    public void assertBooking(BookingDetail booking) throws Exception {
        if (booking.getBookingInformation() != null) {
            List<BookingInformationDetail> bookingInformationDetails = booking.getBookingInformation().getBookingInformationDetail();
            if (bookingInformationDetails != null && !bookingInformationDetails.isEmpty()) {
                for (BookingInformationDetail bookingInformationDetail : bookingInformationDetails) {
                    assertBookingInformationDetail(bookingInformationDetail);
                }
            }
            assertBookingInformation(booking.getBookingInformation());
            assertStatus(booking.getBookingInformation());
        }
        if (booking.getCashInformation() != null){
            assertCashInformation(booking);
        }
        if (booking.getInstrumentDetails() != null)
            assertInstrumentDetails(booking.getInstrumentDetails());
        if (booking.getAgreementOverview() != null)
            assertAgreementOverview(booking.getAgreementOverview());
        assertPaymentInstructions(booking);
        assertCouponCalculation(booking);
        assertExternalBookingInformation(booking);

        if (booking.getValidationError() != null && booking.getValidationError().size()>0) {
            List<ValidationErrorType> validationErrors = booking.getValidationError();
            for (ValidationErrorType validationError : validationErrors) {
                if (validationError.getContext() != null) {
                    assertThat("ag.bookingInfo.validationError").innerText().contains(validationError.getContext().getRealValue());
                }
            }
        }


    }

    private void assertCashInformation(BookingDetail booking) throws Exception  {
        if (booking.getCashInformation().getGroupSettlementId() != null) {
            assertThat("ag.cash.info.groupSettlementId").innerText().isEqualToIgnoringWhitespace(booking.getCashInformation().getGroupSettlementId().getRealValue());
        }

    }

    private void assertInstrumentDetails(Instrument instrument) throws Exception {
        if (instrument.getAssetClass() != null) {
            assertThat(locator("ag.ins.detail.assetclass")).innerText().isEqualToIgnoringWhitespace(instrument.getAssetClass().getRealValue());
        }
        if (instrument.getAssetType() != null) {
            assertThat(locator("ag.ins.detail.assettype")).innerText().isEqualToIgnoringWhitespace(instrument.getAssetType().getRealValue());
        }
        if (instrument.getAssetClassification() != null && !instrument.getAssetClassification().isEmpty()) {
            for (AssetClassification assetClassification : instrument.getAssetClassification()) {
                assertThat(locator("ag.ins.detail.assetclassification")).innerText().contains(assetClassification.getAssetClassificationName().getRealValue());
            }
        }
        if (instrument.getInstrumentId() != null && !instrument.getInstrumentId().isEmpty()) {
            InstrumentId instrumentId = instrument.getInstrumentId().get(instrument.getInstrumentId().size() - 1);
            if (instrumentId.getValue() != null)
                assertThat(locator("ag.ins.detail.insid")).innerText().isEqualToIgnoringWhitespace(instrumentId.getValue().getRealValue());
            if (instrumentId.getType() != null)
                assertThat(locator("ag.ins.detail.insid.type")).innerText().isEqualToIgnoringWhitespace(instrumentId.getType().value());
        }
        if (instrument.getMaturityDate() != null) {
            assertThat(locator("ag.ins.detail.maturity.date")).innerText().isEqualToIgnoringWhitespace(instrument.getMaturityDate().getRealValue());
        }
        if (instrument.getCurrencyOfDenomination() != null) {
            assertThat(locator("ag.ins.detail.ccy.denomination")).innerText().isEqualToIgnoringWhitespace(instrument.getCurrencyOfDenomination().getRealValue());
        }
        if (instrument.getPriceSource() != null && !instrument.getPriceSource().isEmpty()) {
            PriceSource priceSource = instrument.getPriceSource().get(instrument.getPriceSource().size() - 1);
            if (priceSource.getCleanPrice() != null)
                assertThat(locator("ag.ins.detail.price")).innerText().isEqualToIgnoringWhitespace(priceSource.getCleanPrice().getRealValue());
            if (priceSource.getPriceDate() != null)
                assertThat(locator("ag.ins.detail.price.date")).innerText().isEqualToIgnoringWhitespace(priceSource.getPriceDate().getRealValue());
        }
        if (instrument.getPriceFactor() != null) {
            assertThat(locator("ag.ins.detail.price.factor")).innerText().isEqualToIgnoringWhitespace(instrument.getPriceFactor().getRealValue());
        }
        if (instrument.getIssuer() != null) {
            OrganisationSearch organisationSearch = instrument.getIssuer();
            if (organisationSearch.getLinkText() != null)
                assertThat(locator("ag.ins.detail.issuer")).innerText().isEqualToIgnoringWhitespace(organisationSearch.getLinkText().getRealValue());
        }
        if (instrument.getDescription() != null) {
            assertThat(locator("ag.ins.detail.desc")).innerText().isEqualToIgnoringWhitespace(instrument.getDescription().getRealValue());
        }
        if (instrument.getRecordDate() != null) {
            assertThat(locator("ag.ins.detail.record.date")).innerText().isEqualToIgnoringWhitespace(instrument.getRecordDate().getRealValue());
        }
        if (instrument.getExDivDate() != null) {
            assertThat(locator("ag.ins.detail.exdiv.date")).innerText().isEqualToIgnoringWhitespace(instrument.getExDivDate().getRealValue());
        }
        if (instrument.getIssuerRating() != null && !instrument.getIssuerRating().isEmpty()) {
            for (OrganisationRating organisationRating : instrument.getIssuerRating()) {
                if (organisationRating.getAgency() != null && organisationRating.getRating() != null)
                    //assertThat(locator("ag.ins.detail.issuer.rating", organisationRating.getAgency().getRealValue())).innerText().isEqualToIgnoringWhitespace(organisationRating.getRating().getRealValue());
                    assertThat(locator("ag.ins.detail.issuer.rating")).innerText().contains(organisationRating.getAgency().getRealValue() + "  " + organisationRating.getRating().getRealValue());
            }
        }
        if (instrument.getIssueRating() != null && !instrument.getIssueRating().isEmpty()) {
            for (OrganisationRating organisationRating : instrument.getIssueRating()) {
                if (organisationRating.getAgency() != null && organisationRating.getRating() != null)
                    //assertThat(locator("ag.ins.detail.issue.rating", organisationRating.getAgency().getRealValue())).innerText().isEqualToIgnoringWhitespace(organisationRating.getRating().getRealValue());
                    assertThat(locator("ag.ins.detail.issue.rating")).innerText().contains(organisationRating.getAgency().getRealValue() + "  " + organisationRating.getRating().getRealValue());
            }
        }
        if (instrument.getCqs() != null && !instrument.getCqs().isEmpty()) {
            for (OrganisationRating organisationRating : instrument.getCqs()) {
                if (organisationRating.getAgency() != null && organisationRating.getRating() != null)
                    //assertThat(locator("", organisationRating.getAgency().getRealValue())).innerText().isEqualToIgnoringWhitespace(organisationRating.getRating().getRealValue());
                    assertThat(locator("ag.ins.detail.cqs")).innerText().contains(organisationRating.getAgency().getRealValue() + "   " + organisationRating.getRating().getRealValue());
            }
        }
        if (instrument.getDebtClassification() != null) {
            assertThat(locator("ag.ins.detail.debt.classification")).innerText().isEqualToIgnoringWhitespace(instrument.getDebtClassification().getRealValue());
        }
        if (instrument.getMinimumTradableAmount() != null) {
            assertThat(locator("ag.ins.detail.min.tradeable.amount")).innerText().isEqualToIgnoringWhitespace(instrument.getMinimumTradableAmount().getRealValue());
        }
        if (instrument.getNotation() != null) {
            assertThat(locator("ag.ins.detail.notation")).innerText().isEqualToIgnoringWhitespace(instrument.getNotation().getRealValue());
        }
        if (instrument.getMarket() != null) {
            assertThat(locator("ag.ins.detail.market")).innerText().isEqualToIgnoringWhitespace(instrument.getMarket().getRealValue());
        }
    }

    private void assertAgreementOverview(BookingAgreementOverview booking) throws Exception {
        if (booking.getTotalCallRequired() != null)
            assertThat(locator("ag.total.call.required")).innerText().isEqualToIgnoringWhitespace(booking.getTotalCallRequired().getRealValue());
        if (booking.getTotalDeliveryRequired() != null)
            assertThat(locator("ag.total.delivery.required")).innerText().isEqualToIgnoringWhitespace(booking.getTotalDeliveryRequired().getRealValue());
        if (booking.getTotalRecallRequired() != null)
            assertThat(locator("ag.total.recall.required")).innerText().isEqualToIgnoringWhitespace(booking.getTotalRecallRequired().getRealValue());
        if (booking.getTotalReturnRequired() != null)
            assertThat(locator("ag.total.return.required")).innerText().isEqualToIgnoringWhitespace(booking.getTotalReturnRequired().getRealValue());
        if (booking.getMaxRecallHolding() != null)
            assertThat(locator("ag.max.recall.holding")).innerText().isEqualToIgnoringWhitespace(booking.getMaxRecallHolding().getRealValue());
        if (booking.getMaxReturnHolding() != null)
            assertThat(locator("ag.max.return.holding")).innerText().isEqualToIgnoringWhitespace(booking.getMaxReturnHolding().getRealValue());
        if (booking.getMaxRecallAvailable() != null)
            assertThat(locator("ag.max.recall.available")).innerText().isEqualToIgnoringWhitespace(booking.getMaxRecallAvailable().getRealValue());
        if (booking.getMaxReturnAvailable() != null)
            assertThat(locator("ag.max.return.available")).innerText().isEqualToIgnoringWhitespace(booking.getMaxReturnAvailable().getRealValue());
        if (booking.getTotalVMCallRequired() != null)
            assertThat(locator("ag.total.vm.call.required")).innerText().isEqualToIgnoringWhitespace(booking.getTotalVMCallRequired().getRealValue());
        if (booking.getTotalVMDeliveryRequired() != null)
            assertThat(locator("ag.total.vm.delivery.required")).innerText().isEqualToIgnoringWhitespace(booking.getTotalVMDeliveryRequired().getRealValue());
        if (booking.getTotalVMRecallRequired() != null)
            assertThat(locator("ag.total.vm.recall.required")).innerText().isEqualToIgnoringWhitespace(booking.getTotalVMRecallRequired().getRealValue());
        if (booking.getTotalVMReturnRequired() != null)
            assertThat(locator("ag.total.vm.return.required")).innerText().isEqualToIgnoringWhitespace(booking.getTotalVMReturnRequired().getRealValue());
        if (booking.getTotalIMCallRequired() != null)
            assertThat(locator("ag.total.im.call.required")).innerText().isEqualToIgnoringWhitespace(booking.getTotalIMCallRequired().getRealValue());
        if (booking.getTotalIMDeliveryRequired() != null)
            assertThat(locator("ag.total.im.delivery.required")).innerText().isEqualToIgnoringWhitespace(booking.getTotalIMDeliveryRequired().getRealValue());
        if (booking.getTotalIMReCallRequired() != null)
            assertThat(locator("ag.total.im.recall.required")).innerText().isEqualToIgnoringWhitespace(booking.getTotalIMReCallRequired().getRealValue());
        if (booking.getTotalIMReturnRequired() != null)
            assertThat(locator("ag.total.im.return.required")).innerText().isEqualToIgnoringWhitespace(booking.getTotalIMReturnRequired().getRealValue());
        if (booking.getMaxVMRecallHolding() != null)
            assertThat(locator("ag.max.vm.recall.holding")).innerText().isEqualToIgnoringWhitespace(booking.getMaxVMRecallHolding().getRealValue());
        if (booking.getMaxVMReturnHolding() != null)
            assertThat(locator("ag.max.vm.return.holding")).innerText().isEqualToIgnoringWhitespace(booking.getMaxVMReturnHolding().getRealValue());
        if (booking.getMaxVMRecallAvailable() != null)
            assertThat(locator("ag.max.vm.recall.available")).innerText().isEqualToIgnoringWhitespace(booking.getMaxVMRecallAvailable().getRealValue());
        if (booking.getMaxVMReturnAvailable() != null)
            assertThat(locator("ag.max.vm.return.available")).innerText().isEqualToIgnoringWhitespace(booking.getMaxVMReturnAvailable().getRealValue());
        if (booking.getMaxIMRecallHolding() != null)
            assertThat(locator("ag.max.im.recall.holding")).innerText().isEqualToIgnoringWhitespace(booking.getMaxIMRecallHolding().getRealValue());
        if (booking.getMaxIMReturnHolding() != null)
            assertThat(locator("ag.max.im.return.holding")).innerText().isEqualToIgnoringWhitespace(booking.getMaxIMReturnHolding().getRealValue());
        if (booking.getMaxIMRecallAvailable() != null)
            assertThat(locator("ag.max.im.recall.available")).innerText().isEqualToIgnoringWhitespace(booking.getMaxIMRecallAvailable().getRealValue());
        if (booking.getMaxIMReturnAvailable() != null)
            assertThat(locator("ag.max.im.return.available")).innerText().isEqualToIgnoringWhitespace(booking.getMaxIMReturnAvailable().getRealValue());
    }

    private void assertStatus(BookingInformation booking) throws Exception {
        SettlementStatusInfoDetail detail = null;
        if (booking.getSettlementStatusInfo() != null && !booking.getSettlementStatusInfo().isEmpty())
            detail = booking.getSettlementStatusInfo().get(booking.getSettlementStatusInfo().size() - 1);
        if (detail != null) {
            if (detail.getSettlementComment() != null) {
                assertThat(locator("ag.bookingInfo.settlementComment")).attributeValueOf("value").isEqualTo(detail.getSettlementComment().getRealValue());
            }
            if (detail.getSettlementStatus() != null && detail.isApply() != null && !detail.isApply()) {
                assertThat(locator("ag.bookingInfo.settlementStatus")).allOptionTexts().doesNotContain(detail.getSettlementStatus().value());
            } else {
                assertThat(locator("ag.bookingInfo.settlementStatus")).selectedText().isEqualTo(detail.getSettlementStatus().value());
            }
        }
    }

    private void assertExternalBookingInformation(BookingDetail booking) throws Exception {
        if (booking.getExternalBookingInformation() != null) {
            if (booking.getExternalBookingInformation().getAttachment() != null) {
                String file = booking.getExternalBookingInformation().getAttachment().getRealValue();
                String[] holder = file.split(Biz.FIELD_SEPARATOR);
                file = holder[holder.length - 1];
                assertThat(locator("ag.externalBookingInfo.attachment")).attributeValueOf("value").isEqualTo(file);
            }
            if (booking.getExternalBookingInformation().getAuditInfo() != null) {
                for (AuditInfo AuditInfo : booking.getExternalBookingInformation().getAuditInfo()) {
                    if (AuditInfo.getBookingStatusRow1() != null) {
                        assertThat("ag.AuditInfoBookingStatusRow1").innerText().isEqualToIgnoringWhitespace(AuditInfo.getBookingStatusRow1().getRealValue());
                    }
                    if (AuditInfo.getBookingStatusRow2() != null) {
                        assertThat("ag.AuditInfoBookingStatusRow2").innerText().isEqualToIgnoringWhitespace(AuditInfo.getBookingStatusRow2().getRealValue());
                    }
                    if (AuditInfo.getBookingStatusRow3() != null) {
                        assertThat("ag.AuditInfoBookingStatusRow3").innerText().isEqualToIgnoringWhitespace(AuditInfo.getBookingStatusRow3().getRealValue());
                    }

                }


            }
        }
    }

    private void assertCouponCalculation(BookingDetail booking) throws Exception {
        if (booking.getCouponCalculation() != null) {
            if (booking.getCouponCalculation().getDaysAccrued() != null) {
                assertThat(locator("ag.couponCal.daysAccrued")).innerText().isEqualTo(booking.getCouponCalculation().getDaysAccrued().getRealValue());
            }
            if (booking.getCouponCalculation().getCurrentAccrual() != null) {
                assertThat(locator("ag.couponCal.currentAccrual")).innerText().isEqualTo(booking.getCouponCalculation().getCurrentAccrual().getRealValue());
            }
            if (booking.getCouponCalculation().getDaysinPeriod() != null) {
                assertThat(locator("ag.couponCal.daysInPeriod")).innerText().isEqualTo(booking.getCouponCalculation().getDaysinPeriod().getRealValue());
            }
            if (booking.getCouponCalculation().getNextCouponPayment() != null) {
                assertThat(locator("ag.couponCal.nextCouponPayment")).innerText().isEqualTo(booking.getCouponCalculation().getNextCouponPayment().getRealValue());
            }
        }
    }

    private void assertPaymentInstructions(BookingDetail booking) throws Exception {
        if (booking.getPrincipalPaymentInstructions() != null) {
            if (booking.getPrincipalPaymentInstructions().getCapitalPayInstr() != null) {
                assertThat(locator("ag.paymentInsr.principalCapitalPayInstr")).allSelectedTexts().containsOnly(booking.getPrincipalPaymentInstructions().getCapitalPayInstr().getRealValue());
            }
        }
        if (booking.getCounterpartyPaymentInstructions() != null) {
            if (booking.getCounterpartyPaymentInstructions().getCapitalPayInstr() != null) {
                assertThat(locator("ag.paymentInsr.cptyCapitalPayInstr")).allSelectedTexts().containsOnly(booking.getCounterpartyPaymentInstructions().getCapitalPayInstr().getRealValue());
            }
        }
        if (booking.getPlaceOfSettlement() != null) {
            if (booking.getPlaceOfSettlement().getCapitalPayInstr() != null) {
                assertThat(locator("ag.paymentInsr.placeOfSettlement")).allSelectedTexts().containsOnly(booking.getPlaceOfSettlement().getCapitalPayInstr().getRealValue());
            }
        }
        if (booking.getPrincipal() != null) {
            List<Account> account = booking.getPrincipal().getAccount();
            int accountSize = account.size();
            for (Account anAccount : account) {
                if (anAccount.getAccountName() != null) {
                    if (anAccount.getValue() != null) {
                        assertThat("ag.paymentInsr.principalAccountValue", anAccount.getAccountName().getRealValue()).allInnerTexts().containsOnly(anAccount.getValue().getRealValue());
                    }
                }
            }
        }
        if (booking.getCounterParty() != null) {
            List<Account> account = booking.getCounterParty().getAccount();
            int accountSize = account.size();
            for (Account anAccount : account) {
                if (anAccount.getAccountName() != null) {
                    if (anAccount.getValue() != null) {
                        assertThat("ag.paymentInsr.counterpartyAccountValue", anAccount.getAccountName().getRealValue()).allInnerTexts().containsOnly(anAccount.getValue().getRealValue());
                    }
                }
            }
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

    private void assertBookingInformation(BookingInformation booking) throws Exception {
        if (booking.getBookingType() != null) {
            assertThat(locator("ag.bookingInfo.bookingType")).selectedText().isEqualTo(booking.getBookingType().value());
        }

        if (booking.getSettlementDate() != null) {
            if ((booking.getSettlementDate().getRealValue().contains("t")) || (booking.getSettlementDate().getRealValue().contains("T"))){
                String settlementDate = Biz.convertDate(getServerTime(), booking.getSettlementDate().getRealValue(), "M/d/yyyy");
                if (element("ag.bookingInfo.settlementDate").isDisplayed())  {
                    assertThat(locator("ag.bookingInfo.settlementDate")).attributeValueOf("value").isEqualTo(settlementDate);
                } else {
                    assertThat(locator("ag.bookingInfo.settlementDate.value")).innerText().isEqualTo(settlementDate);
                }
            } else {
                if (element("ag.bookingInfo.settlementDate").isDisplayed())  {
                    assertThat(locator("ag.bookingInfo.settlementDate")).attributeValueOf("value").isEqualTo(booking.getSettlementDate().getRealValue());
                } else {
                    assertThat(locator("ag.bookingInfo.settlementDate.value")).innerText().isEqualToIgnoringWhitespace(booking.getSettlementDate().getRealValue());
                }
            }
//            if (element("ag.bookingInfo.settlementDate").isDisplayed()) {
//                if ((booking.getSettlementDate().getRealValue().contains("t")) || (booking.getSettlementDate().getRealValue().contains("T"))){
//                    String settlementDate = Biz.convertDate(getServerTime(), booking.getSettlementDate().getRealValue(), "M/d/yyyy");
//                    assertThat(locator("ag.bookingInfo.settlementDate")).attributeValueOf("value").isEqualTo(settlementDate);
//                } else {
//                    assertThat(locator("ag.bookingInfo.settlementDate")).attributeValueOf("value").isEqualTo(booking.getSettlementDate().getRealValue());
//                }
//            } else {
//                if ((booking.getSettlementDate().getRealValue().contains("t")) || (booking.getSettlementDate().getRealValue().contains("T"))){
//                    String settlementDate = Biz.convertDate(getServerTime(), booking.getSettlementDate().getRealValue(), "M/d/yyyy");
//                    assertThat(locator("ag.bookingInfo.settlementDate.value")).innerText().isEqualToIgnoringWhitespace(booking.getSettlementDate().getRealValue());
//                } else {
//                    assertThat(locator("ag.bookingInfo.settlementDate.value")).innerText().isEqualToIgnoringWhitespace(booking.getSettlementDate().getRealValue());
//                }
//            }
        }

        if (booking.getEvent() != null) {
            if (element("ag.bookingInfo.event").isDisplayed()) {
                assertThat(locator("ag.bookingInfo.event")).selectedText().isEqualTo(booking.getEvent().value());
            } else {
                assertThat(locator("ag.bookingInfo.event.value")).innerText().isEqualToIgnoringWhitespace(booking.getEvent().value());
            }
        }
        if (booking.getEventId() != null) {
            if (element("ag.bookingInfo.eventId.value").isDisplayed()) {
                assertThat(locator("ag.bookingInfo.eventId.value")).innerText().isEqualTo(booking.getEventId().getRealValue());
            } else {
                assertThat(locator("ag.bookingInfo.eventId.value1")).innerText().isEqualTo(booking.getEventId().getRealValue());
            }
        }

        if (booking.isRehypothecated() != null) {
            assertThat(locator("ag.bookingInfo.rehypothecated")).selected().isEqualTo(booking.isRehypothecated());
        }
        if (booking.getAssetOwner() != null) {
            if (booking.getAssetOwner().getType() != null) {
                assertThat(locator("ag.bookingInfo.assetOwnerSearchType")).allSelectedTexts().containsOnly(booking.getAssetOwner().getType().value());
            }
            if (booking.getAssetOwner().getFilter() != null) {
                assertThat(locator("ag.bookingInfo.assetOwnerSearchFilter")).attributeValueOf("value").isEqualTo(booking.getAssetOwner().getFilter().getRealValue());
            } else if (booking.getAssetOwner().getLinkText() != null) {
                assertThat(locator("ag.bookingInfo.assetOwnerSearchFilter")).attributeValueOf("value").isEqualTo(booking.getAssetOwner().getLinkText().getRealValue());
            }
        }

        if (booking.getSettlementStatusInfo() != null && !booking.getSettlementStatusInfo().isEmpty()) {
            for (SettlementStatusInfoDetail settlementStatusInfoDetail : booking.getSettlementStatusInfo()) {
                if (settlementStatusInfoDetail.getSettlementStatus() != null) {
                    if (settlementStatusInfoDetail.isApply() != null && !settlementStatusInfoDetail.isApply()) {
                        assertThat(locator("ag.bookingInfo.settlementStatus")).allOptionTexts().doesNotContain(settlementStatusInfoDetail.getSettlementStatus().value());
                    } else {
                        assertThat(locator("ag.bookingInfo.settlementStatus")).selectedText().isEqualToIgnoringCase(settlementStatusInfoDetail.getSettlementStatus().value());
                    }
                }
            }
        }

        if (booking.getComment() != null) {
            assertThat(locator("ag.bookingInfo.comment")).attributeValueOf("value").isEqualTo(booking.getComment().getRealValue());
        }

        if (booking.getAssetNotes2() != null) {
            assertThat(locator("ag.bookingInfo.assetNotes2")).attributeValueOf("value").isEqualTo(booking.getAssetNotes2().getRealValue());
        }
        if (booking.getAssetNotes3() != null) {
            assertThat(locator("ag.bookingInfo.assetNotes3")).attributeValueOf("value").isEqualTo(booking.getAssetNotes3().getRealValue());
        }
        if (booking.getMovementDescription() != null) {
            assertThat(locator("ag.bookingInfo.movementDescription")).allSelectedTexts().containsOnly(booking.getMovementDescription().getRealValue());
        }
        if (booking.isFlushed() != null) {
            assertThat(locator("ag.flushed")).selected().isEqualTo(booking.isFlushed());
        }
        if (booking.getValuationPercentage() != null) {
            if (element("ag.bookingInfo.valuationPercentage").isDisplayed()) {
                assertThat(locator("ag.bookingInfo.valuationPercentage")).attributeValueOf("value").isEqualTo(booking.getValuationPercentage().getRealValue());
            } else {
                assertThat(locator("ag.bookingInfo.valuationPercentage.value")).innerText().isEqualTo(booking.getValuationPercentage().getRealValue());
            }
        }
    }

    private void assertBookingInformationDetail(BookingInformationDetail booking) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if (booking.getAgreementName() == null && (booking.getAgreementDiscription() == null && booking.getPrincipalShortName() == null &&
                booking.getCounterpartyShortName() == null && booking.getExternalId() == null && booking.getAgreementId() == null)) {
            if (booking.getNominalAmount() != null) {
                if (element("ag.bookingInfo.norminalAmount").isDisplayed()) {
                    try {
                        assertThat(locator("ag.bookingInfo.norminalAmount")).attributeValueOf("value").isEqualTo(
                                format.format(Float.parseFloat(booking.getNominalAmount().getRealValue())));
                    } catch (NumberFormatException e) {
                        assertThat(locator("ag.bookingInfo.norminalAmount")).attributeValueOf("value").isEqualTo(
                                (booking.getNominalAmount().getRealValue()));
                    }

                } else {
                    try {
                        assertThat(locator("ag.bookingInfo.norminalAmount.value")).innerText().isEqualTo(
                                format.format(Float.parseFloat(booking.getNominalAmount().getRealValue())));
                    } catch (NumberFormatException e) {
                        assertThat(locator("ag.bookingInfo.norminalAmount.value")).innerText().isEqualTo(
                                booking.getNominalAmount().getRealValue());
                    }


                }
            }
            if (booking.getRequiredAmount() != null && element("ag.bookingInfo.requiredAmount").isDisplayed()) {
                assertThat(locator("ag.bookingInfo.requiredAmount")).attributeValueOf("value").isEqualTo(
                        format.format(Float.parseFloat(booking.getRequiredAmount().getRealValue()))
                );
            }
            if (booking.getMovement() != null) {
                if (element("ag.bookingInfo.movement").isDisplayed()) {
                    assertThat(locator("ag.bookingInfo.movement")).allSelectedTexts().containsOnly(booking.getMovement().value());
                } else {
                    assertThat(locator("ag.bookingInfo.movement.value")).innerText().isEqualTo(booking.getMovement().value());
                }
            }
            if (booking.getVmAmount() != null) {
                if (element("ag.bookingInfo.vmAmount").isDisplayed()) {
                    assertThat(locator("ag.bookingInfo.vmAmount")).attributeValueOf("value").isEqualTo(
                            format.format(Float.parseFloat(booking.getVmAmount().getRealValue()))
                    );
                } else {
                    assertThat(locator("ag.bookingInfo.vmAmount.value")).innerText().isEqualTo(
                            format.format(Float.parseFloat(booking.getVmAmount().getRealValue()))
                    );
                }
            }
            if (booking.getImAmount() != null) {
                if (element("ag.bookingInfo.imAmount").isDisplayed()) {
                    assertThat(locator("ag.bookingInfo.imAmount")).attributeValueOf("value").isEqualTo(
                            format.format(Float.parseFloat(booking.getImAmount().getRealValue()))
                    );
                } else {
                    assertThat(locator("ag.bookingInfo.imAmount.value")).innerText().isEqualTo(
                            format.format(Float.parseFloat(booking.getImAmount().getRealValue()))
                    );
                }
            }
            if (booking.getVmRequiredAmount() != null && element("ag.bookingInfo.vmRequiredAmount").isDisplayed()) {
                assertThat(locator("ag.bookingInfo.vmRequiredAmount")).attributeValueOf("value").isEqualTo(
                        format.format(Float.parseFloat(booking.getVmRequiredAmount().getRealValue()))
                );
            }
            if (booking.getImRequiredAmount() != null && element("ag.bookingInfo.imRequiredAmount").isDisplayed()) {
                assertThat(locator("ag.bookingInfo.imRequiredAmount")).attributeValueOf("value").isEqualTo(
                        format.format(Float.parseFloat(booking.getImRequiredAmount().getRealValue()))
                );
            }
            if (booking.getDfRequiredAmount() != null && element("ag.bookingInfo.dfRequiredAmount").isDisplayed()) {
                assertThat(locator("ag.bookingInfo.dfRequiredAmount")).attributeValueOf("value").isEqualTo(
                        format.format(Float.parseFloat(booking.getDfRequiredAmount().getRealValue()))
                );
            }
        } else {
            String agreement = getActuralAgreementName(booking);
            if (booking.getNominalAmount() != null) {
                if (element("ag.bookingInfo.subAmount", agreement).isDisplayed()) {
                    assertThat(locator("ag.bookingInfo.subAmount", agreement)).attributeValueOf("value").isEqualTo(
                            format.format(Float.parseFloat(booking.getNominalAmount().getRealValue()))
                    );
                } else {
                    assertThat(locator("ag.bookingInfo.subAmount.value", agreement)).innerText().isEqualTo(
                            format.format(Float.parseFloat(booking.getNominalAmount().getRealValue()))
                    );
                }
            }
            if (booking.getVmAmount() != null) {
                if (element("ag.bookingInfo.subVmAmount").isDisplayed()) {
                    assertThat(locator("ag.bookingInfo.subVmAmount", agreement)).attributeValueOf("value").isEqualTo(
                            format.format(Float.parseFloat(booking.getVmAmount().getRealValue()))
                    );
                } else {
                    assertThat(locator("ag.bookingInfo.subVmAmount.value", agreement)).innerText().isEqualTo(
                            format.format(Float.parseFloat(booking.getVmAmount().getRealValue()))
                    );
                }
            }
            if (booking.getVmRequiredAmount() != null && element("ag.bookingInfo.subVmRequiredAmount", agreement).isDisplayed()) {
                assertThat(locator("ag.bookingInfo.subVmRequiredAmount", agreement)).attributeValueOf("value").isEqualTo(
                        format.format(Float.parseFloat(booking.getVmRequiredAmount().getRealValue()))
                );
            }
            if (booking.getImAmount() != null) {
                if (element("ag.bookingInfo.subImAmount", agreement).isDisplayed()) {
                    assertThat(locator("ag.bookingInfo.subImAmount", agreement)).attributeValueOf("value").isEqualTo(
                            format.format(Float.parseFloat(booking.getImAmount().getRealValue()))
                    );
                } else {
                    assertThat(locator("ag.bookingInfo.subImAmount.value", agreement)).innerText().isEqualTo(
                            format.format(Float.parseFloat(booking.getImAmount().getRealValue()))
                    );
                }
            }
            if (booking.getImRequiredAmount() != null && element("ag.bookingInfo.subImRequiredAmount", agreement).isDisplayed()) {
                assertThat(locator("ag.bookingInfo.subImRequiredAmount", agreement)).attributeValueOf("value").isEqualTo(
                        format.format(Float.parseFloat(booking.getImRequiredAmount().getRealValue()))
                );
            }
            if (booking.getMovement() != null) {
                if (element("ag.bookingInfo.subMovement").isDisplayed()) {
                    assertThat(locator("ag.bookingInfo.subMovement", agreement)).allSelectedTexts().containsOnly(booking.getMovement().value());
                } else {
                    assertThat(locator("ag.bookingInfo.subMovement.value", agreement)).innerText().isEqualTo(booking.getMovement().value());
                }
            }
        }
    }

    public void changeStatementStatus(SettlementStatusInfoDetail detail) throws Exception {
        if (detail.getSettlementStatus() != null) {
            element("ag.bookingInfo.settlementStatus").selectByVisibleText(detail.getSettlementStatus().value());
        }

        if (detail.getSettlementComment() != null) {
            element("ag.bookingInfo.settlementComment").input(detail.getSettlementComment().getRealValue());
            //element("ag.bookingInfo.settlementComment").isEnabled()
        }

        save();
    }

    public void save() throws Exception {
        element("ag.save").click();
    }

    public void saveAndNext() throws Exception {
        element("ag.save.and.next").click();
    }

    public void saveAndComplete() throws Exception {
        element("ag.save.and.complete").click();
    }

    public void cancel() throws Exception {
        element("ag.cancel").click();
    }

    public void apply() throws Exception{
        element("ad.apply").click();
    }
    public void Recalculate() throws Exception{
        element("ag.bookingInfo.Recalculate").click();
    }

    public String getActuralAgreementName(BookingInformationDetail booking) {
        if (booking.getAgreementName() != null) {
            return booking.getAgreementName().getRealValue();
        } else {
            StringBuilder acturalAgreementName = new StringBuilder();
            if (booking.getAgreementDiscription() != null) {
//                acturalAgreementName.append(booking.getAgreementDiscription().getRealValue() + "/");
                acturalAgreementName.append(booking.getAgreementDiscription().getRealValue()).append("/");
            }
            if (booking.getPrincipalShortName() != null) {
//                acturalAgreementName.append(booking.getPrincipalShortName().getRealValue() + "/");
                acturalAgreementName.append(booking.getPrincipalShortName().getRealValue()).append("/");
            }
            if (booking.getCounterpartyShortName() != null) {
//                acturalAgreementName.append(booking.getCounterpartyShortName().getRealValue() + "/");
                acturalAgreementName.append(booking.getCounterpartyShortName().getRealValue()).append("/");
            }
            if (booking.getExternalId() != null) {
//                acturalAgreementName.append(booking.getExternalId().getRealValue() + "/");
                acturalAgreementName.append(booking.getExternalId().getRealValue()).append("/");
            }
            if (booking.getAgreementId() != null) {
                acturalAgreementName.append(booking.getAgreementId().getRealValue());
            }
            return acturalAgreementName.toString();
        }
    }

    public void resendConfLetter() throws Exception {
        element("ag.resend.conf.letter").click();
    }

    public void returnToAssetHoldings() throws Exception {
        element("ag.return.to.assetholding").click();
    }

    public void clickCancelButton() throws Exception {
        cancel();
    }

    public void assertEligibilityRuleBreached(EligibilityRuleBreached eligibilityRuleBreached) throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        StringBuffer xpath = new StringBuffer();
        xpath.append("//tr");
        Method[] methods = eligibilityRuleBreached.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")
                    && method.invoke(eligibilityRuleBreached) != null
                    && !method.getName().equals("getName")
                    && method.getReturnType().equals(StringType.class)) {
                StringType stringType = (StringType) method.invoke(eligibilityRuleBreached);
                if (method.getName().equals("getInstrumentId")) {
//                    xpath.append("[td[contains(text(),'" + stringType.getRealValue() + "')]]");
                    xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                }
                if (method.getName().equals("getRule")) {
//                    xpath.append("[td[6][normalize-space(contains(text(),'" + stringType.getRealValue() + "'))]]");
                    xpath.append("[td[6][normalize-space(contains(text(),'").append(stringType.getRealValue()).append("'))]]");
                }
                if (method.getName().equals("getSource")) {
//                    xpath.append("[td[contains(text(),'" + stringType.getRealValue() + "')]]");
                    xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                }
                if (method.getName().equals("getCurrency")) {
//                    xpath.append("[td[contains(text(),'" + stringType.getRealValue() + "')]]");
                    xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                }

            }
        }
        if (eligibilityRuleBreached.isIsDisplayed() == null ||
                (eligibilityRuleBreached.isIsDisplayed() != null && eligibilityRuleBreached.isIsDisplayed().equals(true))) {
            assertThat("ag.eligible.breached.rule.detail", xpath.toString()).displayed().isEqualTo(true);
        } else {
            assertThat("ag.eligible.breached.rule.detail", xpath.toString()).displayed().isEqualTo(false);
        }
    }

    public void handleAlters(List<Message> handles) {
        PageHelper.handleAlters(handles);
    }
}
