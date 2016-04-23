package claimswork.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.exponentus.common.model.Attachment;
import com.exponentus.dataengine.jpa.SecureAppEntity;

import staff.model.Department;

@Entity
@Table(name = "claims")
@NamedQuery(name = "Claim.findAll", query = "SELECT m FROM Claim AS m ORDER BY m.regDate")
public class Claim extends SecureAppEntity {
	private Department department;

	private Long assignee;

	private String basis;

	private Date basisDate;

	private Date dueDate;

	private String disputeType;

	private String disputeSubType;

	private String matterOfDispute;

	private int stateFees;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinTable(name = "state_fees_attachments", joinColumns = { @JoinColumn(name = "parent_id", referencedColumnName = "id") }, inverseJoinColumns = {
	        @JoinColumn(name = "attachment_id", referencedColumnName = "id") })
	private List<Attachment> stateFeesAttachments;

	private String responsiblePerson;

}
