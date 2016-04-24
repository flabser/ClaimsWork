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

import reference.model.ClaimantDecisionType;
import reference.model.DefendantType;
import reference.model.OrgCategory;

@Entity
@Table(name = "criminal_proceedings")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQuery(name = "CriminalProceeding.findAll", query = "SELECT m FROM CriminalProceeding AS m ORDER BY m.regDate")
public class CriminalProceeding extends Claim {
	private OrgCategory claimant;

	@ManyToOne
	@JoinColumn
	private DefendantType defendantType;

	private String damageCaused;

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

}
