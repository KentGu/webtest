@InterestManager @regression
Feature: InterestManager

  Background:
    Given have login with credential login.credential.test1
    And have collateral preferences collateral.preference1
    And have default stp configuration
    And have default organisation global preferences

  @KentGu @InterestManagerSearch @T30911 @F7836 @14.0.0 @Reviewed
  Scenario: IM ISSR-01-52 Verify Flushed Booking should be ignored from Interest Manager
    When navigate to interest manager search page
    And Interest Manager - search interest event by net.interest.event.search
    Then Interest Manager - event net.interest.event is existed
    And search agreement search.otc.net.interest.agreement
    And view completed agreement statement search.result.otc.net.interest.agreement
    And edit asset summary info
    And view asset type qtp.cash.usd agreement asset CASH Page
    And edit call booking call.cash.booking to flush.cash.booking
    When navigate to interest manager search page
    And Interest Manager - search interest event by net.interest.event.search
    Then Interest Manager - event net.interest.event is not existed

  @KentGu @InterestManagerWorkflow @T21662 @F4031 @2012.2 @Reviewed
  Scenario: IM IAA 01-21 Verify Eligibility Check and booking correct by Apply Intrest Booking  for multi-model agreement-FCM, Cash
    When navigate to interest manager search page
    And Interest Manager - search interest event by net.interest.event.search
    And Interest Manager - switch to Pay tab
    And expand Multimodel interest event in IM
    And Interest Manager - tick interest events interest.event.model.usd.cash.eur,interest.event.model.eur.cash.eur,interest.event.model.usd.cash.cad,interest.event.model.eur.cash.cad
    And Interest Manager - Apply interest movement interest.event.model.usd.cash.eur,interest.event.model.eur.cash.eur,interest.event.model.usd.cash.cad,interest.event.model.eur.cash.cad
    And expand Multimodel interest event in IM
    Then Interest Manager interest.event.model.usd.cash.eur - event should be check.interest.event.model.usd.cash.eur
    And Interest Manager interest.event.model.eur.cash.eur - event should be check.interest.event.model.eur.cash.eur
    And Interest Manager interest.event.model.usd.cash.cad - event should be check.interest.event.model.usd.cash.cad
    And Interest Manager interest.event.model.eur.cash.cad - event should be check.interest.event.model.eur.cash.cad
    When Interest Manager - switch to Receive tab
    And expand Multimodel interest event in IM
    And Interest Manager - Apply interest movement interest.event.model.usd.cash.usd,interest.event.model.eur.cash.usd
    And expand Multimodel interest event in IM
    Then Interest Manager interest.event.model.usd.cash.usd - event should be check.interest.event.model.usd.cash.usd
    And Interest Manager interest.event.model.eur.cash.usd - event should be check.interest.event.model.eur.cash.usd

  @KentGu @InterestManagerSearch @T30345 @F8767 @13.3.0
  Scenario: IM IAA 01-25 Verify respective event on IM get unapplied if it is not purged when interest linked this event is cancelled-pay
    When navigate to interest manager search page
    And Interest Manager - search interest event by net.interest.event.search
    And Interest Manager - switch to Pay tab
    Then Interest Manager original.net.interest.event - event should be before.apply.interest.movement.net.interest.event
    When Interest Manager - tick interest events original.net.interest.event
    And Interest Manager - change interest events to input.agreed.interest.amount.and.wht.amount
    And Interest Manager - Apply interest movement apply.interest.movement.event
    Then Interest Manager original.net.interest.event - event should be after.apply.interest.movement.net.interest.event
    Given switch user to login.credential.user2
    When navigate to settlement status page
    And settlement status - search settlement status search.net.interest.movement.settlement
    And settlement status - show asset qtp.cash.usd.settlement details
    And settlement status - approve all pending in settlement status summary
    And settlement status - approve all authorised in settlement status summary
    And settlement status - edit cash movement booking original.qtp.cash.usd.interest.movement.booking.event to cancel.cash.movement.booking
    When navigate to interest manager search page
    And Interest Manager - search interest event by net.interest.event.search
    And Interest Manager - switch to Pay tab
    Then Interest Manager original.net.interest.event - event should be before.apply.interest.movement.net.interest.event

  @KentGu @InterestManagerWorkflow @T4533 @2011.1 @2011.2
  Scenario: IM IARI 01-01 Verify Reset All for Normal agreements
    When navigate to interest manager search page
    And Interest Manager - search interest event by not.net.interest.event.search
    And Interest Manager - switch to Pay tab
    Then Interest Manager original.not.net.interest.event - event should be before.email.and.apply.interest.movement.not.net.interest.pay.event
    When Interest Manager - tick interest events original.not.net.interest.event
    And Interest Manager - email for event email.not.net.interest.pay.event
    And Interest Manager - tick interest events original.not.net.interest.event
#    And Interest Manager - Apply interest movement apply.not.net.interest.pay.event
    Then Interest Manager original.not.net.interest.event - event should be after.email.and.apply.interest.movement.not.net.interest.pay.event
    When Interest Manager - switch to Capitalise receive tab
    Then Interest Manager original.not.net.interest.event - event should be before.email.and.apply.interest.movement.not.net.interest.capitalise.receive.event
    When Interest Manager - tick interest events original.not.net.interest.event
    And Interest Manager - email for event email.not.net.interest.capitalise.receive.event
    And Interest Manager - tick interest events original.not.net.interest.event
#    And Interest Manager - Apply interest movement apply.not.net.interest.capitalise.receive.event
    Then Interest Manager original.not.net.interest.event - event should be after.email.and.apply.interest.movement.not.net.interest.capitalise.receive.event
    When Interest Manager - reset all events
    And Interest Manager - switch to Pay tab
    Then Interest Manager original.not.net.interest.event - event should be before.email.and.apply.interest.movement.not.net.interest.pay.event
    When Interest Manager - switch to Capitalise receive tab
    Then Interest Manager original.not.net.interest.event - event should be before.email.and.apply.interest.movement.not.net.interest.capitalise.receive.event

  @KentGu @InterestManagerWorkflow @T30215 @F8767 @13.3.0 @wip
  Scenario: IM IARI 01-20 Verify reset all interest movement applied im event-booking which is cancle or failed-collateral at fund-receive
    When navigate to interest manager search page
    And Interest Manager - search interest event by search.all.30215.interest.event
    And Interest Manager - switch to Capitalise receive tab
    And Interest Manager - expand top events agreement.1.umbrella.interest.event,agreement.2.umbrella.interest.event
    Then Interest Manager agreement.1.umbrella.interest.event - event should be agreement.1.umbrella.interest.event.before.apply
    And Interest Manager agreement.1.fund.1.interest.event - event should be agreement.1.fund.1.interest.event.before.apply
    And Interest Manager agreement.1.fund.2.interest.event - event should be agreement.1.fund.2.interest.event.before.apply
    And Interest Manager agreement.2.umbrella.interest.event - event should be agreement.2.umbrella.interest.event.before.apply
    And Interest Manager agreement.2.fund.1.interest.event - event should be agreement.2.fund.1.interest.event.before.apply
    And Interest Manager agreement.2.fund.2.interest.event - event should be agreement.2.fund.2.interest.event.before.apply
    When Interest Manager - tick interest events agreement.1.fund.1.interest.event,agreement.1.fund.2.interest.event,agreement.2.fund.1.interest.event,agreement.2.fund.2.interest.event
    And Interest Manager - Apply interest movement agreement.1.fund.1.interest.event.after.apply,agreement.1.fund.2.interest.event.after.apply,agreement.2.fund.1.interest.event.after.apply,agreement.2.fund.2.interest.event.after.apply
    And expand Umbrella interest event in IM
    Then Interest Manager agreement.1.fund.1.interest.event - event should be agreement.1.fund.1.interest.event.after.apply
    And Interest Manager agreement.1.fund.2.interest.event - event should be agreement.1.fund.2.interest.event.after.apply
    And Interest Manager agreement.2.fund.1.interest.event - event should be agreement.2.fund.1.interest.event.after.apply
    And Interest Manager agreement.2.fund.2.interest.event - event should be agreement.2.fund.2.interest.event.after.apply
    When Interest Manager - view interest movements for event agreement.1.fund.1.interest.event
    And edit cashmovement booking pending.cash.movement.event.summary to cancel.cash.movement.event.detail
    And navigate to interest manager search page
    And Interest Manager - search interest event by search.all.30215.interest.event
    And Interest Manager - switch to Capitalise receive tab
    And expand Umbrella interest event in IM
    And Interest Manager - view interest movements for event agreement.2.fund.1.interest.event
    And edit cashmovement booking pending.cash.movement.event.summary to cancel.cash.movement.event.detail
    And navigate to interest manager search page
    And Interest Manager - search interest event by search.all.30215.interest.event
    And Interest Manager - switch to Capitalise receive tab
    And expand Umbrella interest event in IM
    And Interest Manager - reset all events
    Then error message in IM Search Result should be reset.interest.manager.completed.2.success.2.failed
    When expand Umbrella interest event in IM
    Then Interest Manager agreement.1.fund.2.interest.event - event should be agreement.1.fund.2.interest.event.before.apply
    And Interest Manager agreement.2.fund.2.interest.event - event should be agreement.2.fund.2.interest.event.before.apply
    And Interest Manager - view interest movements for event agreement.1.fund.2.interest.event
    And edit cashmovement booking pending.cash.movement.event.summary to cancel.cash.movement.event.detail
    And navigate to interest manager search page
    And Interest Manager - search interest event by search.all.30215.interest.event
    And Interest Manager - switch to Capitalise receive tab
    And expand Umbrella interest event in IM
    And Interest Manager - view interest movements for event agreement.2.fund.1.interest.event
    And edit cashmovement booking pending.cash.movement.event.summary to cancel.cash.movement.event.detail
    And navigate to interest manager search page
    And Interest Manager - search interest event by search.all.30215.interest.event
    And Interest Manager - switch to Capitalise receive tab
    And expand Umbrella interest event in IM
    And Interest Manager - tick interest events agreement.1.fund.1.interest.event,agreement.2.fund.1.interest.event
    And Interest Manager - Apply interest movement agreement.1.fund.1.interest.event.after.apply,agreement.2.fund.1.interest.event.after.apply
    Then Interest Manager agreement.1.fund.1.interest.event - event should be agreement.1.fund.1.interest.event.after.apply
    And Interest Manager agreement.2.fund.1.interest.event - event should be agreement.2.fund.1.interest.event.after.apply
    When navigate to task scheduler page
    And task scheduler - switch to Workflow tab
    And edit task scheduler reset.interest.task
    And run task scheduler reset.interest.task
    Then task scheduler messaging should be reset.successful.2.failed.2
    When navigate to interest manager search page
    And Interest Manager - search interest event by search.all.30215.interest.event
    And Interest Manager - switch to Capitalise receive tab
    And expand Umbrella interest event in IM
    Then Interest Manager agreement.1.fund.2.interest.event - event should be agreement.1.fund.2.interest.event.before.apply
    And Interest Manager agreement.2.fund.2.interest.event - event should be agreement.2.fund.2.interest.event.before.apply
    When Interest Manager - tick interest events agreement.1.fund.1.interest.event,agreement.2.fund.1.interest.event
    And Interest Manager - Apply interest movement agreement.1.fund.1.interest.event.after.apply,agreement.2.fund.1.interest.event.after.apply
    When navigate to task scheduler page
    And task scheduler - switch to Workflow tab
    And edit task scheduler reset.interest.task
    And run task scheduler reset.interest.task
    Then task scheduler messaging should be reset.success.4.failed.0