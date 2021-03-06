package claimswork.page.form;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.eclipse.persistence.exceptions.DatabaseException;

import com.exponentus.common.model.Attachment;
import com.exponentus.env.EnvConst;
import com.exponentus.env.Environment;
import com.exponentus.exception.SecureException;
import com.exponentus.localization.LanguageCode;
import com.exponentus.scripting._POJOListWrapper;
import com.exponentus.scripting._Session;
import com.exponentus.scripting._Validation;
import com.exponentus.scripting._WebFormData;
import com.exponentus.scripting.actions._Action;
import com.exponentus.scripting.actions._ActionBar;
import com.exponentus.scripting.actions._ActionType;
import com.exponentus.scripting.event._DoPage;
import com.exponentus.user.IUser;
import com.exponentus.util.Util;
import com.exponentus.webserver.servlet.UploadedFile;

import claimswork.dao.CriminalProceedingDAO;
import claimswork.model.CriminalProceeding;
import reference.dao.ClaimantDecisionTypeDAO;
import reference.dao.DefendantTypeDAO;
import reference.dao.DisputeTypeDAO;
import reference.dao.LawArticleDAO;
import reference.dao.LawBranchDAO;
import reference.dao.OrgCategoryDAO;
import reference.dao.ResponsibleTypeDAO;
import reference.model.ClaimantDecisionType;
import reference.model.DefendantType;
import reference.model.DisputeType;
import reference.model.LawArticle;
import reference.model.LawBranch;
import reference.model.OrgCategory;
import reference.model.ResponsibleType;
import staff.dao.DepartmentDAO;
import staff.dao.EmployeeDAO;
import staff.model.Department;
import staff.model.Employee;

public class CriminalProceedingForm extends _DoPage {

	@Override
	public void doGET(_Session session, _WebFormData formData) {

		IUser<Long> user = session.getUser();
		CriminalProceeding entity;
		String id = formData.getValueSilently("docid");
		if (!id.isEmpty()) {
			CriminalProceedingDAO dao = new CriminalProceedingDAO(session);
			entity = dao.findById(UUID.fromString(id));
			addValue("formsesid", Util.generateRandomAsText());

			formData.getValueSilently("attachment");
		} else {
			entity = new CriminalProceeding();
			entity.setAuthor(user);
			entity.setRegNumber("");
			entity.setBasis("");
			entity.setArticle("");
			entity.setDamageCaused("");

			Department tempDpt = new Department();
			entity.setDepartment(tempDpt);
			tempDpt.setName("");

			Employee tempEmp = new Employee();
			entity.setExecutor(tempEmp);
			tempEmp.setName("");

			ResponsibleType tmpResponsibleType = new ResponsibleType();
			entity.setResponsibleType(tmpResponsibleType);
			tmpResponsibleType.setName("");

			DisputeType tmpDisputeType = new DisputeType();
			entity.setDisputeType(tmpDisputeType);
			tmpDisputeType.setName("");

			LawBranch tmpLawBranch = new LawBranch();
			entity.setLawBranch(tmpLawBranch);
			tmpLawBranch.setName("");

			LawArticle tmpLawArticle = new LawArticle();
			entity.setLawArticle(tmpLawArticle);
			tmpLawArticle.setName("");

			OrgCategory tmpOrgCategory = new OrgCategory();
			entity.setClaimantOrgCategory(tmpOrgCategory);
			tmpOrgCategory.setName("");

			ClaimantDecisionType tmpDecisionType = new ClaimantDecisionType();
			entity.setDecisionType(tmpDecisionType);
			tmpDecisionType.setName("");

			DefendantType tmpDefendantType = new DefendantType();
			entity.setDefendantType(tmpDefendantType);
			tmpDefendantType.setName("");

			String fsId = formData.getValueSilently(EnvConst.FSID_FIELD_NAME);
			addValue("formsesid", fsId);
			List<String> formFiles = null;
			Object obj = session.getAttribute(fsId);
			if (obj == null) {
				formFiles = new ArrayList<String>();
			} else {
				formFiles = (List<String>) obj;
			}

			List<UploadedFile> filesToPublish = new ArrayList<UploadedFile>();

			for (String fn : formFiles) {
				UploadedFile uf = (UploadedFile) session.getAttribute(fsId + "_file" + fn);
				if (uf == null) {
					uf = new UploadedFile();
					uf.setName(fn);
					session.setAttribute(fsId + "_file" + fn, uf);
				}
				filesToPublish.add(uf);
			}
			addContent(new _POJOListWrapper(filesToPublish, session));
		}

		addContent(entity);
		_ActionBar actionBar = new _ActionBar(session);
		actionBar.addAction(new _Action(getLocalizedWord("save_close", session.getLang()), "", _ActionType.SAVE_AND_CLOSE));
		actionBar.addAction(new _Action(getLocalizedWord("close", session.getLang()), "", _ActionType.CLOSE));
		if (entity.getId() != null) {

		}
		addContent(actionBar);
		startSaveFormTransact(entity);
	}

	@Override
	public void doPOST(_Session session, _WebFormData formData) {
		try {
			_Validation ve = validate(formData, session.getLang());
			if (ve.hasError()) {
				setBadRequest();
				setValidation(ve);
				return;
			}

			CriminalProceedingDAO dao = new CriminalProceedingDAO(session);
			CriminalProceeding entity;
			String id = formData.getValueSilently("docid");
			boolean isNew = id.isEmpty();

			if (isNew) {
				entity = new CriminalProceeding();
			} else {
				entity = dao.findById(id);
			}

			String[] fileNames = formData.getListOfValuesSilently("fileid");
			if (fileNames.length > 0) {
				File userTmpDir = new File(Environment.tmpDir + File.separator + session.getUser().getUserID());
				for (String fn : fileNames) {
					File file = new File(userTmpDir + File.separator + fn);
					InputStream is = new FileInputStream(file);
					Attachment att = new Attachment();
					att.setRealFileName(fn);
					att.setFile(IOUtils.toByteArray(is));
					att.setAuthor(session.getUser());
					att.setForm("attachment");
					// entity.getAttachments().add(att);
				}
			}
			entity.setRegNumber(formData.getValueSilently("regnumber"));
			entity.setNotificationReceivingDate(formData.getDateSilently("notificationreceivingdate"));
			entity.setPreliminaryInvestigationDate(formData.getDateSilently("preliminaryinvestigationdate"));
			entity.setBasis(formData.getValueSilently("basis"));
			entity.setArticle(formData.getValueSilently("article"));
			entity.setDamageCaused(formData.getValueSilently("damagecaused"));
			entity.setProceedingtype("Уголовный процесс");
			if (formData.containsField("department")) {
				DepartmentDAO dDao = new DepartmentDAO(session);
				Department dept = dDao.findById(formData.getValueSilently("department"));
				entity.setDepartment(dept);
			}
			if (formData.containsField("responsible")) {
				ResponsibleTypeDAO rDao = new ResponsibleTypeDAO(session);
				ResponsibleType responsible = rDao.findById(formData.getValueSilently("responsible"));
				entity.setResponsibleType(responsible);
			}
			if (formData.containsField("disputetype")) {
				DisputeTypeDAO dtDao = new DisputeTypeDAO(session);
				DisputeType disputetype = dtDao.findById(formData.getValueSilently("disputetype"));
				entity.setDisputeType(disputetype);
			}
			if (formData.containsField("lawarticle")) {
				LawArticleDAO laDao = new LawArticleDAO(session);
				LawArticle lawArticle = laDao.findById(formData.getValueSilently("lawarticle"));
				entity.setLawArticle(lawArticle);
			}
			if (formData.containsField("lawbranch")) {
				LawBranchDAO lbDao = new LawBranchDAO(session);
				LawBranch lawBranch = lbDao.findById(formData.getValueSilently("lawbranch"));
				entity.setLawBranch(lawBranch);
			}

			if (formData.containsField("executor")) {
				EmployeeDAO eDao = new EmployeeDAO(session);
				Employee executor = eDao.findById(formData.getValueSilently("executor"));
				entity.setExecutor(executor);
			}

			if (formData.containsField("claimantorgcategory")) {
				OrgCategoryDAO oDao = new OrgCategoryDAO(session);
				OrgCategory orgCategory = oDao.findById(formData.getValueSilently("claimantorgcategory"));
				entity.setClaimantOrgCategory(orgCategory);
			}

			if (formData.containsField("decisiontype")) {
				ClaimantDecisionTypeDAO oDao = new ClaimantDecisionTypeDAO(session);
				ClaimantDecisionType DecisionType = oDao.findById(formData.getValueSilently("decisiontype"));
				entity.setDecisionType(DecisionType);
			}

			if (formData.containsField("defendant")) {
				DefendantTypeDAO dDao = new DefendantTypeDAO(session);
				DefendantType DefendantType = dDao.findById(formData.getValueSilently("defendant"));
				entity.setDefendantType(DefendantType);
			}

			if (isNew) {
				IUser<Long> user = session.getUser();
				entity.addReaderEditor(user);
				entity = dao.add(entity);
			} else {
				entity = dao.update(entity);
			}

			finishSaveFormTransact(entity);
		} catch (SecureException e) {
			setError(e);
		} catch (DatabaseException | IOException e) {
			logError(e);
			setBadRequest();
		}
	}

	private _Validation validate(_WebFormData formData, LanguageCode lang) {
		_Validation ve = new _Validation();
		if (formData.getValueSilently("regnumber").isEmpty()) {
			ve.addError("regnumber", "required", getLocalizedWord("field_is_empty", lang));
		}
		if (formData.getValueSilently("claimantorgcategory").isEmpty()) {
			ve.addError("claimantorgcategory", "required", getLocalizedWord("field_is_empty", lang));
		}
		if (formData.getValueSilently("basis").isEmpty()) {
			ve.addError("basis", "required", getLocalizedWord("field_is_empty", lang));
		}
		if (formData.getValueSilently("responsible").isEmpty()) {
			ve.addError("responsible", "required", getLocalizedWord("field_is_empty", lang));
		}
		if (formData.getValueSilently("disputetype").isEmpty()) {
			ve.addError("disputetype", "required", getLocalizedWord("field_is_empty", lang));
		}
		if (formData.getValueSilently("department").isEmpty()) {
			ve.addError("department", "required", getLocalizedWord("field_is_empty", lang));
		}
		if (formData.getValueSilently("decisiontype").isEmpty()) {
			ve.addError("decisiontype", "required", getLocalizedWord("field_is_empty", lang));
		}
		if (formData.getValueSilently("notificationreceivingdate").isEmpty()) {
			ve.addError("notificationreceivingdate", "required", getLocalizedWord("field_is_empty", lang));
		}
		if (formData.getValueSilently("lawarticle").isEmpty()) {
			ve.addError("lawarticle", "required", getLocalizedWord("field_is_empty", lang));
		}
		if (formData.getValueSilently("lawbranch").isEmpty()) {
			ve.addError("lawbranch", "required", getLocalizedWord("field_is_empty", lang));
		}
		if (formData.getValueSilently("executor").isEmpty()) {
			ve.addError("executor", "required", getLocalizedWord("field_is_empty", lang));
		}
		if (formData.getValueSilently("defendant").isEmpty()) {
			ve.addError("defendant", "required", getLocalizedWord("field_is_empty", lang));
		}

		if (formData.getValueSilently("preliminaryinvestigationdate").isEmpty()) {
			ve.addError("preliminaryinvestigationdate", "required", getLocalizedWord("field_is_empty", lang));
		}

		return ve;
	}

}
