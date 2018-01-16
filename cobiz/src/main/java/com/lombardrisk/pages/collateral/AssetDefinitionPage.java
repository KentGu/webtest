package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.AssetDefinition;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AssetDefinitionPage extends PageBase {
    public AssetDefinitionPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * add asset data
     *
     * @param asset
     */
    public void addAsset(AssetDefinition asset) throws Exception {
        if (asset.getParent() == null) {
            element("ad.add.new.asset.class").click();
        } else {
            element("ad.asset.class.expand", asset.getParent().getAssetName().getRealValue()).clickByJavaScript();
            element("ad.add.new.asset.type", asset.getParent().getAssetName().getRealValue()).click();
        }
    }
    
    public void editAsset(AssetDefinition asset) throws Exception{
    	if (asset.getParent() == null){
    		element("ad.asset.class",asset.getAssetName().getRealValue()).click();
    	}else{
    	    if (element("ad.asset.class.expand", asset.getParent().getAssetName().getRealValue()).isDisplayed())
                element("ad.asset.class.expand", asset.getParent().getAssetName().getRealValue()).clickByJavaScript();
            element("ad.asset.type", asset.getAssetName().getRealValue()).click();
    	}
    }

    public void setAssetSearchFilter(AssetDefinition asset) throws Exception{
    	if (asset.getAssetClassification() != null){
    		element("ad.assetClassfication").selectByVisibleText(asset.getAssetClassification().getRealValue());
    	}
    	if (asset.getFilterByStatus() != null){
    		element("ad.filterByStatus").selectByVisibleText(asset.getFilterByStatus().getRealValue());
    	}
    }


}
