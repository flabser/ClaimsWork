package claimswork.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.exponentus.common.model.Attachment;

import com.exponentus.scripting._Session;
import com.exponentus.util.Util;
import reference.model.ClaimantDecisionType;
import reference.model.DefendantType;
import reference.model.OrgCategory;

@Entity
@Table(name = "criminal_proceedings")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "CriminalProceeding.findAll", query = "SELECT m FROM CriminalProceeding AS m ORDER BY m.regDate")
public class CriminalProceeding extends Claim {
	@ManyToOne
	@JoinColumn
	private OrgCategory claimantOrgCategory;

	@ManyToOne
	@JoinColumn
	private DefendantType defendantType;

	private String damageCaused;

	private String article;

	@ManyToOne
	@JoinColumn
	private ClaimantDecisionType decisionType;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "claimant_decision_type_attachments", joinColumns = {
	        @JoinColumn(name = "parent_id", referencedColumnName = "id") }, inverseJoinColumns = {
	                @JoinColumn(name = "attachment_id", referencedColumnName = "id") })
	private List<Attachment> claimantDecisionTypeAttachments;

	@Column(name = "notification_receiving_date")
	private Date notificationReceivingDate;

	@Column(name = "prelim_investigation_date")
	private Date preliminaryInvestigationDate;

	public OrgCategory getClaimantOrgCategory(){return claimantOrgCategory;}

	public void setClaimantOrgCategory(OrgCategory claimantOrgCategory) {
		this.claimantOrgCategory = claimantOrgCategory;
	}

	public DefendantType getDefendantType(){return defendantType;}

	public void setDefendantType(DefendantType defendantType) {
		this.defendantType = defendantType;
	}

	public String getDamageCaused(){return damageCaused;}

	public void setDamageCaused(String damageCaused) {
		this.damageCaused = damageCaused;
	}

	public String getArticle(){return article;}

	public void setArticle(String article) {
		this.article = article;
	}

	public ClaimantDecisionType getDecisionType(){return decisionType;}

	public void setDecisionType(ClaimantDecisionType decisionType) {
		this.decisionType = decisionType;
	}

	public Date getNotificationReceivingDate(){return notificationReceivingDate;}

	public void setNotificationReceivingDate(Date notificationReceivingDate) {
		this.notificationReceivingDate = notificationReceivingDate;
	}

	public Date getPreliminaryInvestigationDate(){return preliminaryInvestigationDate;}

	public void setPreliminaryInvestigationDate(Date preliminaryInvestigationDate) {
		this.preliminaryInvestigationDate = preliminaryInvestigationDate;
	}

	@Override
	public String getShortXMLChunk(_Session ses) {
		StringBuilder chunk = new StringBuilder(1000);
		chunk.append("<regnumber>" + getRegNumber() + "</regnumber>");
		chunk.append("<department>" + getDepartment().getLocalizedName(ses.getLang()) + "</department>");
		chunk.append("<executor>" + getExecutor().getLocalizedName(ses.getLang()) + "</executor>");
		chunk.append("<proceedingtype>" + getProceedingtype() + "</proceedingtype>");
		chunk.append("<basis>" + getBasis() + "</basis>");
		return chunk.toString();
	}


	@Override
	public String getFullXMLChunk(_Session ses) {
		return super.getFullXMLChunk(ses) +
				"<claimantorgcategory id=\"" + claimantOrgCategory.getId() + "\">" + claimantOrgCategory.getLocalizedName(ses.getLang()) + "</claimantorgcategory>" +
				"<decisiontype id=\"" + decisionType.getId() + "\">" + decisionType.getLocalizedName(ses.getLang()) + "</decisiontype>" +
				"<defendanttype id=\"" + defendantType.getId() + "\">" + defendantType.getLocalizedName(ses.getLang()) + "</defendanttype>" +
				"<notificationreceivingdate>" + Util.convertDateToStringSilently(notificationReceivingDate) + "</notificationreceivingdate>"+
				"<preliminaryinvestigationdate>" + Util.convertDateToStringSilently(preliminaryInvestigationDate) + "</preliminaryinvestigationdate>"+
				"<article>" + article + "</article>"+
				"<damagecaused>" + damageCaused + "</damagecaused>";
	}

}
