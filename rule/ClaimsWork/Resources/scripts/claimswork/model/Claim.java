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

import claimswork.model.constants.ProceedingStatusType;
import com.exponentus.scripting._Session;
import com.exponentus.util.NumberUtil;
import com.exponentus.util.Util;
import reference.model.*;
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

	private Long assignee;

	private String basis;

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


	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public Department getDepartment(){return department;}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String getShortXMLChunk(_Session ses) {
		StringBuilder chunk = new StringBuilder(1000);
		chunk.append("<regnumber>" + regNumber + "</regnumber>");
		chunk.append("<department>" + department.getLocalizedName(ses.getLang()) + "</department>");
		return chunk.toString();
	}

	@Override
	public String getFullXMLChunk(_Session ses) {
		StringBuilder chunk = new StringBuilder(1000);
		chunk.append("<regdate>" + Util.simpleDateTimeFormat.format(regDate) + "</regdate>");
		EmployeeDAO eDao = new EmployeeDAO(ses);
		Employee user = eDao.findByUserId(author);
		if (user != null) {
			chunk.append("<author>" + user.getName() + "</author>");
		} else {
			chunk.append("<author></author>");
		}

		chunk.append("<regnumber>" + regNumber + "</regnumber>");
		chunk.append("<department id=\"" + department.getId() + "\">" + department.getLocalizedName(ses.getLang()) + "</department>");
		return chunk.toString();
	}
}
