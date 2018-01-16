package com.lombardrisk.pages.optimisation;

import com.lombardrisk.data.OptimisationMenu;
import com.lombardrisk.pages.AbstractCollinePage;
import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.openqa.selenium.WebElement;
import org.yiwan.webcore.web.IWebDriverWrapper;

import java.util.Iterator;
import java.util.List;

/**
 * Created by mengli huang on 09/12/2016.
 */
public class OptimisationPage extends AbstractCollinePage {
    public OptimisationPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }


    /**
     * Click navigate filters on the left navigation panel of exposure
     * management
     *
     * @param filter a sample as "Margin Filters>Substitution" filter
     */
    public void navigate(OptimisationMenu filter) throws Exception {
        String optimisationtMenu = filter.getName();
        String[] layer = optimisationtMenu.split(Biz.SEPARATOR);
        if (!element("opt.menu2", layer[0], layer[1]).isDisplayed())
            element("opt.menu", layer[0]).click();
        if (layer.length < 3) {
            element("opt.menu2", layer[0], layer[1]).click();
        } else {

        }

    }

    public void addOptRule(OptimisationRule optimisationRule) throws Exception {
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
        addRule();
        addRule(optimisationRule);
        saveRule();
        PageHelper.handleAlters(optimisationRule.getAlertHandling());
    }

    public void modifyOptRule(OptimisationRule oriRule, OptimisationRule newRule) throws Exception {
        editBasicInfo(newRule);
        modifyAttributeDetail(oriRule.getOptimisationFilterRuleAttribute(), newRule.getOptimisationFilterRuleAttribute());
        modifyAttributeDetail(oriRule.getOptimisationRankingRuleAttribute(), newRule.getOptimisationRankingRuleAttribute());
        if (newRule.getDataParameters() != null) {
            editDataParameters(newRule.getDataParameters());
        }
        if (newRule.getOptimisationParameters() != null) {
            editOptParameters(newRule.getOptimisationParameters());
        }
        if (newRule.getRunParameters() != null) {
            editRunParameters(newRule.getRunParameters());
        }
        saveRule();
        PageHelper.handleAlters(newRule.getAlertHandling());
    }

    public void enterRule(OptimisationRule oriRule) throws Exception {
        String xpath = "//tr";
        if (oriRule.getRuleName() != null)
            xpath += "[td='" + oriRule.getRuleName().getRealValue() + "']";
        element("opt.result.detail.operator", xpath, "Edit").clickByJavaScript();
//        handleAlters(result.getAlertHandling());
    }

    public void deleteRule(OptimisationRuleDetail result) throws Exception {
        String xpath = getSearchResultRow(result);
        alert().disable();
        element("opt.result.detail.operator", xpath, "Delete").clickByJavaScript();
        if (result.getAlertHandling() != null && result.getAlertHandling().size() > 0)
            alterHander(result.getAlertHandling().get(0));
        alert().enable();

    }

    public void runRule(OptimisationRuleDetail result) throws Exception {
        String xpath = getSearchResultRow(result);
        element("opt.result.detail.operator", xpath, "Run Now").clickByJavaScript();
        if (result.getAlertHandling() != null && result.getAlertHandling().size() > 0) {
            Message message = result.getAlertHandling().get(0);
            if (message.isAccept() != null && message.isAccept()) {
                element("opt.rule.run.yes").click();
                if (alert().isPresent())
                    alert().accept();
            } else
                element("opt.rule.run.cancel").click();
        }
    }

    private void addRule() throws Exception {
        element("opt.rule.add").click();
    }

    private void saveRule() throws Exception {
        element("opt.rule.save").click();
    }

    private void cancelRule() throws Exception {
        element("opt.rule.cancel").click();
    }


    private void addRule(OptimisationRule optimisationRule) throws Exception {
        if (optimisationRule != null) {
            editBasicInfo(optimisationRule);
            if (optimisationRule.getOptimisationFilterRuleAttribute() != null && optimisationRule.getOptimisationFilterRuleAttribute().size() > 0) {
                addAttributeDetail(optimisationRule.getOptimisationFilterRuleAttribute());
            }
            if (optimisationRule.getOptimisationRankingRuleAttribute() != null && optimisationRule.getOptimisationRankingRuleAttribute().size() > 0) {
                addAttributeDetail(optimisationRule.getOptimisationRankingRuleAttribute());
            }

            if (optimisationRule.getDataParameters() != null) {
                editDataParameters(optimisationRule.getDataParameters());
            }
            if (optimisationRule.getOptimisationParameters() != null) {
                editOptParameters(optimisationRule.getOptimisationParameters());
            }
            if (optimisationRule.getRunParameters() != null) {
                editRunParameters(optimisationRule.getRunParameters());
            }
        }
    }

    private void editBasicInfo(OptimisationRule optimisationRule) throws Exception {
        if (optimisationRule != null) {
            if (optimisationRule.getRuleName() != null)
                element("opt.rule.name").input(optimisationRule.getRuleName().getRealValue());
            if (optimisationRule.getAvailabilityBetweenFrom() != null)
                element("opt.rule.availability.between").input(optimisationRule.getAvailabilityBetweenFrom().getRealValue());
            if (optimisationRule.getAvailabilityBetweenTo() != null)
                element("opt.rule.availability.to").input(optimisationRule.getAvailabilityBetweenTo().getRealValue());
            if (optimisationRule.getMinimunNotional() != null)
                element("opt.rule.minimum.notional").input(optimisationRule.getMinimunNotional().getRealValue());
            if (optimisationRule.isIncludeCollineData() != null)
                //noinspection UnnecessaryUnboxing
                element("opt.rule.include.colline.data").check(optimisationRule.isIncludeCollineData());
        }
    }

    private void editDataParameters(DataParameters dataParameters) throws Exception {
        if (dataParameters.getAssetFilterRule() != null) {
            if (dataParameters.getAssetFilterRule().getFilter() != null)
                element("opt.asset.filter.rule").input(dataParameters.getAssetFilterRule().getFilter().getRealValue());
            if (dataParameters.getAssetFilterRule().getLinkText() != null)
                element("opt.list.value", dataParameters.getAssetFilterRule().getLinkText().getRealValue()).click();
        }
        if (dataParameters.getAssetRankingRule() != null) {
            if (dataParameters.getAssetRankingRule().getFilter() != null)
                element("opt.asset.ranking.rule").input(dataParameters.getAssetRankingRule().getFilter().getRealValue());
            if (dataParameters.getAssetRankingRule().getLinkText() != null)
                element("opt.list.value", dataParameters.getAssetRankingRule().getLinkText().getRealValue()).click();
        }
        if (dataParameters.getAgreementFilterRule() != null) {
            if (dataParameters.getAgreementFilterRule().getFilter() != null)
                element("opt.agreement.filter.rule").input(dataParameters.getAgreementFilterRule().getFilter().getRealValue());
            if (dataParameters.getAgreementFilterRule().getLinkText() != null)
                element("opt.list.value", dataParameters.getAgreementFilterRule().getLinkText().getRealValue()).click();
        }
        if (dataParameters.getAgreementRankingRule() != null) {
            if (dataParameters.getAgreementRankingRule().getFilter() != null)
                element("opt.agreement.ranking.rule").input(dataParameters.getAgreementRankingRule().getFilter().getRealValue());
            if (dataParameters.getAgreementRankingRule().getLinkText() != null)
                element("opt.list.value", dataParameters.getAgreementRankingRule().getLinkText().getRealValue()).click();
        }
        if (dataParameters.getAgreementStatus() != null && dataParameters.getAgreementStatus().size() > 0) {
            element("opt.agreement.status").click();
            for (IWebDriverWrapper.IWebElementWrapper option : element("opt.rule.attribute.checkbox.all").getAllMatchedElements()) {
                option.check(false);
            }
            for (AgreementStatusType type : dataParameters.getAgreementStatus()) {
                element("opt.multi.check.value", type.value()).check(true);
            }
        }
        if (dataParameters.getEventStatus() != null && dataParameters.getEventStatus().size() > 0) {
            element("opt.event.status").click();
            for (IWebDriverWrapper.IWebElementWrapper option : element("opt.rule.attribute.checkbox.all").getAllMatchedElements()) {
                option.check(false);
            }
            for (StringType type : dataParameters.getEventStatus()) {
                element("opt.multi.check.value", type.getRealValue()).check(true);
            }
        }
        if (dataParameters.isInternalReview() != null)
            //noinspection UnnecessaryUnboxing
            element("opt.internal.review").check(dataParameters.isInternalReview());
    }

    private void editOptParameters(OptimisationParameters optimisationParameters) throws Exception {
        if (optimisationParameters.isAssumeSweepOfExistingBooking() != null) {
            //noinspection UnnecessaryUnboxing
            element("opt.assume.sweep.of.existing.booking").check(optimisationParameters.isAssumeSweepOfExistingBooking());
        }
        if (optimisationParameters.isOutgoingAssetsRule() != null) {
            //noinspection UnnecessaryUnboxing
            element("opt.outgoing.assets.rule").check(optimisationParameters.isOutgoingAssetsRule());
        }
        if (optimisationParameters.getRequirementMatchTolerance() != null) {
            element("opt.requirement.match.tolerance").input(optimisationParameters.getRequirementMatchTolerance().getRealValue());
        }
        if (optimisationParameters.isUnlimitedAssets() != null) {
            //noinspection UnnecessaryUnboxing
            element("opt.unlimited.assets").check(optimisationParameters.isUnlimitedAssets());
        }
        if (optimisationParameters.getShortfallCostCosfficient() != null) {
            element("opt.shortfall.cost.coefficient").input(optimisationParameters.getShortfallCostCosfficient().getRealValue());
        }
    }

    private void editRunParameters(RunParameters runParameters) throws Exception {
        if (runParameters.getAlgorithm() != null) {
            element("opt.algorithm").selectByVisibleText(runParameters.getAlgorithm().getRealValue());
        }
        if (runParameters.getReportingCurrency() != null) {
            if (runParameters.getReportingCurrency().getFilter() != null)
                element("opt.reporting.currency").input(runParameters.getReportingCurrency().getFilter().getRealValue());
            if (runParameters.getReportingCurrency().getLinkText() != null)
                element("opt.list.value", runParameters.getReportingCurrency().getLinkText().getRealValue()).click();
        }
        if (runParameters.isRandomisation() != null) {
            //noinspection UnnecessaryUnboxing
            element("opt.randomisation").check(runParameters.isRandomisation());
            if (runParameters.getMaxinumNumberOfRun() != null) {
                if (runParameters.getMaxinumNumberOfRun().isIsTick() != null) {
                    //noinspection UnnecessaryUnboxing
                    element("opt.max.number.of.runs.check").check(runParameters.getMaxinumNumberOfRun().isIsTick());
                    if (runParameters.getMaxinumNumberOfRun().isIsTick() && runParameters.getMaxinumNumberOfRun().getValue() != null) {
                        element("opt.max.number.of.runs.value").input(runParameters.getMaxinumNumberOfRun().getValue().getRealValue());
                    }
                }
            }
        }
        if (runParameters.getTimeOut() != null) {
            if (runParameters.getTimeOut().isIsTick() != null) {
                //noinspection UnnecessaryUnboxing
                element("opt.timeout.check").check(runParameters.getTimeOut().isIsTick());
                if (runParameters.getTimeOut().isIsTick() && runParameters.getTimeOut().getValue() != null) {
                    element("opt.timeout.value").input(runParameters.getTimeOut().getValue().getRealValue());
                }
            }
        }
    }

    private void addAttributeDetail(List<OptimisationRuleAttribute> list) throws Exception {
        for (OptimisationRuleAttribute attribute : list) {
            if (attribute.getAttributeName() != null && attribute.getName() == null) {
                element("opt.rule.builder.attribute"
                        , attribute.getAttributeName().getRealValue()
                        , attribute.getAttributeName().getRealValue()).dragAndDrop(locator("opt.rule.drop.area"));
                if (attribute.getOpenBracket() != null)
                    element("opt.rule.attribute.openbracket", attribute.getAttributeName().getRealValue()).selectByVisibleText(attribute.getOpenBracket());
                if (attribute.getAttributeDetail() != null && !attribute.getAttributeDetail().isEmpty()) {
                    Iterator<AttributeDetail> iterator = attribute.getAttributeDetail().iterator();
                    while (iterator.hasNext()) {
                        AttributeDetail detail = iterator.next();
                        if (detail.getType() != null)
                            element("opt.rule.attribute.type", attribute.getAttributeName().getRealValue()).selectByVisibleText(detail.getType().getRealValue());
                        if (detail.getOperator() != null)
                            element("opt.rule.attribute.operator", attribute.getAttributeName().getRealValue()).selectByVisibleText(detail.getOperator().value());
                        if (detail.getFilterRuleAttributeValue() != null) {
                            if (detail.getFilterRuleAttributeValue().getSelectOption() != null && !detail.getFilterRuleAttributeValue().getSelectOption().isEmpty()) {
                                element("opt.rule.attribute.value.select.option", attribute.getAttributeName().getRealValue()).click();
                                if (detail.getFilterRuleAttributeValue().getFilter() != null)
                                    element("opt.rule.attribute.filter").input(detail.getFilterRuleAttributeValue().getFilter().getRealValue());
                                for (StringType selectOption : detail.getFilterRuleAttributeValue().getSelectOption()) {
                                    element("opt.rule.attribute.checkbox", selectOption.getRealValue()).scrollIntoView().clickByJavaScript().fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                                }
                                element("opt.rule.attribute.value.select.option", attribute.getAttributeName().getRealValue()).click();
                            }
                        }
                        if (detail.getSelectedValue() != null)
                            element("opt.rule.attribute.selectValue", attribute.getAttributeName().getRealValue()).selectByVisibleText(detail.getSelectedValue().getRealValue());
                        if (detail.getInputValue() != null)
                            element("opt.rule.attribute.inputValue", attribute.getAttributeName().getRealValue()).input(detail.getInputValue().getRealValue());
                        if (detail.getFilterValue() != null) {
                            if (detail.getFilterValue().getFilter() != null)
                                element("opt.rule.attribute.inputValue", attribute.getAttributeName().getRealValue()).input(detail.getFilterValue().getFilter().getRealValue());
                            if (detail.getFilterValue().getLinkText() != null)
                                element("opt.list.value", detail.getFilterValue().getLinkText().getRealValue()).click();
                        }
                        if (detail.getRankValue() != null)
                            element("opt.rule.attribute.rankValue", attribute.getAttributeName().getRealValue()).input(detail.getRankValue().getRealValue());
                        if (iterator.hasNext())
                            element("opt.rule.attribute.add", attribute.getAttributeName().getRealValue()).click();
                        if (detail.getAndOr() != null)
                            element("opt.rule.attribute.andOr", attribute.getAttributeName().getRealValue()).selectByVisibleText(detail.getAndOr().value());
                    }
                }
                if (attribute.getGroupAndOr() != null)
                    element("opt.rule.attribute.groupAndOr", attribute.getAttributeName().getRealValue()).selectByVisibleText(attribute.getGroupAndOr().value());
                if (attribute.getCloseBracket() != null)
                    element("opt.rule.attribute.closeBracket", attribute.getAttributeName().getRealValue()).selectByVisibleText(attribute.getCloseBracket());
                if (attribute.getBracketAndOr() != null)
                    element("opt.rule.attribute.bracketAndOr", attribute.getAttributeName().getRealValue()).selectByVisibleText(attribute.getBracketAndOr().value());
                if (attribute.getGroupRank() != null)
                    element("opt.rule.attribute.groupRank", attribute.getAttributeName().getRealValue()).input(attribute.getGroupRank().getRealValue());
            }
        }
    }

    private String getSearchResultRow(OptimisationRuleDetail result) {
        StringBuilder xpath = new StringBuilder();
        xpath.append("//tr");
        if (result.getRuleName() != null) {
            xpath.append("[td='").append(result.getRuleName().getRealValue()).append("']");
        }
        if (result.getCreateBy() != null) {
            xpath.append("[td='").append(result.getCreateBy().getRealValue()).append("']");
        }
        if (result.getCreateTime() != null) {
            xpath.append("[td='").append(result.getCreateTime().getRealValue()).append("']");
        }
        if (result.getLastUpdatedBy() != null) {
            xpath.append("[td='").append(result.getLastUpdatedBy().getRealValue()).append("']");
        }
        if (result.getLastUpdatedTime() != null) {
            xpath.append("[td='").append(result.getLastUpdatedTime().getRealValue()).append("']");
        }
        return xpath.toString();
    }

    private void alterHander(Message handle) throws Exception {
        if (element("opt.rule.alert.dialog").isDisplayed()) {
            if (handle.isAccept()) {
                element("opt.rule.delete.dialog", "Yes").clickByJavaScript();
            } else {
                element("opt.rule.delete.dialog", "Cancel").clickByJavaScript();
            }
        }

    }


    private void modifyAttributeDetail(List<OptimisationRuleAttribute> attributeList, List<OptimisationRuleAttribute> newAttributeList) throws Exception {
        for (OptimisationRuleAttribute attribute : attributeList) {
            //attribute level  remove=true
            if (attribute.getAttributeName() != null) {
                OptimisationRuleAttribute newAttribute = (OptimisationRuleAttribute) Biz.matchedObjectWithName(attribute, newAttributeList);
                if (attribute.isRemove() != null && attribute.isRemove()) {
                    element("opt.rule.attribute.header", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue()).moveTo();
                    element("opt.rule.attribute.header.delete", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue()).click();
                } else {//attribute level modify
                    int matchNum = element("opt.rule.attribute.body.list", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue()).getNumberOfMatches();
                    for (AttributeDetail nameDetail : newAttribute.getAttributeDetail()) {
                        //detail level  add
                        if (nameDetail.getName() == null) {
                            element("opt.rule.attribute.add", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue()).click();
                            matchNum += 1;
                            if (nameDetail.getType() != null)
                                element("opt.rule.attribute.type", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(matchNum))
                                        .selectByVisibleText(nameDetail.getType().getRealValue());
                            if (nameDetail.getOperator() != null)
                                element("opt.rule.attribute.operator", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(matchNum))
                                        .selectByVisibleText(nameDetail.getOperator().value());
                            if (nameDetail.getFilterRuleAttributeValue() != null) {
                                if (nameDetail.getFilterRuleAttributeValue().getSelectOption() != null && nameDetail.getFilterRuleAttributeValue().getSelectOption().size() > 0) {
                                    element("opt.rule.attribute.value.select.option", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(matchNum)).click();
                                    if (nameDetail.getFilterRuleAttributeValue().getFilter() != null)
                                        element("opt.rule.attribute.filter").input(nameDetail.getFilterRuleAttributeValue().getFilter().getRealValue());
                                    for (StringType selectOption : nameDetail.getFilterRuleAttributeValue().getSelectOption()) {
                                        element("opt.rule.attribute.checkbox", selectOption.getRealValue()).check(true);
                                    }
                                    element("opt.rule.attribute.value.select.option", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(matchNum)).click();

                                }
                                if (nameDetail.getFilterRuleAttributeValue().getInputValue() != null)
                                    element("opt.rule.attribute.value", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(matchNum))
                                            .input(nameDetail.getFilterRuleAttributeValue().getInputValue().getRealValue());
                            }

                            if (nameDetail.getSelectedValue() != null)
                                element("opt.rule.attribute.selectValue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(matchNum))
                                        .selectByVisibleText(nameDetail.getSelectedValue().getRealValue());
                            if (nameDetail.getInputValue() != null)
                                element("opt.rule.attribute.inputValue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(matchNum))
                                        .input(nameDetail.getInputValue().getRealValue());
                            if (nameDetail.getRankValue() != null)
                                element("opt.rule.attribute.rankValue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(matchNum))
                                        .input(nameDetail.getRankValue().getRealValue());
                            if (nameDetail.getAndOr() != null)
                                element("opt.rule.attribute.andOr", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(matchNum))
                                        .selectByVisibleText(nameDetail.getAndOr().value());
                        }
                    }

                    if (attribute.getAttributeDetail() != null && attribute.getAttributeDetail().size() > 0) {
                        for (AttributeDetail detail : attribute.getAttributeDetail()) {
                            for (int index = 1; index <= matchNum; index++) {

                                //find matched detail
                                boolean flag = false;
                                if (detail.getType() != null || detail.getOperator() != null || detail.getInputValue() != null || detail.getSelectedValue() != null || detail.getAndOr() != null) {
                                    flag = true;
                                    if (detail.getType() != null)
                                        flag = detail.getType().getRealValue().equals(element("opt.rule.attribute.body.type", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).getSelectedText());
                                    if (detail.getOperator() != null)
                                        flag = detail.getOperator().value().equals(element("opt.rule.attribute.body.operator", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).getSelectedText());
                                    if (detail.getFilterRuleAttributeValue() != null) {
                                        if (detail.getFilterRuleAttributeValue().getInputValue() != null)
                                            flag = detail.getFilterRuleAttributeValue().getInputValue().getRealValue().equals(element("opt.rule.attribute.body.inputvalue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).getAttribute("value"));
                                        if (detail.getFilterRuleAttributeValue().getSelectOption() != null && detail.getFilterRuleAttributeValue().getSelectOption().size() > 0) {
                                            int optionLength = 0;
                                            for (StringType option : detail.getFilterRuleAttributeValue().getSelectOption()) {
                                                flag = flag && element("opt.rule.attribute.body.selectvalue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).getInnerText().contains(option.getRealValue());
                                                optionLength = optionLength + option.getRealValue().length() + 2;
                                            }
                                            flag = (optionLength - 2 == element("opt.rule.attribute.body.selectvalue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).getInnerText().length());
                                        }

                                    }
                                    if (detail.getAndOr() != null)
                                        flag = detail.getAndOr().value().equals(element("opt.rule.attribute.body.andor", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).getSelectedText());
                                    if (detail.getRankValue() != null)
                                        flag = detail.getRankValue().getRealValue().equals(element("opt.rule.attribute.rankValue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).getAttribute("value"));
                                    if (detail.getInputValue() != null)
                                        flag = detail.getInputValue().getRealValue().equals(element("opt.rule.attribute.body.inputvalue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).getAttribute("value"));
                                    if (detail.getSelectedValue() != null)
                                        flag = detail.getSelectedValue().getRealValue().equals(element("opt.rule.attribute.body.select.alloption", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).getSelectedText());
                                    if (detail.getFilterValue() != null && detail.getFilterValue().getLinkText() != null)
                                        flag = detail.getFilterValue().getLinkText().getRealValue().equals(element("opt.rule.attribute.body.inputvalue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).getAttribute("value"));

                                }
                                if (flag) {
                                    if (detail.isRemove() != null && detail.isRemove()) {
                                        element("opt.rule.attribute.body", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).moveTo();
                                        element("opt.rule.attribute.body.delete", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).click();
                                    } else {
                                        AttributeDetail newDetail = (AttributeDetail) Biz.matchedObjectWithName(detail, newAttribute.getAttributeDetail());
                                        if (newDetail.getType() != null)
                                            element("opt.rule.attribute.body.type", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).selectByVisibleText(newDetail.getType().getRealValue());
                                        if (newDetail.getOperator() != null)
                                            element("opt.rule.attribute.body.operator", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).selectByVisibleText(newDetail.getOperator().value());
                                        if (newDetail.getFilterRuleAttributeValue() != null) {
                                            if (newDetail.getFilterRuleAttributeValue().getSelectOption() != null && newDetail.getFilterRuleAttributeValue().getSelectOption().size() > 0) {
//                                                element("opt.rule.attribute.value.select.option", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).click();
                                                element("opt.rule.attribute.value.select.option", attribute.getAttributeName().getRealValue()).click();
                                                for (WebElement allOption : element("opt.rule.attribute.body.select.alloption", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).getAllOptions()) {
                                                    if (element("opt.rule.attribute.checkbox", allOption.getAttribute("text")).isDisplayed())
                                                        element("opt.rule.attribute.checkbox", allOption.getAttribute("text")).check(false);
                                                }
                                                if (newDetail.getFilterRuleAttributeValue().getFilter() != null)
                                                    element("opt.rule.attribute.filter").input(newDetail.getFilterRuleAttributeValue().getFilter().getRealValue());
                                                for (StringType selectOption : newDetail.getFilterRuleAttributeValue().getSelectOption()) {
//                                                    element("opt.rule.attribute.body.selectvalue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).selectByVisibleText(newDetail.getSelectedValue().getRealValue());
                                                    element("opt.rule.attribute.checkbox", selectOption.getRealValue()).check(true);
                                                }
//                                                element("opt.rule.attribute.value.select.option", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).click();
                                                element("opt.rule.attribute.value.select.option", attribute.getAttributeName().getRealValue()).click();

                                            }
                                            if (newDetail.getFilterRuleAttributeValue().getInputValue() != null)
                                                element("opt.rule.attribute.body.inputvalue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).input(newDetail.getFilterRuleAttributeValue().getInputValue().getRealValue());

                                        }
                                        if (newDetail.getSelectedValue() != null)
                                            element("opt.rule.attribute.selectValue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index))
                                                    .selectByVisibleText(newDetail.getSelectedValue().getRealValue());
                                        if (newDetail.getInputValue() != null)
                                            element("opt.rule.attribute.inputValue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index))
                                                    .input(newDetail.getInputValue().getRealValue());
                                        if (newDetail.getRankValue() != null)
                                            element("opt.rule.attribute.rankValue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index))
                                                    .input(newDetail.getRankValue().getRealValue());
                                        if (newDetail.getAndOr() != null)
                                            element("opt.rule.attribute.body.andor", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index)).selectByVisibleText(newDetail.getAndOr().value());
                                        if (newDetail.getFilterValue() != null) {
                                            if (newDetail.getFilterValue().getFilter() != null)
                                                element("opt.rule.attribute.inputValue", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), String.valueOf(index))
                                                        .input(newDetail.getFilterValue().getFilter().getRealValue());
                                            if (newDetail.getFilterValue().getLinkText() != null)
                                                element("opt.list.value", newDetail.getFilterValue().getLinkText().getRealValue()).click();
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    }

                    if (newAttribute.getOpenBracket() != null)
                        element("opt.rule.attribute.openbracket", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue()).selectByVisibleText(newAttribute.getOpenBracket());
                    if (newAttribute.getGroupAndOr() != null)
                        element("opt.rule.attribute.groupAndOr", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue()).selectByVisibleText(newAttribute.getGroupAndOr().value());
                    if (newAttribute.getCloseBracket() != null)
                        element("opt.rule.attribute.closeBracket", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue()).selectByVisibleText(newAttribute.getCloseBracket());
                    if (newAttribute.getBracketAndOr() != null)
                        element("opt.rule.attribute.bracketAndOr", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue()).selectByVisibleText(newAttribute.getBracketAndOr().value());
                    if (newAttribute.getGroupRank() != null)
                        element("opt.rule.attribute.groupRank", attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue(), attribute.getAttributeName().getRealValue()).input(newAttribute.getGroupRank().getRealValue());

                }
            }
        }
        addAttributeDetail(newAttributeList);

    }

}
