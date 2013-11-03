<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:S="http://webservices.amazon.com/AWSECommerceService/2011-08-01">
	<xsl:output method="xml" indent="no" encoding="UTF-8" />
	<xsl:param name="signature" />
	<xsl:param name="timestamp" />
	<xsl:template match="@*|node()">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
		</xsl:copy>
	</xsl:template>
	<xsl:template match="S:ItemSearch">
		<xsl:copy>
			<xsl:apply-templates select="@*|node()" />
			<xsl:element name="Signature">
				<xsl:value-of select="$signature" />
			</xsl:element>
			<xsl:element name="Timestamp">
				<xsl:value-of select="$timestamp" />
			</xsl:element>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>