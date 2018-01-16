package com.lombardrisk.pages.collateral;

import com.lombardrisk.pages.AbstractRuleSetupPage;
import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class EligibilityRulesTemplatePage extends AbstractRuleSetupPage {
    public EligibilityRulesTemplatePage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void openEligibilityRulesTemplate(EligibilityRulesTemplate template) throws Exception {
        if (template.getTemplateName()!=null)
            element("er.edit", template.getTemplateName().getRealValue(), template.getTemplateName().getRealValue()).clickByJavaScript();
    }

    /**
     * add eligibility rules template and approve it
     *
     * @param template
     */
    public void setupEligibilityRulesTemplate(EligibilityRulesTemplate template) throws Exception {
        addEligibilityRulesTemplate(template);
        searchEligibilityRulesTemplate(template);
        approveEligibilityRulesTemplate(template);
    }

    public void setupEligibilityRuleTemplateBaseInfo(EligibilityRulesTemplate template) throws Exception{
        if (template.getAssetClassification() != null && template.getAssetClassification().getValue() != null){
            element("er.asset.classification").selectByVisibleText(template.getAssetClassification().getValue().getRealValue());
            if(template.getAssetClassification().getAssetClassificationAlertHandling() != null)
                PageHelper.handleAlter(template.getAssetClassification().getAssetClassificationAlertHandling());
            PageHelper.d33640Workaround();
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
        }
        if (template.getTemplateName() != null)
            element("er.template.name").input(template.getTemplateName().getRealValue());
        if (template.getNettingIAandMtM() != null)
            element("er.net").selectByVisibleText(template.getNettingIAandMtM().value());
        if (template.getGracePeriod() != null)
            element("er.asset.graceperiod").selectByVisibleText(template.getGracePeriod().getRealValue());
        if (template.getConcentrationLimitTrigger() != null)
            element("er.cl.trigger").input(template.getConcentrationLimitTrigger().getRealValue());
        if (template.getConcentrationLimitBreachAdjustment() != null){
            if(template.getConcentrationLimitBreachAdjustment() == ConcentrationLimitBreachAdjustmentType.BLANK) {
                element("ap.cl.rule.adj").selectByValue("0");
            }else{
                element("ap.cl.rule.adj").selectByVisibleText(template.getConcentrationLimitBreachAdjustment().value());
            }
        }
        if (template.isUseIssueRating() != null)
            element("er.use.issue.rating").check(template.isUseIssueRating());
        if (template.isUseIssuerRating() != null)
            element("er.use.issuer.rating").check(template.isUseIssuerRating());
        if (template.isApplicableAgencies() != null) {
            element("er.applicable.agencies").check(template.isApplicableAgencies());
            if (template.isApplicableAgencies()) {
                if (template.getReferenceRatingAgencies() != null && !template.getReferenceRatingAgencies().isEmpty()) {
                    element("er.reference.rating.agencies").deselectAll();
                    for (StringType stringType : template.getReferenceRatingAgencies()) {
                        element("er.reference.rating.agencies").selectByVisibleText(stringType.getRealValue());
                    }
                }
                if (template.getDebtClassification() != null)
                    element("er.debt.classification").selectByVisibleText(template.getDebtClassification().getRealValue());
                if (template.getSelectionOfAgenciesUse() != null)
                    element("er.selection.of.agencies.use").input(template.getSelectionOfAgenciesUse().getRealValue());
                if (template.getSelectionofAgenciesUseDirection() != null)
                    element("er.selection.of.agencies.use.direction").selectByVisibleText(template.getSelectionofAgenciesUseDirection().getRealValue());
                if (template.getApplicationOfRatings() != null)
                    element("er.application.of.ratings").selectByVisibleText(template.getApplicationOfRatings().getRealValue());
            }
        }
    }
    public void setupEligibilityRuleTemplateRule(EligibilityRulesTemplate template) throws Exception{
        if (template.getCollateralAssetClass() != null && !template.getCollateralAssetClass().isEmpty()) {
//            for (CollateralAssetClass assetClass : template.getCollateralAssetClass()) {
//                if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
//                    int r = 1;// asset type row number
//                    for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
//                        // click + icon to add a new asset type
//                        element("er.add.new.asset", assetClass.getCollateralAssetClassName().getRealValue()).click();
//                        setupEligibilityRulesTemplateAssetType(locator("er.asset.row",
//                                assetClass.getCollateralAssetClassName().getRealValue(), String.valueOf(r)).getExpression_(),
//                                assetType);
//                        r++;
//                    }
//                }
//                setupEligibilityRulesTemplateAssetClass(assetClass);
//            }
            setupAssetInfo(template.getCollateralAssetClass());
        }
        if (template.getCollateralAssetClassification()!=null && template.getCollateralAssetClassification().size()>0){
            for (CollateralAssetClassification classification:template.getCollateralAssetClassification()){
                int r = 1;
                if (classification.getAssetCategoryName()!=null){
                    element("er.add.new.asset",classification.getAssetCategoryName().getRealValue()).click();
                    setupEligibilityRulesTemplateAssetCategory(locator("er.asset.row",
                            classification.getAssetCategoryName().getRealValue(), String.valueOf(r)).getExpression_(),
                            classification);
                    r++;
                }
            }
        }
        if (template.getTemplateLevelConcentrationLimitRules()!=null && template.getTemplateLevelConcentrationLimitRules().size()>0){
            openTemplateLevelRule();
            switchToConcentrationLimitRulesTab();
            setupConcentrationLimitRules(template.getTemplateLevelConcentrationLimitRules());
            saveConcentrationLimitRules();
            exitConcentrationLimitRules();
        }
    }

    public void modifyEligibilityRuleTemplateRule(EligibilityRulesTemplate oriTemplate,EligibilityRulesTemplate template) throws Exception{
        if (template.getCollateralAssetClass() != null && !template.getCollateralAssetClass().isEmpty()) {
//            for (CollateralAssetClass assetClass : template.getCollateralAssetClass()) {
//                if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
//                    int r = 1;// asset type row number
//                    for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
//                        // click + icon to add a new asset type
//                        if (assetType.getName()!=null){
//                            CollateralAssetClass oriClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriTemplate.getCollateralAssetClass());
//                            if (oriClass!=null && oriClass.getCollateralAssetType()!=null && oriClass.getCollateralAssetClassName()!=null) {
//                                CollateralAssetType oriAssetType = (CollateralAssetType) Biz.matchedObjectWithName(assetType, oriClass.getCollateralAssetType());
//                                modifyEligibilityRulesTemplateAssetType(locator("er.asset.row.edit",
//                                        oriClass.getCollateralAssetClassName().getRealValue(),oriAssetType.getCollateralAssetTypeName().getRealValue()).getExpression_(),
//                                        oriAssetType,assetType);
//                            }else {
//                                element("er.add.new.asset", assetClass.getCollateralAssetClassName().getRealValue()).click();
//                                setupEligibilityRulesTemplateAssetType(locator("er.asset.row",
//                                        assetClass.getCollateralAssetClassName().getRealValue(), String.valueOf(r)).getExpression_(),
//                                        assetType);
//                                r++;
//                            }
//                        }else {
//                            element("er.add.new.asset", assetClass.getCollateralAssetClassName().getRealValue()).click();
//                            setupEligibilityRulesTemplateAssetType(locator("er.asset.row",
//                                    assetClass.getCollateralAssetClassName().getRealValue(), String.valueOf(r)).getExpression_(),
//                                    assetType);
//                            r++;
//                        }
//                    }
//                }
//                if (assetClass.getName()!=null) {
//                    CollateralAssetClass oriClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriTemplate.getCollateralAssetClass());
//                    editEligibilityRulesTemplateAssetClass(oriClass,assetClass);
//                }else
//                    setupEligibilityRulesTemplateAssetClass(assetClass);
//            }
            modifyAssetInfo(oriTemplate.getCollateralAssetClass(),template.getCollateralAssetClass());
        }
        if (template.getCollateralAssetClassification()!=null && template.getCollateralAssetClassification().size()>0){
            for (CollateralAssetClassification classification:template.getCollateralAssetClassification()){
                int r = 1;
                if (classification.getAssetCategoryName()!=null){
                    if (classification.getName()!=null) {
                        CollateralAssetClassification oriClassification = (CollateralAssetClassification) Biz.matchedObjectWithName(classification, oriTemplate.getCollateralAssetClassification());
                        if (oriClassification!=null){
                            modifyEligibilityRulesTemplateAssetCategory(locator("er.asset.row",
                                    oriClassification.getAssetCategoryName().getRealValue(), String.valueOf(r)).getExpression_(),oriClassification,classification);}
                    }else {
                        element("er.add.new.asset",classification.getAssetCategoryName().getRealValue()).click();
                        setupEligibilityRulesTemplateAssetCategory(locator("er.asset.row",
                                classification.getAssetCategoryName().getRealValue(), String.valueOf(r)).getExpression_(),
                                classification);
                        r++;
                    }
                }
            }
        }
        if (template.getTemplateLevelConcentrationLimitRules()!=null && template.getTemplateLevelConcentrationLimitRules().size()>0){
            openTemplateLevelRule();
            switchToConcentrationLimitRulesTab();
            if (oriTemplate!=null)
                editConcentrationLimitRules(oriTemplate.getTemplateLevelConcentrationLimitRules(),template.getTemplateLevelConcentrationLimitRules());
            else
                setupConcentrationLimitRules(template.getTemplateLevelConcentrationLimitRules());
            saveConcentrationLimitRules();
            exitConcentrationLimitRules();
        }
    }


    /**
     * add eligibility rules template
     *
     * @param template
     */
    public void addEligibilityRulesTemplate(EligibilityRulesTemplate template) throws Exception {
        element("er.add").click();
        setupEligibilityRuleTemplateBaseInfo(template);
        setupEligibilityRuleTemplateRule(template);
        element("er.save").clickByJavaScript();
        waitThat("er.search").toBeVisible();
    }

    /**
     * change source template to target template
     *
     * @param srcTemplate source template
     * @param tarTemplate target template
     * @throws Exception
     */
    public void changeEligibilityRulesTemplate(EligibilityRulesTemplate srcTemplate, EligibilityRulesTemplate tarTemplate) throws Exception {
//        searchEligibilityRulesTemplate(srcTemplate);
//        element("er.edit", srcTemplate.getTemplateName().getRealValue()).click();
        openEligibilityRulesTemplate(srcTemplate);
        setupEligibilityRuleTemplateBaseInfo(tarTemplate);
        modifyEligibilityRuleTemplateRule(srcTemplate,tarTemplate);
        element("er.save").click();
        if (tarTemplate != null){
            if(tarTemplate.getAlertHandling() != null && !tarTemplate.getAlertHandling().isEmpty())
                PageHelper.handleAlters(tarTemplate.getAlertHandling());
        }

        waitThat("er.search").toBeVisible();
    }

    public void assertEligibilityRulesTemplate(EligibilityRulesTemplate template) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("#,###,#00.00");
        searchEligibilityRulesTemplate(template);
        openEligibilityRulesTemplate(template);
        // wait the block loading image disappeared
//        waitCoverDiv();
        waitThat("er.cancel").toBeClickable();
        // TODO assert basic info here

        if(template.getTemplateName() != null)
            validateThat("er.template.name").attributeValueOf("value").isEqualToIgnoringWhitespace(template.getTemplateName().getRealValue());
        if(template.getNettingIAandMtM() != null)
            validateThat("er.net").selectedText().isEqualToIgnoringWhitespace(template.getNettingIAandMtM().value());
        if(template.getGracePeriod() != null)
            validateThat("er.asset.graceperiod").selectedText().isEqualToIgnoringWhitespace(template.getGracePeriod().getRealValue());
        if(template.getAssetClassification() != null && template.getAssetClassification().getValue() != null)
            validateThat("er.asset.classification").selectedText().isEqualToIgnoringWhitespace(template.getAssetClassification().getValue().getRealValue());
        if (template.getConcentrationLimitTrigger() != null)
            validateThat("er.cl.trigger").attributeValueOf("value").isEqualToIgnoringWhitespace(String.valueOf((format.format(Float.parseFloat(template.getConcentrationLimitTrigger().getRealValue())))));
        if (template.getConcentrationLimitBreachAdjustment() != null){
            if(template.getConcentrationLimitBreachAdjustment() == ConcentrationLimitBreachAdjustmentType.BLANK) {
                validateThat("ap.cl.rule.adj").selectedText().isEqualToIgnoringWhitespace("");
            }else{
                validateThat("ap.cl.rule.adj").selectedText().isEqualToIgnoringWhitespace(template.getConcentrationLimitBreachAdjustment().value());
            }
        }
        if (template.isUseIssueRating() != null)
            validateThat("er.use.issue.rating").selected().isEqualTo(template.isUseIssueRating());
        if (template.isUseIssuerRating() != null)
            validateThat("er.use.issuer.rating").selected().isEqualTo(template.isUseIssuerRating());

        if (template.isApplicableAgencies() != null) {
            validateThat("er.applicable.agencies").selected().isEqualTo(template.isApplicableAgencies());
            if (template.isApplicableAgencies()) {
                if (template.getReferenceRatingAgencies() != null && !template.getReferenceRatingAgencies().isEmpty()) {
                    for (StringType stringType : template.getReferenceRatingAgencies()) {
                        validateThat("er.reference.rating.agencies").allSelectedTexts().contains(stringType.getRealValue());
                    }
                }
                if (template.getDebtClassification() != null)
                    validateThat("er.debt.classification").selectedText().isEqualToIgnoringWhitespace(template.getDebtClassification().getRealValue());
                if (template.getSelectionOfAgenciesUse() != null)
                    validateThat("er.selection.of.agencies.use").attributeValueOf("value").isEqualToIgnoringWhitespace(template.getSelectionOfAgenciesUse().getRealValue());
                if (template.getSelectionofAgenciesUseDirection() != null)
                    validateThat("er.selection.of.agencies.use.direction").selectedText().isEqualToIgnoringWhitespace(template.getSelectionofAgenciesUseDirection().getRealValue());
                if (template.getApplicationOfRatings() != null)
                    validateThat("er.application.of.ratings").selectedText().isEqualToIgnoringWhitespace(template.getApplicationOfRatings().getRealValue());
            }
        }

        // assert asset class & asset type info here
        if (template.getCollateralAssetClass() != null && !template.getCollateralAssetClass().isEmpty()) {
            for (CollateralAssetClass assetClass : template.getCollateralAssetClass()) {
                if (assetClass.getCollateralAssetClassName() != null)
                    assertEligibilityRulesTemplateAssetClass(assetClass);
                if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
                    for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                        assertEligibilityRulesTemplateAssetType(assetType);
                    }
                }
            }
        }
    }

    /**
     * only used for invoking by assertEligibilityRulesTemplate
     *
     * @param assetType
     */
    private void assertEligibilityRulesTemplateAssetType(CollateralAssetType assetType) throws Exception {
        if(assetType.getCollateralAssetTypeName() != null){
            String xpath = "//tr[contains(@id,'row_type')][td/span='" + assetType.getCollateralAssetTypeName().getRealValue() + "']";
            element("er.expand.asset.notes", xpath).clickCircularly();
//            if(assetType.getApplicableParty() != null){
//                if(assetType.getApplicableParty() != ApplicablePartyToType.BOTH)
//                    validateThat("er.applicable.party", xpath).selectedText().isEqualToIgnoringWhitespace(assetType.getApplicableParty().value());
//                else
//                    validateThat("er.applicable.party", xpath).allSelectedTexts().contains(ApplicablePartyToType.P.value(),ApplicablePartyToType.C.value());
//            }
//            if(assetType.getApplicableType() != null) {
//                if(!assetType.getApplicableType().value().equals("VI"))
//                    validateThat("er.applicable.type", xpath).selectedText().isEqualToIgnoringWhitespace(assetType.getApplicableType().value());
//                else
//                    validateThat("er.applicable.type", xpath).allSelectedTexts().contains(ApplicableToType.VM.value(), ApplicableToType.IM.value());
//            }
            if(assetType.getSettlementDate() != null)
                validateThat("er.settlement.date", xpath).selectedText().isEqualToIgnoringWhitespace(assetType.getSettlementDate().getRealValue());
            if(assetType.getDeliveryPriority() != null)
                validateThat("er.delivery.priority", xpath).selectedText().isEqualToIgnoringWhitespace(assetType.getDeliveryPriority().value());
            if(assetType.getRecallPriority() != null)
                validateThat("er.recall.priority", xpath).selectedText().isEqualToIgnoringWhitespace(assetType.getRecallPriority().value());
            if(assetType.getAssetNote1() != null)
                validateThat("er.asset.notes1", xpath).attributeValueOf("value").isEqualToIgnoringWhitespace(assetType.getAssetNote1().getRealValue());
            if(assetType.getAssetNote2() != null)
                validateThat("er.asset.notes2", xpath).attributeValueOf("value").isEqualToIgnoringWhitespace(assetType.getAssetNote2().getRealValue());
            if(assetType.getAssetNote3() != null)
                validateThat("er.asset.notes3", xpath).attributeValueOf("value").isEqualToIgnoringWhitespace(assetType.getAssetNote3().getRealValue());
            if(assetType.getEligibilityRule() != null){
                validateThat("er.eligibility.rule.checkbox", xpath).selected().isTrue();
                openAssetTypeRule(xpath, assetType);
                switchToEligibilityRulesTab();
                assertEligibilityRules(assetType.getEligibilityRule());
                exitEligibilityRules();
            }
            if(assetType.getConcentrationLimitRule() != null && !assetType.getConcentrationLimitRule().isEmpty()) {
                validateThat("er.concentration.rule.checkbox", xpath).selected().isTrue();
                openAssetTypeRule(xpath, assetType);
                switchToConcentrationLimitRulesTab();
                assertConcentrationLimitRules(assetType.getConcentrationLimitRule());
                exitConcentrationLimitRules();
            }
            if(assetType.getHaircutRule() != null) {
                validateThat("er.haircut.rule.checkbox", xpath).selected().isTrue();
                openAssetTypeRule(xpath, assetType);
                switchToHaircutTab();
                assertHaircutRule(assetType.getHaircutRule());
                exitHaircutRule();
            }
        }
    }



    private void setupEligibilityRulesTemplateAssetCategory(String rowXpath, CollateralAssetClassification classification) throws Exception {

        if (classification.getApplicableParty() != null && classification.getApplicableParty().size()>0) {
            element("er.applicable.party", rowXpath).deselectAll();
            for (ApplicablePartyToType type : classification.getApplicableParty()){
                if (type == ApplicablePartyToType.BOTH){
                    element("er.applicable.party", rowXpath).selectByVisibleText(ApplicablePartyToType.P.value()).selectByVisibleText(ApplicablePartyToType.C.value());
                }else {
                    element("er.applicable.party", rowXpath).selectByVisibleText(type.value());
                }
            }
        }

        if (classification.getApplicableType() != null && classification.getApplicableType().size()>0){
            element("er.applicable.type", rowXpath).deselectAll();
            for (ApplicableToType type : classification.getApplicableType()){
                if (type == ApplicableToType.VI) {
                    element("er.applicable.type", rowXpath).selectByVisibleText(ApplicableToType.VM.value()).selectByVisibleText(ApplicableToType.IM.value());
                }else{
                    element("er.applicable.type", rowXpath).selectByVisibleText(type.value());
                }
            }
        }

        if (classification.getHaircutRule() != null) {
            openAssetTypeRule(rowXpath);
            switchToHaircutTab();
            setupHaircutRule(classification.getHaircutRule());
            saveHaircutRule();
            exitHaircutRule();
        }
        // setup asset type eligibility Rules
        if (classification.getEligibilityRule() != null) {
            openAssetTypeRule(rowXpath);
            switchToEligibilityRulesTab();
            setupEligibilityRules(classification.getEligibilityRule());
            saveEligibilityRules();
            exitEligibilityRules();
        }
        // setup asset type concentration limit rules
        if (classification.getConcentrationLimitRule() != null && !classification.getConcentrationLimitRule().isEmpty()) {
            openAssetTypeRule(rowXpath);
            switchToConcentrationLimitRulesTab();
            setupConcentrationLimitRules(classification.getConcentrationLimitRule());
            saveConcentrationLimitRules();
            element("er.cl.rules.exit").click();
        }

    }

    private void modifyEligibilityRulesTemplateAssetCategory(String rowXpath, CollateralAssetClassification oriClassification,CollateralAssetClassification classification) throws Exception {

        if (classification.getApplicableParty() != null && classification.getApplicableParty().size()>0) {
            element("er.applicable.party", rowXpath).deselectAll();
            for (ApplicablePartyToType type : classification.getApplicableParty()){
                element("er.applicable.party", rowXpath).selectByVisibleText(type.value());
            }
        }

        if (classification.getApplicableType() != null && classification.getApplicableType().size()>0){
            element("er.applicable.type", rowXpath).deselectAll();
            for (ApplicableToType type : classification.getApplicableType()){
                element("er.applicable.type", rowXpath).selectByVisibleText(type.value());
            }
        }

        if (classification.getHaircutRule() != null) {
            openAssetTypeRule(rowXpath);
            switchToHaircutTab();
            editHaircutRule(oriClassification.getHaircutRule(),classification.getHaircutRule());
            saveHaircutRule();
            exitHaircutRule();
        }
        // setup asset type eligibility Rules
        if (classification.getEligibilityRule() != null) {
            openAssetTypeRule(rowXpath);
            switchToEligibilityRulesTab();
            editEligibilityRules(oriClassification.getEligibilityRule(),classification.getEligibilityRule());
            saveEligibilityRules();
            exitEligibilityRules();
        }
        // setup asset type concentration limit rules
        if (classification.getConcentrationLimitRule() != null && !classification.getConcentrationLimitRule().isEmpty()) {
            openAssetTypeRule(rowXpath);
            switchToConcentrationLimitRulesTab();
            editConcentrationLimitRules(oriClassification.getConcentrationLimitRule(),classification.getConcentrationLimitRule());
            saveConcentrationLimitRules();
            element("er.cl.rules.exit").click();
        }

    }



    /**
     * search eligibility rules template
     *
     * @param template
     */
    public void searchEligibilityRulesTemplate(EligibilityRulesTemplate template) throws Exception {
        if (template.getTemplateName() != null)
            element("er.name").setAttribute("value", template.getTemplateName().getRealValue());
        element("er.search").click();
    }

    public void searchEligibilityRulesTemplateByTemplateName(EligibilityRulesTemplateSearch templateSearch) throws Exception {
        if (templateSearch.getEligibilityRulesTemplateName() != null){
            if(templateSearch.getEligibilityRulesTemplateName().getFilter()!=null)
                element("am.eligibilityRuleTemp.nameFilter").input(templateSearch.getEligibilityRulesTemplateName().getFilter().getRealValue());
            if (templateSearch.getEligibilityRulesTemplateName().getLinkText()!=null)
                element("am.eligibilityRuleTemp.nameLinktext",templateSearch.getEligibilityRulesTemplateName().getLinkText().getRealValue()).click();
        }

        element("er.search").click();
    }

    /**
     * search eligibility rules template
     *
     * @param template
     */
    public void searchEligibilityRulesTemplate(FeedEligibilityRulesTemplate template) throws Exception {
        if (template.getTemplateName() != null)
            element("er.name").setAttribute("value", template.getTemplateName().getRealValue());
        element("er.search").click();
    }

    public void viewEligibilityRulesTemplateChanges(EligibilityRulesTemplate template) throws Exception {
//        searchEligibilityRulesTemplate(template);
        element("er.view.changes", template.getTemplateName().getRealValue(), template.getTemplateName().getRealValue()).click();
    }

    public void approveEligibilityRulesTemplate(EligibilityRulesTemplate template) throws Exception {
        viewEligibilityRulesTemplateChanges(template);
        if (template.getRejectReason() != null)
            element("er.reject.reason").input(template.getRejectReason().getRealValue());
        element("er.approve").click();
    }

    public void rejectEligibilityRulesTemplate(EligibilityRulesTemplate template) throws Exception {
        viewEligibilityRulesTemplateChanges(template);
        if (template.getRejectReason() != null)
            element("er.reject.reason").input(template.getRejectReason().getRealValue());
        element("er.reject").click();
    }



    /**
     * only used for invoking by assertEligibilityRulesTemplate
     *
     * @param assetClass
     */
    private void assertEligibilityRulesTemplateAssetClass(CollateralAssetClass assetClass) throws Exception {
        // assert asset class concentration limit rules
        if (assetClass.getConcentrationLimitRule() != null & !assetClass.getConcentrationLimitRule().isEmpty()) {
            element("er.edit.rules", assetClass.getCollateralAssetClassName().getRealValue()).click();
            switchToConcentrationLimitRulesTab();
            assertConcentrationLimitRules(assetClass.getConcentrationLimitRule());
            element("er.cl.rules.exit").click();
        }
    }


    public boolean isEligibilityRulesTemplateExists() throws Exception {
        return element("er.search.result.not.found").isDisplayed();
    }

    /**
     * assert concentration limit rules on setup concentration limit rule page,
     * applicable for setup agreement and setup eligibility rules template
     *
     * @param list
     */
    @Override
    public void assertConcentrationLimitRules(List<ConcentrationLimitRule> list) throws Exception {
        int i = 0;
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,##0.00");
        for (ConcentrationLimitRule rule : list) {
            StringBuffer xpath = new StringBuffer();
            if(rule.getTrigger() != null) {
//                xpath.append("[td/input[contains(@*, 'trigger') and @value='" + format.format(Float.parseFloat(rule.getTrigger().getRealValue())) + "']]");
                xpath.append("[td/input[contains(@*, 'trigger') and @value='").append(format.format(Float.parseFloat(rule.getTrigger().getRealValue()))).append("']]");
            }
//            if(rule.getCurrency() != null)
//                xpath.append("[td/select[contains(@*, 'clCurrency') and option[text()='" + rule.getCurrency().getRealValue() + "' and @selectd='selected']]]");
//            if(rule.getRule() != null)
//                xpath.append("[td/select[contains(@*, 'clrule') and option[text()='" + rule.getRule().value() + "' and @selectd='selected']]]");
            if(rule.getElement() != null && !rule.getElement().isEmpty()){
                for(SimpleSearch simpleSearch : rule.getElement()){
//                    xpath.append("[td/input[contains(@*, 'ruleElement') and contains(@value,'" + simpleSearch.getLinkText().getRealValue() + "')]]");
                    xpath.append("[td/input[contains(@*, 'ruleElement') and contains(@value,'").append(simpleSearch.getLinkText().getRealValue()).append("')]]");
                }
            }
//            if(rule.getFrom() != null)
//                xpath.append("[td/select[contains(@*, 'fromRating') and option[text()='" + rule.getFrom().getRealValue() + "' and @selectd='selected']]]");
//            if(rule.getTo() != null)
//                xpath.append("[td/select[contains(@*, 'toRating') and option[text()='" + rule.getTo().getRealValue() + "' and @selectd='selected']]]");
//            if(rule.getMethod() != null)
//                xpath.append("[td/select[contains(@*, 'clmethod') and option[text()='" + rule.getMethod().value() + "' and @selectd='selected']]]");
            if(rule.getValue() != null) {
//                xpath.append("[td/input[contains(@*, 'clvalue') and contains(@value,'" + format.format(Float.parseFloat(rule.getValue().getRealValue())) + "')]]");
                xpath.append("[td/input[contains(@*, 'clvalue') and contains(@value,'").append(format.format(Float.parseFloat(rule.getValue().getRealValue()))).append("')]]");
            }
            if(rule.getGroup() != null) {
//                xpath.append("[td/input[contains(@*, 'groupId') and contains(@value,'" + format.format(Float.parseFloat(rule.getGroup().getRealValue())) + "')]]");
//                xpath.append("[td/input[contains(@*, 'groupId') and contains(@value,'" + rule.getGroup().getRealValue() + "')]]");
                xpath.append("[td/input[contains(@*, 'groupId') and contains(@value,'").append(rule.getGroup().getRealValue()).append("')]]");
            }
            validateThat("ap.cl.rule.record", xpath.toString()).displayed().isTrue();

//            if (rule.getRule() != null)
//                validateThat("ap.cl.rule", String.valueOf(i)).allSelectedTexts().contains(rule.getRule().value());
//            if (rule.getElement() != null)
//                validateThat("ap.cl.element", String.valueOf(i), String.valueOf(i)).attributeValueOf("value").isEqualTo(rule.getElement().getRealValue());
//            if (rule.getFrom() != null)
//                validateThat("ap.cl.from", String.valueOf(i)).allSelectedTexts().contains(rule.getFrom().getRealValue());
//            if (rule.getTo() != null)
//                validateThat("ap.cl.to", String.valueOf(i)).allSelectedTexts().contains(rule.getTo().getRealValue());
//            if (rule.getMethod() != null)
//                validateThat("ap.cl.method", String.valueOf(i)).allSelectedTexts().contains(rule.getMethod().value());
//            if (rule.getValue() != null)
//                validateThat("ap.cl.value", String.valueOf(i)).attributeValueOf("value").isEqualTo(rule.getValue().getRealValue());
//            i++;
        }
    }


    /**
     * skip BulkBooking Warning page
     */
    public void skipBulkBookingWarning() throws Exception {
        if (element("em.override").isDisplayed()) {
            // tick on checkbox
            element("em.override").check(true);
            element("em.save.booking").click();
        }
    }

//    public void openAssetClassRule(CollateralAssetClass collateralAssetClass) throws Exception{
//        element("er.edit.rules", collateralAssetClass.getCollateralAssetClassName().getRealValue()).clickByJavaScript();
//    }

    public void openTemplateLevelRule() throws Exception{
        element("er.edit.template.level.rules").clickByJavaScript();
    }

//    public void openAssetTypeRule(String rowXpath) throws Exception{
//        element("er.edit.type.rules", rowXpath).clickByJavaScript();
//    }


//    public void openAssetTypeRule(String rowXpath,CollateralAssetType collateralAssetType) throws Exception{
//        element("er.edit.type.rules", rowXpath).clickByJavaScript();
//    }

    public void openAssetTypeRule(String rowXpath,CollateralAssetType collateralAssetType) throws Exception{
        element("er.edit.type.rules", rowXpath).clickByJavaScript();
    }

}
