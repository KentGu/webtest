**Colline Test Repository Project**
===
A place to store all the automation test cases/scenarios/data and the other relevant automation test documents

**Guideline**
===
1.	restore oracle or sqlserver database with dump files located at \\sha-nas-a\CollineQA\database backup\automation

2.	QC available automation status: BDD automated, ReportCompare automated, LetterCompare automated, unachievable.

3.	QC automation owner.

4.	Test scenario grouped by Module, e.g. Feeds.feature, AssetHoldings.feature, Reports.feature, WebService.feature, TaskScheduler.feature.

5.	Tags:

* Feature tags:
    * 1)	Main module e.g. @AssetHoldings  @Feeds @Reports @Task @WebService @TaskScheduler

* Scenario tags:
    * 1)	create version @14.1.0 @14.1.3
    * 2)	sub module or across module, e.g. @FeedAgreements @AgreementReports @TaskManager @Reports
    * 3)	automation owner by **full name**: e.g. @YanLu @KentGu @MengliHuang
    * 4)	feature/defect id @F12345 @D19485
    * 5)	Test case id @T30281
    * 6)	@wip @todo @ignore

6.	Split test cases by task in feeds module to task module in TaskScheduler.feature (the same to other cases, say a step named “repeat previous steps by task scheduler”).

7.	Consider move some test cases from ‘Letter’ module to ‘Letter Compare’ project.

8.	Proposed productivity 3 **effective automation scenarios** / day.
    * 1)	A scenario approved by Sydney review (functional coverage and readability)
    * 2)	A scenario approved by Kenny’s review (technical approach, e.g. WebServiceHelper)
    * 3)	A scenario passed by historical daily build validation (shown in test report)