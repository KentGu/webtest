<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<html>
			<body>
				<h1>System Data</h1>
				<h2>login credential</h2>
				<table border="1">
					<tr bgcolor="#9acd32">
						<th align="left">id</th>
						<th align="left">username</th>
						<th align="left">password</th>
						<th align="left">old password</th>
						<th align="left">new password</th>
						<th align="left">confirm password</th>
					</tr>
					<xsl:for-each select="dataBean/systemData/loginCredential">
						<xsl:sort select="@id"/>
						<tr>
							<td>
								<xsl:value-of select="@id"/>
							</td>
							<td>
								<xsl:value-of select="username"/>
							</td>
							<td>
								<xsl:value-of select="password"/>
							</td>
							<td>
								<xsl:value-of select="oldPassword"/>
							</td>
							<td>
								<xsl:value-of select="newPassword"/>
							</td>
							<td>
								<xsl:value-of select="confirmPassword"/>
							</td>
						</tr>
					</xsl:for-each>
				</table>
				<h2>static data</h2>
				<table border="1">
					<tr bgcolor="#9acd32">
						<th align="left">id</th>
						<th align="left">name</th>
						<th align="left">description</th>
						<th align="left">enabled</th>
					</tr>
					<xsl:for-each select="dataBean/systemData/staticData">
						<xsl:sort select="@id"/>
						<tr>
							<td>
								<xsl:value-of select="@id"/>
							</td>
							<td>
								<xsl:value-of select="dataName"/>
							</td>
							<td>
								<xsl:value-of select="description"/>
							</td>
							<td>
								<xsl:value-of select="enabled"/>
							</td>
						</tr>
					</xsl:for-each>
				</table>


				<h1>Feature Data</h1>
				<h2>login credential</h2>
				<table border="1">
					<tr bgcolor="#9acd32">
						<th align="left">id</th>
						<th align="left">username</th>
						<th align="left">password</th>
						<th align="left">old password</th>
						<th align="left">new password</th>
						<th align="left">confirm password</th>
					</tr>
					<xsl:for-each select="dataBean/featureData/loginCredential">
						<xsl:sort select="@id"/>
						<tr>
							<td>
								<xsl:value-of select="@id"/>
							</td>
							<td>
								<xsl:value-of select="username"/>
							</td>
							<td>
								<xsl:value-of select="password"/>
							</td>
							<td>
								<xsl:value-of select="oldPassword"/>
							</td>
							<td>
								<xsl:value-of select="newPassword"/>
							</td>
							<td>
								<xsl:value-of select="confirmPassword"/>
							</td>
						</tr>
					</xsl:for-each>
				</table>
				<h2>static data</h2>
				<table border="1">
					<tr bgcolor="#9acd32">
						<th align="left">id</th>
						<th align="left">name</th>
						<th align="left">description</th>
						<th align="left">enabled</th>
					</tr>
					<xsl:for-each select="dataBean/featureData/staticData">
						<xsl:sort select="@id"/>
						<tr>
							<td>
								<xsl:value-of select="@id"/>
							</td>
							<td>
								<xsl:value-of select="dataName"/>
							</td>
							<td>
								<xsl:value-of select="description"/>
							</td>
							<td>
								<xsl:value-of select="enabled"/>
							</td>
						</tr>
					</xsl:for-each>
				</table>

				<h1>Scenario Data</h1>
				<h2>login credential</h2>
				<table border="1">
					<tr bgcolor="#9acd32">
						<th align="left">id</th>
						<th align="left">username</th>
						<th align="left">password</th>
						<th align="left">old password</th>
						<th align="left">new password</th>
						<th align="left">confirm password</th>
					</tr>
					<xsl:for-each select="dataBean/scenarioData/loginCredential">
						<xsl:sort select="@id"/>
						<tr>
							<td>
								<xsl:value-of select="@id"/>
							</td>
							<td>
								<xsl:value-of select="username"/>
							</td>
							<td>
								<xsl:value-of select="password"/>
							</td>
							<td>
								<xsl:value-of select="oldPassword"/>
							</td>
							<td>
								<xsl:value-of select="newPassword"/>
							</td>
							<td>
								<xsl:value-of select="confirmPassword"/>
							</td>
						</tr>
					</xsl:for-each>
				</table>
				<h2>static data</h2>
				<table border="1">
					<tr bgcolor="#9acd32">
						<th align="left">id</th>
						<th align="left">name</th>
						<th align="left">description</th>
						<th align="left">enabled</th>
					</tr>
					<xsl:for-each select="dataBean/scenarioData/staticData">
						<xsl:sort select="@id"/>
						<tr>
							<td>
								<xsl:value-of select="@id"/>
							</td>
							<td>
								<xsl:value-of select="dataName"/>
							</td>
							<td>
								<xsl:value-of select="description"/>
							</td>
							<td>
								<xsl:value-of select="enabled"/>
							</td>
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
