package com.lombardrisk.test;

import com.lombardrisk.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.yiwan.webcore.test.ITestDataManager;
import org.yiwan.webcore.util.JaxbHelper;
import org.yiwan.webcore.util.PropHelper;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.*;

/**
 * @author Kenny Wang
 */
public class TestDataManager implements ITestDataManager {
    private final static Logger logger = LoggerFactory.getLogger(TestDataManager.class);
    private DataBean testData;
    private String systemData;
    private String featureId;
    private String scenarioId;
    private String targetDataFolder;

    public TestDataManager(String systemData, String featureId, String scenarioId, String targetDataFolder) {
        this.systemData = systemData;
        this.featureId = featureId;
        this.scenarioId = scenarioId;
        this.targetDataFolder = targetDataFolder;
    }

    private static InputStream getDataSchemaStream() {
        return ClassLoader.getSystemResourceAsStream(PropHelper.DATA_SCHEMA_FILE);
    }

    public DataBean getTestData() {
        return testData;
    }

    private InputStream getSystemDataStream() {
        return ClassLoader.getSystemResourceAsStream(systemData);
    }

    private InputStream getSourceScenarioDataStream(String fileName) throws FileNotFoundException {
        String src = PropHelper.SOURCE_SCENARIO_DATA_FOLDER + fileName;
        InputStream is = ClassLoader.getSystemResourceAsStream(src);
        if (is == null) {
            throw new FileNotFoundException(String.format("source scenario data file %s was not found", src));
        }
        return is;
    }

    private InputStream getFeatureDataStream(String fileName) throws FileNotFoundException {
        String src = PropHelper.FEATURE_DATA_FOLDER + fileName;
        InputStream is = ClassLoader.getSystemResourceAsStream(src);
        if (is == null) {
            throw new FileNotFoundException(String.format("feature data file %s was not found", src));
        }
        return is;
    }

    private XMLReader getXMLReader(final String featureFileName) throws ParserConfigurationException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setXIncludeAware(true);
        spf.setNamespaceAware(true);
        XMLReader xr = spf.newSAXParser().getXMLReader();
        xr.setEntityResolver(((publicId, systemId) -> {
            if (systemId.contains("/system/") || systemId.contains("\\system\\")) {
                logger.debug("referencing system data " + systemData);
                return new InputSource(getSystemDataStream());
            } else if (systemId.contains("/feature/") || systemId.contains("\\feature\\")) {
                logger.debug("referencing feature data " + PropHelper.FEATURE_DATA_FOLDER + featureFileName);
                return new InputSource(getFeatureDataStream(featureFileName));
            }
            return null;
        }));
//        xr.setEntityResolver(new EntityResolver() {
//            @Override
//            public InputSource resolveEntity(String publicId, String systemId)
//                    throws SAXException, IOException {
//                if (systemId.contains("/system/") || systemId.contains("\\system\\")) {
//                    logger.debug("referencing system data " + systemData);
//                    return new InputSource(getSystemDataStream());
//                } else if (systemId.contains("/feature/") || systemId.contains("\\feature\\")) {
//                    logger.debug("referencing feature data " + PropHelper.FEATURE_DATA_FOLDER + featureFileName);
//                    return new InputSource(getFeatureDataStream(featureFileName));
//                }
//                return null;
//            }
//        });
        return xr;
    }

    /**
     * unmarshal test data to test java object
     */
    public void unmarshalTestData() throws Exception {
        String scenarioFileName = scenarioId + ".xml";
        File tar = new File(targetDataFolder + scenarioFileName);
        if (Boolean.parseBoolean(PropHelper.getProperty("breakpoint.debugging.support")) && tar.exists()) {
            logger.debug("using target scenario data");
            unmarshalTargetData(tar);
        } else {
            logger.debug("using source scenario data");
            String featureFileName = featureId + ".xml";
            unmarshalSourceData(featureFileName, scenarioFileName);
        }
    }

    /**
     * unmarshal source scenario data to test java object
     */
    private void unmarshalSourceData(String featureFileName, String scenarioFileName) throws Exception {
        logger.debug("unmarshalling source scenario data " + scenarioFileName);
        InputStream src = getSourceScenarioDataStream(scenarioFileName);
        InputStream xsd = getDataSchemaStream();
        XMLReader xr = getXMLReader(featureFileName);
        SAXSource sax = new SAXSource(xr, new InputSource(src));
        testData = JaxbHelper.unmarshal(sax, xsd, DataBean.class);
    }

    /**
     * unmarshal target scenario data to test java object
     */
    private void unmarshalTargetData(File targetScenarioData) throws Exception {
        logger.debug("unmarshalling target scenario data " + targetScenarioData.getAbsolutePath());
        InputStream src = new FileInputStream(targetScenarioData);
        InputStream xsd = getDataSchemaStream();
        testData = JaxbHelper.unmarshal(src, xsd, DataBean.class);
    }

    /**
     * marshal target test data bean into target scenario data file
     */
    public void marshalTestData() throws IOException, JAXBException {
        String scenarioFileName = scenarioId + ".xml";
        File tar = new File(targetDataFolder, scenarioFileName);
        logger.debug("marshalling scenario data to " + tar.getAbsolutePath());
        JaxbHelper.marshal(testData, tar);
    }

    public StringType getVariable(String id) throws Exception {
        StringType ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getVariable(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public LoginCredential getLoginCredential(String id) throws Exception {
        LoginCredential ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getLoginCredential(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public StaticData getOrganisationStaticData(String id) throws Exception {
        StaticData ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getOrganisationStaticData(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public StaticData getCollateralStaticData(String id) throws Exception {
        StaticData ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getCollateralStaticData(id)) != null) {
                return ret;
            }
        }
        return null;
    }
    public StaticData getUdfValue(String id) throws Exception{
        StaticData ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getUdfValue(id)) != null) {
                return ret;
            }
        }
        return null;
    }
    public StaticData getSystemStaticData(String id) throws Exception {
        StaticData ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getSystemStaticData(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AgencyRating getAgencyRating(String id) throws Exception {
        AgencyRating ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAgencyRating(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public OrganisationBook getOrganisationBook(String id) throws Exception {
        OrganisationBook ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getOrganisationBook(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public OrganisationIndustry getOrganisationIndustry(String id) throws Exception {
        OrganisationIndustry ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getOrganisationIndustry(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public OrganisationSearch getOrganisationSearch(String id) throws Exception {
        OrganisationSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getOrganisationSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public Organisation getOrganisation(String id) throws Exception {
        Organisation ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getOrganisation(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public OrganisationSearchResult getOrganisationSearchResult(String id) throws Exception {
        OrganisationSearchResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getOrganisationSearchResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public OrganisationGlobalPreference getOrganisationGlobalPreference(String id) throws Exception {
        OrganisationGlobalPreference ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getOrganisationGlobalPreference(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FxRateSet getFxRateSet(String id) throws Exception {
        FxRateSet ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFxRateSet(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public Instrument getInstrument(String id) throws Exception {
        Instrument ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getInstrument(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AgreementSearch getAgreementSearch(String id) throws Exception {
        AgreementSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAgreementSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AgreementSearchResult getAgreementSearchResult(String id) throws Exception {
        AgreementSearchResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAgreementSearchResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AgreementSearchResult getApprovalManagerStatementSearchResult(String id) throws Exception {
        AgreementSearchResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getApprovalManagerStatementSearchResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public Agreement getAgreement(String id) throws Exception {
        Agreement ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAgreement(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AgreementSummary getAgreementSummary(String id) throws Exception {
        AgreementSummary ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAgreementSummary(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AgreementStatement getAgreementStatement(String id) throws Exception {
        AgreementStatement ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAgreementStatement(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public MultiModelAgreementStatement getMultiModelAgreementStatement(String id) throws Exception {
        MultiModelAgreementStatement ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getMultiModelAgreementStatement(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public EtdAgreementStatement getEtdAgreementStatement(String id) throws Exception {
        EtdAgreementStatement ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getEtdAgreementStatement(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public EtfAgreementStatement getEtfAgreementStatement(String id) throws Exception {
        EtfAgreementStatement ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getEtfAgreementStatement(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public ConcentrationLimitBreached getConcentrationLimitBreached(String id) throws Exception {
        ConcentrationLimitBreached ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getConcentrationLimitBreached(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public EligibilityRuleBreached getEligibilityRuleBreached(String id) throws Exception {
        EligibilityRuleBreached ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getEligibilityRuleBreached(id)) != null) {
                return ret;
            }
        }
        return null;
    }



    public TradeDetail getTradeDetail(String id) throws Exception {
        TradeDetail ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getTradeDetail(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public TradeSummary getTradeSummary(String id) throws Exception {
        TradeSummary ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getTradeSummary(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AgreementExposureSummary getAgreementExposureSummary(String id) throws Exception {
        AgreementExposureSummary ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAgreementExposureSummary(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public ExposureStatement getExposureStatement(String id) throws Exception {
        ExposureStatement ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getExposureStatement(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public BookingDetail getBookingDetail(String id) throws Exception {
        BookingDetail ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getBookingDetail(id)) != null) {
                return ret;
            }
        }
        return null;
    }





    public AssetBookingSummary getAssetBookingSummary(String id) throws Exception {
        AssetBookingSummary ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAssetBookingSummary(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public CollateralAssetHoldingSummary getCollateralAssetHoldingSummary(String id) throws Exception {
        CollateralAssetHoldingSummary ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getCollateralAssetHoldingSummary(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public CashMovementDetail getCashMovementBooking(String id) throws Exception {
        CashMovementDetail ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getCashMovementDetail(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public EligibilityRulesTemplate getEligibilityRulesTemplate(String id) throws Exception {
        EligibilityRulesTemplate ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getEligibilityRulesTemplate(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedAgreement getFeedAgreement(String id) throws Exception {
        FeedAgreement ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedAgreement(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedHolidayCentre getFeedHolidayCentre(String id) throws Exception {
        FeedHolidayCentre ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getFeedHolidayCentre(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public FeedWeekend getFeedWeekend(String id) throws Exception{
        FeedWeekend ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getFeedWeekend(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public FeedStaticData getFeedStaticData(String id) throws Exception{
        FeedStaticData ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getFeedStaticData(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public FeedEtdBalances getFeedEtdBalances(String id) throws Exception{
        FeedEtdBalances ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getFeedEtdBalances(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public FeedEtdAdjustment getFeedEtdAdjustment(String id) throws Exception{
        FeedEtdAdjustment ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getFeedEtdAdjustment(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public FeedFxRate getFeedFxRate(String id) throws Exception {
        FeedFxRate ret;
        for(DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getFeedFxRate(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public FeedNAV getFeedNAV(String id) throws Exception {
        FeedNAV ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getFeedNAV(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public FeedPortfolioTSA getFeedPortfolioTSA(String id) throws Exception{
        FeedPortfolioTSA ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getFeedPortfolioTSA(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public FeedPortfolioTSA getFeedPortfolioCashflow(String id) throws Exception{
        FeedPortfolioTSA ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = (FeedPortfolioTSA) com.lombardrisk.util.Biz.filterListById(data.getFeedPortfolioCashflow(), id)) != null){
                return ret;
            }
        }
        return null;
    }

    public FeedSettlementInstruction getFeedSettlementInstruction(String id) throws Exception{
        FeedSettlementInstruction ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getFeedSettlementInstruction(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public FeedRepoSettlement getFeedRepoSettlement(String id) throws Exception{
        FeedRepoSettlement ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getFeedRepoSettlement(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public FeedAgreementRating getFeedAgreementRating(String id) throws Exception {
        FeedAgreementRating ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedAgreementRating(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedAgreementUDF getFeedAgreementUDF(String id) throws Exception {
        FeedAgreementUDF ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedAgreementUDF(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedCounterpartyAmount getFeedCounterpartyAmount(String id) throws Exception {
        FeedCounterpartyAmount ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedCounterpartyAmount(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedExternalIA getFeedExternalIA(String id) throws Exception {
        FeedExternalIA ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedExternalIA(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedEligibilityRulesTemplate getFeedEligibilityRulesTemplate(String id) throws Exception {
        FeedEligibilityRulesTemplate ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedEligibilityRulesTemplate(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedOrganisation getFeedOrganisation(String id) throws Exception {
        FeedOrganisation ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedOrganisation(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedOrganisationContact getFeedOrganisationContact(String id) throws Exception {
        FeedOrganisationContact ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedOrganisationContact(id)) != null) {
                return  ret;
            }
        }
        return null;
    }

    public FeedSecurityTemplate getFeedSecurityTemplate(String id) throws Exception {
        FeedSecurityTemplate ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedSecurityTemplate(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedAssetBooking getFeedAssetBooking(String id) throws Exception {
        FeedAssetBooking ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedAssetBooking(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedAssetPricing getFeedAssetPricing(String id) throws Exception {
        FeedAssetPricing ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedAssetPricing(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedAssetRating getFeedAssetRating(String id) throws Exception {
        FeedAssetRating ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedAssetRating(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedInterestAmount getFeedInterestAmount(String id) throws Exception {
        FeedInterestAmount ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedInterestAmount(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedInterestRate getFeedInterestRate(String id) throws Exception {
        FeedInterestRate ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedInterestRate(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedInventoryManager getFeedInventoryManager(String id) throws Exception {
        FeedInventoryManager ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedInventoryManager(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedTrade getFeedTrade(String id) throws Exception {
        FeedTrade ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedTrade(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedRepoTrade getFeedRepoTrade(String id) throws Exception {
        FeedRepoTrade ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedRepoTrade(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedETFTrade getFeedETFTrade(String id) throws Exception {
        FeedETFTrade ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedETFTrade(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedResult getFeedResult(String id) throws Exception {
        FeedResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedMtM getFeedMtM(String id) throws Exception {
        FeedMtM ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedMtM(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public InterestRate getInterestRate(String id) throws Exception {
        InterestRate ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getInterestRate(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SettlementDataSearch getSettlementDataSearch(String id) throws Exception {
        SettlementDataSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getSettlementDataSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SettlementDataSearchResult getSettlementDataSearchResult(String id) throws Exception {
        SettlementDataSearchResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getSettlementDataSearchResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SettlementData getSettlementData(String id) throws Exception {
        SettlementData ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getSettlementData(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public UmbrellaRule getUmbrellaRule(String id) throws Exception {
        UmbrellaRule ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getUmbrellaRule(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public OrganisationSimpleSearch getApprovalManagerOrganisationSimpleSearch(String id) throws Exception {
        OrganisationSimpleSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getApprovalManagerOrganisationSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public ExposureEventSearch getExposureEventSearch(String id) throws Exception {
        ExposureEventSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getExposureEventSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public ExposureEventDetail getExposureEventDetail(String id) throws Exception {
        ExposureEventDetail ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getExposureEventDetail(id)) != null) {
                return ret;
            }
        }
        return null;
    }
    public ExposureManagementMessage getExposureManagementMessage(String id) throws Exception {
        ExposureManagementMessage ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getExposureManagementMessage(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AssetDefinition getAssetDefinition(String id) throws Exception {
        AssetDefinition ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAssetDefinition(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SecuritySearch getSecuritySearch(String id) throws Exception {
        SecuritySearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getSecuritySearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AssetHoldingsAndValuationReportFilter getAssetHoldingsAndValuationReportFilter(String id) throws Exception {
        AssetHoldingsAndValuationReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAssetHoldingsAndValuationReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AssetManagementReportFilter getAssetManagementReportFilter(String id) throws Exception {
        AssetManagementReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAssetManagementReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AssetSettlementReportFilter getAssetSettlementReportFilter(String id) throws Exception {
        AssetSettlementReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAssetSettlementReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }


    public CollateralAvailabilityReportFilter getCollateralAvailabilityReportFilter(String id) throws Exception {
        CollateralAvailabilityReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getCollateralAvailabilityReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public ConcentrationLimitsReportFilter getConcentrationLimitsReportFilter(String id) throws Exception {
        ConcentrationLimitsReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getConcentrationLimitsReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public CorporateActionsReportFilter getCorporateActionsReportFilter(String id) throws Exception {
        CorporateActionsReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getCorporateActionsReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public IneligibleAssetReportFilter getIneligibleAssetReportFilter(String id) throws Exception {
        IneligibleAssetReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getIneligibleAssetReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public InventoryManagerReportFilter getInventoryManagerReportFilter(String id) throws Exception {
        InventoryManagerReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getInventoryManagerReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public RehypothecationEligibilityReportFilter getRehypothecationEligibilityReportFilter(String id) throws Exception {
        RehypothecationEligibilityReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getRehypothecationEligibilityReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SecurityInstrumentsReportFilter getSecurityInstrumentsReportFilter(String id) throws Exception {
        SecurityInstrumentsReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getSecurityInstrumentsReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AuditAgreementsReportFilter getAuditAgreementsReportFilter(String id) throws Exception {
        AuditAgreementsReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAuditAgreementsReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AuditAssetDefinitionReportFilter getAuditAssetDefinitionReportFilter(String id) throws Exception {
        AuditAssetDefinitionReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAuditAssetDefinitionReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AuditAssetsReportFilter getAuditAssetsReportFilter(String id) throws Exception {
        AuditAssetsReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAuditAssetsReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AuditEligibilityRuleTemplateReportFilter getAuditEligibilityRuleTemplateReportFilter(String id) throws Exception {
        AuditEligibilityRuleTemplateReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAuditEligibilityRuleTemplateReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AuditHolidayCentresReportFilter getAuditHolidayCentresReportFilter(String id) throws Exception {
        AuditHolidayCentresReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAuditHolidayCentresReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AuditInterestRatesReportFilter getAuditInterestRatesReportFilter(String id) throws Exception {
        AuditInterestRatesReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAuditInterestRatesReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AuditOptimisationRuleReportFilter getAuditOptimisationRuleReportFilter(String id) throws Exception {
        AuditOptimisationRuleReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAuditOptimisationRuleReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AuditOrganisationsReportFilter getAuditOrganisationsReportFilter(String id) throws Exception {
        AuditOrganisationsReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAuditOrganisationsReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AuditRolesAdministrationReportFilter getAuditRolesAdministrationReportFilter(String id) throws Exception {
        AuditRolesAdministrationReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAuditRolesAdministrationReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AuditStatementsReportFilter getAuditStatementsReportFilter(String id) throws Exception {
        AuditStatementsReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAuditStatementsReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AuditTradesReportFilter getAuditTradesReportFilter(String id) throws Exception {
        AuditTradesReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAuditTradesReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AuditUserAdministrationReportFilter getAuditUserAdministrationReportFilter(String id) throws Exception {
        AuditUserAdministrationReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAuditUserAdministrationReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FrequencyDistributionLogReportFilter getFrequencyDistributionLogReportFilter(String id) throws Exception {
        FrequencyDistributionLogReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFrequencyDistributionLogReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AgreementsReportFilter getAgreementsReportFilter(String id) throws Exception {
        AgreementsReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getAgreementsReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public EligibilityRulesTemplateReportFilter getEligibilityRulesTemplateReportFilter(String id) throws Exception {
        EligibilityRulesTemplateReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getEligibilityRulesTemplateReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public HistoricalFxRatesReportFilter getHistoricalFxRatesReportFilter(String id) throws Exception {
        HistoricalFxRatesReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getHistoricalFxRatesReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public HistoricalInterestRateReportFilter getHistoricalInterestRateReportFilter(String id) throws Exception {
        HistoricalInterestRateReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getHistoricalInterestRateReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public LicensingReportFilter getLicensingReportFilter(String id) throws Exception {
        LicensingReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getLicensingReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }


    public ReportValidationRules getReportValidationRules(String id) throws Exception {
        ReportValidationRules ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getReportValidationRules(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public CollateralPreference getCollateralPreference(String id) throws Exception {
        CollateralPreference ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getCollateralPreference(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SystemPreference getSystemPreference(String id) throws Exception {
        SystemPreference ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getSystemPreference(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public InterestEventSearch getInterestEventSearch(String id) throws Exception {
        InterestEventSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getInterestEventSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public InterestEventDetail getInterestEventDetail(String id) throws Exception {
        InterestEventDetail ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getInterestEventDetail(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public MarginLetter getLetter(String id) throws Exception {
        MarginLetter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getMarginLetter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public LetterConfiguration getLetterConfiguration(String id) throws Exception {
        LetterConfiguration ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getLetterConfiguration(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public TsaRule getTsaRule(String id) throws Exception {
        TsaRule ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getTsaRule(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public TsaRule getCashflowRule(String id) throws Exception {
        TsaRule ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getCashflowRule(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public TaskSchedulerDetail getTaskSchedulerDetail(String id) throws Exception {
        TaskSchedulerDetail ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getTaskSchedulerDetail(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public UserFavourite getUserFavourite(String id) throws Exception {
        UserFavourite ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getUserFavourite(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public UserPreference getUserPreference(String id) throws Exception {
        UserPreference ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getUserPreference(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public HolidayCentre getHolidayCentre(String id) throws Exception {
        HolidayCentre ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getHolidayCentre(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public Region getRegion(String id) throws Exception {
        Region ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getRegion(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public Feed getFeed(String id) throws Exception {
        Feed ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeed(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public FeedStatus getFeedStatus(String id) throws Exception {
        FeedStatus ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getFeedStatus(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SecuritySearchResult getSecuritySearchResult(String id) throws Exception {
        SecuritySearchResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getSecuritySearchResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public StartupDashboard getStartupDashboard(String id) throws Exception {
        StartupDashboard ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getStartupDashboard(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public Dashboard getDashboard(String id) throws Exception {
        Dashboard ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getDashboard(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public EligibilityRulesTemplateSearchResult getEligibilityRulesTemplateSearchResult(String id) throws Exception {
        EligibilityRulesTemplateSearchResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getEligibilityRulesTemplateSearchResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public EligibilityRulesTemplateSearch getEligibilityRulesTemplateSearch(String id) throws Exception {
        EligibilityRulesTemplateSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getEligibilityRulesTemplateSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public EligibilityRulesTemplateSimpleSearch getApprovalManagerEligibilityRulesTemplateSimpleSearch(String id) throws Exception {
        EligibilityRulesTemplateSimpleSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerEligibilityRulesTemplateSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SettlementStatusDetail getSettlementStatusDetail(String id) throws Exception {
        SettlementStatusDetail ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getSettlementStatusDetail(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SecurityMovementSearch getSecurityMovementSearch(String id) throws Exception {
        SecurityMovementSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getSecurityMovementSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SecurityMovementSummary getSecurityMovementSummary(String id) throws Exception {
        SecurityMovementSummary ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getSecurityMovementSummary(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public InternalReviewSearch getInternalReviewSearch(String id) throws Exception {
        InternalReviewSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getInternalReviewSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public InternalReviewSummary getInternalReviewSummary(String id) throws Exception {
        InternalReviewSummary ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getInternalReviewSummary(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SettlementInstructionsSearch getApprovalManagerSettlementInstructionsSearch(String id) throws Exception {
        SettlementInstructionsSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerSettlementInstructions(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public CashMovementSummary getCashMovementSummary(String id) throws Exception {
        CashMovementSummary ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null && (ret = data.getCashMovementSummary(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public WorkflowSearchResult getApprovalManagerWorkflowSearchResult(String id) throws Exception {
        WorkflowSearchResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerWorkflowSearchResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AgreementSearch getApprovalManagerWorkflowSearch(String id) throws Exception {
        AgreementSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerWorkflowSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SettlementInstructionsSearchResult getApprovalManagerSettlementInstructionsSearchResult(String id) throws Exception {
        SettlementInstructionsSearchResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerSettlementInstructionsSearchResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AgreementSearch getApprovalsManagerAgreementSearch(String id) throws Exception {
        AgreementSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerAgreementSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AgreementSearch getApprovalsManagerStatementSearch(String id) throws Exception {
        AgreementSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerStatementSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public ApprovalManagerTradesSearch getApprovalManagerTradesSearch(String id) throws Exception {
        ApprovalManagerTradesSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerTradesSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public ApprovalManagerTradesSearchResult getApprovalManagerTradesSearchResult(String id) throws Exception {
        ApprovalManagerTradesSearchResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerTradesSearchResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SecuritySearch getApprovalsManagerSecuritySearch(String id) throws Exception {
        SecuritySearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerSecuritiesDataSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public OrganisationSearchResult getApprovalManagerOrganisationSearchResult(String id) throws Exception {
        OrganisationSearchResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerOrganisationSearchResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public AgreementSearchResult getApprovalManagerAgreementSearchResult(String id) throws Exception {
        AgreementSearchResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerAgreementSearchResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SecuritySearchResult getApprovalManagerSecuritySearchResult(String id) throws Exception {
        SecuritySearchResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerSecuritiesDataSearchResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public EligibilityRulesTemplateSearchResult getApprovalManagerEligibilityRulesTemplateSearchResult(String id) throws Exception {
        EligibilityRulesTemplateSearchResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getApprovalManagerEligibilityRulesTemplateSearchResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SettlementStatusSearch getSettlementStatusSearch(String id) throws Exception {
        SettlementStatusSearch ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getSettlementStatusSearch(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SettlementStatusSummary getSettlementStatusSummary(String id) throws Exception {
        SettlementStatusSummary ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getSettlementStatusSummary(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SavedReport getSavedReport(String id) throws Exception {
        SavedReport ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getSavedReport(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public PasswordChange getPasswordChange(String id) throws Exception{
        PasswordChange ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getPasswordChange(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public OrganisationAgreementsReportFilter getOrganisationAgreementsReportFilter(String id) throws Exception {
        OrganisationAgreementsReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getOrganisationAgreementsReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }
    public OrganisationThresholdReportFilter getOrganisationThresholdReportFilter(String id) throws Exception {
        OrganisationThresholdReportFilter ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getOrganisationThresholdReportFilter(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public SettlementInstructionsReportFilter getSettlementInstructionsReportFilter(String id) throws Exception{
        SettlementInstructionsReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getSettlementInstructionsReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public BadStagingFeedReportFilter getBadStagingFeedReportFilter(String id) throws Exception{
        BadStagingFeedReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getBadStagingFeedReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public CashAndAccrualReportFilter getCashAndAccrualReportFilter(String id) throws Exception{
        CashAndAccrualReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getCashAndAccrualReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public InterestAppliedReportFilter getInterestAppliedReportFilter(String id) throws Exception{
        InterestAppliedReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getInterestAppliedReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public InterestCalculationsScheduleReportFilter getInterestCalculationsScheduleReportFilter(String id) throws Exception{
        InterestCalculationsScheduleReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getInterestCalculationsScheduleReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public InterestChangesReportFilter getInterestChangesReportFilter(String id) throws Exception{
        InterestChangesReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getInterestChangesReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public MonthEndReportFilter getMonthEndReportFilter(String id) throws Exception{
        MonthEndReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getMonthEndReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public RejectedTradesReportFilter getRejectedTradesReportFilter(String id) throws Exception{
        RejectedTradesReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getRejectedTradesReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public RepoEtfsblTradesOutputReportFilter getRepoEtfsblTradesOutputReportFilter(String id) throws Exception{
        RepoEtfsblTradesOutputReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getRepoEtfsblTradesOutputReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public TradesOutputReportFilter getTradesOutputReportFilter(String id) throws Exception{
        TradesOutputReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getTradesOutputReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public DailyExposureReportFilter getDailyExposureReportFilter(String id) throws Exception{
        DailyExposureReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getDailyExposureReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public DisputeReportFilter getDisputeReportFilter(String id) throws Exception{
        DisputeReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getDisputeReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public DisputeHistoryReportFilter getDisputeHistoryReportFilter(String id) throws Exception{
        DisputeHistoryReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getDisputeHistoryReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public HistoricalExposureReportFilter getHistoricalExposureReportFilter(String id) throws Exception{
        HistoricalExposureReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getHistoricalExposureReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public InternalReviewReportFilter getInternalReviewReportFilter(String id) throws Exception{
        InternalReviewReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getInternalReviewReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public MisReportFilter getMisReportFilter(String id) throws Exception{
        MisReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getMisReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public MtmComparisonReportFilter getMtmComparisonReportFilter(String id) throws Exception{
        MtmComparisonReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getMtmComparisonReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public RepoEtfsblDailyExporesureReportFilter getRepoEtfsblDailyExporesureReportFilter(String id) throws Exception{
        RepoEtfsblDailyExporesureReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getRepoEtfsblDailyExporesureReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public RepoEtfsblHistoricalExporesureReportFilter getRepoEtfsblHistoricalExporesureReportFilter(String id) throws Exception{
        RepoEtfsblHistoricalExporesureReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getRepoEtfsblHistoricalExporesureReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public TsaReportFilter getTsaReportFilter(String id) throws Exception{
        TsaReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getTsaReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public TsaReportFilter getCashflowReportFilter(String id) throws Exception{
        TsaReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getCashflowReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public WhatIfScenarioReportFilter getWhatIfScenarioReportFilter(String id) throws Exception{
        WhatIfScenarioReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getWhatIfScenarioReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public OptimisationResultReportFilter getOptimisationResultReportFilter(String id) throws Exception{
        OptimisationResultReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getOptimisationResultReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public OptimisationRuleReportFilter getOptimisationRuleReportFilter(String id) throws Exception{
        OptimisationRuleReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getOptimisationRuleReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }




    public ManuallyOverriddenAssetPriceReportFilter getManuallyOverriddenAssetPriceReportFilter(String id) throws Exception{
        ManuallyOverriddenAssetPriceReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getManuallyOverriddenAssetPriceReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public PriceAgeExceptionsReportFilter getPriceAgeExceptionsReportFilter(String id) throws Exception{
        PriceAgeExceptionsReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getPriceAgeExceptionsReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public PriceExceptionsReportFilter getPriceExceptionsReportFilter(String id) throws Exception{
        PriceExceptionsReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getPriceExceptionsReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public PriceVarianceExceptionsReportFilter getPriceVarianceExceptionsReportFilter(String id) throws Exception{
        PriceVarianceExceptionsReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getPriceVarianceExceptionsReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }
    public ReconciliationOutputReportFilter getReconciliationOutputReportFilter(String id) throws Exception{
        ReconciliationOutputReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getReconciliationOutputReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }
    public ReconciliationTradesOutputReportFilter getReconciliationTradesOutputReportFilter(String id) throws Exception{
        ReconciliationTradesOutputReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getReconciliationTradesOutputReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }






    public StpDashboardReportFilter getStpDashboardReportFilter(String id) throws Exception{
        StpDashboardReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getStpDashboardReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public UserProfileReportFilter getUserProfileReportFilter(String id) throws Exception{
        UserProfileReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getUserProfileReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public RoleApprovalReportFilter getRoleApprovalReportFilter(String id) throws Exception{
        RoleApprovalReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getRoleApprovalReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public AuditSettlementDetailsReportFilter getAuditSettlementDetailsReportFilter(String id) throws Exception{
        AuditSettlementDetailsReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getAuditSettlementDetailsReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public RejectedTradesReportFilter getRepoEtfsblRejectedTradesReportFilter(String id) throws Exception{
        RejectedTradesReportFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getRepoEtfsblRejectedTradesReportFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public EventLogSearch getEventLogSearch(String id) throws Exception{
        EventLogSearch ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getEventLogSearch(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public User getUser(String id) throws Exception{
        User ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getUser(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public AdministerRole getAdministerRole(String id) throws Exception{
        AdministerRole ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getAdministerRole(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public ApprovalProfile getApprovalProfile(String id) throws Exception{
        ApprovalProfile ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getApprovalProfile(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public StpConfiguration getStpConfiguration(String id) throws Exception{
        StpConfiguration ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getStpConfigurations(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public SubstitutionEventSearch getSubstitutionEventSearch(String id) throws Exception{
        SubstitutionEventSearch ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getSubstitutionEventSearch(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public SubstitutionEventDetail getSubstitutionEventDetail(String id) throws Exception{
        SubstitutionEventDetail ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getSubstitutionEventDetail(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public AgreementExposureAdjustment getAgreementExposureAdjustment(String id) throws Exception{
        AgreementExposureAdjustment ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getAgreementExposureAdjustment(id)) != null){
                return ret;
            }
        }
        return null;
    }
    public AgreementIaValue getAgreementIaValue(String id) throws Exception{
        AgreementIaValue ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getAgreementIaValue(id)) != null){
                return ret;
            }
        }
        return null;
    }
    public ExposureManagementFilter getExposureManagementFilter(String id) throws Exception{
        ExposureManagementFilter ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getExposureManagementFilter(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public FilterType getInventoryManagerFilter(String id) throws Exception{
        FilterType ret;
        for(DataBeanType data : testData.getDataList()){
           if(data != null
                   && (ret = data.getInventoryManagerFilter(id)) != null){
               return ret;
           }
        }
        return null;
    }

    public TradeSearch getTradeSearch(String id) throws Exception{
        TradeSearch ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getTradeSearch(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public TradeSearchResult getTradeSearchResult(String id) throws Exception{
        TradeSearchResult ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getTradeSearchResult(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public StatementArchiveSearch getStatementArchiveSearch(String id) throws Exception{
        StatementArchiveSearch ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getStatementArchiveSearch(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public InterestReportDetails getInterestReportDetails(String id) throws Exception{
        InterestReportDetails ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getInterestReportDetails(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public TaskSchedulerMessage getTaskSchedulerMessage(String id) throws Exception{
        TaskSchedulerMessage ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getTaskSchedulerMessage(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public MarginLetterSummary getMarginLetterSummary(String id) throws Exception{
        MarginLetterSummary ret;
        for(DataBeanType data : testData.getDataList()){
            if(data != null
                    && (ret = data.getMarginLetterSummary(id)) != null){
                return ret;
            }
        }
        return null;
    }

    public SecurityMovementDetail getSecurityMovementDetail(String id) throws Exception {
        SecurityMovementDetail ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getSecurityMovementDetail(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public Privileges getPrivileges(String id) throws Exception {
        Privileges ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getPrivileges(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public OptimisationRule getOptimisationRule(String id) throws Exception {
        OptimisationRule ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getOptimisationRule(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public OptimisationRuleDetail getOptimisationRuleDetail(String id) throws Exception {
        OptimisationRuleDetail ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getOptimisationRuleDetail(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public OptimisationResultDetail getOptimisationResultDetail (String id) throws Exception {
        OptimisationResultDetail ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getOptimisationResultDetail(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public OptimisationResultDetail getOptimisationResultSearch (String id) throws Exception {
        OptimisationResultDetail ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getOptimisationResultDetail(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public DatabaseQuery getDatabaseQuery (String id) throws Exception {
        DatabaseQuery ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getDatabaseQuery(id)) != null) {
                return  ret;
            }
        }
        return null;
    }

    public DatabaseQueryResult getDatabaseQueryResult (String id) throws Exception {
        DatabaseQueryResult ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getDatabaseQueryResult(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public StatementCalcRule getStatementCalcRule (String id) throws Exception {
        StatementCalcRule ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getStatementCalcRule(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public MessageHandler getMessageHandler (String id) throws Exception {
        MessageHandler ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getMessageHandler(id)) != null) {
                return ret;
            }
        }
        return null;
    }

    public ExternalExposureList getExternalExposureList (String id) throws Exception {
        ExternalExposureList ret;
        for (DataBeanType data : testData.getDataList()) {
            if (data != null
                    && (ret = data.getExternalExposureList(id)) != null) {
                return ret;
            }
        }
        return null;
    }




}