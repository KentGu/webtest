package com.lombardrisk.pages.organisations;

import com.lombardrisk.pojo.StringType;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.util.List;


/**
 * @author Kenny Wang
 */
public final class ViewOurOrganisationsPage extends PageBase {

    public ViewOurOrganisationsPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void  expandOrgTree(List<StringType> layers) throws Exception {
    	if (layers != null && !layers.isEmpty()){
    		for (StringType layer : layers) {
				element("og.expand", layer.getRealValue()).clickByJavaScript();
			}
			element("og.search.result", layers.get(layers.size()-1).getRealValue()).click();
    	} else {
    		logger.info("Layer is null or empty");
		}
    }

    public void collapseOrgTree(List<StringType> layers) throws  Exception{
    	if (layers != null && !layers.isEmpty()){
    		for (StringType layer : layers) {
    			element("og.expand", layer.getRealValue()).clickByJavaScript();
			}
			element("og.search.result", layers.get(layers.size()-1).getRealValue()).click();
    	}else {
			logger.info("Layer is null or empty");
		}
    }


}
