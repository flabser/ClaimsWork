package claimswork.model;

import java.util.List;

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
import reference.model.DisputeType;
import reference.model.LawArticle;
import reference.model.LawBranch;
import reference.model.ResponsibleType;
import staff.model.Department;

@Entity
@Table(name = "claims")
@NamedQuery(name = "Claim.findAll", query = "SELECT m FROM Claim AS m ORDER BY m.regDate")
public class Claim extends SecureAppEntity {

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false, length = 32)
	private ProceedingStatusType status;

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

	@ManyToOne
	@JoinColumn
	private LawArticle lawArticle;

	@ManyToOne
	@JoinColumn
	private ResponsibleType responsible;

}
