package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.Agreement;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;


/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementCallScheduleSetupPage extends PageBase {
    public AgreementCallScheduleSetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setupAgreementCallSchedule(Agreement agmt) throws Exception {
    	setupCallFrequency(agmt);
		setupDfCallFrequency(agmt);
    	setupVMIMCallFrequency(agmt);
    	setupTimeZone(agmt);
        enterNextStage();
  }
    
    
    
    public void setupCallFrequency(Agreement agmt) throws Exception{
        //net agreement
        if (agmt.getCallFrequency() != null) {
            if (agmt.getCallFrequency().getLegalReviewFrequency() != null) {
                element("ap.lrf").selectByVisibleText(agmt.getCallFrequency().getLegalReviewFrequency().value());
            }
            if (agmt.getCallFrequency().getFrequencyStart() != null) {
                element("ap.frequency.start").input(agmt.getCallFrequency().getFrequencyStart().getRealValue());
            }
            if (agmt.getCallFrequency().getNotificationTime() != null && agmt.getCallFrequency().getNotificationTime().size() > 0) {
//            	please do a notification time enhancement for ETF agreement
                element("ap.notification.time").selectByVisibleText(agmt.getCallFrequency().getNotificationTime().get(0).getRealValue());
            }
            if (agmt.getCallFrequency().getResolutionTime() != null) {
                element("ap.resolution.time").selectByVisibleText(agmt.getCallFrequency().getResolutionTime().getRealValue());
            }
            if (agmt.getCallFrequency().getResolutionTimeType() != null) {
                element("ap.resolution.time.type").selectByVisibleText(agmt.getCallFrequency().getResolutionTimeType().getRealValue());
            }
        }
    }

	public void setupDfCallFrequency(Agreement agmt) throws Exception{
		//net agreement
		if (agmt.getDfCallFrequency() != null) {
			if (agmt.getDfCallFrequency().getLegalReviewFrequency() != null) {
				element("ap.lrf.df").selectByVisibleText(agmt.getDfCallFrequency().getLegalReviewFrequency().value());
			}
			if (agmt.getDfCallFrequency().getFrequencyStart() != null) {
				element("ap.frequency.start.df").input(agmt.getCallFrequency().getFrequencyStart().getRealValue());
			}
			if (agmt.getDfCallFrequency().getNotificationTime() != null && agmt.getCallFrequency().getNotificationTime().size() > 0) {
//            	please do a notification time enhancement for ETF agreement
				element("ap.notification.time.df").selectByVisibleText(agmt.getDfCallFrequency().getNotificationTime().get(0).getRealValue());
			}
			if (agmt.getDfCallFrequency().getResolutionTime() != null) {
				element("ap.resolution.time.df").selectByVisibleText(agmt.getDfCallFrequency().getResolutionTime().getRealValue());
			}
			if (agmt.getDfCallFrequency().getResolutionTimeType() != null) {
				element("ap.resolution.time.type.df").selectByVisibleText(agmt.getDfCallFrequency().getResolutionTimeType().getRealValue());
			}
		}
	}
    
    public void setupVMIMCallFrequency(Agreement agmt) throws Exception{
    	if(agmt.getNettingIAandMtM() != null){
    		if(agmt.getNettingIAandMtM().value().equalsIgnoreCase("Not-Net")){
//				if(agmt.getNettingIAandMtM().value().equals(NettingIAandMtMType.NOT_NET)){
    			 //vm call frequency
    	        if (agmt.getVmCallFrequency() != null) {
    	            if (agmt.getVmCallFrequency().getLegalReviewFrequency() != null) {
    	                element("ap.lrf").selectByVisibleText(agmt.getVmCallFrequency().getLegalReviewFrequency().value()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
    	            }
    	            if (agmt.getVmCallFrequency().getAdjustType() != null) {
    	                element("ap.adjust.type").selectByVisibleText(agmt.getVmCallFrequency().getAdjustType().value());
    	            }
    	            if (agmt.getVmCallFrequency().getFrequencyStart() != null) {
    	                element("ap.frequency.start").input(agmt.getVmCallFrequency().getFrequencyStart().getRealValue());
    	            }
    	            if (agmt.getVmCallFrequency().getNotificationTime() != null && agmt.getVmCallFrequency().getNotificationTime().size() > 0) {
    	            	//please do a notification time enhancement for ETF agreement
    	                element("ap.notification.time").selectByVisibleText(agmt.getVmCallFrequency().getNotificationTime().get(0).getRealValue());
    	            }
    	            if (agmt.getVmCallFrequency().getResolutionTime() != null) {
    	                element("ap.resolution.time").selectByVisibleText(agmt.getVmCallFrequency().getResolutionTime().getRealValue());
    	            }
    	            if (agmt.getVmCallFrequency().getResolutionTimeType() != null) {
    	                element("ap.resolution.time.type").selectByVisibleText(agmt.getVmCallFrequency().getResolutionTimeType().getRealValue());
    	            }
    	        }
    	        //im call frequency
    	        if (agmt.getImCallFrequency() != null) {
    	            if (agmt.getImCallFrequency().getLegalReviewFrequency() != null) {
    	                element("ap.lrf.im").selectByVisibleText(agmt.getImCallFrequency().getLegalReviewFrequency().value()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
    	            }
    	            if (agmt.getImCallFrequency().getAdjustType() != null) {
    	                element("ap.adjust.type.im").selectByVisibleText(agmt.getImCallFrequency().getAdjustType().value());
    	            }
    	            if (agmt.getImCallFrequency().getFrequencyStart() != null) {
    	                element("ap.frequency.start.im").input(agmt.getImCallFrequency().getFrequencyStart().getRealValue());
    	            }
    	            if (agmt.getImCallFrequency().getNotificationTime() != null && agmt.getImCallFrequency().getNotificationTime().size() > 0) {
//    	            	please do a notification time enhancement for ETF agreement
    	                element("ap.notification.time.im").selectByVisibleText(agmt.getImCallFrequency().getNotificationTime().get(0).getRealValue());
    	            }
    	            if (agmt.getImCallFrequency().getResolutionTime() != null) {
    	                element("ap.resolution.time.im").selectByVisibleText(agmt.getImCallFrequency().getResolutionTime().getRealValue());
    	            }
    	            if (agmt.getImCallFrequency().getResolutionTimeType() != null) {
    	                element("ap.resolution.time.type.im").selectByVisibleText(agmt.getImCallFrequency().getResolutionTimeType().getRealValue());
    	            }
    	        }   
        	}
    	}
    	
       
    }
    
    public void setupTimeZone(Agreement agmt) throws Exception{
    	if (agmt.getTimeZone() != null) {
            element("ap.time.zone").selectByVisibleText(agmt.getTimeZone().getRealValue());
        }
        if (agmt.getHolidayCentre() != null && !agmt.getHolidayCentre().isEmpty())
			element("ap.holiday.centre").deselectAll();
            for (StringType holiday : agmt.getHolidayCentre())
                element("ap.holiday.centre").selectByVisibleText(holiday.getRealValue());
        if (agmt.isClientReleaseRequired() != null) {
            element("ap.crr").check(agmt.isClientReleaseRequired());
        }
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

	public void saveAndExit() throws Exception {
		element("ap.save.and.exit").click();
	}
}
