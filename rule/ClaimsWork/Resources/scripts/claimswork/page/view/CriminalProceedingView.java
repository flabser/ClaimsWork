package claimswork.page.view;

import claimswork.dao.CriminalProceedingDAO;
import com.exponentus.scripting._Session;
import com.exponentus.scripting._WebFormData;
import com.exponentus.scripting.actions._Action;
import com.exponentus.scripting.actions._ActionBar;
import com.exponentus.scripting.actions._ActionType;
import com.exponentus.user.IUser;

public class CriminalProceedingView extends AbstractClaimsWorkView {

	@Override
	public void doGET(_Session session, _WebFormData formData) {
		IUser<Long> user = session.getUser();
		//if (user.getId() == SuperUser.ID || user.getRoles().contains("registrator_criminalproceeding")) {
			_ActionBar actionBar = new _ActionBar(session);
			_Action newDocAction = new _Action(getLocalizedWord("new_", session.getLang()), "", "new_criminalproceeding");
			newDocAction.setURL("Provider?id=criminalproceeding-form");
			actionBar.addAction(newDocAction);
			actionBar.addAction(new _Action(getLocalizedWord("del_document", session.getLang()), "", _ActionType.DELETE_DOCUMENT));
			addContent(actionBar);
		//}
		addContent(getViewPage(new CriminalProceedingDAO(session), formData));
	}

	@Override
	public void doDELETE(_Session session, _WebFormData formData) {

	}
}
