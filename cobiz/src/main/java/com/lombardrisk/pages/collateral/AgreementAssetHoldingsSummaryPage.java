package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.text.DecimalFormat;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementAssetHoldingsSummaryPage extends PageBase {
    public AgreementAssetHoldingsSummaryPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * enter agreement assets cash/equity/bond summary page from agreement asset
     * holding summary page
     *
     * @param booking
     */
    public void enterAgreementAssetsSummary(AssetBookingSummary booking) throws Exception {
        if (booking.getAssetType() != null) {
            waitThat().document().toBeReady();
            waitThat().jQuery().toBeInactive();
            PageHelper.d33640Workaround();
            element("ag.asset.type", booking.getAssetType().getRealValue()).clickByJavaScript();
        }
    }
    /**
     * click add on Agreement Assets - Cash Summary page to enter
     * in Collateral - Agreement Assets - Cash Editor
     * @param
     */

    public void add_booking() throws Exception{
        element("ag.add.book").click();
    }

    /**
     * enter agreement assets cash/equity/bond summary page from agreement asset
     * holding summary page
     *
     * @param booking
     */
    public void enterAgreementAssetsSummary(BookingDetail booking) throws Exception {
        if (booking.getAssetType() != null)
            element("ag.asset.type", booking.getAssetType().getRealValue()).click();
    }

    /**
     * enter agreement assets cash/equity/bond summary page from agreement asset
     * holding summary page
     *
     * @param booking
     */
    public void enterAgreementAssetsSummary(CashMovementDetail booking) throws Exception {
        if (booking.getAssetType() != null)
            element("ag.asset.type", booking.getAssetType().getRealValue()).click();
    }



    public void assertAssetHoldingSummary(CollateralAssetHoldingSummary summary) throws Exception{
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if (summary.getCollateralAssetHoldingSummaryList()!=null && summary.getCollateralAssetHoldingSummaryList().size()>0){
            for (CollateralAssetHoldingSummaryList cahslist : summary.getCollateralAssetHoldingSummaryList()){
                if (cahslist.getAssetClass()!=null)
                    assertThat("ag.asset.summary.asset.class",cahslist.getAssetClass().getRealValue()).displayed().isTrue();
                if (cahslist.getAssetTypeHoldingSummaryList()!=null && cahslist.getAssetTypeHoldingSummaryList().size()>0){
                    for (AssetTypeHoldingSummaryList athslist : cahslist.getAssetTypeHoldingSummaryList()){
                        if (athslist.getAssetType()!=null) {
                            assertThat("ag.asset.summary.asset.type", athslist.getAssetType().getRealValue()).displayed().isTrue();
                            if (athslist.isHeld() != null) {
                                if (athslist.isHeld())
                                    assertThat("ag.asset.summary.held", athslist.getAssetType().getRealValue()).displayed().isTrue();
                                else
                                    assertThat("ag.asset.summary.held", athslist.getAssetType().getRealValue()).displayed().isFalse();
                            }
                            if (athslist.isDelivery() != null) {
                                if (athslist.isDelivery())
                                    assertThat("ag.asset.summary.delivery", athslist.getAssetType().getRealValue()).displayed().isTrue();
                                else
                                    assertThat("ag.asset.summary.delivery", athslist.getAssetType().getRealValue()).displayed().isFalse();
                            }
                            if (athslist.isSysDraft() != null) {
                                if (athslist.isSysDraft())
                                    assertThat("ag.asset.summary.sysdraft", athslist.getAssetType().getRealValue()).displayed().isTrue();
                                else
                                    assertThat("ag.asset.summary.sysdraft", athslist.getAssetType().getRealValue()).displayed().isFalse();
                            }
                            if (athslist.getEligibleForReceipt() != null)
                                assertThat("ag.asset.summary.eligible.for.receipt", athslist.getAssetType().getRealValue()).innerText().isEqualToIgnoringWhitespace(athslist.getEligibleForReceipt().getRealValue());
                            if (athslist.getBookingPriority() != null)
                                assertThat("ag.asset.summary.booking.priority", athslist.getAssetType().getRealValue()).innerText().isEqualToIgnoringWhitespace(athslist.getBookingPriority().getRealValue());
                            if (athslist.getMarketValue() != null) {
                                try {
                                    assertThat("ag.asset.summary.market.value", athslist.getAssetType().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                            format.format(Double.parseDouble(athslist.getMarketValue().getRealValue()))
                                    );
                                } catch(NumberFormatException e){
                                    assertThat("ag.asset.summary.market.value", athslist.getAssetType().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                            athslist.getMarketValue().getRealValue()
                                    );
                                }
                            }
                            if (athslist.getCollateralValue()!=null) {
                                try {
                                    assertThat("ag.asset.summary.collateral.value", athslist.getAssetType().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                            format.format(Double.parseDouble(athslist.getCollateralValue().getRealValue()))
                                    );
                                } catch (NumberFormatException e) {
                                    assertThat("ag.asset.summary.collateral.value", athslist.getAssetType().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                            athslist.getCollateralValue().getRealValue()
                                    );
                                }
                            }
                            if (athslist.getVmcollateralValue()!=null){
                            try {
                                    assertThat("ag.asset.summary.vmcollateral.value", athslist.getAssetType().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                            format.format(Double.parseDouble(athslist.getVmcollateralValue().getRealValue()))
                                    );
                                } catch(NumberFormatException e){
                                    assertThat("ag.asset.summary.vmcollateral.value", athslist.getAssetType().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                            athslist.getVmcollateralValue().getRealValue()
                                    );
                                }
                            }
                            if (athslist.getImcollateralValue()!=null) {
                                try {
                                    assertThat("ag.asset.summary.imcollateral.value", athslist.getAssetType().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                            format.format(Double.parseDouble(athslist.getImcollateralValue().getRealValue()))
                                    );
                                } catch (NumberFormatException e) {
                                    assertThat("ag.asset.summary.imcollateral.value", athslist.getAssetType().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                            athslist.getImcollateralValue().getRealValue()
                                    );
                                }
                            }
                        }
                    }
                }
            }
        }
        if (summary.getTotalCollateralHeldByPrincipal()!=null){
            if (summary.getTotalCollateralHeldByPrincipal().getAmount()!=null)
                assertThat("ag.asset.summary.total.collateral.amount","Principal").innerText().isEqualToIgnoringWhitespace(summary.getTotalCollateralHeldByPrincipal().getAmount().getRealValue());
            if (summary.getTotalCollateralHeldByPrincipal().getCurrency()!=null)
                assertThat("ag.asset.summary.total.collateral.ccy","Principal").innerText().isEqualToIgnoringWhitespace("Total Collateral held by Principal (" + summary.getTotalCollateralHeldByPrincipal().getCurrency().getRealValue() +")");
        }
        if (summary.getTotalCollateralHeldByCouterparty()!=null){
            if (summary.getTotalCollateralHeldByCouterparty().getAmount()!=null)
                assertThat("ag.asset.summary.total.collateral.amount","Counterparty").innerText().isEqualToIgnoringWhitespace(summary.getTotalCollateralHeldByCouterparty().getAmount().getRealValue());
            if (summary.getTotalCollateralHeldByCouterparty().getCurrency()!=null)
                assertThat("ag.asset.summary.total.collateral.ccy","Counterparty").innerText().isEqualToIgnoringWhitespace("Total Collateral held by Counterparty (" + summary.getTotalCollateralHeldByCouterparty().getCurrency().getRealValue() +")");
        }
    }


}
