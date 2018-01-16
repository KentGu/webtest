package com.lombardrisk.pages.tools;

import com.lombardrisk.pojo.UserFavourite;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

public final class AddToMyFavouritesPage extends PageBase {

    public AddToMyFavouritesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * add Favourite on AddToMyFavouritesPage -> Add to My Favourites page
     *
     * @param favourite
     */
    public void editFavourites(UserFavourite favourite) throws Exception {
        if (favourite.getFavouriteName() != null){
        	element("to.fav.name").input(favourite.getFavouriteName().getRealValue());
        }
    }
    
    public void addFavourites() throws Exception{
    	element("to.fav.add").click();
    }
    
    public void cancelFavourites() throws Exception{
    	element("to.fav.cancel").click();
    }

}
