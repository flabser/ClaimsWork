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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.exponentus.common.model.Attachment;
import com.exponentus.scripting._Session;
import com.exponentus.util.Util;
import reference.model.DefendantType;

@Entity
@Table(name = "civil_proceedings")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "CivilProceeding.findAll", query = "SELECT m FROM CivilProceeding AS m ORDER BY m.regDate")
public class CivilProceeding extends Claim {

	private DefendantType claimant;

	private DefendantType defendant;

	private Date basisDate;

	private Date dueDate;

	@Column(name = "state_fees")
	private int stateFees;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "state_fees_attachments", joinColumns = { @JoinColumn(name = "parent_id", referencedColumnName = "id") }, inverseJoinColumns = {
	        @JoinColumn(name = "attachment_id", referencedColumnName = "id") })
	private List<Attachment> stateFeesAttachments;

	public DefendantType getClaimant(){return claimant;}

	public void setClaimant(DefendantType claimant) {
		this.claimant = claimant;
	}

	public DefendantType getDefendant(){return defendant;}

	public void setDefendant(DefendantType defendant) {
		this.defendant = defendant;
	}

	public Date getBasisDate(){return basisDate;}

	public void setBasisDate(Date basisDate) {
		this.basisDate = basisDate;
	}

	public Date getDueDate(){return dueDate;}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getStateFees(){return stateFees;}

	public void setStateFees(int stateFees) {
		this.stateFees = stateFees;
	}

	@Override
	public String getShortXMLChunk(_Session ses) {
		StringBuilder chunk = new StringBuilder(1000);
		chunk.append("<regnumber>" + getRegNumber() + "</regnumber>");
		chunk.append("<department>" + getDepartment().getLocalizedName(ses.getLang()) + "</department>");
		return chunk.toString();
	}


	@Override
	public String getFullXMLChunk(_Session ses) {
		return super.getFullXMLChunk(ses) +
				"<test>" + 1 + "</test>";
	}
}
