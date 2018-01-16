@Reports @regression
Feature: Reports
  This feature is to validate all report

  In order to validate all report
  As a colline user
  I want test following functions

  Background:
    Given have login with credential login.credential.test1
    And have system preferences system.preference1
    And have organisation global preferences organisation.global.preference.default

  @MengliHuang @AuditReports @T34235 @14.1.3 @Reviewed @Proxied
  Scenario Outline: audit asset report bond equity commodity create
    When navigate to security search page
    And add security <instrument.create>
    And search security <instrument.startingwith>
    And approve security <instrument.search.result>
    When navigate to Audit Assets Report
    And search Audit Assets Report <audit.report.filter>
    And run report to <reportPath> as <reportFormat>
    Then assert <reportFormat> report <reportPath> should follow the rule <reportValidationRule>
    Examples:
      | instrument.create                 | instrument.startingwith     | instrument.search.result   | audit.report.filter               | reportPath      | reportFormat    | reportValidationRule              |
      | audit.bond.instrument.create      | search.bond.instrument      | audit.bond.instrument      | audit.asset.by.bondassettype      | auditReportPath | Excel Worksheet | audit.asset.report.bond.rule      |
      | audit.equity.instrument.create    | search.equity.instrument    | audit.equity.instrument    | audit.asset.by.equityassettype    | auditReportPath | Excel Worksheet | audit.asset.report.equity.rule    |
      | audit.commodity.instrument.create | search.commodity.instrument | audit.commodity.instrument | audit.asset.by.commodityassettype | auditReportPath | Excel Worksheet | audit.asset.report.commodity.rule |

  @MengliHuang @AuditReports @T34235 @14.1.3 @Reviewed @Proxied
  Scenario: audit asset report cash create
    When navigate to asset definition page
    And add asset types audit.cash.assetType
    When navigate to Audit Assets Report
    And search Audit Assets Report audit.asset.by.cashassettype
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule audit.asset.report.cash.rule

  @MengliHuang @AuditReports @T34235 @14.1.3 @Reviewed @92841 @Proxied
  Scenario Outline: audit asset report bond equity commodity cash update
    When navigate to security search page
    And search security <instrument.startingwith>
    And edit security <instrument.search.result> to <instrument.update>
    And search security <instrument.startingwith>
    And approve security <instrument.search.result>
    When navigate to Audit Assets Report
    And search Audit Assets Report <audit.report.filter>
    And run report to <reportPath> as <reportFormat>
    Then assert <reportFormat> report <reportPath> should follow the rule <reportValidationRule>
    Examples:
      | instrument.startingwith     | instrument.search.result   | instrument.update           | audit.report.filter               | reportPath      | reportFormat    | reportValidationRule              |
      | search.bond.instrument      | audit.bond.instrument      | bond.instrument.update      | audit.asset.by.bondassettype      | auditReportPath | Excel Worksheet | audit.asset.report.bond.rule      |
      | search.equity.instrument    | audit.equity.instrument    | equity.instrument.update    | audit.asset.by.equityassettype    | auditReportPath | Excel Worksheet | audit.asset.report.equity.rule    |
      | search.commodity.instrument | audit.commodity.instrument | commodity.instrument.update | audit.asset.by.commodityassettype | auditReportPath | Excel Worksheet | audit.asset.report.commodity.rule |
      | search.cash.instrument      | audit.cash.instrument      | cash.instrument.update      | audit.asset.by.cashassettype      | auditReportPath | Excel Worksheet | audit.asset.report.cash.rule      |

  @MengliHuang  @AuditReports @T34235 @14.1.3 @Reviewed @Proxied
  Scenario Outline: audit asset definition report create
    When navigate to collateral static data page
    And add collateral static data <asset.classification>
    And navigate to asset definition page
    And add asset class <asset.class>
    And add asset type <asset.type>
    And add asset category <asset.category.with.assettype>
    When navigate to Audit Asset Definition Report
    And search Audit Asset Definition Report <audit.report.filter>
    And run report to <reportPath> as <reportFormat>
    Then assert <reportFormat> report <reportPath> should follow the rule <reportValidationRule>
    Examples:
      | asset.classification | asset.class       | asset.type       | asset.category.with.assettype  | audit.report.filter                  | reportPath      | reportFormat    | reportValidationRule              |
      | asset.classification | class.FI.Security | type.FI.Security | category.with.FI.Security.type | audit.asset.definition.by.class.type | auditReportPath | Excel Worksheet | audit.asset.report.bond.rule      |
      | asset.classification | class.cash        | type.cash        | category.with.cash.type        | audit.asset.definition.by.class.type | auditReportPath | Excel Worksheet | audit.asset.report.cash.rule      |
      | asset.classification | class.equity      | type.equity      | category.with.equity.type      | audit.asset.definition.by.class.type | auditReportPath | Excel Worksheet | audit.asset.report.equity.rule    |
      | asset.classification | class.commodity   | type.commodity   | category.with.commodity.type   | audit.asset.definition.by.class.type | auditReportPath | Excel Worksheet | audit.asset.report.commodity.rule |
      | asset.classification | class.other       | type.other       | category.with.other.type       | audit.asset.definition.by.class.type | auditReportPath | Excel Worksheet | audit.asset.report.other.rule     |

  @MengliHuang @AuditReports @T34234 @14.1.3 @Reviewed @Proxied
  Scenario Outline: audit asset definition report update
    When navigate to collateral static data page
    And edit collateral static data <asset.classification> to <asset.classification.update>
    And navigate to asset definition page
    And edit asset class <asset.class> to <asset.class.update>
    And edit asset type <asset.type> to <asset.type.update>
    And edit asset category <asset.category> to <asset.category.update>
    When navigate to Audit Asset Definition Report
    And search Audit Asset Definition Report <audit.report.filter>
    And run report to <reportPath> as <reportFormat>
    Then assert <reportFormat> report <reportPath> should follow the rule <reportValidationRule>
    Examples:
      | asset.classification | asset.classification.update | asset.class       | asset.class.update       | asset.type       | asset.type.update       | asset.category                 | asset.category.update                 | audit.report.filter                  | reportPath      | reportFormat    | reportValidationRule              |
      | asset.classification | asset.classification.update | class.FI.Security | class.FI.Security.update | type.FI.Security | type.FI.Security.update | category.with.FI.Security.type | category.with.FI.Security.type.update | audit.asset.definition.by.class.type | auditReportPath | Excel Worksheet | audit.asset.report.bond.rule      |
      | asset.classification | asset.classification.update | class.cash        | class.cash.update        | type.cash        | type.cash.update        | category.with.cash.type        | category.with.cash.type.update        | audit.asset.definition.by.class.type | auditReportPath | Excel Worksheet | audit.asset.report.cash.rule      |
      | asset.classification | asset.classification.update | class.equity      | class.equity.update      | type.equity      | type.equity.update      | category.with.equity.type      | category.with.equity.type.update      | audit.asset.definition.by.class.type | auditReportPath | Excel Worksheet | audit.asset.report.equity.rule    |
      | asset.classification | asset.classification.update | class.commodity   | class.commodity.update   | type.commodity   | type.commodity.update   | category.with.commodity.type   | category.with.commodity.type.update   | audit.asset.definition.by.class.type | auditReportPath | Excel Worksheet | audit.asset.report.commodity.rule |
      | asset.classification | asset.classification.update | class.other       | class.other.update       | type.other       | type.other.update       | category.with.other.type       | category.with.other.type.update       | audit.asset.definition.by.class.type | auditReportPath | Excel Worksheet | audit.asset.report.other.rule     |

  @MengliHuang @AuditReports @T34247 @14.1.3 @Reviewed @Proxied
  Scenario: audit holiday centres report create
    When navigate to holiday centre page
    And add holiday centres audit.holiday.centres.create
    And navigate to Audit Holiday Centres Report
    And search Audit Holiday Centres Report audit.holiday.centre.by.name
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule audit.holiday.centre.report.rule

  @MengliHuang @AuditReports @T34247 @14.1.3 @Reviewed @Proxied
  Scenario: audit holiday centres report update
    When navigate to holiday centre page
    And edit holiday centre audit.holiday.centre.update
    And navigate to Audit Holiday Centres Report
    And search Audit Holiday Centres Report audit.holiday.centre.by.name
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule audit.holiday.centre.report.rule

  @MengliHuang @AuditReports @T34248 @14.1.3 @Reviewed @Proxied
  Scenario Outline: audit interests rates report create
    When navigate to interest rates page
    And add Interest Rates <audit.interests.rates.create>
    And navigate to Audit Interest Rates Report
    And search Audit Interest Rates Report <audit.interests.rates..report.filter>
    And run report to <reportPath> as <reportFormat>
    Then assert <reportFormat> report <reportPath> should follow the rule <reportValidationRule>
    Examples:
      | audit.interests.rates.create | audit.interests.rates..report.filter | reportPath      | reportFormat    | reportValidationRule       |
      | interest.rate.create         | audit.interests.rates.by.name        | auditReportPath | Excel Worksheet | audit.interest.report.rule |
      | wht.rate.create              | audit.wht.rates.by.name              | auditReportPath | Excel Worksheet | audit.wht.report.rule      |

  @MengliHuang @AuditReports @T34248 @14.1.3 @Reviewed @Proxied
  Scenario Outline: audit interests rates report update
    When navigate to interest rates page
    And edit Interest Rate <audit.interests.rates> to <audit.interests.rates.update>
    And navigate to Audit Interest Rates Report
    And search Audit Interest Rates Report <audit.interests.rates..report.filter>
    And run report to <reportPath> as <reportFormat>
    Then assert <reportFormat> report <reportPath> should follow the rule <reportValidationRule>
    Examples:
      | audit.interests.rates | audit.interests.rates.update | audit.interests.rates..report.filter | reportPath      | reportFormat    | reportValidationRule       |
      | interest.rate.exist   | interest.rate.update         | audit.interests.rates.by.name        | auditReportPath | Excel Worksheet | audit.interest.report.rule |
      | wht.rate.exist        | wht.rate.update              | audit.wht.rates.by.name              | auditReportPath | Excel Worksheet | audit.wht.report.rule      |

  @MengliHuang @AuditReports @T34251 @14.1.3 @Reviewed @Proxied
  Scenario: audit roles administration report create
    When navigate to user&role administration page
    And add administer roles audit.administer.role.create
    And navigate to privileges page
    And edit privileges audit.role.privilege.collateral,audit.role.privilege.instrument.data,audit.role.privilege.systemadmin,audit.role.privilege.market.data,audit.role.privilege.org,audit.role.privilege.reconciliation,audit.role.privilege.report,audit.role.privilege.static.data,audit.role.privilege.reform,audit.role.privilege.self.service,audit.role.privilege.opt
    And navigate to Audit Roles Administration Report
    And search Audit Roles Administration Report audit.role.administer.by.rolename
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule audit.role.administer.report.rule

  @MengliHuang @AuditReports @T34251 @14.1.3 @Proxied
  Scenario: audit roles administration report update
    When navigate to privileges page
    And edit privileges audit.role.privilege.collateral,audit.role.privilege.instrument.data,audit.role.privilege.systemadmin,audit.role.privilege.market.data,audit.role.privilege.org,audit.role.privilege.reconciliation,audit.role.privilege.report,audit.role.privilege.static.data,audit.role.privilege.reform,audit.role.privilege.self.service,audit.role.privilege.opt
#    And navigate to user&role administration page
#    And disable administer roles audit.administer.role
    And navigate to Audit Roles Administration Report
    And search Audit Roles Administration Report audit.role.administer.by.rolename
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule audit.role.administer.report.rule

  @MengliHuang @AuditReports @T34255 @14.1.3 @Reviewed @Proxied
  Scenario: audit user administration report create
    When navigate to user&role administration page
    And add users audit.user.create
    And edit user audit.user.edit
    And navigate to Audit User Administration Report
    And search Audit User Administration Report audit.user.administer.by.username
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule audit.user.administer.report.rule

  @MengliHuang @AuditReports @T34255 @14.1.3 @Reviewed @Proxied
  Scenario: audit user administration report update
    When navigate to user&role administration page
    And edit user audit.user.update
    When navigate to Audit User Administration Report
    And search Audit User Administration Report audit.user.administer.by.username
    And run report to auditReportPath as Excel Worksheet

  @MengliHuang @AuditReports @T34250 @14.1.3 @Reviewed @Proxied
  Scenario: audit organisation report create
    When navigate to organisation static data page
    And edit organisation static data udf1 to udf1.update
    And edit organisation static data udf15 to udf15.update
    And edit organisation static data udf16 to udf16.update
    And edit organisation static data udf30 to udf30.update
    And add organisations org1,org2,org3,org4,org5,audit.org.create
    And navigate to Audit Organisations Report
    And search Audit Organisations Report audit.organisation.by.orgname
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule audit.organisation.report.rule

  @MengliHuang @AuditReports @T34250 @14.1.3 @Reviewed @Proxied
  Scenario: audit organisation report update
    When navigate to view organisation
    And search organisations org.search
    And edit organisation to audit.org.update
    And navigate to Audit Organisations Report
    And search Audit Organisations Report audit.organisation.by.orgname
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule audit.organisation.report.rule

  @MengliHuang @AuditReports @T34250 @14.1.3 @Reviewed @Proxied
  Scenario: audit organisation report delete
    When navigate to view organisation
    And search organisations org.search
    And edit organisation to audit.org.delete
    And delete organisations org.search
    And navigate to Audit Organisations Report
    And search Audit Organisations Report audit.organisation.by.operate
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule audit.organisation.report.rule

  @MengliHuang @AuditReports @T34246 @14.1.3 @Proxied
  Scenario Outline: audit eligibility rule template report create
    When navigate to eligibility rules template page
    And add eligibility rules template <eligibility.rule.template.create>
    And navigate to Audit Eligibility Rule Template Report
    And search Audit Eligibility Rule Template Report <audit.eligibility.rule.template.report.filter>
    And run report to <report.path> as <report.format>
    Then assert <report.format> report <report.path> should follow the rule <audit.eligibility.rule.template.report.rule>
    Examples:
      | eligibility.rule.template.create                                | audit.eligibility.rule.template.report.filter | report.path     | report.format   | audit.eligibility.rule.template.report.rule |
      | audit.eligibility.rule.template.with.assettype.create           | audit.eligibility.rule.by.tempalte            | auditReportPath | Excel Worksheet | reportRule                                  |
      | audit.eligibility.rule.template.with.assetclassifacation.create | audit.eligibility.rule.by.tempalte            | auditReportPath | Excel Worksheet | reportRule                                  |

  @MengliHuang @AuditReports @T34246 @14.1.3 @todo @Proxied
  Scenario Outline: audit eligibility rule template report update
    When navigate to eligibility rules template page
    And search eligibility rules template <eligibility.rule.template.search>
    And edit eligibility rules template <eligibility.rule.template.source> to <eligibility.rule.template.update>
    And navigate to Audit Eligibility Rule Template Report
    And search Audit Eligibility Rule Template Report <audit.eligibility.rule.template.report.filter>
    And run report to <report.path> as <report.format>
    Then assert <report.format> report <report.path> should follow the rule <audit.eligibility.rule.template.report.rule>
    Examples:
      | eligibility.rule.template.search | eligibility.rule.template.source                     | eligibility.rule.template.update                     | audit.eligibility.rule.template.report.filter      | report.path     | report.format   | audit.eligibility.rule.template.report.rule |
      | eligibility.rule.template.search | eligibility.rule.template.assettype.source           | eligibility.rule.template.assettype.update           | audit.eligibility.rule.template.report.by.template | auditReportPath | Excel Worksheet | reportRule                                  |
      | eligibility.rule.template.search | eligibility.rule.template.assetclassification.source | eligibility.rule.template.assetclassification.update | audit.eligibility.rule.template.report.by.template | auditReportPath | Excel Worksheet | reportRule                                  |

  @MengliHuang @AuditReports @T34180 @Proxied
  Scenario Outline: audit agreements report create
    When add <bussinessLine> agreements <agreementInfo>
    And navigate to Audit Agreements Report
    And search Audit Agreements Report <report.filter>
    And run report to <report.path> as <report.format>
    Then assert <report.format> report <report.path> should follow the rule <audit.agreement.report.rule>
    Examples:
      | bussinessLine | agreementInfo                          | report.filter                  | report.path     | report.format   | audit.agreement.report.rule |
      | FCM           | agreement.fcm.with.all.contents        | audit.agreement.by.description | auditReportPath | Excel Worksheet | reportRule                  |
      | OTC           | agreement.multimodel.with.all.contents | audit.agreement.by.description | auditReportPath | Excel Worksheet | reportRule                  |
      | Umbrella      | agreement.umbrella.with.all.contents   | audit.agreement.by.description | auditReportPath | Excel Worksheet | reportRule                  |
      | Repo          | agreement.repo.with.all.contents       | audit.agreement.by.description | auditReportPath | Excel Worksheet | reportRule                  |
      | ETF           | agreement.etf.with.all.contents        | audit.agreement.by.description | auditReportPath | Excel Worksheet | reportRule                  |

  @MengliHuang @AuditReports @T34180 @Proxied
  Scenario Outline: audit agreements report update
    When search agreement <agreement.search.filter>
    And edit agreement <agreementSearchResult> in agreement search page
    And edit <bussinessLine> agreement <oriAgreement> to <agreementInfo>
    And navigate to Audit Agreements Report
    And search Audit Agreements Report <report.filter>
    And run report to <report.path> as <report.format>
    Then assert <report.format> report <report.path> should follow the rule <audit.agreement.report.rule>
    Examples:
      | agreement.search.filter | bussinessLine | agreementSearchResult | oriAgreement         | agreementInfo                                 | report.filter                  | report.path     | report.format   | audit.agreement.report.rule |
      | filter.with.description | FCM           | agreement.fcm         | agreement.fcm        | agreement.fcm.with.all.contents.update        | audit.agreement.by.description | auditReportPath | Excel Worksheet | reportRule                  |
      | filter.with.description | OTC           | agreement.multimodel  | agreement.multimodel | agreement.multimodel.with.all.contents.update | audit.agreement.by.description | auditReportPath | Excel Worksheet | reportRule                  |
      | filter.with.description | Umbrella      | agreement.umbrella    | no.agreement         | agreement.umbrella.with.all.contents.update   | audit.agreement.by.description | auditReportPath | Excel Worksheet | reportRule                  |
      | filter.with.description | Repo          | agreement.repo        | agreement.repo       | agreement.repo.with.all.contents.update       | audit.agreement.by.description | auditReportPath | Excel Worksheet | reportRule                  |
      | filter.with.description | ETF           | agreement.etf         | agreement.etf        | agreement.etf.with.all.contents.update        | audit.agreement.by.description | auditReportPath | Excel Worksheet | reportRule                  |

  @MengliHuang @AuditReport @T34254 @Proxied
  Scenario Outline: audit trade report create
    When search agreement <agreement.search.filter>
    And view completed agreement statement <agreement.search.result>
    And edit summary exposure info
    And view product type <product.type> on exposure summary page
    And add OTC trades <trade.with.all.content>
    And navigate to Audit Trades Report
    And search Audit Trades Report <report.filter>
    And run report to <report.path> as <report.format>
    Then assert <report.format> report <report.path> should follow the rule <audit.trade.report.rule>
    Examples:
      | agreement.search.filter            | agreement.search.result | product.type | trade.with.all.content  | report.filter             | report.path     | report.format   | audit.trade.report.rule |
      | filter.with.description.otc        | agreement.otc           | product.qtp  | otc.trade.create        | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |
      | filter.with.description.fcm        | agreement.fcm           | product.qtp  | fcm.trade.create        | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |
      | filter.with.description.repo       | agreement.repo          | product.qtp  | repo.trade.create       | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |
      | filter.with.description.etf        | agreement.etf           | product.qtp  | etf.trade.create        | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |
      | filter.with.description.multimodel | agreement.multimodel    | product.qtp  | multimodel.trade.create | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |

  @MengliHuang @AuditReport @T34254 @Proxied
  Scenario Outline: audit trade report update
    When search agreement <agreement.search.filter>
    And view completed agreement statement <agreement.search.result>
    And edit summary exposure info
    And view product type <product.type> on exposure summary page
    And edit trade <oriTrade> to <trade.with.all.content.update>
    And navigate to Audit Trades Report
    And search Audit Trades Report <report.filter>
    And run report to <report.path> as <report.format>
    Then assert <report.format> report <report.path> should follow the rule <audit.trade.report.rule>
    Examples:
      | agreement.search.filter            | agreement.search.result | product.type | oriTrade         | trade.with.all.content.update | report.filter             | report.path     | report.format   | audit.trade.report.rule |
      | filter.with.description.otc        | agreement.otc           | product.qtp  | otc.trade        | otc.trade.update              | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |
      | filter.with.description.fcm        | agreement.fcm           | product.qtp  | fcm.trade        | fcm.trade.update              | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |
      | filter.with.description.repo       | agreement.repo          | product.qtp  | repo.trade       | repo.trade.update             | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |
      | filter.with.description.etf        | agreement.etf           | product.qtp  | etf.trade        | etf.trade.update              | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |
      | filter.with.description.multimodel | agreement.multimodel    | product.qtp  | multimodel.trade | multimodel.trade.update       | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |

  @MengliHuang @AuditReport @T34254 @Proxied
  Scenario Outline: audit trade report delete
    When search agreement <agreement.search.filter>
    And view completed agreement statement <agreement.search.result>
    And edit summary exposure info
    And view product type <product.type> on exposure summary page
    And delete trade <oriTrade>
    And navigate to Audit Trades Report
    And search Audit Trades Report <report.filter>
    And run report to <report.path> as <report.format>
    Then assert <report.format> report <report.path> should follow the rule <audit.trade.report.rule>
    Examples:
      | agreement.search.filter            | agreement.search.result | product.type | oriTrade         | report.filter             | report.path     | report.format   | audit.trade.report.rule |
      | filter.with.description.otc        | agreement.otc           | product.qtp  | otc.trade        | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |
      | filter.with.description.fcm        | agreement.fcm           | product.qtp  | fcm.trade        | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |
      | filter.with.description.repo       | agreement.repo          | product.qtp  | repo.trade       | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |
      | filter.with.description.etf        | agreement.etf           | product.qtp  | etf.trade        | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |
      | filter.with.description.multimodel | agreement.multimodel    | product.qtp  | multimodel.trade | audit.trade.report.filter | auditReportPath | Excel Worksheet | reportRule              |

  @MengliHuang @AuditReport @Reviewed @T34249 @Proxied
  Scenario Outline: audit optimisation report create
    When navigate to optimisation rule builder page
    And Optimisation - select <mainModule> - <subModule>
    And Optimisation - add <mainModule> - <subModule> <optimisationRule>
    And navigate to Audit Optimisation Rule Report
    And search Audit Optimisation Rule Report <report.filter>
    And run report to <report.path> as <report.format>
    Then assert <report.format> report <report.path> should follow the rule <report.rule>
    Examples:
      | mainModule   | subModule | optimisationRule                          | report.filter                    | report.path     | report.format   | report.rule |
      | filters      | Asset     | audit.optimisation.filter.asset.rule      | audit.optimisation.report.filter | auditReportPath | Excel Worksheet | reportRule  |
      | filters      | Agreement | audit.optimisation.filter.agreement.rule  | audit.optimisation.report.filter | auditReportPath | Excel Worksheet | reportRule  |
      | rankings     | Asset     | audit.optimisation.ranking.asset.rule     | audit.optimisation.report.filter | auditReportPath | Excel Worksheet | reportRule  |
      | rankings     | Agreement | audit.optimisation.ranking.agreement.rule | audit.optimisation.report.filter | auditReportPath | Excel Worksheet | reportRule  |
      | optimisation | Rules     | audit.optimisation.opt.rule               | audit.optimisation.report.filter | auditReportPath | Excel Worksheet | reportRule  |


  @MengliHuang @AuditReport @Reviewed @T34249 @Proxied
  Scenario Outline: audit optimisation report delete
    When navigate to optimisation rule builder page
    And Optimisation - select <mainModule> - <subModule>
    And Optimisation - delete rule <optimisationRule>
    And navigate to Audit Optimisation Rule Report
    And search Audit Optimisation Rule Report <report.filter>
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule reportRule
    Examples:
      | mainModule   | subModule | optimisationRule                            | report.filter                    |
      | filters      | Asset     | audit.optimisation.filter.asset.delete      | audit.optimisation.report.filter |
      | filters      | Agreement | audit.optimisation.filter.agreement.delete  | audit.optimisation.report.filter |
      | rankings     | Asset     | audit.optimisation.ranking.asset.delete     | audit.optimisation.report.filter |
      | rankings     | Agreement | audit.optimisation.ranking.agreement.delete | audit.optimisation.report.filter |
      | optimisation | Rules     | audit.optimisation.rule.delete              | audit.optimisation.report.filter |

  @MengliHuang @AuditReport @Reviewed @T34249 @Proxied
  Scenario Outline: audit optimisation report update
    When navigate to optimisation rule builder page
    And Optimisation - select <mainModule> - <subModule>
    And Optimisation - edit rule <ori.opt> to <new.opt>
    And navigate to Audit Optimisation Rule Report
    And search Audit Optimisation Rule Report audit.optimisation.report.filter
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule reportRule
    Examples:
      | mainModule   | subModule | ori.opt               | new.opt                  |
      | filters      | Asset     | filter.asset.ori      | filter.asset.update      |
      | filters      | Agreement | filter.agreement.ori  | filter.agreement.update  |
      | rankings     | Asset     | ranking.asset.ori     | ranking.asset.update     |
      | rankings     | Agreement | ranking.Agreement.ori | ranking.Agreement.update |
      | optimisation | Rules     | opt.rule.ori          | opt.rule.update          |


  @MengliHuang @AuditReport @T34252 @Proxied
  Scenario Outline: audit settlement date report create
    When navigate to collateral static data settlement data page
    And add collateral static data settlement data <settlement.data>
    And navigate to Audit Settlement Details Report
    And search Audit Settlement Details Report audit.settlement.detail.report.filter
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule reportRule
    Examples:
      | settlement.data             |
      | settlement.data.create.agmt |
      | settlement.data.create.prin |
      | settlement.data.create.org  |

  @MengliHuang @AuditReport @T34252 @Proxied
  Scenario Outline: audit settlement date report update
    When navigate to collateral static data settlement data page
    And search collateral static data settlement data <settlement.data.search.filter>
    And edit collateral static data settlement data <settlement.data.search.result> to <settlement.data.new>
    And approve collateral static data settlement data <settlement.data.search.result>
    And navigate to Audit Settlement Details Report
    And search Audit Settlement Details Report audit.settlement.detail.report.filter
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule reportRule
    Examples:
      | settlement.data.search.filter       | settlement.data.search.result | settlement.data.new                |
      | settlement.data.search.counterparty | settlement.data.search.result | settlement.data.update.agmt        |
      | settlement.data.search.principal    | settlement.data.search.result | settlement.data.update.principal   |
      | settlement.data.search.orgnisation  | settlement.data.search.result | settlement.data.update.orgnisation |

  @MengliHuang @AuditReport @T34253 @Proxied
  Scenario Outline: audit statement report create fcm repo
    When add <businessLine> agreements <agreement.content>
    And view statement
    And edit summary exposure info
    And view product type <productType> on exposure summary page
    And add <businessLine> trades <trade.info1>,<trade.info2>
    And quick link - view statement
    And edit asset summary info
    And view asset type cashbookingtype1 agreement asset CASH Page
    And add call booking - statement booking <cashbooking1>
    And edit call booking <booking.summary> to <booking.update>
    And click back button to previous page
    And view asset type bondbookingtype1 agreement asset Bond Page
    And add call booking - statement booking <bondbooking1>
    And click back button to previous page
    And view asset type cashbookingtype1 agreement asset Cash Movements Page
    And add <interesttype> booking - statement booking <interestbooking1>
    And quick link - view statement
    And approve agreement <agreement.content>
    And navigate to Audit statements Report
    And search Audit Statements Report audit.statement.by.cpty
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule reportRule
    Examples:
      | businessLine | agreement.content               | productType  | trade.info1                | trade.info2    | cashbooking1 | booking.summary | booking.update    | bondbooking1     | interesttype | interestbooking1     |
      | FCM          | agreement.fcm.with.all.contents | product.QTP  | trade.with.full.content.1m | trade.with.-2m | booking.cash | booking.summary | update.to.settled | booking.security | TSA          | booking.cashmovement |
      | Repo         | agreement.repo                  | product.pro1 | trade.with.full.content.3m | trade.with.-2m | booking.cash | booking.summary | update.to.settled | booking.security | net interest | booking.cashmovement |

  @MengliHuang @AuditReport @T34253 @Proxied
  Scenario Outline: audit statement report create etf multimodel
    When add <businessLine> agreements <agreement.content>
    And view statement
    And edit summary exposure info
    And view product type <productType> on exposure summary page
    And add <businessLine> trades <trade.info1>,<trade.info2>
    And quick link - view statement
    And select <select.type> <agreement.statement>
    And edit asset summary info
    And view asset type cashbookingtype1 agreement asset CASH Page
    And add call booking - statement booking <cashbooking1>,<cashbooking2>
    And click back button to previous page
    And view asset type bondbookingtype1 agreement asset Bond Page
    And add call booking - statement booking <bondbooking1>,<bondbooking2>
    And click back button to previous page
    And view asset type cashbookingtype1 agreement asset Cash Movements Page
    And add <interesttype> booking - statement booking <interestbooking1>
    And add <interesttype> booking - statement booking <interestbooking2>
    And add <interesttype> booking - statement booking <interestbooking3>
    And add <interesttype> booking - statement booking <interestbooking4>
    And edit cashmovement booking <cashmovement.summary1> to <interestbooking2.update>
    And edit cashmovement booking <cashmovement.summary2> to <interestbooking4.update>
    And quick link - view statement
    And approve agreement <agreement.content>
    And navigate to Audit statements Report
    And search Audit Statements Report audit.statement.by.cpty
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule reportRule
    Examples:
      | businessLine | agreement.content    | productType | trade.info1   | trade.info2    | agreement.statement          | select.type | cashbooking1  | cashbooking2  | bondbooking1      | bondbooking2      | interesttype   | interestbooking1                 | interestbooking2                    | interestbooking3                           | interestbooking4                           | cashmovement.summary1 | cashmovement.summary2         | interestbooking2.update                 | interestbooking4.update                           |
      | ETF          | agreement.etf        | product.etf | trade.with.1m | trade.with.-2m | etfAgreementStatement        | order       | booking.cash1 | booking.cash2 | booking.security1 | booking.security2 | Cash Component | booking.cashmovement.fee.pending | booking.cashmovement.fee.settled    | booking.cashmovement.cashcomponent.pending | booking.cashmovement.cashcomponent.settled | booking.fee.summary   | booking.cashcomponent.summary | booking.cashmovement.fee.settled.update | booking.cashmovement.cashcomponent.settled.update |
      | OTC          | agreement.multimodel | product.qtp | trade.with.1m | trade.with.-2m | multimodelAgreementStatement | model       | booking.cash1 | booking.cash2 | booking.security1 | booking.security2 | net interest   | booking.cashmovement.pay.settled | booking.cashmovement.cappay.pending | booking.cashmovement.receive.pending       | booking.cashmovement.capreceive.settled    | booking.pay.summary   | booking.receive.summary       | booking.cashmovement.pay.settled.update | booking.cashmovement.capreceive.settled.update    |


  @MengliHuang @AuditReport @T34253 @Proxied @23222
  Scenario: audit statement report create OTC
    When add OTC agreements agreement.otc.not.net.gross
    And view statement
    And edit summary exposure info
    And view product type product.QTP on exposure summary page
    And add OTC trades trade.with.1m,trade.with.-2m
    And quick link - view statement
    And edit asset summary info
    And view asset type cashbookingtype1 agreement asset CASH Page
    And add call booking - statement booking booking.cash1,booking.cash2
    And edit call booking booking.cash2.summary to booking.cash2.update
    And click back button to previous page
    And view asset type bondbookingtype1 agreement asset Bond Page
    And add call booking - statement booking booking.security1,booking.security2
    And edit call booking booking.security2.summary to booking.security2.update
    And quick link - view statement
    And approve agreement agreement.otc.not.net.gross
    And feed external IA external.ia by excel
    And navigate to Audit statements Report
    And search Audit Statements Report audit.statement.by.cpty
    And run report to auditReportPath as Excel Worksheet
    Then assert Excel Worksheet report auditReportPath should follow the rule reportRule

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for OTC Delta New Trade Feed by Task Manager
    When feed delta trades delta.feed.otc.trade by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feedtrades.and.qtp
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed delta trades feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feedtrades.and.qtp
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed delta trades feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    When search agreement search.agr.for.delta.new.trade.feed
    And view completed agreement statement search.agr.for.delta.new.trade.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <trade.detail.as.feed> detail
    Then trade detail should be <trade.detail.as.feed>
    Examples:
    | feed.file.format | trade.detail.as.feed            |
    | excel            | trade.date.20170509.amount.1000 |
    | csv              | trade.date.20170510.amount.2000 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for OTC Delta Update Trade Feed by Task Manager
    When feed delta trades delta.feed.otc.trade by <feed.file.format>
    Then feed log should be feed.result.parsing.error
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feedtrades.and.qtp
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule

    When feed delta trades feed.modified.correct.staging.report.to.update by <feed.file.format>
    Then feed log should be feed.result.successfully.modified

    When search agreement search.agr.for.delta.update.trade.feed
    And view completed agreement statement search.agr.for.delta.update.trade.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <trade.detail.as.feed> detail
    Then trade detail should be <trade.detail.as.feed>
    Examples:
      | feed.file.format | trade.detail.as.feed            |
      | excel            | trade.date.20170509.amount.1000 |
      | csv              | trade.date.20170510.amount.2000 |
#add xlsx
  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for OTC Flush Trade Feed by Task Manager
    When navigate to organisation global preferences page
    And set organisation global preferences feed.system.for.f13658.flush.trade1
    And feed flush trades flush.feed.otc.trade by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feedtrades.and.new.created.feed.system
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed flush trades flush.feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feedtrades.and.new.created.feed.system
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed flush trades flush.feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    And search agreement search.agr.for.flush.trade.feed
    And view completed agreement statement search.agr.for.flush.trade.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <check.flush.feed.trade.details> detail
    Then trade detail should be <check.flush.feed.trade.details>
    Examples:
      | feed.file.format | check.flush.feed.trade.details  |
      | excel            | trade.date.20170509.amount.1000 |
      | csv              | trade.date.20170510.amount.2000 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for OTC Delta New Trade Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.delta.trade
    And copy trade feed <task.scheduler.file.format> file task.feed.delta.new.trade for task.scheduler.import.delta.trade to file.name
    And run task scheduler task.scheduler.import.delta.trade
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feedtrades.and.qtp
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And copy trade feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.delta.trade to file.name
    And run task scheduler task.scheduler.import.delta.trade
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feedtrades.and.qtp
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And copy trade feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.delta.trade to file.name
    And run task scheduler task.scheduler.import.delta.trade
    Then task scheduler messaging should be <task.scheduler.import.successful>

    When search agreement search.agr.for.delta.new.trade.feed
    And view completed agreement statement search.agr.for.delta.new.trade.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <trade.detail.as.feed> detail
    Then trade detail should be <trade.detail.as.feed>
    Examples:
      | task.scheduler.file.format | task.scheduler.import.successful | trade.detail.as.feed            |
      | excel                      | feed.xls.successfuly             | trade.date.20170509.amount.1000 |
      | csv                        | feed.csv.successfuly             | trade.date.20170510.amount.2000 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for OTC Delta Update Trade Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.delta.trade
    And copy trade feed <task.scheduler.file.format> file task.feed.otc.delta.update.trade for task.scheduler.import.delta.trade to file.name
    And run task scheduler task.scheduler.import.delta.trade
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report bad.staging.by.feedsystemandbusinessline
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And copy trade feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.delta.trade to file.name
    And run task scheduler task.scheduler.import.delta.trade
    Then task scheduler messaging should be task.scheduler.successful

    When search agreement search.agr.for.delta.update.trade.feed
    And view completed agreement statement search.agr.for.delta.update.trade.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <trade.detail.as.feed> detail
    Then trade detail should be <trade.detail.as.feed>
    Examples:
      | task.scheduler.file.format | trade.detail.as.feed            |
      | excel                      | trade.date.20170509.amount.1000 |
      | csv                        | trade.date.20170510.amount.2000 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for OTC Flush Trade Feed by Task Scheduler
    When navigate to organisation global preferences page
    And set organisation global preferences feed.system.for.f13658.flush.trade1
    And navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.flush.trade
    And copy trade feed <task.scheduler.file.format> file task.flush.feed.otc.trade for task.scheduler.import.flush.trade to file.name
    And run task scheduler task.scheduler.import.flush.trade
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feedtrades.and.new.created.feed.system
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.flush.trade
    And copy trade feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.flush.trade to file.name
    And run task scheduler task.scheduler.import.flush.trade
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feedtrades.and.new.created.feed.system
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.flush.trade
    And copy trade feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.flush.trade to file.name
    And run task scheduler task.scheduler.import.flush.trade
    Then task scheduler messaging should be <task.scheduler.import.successful>

    When search agreement search.agr.for.flush.trade.feed
    And view completed agreement statement search.agr.for.flush.trade.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <check.flush.feed.trade.details> detail
    Then trade detail should be <check.flush.feed.trade.details>
    Examples:
      | task.scheduler.file.format | task.scheduler.import.successful | check.flush.feed.trade.details  |
      | excel                      | feed.xls.successfuly             | trade.date.20170509.amount.1000 |
      | csv                        | feed.csv.successfuly             | trade.date.20170510.amount.2000 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for OTC MTM Feed by Task Manager
    When feed mtm feed.otc.mtm by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.mtm
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed mtm feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.mtm
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed mtm feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    When search agreement search.agr.for.mtm.feed
    And view completed agreement statement search.agr.for.mtm.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <check.mtm.feed.trade.details> detail
    Then trade detail should be <check.mtm.feed.trade.details>
    Examples:
      | feed.file.format | check.mtm.feed.trade.details      |
      | excel            | trade.date.20170509.amount.599.99 |
      | csv              | trade.date.20170510.amount.588.88 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for OTC MTM Feed by Task Scheduler
    When navigate to task scheduler page
    When task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.mtm
    And copy MTM feed <task.scheduler.file.format> file task.feed.otc.mtm for task.scheduler.import.mtm to file.name
    And run task scheduler task.scheduler.import.mtm
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.mtm
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.mtm
    And copy MTM feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.mtm to file.name
    And run task scheduler task.scheduler.import.mtm
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.mtm
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    And navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.mtm
    And copy MTM feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.mtm to file.name
    And run task scheduler task.scheduler.import.mtm
    Then task scheduler messaging should be <task.scheduler.successful>

    When search agreement search.agr.for.mtm.feed
    And view completed agreement statement search.agr.for.mtm.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <check.mtm.feed.trade.details> detail
    Then trade detail should be <check.mtm.feed.trade.details>
    Examples:
      | task.scheduler.file.format | task.scheduler.successful | check.mtm.feed.trade.details      |
      | excel                      | feed.xls.successful       | trade.date.20170509.amount.599.99 |
      | csv                        | feed.csv.successful       | trade.date.20170510.amount.588.88 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for Repo Delta New Trade Feed by Task Manager
    When feed delta Repo trades feed.repo.trade by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.qtp.repo
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed delta Repo trades feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.qtp.repo
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed delta Repo trades feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    When search agreement search.agr.for.repo.delta.new.trade.feed
    And view completed agreement statement search.agr.for.repo.delta.new.trade.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <trade.detail.as.feed> detail
    Then trade detail should be <trade.detail.as.feed>
    Examples:
      | feed.file.format | trade.detail.as.feed            |
      | excel            | trade.date.20170509.amount.1000 |
      | csv              | trade.date.20170510.amount.2000 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for Repo Delta New Trade Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.repo.delta.trade
    And copy repo trade feed <task.scheduler.file.format> file task.feed.repo.delta.new.trade for task.scheduler.import.repo.delta.trade to file.name
    And run task scheduler task.scheduler.import.repo.delta.trade
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.qtp.repo
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.repo.delta.trade
    And copy repo trade feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.repo.delta.trade to file.name
    And run task scheduler task.scheduler.import.repo.delta.trade
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.qtp.repo
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.repo.delta.trade
    And copy repo trade feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.repo.delta.trade to file.name
    And run task scheduler task.scheduler.import.repo.delta.trade
    Then task scheduler messaging should be <task.scheduler.import.successful>

    When search agreement search.agr.for.repo.delta.new.trade.feed
    And view completed agreement statement search.agr.for.repo.delta.new.trade.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <trade.detail.as.feed> detail
    Then trade detail should be <trade.detail.as.feed>
    Examples:
      | task.scheduler.file.format | task.scheduler.import.successful | trade.detail.as.feed            |
      | excel                      | feed.xls.successfuly             | trade.date.20170509.amount.1000 |
      | csv                        | feed.csv.successfuly             | trade.date.20170510.amount.2000 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for Repo Delta Update Trade Feed by Task Manager
    When feed delta Repo trades feed.repo.trade by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report bad.staging.by.feedsystemandbusinessline
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed delta Repo trades feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    And search agreement search.agr.for.repo.delta.update.trade.feed
    And view completed agreement statement search.agr.for.repo.delta.update.trade.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <trade.detail.as.feed> detail
    Then trade detail should be <trade.detail.as.feed>
    Examples:
      | feed.file.format | trade.detail.as.feed            |
      | excel            | trade.date.20170509.amount.1000 |
      | csv              | trade.date.20170510.amount.2000 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for Repo Delta Update Trade Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.repo.delta.trade
    And copy repo trade feed <task.scheduler.file.format> file task.feed.repo.delta.update.trade for task.scheduler.import.repo.delta.trade to file.name
    And run task scheduler task.scheduler.import.repo.delta.trade
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report bad.staging.by.feedsystemandbusinessline
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And copy repo trade feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.repo.delta.trade to file.name
    And run task scheduler task.scheduler.import.repo.delta.trade
    Then task scheduler messaging should be task.scheduler.successful

    And search agreement search.agr.for.repo.delta.update.trade.feed
    And view completed agreement statement search.agr.for.repo.delta.update.trade.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <trade.detail.as.feed> detail
    Then trade detail should be <trade.detail.as.feed>
    Examples:
    | task.scheduler.file.format | trade.detail.as.feed            |
    | excel                      | trade.date.20170509.amount.1000 |
    | csv                        | trade.date.20170510.amount.2000 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for Repo Flush Trade Feed by Task Manager
    When navigate to organisation global preferences page
    And set organisation global preferences feed.system.for.f13658.flush.trade1
    And feed flush Repo trades flush.feed.repo.trade by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.qtp.repo
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed flush Repo trades feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.qtp.repo
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed flush Repo trades feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    And search agreement search.agr.for.repo.flush.trade.feed
    And view completed agreement statement search.agr.for.repo.flush.trade.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <trade.detail.as.feed> detail
    Then trade detail should be <trade.detail.as.feed>
    Examples:
    | feed.file.format | trade.detail.as.feed            |
    | excel            | trade.date.20170509.amount.1000 |
    | csv              | trade.date.20170510.amount.2000 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for Repo Flush Trade Feed by Task Scheduler
    When navigate to organisation global preferences page
    And set organisation global preferences feed.system.for.f13658.flush.trade1
    And navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.repo.flush.trade
    And copy repo trade feed <task.scheduler.file.format> file task.flush.feed.repo.trade for task.scheduler.import.repo.flush.trade to file.name
    And run task scheduler task.scheduler.import.repo.flush.trade
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.qtp.repo
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.repo.flush.trade
    And copy repo trade feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.repo.flush.trade to file.name
    And run task scheduler task.scheduler.import.repo.flush.trade
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.qtp.repo
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.repo.flush.trade
    And copy repo trade feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.repo.flush.trade to file.name
    And run task scheduler task.scheduler.import.repo.flush.trade
    Then task scheduler messaging should be <task.scheduler.successful>

    And search agreement search.agr.for.repo.flush.trade.feed
    And view completed agreement statement search.agr.for.repo.flush.trade.feed.result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <trade.detail.as.feed> detail
    Then trade detail should be <trade.detail.as.feed>
    Examples:
      | task.scheduler.file.format | task.scheduler.successful | trade.detail.as.feed            |
      | excel                      | feed.xls.successful       | trade.date.20170509.amount.1000 |
      | csv                        | feed.csv.successful       | trade.date.20170510.amount.2000 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for Repo MTM Feed by Task Manager
    When feed repo mtm feed.repo.mtm by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.qtp.repo
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed repo mtm feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.qtp.repo
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed repo mtm feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    And search agreement search.agr.for.repo.mtm.feed
    And view completed agreement statement search.agr.for.repo.mtm.feed.result
    And approve agreement approve.statement.status
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <check.repo.mtm.feed.trade.details> detail
    Then trade detail should be <check.repo.mtm.feed.trade.details>
    Examples:
      | feed.file.format | check.repo.mtm.feed.trade.details |
      | excel            | trade.date.20170509.amount.999   |
      | csv              | trade.date.20170510.amount.888   |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline: Bad Staging Feed Report for Repo MTM Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.repo.mtm
    And copy MTM feed <task.scheduler.file.format> file task.feed.repo.mtm for task.scheduler.import.repo.mtm to file.name
    And run task scheduler task.scheduler.import.repo.mtm
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.qtp.repo
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    And navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.repo.mtm
    And copy MTM feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.repo.mtm to file.name
    And run task scheduler task.scheduler.import.repo.mtm
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.qtp.repo
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    And navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.repo.mtm
    And copy MTM feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.repo.mtm to file.name
    And run task scheduler task.scheduler.import.repo.mtm
    Then task scheduler messaging should be <task.scheduler.successful>

    And search agreement search.agr.for.repo.mtm.feed
    And view completed agreement statement search.agr.for.repo.mtm.feed.result
    And approve agreement approve.statement.status
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And view trade <check.repo.mtm.feed.trade.details> detail
    Then trade detail should be <check.repo.mtm.feed.trade.details>
    Examples:
      | task.scheduler.file.format | task.scheduler.successful | check.repo.mtm.feed.trade.details |
      | excel                      | feed.xls.successful       | trade.date.20170509.amount.777   |
      | csv                        | feed.csv.successful       | trade.date.20170510.amount.666   |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario: Bad Staging Feed Report for ETF Trade Feed
    Given have organisation global preferences organisation.global.preference.default
    When add ETF agreements manual.agr.etf1
    When feed delta ETF trades feed.etf.trade1 by excel
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report bad.staging.by.feedsystemandbusinessline
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario: Bad Staging Feed Report for ETF MTM Feed
    Given have organisation global preferences organisation.global.preference.default
    When add ETF agreements manual.agr.etf2
    And view statement
    And edit summary exposure info
    And view product type Product.etf.basket.del on exposure summary page
    And add Repo trades manual.etf.trade1
    And feed mtm etf.mtm by excel
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report bad.staging.by.feedsystemandbusinessline
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Agreements Feed
    Given have organisation global preferences organisation.global.preference.default
    When feed agreements <feed.agreements> by <file.format>
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report <bad.staging.filter.for.agreements.feed>
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule

    Examples:
    | feed.agreements      | file.format | bad.staging.filter.for.agreements.feed |
    | feed.otc.agreement1  | xml         | filter.otc                             |
    | feed.fcm.agreement1  | excel       | filter.fcm                             |
    | feed.repo.agreement1 | excel       | filter.repo                            |
    | feed.etf.agreement1  | excel       | filter.etf                             |
    | feed.etd.agreement1  | excel       | filter.etd                             |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Agreement Ratings Feed by Task Manager
    When feed agreement ratings feed.agr.rating by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.agr.ratings
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed agreement ratings feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.agr.ratings
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed agreement ratings feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    And search agreement search.agr.for.agr.rating.feed
    And edit agreement search.agr.for.agr.rating.feed.result in agreement search page
    Then agreement summary � fixed trigger should be <check.agr.rating.details>
    Examples:
      | feed.file.format | check.agr.rating.details                        |
      | excel            | threshold.255.55.mintransfer.55.55.rounding.100 |
      | csv              | threshold.255.56.mintransfer.55.56.rounding.50  |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Agreement Ratings Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.agreement.rating
    And copy Agreement Rating feed <task.scheduler.file.format> file task.feed.agreement.rating for task.scheduler.import.agreement.rating to file.name
    And run task scheduler task.scheduler.import.agreement.rating
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.agr.ratings
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.agreement.rating
    And copy Agreement Rating feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.agreement.rating to file.name
    And run task scheduler task.scheduler.import.agreement.rating
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.agr.ratings
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.agreement.rating
    And copy Agreement Rating feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.agreement.rating to file.name
    And run task scheduler task.scheduler.import.agreement.rating
    Then task scheduler messaging should be <task.scheduler.import.successful>

    And search agreement search.agr.for.agr.rating.feed
    And edit agreement search.agr.for.agr.rating.feed.result in agreement search page
    Then agreement summary � fixed trigger should be <check.agr.rating.details>
    Examples:
      | task.scheduler.file.format | task.scheduler.import.successful | check.agr.rating.details                        |
      | excel                      | feed.xls.successful              | threshold.255.56.mintransfer.55.56.rounding.100 |
      | csv                        | feed.csv.successful              | threshold.255.57.mintransfer.55.57.rounding.50  |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario:Bad Staging Feed Report for Agreement UDF Feed
    Given have organisation global preferences organisation.global.preference.default
    When add OTC agreements manual.agr.without.udf

    And feed agreement UDF feed.agr.udf.no.legalid.and.agreementid by xml
    Then feed log should be feed.agr.udf.unsuccessful

    When feed agreement UDF feed.agr.udf.no.legalid by xml
    Then feed log should be feed.agr.udf.successful

    When feed agreement UDF feed.agr.udf.no.agreementid by xml
    Then feed log should be feed.agr.udf.successful

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Asset Booking Feed
    Given have organisation global preferences organisation.global.preference.default
    When add <business.line> agreements <add.agreements>
    And feed asset bookings <feed.asset.bookings> by <file.format>
    Then feed log should be feed.asset.booking.parsing
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report <bad.staging.filter.for.agreements.feed>
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule

  Examples:
    | business.line | add.agreements  | feed.asset.bookings      | file.format |
    | OTC           | add.otc.agr     | feed.asset.bookings.otc  | excel       |
    | FCM           | add.fcm.agr     | feed.asset.bookings.fcm  | xml         |
    | Repo          | add.repo.agr    | feed.asset.bookings.repo | excel       |
    | ETF           | add.etf.agr     | feed.asset.bookings.etf  | excel       |
#    | ETD           | add.etd.agr     | feed.asset.bookings.etd  | excel       |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Asset Pricing Feed by Task Manager
    When feed asset pricings feed.asset.pricing by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.asset.pricing
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed asset pricings feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.asset.pricing
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed asset pricings feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    When navigate to security search page
    And search security search.ins.F13658InsForAssetPricing
    Then security search.ins.F13658InsForAssetPricing.result should be <price.and.date.updated>
    Examples:
      | feed.file.format | price.and.date.updated       |
      | excel            | price.99.and.date.2017.05.09 |
      | csv              | price.98.and.date.2017.05.08 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Asset Pricing Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.asset.pricing
    And copy Asset Pricing feed <task.scheduler.file.format> file task.feed.asset.pricing for task.scheduler.import.asset.pricing to file.name
    And run task scheduler task.scheduler.import.asset.pricing
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.asset.pricing
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1
#
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.asset.pricing
    And copy Asset Pricing feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.asset.pricing to file.name
    And run task scheduler task.scheduler.import.asset.pricing
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.asset.pricing
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.asset.pricing
    And copy Asset Pricing feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.asset.pricing to file.name
    And run task scheduler task.scheduler.import.asset.pricing
    Then task scheduler messaging should be <task.scheduler.successful>

    When navigate to security search page
    And search security search.ins.F13658InsForAssetPricing
    Then security search.ins.F13658InsForAssetPricing.result should be <price.and.date.updated>
    Examples:
      | task.scheduler.file.format | task.scheduler.successful | price.and.date.updated       |
      | excel                      | feed.xls.successful       | price.88.and.date.2017.05.10 |
      | csv                        | feed.csv.successful       | price.89.and.date.2017.05.09 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Asset Ratings Feed by Task Manager
    When feed asset ratings feed.asset.rating by <feed.file.format>
    Then feed log should be feed.asset.rating.parsing
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.asset.ratings
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule

    When feed asset ratings feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    When navigate to security search page
    And search security search.ins.F13658InsForAssetRating
    Then security search.ins.F13658InsForAssetRating.result should be <sandp.moodys.fitch.updated>
    Examples:
      | feed.file.format | sandp.moodys.fitch.updated   |
      | excel            | update.to.AAA.Aaa.A+         |
      | csv              | price.98.and.date.2017.05.08 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario:Bad Staging Feed Report for Asset Ratings Feed by Task Scheduler
    When have organisation global preferences organisation.global.preference.default
    And navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.asset.rating
    And copy Asset Rating feed excel file task.feed.asset.rating for task.scheduler.import.asset.rating to file.name
    And run task scheduler task.scheduler.import.asset.rating
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.asset.ratings
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.asset.rating
    And copy Asset Rating feed excel file task.feed.modified.correct.staging.report for task.scheduler.import.asset.rating to file.name
    And run task scheduler task.scheduler.import.asset.rating
    Then task scheduler messaging should be task.scheduler.successful

    When navigate to security search page
    And search security search.ins.F13658InsForAssetRating
    Then security search.ins.F13658InsForAssetRating.result should be ins.F13658InsForAssetRating.details

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Counterparty Amount Feed by Task Manager
    When feed counterparty amount feed.cpty.amount by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.cpty.amount
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed counterparty amount feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.cpty.amount
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed counterparty amount feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    And search agreement search.agr.for.cpty.amount.feed
    And view completed agreement statement search.agr.for.cpty.amount.feed.result
    And quick link - agreement exposure management
    Then Exposure Management - event should be <cpty.amount.updated>
    Examples:
    | feed.file.format | cpty.amount.updated        |
    | excel            | call.event.cpty.amount.999 |
    | csv              | call.event.cpty.amount.998 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Counterparty Amount Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.cpty.amount
    And copy counterparty amount feed <task.scheduler.file.format> file task.feed.cpty.amount for task.scheduler.import.cpty.amount to file.name
    And run task scheduler task.scheduler.import.cpty.amount
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.cpty.amount
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.cpty.amount
    And copy counterparty amount feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.cpty.amount to file.name
    And run task scheduler task.scheduler.import.cpty.amount
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.cpty.amount
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.cpty.amount
    And copy counterparty amount feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.cpty.amount to file.name
    And run task scheduler task.scheduler.import.cpty.amount
    Then task scheduler messaging should be task.scheduler.import.successful

    And search agreement search.agr.for.cpty.amount.feed
    And view completed agreement statement search.agr.for.cpty.amount.feed.result
    And quick link - agreement exposure management
    Then Exposure Management - event should be <cpty.amount.updated>
    Examples:
      | task.scheduler.file.format | cpty.amount.updated        |
      | excel                      | call.event.cpty.amount.888 |
      | csv                        | call.event.cpty.amount.887 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for FX Rate Feed by Task Manager
    When feed FX rates feed.fx.rate by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.fx.rate
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed FX rates feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.fx.rate
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed FX rates feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    When navigate to FX rate page
    Then fx rate set should be <check.fx.rate>
    Examples:
      | feed.file.format | check.fx.rate    |
      | excel            | fx.rate.gbp.0.833333 |
      | csv              | fx.rate.gbp.0.833334 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for FX Rate Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.fx.rates
    And copy FX rate feed <task.scheduler.file.format> file task.feed.fx.rates for task.scheduler.import.fx.rates to file.name
    And run task scheduler task.scheduler.import.fx.rates
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.fx.rate
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.fx.rates
    And copy FX rate feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.fx.rates to file.name
    And run task scheduler task.scheduler.import.fx.rates
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.fx.rate
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.fx.rates
    And copy FX rate feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.fx.rates to file.name
    And run task scheduler task.scheduler.import.fx.rates
    Then task scheduler messaging should be task.scheduler.import.successful

    When navigate to FX rate page
    Then fx rate set should be <check.fx.rate>
    Examples:
      | task.scheduler.file.format | check.fx.rate        |
      | excel                      | fx.rate.gbp.0.944444 |
      | csv                        | fx.rate.gbp.0.944445 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for External Independent Amount Feed by Task Manager
    When feed external IA feed.external.IA by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.external.ia
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed external IA feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.external.ia
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed external IA feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    And search agreement search.agr.for.external.ia.feed
    And edit agreement search.agr.for.external.ia.feed.result in agreement search page
    Then agreement summary � products should be <external.ia.amount.updated>
    Examples:
      | feed.file.format | external.ia.amount.updated |
      | excel            | external.ia.amount.100.36  |
      | csv              | external.ia.amount.100.37  |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for External Independent Amount Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.external.IA
    And copy External IA feed <task.scheduler.file.format> file task.feed.external.IA for task.scheduler.import.external.IA to file.name
    And run task scheduler task.scheduler.import.external.IA
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.external.ia
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.external.IA
    And copy External IA feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.external.IA to file.name
    And run task scheduler task.scheduler.import.external.IA
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.external.ia
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.external.IA
    And copy External IA feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.external.IA to file.name
    And run task scheduler task.scheduler.import.external.IA
    Then task scheduler messaging should be task.scheduler.successful

    And search agreement search.agr.for.external.ia.feed
    And edit agreement search.agr.for.external.ia.feed.result in agreement search page
    Then agreement summary � products should be <external.ia.amount.updated>
    Examples:
    | task.scheduler.file.format | external.ia.amount.updated |
    | excel                      | external.ia.amount.99.35   |
    | csv                        | external.ia.amount.99.36   |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Holiday Centre Feed by Task Manager
    When feed holiday centres feed.holiday.centre by <feed.file.format>
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.holiday.centre
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed holiday centres feed.modified.incorrect.staging.report by <feed.file.format>
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.holiday.centre
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed holiday centres feed.modified.correct.staging.report by <feed.file.format>

    When navigate to holiday centre page
    And edit holiday centre HolidayCentreForF13658
    Then holiday centre should be <holiday.centre.updated>
    Examples:
    | feed.file.format | holiday.centre.updated       |
    | excel            | bankholiday.2017-05-09.added |
    | csv              | bankholiday.2017-05-10.added |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Holiday Centre Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.holiday.centre
    And copy holiday centres feed <task.scheduler.file.format> file task.feed.holiday.centre for task.scheduler.import.holiday.centre to file.name
    And run task scheduler task.scheduler.import.holiday.centre
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.holiday.centre
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.holiday.centre
    And copy holiday centres feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.holiday.centre to file.name
    And run task scheduler task.scheduler.import.holiday.centre
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.holiday.centre
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.holiday.centre
    And copy holiday centres feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.holiday.centre to file.name
    And run task scheduler task.scheduler.import.holiday.centre
    Then task scheduler messaging should be task.scheduler.import.successful

    When navigate to holiday centre page
    And edit holiday centre HolidayCentreForF13658
    Then holiday centre should be <holiday.centre.updated>
    Examples:
      | task.scheduler.file.format | holiday.centre.updated       |
      | xlsx                       | bankholiday.2017-05-11.added |
      | csv                        | bankholiday.2017-05-12.added |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Interest Amount Feed by Task Manager
    When feed interest amount feed.interest.amount by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.interest.amount
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed interest amount feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.interest.amount
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed interest amount feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    When navigate to interest manager search page
    And Interest Manager - search interest event by search.interest.event.by.agr.description
    And Interest Manager - switch to Pay tab
    Then Interest Manager event.id - event should be <interest.amount.updated>
    Examples:
      | feed.file.format | interest.amount.updated |
      | excel            | interest.amount.-1.05   |
      | csv              | interest.amount.-1.06   |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Interest Amount Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.interest.amount
    And copy interest amount feed <task.scheduler.file.format> file task.feed.interest.amount for task.scheduler.import.interest.amount to file.name
    And run task scheduler task.scheduler.import.interest.amount
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.interest.amount
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.interest.amount
    And copy interest amount feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.interest.amount to file.name
    And run task scheduler task.scheduler.import.interest.amount
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.interest.amount
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.interest.amount
    And copy interest amount feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.interest.amount to file.name
    And run task scheduler task.scheduler.import.interest.amount
    Then task scheduler messaging should be <task.scheduler.successful>

    When navigate to interest manager search page
    And Interest Manager - search interest event by search.interest.event.by.agr.description
    And Interest Manager - switch to Pay tab
    Then Interest Manager event.id - event should be <interest.amount.updated>
    Examples:
      | task.scheduler.file.format | task.scheduler.successful | interest.amount.updated |
      | excel                      | feed.xls.successful       | interest.amount.-1.04   |
      | csv                        | feed.csv.successful       | interest.amount.-1.03   |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Interest Rate Feed by Task Manager
    When feed interest rates feed.interest.rate by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.interest.rate
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed interest rates feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.interest.rate
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed interest rates feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful
    Examples:
      | feed.file.format |
      | excel            |
      | csv              |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Interest Rate Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.interest.rate
    And copy interest rate feed <task.scheduler.file.format> file task.feed.interest.rate for task.scheduler.import.interest.rate to file.name
    And run task scheduler task.scheduler.import.interest.rate
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.interest.rate
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.interest.rate
    And copy interest rate feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.interest.rate to file.name
    And run task scheduler task.scheduler.import.interest.rate
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.interest.rate
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.interest.rate
    And copy interest rate feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.interest.rate to file.name
    And run task scheduler task.scheduler.import.interest.rate
    Then task scheduler messaging should be <task.scheduler.successful>
    Examples:
      | task.scheduler.file.format | task.scheduler.successful |
      | excel                      | feed.xls.successful       |
      | csv                        | feed.csv.successful       |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for NAV Feed by Task Manager
    When feed NAV feed.NAV by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.nav
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed NAV feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.nav
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed NAV feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    And search agreement search.agr.for.nav.feed
    And edit agreement search.agr.for.nav.feed.result in agreement search page
    Then agreement summary � documentation should be <nav.principal.and.cpty.updated>
    Examples:
      | feed.file.format | nav.principal.and.cpty.updated |
      | excel            | nav.principal.1000.cpty.2000   |
      | csv              | nav.principal.3000.cpty.4000   |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for NAV Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.NAV
    And copy NAV feed <task.scheduler.file.format> file task.feed.NAV for task.scheduler.import.NAV to file.name
    And run task scheduler task.scheduler.import.NAV
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.nav
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.NAV
    And copy NAV feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.NAV to file.name
    And run task scheduler task.scheduler.import.NAV
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.nav
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.NAV
    And copy NAV feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.NAV to file.name
    And run task scheduler task.scheduler.import.NAV
    Then task scheduler messaging should be <task.scheduler.successful>

    And search agreement search.agr.for.nav.feed
    And edit agreement search.agr.for.nav.feed.result in agreement search page
    Then agreement summary � documentation should be <nav.principal.and.cpty.updated>
    Examples:
      | task.scheduler.file.format | task.scheduler.successful | nav.principal.and.cpty.updated |
      | excel                      | feed.xls.successful       | nav.principal.999.cpty.1888    |
      | csv                        | feed.csv.successful       | nav.principal.998.cpty.1887    |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Portfolio Cashflow Feed by Task Manager
    When feed portfolio Cashflow feed.portfolio.cashflow by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.portfolio.cashflow
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed portfolio Cashflow feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.unsuccessful

    When feed portfolio Cashflow feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    And search agreement search.agr.for.cashflow.feed
    And view completed agreement statement search.agr.for.cashflow.feed.result
    Then agreement statement should be <upfront.fee.updated>
    Examples:
    | feed.file.format | upfront.fee.updated       |
    | excel            | upfront.fee.equals.to.999 |
    | csv              | upfront.fee.equals.to.998 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Portfolio Cashflow Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.portfolio.cashflow
    And copy portfolio TSA feed <task.scheduler.file.format> file task.feed.portfolio.cashflow for task.scheduler.import.portfolio.cashflow to file.name
    And run task scheduler task.scheduler.import.portfolio.cashflow
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.portfolio.cashflow
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.portfolio.cashflow
    And copy portfolio TSA feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.portfolio.cashflow to file.name
    And run task scheduler task.scheduler.import.portfolio.cashflow
    Then task scheduler messaging should be <task.scheduler.unsuccessful>

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.portfolio.cashflow
    And copy portfolio TSA feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.portfolio.cashflow to file.name
    And run task scheduler task.scheduler.import.portfolio.cashflow
    Then task scheduler messaging should be <task.scheduler.successful>

    And search agreement search.agr.for.cashflow.feed
    And view completed agreement statement search.agr.for.cashflow.feed.result
    Then agreement statement should be <upfront.fee.updated>
    Examples:
      | task.scheduler.file.format | task.scheduler.unsuccessful | task.scheduler.successful | upfront.fee.updated       |
      | excel                      | feed.xls.unsuccessful       | feed.xls.successful       | upfront.fee.equals.to.888 |
      | csv                        | feed.csv.unsuccessful       | feed.csv.successful       | upfront.fee.equals.to.887 |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Repo Settlement Feed by Task Manager
    When feed repo settlement feed.repo.settlement by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.repo.settlement
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed repo settlement feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.repo.settlement
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed repo settlement feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    And search agreement search.agr.for.repo.settlement.feed
    And view completed agreement statement search.agr.for.repo.settlement.feed.result
    And edit summary exposure info
    And view product type product.type.qtp.pro1 on exposure summary page
    And view trade <check.trade.settlement.start.leg.and.close.leg> detail
    Then trade detail should be <check.trade.settlement.start.leg.and.close.leg>
    Examples:
      | feed.file.format | check.trade.settlement.start.leg.and.close.leg |
      | excel            | start.leg.pending.and.close.leg.settled        |
      | csv              | start.leg.settled.and.close.leg.pending        |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Repo Settlement Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.repo.settlement
    And copy repo settlement feed <task.scheduler.file.format> file task.feed.repo.settlement for task.scheduler.import.repo.settlement to file.name
    And run task scheduler task.scheduler.import.repo.settlement
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.repo.settlement
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.repo.settlement
    And copy repo settlement feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.repo.settlement to file.name
    And run task scheduler task.scheduler.import.repo.settlement
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.repo.settlement
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2
#
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.repo.settlement
    And copy repo settlement feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.repo.settlement to file.name
    And run task scheduler task.scheduler.import.repo.settlement
    Then task scheduler messaging should be <task.scheduler.successful>

    And search agreement search.agr.for.repo.settlement.feed
    And view completed agreement statement search.agr.for.repo.settlement.feed.result
    And edit summary exposure info
    And view product type product.type.qtp.pro1 on exposure summary page
    And view trade <check.trade.settlement.start.leg.and.close.leg> detail
    Then trade detail should be <check.trade.settlement.start.leg.and.close.leg>
    Examples:
      | task.scheduler.file.format | task.scheduler.successful | check.trade.settlement.start.leg.and.close.leg |
      | excel                      | feed.xls.successful       | start.leg.failed.and.close.leg.pending         |
      | csv                        | feed.csv.successful       | start.leg.pending.and.close.leg.failed         |


  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Static Data Feed by Task Manager
    When feed system static data feed.static.data by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.static.data
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed system static data feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.static.data
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed system static data feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

    When navigate to collateral static data page
    Then collateral static data should be <static.data.updated>
    Examples:
      | feed.file.format | static.data.updated                                    |
      | excel            | static.data.F13658TestName1.add.to.MasterDocumentation |
      | csv              | static.data.F13658TestName2.add.to.MasterDocumentation |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Static Data Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.static.data
    And copy static data feed <task.scheduler.file.format> file task.feed.static.data for task.scheduler.import.static.data to file.name
    And run task scheduler task.scheduler.import.static.data
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.static.data
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.static.data
    And copy static data feed <task.scheduler.file.format> file task.feed.modified.incorrect.staging.report for task.scheduler.import.static.data to file.name
    And run task scheduler task.scheduler.import.static.data
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.static.data
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.static.data
    And copy static data feed <task.scheduler.file.format> file task.feed.modified.correct.staging.report for task.scheduler.import.static.data to file.name
    And run task scheduler task.scheduler.import.static.data
    Then task scheduler messaging should be <task.scheduler.successful>

    When navigate to collateral static data page
    Then collateral static data should be <static.data.updated>
    Examples:
      | task.scheduler.file.format | task.scheduler.successful | static.data.updated                                    |
      | excel                      | feed.xls.successful       | static.data.F13658TestName3.add.to.MasterDocumentation |
      | csv                        | feed.csv.successful       | static.data.F13658TestName4.add.to.MasterDocumentation |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Flush ETD Balance Feed by Task Manager
#    When add ETD agreements manual.add.etd.agreement
#    And view statement
#    And approve agreement manual.add.etd.agreement
    When feed flush etd balances feed.etd.balances by <feed.file.format>
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.etd.balances
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed flush etd balances feed.modified.incorrect.staging.report by <feed.file.format>
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.etd.balances
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed flush etd balances feed.modified.correct.staging.report by <feed.file.format>
    Then feed log should be feed.result.successful

#    When navigate to agreement search page
#    And search agreement search.agr.for.etd.balances.feed
#    And view completed agreement statement search.agr.for.etd.balances.feed.result
#    Then agreement statement should be model1.sec.balances.details
#    Then multi model agreement statement summary should be \S+
    Examples:
      | feed.file.format |
      | excel            |
      | csv              |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario Outline:Bad Staging Feed Report for Flush ETD Balance Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.flush.etd.balances
    And copy etd balances feed <task.scheduler.file.format> file task.feed.etd.balances for task.scheduler.import.flush.etd.balances to file.name
    And run task scheduler task.scheduler.import.flush.etd.balances
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.etd.balances
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.flush.etd.balances
    And copy etd balances feed <task.scheduler.file.format> file feed.modified.incorrect.staging.report for task.scheduler.import.flush.etd.balances to file.name
    And run task scheduler task.scheduler.import.flush.etd.balances
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.etd.balances
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.flush.etd.balances
    And copy etd balances feed <task.scheduler.file.format> file feed.modified.correct.staging.report for task.scheduler.import.flush.etd.balances to file.name
    And run task scheduler task.scheduler.import.flush.etd.balances
    Then task scheduler messaging should be <task.scheduler.successful>
    Examples:
      | task.scheduler.file.format | task.scheduler.successful |
      | excel                      | feed.xls.successful       |
      | csv                        | feed.csv.successful       |

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario:Bad Staging Feed Report for Delta ETD Balance Feed by Task Manager
#    When add ETD agreements manual.add.etd.agreement
#    And view statement
#    And approve agreement manual.add.etd.agreement
    When feed flush etd balances feed.etd.balances by excel
    Then feed log should be feed.result.parsing.error1
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.etd.balances
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When feed flush etd balances feed.modified.incorrect.staging.report by excel
    Then feed log should be feed.result.parsing.error2
    When navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.etd.balances
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When feed flush etd balances feed.modified.correct.staging.report by excel
    Then feed log should be feed.result.successful

#    When navigate to agreement search page
#    And search agreement search.agr.for.etd.balances.feed
#    And view completed agreement statement search.agr.for.etd.balances.feed.result
#    Then agreement statement should be model1.sec.balances.details
#    Then multi model agreement statement summary should be \S+

  @BinHu @Feed @15.2.0 @F13658 @Proxied @wip
  Scenario:Bad Staging Feed Report for Delta ETD Balance Feed by Task Scheduler
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.delta.etd.balances
    And copy etd balances feed excel file task.feed.etd.balances for task.scheduler.import.delta.etd.balances to file.name
    And run task scheduler task.scheduler.import.delta.etd.balances
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.etd.balances
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule1

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.delta.etd.balances
    And copy etd balances feed excel file feed.modified.incorrect.staging.report for task.scheduler.import.flush.etd.balances to file.name
    And run task scheduler task.scheduler.import.flush.etd.balances
    And navigate to Bad Staging Feed Report
    And search Bad Staging Feed Report search.by.feed.etd.balances
    And run report to badStagingFeedReportPath as Excel Worksheet
    Then assert Excel Worksheet report badStagingFeedReportPath should follow the rule reportRule2

    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And edit task scheduler task.scheduler.import.delta.etd.balances
    And copy etd balances feed excel file feed.modified.correct.staging.report for task.scheduler.import.flush.etd.balances to file.name
    Then task scheduler messaging should be task.scheduler.import.successful

#    When navigate to agreement search page
#    And search agreement search.agr.for.etd.balances.feed
#    And view completed agreement statement search.agr.for.etd.balances.feed.result
#    Then agreement statement should be model1.sec.balances.details

