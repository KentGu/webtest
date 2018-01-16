@TaskScheduler @regression
Feature: TaskScheduler

  Background:
    Given have login with credential login.credential.test1
#    And have collateral preferences collateral.preference1
#    And have default stp configuration
#    And have default organisation global preferences

  @KentGu @FeedAgreement @TaskManager @T28216 @F7895 @13.0.0
  Scenario Outline: Feeds FA-CS-01-13 Verify only check VM call frequency field when update NotNet to Net
#    Given have OTC agreement <otc.notnet.agr1>,<otc.notnet.agr2>,<otc.notnet.agr3>,<otc.notnet.agr4>
    When navigate to task scheduler page
    And edit task scheduler <task.feed.agreement>
    And copy agreement feed <file.format> file <feed.agr1>,<feed.agr2>,<feed.agr3>,<feed.agr4> for <task.feed.agreement> to <file.name>
    And run task scheduler <task.feed.agreement>
    Then task scheduler messaging should be <imported.2.agreements>
    And feed status should be <feed.status.failed>
    And search agreement <otc.notnet.agr1.search>
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
      | task.feed.agreement | file.format | feed.agr1 | feed.agr2 | feed.agr3 | feed.agr4 | file.name | imported.2.agreements | feed.status.failed | otc.notnet.agr1.search | otc.notnet.agr1.search.result | otc.notnet.agr1.summary | otc.notnet.agr2.search | otc.notnet.agr2.search.result | otc.notnet.agr2.summary | otc.notnet.agr3.search | otc.notnet.agr3.search.result | otc.agr3.summary        | otc.notnet.agr4.search | otc.notnet.agr4.search.result | otc.notnet.agr4.summary |
      | task.feed.agreement | xml         | feed.agr1 | feed.agr2 | feed.agr3 | feed.agr4 | file.name | imported.2.agreements | feed.status.failed | otc.notnet.agr1.search | otc.notnet.agr1.search.result | otc.notnet.agr1.summary | otc.notnet.agr2.search | otc.notnet.agr2.search.result | otc.notnet.agr2.summary | otc.notnet.agr3.search | otc.notnet.agr3.search.result | otc.notnet.agr3.summary | otc.notnet.agr4.search | otc.notnet.agr4.search.result | otc.notnet.agr4.summary |
#      | task.feed.agreement | xlsx        | feed.agr1 | feed.agr2 | feed.agr3 | feed.agr4 | file.name | imported.2.agreements | feed.status.failed | otc.notnet.agr1.search | otc.notnet.agr1.search.result | otc.notnet.agr1.summary | otc.notnet.agr2.search | otc.notnet.agr2.search.result | otc.notnet.agr2.summary | otc.notnet.agr3.search | otc.notnet.agr3.search.result | otc.notnet.agr3.summary | otc.notnet.agr4.search | otc.notnet.agr4.search.result | otc.notnet.agr4.summary |


  @KentGu @FeedFxRate @TaskManager @T4800 @F9068 @14.1.0
  Scenario Outline: Feeds FFXR-FXRTC-01-01 Verify that successfully to import FX Rates file when [fxRate] is correct
    Given have FX rate sets <base.fx.rate.set>
    When navigate to task scheduler page
    And edit task scheduler <task.feed.fx>
    And copy FX rate feed <file.format> file <feed.fx.rate.bid.222> for <task.feed.fx> to file
    And run task scheduler <task.feed.fx>
    And navigate to FX rate page
    Then fx rate set should be <check.fx.rate.bid.222>
    Examples:
      | base.fx.rate.set | task.feed.fx | feed.fx.rate.bid.222 | file.format | check.fx.rate.bid.222 |
      | base.fx.rate.set | task.feed.fx | feed.fx.rate.bid.222 | xml         | check.fx.rate.bid.222 |
      | base.fx.rate.set | task.feed.fx | feed.fx.rate.bid.222 | xlsx        | check.fx.rate.bid.222 |

  @KentGu @FeedInterestRates @TaskManager @T4592 @F5020 @F9068 @14.1.0
  Scenario Outline: Feeds FIR-01-01 Feeds interest rates with correct fields
    When navigate to interest rates page
    And add Interest Rates <base.daily.interest.rate>
    And add <agreement.type> agreements <otc.agreement>
    And view statement
    And edit asset summary info
    And view asset type <asset.qtp.cash.usd> agreement asset <cash> Page
    And add call bookings - statement booking <booking.2m.call>
    And edit call booking <asset.qtp.cash.usd> to <booking.2m.call.approved>
    And quick link - view statement
    And approve agreement approve.agreement
    And navigate to task scheduler page
    And edit task scheduler <task.feed.interestrate>
    And copy interest rate feed <file.format> file <feed.interest.rate.amount.6.77> for <task.feed.interestrate> to <file>
    And run task scheduler <task.feed.interestrate>
    Then task scheduler messaging should be <imported.1.interest.rates>
    Examples:
      | base.daily.interest.rate | agreement.type | otc.agreement | asset.qtp.cash.usd | cash | booking.2m.call | booking.2m.call.approved | task.feed.interestrate | file.format | feed.interest.rate.amount.6.77 | file | imported.1.interest.rates |
      | base.daily.interest.rate | OTC            | otc.agr       | asset.qtp.cash.usd | CASH | booking.2m.call | booking.2m.call.approved | task.feed.interestrate | xml         | feed.interest.rate.amount.6.77 | file | imported.1.interest.rates |
#      | base.daily.interest.rate | OTC            | otc.agr       | asset.qtp.cash.usd | CASH | booking.2m.call | booking.2m.call.approved | task.feed.interestrate | xlsx        | feed.interest.rate.amount.6.77 | file | imported.1.interest.rates |

  @KentGu @FeedAgreementUDF @TaskManager @T3696 @F5020 @F9068  @14.1.0
  Scenario Outline:  Feeds FAUDF-01-01 Verify that successful to feed agreement UDF with all correct fields in template
    Given have <agreement.type> agreements <otc.agreement>
    And have collateral static data <col.static.data.d1>,<col.static.data.d2>,<col.static.data.d3>,<col.static.data.d4>
    When navigate to collateral static data page
    And edit collateral static data <col.static.data.d3> to <col.static.data.disable>
    And navigate to task scheduler page
    And edit task scheduler <task.feed.agreement.udf>
    And copy agreement UDF feed <file.format> file <feed.agrudf.1> for <task.feed.agreement.udf> to <feed.agrudf.1.file>
    And run task scheduler <task.feed.agreement.udf>
    And search agreement <search.otc.agr>
    And edit agreement <search.result.otc.agr> in agreement search page
    Then agreement summary – additional fields should be <otc.agr.after.first.feed.agrudf>
    When navigate to task scheduler page
    And edit task scheduler <task.feed.agreement.udf>
    And copy agreement UDF feed <file.format> file feed.agrudf.2 for <task.feed.agreement.udf> to <feed.agrudf.2.file>
    And run task scheduler <task.feed.agreement.udf>
    Then task scheduler messaging should be <imported.0.agreement.udfs>
    When navigate to collateral static data page
    And edit collateral static data <col.static.data.d1> to <col.static.data.disable>
    And edit collateral static data <col.static.data.d2> to <col.static.data.disable>
    And edit collateral static data <col.static.data.d3> to <col.static.data.disable>
    And edit collateral static data <col.static.data.d4> to <col.static.data.disable>
    Examples:
      | agreement.type | otc.agreement | col.static.data.d1 | col.static.data.d2 | col.static.data.d3 | col.static.data.d4 | col.static.data.disable | task.feed.agreement.udf | file.format | feed.agrudf.1 | feed.agrudf.1.file | search.otc.agr | search.result.otc.agr | otc.agr.after.first.feed.agrudf | feed.agrudf.2.file | imported.0.agreement.udfs |
      | OTC            | otc.agr       | col.static.data.d1 | col.static.data.d2 | col.static.data.d3 | col.static.data.d4 | col.static.data.disable | task.feed.agreement.udf | xml         | feed.agrudf.1 | feed.agrudf.1.file | search.otc.agr | search.result.otc.agr | otc.agr.after.first.feed.agrudf | feed.agrudf.2.file | imported.0.agreement.udfs |
#      | OTC            | otc.agr       | col.static.data.d1 | col.static.data.d2 | col.static.data.d3 | col.static.data.d4 | col.static.data.disable | task.feed.agreement.udf | xlsx        | feed.agrudf.1 | feed.agrudf.1.file | search.otc.agr | search.result.otc.agr | otc.agr.after.first.feed.agrudf | feed.agrudf.2.file | imported.0.agreement.udfs |

  @KentGu @FeedAgreementRatings @TaskManager @T3688 @F5020 @F9068 @14.1.0
  Scenario Outline: Feeds FAR-01-01 Verify that successful to feed agreement ratings with all correct fields in template
    Given have <agreement.type> agreements <otc.agr.rating.trigger>,<otc.agr.non.rating.trigger>
    When navigate to task scheduler page
    And edit task scheduler <task.feed.agreement.rating>
    And copy Agreement Rating feed <file.format> file <feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty> for <task.feed.agreement.rating> to <file>
    And run task scheduler <task.feed.agreement.rating>
    Then task scheduler messaging should be <imported.1.agreement.ratings>
    And search agreement <otc.agr.rating.trigger.search>
    And edit agreement <otc.agr.rating.trigger.search.result> in agreement search page
    Then agreement summary – agreement summary should be <otc.agr.rating.trigger.summary>
    Examples:
      | agreement.type | otc.agr.rating.trigger | otc.agr.non.rating.trigger | task.feed.agreement.rating | file.format | feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty | file | imported.1.agreement.ratings | otc.agr.rating.trigger.search | otc.agr.rating.trigger.search.result | otc.agr.rating.trigger.summary |
      | OTC            | otc.agr.rating.trigger | otc.agr.non.rating.trigger | task.feed.agreement.rating | xml         | feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty | file | imported.1.agreement.ratings | otc.agr.rating.trigger.search | otc.agr.rating.trigger.search.result | otc.agr.rating.trigger.summary |
#      | OTC            | otc.agr.rating.trigger | otc.agr.non.rating.trigger | task.feed.agreement.rating | xlsx        | feed.otc.agr.rating.trigger.p.fitch.invalid.counterparty | file | imported.1.agreement.ratings | otc.agr.rating.trigger.search | otc.agr.rating.trigger.search.result | otc.agr.rating.trigger.summary |

  @KentGu @FeedAgreement @TaskManager @T32496 @F11281 @14.1.0
  Scenario Outline: Feeds FA-C-01-72 Verify that successful to feed agreement with correct [Ultimate Parent Correlation] and [Close Link Correlation]
    When navigate to task scheduler page
    And edit task scheduler <task.feed.agreement>
    And copy agreement feed <file.format> file <feed.otc.agreement.ultimate.parent.true.close.link.false> for <task.feed.agreement> to <file>
    And run task scheduler <task.feed.agreement>
    Then task scheduler messaging should be <task.feed.agreement.message>
    And feed status should be <feed.otc.agreement.ultimate.parent.true.close.link.false.status.successful.1>
    And search agreement <otc.agreement.ultimate.parent.true.close.link.false.search>
    And edit agreement <otc.agreement.ultimate.parent.true.close.link.false.search.result> in agreement search page
    And edit <agreement.type> agreement to <otc.agreement.settlement.tab>
    Then agreement summary – agreement summary should be <otc.agreement.ultimate.parent.true.close.link.false.summary>
    Examples:
      | task.feed.agreement | file.format | feed.otc.agreement.ultimate.parent.true.close.link.false | file | task.feed.agreement.message | feed.otc.agreement.ultimate.parent.true.close.link.false.status.successful.1 | otc.agreement.ultimate.parent.true.close.link.false.search | otc.agreement.ultimate.parent.true.close.link.false.search.result | agreement.type | otc.agreement.settlement.tab | otc.agreement.ultimate.parent.true.close.link.false.summary |
      | task.feed.agreement | xml         | feed.otc.agreement.ultimate.parent.true.close.link.false | file | task.feed.agreement.message | feed.otc.agreement.ultimate.parent.true.close.link.false.status.successful.1 | otc.agreement.ultimate.parent.true.close.link.false.search | otc.agreement.ultimate.parent.true.close.link.false.search.result | OTC            | otc.agreement.settlement.tab | otc.agreement.ultimate.parent.true.close.link.false.summary |
#      | task.feed.agreement | xlsx        | feed.otc.agreement.ultimate.parent.true.close.link.false | file | task.feed.agreement.message | feed.otc.agreement.ultimate.parent.true.close.link.false.status.successful.1 | otc.agreement.ultimate.parent.true.close.link.false.search | otc.agreement.ultimate.parent.true.close.link.false.search.result | OTC            | otc.agreement.settlement.tab | otc.agreement.ultimate.parent.true.close.link.false.summary |

  @KentGu @FeedAgreement @TaskManager @T32603 @F11270 @14.1.0
  Scenario Outline: Feeds FA-C-01-80 Verify feed agreement haircut adjustment rules succeed with valid values_Bond
    Given have securities <bond.usd.price.100.1>,<bond.usd.price.100.2>,<bond.gbp.price.100>,<equity.usd.price.1.1>,<equity.usd.price.1.2>,<equity.gbp.price.1>
    When navigate to task scheduler page
    And edit task scheduler <task.feed.agreement>
    And copy agreement feed <file.format> file <feed.otc.net.agreement.haircut.rule.on.bond.usd> for <task.feed.agreement> to <file1>
    And run task scheduler <task.feed.agreement>
    Then task scheduler messaging should be <task.feed.agreement.message>
    And feed status should be <feed.otc.net.agreement.haircut.rule.on.bond.usd.status>
    And search agreement <otc.agreement.ultimate.parent.true.close.link.false.search>
    And edit agreement <otc.agreement.ultimate.parent.true.close.link.false.search.result> in agreement search page
    And edit <agreement.type> agreement to <otc.net.agreement.complete>
    Then agreement summary – agreement summary should be <otc.net.agreement.haircut.rule.on.bond.usd.summary>
    When navigate to task scheduler page
    And edit task scheduler <task.feed.agreement>
    And copy agreement feed <file.format> file <feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule> for <task.feed.agreement> to <file2>
    And run task scheduler <task.feed.agreement>
    Then task scheduler messaging should be <task.feed.agreement.message>
    And feed status should be <feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.status>
    And search agreement <otc.agreement.ultimate.parent.true.close.link.false.search>
    And edit agreement <otc.agreement.ultimate.parent.true.close.link.false.search.result> in agreement search page
    And edit <agreement.type> agreement to <otc.net.agreement.complete>
    Then agreement summary – agreement summary should be <otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.summary>
    Examples:
      | bond.usd.price.100.1 | bond.usd.price.100.2 | bond.gbp.price.100 | equity.usd.price.1.1 | equity.usd.price.1.2 | equity.gbp.price.1 | task.feed.agreement | file.format | feed.otc.net.agreement.haircut.rule.on.bond.usd | file1 | task.feed.agreement.message | feed.otc.net.agreement.haircut.rule.on.bond.usd.status | otc.agreement.ultimate.parent.true.close.link.false.search | otc.agreement.ultimate.parent.true.close.link.false.search.result | agreement.type | otc.net.agreement.complete | otc.net.agreement.haircut.rule.on.bond.usd.summary | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule | file2 | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.status | otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.summary |
      | bond.usd.price.100.1 | bond.usd.price.100.2 | bond.gbp.price.100 | equity.usd.price.1.1 | equity.usd.price.1.2 | equity.gbp.price.1 | task.feed.agreement | xml         | feed.otc.net.agreement.haircut.rule.on.bond.usd | file1 | task.feed.agreement.message | feed.otc.net.agreement.haircut.rule.on.bond.usd.status | otc.agreement.ultimate.parent.true.close.link.false.search | otc.agreement.ultimate.parent.true.close.link.false.search.result | OTC            | otc.net.agreement.complete | otc.net.agreement.haircut.rule.on.bond.usd.summary | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule | file2 | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.status | otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.summary |
#      | bond.usd.price.100.1 | bond.usd.price.100.2 | bond.gbp.price.100 | equity.usd.price.1.1 | equity.usd.price.1.2 | equity.gbp.price.1 | task.feed.agreement | xlsx        | feed.otc.net.agreement.haircut.rule.on.bond.usd | file1 | task.feed.agreement.message | feed.otc.net.agreement.haircut.rule.on.bond.usd.status | otc.agreement.ultimate.parent.true.close.link.false.search | otc.agreement.ultimate.parent.true.close.link.false.search.result | OTC            | otc.net.agreement.complete | otc.net.agreement.haircut.rule.on.bond.usd.summary | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule | file2 | feed.otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.status | otc.net.agreement.haircut.rule.on.bond.usd.edit.rule.summary |

  @KentGu @FeedAgreement @TaskManager @T32997 @F11529 @14.1.0
  Scenario Outline: Feeds FA-C-01-92 Verify feed agreement  Template vs Agreement Eligibility Rules succeed
    When navigate to task scheduler page
    And edit task scheduler task.feed.agreement
    And copy agreement feed <file.format> file <feed.agreement> for task.feed.agreement to file
    And run task scheduler task.feed.agreement
    Then task scheduler messaging should be task.feed.agreement.message
    And feed status should be <feed.status>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    And edit OTC agreement to agreement.complete
    Then agreement summary – agreement summary should be <agreement.summary>
    Examples:
      | feed.agreement                                   | file.format | feed.status                                             | agreement.search                                        | agreement.search.result                                        | agreement.summary                                        |
      | feed.net.otc.agreement.eligibility.rule          | xml         | feed.net.otc.agreement.eligibility.rule.status          | feed.net.otc.agreement.eligibility.rule.search          | feed.net.otc.agreement.eligibility.rule.search.result          | feed.net.otc.agreement.eligibility.rule.summary          |
      | feed.net.otc.agreement.eligibility.rule.pc       | xml         | feed.net.otc.agreement.eligibility.rule.pc.status       | feed.net.otc.agreement.eligibility.rule.pc.search       | feed.net.otc.agreement.eligibility.rule.pc.search.result       | feed.net.otc.agreement.eligibility.rule.pc.summary       |
      | feed.net.otc.agreement.eligibility.rule.pc1      | xml         | feed.net.otc.agreement.eligibility.rule.pc1.status      | feed.net.otc.agreement.eligibility.rule.pc1.search      | feed.net.otc.agreement.eligibility.rule.pc1.search.result      | feed.net.otc.agreement.eligibility.rule.pc1.summary      |
      | feed.net.otc.agreement.eligibility.rule.new.bond | xml         | feed.net.otc.agreement.eligibility.rule.new.bond.status | feed.net.otc.agreement.eligibility.rule.new.bond.search | feed.net.otc.agreement.eligibility.rule.new.bond.search.result | feed.net.otc.agreement.eligibility.rule.new.bond.summary |
      | feed.non.net.otc.agreement.eligibility.rule      | xml         | feed.non.net.otc.agreement.eligibility.rule.status      | feed.non.net.otc.agreement.eligibility.rule.search      | feed.non.net.otc.agreement.eligibility.rule.search.result      | feed.non.net.otc.agreement.eligibility.rule.summary      |

  @KentGu @FeedAgreement @TaskManager @T33065 @F11809 @14.1.0
  Scenario Outline: Feeds FA-C-01-93 Verify Ultimate Parent Holding CL check_Agreement feed
    Given have organisations <organisation>
    When navigate to task scheduler page
    And edit task scheduler <task.feed.agreement>
    And copy agreement feed <file.format> file <feed.agreement> for <task.feed.agreement> to <file>
    And run task scheduler <task.feed.agreement>
    Then task scheduler messaging should be <task.feed.agreement.message>
    And feed status should be <feed.status>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    And edit <agreement.type> agreement to <agreement.edit>
    Then agreement summary – agreement summary should be <agreement.summary>
    And asset rule in agreement edit page should be <agreement.asset.rule>
    Examples:
      | organisation        | task.feed.agreement | file.format | file | feed.agreement                                        | task.feed.agreement.message | feed.status                                                  | agreement.search                                             | agreement.search.result                                             | agreement.type | agreement.edit     | agreement.summary                                             | agreement.asset.rule                                   |
      | ultimate.parent.org | task.feed.agreement | xml         | file | feed.otc.agreement.concentration.rules.max.percentage | task.feed.agreement.message | feed.otc.agreement.concentration.rules.max.percentage.status | feed.otc.agreement.concentration.rules.max.percentage.search | feed.otc.agreement.concentration.rules.max.percentage.search.result | OTC            | agreement.complete | feed.otc.agreement.concentration.rules.max.percentage.summary | check.otc.agreement.concentration.rules.max.percentage |

  @KentGu @FeedAgreement @TaskManager @T32586 @F11270 @14.1.0
  Scenario Outline: Feeds FA-D-01-47 Verify agreement feed Termination and Transfer currency with valid value can set or update succeed
    When navigate to task scheduler page
    And edit task scheduler <task.feed.agreement>
    And copy agreement feed <file.format> file <feed.agreement> for <task.feed.agreement> to <file>
    And run task scheduler <task.feed.agreement>
    Then task scheduler messaging should be <task.feed.agreement.message>
    And feed status should be <feed.status>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    And edit <agreement.type> agreement to <agreement.edit>
    Then agreement summary – agreement summary should be <agreement.summary>
    Examples:
      | task.feed.agreement | file.format | feed.agreement                                                            | file | task.feed.agreement.message | feed.status                                                                      | agreement.search                                                                 | agreement.search.result                                                                 | agreement.type | agreement.edit     | agreement.summary                                                                 |
      | task.feed.agreement | xml         | feed.etf.agreement.principal.transfer.ccy.usd.counterparty.transfer.usd   | file | task.feed.agreement.message | feed.etf.agreement.principal.transfer.ccy.usd.counterparty.transfer.usd.status   | feed.etf.agreement.principal.transfer.ccy.usd.counterparty.transfer.usd.search   | feed.etf.agreement.principal.transfer.ccy.usd.counterparty.transfer.usd.search.result   | ETF            | agreement.complete | feed.etf.agreement.principal.transfer.ccy.usd.counterparty.transfer.usd.summary   |
      | task.feed.agreement | xml         | feed.etf.agreement.principal.transfer.ccy.gbp.counterparty.transfer.blank | file | task.feed.agreement.message | feed.etf.agreement.principal.transfer.ccy.gbp.counterparty.transfer.blank.status | feed.etf.agreement.principal.transfer.ccy.gbp.counterparty.transfer.blank.search | feed.etf.agreement.principal.transfer.ccy.gbp.counterparty.transfer.blank.search.result | ETF            | agreement.complete | feed.etf.agreement.principal.transfer.ccy.gbp.counterparty.transfer.blank.summary |
      | task.feed.agreement | xml         | feed.fcm.agreement.principal.transfer.ccy.usd.counterparty.transfer.gbp   | file | task.feed.agreement.message | feed.fcm.agreement.principal.transfer.ccy.usd.counterparty.transfer.gbp.status   | feed.fcm.agreement.principal.transfer.ccy.usd.counterparty.transfer.gbp.search   | feed.fcm.agreement.principal.transfer.ccy.usd.counterparty.transfer.gbp.search.result   | FCM            | agreement.complete | feed.fcm.agreement.principal.transfer.ccy.usd.counterparty.transfer.gbp.summary   |
      | task.feed.agreement | xml         | feed.fcm.agreement.principal.transfer.ccy.gbp.counterparty.transfer.usd   | file | task.feed.agreement.message | feed.fcm.agreement.principal.transfer.ccy.gbp.counterparty.transfer.usd.status   | feed.fcm.agreement.principal.transfer.ccy.gbp.counterparty.transfer.usd.search   | feed.fcm.agreement.principal.transfer.ccy.gbp.counterparty.transfer.usd.search.result   | FCM            | agreement.complete | feed.fcm.agreement.principal.transfer.ccy.gbp.counterparty.transfer.usd.summary   |

  @KentGu @FeedAgreement @TaskManager @T32804 @F11590 @F13083 @14.1.0 @14.1.0.SP3
  Scenario Outline: Feeds FA-FT-02-20 Verify that feed agreements for Organisation Threshold check
    Given have organisations <organisation.principal.im.threshold.3000>,<organisation.principal.im.threshold.2000>,<organisation.principal.im.threshold.1000>,<organisation.counterparty.1>,<organisation.counterparty.2>
    When navigate to task scheduler page
    And edit task scheduler <task.feed.agreement>
    And copy agreement feed <file.format> file <feed.agreement> for <task.feed.agreement> to <file>
    And run task scheduler <task.feed.agreement>
    Then task scheduler messaging should be <task.feed.agreement.message>
    And feed status should be <feed.status>
    And search agreement <agreement.search>
    And edit agreement <agreement.search.result> in agreement search page
    And edit <agreement.type> agreement to <agreement.edit>
    Then agreement summary – agreement summary should be <agreement.summary>
    Examples:
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.agreement                                                           | file.format | feed.status                                                                     | agreement.search                                                                | agreement.search.result                                                                | agreement.type | agreement.edit     | agreement.summary                                                                |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger                                   | xml         | feed.otc.not.net.agreement.fix.trigger.status                                   | feed.otc.not.net.agreement.fix.trigger.search                                   | feed.otc.not.net.agreement.fix.trigger.search.result                                   | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.summary                                   |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger                                           | xml         | feed.fcm.agreement.fix.trigger.status                                           | feed.fcm.agreement.fix.trigger.search                                           | feed.fcm.agreement.fix.trigger.search.result                                           | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.summary                                           |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.principal.2                       | xml         | feed.otc.not.net.agreement.fix.trigger.principal.2.status                       | feed.otc.not.net.agreement.fix.trigger.principal.2.search                       | feed.otc.not.net.agreement.fix.trigger.principal.2.search.result                       | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.principal.2.summary                       |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.principal.2                               | xml         | feed.fcm.agreement.fix.trigger.principal.2.status                               | feed.fcm.agreement.fix.trigger.principal.2.search                               | feed.fcm.agreement.fix.trigger.principal.2.search.result                               | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.principal.2.summary                               |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.principal.3                       | xml         | feed.otc.not.net.agreement.fix.trigger.principal.3.status                       | feed.otc.not.net.agreement.fix.trigger.principal.3.search                       | feed.otc.not.net.agreement.fix.trigger.principal.3.search.result                       | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.principal.3.summary                       |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.principal.3                               | xml         | feed.fcm.agreement.fix.trigger.principal.3.status                               | feed.fcm.agreement.fix.trigger.principal.3.search                               | feed.fcm.agreement.fix.trigger.principal.3.search.result                               | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.principal.3.summary                               |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.counterparty.2                    | xml         | feed.otc.not.net.agreement.fix.trigger.counterparty.2.status                    | feed.otc.not.net.agreement.fix.trigger.counterparty.2.search                    | feed.otc.not.net.agreement.fix.trigger.counterparty.2.search.result                    | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.counterparty.2.summary                    |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.counterparty.2                            | xml         | feed.fcm.agreement.fix.trigger.counterparty.2.status                            | feed.fcm.agreement.fix.trigger.counterparty.2.search                            | feed.fcm.agreement.fix.trigger.counterparty.2.search.result                            | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.counterparty.2.summary                            |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.im.threshold.300                  | xml         | feed.otc.not.net.agreement.fix.trigger.im.threshold.300.status                  | feed.otc.not.net.agreement.fix.trigger.im.threshold.300.search                  | feed.otc.not.net.agreement.fix.trigger.im.threshold.300.search.result                  | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.im.threshold.300.summary                  |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.im.threshold.400                          | xml         | feed.fcm.agreement.fix.trigger.im.threshold.400.status                          | feed.fcm.agreement.fix.trigger.im.threshold.400.search                          | feed.fcm.agreement.fix.trigger.im.threshold.400.search.result                          | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.im.threshold.400.summary                          |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.principal.2.im.threshold.500      | xml         | feed.otc.not.net.agreement.fix.trigger.principal.2.im.threshold.500.status      | feed.otc.not.net.agreement.fix.trigger.principal.2.im.threshold.500.search      | feed.otc.not.net.agreement.fix.trigger.principal.2.im.threshold.500.search.result      | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.principal.2.im.threshold.500.summary      |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.principal.2.im.threshold.600              | xml         | feed.fcm.agreement.fix.trigger.principal.2.im.threshold.600.status              | feed.fcm.agreement.fix.trigger.principal.2.im.threshold.600.search              | feed.fcm.agreement.fix.trigger.principal.2.im.threshold.600.search.result              | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.principal.2.im.threshold.600.summary              |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.principal.3.im.threshold.1200     | xml         | feed.otc.not.net.agreement.fix.trigger.principal.3.im.threshold.1200.status     | feed.otc.not.net.agreement.fix.trigger.principal.3.im.threshold.1200.search     | feed.otc.not.net.agreement.fix.trigger.principal.3.im.threshold.1200.search.result     | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.principal.3.im.threshold.1200.summary     |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.principal.3.im.threshold.500              | xml         | feed.fcm.agreement.fix.trigger.principal.3.im.threshold.500.status              | feed.fcm.agreement.fix.trigger.principal.3.im.threshold.500.search              | feed.fcm.agreement.fix.trigger.principal.3.im.threshold.500.search.result              | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.principal.3.im.threshold.500.summary              |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.counterparty.2.im.threshold.600   | xml         | feed.otc.not.net.agreement.fix.trigger.counterparty.2.im.threshold.600.status   | feed.otc.not.net.agreement.fix.trigger.counterparty.2.im.threshold.600.search   | feed.otc.not.net.agreement.fix.trigger.counterparty.2.im.threshold.600.search.result   | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.counterparty.2.im.threshold.600.summary   |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.counterparty.2.im.threshold.800           | xml         | feed.fcm.agreement.fix.trigger.counterparty.2.im.threshold.800.status           | feed.fcm.agreement.fix.trigger.counterparty.2.im.threshold.800.search           | feed.fcm.agreement.fix.trigger.counterparty.2.im.threshold.800.search.result           | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.counterparty.2.im.threshold.800.summary           |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.principal.2.cad.im.threshold.1000 | xml         | feed.otc.not.net.agreement.fix.trigger.principal.2.cad.im.threshold.1000.status | feed.otc.not.net.agreement.fix.trigger.principal.2.cad.im.threshold.1000.search | feed.otc.not.net.agreement.fix.trigger.principal.2.cad.im.threshold.1000.search.result | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.principal.2.cad.im.threshold.1000.summary |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.fcm.agreement.fix.trigger.principal.2.cad.im.threshold.1100         | xml         | feed.fcm.agreement.fix.trigger.principal.2.cad.im.threshold.1100.status         | feed.fcm.agreement.fix.trigger.principal.2.cad.im.threshold.1100.search         | feed.fcm.agreement.fix.trigger.principal.2.cad.im.threshold.1100.search.result         | FCM            | agreement.complete | feed.fcm.agreement.fix.trigger.principal.2.cad.im.threshold.1100.summary         |
      | task.feed.agreement | file | task.feed.agreement.message | organisation.principal.im.threshold.3000 | organisation.principal.im.threshold.2000 | organisation.principal.im.threshold.1000 | organisation.counterparty.1 | organisation.counterparty.2 | feed.otc.not.net.agreement.fix.trigger.principal.3.cad.im.threshold.1200 | xml         | feed.otc.not.net.agreement.fix.trigger.principal.3.cad.im.threshold.1200.status | feed.otc.not.net.agreement.fix.trigger.principal.3.cad.im.threshold.1200.search | feed.otc.not.net.agreement.fix.trigger.principal.3.cad.im.threshold.1200.search.result | OTC            | agreement.complete | feed.otc.not.net.agreement.fix.trigger.principal.3.cad.im.threshold.1200.summary |

  @KentGu @FeedAgreement @TaskManager @T2101 @F9068 @BZ_33011 @14.1.0 @2010.1.SP5 @wip
  Scenario: Feeds FA-01-02 Feed Agreements with consecutive blank columns in Excel
    When navigate to task scheduler page
    And edit task scheduler task.feed.agreement
    And copy agreement feed xlsx file feed.otc.agreement.grace.period.0.double.principal for task.feed.agreement to feed.file
    And run task scheduler task.feed.agreement
    Then task scheduler messaging should be task.feed.agreement.failed.message

  @KentGu @FeedStaticData @TaskManager @T27841 @F9068 @D13645 @D13989 @14.1.0 @2012.2.SP2.6.1 @2012.3.2.SP1
  Scenario: Feeds FSD-01-27 feed static data by xlsx format
    When navigate to task scheduler page
    And edit task scheduler task.feed.otc.product
    And copy static data feed xlsx file feed.otc.product for task.feed.otc.product to file.path
    And run task scheduler task.feed.otc.product
    Then task scheduler messaging should be task.feed.otc.product.message
    And feed status should be feed.success.log
    When navigate to collateral static data page
    Then collateral static data should be check.otc.product

  @KentGu @FeedOrganisations @TaskManager @T26091 @F7052 @F9068 @14.1.0 @13.0.0
  Scenario Outline: Feeds FO-01-36 Verify that successful to feed org with new filed LEI by XML
    When navigate to task scheduler page
    And edit task scheduler <task.feed.organisation>
    And copy organisation feed <file.format> file <feed.organisation> for <task.feed.organisation> to <file>
    And run task scheduler <task.feed.organisation>
    Then task scheduler messaging should be <task.feed.organisation.message>
    Then feed status should be <feed.status>
    When navigate to view organisation
    And search organisations <search.organisation>
    Then organisation should be <check.feed.organisation>
    Examples:
      | task.feed.organisation | file      | task.feed.organisation.message | feed.organisation                  | file.format | feed.status                               | search.organisation                       | check.feed.organisation                  |
      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.normal           | xml         | feed.organisation.normal.status           | feed.organisation.normal.search           | check.feed.organisation.normal           |
      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.long.lei         | xml         | feed.organisation.long.lei.status         | feed.organisation.long.lei.search         | check.feed.organisation.long.lei         |
      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.normal.modify    | xml         | feed.organisation.normal.modify.status    | feed.organisation.normal.modify.search    | check.feed.organisation.normal.modify    |
      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.normal.empty.lei | xml         | feed.organisation.normal.empty.lei.status | feed.organisation.normal.empty.lei.search | check.feed.organisation.normal.empty.lei |
#      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.normal           | excel       | feed.organisation.normal.status           | feed.organisation.normal.search           | check.feed.organisation.normal           |
#      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.long.lei         | excel       | feed.organisation.long.lei.status         | feed.organisation.long.lei.search         | check.feed.organisation.long.lei         |
#      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.normal.modify    | excel       | feed.organisation.normal.modify.status    | feed.organisation.normal.modify.search    | check.feed.organisation.normal.modify    |
#      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.normal.empty.lei | excel       | feed.organisation.normal.empty.lei.status | feed.organisation.normal.empty.lei.search | check.feed.organisation.normal.empty.lei |

  @KentGu @FeedOrganisations @TaskManager @T32501 @F11281 @14.1.0
  Scenario Outline: Feeds FO-02-16 Verify that successful to feed org with correct [Ultimate Parent Correlation] and [Close Link Correlation]_xml
    When navigate to task scheduler page
    And edit task scheduler <task.feed.organisation>
    And copy organisation feed <file.format> file <feed.organisation> for <task.feed.organisation> to <file>
    And run task scheduler <task.feed.organisation>
    Then task scheduler messaging should be <task.feed.organisation.message>
    And feed status should be <feed.status>
    When navigate to view organisation
    And search organisations <search.organisation>
    Then organisation should be <check.feed.organisation>
    Examples:
      | task.feed.organisation | file      | task.feed.organisation.message | feed.organisation                                                   | file.format | feed.status                                                                | search.organisation                                                        | check.feed.organisation                                                   |
      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.empty.ultimate.parenets.empty.close.links         | xml         | feed.organisation.empty.ultimate.parenets.empty.close.links.status         | feed.organisation.empty.ultimate.parenets.empty.close.links.search         | check.feed.organisation.empty.ultimate.parenets.empty.close.links         |
      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.invalid.ultimate.parenets.invalid.close.links     | xml         | feed.organisation.invalid.ultimate.parenets.invalid.close.links.status     | feed.organisation.invalid.ultimate.parenets.invalid.close.links.search     | check.feed.organisation.invalid.ultimate.parenets.invalid.close.links     |
      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.valid.ultimate.parenets.valid.close.links         | xml         | feed.organisation.valid.ultimate.parenets.valid.close.links.status         | feed.organisation.valid.ultimate.parenets.valid.close.links.search         | check.feed.organisation.valid.ultimate.parenets.valid.close.links         |
      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links | xml         | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links.status | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links.search | check.feed.organisation.valid.one.ultimate.parenets.valid.one.close.links |
#      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.empty.ultimate.parenets.empty.close.links         | excel       | feed.organisation.empty.ultimate.parenets.empty.close.links.status         | feed.organisation.empty.ultimate.parenets.empty.close.links.search         | check.feed.organisation.empty.ultimate.parenets.empty.close.links         |
#      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.invalid.ultimate.parenets.invalid.close.links     | excel       | feed.organisation.invalid.ultimate.parenets.invalid.close.links.status     | feed.organisation.invalid.ultimate.parenets.invalid.close.links.search     | check.feed.organisation.invalid.ultimate.parenets.invalid.close.links     |
#      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.valid.ultimate.parenets.valid.close.links         | excel       | feed.organisation.valid.ultimate.parenets.valid.close.links.status         | feed.organisation.valid.ultimate.parenets.valid.close.links.search         | check.feed.organisation.valid.ultimate.parenets.valid.close.links         |
#      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links | excel       | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links.status | feed.organisation.valid.one.ultimate.parenets.valid.one.close.links.search | check.feed.organisation.valid.one.ultimate.parenets.valid.one.close.links |

  @KentGu @FeedOrganisations @TaskManager @T3740 @F3629 @F5020 @F9068 @14.1.0 @2011.1 @2011.2 @2012.1 @2012.3
  Scenario Outline: Feeds FOC-OCTC-01-01 Verify that successful to import org contact file when [PARENT] is correct
    When navigate to task scheduler page
    And edit task scheduler <task.feed.organisation.contact>
    And copy organisation contact feed <file.format> file <feed.organisation.contact> for <task.feed.organisation.contact> to <file>
    And run task scheduler <task.feed.organisation.contact>
    Then task scheduler messaging should be <task.feed.organisation.contact.message>
    And feed status should be <feed.status>
    When navigate to view organisation
    And search organisations <search.organisation>
    Then organisation should be <check.feed.organisation>
    Examples:
      | task.feed.organisation.contact | file      | task.feed.organisation.contact.message | feed.organisation.contact               | file.format | feed.status                                    | search.organisation                            | check.feed.organisation                       |
      | task.feed.organisation.contact | file.path | task.feed.organisation.contact.message | feed.organisation.contact.normal        | xml         | feed.organisation.contact.normal.status        | feed.organisation.contact.normal.search        | check.feed.organisation.contact.normal        |
      | task.feed.organisation.contact | file.path | task.feed.organisation.contact.message | feed.organisation.contact.normal.modify | xml         | feed.organisation.contact.normal.modify.status | feed.organisation.contact.normal.modify.search | check.feed.organisation.contact.normal.modify |

  @KentGu @FeedOrganisations @TaskManager @T30258 @F7540 @F9068 @14.0.0 @14.1.0
  Scenario: Feeds FO-SEC-01-02 verify that feed single or multiple excode to multiple system by xml
    When navigate to task scheduler page
    And edit task scheduler task.feed.organisation
    And copy organisation feed xml file feed.organisation.external.code.normal for task.feed.organisation to file.path
    And run task scheduler task.feed.organisation
    Then task scheduler messaging should be task.feed.organisation.message
    And feed status should be feed.organisation.external.code.normal.status
    When navigate to view organisation
    And search organisations feed.organisation.external.code.normal.search
    Then organisation should be check.feed.organisation.external.code.normal

  @KentGu @FeedOrganisations @TaskManager @T16846 @F1194 @F1224 @F9068 @14.1.0 @2012.1
  Scenario Outline: Feeds FOUDF-02-04 Verify that fail to feed org UDF with not defined Values to enabled UDF schema
    When navigate to organisation static data page
    And edit organisation static data <organisation.udf> to <organisation.udf.disable>
    When navigate to task scheduler page
    And edit task scheduler <task.feed.organisation>
    And copy organisation feed <file.format> file <feed.organisation> for <task.feed.organisation> to <file>
    And run task scheduler <task.feed.organisation>
    Then task scheduler messaging should be <task.feed.organisation.message>
    And feed status should be <feed.status>
    When navigate to view organisation
    And search organisations <search.organisation>
    Then organisation should be <check.feed.organisation>
    Examples:
      | task.feed.organisation | file      | task.feed.organisation.message | organisation.udf | organisation.udf.disable | feed.organisation     | file.format | feed.status                  | search.organisation          | check.feed.organisation     |
      | task.feed.organisation | file.path | task.feed.organisation.message | organisation.udf | organisation.udf.disable | feed.organisation.udf | xml         | feed.organisation.udf.status | feed.organisation.udf.search | check.feed.organisation.udf |
#      | task.feed.organisation | file.path | task.feed.organisation.message | organisation.udf | organisation.udf.disable | feed.organisation.udf | xlsx        | feed.organisation.udf.status | feed.organisation.udf.search | check.feed.organisation.udf |

  @KentGu @FeedSecurityTemplates @TaskManager @T5668 @F9068 @14.1.0 @2011.1
  Scenario Outline: Feeds FST-01-01 Verify that successful to import security file when [ASSET_CLASS] is correct
    When navigate to task scheduler page
    And edit task scheduler <task.feed.security.template>
    And copy security template feed <file.format> file <feed.security.template> for <task.feed.security.template> to <file>
    And run task scheduler <task.feed.security.template>
    Then task scheduler messaging should be <task.feed.organisation.message>
    And feed status should be <feed.security.template.status>
    When navigate to security search page
    And search security <feed.security.template.search>
    Then security <feed.security.template.search.result> should be <check.feed.security.template>
    Examples:
      | task.feed.security.template                | file      | task.feed.organisation.message                     | feed.security.template                | file.format | feed.security.template.status                | feed.security.template.search                | feed.security.template.search.result                | check.feed.security.template                |
      | task.feed.security.template.qtp.bond.usd   | file.path | task.feed.security.template.qtp.bond.usd.message   | feed.security.template.qtp.bond.usd   | xml         | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | task.feed.security.template.qtp.bond.usd   | file.path | task.feed.security.template.qtp.bond.usd.message   | feed.security.template.qtp.bond.usd   | xlsx        | feed.security.template.qtp.bond.usd.status   | feed.security.template.qtp.bond.usd.search   | feed.security.template.qtp.bond.usd.search.result   | check.feed.security.template.qtp.bond.usd   |
      | task.feed.security.template.qtp.equity.usd | file.path | task.feed.security.template.qtp.equity.usd.message | feed.security.template.qtp.equity.usd | xml         | feed.security.template.qtp.equity.usd.status | feed.security.template.qtp.equity.usd.search | feed.security.template.qtp.equity.usd.search.result | check.feed.security.template.qtp.equity.usd |
      | task.feed.security.template.qtp.equity.usd | file.path | task.feed.security.template.qtp.equity.usd.message | feed.security.template.qtp.equity.usd | xlsx        | feed.security.template.qtp.equity.usd.status | feed.security.template.qtp.equity.usd.search | feed.security.template.qtp.equity.usd.search.result | check.feed.security.template.qtp.equity.usd |

  @KentGu @FeedAssetPricing @TaskManager @T6107 @F9068 @14.1.0 @2011.1
  Scenario Outline: Feeds FAP-01-01 Feed Asset Pricing
    When navigate to task scheduler page
    And edit task scheduler <task.feed.asset.pricing>
    And copy Asset Pricing feed <file.format> file <feed.asset.pricing> for <task.feed.asset.pricing> to <file>
    And run task scheduler <task.feed.asset.pricing>
    Then task scheduler messaging should be <task.feed.asset.pricing.message>
    And feed status should be <feed.asset.pricing.status>
    When navigate to security search page
    And search security <feed.asset.pricing.search>
    Then security <feed.asset.pricing.search.result> should be <check.feed.asset.pricing>
    Examples:
      | task.feed.asset.pricing | file | task.feed.asset.pricing.message | feed.asset.pricing                  | file.format | feed.asset.pricing.status                  | feed.asset.pricing.search                  | feed.asset.pricing.search.result                  | check.feed.asset.pricing                  |
      | task.feed.asset.pricing | file | task.feed.asset.pricing.message | feed.asset.bond.pricing             | xml         | feed.asset.bond.pricing.status             | feed.asset.bond.pricing.search             | feed.asset.bond.pricing.search.result             | check.feed.asset.bond.pricing             |
      | task.feed.asset.pricing | file | task.feed.asset.pricing.message | feed.asset.bond.pricing.not.existed | xml         | feed.asset.bond.pricing.not.existed.status | feed.asset.bond.pricing.not.existed.search | feed.asset.bond.pricing.not.existed.search.result | check.feed.asset.bond.pricing.not.existed |

  @KentGu @FeedPortfolioTSA @TaskManager @T25621 @F9068 @D11162 @14.1.0 @2011.2.SP3.5
  Scenario Outline: Feed TF-01-11 Verify that feed with CSV file correctly support the wrapping of values that contain comma
    When navigate to task scheduler page
    And edit task scheduler <task.feed.portfolio.tsa>
    And copy portfolio TSA feed <file.format> file <feed.protfolio.tsa> for <task.feed.portfolio.tsa> to <file>
    And run task scheduler <task.feed.portfolio.tsa>
    Then task scheduler messaging should be <task.feed.portfolio.tsa.message>
    And feed status should be <feed.protfolio.tsa.status>
    And search agreement <feed.protfolio.tsa.agreement.search>
    And view completed agreement statement <feed.protfolio.tsa.agreement.search.result>
    And select model <feed.protfolio.tsa.agreement.statement>
    Then agreement statement should be <check.feed.protfolio.tsa.agreement.statement>
    Examples:
      | task.feed.portfolio.tsa | file      | task.feed.portfolio.tsa.message | feed.protfolio.tsa                                                                                                                                                                                                                                                                                         | file.format | feed.protfolio.tsa.status | feed.protfolio.tsa.agreement.search | feed.protfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      | task.feed.protfolio.tsa | file.path | task.feed.protfolio.tsa.message | feed.tsa.upfront.fee,feed.tsa.pai,feed.tsa.transfer.value,feed.tsa.banked.coupon,feed.tsa.initial.coupon,feed.tsa.credit.event.cash.amount,feed.tsa.succession.event.cash.amount,feed.tsa.reset.to.par,feed.tsa.im.interest,feed.tsa.ndf.cashflow,feed.tsa.tsa.misc1,feed.tsa.tsa.misc2,feed.tsa.tsa.misc3 | xml         | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      | task.feed.protfolio.tsa | file.path | task.feed.protfolio.tsa.message | feed.tsa.upfront.fee,feed.tsa.pai,feed.tsa.transfer.value,feed.tsa.banked.coupon,feed.tsa.initial.coupon,feed.tsa.credit.event.cash.amount,feed.tsa.succession.event.cash.amount,feed.tsa.reset.to.par,feed.tsa.im.interest,feed.tsa.ndf.cashflow,feed.tsa.tsa.misc1,feed.tsa.tsa.misc2,feed.tsa.tsa.misc3 | csv         | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |
      | task.feed.protfolio.tsa | file.path | task.feed.protfolio.tsa.message | feed.tsa.upfront.fee,feed.tsa.pai,feed.tsa.transfer.value,feed.tsa.banked.coupon,feed.tsa.initial.coupon,feed.tsa.credit.event.cash.amount,feed.tsa.succession.event.cash.amount,feed.tsa.reset.to.par,feed.tsa.im.interest,feed.tsa.ndf.cashflow,feed.tsa.tsa.misc1,feed.tsa.tsa.misc2,feed.tsa.tsa.misc3 | xlsx        | feed.portfolio.tsa.status | feed.portfolio.tsa.agreement.search | feed.portfolio.tsa.agreement.search.result | feed.protfolio.tsa.agreement.statement | check.feed.protfolio.tsa.agreement.statement |

  @KentGu @FeedExternalIA @TaskManager @T4877 @F9068 @14.1.0 @2011.1
  Scenario Outline: Feeds FEIA-EIATC-01-01  Verify that successfull to import External IA file when [AGREEMENTID] is correct
    When navigate to task scheduler page
    And edit task scheduler <task.feed.external.ia>
    And copy External IA feed <file.format> file <feed.external.ia> for <task.feed.external.ia> to <file>
    And run task scheduler <task.feed.external.ia>
    Then task scheduler messaging should be <task.feed.external.ia.message>
    And feed status should be <feed.external.ia.status>
    And search agreement <feed.external.ia.agreement.search>
    And view completed agreement statement <feed.external.ia.agreement.search.result>
    And select model <feed.external.ia.agreement.statement>
    Then agreement statement should be <check.feed.external.ia.agreement>
    Examples:
      | task.feed.external.ia              | file      | task.feed.external.ia.message              | feed.external.ia              | file.format | feed.external.ia.status              | feed.external.ia.agreement.search              | feed.external.ia.agreement.search.result              | feed.external.ia.agreement.statement              | check.feed.external.ia.agreement              |
      | task.feed.external.ia.principal    | file.path | task.feed.external.ia.principal.message    | feed.external.ia.principal    | xml         | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      | task.feed.external.ia.principal    | file.path | task.feed.external.ia.principal.message    | feed.external.ia.principal    | xlsx        | feed.external.ia.principal.status    | feed.external.ia.principal.agreement.search    | feed.external.ia.principal.agreement.search.result    | feed.external.ia.principal.agreement.statement    | check.feed.external.ia.principal.agreement    |
      | task.feed.external.ia.counterparty | file.path | task.feed.external.ia.counterparty.message | feed.external.ia.counterparty | xml         | feed.external.ia.counterparty.status | feed.external.ia.counterparty.agreement.search | feed.external.ia.counterparty.agreement.search.result | feed.external.ia.counterparty.agreement.statement | check.feed.external.ia.counterparty.agreement |
      | task.feed.external.ia.counterparty | file.path | task.feed.external.ia.counterparty.message | feed.external.ia.counterparty | xlsx        | feed.external.ia.counterparty.status | feed.external.ia.counterparty.agreement.search | feed.external.ia.counterparty.agreement.search.result | feed.external.ia.counterparty.agreement.statement | check.feed.external.ia.counterparty.agreement |

  @KentGu @FeedAssetRating @TaskManager @T30304 @F9068 @D20125 @D24971 @D28209 @13.2.SP2 @13.3.SP2 @14.0.0.SP1 @14.1.0
  Scenario Outline: Feeds FST-01-01 [Market] should be accepted as null
    When navigate to task scheduler page
    And edit task scheduler <task.feed.asset.rating>
    And copy Asset Rating feed <file.format> file <feed.asset.rating> for <task.feed.asset.rating> to <file>
    And run task scheduler <task.feed.asset.rating>
    Then task scheduler messaging should be <task.feed.asset.rating.message>
    And feed status should be <feed.asset.rating.status>
    When navigate to security search page
    And search security <feed.asset.rating.search>
    Then security <feed.asset.rating.search.result> should be <check.feed.asset.rating>
    Examples:
      | task.feed.asset.rating | file      | task.feed.asset.rating.message | feed.asset.rating                      | file.format | feed.asset.rating.status                      | feed.asset.rating.search                      | feed.asset.rating.search.result                      | check.feed.asset.rating                      |
      | task.feed.asset.rating | file.path | task.feed.asset.rating.message | feed.asset.rating.bond.usd             | xml         | feed.asset.rating.bond.usd.status             | feed.asset.rating.bond.usd.search             | feed.asset.rating.bond.usd.search.result             | check.feed.asset.rating.bond.usd             |
      | task.feed.asset.rating | file.path | task.feed.asset.rating.message | feed.asset.rating.bond.usd.with.market | xml         | feed.asset.rating.bond.usd.with.market.status | feed.asset.rating.bond.usd.with.market.search | feed.asset.rating.bond.usd.with.market.search.result | check.feed.asset.rating.bond.usd.with.market |
      | task.feed.asset.rating | file.path | task.feed.asset.rating.message | feed.asset.rating.bond.usd             | xml         | feed.asset.rating.bond.usd.status             | feed.asset.rating.bond.usd.search             | feed.asset.rating.bond.usd.search.result             | check.feed.asset.rating.bond.usd             |
      | task.feed.asset.rating | file.path | task.feed.asset.rating.message | feed.asset.rating.bond.usd.with.market | xlsx        | feed.asset.rating.bond.usd.with.market.status | feed.asset.rating.bond.usd.with.market.search | feed.asset.rating.bond.usd.with.market.search.result | check.feed.asset.rating.bond.usd.with.market |

  @KentGu @FeedNAV @TaskManager @T5185 @F9068 @14.1.0 @2011.1
  Scenario Outline: Feeds FNAV-NAVTC-01-01  Verify that successfull to import NAV file when [agreementId] is correct
    When navigate to task scheduler page
    And edit task scheduler <task.feed.nav>
    And copy NAV feed <file.format> file <feed.nav> for <task.feed.nav> to <file>
    And run task scheduler <task.feed.nav>
    Then task scheduler messaging should be <task.feed.nav.message>
    And feed status should be <feed.nav.status>
    And search agreement <feed.nav.agreement.search>
    And edit agreement <feed.nav.agreement.search.result> in agreement search page
    Then agreement summary – agreement summary should be <check.feed.nav.agreement>
    Examples:
      | task.feed.nav | file      | task.feed.nav.message | feed.nav                | file.format | feed.nav.status                | feed.nav.agreement.search      | feed.nav.agreement.search.result      | check.feed.nav.agreement      |
      | task.feed.nav | file.path | task.feed.nav.message | feed.nav.repo.agreement | xml         | feed.nav.repo.agreement.status | feed.nav.repo.agreement.search | feed.nav.repo.agreement.search.result | check.feed.nav.repo.agreement |
      | task.feed.nav | file.path | task.feed.nav.message | feed.nav.etf.agreement  | xlsx        | feed.nav.etf.agreement.status  | feed.nav.etf.agreement.search  | feed.nav.etf.agreement.search.result  | check.feed.nav.etf.agreement  |

  @KentGu @FeedMtM @TaskManager @T5328 @F5020 @F9068 @14.1.0 @2011.1 @2011.2 @2012.3
  Scenario Outline: Feeds FMTM-MTMTC-01-01 Verify that successful to import MTM with correct feedSystem
    When navigate to task scheduler page
    And edit task scheduler <task.feed.mtm>
    And copy MTM feed <file.format> file <feed.mtm> for <task.feed.mtm> to <file>
    And run task scheduler <task.feed.mtm>
    Then task scheduler messaging should be <task.feed.mtm.message>
    And feed status should be <feed.mtm.status>
    And search agreement <feed.mtm.agreement.search>
    And view completed agreement statement <feed.mtm.agreement.search.result>
    And edit summary exposure info
    And view product type <feed.mtm.trade.product.type> on exposure summary page
    And view trade <check.feed.mtm.trade> detail
    Then trade detail should be <check.feed.mtm.trade>
    Examples:
      | task.feed.mtm | file      | task.feed.mtm.message | feed.mtm               | file.format | feed.mtm.status               | feed.mtm.agreement.search               | feed.mtm.agreement.search.result               | feed.mtm.trade.product.type         | check.feed.mtm.trade         |
      | task.feed.mtm | file.path | task.feed.mtm.message | feed.mtm.otc.net.trade | xml         | feed.mtm.otc.net.trade.status | feed.mtm.otc.net.trade.agreement.search | feed.mtm.otc.net.trade.agreement.search.result | feed.mtm.otc.net.trade.product.type | check.feed.mtm.otc.net.trade |

  @KentGu @FeedOrganisations @TaskManager @T32761 @F11590 @14.1.0
  Scenario Outline: Feeds FO-03-03 Verify that successfully to feed Org with IM Threshold,IM Threshold Currency, Include Subsidiaries
    When navigate to task scheduler page
    And edit task scheduler <task.feed.organisation>
    And copy organisation feed <file.format> file <feed.organisation> for <task.feed.organisation> to <file>
    And run task scheduler <task.feed.organisation>
    Then task scheduler messaging should be <task.feed.organisation.message>
    And feed status should be <feed.organisation.status>
    When navigate to view organisation
    And search organisations <search.feed.organisation>
    Then organisation should be <check.feed.organisation>
    Examples:
      | task.feed.organisation | file      | task.feed.organisation.message | feed.organisation | file.format | feed.organisation.status | search.feed.organisation | check.feed.organisation |
      | task.feed.organisation | file.path | task.feed.organisation.message | feed.organisation | xml         | feed.organisation.status | search.feed.organisation | check.feed.organisation |

  @KentGu @FeedCounterpartyAmount @TaskManager @T6108 @F9068 @14.1.0 @2011.1 @2011.2.SP3.5
  Scenario Outline: Feed FCA-01-01 Feed CP amount for Net agreement
    When navigate to task scheduler page
    And edit task scheduler <task.feed.counterparty.amount>
    And copy counterparty amount feed <file.format> file <feed.counterparty.amount> for <task.feed.counterparty.amount> to <file>
    And run task scheduler <task.feed.counterparty.amount>
    Then task scheduler messaging should be <task.feed.counterparty.amount.message>
    And feed status should be <feed.counterparty.amount.status>
    And search agreement <feed.counterparty.amount.agreement.search>
    And view completed agreement statement <feed.counterparty.amount.agreement.search.result>
    And quick link - agreement exposure management
    Then Exposure Management <exposure.event.detail> - event should be <check.exposure.event.detail>
    Examples:
      | task.feed.counterparty.amount | file | task.feed.counterparty.amount.message | feed.counterparty.amount | file.format | feed.counterparty.amount.status | feed.counterparty.amount.agreement.search | feed.counterparty.amount.agreement.search.result | exposure.event.detail            | check.exposure.event.detail              |
      #Feed CP amount for Net agreement(amended and approved)
      #Need to add task scheduler case
      | task.feed.counterparty.amount | file | task.feed.counterparty.amount.message | feed.counterparty.amount | xlsx        | feed.counterparty.amount.status | feed.counterparty.amount.agreement.search | feed.counterparty.amount.agreement.search.result | orignal.feed.counterparty.amount | check.feed.counterparty.amount.agreement |

  @KentGu @FeedInterestAmount @TaskManager @T30638 @F8318 @F9068 @14.0.0 @14.1.0
  Scenario Outline: Feeds FIA-01-02 Verify Feed interest amount and  WTH amount successfully  via Agr ID
    When navigate to task scheduler page
    And edit task scheduler <task.feed.interest.amount>
    And copy interest amount feed <file.format> file <feed.interest.amount> for <task.feed.interest.amount> to <file>
    And run task scheduler <task.feed.interest.amount>
    Then task scheduler messaging should be <task.feed.interest.amount.message>
    And feed status should be <feed.interest.amount.status>
    When navigate to interest manager search page
    And Interest Manager - search interest event by <feed.interest.amount.search.event>
    And Interest Manager - switch to <interest.direction> tab
    Then Interest Manager <interest.event.detail> - event should be <check.interest.event.detail>
    Examples:
      | task.feed.interest.amount | file      | task.feed.interest.amount.message | feed.interest.amount | file.format | feed.interest.amount.status | feed.interest.amount.search.event | interest.direction | interest.event.detail      | check.interest.event.detail      |
       #Verify Feed interest amount and WTH amount successfully  via Event ID
      | task.feed.interest.amount | file.path | task.feed.interest.amount.message | feed.interest.amount | xlsx        | feed.interest.amount.status | feed.interest.amount.search.event | Receive            | feed.interest.amount.event | check.feed.interest.amount.event |

  @KentGu @FeedAssetBookings @TaskManager @T28696 @F9068 @13.0.0 @14.1.0
  Scenario Outline: Feeds FABC-01-01 Verify feed Collatereal booking to non-net agreement by V13 template
    When navigate to task scheduler page
    And edit task scheduler <task.feed.asset.booking>
    And copy Asset Booking feed <file.format> file <feed.asset.booking> for <task.feed.asset.booking> to <file>
    And run task scheduler <task.feed.asset.booking>
    Then task scheduler messaging should be <task.feed.asset.booking.message>
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
      | task.feed.asset.booking | file | task.feed.asset.booking.message | feed.asset.booking      | file.format | feed.asset.booking.status | search.feed.asset.booking.agreement | search.result.feed.asset.booking.agreement | check.feed.asset.booking.agreement.statement | check.collateral.feed.asset.booking.holding.summary | feed.asset.booking.holding.summary | asset.type | check.feed.asset.booking.detail |
        #Verify feed Collatereal booking to non-net agreement by V13 template
      | task.feed.asset.booking | file | task.feed.asset.booking.message | feed.asset.booking.cash | xlsx        | feed.asset.booking.status | search.feed.asset.booking.agreement | search.result.feed.asset.booking.agreement | check.feed.asset.booking.agreement.statement | check.collateral.feed.asset.booking.holding.summary | feed.asset.booking.holding.summary | Bond       | check.feed.asset.booking.detail |

  @KentGu @FeedFlushTrade @TaskManager @T11294 @BZ_33011 @F9068 @14.1.0 @2011.1 @2011.2 @2011.2.SP3.5
  Scenario Outline: Feeds FT-01-02 Feed Flush Trade with consecutive blank columns in Excel & CSV
    When navigate to task scheduler page
    And edit task scheduler <task.feed.trade>
    And copy trade feed <file.format> file <feed.trade> for <task.feed.trade> to <file>
    And run task scheduler <task.feed.trade>
    Then task scheduler messaging should be <task.feed.trade.message>
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
      | task.feed.trade | file      | task.feed.trade.message | feed.trade | file.format | feed.trade.status | search.feed.trade.agreement | search.result.feed.trade.agreement | feed.trade.agreement.model | check.feed.trade.agreement.statement | check.feed.trade.exposure.summary | check.feed.trade.trade.summary | check.feed.trade.detail |
      | task.feed.trade | file.path | task.feed.trade.message | feed.trade | xml         | feed.trade.status | search.feed.trade.agreement | search.result.feed.trade.agreement | feed.trade.agreement.model | check.feed.trade.agreement.statement | check.feed.trade.exposure.summary | check.feed.trade.trade.summary | check.feed.trade.detail |


  @MengliHuang @ExtractModifyedAgreement @System @T11111
  Scenario Outline: Extract modify agreement and compare extract result
    When navigate to task scheduler page
    And create new folder extract.modified.agreement on server
    And task scheduler - switch to System tab
    And edit task scheduler extract.modified.agreement
    And run task scheduler extract.modified.agreement
    And get extract agreement result for extract.modified.agreement to local actual.file
    Then assert extract agreement file actual.file should be <expected.result>
    Examples:
      | expected.result              |
      | extract.agreement.fcm        |
      | extract.agreement.multimodel |
      | extract.agreement.repo       |
      | extract.agreement.etf        |
      | extract.agreement.umbrella   |

  @JaneZhang @v2011.2.SP6;2012.3.SP9 @D11515#D16750 @FeedAssetBooking @T26988_3_feedAthorisedBookingByTask
  Scenario Outline: Verify bookings can not be fed by task with authorised status when tick do not include authorised
    When navigate to collateral preferences page
    And set collateral preferences CollateralPreferences26988.DoNotIncludeAuthorised
    When navigate to task scheduler page
    And edit task scheduler <task.feed.asset.booking>
    And copy Asset Booking feed <task.file.format> file <feed.asset.booking> for <task.feed.asset.booking> to <file>
    And run task scheduler <task.feed.asset.booking>
    Then task scheduler messaging should be <task.feed.asset.booking.message>
    And feed status should be <task.feed.asset.booking.status>

    Examples:
      | task.feed.asset.booking             | task.file.format | feed.asset.booking             | file | task.feed.asset.booking.message             | task.feed.asset.booking.status                              |
      | task.feed.cash.Return.1m.authorised | xlsx             | feed.cash.Return.1m.authorised | file | fail.feed.cash.Return.1m.authorised.message | cash.authorisedBooking.cannotAdd.TickDoNotIncludeAuthorised |
      | task.feed.bond.Return.1m.authorised | xlsx             | feed.bond.Return.1m.authorised | file | fail.feed.bond.Return.1m.authorised.message | bond.authorisedBooking.cannotAdd.TickDoNotIncludeAuthorised |
      | task.feed.equity.Return.1m.authorised | xlsx             | feed.equity.Return.1m.authorised | file | fail.feed.equity.Return.1m.authorised.message | equity.authorisedBooking.cannotAdd.TickDoNotIncludeAuthorised |

  @JaneZhang @14.1.0 @F469 @AssetHoldings @T12186_2 @wip
  Scenario: Verify auto booking can be made by auto booking button and task for Normal NotNet agreement Margin IM VM Recall event Use Cpty Amount by task
    When Go to agreement agr12186.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.recall.50000,im.recall.50000
    When Exposure Management - tick events vm.recall.50000,im.recall.50000
    And Exposure Management - auto send letter
    And Exposure Management - margin filters - search margin issued agr12186.issued
    And Exposure Management - tick events vm.recall.50000,im.recall.50000
    And Exposure Management - set event vmRecall.CpyAmount value to vmRecall.CpyAmount.49900
    And Exposure Management - set event imRecall.CpyAmount value to imRecall.CpyAmount.50000
    And Exposure Management - save changes
    When navigate to task scheduler page
    And task scheduler - switch to Workflow tab
    And edit task scheduler autoBookCashforAgr12186
    And run task scheduler autoBookCashforAgr12186
    Then task scheduler messaging should be autoBookCashforAgr12186.success
    When Go to agreement agr12186.notnet statement page by URL
    And edit asset summary info
    And view asset type asset.type.cash.USD agreement asset CASH Page
    Then asset holding detail should be vm.recall.autoBook.systemDraft.49900
    And asset holding detail should be im.recall.autoBook.systemDraft.50000

  @JaneZhang @14.1.0 @F469 @AssetHoldings @T15394_2 @wip
  Scenario: Verify VM and IM auto booking using own Tolerance Amount for Normal Not Net agreement Margin IM and VM Call event by task
    When Go to agreement agr15394.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be call.3000000
    When Exposure Management - tick events call.3000000
    And Exposure Management - auto send letter
    Then Exposure Management - EM message should be call.autoEmailSuccess
    When Exposure Management - margin filters - search margin issued agr15394.issued
    And Exposure Management - tick events call.3000000
    And Exposure Management - set event Call.CpyAmount value to Call.CpyAmount.3500000
    And Exposure Management - save changes
    When navigate to task scheduler page
    And task scheduler - switch to Workflow tab
    And edit task scheduler autoBookCashforAgr15394
    And run task scheduler autoBookCashforAgr15394
    Then task scheduler messaging should be autoBookCashforAgr15394.imCallSuccess.vmCallUnsuccess
    When Go to agreement agr15394.notnet statement page by URL
    And edit asset summary info
    And view asset type asset.type.cash.USD agreement asset CASH Page
    Then asset holding detail should be vm.call.autoBook.pending.notadded
    And asset holding detail should be im.recall.autoBook.pending.1000000.added

  @2012.3 @JaneZhang @F10624 @T31367_2 @AutoBookCash
  Scenario: Verify that auto book for IM Call and IM Recall when both IM Call and IM Recall selected FCMGrossIM by task
  """
     R6 Auto Book Buttons and Task
     Regarding R2, R3, Auto book collateral booking is depending on what kind letter issued if send letter to trigger auto-booking. Now if we click auto book button or execute Schedule Task - Auto Book Cash:
    · Both Call and Recall applicable or selected: auto book collateral for Call and Recall
    · Above logic should be applied to all Auto-Book button on EM and Schedule Task
  """

    When Go to agreement agr31367.notnet statement page by URL
    And quick link - agreement exposure management
    When Exposure Management - set event im.call.agreementRequirement.1000000.pendingReview value to im.call.agreementRequirement.1000000.MarginRequestIssued.counterpartyAmount.600000
    And Exposure Management - set event im.recall.agreementRequirement.2000000.pendingReview value to im.recall.agreementRequirement.2000000.MarginRequestIssued.counterpartyAmount.700000
    And Exposure Management - save changes

    When navigate to task scheduler page
    And task scheduler - switch to Workflow tab
    And edit task scheduler autoBookCashforAgr31367
    And run task scheduler autoBookCashforAgr31367
    Then task scheduler messaging should be autoBookCashforAgr31367.success
    When Go to agreement agr31367.notnet statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    Then asset holding detail should be im.call.pending.autobook.0.6m
    Then asset holding detail should be im.recall.pending.autobook.0.7m
    When Go to agreement agr31367.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.noCall,im.call.agreementRequirement.1000000.partialDisbute,im.recall.agreementRequirement.2000000.partialDisbute

