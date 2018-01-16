package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.Agreement;
import com.lombardrisk.pojo.Product;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;


/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementProductsSetupPage extends PageBase {
    public AgreementProductsSetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
  public void setupAgreementProducts(Agreement agmt) throws Exception {
	  if (agmt.getExposureProfile() != null) {
          element("ap.exposure.profile").selectByVisibleText(agmt.getExposureProfile().getRealValue());
      }
	  if (agmt.getDefaultTradeValuationRule() != null){
		  element("ap.default.trade.valuation.rule").selectByVisibleText(agmt.getDefaultTradeValuationRule().value());
	  }
      if (agmt.getSharesPerCreationUnit()!=null){
          element("ap.share.per.creation.unit").input(agmt.getSharesPerCreationUnit().getRealValue());
      }
     
      //set trade mapping rule
      if (agmt.isTradeMappingRuleFlag() != null) {
          element("ap.trade.mapping.rule").check(agmt.isTradeMappingRuleFlag());
      }

      // click + button serveral times
      if (agmt.getTradeMappingRule() != null) {
          for (int i = 0; i < agmt.getTradeMappingRule().size(); i++) {
              //element("ap.add.mapping.rule").fireEvent(Biz.FIRE_EVENT_ONCLICK);
              element("ap.add.mapping.rule").clickByJavaScript();
          }
      }

      if (agmt.getTradeMappingRule() != null){
    	  for (int i = 0; i < agmt.getTradeMappingRule().size(); i++) {
    		  if (agmt.getTradeMappingRule().get(i).getField() != null) {
                  element("ap.tmr.field", String.valueOf(i)).selectByVisibleText(agmt.getTradeMappingRule().get(i).getField().getRealValue());
              }
              if (agmt.getTradeMappingRule().get(i).getRule() != null) {
                  element("ap.tmr.rule", String.valueOf(i)).selectByVisibleText(agmt.getTradeMappingRule().get(i).getRule().value());
              }
              if (agmt.getTradeMappingRule().get(i).getValue() != null) {
                  element("ap.tmr.value", String.valueOf(i)).input(agmt.getTradeMappingRule().get(i).getValue().getRealValue());
              }
              if (agmt.getTradeMappingRule().get(i).getOperator() != null) {
                  element("ap.tmr.operator", String.valueOf(i)).selectByVisibleText(agmt.getTradeMappingRule().get(i).getOperator().value());
              }
          }
      }
      //set trade calculation rule
      if (agmt.getTradeCalcRule() != null){
          if (agmt.getTradeCalcRule().isIncludeFailedOpenLeg() != null){
              element("ap.include.failed.open.leg").check(agmt.getTradeCalcRule().isIncludeFailedOpenLeg());
          }
          if (agmt.getTradeCalcRule().isIncludeFailedCloseLeg() != null){
              element("ap.include.failed.close.leg").check(agmt.getTradeCalcRule().isIncludeFailedCloseLeg());
          }
          if (agmt.getTradeCalcRule().isIncludeFailedTradeValuations()!=null){
              element("ap.include.failed.trade.valuation").check(agmt.getTradeCalcRule().isIncludeFailedTradeValuations());

          }
      }
      //set agreementIA
      if (agmt.getAgreementIA() != null) {
          if (agmt.getAgreementIA().isCheck() != null) {
              element("ap.independent.amount").check(agmt.getAgreementIA().isCheck());
          }
          if (agmt.getAgreementIA().getUpfrontCalculationMethodology() != null) {
              element("ap.upfront.cal.method").selectByVisibleText(agmt.getAgreementIA().getUpfrontCalculationMethodology().value());
          }
          if (agmt.getAgreementIA().getExternalIADetail() != null) {
              if (agmt.getAgreementIA().getExternalIADetail().isPrincipalUpfronts() != null) {
                  element("ap.agia.pin.upfronts").check(agmt.getAgreementIA().getExternalIADetail().isPrincipalUpfronts());
              }
              if (agmt.getAgreementIA().getExternalIADetail().isCptyUpfronts() != null) {
                  element("ap.agia.cpt.upfronts").check(agmt.getAgreementIA().getExternalIADetail().isCptyUpfronts());
              }
              if (agmt.getAgreementIA().getExternalIADetail().getFixedPercentage() != null) {
                  element("ap.agia.fixed.percen").input(agmt.getAgreementIA().getExternalIADetail().getFixedPercentage().getRealValue());
              }
              if (agmt.getAgreementIA().getExternalIADetail().getFixedValue() != null) {
                  element("ap.agia.fixed.value").input(agmt.getAgreementIA().getExternalIADetail().getFixedValue().getRealValue());
              }
          }
      }
      //set scale up margin
      if (agmt.getScaleUpMargin() != null){
          if (agmt.getScaleUpMargin().isCheck() != null){
              element("ap.scale.up.margin").check(agmt.getScaleUpMargin().isCheck());
          }
          if (agmt.getScaleUpMargin().getUpfrontCalculationMethodology() != null) {
              element("ap.sacle.upfront.cal.method").selectByVisibleText(agmt.getScaleUpMargin().getUpfrontCalculationMethodology().value());
          }
          if (agmt.getScaleUpMargin().getExternalIADetail() != null) {
              if (agmt.getScaleUpMargin().getExternalIADetail().isPrincipalUpfronts() != null) {
                  element("ap.sacle.pin.upfronts").check(agmt.getScaleUpMargin().getExternalIADetail().isPrincipalUpfronts());
              }
              if (agmt.getScaleUpMargin().getExternalIADetail().isCptyUpfronts() != null) {
                  element("ap.sacle.cpt.upfronts").check(agmt.getScaleUpMargin().getExternalIADetail().isCptyUpfronts());
              }
              if (agmt.getScaleUpMargin().getExternalIADetail().getFixedPercentage() != null) {
                  element("ap.sacle.fixed.percen").input(agmt.getScaleUpMargin().getExternalIADetail().getFixedPercentage().getRealValue());
              }
              if (agmt.getScaleUpMargin().getExternalIADetail().getFixedValue() != null) {
                  element("ap.sacle.fixed.value").input(agmt.getScaleUpMargin().getExternalIADetail().getFixedValue().getRealValue());
              }
              if (agmt.getScaleUpMargin().getExternalIADetail().getAggregationRules() != null){
                  element("ap.sacle.aggregation.rules").selectByVisibleText(agmt.getScaleUpMargin().getExternalIADetail().getAggregationRules().getRealValue());
              }
          }
      }






      //
      for (Product product : agmt.getProduct()) {
          setupAgreementProduct(product);
      }
      enterNextStage();
  }

  public void setupAgreementProduct(Product product) throws Exception {
      if (product.getProductName() != null)
          element("ap.product", product.getProductName().getRealValue()).check(true);

      if (product.getExposurePercentageMTM() != null) {
          element("ap.product.exposure.mtm", product.getProductName().getRealValue()).input(product.getExposurePercentageMTM().getRealValue());
      }
      if (product.getSettlementPeriod() != null) {
          element("ap.product.settlement.period", product.getProductName().getRealValue()).selectByVisibleText(product.getSettlementPeriod());
      }
      if (product.getUpfrontCalculationMethodology() != null) {
          element("ap.product.up.cal.medo", product.getProductName().getRealValue()).selectByVisibleText(product.getUpfrontCalculationMethodology().value());
      }

      if (product.getHaircutCalculationMethodology()!=null){
          element("ap.product.haircut.cal.medo",product.getProductName().getRealValue()).selectByVisibleText(product.getHaircutCalculationMethodology().getRealValue());
      }
      if (product.isIncludeValuation()!=null){
          element("ap.product.include.valuation",product.getProductName().getRealValue()).check(product.isIncludeValuation());
      }
      if (product.getFeeCalculationMethodology()!=null){
          element("ap.product.fee.cal.medo",product.getProductName().getRealValue()).selectByVisibleText(product.getFeeCalculationMethodology().value());
      }

      if (product.getExternalIADetail() != null) {
          if (product.getExternalIADetail().isPrincipalUpfronts() != null) {
              element("ap.product.prin.upf", product.getProductName().getRealValue()).check(product.getExternalIADetail().isPrincipalUpfronts());
          }
          if (product.getExternalIADetail().isCptyUpfronts() != null) {
              element("ap.product.cpt.upf", product.getProductName().getRealValue()).check(product.getExternalIADetail().isCptyUpfronts());
          }

          if (product.getExternalIADetail().getFixedPercentage() != null) {
              element("ap.product.fixed.perc", product.getProductName().getRealValue()).input(product.getExternalIADetail().getFixedPercentage().getRealValue());
          }
          if (product.getExternalIADetail().getFixedValue() != null) {
              element("ap.product.fixed.value", product.getProductName().getRealValue()).input(product.getExternalIADetail().getFixedValue().getRealValue());
          }
          if (product.getExternalIADetail().getCcy() != null) {
              element("ap.product.ccy", product.getProductName().getRealValue()).input(product.getExternalIADetail().getCcy().getRealValue());
          }
      }
      
      if (product.getNettingRule() != null) {
          element("ap.product.edit", product.getProductName().getRealValue()).click();
          setupAgreementProductNettingRule(product);
      }
  }
  
  public void setupAgreementProductNettingRule(Product product) throws Exception {

      if (product.getNettingRule().getTradeRule() != null) {
          element("ap.product.trade.rule", product.getNettingRule().getTradeRule().value()).click();
      }
      if (product.getNettingRule().getNettingRule() != null) {
          element("ap.product.netting.rule", product.getNettingRule().getNettingRule().value()).click();
      }
      if (product.getNettingRule().getNettingCalculation() != null) {
          element("ap.product.netting.cal", product.getNettingRule().getNettingCalculation().value()).click();
      }
      if (product.getNettingRule().getRateDefinition() != null) {

          for (int i = 1; i < product.getNettingRule().getRateDefinition().size(); i++) {
              element("ap.product.netting.rule.add").click();
          }
          for (int i = 1; i <= product.getNettingRule().getRateDefinition().size(); i++) {

              if (product.getNettingRule().getRateDefinition().get(i - 1).getRate() != null) {
                  element("ap.product.netting.rule.rate", String.valueOf(i - 1)).input(product.getNettingRule().getRateDefinition().get(i - 1).getRate().getRealValue());
              }

              if (product.getNettingRule().getRateDefinition().get(i - 1).getCurrency() != null) {
                  for (int k = 0; k < product.getNettingRule().getRateDefinition().get(i - 1).getCurrency().size(); k++) {
                      element("ap.product.netting.currency", String.valueOf(i - 1)).selectByVisibleText(product.getNettingRule().getRateDefinition().get(i - 1).getCurrency().get(k).getRealValue());
                  }
              }
              if (product.getNettingRule().getRateDefinition().get(i - 1).getCurrencyGroup() != null) {
                  element("ap.product.netting.currency.group", String.valueOf(i - 1)).selectByVisibleText(product.getNettingRule().getRateDefinition().get(i - 1).getCurrencyGroup().getRealValue());
              }
          }
      }
      //click save
      element("ap.product.netting.rule.save").click();
  }
  /**
   * enter next stage while setup agreement
   */
  public void enterNextStage() throws Exception {
      waitThat().document().toBeReady();
      waitThat().jQuery().toBeInactive();
      element("ap.next").clickByJavaScript();
      PageHelper.d33640Workaround();
  }
  
}
   

