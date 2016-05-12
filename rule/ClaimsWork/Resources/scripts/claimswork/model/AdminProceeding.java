package claimswork.model;

import java.util.Date;

import javax.persistence.*;

import com.exponentus.scripting._Session;
import com.exponentus.util.Util;
import reference.model.OrgCategory;

@Entity
@Table(name = "admin_proceedings")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "AdminProceeding.findAll", query = "SELECT m FROM AdminProceeding AS m ORDER BY m.regDate")
public class AdminProceeding extends Claim {

	@ManyToOne
	@JoinColumn
	private OrgCategory claimantOrgCategory;

	private Date basisDate;

	private Date dueDate;

	private String article;

	public OrgCategory getClaimantOrgCategory(){return claimantOrgCategory;}

	public void setClaimantOrgCategory(OrgCategory claimantOrgCategory) {
		this.claimantOrgCategory = claimantOrgCategory;
	}

	public Date getBasisDate(){return basisDate;}

	public void setBasisDate(Date basisDate) {
		this.basisDate = basisDate;
	}

	public Date getDueDate(){return dueDate;}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getArticle(){return article;}

	public void setArticle(String article) {
		this.article = article;
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
				"<basisdate>" + Util.convertDateToStringSilently(basisDate) + "</basisdate>"+
				"<duedate>" + Util.convertDateToStringSilently(dueDate) + "</duedate>"+
				"<article>" + article + "</article>";
	}
}
