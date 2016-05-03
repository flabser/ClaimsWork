<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:import href="../layout.xsl"/>
    <xsl:import href="../templates/sharedfields.xsl"/>

    <xsl:template match="/request">
        <xsl:call-template name="layout"/>
    </xsl:template>

    <xsl:template name="_content">
        <xsl:apply-templates select="//document[@entity != '']"/>
    </xsl:template>

    <xsl:template match="document[@entity]">
        <form class="form" name="{@entity}" action="" enctype="application/x-www-form-urlencoded"
              data-edit="{@editable}">
            <header class="content-header">
                <h1 class="header-title">
                    <xsl:variable name="kufName" select="lower-case(fields/kuf/@name)"/>
                    <xsl:value-of select="//captions/*[lower-case(name()) = $kufName]/@caption"/>
                </h1>
                <div class="content-actions">
                    <xsl:apply-templates select="//actionbar"/>
                </div>
            </header>
            <section class="content-body">
                <ul class="nav nav-tabs" role="tablist">
                    <li class="active">
                        <a href="#tabs-1" role="tab" data-toggle="tab">
                            <xsl:value-of select="//captions/properties/@caption"/>
                        </a>
                    </li>
                    <li>
                        <a href="#tabs-2" role="tab" data-toggle="tab">
                            <xsl:value-of select="//captions/reg_documents/@caption"/>
                        </a>
                    </li>

                    <li>
                        <a href="#tabs-3" role="tab" data-toggle="tab">
                            <xsl:value-of select="//captions/additional/@caption"/>
                        </a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="tabs-1">
                        <fieldset class="fieldset">
                            <xsl:call-template name="reg_number"/>
                            <xsl:call-template name="organization"/>
                            <xsl:call-template name="executor"/>
                            <xsl:call-template name="claimant"/>
                            <xsl:call-template name="defendant"/>
                            <xsl:call-template name="lawarticle"/>
                            <xsl:call-template name="applicationdate"/>
                            <xsl:call-template name="controldate"/>
                            <xsl:call-template name="disputetype"/>
                            <xsl:call-template name="lawbranch"/>
                            <xsl:call-template name="statetaxcost"/>
                            <xsl:call-template name="responsibletype"/>
                        </fieldset>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="tabs-2">
                        <fieldset class="fieldset">
                            <xsl:call-template name="upload-files">
                                <xsl:with-param name="input-name" select="'reg-files'"/>
                            </xsl:call-template>
                        </fieldset>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="tabs-3">
                        <xsl:call-template name="docinfo"/>
                    </div>
                </div>
            </section>
        </form>
    </xsl:template>
</xsl:stylesheet>
