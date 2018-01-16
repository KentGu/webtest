package com.lombardrisk.pages;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.openqa.selenium.Keys;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by kent gu on 7/11/2016.
 */
public abstract class AbstractRuleSetupPage extends PageBase {

    public AbstractRuleSetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    //Eligibility Rule setup below
    public void switchToEligibilityRulesTab() throws Exception{
        //Switch to eligibilityRule tab
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("rules.el.rule.tab").click();
    }

    public void setupEligibilityRules(EligibilityRule eligibilityRule) throws Exception{
        //Setup rating rules
        if(eligibilityRule.getMinimumEligibilityRating() != null && !eligibilityRule.getMinimumEligibilityRating().isEmpty()){
            for(OrganisationRating rating : eligibilityRule.getMinimumEligibilityRating()){
                element("rules.el.minielrating", rating.getAgency().getRealValue())
                        .selectByVisibleText(rating.getRating().getRealValue());
            }
        }
        if(eligibilityRule.getRatingEligibilityMethod() != null)
            element("rules.el.ratingelmethod").selectByVisibleText(eligibilityRule.getRatingEligibilityMethod().getRealValue());

        //Setup CQS rules
        if(eligibilityRule.getMinEligibilityCqs() != null)
            element("rules.el.minielcqs").selectByVisibleText(eligibilityRule.getMinEligibilityCqs().getRealValue());

        //Setup Time Rules
        if(eligibilityRule.getIssueDate() != null)
            element("rules.el.issdate").input(eligibilityRule.getIssueDate().getRealValue());
        if(eligibilityRule.getMaturityBasis() != null)
            element("rules.el.matbas").selectByVisibleText(eligibilityRule.getMaturityBasis().getRealValue());
        if(eligibilityRule.getMaturityTimeType() != null)
            element("rules.el.mattimetype").selectByVisibleText(eligibilityRule.getMaturityTimeType().getRealValue());
        if(eligibilityRule.getMaturityTimeValue() != null)
            element("rules.el.mattimeval").input(eligibilityRule.getMaturityTimeValue().getRealValue());

        //Setup Correlation Rules
        if(eligibilityRule.getRoleCorrelation() != null && !eligibilityRule.getRoleCorrelation().isEmpty()){
            element("rules.el.rolcor").deselectAll();
            for(StringType value : eligibilityRule.getRoleCorrelation()){
                element("rules.el.rolcor").selectByVisibleText(value.getRealValue());
            }
        }
        if(eligibilityRule.isUltimateParentCorrelation() != null)
            element("rules.el.ultparcor").check(eligibilityRule.isUltimateParentCorrelation());
        if(eligibilityRule.isCloseLinkCorrelation() != null)
            element("rules.el.clolincorr").check(eligibilityRule.isCloseLinkCorrelation());
        if(eligibilityRule.isIndustryCorrelation() != null)
            element("rules.el.indcorr").check(eligibilityRule.isIndustryCorrelation());

        //Setup UDF Rules
        if(eligibilityRule.getUdfRule() != null && !eligibilityRule.getUdfRule().isEmpty()){
            for(int i = 0;i < eligibilityRule.getUdfRule().size(); i++){
                int totalRules = element("rules.el.udf.row").getNumberOfMatches();
                UdfRule udfRule = eligibilityRule.getUdfRule().get(i);
                if(udfRule.isRemove() == null && udfRule.getName() == null) {

                    if(i != 0 || totalRules != 1){
                        element("rules.el.udf.add", "[" + String.valueOf(totalRules) + "]").click();
                        totalRules = element("rules.el.udf.row").getNumberOfMatches();
                    }

                    if (udfRule.getSecurityUdf() != null)
                        element("rules.el.udf.secuudf", "[" + String.valueOf(totalRules) + "]")
                                .selectByVisibleText(udfRule.getSecurityUdf().getRealValue());
                    if (udfRule.getEligibilityRule() != null)
                        element("rules.el.udf.elrule", "[" + String.valueOf(totalRules) + "]")
                                .selectByVisibleText(udfRule.getEligibilityRule().getRealValue());
                    if(udfRule.getValue() != null && !udfRule.getValue().isEmpty()){
                        if(element("rules.el.udf.value.editor", "[" + String.valueOf(totalRules) + "]").isDisplayed()){
                            element("rules.el.udf.value.editor", "[" + String.valueOf(totalRules) + "]").setValue("");
                            for(StringType value : udfRule.getValue()){
                                element("rules.el.udf.value.editor", "[" + String.valueOf(totalRules) + "]").type(value.getRealValue());
                            }
                        }else{
                            element("rules.el.udf.value.select", "[" + String.valueOf(totalRules) + "]").deselectAll();
                            for(StringType value : udfRule.getValue()){
                                element("rules.el.udf.value.select", "[" + String.valueOf(totalRules) + "]")
                                        .selectByVisibleText(value.getRealValue());
                            }
                        }
                    }
                    if (udfRule.getCurrency() != null)
                        element("rules.el.udf.ccy", "[" + String.valueOf(totalRules) + "]").selectByVisibleText(udfRule.getCurrency().getRealValue());
                }
            }
        }

        //Setup Other Rules
        if(eligibilityRule.getEligibleCurrency() != null && !eligibilityRule.getEligibleCurrency().isEmpty()){
            element("rules.el.ccy").deselectAll();
            for(StringType value : eligibilityRule.getEligibleCurrency()){
                element("rules.el.ccy").selectByVisibleText(value.getRealValue());
            }
        }
        if(eligibilityRule.getEligibleCountry() != null && !eligibilityRule.getEligibleCountry().isEmpty()){
            element("rules.el.country").deselectAll();
            for(StringType value : eligibilityRule.getEligibleCountry()){
                element("rules.el.country").selectByVisibleText(value.getRealValue());
            }
        }
        if(eligibilityRule.getEligibleIssuer() != null && !eligibilityRule.getEligibleIssuer().isEmpty()){
            for(EligibleIssuerSimpleSearch eligibleIssuerSimpleSearch : eligibilityRule.getEligibleIssuer()){
                if(eligibleIssuerSimpleSearch.isRemove() == null || !eligibleIssuerSimpleSearch.isRemove()) {
                    if (eligibleIssuerSimpleSearch.getType() != null)
                        element("rules.el.issuer.type").selectByVisibleText(eligibleIssuerSimpleSearch.getType().value());
                    if (eligibleIssuerSimpleSearch.getFilter() != null)
                        element("rules.el.issuer.filter")
                                .input(eligibleIssuerSimpleSearch.getFilter().getRealValue())
                                .fireEvent("onfocus");
                    if (eligibleIssuerSimpleSearch.getLinkText() != null)
                        element("rules.el.issuer.linktext", eligibleIssuerSimpleSearch.getLinkText().getRealValue()).click();
                }
            }
        }
        if(eligibilityRule.getExcludeInstrument() != null && !eligibilityRule.getExcludeInstrument().isEmpty()){
            for(SimpleSearch simpleSearch : eligibilityRule.getExcludeInstrument()){
                if(simpleSearch.isRemove() == null || !simpleSearch.isRemove()) {
                    if (simpleSearch.getFilter() != null)
                        element("rules.el.instr.filter").input(simpleSearch.getFilter().getRealValue()).fireEvent("onfocus");
                    if (simpleSearch.getLinkText() != null)
                        element("rules.el.instr.linktext", simpleSearch.getLinkText().getRealValue()).click();
                }
            }
        }
    }

    public void assertEligibilityRules(EligibilityRule eligibilityRule) throws Exception{
        if(eligibilityRule.getMinimumEligibilityRating() != null && !eligibilityRule.getMinimumEligibilityRating().isEmpty()){
            for(OrganisationRating rating : eligibilityRule.getMinimumEligibilityRating()){
                validateThat("rules.el.minielrating", rating.getAgency().getRealValue()).selectedText().isEqualToIgnoringWhitespace(rating.getRating().getRealValue());
            }
        }
        if(eligibilityRule.getRatingEligibilityMethod() != null)
            validateThat("rules.el.ratingelmethod").selectedText().isEqualToIgnoringWhitespace(eligibilityRule.getRatingEligibilityMethod().getRealValue());

        //Setup CQS rules
        if(eligibilityRule.getMinEligibilityCqs() != null)
            validateThat("rules.el.minielcqs").selectedText().isEqualToIgnoringWhitespace(eligibilityRule.getMinEligibilityCqs().getRealValue());

        //Setup Time Rules
        if(eligibilityRule.getIssueDate() != null)
            validateThat("rules.el.issdate").attributeValueOf("value").isEqualToIgnoringWhitespace(eligibilityRule.getIssueDate().getRealValue());
        if(eligibilityRule.getMaturityBasis() != null)
            validateThat("rules.el.matbas").selectedText().isEqualToIgnoringWhitespace(eligibilityRule.getMaturityBasis().getRealValue());
        if(eligibilityRule.getMaturityTimeType() != null)
            validateThat("rules.el.mattimetype").selectedText().isEqualToIgnoringWhitespace(eligibilityRule.getMaturityTimeType().getRealValue());
        if(eligibilityRule.getMaturityTimeValue() != null)
            validateThat("rules.el.mattimeval").attributeValueOf("value").isEqualToIgnoringWhitespace(eligibilityRule.getMaturityTimeValue().getRealValue());

        //Setup Correlation Rules
        if(eligibilityRule.getRoleCorrelation() != null && !eligibilityRule.getRoleCorrelation().isEmpty()){
            element("rules.el.rolcor").deselectAll();
            for(StringType value : eligibilityRule.getRoleCorrelation()){
                validateThat("rules.el.rolcor").allSelectedTexts().contains(value.getRealValue());
            }
        }
        if(eligibilityRule.isUltimateParentCorrelation() != null)
            validateThat("rules.el.ultparcor").selected().isEqualTo(eligibilityRule.isUltimateParentCorrelation());
        if(eligibilityRule.isCloseLinkCorrelation() != null)
            validateThat("rules.el.clolincorr").selected().isEqualTo(eligibilityRule.isCloseLinkCorrelation());
        if(eligibilityRule.isIndustryCorrelation() != null)
            validateThat("rules.el.indcorr").selected().isEqualTo(eligibilityRule.isIndustryCorrelation());

        //Setup UDF Rules
        if(eligibilityRule.getUdfRule() != null && !eligibilityRule.getUdfRule().isEmpty()){
            for(UdfRule udfRule : eligibilityRule.getUdfRule()){
                validateThat("rules.udf.rule.record", getUDFRuleRecord(udfRule)).displayed().isTrue();
            }
        }

        //Setup Other Rules
        if(eligibilityRule.getEligibleCurrency() != null && !eligibilityRule.getEligibleCurrency().isEmpty()){
            element("rules.el.ccy").deselectAll();
            for(StringType value : eligibilityRule.getEligibleCurrency()){
                validateThat("rules.el.ccy").allSelectedTexts().contains(value.getRealValue());
            }
        }
        if(eligibilityRule.getEligibleCountry() != null && !eligibilityRule.getEligibleCountry().isEmpty()){
            element("rules.el.country").deselectAll();
            for(StringType value : eligibilityRule.getEligibleCountry()){
                validateThat("rules.el.country").allSelectedTexts().contains(value.getRealValue());
            }
        }
        if(eligibilityRule.getEligibleIssuer() != null && !eligibilityRule.getEligibleIssuer().isEmpty()){
            for(EligibleIssuerSimpleSearch eligibleIssuerSimpleSearch : eligibilityRule.getEligibleIssuer()){
                if(eligibleIssuerSimpleSearch.getLinkText() != null)
                    validateThat("rules.el.issuer.select").allSelectedTexts().contains(eligibleIssuerSimpleSearch.getLinkText().getRealValue());
            }
        }
        if(eligibilityRule.getExcludeInstrument() != null && !eligibilityRule.getExcludeInstrument().isEmpty()){
            for(SimpleSearch simpleSearch : eligibilityRule.getExcludeInstrument()){
                if(simpleSearch.getLinkText() != null)
                    validateThat("rules.el.instr.select").allSelectedTexts().contains(simpleSearch.getLinkText().getRealValue());
            }
        }
    }

    private String getUDFRuleRecord(UdfRule udfRule) {
        StringBuffer xpath = new StringBuffer();
        if(udfRule.getSecurityUdf() != null)
            xpath.append("[td[*[text()='Security UDF']]/following-sibling::td[1]/select/option[text()='").append(udfRule.getSecurityUdf().getRealValue()).append("']]");
        if(udfRule.getEligibilityRule() != null)
            xpath.append("[td[*[text()='Eligibility Rule']]/following-sibling::td[1]/select/option[text()='").append(udfRule.getEligibilityRule().getRealValue()).append("']]");
        if(udfRule.getValue() != null && !udfRule.getValue().isEmpty()){
            for(StringType stringType : udfRule.getValue()){
                xpath.append("[td[*[text()='Value']][following-sibling::td[1]/input[@value='").append(stringType.getRealValue()).append("']] or following-sibling::td/select[(@multiple='true' or @multiple='multiple') and option[text()='").append(stringType.getRealValue()).append("']]]");
            }
        }
        if(udfRule.getCurrency() != null)
            xpath.append("[td[*[text()='Value']]/following-sibling::td[@id='UDFRuleCcy' or @class='sec-ccy-list']/select/option[text()='").append(udfRule.getCurrency().getRealValue()).append("']]");
        return xpath.toString();
    }

    public void editEligibilityRules(EligibilityRule oriRule, EligibilityRule newRule) throws Exception{
        //Edit UDF Rules
        if(newRule.getUdfRule() != null && !newRule.getUdfRule().isEmpty()){
            for(int i = 0;i < newRule.getUdfRule().size(); i++){
                int totalRules = element("rules.el.udf.row").getNumberOfMatches();
                UdfRule udfRule = newRule.getUdfRule().get(i);

//                if(udfRule.isRemove() == null && udfRule.getName() == null) {
//                    element("rules.el.udf.add", String.valueOf(totalRules)).click();
//                    totalRules = element("rules.el.udf.row").getNumberOfMatches();
//                    if (udfRule.getSecurityUdf() != null)
//                        element("rules.el.udf.secuudf", "[" + String.valueOf(totalRules) + "]").selectByVisibleText(udfRule.getSecurityUdf().getRealValue());
//                    if (udfRule.getEligibilityRule() != null)
//                        element("rules.el.udf.elrule", "[" + String.valueOf(totalRules) + "]")
//                                .selectByVisibleText(udfRule.getEligibilityRule().getRealValue());
//                    if(udfRule.getValue() != null && !udfRule.getValue().isEmpty()){
//                        if(element("rules.el.udf.value.editor", "[" + String.valueOf(totalRules) + "]").isDisplayed()){
//                            element("rules.el.udf.value.editor", "[" + String.valueOf(totalRules) + "]").setValue("");
//                            for(StringType value : udfRule.getValue()){
//                                element("rules.el.udf.value.editor", "[" + String.valueOf(totalRules) + "]").type(value.getRealValue());
//                            }
//                        }else{
//                            element("rules.el.udf.value.select", "[" + String.valueOf(totalRules) + "]").deselectAll();
//                            for(StringType value : udfRule.getValue()){
//                                element("rules.el.udf.value.select", "[" + String.valueOf(totalRules) + "]").selectByVisibleText(value.getRealValue());
//                            }
//                        }
//                    }
//                    if (udfRule.getCurrency() != null)
//                        element("rules.el.udf.ccy", "[" + String.valueOf(totalRules) + "]").selectByVisibleText(udfRule.getCurrency().getRealValue());
//                }else
                if(udfRule.isRemove() != null && udfRule.isRemove()){
                    element("rules.el.udf.delete", getUdfRuleRow(udfRule)).click();
                }else if(udfRule.getName() != null){
                    UdfRule oriUdfRule = (UdfRule) Biz.matchedObjectWithName(udfRule, oriRule.getUdfRule());
                    if(udfRule.getSecurityUdf() != null)
                        element("rules.el.udf.secuudf.edit", getUdfRuleRow(oriUdfRule)).selectByVisibleText(udfRule.getSecurityUdf().getRealValue());
                    if(udfRule.getEligibilityRule() != null)
                        element("rules.el.udf.elrule.edit", getUdfRuleRow(oriUdfRule)).selectByVisibleText(udfRule.getEligibilityRule().getRealValue());
                    if(udfRule.getValue() != null && !udfRule.getValue().isEmpty()){
                        if(element("rules.el.udf.value.editor.edit", getUdfRuleRow(oriUdfRule)).isDisplayed()){
                            element("rules.el.udf.value.editor.edit", getUdfRuleRow(oriUdfRule)).setValue("");
                            for(StringType value : udfRule.getValue()){
                                element("rules.el.udf.value.editor.edit", getUdfRuleRow(oriUdfRule)).type(value.getRealValue());
                            }
                        }else{
                            element("rules.el.udf.value.select.edit", getUdfRuleRow(oriUdfRule)).deselectAll();
                            for(StringType value : udfRule.getValue()){
                                element("rules.el.udf.value.select.edit", getUdfRuleRow(oriUdfRule)).selectByVisibleText(value.getRealValue());
                            }
                        }
                    }
                    if(udfRule.getCurrency() != null)
                        element("rules.el.udf.ccy.edit", getUdfRuleRow(oriUdfRule)).selectByVisibleText(udfRule.getCurrency().getRealValue());
                }
            }
        }

        //Edit Eligible Issuer
        if(newRule.getEligibleIssuer() != null && !newRule.getEligibleIssuer().isEmpty()){
            for(EligibleIssuerSimpleSearch eligibleIssuerSimpleSearch : newRule.getEligibleIssuer()){
                if(eligibleIssuerSimpleSearch.isRemove() != null && eligibleIssuerSimpleSearch.isRemove())
                    element("rules.el.issuer.select").deselectByVisibleText(eligibleIssuerSimpleSearch.getLinkText().getRealValue());
            }
        }

        //Edit Exclude Instrument
        if(newRule.getExcludeInstrument() != null && !newRule.getExcludeInstrument().isEmpty()){
            for(SimpleSearch simpleSearch : newRule.getExcludeInstrument()){
                if(simpleSearch.isRemove() != null && simpleSearch.isRemove())
                    element("rules.el.instr.select.Option",simpleSearch.getLinkText().getRealValue()).click().type(Keys.DELETE);
            }
        }

        //Add new eligibility rules
        setupEligibilityRules(newRule);
    }

    public void saveEligibilityRules() throws Exception{
        element("rules.el.save").click();
    }

    public void exitEligibilityRules() throws Exception{
        element("rules.el.exit").click();
    }

    private String getUdfRuleRow(UdfRule udfRule) {
        StringBuffer xpath = new StringBuffer();
        if(udfRule.getSecurityUdf() != null)
            xpath.append("[td/select/option[text()='").append(udfRule.getSecurityUdf().getRealValue()).append("']]");
        if(udfRule.getEligibilityRule() != null)
            xpath.append("[td/select/option[text()='").append(udfRule.getEligibilityRule().getRealValue()).append("']]");
        if(udfRule.getValue() != null && !udfRule.getValue().isEmpty()){
            xpath.append("//tr[td/input[@value='").append(udfRule.getValue().get(0).getRealValue()).append("'] or td/select/option[text()='").append(udfRule.getValue().get(0).getRealValue()).append("']]");
        }
        return xpath.toString();
    }

    //Eligibility Rule setup above

    //Concentration Limit Rules below
    public void switchToConcentrationLimitRulesTab() throws Exception{
        //Switch to ConcentrationLimitRule tab
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("rules.cl.rule.tab").click();
    }
    /**
     * setup agreement concentration limit rules on concentration limit rules
     * tab
     *
     * @param list
     */
    public void setupConcentrationLimitRules(List<ConcentrationLimitRule> list) throws Exception {
        for(ConcentrationLimitRule rule : list){
            if(rule.isRemove() == null && rule.getName() == null) {
                element("rules.cl.rule.add").clickByJavaScript();
                int rowNum = element("rules.cl.rule.rows").getNumberOfMatches();
                if (rule.getAssetCategories()!=null && rule.getAssetCategories().size()>0){
                    element("rules.cl.rule.asset.categories", String.valueOf(rowNum)).input("");
                    for (SimpleSearch categotySearch : rule.getAssetCategories()){
                        if (categotySearch.getFilter()!=null)
                            element("rules.cl.rule.asset.categories", String.valueOf(rowNum)).type(categotySearch.getFilter().getRealValue());
                        if (categotySearch.getLinkText()!=null)
                            element("rules.cl.rule.asset.categories.linktext", categotySearch.getLinkText().getRealValue()).click();
                    }
                }
                if (rule.getTrigger() != null)
                    element("rules.cl.rule.trigger", String.valueOf(rowNum)).input(rule.getTrigger().getRealValue());
                if (rule.getCurrency() != null)
                    element("rules.cl.rule.currency", String.valueOf(rowNum)).selectByVisibleText(rule.getCurrency().getRealValue());
                if (rule.getPrincipalOrCpty()!=null && !rule.getPrincipalOrCpty().isEmpty()){
                    alert().disable();
                    element("rules.cl.rule.principal.cpty",String.valueOf(rowNum)).deselectAll();
                    for (ApplicablePartyToType pcType : rule.getPrincipalOrCpty())
                        element("rules.cl.rule.principal.cpty",String.valueOf(rowNum)).selectByVisibleText(pcType.value());
                    alert().enable();
                }
                if (rule.getBookingType()!=null && !rule.getBookingType().isEmpty()){
                    alert().disable();
                    element("rules.cl.rule.booking.type",String.valueOf(rowNum)).deselectAll();
                    for (ApplicableToType bookingType : rule.getBookingType())
                        element("rules.cl.rule.booking.type",String.valueOf(rowNum)).selectByVisibleText(bookingType.value());
                    alert().enable();
                }
                if (rule.getRule() != null)
                    element("rules.cl.rule.rule", String.valueOf(rowNum)).selectByVisibleText(rule.getRule().value());
                if (rule.getElement() != null && rule.getElement().size()>0) {
                    for (int index=0; index<rule.getElement().size(); index++) {
                        if (rule.getElement().get(index).getFilter() != null)
                            element("rules.cl.rule.element", String.valueOf(rowNum)).input(rule.getElement().get(index).getFilter().getRealValue());
                        if (rule.getElement().get(index).getLinkText() != null)
                            element("rules.cl.rule.element.linktext", rule.getElement().get(index).getLinkText().getRealValue()).click();
                    }
                }
                if (rule.getFrom() != null)
                    element("rules.cl.rule.from", String.valueOf(rowNum)).selectByVisibleText(rule.getFrom().getRealValue());
                if (rule.getTo() != null)
                    element("rules.cl.rule.to", String.valueOf(rowNum)).selectByVisibleText(rule.getTo().getRealValue());
                if (rule.getMethod() != null)
                    element("rules.cl.rule.method", String.valueOf(rowNum)).selectByVisibleText(rule.getMethod().value());
                if (rule.getValue() != null)
                    element("rules.cl.rule.value", String.valueOf(rowNum)).input(rule.getValue().getRealValue());
                if (rule.getGroup() != null)
                    element("rules.cl.rule.group", String.valueOf(rowNum)).input(rule.getGroup().getRealValue());
            }
        }
    }

    public void assertConcentrationLimitRules(List<ConcentrationLimitRule> list) throws Exception{
        for(ConcentrationLimitRule rule : list){
            if(rule.isRemove() == null && rule.getName() == null) {
                assertThat("rules.cl.rule.delete", getConcentrationLimitRuleRow(rule)).present().isTrue();
            }
        }
    }

    public void editConcentrationLimitRules(List<ConcentrationLimitRule> oriList, List<ConcentrationLimitRule> newList) throws Exception{
        for(ConcentrationLimitRule rule : newList){
            if(rule.isRemove() != null && rule.isRemove()){
                element("rules.cl.rule.delete", getConcentrationLimitRuleRow(rule)).click();
            }else if(rule.getName() != null){
                ConcentrationLimitRule oriRule = (ConcentrationLimitRule) Biz.matchedObjectWithName(rule, oriList);
                String xpath = getConcentrationLimitRuleRow(oriRule);
                if (rule.getTrigger() != null)
                    element("rules.cl.rule.trigger.edit", xpath).input(rule.getTrigger().getRealValue());
                if (rule.getCurrency() != null)
                    element("rules.cl.rule.currency.edit", xpath).selectByVisibleText(rule.getCurrency().getRealValue());
                if (rule.getRule() != null)
                    element("rules.cl.rule.rule.edit", xpath).selectByVisibleText(rule.getRule().value());
                if (rule.getElement() != null && !rule.getElement().isEmpty()) {
                    element("rules.cl.rule.element.edit", xpath).clear();
                    for (int index=0; index<rule.getElement().size(); index++) {
                        if (rule.getElement().get(index).getFilter() != null)
                            element("rules.cl.rule.element.edit", xpath).type(rule.getElement().get(index).getFilter().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                        if (rule.getElement().get(index).getLinkText() != null)
                            element("rules.cl.rule.element.linktext",rule.getElement().get(index).getLinkText().getRealValue()).click();
                    }
                }
                if (rule.getFrom() != null)
                    element("rules.cl.rule.from.edit", xpath).selectByVisibleText(rule.getFrom().getRealValue());
                if (rule.getTo() != null)
                    element("rules.cl.rule.to.edit", xpath).selectByVisibleText(rule.getTo().getRealValue());
                if (rule.getMethod() != null)
                    element("rules.cl.rule.method.edit", xpath).selectByVisibleText(rule.getMethod().value());
                if (rule.getValue() != null)
                    element("rules.cl.rule.value.edit", xpath).input(rule.getValue().getRealValue());
                if (rule.getGroup() != null)
                    element("rules.cl.rule.group.edit", xpath).input(rule.getGroup().getRealValue());
            }
        }
        setupConcentrationLimitRules(newList);
    }

    public void saveConcentrationLimitRules() throws Exception{
        element("rules.cl.save").click();
    }

    public void exitConcentrationLimitRules() throws Exception{
        element("rules.cl.rule.exit").click();
    }

    private String getConcentrationLimitRuleRow(ConcentrationLimitRule rule) {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,##0.00");
        StringBuffer xpath = new StringBuffer();
        if(rule.getTrigger() != null)
            xpath.append("[td/input[contains(@id,'_trigger') or contains(@name,'_ruleTrigger') and @value='").append(format.format(Float.parseFloat(rule.getTrigger().getRealValue()))).append("']]");
        if(rule.getCurrency() != null)
            xpath.append("[td/select[contains(@id,'clCurrency') or contains(@name,'triggerCcy') and option[text()='").append(rule.getCurrency().getRealValue()).append("']]]");
        if(rule.getRule() != null)
            xpath.append("[td/select[contains(@id,'clrule') or contains(@name,'clrule') and option[text()='").append(rule.getRule().value()).append("']]]");
//        if(rule.getElement() != null) {
////            xpath.append("[td/input[contains(@id,'_ruleElement') or contains(@name,'ruleElement') and contains(@value,'" + rule.getElement().getRealValue() + ",')]]");
//            xpath.append("[td/input[contains(@id,'_ruleElement') or contains(@name,'ruleElement') and @value='" + rule.getElement().getRealValue() + ", ']]");
//        }
        if(rule.getFrom() != null)
            xpath.append("[td/select[(contains(@id,'concentrationLimits') or contains(@name,'concentrationLimit')) and (contains(@id,'fromRating') or contains(@name,'fromRating')) and option[text()='").append(rule.getFrom().getRealValue()).append("']]]");
        if(rule.getTo() != null)
            xpath.append("[td/select[(contains(@id,'concentrationLimits') or contains(@name,'concentrationLimit')) and (contains(@id,'toRating') or contains(@name,'toRating')) and option[text()='").append(rule.getTo().getRealValue()).append("']]]");
//        if(rule.getMethod() != null)
//            xpath.append("[td/select[(contains(@id,'concentrationLimits') or contains(@name,'concentrationLimit')) and (contains(@id,'clmethod') or contains(@name,'clmethod')) and option[text()='" + rule.getMethod().value() + "']]]");
        if(rule.getValue() != null)
            xpath.append("[td/input[contains(@name,'_clvalue') or contains(@id,'_clvalue') and @value='").append(format.format(Float.parseFloat(rule.getValue().getRealValue()))).append("']]");
        if(rule.getGroup() != null)
            xpath.append("[td/input[contains(@name,'_groupId') or contains(@id,'_groupId') and @value='").append(rule.getGroup().getRealValue()).append("']]");
        return xpath.toString();
    }
    //Concentration Limit Rules above

    //Interest Rules and WHT Rules below
    public void switchToInterestRuleAndWhtRuleTab() throws Exception{
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("rules.ir.rule.tab").click();
    }

    public void setupInterestRule(InterestRule interestRule) throws Exception{
        if(interestRule.getAgreementRate() != null)
            element("rules.ir.rule.agrrate").selectByVisibleText(interestRule.getAgreementRate().getRealValue());

        if(interestRule.getBpSpread() != null && !interestRule.getBpSpread().isEmpty()){
            for(int i = 0;i < interestRule.getBpSpread().size(); i++){
                int rowNum = element("rules.ir.intrule.rows").getNumberOfMatches();
                BpSpread bpSpread = interestRule.getBpSpread().get(i);
                if(bpSpread.isRemove() == null && bpSpread.getName() == null ){
                    if(i != 0 || rowNum == 0){
                        element("rules.ir.intrule.add").click();
                        rowNum = element("rules.ir.intrule.rows").getNumberOfMatches();
                    }
                    if(bpSpread.getEffectiveDate() != null)
                        element("rules.ir.intrule.effdate", String.valueOf(rowNum)).input(bpSpread.getEffectiveDate().getRealValue());
                    if(bpSpread.getEffectiveValue() != null)
                        element("rules.ir.intrule.effval", String.valueOf(rowNum)).input(bpSpread.getEffectiveValue().getRealValue());
                }
            }
        }

        if(interestRule.getReinvestRate() != null)
            element("rules.ir.intrule.reinrate").selectByVisibleText(interestRule.getReinvestRate().getRealValue());
    }

    public void editInterestRule(InterestRule oriInterestRule, InterestRule newInterestRule) throws Exception{
        if(newInterestRule.getBpSpread() != null && !newInterestRule.getBpSpread().isEmpty()){
            for(BpSpread bpSpread : newInterestRule.getBpSpread()){
                if(bpSpread.isRemove() != null){
                    element("rules.ir.intrule.delete", getInterestRuleOrWhtRuleRow(bpSpread)).click();
                }else if(bpSpread.getName() != null){
                    BpSpread oriBpSpread = (BpSpread) Biz.matchedObjectWithName(bpSpread, oriInterestRule.getBpSpread());
                    if(bpSpread.getEffectiveDate() != null)
                        element("rules.ir.intrule.effdate.edit", getInterestRuleOrWhtRuleRow(oriBpSpread)).input(bpSpread.getEffectiveDate().getRealValue());
                    if(bpSpread.getEffectiveValue() != null)
                        element("rules.ir.intrule.effval.edit", getInterestRuleOrWhtRuleRow(oriBpSpread)).input(bpSpread.getEffectiveValue().getRealValue());
                }
            }
        }
        setupInterestRule(newInterestRule);
    }

    public void setupWhtRule(WhtRule whtRule) throws Exception{
        if(whtRule.getWhtRate() != null)
            element("rules.ir.whtrule.rate").selectByVisibleText(whtRule.getWhtRate().getRealValue());

        if(whtRule.getWhtApplied() != null && !whtRule.getWhtApplied().isEmpty()){
            for(int i = 0; i < whtRule.getWhtApplied().size(); i++){
                int rowNum = element("rules.ir.whtrule.rows").getNumberOfMatches();
                WhtApplied whtApplied = whtRule.getWhtApplied().get(i);
                if(whtApplied.isRemove() == null && whtApplied.getName() == null){
                    if(i != 0 || rowNum == 0){
                        element("rules.ir.whtrule.add").click();
                        rowNum = element("rules.ir.whtrule.rows").getNumberOfMatches();
                    }
                    rowNum = element("rules.ir.whtrule.rows").getNumberOfMatches();
                    if(whtApplied.getEffectiveDate() != null)
                        element("rules.ir.whtrule.effdate", String.valueOf(rowNum)).input(whtApplied.getEffectiveDate().getRealValue());
                    if(whtApplied.isApplied() != null)
                        element("rules.ir.whtrule.applied", String.valueOf(rowNum)).check(whtApplied.isApplied());
                }
            }
        }

        if(whtRule.getApplicableParty() != null)
            element("rules.ir.whtrule.appparty").selectByVisibleText(whtRule.getApplicableParty().getRealValue());
    }

    public void editWhtRule(WhtRule oriWhtRule, WhtRule newWhtRule) throws Exception{
        if(newWhtRule.getWhtApplied() != null && !newWhtRule.getWhtApplied().isEmpty()){
            for(WhtApplied whtApplied : newWhtRule.getWhtApplied()){
                if(whtApplied.isRemove() != null){
                    element("rules.ir.whtrule.delete", getInterestRuleOrWhtRuleRow(whtApplied)).click();
                }else if(whtApplied.getName() != null){
                    WhtApplied oriWhtApplied = (WhtApplied) Biz.matchedObjectWithName(whtApplied, oriWhtRule.getWhtApplied());
                    if(whtApplied.getEffectiveDate() != null)
                        element("rules.ir.whtrule.effdate.edit", getInterestRuleOrWhtRuleRow(oriWhtApplied)).input(whtApplied.getEffectiveDate().getRealValue());
                    if(whtApplied.isApplied() != null)
                        element("rules.ir.whtrule.applied.edit", getInterestRuleOrWhtRuleRow(oriWhtApplied)).check(whtApplied.isApplied());
                }
            }
        }
        setupWhtRule(newWhtRule);
    }

    public void saveInterestRuleAndWhtRule() throws Exception{
        element("rules.ir.save").click();
    }

    public void exitInterestRuleAndWhtRule() throws Exception{
        element("rules.ir.exit").click();
    }

    public String getInterestRuleOrWhtRuleRow(Object obj) throws Exception{
        StringBuffer xpath = new StringBuffer();

        Method[] methods = obj.getClass().getDeclaredMethods();

        for(Method method : methods){
            if((method.getReturnType() == StringType.class || method.getReturnType() == boolean.class || method.getReturnType() == Boolean.class)
                    && method.invoke(obj) != null){
                if(method.getName().equalsIgnoreCase("getEffectiveDate")){
                    StringType value = (StringType) method.invoke(obj);
                    xpath.append("[td/input[contains(@name,'effectiveDateStr') and @value='").append(value.getRealValue()).append("']]");
                }else if(method.getName().equalsIgnoreCase("getEffectiveValue")){
                    StringType value = (StringType) method.invoke(obj);
                    xpath.append("[td/input[contains(@name,'effectiveValueStr') and @value='").append(value.getRealValue()).append("']]");
                }else if(method.getName().equalsIgnoreCase("isApplied")){
                    boolean flag = (boolean) method.invoke(obj);
                    if(flag){
                        xpath.append("[td/input[contains(@name,'applied') and @checked='checked']]");
                    }else{
                        xpath.append("[td/input[contains(@name,'applied') and not(@checked = 'checked')]]");
                    }
                }
            }
        }
        return xpath.toString();
    }
    //Interest Rules and WHT Rules  above

    //AutoBooking Rules below
    public void switchToAutoBookingRuleTab() throws Exception{
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("rules.ab.tab").click();
    }

    public void setupAutoBookingRule(List<AutoBookingRule> list) throws Exception{
        for(AutoBookingRule autoBookingRule : list){
            StringBuffer appTo = new StringBuffer();
            if(autoBookingRule.getApplicableTo() != null) {
                String val = autoBookingRule.getApplicableTo().value();
                if(val.contains("TSA"))
                    val = val.replace("TSA", "Cashflow");
                appTo.append("//tr[normalize-space(td)='" + val + "']");
            }
            if(autoBookingRule.getAssetType() != null)
                element("rules.ab.assettype", appTo.toString()).selectByVisibleText(autoBookingRule.getAssetType().getRealValue());
            if(autoBookingRule.getTolerance() != null)
                element("rules.ab.tolerance", appTo.toString()).selectByVisibleText(autoBookingRule.getTolerance().value());
            if(autoBookingRule.getAmount() != null)
                element("rules.ab.amount", appTo.toString()).input(autoBookingRule.getAmount().getRealValue());
            if(autoBookingRule.getValue() != null)
                element("rules.ab.value", appTo.toString()).selectByVisibleText(autoBookingRule.getValue().value());
            if(autoBookingRule.getMovement() != null && !autoBookingRule.getMovement().isEmpty()){
                element("rules.ab.movement", appTo.toString()).deselectAll();
                for(BookingMovementType type: autoBookingRule.getMovement()){
                    element("rules.ab.movement", appTo.toString()).selectByVisibleText(type.value());
                }
            }
            if(autoBookingRule.getBookingStatus() != null)
                element("rules.ab.bookingstatus", appTo.toString()).selectByVisibleText(autoBookingRule.getBookingStatus().value());
            if(autoBookingRule.isCreateWithLetter() != null)
                element("rules.ab.createwithlet", appTo.toString()).check(autoBookingRule.isCreateWithLetter());
        }
    }

    public void saveAutoBookingRule() throws Exception{
        element("rules.ab.save").click();
    }

    public void exitAutoBookingRule() throws Exception{
        element("rules.ab.exit").click();
    }
    //AutoBooking Rules above

    //Haircut Rules below
    public void switchToHaircutTab() throws Exception{
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("rules.hc.tab").click();
    }

    public void setupHaircutRule(HaircutRule haircutRule) throws Exception{
        if(haircutRule.getHaircutSchedule() != null)
            setupHaircutSchedule(haircutRule.getHaircutSchedule());
        if(haircutRule.getHaircutAdjustment() != null && !haircutRule.getHaircutAdjustment().isEmpty())
            setupHaircutAdjustment(haircutRule.getHaircutAdjustment());
    }

    public void assertHaircutRule(HaircutRule haircutRule) throws Exception{
        if(haircutRule.getHaircutSchedule() != null)
            assertHaircutSchedule(haircutRule.getHaircutSchedule());
        if(haircutRule.getHaircutAdjustment() != null && !haircutRule.getHaircutAdjustment().isEmpty())
            assertHaircutAdjustment(haircutRule.getHaircutAdjustment());
    }

    private void assertHaircutAdjustment(List<HaircutAdjustment> list) throws Exception{
        for(HaircutAdjustment haircutAdjustment : list){
            StringBuffer xpath = new StringBuffer();
            if(haircutAdjustment.getType() != null)
                xpath.append("[//select[@class='haircutAdjustmentTypeClass']/option[text()='").append(haircutAdjustment.getType().getRealValue()).append("']]");
            if(haircutAdjustment.getInstrumentField() != null)
                xpath.append("[//select[@class='instrumentFieldClass']/option[text()='").append(haircutAdjustment.getInstrumentField().getRealValue()).append("']]");
            if(haircutAdjustment.getRule() != null)
                xpath.append("[//select[@class='haircutAdjRuleClass' or contains(@name,'haRule')]/option[text()='").append(haircutAdjustment.getRule().getRealValue()).append("']]");
//            if(haircutAdjustment.getValue() != null)
//                xpath.append("[//input[contains(@class,'haircutAdjValueClass') and @value='" + haircutAdjustment.getValue().getRealValue() + "']]");
            if(haircutAdjustment.getOperation() != null)
                xpath.append("[//select[@class='haircutAdjOperationClass' or contains(@name,'haOperation')]/option[text()='").append(haircutAdjustment.getOperation().getRealValue()).append("']]");
            if(haircutAdjustment.getValuationPercentage() != null)
                xpath.append("[//input[contains(@name,'valuation') and @value='").append(haircutAdjustment.getValuationPercentage().getRealValue()).append("']]");
            validateThat("rules.hc.adj.row.record", xpath.toString()).displayed().isTrue();
        }
    }

    private void assertHaircutSchedule(HaircutSchedule haircutSchedule) throws Exception{
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("0.00");
        if(haircutSchedule.getHaircutRule() == null || haircutSchedule.getHaircutRule() == HaircutRuleType.FIXED){
//            if(element("rules.hc.sche.fixval").isDisplayed())
//                validateThat("rules.hc.sche.fixval").selected().isTrue();
            if(haircutSchedule.getFixedValuation() != null)
                validateThat("rules.hc.sche.fixval.input").attributeValueOf("value").isEqualToIgnoringWhitespace(format.format(Float.parseFloat(haircutSchedule.getFixedValuation().getRealValue())));
        }else{
            assertThat("rules.hc.sche.type").selected().isTrue();
            if(haircutSchedule.getLeftvaluationBasis() != null)
                validateThat("rules.hc.sche.left.valbasis").selectedText().isEqualToIgnoringWhitespace(haircutSchedule.getLeftvaluationBasis().getRealValue());
            if(haircutSchedule.isLeftCheck() != null){
                validateThat("rules.hc.sche.left.check").selected().isEqualTo(haircutSchedule.isLeftCheck());
                if(haircutSchedule.isLeftCheck()){
                    if(haircutSchedule.getLeftApply() != null)
                        validateThat("rules.hc.sche.left.apply").selectedText().isEqualToIgnoringWhitespace(haircutSchedule.getLeftApply().getRealValue());
                    if(haircutSchedule.getLeftValuationBasisRuleDetail() != null && !haircutSchedule.getLeftValuationBasisRuleDetail().isEmpty()){
                        for(TimeBasedHairCutSchedule timeBasedHairCutSchedule : haircutSchedule.getLeftValuationBasisRuleDetail()){
                            StringBuffer xpath = new StringBuffer();
                            if(timeBasedHairCutSchedule.getRatingInfo() != null){
                                if(timeBasedHairCutSchedule.getRatingInfo().getRatingFrom() != null)
                                    xpath.append("[//th[5]/select[@id='headFromRatingLevel'][option[text()='").append(timeBasedHairCutSchedule.getRatingInfo().getRatingFrom().getRealValue()).append("']]]");
                                if(timeBasedHairCutSchedule.getRatingInfo().getRatingTo() != null)
                                    xpath.append("[//th[6]/select[@id='headFromRatingLevel'][option[text()='").append(timeBasedHairCutSchedule.getRatingInfo().getRatingTo().getRealValue()).append("']]]");
                            }
                            if(timeBasedHairCutSchedule.getTimeInfo() != null && !timeBasedHairCutSchedule.getTimeInfo().isEmpty()){
                                for(TimeInfo timeInfo : timeBasedHairCutSchedule.getTimeInfo()){
                                    if(timeInfo.getTimeFrom() != null)
                                        xpath.append("[//tr[position()>1]//input[contains(@id,'fromTime') and @value='").append(timeInfo.getTimeFrom().getRealValue()).append("']]");
                                    if(timeInfo.getTimeTo() != null)
                                        xpath.append("[//tr[position()>1]//input[contains(@id,'toTime') and @value='").append(timeInfo.getTimeTo().getRealValue()).append("']]");
                                    if(timeInfo.getTimePercentage() != null)
                                        xpath.append("[//tr[position()>1]//input[contains(@id,'valuationPerc') and @value='").append(timeInfo.getTimePercentage().getRealValue()).append("']]");
                                }
                            }
                            validateThat("rules.hc.sche.left.rating.table", xpath.toString()).displayed().isTrue();
                        }
                    }
                    if(haircutSchedule.getRightvaluationBasis() != null)
                        validateThat("rules.hc.sche.right.valbasis").selectedText().isEqualToIgnoringWhitespace(haircutSchedule.getRightvaluationBasis().getRealValue());
                    if(haircutSchedule.isRightCheck() != null)
                        validateThat("rules.hc.sche.right.check").selected().isEqualTo(haircutSchedule.isRightCheck());
                    if(haircutSchedule.isRightCheck() != null && haircutSchedule.isRightCheck()){
                        if(haircutSchedule.getRightApply() != null)
                            validateThat("rules.hc.sche.right.apply").selectedText().isEqualToIgnoringWhitespace(haircutSchedule.getRightApply().getRealValue());
                        if(haircutSchedule.getRightValuationBasisRuleDetail() != null && !haircutSchedule.getRightValuationBasisRuleDetail().isEmpty()){
                            for(TimeBasedHairCutSchedule timeBasedHairCutSchedule : haircutSchedule.getRightValuationBasisRuleDetail()){
                                StringBuffer xpath = new StringBuffer();
                                if(timeBasedHairCutSchedule.getRatingInfo() != null){
                                    if(timeBasedHairCutSchedule.getRatingInfo().getRatingFrom() != null)
                                        xpath.append("[//th[5]/select[@id='headFromRatingLevel'][option[text()='").append(timeBasedHairCutSchedule.getRatingInfo().getRatingFrom().getRealValue()).append("']]]");
                                    if(timeBasedHairCutSchedule.getRatingInfo().getRatingTo() != null)
                                        xpath.append("[//th[6]/select[@id='headFromRatingLevel'][option[text()='").append(timeBasedHairCutSchedule.getRatingInfo().getRatingTo().getRealValue()).append("']]]");
                                }
                                if(timeBasedHairCutSchedule.getTimeInfo() != null && !timeBasedHairCutSchedule.getTimeInfo().isEmpty()){
                                    for(TimeInfo timeInfo : timeBasedHairCutSchedule.getTimeInfo()){
                                        if(timeInfo.getTimeFrom() != null)
                                            xpath.append("[//tr[position()>1]//input[contains(@id,'fromTime') and @value='").append(timeInfo.getTimeFrom().getRealValue()).append("']]");
                                        if(timeInfo.getTimeTo() != null)
                                            xpath.append("[//tr[position()>1]//input[contains(@id,'toTime') and @value='").append(timeInfo.getTimeTo().getRealValue()).append("']]");
                                        if(timeInfo.getTimePercentage() != null)
                                            xpath.append("[//tr[position()>1]//input[contains(@id,'valuationPerc') and @value='").append(timeInfo.getTimePercentage().getRealValue()).append("']]");
                                    }
                                }
                                validateThat("rules.hc.sche.right.rating.table", xpath.toString()).displayed().isTrue();
                            }
                        }
                    }
                }
            }
        }
    }

    public void editHaircutRule(HaircutRule oriHaircutRule, HaircutRule newHaircutRule) throws Exception{
        if(oriHaircutRule!=null && newHaircutRule!=null) {
            if (newHaircutRule.getHaircutSchedule() != null) {
                if (newHaircutRule.getHaircutSchedule().getHaircutRule() != null && newHaircutRule.getHaircutSchedule().getHaircutRule() == HaircutRuleType.TIME_BASED)
                    editHaircutSchedule(oriHaircutRule.getHaircutSchedule(), newHaircutRule.getHaircutSchedule());
            }
            if (newHaircutRule.getHaircutAdjustment() != null && !newHaircutRule.getHaircutAdjustment().isEmpty())
                editHaircutAdjustment(oriHaircutRule.getHaircutAdjustment(), newHaircutRule.getHaircutAdjustment());
        }
        setupHaircutRule(newHaircutRule);
    }

    public void saveHaircutRule() throws Exception{
        element("rules.hc.save").clickByJavaScript();
    }

    public void exitHaircutRule() throws Exception{
        element("rules.hc.exit").click();
    }

    private void setupHaircutSchedule(HaircutSchedule haircutSchedule) throws Exception{
        if(haircutSchedule.getHaircutRule() == null || haircutSchedule.getHaircutRule().value().equalsIgnoreCase(HaircutRuleType.FIXED.value()) ){
            if(element("rules.hc.sche.fixval").isDisplayed())
                element("rules.hc.sche.fixval").check(true);
            if(haircutSchedule.getFixedValuation() != null)
                if (element("rules.hc.sche.fixval.input").isDisplayed()) {
                    element("rules.hc.sche.fixval.input").input(haircutSchedule.getFixedValuation().getRealValue());
                }else if (element("rules.hc.sche.fixval.input.agmt").isDisplayed())
                    element("rules.hc.sche.fixval.input.agmt").input(haircutSchedule.getFixedValuation().getRealValue());
                else if (element("rules.hc.sche.fixval.input.equity").isDisplayed())
                    element("rules.hc.sche.fixval.input.equity").input(haircutSchedule.getFixedValuation().getRealValue());

        }else{
            element("rules.hc.sche.type").check(true);
            if(haircutSchedule.getLeftvaluationBasis() != null)
                element("rules.hc.sche.left.valbasis").selectByVisibleText(haircutSchedule.getLeftvaluationBasis().getRealValue());
            if(haircutSchedule.isLeftCheck() != null){
                element("rules.hc.sche.left.check").check(haircutSchedule.isLeftCheck());
                if(haircutSchedule.isLeftCheck()){
                    if(haircutSchedule.getLeftApply() != null)
                        element("rules.hc.sche.left.apply").selectByVisibleText(haircutSchedule.getLeftApply().getRealValue());
                    if(haircutSchedule.getLeftValuationBasisRuleDetail() != null && !haircutSchedule.getLeftValuationBasisRuleDetail().isEmpty()){
                        int tableNum = element("rules.hc.sche.left.table").getNumberOfMatches();
                        for(int i = 0; i < haircutSchedule.getLeftValuationBasisRuleDetail().size(); i++){
                            TimeBasedHairCutSchedule timeBasedHairCutSchedule = haircutSchedule.getLeftValuationBasisRuleDetail().get(i);
                            if(timeBasedHairCutSchedule.isRemove() == null && timeBasedHairCutSchedule.getName() == null){
                                if(i != 0 || tableNum != 1){
                                    element("rules.hc.sche.left.add.rating", String.valueOf(tableNum)).clickByJavaScript();
                                    tableNum = element("rules.hc.sche.left.table").getNumberOfMatches();
                                }
                                if(timeBasedHairCutSchedule.getRatingInfo() != null){
                                    RatingInfo ratingInfo = timeBasedHairCutSchedule.getRatingInfo();
                                    if(ratingInfo.isRemove() == null && ratingInfo.getName() == null) {
                                        if (ratingInfo.getRatingFrom() != null)
                                            element("rules.hc.sche.left.ratingfrom", String.valueOf(tableNum)).selectByVisibleText(ratingInfo.getRatingFrom().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                                        if (ratingInfo.getRatingTo() != null)
                                            element("rules.hc.sche.left.ratingto", String.valueOf(tableNum)).selectByVisibleText(ratingInfo.getRatingTo().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                                    }
                                }
                                if(timeBasedHairCutSchedule.getTimeInfo() != null && !timeBasedHairCutSchedule.getTimeInfo().isEmpty()){
                                    int rowNum = element("rules.hc.sche.left.time.row", String.valueOf(tableNum)).getNumberOfMatches();
                                    for(int j = 0; j < timeBasedHairCutSchedule.getTimeInfo().size(); j++){
                                        TimeInfo timeInfo =  timeBasedHairCutSchedule.getTimeInfo().get(j);
                                        if(j != 0 || rowNum != 2){
                                            element("rules.hc.sche.left.time.add", String.valueOf(tableNum)).clickByJavaScript();
                                            rowNum = element("rules.hc.sche.left.time.row", String.valueOf(tableNum)).getNumberOfMatches();
                                        }
                                        if(timeInfo.getTimeFrom() != null)
                                            element("rules.hc.sche.left.time.from", String.valueOf(tableNum), String.valueOf(rowNum)).input(timeInfo.getTimeFrom().getRealValue());
                                        if(timeInfo.getTimeTo() != null)
                                            element("rules.hc.sche.left.time.to", String.valueOf(tableNum), String.valueOf(rowNum)).input(timeInfo.getTimeTo().getRealValue()).fireEvent("onblur");
                                        if(timeInfo.getTimePercentage() != null)
                                            element("rules.hc.sche.left.time.percen", String.valueOf(tableNum), String.valueOf(rowNum)).input(timeInfo.getTimePercentage().getRealValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if(haircutSchedule.getRightvaluationBasis() != null)
                element("rules.hc.sche.right.valbasis").selectByVisibleText(haircutSchedule.getRightvaluationBasis().getRealValue());
            if(haircutSchedule.isRightCheck() != null && haircutSchedule.isRightCheck()){
                element("rules.hc.sche.right.check").check(haircutSchedule.isRightCheck());
                if(haircutSchedule.getRightApply() != null)
                    element("rules.hc.sche.right.apply").selectByVisibleText(haircutSchedule.getRightApply().getRealValue());
                if(haircutSchedule.getRightValuationBasisRuleDetail() != null && !haircutSchedule.getRightValuationBasisRuleDetail().isEmpty()){
                    int tableNum = element("rules.hc.sche.right.table").getNumberOfMatches();
                    for(int i = 0; i < haircutSchedule.getRightValuationBasisRuleDetail().size(); i++){
                        TimeBasedHairCutSchedule timeBasedHairCutSchedule = haircutSchedule.getRightValuationBasisRuleDetail().get(i);
                        if(timeBasedHairCutSchedule.isRemove() == null && timeBasedHairCutSchedule.getName() ==  null){
                            if(i != 0 || tableNum != 1){
                                element("rules.hc.sche.right.add.rating", String.valueOf(tableNum)).clickByJavaScript();
                                tableNum = element("rules.hc.sche.right.table").getNumberOfMatches();
                            }
                            if(timeBasedHairCutSchedule.getRatingInfo() != null){
                                RatingInfo ratingInfo = timeBasedHairCutSchedule.getRatingInfo();
                                if(ratingInfo.isRemove() == null && ratingInfo.getName() == null) {
                                    if (ratingInfo.getRatingFrom() != null)
                                        element("rules.hc.sche.right.ratingfrom", String.valueOf(tableNum)).selectByVisibleText(ratingInfo.getRatingFrom().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                                    if (ratingInfo.getRatingTo() != null)
                                        element("rules.hc.sche.right.ratingto", String.valueOf(tableNum)).selectByVisibleText(ratingInfo.getRatingTo().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                                }
                            }
                            if(timeBasedHairCutSchedule.getTimeInfo() != null && !timeBasedHairCutSchedule.getTimeInfo().isEmpty()){
                                int rowNum = element("rules.hc.sche.right.time.row", String.valueOf(tableNum)).getNumberOfMatches();
                                for(int j = 0; j < timeBasedHairCutSchedule.getTimeInfo().size(); j++){
                                    TimeInfo timeInfo = timeBasedHairCutSchedule.getTimeInfo().get(j);
                                    if(j != 0 || rowNum != 2){
                                        element("rules.hc.sche.right.time.add", String.valueOf(tableNum)).clickByJavaScript();
                                        rowNum = element("rules.hc.sche.right.time.row", String.valueOf(tableNum)).getNumberOfMatches();
                                    }
                                    if(timeInfo.getTimeFrom() != null)
                                        element("rules.hc.sche.right.time.from", String.valueOf(tableNum), String.valueOf(rowNum)).input(timeInfo.getTimeFrom().getRealValue());
                                    if(timeInfo.getTimeTo() != null)
                                        element("rules.hc.sche.right.time.to", String.valueOf(tableNum), String.valueOf(rowNum)).input(timeInfo.getTimeTo().getRealValue()).fireEvent("onblur");
                                    if(timeInfo.getTimePercentage() != null)
                                        element("rules.hc.sche.right.time.percen", String.valueOf(tableNum), String.valueOf(rowNum)).input(timeInfo.getTimePercentage().getRealValue());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void setupHaircutAdjustment(List<HaircutAdjustment> list) throws Exception{
        for(HaircutAdjustment haircutAdjustment : list){
            if(haircutAdjustment.isRemove() == null && haircutAdjustment.getName() == null){
                element("rules.hc.adj.add").clickByJavaScript();
                int rowNum = element("rules.hc.adj.row").getNumberOfMatches();
                if(haircutAdjustment.getType() != null)
                    element("rules.hc.adj.type", String.valueOf(rowNum)).selectByVisibleText(haircutAdjustment.getType().getRealValue());
                if(haircutAdjustment.getInstrumentField() != null)
                    element("rules.hc.adj.insfield", String.valueOf(rowNum)).selectByVisibleText(haircutAdjustment.getInstrumentField().getRealValue());
                if(haircutAdjustment.getRule() != null)
                    element("rules.hc.adj.rule", String.valueOf(rowNum)).selectByVisibleText(haircutAdjustment.getRule().getRealValue());
                if(haircutAdjustment.getValue() != null && haircutAdjustment.getValue().size()>0) {
                    for (SimpleSearch value:haircutAdjustment.getValue()) {
                        if (value.getFilter() != null)
                            element("rules.hc.adj.value", String.valueOf(rowNum)).type(value.getFilter().getRealValue());
                        if (value.getLinkText() != null)
                            element("rules.hc.adj.value.linktext", value.getLinkText().getRealValue()).click();
                    }
                }
                if(haircutAdjustment.getOperation() != null)
                    element("rules.hc.adj.ope", String.valueOf(rowNum)).selectByVisibleText(haircutAdjustment.getOperation().getRealValue());
                if(haircutAdjustment.getValuationPercentage() != null)
                    element("rules.hc.adj.valper", String.valueOf(rowNum)).input(haircutAdjustment.getValuationPercentage().getRealValue());
            }
        }
    }

    private void editHaircutSchedule(HaircutSchedule oriHaircutSchedule, HaircutSchedule newHaircutSchedule) throws Exception{
        if(newHaircutSchedule.isLeftCheck() != null){
            if(newHaircutSchedule.isLeftCheck()){
                if(newHaircutSchedule.getLeftValuationBasisRuleDetail() != null && !newHaircutSchedule.getLeftValuationBasisRuleDetail().isEmpty()){
                    for(TimeBasedHairCutSchedule timeBasedHairCutSchedule : newHaircutSchedule.getLeftValuationBasisRuleDetail()){
                        if((timeBasedHairCutSchedule.isRemove() != null
                                && timeBasedHairCutSchedule.isRemove())
                                || (timeBasedHairCutSchedule.getRatingInfo() != null
                                && timeBasedHairCutSchedule.getRatingInfo().isRemove() != null
                                && timeBasedHairCutSchedule.getRatingInfo().isRemove())){
                            element("rules.hc.sche.left.del.rating", getHaircutScheduleTable(timeBasedHairCutSchedule.getRatingInfo(), timeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(timeBasedHairCutSchedule.getRatingInfo())).click();
                        }else if(timeBasedHairCutSchedule.getName() != null){
                            TimeBasedHairCutSchedule oriTimeBasedHairCutSchedule = (TimeBasedHairCutSchedule) Biz.matchedObjectWithName(timeBasedHairCutSchedule, oriHaircutSchedule.getLeftValuationBasisRuleDetail());
                            if(timeBasedHairCutSchedule.getRatingInfo() != null && timeBasedHairCutSchedule.getRatingInfo().getName() != null){
                                RatingInfo ratingInfo = timeBasedHairCutSchedule.getRatingInfo();
//                                RatingInfo oriRatingInfo = (RatingInfo) Biz.matchedObjectWithName(ratingInfo, Arrays.asList(oriTimeBasedHairCutSchedule.getRatingInfo()));
                                RatingInfo oriRatingInfo = oriTimeBasedHairCutSchedule.getRatingInfo();
                                if(ratingInfo.getRatingFrom() != null) {
                                    element("rules.hc.sche.left.ratingfrom.edit", getHaircutScheduleTable(oriRatingInfo, oriTimeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(oriRatingInfo)).selectByVisibleText(ratingInfo.getRatingFrom().getRealValue());
                                    oriRatingInfo.getRatingFrom().setValue(ratingInfo.getRatingFrom().getRealValue());
                                }
                                if(ratingInfo.getRatingTo() != null) {
                                    element("rules.hc.sche.left.ratingto.edit", getHaircutScheduleTable(oriRatingInfo, oriTimeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(oriRatingInfo)).selectByVisibleText(ratingInfo.getRatingTo().getRealValue());
                                    oriRatingInfo.getRatingTo().setValue(ratingInfo.getRatingTo().getRealValue());
                                }
                            }
                            if(timeBasedHairCutSchedule.getTimeInfo() != null && !timeBasedHairCutSchedule.getTimeInfo().isEmpty()){
                                for(TimeInfo timeInfo : timeBasedHairCutSchedule.getTimeInfo()){
                                    if(timeInfo.isRemove() != null && timeInfo.isRemove()){
                                        element("rules.hc.sche.left.time.del", getHaircutScheduleTable(timeBasedHairCutSchedule.getRatingInfo(), timeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(timeInfo)).click();
                                    }else if(timeInfo.getName() != null){
                                        TimeInfo oriTimeInfo = (TimeInfo) Biz.matchedObjectWithName(timeInfo, oriTimeBasedHairCutSchedule.getTimeInfo());
                                        if(timeInfo.getTimeFrom() != null) {
                                            element("rules.hc.sche.left.time.from.edit", getHaircutScheduleTable(oriTimeBasedHairCutSchedule.getRatingInfo(), oriTimeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(oriTimeInfo)).input(timeInfo.getTimeFrom().getRealValue());
                                            oriTimeInfo.getTimeFrom().setValue(timeInfo.getTimeFrom().getRealValue());
                                        }
                                        if(timeInfo.getTimeTo() != null) {
                                            element("rules.hc.sche.left.time.to.edit", getHaircutScheduleTable(oriTimeBasedHairCutSchedule.getRatingInfo(), oriTimeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(oriTimeInfo)).input(timeInfo.getTimeTo().getRealValue());
                                            oriTimeInfo.getTimeTo().setValue(timeInfo.getTimeTo().getRealValue());
                                        }
                                        if(timeInfo.getTimePercentage() != null) {
                                            element("rules.hc.sche.left.time.percen.edit", getHaircutScheduleTable(oriTimeBasedHairCutSchedule.getRatingInfo(), oriTimeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(oriTimeInfo)).input(timeInfo.getTimePercentage().getRealValue());
                                            oriTimeInfo.getTimePercentage().setValue(timeInfo.getTimePercentage().getRealValue());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if(newHaircutSchedule.isRightCheck()){
            if(newHaircutSchedule.getRightValuationBasisRuleDetail() != null && !newHaircutSchedule.getRightValuationBasisRuleDetail().isEmpty()){
                for(TimeBasedHairCutSchedule timeBasedHairCutSchedule : newHaircutSchedule.getRightValuationBasisRuleDetail()){
                    if((timeBasedHairCutSchedule.isRemove() != null
                            && timeBasedHairCutSchedule.isRemove())
                            || (timeBasedHairCutSchedule.getRatingInfo() != null
                            && timeBasedHairCutSchedule.getRatingInfo().isRemove() != null
                            && timeBasedHairCutSchedule.getRatingInfo().isRemove())){
                        element("rules.hc.sche.right.del.rating", getHaircutScheduleTable(timeBasedHairCutSchedule.getRatingInfo(), timeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(timeBasedHairCutSchedule.getRatingInfo())).click();
                    }else if(timeBasedHairCutSchedule.getName() != null){
                        TimeBasedHairCutSchedule oriTimeBasedHairCutSchedule = (TimeBasedHairCutSchedule) Biz.matchedObjectWithName(timeBasedHairCutSchedule, oriHaircutSchedule.getLeftValuationBasisRuleDetail());
                        if(timeBasedHairCutSchedule.getRatingInfo() != null && timeBasedHairCutSchedule.getRatingInfo().getName() != null){
                            RatingInfo ratingInfo = timeBasedHairCutSchedule.getRatingInfo();
//                            RatingInfo oriRatingInfo = (RatingInfo) Biz.matchedObjectWithName(ratingInfo, Arrays.asList(oriTimeBasedHairCutSchedule.getRatingInfo()));
                            RatingInfo oriRatingInfo = oriTimeBasedHairCutSchedule.getRatingInfo();
                            if(ratingInfo.getRatingFrom() != null) {
                                element("rules.hc.sche.right.ratingfrom.edit", getHaircutScheduleTable(oriRatingInfo, oriTimeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(oriRatingInfo)).selectByVisibleText(ratingInfo.getRatingFrom().getRealValue());
                                oriRatingInfo.getRatingFrom().setValue(ratingInfo.getRatingFrom().getRealValue());
                            }
                            if(ratingInfo.getRatingTo() != null) {
                                element("rules.hc.sche.right.ratingto.edit", getHaircutScheduleTable(oriRatingInfo, oriTimeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(oriRatingInfo)).selectByVisibleText(ratingInfo.getRatingTo().getRealValue());
                                oriRatingInfo.getRatingTo().setValue(ratingInfo.getRatingTo().getRealValue());
                            }
                        }
                        if(timeBasedHairCutSchedule.getTimeInfo() != null && !timeBasedHairCutSchedule.getTimeInfo().isEmpty()){
                            for(TimeInfo timeInfo : timeBasedHairCutSchedule.getTimeInfo()){
                                if(timeInfo.isRemove() != null && timeInfo.isRemove()){
                                    element("rules.hc.sche.right.time.del", getHaircutScheduleTable(timeBasedHairCutSchedule.getRatingInfo(), timeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(timeInfo)).click();
                                }else if(timeInfo.getName() != null){
                                    TimeInfo oriTimeInfo = (TimeInfo) Biz.matchedObjectWithName(timeInfo, oriTimeBasedHairCutSchedule.getTimeInfo());
                                    if(timeInfo.getTimeFrom() != null) {
                                        element("rules.hc.sche.right.time.from.edit", getHaircutScheduleTable(oriTimeBasedHairCutSchedule.getRatingInfo(), oriTimeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(oriTimeInfo)).input(timeInfo.getTimeFrom().getRealValue());
                                        oriTimeInfo.getTimeFrom().setValue(timeInfo.getTimeFrom().getRealValue());
                                    }
                                    if(timeInfo.getTimeTo() != null) {
                                        element("rules.hc.sche.right.time.to.edit", getHaircutScheduleTable(oriTimeBasedHairCutSchedule.getRatingInfo(), oriTimeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(oriTimeInfo)).input(timeInfo.getTimeTo().getRealValue());
                                        oriTimeInfo.getTimeTo().setValue(timeInfo.getTimeTo().getRealValue());
                                    }
                                    if(timeInfo.getTimePercentage() != null) {
                                        element("rules.hc.sche.right.time.percen.edit", getHaircutScheduleTable(oriTimeBasedHairCutSchedule.getRatingInfo(), oriTimeBasedHairCutSchedule.getTimeInfo()), getHaircutScheduleRow(oriTimeInfo)).input(timeInfo.getTimePercentage().getRealValue());
                                        oriTimeInfo.getTimePercentage().setValue(timeInfo.getTimePercentage().getRealValue());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void editHaircutAdjustment(List<HaircutAdjustment> oriList, List<HaircutAdjustment> newList) throws Exception{
        for(HaircutAdjustment haircutAdjustment : newList){
            if(haircutAdjustment.isRemove() != null && haircutAdjustment.isRemove()){
                element("rules.hc.adj.del",getHairAdjustmentRow(haircutAdjustment)).click();
            }else if(haircutAdjustment.getName() != null){
                HaircutAdjustment oriHaircutAdjustment = (HaircutAdjustment) Biz.matchedObjectWithName(haircutAdjustment, oriList);
                if(haircutAdjustment.getType() != null)
                    element("rules.hc.adj.type.edit", getHairAdjustmentRow(oriHaircutAdjustment)).selectByVisibleText(haircutAdjustment.getType().getRealValue());
                if(haircutAdjustment.getInstrumentField() != null)
                    element("rules.hc.adj.insfield.edit", getHairAdjustmentRow(oriHaircutAdjustment)).selectByVisibleText(haircutAdjustment.getInstrumentField().getRealValue());
                if(haircutAdjustment.getRule() != null)
                    element("rules.hc.adj.rule.edit", getHairAdjustmentRow(oriHaircutAdjustment)).selectByVisibleText(haircutAdjustment.getRule().getRealValue());
                if(haircutAdjustment.getValue() != null && haircutAdjustment.getValue().size()>0) {
                    element("rules.hc.adj.value.edit", getHairAdjustmentRow(oriHaircutAdjustment)).clear();
                    for (SimpleSearch value:haircutAdjustment.getValue()) {
                        if (value.getFilter()!=null)
                            element("rules.hc.adj.value.edit", getHairAdjustmentRow(oriHaircutAdjustment)).type(value.getFilter().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                        if (value.getLinkText()!=null)
                            element("rules.hc.adj.value.linktext", value.getLinkText().getRealValue()).click();
                    }
                }
                if(haircutAdjustment.getOperation() != null)
                    element("rules.hc.adj.ope.edit", getHairAdjustmentRow(oriHaircutAdjustment)).selectByVisibleText(haircutAdjustment.getOperation().getRealValue());
                if(haircutAdjustment.getValuationPercentage() != null)
                    element("rules.hc.adj.valper.edit", getHairAdjustmentRow(oriHaircutAdjustment)).input(haircutAdjustment.getValuationPercentage().getRealValue());
            }
        }
    }

    private String getHaircutScheduleTable(RatingInfo ratingInfo, List<TimeInfo> list) {
        StringBuffer xpath = new StringBuffer();
        if(ratingInfo != null)
            xpath.append("[//tr").append(getHaircutScheduleRow(ratingInfo)).append("]");
        if(list != null && !list.isEmpty()) {
            for (TimeInfo timeInfo : list) {
                xpath.append("[//tr").append(getHaircutScheduleRow(timeInfo)).append("]");
            }
        }
        return xpath.toString();
    }

    private String getHaircutScheduleRow(Object obj) {
        StringBuffer xpath = new StringBuffer();
        if(obj instanceof RatingInfo){
            RatingInfo rating = (RatingInfo) obj;
            if(rating.getRatingFrom() != null)
                xpath.append("[(th/select)[1][option[text='").append(rating.getRatingFrom().getRealValue()).append("']]]");
            if(rating.getRatingTo() != null)
                xpath.append("[(th/select)[2][option[text='").append(rating.getRatingTo().getRealValue()).append("']]]");
        }else if(obj instanceof TimeInfo){
            TimeInfo time = (TimeInfo) obj;
            if(time.getTimeFrom() != null)
                xpath.append("[td/input[contains(@id,'fromTime') or contains(@name,'fromTime') and @value='").append(time.getTimeFrom().getRealValue()).append("']]");
            if(time.getTimeTo() != null)
                xpath.append("[td/input[contains(@id,'toTime') or contains(@name,'toTime') and @value='").append(time.getTimeTo().getRealValue()).append("']]");
            if(time.getTimePercentage() != null)
                xpath.append("[td/input[contains(@id,'valuationPerc') or contains(@name,'valuationPerc') and @value='").append(time.getTimePercentage().getRealValue()).append("']]");
        }
        return xpath.toString();
    }

    private String getHairAdjustmentRow(HaircutAdjustment haircutAdjustment) throws Exception{
        StringBuffer xpath = new StringBuffer();
        Method[] methods = haircutAdjustment.getClass().getDeclaredMethods();
        for(Method method : methods){
            if(method.getName().startsWith("get")
                    && method.invoke(haircutAdjustment) != null
                    && method.getReturnType() == StringType.class){
                StringType value = (StringType) method.invoke(haircutAdjustment);
                if(method.getName().equals("getValue") || method.getName().equals("getValuationPercentage")){
                    xpath.append("[td/input[@value='").append(value.getRealValue()).append("']]");
                }else{
                    xpath.append("[td/select[option[text()='").append(value.getRealValue()).append("']]]");
                }
            }
        }
        return xpath.toString();
    }
    //Haircut Rules above
    public void setupAssetInfo(List<CollateralAssetClass> collateralAssetClassList) throws Exception{
        for (CollateralAssetClass assetClass : collateralAssetClassList) {
            if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
                // asset type row number
//                int r = 1;
                for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                    // click + icon to add a new asset type
                    String xpath = "//tr[contains(@id,'row_type_') and preceding-sibling::tr/td/b='" + assetClass.getCollateralAssetClassName().getRealValue()
                            + "'][(following-sibling::tr=//tr[td/b='" + assetClass.getCollateralAssetClassName().getRealValue()
                            + "']/following-sibling::tr[contains(@id,'row_class')][1]) or (count(//tr[td/b='" + assetClass.getCollateralAssetClassName().getRealValue()
                            + "']/following-sibling::tr[contains(@id,'row_class')])=0)][last()]";
                    element("er.add.new.asset", assetClass.getCollateralAssetClassName().getRealValue()).clickByJavaScript();
//                    setupEligibilityRulesTemplateAssetType(locator("er.asset.row",
//                            assetClass.getCollateralAssetClassName().getRealValue(), "last()").getExpression_(),
//                            assetType);
                    setupEligibilityRulesTemplateAssetType(xpath, assetType);
//                    r++;
                }
            }
            setupEligibilityRulesTemplateAssetClass(assetClass);
        }
    }

    public void deleteAssetType(String rowXpath) throws Exception {
        element("er.delete.type.rules", rowXpath).click();

    }

    public void modifyAssetInfo(List<CollateralAssetClass> oriCollateralAssetClassList,List<CollateralAssetClass> collateralAssetClassList) throws Exception {
        for (CollateralAssetClass assetClass : collateralAssetClassList) {
            if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
                int r = 1;// asset type row number
                for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                    // click + icon to add a new asset type
                    if (assetType.getName() != null) {
                        CollateralAssetClass oriClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriCollateralAssetClassList);
                        if (oriClass != null && oriClass.getCollateralAssetType() != null && oriClass.getCollateralAssetClassName() != null) {
                            CollateralAssetType oriAssetType = (CollateralAssetType) Biz.matchedObjectWithName(assetType, oriClass.getCollateralAssetType());
                            if (assetType.isRemove() != null && assetType.isRemove()){
                                deleteAssetType(locator("er.asset.row.edit", assetClass.getCollateralAssetClassName().getRealValue(),assetType.getCollateralAssetTypeName().getRealValue()).getExpression_());
                                if (assetType.isApply() != null && assetType.isApply()){
                                    if (alert().isPresent())
                                    alert().dismiss();
                                }
                                else {
                                    if (alert().isPresent())
                                    alert().accept();
                                }
                            }else {
                                modifyEligibilityRulesTemplateAssetType(locator("er.asset.row.edit",
                                        oriClass.getCollateralAssetClassName().getRealValue(), oriAssetType.getCollateralAssetTypeName().getRealValue()).getExpression_(),
                                        oriAssetType, assetType);
                            }
                        } else {
                            element("er.add.new.asset", assetClass.getCollateralAssetClassName().getRealValue()).clickByJavaScript();
                            setupEligibilityRulesTemplateAssetType(locator("er.asset.row",
                                    assetClass.getCollateralAssetClassName().getRealValue(), String.valueOf(r)).getExpression_(),
                                    assetType);
                            r++;
                        }
                    } else {
                        element("er.add.new.asset", assetClass.getCollateralAssetClassName().getRealValue()).clickByJavaScript();
                        setupEligibilityRulesTemplateAssetType(locator("er.asset.row",
                                assetClass.getCollateralAssetClassName().getRealValue(), String.valueOf(r)).getExpression_(),
                                assetType);
                        r++;
                    }
                }
            }
            if (assetClass.getName() != null) {
                CollateralAssetClass oriClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriCollateralAssetClassList);
                editEligibilityRulesTemplateAssetClass(oriClass, assetClass);
            } else
                setupEligibilityRulesTemplateAssetClass(assetClass);
        }
    }

    protected void modifyEligibilityRulesTemplateAssetType(String rowXpath,CollateralAssetType oriAssetType, CollateralAssetType assetType) throws Exception {
        if (assetType.getApplicableParty() != null && assetType.getApplicableParty().size()>0) {
            element("er.applicable.party", rowXpath).deselectAll();
            for (ApplicablePartyToType type : assetType.getApplicableParty()){
                element("er.applicable.party", rowXpath).selectByVisibleText(type.value());
            }
        }

        if (assetType.getTemplateVsAgreementEligibilityRules() != null ) {
            element("er.templateVsAgreementEligibilityRules", rowXpath).selectByVisibleText(assetType.getTemplateVsAgreementEligibilityRules().getRealValue());
        }

        if (assetType.getApplicableType() != null && assetType.getApplicableType().size()>0){
            element("er.applicable.type", rowXpath).deselectAll();
            for (ApplicableToType type : assetType.getApplicableType()){
                element("er.applicable.type", rowXpath).selectByVisibleText(type.value());
            }
        }

        if (assetType.getSettlementDate() != null)
            element("er.settlement.date", rowXpath).selectByVisibleText(assetType.getSettlementDate().getRealValue());

        if (assetType.getDeliveryPriority() != null)
            element("er.delivery.priority", rowXpath).selectByVisibleText(assetType.getDeliveryPriority().value());

        if (assetType.getRecallPriority() != null)
            element("er.recall.priority", rowXpath).selectByVisibleText(assetType.getRecallPriority().value());

        if (assetType.getAssetNote1() != null)
            element("er.asset.notes1", rowXpath).input(assetType.getAssetNote1().getRealValue());

        if (assetType.getAssetNote2() != null || assetType.getAssetNote3() != null){
            element("er.asset.expand",rowXpath).clickByJavaScript();
            if (assetType.getAssetNote2() != null)
                element("er.asset.notes2", rowXpath).input(assetType.getAssetNote2().getRealValue());
            if (assetType.getAssetNote3() != null)
                element("er.asset.notes3", rowXpath).input(assetType.getAssetNote3().getRealValue());
        }

        if (assetType.getHaircutRule() != null) {
            openAssetTypeRule(rowXpath);
            switchToHaircutTab();
            editHaircutRule(oriAssetType.getHaircutRule(),assetType.getHaircutRule());
            saveHaircutRule();
            exitHaircutRule();
        }
        // setup asset type eligibility Rules
        if (assetType.getEligibilityRule() != null) {
            openAssetTypeRule(rowXpath);
            switchToEligibilityRulesTab();
            editEligibilityRules(oriAssetType.getEligibilityRule(),assetType.getEligibilityRule());
            saveEligibilityRules();
            exitEligibilityRules();
        }
        // setup asset type concentration limit rules
        if (assetType.getConcentrationLimitRule() != null && !assetType.getConcentrationLimitRule().isEmpty()) {
            openAssetTypeRule(rowXpath);
            switchToConcentrationLimitRulesTab();
            editConcentrationLimitRules(oriAssetType.getConcentrationLimitRule(),assetType.getConcentrationLimitRule());
            saveConcentrationLimitRules();
            element("er.cl.rules.exit").click();
        }

    }

    protected void editEligibilityRulesTemplateAssetClass(CollateralAssetClass oriAssetClass,CollateralAssetClass assetClass) throws Exception {
        // setup asset class concentration limit rules
        if (assetClass.getConcentrationLimitRule() != null && !assetClass.getConcentrationLimitRule().isEmpty()) {
            openAssetClassRule(assetClass);
            switchToConcentrationLimitRulesTab();
            editConcentrationLimitRules(oriAssetClass.getConcentrationLimitRule(),assetClass.getConcentrationLimitRule());
            saveConcentrationLimitRules();
            exitConcentrationLimitRules();
        }

    }

    public void setupEligibilityRulesTemplateAssetType(String rowXpath, CollateralAssetType assetType) throws Exception {

        if (assetType.getCollateralAssetTypeName() != null) {
            element("er.asset.name", rowXpath).setValue("").type(assetType.getCollateralAssetTypeName().getRealValue());
            element("er.asset.name.linktext", assetType.getCollateralAssetTypeName().getRealValue()).click();
        }

        if (assetType.getTemplateVsAgreementEligibilityRules() != null) {
            element("ap.asset.type.template.vs.agreement.eligibility.rule", rowXpath).selectByVisibleText(assetType.getTemplateVsAgreementEligibilityRules().getRealValue());
        }

        if (assetType.getApplicableParty() != null && assetType.getApplicableParty().size()>0) {
            element("er.applicable.party", rowXpath).deselectAll();
            for (ApplicablePartyToType type : assetType.getApplicableParty()){
                element("er.applicable.party", rowXpath).selectByVisibleText(type.value());
            }
        }

        if (assetType.getApplicableType() != null && assetType.getApplicableType().size()>0){
            element("er.applicable.type", rowXpath).deselectAll();
            for (ApplicableToType type : assetType.getApplicableType()){
                element("er.applicable.type", rowXpath).selectByVisibleText(type.value());
            }
        }

        if (assetType.getSettlementDate() != null)
            element("er.settlement.date", rowXpath).selectByVisibleText(assetType.getSettlementDate().getRealValue());

        if (assetType.getDeliveryPriority() != null)
            element("er.delivery.priority", rowXpath).selectByVisibleText(assetType.getDeliveryPriority().value());

        if (assetType.getRecallPriority() != null)
            element("er.recall.priority", rowXpath).selectByVisibleText(assetType.getRecallPriority().value());

        if (assetType.getAssetNote1() != null)
            element("er.asset.notes1", rowXpath).input(assetType.getAssetNote1().getRealValue());

        if (assetType.getAssetNote2() != null || assetType.getAssetNote3() != null){
            element("er.asset.expand",rowXpath).clickByJavaScript();
            if (assetType.getAssetNote2() != null)
                element("er.asset.notes2", rowXpath).input(assetType.getAssetNote2().getRealValue());
            if (assetType.getAssetNote3() != null)
                element("er.asset.notes3", rowXpath).input(assetType.getAssetNote3().getRealValue());
        }

        // setup asset type Interest Rules
        // if (assetType.getInterestRules() != null
        // && !assetType.getInterestRules().isEmpty()) {
        // element(By.xpath(rowXpath + "//img[@alt='Edit']")).click();
        // element(Interest_RULES).click();
        // setupInterestRules(assetType.getInterestRules());
        // element(EXIT_INTEREST_RULES).click();
        // }
        if (assetType.getInterestRule() != null) {
            openAssetTypeRule(rowXpath);
            switchToInterestRuleAndWhtRuleTab();
            setupInterestRule(assetType.getInterestRule());
            saveInterestRuleAndWhtRule();
            exitInterestRuleAndWhtRule();
        }

        if (assetType.getHaircutRule() != null) {
            openAssetTypeRule(rowXpath);
            switchToHaircutTab();
            setupHaircutRule(assetType.getHaircutRule());
            saveHaircutRule();
            exitHaircutRule();
        }
        // setup asset type eligibility Rules
        if (assetType.getEligibilityRule() != null) {
            openAssetTypeRule(rowXpath);
            switchToEligibilityRulesTab();
            setupEligibilityRules(assetType.getEligibilityRule());
            saveEligibilityRules();
            exitEligibilityRules();
        }
        // setup asset type concentration limit rules
        if (assetType.getConcentrationLimitRule() != null && !assetType.getConcentrationLimitRule().isEmpty()) {
            openAssetTypeRule(rowXpath);
            switchToConcentrationLimitRulesTab();
            setupConcentrationLimitRules(assetType.getConcentrationLimitRule());
            saveConcentrationLimitRules();
            element("er.cl.rules.exit").click();
        }

    }

    public void setupEligibilityRulesTemplateAssetClass(CollateralAssetClass assetClass) throws Exception {
        // setup asset class concentration limit rules
        if (assetClass.getConcentrationLimitRule() != null && !assetClass.getConcentrationLimitRule().isEmpty()) {
            openAssetClassRule(assetClass);
            switchToConcentrationLimitRulesTab();
            setupConcentrationLimitRules(assetClass.getConcentrationLimitRule());
            saveConcentrationLimitRules();
            exitConcentrationLimitRules();
        }

    }

    public void openAssetClassRule(CollateralAssetClass collateralAssetClass) throws Exception{
        element("er.edit.rules", collateralAssetClass.getCollateralAssetClassName().getRealValue()).clickByJavaScript();
    }

    public void openAssetTypeRule(String rowXpath) throws Exception{
        element("er.edit.type.rules", rowXpath).clickByJavaScript();
    }

    public void openAssetTypeRule(String rowXpath,CollateralAssetType collateralAssetType) throws Exception{
        element("er.edit.type.rules", rowXpath).clickByJavaScript();
    }

}
