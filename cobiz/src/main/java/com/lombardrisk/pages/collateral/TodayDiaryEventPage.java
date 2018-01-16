package com.lombardrisk.pages.collateral;


import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import com.lombardrisk.pojo.TodayDiaryEvent;

/**
 * @author Kenny Wang
 */
public final class TodayDiaryEventPage extends PageBase {

    public TodayDiaryEventPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void tickEvent(TodayDiaryEvent tde){}
    
    public void resetEvent(TodayDiaryEvent tde){}
    
    public void enterStatement(){}

}
