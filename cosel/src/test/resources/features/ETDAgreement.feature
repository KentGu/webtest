@ETDAgreement
Feature: ETDAgreement

  Background:
    Given have login with credential login.credential.test1
    And have collateral preferences collateral.preference1
    And have default stp configuration
    And have default organisation global preferences
    And have collateral static data add.scheme.model.category,add.SEC.to.model.category,add.SEG.to.model.category,add.abc.to.model.category

  @BinHu @F14656 @COL-1099 @reviewed
  Scenario:verify new fields at etd balance feed for no calc_blankvalue
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.partial.physical
    And add ETD agreements add.etd.agreement.allow.adj.false.and.no.calc
    And view statement
    And approve agreement add.etd.agreement.allow.adj.false.and.no.calc

    When feed flush etd balances feed.etd.balance.with.bold.fields.blank by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When Go to agreement add.etd.agreement.allow.adj.false.and.no.calc statement page by URL
    Then etd agreement top level statement summary should be bold.fields.equal.to.0

    When feed flush etd balances feed.etd.balance.with.bold.fields.valued by csv
    Then feed log should be feed.successful.1.unsuccessful.0
    When Go to agreement add.etd.agreement.allow.adj.false.and.no.calc statement page by URL
    Then etd agreement top level statement summary should be bold.fields.equal.to.feed.value

    When feed flush etd balances feed.etd.balance.without.bold.fields by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When Go to agreement add.etd.agreement.allow.adj.false.and.no.calc statement page by URL
    Then etd agreement top level statement summary should be bold.fields.not.in.feed.then.equal.to.0

    When feed delta etd balances delta.feed.etd.balance.update by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When Go to agreement add.etd.agreement.allow.adj.false.and.no.calc statement page by URL
    Then etd agreement top level statement summary should be bold.fields.delta.update

  @BinHu @F14656 @COL-1609 @reviewed
  Scenario:verify em page display etd cashflow events tree and events count_bk_nocalc_changecashflow rule
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule1.all.capitalised
    And add cashflow rules cashflow.rule2.partial.physical
    And add ETD agreements add.etd.agreement.broker.and.no.calc
    And view statement
    And approve agreement add.etd.agreement.broker.and.no.calc

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0.1st

    When feed flush etd balances feed.etd.balance.for.model2 by excel
    Then feed log should be feed.successful.1.unsuccessful.0.2nd

    When Go to agreement add.etd.agreement.broker.and.no.calc statement page by URL
    When quick link - agreement exposure management
    And Exposure Management - add column add.column.model.category,add.column.model
    Then Exposure Management - event should be no.cashflow.event.in.em

    When Go to agreement add.etd.agreement.broker.and.no.calc statement page by URL
    And quick link - agreement review
    And edit ETD agreement add.etd.agreement.broker.and.no.calc to change.cashflow.rule.from.rule1.to.rule2 in document tab
    And view statement
    Then Agreement Statement - etd agreement top level statement summary should be cashflow.model1.400.and.model2.-800

    When approve agreement apporve.agreement.to.approved.status
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level
    And Exposure Management - expand events expand.cashflow.category.level.SEC
    And Exposure Management - expand events expand.cashflow.category.level.SEG
    Then Exposure Management - event should be agreement.level.-133.33,category.level.SEC.400,category.level.SEG.-533.33,model.level.model1.400,model.level.model2.-800

  @BinHu @F14656 @COL-1102 @reviewed
  Scenario: Verify EM page display ETD Cashflow events tree and events count_BK_NoCalc_changeCashflowEvent
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule2.partial.physical
    And add ETD agreements add.etd.agreement.allow.adj.and.no.calc
    And view statement
    And approve agreement add.etd.agreement.allow.adj.and.no.calc

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0.1st

    When feed flush etd balances feed.etd.balance.for.model2 by excel
    Then feed log should be feed.successful.1.unsuccessful.0.2nd

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level
    And Exposure Management - expand events expand.cashflow.category.level.SEC
    And Exposure Management - expand events expand.cashflow.category.level.SEG
    Then Exposure Management - event should be agreement.level.-133.33,category.level.SEC.400,category.level.SEG.-533.33,model.level.model1.400,model.level.model2.-800

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model1.cashflow.physical.value.to.0
    And Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEG.USD
    And select etd model select.model2
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model2.cashflow.physical.value.to.0
    And Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And approve agreement apporve.agreement.to.approved.status
    And quick link - agreement exposure management
    Then Exposure Management - event should be no.cashflow.events.exist.in.em

  @BinHu @F14656 @COL-1613 @reviewed
  Scenario: Verify cashflow event can be get and save correctly when create cashflow bookings for member agreement
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.partial.physical
    And add ETD agreements add.member.etd.agreement.allow.adj.and.standard
    And view statement
    And approve agreement add.member.etd.agreement.allow.adj.and.standard

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0

    When Go to agreement add.member.etd.agreement.allow.adj.and.standard statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.1
    And Exposure Management - expand events expand.cashflow.category.level.SEC.1
    Then Exposure Management - event should be model.level.model1.400

    When Go to agreement add.member.etd.agreement.allow.adj.and.standard statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And edit asset summary info
    And view asset type Cash-USD agreement asset Cash Movements Page
    And add Cash Component booking - statement booking add.linked.event.cashflow.booking.wire.in

    When Go to agreement add.member.etd.agreement.allow.adj.and.standard statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be model1.cashflow.call.excess.event.completed

    When Go to agreement add.member.etd.agreement.allow.adj.and.standard statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model1.cashflow.physical.value.to.-400
    And Go to agreement add.member.etd.agreement.allow.adj.and.standard statement page by URL
    And approve agreement approve.agreement.to.approved.status
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.2
    And Exposure Management - expand events expand.cashflow.category.level.SEC.2
    Then Exposure Management - event should be model.level.model1.-400

    When Go to agreement add.member.etd.agreement.allow.adj.and.standard statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And edit asset summary info
    And view asset type Cash-USD agreement asset Cash Movements Page
    And add Cash Component booking - statement booking add.linked.event.cashflow.booking.wire.out

    When Go to agreement add.member.etd.agreement.allow.adj.and.standard statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be model1.cashflow.delivery.deficit.event.completed

  @BinHu @F14656 @COL-1623 @reviewed
  Scenario: Verify cashflow event can be get and save correctly when create cashflow bookings for broker agreement
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.partial.physical
    And add ETD agreements add.broker.etd.agreement.allow.adj.and.no.calc
    And view statement
    And approve agreement add.broker.etd.agreement.allow.adj.and.no.calc

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0

    When Go to agreement add.broker.etd.agreement.allow.adj.and.no.calc statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.1
    And Exposure Management - expand events expand.cashflow.category.level.SEC.1
    Then Exposure Management - event should be model.level.model1.400

    When Go to agreement add.broker.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And edit asset summary info
    And view asset type Cash-USD agreement asset Cash Movements Page
    And add Cash Component booking - statement booking add.linked.event.cashflow.booking.wire.out

    When Go to agreement add.broker.etd.agreement.allow.adj.and.no.calc statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be model1.cashflow.delivery.excess.event.completed

    When Go to agreement add.broker.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model1.cashflow.physical.value.to.-400
    And Go to agreement add.broker.etd.agreement.allow.adj.and.no.calc statement page by URL
    And approve agreement approve.agreement.to.approved.status
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.2
    And Exposure Management - expand events expand.cashflow.category.level.SEC.2
    Then Exposure Management - event should be model.level.model1.-400

    When Go to agreement add.broker.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And edit asset summary info
    And view asset type Cash-USD agreement asset Cash Movements Page
    And add Cash Component booking - statement booking add.linked.event.cashflow.booking.wire.in

    When Go to agreement add.broker.etd.agreement.allow.adj.and.no.calc statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be model1.cashflow.call.deficit.event.completed

  @BinHu @F14656 @COL-2084 @reviewed
  Scenario: Verify sufficient Check for Bulk booking - Cashflow bookings
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.partial.physical
    And add ETD agreements add.broker.etd.agreement.component.and.standard
    And view statement
    And approve agreement add.broker.etd.agreement.component.and.standard

    And feed flush etd balances feed.etd.balance.for.model1.cashflow.positive by csv
    Then feed log should be feed.successful.1.unsuccessful.0.1st

    When Go to agreement add.broker.etd.agreement.component.and.standard statement page by URL
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.delivery.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.excess
    And Exposure Management - tick events tick.model1.cashflow.event.delivery.excess
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bulk.booking.component.upfront.fee.wire.out.3m
    And quick link - save
    Then Exposure Managerment - EM booking message should be warning.message.exceeds.required.amount
    When Exposure Management - override warning override.warning.message
    And quick link - save
    And Exposure Management - expand events expand.cashflow.agreement.level.delivery.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.excess
    Then Exposure Management - event should be delivery.excess.cashflow.event.status.turn.to.partially.booked

    When feed flush etd balances feed.etd.balance.for.model1.cashflow.negative by excel
    Then feed log should be feed.successful.1.unsuccessful.0.2nd

    When Go to agreement add.broker.etd.agreement.component.and.standard statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.call.deficit
    And Exposure Management - expand events expand.cashflow.category.level.SEC.call.deficit
    And Exposure Management - tick events tick.model1.cashflow.event.call.deficit
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bulk.booking.component.upfront.fee.wire.in.2m
    And quick link - save
    And Exposure Management - expand events expand.cashflow.agreement.level.call.deficit
    And Exposure Management - expand events expand.cashflow.category.level.SEC.call.deficit
    Then Exposure Management - event should be call.deficit.cashflow.event.status.turn.to.partially.booked

  @BinHu @F14656 @COL-1418 @reviewed
  Scenario: Verify that all calculated fields are calculated correctly when Allow Adj is set to True and Default Fund is Net DForIM_No Calc Statement Calc
    When navigate to stp switch page
    And stp - set stp switch set.stp.on
    And navigate to cashflow rules page
    And add cashflow rules cashflow.rule.partial.physical
    And add ETD agreements add.member.etd.agreement.allow.adj.and.no.calc
    And view statement
    And approve agreement add.member.etd.agreement.allow.adj.and.no.calc

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0

    When Go to agreement add.member.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    Then Agreement Statement - etd agreement model level statement summary should be model1.value.ed.pendinged.imsecurities.imcash.and.currented

    When Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.option.premium.adj.value.to.-300
    When Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.im.securities.adj.value.to.-400
    When Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.im.dcvm.adj.value.to.-500
    Then Agreement Statement - etd agreement model level statement summary should be model1.value.of.im.securities.changed.to.-400

    When edit asset summary info
    And view asset type CashUSDType agreement asset CASH Page
    And add wire in bookings - statement booking add.booking1.cash.usd.cash.wire.in.100
    And add wire in bookings - statement booking add.booking2.cash.usd.cash.wire.in.200
    And edit wire in booking edit.booking2 to approve.to.authorised.status
    And edit wire in booking edit.booking2 to approve.to.pending.release.status
    And edit wire in booking edit.booking2 to approve.to.pending.settlement.status
    And edit wire in booking edit.booking2 to approve.to.settled.status
    And add wire in bookings - statement booking add.booking3.cash.usd.im.wire.in.300
    And edit wire in booking edit.booking3 to approve.to.authorised.status
    And edit wire in booking edit.booking3 to approve.to.pending.release.status
    And add wire in bookings - statement booking add.booking4.cash.usd.im.wire.in.400
    And edit wire in booking edit.booking4 to approve.to.authorised.status
    And edit wire in booking edit.booking4 to approve.to.pending.release.status
    And edit wire in booking edit.booking4 to approve.to.pending.settlement.status
    And edit wire in booking edit.booking4 to approve.to.settled.status
    And click back button to previous page
    And view asset type BondUSDType agreement asset Bond Page
    And add pledge in bookings - statement booking add.booking5.bond.usd.pledge.in.500
    And edit wire in booking edit.booking5 to approve.to.authorised.status
    And edit wire in booking edit.booking5 to approve.to.pending.release.status
    And edit wire in booking edit.booking5 to approve.to.pending.settlement.status
    And add pledge in bookings - statement booking add.booking6.bond.usd.pledge.out.-100
    And edit wire in booking edit.booking6 to approve.to.authorised.status
    And edit wire in booking edit.booking6 to approve.to.pending.release.status
    And edit wire in booking edit.booking6 to approve.to.pending.settlement.status
    And edit wire in booking edit.booking6 to approve.to.settled.status

    And Go to agreement add.member.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    Then Agreement Statement - etd agreement model level statement summary should be model1.value.of.pending.ed.-12.im.seurities.-800.im.cash.-800.changed

  @BinHu @F14656 @COL-1417 @reviewed
  Scenario: Verify that all calculated fields are calculated correctly when Allow Adj is set to True and Default Fund is Standalone DF_No Calc Statement Calc
    When navigate to stp switch page
    And stp - set stp switch set.stp.on
    And navigate to cashflow rules page
    And add cashflow rules cashflow.rule.partial.physical
    And add ETD agreements add.member.etd.agreement.standalone.df
    And view statement
    And approve agreement add.member.etd.agreement.standalone.df

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0

    When Go to agreement add.member.etd.agreement.standalone.df statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    Then Agreement Statement - etd agreement model level statement summary should be model1.value.df.ed.and.df.pending.ed.and.df.current.ed

    When Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.option.premium.adj.value.to.-300
    When Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.im.securities.adj.value.to.-400
    When Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.im.dcvm.adj.value.to.-500
    Then Agreement Statement - etd agreement model level statement summary should be model1.value.of.im.securities.changed.to.-500

    When edit asset summary info
    And view asset type CashUSDType agreement asset CASH Page
    And add wire in bookings - statement booking add.booking1.cash.usd.cash.wire.in.100
    And add wire in bookings - statement booking add.booking2.cash.usd.cash.wire.in.200
    And edit wire in booking edit.booking2 to approve.to.authorised.status
    And edit wire in booking edit.booking2 to approve.to.pending.release.status
    And edit wire in booking edit.booking2 to approve.to.pending.settlement.status
    And edit wire in booking edit.booking2 to approve.to.settled.status
    And add wire in bookings - statement booking add.booking3.cash.usd.im.wire.in.300
    And edit wire in booking edit.booking3 to approve.to.authorised.status
    And edit wire in booking edit.booking3 to approve.to.pending.release.status
    And add wire in bookings - statement booking add.booking4.cash.usd.im.wire.in.400
    And edit wire in booking edit.booking4 to approve.to.authorised.status
    And edit wire in booking edit.booking4 to approve.to.pending.release.status
    And edit wire in booking edit.booking4 to approve.to.pending.settlement.status
    And edit wire in booking edit.booking4 to approve.to.settled.status
    And add wire in bookings - statement booking add.booking7.cash.usd.df.wire.in.100
    And click back button to previous page
    And view asset type BondUSDType agreement asset Bond Page
    And add pledge in bookings - statement booking add.booking5.bond.usd.pledge.in.500
    And edit wire in booking edit.booking5 to approve.to.authorised.status
    And edit wire in booking edit.booking5 to approve.to.pending.release.status
    And edit wire in booking edit.booking5 to approve.to.pending.settlement.status
    And add pledge in bookings - statement booking add.booking6.bond.usd.pledge.out.-100
    And edit wire in booking edit.booking6 to approve.to.authorised.status
    And edit wire in booking edit.booking6 to approve.to.pending.release.status
    And edit wire in booking edit.booking6 to approve.to.pending.settlement.status
    And edit wire in booking edit.booking6 to approve.to.settled.status
    And add pledge in bookings - statement booking add.booking8.bond.usd.df.pledge.out.-200
    And edit wire in booking edit.booking8 to approve.to.authorised.status
    And edit wire in booking edit.booking8 to approve.to.pending.release.status
    And edit wire in booking edit.booking8 to approve.to.pending.settlement.status
    And edit wire in booking edit.booking8 to approve.to.settled.status

    And Go to agreement add.member.etd.agreement.standalone.df statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    Then Agreement Statement - etd agreement model level statement summary should be model1.value.of.pending.ed.-12.df.pending.ed.899.changed

  @BinHu @F14656 @COL-1606 @reviewed
  Scenario: Verify ETD Cashflow events calc logic_DF_Standard_changeCashflow Event
    When navigate to FX rate page
    And add FX rate sets fx.rate.usd.eur.0.5
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.partial.physical
    And add ETD agreements add.member.etd.agreement.allow.adj.and.standard
    And view statement
    And approve agreement add.member.etd.agreement.allow.adj.and.standard

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0.1st

    When feed flush etd balances feed.etd.balance.for.model3 by excel
    Then feed log should be feed.successful.1.unsuccessful.0.2nd

    When Go to agreement add.member.etd.agreement.allow.adj.and.standard statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be model1.usd.27400.model3.usd.-5598.eur.-2799.totals.21802
    When quick link - agreement exposure management
    And Exposure Management - add column add.column.model.category,add.column.model
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC
    Then Exposure Management - event should be model.level.model1.27400
    When Exposure Management - expand events expand.cashflow.category.level.abc
    Then Exposure Management - event should be model.level.model3.-2799

    When Go to agreement add.member.etd.agreement.allow.adj.and.standard statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.abc.usd
    And select etd model select.model3
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model3.pai.adj.to.-1997101

    When Go to agreement add.member.etd.agreement.allow.adj.and.standard statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be model3.usd.-4000000.eur.-2000000.totals.-3972600

    When approve agreement apporve.agreement.to.approved.status
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.SEC
    And Exposure Management - expand events expand.cashflow.category.level.abc
    Then Exposure Management - event should be agreement.level.-3972600,category.level.SEC.27400,category.level.abc.-4000000,model.level.model1.27400,model.level.model3.-2000000

  @BinHu @F14656 @COL-1596 @reviewed
  Scenario: Verify ETD Cashflow events calc logic_BK_Stand_changeCashflow Event
    When navigate to FX rate page
    And add FX rate sets fx.rate.usd.eur.0.5
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.partial.physical
    And add ETD agreements add.etd.agreement.allow.adj.and.standard
    And view statement
    And approve agreement add.etd.agreement.allow.adj.and.standard

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0.1st

    When feed flush etd balances feed.etd.balance.for.model3 by excel
    Then feed log should be feed.successful.1.unsuccessful.0.2nd

    When Go to agreement add.etd.agreement.allow.adj.and.standard statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model1.upfront.fee.value.to.55600
    Then Agreement Statement - etd agreement model level statement summary should be cashflow.physical.40000

    When Go to agreement add.etd.agreement.allow.adj.and.standard statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.abc.USD
    And select etd model select.model3
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model3.pai.value.to.-16500
    Then Agreement Statement - etd agreement model level statement summary should be cashflow.physical.-10000

    When Go to agreement add.etd.agreement.allow.adj.and.standard statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be cashflow.SEC.40000.model1.40000.abc.-20000.model3.-10000.totals.20000

    When approve agreement apporve.agreement.to.approved.status
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.delivery.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.excess
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.deficit
    Then Exposure Management - event should be agreement.level.cashflow.event.delivery.excess.20000,category.SEC.cashflow.event.delivery.excess.40000,category.abc.cashflow.event.call.deficit.-20000,model1.cashflow.event.delivery.excess.40000,model3.cashflow.event.call.deficit.-10000

    When Go to agreement add.etd.agreement.allow.adj.and.standard statement page by URL
    And quick link - agreement review
    And edit ETD agreement add.etd.agreement.allow.adj.and.standard to untick.allow.adj.checkbox in document tab
    And view statement
    Then Agreement Statement - etd agreement top level statement summary should be cashflow.SEC.-14199.model1.-14199.abc.12600.model3.6300.totals.-1599

    When approve agreement apporve.agreement.to.approved.status
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.call.deficit
    And Exposure Management - expand events expand.cashflow.category.level.SEC.call.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.delivery.excess
    Then Exposure Management - event should be agreement.level.cashflow.event.call.deficit.-1599,category.SEC.cashflow.event.call.deficit.-14199,category.abc.cashflow.event.delivery.excess.12600,model1.cashflow.event.call.deficit.-14199,model3.cashflow.event.delivery.excess.6300

  @BinHu @F14656 @COL-2097 @reviewed
  Scenario: Verify the ETD_member_Cashflow Workflow Status Auto Shifting
    When navigate to FX rate page
    And add FX rate sets fx.rate.usd.eur.0.5
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.partial.physical
    And add ETD agreements add.etd.agreement.allow.adj.and.no.calc
    And view statement
    And approve agreement add.etd.agreement.allow.adj.and.no.calc

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0.1st

    When feed flush etd balances feed.etd.balance.for.model3 by excel
    Then feed log should be feed.successful.1.unsuccessful.0.2nd

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model1.cashflow.value.to.-1m

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.abc.USD
    And select etd model select.model3
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model3.cashflow.value.to.2.2m

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be cashflow.SEC.-1m.abc.4.4m

    When quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.excess
    Then Exposure Management - event should be agreement.level.cashflow.event.call.excess.pending.review.3.4m
    And Exposure Management - event should be category.SEC.cashflow.event.delivery.deficit.pending.review.-1m
    And Exposure Management - event should be category.abc.cashflow.event.call.excess.pending.review.4.4m
    And Exposure Management - event should be model1.cashflow.event.delivery.deficit.pending.review.-1m
    And Exposure Management - event should be model3.cashflow.event.call.excess.pending.review.2.2m

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And edit asset summary info
    And view asset type Cash-USD agreement asset Cash Movements Page
    And add Cash Component booking - statement booking add.linked.event.cashflow.booking.for.model1.wire.out.0.5m

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.abc.USD
    And select etd model select.model3
    And edit asset summary info
    And view asset type Cash-EUR agreement asset Cash Movements Page
    And add Cash Component booking - statement booking add.linked.event.cashflow.booking.for.model3.wire.in.2.2m

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And approve agreement apporve.agreement.to.approved.status
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.excess
    Then Exposure Management - event should be agreement.level.cashflow.event.partially.booked
    And Exposure Management - event should be category.SEC.cashflow.event.partially.booked
    And Exposure Management - event should be category.abc.cashflow.event.completed
    And Exposure Management - event should be model1.cashflow.event.partially.booked
    And Exposure Management - event should be model3.cashflow.event.completed

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And edit asset summary info
    And view asset type Cash-USD agreement asset Cash Movements Page
    And add Cash Component booking - statement booking add.linked.event.cashflow.booking.for.model1.wire.out.0.5m

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.abc.USD
    And select etd model select.model3
    And edit asset summary info
    And view asset type Cash-EUR agreement asset Cash Movements Page
    And add Cash Component booking - statement booking add.linked.event.cashflow.booking.for.model3.wire.in.1k

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And approve agreement apporve.agreement.to.approved.status
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.excess
    Then Exposure Management - event should be agreement.level.cashflow.event.partially.booked
    And Exposure Management - event should be category.SEC.cashflow.event.completed
    And Exposure Management - event should be category.abc.cashflow.event.partially.booked
    And Exposure Management - event should be model1.cashflow.event.completed
    And Exposure Management - event should be model3.cashflow.event.partially.booked

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be cashflow.SEC.-1m.abc.4.4m

    When select etd model select.model1
    And edit asset summary info
    And view asset type Cash-USD agreement asset Cash Movements Page
    And edit cashmovement booking edit.model1.booking1.-0.5m to set.status.to.cancelled

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    When Agreement Statement - etd agreement statement - expand category expand.category.abc.USD
    And select etd model select.model3
    And edit asset summary info
    And view asset type Cash-EUR agreement asset Cash Movements Page
    And edit cashmovement booking edit.model1.booking1.2.2m to set.status.to.cancelled
    And edit cashmovement booking edit.model1.booking2.1k to set.status.to.cancelled

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And approve agreement apporve.agreement.to.approved.status
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.excess
    Then Exposure Management - event should be agreement.level.cashflow.event.partially.booked
    And Exposure Management - event should be category.SEC.cashflow.event.partially.booked.2
    And Exposure Management - event should be category.abc.cashflow.event.pending.review
    And Exposure Management - event should be model1.cashflow.event.partially.booked
    And Exposure Management - event should be model3.cashflow.event.pending.review

    When Exposure Management - tick events tick.model1.cashflow.event,tick.model3.cashflow.event
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bulk.booking.model1.wire.out.-0.6m,bulk.booking.model3.wire.in.2m
    And quick link - save
    And Exposure Management - override warning override.warning.message.1st
    And quick link - save
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.excess
    Then Exposure Management - event should be agreement.level.cashflow.event.partially.booked
    And Exposure Management - event should be category.SEC.cashflow.event.partially.booked.3
    And Exposure Management - event should be category.abc.cashflow.event.partially.booked.2
    And Exposure Management - event should be model1.cashflow.event.partially.booked
    And Exposure Management - event should be model3.cashflow.event.partially.booked

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And edit asset summary info
    And view asset type Cash-USD agreement asset Cash Movements Page
    And edit cashmovement booking edit.model1.booking1.-0.6m to set.status.to.cancelled

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.excess
    And Exposure Management - tick events tick.model1.cashflow.event,tick.model3.cashflow.event
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bulk.booking.model1.wire.out.-0.5m,bulk.booking.model3.wire.in.0.2m
    And quick link - save
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.excess
    Then Exposure Management - event should be agreement.level.cashflow.event.completed
    And Exposure Management - event should be category.SEC.cashflow.event.completed.2
    And Exposure Management - event should be category.abc.cashflow.event.completed.2
    And Exposure Management - event should be model1.cashflow.event.completed
    And Exposure Management - event should be model3.cashflow.event.completed

  @BinHu @F14656 @COL-2072 @wip
  Scenario: Verify the ETD_broker_Cashflow Workflow Status Auto Shifting
    When navigate to FX rate page
    And add FX rate sets fx.rate.usd.eur.0.5
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.partial.physical
    And add ETD agreements add.etd.agreement.allow.adj.and.no.calc
    And view statement
    And approve agreement add.etd.agreement.allow.adj.and.no.calc

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0.1st

    When feed flush etd balances feed.etd.balance.for.model3 by excel
    Then feed log should be feed.successful.1.unsuccessful.0.2nd

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model1.cashflow.value.to.-1m

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.abc.USD
    And select etd model select.model3
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model3.cashflow.value.to.2.2m

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be cashflow.SEC.-1m.abc.4.4m

    When quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.excess
    Then Exposure Management - event should be agreement.level.cashflow.event.call.excess.pending.review.3.4m,category.SEC.cashflow.event.delivery.deficit.pending.review.-1m,category.abc.cashflow.event.call.excess.pending.review.4.4m,model1.cashflow.event.delivery.deficit.pending.review.-1m,model3.cashflow.event.call.excess.pending.review.2.2m

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And edit asset summary info
    And view asset type Cash-USD agreement asset Cash Movements Page
    And add Cash Component booking - statement booking add.linked.event.cashflow.booking.for.model1.wire.out.0.5m

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.abc.USD
    And select etd model select.model3
    And edit asset summary info
    And view asset type Cash-EUR agreement asset Cash Movements Page
    And add Cash Component booking - statement booking add.linked.event.cashflow.booking.for.model3.wire.in.2.2m

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And approve agreement apporve.agreement.to.approved.status
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.excess
    Then Exposure Management - event should be agreement.level.cashflow.event.partially.booked,category.SEC.cashflow.event.partially.booked,category.abc.cashflow.event.completed,model1.cashflow.event.partially.booked,model3.cashflow.event.completed

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And edit asset summary info
    And view asset type Cash-USD agreement asset Cash Movements Page
    And add Cash Component booking - statement booking add.linked.event.cashflow.booking.for.model1.wire.out.0.5m

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.abc.USD
    And select etd model select.model3
    And edit asset summary info
    And view asset type Cash-EUR agreement asset Cash Movements Page
    And add Cash Component booking - statement booking add.linked.event.cashflow.booking.for.model3.wire.in.1k

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And approve agreement apporve.agreement.to.approved.status
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.excess
    Then Exposure Management - event should be agreement.level.cashflow.event.partially.booked,category.SEC.cashflow.event.completed,category.abc.cashflow.event.partially.booked,model1.cashflow.event.completed,model3.cashflow.event.partially.booked

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be cashflow.SEC.-1m.abc.4.4m

    When select etd model select.model1
    And edit asset summary info
    And view asset type Cash-USD agreement asset Cash Movements Page
    And edit cashmovement booking edit.model1.booking1.-0.5m to set.status.to.cancelled

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    When Agreement Statement - etd agreement statement - expand category expand.category.abc.USD
    And select etd model select.model3
    And edit asset summary info
    And view asset type Cash-EUR agreement asset Cash Movements Page
    And edit cashmovement booking edit.model1.booking1.2.2m to set.status.to.cancelled
    And edit cashmovement booking edit.model1.booking2.1k to set.status.to.cancelled

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And approve agreement apporve.agreement.to.approved.status
    And quick link - agreement exposure management
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.excess
    Then Exposure Management - event should be agreement.level.cashflow.event.partially.booked,category.SEC.cashflow.event.partially.booked,category.abc.cashflow.event.pending.review,model1.cashflow.event.partially.booked,model3.cashflow.event.pending.review

    When Exposure Management - tick events tick.model1.cashflow.event,tick.model3.cashflow.event
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bulk.booking.model1.wire.out.-0.6m,bulk.booking.model3.wire.in.2m
    And quick link - save
    And Exposure Management - override warning override.warning.message.1st
    And quick link - save
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.excess
    Then Exposure Management - event should be agreement.level.cashflow.event.partially.booked,category.SEC.cashflow.event.partially.booked,category.abc.cashflow.event.partially.booked,model1.cashflow.event.partially.booked,model3.cashflow.event.partially.booked

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And edit asset summary info
    And view asset type Cash-USD agreement asset Cash Movements Page
    And edit cashmovement booking edit.model1.booking1.-0.6m to set.status.to.cancelled

    When Exposure Management - tick events tick.model1.cashflow.event,tick.model3.cashflow.event
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bulk.booking.model1.wire.in.0.1m,.model3.wire.in.0.2m
    And quick link - save
    And Exposure Management - override warning override.warning.message.2nd
    And quick link - save
    And Exposure Management - expand events expand.cashflow.agreement.level.call.excess
    And Exposure Management - expand events expand.cashflow.category.level.SEC.delivery.deficit
    And Exposure Management - expand events expand.cashflow.category.level.abc.call.excess
    Then Exposure Management - event should be agreement.level.cashflow.event.completed,category.SEC.cashflow.event.completed,category.abc.cashflow.event.completed,model1.cashflow.event.completed,model3.cashflow.event.completed

  @BinHu @F14656 @COL-1630
  Scenario: Verify that Amber and Purple color shown correctly on top statement page_Allow Adj Yes_Statement Calculation No Calc
    When navigate to FX rate page
    And add FX rate sets fx.rate.gbp.0.8.eur.2
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.partial.physical
    And add ETD agreements add.etd.agreement.allow.adj.and.no.calc
    And view statement
    And approve agreement add.etd.agreement.allow.adj.and.no.calc

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0.1st

    And feed flush etd balances feed.etd.balance.for.model2 by xlsx
    Then feed log should be feed.successful.1.unsuccessful.0.2nd

    When feed flush etd balances feed.etd.balance.for.model3 by excel
    Then feed log should be feed.successful.1.unsuccessful.0.3rd

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be amber.color.on.category.level
    And Agreement Statement - etd agreement top level statement summary should be amber.color.on.model.level
    And Agreement Statement - etd agreement top level statement summary should be amber.color.on.TOTALS.level

    When select etd model select.model1
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model1.ote.to-15000.te.to.655

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model2
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model2.imr.to.200.mmr.to.-200

    When Go to agreement add.etd.agreement.allow.adj.and.no.calc statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be amber.purple.color.on.SEC.and.no.change.on.abc
    And Agreement Statement - etd agreement top level statement summary should be amber.purple.color.on.model1.model2.and.no.change.on.model3
    And Agreement Statement - etd agreement top level statement summary should be amber.purple.color.on.TOTALS.level

  @BinHu @F14656 @COL-1611
  Scenario: Verify that Amber and Purple color shown correctly on top statement page_Allow Adj Yes_Statement Calculation Standard
    When navigate to FX rate page
    And add FX rate sets fx.rate.gbp.0.8.eur.2
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.partial.physical
    And add ETD agreements add.etd.agreement.allow.adj.and.standard
    And view statement
    And approve agreement add.etd.agreement.allow.adj.and.standard

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0.1st

    And feed flush etd balances feed.etd.balance.for.model2 by xlsx
    Then feed log should be feed.successful.1.unsuccessful.0.2nd

    When feed flush etd balances feed.etd.balance.for.model3 by excel
    Then feed log should be feed.successful.1.unsuccessful.0.3rd

    When Go to agreement add.etd.agreement.allow.adj.and.standard statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be no.color.on.category.level
    And Agreement Statement - etd agreement top level statement summary should be no.color.on.model.level
    And Agreement Statement - etd agreement top level statement summary should be no.color.on.TOTALS.level

    When select etd model select.model1
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model1.opening.balance.to.-200.123.lov.to.-300.456.im.dcvm.to.-400.789

    When Go to agreement add.etd.agreement.allow.adj.and.standard statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model2
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model2.ote.to.-14000.123.imr.to.200.mmr.to.-200

    When Go to agreement add.etd.agreement.allow.adj.and.standard statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be purple.color.on.SEC.and.no.change.on.abc
    And Agreement Statement - etd agreement top level statement summary should be purple.color.on.model1.model2.and.no.change.on.model3
    And Agreement Statement - etd agreement top level statement summary should be purple.color.on.TOTALS.level

  @BinHu @F14656 @COL-1608
  Scenario: Verify that Amber and Purple color shown correctly on top statement page_Allow Adj No_Statement Calculation No Calc
    When navigate to FX rate page
    And add FX rate sets fx.rate.gbp.0.8.eur.2
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.partial.physical
    And add ETD agreements add.etd.agreement.standalone.df.and.no.calc
    And view statement
    And approve agreement add.etd.agreement.standalone.df.and.no.calc

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0.1st

    And feed flush etd balances feed.etd.balance.for.model2 by xlsx
    Then feed log should be feed.successful.1.unsuccessful.0.2nd

    When feed flush etd balances feed.etd.balance.for.model3 by excel
    Then feed log should be feed.successful.1.unsuccessful.0.3rd

    When Go to agreement add.etd.agreement.standalone.df.and.no.calc statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be amber.color.on.category.level
    And Agreement Statement - etd agreement top level statement summary should be amber.color.on.model.level
    And Agreement Statement - etd agreement top level statement summary should be amber.color.on.TOTALS.level

    When Go to agreement add.etd.agreement.standalone.df.and.no.calc statement page by URL
    And quick link - agreement review
    And edit ETD agreement add.etd.agreement.standalone.df.and.no.calc to change.allow.adj.from.no.to.yes in document tab
    And view statement

    When Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.model1.end.balance.to.555.ote.-15000

    When Go to agreement add.etd.agreement.standalone.df.and.no.calc statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be amber.and.purple.color.on.category.level
    And Agreement Statement - etd agreement top level statement summary should be amber.and.purple.color.on.model.level
    And Agreement Statement - etd agreement top level statement summary should be amber.and.purple.color.on.TOTALS.level

  @BinHu @F14656 @COL-1213
  Scenario: Check statement Adj UI shown capital values correctly
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.all.capitalise
    And add ETD agreements add.etd.agreement.standard.and.allow.adj
    And view statement
    And approve agreement add.etd.agreement.standard.and.allow.adj

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0.1st

    When Go to agreement add.etd.agreement.standard.and.allow.adj statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    Then Agreement Statement - etd agreement model level statement summary should be no.color.for.all.sod.and.adj.fields

    When Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.banked.coupon.to.100.intial.coupon.to.150
    Then Agreement Statement - etd agreement model level statement summary should be purple.color.for.adj.coupon

    When Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.pai.to.150.upfront.fee.to.150.realised.and.unrealised.pl.cleared.to.200.total.fees.cleared.to.200
    Then Agreement Statement - etd agreement model level statement summary should be purple.color.for.adj.net.pl.cleared

    When Go to agreement add.etd.agreement.standard.and.allow.adj statement page by URL
    And quick link - agreement review
    And edit ETD agreement add.etd.agreement.standard.and.allow.adj to change.statement.calc.to.no.calc in document tab
    And view statement
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.net.pl.cleared.to.2000
    Then Agreement Statement - etd agreement model level statement summary should be amber.color.for.adj.net.pl.cleared

  @BinHu @F14656 @COL-1224
  Scenario: Check statement Adj UI shown physical values correctly
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.all.cashflow.physical
    And add ETD agreements add.etd.agreement.standard.and.allow.adj
    And view statement
    And approve agreement add.etd.agreement.standard.and.allow.adj

    And feed flush etd balances feed.etd.balance.for.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0.1st

    When Go to agreement add.etd.agreement.standard.and.allow.adj statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    Then Agreement Statement - etd agreement model level statement summary should be no.color.for.all.sod.and.adj.fields

    When Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.banked.coupon.to.100.intial.coupon.to.200
    Then Agreement Statement - etd agreement model level statement summary should be purple.color.for.adj.coupon.and.cashflow.physical

    When Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.pai.to.200.upfront.fee.to.300
    Then Agreement Statement - etd agreement model level statement summary should be purple.color.for.adj.cashflow.physical

    When Go to agreement add.etd.agreement.standard.and.allow.adj statement page by URL
    And quick link - agreement review
    And edit ETD agreement add.etd.agreement.standard.and.allow.adj to change.statement.calc.to.no.calc in document tab
    And view statement
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    And Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.net.pl.cleared.to.2000.total.net.pl.to.1000
    Then Agreement Statement - etd agreement model level statement summary should be amber.color.for.adj.net.pl.cleared.and.total.net.pl

    When Agreement Statement - etd agreement statement - click adjust adj button
    And Agreement Statement - etd agreement statement - edit model level ADJ column values modify.cashflow.physical.to.500
    Then Agreement Statement - etd agreement model level statement summary should be purple.color.for.adj.cashflow.physical.500

  @BinHu @F14656 @COL-1127
  Scenario: Verify Feed ETD Adjustment
    When navigate to cashflow rules page
    And add cashflow rules cashflow.rule.all.capitalise
    And add cashflow rules cashflow.rule.all.cashflow.physical
    And add ETD agreements etd.broker.agreement.allow.adj.no.calc
    And view statement
    And approve agreement etd.broker.agreement.allow.adj.no.calc
    And add ETD agreements etd.member.agreement.allow.adj.standard
    And view statement
    And approve agreement etd.member.agreement.allow.adj.standard

    When feed flush etd balances feed.etd.balance.for.agr1.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0

    When feed flush etd balances feed.etd.balance.for.agr1.model2 by xlsx
    Then feed log should be feed.successful.1.unsuccessful.0

    When feed flush etd balances feed.etd.balance.for.agr2.model1 by excel
    Then feed log should be feed.successful.1.unsuccessful.0

    When feed flush etd balances feed.etd.balance.for.agr2.model2 by csv
    Then feed log should be feed.successful.1.unsuccessful.0

    When feed etd adjustment feed.agr1.model1.initial.coupon.110 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.banked.coupon.120 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.pai.130 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.upfront.fee.140 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.realised.pl.cleared.150 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.unrealised.pl.cleared.160 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.total.fees.cleared.170 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.opening.balance.180 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.commission.and.fees.190 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.realised.pl.etd.200 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.option.premium.210 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.cash.amounts.220 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.ending.balance.230 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.net.pl.cleared.240 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.total.net.pl.250 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.ending.balance.260 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.te.270 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.nov.280 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.nlv.290 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.ed.300 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model1.dfr.310 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr1.model1.df.securities.balance.320 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr1.model1.df.cash.balance.330 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr1.model1.df.ed.340 by excel
    Then feed log should be feed.successful.0.unsuccessful.1

    When Go to agreement etd.broker.agreement.allow.adj.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    Then Agreement Statement - etd agreement model level statement summary should be all.feed.adj.value.displays.correctly.on.agr1.model1

    When feed etd adjustment feed.agr2.model1.initial.coupon.110 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model1.banked.coupon.120 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model1.pai.130 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model1.upfront.fee.140 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model1.realised.pl.cleared.150 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model1.unrealised.pl.cleared.160 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model1.total.fees.cleared.170 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model1.opening.balance.180 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model1.commission.and.fees.190 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model1.realised.pl.etd.200 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model1.option.premium.210 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model1.cash.amounts.220 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model1.ending.balance.230 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model1.net.pl.cleared.240 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model1.total.net.pl.250 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model1.ending.balance.260 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model1.te.270 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model1.nov.280 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model1.nlv.290 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model1.ed.300 by excel
    Then feed log should be feed.successful.0.unsuccessful.1

    When Go to agreement etd.member.agreement.allow.adj.standard statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.SEC.USD
    And select etd model select.model1
    Then Agreement Statement - etd agreement model level statement summary should be all.calculated.fields.adj.value.not.equal.to.feed.on.agr2.model1

    When Go to agreement etd.broker.agreement.allow.adj.no.calc statement page by URL
    And quick link - agreement review
    And edit ETD agreement etd.broker.agreement.allow.adj.no.calc to change.cashflow.rule1.to.rule2 in document tab

    When feed etd adjustment feed.agr1.model2.initial.coupon.110 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.banked.coupon.120 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.pai.130 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.upfront.fee.140 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.cashflow.388 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.realised.pl.cleared.150 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.unrealised.pl.cleared.160 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.total.fees.cleared.170 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.opening.balance.180 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.commission.and.fees.190 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.realised.pl.etd.200 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.option.premium.210 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.cash.amounts.220 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.ending.balance.230 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.net.pl.cleared.240 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.total.net.pl.250 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.ending.balance.260 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.te.270 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.nov.280 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.nlv.290 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr1.model2.ed.300 by excel
    Then feed log should be feed.successful.1.unsuccessful.0

    When Go to agreement etd.broker.agreement.allow.adj.no.calc statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.abc.USD
    And select etd model select.model2
    Then Agreement Statement - etd agreement model level statement summary should be agr1.model2.cashflow.physical.388

    When feed etd adjustment feed.agr2.model2.initial.coupon.110 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.banked.coupon.120 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.pai.130 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.upfront.fee.140 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.cashflow.388 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model2.realised.pl.cleared.150 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.unrealised.pl.cleared.160 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.total.fees.cleared.170 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.opening.balance.180 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.commission.and.fees.190 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.realised.pl.etd.200 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.option.premium.210 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.cash.amounts.220 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.ending.balance.230 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model2.net.pl.cleared.240 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model2.total.net.pl.250 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model2.ending.balance.260 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model2.te.270 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model2.nov.280 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model2.nlv.290 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model2.ed.300 by excel
    Then feed log should be feed.successful.0.unsuccessful.1
    When feed etd adjustment feed.agr2.model2.dfr.310 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.df.securities.balance.320 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.df.cash.balance.330 by excel
    Then feed log should be feed.successful.1.unsuccessful.0
    When feed etd adjustment feed.agr2.model2.df.ed.440 by excel
    Then feed log should be feed.successful.0.unsuccessful.1

    When Go to agreement etd.member.agreement.allow.adj.standard statement page by URL
    And Agreement Statement - etd agreement statement - expand category expand.category.abc.USD
    And select etd model select.model2
    Then Agreement Statement - etd agreement model level statement summary should be all.feed.adj.value.displays.correctly.on.agr2.model2

  @BinHu @F14659 @FamilyAgreement @COL-2687 @wip
  Scenario: EM event - Check Cashflow events generated in EM page when Root tier tick on 'Include Event' for Family Agreement - Member- Cashflow Delivery(Deficit)
    When navigate to collateral preferences page
    And set collateral preferences tick.mandatory.comment.when.dispute.condition
    And navigate to FX rate page
    And add FX rate sets fx.rate.usd.gbp.0.5
    And navigate to cashflow rules page
    And add cashflow rules cashflow.rule.all.physical.payment

    When add ETD agreements standard.member.etd.agreement
    And view statement
    And approve agreement standard.member.etd.agreement

    And feed flush etd balances feed.etd.balance.for.agr1.model1 by csv
    Then feed log should be feed.successful.1.unsuccessful.0

    And feed flush etd balances feed.etd.balance.for.agr1.model2 by excel
    Then feed log should be feed.successful.1.unsuccessful.0

    When Go to agreement standard.member.etd.agreement statement page by URL
    Then Agreement Statement - etd agreement top level statement summary should be cashflow.on.model1.-2800.model2.-1400

    When add ETD agreements family.member.etd.agreement

