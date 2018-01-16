package com.lombardrisk.data;

public enum ReportMenu {
    BATCH("Batches>Batch"), ASSET_HOLDINGS_AND_VALUATION(
            "Collateral Assets>Asset Holdings and Valuation"), ASSET_MANAGEMENT(
            "Collateral Assets>Asset Management"), ASSET_SETTLEMENTS(
            "Collateral Assets>Asset Settlements"),COLLATERAL_AVAILABILITY(
            "Collateral Assets>Collateral Availability"), CONCENTRATION_LIMITS(
            "Collateral Assets>Concentration Limits"), CORPORATE_ACTIONS(
            "Collateral Assets>Corporate Actions"), INELIGIBLE_ASSET(
            "Collateral Assets>Ineligible Asset"), INVENTORY_MANAGER(
            "Collateral Assets>Inventory Manager"), REHYPOTHECATION_ELIGIBILITY(
            "Collateral Assets>Rehypothecation Eligibility"), SECURITY_INSTRUMENTS(
            "Collateral Assets>Security Instruments"), AUDIT_AUDIT_AGREEMENTS(
            "Collateral Audit>Audit Agreements"), AUDIT_ASSET_DEFINITION(
            "Collateral Audit>Audit Asset Definition"), AUDIT_ASSETS(
            "Collateral Audit>Audit Assets"), AUDIT_ELIGIBILITY_RULE_TEMPLATE(
            "Collateral Audit>Audit Eligibility Rule Template"), AUDIT_HOLIDAY_CENTRES(
            "Collateral Audit>Audit Holiday Centres"), AUDIT_INTEREST_RATES(
            "Collateral Audit>Audit Interest Rates"), AUDIT_OPTIMISATION_RULE(
            "Collateral Audit>Audit Optimisation Rule"), AUDIT_ORGANISATIONS(
            "Collateral Audit>Audit Organisations"), AUDIT_ROLES_ADMINISTRATION(
            "Collateral Audit>Audit Roles Administration"), AUDIT_SETTLEMENT_DETAILS(
            "Collateral Audit>Audit Settlement Details"), AUDIT_STATEMENTS(
            "Collateral Audit>Audit Statements"), AUDIT_TRADES(
            "Collateral Audit>Audit Trades"), AUDIT_USER_ADMINISTRATION(
            "Collateral Audit>Audit User Administration"), FREQUENCY_DISTRIBUTION_LOG(
            "Collateral Audit>Frequency Distribution Log"), AGREEMENTS(
            "Collateral Documentation>Agreements"), ELIGIBILITY_RULES_TEMPLATE(
            "Collateral Documentation>Eligibility Rules Template"), HISTORICAL_FX_RATES(
            "Collateral Documentation>Historical FX Rates"), HISTORICAL_INTEREST_RATE(
            "Collateral Documentation>Historical Interest Rate"), LICENSING(
            "Collateral Documentation>Licensing"), ORGANISATION_AGREEMENTS(
            "Collateral Documentation>Organisation Agreements"),ORGANISATION_THRESHOLD(
            "Collateral Documentation>Organisation Threshold"), SETTLEMENT_INSTRUCTIONS(
            "Collateral Documentation>Settlement Instructions"), BAD_STAGING_FEED(
            "Collateral Operations>Bad Staging Feed"), CASH_AND_ACCRUAL(
            "Collateral Operations>Cash And Accrual"), INTEREST_APPLIED(
            "Collateral Operations>Interest Applied"), INTEREST_CALCULATIONS_SCHEDULED(
            "Collateral Operations>Interest Calculations Scheduled"), INTEREST_CHANGES(
            "Collateral Operations>Interest Changes"), MONTH_END(
            "Collateral Operations>Month End"), REJECTED_TRADES(
            "Collateral Operations>Rejected Trades"), REPOETFSBL_REJECTED_TRADES(
            "Collateral Operations>RepoETFSBL Rejected Trades"), REPOETFSBL_TRADES_OUTPUT(
            "Collateral Operations>RepoETFSBL Trades Output"), TRADES_OUTPUT(
            "Collateral Operations>Trades Output"), DAILY_EXPOSURE(
            "Collateral Risk Mgt>Daily Exposure"), DISPUTE(
            "Collateral Risk Mgt>Dispute"),DISPUTE_HISTORY(
            "Collateral Risk Mgt>Dispute History"), HISTORICAL_EXPOSURE(
            "Collateral Risk Mgt>Historical Exposure"), INTERNAL_REVIEW(
            "Collateral Risk Mgt>Internal Review"), MIS(
            "Collateral Risk Mgt>MIS"), MTM_COMPARISON(
            "Collateral Risk Mgt>MtM Comparison"), REPOETFSBL_DAILY_EXPOSURE(
            "Collateral Risk Mgt>RepoETFSBL Daily Exposure"), REPOETFSBL_HISTORICAL_EXPOSURE(
            "Collateral Risk Mgt>RepoETFSBL Historical Exposure"), CASHFLOW(
            "Collateral Risk Mgt>Cashflow"), WHAT_IF_SCENARIO(
            "Collateral Risk Mgt>What If Scenario"), OPTIMISATION_RESULT(
            "Optimisation>Optimisation Result"), OPTIMISATION_RULE(
            "Optimisation>Optimisation Rule"), MANUALLY_OVERRIDDEN_ASSET_PRICE(
            "Pricing Control>Manually Overridden Asset Price"), PRICE_AGE_EXCEPTIONS(
            "Pricing Control>Price Age Exceptions"), PRICE_EXCEPTIONS(
            "Pricing Control>Price Exceptions"), PRICE_VARIANCE_EXCEPTIONS(
            "Pricing Control>Price Variance Exceptions"), RECONCILIATION_OUTPUT(
            "Reconciliation>Reconciliation Output"), RECONCILIATION_TRADES_OUTPUT(
            "Reconciliation>Reconciliation Trades Output"), STP_DASHBOARD(
            "STP>Stp Dashboard"), USER_PROFILE(
            "User>User Profile"), ROLE_APPROVAL(
            "User>Role Approval");

    private final String name;

    ReportMenu(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
