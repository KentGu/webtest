Call writeConsole("Letter Comparison Start:")
'Call sendEmail(getAllFilesInFolderAndCovertPDFToWord())
Call writeResult(getCurrentFolder() & "\target", getAllFilesInFolderAndCovertPDFToWord())
Call writeConsole("Letter Comparison Done")

Function getAllFilesInFolderAndCovertPDFToWord()
	Dim folderPath : folderPath = getCurrentFolder() & "\"
	Dim resultMsg : resultMsg = "<html><body><p>Hi Team,</p><p>Please find the letter compare result below.</p><table style='width:75%;border:1px solid black;'><tr><th style='border:1px solid black'>File Name</th><th style='border:1px solid black'>Result</th><th style='border:1px solid black'>Remark</th></tr>"
	If Right(folderPath, 1) <> "\" Then
		folderPath = folderPath & "\"
	End If
	Dim goldenFolderPath, compareFolderPath, resultFolderPath
	goldenFolderPath = folderPath & "letters\GoldenVersion\" & readINI("Configuration", "goldenFileVersion") & "\"
	compareFolderPath = folderPath & "letters\CurrentVersion\"
	resultFolderPath = folderPath & "letters\Result\"
	Call renameFiles(compareFolderPath)
	Dim fso,goldenFolder
	Set fso = CreateObject("Scripting.FileSystemObject")
	Set goldenFolder = fso.GetFolder(goldenFolderPath)
	Call writeConsole("Starting compare files")
	For Each goldenFile In goldenFolder.Files
		Dim fileName : fileName = goldenFile.Name
		Call writeConsole("Comparing file " & fileName)
		If fso.FileExists(compareFolderPath & fileName) Then
			If convertPDFToWordAndCompare(goldenFile.Path, compareFolderPath & fileName, resultFolderPath) Then
				resultMsg = resultMsg & "<tr><td style='border:1px solid black;padding-left:1%;'>" & fileName & "</td><td style='border:1px solid black;background-color:green;padding-left:1%;'>Pass</td><td style='border:1px solid black;padding-left:1%;'></td></tr>"
			Else
				resultMsg = resultMsg & "<tr><td style='border:1px solid black;padding-left:1%;'>" & fileName & "</td><td style='border:1px solid black;background-color:red;padding-left:1%;'>Failed</td><td style='border:1px solid black;padding-left:1%;'></td></tr>"
			End If
		Else
			resultMsg = resultMsg & "<tr><td style='border:1px solid black;padding-left:1%;'>" & fileName & "</td><td style='background-color:yellow;padding-left:1%;'>Warning</td><td style='border:1px solid black;padding-left:1%;'>File not found</td></tr>"
		End If
	Next
	Set fso = Nothing
	Set goldenFolder = Nothing
	resultMsg =  resultMsg & "</table><p>Thanks,<br/>Kent</p></body></html>"
	getAllFilesInFolderAndCovertPDFToWord = resultMsg
	Call writeConsole("Compare files done")
End Function

Sub renameFiles(folderPath)
	Call writeConsole("Starting rename compare pdf file")
	'On Error Resume Next
	Dim fso : Set fso = CreateObject("Scripting.FileSystemObject")
	Dim folder : Set folder = fso.GetFolder(folderPath)
	For Each file In folder.Files
		Dim fileName
		If RegExpTest("^[\S\s]*_(\d{17}).xls$", file.Name) Then
			fileName = Left(file.Name, Len(file.Name)-22) & ".xls"
		ElseIf RegExpTest("^[\S\s]*_(\d{17}).pdf$", file.Name) Then
			fileName = Left(file.Name, Len(file.Name)-22) & ".pdf"
			If RegExpTest("^YourDetails_LetterCompareC_1017_(\d{17}).pdf$", file.Name) Then
				fileName = Replace(fileName, "_LetterCompareC_1017", "_LetterCompareC_1017_" & getComfirmationLetterNormalName(file.Path))
			ElseIf RegExpTest("^Delivery_LetterCompareC_1029_(\d{17}).pdf$", file.Name) Then
				fileName = Replace(fileName, "_LetterCompareC_1029", "_LetterCompareC_1029_" & getUMMarginDeliveryLetterName(file.Path))
			ElseIf RegExpTest("^[\S\s]*_LetterCompareC_(1015|1016|1504|1508)_(\d{17}).pdf$", file.Name) Then
				Dim interestLetterName : interestLetterName = ""
				If getCashAssetInterestLetterName(file.Path) <> "" Then
					interestLetterName = interestLetterName & "_" & getCashAssetInterestLetterName(file.Path)
				End If
				If getInterestSourceLetterName(file.Path) <> "" Then
					interestLetterName = interestLetterName & "_" & getInterestSourceLetterName(file.Path)
				End If
				fileName = Replace(fileName, ".pdf", interestLetterName & ".pdf")
			End If
		End If
		If Not IsNull(fileName) And Not IsEmpty(fileName) And Not fso.FileExists(folder.Path & "\" & fileName) Then
			file.Name = fileName
		End If
	Next
	Set fso = Nothing
	Set folder = Nothing
	Call writeConsole("File renaming done")
End Sub

Function RegExpTest(patrn, str)
	Dim regEx, Match, Matches
	Set regEx = New RegExp
	regEx.Pattern = patrn           
  	regEx.IgnoreCase = True         
  	regEx.Global = True             
  	RegExpTest = regEx.Test(str)
  	Set regEx = Nothing
End Function

Function convertPDFToWordAndCompare(file, file2, resultPath)
	Dim result : result = True
	Dim ObjWD1
	Dim docWord1, docWord2, docWord3
	Dim filename : filename = Split(file,"\")(Ubound(Split(file,"\")))
	Set ObjWD1 = CreateObject("Word.application")
	ObjWD1.Visible = False
	Set docWord1 = ObjWD1.Documents.Open(file)
	Set docWord2 = ObjWD1.Documents.Open(file2)
	Call docWord1.SaveAs2(Replace(file,".pdf",".docx"), wdFormatDocument)
	Call docWord2.SaveAs2(Replace(file2,".pdf",".docx"), wdFormatDocument)
	Set docWord3 = ObjWD1.CompareDocuments(docWord1, docWord2, wdCompareDestinationNew)
	If docWord3.Revisions.Count > 0 Then
		For Each revision In docWord3.Revisions
			If revision.Type = 10 Or revision.Type = 11 Then
				revision.Reject
			ElseIf readINI("Configuration","ignoreString") <> "Nothing" Then
				Dim arrIgnore : arrIgnore = Split(readINI("Configuration","ignoreString"), ",")
				If Not IsNull(revision) Then
					For Each strIgnore In arrIgnore
						'If revision.FormatDescription = strIgnore The
						If RegExpTest(strIgnore, revision.Range.Text) Then
							revision.Reject
							'Call writeConsole("revision matches the ignore pattern")
							Exit For
						End If
					Next
				End If
			End If
		Next
		If docWord3.Revisions.Count > 0 Then
			Call docWord3.SaveAs2(resultPath & "CompareResult_" & Replace(filename,".pdf",".docx"), wdFormatDocument)
			result = False
			Call writeConsole("Different Found - For more information, please check file CompareResult_" & Replace(filename,".pdf",".docx") & " in the result folder")
		End If
	End If
	docWord1.Close
	docWord2.Close
	ObjWD1.Quit
	Set ObjWD1 = Nothing
	Set docWord1 = Nothing
	Set docWord2 = Nothing
	Set docWord3 = Nothing
	deleteFile(Replace(file,".pdf",".docx"))
	deleteFile(Replace(file2,".pdf",".docx"))
	convertPDFToWordAndCompare = result
End Function

Sub writeResult(folder, msg)
	Dim oFS : Set oFS = CreateObject("Scripting.FileSystemObject")
	If Not oFS.FolderExists(folder) Then
		oFS.CreateFolder(folder)
	End If
	Dim oFile : Set oFile = oFS.CreateTextFile(folder & "\result.html", True)
	oFile.WriteLine(msg)
	oFile.Close
	Set oFS = Nothing
	Set oFile = Nothing
End Sub

Sub sendEmail(msg)
	Set objEmail = CreateObject("CDO.Message")	
	objEmail.From = "kent.gu@lombardrisk.com"
	objEmail.To = readINI("Configuration", "recipent")
	objEmail.CC = readINI("Configuration", "cc")
	objEmail.Subject = "Letter Compare Report " & Month(Now) & "/" & day(Now) & "/" & year(Now)
	objEmail.HTMLBody = msg
	objEmail.Configuration.Fields.Item("http://schemas.microsoft.com/cdo/configuration/sendusing") = 2
	objEmail.Configuration.Fields.Item("http://schemas.microsoft.com/cdo/configuration/smtpserver") = "sha-exchange-01.london.lombardrisk.com" 
	objEmail.Configuration.Fields.Item("http://schemas.microsoft.com/cdo/configuration/smtpserverport") = 25
	objEmail.Configuration.Fields.Update
	objEmail.Send
	Set objEmail = Nothing
End Sub

Sub copyFile(filePath, destFolderPath)
	Dim objFSO : Set objFSO = CreateObject("Scripting.FileSystemObject")
	Call objFSO.CopyFile(filePath, destFolderPath)
	Set objFSO = Nothing
End Sub

Sub deleteFile(filePath)
	Dim objFSO : Set objFSO = CreateObject("Scripting.FileSystemObject")
	objFSO.DeleteFile(filePath)
	Set objFSO = Nothing
End Sub

Function getComfirmationLetterNormalName(filepath)
	Dim ObjWD1 : Set ObjWD1 = CreateObject("Word.application")
	Dim docWord1 : Set docWord1 = ObjWD1.Documents.Open(filepath)
	Dim content : content = docWord1.Content
	If InStr(content, "Bond-JPY") > 0 Then
		getComfirmationLetterNormalName = "Bond-JPY"
	ElseIf InStr(content, "Bond-GBP") > 0 Then
		getComfirmationLetterNormalName = "Bond-GBP"
	ElseIf InStr(content, "Equity-JPY") > 0 Then
		getComfirmationLetterNormalName = "Equity-JPY"
	ElseIf InStr(content, "Equity-GBP") > 0 Then
		getComfirmationLetterNormalName = "Equity-GBP"
	ElseIf InStr(content, "Cash-USD") > 0 Then
		getComfirmationLetterNormalName = "Cash-USD"
	ElseIf InStr(content, "Cash-EUR") > 0 Then
		getComfirmationLetterNormalName = "Cash-EUR"
	End If
	docWord1.Close
	ObjWD1.Quit
	Set ObjWD1 = Nothing
	Set docWord1 = Nothing
End Function

Function getUMMarginDeliveryLetterName(filepath)
	Dim ObjWD1 : Set ObjWD1 = CreateObject("Word.application")
	Dim docWord1 : Set docWord1 = ObjWD1.Documents.Open(filepath)
	Dim content : content = docWord1.Content
	If InStr(content, "VM to the value of EUR 0.00") > 0 Then
		getUMMarginDeliveryLetterName = "VM"
	ElseIf InStr(content, "IM value of EUR 0.00") > 0 Then
		getUMMarginDeliveryLetterName = "IM"
	End If
	docWord1.Close
	ObjWD1.Quit
	Set ObjWD1 = Nothing
	Set docWord1 = Nothing
End Function

Function getCashAssetInterestLetterName(filepath)
	Dim ObjWD1 : Set ObjWD1 = CreateObject("Word.application")
	Dim docWord1 : Set docWord1 = ObjWD1.Documents.Open(filepath)
	Dim content : content = docWord1.Content
	If InStr(content, "Cash-GBP") > 0 Then
		getCashAssetInterestLetterName = "Cash-GBP"
	ElseIf InStr(content, "Cash-EUR") > 0 Then
		getCashAssetInterestLetterName = "Cash-EUR"
	ElseIf InStr(content, "Cash-USD") > 0 Then
		getCashAssetInterestLetterName = "Cash-USD"
	ElseIf InStr(content, "Cash-JPY") > 0 Then
		getCashAssetInterestLetterName = "Cash-JPY"
	Else
		getCashAssetInterestLetterName = ""
	End If
	docWord1.Close
	ObjWD1.Quit
	Set ObjWD1 = Nothing
	Set docWord1 = Nothing
End Function

Function getInterestSourceLetterName(filepath)
	Dim ObjWD1 : Set ObjWD1 = CreateObject("Word.application")
	Dim docWord1 : Set docWord1 = ObjWD1.Documents.Open(filepath)
	Dim content : content = docWord1.Content
	If InStr(content, "IM Interest") > 0 Then
		getInterestSourceLetterName = "IM"
	ElseIf InStr(content, "VM Interest") > 0 Then
		getInterestSourceLetterName = "VM"
	ElseIf InStr(content, "Net Interest") > 0 Then
		getInterestSourceLetterName = "Net"
	Else
		getInterestSourceLetterName = ""
	End If
	docWord1.Close
	ObjWD1.Quit
	Set ObjWD1 = Nothing
	Set docWord1 = Nothing
End Function

Function readINI(section, key)
	Dim filePath : filePath = getCurrentFolder() & "\config.ini"
	Dim fso, sReadLine, i, j, ss
	Set fso = CreateObject("Scripting.FileSystemObject")
	Set iniFile = fso.opentextfile(FilePath, 1)
	Do Until iniFile.AtEndOfStream
    	sReadLine = IniFile.readline
    	If sReadLine = "" Then
        	IniFile.skipline
    	ElseIf InStr(sReadLine, "=") > 0 Then
       		If Trim(Split(sReadLine, "=")(0)) = key Then
     			ss = Trim(Split(sReadLine, "=")(1))
        		Exit Do
       		End If
      	End If
  	Loop
	IniFile.Close
	Set fso = Nothing
	Set iniFile = Nothing
	ReadINI = ss
End Function

Function getCurrentFolder
	getCurrentFolder = createobject("Scripting.FileSystemObject").GetFile(Wscript.ScriptFullName).ParentFolder.Path
End Function

Sub writeConsole(msg)
	wsh.Echo msg
End Sub