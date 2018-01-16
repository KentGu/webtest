package com.lombardrisk.pages.tools;

import com.lombardrisk.pojo.UserFavourite;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

public final class EditMyFavouritesPage extends PageBase {

    public EditMyFavouritesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * delete Favourite on AddToMyFavouritesPage -> Edit My Favourites Page
     *
     * @param favourite
     */
    public void tickDeleteFavourite(UserFavourite favourite) throws Exception {
        if (favourite.getFavouriteName() != null){
        	element("to.fav", favourite.getFavouriteName().getRealValue()).click();
        }
    }
    
    public void delete() throws Exception{
    	element("to.fav.delete").click();
    }
    
    public void cancel() throws Exception{
    	element("to.favEdit.cancel").click();
    }

}
