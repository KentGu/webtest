package com.lombardrisk.pages.organisations;

import com.lombardrisk.pojo.AgencyRating;
import com.lombardrisk.pojo.RatingLevel;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class RatingLevelPage extends PageBase {

    public RatingLevelPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * change rating level
     *
     * @param agencyRating
     */
    public void editRatingLevel(AgencyRating agencyRating) throws Exception {
        // levels[0].cqs levels[0].ratings[0].rating
        int r = agencyRating.getLevel() - 1;
        for (RatingLevel level : agencyRating.getRatingLevel()) {
            String agency = level.getAgency().getRealValue();
            if (agency.toUpperCase().equals("CQS")) {
                element("og.cqs.rating", String.valueOf(r), agency.toLowerCase()).selectByVisibleText(
                        level.getRating().getRealValue());
            } else {
                int c = ((int) element("og.agency.cell", agency).getCellColumn() - 1)/2;
                element("og.agency.rating", String.valueOf(r), String.valueOf(c)).selectByVisibleText(
                        level.getRating().getRealValue());
            }
        }
        element("og.rl.save").click();
    }

    /**
     * assert rating level page
     *
     * @param ar
     */
    public void assertRatingLevel(AgencyRating ar) throws Exception {
        int r = ar.getLevel() - 1;
        for (RatingLevel level : ar.getRatingLevel()) {
            String agency = level.getAgency().getRealValue();
            if (agency.toUpperCase().equals("CQS")) {
                assertThat("og.cqs.rating", String.valueOf(r), agency.toLowerCase()).allSelectedTexts().contains(level.getRating().getRealValue());
            } else {
                int c = ((int) element("og.agency.cell", agency).getCellColumn() - 1)/2;
                assertThat("og.agency.rating", String.valueOf(r), String.valueOf(c)).allSelectedTexts().contains(level.getRating().getRealValue());
            }
        }
    }
    
    public void addRatingLevel(AgencyRating ar){}
    
}
