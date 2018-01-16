package com.lombardrisk.pages.collateral;

import com.lombardrisk.pages.AbstractCollinePage;
import com.lombardrisk.pojo.AssetDefinition;
import com.lombardrisk.pojo.Message;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;

import java.util.List;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AddEditAssetPage extends AbstractCollinePage {
    public AddEditAssetPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * add asset data
     *
     * @param asset
     */
    public void editAsset(AssetDefinition asset) throws Exception {
        if (asset.getAssetName() != null)
            element("ad.name").input(asset.getAssetName().getRealValue());
        if (asset.getStatus() != null) {
            element("ad.status").selectByVisibleText(asset.getStatus().value());
        }
        if (asset.getDescription() != null){
        	element("ad.description").input(asset.getDescription().getRealValue());
        }
        if (asset.getAssetType() != null && asset.getAssetType().size() > 0){
            element("ad.assetType.filter").input("");
        	for (int i = 0; i < asset.getAssetType().size(); i++) {
				if (asset.getAssetType().get(i).getFilter() != null){
					element("ad.assetType.filter").type(asset.getAssetType().get(i).getFilter().getRealValue());
				}
				if (asset.getAssetType().get(i).getLinkText() != null){
					element("ad.assetType.linktext",asset.getAssetType().get(i).getLinkText().getRealValue()).click();
				}
			}
        }
        if (asset.getCategory() != null) {
            element("ad.category").selectByVisibleText(asset.getCategory().value());
        }
        if (asset.getAgreementRate() != null){
        	element("ad.agreement.rate").selectByVisibleText(asset.getAgreementRate().getRealValue());
        }
        if (asset.getReinvestRate() != null){
        	element("ad.reinvest.rate").selectByVisibleText(asset.getReinvestRate().getRealValue());
        }
        if (asset.getWithholdingTaxRate() != null){
        	element("ad.withholdingTax.rate").selectByVisibleText(asset.getWithholdingTaxRate().getRealValue());
        }
        
        if (asset.getTolerancePercentage() != null){
        	element("ad.tolerance.percentage").input(asset.getTolerancePercentage().getRealValue());
        }
        if (asset.getToleranceAccount() != null){
        	element("ad.tolerance.amount").input(asset.getToleranceAccount().getRealValue());
        }
        if (asset.getToleranceAccountCurrency() != null){
        	element("ad.tolerance.amountCcy").selectByVisibleText(asset.getToleranceAccountCurrency().getRealValue());
        }
        
        if (asset.getDaysInYear() != null){
        	element("ad.daysInYear").selectByVisibleText(asset.getDaysInYear().getRealValue());
        }
        if (asset.getCurrencyOfDenomination() != null) {
            element("ad.ccy").selectByVisibleText(asset.getCurrencyOfDenomination().getRealValue());
        }
        if (asset.getSettlementDate() != null){
        	element("ad.settlement.date").selectByVisibleText(asset.getSettlementDate().getRealValue());
        }
        if (asset.getHolidayCentre() != null && !asset.getHolidayCentre().isEmpty())
            element("ad.holidayCenter").deselectAll();
            for (StringType holidayCentre : asset.getHolidayCentre()) {
                element("ad.holidayCenter").selectByVisibleText(holidayCentre.getRealValue());
            }
        if (asset.getRoundingAmount() != null){
        	element("ad.rounding.amount").input(asset.getRoundingAmount().getRealValue());
        }
        if (asset.isApplyToInterestCalculation() != null){
        	element("ad.apply.toInterest.calculation").check(asset.isApplyToInterestCalculation());
        }
        if (asset.getRoundingRule() != null){
        	element("ad.rounding.rule").selectByVisibleText(asset.getRoundingRule().value());
        }
        if (asset.getDeliveryBookingPriority() != null){
        	element("ad.delivery.book.priority").selectByVisibleText(asset.getDeliveryBookingPriority().value());
        }
        if (asset.getRecallBookingPriority() != null){
        	element("ad.recall.book.priority").selectByVisibleText(asset.getRecallBookingPriority().value());
        }
        if (asset.getExternalName() != null) {
            element("ad.extName").input(asset.getExternalName().getRealValue());
        }
    }
    
    public void save(List<Message> alerts) throws Exception{
    	element("ad.save").click();
        PageHelper.handleAlters(alerts);
        PageHelper.d33640Workaround();
    }
    
    public void cancel() throws Exception{
    	element("ad.cancel").click();
    }

    public void editAssetInfo(AssetDefinition asset) throws Exception{
        editAsset(asset);
        save(asset.getAlertHandling());
    }
}
