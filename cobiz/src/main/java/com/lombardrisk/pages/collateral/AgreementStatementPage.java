package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import com.lombardrisk.util.collateral.AgreementUtils;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementStatementPage extends PageBase {
    private final String TOTALS_PRE_BUFFER = "//tr[td[normalize-space(.)='TOTALS Pre-Buffer']]";
    private final String BUFFER = "//tr[td[normalize-space(.)='Buffer']]";
    private final String TOTALS = "//tr[td[normalize-space(.)='TOTALS (";

    public AgreementStatementPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void switchModel(MultiModelAgreementStatement agmtStm) throws Exception {
        if (agmtStm.getMultimodelAssetHoldingSummary() != null
                && agmtStm.getMultimodelAssetHoldingSummary().size() > 0) {
            if (agmtStm.getMultimodelAssetHoldingSummary().get(0).getModelName() != null)
                element("at.agmt.model",
                        agmtStm.getMultimodelAssetHoldingSummary().get(0).getModelName().getRealValue()).click();
        }
    }

    public void switchOrder(EtfAgreementStatement agmtStm) throws Exception {
        if (agmtStm.getEtfAssetHoldingSummary() != null
                && agmtStm.getEtfAssetHoldingSummary().size() > 0) {
            if (agmtStm.getEtfAssetHoldingSummary().get(0).getOrderName() != null)
                element("at.agmt.order",
                        agmtStm.getEtfAssetHoldingSummary().get(0).getOrderName().getRealValue()).click();
        }
    }

    public void switchEtdModel(EtdModelAndModelCategoryAssetHoldingSummary assetHoldingSummary, String currency) throws Exception {
        element("at.etd.model.link"
                , getEtdModelAndModelCategoryRecordRow(assetHoldingSummary, currency)
                , assetHoldingSummary.getModel().getRealValue())
                .click();
    }

    /**
     * change agreement status
     *
     * @param status is your agreement current status
     */
    public void changeAgreementStatus(List<AgreementStatusType> status) throws Exception {
        for (AgreementStatusType statu : status) {
            changeAgreementStatus(statu);
        }
    }

    /**
     * chagne statement status
     *
     * @param status is your agreement statement current status
     */
    public void changeStatementStatus(List<AgreementStatusType> status) throws Exception {
        for (AgreementStatusType statu : status) {
            changeStatementStatus(statu);
        }
    }

    /**
     * change agreement status
     *
     * @param status is your agreement current status
     */
    public void changeAgreementStatus(AgreementStatusType status) throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("at.agmt.status").selectByVisibleText(status.value());
        element("at.save").click();
    }

    /**
     * chagne statement status
     *
     * @param status is your agreement statement current status
     */
    public void changeStatementStatus(AgreementStatusType status) throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("at.stmt.status").selectByVisibleText(status.value());
        element("at.save").click();
    }

    /**
     * approve agreement status from any status
     */
    public void approveAgreementStatus() throws Exception {
        String status = element("at.agmt.status").getSelectedText();
        if (AgreementStatusType.DRAFT.value().equals(status)) {
            changeAgreementStatus(AgreementStatusType.PENDING);
        }
        changeAgreementStatus(AgreementStatusType.APPROVED);
    }

    /**
     * approve statement status from any status
     */
    public void approveStatementStatus() throws Exception {
        String status = element("at.stmt.status").getSelectedText();
        if (AgreementStatusType.DRAFT.value().equals(status)) {
            changeStatementStatus(AgreementStatusType.PENDING);
        }
        changeStatementStatus(AgreementStatusType.APPROVED);
    }

    /**
     * enter agreement summary tab
     */
    public void enterAgreementSummary() throws Exception {
        //element("as.smry.tab").click();
        /*
         * Modified by Kent
         * The element as.smry.tab doesn't exist on statement page
         */
        element("at.agmt.review").clickByJavaScript();
    }

    /**
     * click agreement reivew image summary from agreement statement page
     */
    public void reviewAgreement() throws Exception {
        element("at.agmt.review").click();
    }

    /**
     * click edit asset summary info image on agreement statement page
     */
    public void editAssetSummaryInfo() throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        PageHelper.d33640Workaround();
        element("at.edit.asset.smry.info").clickByJavaScript();
    }

    public void editSummaryExposureInfo() throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        PageHelper.d33640Workaround();
        element("at.edit.expo.smry").clickByJavaScript();
    }

    /**
     * assert concentration limits breached on such agreement
     *
     * @param list {@link List<ConcentrationLimitBreached>}
     */
    @Deprecated
    public void assertAgreementConcentrationLimitsBreached(List<ConcentrationLimitBreached> list) throws Exception {
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
                        xpath.append("[td[normalize-space(text())='");
                        xpath.append(method.invoke(clb));
                        xpath.append("']]");
                    }
                }
            }
            assertThat("ag.breached.rule", xpath.toString()).displayed().isEqualTo(true);
        }
    }

    /**
     * enter EM page
     */
    public void enterAgreementExposureManagement() throws Exception {
        element("at.agmt.expo.mgmt").click();
    }

    public void enterAgreementAdmin() throws Exception {
        element("at.view.agradmin").click();
    }

    /**
     * click view rules breached on agreement statement page
     */
    public void viewRulesBreached() throws Exception {
        element("at.view.rules.breached").click();
    }

    public void viewEligibilityRuleBreached() throws Exception {
        element("at.view.eligibility.rules.breached").click();
    }

    /**
     * assert agreement statement value on agreement statement page
     *
     * @param agmt
     */
    public void assertAgreementStatement(AgreementStatement agmt) throws Exception {
        if (agmt.getConcentrationLimitBreached() != null && !agmt.getConcentrationLimitBreached().isEmpty()) {
            for (ConcentrationLimitBreached concentrationLimitBreached : agmt.getConcentrationLimitBreached()) {
                assertConcentrationLimitBreached(concentrationLimitBreached);
            }
        }
        if (agmt.getExposureSummary() != null)
            assertExposureSummary(agmt.getExposureSummary());
        if (agmt.getAssetHoldingsSummary() != null)
            assertAssetHoldingsSummary(agmt.getAssetHoldingsSummary());
        if (agmt.getMarginCallSummary() != null)
            assertMarginCallSummary(agmt.getMarginCallSummary());
        if (agmt.getMarginCallCalculation() != null)
            assertMarginCallCalculation(agmt.getMarginCallCalculation());
        if (agmt.getAgreementAdmin() != null)
            assertAgreementAdmin(agmt.getAgreementAdmin());
        if(agmt.getWarning() != null ){
            if (agmt.getWarning().isApply() != null){
                assertThat("at.warning").displayed().isEqualTo(agmt.getWarning().isApply());
            }
        }

    }

    public void assertMultiModelAgreementStatementSummary(MultiModelAgreementStatement multiModelAgreementStatement) throws Exception {
        if (multiModelAgreementStatement.getMultimodelAssetHoldingSummary() != null
                && !multiModelAgreementStatement.getMultimodelAssetHoldingSummary().isEmpty()) {
            for (MultimodelAssetHoldingSummary assetHoldingSummary : multiModelAgreementStatement.getMultimodelAssetHoldingSummary()) {
                if (assetHoldingSummary.getModelName() != null) {
                    assertMultiModelStatementDetailOnModel(assetHoldingSummary,
                            assetHoldingSummary.getModelName().getRealValue());
                }
                if (assetHoldingSummary.getBaseCurrency() != null) {
                    assertMultiModelStatementDetailOnCcy(assetHoldingSummary,
                            assetHoldingSummary.getBaseCurrency().getRealValue());

                }
            }
        }
    }

    private void assertMultiModelStatementDetailOnModel(MultimodelAssetHoldingSummary assetHoldingSummary, String type) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if (assetHoldingSummary.getImRequirement() != null)
            assertThat("at.ahs.im.requirement", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getImRequirement().getRealValue())));
        if (assetHoldingSummary.getImBalance() != null)
            assertThat("at.ahs.im.balance", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getImBalance().getRealValue())));
        if (assetHoldingSummary.getImExcessOrDeficit() != null)
            assertThat("at.ahs.im.excell.deficit", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getImExcessOrDeficit().getRealValue())));
        if (assetHoldingSummary.getTsaPhysical() != null)
            assertThat("at.ahs.tsa.phy", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getTsaPhysical().getRealValue())));
        if (assetHoldingSummary.getTsaCapitalise() != null)
            assertThat("at.ahs.tsa.cap", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getTsaCapitalise().getRealValue())));
        if (assetHoldingSummary.getVmRequirement() != null)
            assertThat("at.ahs.vm.requirement", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getVmRequirement().getRealValue())));
        if (assetHoldingSummary.getVmBalance() != null)
            assertThat("at.ahs.vm.balance", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getVmBalance().getRealValue())));
        if (assetHoldingSummary.getVmExcessOrDeficit() != null)
            assertThat("at.ahs.vm.excess.deficit", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getVmExcessOrDeficit().getRealValue())));
        if (assetHoldingSummary.getNetExcessOrDefict() != null)
            assertThat("at.ahs.net.excess.deficit",
                    assetHoldingSummary.getModelName().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getNetExcessOrDefict().getRealValue())));

    }

    private void assertMultiModelStatementDetailOnCcy(MultimodelAssetHoldingSummary assetHoldingSummary, String type) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if (assetHoldingSummary.getImRequirement() != null)
            assertThat("at.ahs.ccy.im.requirement", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getImRequirement().getRealValue())));
        if (assetHoldingSummary.getImBalance() != null)
            assertThat("at.ahs.ccy.im.balance", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getImBalance().getRealValue())));
        if (assetHoldingSummary.getImExcessOrDeficit() != null)
            assertThat("at.ahs.ccy.im.excell.deficit", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getImExcessOrDeficit().getRealValue())));
        if (assetHoldingSummary.getTsaPhysical() != null)
            assertThat("at.ahs.ccy.tsa.phy", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getTsaPhysical().getRealValue())));
        if (assetHoldingSummary.getTsaCapitalise() != null)
            assertThat("at.ahs.ccy.tsa.cap", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getTsaCapitalise().getRealValue())));
        if (assetHoldingSummary.getVmRequirement() != null)
            assertThat("at.ahs.ccy.vm.requirement", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getVmRequirement().getRealValue())));
        if (assetHoldingSummary.getVmBalance() != null)
            assertThat("at.ahs.ccy.vm.balance", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getVmBalance().getRealValue())));
        if (assetHoldingSummary.getVmExcessOrDeficit() != null)
            assertThat("at.ahs.ccy.vm.excess.deficit", type).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getVmExcessOrDeficit().getRealValue())));
        if (assetHoldingSummary.getNetExcessOrDefict() != null)
            assertThat("at.ahs.ccy.net.excess.deficit",
                    assetHoldingSummary.getModelName().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(assetHoldingSummary.getNetExcessOrDefict().getRealValue())));

    }

    public void assertEtdAgreementStatementSummary(EtdAgreementStatement etdAgreementStatement) throws Exception {
        if (etdAgreementStatement.getEtdAssetHoldingSummary() != null) {
            assertEtdAssetHoldingSummary(etdAgreementStatement.getEtdAssetHoldingSummary());
        }
    }

    private void assertEtdAssetHoldingSummary(EtdAssetHoldingSummary etdAssetHoldingSummary) throws Exception {
        String currency;
        if (etdAssetHoldingSummary.getAgreementBaseCurrency() != null)
            currency = etdAssetHoldingSummary.getAgreementBaseCurrency().getRealValue();
        else
            currency = "";
        if (etdAssetHoldingSummary.getEtdModelAndModelCategoryAssetHoldingSummary() != null
                && !etdAssetHoldingSummary.getEtdModelAndModelCategoryAssetHoldingSummary().isEmpty()) {
            assertEtdModelAndModelCategoryAssetHoldingSummary(
                    etdAssetHoldingSummary.getEtdModelAndModelCategoryAssetHoldingSummary(), currency);
        }
        if (etdAssetHoldingSummary.getEtdTotalPreBufferAssetHoldingSummary() != null)
            assertEtdAssetHoldingSummaryDetail(etdAssetHoldingSummary.getEtdTotalPreBufferAssetHoldingSummary()
                    , TOTALS_PRE_BUFFER);
        if (etdAssetHoldingSummary.getEtdBufferAssetHoldingSummary() != null)
            assertEtdBufferAssetHoldingSummary(etdAssetHoldingSummary.getEtdBufferAssetHoldingSummary());
        if (etdAssetHoldingSummary.getEtdTotalsAssetHoldingSummary() != null
                && etdAssetHoldingSummary.getEtdTotalsAssetHoldingSummary().getCurrency() != null
                && etdAssetHoldingSummary.getEtdTotalsAssetHoldingSummary().getEtdAssetHoldingSummaryDetail() != null) {
            assertEtdAssetHoldingSummaryDetail(
                    etdAssetHoldingSummary.getEtdTotalsAssetHoldingSummary().getEtdAssetHoldingSummaryDetail()
                    , TOTALS + etdAssetHoldingSummary.getEtdTotalsAssetHoldingSummary().getCurrency() + ")']]");
        }
    }

    private void assertEtdBufferAssetHoldingSummary(EtdAssetHoldingSummaryDetail etdAssetHoldingSummaryDetail) throws Exception{
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if (etdAssetHoldingSummaryDetail.getExcessOrDeficit() != null) {
            validateThat("at.etd.buffer.excess.deficit", BUFFER).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(etdAssetHoldingSummaryDetail.getExcessOrDeficit().getRealValue())));
            assertColor(validateThat("at.etd.buffer.excess.deficit", BUFFER), etdAssetHoldingSummaryDetail.getExcessOrDeficit());
        }
        if (etdAssetHoldingSummaryDetail.getPendingExcessOrDeficit() != null) {
            validateThat("at.etd.buffer.pending.excess.deficit", BUFFER).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(etdAssetHoldingSummaryDetail.getPendingExcessOrDeficit().getRealValue())));
            assertColor(validateThat("at.etd.buffer.pending.excess.deficit", BUFFER), etdAssetHoldingSummaryDetail.getPendingExcessOrDeficit());
        }
    }

    private void assertEtdModelAndModelCategoryAssetHoldingSummary(List<EtdModelAndModelCategoryAssetHoldingSummary> list, String currency) throws Exception {
        for (EtdModelAndModelCategoryAssetHoldingSummary assetHoldingSummary : list) {
            if (assetHoldingSummary.getModelCategory() != null) {
                String model = AgreementUtils.getDisplayedCategoryName(
                        assetHoldingSummary.getModelCategory().getRealValue() , currency);
                element("at.etd.model.category.expand", model).clickSmartly();
            }
            String xpath = getEtdModelAndModelCategoryRecordRow(assetHoldingSummary, currency);
            if (assetHoldingSummary.getEtdAssetHoldingSummaryDetail() != null)
                assertEtdAssetHoldingSummaryDetail(assetHoldingSummary.getEtdAssetHoldingSummaryDetail(), xpath);
            if (assetHoldingSummary.getModel() != null && assetHoldingSummary.getModelAgreementStatement() != null) {
                switchEtdModel(assetHoldingSummary, currency);
                assertAgreementStatement(assetHoldingSummary.getModelAgreementStatement());
                viewStatement();
            }
        }
    }

    private String getEtdModelAndModelCategoryRecordRow(EtdModelAndModelCategoryAssetHoldingSummary etdAssetHoldingSummary, String currency) throws Exception {
        StringBuffer xpath = new StringBuffer();
        if (etdAssetHoldingSummary.getModelCategory() != null
                || etdAssetHoldingSummary.getModel() != null
                || etdAssetHoldingSummary.getDesc() != null
                || etdAssetHoldingSummary.getAccountType() != null) {
            if (etdAssetHoldingSummary.getModelCategory() != null) {
                String model = AgreementUtils.getDisplayedCategoryName(
                        etdAssetHoldingSummary.getModelCategory().getRealValue(), currency);
                xpath.append("//tr[@name='modelCategoryTrName'][td[normalize-space(.)='")
                        .append(model).append("']]");
            }
            if (etdAssetHoldingSummary.getModel() != null
                    || etdAssetHoldingSummary.getDesc() != null
                    || etdAssetHoldingSummary.getAccountType() != null) {
                xpath.append("/following-sibling::tr[contains(@name,'subModel')]");
                if (etdAssetHoldingSummary.getModel() != null)
                    xpath.append("[td[a[normalize-space(.)='")
                            .append(etdAssetHoldingSummary.getModel().getRealValue()).append("']]]");
                if (etdAssetHoldingSummary.getDesc() != null)
                    xpath.append("[td[normalize-space(.)='")
                            .append(etdAssetHoldingSummary.getDesc().getRealValue()).append("']]");
                if (etdAssetHoldingSummary.getAccountType() != null)
                    xpath.append("[td[normalize-space(.)='")
                            .append(etdAssetHoldingSummary.getAccountType().getRealValue()).append("']]");
            }
        }
        return xpath.toString();
    }

    private void assertEtdAssetHoldingSummaryDetail(EtdAssetHoldingSummaryDetail assetHoldingSummaryDetail, String xpath) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if (assetHoldingSummaryDetail.getEndBalance() != null) {
            validateThat("at.etd.end.balance", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getEndBalance().getRealValue())));
            assertColor(validateThat("at.etd.end.balance", xpath), assetHoldingSummaryDetail.getEndBalance());
        }
        if (assetHoldingSummaryDetail.getOTE() != null) {
            validateThat("at.etd.ote", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getOTE().getRealValue())));
            assertColor(validateThat("at.etd.ote", xpath), assetHoldingSummaryDetail.getOTE());
        }
        if (assetHoldingSummaryDetail.getTE() != null) {
            validateThat("at.etd.te", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getTE().getRealValue())));
            assertColor(validateThat("at.etd.te", xpath), assetHoldingSummaryDetail.getTE());
        }
        if (assetHoldingSummaryDetail.getNLV() != null) {
            validateThat("at.etd.nlv", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getNLV().getRealValue())));
            assertColor(validateThat("at.etd.nlv", xpath), assetHoldingSummaryDetail.getNLV());
        }
        if (assetHoldingSummaryDetail.getNOV() != null) {
            validateThat("at.etd.nov", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getNOV().getRealValue())));
            assertColor(validateThat("at.etd.nov", xpath), assetHoldingSummaryDetail.getNOV());
        }
        if (assetHoldingSummaryDetail.getIMR() != null) {
            validateThat("at.etd.imr", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getIMR().getRealValue())));
            assertColor(validateThat("at.etd.imr", xpath), assetHoldingSummaryDetail.getIMR());
        }
        if (assetHoldingSummaryDetail.getMMR() != null) {
            validateThat("at.etd.mmr", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getMMR().getRealValue())));
            assertColor(validateThat("at.etd.mmr", xpath), assetHoldingSummaryDetail.getMMR());
        }
        if (assetHoldingSummaryDetail.getImCollateral() != null) {
            validateThat("at.etd.im.collateral", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getImCollateral().getRealValue())));
            assertColor(validateThat("at.etd.im.collateral", xpath), assetHoldingSummaryDetail.getImCollateral());
        }
        if (assetHoldingSummaryDetail.getCashflow() != null) {
            validateThat("at.etd.cashflow", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getCashflow().getRealValue())));
            assertColor(validateThat("at.etd.cashflow", xpath), assetHoldingSummaryDetail.getCashflow());
        }
        if (assetHoldingSummaryDetail.getExcessOrDeficit() != null) {
            validateThat("at.etd.excess.deficit", xpath).innerText().isEqualToIgnoringWhitespace(
                   format.format(Double.parseDouble(assetHoldingSummaryDetail.getExcessOrDeficit().getRealValue())));
            assertColor(validateThat("at.etd.excess.deficit", xpath), assetHoldingSummaryDetail.getExcessOrDeficit());
        }
        if (assetHoldingSummaryDetail.getPendingExcessOrDeficit() != null) {
            validateThat("at.etd.pending.excess.deficit", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getPendingExcessOrDeficit().getRealValue())));
            assertColor(validateThat("at.etd.pending.excess.deficit", xpath), assetHoldingSummaryDetail.getPendingExcessOrDeficit());
        }
        if (assetHoldingSummaryDetail.getRegMoves() != null) {
            validateThat("at.etd.reg.moves", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getRegMoves().getRealValue())));
            assertColor(validateThat("at.etd.reg.moves", xpath), assetHoldingSummaryDetail.getRegMoves());
        }
        if (assetHoldingSummaryDetail.getDFR() != null) {
            validateThat("at.etd.dfr", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getDFR().getRealValue())));
            assertColor(validateThat("at.etd.dfr", xpath), assetHoldingSummaryDetail.getDFR());
        }
        if (assetHoldingSummaryDetail.getDfCollateral() != null) {
            validateThat("at.etd.df.collateral", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getDfCollateral().getRealValue())));
            assertColor(validateThat("at.etd.df.collateral", xpath), assetHoldingSummaryDetail.getDfCollateral());
        }
        if (assetHoldingSummaryDetail.getDfExcessOrDeficit() != null) {
            validateThat("at.etd.df.excess.deficit", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getDfExcessOrDeficit().getRealValue())));
            assertColor(validateThat("at.etd.df.excess.deficit", xpath), assetHoldingSummaryDetail.getDfExcessOrDeficit());
        }
        if (assetHoldingSummaryDetail.getDfPendingExcessOrDeficit() != null) {
            validateThat("at.etd.df.pending.excess.deficit", xpath).innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(assetHoldingSummaryDetail.getDfPendingExcessOrDeficit().getRealValue())));
            assertColor(validateThat("at.etd.df.pending.excess.deficit", xpath), assetHoldingSummaryDetail.getDfPendingExcessOrDeficit());
        }
    }

    private void assertColor(IWebDriverWrapper.IFluentLocatorAssertion validate, StringType stringType) {
        String amberPattern = "^(.*)color:( rgb\\(242, 182, 0\\))(.*)$";
        String purplePattern = "^(.*)color:( rgb\\(127, 0, 255\\))(.*)$";
        if (stringType.getVariation() != null) {
            switch (stringType.getVariation().toUpperCase()) {
                case "AMBER":
                    validate.attributeValueOf("style").matches(amberPattern);
                    break;
                case "PURPLE":
                    validate.attributeValueOf("style").matches(purplePattern);
                    break;
                case "DEFAULT":
                    validate.attributeValueOf("style").doesNotMatch(amberPattern);
                    validate.attributeValueOf("style").doesNotMatch(purplePattern);
                    break;
            }
        }
    }

    private void assertAgreementAdmin(AgreementAdmin agreementAdmin) throws Exception {
        if (agreementAdmin.getSpecialReminders() != null)
            assertThat("at.aa.specirem").innerText().isEqualToIgnoringWhitespace(
                    agreementAdmin.getSpecialReminders().getRealValue());
        if (agreementAdmin.getAgreementType() != null)
            assertThat("at.aa.agrtype").innerText().isEqualToIgnoringWhitespace(
                    agreementAdmin.getAgreementType().getRealValue());
        if (agreementAdmin.getValuationFrequency() != null)
            assertThat("at.aa.valfreq").innerText().isEqualToIgnoringWhitespace(
                    agreementAdmin.getValuationFrequency().getRealValue());
        if (agreementAdmin.getReciprocity() != null)
            assertThat("at.aa.recip").innerText().isEqualToIgnoringWhitespace(
                    agreementAdmin.getReciprocity().getRealValue());
        if (agreementAdmin.getMarginType() != null)
            assertThat("at.aa.margtype").innerText().isEqualToIgnoringWhitespace(
                    agreementAdmin.getMarginType().getRealValue());
        if (agreementAdmin.getAgreementStatus() != null && !agreementAdmin.getAgreementStatus().isEmpty())
            assertThat("at.agmt.status").selectedText().isEqualTo(
                    agreementAdmin.getAgreementStatus().get(agreementAdmin.getAgreementStatus().size() - 1).value());
        if (agreementAdmin.getStatementStatus() != null && !agreementAdmin.getStatementStatus().isEmpty())
            assertThat("at.stmt.status").selectedText().isEqualTo(
                    agreementAdmin.getStatementStatus().get(agreementAdmin.getStatementStatus().size() - 1).value());
    }

    private void assertMarginCallCalculation(MarginCallCalculation marginCallCalculation) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if (marginCallCalculation.getOtcOrClearingReq() != null) {
            assertMarginCallCalculationOTCAndClearing(marginCallCalculation.getOtcOrClearingReq(), format);
        } else if (marginCallCalculation.getFcmReq() != null) {
            assertFCMMarginCallCalculation(marginCallCalculation.getFcmReq(), format);
        } else if (marginCallCalculation.getRepoReq() != null) {
            assertRepoMarginCallCalculation(marginCallCalculation.getRepoReq(), format);
        } else if (marginCallCalculation.getEtdReq() != null)
            assertETDMarginCallCalculation(marginCallCalculation.getEtdReq(), format);
    }

    private void assertETDMarginCallCalculation(MarginCallCalculationETDReq etdReq, DecimalFormat format) throws Exception {
        if (etdReq.getSod() != null) {
            MarginCallCalculationETDSod sod = etdReq.getSod();
            if (sod.getCoupon() != null) {
                validateThat("at.mcc.etd.coupon.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getCoupon().getRealValue())));
                assertColor(validateThat("at.mcc.etd.coupon.sod"), sod.getCoupon());
            }
            if (sod.getPai() != null) {
                validateThat("at.mcc.etd.pai.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getPai().getRealValue())));
                assertColor(validateThat("at.mcc.etd.pai.sod"), sod.getPai());
            }
            if (sod.getUpfrontFee() != null) {
                validateThat("at.mcc.etd.upfront.fee.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getUpfrontFee().getRealValue())));
                assertColor(validateThat("at.mcc.etd.upfront.fee.sod"), sod.getUpfrontFee());
            }
            if (sod.getRealisedPAndLCleared() != null) {
                validateThat("at.mcc.etd.realised.cleared.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getRealisedPAndLCleared().getRealValue())));
                assertColor(validateThat("at.mcc.etd.realised.cleared.sod"), sod.getRealisedPAndLCleared());
            }
            if (sod.getUnrealisedPAndLCleared() != null) {
                validateThat("at.mcc.etd.unrealised.cleared.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getUnrealisedPAndLCleared().getRealValue())));
                assertColor(validateThat("at.mcc.etd.unrealised.cleared.sod"), sod.getUnrealisedPAndLCleared());
            }
            if (sod.getTotalFeesCleared() != null) {
                validateThat("at.mcc.etd.total.fees.cleared.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getTotalFeesCleared().getRealValue())));
                assertColor(validateThat("at.mcc.etd.total.fees.cleared.sod"), sod.getTotalFeesCleared());
            }
            if (sod.getCashflow() != null) {
                validateThat("at.mcc.etd.cashflow.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getCashflow().getRealValue())));
                assertColor(validateThat("at.mcc.etd.cashflow.sod"), sod.getCashflow());
            }
            if (sod.getNetPAndLCleared() != null) {
                validateThat("at.mcc.etd.net.pl.cleared.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getNetPAndLCleared().getRealValue())));
                assertColor(validateThat("at.mcc.etd.net.pl.cleared.sod"), sod.getNetPAndLCleared());
            }
            if (sod.getRealisedPAndLETD() != null) {
                validateThat("at.mcc.etd.realised.etd.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getRealisedPAndLETD().getRealValue())));
                assertColor(validateThat("at.mcc.etd.realised.etd.sod"), sod.getRealisedPAndLETD());
            }
            if (sod.getTotalNetPAndL() != null) {
                validateThat("at.mcc.etd.total.net.pl.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getTotalNetPAndL().getRealValue())));
                assertColor(validateThat("at.mcc.etd.total.net.pl.sod"), sod.getTotalNetPAndL());
            }
            if (sod.getOpeningBalance() != null) {
                validateThat("at.mcc.etd.opening.balance.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getOpeningBalance().getRealValue())));
                assertColor(validateThat("at.mcc.etd.opening.balance.sod"), sod.getOpeningBalance());
            }
            if (sod.getCommissionAndFees() != null) {
                validateThat("at.mcc.etd.commissions.and.fees.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getCommissionAndFees().getRealValue())));
                assertColor(validateThat("at.mcc.etd.commissions.and.fees.sod"), sod.getCommissionAndFees());
            }
            if (sod.getOptionPremium() != null) {
                validateThat("at.mcc.etd.option.premium.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getOptionPremium().getRealValue())));
                assertColor(validateThat("at.mcc.etd.option.premium.sod"), sod.getOptionPremium());
            }
            if (sod.getCashAmounts() != null) {
                validateThat("at.mcc.etd.cash.amounts.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getCashAmounts().getRealValue())));
                assertColor(validateThat("at.mcc.etd.cash.amounts.sod"), sod.getCashAmounts());
            }
            if (sod.getEndingBalance() != null) {
                validateThat("at.mcc.etd.ending.balance.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getEndingBalance().getRealValue())));
                assertColor(validateThat("at.mcc.etd.ending.balance.sod"), sod.getEndingBalance());
            }
            if (sod.getOpenTradeEquityOTE() != null) {
                validateThat("at.mcc.etd.ote.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getOpenTradeEquityOTE().getRealValue())));
                assertColor(validateThat("at.mcc.etd.ote.sod"), sod.getOpenTradeEquityOTE());
            }
            if (sod.getTotalEquityTE() != null) {
                validateThat("at.mcc.etd.te.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getTotalEquityTE().getRealValue())));
                assertColor(validateThat("at.mcc.etd.te.sod"), sod.getTotalEquityTE());
            }
            if (sod.getLongOptionValueLOV() != null) {
                validateThat("at.mcc.etd.lov.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getLongOptionValueLOV().getRealValue())));
                assertColor(validateThat("at.mcc.etd.lov.sod"), sod.getLongOptionValueLOV());
            }
            if (sod.getShortOptionValueSOV() != null) {
                validateThat("at.mcc.etd.sov.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getShortOptionValueSOV().getRealValue())));
                assertColor(validateThat("at.mcc.etd.sov.sod"), sod.getShortOptionValueSOV());
            }
            if (sod.getNetOptionValueNOV() != null) {
                validateThat("at.mcc.etd.nov.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getNetOptionValueNOV().getRealValue())));
                assertColor(validateThat("at.mcc.etd.nov.sod"), sod.getNetOptionValueNOV());
            }
            if (sod.getNetLiquidatingValueNLV() != null) {
                validateThat("at.mcc.etd.nlv.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getNetLiquidatingValueNLV().getRealValue())));
                assertColor(validateThat("at.mcc.etd.nlv.sod"), sod.getNetLiquidatingValueNLV());
            }
            if (sod.getInitialMarginRequirementIMR() != null) {
                validateThat("at.mcc.etd.imr.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getInitialMarginRequirementIMR().getRealValue())));
                assertColor(validateThat("at.mcc.etd.imr.sod"), sod.getInitialMarginRequirementIMR());
            }
            if (sod.getMaintenanceMarginRequirementMMR() != null) {
                validateThat("at.mcc.etd.mmr.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getMaintenanceMarginRequirementMMR().getRealValue())));
                assertColor(validateThat("at.mcc.etd.mmr.sod"), sod.getMaintenanceMarginRequirementMMR());
            }
            if (sod.getImSecuritiesBalance() != null) {
                validateThat("at.mcc.etd.im.securities.balance.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getImSecuritiesBalance().getRealValue())));
                assertColor(validateThat("at.mcc.etd.im.securities.balance.sod"), sod.getImSecuritiesBalance());
            }
            if (sod.getImCashBalance() != null) {
                validateThat("at.mcc.etd.im.cash.balance.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getImCashBalance().getRealValue())));
                assertColor(validateThat("at.mcc.etd.im.cash.balance.sod"), sod.getImCashBalance());
            }
            if (sod.getImDCVM() != null) {
                validateThat("at.mcc.etd.im.dcvm.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getImDCVM().getRealValue())));
                assertColor(validateThat("at.mcc.etd.im.dcvm.sod"), sod.getImDCVM());
            }
            if (sod.getExcessOrDeficit() != null) {
                validateThat("at.mcc.etd.excess.deficit.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getExcessOrDeficit().getRealValue())));
                assertColor(validateThat("at.mcc.etd.excess.deficit.sod"), sod.getExcessOrDeficit());
            }
            if (sod.getDefaultFundRequirement() != null) {
                validateThat("at.mcc.etd.default.fund.requirement.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getDefaultFundRequirement().getRealValue())));
                assertColor(validateThat("at.mcc.etd.default.fund.requirement.sod"), sod.getDefaultFundRequirement());
            }
            if (sod.getDfSecuritiesBalance() != null) {
                validateThat("at.mcc.etd.df.securities.balance.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getDfSecuritiesBalance().getRealValue())));
                assertColor(validateThat("at.mcc.etd.df.securities.balance.sod"), sod.getDfSecuritiesBalance());
            }
            if (sod.getDfCashBalance() != null) {
                validateThat("at.mcc.etd.df.cash.balance.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getDfCashBalance().getRealValue())));
                assertColor(validateThat("at.mcc.etd.df.cash.balance.sod"), sod.getDfCashBalance());
            }
            if (sod.getDfExcessOrDeficit() != null) {
                validateThat("at.mcc.etd.df.excess.deficit.sod").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(sod.getDfExcessOrDeficit().getRealValue())));
                assertColor(validateThat("at.mcc.etd.df.excess.deficit.sod"), sod.getDfExcessOrDeficit());
            }
        }
        if (etdReq.getAdj() != null) {
            MarginCallCalculationETDAdj adj = etdReq.getAdj();
            if (adj.getCoupon() != null && adj.getCoupon().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.coupon.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(adj.getCoupon().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.coupon.adj"), adj.getCoupon().getAdjustmentAmount());
            }
            if (adj.getPai() != null && adj.getPai().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.pai.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(adj.getPai().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.pai.adj"), adj.getPai().getAdjustmentAmount());
            }
            if (adj.getUpfrontFee() != null && adj.getUpfrontFee().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.upfront.fee.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(adj.getUpfrontFee().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.upfront.fee.adj"), adj.getUpfrontFee().getAdjustmentAmount());
            }
            if (adj.getRealisedPAndLCleared() != null && adj.getRealisedPAndLCleared().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.realised.cleared.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(
                                adj.getRealisedPAndLCleared().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.realised.cleared.adj"), adj.getRealisedPAndLCleared().getAdjustmentAmount());
            }
            if (adj.getUnrealisedPAndLCleared() != null && adj.getUnrealisedPAndLCleared().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.unrealised.cleared.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(
                                adj.getUnrealisedPAndLCleared().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.unrealised.cleared.adj"), adj.getUnrealisedPAndLCleared().getAdjustmentAmount());
            }
            if (adj.getTotalFeesCleared() != null && adj.getTotalFeesCleared().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.total.fees.cleared.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(adj.getTotalFeesCleared().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.total.fees.cleared.adj"), adj.getTotalFeesCleared().getAdjustmentAmount());
            }
            if (adj.getCashflow() != null && adj.getCashflow().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.cashflow.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(adj.getCashflow().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.cashflow.adj"), adj.getCashflow().getAdjustmentAmount());
            }
            if (adj.getNetPAndLCleared() != null && adj.getNetPAndLCleared().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.net.pl.cleared.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(adj.getNetPAndLCleared().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.net.pl.cleared.adj"), adj.getNetPAndLCleared().getAdjustmentAmount());
            }
            if (adj.getRealisedPAndLETD() != null && adj.getRealisedPAndLETD().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.realised.etd.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(adj.getRealisedPAndLETD().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.realised.etd.adj"), adj.getRealisedPAndLETD().getAdjustmentAmount());
            }
            if (adj.getTotalNetPAndL() != null && adj.getTotalNetPAndL().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.total.net.pl.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(adj.getTotalNetPAndL().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.total.net.pl.adj"), adj.getTotalNetPAndL().getAdjustmentAmount());
            }
            if (adj.getOpeningBalance() != null && adj.getOpeningBalance().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.opening.balance.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(adj.getOpeningBalance().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.opening.balance.adj"), adj.getOpeningBalance().getAdjustmentAmount());
            }
            if (adj.getCommissionAndFees() != null && adj.getCommissionAndFees().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.commissions.and.fees.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(adj.getCommissionAndFees().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.commissions.and.fees.adj"), adj.getCommissionAndFees().getAdjustmentAmount());
            }
            if (adj.getOptionPremium() != null && adj.getOptionPremium().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.option.premium.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(adj.getOptionPremium().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.option.premium.adj"), adj.getOptionPremium().getAdjustmentAmount());
            }
            if (adj.getCashAmounts() != null && adj.getCashAmounts().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.cash.amounts.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(adj.getCashAmounts().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.cash.amounts.adj"), adj.getCashAmounts().getAdjustmentAmount());
            }
            if (adj.getEndingBalance() != null && adj.getEndingBalance().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.ending.balance.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(adj.getEndingBalance().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.ending.balance.adj"), adj.getEndingBalance().getAdjustmentAmount());
            }
            if (adj.getOpenTradeEquityOTE() != null && adj.getOpenTradeEquityOTE().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.ote.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(adj.getOpenTradeEquityOTE().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.ote.adj"), adj.getOpenTradeEquityOTE().getAdjustmentAmount());
            }
            if (adj.getTotalEquityTE() != null && adj.getTotalEquityTE().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.te.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(adj.getTotalEquityTE().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.te.adj"), adj.getTotalEquityTE().getAdjustmentAmount());
            }
            if (adj.getLongOptionValueLOV() != null && adj.getLongOptionValueLOV().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.lov.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(adj.getLongOptionValueLOV().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.lov.adj"), adj.getLongOptionValueLOV().getAdjustmentAmount());
            }
            if (adj.getShortOptionValueSOV() != null && adj.getShortOptionValueSOV().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.sov.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(adj.getShortOptionValueSOV().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.sov.adj"), adj.getShortOptionValueSOV().getAdjustmentAmount());
            }
            if (adj.getNetOptionValueNOV() != null && adj.getNetOptionValueNOV().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.nov.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(adj.getNetOptionValueNOV().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.nov.adj"), adj.getNetOptionValueNOV().getAdjustmentAmount());
            }
            if (adj.getNetLiquidatingValueNLV() != null && adj.getNetLiquidatingValueNLV().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.nlv.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(
                                adj.getNetLiquidatingValueNLV().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.nlv.adj"), adj.getNetLiquidatingValueNLV().getAdjustmentAmount());
            }
            if (adj.getInitialMarginRequirementIMR() != null && adj.getInitialMarginRequirementIMR().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.imr.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(
                                adj.getInitialMarginRequirementIMR().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.imr.adj"), adj.getInitialMarginRequirementIMR().getAdjustmentAmount());
            }
            if (adj.getMaintenanceMarginRequirementMMR() != null && adj.getMaintenanceMarginRequirementMMR().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.mmr.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(
                                adj.getMaintenanceMarginRequirementMMR().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.mmr.adj"), adj.getMaintenanceMarginRequirementMMR().getAdjustmentAmount());
            }
            if (adj.getImSecuritiesBalance() != null && adj.getImSecuritiesBalance().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.im.securities.balance.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(adj.getImSecuritiesBalance().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.im.securities.balance.adj"), adj.getImSecuritiesBalance().getAdjustmentAmount());
            }
            if (adj.getImCashBalance() != null && adj.getImCashBalance().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.im.cash.balance.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(adj.getImCashBalance().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.im.cash.balance.adj"), adj.getImCashBalance().getAdjustmentAmount());
            }
            if (adj.getImDCVM() != null && adj.getImDCVM().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.im.dcvm.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(adj.getImDCVM().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.im.dcvm.adj"), adj.getImDCVM().getAdjustmentAmount());
            }
            if (adj.getExcessOrDeficit() != null && adj.getExcessOrDeficit().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.excess.deficit.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(adj.getExcessOrDeficit().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.excess.deficit.adj"), adj.getExcessOrDeficit().getAdjustmentAmount());
            }
            if (adj.getDefaultFundRequirement() != null && adj.getDefaultFundRequirement().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.default.fund.requirement.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(
                                adj.getDefaultFundRequirement().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.default.fund.requirement.adj"), adj.getDefaultFundRequirement().getAdjustmentAmount());
            }
            if (adj.getDfSecuritiesBalance() != null && adj.getDfSecuritiesBalance().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.df.securities.balance.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(adj.getDfSecuritiesBalance().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.df.securities.balance.adj"), adj.getDfSecuritiesBalance().getAdjustmentAmount());
            }
            if (adj.getDfCashBalance() != null && adj.getDfCashBalance().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.df.cash.balance.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(adj.getDfCashBalance().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.df.cash.balance.adj"), adj.getDfCashBalance().getAdjustmentAmount());
            }
            if (adj.getDfExcessOrDeficit() != null && adj.getDfExcessOrDeficit().getAdjustmentAmount() != null) {
                validateThat("at.mcc.etd.df.excess.deficit.adj").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(adj.getDfExcessOrDeficit().getAdjustmentAmount().getRealValue())));
                assertColor(validateThat("at.mcc.etd.df.excess.deficit.adj"), adj.getDfExcessOrDeficit().getAdjustmentAmount());
            }
        }
        if (etdReq.getPendingExcessOrDeficit() != null) {
            validateThat("at.mcc.etd.pending.excess.deficit").innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(etdReq.getPendingExcessOrDeficit().getRealValue())));
            assertColor(validateThat("at.mcc.etd.pending.excess.deficit"), etdReq.getPendingExcessOrDeficit());
        }
        if (etdReq.getImSecuritiesMarginExcessOrDeficit() != null) {
            validateThat("at.mcc.etd.im.securit.margin.excess.deficit").innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(etdReq.getImSecuritiesMarginExcessOrDeficit().getRealValue())));
            assertColor(validateThat("at.mcc.etd.im.securit.margin.excess.deficit"), etdReq.getImSecuritiesMarginExcessOrDeficit());
        }
        if (etdReq.getImCashMarginExcessOrDeficit() != null) {
            validateThat("at.mcc.etd.im.cash.margin.excess.deficit").innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(etdReq.getImCashMarginExcessOrDeficit().getRealValue())));
            assertColor(validateThat("at.mcc.etd.im.cash.margin.excess.deficit"),
                    etdReq.getImCashMarginExcessOrDeficit());
        }
        if (etdReq.getCurrentExcessOrDeficit() != null) {
            validateThat("at.mcc.etd.current.excess.deficit").innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(etdReq.getCurrentExcessOrDeficit().getRealValue())));
            assertColor(validateThat("at.mcc.etd.current.excess.deficit"), etdReq.getCurrentExcessOrDeficit());
        }
        if (etdReq.getDfPendingExcessOrDeficit() != null) {
            validateThat("at.mcc.etd.df.pending.excess.deficit").innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(etdReq.getDfPendingExcessOrDeficit().getRealValue())));
            assertColor(validateThat("at.mcc.etd.df.pending.excess.deficit"), etdReq.getDfPendingExcessOrDeficit());
        }
        if (etdReq.getDfCurrentExcessOrDeficit() != null) {
            validateThat("at.mcc.etd.df.current.excess.deficit").innerText().isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(etdReq.getDfCurrentExcessOrDeficit().getRealValue())));
            assertColor(validateThat("at.mcc.etd.df.current.excess.deficit"), etdReq.getDfCurrentExcessOrDeficit());
        }
        assertETDStatementUDF(etdReq, format);
    }

    private void assertETDStatementUDF(MarginCallCalculationETDReq etdReq, DecimalFormat format) throws Exception {
        if (etdReq.getSod() != null && etdReq.getSod().getEtdStatementUdf() != null) {
            for (EtdStatementUdf udf : etdReq.getSod().getEtdStatementUdf()) {
                validateThat("at.mcc.etd.udf.sod", udf.getUdfName().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(udf.getUdfValue().getRealValue())));
            }
        }
        if (etdReq.getAdj() != null && etdReq.getAdj().getEtdStatementUdf() != null) {
            for (EtdStatementUdf udf : etdReq.getAdj().getEtdStatementUdf()) {
                validateThat("at.mcc.etd.udf.adj", udf.getUdfName().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(udf.getUdfValue().getRealValue())));
            }
        }
    }

    private void assertMarginCallCalculationOTCAndClearing(MarginCallCalculationOTCAndClearing otcAndClearing, DecimalFormat format) throws Exception {
        if (otcAndClearing.getPrincipalReq() != null) {
            MarginCallCalculationPartyReq principalReq = otcAndClearing.getPrincipalReq();
            if (principalReq.getMtmExposure() != null)
                validateThat("at.mcc.mtmexpo.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getMtmExposure().getRealValue());
            if (principalReq.getExposure() != null)
                validateThat("at.mcc.expo.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getExposure().getRealValue());
            if (principalReq.getMarkToMarket() != null)
                validateThat("at.mcc.mtm.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getMarkToMarket().getRealValue());
            if (principalReq.getUpfrontFee() != null)
                validateThat("at.mcc.upfrontfee.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getUpfrontFee().getRealValue());
            if (principalReq.getDealLevelIA() != null)
                validateThat("at.mcc.dealevlia.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getDealLevelIA().getRealValue());
            if (principalReq.getPortfolioIA() != null)
                validateThat("at.mcc.protfolioia.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getPortfolioIA().getRealValue());
            if (principalReq.getAgreementIA() != null)
                validateThat("at.mcc.agria.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getAgreementIA().getRealValue());
            if (principalReq.getTotalExposureAmount() != null)
                validateThat("at.mcc.tolexpoamt.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getTotalExposureAmount().getRealValue());
            if (principalReq.getPrincipalThreshold() != null)
                validateThat("at.mcc.printhreshold.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getPrincipalThreshold().getRealValue());
            if (principalReq.getPrincipalIMThreshold() != null)
                validateThat("at.mcc.prinIMthreshold.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getPrincipalIMThreshold().getRealValue());
            if (principalReq.getCounterpartyThreshold() != null)
                validateThat("at.mcc.cptythreshold.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getCounterpartyThreshold().getRealValue());
            if (principalReq.getCounterpartyIMThreshold() != null)
                validateThat("at.mcc.cptyIMthreshold.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getCounterpartyIMThreshold().getRealValue());
            if (principalReq.getAdjustedExposureAmount() != null)
                validateThat("at.mcc.adjexpoamt.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getAdjustedExposureAmount().getRealValue());
            if (principalReq.getValueofConfirmedCollateralHeld() != null)
                validateThat("at.mcc.value.cfcolheld.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getValueofConfirmedCollateralHeld().getRealValue());
            if (principalReq.getValueofPendingCollateralHeld() != null)
                validateThat("at.mcc.value.pdcolheld.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getValueofPendingCollateralHeld().getRealValue());
            if (principalReq.getValueofConfirmedCollateralDelivered() != null)
                validateThat("at.mcc.value.cfcoldeli.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getValueofConfirmedCollateralDelivered().getRealValue());
            if (principalReq.getValueofPendingCollateralDelivered() != null)
                validateThat("at.mcc.value.pdcoldeli.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getValueofPendingCollateralDelivered().getRealValue());
            if (principalReq.getNetMarginRequirement() != null)
                validateThat("at.mcc.netmargreq.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getNetMarginRequirement().getRealValue());
            if (principalReq.getDeliveryAmount() != null)
                validateThat("at.mcc.deliamt.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getDeliveryAmount().getRealValue());
            if (principalReq.getRecallAmount() != null)
                validateThat("at.mcc.recallamt.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getRecallAmount().getRealValue());
            if (principalReq.getIaDeliveryAmount() != null)
                validateThat("at.mcc.iadeliamt.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getIaDeliveryAmount().getRealValue());
            if (principalReq.getIaRecallAmount() != null)
                validateThat("at.mcc.iarecallamt.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getIaRecallAmount().getRealValue());
            if (principalReq.getLockupMarginRequired() != null)
                validateThat("at.mcc.lkupmargreq.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getLockupMarginRequired().getRealValue());
            if (principalReq.getCurrentValueofLockupMargin() != null)
                validateThat("at.mcc.cntvallkupmarg.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getCurrentValueofLockupMargin().getRealValue());
            if (principalReq.getLockupMarginCall() != null)
                validateThat("at.mcc.lkupmargcall.prin").innerText().isEqualToIgnoringWhitespace(
                        principalReq.getLockupMarginCall().getRealValue());
        }
        if (otcAndClearing.getCounterpartyReq() != null) {
            MarginCallCalculationPartyReq counterpartyReq = otcAndClearing.getCounterpartyReq();
            if (counterpartyReq.getMtmExposure() != null)
                validateThat("at.mcc.mtmexpo.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getMtmExposure().getRealValue());
            if (counterpartyReq.getExposure() != null)
                validateThat("at.mcc.expo.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getExposure().getRealValue());
            if (counterpartyReq.getMarkToMarket() != null)
                validateThat("at.mcc.mtm.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getMarkToMarket().getRealValue());
            if (counterpartyReq.getUpfrontFee() != null)
                validateThat("at.mcc.upfrontfee.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getUpfrontFee().getRealValue());
            if (counterpartyReq.getDealLevelIA() != null)
                validateThat("at.mcc.dealevlia.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getDealLevelIA().getRealValue());
            if (counterpartyReq.getPortfolioIA() != null)
                validateThat("at.mcc.protfolioia.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getPortfolioIA().getRealValue());
            if (counterpartyReq.getAgreementIA() != null)
                validateThat("at.mcc.agria.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getAgreementIA().getRealValue());
            if (counterpartyReq.getTotalExposureAmount() != null)
                validateThat("at.mcc.tolexpoamt.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getTotalExposureAmount().getRealValue());
            if (counterpartyReq.getPrincipalThreshold() != null)
                validateThat("at.mcc.printhreshold.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getPrincipalThreshold().getRealValue());
            if (counterpartyReq.getPrincipalIMThreshold() != null)
                validateThat("at.mcc.prinIMthreshold.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getPrincipalIMThreshold().getRealValue());
            if (counterpartyReq.getCounterpartyThreshold() != null)
                validateThat("at.mcc.cptythreshold.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getCounterpartyThreshold().getRealValue());
            if (counterpartyReq.getCounterpartyIMThreshold() != null)
                validateThat("at.mcc.cptyIMthreshold.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getCounterpartyIMThreshold().getRealValue());
            if (counterpartyReq.getAdjustedExposureAmount() != null)
                validateThat("at.mcc.adjexpoamt.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getAdjustedExposureAmount().getRealValue());
            if (counterpartyReq.getValueofConfirmedCollateralHeld() != null)
                validateThat("at.mcc.value.cfcolheld.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getValueofConfirmedCollateralHeld().getRealValue());
            if (counterpartyReq.getValueofPendingCollateralHeld() != null)
                validateThat("at.mcc.value.pdcolheld.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getValueofPendingCollateralHeld().getRealValue());
            if (counterpartyReq.getValueofConfirmedCollateralDelivered() != null)
                validateThat("at.mcc.value.cfcoldeli.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getValueofConfirmedCollateralDelivered().getRealValue());
            if (counterpartyReq.getValueofPendingCollateralDelivered() != null)
                validateThat("at.mcc.value.pdcoldeli.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getValueofPendingCollateralDelivered().getRealValue());
            if (counterpartyReq.getNetMarginRequirement() != null)
                validateThat("at.mcc.netmargreq.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getNetMarginRequirement().getRealValue());
            if (counterpartyReq.getDeliveryAmount() != null)
                validateThat("at.mcc.deliamt.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getDeliveryAmount().getRealValue());
            if (counterpartyReq.getRecallAmount() != null) {
                try {
                    validateThat("at.mcc.recallamt.cpty").innerText().isEqualToIgnoringWhitespace(
                            format.format(Double.parseDouble(counterpartyReq.getRecallAmount().getRealValue())));
                } catch (NumberFormatException nfe) {
                    validateThat("at.mcc.recallamt.cpty").innerText().isEqualToIgnoringWhitespace(
                            counterpartyReq.getRecallAmount().getRealValue());
                }
            }
            if (counterpartyReq.getIaDeliveryAmount() != null)
                validateThat("at.mcc.iadeliamt.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getIaDeliveryAmount().getRealValue());
            if (counterpartyReq.getIaRecallAmount() != null)
                validateThat("at.mcc.iarecallamt.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getIaRecallAmount().getRealValue());
            if (counterpartyReq.getLockupMarginRequired() != null)
                validateThat("at.mcc.lkupmargreq.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getLockupMarginRequired().getRealValue());
            if (counterpartyReq.getCurrentValueofLockupMargin() != null)
                validateThat("at.mcc.cntvallkupmarg.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getCurrentValueofLockupMargin().getRealValue());
            if (counterpartyReq.getLockupMarginCall() != null)
                validateThat("at.mcc.lkupmargcall.cpty").innerText().isEqualToIgnoringWhitespace(
                        counterpartyReq.getLockupMarginCall().getRealValue());
        }
    }

    private void assertFCMMarginCallCalculation(MarginCallCalculationFCM fcmReq, DecimalFormat format) throws Exception {
        if (fcmReq.getVmCashBalance() != null)
            if (fcmReq.getVmCashBalance().isApply() != null && !fcmReq.getVmCashBalance().isApply())
                validateThat("at.mcc.vm.cashbal").displayed().isFalse();
            else
                validateThat("at.mcc.vm.cashbal").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getVmCashBalance().getRealValue()))
                );
        if (fcmReq.getVmSecuritiesCollateral() != null)
            if (fcmReq.getVmSecuritiesCollateral().isApply() != null && !fcmReq.getVmSecuritiesCollateral().isApply())
                validateThat("at.mcc.vm.seccol").displayed().isFalse();
            else
                validateThat("at.mcc.vm.seccol").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getVmSecuritiesCollateral().getRealValue()))
                );
        if (fcmReq.getTotalSettlementAmount() != null)
            if (fcmReq.getTotalSettlementAmount().isApply() != null && !fcmReq.getTotalSettlementAmount().isApply())
                validateThat("at.mcc.tolsetamt").displayed().isFalse();
            else
                validateThat("at.mcc.tolsetamt").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getTotalSettlementAmount().getRealValue()))
                );
        if (fcmReq.getTotalSettlementAmountPhysical() != null)
            if (fcmReq.getTotalSettlementAmountPhysical().isApply() != null && !fcmReq.getTotalSettlementAmountPhysical().isApply())
                validateThat("at.mcc.tolsetamt.phy").displayed().isFalse();
            else
                validateThat("at.mcc.tolsetamt.phy").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getTotalSettlementAmountPhysical().getRealValue()))
                );
        if (fcmReq.getCashflow() != null)
            if (fcmReq.getCashflow().isApply() != null && !fcmReq.getCashflow().isApply())
                validateThat("at.mcc.tolsetamt").displayed().isFalse();
            else
                validateThat("at.mcc.tolsetamt").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getCashflow().getRealValue()))
                );
        if (fcmReq.getCashflowPhysical() != null)
            if (fcmReq.getCashflowPhysical().isApply() != null && !fcmReq.getCashflowPhysical().isApply())
                validateThat("at.mcc.tolsetamt.phy").displayed().isFalse();
            else
                validateThat("at.mcc.tolsetamt.phy").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getCashflowPhysical().getRealValue()))
                );
        if (fcmReq.getTsaPassthrough() != null)
            if (fcmReq.getTsaPassthrough().isApply() != null && !fcmReq.getTsaPassthrough().isApply())
                validateThat("at.mcc.tsa.passthrough").displayed().isFalse();
            else
                validateThat("at.mcc.tsa.passthrough").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getTsaPassthrough().getRealValue()))
                );
        if (fcmReq.getTotalResetToPar() != null)
            if (fcmReq.getTotalResetToPar().isApply() != null && !fcmReq.getTotalResetToPar().isApply())
                validateThat("at.mcc.tolresettopar").displayed().isFalse();
            else
                validateThat("at.mcc.tolresettopar").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getTotalResetToPar().getRealValue()))
                );
            if(fcmReq.getTotalResetToParPhysical() != null)
                if (fcmReq.getTotalResetToParPhysical().isApply()!=null && !fcmReq.getTotalResetToParPhysical().isApply())
                    validateThat("at.mcc.tolresettopar.phy").displayed().isFalse();
                else
                    validateThat("at.mcc.tolresettopar.phy").innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(fcmReq.getTotalResetToParPhysical().getRealValue()))
                    );
            if(fcmReq.getTotalInitialCoupon() != null)
                if (fcmReq.getTotalInitialCoupon().isApply()!=null && !fcmReq.getTotalInitialCoupon().isApply())
                    validateThat("at.mcc.tolinicoup").displayed().isFalse();
                else
                    validateThat("at.mcc.tolinicoup").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getTotalInitialCoupon().getRealValue()))
                );
            if(fcmReq.getTotalInitialCouponPhysical() != null)
                if (fcmReq.getTotalInitialCouponPhysical().isApply()!=null && !fcmReq.getTotalInitialCouponPhysical().isApply())
                    validateThat("at.mcc.tolinicoup.phy").displayed().isFalse();
                else
                    validateThat("at.mcc.tolinicoup.phy").innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(fcmReq.getTotalInitialCouponPhysical().getRealValue()))
                    );
            if(fcmReq.getOtherCashAdjustments() != null)
                if (fcmReq.getOtherCashAdjustments().isApply()!=null && !fcmReq.getOtherCashAdjustments().isApply())
                    validateThat("at.mcc.otcashadj").displayed().isFalse();
                else
                    validateThat("at.mcc.otcashadj").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getOtherCashAdjustments().getRealValue()))
                );
        if (fcmReq.getOtherCashAdjustmentsPhysical() != null)
            if (fcmReq.getOtherCashAdjustmentsPhysical().isApply() != null && !fcmReq.getOtherCashAdjustmentsPhysical().isApply())
                validateThat("at.mcc.otcashadj.phy").displayed().isFalse();
            else
                validateThat("at.mcc.otcashadj.phy").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getOtherCashAdjustmentsPhysical().getRealValue()))
                );
        if (fcmReq.getBankedCoupon() != null)
            if (fcmReq.getBankedCoupon().isApply() != null && !fcmReq.getBankedCoupon().isApply())
                validateThat("at.mcc.bankedcoup").displayed().isFalse();
            else
                validateThat("at.mcc.bankedcoup").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getBankedCoupon().getRealValue()))
                );
            if(fcmReq.getBankedCouponPhysical() != null)
                if (fcmReq.getBankedCouponPhysical().isApply()!=null && !fcmReq.getBankedCouponPhysical().isApply())
                    validateThat("at.mcc.bankedcoup.phy").displayed().isFalse();
                else
                    validateThat("at.mcc.bankedcoup.phy").innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(fcmReq.getBankedCouponPhysical().getRealValue()))
                    );
            if(fcmReq.getPai() != null)
                if (fcmReq.getPai().isApply()!=null && !fcmReq.getPai().isApply())
                    validateThat("at.mcc.pai").displayed().isFalse();
                else
                    validateThat("at.mcc.pai").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getPai().getRealValue()))
                );
            if(fcmReq.getPaiPhysical() != null)
                if (fcmReq.getPaiPhysical().isApply()!=null && !fcmReq.getPaiPhysical().isApply())
                    validateThat("at.mcc.pai.phy").displayed().isFalse();
                else
                    validateThat("at.mcc.pai.phy").innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(fcmReq.getPaiPhysical().getRealValue()))
                    );
            if(fcmReq.getImInterest() != null)
                if (fcmReq.getImInterest().isApply()!=null && !fcmReq.getImInterest().isApply())
                    validateThat("at.mcc.im.interest").displayed().isFalse();
                else
                    validateThat("at.mcc.im.interest").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getImInterest().getRealValue()))
                );
            if(fcmReq.getImInterestPhysical() != null)
                if (fcmReq.getImInterestPhysical().isApply()!=null && !fcmReq.getImInterestPhysical().isApply())
                    validateThat("at.mcc.im.interest.phy").displayed().isFalse();
                else
                    validateThat("at.mcc.im.interest.phy").innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(fcmReq.getImInterestPhysical().getRealValue()))
                    );
            if(fcmReq.getNdfCashflow() != null)
                if (fcmReq.getNdfCashflow().isApply()!=null && !fcmReq.getNdfCashflow().isApply())
                    validateThat("at.mcc.ndfcashflow").displayed().isFalse();
                else
                    validateThat("at.mcc.ndfcashflow").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getNdfCashflow().getRealValue()))
                );
            if(fcmReq.getNdfCashflowPhysical() != null)
                if (fcmReq.getNdfCashflowPhysical().isApply()!=null && !fcmReq.getNdfCashflowPhysical().isApply())
                    validateThat("at.mcc.ndfcashflow.phy").displayed().isFalse();
                else
                    validateThat("at.mcc.ndfcashflow.phy").innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(fcmReq.getNdfCashflowPhysical().getRealValue()))
                    );
            if(fcmReq.getUpfrontFee() != null)
                if (fcmReq.getUpfrontFee().isApply()!=null && !fcmReq.getUpfrontFee().isApply())
                    validateThat("at.mcc.upfrontfee").displayed().isFalse();
                else
                    validateThat("at.mcc.upfrontfee").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getUpfrontFee().getRealValue()))
                );

            if(fcmReq.getUpfrontFeePhysical() != null)
                if (fcmReq.getUpfrontFeePhysical().isApply()!=null && !fcmReq.getUpfrontFeePhysical().isApply())
                    validateThat("at.mcc.upfrontfee.phy").displayed().isFalse();
                else
                    validateThat("at.mcc.upfrontfee.phy").innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(fcmReq.getUpfrontFeePhysical().getRealValue()))
                    );
            if(fcmReq.getVariationMargin() != null)
                if (fcmReq.getVariationMargin().isApply()!=null && !fcmReq.getVariationMargin().isApply())
                    validateThat("at.mcc.varimarg").displayed().isFalse();
                else
                    validateThat("at.mcc.varimarg").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getVariationMargin().getRealValue()))
                );
        if (fcmReq.getMtmRequirement() != null)
            if (fcmReq.getMtmRequirement().isApply() != null && !fcmReq.getMtmRequirement().isApply())
                validateThat("at.mcc.mtmreq").displayed().isFalse();
            else
                validateThat("at.mcc.mtmreq").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getMtmRequirement().getRealValue()))
                );
        if (fcmReq.getAccruedCoupon() != null)
            if (fcmReq.getAccruedCoupon().isApply() != null && !fcmReq.getAccruedCoupon().isApply())
                validateThat("at.mcc.acccoup").displayed().isFalse();
            else
                validateThat("at.mcc.acccoup").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getAccruedCoupon().getRealValue()))
                );
        if (fcmReq.getUnsettledUpfrontFee() != null)
            if (fcmReq.getUnsettledUpfrontFee().isApply() != null && !fcmReq.getUnsettledUpfrontFee().isApply())
                validateThat("at.mcc.unsettled.upfront.fee").displayed().isFalse();
            else
                validateThat("at.mcc.unsettled.upfront.fee").innerText().isEqualToIgnoringCase(
                        format.format(Float.parseFloat(fcmReq.getUnsettledUpfrontFee().getRealValue()))
                );
        if (fcmReq.getVmNetExcessOrDeficit() != null)
            if (fcmReq.getVmNetExcessOrDeficit().isApply() != null && !fcmReq.getVmNetExcessOrDeficit().isApply())
                validateThat("at.mcc.vm.netexceordef").displayed().isFalse();
            else
                validateThat("at.mcc.vm.netexceordef").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getVmNetExcessOrDeficit().getRealValue()))
                );
        if (fcmReq.getImSecuritiesCollateral() != null)
            if (fcmReq.getImSecuritiesCollateral().isApply() != null && !fcmReq.getImSecuritiesCollateral().isApply())
                validateThat("at.mcc.im.secucol").displayed().isFalse();
            else
                validateThat("at.mcc.im.secucol").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getImSecuritiesCollateral().getRealValue()))
                );
        if (fcmReq.getImCashBalance() != null)
            if (fcmReq.getImCashBalance().isApply() != null && !fcmReq.getImCashBalance().isApply())
                validateThat("at.mcc.im.cashbal").displayed().isFalse();
            else
                validateThat("at.mcc.im.cashbal").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getImCashBalance().getRealValue()))
                );
        if (fcmReq.getImRequirement() != null)
            if (fcmReq.getImRequirement().isApply() != null && !fcmReq.getImRequirement().isApply())
                validateThat("at.mcc.im.req").displayed().isFalse();
            else
                validateThat("at.mcc.im.req").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getImRequirement().getRealValue()))
                );
        if (fcmReq.getImNetExcessOrDeficit() != null)
            if (fcmReq.getImNetExcessOrDeficit().isApply() != null && !fcmReq.getImNetExcessOrDeficit().isApply())
                validateThat("at.mcc.im.netexceordef").displayed().isFalse();
            else
                validateThat("at.mcc.im.netexceordef").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getImNetExcessOrDeficit().getRealValue()))
                );
        if (fcmReq.getCashAvailableForNetExcess() != null)
            if (fcmReq.getCashAvailableForNetExcess().isApply() != null && !fcmReq.getCashAvailableForNetExcess().isApply())
                validateThat("at.mcc.cashavafornetexce").displayed().isFalse();
            else
                validateThat("at.mcc.cashavafornetexce").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getCashAvailableForNetExcess().getRealValue()))
                );
        if (fcmReq.getNetExcessOrDeficit() != null)
            if (fcmReq.getNetExcessOrDeficit().isApply() != null && !fcmReq.getNetExcessOrDeficit().isApply())
                validateThat("at.mcc.netexceordef").displayed().isFalse();
            else
                validateThat("at.mcc.netexceordef").innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(fcmReq.getNetExcessOrDeficit().getRealValue()))
                );
    }

    private void assertRepoMarginCallCalculation(MarginCallCalculationRepo repoReq, DecimalFormat format) throws Exception {
        if (repoReq.getNetSysCalcSecurityLeg() != null)
            validateThat("at.mcc.netsyscalcseculeg").innerText().isEqualToIgnoringWhitespace(
                    repoReq.getNetSysCalcSecurityLeg().getRealValue());
        if (repoReq.getNetSysCalcCashLeg() != null)
            validateThat("at.mcc.netsyscalccashleg").innerText().isEqualToIgnoringWhitespace(
                    repoReq.getNetSysCalcCashLeg().getRealValue());
        if (repoReq.getNetNonSysCalcValue() != null)
            validateThat("at.mcc.netsyscalcval").innerText().isEqualToIgnoringWhitespace(
                    repoReq.getNetNonSysCalcValue().getRealValue());
        if (repoReq.getNetFeeCollateralValue() != null)
            validateThat("at.mcc.netfeecolval").innerText().isEqualToIgnoringWhitespace(
                    repoReq.getNetFeeCollateralValue().getRealValue());
        if (repoReq.getNetMarginRequirement() != null)
            validateThat("at.mcc.netmargreq").innerText().isEqualToIgnoringWhitespace(
                    repoReq.getNetMarginRequirement().getRealValue());
        if (repoReq.getCallAmount() != null)
            validateThat("at.mcc.callamt").innerText().isEqualToIgnoringWhitespace(
                    repoReq.getCallAmount().getRealValue());
        if (repoReq.getRecallAmount() != null)
            validateThat("at.mcc.recallamt").innerText().isEqualToIgnoringWhitespace(
                    repoReq.getRecallAmount().getRealValue());
        if (repoReq.getDeliveryAmount() != null)
            validateThat("at.mcc.deliamt").innerText().isEqualToIgnoringWhitespace(
                    repoReq.getDeliveryAmount().getRealValue());
        if (repoReq.getReturnAmount() != null)
            validateThat("at.mcc.returnamt").innerText().isEqualToIgnoringWhitespace(
                    repoReq.getReturnAmount().getRealValue());
    }

    private void assertMarginCallSummary(MarginCallSummary marginCallSummary) throws Exception {
        if (marginCallSummary.getPrincipalMinimumTransferAmount() != null)
            assertThat(locator("at.mcs.prinminitransamt")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getPrincipalMinimumTransferAmount().getRealValue());
        if (marginCallSummary.getPrincipalIMMinimumTransferAmount() != null)
            assertThat(locator("at.mcs.prinIMminitransamt")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getPrincipalIMMinimumTransferAmount().getRealValue());
        if (marginCallSummary.getPrincipalRoundingAmount() != null)
            assertThat(locator("at.mcs.prinroundamt")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getPrincipalRoundingAmount().getRealValue());
        if (marginCallSummary.getPrincipalIMRoundingAmount() != null)
            assertThat(locator("at.mcs.prinIMroundamt")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getPrincipalIMRoundingAmount().getRealValue());
        if (marginCallSummary.getMarginToBeDeliveredToPrincipal() != null)
            assertThat(locator("at.mcs.mardeltoprin")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getMarginToBeDeliveredToPrincipal().getRealValue());
        if (marginCallSummary.getMarginToBeReturnedByPrincipal() != null)
            assertThat(locator("at.mcs.marretbyprin")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getMarginToBeReturnedByPrincipal().getRealValue());
        if (marginCallSummary.getIaToBeDeliveredToPrincipal() != null)
            assertThat(locator("at.mcs.iadeltoprin")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getIaToBeDeliveredToPrincipal().getRealValue());
        if (marginCallSummary.getIaToBeReturnedByPrincipal() != null)
            assertThat(locator("at.mcs.iaretbyprin")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getIaToBeReturnedByPrincipal().getRealValue());
        if (marginCallSummary.getLockupToBeDeliveredToPrincipal() != null)
            assertThat(locator("at.mcs.lkupdeltoprin")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getLockupToBeDeliveredToPrincipal().getRealValue());
        if (marginCallSummary.getLockupToBeReturnedByPrincipal() != null)
            assertThat(locator("at.mcs.lkupretbyprin")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getLockupToBeReturnedByPrincipal().getRealValue());
        if (marginCallSummary.getCounterpartyMinimumTransferAmount() != null)
            assertThat(locator("at.mcs.cptyminitransamt")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getCounterpartyMinimumTransferAmount().getRealValue());
        if (marginCallSummary.getCounterpartyIMMinimumTransferAmount() != null)
            assertThat(locator("at.mcs.cptyIMminitransamt")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getCounterpartyIMMinimumTransferAmount().getRealValue());
        if (marginCallSummary.getCounterpartyRoundingAmount() != null)
            assertThat(locator("at.mcs.cptyroundamt")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getCounterpartyRoundingAmount().getRealValue());
        if (marginCallSummary.getCounterpartyIMRoundingAmount() != null)
            assertThat(locator("at.mcs.cptyIMroundamt")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getCounterpartyIMRoundingAmount().getRealValue());
        if (marginCallSummary.getMarginToBeDeliveredToCounterparty() != null)
            assertThat(locator("at.mcs.mardeltocpty")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getMarginToBeDeliveredToCounterparty().getRealValue());
        if (marginCallSummary.getMarginToBeReturnedByCounterparty() != null)
            assertThat(locator("at.mcs.marretbycpty")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getMarginToBeReturnedByCounterparty().getRealValue());
        if (marginCallSummary.getIaToBeDeliveredToCounterparty() != null)
            assertThat(locator("at.mcs.iadeltocpty")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getIaToBeDeliveredToCounterparty().getRealValue());
        if (marginCallSummary.getIaToBeReturnedByCounterparty() != null)
            assertThat(locator("at.mcs.iaretbycpty")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getIaToBeReturnedByCounterparty().getRealValue());
        if (marginCallSummary.getLockupToBeDeliveredToCounterparty() != null)
            assertThat(locator("at.mcs.lkupdeltocpty")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getLockupToBeDeliveredToCounterparty().getRealValue());
        if (marginCallSummary.getLockupToBeReturnedByCounterparty() != null)
            assertThat(locator("at.mcs.lkupretbycpty")).innerText().isEqualToIgnoringWhitespace(
                    marginCallSummary.getLockupToBeReturnedByCounterparty().getRealValue());
    }

    private void assertAssetHoldingsSummary(AssetHoldingsSummary assetHoldingsSummary) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if (assetHoldingsSummary.getMktValueOfAssetsHeldByPrincipal() != null) {
            try {
                validateThat(locator("at.ah.mktvalofassetheld.prin")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(
                                assetHoldingsSummary.getMktValueOfAssetsHeldByPrincipal().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.mktvalofassetheld.prin")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getMktValueOfAssetsHeldByPrincipal().getRealValue());
            }
        }
        if (assetHoldingsSummary.getAdjustedCollateralValuePrincipal() != null) {
            try {
                validateThat(locator("at.ah.adjcolval.prin")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(
                                assetHoldingsSummary.getAdjustedCollateralValuePrincipal().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.adjcolval.prin")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getAdjustedCollateralValuePrincipal().getRealValue());
            }
        }
        if (assetHoldingsSummary.getIaMarketValueHeldByPrincipal() != null) {
            try {
                validateThat(locator("at.ah.iamktvalheld.prin")).innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Float.parseFloat(assetHoldingsSummary.getIaMarketValueHeldByPrincipal().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.iamktvalheld.prin")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getIaMarketValueHeldByPrincipal().getRealValue());
            }
        }
        if (assetHoldingsSummary.getAdjustedIACollateralValuePrincipal() != null) {
            try {
                validateThat(locator("at.ah.adjiacolval.prin")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(
                                assetHoldingsSummary.getAdjustedIACollateralValuePrincipal().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.adjiacolval.prin")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getAdjustedIACollateralValuePrincipal().getRealValue());
            }
        }
        if (assetHoldingsSummary.getMktValueOfAssetsHeldByCounterparty() != null) {
            try {
                validateThat(locator("at.ah.mktvalofassetheld.coupty")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(
                                assetHoldingsSummary.getMktValueOfAssetsHeldByCounterparty().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.mktvalofassetheld.coupty")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getMktValueOfAssetsHeldByCounterparty().getRealValue());
            }
        }
        if (assetHoldingsSummary.getAdjustedCollateralValueCounterparty() != null) {
            try {
                validateThat(locator("at.ah.adjcolval.coupty")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(
                                assetHoldingsSummary.getAdjustedCollateralValueCounterparty().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.adjcolval.coupty")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getAdjustedCollateralValueCounterparty().getRealValue());
            }
        }
        if (assetHoldingsSummary.getIaMarketValueHeldByCounterparty() != null) {
            try {
                validateThat(locator("at.ah.iamktvalheld.coupty")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(
                                assetHoldingsSummary.getIaMarketValueHeldByCounterparty().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.iamktvalheld.coupty")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getIaMarketValueHeldByCounterparty().getRealValue());
            }
        }
        if (assetHoldingsSummary.getAdjustedIACollateralValueCounterparty() != null) {
            try {
                validateThat(locator("at.ah.adjiacolval.coupty")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(
                                assetHoldingsSummary.getAdjustedIACollateralValueCounterparty().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.adjiacolval.coupty")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getAdjustedIACollateralValueCounterparty().getRealValue());
            }
        }
        if (assetHoldingsSummary.getTsaBookingVM() != null) {
            try {
                validateThat(locator("at.ah.tsa.book.vm")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(assetHoldingsSummary.getTsaBookingVM().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.tsa.book.vm")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getTsaBookingVM().getRealValue());
            }
        }
        if (assetHoldingsSummary.getVmCollateralBalance() != null) {
            try {
                validateThat(locator("at.ah.colbal.vm")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(assetHoldingsSummary.getVmCollateralBalance().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.colbal.vm")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getVmCollateralBalance().getRealValue());
            }
        }
        if (assetHoldingsSummary.getVmCashBalance() != null) {
            try {
                validateThat(locator("at.ah.cashbal.vm")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(assetHoldingsSummary.getVmCashBalance().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.cashbal.vm")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getVmCashBalance().getRealValue());
            }
        }
        if (assetHoldingsSummary.getVmMarketValueOfCash() != null) {
            try {
                validateThat(locator("at.ah.mktvaluecash.vm")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(assetHoldingsSummary.getVmMarketValueOfCash().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.mktvaluecash.vm")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getVmMarketValueOfCash().getRealValue());
            }
        }
        if (assetHoldingsSummary.getVmSecuritiesBalance() != null) {
            try {
                validateThat(locator("at.ah.secubal.vm")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(assetHoldingsSummary.getVmSecuritiesBalance().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.secubal.vm")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getVmSecuritiesBalance().getRealValue());
            }
        }
        if (assetHoldingsSummary.getVmMarketValueOfSecurities() != null) {
            try {
                validateThat(locator("at.ah.mktvaluesecu.vm")).innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Float.parseFloat(assetHoldingsSummary.getVmMarketValueOfSecurities().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.mktvaluesecu.vm")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getVmMarketValueOfSecurities().getRealValue());
            }
        }
        if (assetHoldingsSummary.getImCollateralBalance() != null) {
            try {
                validateThat(locator("at.ah.colbal.im")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(assetHoldingsSummary.getImCollateralBalance().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.colbal.im")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getImCollateralBalance().getRealValue());
            }
        }
        if (assetHoldingsSummary.getImCashBalance() != null) {
            try {
                validateThat(locator("at.ah.cashbal.im")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(assetHoldingsSummary.getImCashBalance().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.cashbal.im")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getImCashBalance().getRealValue());
            }
        }
        if (assetHoldingsSummary.getImMarketValueOfCash() != null) {
            try {
                validateThat(locator("at.ah.mktvaluecash.im")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(assetHoldingsSummary.getImMarketValueOfCash().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.mktvaluecash.im")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getImMarketValueOfCash().getRealValue());
            }
        }
        if (assetHoldingsSummary.getImSecuritiesBalance() != null) {
            try {
                validateThat(locator("at.ah.secubal.im")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(assetHoldingsSummary.getImSecuritiesBalance().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.secubal.im")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getImSecuritiesBalance().getRealValue());
            }
        }
        if (assetHoldingsSummary.getImMarketValueOfSecurities() != null) {
            try {
                validateThat(locator("at.ah.mktvaluesecu.im")).innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Float.parseFloat(assetHoldingsSummary.getImMarketValueOfSecurities().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.mktvaluesecu.im")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getImMarketValueOfSecurities().getRealValue());
            }
        }
        if (assetHoldingsSummary.getNetFreeCollateralValue() != null) {
            try {
                validateThat(locator("at.ah.netfreecolval")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(assetHoldingsSummary.getNetFreeCollateralValue().getRealValue()))
                );
            } catch (NumberFormatException e) {
                validateThat(locator("at.ah.netfreecolval")).innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getNetFreeCollateralValue().getRealValue());
            }
        }
        if (assetHoldingsSummary.getPendingCashBookings() != null) {
            try {
                validateThat("at.ah.pending.cash.bookings").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(assetHoldingsSummary.getPendingCashBookings().getRealValue())));
            } catch (NumberFormatException e) {
                validateThat("at.ah.pending.cash.bookings").innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getPendingCashBookings().getRealValue());
            }
        }
        if (assetHoldingsSummary.getTodaysSettledCashBookings() != null) {
            try {
                validateThat("at.ah.today.settled.cash.bookings").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(
                                assetHoldingsSummary.getTodaysSettledCashBookings().getRealValue())));
            } catch (NumberFormatException e) {
                validateThat("at.ah.today.settled.cash.bookings").innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getTodaysSettledCashBookings().getRealValue());
            }
        }
        if (assetHoldingsSummary.getPendingIMCashBookings() != null) {
            try {
                validateThat("at.ah.pending.im.cash.bookings").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(assetHoldingsSummary.getPendingIMCashBookings().getRealValue())));
            } catch (NumberFormatException e) {
                validateThat("at.ah.pending.im.cash.bookings").innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getPendingIMCashBookings().getRealValue());
            }
        }
        if (assetHoldingsSummary.getTodaysSettledIMCashBookings() != null) {
            try {
                validateThat("at.ah.today.settled.im.cash.bookings").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(
                                assetHoldingsSummary.getTodaysSettledIMCashBookings().getRealValue())));
            } catch (NumberFormatException e) {
                validateThat("at.ah.today.settled.im.cash.bookings").innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getTodaysSettledIMCashBookings().getRealValue());
            }
        }
        if (assetHoldingsSummary.getPendingIMSecurityBookings() != null) {
            try {
                validateThat("at.ah.pending.im.security.bookings").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(
                                assetHoldingsSummary.getPendingIMSecurityBookings().getRealValue())));
            } catch (NumberFormatException e) {
                validateThat("at.ah.pending.im.security.bookings").innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getPendingIMSecurityBookings().getRealValue());
            }
        }
        if (assetHoldingsSummary.getTodaysSettledIMSecurityBookings() != null) {
            try {
                validateThat("at.ah.today.settled.im.security.bookings").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(
                                assetHoldingsSummary.getTodaysSettledIMSecurityBookings().getRealValue())));
            } catch (NumberFormatException e) {
                validateThat("at.ah.today.settled.im.security.bookings").innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getTodaysSettledIMSecurityBookings().getRealValue());
            }
        }
        if (assetHoldingsSummary.getNonCashCollateralOnDeposit() != null) {
            try {
                validateThat("at.ah.non.cash.collateral.on.deposit").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(
                                assetHoldingsSummary.getNonCashCollateralOnDeposit().getRealValue())));
            } catch (NumberFormatException e) {
                validateThat("at.ah.non.cash.collateral.on.deposit").innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getNonCashCollateralOnDeposit().getRealValue());
            }
        }
        if (assetHoldingsSummary.getCashOnDepositForIMR() != null) {
            try {
                validateThat("at.ah.cash.on.deposit.for.imr").innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Double.parseDouble(assetHoldingsSummary.getCashOnDepositForIMR().getRealValue())));
            } catch (NumberFormatException e) {
                validateThat("at.ah.cash.on.deposit.for.imr").innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getCashOnDepositForIMR().getRealValue());
            }
        }
        if (assetHoldingsSummary.getAccountCashBalance() != null) {
            try {
                validateThat("at.ah.acount.cash.balance").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(assetHoldingsSummary.getAccountCashBalance().getRealValue())));
            } catch (NumberFormatException e) {
                validateThat("at.ah.acount.cash.balance").innerText().isEqualToIgnoringWhitespace(
                        assetHoldingsSummary.getAccountCashBalance().getRealValue());
            }
        }
    }

    private void assertExposureSummary(ExposureSummary exposureSummary) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if (exposureSummary.getPrincipalPaysIndependentAmount() != null) {
            try {
                assertThat(locator("at.expo.smry.prinpayindpamt")).innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Float.parseFloat(exposureSummary.getPrincipalPaysIndependentAmount().getRealValue())));
            } catch (NumberFormatException e) {
                assertThat(locator("at.expo.smry.prinpayindpamt")).innerText().isEqualToIgnoringWhitespace(
                        exposureSummary.getPrincipalPaysIndependentAmount().getRealValue());
            }
        }

        if (exposureSummary.getCounterpartyPaysIndependentAmount() != null) {
            try {
                assertThat(locator("at.expo.smry.coupartypayindpamt")).innerText().isEqualToIgnoringWhitespace(
                        format.format(
                                Float.parseFloat(exposureSummary.getCounterpartyPaysIndependentAmount().getRealValue()))
                );
            } catch (NumberFormatException e) {
                assertThat(locator("at.expo.smry.coupartypayindpamt")).innerText().isEqualToIgnoringWhitespace(
                        exposureSummary.getCounterpartyPaysIndependentAmount().getRealValue());
            }
        }
        if (exposureSummary.getItmExposure() != null) {
            try {
                assertThat(locator("at.expo.smry.itmexpo")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(exposureSummary.getItmExposure().getRealValue()))
                );
            } catch (NumberFormatException e) {
                assertThat(locator("at.expo.smry.itmexpo")).innerText().isEqualToIgnoringWhitespace(
                        exposureSummary.getItmExposure().getRealValue());
            }
        }

        if (exposureSummary.getOtmExposure() != null) {
            try {
                assertThat(locator("at.expo.smry.otmexpo")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(exposureSummary.getOtmExposure().getRealValue()))
                );
            } catch (NumberFormatException e) {
                assertThat(locator("at.expo.smry.otmexpo")).innerText().isEqualToIgnoringWhitespace(
                        exposureSummary.getOtmExposure().getRealValue());
            }
        }

        if (exposureSummary.getTotalExposureAmount() != null) {
            try {
                assertThat(locator("at.expo.smry.tolexpoamt")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(exposureSummary.getTotalExposureAmount().getRealValue()))
                );
            } catch (NumberFormatException e) {
                assertThat(locator("at.expo.smry.tolexpoamt")).innerText().isEqualToIgnoringWhitespace(
                        exposureSummary.getTotalExposureAmount().getRealValue());
            }
        }
        if (exposureSummary.getPrincipalExempt() != null)
            assertThat(locator("at.expo.smry.principal.exempt")).innerText().isEqualToIgnoringWhitespace(
                    exposureSummary.getPrincipalExempt().getRealValue());
        if (exposureSummary.getCounterpartyExempt() != null)
            assertThat(locator("at.expo.smry.counterparty.exempt")).innerText().isEqualToIgnoringWhitespace(
                    exposureSummary.getCounterpartyExempt().getRealValue());
        if (exposureSummary.getPrincipalFinra4210Buffer() != null)
            assertThat(locator("at.expo.smry.principal.finra4210.buffer")).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(exposureSummary.getPrincipalFinra4210Buffer().getRealValue())));
        if (exposureSummary.getCounterpartyFinra4210Buffer() != null)
            assertThat(locator("at.expo.smry.counterparty.finra4210.buffer")).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(exposureSummary.getCounterpartyFinra4210Buffer().getRealValue())));

        if (exposureSummary.getNetSysCalcSecurityLeg() != null)
            assertThat(locator("at.expo.smry.net.syscalcsecurityleg")).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(exposureSummary.getNetSysCalcSecurityLeg().getRealValue()))
            );
        if (exposureSummary.getNetSysCalcCashLeg() != null)
            assertThat(locator("at.expo.smry.net.syscalccashleg")).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(exposureSummary.getNetSysCalcCashLeg().getRealValue()))
            );
        if (exposureSummary.getNetNonSysCalcValue() != null)
            assertThat(locator("at.expo.smry.net.nonsyscalcval")).innerText().isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(exposureSummary.getNetNonSysCalcValue().getRealValue()))
            );
        if (exposureSummary.getTotalMTMAmount() != null) {
            try {
                assertThat(locator("at.expo.smry.tolmtmamt")).innerText().isEqualToIgnoringWhitespace(
                        format.format(Float.parseFloat(exposureSummary.getTotalMTMAmount().getRealValue()))
                );
            } catch (NumberFormatException e) {
                assertThat(locator("at.expo.smry.tolmtmamt")).innerText().isEqualToIgnoringWhitespace(
                        exposureSummary.getTotalMTMAmount().getRealValue());
            }
        }
    }

    private void assertConcentrationLimitBreached(ConcentrationLimitBreached concentrationLimitBreached) throws Exception {
        if (concentrationLimitBreached.getBreachMessage() != null)
            assertThat("at.clb.msg", concentrationLimitBreached.getBreachMessage().getRealValue(),
                    concentrationLimitBreached.getBreachMessage().getRealValue()).displayed().isTrue();
    }

    /**
     * click recalculate image in agreement statement page
     */
    public void recalculate() throws Exception {
        element("at.recalc").clickByJavaScript();
    }

    /**
     * recalculate statement if page source contains such string, recaculate statement
     *
     * @param textInPageSource
     * @param times
     * @param milliseconds     waiting for each cycle
     * @throws Exception
     */
    public void recalculateIf(String textInPageSource, int times, long milliseconds) throws Exception {
        int cycle = 0;
        while (getPageSource().contains(textInPageSource) && cycle++ < times) {
            logger.debug("found {} on page source, try to wait {} milliseconds in {} of {} times", textInPageSource,
                    milliseconds, cycle, times);
            waitThat().timeout(milliseconds);
            recalculate();
        }
    }

    /*
    * select model when it exist on the Collateral - Agreement Statement page
    *
    * @param model
    */
    public void selectAgreementModel(Model model) throws Exception {
        if (model.getModelEligibilityRule() != null && model.getModelEligibilityRule().size() > 0) {
            for (int i = 0; i < model.getModelEligibilityRule().size(); i++) {
                if (model.getModelEligibilityRule().get(i).getModelName() != null) {
                    element("at.link", model.getModelEligibilityRule().get(i).getModelName().getRealValue()).click();
                }
            }
        }


    }

    /**
     * According to whether agreement name exists to click the link when
     * agreement is UmbrellaNetting on the Collateral Statement page
     *
     * @param agreement
     */
    public void switchAgreement(Agreement agreement) throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        if (agreement.getAgreementName() == null) {
            //a[text()='?']
            StringBuffer xpath = new StringBuffer();
            if (agreement.getAgreementDescription() != null && agreement.getPrincipal() != null
                    && agreement.getCounterparty() != null && agreement.getId() != null) {
//                xpath.append(agreement.getAgreementDescription().getRealValue() + "/" + agreement.getPrincipal().getLinkText().getRealValue() + "/"
//                        + agreement.getCounterparty().getLinkText().getRealValue() + "/" + agreement.getAgreementId().getRealValue());
                xpath.append(agreement.getAgreementDescription().getRealValue()).append("/")
                        .append(agreement.getPrincipal().getLinkText().getRealValue()).append("/")
                        .append(agreement.getCounterparty().getLinkText().getRealValue())
                        .append("/").append(agreement.getAgreementId().getRealValue());
            }
            waitThat().document().toBeReady();
            waitThat().jQuery().toBeInactive();
            PageHelper.d33640Workaround();
            element("at.link", xpath.toString()).clickByJavaScript();
            PageHelper.d33640Workaround();
            waitThat().document().toBeReady();
            waitThat().jQuery().toBeInactive();
        } else {
            waitThat().document().toBeReady();
            waitThat().jQuery().toBeInactive();
            PageHelper.d33640Workaround();
            element("at.agmt.name", agreement.getAgreementName().getRealValue()).clickByJavaScript();
            PageHelper.d33640Workaround();
            waitThat().document().toBeReady();
            waitThat().jQuery().toBeInactive();
        }
    }

    public void viewSimulation() throws Exception {
        element("at.view.simulation").click();
    }

    public void viewStatement() throws Exception {
        element("at.view.statement").click();
    }

    public String getAgreementId() throws Exception {
        return element("at.agmt.id").getInnerText().trim().replace("Agreement Id:", "");
    }

    public void expandETDModelCategory(EtdModelAndModelCategoryAssetHoldingSummary assetHoldingSummary, String currency) throws Exception {
        if (assetHoldingSummary.getModelCategory() != null) {
            StringBuffer model = new StringBuffer(assetHoldingSummary.getModelCategory().getRealValue());
            if (!currency.equals(""))
                model.append(" (").append(currency).append(")");
            element("at.etd.model.category.expand", model.toString()).click();
        }
    }

    public void clickEditMarginCallCalculationButton() throws Exception {
        element("at.mcc.etd.edit.margin.call.calculation").click();
    }

    public void inputAdjColumnValue(AgreementStatement agreementStatement) throws Exception {
        if (agreementStatement.getMarginCallCalculation() != null
                && agreementStatement.getMarginCallCalculation().getEtdReq() != null
                && agreementStatement.getMarginCallCalculation().getEtdReq().getAdj() != null) {
            MarginCallCalculationETDAdj marginCallCalculationETDAdj = agreementStatement.getMarginCallCalculation().getEtdReq().getAdj();
            if (marginCallCalculationETDAdj.getInitialCoupon() != null || marginCallCalculationETDAdj.getBankedCoupon() != null) {
                element("at.mcc.etd.coupon.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getInitialCoupon() != null) {
                    EtdAdjAdjustmemtType etdAdjAdjustmemtType = marginCallCalculationETDAdj.getInitialCoupon();
                    if (etdAdjAdjustmemtType.getAdjustmentAmount() != null)
                        element("at.mcc.etd.ic.adjust.amount.edit").input(
                                etdAdjAdjustmemtType.getAdjustmentAmount().getRealValue());
                    if (etdAdjAdjustmemtType.getComment() != null)
                        element("at.mcc.etd.ic.adjust.comment.edit").input(
                                etdAdjAdjustmemtType.getComment().getRealValue());
                    if (etdAdjAdjustmemtType.isApply() != null && !etdAdjAdjustmemtType.isApply())
                        save = false;
                }
                if (marginCallCalculationETDAdj.getBankedCoupon() != null) {
                    EtdAdjAdjustmemtType etdAdjAdjustmemtType = marginCallCalculationETDAdj.getBankedCoupon();
                    if (etdAdjAdjustmemtType.getAdjustmentAmount() != null)
                        element("at.mcc.etd.bc.adjust.amount.edit").input(
                                etdAdjAdjustmemtType.getAdjustmentAmount().getRealValue());
                    if (etdAdjAdjustmemtType.getComment() != null)
                        element("at.mcc.etd.bc.adjust.comment.edit").input(
                                etdAdjAdjustmemtType.getComment().getRealValue());
                    if (etdAdjAdjustmemtType.isApply() != null && !etdAdjAdjustmemtType.isApply())
                        save = false;
                }
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getPai() != null) {
                element("at.mcc.etd.pai.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getPai() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getPai());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getUpfrontFee() != null) {
                element("at.mcc.etd.upfront.fee.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getUpfrontFee() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getUpfrontFee());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getRealisedPAndLCleared() != null) {
                element("at.mcc.etd.realised.cleared.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getRealisedPAndLCleared() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getRealisedPAndLCleared());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getUnrealisedPAndLCleared() != null) {
                element("at.mcc.etd.unrealised.cleared.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getUnrealisedPAndLCleared() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getUnrealisedPAndLCleared());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getTotalFeesCleared() != null) {
                element("at.mcc.etd.total.fees.cleared.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getTotalFeesCleared() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getTotalFeesCleared());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getCashflow() != null) {
                element("at.mcc.etd.cashflow.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getCashflow() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getCashflow());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getNetPAndLCleared() != null) {
                element("at.mcc.etd.net.pl.cleared.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getNetPAndLCleared() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getNetPAndLCleared());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getRealisedPAndLETD() != null) {
                element("at.mcc.etd.realised.etd.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getRealisedPAndLETD() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getRealisedPAndLETD());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getTotalNetPAndL() != null) {
                element("at.mcc.etd.total.net.pl.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getTotalNetPAndL() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getTotalNetPAndL());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getOpeningBalance() != null) {
                element("at.mcc.etd.opening.balance.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getOpeningBalance() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getOpeningBalance());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getCommissionAndFees() != null) {
                element("at.mcc.etd.commissions.and.fees.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getCommissionAndFees() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getCommissionAndFees());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getOptionPremium() != null) {
                element("at.mcc.etd.option.premium.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getOptionPremium() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getOptionPremium());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getCashAmounts() != null) {
                element("at.mcc.etd.cash.amounts.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getCashAmounts() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getCashAmounts());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getEndingBalance() != null) {
                element("at.mcc.etd.ending.balance.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getEndingBalance() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getEndingBalance());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getOpenTradeEquityOTE() != null) {
                element("at.mcc.etd.ote.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getOpenTradeEquityOTE() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getOpenTradeEquityOTE());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getTotalEquityTE() != null) {
                element("at.mcc.etd.te.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getTotalEquityTE() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getTotalEquityTE());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getLongOptionValueLOV() != null) {
                element("at.mcc.etd.lov.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getLongOptionValueLOV() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getLongOptionValueLOV());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getShortOptionValueSOV() != null) {
                element("at.mcc.etd.sov.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getShortOptionValueSOV() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getShortOptionValueSOV());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getNetOptionValueNOV() != null) {
                element("at.mcc.etd.nov.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getNetOptionValueNOV() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getNetOptionValueNOV());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getNetLiquidatingValueNLV() != null) {
                element("at.mcc.etd.nlv.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getNetLiquidatingValueNLV() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getNetLiquidatingValueNLV());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getInitialMarginRequirementIMR() != null) {
                element("at.mcc.etd.imr.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getInitialMarginRequirementIMR() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getInitialMarginRequirementIMR());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getMaintenanceMarginRequirementMMR() != null) {
                element("at.mcc.etd.mmr.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getMaintenanceMarginRequirementMMR() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getMaintenanceMarginRequirementMMR());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getImSecuritiesBalance() != null) {
                element("at.mcc.etd.im.securities.balance.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getImSecuritiesBalance() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getImSecuritiesBalance());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getImCashBalance() != null) {
                element("at.mcc.etd.im.cash.balance.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getImCashBalance() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getImCashBalance());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getImDCVM() != null) {
                element("at.mcc.etd.im.dcvm.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getImDCVM() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getImDCVM());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getDefaultFundRequirement() != null) {
                element("at.mcc.etd.default.fund.requirement.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getDefaultFundRequirement() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getDefaultFundRequirement());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getDfSecuritiesBalance() != null) {
                element("at.mcc.etd.df.securities.balance.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getDfSecuritiesBalance() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getDfSecuritiesBalance());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getDfCashBalance() != null) {
                element("at.mcc.etd.df.cash.balance.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getDfCashBalance() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getDfCashBalance());
                saveAdjustmentBox(save);
            }
            if (marginCallCalculationETDAdj.getDfExcessOrDeficit() != null) {
                element("at.mcc.etd.df.excess.deficit.adj.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
                boolean save = true;
                if (marginCallCalculationETDAdj.getDfExcessOrDeficit() != null)
                    save = editAdjustmentBox(marginCallCalculationETDAdj.getDfExcessOrDeficit());
                saveAdjustmentBox(save);
            }
        }
    }

    public void saveADJAdjustmentChange(MarginCallCalculationETDAdj marginCallCalculationETDAdj) throws Exception {
        if (marginCallCalculationETDAdj.isApply() != null && !marginCallCalculationETDAdj.isApply()) {
            if (element("at.mcc.etd.adjust.cancel").isDisplayed()) {
                element("at.mcc.etd.adjust.cancel").click();
            }
            else
                element("at.mcc.etd.combined.adjust.box.cancel").click();
        }
        else {
            if (element("at.mcc.etd.adjust.save").isDisplayed()) {
                element("at.mcc.etd.adjust.save").click();
            }
            else
                element("at.mcc.etd.combined.adjust.box.save").click();
        }
    }

    private boolean editAdjustmentBox(EtdAdjAdjustmemtType etdAdjAdjustmemtType) throws Exception{
        boolean save = true;
        if (etdAdjAdjustmemtType.getAdjustmentAmount() != null)
            element("at.mcc.etd.adjust.amount.edit").input(etdAdjAdjustmemtType.getAdjustmentAmount().getRealValue());
        if (etdAdjAdjustmemtType.getComment() != null)
            element("at.mcc.etd.adjust.comment.edit").input(etdAdjAdjustmemtType.getComment().getRealValue());
        if (etdAdjAdjustmemtType.isApply() != null && !etdAdjAdjustmemtType.isApply())
            save = false;
        return save;
    }

    private void saveAdjustmentBox(boolean flag) throws Exception {
        if (flag){
            if (element("at.mcc.etd.adjust.box.save").isDisplayed()){
                element("at.mcc.etd.adjust.box.save").click();
            }
            else {
                element("at.mcc.etd.combined.adjust.box.save").click();
            }
        }
        else {
            if (element("at.mcc.etd.adjust.box.cancel").isDisplayed()){
                element("at.mcc.etd.adjust.box.cancel").click();
            }
            else
                element("at.mcc.etd.combined.adjust.box.cancel").click();
        }
    }
    public void assertCategoryEdValue(double totalEdValue, String modelCategoryName) throws Exception {
        DecimalFormat df = new DecimalFormat("#,###,###.##");
        df.setMinimumFractionDigits(2);
        assertThat(element("at.etd.pending.excess.deficit",
                locator("at.etd.model.category.row", String.valueOf(modelCategoryName))
                        .getExpression_().toString())).innerText().isEqualToIgnoringWhitespace(df.format(totalEdValue));
    }

    public void showHideZeroBalance() throws Exception {
        element("at.hide-show.zero.balances").click();
    }
}

