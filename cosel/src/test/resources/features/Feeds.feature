@Feeds @regression
Feature: Feeds

  Background:
    Given have login with credential login.credential.test1
    And have collateral preferences collateral.preference1
    And have default stp configuration
    And have default organisation global preferences

  @KentGu @FeedAgreement @T28216 @F7895 @13.0.0
  Scenario Outline: Feeds FA-CS-01-13 Verify only check VM call frequency field when update NotNet to Net
#    Given have OTC agreement <otc.notnet.agr1>,<otc.notnet.agr2>,<otc.notnet.agr3>,<otc.notnet.agr4>
    When feed agreements <feed.agreement1>,<feed.agreement2>,<feed.agreement3>,<feed.agreement4> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    When search agreement <otc.notnet.agr1.search>
    And edit agreement <otc.notnet.agr1.search.result> in agreement search page
    Then agreement summary – agreement summary should be <otc.notnet.agr1.summary>
    And search agreement <otc.notnet.agr2.search>
    And edit agreement <otc.notnet.agr2.search.result> in agreement search page
    Then agreement summary – agreement summary should be <otc.notnet.agr2.summary>
    And search agreement <otc.notnet.agr3.search>
    And edit agreement <otc.notnet.agr3.search.result> in agreement search page
    Then agreement summary – agreement summary should be <otc.agr3.summary>
    And search agreement <otc.notnet.agr4.search>
    And edit agreement <otc.notnet.agr4.search.result> in agreement search page
    Then agreement summary – agreement summary should be <otc.notnet.agr4.summary>
    Examples:
      | feed.agreement1 | feed.agreement2 | feed.agreement3 | feed.agreement4 | feed.result | feed.status        | file.format | otc.notnet.agr1.search | otc.notnet.agr1.search.result | otc.notnet.agr1.summary | otc.notnet.agr2.search | otc.notnet.agr2.search.result | otc.notnet.agr2.summary | otc.notnet.agr3.search | otc.notnet.agr3.search.result | otc.agr3.summary        | otc.notnet.agr4.search | otc.notnet.agr4.search.result | otc.notnet.agr4.summary |
      | feed.agr1       | feed.agr2       | feed.agr3       | feed.agr4       | feed.result | feed.status.failed | xml         | otc.notnet.agr1.search | otc.notnet.agr1.search.result | otc.notnet.agr1.summary | otc.notnet.agr2.search | otc.notnet.agr2.search.result | otc.notnet.agr2.summary | otc.notnet.agr3.search | otc.notnet.agr3.search.result | otc.notnet.agr3.summary | otc.notnet.agr4.search | otc.notnet.agr4.search.result | otc.notnet.agr4.summary |
#      | feed.agr1       | feed.agr2       | feed.agr3       | feed.agr4       | feed.result | feed.status.failed | xlsx        | otc.notnet.agr1.search | otc.notnet.agr1.search.result | otc.notnet.agr1.summary | otc.notnet.agr2.search | otc.notnet.agr2.search.result | otc.notnet.agr2.summary | otc.notnet.agr3.search | otc.notnet.agr3.search.result | otc.notnet.agr3.summary | otc.notnet.agr4.search | otc.notnet.agr4.search.result | otc.notnet.agr4.summary |

  @KentGu @FeedFxRate @T4800 @F9068 @14.1.0
  Scenario Outline: Feeds FFXR-FXRTC-01-01 Verify that successfully to import FX Rates file when [fxRate] is correct
    Given have FX rate sets <base.fx.rate.set>
    When feed FX rates <feed.fx.rate1> by <file.format>
    Then feed log should be <feed.success1>
    When navigate to FX rate page
    Then fx rate set should be <check.fx.rate1>
    When feed FX rates <feed.fx.rate2> by <file.format>
    Then feed log should be <feed.success2>
    When navigate to FX rate page
    Then fx rate set should be <check.fx.rate2>
    Examples:
      | base.fx.rate.set | feed.fx.rate1        | feed.success1 | file.format | check.fx.rate1        | feed.fx.rate2          | check.fx.rate2          | feed.success2 |
      | base.fx.rate.set | feed.fx.rate.bid.0.5 | feed.success1 | xml         | check.fx.rate.bid.0.5 | feed.fx.rate.bid.12345 | check.fx.rate.bid.12345 | feed.success2 |
      | base.fx.rate.set | feed.fx.rate.bid.0.5 | feed.success1 | xlsx        | check.fx.rate.bid.0.5 | feed.fx.rate.bid.12345 | check.fx.rate.bid.12345 | feed.success2 |

  @KentGu @FeedInterestRates @T4592 @F5020 @F9068 @14.1.0
  Scenario Outline: Feeds FIR-01-01 Feeds interest rates with correct fields
    Given have interest rates <base.daily.interest.rate>
    When add <agreement.type> agreements <otc.agreement>
    And view statement
    And edit asset summary info
    And view asset type <qtp.cash.usd> agreement asset <cash> Page
    And add call bookings - statement booking <booking.call>
    And edit call booking <qtp.cash.usd> to <booking.call.approve>
    And quick link - view statement
    And approve agreement approve.agreement
    And feed interest rates <feed.interest.rate.1> by <file.format>
    #newly added
    Then feed log should be <feed.result.1>
    When navigate to interest manager search page
    And Interest Manager - search interest event by <interet.event.search>
    And Interest Manager - view details for event <interest.event>
    Then interest rates should be <feed.interest.rate.check1>
    And feed interest rates <feed.interest.rate.2> by <file.format>
    #newly added
    Then feed log should be <feed.result.2>
    When navigate to interest manager search page
    And Interest Manager - search interest event by <interet.event.search>
    And Interest Manager - view details for event <interest.event>
    Then interest rates should be <feed.interest.rate.check2>
    Examples:
      | base.daily.interest.rate | agreement.type | otc.agreement | qtp.cash.usd       | cash | booking.call    | booking.call.approve     | feed.interest.rate.1           | file.format | feed.result.1               | interet.event.search                | interest.event              | feed.interest.rate.check1               | feed.interest.rate.2           | feed.result.2               | feed.interest.rate.check2               |
      | base.daily.interest.rate | OTC            | otc.agr       | asset.qtp.cash.usd | CASH | booking.2m.call | booking.2m.call.approved | feed.interest.rate.amount.3.77 | xml         | feed.interest.rate.result.1 | interest.event.search.otc.agreement | interest.event.qtp.cash.usd | interest.report.detail.rate.amount.3.77 | feed.interest.rate.amount.5.77 | feed.interest.rate.result.2 | interest.report.detail.rate.amount.5.77 |
#      | base.daily.interest.rate | OTC            | otc.agr       | asset.qtp.cash.usd | CASH | booking.2m.call | booking.2m.call.approved | feed.interest.rate.amount.3.77 | xlsx        | feed.interest.rate.result.1 | interest.event.search.otc.agreement | interest.event.qtp.cash.usd | interest.report.detail.rate.amount.3.77 | feed.interest.rate.amount.5.77 | feed.interest.rate.result.2 | interest.report.detail.rate.amount.5.77 |


  @KentGu @FeedAgreementUDF @T3696 @F5020 @F9068 @14.1.0
  Scenario Outline:  Feeds FAUDF-01-01 Verify that successful to feed agreement UDF with all correct fields in template
    Given have OTC agreements <otc.agreement>
    And have collateral static data <static.data1>,<static.data2>,<static.data3>,<static.data4>
    When feed agreement UDF <feed.agreement.udf.1> by <file.format>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    Then agreement summary – additional fields should be <agreement.summary1>
    When navigate to collateral static data page
    And edit collateral static data <static.data3> to <static.data.disable>
    And feed agreement UDF <feed.agreement.udf.2> by <file.format>
    Then feed log should be <feed.result1>
    When feed agreement UDF <feed.agreement.udf.3> by <file.format>
    Then feed log should be <feed.result2>
    When feed agreement UDF <feed.agreement.udf.4> by <file.format>
    Then feed log should be <feed.result3>
    When navigate to collateral static data page
    And edit collateral static data <static.data1> to <static.data.disable>
    And edit collateral static data <static.data2> to <static.data.disable>
    And edit collateral static data <static.data3> to <static.data.disable>
    And edit collateral static data <static.data4> to <static.data.disable>
    Examples:
      | otc.agreement | static.data1       | static.data2       | static.data3       | static.data4       | feed.agreement.udf.1 | file.format | agreement.search | agreement.search.result | agreement.summary1              | static.data.disable     | feed.agreement.udf.2 | feed.result1                                  | feed.agreement.udf.3 | feed.result2                                  | feed.agreement.udf.4 | feed.result3                                  |
      | otc.agr       | col.static.data.d1 | col.static.data.d2 | col.static.data.d3 | col.static.data.d4 | feed.agrudf.1        | xml         | search.otc.agr   | search.result.otc.agr   | otc.agr.after.first.feed.agrudf | col.static.data.disable | feed.agrudf.2        | feed.result.for.feed.agr.udf.2.unsuccessful.1 | feed.agrudf.3        | feed.result.for.feed.agr.udf.3.unsuccessful.1 | feed.agrudf.4        | feed.result.for.feed.agr.udf.4.unsuccessful.1 |
#      | otc.agr       | col.static.data.d1 | col.static.data.d2 | col.static.data.d3 | col.static.data.d4 | feed.agrudf.1        | xlsx        | search.otc.agr   | search.result.otc.agr   | otc.agr.after.first.feed.agrudf | col.static.data.disable | feed.agrudf.2        | feed.result.for.feed.agr.udf.2.unsuccessful.1 | feed.agrudf.3        | feed.result.for.feed.agr.udf.3.unsuccessful.1 | feed.agrudf.4        | feed.result.for.feed.agr.udf.4.unsuccessful.1 |

  @KentGu @FeedAgreementRatings @T3688 @F5020 @F9068 @14.1.0
  Scenario Outline: Feeds FAR-01-01 Verify that successful to feed agreement ratings with all correct fields in template
    Given have OTC agreements <otc.agr.rating.trigger>,<otc.agr.non.rating.trigger>
    When feed agreement ratings <feed.otc.agr.rating.trigger.p.fitch> by <file.format>
    #newly added
    Then feed log should be <feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty.result.successfully.1>
    When search agreement <otc.agr.rating.trigger.search>
    And edit agreement <otc.agr.rating.trigger.search.result> in agreement search page
    Then agreement summary – agreement summary should be <otc.agr.rating.trigger.summary.p.fitch>
    When feed agreement ratings <feed.otc.agr.non.rating.trigger.p.fitch> by <file.format>
    #newly added
    Then feed log should be <feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty.result.successfully.1>
    When search agreement <otc.agr.non.rating.trigger.search>
    And edit agreement <otc.agr.non.rating.trigger.search.result> in agreement search page
    And edit fix trigger tab in agreement to <edit.to.principal.rating.trigger>
    Then agreement summary – agreement summary should be <otc.agr.rating.trigger.summary.p.fitch>
    When feed agreement ratings <feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty> by <file.format>
    Then feed log should be <feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty.result.successfully.1>
    When feed agreement ratings <feed.otc.agr.non.rating.trigger.fitch.invalid.cpty.principal.ind> by <file.format>
    #newly added
    Then feed log should be <feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty.result.successfully.1>
    And search agreement <otc.agr.non.rating.trigger.search>
    And edit agreement <otc.agr.non.rating.trigger.search.result> in agreement search page
    And edit fix trigger tab in agreement to <edit.to.counterparty.rating.trigger>
    Then agreement summary – agreement summary should be <otc.agr.non.rating.trigger.summary.c.fitch>
    When feed agreement ratings <feed.otc.agr.non.rating.trigger.p.sandp> by <file.format>
    #newly added
    Then feed log should be <feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty.result.successfully.1>
    And search agreement <otc.agr.non.rating.trigger.search>
    And edit agreement <otc.agr.non.rating.trigger.search.result> in agreement search page
    Then agreement summary – agreement summary should be <otc.agr.non.rating.trigger.summary.p.ratinglvel>
    Examples:
      | otc.agr.rating.trigger | otc.agr.non.rating.trigger | feed.otc.agr.rating.trigger.p.fitch | file.format | feed.result | otc.agr.rating.trigger.search | otc.agr.rating.trigger.search.result | otc.agr.rating.trigger.summary.p.fitch | feed.otc.agr.non.rating.trigger.p.fitch | otc.agr.non.rating.trigger.search | otc.agr.non.rating.trigger.search.result | edit.to.principal.rating.trigger | feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty | feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty.result.successfully.1 | feed.otc.agr.non.rating.trigger.fitch.invalid.cpty.principal.ind | otc.agr.non.rating.trigger.search | otc.agr.non.rating.trigger.search.result | edit.to.counterparty.rating.trigger | otc.agr.non.rating.trigger.summary.c.fitch | feed.otc.agr.non.rating.trigger.p.sandp | otc.agr.non.rating.trigger.summary.p.ratinglvel |
      | otc.agr.rating.trigger | otc.agr.non.rating.trigger | feed.otc.agr.rating.trigger.p.fitch | xml         |             | otc.agr.rating.trigger.search | otc.agr.rating.trigger.search.result | otc.agr.rating.trigger.summary.p.fitch | feed.otc.agr.non.rating.trigger.p.fitch | otc.agr.non.rating.trigger.search | otc.agr.non.rating.trigger.search.result | edit.to.principal.rating.trigger | feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty | feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty.result.successfully.1 | feed.otc.agr.non.rating.trigger.fitch.invalid.cpty.principal.ind | otc.agr.non.rating.trigger.search | otc.agr.non.rating.trigger.search.result | edit.to.counterparty.rating.trigger | otc.agr.non.rating.trigger.summary.c.fitch | feed.otc.agr.non.rating.trigger.p.sandp | otc.agr.non.rating.trigger.summary.p.ratinglvel |
      | otc.agr.rating.trigger | otc.agr.non.rating.trigger | feed.otc.agr.rating.trigger.p.fitch | xlsx        |             | otc.agr.rating.trigger.search | otc.agr.rating.trigger.search.result | otc.agr.rating.trigger.summary.p.fitch | feed.otc.agr.non.rating.trigger.p.fitch | otc.agr.non.rating.trigger.search | otc.agr.non.rating.trigger.search.result | edit.to.principal.rating.trigger | feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty | feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty.result.successfully.1 | feed.otc.agr.non.rating.trigger.fitch.invalid.cpty.principal.ind | otc.agr.non.rating.trigger.search | otc.agr.non.rating.trigger.search.result | edit.to.counterparty.rating.trigger | otc.agr.non.rating.trigger.summary.c.fitch | feed.otc.agr.non.rating.trigger.p.sandp | otc.agr.non.rating.trigger.summary.p.ratinglvel |

  @KentGu @FeedAgreement @T32496 @F11281 @14.1.0
  Scenario Outline: Feeds FA-C-01-72 Verify that successful to feed agreement with correct [Ultimate Parent Correlation] and [Close Link Correlation]
    When feed agreements <feed.otc.agreement.ultimate.parent.true.close.link.true> by <file.format>
    Then feed log should be <feed.otc.agreement.ultimate.parent.true.close.link.true.result.successful.1>
    And feed status should be <feed.otc.agreement.ultimate.parent.true.close.link.true.status.successful.1>
    And search agreement <otc.agreement.ultimate.parent.true.close.link.true.search>
    And edit agreement <otc.agreement.ultimate.parent.true.close.link.true.search.result> in agreement search page
    And edit <agreement.type.otc> agreement to <otc.agreement.settlement.tab>
    Then agreement summary – agreement summary should be <otc.agreement.ultimate.parent.true.close.link.true.summary>
    When feed agreement <feed.otc.agreement.ultimate.parent.blank.close.link.blank> by <file.format>
    Then feed log should be <feed.otc.agreement.ultimate.parent.blank.close.link.blank.result.successful.1>
    And feed status should be <feed.otc.agreement.ultimate.parent.blank.close.link.blank.status.successful.1>
    And search agreement <otc.agreement.ultimate.parent.blank.close.link.blank.search>
    And edit agreement <otc.agreement.ultimate.parent.blank.close.link.blank.search.result> in agreement search page
    And edit <agreement.type.otc> agreement to <otc.agreement.settlement.tab>
    Then agreement summary – agreement summary should be <otc.agreement.ultimate.parent.blank.close.link.blank.summary>
    When feed agreement <feed.otc.agreement.ultimate.parent.false.close.link.false> by <file.format>
    Then feed log should be <feed.otc.agreement.ultimate.parent.false.close.link.false.result.successful.1>
    And feed status should be <feed.otc.agreement.ultimate.parent.false.close.link.false.status.successful.1>
    And search agreement <otc.agreement.ultimate.parent.false.close.link.false.search>
    And edit agreement <otc.agreement.ultimate.parent.false.close.link.false.search.result> in agreement search page
    And edit <agreement.type.otc> agreement to <otc.agreement.settlement.tab>
    Then agreement summary – agreement summary should be <otc.agreement.ultimate.parent.false.close.link.false.summary>
    When feed agreement <feed.etf.agreement.ultimate.parent.true.close.link.true> by <file.format>
    Then feed log should be <feed.etf.agreement.ultimate.parent.true.close.link.true.result.successful.1>
    And feed status should be <feed.etf.agreement.ultimate.parent.true.close.link.true.status.successful.1>
    And search agreement <etf.agreement.ultimate.parent.true.close.link.true.search>
    And edit agreement <etf.agreement.ultimate.parent.true.close.link.true.search.result> in agreement search page
    And edit <agreement.type.etf> agreement to <etf.agreement.settlement.tab>
    Then agreement summary – agreement summary should be <etf.agreement.ultimate.parent.true.close.link.true.summary>
    When feed agreement <feed.etf.agreement.ultimate.parent.blank.close.link.blank> by <file.format>
    Then feed log should be <feed.etf.agreement.ultimate.parent.blank.close.link.blank.result.successful.1>
    And feed status should be <feed.etf.agreement.ultimate.parent.blank.close.link.blank.status.successful.1>
    And search agreement <etf.agreement.ultimate.parent.blank.close.link.blank.search>
    And edit agreement <etf.agreement.ultimate.parent.blank.close.link.blank.search.result> in agreement search page
    And edit <agreement.type.etf> agreement to <etf.agreement.settlement.tab>
    Then agreement summary – agreement summary should be <etf.agreement.ultimate.parent.blank.close.link.blank.summary>
    Examples:
      | feed.otc.agreement.ultimate.parent.true.close.link.true | file.format | feed.otc.agreement.ultimate.parent.true.close.link.true.result.successful.1 | feed.otc.agreement.ultimate.parent.true.close.link.true.status.successful.1 | otc.agreement.ultimate.parent.true.close.link.true.search | otc.agreement.ultimate.parent.true.close.link.true.search.result | otc.agreement.settlement.tab | otc.agreement.ultimate.parent.true.close.link.true.summary | feed.otc.agreement.ultimate.parent.blank.close.link.blank | feed.otc.agreement.ultimate.parent.blank.close.link.blank.result.successful.1 | feed.otc.agreement.ultimate.parent.blank.close.link.blank.status.successful.1 | otc.agreement.ultimate.parent.blank.close.link.blank.search | otc.agreement.ultimate.parent.blank.close.link.blank.search.result | otc.agreement.ultimate.parent.blank.close.link.blank.summary | feed.otc.agreement.ultimate.parent.false.close.link.false | feed.otc.agreement.ultimate.parent.false.close.link.false.result.successful.1 | feed.otc.agreement.ultimate.parent.false.close.link.false.status.successful.1 | otc.agreement.ultimate.parent.false.close.link.false.search | otc.agreement.ultimate.parent.false.close.link.false.search.result | otc.agreement.ultimate.parent.false.close.link.false.summary | feed.etf.agreement.ultimate.parent.true.close.link.true | feed.etf.agreement.ultimate.parent.true.close.link.true.result.successful.1 | feed.etf.agreement.ultimate.parent.true.close.link.true.status.successful.1 | etf.agreement.ultimate.parent.true.close.link.true.search | etf.agreement.ultimate.parent.true.close.link.true.search.result | etf.agreement.settlement.tab | etf.agreement.ultimate.parent.true.close.link.true.summary | feed.etf.agreement.ultimate.parent.blank.close.link.blank | feed.etf.agreement.ultimate.parent.blank.close.link.blank.result.successful.1 | feed.etf.agreement.ultimate.parent.blank.close.link.blank.status.successful.1 | etf.agreement.ultimate.parent.blank.close.link.blank.search | etf.agreement.ultimate.parent.blank.close.link.blank.search.result | etf.agreement.ultimate.parent.blank.close.link.blank.summary | agreement.type.otc | agreement.type.etf |
      | feed.otc.agreement.ultimate.parent.true.close.link.true | xml         | feed.otc.agreement.ultimate.parent.true.close.link.true.result.successful.1 | feed.otc.agreement.ultimate.parent.true.close.link.true.status.successful.1 | otc.agreement.ultimate.parent.true.close.link.true.search | otc.agreement.ultimate.parent.true.close.link.true.search.result | otc.agreement.settlement.tab | otc.agreement.ultimate.parent.true.close.link.true.summary | feed.otc.agreement.ultimate.parent.blank.close.link.blank | feed.otc.agreement.ultimate.parent.blank.close.link.blank.result.successful.1 | feed.otc.agreement.ultimate.parent.blank.close.link.blank.status.successful.1 | otc.agreement.ultimate.parent.blank.close.link.blank.search | otc.agreement.ultimate.parent.blank.close.link.blank.search.result | otc.agreement.ultimate.parent.blank.close.link.blank.summary | feed.otc.agreement.ultimate.parent.false.close.link.false | feed.otc.agreement.ultimate.parent.false.close.link.false.result.successful.1 | feed.otc.agreement.ultimate.parent.false.close.link.false.status.successful.1 | otc.agreement.ultimate.parent.false.close.link.false.search | otc.agreement.ultimate.parent.false.close.link.false.search.result | otc.agreement.ultimate.parent.false.close.link.false.summary | feed.etf.agreement.ultimate.parent.true.close.link.true | feed.etf.agreement.ultimate.parent.true.close.link.true.result.successful.1 | feed.etf.agreement.ultimate.parent.true.close.link.true.status.successful.1 | etf.agreement.ultimate.parent.true.close.link.true.search | etf.agreement.ultimate.parent.true.close.link.true.search.result | etf.agreement.settlement.tab | etf.agreement.ultimate.parent.true.close.link.true.summary | feed.etf.agreement.ultimate.parent.blank.close.link.blank | feed.etf.agreement.ultimate.parent.blank.close.link.blank.result.successful.1 | feed.etf.agreement.ultimate.parent.blank.close.link.blank.status.successful.1 | etf.agreement.ultimate.parent.blank.close.link.blank.search | etf.agreement.ultimate.parent.blank.close.link.blank.search.result | etf.agreement.ultimate.parent.blank.close.link.blank.summary | OTC                | ETF                |
#      | feed.otc.agreement.ultimate.parent.true.close.link.true | xlsx        | feed.otc.agreement.ultimate.parent.true.close.link.true.result.successful.1 | feed.otc.agreement.ultimate.parent.true.close.link.true.status.successful.1 | otc.agreement.ultimate.parent.true.close.link.true.search | otc.agreement.ultimate.parent.true.close.link.true.search.result | otc.agreement.settlement.tab | otc.agreement.ultimate.parent.true.close.link.true.summary | feed.otc.agreement.ultimate.parent.blank.close.link.blank | feed.otc.agreement.ultimate.parent.blank.close.link.blank.result.successful.1 | feed.otc.agreement.ultimate.parent.blank.close.link.blank.status.successful.1 | otc.agreement.ultimate.parent.blank.close.link.blank.search | otc.agreement.ultimate.parent.blank.close.link.blank.search.result | otc.agreement.ultimate.parent.blank.close.link.blank.summary | feed.otc.agreement.ultimate.parent.false.close.link.false | feed.otc.agreement.ultimate.parent.false.close.link.false.result.successful.1 | feed.otc.agreement.ultimate.parent.false.close.link.false.status.successful.1 | otc.agreement.ultimate.parent.false.close.link.false.search | otc.agreement.ultimate.parent.false.close.link.false.search.result | otc.agreement.ultimate.parent.false.close.link.false.summary | feed.etf.agreement.ultimate.parent.true.close.link.true | feed.etf.agreement.ultimate.parent.true.close.link.true.result.successful.1 | feed.etf.agreement.ultimate.parent.true.close.link.true.status.successful.1 | etf.agreement.ultimate.parent.true.close.link.true.search | etf.agreement.ultimate.parent.true.close.link.true.search.result | etf.agreement.settlement.tab | etf.agreement.ultimate.parent.true.close.link.true.summary | feed.etf.agreement.ultimate.parent.blank.close.link.blank | feed.etf.agreement.ultimate.parent.blank.close.link.blank.result.successful.1 | feed.etf.agreement.ultimate.parent.blank.close.link.blank.status.successful.1 | etf.agreement.ultimate.parent.blank.close.link.blank.search | etf.agreement.ultimate.parent.blank.close.link.blank.search.result | etf.agreement.ultimate.parent.blank.close.link.blank.summary | OTC                | ETF                |

  @KentGu @FeedAgreement @T32603 @F11270 @14.1.0
  Scenario Outline: Feeds FA-C-01-80 Verify feed agreement haircut adjustment rules succeed with valid values_Bond
    Given have securities <bond.usd.price.100.1>,<bond.usd.price.100.2>,<bond.gbp.price.100>,<equity.usd.price.1.1>,<equity.usd.price.1.2>,<equity.gbp.price.1>
    When feed agreements <feed.otc.net.agreement.haircut.rule.on.bond.usd> by <file.format>
    Then feed log should be <feed.otc.net.agreement.haircut.rule.on.bond.usd.log>
    And feed status should be <feed.otc.net.agreement.haircut.rule.on.bond.usd.status>
    And search agreement <otc.net.agreement.haircut.rule.on.bond.usd.search>
    And edit agreement <otc.net.agreement.haircut.rule.on.bond.usd.search.result> in agreement search page
    And edit <agreement.type.otc> agreement to <otc.net.agreement.complete>
    Then agreement summary – agreement summary should be <otc.net.agreement.haircut.rule.on.bond.usd.summary>
    When feed agreements <feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule> by <file.format>
    Then feed log should be <feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.log>
    And feed status should be <feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.status>
    And search agreement <otc.net.agreement.haircut.rule.on.bond.usd.search>
    And edit agreement <otc.net.agreement.haircut.rule.on.bond.usd.search.result> in agreement search page
    And edit <agreement.type.otc> agreement to otc.net.agreement.complete
    Then agreement summary – agreement summary should be <otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.summary>
    Examples:
      | bond.usd.price.100.1 | bond.usd.price.100.2 | bond.gbp.price.100 | equity.usd.price.1.1 | equity.usd.price.1.2 | equity.gbp.price.1 | file.format | feed.otc.net.agreement.haircut.rule.on.bond.usd | feed.otc.net.agreement.haircut.rule.on.bond.usd.log | feed.otc.net.agreement.haircut.rule.on.bond.usd.status | otc.net.agreement.haircut.rule.on.bond.usd.search | otc.net.agreement.haircut.rule.on.bond.usd.search.result | otc.net.agreement.complete | agreement.type.otc | otc.net.agreement.haircut.rule.on.bond.usd.summary | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.log | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.status | otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.summary |
      | bond.usd.price.100.1 | bond.usd.price.100.2 | bond.gbp.price.100 | equity.usd.price.1.1 | equity.usd.price.1.2 | equity.gbp.price.1 | xml         | feed.otc.net.agreement.haircut.rule.on.bond.usd | feed.otc.net.agreement.haircut.rule.on.bond.usd.log | feed.otc.net.agreement.haircut.rule.on.bond.usd.status | otc.net.agreement.haircut.rule.on.bond.usd.search | otc.net.agreement.haircut.rule.on.bond.usd.search.result | otc.net.agreement.complete | OTC                | otc.net.agreement.haircut.rule.on.bond.usd.summary | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.log | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.status | otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.summary |
#      | bond.usd.price.100.1 | bond.usd.price.100.2 | bond.gbp.price.100 | equity.usd.price.1.1 | equity.usd.price.1.2 | equity.gbp.price.1 | xlsx        | feed.otc.net.agreement.haircut.rule.on.bond.usd | feed.otc.net.agreement.haircut.rule.on.bond.usd.log | feed.otc.net.agreement.haircut.rule.on.bond.usd.status | otc.net.agreement.haircut.rule.on.bond.usd.search | otc.net.agreement.haircut.rule.on.bond.usd.search.result | otc.net.agreement.complete | OTC                | otc.net.agreement.haircut.rule.on.bond.usd.summary | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.log | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.status | otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.summary |

  @KentGu @FeedAgreement @T32961 @F11280 @F12712 @14.1.0
  Scenario Outline: Feeds FA-C-01-88 To feed agreement with Grace period setting in excel format
    When feed agreements <feed.agreement> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    And edit <agreement.type> agreement to <agreement.edit>
    Then agreement summary – agreement summary should be <agreement.summary>
    Examples:
      | feed.agreement                                 | file.format | feed.result                                           | feed.status                                           | agreement.search                                      | agreement.search.result                                      | agreement.type | agreement.edit     | agreement.summary                                      |
      | feed.clearing.agreement.grace.period.0         | xml         | feed.clearing.agreement.grace.period.0.result         | feed.clearing.agreement.grace.period.0.status         | feed.clearing.agreement.grace.period.0.search         | feed.clearing.agreement.grace.period.0.search.result         | FCM            | agreement.complete | feed.clearing.agreement.grace.period.0.summary         |
      | feed.otc.agreement.grace.period.infinity       | xml         | feed.otc.agreement.grace.period.infinity.result       | feed.otc.agreement.grace.period.infinity.status       | feed.otc.agreement.grace.period.infinity.search       | feed.otc.agreement.grace.period.infinity.search.result       | OTC            | agreement.complete | feed.otc.agreement.grace.period.infinity.summary       |
      | feed.repo.agreement.grace.period.blank         | xml         | feed.repo.agreement.grace.period.blank.result         | feed.repo.agreement.grace.period.blank.status         | feed.repo.agreement.grace.period.blank.search         | feed.repo.agreement.grace.period.blank.search.result         | Repo           | agreement.complete | feed.repo.agreement.grace.period.blank.summary         |
      | feed.otc.agreement.grace.period.1              | xml         | feed.otc.agreement.grace.period.1.result              | feed.otc.agreement.grace.period.1.status              | feed.otc.agreement.grace.period.1.search              | feed.otc.agreement.grace.period.1.search.result              | OTC            | agreement.complete | feed.otc.agreement.grace.period.1.summary              |
      | feed.clearing.agreement.grace.period.blank     | xml         | feed.clearing.agreement.grace.period.blank.result     | feed.clearing.agreement.grace.period.blank.status     | feed.clearing.agreement.grace.period.blank.search     | feed.clearing.agreement.grace.period.blank.search.result     | FCM            | agreement.complete | feed.clearing.agreement.grace.period.blank.summary     |
      | feed.otc.agreement.grace.period.0              | xml         | feed.otc.agreement.grace.period.0.result              | feed.otc.agreement.grace.period.0.status              | feed.otc.agreement.grace.period.0.search              | feed.otc.agreement.grace.period.0.search.result              | OTC            | agreement.complete | feed.otc.agreement.grace.period.0.summary              |
      | feed.repo.agreement.grace.period.infinity      | xml         | feed.repo.agreement.grace.period.infinity.result      | feed.repo.agreement.grace.period.infinity.status      | feed.repo.agreement.grace.period.infinity.search      | feed.repo.agreement.grace.period.infinity.search.result      | Repo           | agreement.complete | feed.repo.agreement.grace.period.infinity.summary      |
      | feed.otc.agreement.grace.period.10             | xml         | feed.otc.agreement.grace.period.10.result             | feed.otc.agreement.grace.period.10.status             | feed.otc.agreement.grace.period.10.search             | feed.otc.agreement.grace.period.10.search.result             | OTC            | agreement.complete | feed.otc.agreement.grace.period.10.summary             |
      | feed.clearing.agreement.grace.period.infini    | xml         | feed.clearing.agreement.grace.period.infini.result    | feed.clearing.agreement.grace.period.infini.status    | feed.clearing.agreement.grace.period.infini.search    | feed.clearing.agreement.grace.period.infini.search.result    | FCM            | agreement.complete | feed.clearing.agreement.grace.period.infini.summary    |
      | feed.repo.agreement.grace.period.11            | xml         | feed.repo.agreement.grace.period.11.result            | feed.repo.agreement.grace.period.11.status            | feed.repo.agreement.grace.period.11.search            | feed.repo.agreement.grace.period.11.search.result            | Repo           | agreement.complete | feed.repo.agreement.grace.period.11.summary            |
      | feed.otc.agreement.grace.period.dollar.percent | xml         | feed.otc.agreement.grace.period.dollar.percent.result | feed.otc.agreement.grace.period.dollar.percent.status | feed.otc.agreement.grace.period.dollar.percent.search | feed.otc.agreement.grace.period.dollar.percent.search.result | OTC            | agreement.complete | feed.otc.agreement.grace.period.dollar.percent.summary |
      | feed.clearing.agreement.grace.period.infinity  | xml         | feed.clearing.agreement.grace.period.infinity.result  | feed.clearing.agreement.grace.period.infinity.status  | feed.clearing.agreement.grace.period.infinity.search  | feed.clearing.agreement.grace.period.infinity.search.result  | FCM            | agreement.complete | feed.clearing.agreement.grace.period.infinity.summary  |
      | feed.repo.agreement.grace.period.0             | xml         | feed.repo.agreement.grace.period.0.result             | feed.repo.agreement.grace.period.0.status             | feed.repo.agreement.grace.period.0.search             | feed.repo.agreement.grace.period.0.search.result             | Repo           | agreement.complete | feed.repo.agreement.grace.period.0.summary             |
      | feed.otc.agreement.grace.period.blank          | xml         | feed.otc.agreement.grace.period.blank.result          | feed.otc.agreement.grace.period.blank.status          | feed.otc.agreement.grace.period.blank.search          | feed.otc.agreement.grace.period.blank.search.result          | OTC            | agreement.complete | feed.otc.agreement.grace.period.blank.summary          |

  @KentGu @FeedAgreement @T32997 @F11529 @14.1.0
  Scenario Outline: Feeds FA-C-01-92 Verify feed agreement  Template vs Agreement Eligibility Rules succeed
    When feed agreements <feed.agreement> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    And edit <agreement.type> agreement to <agreement.edit>
    Then agreement summary – agreement summary should be <agreement.summary>
    Examples:
      | feed.agreement                                   | file.format | feed.result                                             | feed.status                                             | agreement.search                                        | agreement.search.result                                        | agreement.type | agreement.edit     | agreement.summary                                        |
      | feed.net.otc.agreement.eligibility.rule          | xml         | feed.net.otc.agreement.eligibility.rule.result          | feed.net.otc.agreement.eligibility.rule.status          | feed.net.otc.agreement.eligibility.rule.search          | feed.net.otc.agreement.eligibility.rule.search.result          | OTC            | agreement.complete | feed.net.otc.agreement.eligibility.rule.summary          |
      | feed.net.otc.agreement.eligibility.rule.pc       | xml         | feed.net.otc.agreement.eligibility.rule.pc.result       | feed.net.otc.agreement.eligibility.rule.pc.status       | feed.net.otc.agreement.eligibility.rule.pc.search       | feed.net.otc.agreement.eligibility.rule.pc.search.result       | OTC            | agreement.complete | feed.net.otc.agreement.eligibility.rule.pc.summary       |
      | feed.net.otc.agreement.eligibility.rule.pc1      | xml         | feed.net.otc.agreement.eligibility.rule.pc1.result      | feed.net.otc.agreement.eligibility.rule.pc1.status      | feed.net.otc.agreement.eligibility.rule.pc1.search      | feed.net.otc.agreement.eligibility.rule.pc1.search.result      | OTC            | agreement.complete | feed.net.otc.agreement.eligibility.rule.pc1.summary      |
      | feed.net.otc.agreement.eligibility.rule.new.bond | xml         | feed.net.otc.agreement.eligibility.rule.new.bond.result | feed.net.otc.agreement.eligibility.rule.new.bond.status | feed.net.otc.agreement.eligibility.rule.new.bond.search | feed.net.otc.agreement.eligibility.rule.new.bond.search.result | OTC            | agreement.complete | feed.net.otc.agreement.eligibility.rule.new.bond.summary |
      | feed.non.net.otc.agreement.eligibility.rule      | xml         | feed.non.net.otc.agreement.eligibility.rule.result      | feed.non.net.otc.agreement.eligibility.rule.status      | feed.non.net.otc.agreement.eligibility.rule.search      | feed.non.net.otc.agreement.eligibility.rule.search.result      | OTC            | agreement.complete | feed.non.net.otc.agreement.eligibility.rule.summary      |

  @KentGu @FeedAgreement @T33065 @F11809 @14.1.0
  Scenario Outline: Feeds FA-C-01-93 Verify Ultimate Parent Holding CL check_Agreement feed
    Given have organisations <organisation>
    When feed agreements <feed.agreement> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    And edit <agreement.type> agreement to <agreement.edit>
    Then agreement summary – agreement summary should be <agreement.summary>
    And asset rule in agreement edit page should be <agreement.asset.rule>
    Examples:
      | organisation        | feed.agreement                                                | file.format | feed.result                                                          | feed.status                                                          | agreement.search                                                     | agreement.search.result                                                     | agreement.type | agreement.edit     | agreement.summary                                                     | agreement.asset.rule                                           |
      | ultimate.parent.org | feed.otc.agreement.concentration.rules.max.value              | xml         | feed.otc.agreement.concentration.rules.max.value.result              | feed.otc.agreement.concentration.rules.max.value.status              | feed.otc.agreement.concentration.rules.max.value.search              | feed.otc.agreement.concentration.rules.max.value.search.result              | OTC            | agreement.complete | feed.otc.agreement.concentration.rules.max.value.summary              | check.otc.agreement.concentration.rules.max.value              |
      | ultimate.parent.org | feed.otc.agreement.concentration.rules.max.percentage         | xml         | feed.otc.agreement.concentration.rules.max.percentage.result         | feed.otc.agreement.concentration.rules.max.percentage.status         | feed.otc.agreement.concentration.rules.max.percentage.search         | feed.otc.agreement.concentration.rules.max.percentage.search.result         | OTC            | agreement.complete | feed.otc.agreement.concentration.rules.max.percentage.summary         | check.otc.agreement.concentration.rules.max.percentage         |
      | ultimate.parent.org | feed.otc.agreement.concentration.rules.max.percentage.invalid | xml         | feed.otc.agreement.concentration.rules.max.percentage.invalid.result | feed.otc.agreement.concentration.rules.max.percentage.invalid.status | feed.otc.agreement.concentration.rules.max.percentage.invalid.search | feed.otc.agreement.concentration.rules.max.percentage.invalid.search.result | OTC            | agreement.complete | feed.otc.agreement.concentration.rules.max.percentage.invalid.summary | check.otc.agreement.concentration.rules.max.percentage.invalid |
      | ultimate.parent.org | feed.otc.agreement.concentration.rules.max.value.invalid      | xml         | feed.otc.agreement.concentration.rules.max.value.invalid.result      | feed.otc.agreement.concentration.rules.max.value.invalid.status      | feed.otc.agreement.concentration.rules.max.value.invalid.search      | feed.otc.agreement.concentration.rules.max.value.invalid.search.result      | OTC            | agreement.complete | feed.otc.agreement.concentration.rules.max.value.invalid.summary      | check.otc.agreement.concentration.rules.max.value.invalid      |

  @KentGu @FeedAgreement @T33184 @F11314 @14.1.0
  Scenario Outline: Feeds FA-C-01-94 Verify CL Breach Adjustment to Feed new Agreement
    Given have collateral preferences <collateral.preferences.concentration.limits.calculation.equal.to.collateral.value>
    When feed agreements <feed.agreement> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    And edit <agreement.type> agreement to <agreement.edit>
    Then agreement summary – agreement summary should be <agreement.summary>
    Examples:
      | collateral.preferences.concentration.limits.calculation.equal.to.collateral.value | feed.agreement                                                         | file.format | feed.result                                                                   | feed.status                                                                   | agreement.search                                                              | agreement.search.result                                                              | agreement.type | agreement.edit     | agreement.summary                                                              |
      | collateral.preferences.collateral.value                                           | feed.otc.agreement.concen.limit.breach.adjust.no                       | xml         | feed.otc.agreement.concen.limit.breach.adjust.no.result                       | feed.otc.agreement.concen.limit.breach.adjust.no.status                       | feed.otc.agreement.concen.limit.breach.adjust.no.search                       | feed.otc.agreement.concen.limit.breach.adjust.no.search.result                       | OTC            | agreement.complete | feed.otc.agreement.concen.limit.breach.adjust.no.summary                       |
      | collateral.preferences.collateral.value                                           | feed.otc.agreement.concen.limit.breach.adjust.asset.adjustment         | xml         | feed.otc.agreement.concen.limit.breach.adjust.asset.adjustment.result         | feed.otc.agreement.concen.limit.breach.adjust.asset.adjustment.status         | feed.otc.agreement.concen.limit.breach.adjust.asset.adjustment.search         | feed.otc.agreement.concen.limit.breach.adjust.asset.adjustment.search.result         | OTC            | agreement.complete | feed.otc.agreement.concen.limit.breach.adjust.asset.adjustment.summary         |
      | collateral.preferences.collateral.value                                           | feed.otc.agreement.concen.limit.breach.adjust.no.adjustment            | xml         | feed.otc.agreement.concen.limit.breach.adjust.no.adjustment.result            | feed.otc.agreement.concen.limit.breach.adjust.no.adjustment.status            | feed.otc.agreement.concen.limit.breach.adjust.no.adjustment.search            | feed.otc.agreement.concen.limit.breach.adjust.no.adjustment.search.result            | OTC            | agreement.complete | feed.otc.agreement.concen.limit.breach.adjust.no.adjustment.summary            |
      | collateral.preferences.collateral.value                                           | feed.otc.agreement.concen.limit.breach.adjust.blank                    | xml         | feed.otc.agreement.concen.limit.breach.adjust.blank.result                    | feed.otc.agreement.concen.limit.breach.adjust.blank.status                    | feed.otc.agreement.concen.limit.breach.adjust.blank.search                    | feed.otc.agreement.concen.limit.breach.adjust.blank.search.result                    | OTC            | agreement.complete | feed.otc.agreement.concen.limit.breach.adjust.blank.summary                    |
      | collateral.preferences.collateral.value                                           | feed.otc.agreement.concen.limit.breach.adjust.no.tag                   | xml         | feed.otc.agreement.concen.limit.breach.adjust.no.tag.result                   | feed.otc.agreement.concen.limit.breach.adjust.no.tag.status                   | feed.otc.agreement.concen.limit.breach.adjust.no.tag.search                   | feed.otc.agreement.concen.limit.breach.adjust.no.tag.search.result                   | OTC            | agreement.complete | feed.otc.agreement.concen.limit.breach.adjust.no.tag.summary                   |
      | collateral.preferences.collateral.value                                           | feed.fcm.agreement.concen.limit.breach.adjust.asset.adjustment         | xml         | feed.fcm.agreement.concen.limit.breach.adjust.asset.adjustment.result         | feed.fcm.agreement.concen.limit.breach.adjust.asset.adjustment.status         | feed.fcm.agreement.concen.limit.breach.adjust.asset.adjustment.search         | feed.fcm.agreement.concen.limit.breach.adjust.asset.adjustment.search.result         | FCM            | agreement.complete | feed.fcm.agreement.concen.limit.breach.adjust.asset.adjustment.summary         |
      | collateral.preferences.collateral.value                                           | feed.repo.agreement.concen.limit.breach.adjust.asset.adjustment        | xml         | feed.repo.agreement.concen.limit.breach.adjust.asset.adjustment.result        | feed.repo.agreement.concen.limit.breach.adjust.asset.adjustment.status        | feed.repo.agreement.concen.limit.breach.adjust.asset.adjustment.search        | feed.repo.agreement.concen.limit.breach.adjust.asset.adjustment.search.result        | Repo           | agreement.complete | feed.repo.agreement.concen.limit.breach.adjust.asset.adjustment.summary        |
      | collateral.preferences.collateral.value                                           | feed.etf.agreement.concen.limit.breach.adjust.asset.adjustment         | xml         | feed.etf.agreement.concen.limit.breach.adjust.asset.adjustment.result         | feed.etf.agreement.concen.limit.breach.adjust.asset.adjustment.status         | feed.etf.agreement.concen.limit.breach.adjust.asset.adjustment.search         | feed.etf.agreement.concen.limit.breach.adjust.asset.adjustment.search.result         | ETF            | agreement.complete | feed.etf.agreement.concen.limit.breach.adjust.asset.adjustment.summary         |
      | collateral.preferences.collateral.value                                           | feed.multi.model.agreement.concen.limit.breach.adjust.asset.adjustment | xml         | feed.multi.model.agreement.concen.limit.breach.adjust.asset.adjustment.result | feed.multi.model.agreement.concen.limit.breach.adjust.asset.adjustment.status | feed.multi.model.agreement.concen.limit.breach.adjust.asset.adjustment.search | feed.multi.model.agreement.concen.limit.breach.adjust.asset.adjustment.search.result | OTC            | agreement.complete | feed.multi.model.agreement.concen.limit.breach.adjust.asset.adjustment.summary |

  @KentGu @FeedAgreement @T33187 @F11314 @14.1.0
  Scenario Outline: Feeds FA-C-01-95 Verify CL Breach Adjustment to Feed Update Agreement
    Given have collateral preferences <collateral.preferences.concentration.limits.calculation.equal.to.collateral.value>
    And have <agreement.type> agreements <pre.condition.agreement>
    When feed agreements <feed.agreement> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    Then agreement summary – agreement summary should be <agreement.summary>
    Examples:
      | collateral.preferences.concentration.limits.calculation.equal.to.collateral.value | agreement.type | pre.condition.agreement                         | feed.agreement                                                         | file.format | feed.result                                                                   | feed.status                                                                   | agreement.search                                                              | agreement.search.result                                                              | agreement.summary                                                              |
      | collateral.preferences.collateral.value                                           | OTC            | otc.agreeement.has.cl.breach.adjustment         | feed.otc.agreement.concen.limit.breach.adjust.no                       | xml         | feed.otc.agreement.concen.limit.breach.adjust.no.result                       | feed.otc.agreement.concen.limit.breach.adjust.no.status                       | feed.otc.agreement.concen.limit.breach.adjust.no.search                       | feed.otc.agreement.concen.limit.breach.adjust.no.search.result                       | feed.otc.agreement.concen.limit.breach.adjust.no.summary                       |
      | collateral.preferences.collateral.value                                           | FCM            | clearing.agreeement.has.cl.breach.adjustment    | feed.clearing.agreement.concen.limit.breach.adjust.no.adjustment       | xml         | feed.clearing.agreement.concen.limit.breach.adjust.no.adjustment.result       | feed.clearing.agreement.concen.limit.breach.adjust.no.adjustment.status       | feed.clearing.agreement.concen.limit.breach.adjust.no.adjustment.search       | feed.clearing.agreement.concen.limit.breach.adjust.no.adjustment.search.result       | feed.clearing.agreement.concen.limit.breach.adjust.no.adjustment.summary       |
      | collateral.preferences.collateral.value                                           | FCM            | clearing.agreeement.has.no.cl.breach.adjustment | feed.clearing.agreement.concen.limit.breach.adjust.asset.adjustment    | xml         | feed.clearing.agreement.concen.limit.breach.adjust.asset.adjustment.result    | feed.clearing.agreement.concen.limit.breach.adjust.asset.adjustment.status    | feed.clearing.agreement.concen.limit.breach.adjust.asset.adjustment.search    | feed.clearing.agreement.concen.limit.breach.adjust.asset.adjustment.search.result    | feed.clearing.agreement.concen.limit.breach.adjust.asset.adjustment.summary    |
      | collateral.preferences.collateral.value                                           | OTC            | otc.agreeement.has.cl.breach.adjustment         | feed.otc.agreement.concen.limit.breach.adjust.blank                    | xml         | feed.otc.agreement.concen.limit.breach.adjust.blank.result                    | feed.otc.agreement.concen.limit.breach.adjust.blank.status                    | feed.otc.agreement.concen.limit.breach.adjust.blank.search                    | feed.otc.agreement.concen.limit.breach.adjust.blank.search.result                    | feed.otc.agreement.concen.limit.breach.adjust.blank.summary                    |
      | collateral.preferences.collateral.value                                           | OTC            | otc.agreeement.has.cl.breach.adjustment         | feed.otc.agreement.concen.limit.breach.adjust.no.tag                   | xml         | feed.otc.agreement.concen.limit.breach.adjust.no.tag.result                   | feed.otc.agreement.concen.limit.breach.adjust.no.tag.status                   | feed.otc.agreement.concen.limit.breach.adjust.no.tag.search                   | feed.otc.agreement.concen.limit.breach.adjust.no.tag.search.result                   | feed.otc.agreement.concen.limit.breach.adjust.no.tag.summary                   |
      | collateral.preferences.collateral.value                                           | FCM            | fcm.agreement                                   | feed.fcm.agreement.concen.limit.breach.adjust.asset.adjustment         | xml         | feed.fcm.agreement.concen.limit.breach.adjust.asset.adjustment.result         | feed.fcm.agreement.concen.limit.breach.adjust.asset.adjustment.status         | feed.fcm.agreement.concen.limit.breach.adjust.asset.adjustment.search         | feed.fcm.agreement.concen.limit.breach.adjust.asset.adjustment.search.result         | feed.fcm.agreement.concen.limit.breach.adjust.asset.adjustment.summary         |
      | collateral.preferences.collateral.value                                           | Repo           | repo.agreement                                  | feed.repo.agreement.concen.limit.breach.adjust.asset.adjustment        | xml         | feed.repo.agreement.concen.limit.breach.adjust.asset.adjustment.result        | feed.repo.agreement.concen.limit.breach.adjust.asset.adjustment.status        | feed.repo.agreement.concen.limit.breach.adjust.asset.adjustment.search        | feed.repo.agreement.concen.limit.breach.adjust.asset.adjustment.search.result        | feed.repo.agreement.concen.limit.breach.adjust.asset.adjustment.summary        |
      | collateral.preferences.collateral.value                                           | ETF            | etf.agreement                                   | feed.etf.agreement.concen.limit.breach.adjust.asset.adjustment         | xml         | feed.etf.agreement.concen.limit.breach.adjust.asset.adjustment.result         | feed.etf.agreement.concen.limit.breach.adjust.asset.adjustment.status         | feed.etf.agreement.concen.limit.breach.adjust.asset.adjustment.search         | feed.etf.agreement.concen.limit.breach.adjust.asset.adjustment.search.result         | feed.etf.agreement.concen.limit.breach.adjust.asset.adjustment.summary         |
      | collateral.preferences.collateral.value                                           | OTC            | multi.model.agreement                           | feed.multi.model.agreement.concen.limit.breach.adjust.asset.adjustment | xml         | feed.multi.model.agreement.concen.limit.breach.adjust.asset.adjustment.result | feed.multi.model.agreement.concen.limit.breach.adjust.asset.adjustment.status | feed.multi.model.agreement.concen.limit.breach.adjust.asset.adjustment.search | feed.multi.model.agreement.concen.limit.breach.adjust.asset.adjustment.search.result | feed.multi.model.agreement.concen.limit.breach.adjust.asset.adjustment.summary |

  @KentGu @FeedAgreement @T32586 @F11270 @14.1.0
  Scenario Outline: Feeds FA-D-01-47 Verify agreement feed Termination and Transfer currency with valid value can set or update succeed
    When feed agreements <feed.agreement> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    And edit <agreement.type> agreement to <agreement.edit>
    Then agreement summary – agreement summary should be <agreement.summary>
    Examples:
      | feed.agreement                                                                  | file.format | feed.result                                                                            | feed.status                                                                            | agreement.search                                                                       | agreement.search.result                                                                       | agreement.type | agreement.edit     | agreement.summary                                                                       |
      | feed.otc.agreement.principal.transfer.ccy.blank.counterparty.transfer.ccy.blank | xml         | feed.otc.agreement.principal.transfer.ccy.blank.counterparty.transfer.ccy.blank.result | feed.otc.agreement.principal.transfer.ccy.blank.counterparty.transfer.ccy.blank.status | feed.otc.agreement.principal.transfer.ccy.blank.counterparty.transfer.ccy.blank.search | feed.otc.agreement.principal.transfer.ccy.blank.counterparty.transfer.ccy.blank.search.result | OTC            | agreement.complete | feed.otc.agreement.principal.transfer.ccy.blank.counterparty.transfer.ccy.blank.summary |
      | feed.otc.agreement.principal.transfer.ccy.usd.counterparty.transfer.gbp         | xml         | feed.otc.agreement.principal.transfer.ccy.usd.counterparty.transfer.gbp.result         | feed.otc.agreement.principal.transfer.ccy.usd.counterparty.transfer.gbp.status         | feed.otc.agreement.principal.transfer.ccy.usd.counterparty.transfer.gbp.search         | feed.otc.agreement.principal.transfer.ccy.usd.counterparty.transfer.gbp.search.result         | OTC            | agreement.complete | feed.otc.agreement.principal.transfer.ccy.usd.counterparty.transfer.gbp.summary         |
      | feed.otc.agreement.principal.transfer.ccy.blank.counterparty.transfer.ccy.blank | xml         | feed.otc.agreement.principal.transfer.ccy.blank.counterparty.transfer.ccy.blank.result | feed.otc.agreement.principal.transfer.ccy.blank.counterparty.transfer.ccy.blank.status | feed.otc.agreement.principal.transfer.ccy.blank.counterparty.transfer.ccy.blank.search | feed.otc.agreement.principal.transfer.ccy.blank.counterparty.transfer.ccy.blank.search.result | OTC            | agreement.complete | feed.otc.agreement.principal.transfer.ccy.blank.counterparty.transfer.ccy.blank.summary |
      | feed.otc.agreement.principal.transfer.ccy.gbp.counterparty.transfer.usd         | xml         | feed.otc.agreement.principal.transfer.ccy.gbp.counterparty.transfer.usd.result         | feed.otc.agreement.principal.transfer.ccy.gbp.counterparty.transfer.usd.status         | feed.otc.agreement.principal.transfer.ccy.gbp.counterparty.transfer.usd.search         | feed.otc.agreement.principal.transfer.ccy.gbp.counterparty.transfer.usd.search.result         | OTC            | agreement.complete | feed.otc.agreement.principal.transfer.ccy.gbp.counterparty.transfer.usd.summary         |
      | feed.repo.agreement.principal.transfer.ccy.usd.counterparty.transfer.usd        | xml         | feed.repo.agreement.principal.transfer.ccy.usd.counterparty.transfer.usd.result        | feed.repo.agreement.principal.transfer.ccy.usd.counterparty.transfer.usd.status        | feed.repo.agreement.principal.transfer.ccy.usd.counterparty.transfer.usd.search        | feed.repo.agreement.principal.transfer.ccy.usd.counterparty.transfer.usd.search.result        | Repo           | agreement.complete | feed.repo.agreement.principal.transfer.ccy.usd.counterparty.transfer.usd.summary        |
      | feed.repo.agreement.principal.transfer.ccy.gbp.counterparty.transfer.gbp        | xml         | feed.repo.agreement.principal.transfer.ccy.gbp.counterparty.transfer.gbp.result        | feed.repo.agreement.principal.transfer.ccy.gbp.counterparty.transfer.gbp.status        | feed.repo.agreement.principal.transfer.ccy.gbp.counterparty.transfer.gbp.search        | feed.repo.agreement.principal.transfer.ccy.gbp.counterparty.transfer.gbp.search.result        | Repo           | agreement.complete | feed.repo.agreement.principal.transfer.ccy.gbp.counterparty.transfer.gbp.summary        |

  @KentGu @FeedAgreement @T31441 @F10168 @14.1.0
  Scenario Outline: Feeds-FA-D-02-44 Verify that column rehypothecation_flag and im_rehypothecation_flag work successfully for Not-Net agreement by feed
    When feed agreements <feed.agreement1>,<feed.agreement2> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    And search agreement <agreement1.search>
    And edit agreement <agreement1.search.result> in agreement search page
    And edit <agreement.type> agreement to <agreement.edit>
    Then agreement summary – agreement summary should be <agreement1.summary>
    And search agreement <agreement2.search>
    And edit agreement <agreement2.search.result> in agreement search page
    And edit <agreement.type> agreement to <agreement.edit>
    Then agreement summary – agreement summary should be <agreement2.summary>
    Examples:
      | feed.agreement1                                       | feed.agreement2                                        | file.format | feed.result         | feed.status         | agreement1.search                                            | agreement1.search.result                                            | agreement.type | agreement.edit     | agreement1.summary                                            | agreement2.search                                             | agreement2.search.result                                             | agreement2.summary                                             |
      | feed.otc.not.net.agreement.vm.bilateral.im.unilateral | feed.otc.not.net.agreement.vm.unilateral.im.unilateral | xml         | feed.success.result | feed.success.status | feed.otc.not.net.agreement.vm.bilateral.im.unilateral.search | feed.otc.not.net.agreement.vm.bilateral.im.unilateral.search.result | OTC            | agreement.complete | feed.otc.not.net.agreement.vm.bilateral.im.unilateral.summary | feed.otc.not.net.agreement.vm.unilateral.im.unilateral.search | feed.otc.not.net.agreement.vm.unilateral.im.unilateral.search.result | feed.otc.not.net.agreement.vm.unilateral.im.unilateral.summary |
      | feed.otc.not.net.agreement.invalid.rehypothecation    | feed.otc.not.net.agreement.invalid.im.rehypothecation  | xml         | feed.success.result | feed.success.status | feed.otc.not.net.agreement.invalid.rehypothecation.search    | feed.otc.not.net.agreement.invalid.rehypothecation.search.result    | OTC            | agreement.complete | feed.otc.not.net.agreement.invalid.rehypothecation.summary    | feed.otc.not.net.agreement.invalid.im.rehypothecation.search  | feed.otc.not.net.agreement.invalid.im.rehypothecation.search.result  | feed.otc.not.net.agreement.invalid.im.rehypothecation.summary  |
      | feed.otc.not.net.agreement.rehyp.flag.no              | feed.otc.not.net.agreement.im.vm.rehyp.flag.no         | xml         | feed.success.result | feed.success.status | feed.otc.not.net.agreement.rehyp.flag.no.search              | feed.otc.not.net.agreement.rehyp.flag.no.search.result              | OTC            | agreement.complete | feed.otc.not.net.agreement.rehyp.flag.no.summary              | feed.otc.not.net.agreement.im.vm.rehyp.flag.no.search         | feed.otc.not.net.agreement.im.vm.rehyp.flag.no.search.result         | feed.otc.not.net.agreement.im.vm.rehyp.flag.no.summary         |

  @KentGu @FeedAgreement @T32804 @F11590 @F13083 @14.1.0 @14.1.0.SP3
  Scenario Outline: Feeds FA-FT-02-20 Verify that feed agreements for Organisation Threshold check
    Given have organisations <organisation.principal.im.threshold.3000>,<organisation.principal.im.threshold.2000>,<organisation.principal.im.threshold.1000>,<organisation.counterparty.1>,<organisation.counterparty.2>
    When feed agreements <feed.agreement> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    And edit <agreement.type> agreement to <agreement.edit>
    Then agreement summary – agreement summary should be <agreement.summary>
    Examples:
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.agreement                                                           | file.format | feed.result                                                                     | feed.status                                                                     | agreement.search                                                                | agreement.search.result                                                                | agreement.type | agreement.edit     | agreement.summary                                                                |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger                                   | xml         | feed.otc.not.net.agreement.fix.trigger.result                                   | feed.otc.not.net.agreement.fix.trigger.status                                   | feed.otc.not.net.agreement.fix.trigger.search                                   | feed.otc.not.net.agreement.fix.trigger.search.result                                   | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.summary                                   |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger                                           | xml         | feed.fcm.agreement.fix.trigger.result                                           | feed.fcm.agreement.fix.trigger.status                                           | feed.fcm.agreement.fix.trigger.search                                           | feed.fcm.agreement.fix.trigger.search.result                                           | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.summary                                           |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.principal.2                       | xml         | feed.otc.not.net.agreement.fix.trigger.principal.2.result                       | feed.otc.not.net.agreement.fix.trigger.principal.2.status                       | feed.otc.not.net.agreement.fix.trigger.principal.2.search                       | feed.otc.not.net.agreement.fix.trigger.principal.2.search.result                       | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.principal.2.summary                       |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.principal.2                               | xml         | feed.fcm.agreement.fix.trigger.principal.2.result                               | feed.fcm.agreement.fix.trigger.principal.2.status                               | feed.fcm.agreement.fix.trigger.principal.2.search                               | feed.fcm.agreement.fix.trigger.principal.2.search.result                               | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.principal.2.summary                               |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.principal.3                       | xml         | feed.otc.not.net.agreement.fix.trigger.principal.3.result                       | feed.otc.not.net.agreement.fix.trigger.principal.3.status                       | feed.otc.not.net.agreement.fix.trigger.principal.3.search                       | feed.otc.not.net.agreement.fix.trigger.principal.3.search.result                       | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.principal.3.summary                       |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.principal.3                               | xml         | feed.fcm.agreement.fix.trigger.principal.3.result                               | feed.fcm.agreement.fix.trigger.principal.3.status                               | feed.fcm.agreement.fix.trigger.principal.3.search                               | feed.fcm.agreement.fix.trigger.principal.3.search.result                               | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.principal.3.summary                               |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.counterparty.2                    | xml         | feed.otc.not.net.agreement.fix.trigger.counterparty.2.result                    | feed.otc.not.net.agreement.fix.trigger.counterparty.2.status                    | feed.otc.not.net.agreement.fix.trigger.counterparty.2.search                    | feed.otc.not.net.agreement.fix.trigger.counterparty.2.search.result                    | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.counterparty.2.summary                    |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.counterparty.2                            | xml         | feed.fcm.agreement.fix.trigger.counterparty.2.result                            | feed.fcm.agreement.fix.trigger.counterparty.2.status                            | feed.fcm.agreement.fix.trigger.counterparty.2.search                            | feed.fcm.agreement.fix.trigger.counterparty.2.search.result                            | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.counterparty.2.summary                            |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.im.threshold.300                  | xml         | feed.otc.not.net.agreement.fix.trigger.im.threshold.300.result                  | feed.otc.not.net.agreement.fix.trigger.im.threshold.300.status                  | feed.otc.not.net.agreement.fix.trigger.im.threshold.300.search                  | feed.otc.not.net.agreement.fix.trigger.im.threshold.300.search.result                  | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.im.threshold.300.summary                  |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.im.threshold.400                          | xml         | feed.fcm.agreement.fix.trigger.im.threshold.400.result                          | feed.fcm.agreement.fix.trigger.im.threshold.400.status                          | feed.fcm.agreement.fix.trigger.im.threshold.400.search                          | feed.fcm.agreement.fix.trigger.im.threshold.400.search.result                          | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.im.threshold.400.summary                          |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.principal.2.im.threshold.500      | xml         | feed.otc.not.net.agreement.fix.trigger.principal.2.im.threshold.500.result      | feed.otc.not.net.agreement.fix.trigger.principal.2.im.threshold.500.status      | feed.otc.not.net.agreement.fix.trigger.principal.2.im.threshold.500.search      | feed.otc.not.net.agreement.fix.trigger.principal.2.im.threshold.500.search.result      | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.principal.2.im.threshold.500.summary      |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.principal.2.im.threshold.600              | xml         | feed.fcm.agreement.fix.trigger.principal.2.im.threshold.600.result              | feed.fcm.agreement.fix.trigger.principal.2.im.threshold.600.status              | feed.fcm.agreement.fix.trigger.principal.2.im.threshold.600.search              | feed.fcm.agreement.fix.trigger.principal.2.im.threshold.600.search.result              | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.principal.2.im.threshold.600.summary              |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.principal.3.im.threshold.1200     | xml         | feed.otc.not.net.agreement.fix.trigger.principal.3.im.threshold.1200.result     | feed.otc.not.net.agreement.fix.trigger.principal.3.im.threshold.1200.status     | feed.otc.not.net.agreement.fix.trigger.principal.3.im.threshold.1200.search     | feed.otc.not.net.agreement.fix.trigger.principal.3.im.threshold.1200.search.result     | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.principal.3.im.threshold.1200.summary     |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.principal.3.im.threshold.500              | xml         | feed.fcm.agreement.fix.trigger.principal.3.im.threshold.500.result              | feed.fcm.agreement.fix.trigger.principal.3.im.threshold.500.status              | feed.fcm.agreement.fix.trigger.principal.3.im.threshold.500.search              | feed.fcm.agreement.fix.trigger.principal.3.im.threshold.500.search.result              | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.principal.3.im.threshold.500.summary              |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.counterparty.2.im.threshold.600   | xml         | feed.otc.not.net.agreement.fix.trigger.counterparty.2.im.threshold.600.result   | feed.otc.not.net.agreement.fix.trigger.counterparty.2.im.threshold.600.status   | feed.otc.not.net.agreement.fix.trigger.counterparty.2.im.threshold.600.search   | feed.otc.not.net.agreement.fix.trigger.counterparty.2.im.threshold.600.search.result   | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.counterparty.2.im.threshold.600.summary   |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.counterparty.2.im.threshold.800           | xml         | feed.fcm.agreement.fix.trigger.counterparty.2.im.threshold.800.result           | feed.fcm.agreement.fix.trigger.counterparty.2.im.threshold.800.status           | feed.fcm.agreement.fix.trigger.counterparty.2.im.threshold.800.search           | feed.fcm.agreement.fix.trigger.counterparty.2.im.threshold.800.search.result           | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.counterparty.2.im.threshold.800.summary           |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.principal.2.cad.im.threshold.1000 | xml         | feed.otc.not.net.agreement.fix.trigger.principal.2.cad.im.threshold.1000.result | feed.otc.not.net.agreement.fix.trigger.principal.2.cad.im.threshold.1000.status | feed.otc.not.net.agreement.fix.trigger.principal.2.cad.im.threshold.1000.search | feed.otc.not.net.agreement.fix.trigger.principal.2.cad.im.threshold.1000.search.result | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.principal.2.cad.im.threshold.1000.summary |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.principal.2.cad.im.threshold.1100         | xml         | feed.fcm.agreement.fix.trigger.principal.2.cad.im.threshold.1100.result         | feed.fcm.agreement.fix.trigger.principal.2.cad.im.threshold.1100.status         | feed.fcm.agreement.fix.trigger.principal.2.cad.im.threshold.1100.search         | feed.fcm.agreement.fix.trigger.principal.2.cad.im.threshold.1100.search.result         | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.principal.2.cad.im.threshold.1100.summary         |
      | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.principal.3.cad.im.threshold.1200 | xml         | feed.otc.not.net.agreement.fix.trigger.principal.3.cad.im.threshold.1200.result | feed.otc.not.net.agreement.fix.trigger.principal.3.cad.im.threshold.1200.status | feed.otc.not.net.agreement.fix.trigger.principal.3.cad.im.threshold.1200.search | feed.otc.not.net.agreement.fix.trigger.principal.3.cad.im.threshold.1200.search.result | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.principal.3.cad.im.threshold.1200.summary |

  @KentGu @FeedAgreement @T31492 @F10409 @14.1.0
  Scenario Outline: Feeds-FA-03-01 Verify feed agreement successfully for TMR
    When feed agreements <feed.agreement> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    And edit <agreement.type> agreement to <agreement.edit>
    Then agreement summary – agreement summary should be <agreement.summary>
    Examples:
      | feed.agreement                        | file.format | feed.result                                  | feed.status                                  | agreement.search                             | agreement.search.result                             | agreement.type | agreement.edit     | agreement.summary                             |
      | feed.otc.agreement.trade.mapping.rule | xml         | feed.otc.agreement.trade.mapping.rule.result | feed.otc.agreement.trade.mapping.rule.status | feed.otc.agreement.trade.mapping.rule.search | feed.otc.agreement.trade.mapping.rule.search.result | OTC            | agreement.complete | feed.otc.agreement.trade.mapping.rule.summary |

  @KentGu @FeedAgreement @T32300 @F8987 @14.1.0
  Scenario Outline: Feeds-FA-03-04 Verify feed agreement successfully for Interest Letter_xml
    When feed agreements <feed.agreement> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    And edit <agreement.type> agreement to <agreement.edit>
    Then agreement summary – agreement summary should be <agreement.summary>
    Examples:
      | feed.agreement                                 | file.format | feed.result                                           | feed.status                                           | agreement.search                                      | agreement.search.result                                      | agreement.type | agreement.edit     | agreement.summary                                      |
      | feed.otc.agreement.multi.model.interest.letter | xml         | feed.otc.agreement.multi.model.interest.letter.result | feed.otc.agreement.multi.model.interest.letter.status | feed.otc.agreement.multi.model.interest.letter.search | feed.otc.agreement.multi.model.interest.letter.search.result | OTC            | agreement.complete | feed.otc.agreement.multi.model.interest.letter.summary |
      | feed.etf.agreement.multi.model.interest.letter | xml         | feed.etf.agreement.multi.model.interest.letter.result | feed.etf.agreement.multi.model.interest.letter.status | feed.etf.agreement.multi.model.interest.letter.search | feed.etf.agreement.multi.model.interest.letter.search.result | ETF            | agreement.complete | feed.etf.agreement.multi.model.interest.letter.summary |

  @KentGu @FeedAgreement @T2101 @F9068 @BZ_33011 @14.1.0 @2010.1.SP5 @wip
  Scenario: Feeds FA-01-02 Feed Agreements with consecutive blank columns in Excel
    When feed agreements feed.otc.agreement.grace.period.0 by xlsx
    Then feed log should be feed.otc.agreement.grace.period.0.result
    And feed status should be feed.otc.agreement.grace.period.0.status
    And search agreement feed.otc.agreement.grace.period.0.search
    And edit agreement feed.otc.agreement.grace.period.0.search.result in agreement search page
    And edit OTC agreement to agreement.complete
    Then agreement summary – agreement summary should be feed.otc.agreement.grace.period.0.summary

  @KentGu @FeedAgreement @T33033 @F11823 @14.1.0
  Scenario Outline: Feeds FA-01-89 Verify that messageIdType and messageAgreementId logic works with API Licence - Message Agreement Id
    When feed agreements <feed.agreement> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    Examples:
      | feed.agreement                            | file.format | feed.result                                      | feed.status                                      |
      | feed.otc.agreement.blank.message.medium   | xml         | feed.otc.agreement.blank.message.medium.result   | feed.otc.agreement.blank.message.medium.status   |
      | feed.otc.agreement.not.exist.agreement.id | xml         | feed.otc.agreement.not.exist.agreement.id.result | feed.otc.agreement.not.exist.agreement.id.status |
      | feed.otc.agreement.exist.agreement.id     | xml         | feed.otc.agreement.exist.agreement.id.result     | feed.otc.agreement.exist.agreement.id.status     |

  @KentGu @FeedAgreement @T33034 @F11823 @14.1.0
  Scenario Outline: Feeds FA-01-90 Verify that messageIdType and messageAgreementId logic works with API Licence - Message Group Id
    When feed agreements <feed.agreement> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    Examples:
      | feed.agreement                                           | file.format | feed.result                                                     | feed.status                                                     |
      | feed.otc.agreement.blank.message.medium.message.group.id | xml         | feed.otc.agreement.blank.message.medium.message.group.id.result | feed.otc.agreement.blank.message.medium.message.group.id.status |
      | feed.otc.agreement.blank.message.group.id                | xml         | feed.otc.agreement.blank.message.group.id.result                | feed.otc.agreement.blank.message.group.id.status                |
      | feed.otc.agreement.invalid.message.group.id              | xml         | feed.otc.agreement.invalid.message.group.id.result              | feed.otc.agreement.invalid.message.group.id.status              |
      | feed.otc.agreement.valid.message.group.id                | xml         | feed.otc.agreement.valid.message.group.id.result                | feed.otc.agreement.valid.message.group.id.status                |
      | feed.otc.agreement.existing.message.group.id             | xml         | feed.otc.agreement.existing.message.group.id.result             | feed.otc.agreement.existing.message.group.id.status             |

  @KentGu @FeedStaticData @T27841 @F9068 @D13645 @D13989 @14.1.0 @2012.2.SP2.6.1 @2012.3.2.SP1
  Scenario: Feeds FSD-01-27 feed static data by xlsx format
    When feed system static data feed.otc.product by xlsx
    Then feed log should be feed.success.result
    And feed status should be feed.success.log
    When navigate to collateral static data page
    Then collateral static data should be check.otc.product

  @KentGu @FeedHolidayCentre @T5322 @F9068 @14.1.0 @2011.1
  Scenario Outline: Feeds FHC-HCTC-01-02 Verify that successfull to import holiday centre file with correct [Date]
    When feed holiday centres <feed.holiday.centres> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    When navigate to holiday centre page
    Then holiday centre should be <check.holiday.centres>
    Examples:
      | feed.holiday.centres              | file.format | feed.result                              | feed.status                              | check.holiday.centres                   |
      | feed.holiday.centres.new.year     | xml         | feed.holiday.centres.new.year.result     | feed.holiday.centres.new.year.status     | check.feed.holiday.centres.new.year     |
      | feed.holiday.centres.teachers.day | xml         | feed.holiday.centres.teachers.day.result | feed.holiday.centres.teachers.day.status | check.feed.holiday.centres.teachers.day |
      | feed.holiday.centres.new.year     | xlsx        | feed.holiday.centres.new.year.result     | feed.holiday.centres.new.year.status     | check.feed.holiday.centres.new.year     |
      | feed.holiday.centres.teachers.day | xlsx        | feed.holiday.centres.teachers.day.result | feed.holiday.centres.teachers.day.status | check.feed.holiday.centres.teachers.day |
      | feed.holiday.centres.new.year     | csv         | feed.holiday.centres.new.year.result     | feed.holiday.centres.new.year.status     | check.feed.holiday.centres.new.year     |
      | feed.holiday.centres.teachers.day | csv         | feed.holiday.centres.teachers.day.result | feed.holiday.centres.teachers.day.status | check.feed.holiday.centres.teachers.day |

  @KentGu @FeedOrganisations @T26090 @F7052 @F9068 @14.1.0 @13.0.0
  Scenario Outline: Feeds FO-01-35 Verify that successful to feed org with new filed LEI by execl
    When feed organisations <feed.organisation> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    When navigate to view organisation
    And search organisations <search.organisation>
    Then organisation should be <check.feed.organisation>
    Examples:
      | feed.organisation           | file.format | feed.result                        | feed.status                        | search.organisation                | check.feed.organisation           |
      | feed.organisation.empty.lei | xml         | feed.organisation.empty.lei.result | feed.organisation.empty.lei.status | feed.organisation.empty.lei.search | check.feed.organisation.empty.lei |
#      | feed.organisation.empty.lei | xlsx        | feed.organisation.empty.lei.result | feed.organisation.empty.lei.status | feed.organisation.empty.lei.search | check.feed.organisation.empty.lei |

  @KentGu @FeedOrganisations @T26091 @F7052 @F9068 @14.1.0 @13.0.0
  Scenario Outline: Feeds FO-01-36 Verify that successful to feed org with new filed LEI by XML
    When feed organisations <feed.organisation> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    When navigate to view organisation
    And search organisations <search.organisation>
    Then organisation should be <check.feed.organisation>
    Examples:
      | feed.organisation                  | file.format | feed.result                               | feed.status                               | search.organisation                       | check.feed.organisation                  |
      | feed.organisation.normal           | xml         | feed.organisation.normal.result           | feed.organisation.normal.status           | feed.organisation.normal.search           | check.feed.organisation.normal           |
      | feed.organisation.long.lei         | xml         | feed.organisation.long.lei.result         | feed.organisation.long.lei.status         | feed.organisation.long.lei.search         | check.feed.organisation.long.lei         |
      | feed.organisation.normal.modify    | xml         | feed.organisation.normal.modify.result    | feed.organisation.normal.modify.status    | feed.organisation.normal.modify.search    | check.feed.organisation.normal.modify    |
      | feed.organisation.normal.empty.lei | xml         | feed.organisation.normal.empty.lei.result | feed.organisation.normal.empty.lei.status | feed.organisation.normal.empty.lei.search | check.feed.organisation.normal.empty.lei |
#      | feed.organisation.normal           | excel       | feed.organisation.normal.result           | feed.organisation.normal.status           | feed.organisation.normal.search           | check.feed.organisation.normal           |
#      | feed.organisation.long.lei         | excel       | feed.organisation.long.lei.result         | feed.organisation.long.lei.status         | feed.organisation.long.lei.search         | check.feed.organisation.long.lei         |
#      | feed.organisation.normal.modify    | excel       | feed.organisation.normal.modify.result    | feed.organisation.normal.modify.status    | feed.organisation.normal.modify.search    | check.feed.organisation.normal.modify    |
#      | feed.organisation.normal.empty.lei | excel       | feed.organisation.normal.empty.lei.result | feed.organisation.normal.empty.lei.status | feed.organisation.normal.empty.lei.search | check.feed.organisation.normal.empty.lei |

  @KentGu @FeedOrganisations @T32501 @T32500 @F11281 @14.1.0
  Scenario Outline: Feeds FO-02-16 Verify that successful to feed org with correct [Ultimate Parent Correlation] and [Close Link Correlation]_xml
    When feed organisations <feed.organisation> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    When navigate to view organisation
    And search organisations <search.organisation>
    Then organisation should be <check.feed.organisation>
    Examples:
      | feed.organisation                                                   | file.format | feed.result                                                                | feed.status                                                                | search.organisation                                                        | check.feed.organisation                                                   |
      | feed.organisation.empty.ultimate.parenets.empty.close.links         | xml         | feed.organisation.empty.ultimate.parenets.empty.close.links.result         | feed.organisation.empty.ultimate.parenets.empty.close.links.status         | feed.organisation.empty.ultimate.parenets.empty.close.links.search         | check.feed.organisation.empty.ultimate.parenets.empty.close.links         |
      | feed.organisation.valid.ultimate.parenets.valid.close.links         | xml         | feed.organisation.valid.ultimate.parenets.valid.close.links.result         | feed.organisation.valid.ultimate.parenets.valid.close.links.status         | feed.organisation.valid.ultimate.parenets.valid.close.links.search         | check.feed.organisation.valid.ultimate.parenets.valid.close.links         |
      | feed.organisation.invalid.ultimate.parenets.invalid.close.links     | xml         | feed.organisation.invalid.ultimate.parenets.invalid.close.links.result     | feed.organisation.invalid.ultimate.parenets.invalid.close.links.status     | feed.organisation.invalid.ultimate.parenets.invalid.close.links.search     | check.feed.organisation.invalid.ultimate.parenets.invalid.close.links     |
      | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links | xml         | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links.result | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links.status | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links.search | check.feed.organisation.valid.one.ultimate.parenets.valid.one.close.links |
#      | feed.organisation.empty.ultimate.parenets.empty.close.links         | excel       | feed.organisation.empty.ultimate.parenets.empty.close.links.result         | feed.organisation.empty.ultimate.parenets.empty.close.links.status         | feed.organisation.empty.ultimate.parenets.empty.close.links.search         | check.feed.organisation.empty.ultimate.parenets.empty.close.links         |
#      | feed.organisation.invalid.ultimate.parenets.invalid.close.links     | excel       | feed.organisation.invalid.ultimate.parenets.invalid.close.links.result     | feed.organisation.invalid.ultimate.parenets.invalid.close.links.status     | feed.organisation.invalid.ultimate.parenets.invalid.close.links.search     | check.feed.organisation.invalid.ultimate.parenets.invalid.close.links     |
#      | feed.organisation.valid.ultimate.parenets.valid.close.links         | excel       | feed.organisation.valid.ultimate.parenets.valid.close.links.result         | feed.organisation.valid.ultimate.parenets.valid.close.links.status         | feed.organisation.valid.ultimate.parenets.valid.close.links.search         | check.feed.organisation.valid.ultimate.parenets.valid.close.links         |
#      | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links | excel       | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links.result | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links.status | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links.search | check.feed.organisation.valid.one.ultimate.parenets.valid.one.close.links |

  @KentGu @FeedOrganisations @T3740 @F3629 @F5020 @F9068 @14.1.0 @2011.1 @2011.2 @2012.1 @2012.3
  Scenario Outline: Feeds FOC-OCTC-01-01 Verify that successful to import org contact file when [PARENT] is correct
    When feed organisation contact <feed.organisation.contact> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    When navigate to view organisation
    And search organisations <search.organisation>
    Then organisation should be <check.feed.organisation>
    Examples:
      | feed.organisation.contact               | file.format | feed.result                                    | feed.status                                    | search.organisation                            | check.feed.organisation                       |
      | feed.organisation.contact.normal        | xml         | feed.organisation.contact.normal.result        | feed.organisation.contact.normal.status        | feed.organisation.contact.normal.search        | check.feed.organisation.contact.normal        |
      | feed.organisation.contact.normal.modify | xml         | feed.organisation.contact.normal.modify.result | feed.organisation.contact.normal.modify.status | feed.organisation.contact.normal.modify.search | check.feed.organisation.contact.normal.modify |

  @KentGu @FeedOrganisations @T30258 @F7540 @F9068 @14.0.0 @14.1.0
  Scenario Outline: Feeds FO-SEC-01-02 verify that feed single or multiple excode to multiple system by xml
    When feed organisations <feed.organisation> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    When navigate to view organisation
    And search organisations <search.organisation>
    Then organisation should be <check.feed.organisation>
    Examples:
      | feed.organisation                                       | file.format | feed.result                                                    | feed.status                                                    | search.organisation                                            | check.feed.organisation                                       |
      | feed.organisation.external.code.normal                  | xml         | feed.organisation.external.code.normal.result                  | feed.organisation.external.code.normal.status                  | feed.organisation.external.code.normal.search                  | check.feed.organisation.external.code.normal                  |
      | feed.organisation.external.code.multiple.code           | xml         | feed.organisation.external.code.multiple.code.result           | feed.organisation.external.code.multiple.code.status           | feed.organisation.external.code.multiple.code.search           | check.feed.organisation.external.code.multiple.code           |
      | feed.organisation.external.code.existing.code           | xml         | feed.organisation.external.code.existing.code.result           | feed.organisation.external.code.existing.code.status           | feed.organisation.external.code.existing.code.search           | check.feed.organisation.external.code.existing.code           |
      | feed.organisation.external.code.existing.invalid.system | xml         | feed.organisation.external.code.existing.invalid.system.result | feed.organisation.external.code.existing.invalid.system.status | feed.organisation.external.code.existing.invalid.system.search | check.feed.organisation.external.code.existing.invalid.system |

  @KentGu @FeedOrganisations @T16846 @F1194 @F1224 @F9068 @14.1.0 @2012.1
  Scenario Outline: Feeds FOUDF-02-04 Verify that fail to feed org UDF with not defined Values to enabled UDF schema
    When navigate to organisation static data page
    And edit organisation static data <organisation.udf> to <organisation.udf.disable>
    And feed organisations <feed.organisation> by <file.format>
    Then feed log should be <feed.result>
    And feed status should be <feed.status>
    When navigate to view organisation
    And search organisations <search.organisation>
    Then organisation should be <check.feed.organisation>
    Examples:
      | organisation.udf | organisation.udf.disable | feed.organisation     | file.format | feed.result                  | feed.status                  | search.organisation          | check.feed.organisation     |
      | organisation.udf | organisation.udf.disable | feed.organisation.udf | xml         | feed.organisation.udf.result | feed.organisation.udf.status | feed.organisation.udf.search | check.feed.organisation.udf |
#      | organisation.udf | organisation.udf.disable | feed.organisation.udf | xlsx        | feed.organisation.udf.result | feed.organisation.udf.status | feed.organisation.udf.search | check.feed.organisation.udf |

  @KentGu @FeedEligibilityRuleTemplate @T24533 @F5761 @F9068 @14.1.0 @2012.3
  Scenario: Feeds FERT-01-05 Verify  successfully feed Flush mandatory data
    When feed eligibility rules templates feed.eligibility.rule.template.normal by xml
    Then feed log should be feed.eligibility.rule.template.normal.result
    And feed status should be feed.eligibility.rule.template.normal.status
    When navigate to approvals manager page
    And approvals manager - search eligibility rules template feed.eligibility.rule.template.normal.approval.manager.search
    And approvals manager - tick eligibility rules template feed.eligibility.rule.template.normal.approval.manager.search.result
    And approvals manager - approve ticked eligibility rules templates
    And navigate to eligibility rules template page
    Then eligibility rules template should be check.feed.eligibility.rule.template.normal

  @KentGu @FeedEligibilityRuleTemplate @T31900 @F10575 @14.1.0
  Scenario Outline: Feeds FERT-02-01 Verify succeed to feed eligibility rule template
    When feed eligibility rules templates <feed.eligibility.rule.template> by <file.format>
    Then feed log should be <feed.eligibility.rule.template.result>
    And feed status should be <feed.eligibility.rule.template.status>
    When navigate to approvals manager page
    And approvals manager - search eligibility rules template <feed.eligibility.rule.template.search>
    And approvals manager - tick eligibility rules template <feed.eligibility.rule.template.search.result>
    And approvals manager - approve ticked eligibility rules templates
    And navigate to eligibility rules template page
    Then eligibility rules template should be <check.feed.eligibility.rule.template>
    Examples:
      | feed.eligibility.rule.template         | file.format | feed.eligibility.rule.template.result         | feed.eligibility.rule.template.status         | feed.eligibility.rule.template.search                | feed.eligibility.rule.template.search.result                | check.feed.eligibility.rule.template         |
      | feed.not.net.eligibility.rule.template | xml         | feed.not.net.eligibility.rule.template.result | feed.not.net.eligibility.rule.template.status | feed.not.net.eligibility.rule.template.status.search | feed.not.net.eligibility.rule.template.status.search.result | check.feed.not.net.eligibility.rule.template |
      | feed.net.eligibility.rule.template     | xml         | feed.net.eligibility.rule.template.result     | feed.net.eligibility.rule.template.status     | feed.net.eligibility.rule.template.status.search     | feed.net.eligibility.rule.template.status.search.result     | check.feed.net.eligibility.rule.template     |

  @KentGu @FeedSecurityTemplates @T5668 @F9068 @14.1.0 @2011.1 @Reviewed
  Scenario Outline: Feeds FST-01-01 Verify that successful to import security file when [ASSET_CLASS] is correct
    When feed security templates <feed.security.template> by <file.format>
    Then feed log should be <feed.security.template.result>
    And feed status should be <feed.security.template.status>
    When navigate to security search page
    And search security <feed.security.template.search>
    Then security <feed.security.template.search.result> should be <check.feed.security.template>
    Examples:
      | feed.security.template                                                      | file.format | feed.security.template.result                | feed.security.template.status                | feed.security.template.search                | feed.security.template.search.result                | check.feed.security.template                |
      | feed.security.template.qtp.bond.usd,feed.security.template.qtp.bond.usd.ps1 | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      #Need refine the xlsx feed file generating function and add the rating data back to the data file
      | feed.security.template.qtp.bond.usd                                         | xlsx        | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.equity.usd                                       | xml         | feed.security.template.qtp.equity.usd.result | feed.security.template.qtp.equity.usd.status | feed.security.template.qtp.equity.usd.search | feed.security.template.qtp.equity.usd.search.result | check.feed.security.template.qtp.equity.usd |
      | feed.security.template.qtp.equity.usd                                       | xlsx        | feed.security.template.qtp.equity.usd.result | feed.security.template.qtp.equity.usd.status | feed.security.template.qtp.equity.usd.search | feed.security.template.qtp.equity.usd.search.result | check.feed.security.template.qtp.equity.usd |
      | feed.security.template.qtp.bond.usd                                         | csv         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | feed.security.template.qtp.bond.usd                                         | xml         | feed.security.template.qtp.bond.usd.result   | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |

  @KentGu @FeedAssetPricing @T6107 @F9068 @14.1.0 @2011.1 @Reviewed
  Scenario Outline: Feeds FAP-01-01 Feed Asset Pricing
    When feed asset pricings <feed.asset.pricing> by <file.format>
    Then feed log should be <feed.asset.pricing.result>
    And feed status should be <feed.asset.pricing.status>
    When navigate to security search page
    And search security <feed.asset.pricing.search>
    Then security <feed.asset.pricing.search.result> should be <check.feed.asset.pricing>
    Examples:
      | feed.asset.pricing                  | file.format | feed.asset.pricing.result                  | feed.asset.pricing.status                  | feed.asset.pricing.search                  | feed.asset.pricing.search.result                  | check.feed.asset.pricing                  |
      | feed.asset.bond.pricing             | xml         | feed.asset.bond.pricing.result             | feed.asset.bond.pricing.status             | feed.asset.bond.pricing.search             | feed.asset.bond.pricing.search.result             | check.feed.asset.bond.pricing             |
      | feed.asset.bond.pricing.not.existed | xml         | feed.asset.bond.pricing.not.existed.result | feed.asset.bond.pricing.not.existed.status | feed.asset.bond.pricing.not.existed.search | feed.asset.bond.pricing.not.existed.search.result | check.feed.asset.bond.pricing.not.existed |
      | feed.asset.bond.pricing             | xml         | feed.asset.bond.pricing.result             | feed.asset.bond.pricing.status             | feed.asset.bond.pricing.search             | feed.asset.bond.pricing.search.result             | check.feed.asset.bond.pricing             |
      | feed.asset.bond.pricing             | csv         | feed.asset.bond.pricing.result             | feed.asset.bond.pricing.status             | feed.asset.bond.pricing.search             | feed.asset.bond.pricing.search.result             | check.feed.asset.bond.pricing             |
      | feed.asset.equity.pricing           | xlsx        | feed.asset.equity.pricing.result           | feed.asset.equity.pricing.status           | feed.asset.equity.pricing.search           | feed.asset.equity.pricing.search.result           | check.feed.asset.equity.pricing           |
      | feed.asset.equity.pricing           | xlsx        | feed.asset.equity.pricing.result           | feed.asset.equity.pricing.status           | feed.asset.equity.pricing.search           | feed.asset.equity.pricing.search.result           | check.feed.asset.equity.pricing           |
      | feed.asset.bond.pricing             | xml         | feed.asset.bond.pricing.result             | feed.asset.bond.pricing.status             | feed.asset.bond.pricing.search             | feed.asset.bond.pricing.search.result             | check.feed.asset.bond.pricing             |
      #Feed failed when updating not in used instrument
#      | feed.asset.bond.pricing             | xml         | feed.asset.bond.pricing.result             | feed.asset.bond.pricing.status             | feed.asset.bond.pricing.search             | feed.asset.bond.pricing.search.result             | check.feed.asset.bond.pricing             |
      #Minimum Fields
      | feed.asset.bond.pricing             | xml         | feed.asset.bond.pricing.result             | feed.asset.bond.pricing.status             | feed.asset.bond.pricing.search             | feed.asset.bond.pricing.search.result             | check.feed.asset.bond.pricing             |
      #Missing mandatory fields
      | feed.asset.bond.pricing             | xml         | feed.asset.bond.pricing.result             | feed.asset.bond.pricing.status             | feed.asset.bond.pricing.search             | feed.asset.bond.pricing.search.result             | check.feed.asset.bond.pricing             |
      | feed.asset.bond.pricing             | xml         | feed.asset.bond.pricing.result             | feed.asset.bond.pricing.status             | feed.asset.bond.pricing.search             | feed.asset.bond.pricing.search.result             | check.feed.asset.bond.pricing             |
      | feed.asset.bond.pricing             | xml         | feed.asset.bond.pricing.result             | feed.asset.bond.pricing.status             | feed.asset.bond.pricing.search             | feed.asset.bond.pricing.search.result             | check.feed.asset.bond.pricing             |
      #Comma in the csv file
      | feed.asset.bond.pricing             | csv         | feed.asset.bond.pricing.result             | feed.asset.bond.pricing.status             | feed.asset.bond.pricing.search             | feed.asset.bond.pricing.search.result             | check.feed.asset.bond.pricing             |
      #Invalid price source
      | feed.asset.bond.pricing             | xml         | feed.asset.bond.pricing.result             | feed.asset.bond.pricing.status             | feed.asset.bond.pricing.search             | feed.asset.bond.pricing.search.result             | check.feed.asset.bond.pricing             |


  @KentGu @FeedPortfolioTSA @T25621 @F9068 @D11162 @14.1.0 @2011.2.SP3.5 @Reviewed
  Scenario Outline: Feed TF-01-11 Verify that feed with CSV file correctly support the wrapping of values that contain comma
    When feed portfolio TSA <feed.protfolio.tsa> by <file.format>
    Then feed log should be <feed.protfolio.tsa.result>
    And feed status should be <feed.protfolio.tsa.status>
    And search agreement <feed.protfolio.tsa.agreement.search>
    And view completed agreement statement <feed.protfolio.tsa.agreement.search.result>
    And select model <feed.protfolio.tsa.agreement.statement>
    Then agreement statement should be <check.feed.protfolio.tsa.agreement.statement>
    Examples:
      | feed.protfolio.tsa                                                                                                                                                                                                                                                                                                          | file.format | feed.protfolio.tsa.result | feed.protfolio.tsa.status | feed.protfolio.tsa.agreement.search | feed.protfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      | feed.tsa.upfront.fee,feed.tsa.pai,feed.tsa.transfer.value,feed.tsa.banked.coupon,feed.tsa.initial.coupon,feed.tsa.credit.event.cash.amount,feed.tsa.succession.event.cash.amount,feed.tsa.reset.to.par,feed.tsa.im.interest,feed.tsa.ndf.cashflow,feed.tsa.tsa.misc1,feed.tsa.tsa.misc2,feed.tsa.tsa.misc3,feed.tsa.tsa.bad | xml         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      | feed.tsa.upfront.fee,feed.tsa.pai,feed.tsa.transfer.value,feed.tsa.banked.coupon,feed.tsa.initial.coupon,feed.tsa.credit.event.cash.amount,feed.tsa.succession.event.cash.amount,feed.tsa.reset.to.par,feed.tsa.im.interest,feed.tsa.ndf.cashflow,feed.tsa.tsa.misc1,feed.tsa.tsa.misc2,feed.tsa.tsa.misc3                  | csv         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      | feed.tsa.upfront.fee,feed.tsa.pai,feed.tsa.transfer.value,feed.tsa.banked.coupon,feed.tsa.initial.coupon,feed.tsa.credit.event.cash.amount,feed.tsa.succession.event.cash.amount,feed.tsa.reset.to.par,feed.tsa.im.interest,feed.tsa.ndf.cashflow,feed.tsa.tsa.misc1,feed.tsa.tsa.misc2,feed.tsa.tsa.misc3                  | xlsx        | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      #Nagative case - Agreement ID and legal ID are invalid, Agreement ID, Legal ID, Component and Amount are null, Invalid Component and Amount
      | feed.tsa.upfront.fee,feed.tsa.pai,feed.tsa.transfer.value,feed.tsa.banked.coupon                                                                                                                                                                                                                                            | xml         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      #Nagative case - Verify fail to feed portofolio TSA when agreement_id and legal_id map to different agreement
      | feed.tsa.upfront.fee                                                                                                                                                                                                                                                                                                        | xml         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      #Nagative case - Verify fail to feed portofolio TSA for CCP Not-Net agreement with TSA not-ticked - Not umbrella or sub-agreement
      | feed.tsa.upfront.fee                                                                                                                                                                                                                                                                                                        | xml         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      #Nagative case - Verify fail to feed portofolio TSA for CCP Net agreement with TSA ticked - Not umbrella or sub-agreement
      | feed.tsa.upfront.fee                                                                                                                                                                                                                                                                                                        | xml         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      #Nagative case - Verify fail to feed portofolio TSA for Non-CCP agreement - Not umbrella or sub-agreement
      | feed.tsa.upfront.fee                                                                                                                                                                                                                                                                                                        | xml         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      #Nagative case - Verify fail to feed portofolio TSA for CCP Not-Net Sub-Agreement without TSA ticked
      | feed.tsa.upfront.fee                                                                                                                                                                                                                                                                                                        | xml         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      #Nagative case - Verify fail to feed portofolio TSA for CCP Net Sub-Agreement  with TSA ticked
      | feed.tsa.upfront.fee                                                                                                                                                                                                                                                                                                        | xml         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      #Nagative case - Verify fail to feed portofolio TSA for Non-CCP Sub-Agreement. Error message will be populated
      | feed.tsa.upfront.fee                                                                                                                                                                                                                                                                                                        | xml         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      #Nagative case - Verify fail to feed portofolio TSA for Umbrella FCM Agreement. Error message will be populated
      | feed.tsa.upfront.fee                                                                                                                                                                                                                                                                                                        | xml         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      #Nagative case - Feed TF-01-09 Verify logic of feed Portfolio TSA with a not set or unknown model name  for a FCM Multi-Model agreement
      | feed.tsa.upfront.fee                                                                                                                                                                                                                                                                                                        | xml         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      #Success - Verify succeed to feed portofolio TSA will revert to Agreement Base Currency when Currency is invalid
      | feed.tsa.upfront.fee,feed.tsa.pai,feed.tsa.transfer.value,feed.tsa.banked.coupon,feed.tsa.initial.coupon,feed.tsa.credit.event.cash.amount,feed.tsa.succession.event.cash.amount,feed.tsa.reset.to.par,feed.tsa.im.interest,feed.tsa.ndf.cashflow,feed.tsa.tsa.misc1,feed.tsa.tsa.misc2,feed.tsa.tsa.misc3                  | xml         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      #Success - Verify the TSA feed successful without warning message when component not included in TSA Rule
      #This case is removed by Sydney's request
#      | feed.tsa.upfront.fee                                                                                                                                                                                                                                                                                                        | xml         | feed.portfolio.tsa.result | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |

  @KentGu @FeedExternalIA @T4877 @F9068 @14.1.0 @2011.1 @Reviewed
  Scenario Outline: Feeds FEIA-EIATC-01-01  Verify that successfull to import External IA file when [AGREEMENTID] is correct
    When feed external IA <feed.external.ia> by <file.format>
    Then feed log should be <feed.external.ia.result>
    And feed status should be <feed.external.ia.status>
    And search agreement <feed.external.ia.agreement.search>
    And view completed agreement statement <feed.external.ia.agreement.search.result>
    And select model <feed.external.ia.agreement.statement>
    Then agreement statement should be <check.feed.external.ia.agreement>
    Examples:
      | feed.external.ia                                         | file.format | feed.external.ia.result              | feed.external.ia.status              | feed.external.ia.agreement.search              | feed.external.ia.agreement.search.result              | feed.external.ia.agreement.statement              | check.feed.external.ia.agreement              |
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      | feed.external.ia.principal                               | xlsx        | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      | feed.external.ia.counterparty                            | xml         | feed.external.ia.counterparty.result | feed.external.ia.counterparty.status | feed.external.ia.counterparty.agreement.search | feed.external.ia.counterparty.agreement.search.result | feed.external.ia.counterparty.agreement.statement | check.feed.external.ia.counterparty.agreement |
      | feed.external.ia.counterparty                            | xlsx        | feed.external.ia.counterparty.result | feed.external.ia.counterparty.status | feed.external.ia.counterparty.agreement.search | feed.external.ia.counterparty.agreement.search.result | feed.external.ia.counterparty.agreement.statement | check.feed.external.ia.counterparty.agreement |
      #Missing Agreement ID and Legal ID
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Missing Product and ScaleUP is false
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #This test case is to check that External IA cannot be fed into Umbrella Agreement
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify fail to feed Portfolio IA with a not set or unknown model value for Model agreement and Determined by Feed is unticked
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Invalid Agreement ID and valid external ID
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Invalid Agreement ID and invalid external ID
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify that fail to import External IA file when [legal_id] is invalid
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify that fail to import External IA file when [PRODUCT] is invalid.
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify that fail to import External IA file when [AMOUNT] is invalid
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify that successful to import External IA file when [CURRENCY] is invalid.
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify that successful to import External IA file when [externalSystem] is invalid.
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify that fail to import External IA file when [scaleUp] is invalid.
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify that successful to import  External IA file when [scaleUp] is false or null.
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify succeed to feed Portfolio IA with correct model value for Model agreement and Determined by Feed is unticked
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify succeed to feed Portfolio IA with correct model value for Model agreement and Determined by Feed is ticked
      | feed.external.ia.principal,feed.external.ia.principal1   | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify that feed  External IA file with combination of [scaleUp] is true and false.
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify failed to feed Portfolio IA with a not set or unknown model value for Model agreement and Determined by Feed is ticked
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify failed to feed Product IA with a not set or unknown model value for Model agreement and Determined by Feed is ticked
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify succeed to feed Scale Up Margin without model value for Non-model agreement
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify Feed Agreement Level IA successfully for agreement with Gross IM option for Gross Calc
      | feed.external.ia.principal,feed.external.ia.couterparty  | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify Feed Agreement Level IA successfully for agreement with Gross option for Gross Calc
      | feed.external.ia.principal,feed.external.ia.counterparty | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify that feed with CSV file correctly support the wrapping of values that contain comma
      | feed.external.ia.principal                               | csv         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #New filed payparty Applicable to all supported External IA feeds: Portfolio IA, Product  IA, Agreement IA, ScaleUp Margin
      | feed.external.ia.principal,feed.external.ia.counterparty | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
#      #New filed payparty Applicable to all supported External IA feeds: Portfolio IA, Product  IA, Agreement IA, ScaleUp Margin
#      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
#      #within the same feed file if payParty P data and PayParty C data are provided then each Party will be set individually without affecting each other
#      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify Feed different External IA successfully for umbrella agreement
      | feed.external.ia.counterparty                            | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      #Verify that feed External IA with [AMOUNT] missing.
      | feed.external.ia.principal                               | xml         | feed.external.ia.principal.result    | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |


  @KentGu @FeedAssetRating @T30304 @F9068 @D20125 @D24971 @D28209 @13.2.SP2 @13.3.SP2 @14.0.0.SP1 @14.1.0
  Scenario Outline: Feeds FST-01-01 [Market] should be accepted as null
    When feed asset ratings <feed.asset.rating> by <file.format>
    Then feed log should be <feed.asset.rating.result>
    And feed status should be <feed.asset.rating.status>
    When navigate to security search page
    And search security <feed.asset.rating.search>
    Then security <feed.asset.rating.search.result> should be <check.feed.asset.rating>
    Examples:
      | feed.asset.rating                      | file.format | feed.asset.rating.result                      | feed.asset.rating.status                      | feed.asset.rating.search                      | feed.asset.rating.search.result                      | check.feed.asset.rating                      |
      #Check the instrument Rating with new Rating filelds by Revised Rating template feed - for Approved instrument
      | feed.asset.rating.bond.usd             | csv         | feed.asset.rating.bond.usd.result             | feed.asset.rating.bond.usd.status             | feed.asset.rating.bond.usd.search             | feed.asset.rating.bond.usd.search.result             | check.feed.asset.rating.bond.usd             |
      #Check the instrument Rating with new Rating filelds by Revised Rating template feed - for Amended instrument
      | feed.asset.rating.bond.usd.with.market | xlsx        | feed.asset.rating.bond.usd.with.market.result | feed.asset.rating.bond.usd.with.market.status | feed.asset.rating.bond.usd.with.market.search | feed.asset.rating.bond.usd.with.market.search.result | check.feed.asset.rating.bond.usd.with.market |
      | feed.asset.rating.bond.usd             | xml         | feed.asset.rating.bond.usd.result             | feed.asset.rating.bond.usd.status             | feed.asset.rating.bond.usd.search             | feed.asset.rating.bond.usd.search.result             | check.feed.asset.rating.bond.usd             |
      | feed.asset.rating.bond.usd.with.market | xlsx        | feed.asset.rating.bond.usd.with.market.result | feed.asset.rating.bond.usd.with.market.status | feed.asset.rating.bond.usd.with.market.search | feed.asset.rating.bond.usd.with.market.search.result | check.feed.asset.rating.bond.usd.with.market |
      #Check the instrument Rating with null filelds by Revised Rating template feed - for Approved instrument
      | feed.asset.rating.bond.usd             | csv         | feed.asset.rating.bond.usd.result             | feed.asset.rating.bond.usd.status             | feed.asset.rating.bond.usd.search             | feed.asset.rating.bond.usd.search.result             | check.feed.asset.rating.bond.usd             |
      #Check the instrument Rating with null filelds by Revised Rating template feed - for Amended instrument
      | feed.asset.rating.bond.usd.with.market | xlsx        | feed.asset.rating.bond.usd.with.market.result | feed.asset.rating.bond.usd.with.market.status | feed.asset.rating.bond.usd.with.market.search | feed.asset.rating.bond.usd.with.market.search.result | check.feed.asset.rating.bond.usd.with.market |
      #Check the instrument Rating with NA filelds by Revised Rating template feed - for Approved instrument
      | feed.asset.rating.bond.usd             | xml         | feed.asset.rating.bond.usd.result             | feed.asset.rating.bond.usd.status             | feed.asset.rating.bond.usd.search             | feed.asset.rating.bond.usd.search.result             | check.feed.asset.rating.bond.usd             |
      #Check the instrument Rating with NA filelds by Revised Rating template feed - for Amended instrument
      | feed.asset.rating.bond.usd.with.market | xml         | feed.asset.rating.bond.usd.with.market.result | feed.asset.rating.bond.usd.with.market.status | feed.asset.rating.bond.usd.with.market.search | feed.asset.rating.bond.usd.with.market.search.result | check.feed.asset.rating.bond.usd.with.market |

  @KentGu @FeedNAV @T5185 @F9068 @14.1.0 @2011.1
  Scenario Outline: Feeds FNAV-NAVTC-01-01  Verify that successfull to import NAV file when [agreementId] is correct
    When feed NAV <feed.nav> by <file.format>
    Then feed log should be <feed.nav.result>
    And feed status should be <feed.nav.status>
    And search agreement <feed.nav.agreement.search>
    And edit agreement <feed.nav.agreement.search.result> in agreement search page
    Then agreement summary – agreement summary should be <check.feed.nav.agreement>
    Examples:
      | feed.nav                       | file.format | feed.nav.result                       | feed.nav.status                       | feed.nav.agreement.search             | feed.nav.agreement.search.result             | check.feed.nav.agreement             |
      | feed.nav.otc.net.agreement     | xml         | feed.nav.otc.net.agreement.result     | feed.nav.otc.net.agreement.status     | feed.nav.otc.net.agreement.search     | feed.nav.otc.net.agreement.search.result     | check.feed.nav.otc.net.agreement     |
      | feed.nav.otc.not.net.agreement | xlsx        | feed.nav.otc.not.net.agreement.result | feed.nav.otc.not.net.agreement.status | feed.nav.otc.not.net.agreement.search | feed.nav.otc.not.net.agreement.search.result | check.feed.nav.otc.not.net.agreement |
      | feed.nav.fcm.agreement         | xml         | feed.nav.fcm.agreement.result         | feed.nav.fcm.agreement.status         | feed.nav.fcm.agreement.search         | feed.nav.fcm.agreement.search.result         | check.feed.nav.fcm.agreement         |

  @KentGu @FeedMtM @T5328 @F5020 @F9068 @14.1.0 @2011.1 @2011.2 @2012.3
  Scenario Outline: Feeds FMTM-MTMTC-01-01 Verify that successful to import MTM with correct feedSystem
    When feed mtm <feed.mtm> by <file.format>
    Then feed log should be <feed.mtm.result>
    And feed status should be <feed.mtm.status>
    And search agreement <feed.mtm.agreement.search>
    And view completed agreement statement <feed.mtm.agreement.search.result>
    And edit summary exposure info
    And view product type <feed.mtm.trade.product.type> on exposure summary page
    And view trade <check.feed.mtm.trade> detail
    Then trade detail should be <check.feed.mtm.trade>
    Examples:
      | feed.mtm               | file.format | feed.mtm.result               | feed.mtm.status               | feed.mtm.agreement.search               | feed.mtm.agreement.search.result               | feed.mtm.trade.product.type         | check.feed.mtm.trade         |
      | feed.mtm.otc.net.trade | xml         | feed.mtm.otc.net.trade.result | feed.mtm.otc.net.trade.status | feed.mtm.otc.net.trade.agreement.search | feed.mtm.otc.net.trade.agreement.search.result | feed.mtm.otc.net.trade.product.type | check.feed.mtm.otc.net.trade |

  @KentGu @FeedOrganisations @T32761 @F11590 @14.1.0
  Scenario Outline: Feeds FO-03-03 Verify that successfully to feed Org with IM Threshold,IM Threshold Currency, Include Subsidiaries
    When feed organisations <feed.organisation> by <file.format>
    Then feed log should be <feed.organisation.result>
    And feed status should be <feed.organisation.status>
    When navigate to view organisation
    And search organisations <search.feed.organisation>
    Then organisation should be <check.feed.organisation>
    Examples:
      | feed.organisation | file.format | feed.organisation.result | feed.organisation.status | search.feed.organisation | check.feed.organisation |
      | feed.organisation | xml         | feed.organisation.result | feed.organisation.status | search.feed.organisation | check.feed.organisation |

  @KentGu @FeedCounterpartyAmount @T6108 @F9068 @14.1.0 @2011.1 @2011.2.SP3.5
  Scenario Outline: Feed FCA-01-01 Feed CP amount for Net agreement
    When feed counterparty amount <feed.counterparty.amount> by <file.format>
    Then feed log should be <feed.counterparty.amount.result>
    And feed status should be <feed.counterparty.amount.status>
    And search agreement <feed.counterparty.amount.agreement.search>
    And view completed agreement statement <feed.counterparty.amount.agreement.search.result>
    And quick link - agreement exposure management
    Then Exposure Management <exposure.event.detail> - event should be <check.exposure.event.detail>
    Examples:
      | feed.counterparty.amount | file.format | feed.counterparty.amount.result | feed.counterparty.amount.status | feed.counterparty.amount.agreement.search | feed.counterparty.amount.agreement.search.result | exposure.event.detail            | check.exposure.event.detail              |
      #Feed CP amount for Net agreement(amended and approved)
      | feed.counterparty.amount | xml         | feed.counterparty.amount.result | feed.counterparty.amount.status | feed.counterparty.amount.agreement.search | feed.counterparty.amount.agreement.search.result | orignal.feed.counterparty.amount | check.feed.counterparty.amount.agreement |

  @KentGu @FeedInterestAmount @T30638 @F8318 @F9068 @14.0.0 @14.1.0
  Scenario Outline: Feeds FIA-01-02 Verify Feed interest amount and  WTH amount successfully  via Agr ID
    When feed interest amount <feed.interest.amount> by <file.format>
    Then feed log should be <feed.interest.amount.result>
    And feed status should be <feed.interest.amount.status>
    When navigate to interest manager search page
    And Interest Manager - search interest event by <feed.interest.amount.search.event>
    And Interest Manager - switch to <interest.direction> tab
    Then Interest Manager <interest.event.detail> - event should be <check.interest.event.detail>
    Examples:
      | feed.interest.amount | file.format | feed.interest.amount.result | feed.interest.amount.status | feed.interest.amount.search.event | interest.direction | interest.event.detail      | check.interest.event.detail      |
      #Verify Feed interest amount and WTH amount successfully  via Event ID
      | feed.interest.amount | xml         | feed.interest.amount.result | feed.interest.amount.status | feed.interest.amount.search.event | Receive            | feed.interest.amount.event | check.feed.interest.amount.event |

  @KentGu @FeedAssetBookings @T28696 @F9068 @13.0.0 @14.1.0
  Scenario Outline: Feeds FABC-01-01 Verify feed Collatereal booking to non-net agreement by V13 template
    When feed asset bookings <feed.asset.booking> by <file.format>
    Then feed log should be <feed.asset.booking.result>
    And feed status should be <feed.asset.booking.status>
    And search agreement <search.feed.asset.booking.agreement>
    And view completed agreement statement <search.result.feed.asset.booking.agreement>
    Then agreement statement should be <check.feed.asset.booking.agreement.statement>
    When edit asset summary info
    Then asset holding summary should be <check.collateral.feed.asset.booking.holding.summary>
    When view asset type <feed.asset.booking.holding.summary> agreement asset <asset.type> Page
    Then asset holding detail should be <feed.asset.booking.holding.summary>
    When open asset booking editor page <feed.asset.booking.holding.summary>
    Then booking details should be <check.feed.asset.booking.detail>
    Examples:
      | feed.asset.booking      | file.format | feed.asset.booking.result | feed.asset.booking.status | search.feed.asset.booking.agreement | search.result.feed.asset.booking.agreement | check.feed.asset.booking.agreement.statement | check.collateral.feed.asset.booking.holding.summary | feed.asset.booking.holding.summary | asset.type | check.feed.asset.booking.detail |
      #Verify feed Collatereal booking to non-net agreement by V13 template
      | feed.asset.booking.cash | xml         | feed.asset.booking.result | feed.asset.booking.status | search.feed.asset.booking.agreement | search.result.feed.asset.booking.agreement | check.feed.asset.booking.agreement.statement | check.collateral.feed.asset.booking.holding.summary | feed.asset.booking.holding.summary | Bond       | check.feed.asset.booking.detail |

  @KentGu @FeedFlushTrade @T11294 @BZ_33011 @F9068 @14.1.0 @2011.1 @2011.2 @2011.2.SP3.5
  Scenario Outline: Feeds FT-01-02 Feed Flush Trade with consecutive blank columns in Excel & CSV
    When feed <feed.mode> trades <feed.trade> by <file.format>
    Then feed log should be <feed.trade.result>
    And feed status should be <feed.trade.status>
    And search agreement <search.feed.trade.agreement>
    And view completed agreement statement <search.result.feed.trade.agreement>
    And select model <feed.trade.agreement.model>
    Then agreement statement should be <check.feed.trade.agreement.statement>
    When edit summary exposure info
    Then exposure summary should be <check.feed.trade.exposure.summary>
    When view product type <check.feed.trade.exposure.summary> on exposure summary page
    Then trade summary should be <check.feed.trade.trade.summary>
    When view trade <check.feed.trade.detail> detail
    Then trade detail should be <check.feed.trade.detail>
    Examples:
      | feed.mode | feed.trade | file.format | feed.trade.result | feed.trade.status | search.feed.trade.agreement | search.result.feed.trade.agreement | feed.trade.agreement.model | check.feed.trade.agreement.statement | check.feed.trade.exposure.summary | check.feed.trade.trade.summary | check.feed.trade.detail |
      | flush     | feed.trade | xml         | feed.trade.result | feed.trade.status | search.feed.trade.agreement | search.result.feed.trade.agreement | feed.trade.agreement.model | check.feed.trade.agreement.statement | check.feed.trade.exposure.summary | check.feed.trade.trade.summary | check.feed.trade.detail |

  @KentGu @FeedRepo/ETF/SBLTrade @F1270 @F1191 @F3585 @F5020 @D21453 @F9068 @14.1.0 @2012.2 @2012.3 @2012.3.SP12.2
  Scenario Outline: Feeds FRT-01-01 Verify Repo trade succeed to feed by Flush feed - Excel
    When feed <feed.mode> <trade.type> trades <feed.trade> by <file.format>
    Then feed log should be <feed.trade.result>
    And feed status should be <feed.trade.status>
    And search agreement <search.feed.trade.agreement>
    And view completed agreement statement <search.result.feed.trade.agreement>
    Then agreement statement should be <check.feed.trade.agreement.statement>
    When edit summary exposure info
    Then exposure summary should be <check.feed.trade.exposure.summary>
    When view product type <check.feed.trade.exposure.summary> on exposure summary page
    Then trade summary should be <check.feed.trade.trade.summary>
    When view trade <check.feed.trade.detail> detail
    Then trade detail should be <check.feed.trade.detail>
    Examples:
      | feed.mode | trade.type | feed.trade      | file.format | feed.trade.result      | feed.trade.status      | search.feed.trade.agreement      | search.result.feed.trade.agreement      | check.feed.trade.agreement.statement      | check.feed.trade.exposure.summary      | check.feed.trade.trade.summary      | check.feed.trade.detail      |
      | delta     | Repo       | feed.repo.trade | xml         | feed.repo.trade.result | feed.repo.trade.status | search.feed.repo.trade.agreement | search.result.feed.repo.trade.agreement | check.feed.repo.trade.agreement.statement | check.feed.repo.trade.exposure.summary | check.feed.repo.trade.trade.summary | check.feed.repo.trade.detail |

  @KentGu @FeedAgreements @F9614 @T32748 @14.1.0
  Scenario Outline: Feeds FA-01-25 Verify OTC Agreements XML Feed to Support all the missing 76 agreement feed fields
    When feed agreements <feed.agreement> by <file.format>
    Then feed log should be <feed.agreement.result>
    And feed status should be <feed.agreement.status>
    And search agreement <search.feed.agreement.agreement>
    And edit agreement <search.result.feed.agreement.agreement> in agreement search page
    Then agreement summary – agreement summary should be <check.feed.agreement.summary>
    Examples:
      | feed.agreement                 | file.format | feed.agreement.result | feed.agreement.status | search.feed.agreement.agreement | search.result.feed.agreement.agreement | check.feed.agreement.summary                 |
      | feed.otc.multi.model.agreement | xml         | feed.agreement.result | feed.agreement.status | search.feed.agreement.agreement | search.result.feed.agreement.agreement | check.feed.otc.multi.model.agreement.summary |
      | feed.repo.agreement            | xml         | feed.agreement.result | feed.agreement.status | search.feed.agreement.agreement | search.result.feed.agreement.agreement | check.feed.repo.agreement.summary            |
      | feed.etf.agreement             | xml         | feed.agreement.result | feed.agreement.status | search.feed.agreement.agreement | search.result.feed.agreement.agreement | check.feed.etf.agreement.summary             |
      | feed.fcm.agreement             | xml         | feed.agreement.result | feed.agreement.status | search.feed.agreement.agreement | search.result.feed.agreement.agreement | check.feed.fcm.agreement.summary             |
      | feed.otc.not.net.agreement     | xml         | feed.agreement.result | feed.agreement.status | search.feed.agreement.agreement | search.result.feed.agreement.agreement | check.feed.otc.not.net.agreement.summary     |


  @JaneZhang @v2011.2.SP6;2012.3.SP9 @D11515#D16750 @FeedAssetBooking @T26988_2_feedAthorisedBooking
  Scenario Outline: Verify bookings can not be fed with authorised status when tick do not include authorised
    When navigate to collateral preferences page
    When set collateral preferences CollateralPreferences26988.DoNotIncludeAuthorised
    When feed asset bookings <feed.asset.booking> by <file.format>
    Then feed log should be <feed.asset.booking.result>
    And feed status should be <feed.asset.booking.status>

    Examples:
      | feed.asset.booking               | file.format | feed.asset.booking.result                   | feed.asset.booking.status                                                  |
      | feed.cash.Return.1m.authorised   | excel       | feed.cash.Return.1m.authorised.failToFeed   | feed.cash.Return.1m.errorMsg.settlementStatusInvalidForAuthorisedBooking   |
      | feed.bond.Return.1m.authorised   | excel       | feed.bond.Return.1m.authorised.failToFeed   | feed.bond.Return.1m.errorMsg.settlementStatusInvalidForAuthorisedBooking   |
      | feed.equity.Return.1m.authorised | excel       | feed.equity.Return.1m.authorised.failToFeed | feed.equity.Return.1m.errorMsg.settlementStatusInvalidForAuthorisedBooking |
