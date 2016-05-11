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

@Entity
@Table(name = "civil_proceedings")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "CivilProceeding.findAll", query = "SELECT m FROM CivilProceeding AS m ORDER BY m.regDate")
public class CivilProceeding extends Claim {

	private String claimant;

	private String defendant;

	private Date basisDate;

	private Date dueDate;

	@Column(name = "state_fees")
	private int stateFees;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "state_fees_attachments", joinColumns = { @JoinColumn(name = "parent_id", referencedColumnName = "id") }, inverseJoinColumns = {
	        @JoinColumn(name = "attachment_id", referencedColumnName = "id") })
	private List<Attachment> stateFeesAttachments;


	@Override
	public String getShortXMLChunk(_Session ses) {
		StringBuilder chunk = new StringBuilder(1000);
		chunk.append("<regnumber>" + getRegNumber() + "</regnumber>");
		chunk.append("<department>" + getDepartment().getLocalizedName(ses.getLang()) + "</department>");
		return chunk.toString();
	}
}
