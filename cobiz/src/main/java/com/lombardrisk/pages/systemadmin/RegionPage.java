package com.lombardrisk.pages.systemadmin;

import com.lombardrisk.pojo.Region;
import org.yiwan.webcore.web.IWebDriverWrapper;

import org.yiwan.webcore.web.PageBase;


public final class RegionPage extends PageBase {

    public RegionPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void openRegion(Region region) throws Exception {
        if (region.getRegionName() != null){
            element("rg.region.link").click();
        }
    }

    public void addNewRegion() throws Exception {
        element("rg.add.new.region.link").click();
    }

    public void inputNewRegion(Region region) throws Exception {
        if (region.getRegionName() != null){
            element("rg.add.new.region.name").input(region.getRegionName().getRealValue());
        }
        if (region.getCountry() != null && region.getCountry().size() > 0){
            for (int i=0;i<region.getCountry().size();i++){
                element("rg.add.new.item").click();
                element("rg.region.country",String.valueOf(i)).selectByVisibleText(region.getCountry().get(i).getRealValue());
            }
        }
    }



}
