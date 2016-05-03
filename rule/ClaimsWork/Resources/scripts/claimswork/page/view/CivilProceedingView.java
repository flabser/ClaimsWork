package claimswork.page.view;

import com.exponentus.scripting._Session;
import com.exponentus.scripting._WebFormData;

import claimswork.dao.CivilProceedingDAO;
import com.exponentus.scripting.actions._Action;
import com.exponentus.scripting.actions._ActionBar;
import com.exponentus.scripting.actions._ActionType;
import com.exponentus.user.IUser;
import com.exponentus.user.SuperUser;

public class CivilProceedingView extends AbstractClaimsWorkView {

	@Override
	public void doGET(_Session session, _WebFormData formData) {
		IUser<Long> user = session.getUser();
		//if (user.getId() == SuperUser.ID || user.getRoles().contains("registrator_civilproceeding")) {
			_ActionBar actionBar = new _ActionBar(session);
			_Action newDocAction = new _Action(getLocalizedWord("new_", session.getLang()), "", "new_civilproceeding");
			newDocAction.setURL("Provider?id=civilproceeding-form");
			actionBar.addAction(newDocAction);
			actionBar.addAction(new _Action(getLocalizedWord("del_document", session.getLang()), "", _ActionType.DELETE_DOCUMENT));
			addContent(actionBar);
		//}
		addContent(getViewPage(new CivilProceedingDAO(session), formData));
	}

	@Override
	public void doDELETE(_Session session, _WebFormData formData) {

	}
}
