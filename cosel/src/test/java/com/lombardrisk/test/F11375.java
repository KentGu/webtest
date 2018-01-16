package com.lombardrisk.test;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Kenny Wang on 3/22/2016.
 */
public class F11375 extends TestNGCase {

    @BeforeMethod(dependsOnMethods = {"setUpMethod"})
    public void background() throws Exception {
        given.have_login_with_credential("login.credential.test1");
    }

    @Test
    public void testCase32467() throws Exception {
        //step1(1.go to Collateral>static data>Asset definition,For Bonds)
        when.navigate_to_asset_definition_page();
        when.add_asset_class_category_type(Collections.singletonList("ac1"));
        when.add_asset_class_category_type(Collections.singletonList("at1"));
        when.add_asset_class_category_type(Collections.singletonList("ac2"));
        when.add_asset_class_category_type(Collections.singletonList("at2"));

        //step2(go to add instrument under bond-usd instrument1:price=100,instrument2:price=1)
        when.navigate_to_security_search_page();
        when.add_securities(Collections.singletonList("ins1"));
        when.search_security("ins1Search");
        when.approve_securities(Collections.singletonList("ins1SearchResult1"));
        when.add_securities(Collections.singletonList("ins2"));
        when.search_security("ins2Search");
        when.approve_securities(Collections.singletonList("ins1SearchResult2"));

        //step3(add clearing agreement)
        when.add_agreements("Clearing", Collections.singletonList("agr"));
        when.view_statement();
        when.edit_exposure_summary_info();
        when.view_product_type_on_exposure_summary_page("agreementSummary1");
        when.add_trade(Collections.singletonList("agrTrade1"));
        when.quick_link_view_statement();
        when.approve_agreement("agr");

        //step05(tick event1,make bulkbooking,instrument1 10003000.01,instrument2 1000000.00)
        when.quick_link_agreement_exposure_management();
        when.exposure_management_tick_event(Collections.singletonList("tickEvent1"));
        when.add_bulk_booking(Arrays.asList("makeBulkbooking1","makeBulkbooking2"));
        when.quick_link_save();
        //step06 need to do asset bulk booking warning message
        then.exposure_management_booking_message_should_be("bookingWarning1");


        //step07(change bulkbooking, change booking1 notional 1000309)
        when.quick_link_change_bookings();
        when.add_bulk_booking(Arrays.asList("makeBulkbookingChangeBooking1","makeBulkbooking4"));
        when.quick_link_save();


        //step08(override warning message)
        then.exposure_management_booking_message_should_be("bookingWarning2");
        //setp09 tick override warning message and then save
        when.override_warning("bookingWarning2");
        when.quick_link_save();


        //Step10
        when.search_agreement("searchAgr");
        when.view_completed_agreement_statement_from_agreement_search_page("searchAgrResult");
        when.edit_asset_summary_info();
        when.view_asset_type_in_agreement_asset_page("assetBookingSummaryForBooking1","Delivery");
        //edit booking1 1000309 to 1000300
        when.edit_booking_to("assetBookingSummaryForBooking2","booking3");

        //Step11(cancel booking1)
        when.search_agreement("searchAgr");
        when.view_completed_agreement_statement_from_agreement_search_page("searchAgrResult");
        when.edit_asset_summary_info();
        when.view_asset_type_in_agreement_asset_page("assetBookingSummaryForBooking1","Delivery");
        //cancel booking1
        when.edit_booking_to("assetBookingSummaryForBooking3","cancelBooking1");

        //step12(cancel booking2)
        when.search_agreement("searchAgr");
        when.view_completed_agreement_statement_from_agreement_search_page("searchAgrResult");
        when.edit_asset_summary_info();
        when.view_asset_type_in_agreement_asset_page("assetBookingSummaryForBooking4","Delivery");
        //cancel booking2
        when.edit_booking_to("assetBookingSummaryForBooking4","cancelBooking2");

        //step13
        when.search_agreement("searchAgr");
        when.view_completed_agreement_statement_from_agreement_search_page("searchAgrResult");
        when.approve_agreement("approveAgreement");

        //step14
        when.quick_link_agreement_exposure_management();
        when.exposure_management_tick_event(Collections.singletonList("tickEvent2"));
        when.add_bulk_booking(Arrays.asList("makeBulkbooking3","makeBulkbooking4"));
        when.quick_link_save();

//        //step15
        when.feed_asset_bookings(Collections.singletonList("feedBooking"),"xml");
        //assert feed result
        then.feed_log_should_be("feedBookingResult");
    }

}
