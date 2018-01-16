package com.lombardrisk.pages.systemadmin;

import com.lombardrisk.pojo.Holiday;
import com.lombardrisk.pojo.HolidayCentre;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;


public final class HolidayCentrePage extends PageBase {

    public HolidayCentrePage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void openHolidayCentre(HolidayCentre holidayCentre) throws Exception {
        if (holidayCentre.getHolidayCentreName() != null){
            element("hc.holiday.centre.link", holidayCentre.getHolidayCentreName().getRealValue()).click();
        }
    }
    public void addHolidayCity() throws Exception {
        element("hc.holiday.city.add").click();
    }

    public void assertHolidayCentre(HolidayCentre holidayCentre) throws Exception {
        if(holidayCentre.getHolidayCentreName() != null)
            assertThat("hc.holiday.centre.name").attributeValueOf("value").isEqualToIgnoringWhitespace(holidayCentre.getHolidayCentreName().getRealValue());
        if(holidayCentre.getHolidayCentreDescription() != null)
            assertThat("hc.holiday.centre.description").attributeValueOf("value").isEqualToIgnoringWhitespace(holidayCentre.getHolidayCentreDescription().getRealValue());
        if(holidayCentre.getStatus() != null)
            assertThat("hc.holiday.centre.status").selectedText().isEqualToIgnoringWhitespace(holidayCentre.getStatus().value());
        if(holidayCentre.getWeekendDay1() != null)
            assertThat("hc.holiday.centre.weekendDay1").selectedText().isEqualToIgnoringWhitespace(holidayCentre.getWeekendDay1().value());
        if(holidayCentre.getWeekendDay2() != null)
            assertThat("hc.holiday.centre.weekendDay2").selectedText().isEqualToIgnoringWhitespace(holidayCentre.getWeekendDay2().value());
        if(holidayCentre.getHoliday() != null && !holidayCentre.getHoliday().isEmpty()){
            for(Holiday holiday : holidayCentre.getHoliday()){
                assertThat("hc.holiday.delete", getXpath(holiday)).displayed().isTrue();
            }
        }
    }

    public void editHolidatCentre(HolidayCentre holidayCentre) throws Exception {
        if (holidayCentre.getHolidayCentreName() != null){
            element("hc.holiday.centre.name").input(holidayCentre.getHolidayCentreName().getRealValue());
        }
        if (holidayCentre.getHolidayCentreDescription() != null){
            element("hc.holiday.centre.description").input(holidayCentre.getHolidayCentreDescription().getRealValue());
        }
        if (holidayCentre.getStatus() != null){
            element("hc.holiday.centre.status").selectByVisibleText(holidayCentre.getStatus().value());
        }
        if (holidayCentre.getWeekendDay1() != null){
            element("hc.holiday.centre.weekendDay1").selectByVisibleText(holidayCentre.getWeekendDay1().value());
        }
        if (holidayCentre.getWeekendDay2() != null){
            element("hc.holiday.centre.weekendDay2").selectByVisibleText(holidayCentre.getWeekendDay2().value());
        }
        saveHolidayCentre();
        if (holidayCentre.getHoliday() != null && holidayCentre.getHoliday().size() > 0){
            for (Holiday holiday : holidayCentre.getHoliday()){
                if (holiday.isDelete()!=null && holiday.isDelete())
                    deleteHoliday(holiday);
                else {
                    inputHoliday(holiday);
                    addHoliday();
                }

            }
        }
    }
    public void saveHolidayCentre() throws Exception {
        element("hc.holiday.centre.save").click();
    }



    public void inputHoliday(Holiday holiday) throws Exception {
        if (holiday.getDate() != null){
            element("hc.holiday.date").input(holiday.getDate().getRealValue());
        }
        if (holiday.getDescription() != null){
            element("hc.holiday.description").input(holiday.getDescription().getRealValue());
        }
        if (holiday.getRepeatYears() != null){
            element("hc.holiday.repeat.years").input(holiday.getRepeatYears().getRealValue());
        }
    }

    public void addHoliday() throws Exception {
        element("hc.holiday.add").click();
    }


    public String getXpath(Holiday holiday){
        StringBuffer xpath = new StringBuffer("//tr");
        if (holiday.getDate() != null){
//            xpath.append("[td=\""+holiday.getDate().getRealValue()+"\"]");
            xpath.append("[td=\"").append(holiday.getDate().getRealValue()).append("\"]");
        }
        if (holiday.getDescription() != null){
//            xpath.append("[td[contains(text(),\""+holiday.getDescription().getRealValue()+"\")]]");
            xpath.append("[td[contains(text(),\"").append(holiday.getDescription().getRealValue()).append("\")]]");
        }
        return xpath.toString();
    }
    //tr[td='2018-07-13'][td[contains(text(),'123')]]//img[@title='Edit Holiday']
    public void editHoliday(Holiday holiday) throws Exception {
        element("hc.holiday.edit", getXpath(holiday)).click();
    }

    public void deleteHoliday(Holiday holiday) throws Exception {
        element("hc.holiday.delete", getXpath(holiday)).clickByJavaScript();
    }

}
