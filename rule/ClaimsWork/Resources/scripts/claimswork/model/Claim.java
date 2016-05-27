package claimswork.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.exponentus.common.model.Attachment;
import com.exponentus.dataengine.jpa.SecureAppEntity;
import com.exponentus.scripting._Session;
import com.exponentus.util.Util;

import claimswork.model.constants.ProceedingStatusType;
import reference.model.DisputeType;
import reference.model.LawArticle;
import reference.model.LawBranch;
import reference.model.ResponsibleType;
import staff.dao.EmployeeDAO;
import staff.model.Department;
import staff.model.Employee;

@Entity
@Table(name = "claims")
@NamedQuery(name = "Claim.findAll", query = "SELECT m FROM Claim AS m ORDER BY m.regDate")
public class Claim extends SecureAppEntity<UUID> {

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 32)
	private ProceedingStatusType status = ProceedingStatusType.UNKNOWN;

	private Department department;

	private Employee executor;

	private Long assignee;

	private String basis;

	private String proceedingtype;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "basis_attachments", joinColumns = { @JoinColumn(name = "parent_id", referencedColumnName = "id") }, inverseJoinColumns = {
	        @JoinColumn(name = "attachment_id", referencedColumnName = "id") })
	private List<Attachment> basisAttachments;

	@ManyToOne
	@JoinColumn
	private DisputeType disputeType;

	@ManyToOne
	@JoinColumn
	private LawBranch branchOfLaw;

	@Column(name = "matter_of_dispute")
	private String matterOfDispute;

	@Column(name = "reg_number")
	private String regNumber;

	@ManyToOne
	@JoinColumn
	private LawArticle lawArticle;

	@ManyToOne
	@JoinColumn
	private ResponsibleType responsible;

	public String getBasis() {
		return basis;
	}

	public void setBasis(String basis) {
		this.basis = basis;
	}

	public String getProceedingtype() {
		return proceedingtype;
	}

	public void setProceedingtype(String proceedingtype) {
		this.proceedingtype = proceedingtype;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Employee getExecutor() {
		return executor;
	}

	public void setExecutor(Employee executor) {
		this.executor = executor;
	}

	public ResponsibleType getResponsibleType() {
		return responsible;
	}

	public void setResponsibleType(ResponsibleType responsible) {
		this.responsible = responsible;
	}

	public DisputeType getDisputeType() {
		return disputeType;
	}

	public void setDisputeType(DisputeType disputeType) {
		this.disputeType = disputeType;
	}

	public LawArticle getLawArticle() {
		return lawArticle;
	}

	public void setLawArticle(LawArticle lawArticle) {
		this.lawArticle = lawArticle;
	}

	public LawBranch getLawBranch() {
		return branchOfLaw;
	}

	public void setLawBranch(LawBranch branchOfLaw) {
		this.branchOfLaw = branchOfLaw;
	}

	@Override
	public String getShortXMLChunk(_Session ses) {
		StringBuilder chunk = new StringBuilder(1000);
		chunk.append("<regnumber>" + regNumber + "</regnumber>");
		chunk.append("<department>" + department.getLocalizedName(ses.getLang()) + "</department>");
		chunk.append("<executor>" + executor.getLocalizedName(ses.getLang()) + "</executor>");
		chunk.append("<proceedingtype>" + proceedingtype + "</proceedingtype>");
		chunk.append("<basis>" + basis + "</basis>");
		return chunk.toString();
	}

	@Override
	public String getFullXMLChunk(_Session ses) {
		StringBuilder chunk = new StringBuilder(1000);
		chunk.append("<regdate>" + Util.convertDataTimeToString(regDate) + "</regdate>");
		EmployeeDAO eDao = new EmployeeDAO(ses);
		Employee user = eDao.findByUserId(author);
		if (user != null) {
			chunk.append("<author>" + user.getName() + "</author>");
		} else {
			chunk.append("<author></author>");
		}

		chunk.append("<regnumber>" + regNumber + "</regnumber>");
		chunk.append("<department id=\"" + department.getId() + "\">" + department.getLocalizedName(ses.getLang()) + "</department>");
		chunk.append("<executor id=\"" + executor.getId() + "\">" + executor.getLocalizedName(ses.getLang()) + "</executor>");
		chunk.append("<responsible id=\"" + responsible.getId() + "\">" + responsible.getLocalizedName(ses.getLang()) + "</responsible>");
		chunk.append("<lawbranch id=\"" + branchOfLaw.getId() + "\">" + branchOfLaw.getLocalizedName(ses.getLang()) + "</lawbranch>");
		chunk.append("<lawarticle id=\"" + lawArticle.getId() + "\">" + lawArticle.getLocalizedName(ses.getLang()) + "</lawarticle>");
		chunk.append("<disputetype id=\"" + disputeType.getId() + "\">" + disputeType.getLocalizedName(ses.getLang()) + "</disputetype>");
		chunk.append("<basis>" + basis + "</basis>");
		return chunk.toString();
	}
}
