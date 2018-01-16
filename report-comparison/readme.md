**Colline Report Comparison Test**
===
A place to store all the report comparison test cases/scenarios/data and the other relevant automation test documents

**Guideline**
===
1.  restore database with dump files located at \\sha-nas-a\CollineQA\database backup\automation\report-compare

2.  test.properties，该文件夹将保存批量对比生成的html的结果
	<resultDir>D:\a\b\c\</resultDir>
	例如该配置，生成的对比结果将放在 D:\a\b\c\目录下
	请注意：文件夹最后请用斜杠 \ 结尾
	
	<StatisticsFile>D:\StatisticsReport.xls</StatisticsFile>
	case执行统计结果会存储到该文件里面，便于和QC同步

3.  suites/report-compare.xml，该文件将配置执行哪些模块Report对比
	<Suite>
		<!-- golden file文件夹所在路径 -->
		<parameter name="goldenDir" value="D:/svndoc/trunk/Data/Reports/GoldenData/" />

		<!-- result file 文件夹所在路径 -->
		<parameter name="resultDir" value="D:/svndoc/trunk/Data/Reports/ResultData/" />

		<test name="AHV">
			<!-- 需要对比的文件夹名字-->
			<parameter name="folderName" value="AHV" />
			
			<!--想要忽略的列，多列请用竖线 | 分割开来，忽略的列将不参与对比-->
			<parameter name="excludeColumns" value="FX Rate" />
			
			<!--想定义为Key的列，多列的话请用竖线 | 分割开来-->
			<parameter name="keyColumns" value="" />
			
			<!--用以执行对比的Class，请不要做任何修改-->
			<classes>
				<class name="com.woxiangbo.projects.mycompare.SimpleTest" />
			</classes>
		</test>
	</Suite>
	每一个<test>...</test>表示一个执行模块，如果只想执行某个特定的模块，可以把其它的 <test>...</test>注释掉。
	注释请用 XML 注释语法 <!--开头,-->结尾。

4.  mvn clean verify
	执行完毕，可以在当前目录的 target目录下，找到surefire-reports文件夹，找到 index.html,即可查看到所有对比结果
	这里对比结果链接的是在步骤3中的文件，只是看起来更规范，更整齐。也可以直接打开步骤3所配置的文件夹路径，一个一个查看

	NOTICE：在对比结果页面，如果有任何的error信息，则标示对比结果不可信，请根据error信息提示后，重新执行命令并对比
