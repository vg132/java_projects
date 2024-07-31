<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:template match="channel">
		<table cellpadding="1" cellspacing="1" border="0" widht="100%">
			<tr>
				<th colspan="2" bgcolor="#afc4d7">
					<xsl:text disable-output-escaping="yes"><![CDATA[<a href="]]></xsl:text><xsl:value-of select="link"/><xsl:text disable-output-escaping="yes"><![CDATA[">]]></xsl:text><xsl:value-of select="title"/><xsl:text disable-output-escaping="yes"><![CDATA[</a> (<a href="/dsv/servlet/ass12?whattodo=remove&channel_url=<!--channel_url-->">Remove</a>)]]></xsl:text>
				</th>
			</tr>
			<xsl:apply-templates select="item"/>
		</table>
	</xsl:template>
	
	<xsl:template match="item">
		<tr bgcolor="#afc4d7">
			<td>
				<xsl:text disable-output-escaping="yes"><![CDATA[<a href="]]></xsl:text><xsl:value-of select="link"/><xsl:text disable-output-escaping="yes"><![CDATA[" target="_new">]]></xsl:text><xsl:value-of select="title"/> (<xsl:value-of select="pubDate"/>)<xsl:text disable-output-escaping="yes"><![CDATA[</a>]]></xsl:text>
			</td>
		</tr>
		<tr bgcolor="#f1f1f1">
			<td>
				<xsl:value-of select="description"/>
			</td>
		</tr>
	</xsl:template>
</xsl:stylesheet>