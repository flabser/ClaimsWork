package claimswork.page.view;

import com.exponentus.scripting._Session;
import com.exponentus.scripting._WebFormData;

import claimswork.dao.CivilProceedingDAO;

public class CivilProceedingView extends AbstractClaimsWorkView {

	@Override
	public void doGET(_Session session, _WebFormData formData) {
		addContent(getViewPage(new CivilProceedingDAO(session), formData));
	}

	@Override
	public void doDELETE(_Session session, _WebFormData formData) {

	}
}
