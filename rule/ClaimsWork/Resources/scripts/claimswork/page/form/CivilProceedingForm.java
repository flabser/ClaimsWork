package claimswork.page.form;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import claimswork.dao.ClaimDAO;
import claimswork.model.Claim;
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

import claimswork.dao.CivilProceedingDAO;
import claimswork.model.CivilProceeding;
import reference.dao.DisputeTypeDAO;
import reference.dao.LawArticleDAO;
import reference.dao.ResponsibleTypeDAO;
import reference.model.DisputeType;
import reference.model.LawArticle;
import reference.model.ResponsibleType;
import staff.dao.DepartmentDAO;
import staff.model.Department;

public class CivilProceedingForm extends _DoPage {

	@Override
	public void doGET(_Session session, _WebFormData formData) {

		IUser<Long> user = session.getUser();
		Claim entity;
		String id = formData.getValueSilently("docid");
		if (!id.isEmpty()) {
			ClaimDAO dao = new ClaimDAO(session);
			entity = dao.findById(UUID.fromString(id));
			addValue("formsesid", Util.generateRandomAsText());

			formData.getValueSilently("attachment");
		} else {
			entity = new Claim();
			entity.setAuthor(user);
			entity.setRegDate(new Date());
			entity.setRegNumber("");
			Department tempDpt = new Department();
			entity.setDepartment(tempDpt);
			tempDpt.setName("");
			ResponsibleType tmpResponsibleType = new ResponsibleType();
			entity.setResponsibleType(tmpResponsibleType);
			tmpResponsibleType.setName("");
			DisputeType tmpDisputeType = new DisputeType();
			entity.setDisputeType(tmpDisputeType);
			tmpDisputeType.setName("");
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

			CivilProceedingDAO dao = new CivilProceedingDAO(session);
			CivilProceeding entity;
			String id = formData.getValueSilently("docid");
			boolean isNew = id.isEmpty();

			if (isNew) {
				entity = new CivilProceeding();
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
			if (formData.containsField("disputeType")) {
				DisputeTypeDAO dtDao = new DisputeTypeDAO(session);
				DisputeType disputetype = dtDao.findById(formData.getValueSilently("disputeType"));
				entity.setDisputeType(disputetype);
			}
			if (formData.containsField("lawArticle")) {
				LawArticleDAO laDao = new LawArticleDAO(session);
				LawArticle lawArticle = laDao.findById(formData.getValueSilently("lawArticle"));
				entity.setLawArticle(lawArticle);
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
			error(e);
			setBadRequest();
		}
	}

	private _Validation validate(_WebFormData formData, LanguageCode lang) {
		_Validation ve = new _Validation();


		return ve;
	}

}
