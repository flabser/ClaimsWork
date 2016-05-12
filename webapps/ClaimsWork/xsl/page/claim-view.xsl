﻿<?xml version="1.0" ?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="../layout.xsl"/>

    <xsl:template match="/request">
        <xsl:call-template name="layout"/>
    </xsl:template>

    <xsl:template name="_content">
        <div class="content-header">
            <xsl:call-template name="page-info">
                <xsl:with-param name="title" select="//captions/claims_work/@caption"/>
            </xsl:call-template>
        </div>
        <div class="content-body">
            <div class="view">
                <xsl:call-template name="view-table"/>
            </div>
        </div>
    </xsl:template>

    <xsl:template name="view-table">
        <header class="entries-head">
            <div class="head-wrap">
                <label class="entry-select">
                    <input type="checkbox" data-toggle="docid" class="all"/>
                </label>
                <div class="entry-captions">
                    <span class="vw-regdate">
                        <xsl:value-of select="//captions/number/@caption"/>
                    </span>
                    <span class="vw-balance-holder">
                        <xsl:value-of select="//captions/executor/@caption"/>
                    </span>
                    <span class="vw-order-status">
                        <xsl:value-of select="//captions/division/@caption"/>
                    </span>
                    <span class="vw-order-status">
                        <xsl:value-of select="//captions/proceedings_type/@caption"/>
                    </span>
                    <span class="vw-order-description">
                        <xsl:value-of select="//captions/content/@caption"/>
                    </span>
                </div>
            </div>
        </header>
        <div class="entries">
            <xsl:apply-templates select="//view_content//query/entry" mode="view-table-body"/>
        </div>
    </xsl:template>

    <xsl:template match="entry" mode="view-table-body">
        <div class="entry-wrap">
            <div data-id="{@id}" class="entry">
                <label class="entry-select">
                    <input type="checkbox" name="docid" value="{@id}"/>
                </label>
                <a href="{@url}" class="entry-link">
                    <div class="entry-fields">
                        <span class="vw-regdate">
                            <xsl:value-of select="viewcontent/regnumber"/>
                        </span>
                        <span class="vw-balance-holder">
                            <xsl:value-of select="viewcontent/executor"/>
                        </span>
                        <span class="vw-order-status">
                            <xsl:value-of select="viewcontent/department"/>
                        </span>
                        <span class="vw-order-status">
                            <xsl:value-of select="viewcontent/proceedingtype"/>
                        </span>
                        <span class="vw-order-description">
                            <xsl:value-of select="viewcontent/basis"/>
                        </span>
                    </div>
                </a>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>
