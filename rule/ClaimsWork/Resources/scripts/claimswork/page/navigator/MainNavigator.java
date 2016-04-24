package claimswork.page.navigator;

import java.util.ArrayList;
import java.util.List;

import com.exponentus.scripting._Session;
import com.exponentus.scripting._WebFormData;
import com.exponentus.scripting.event._DoPage;
import com.exponentus.scriptprocessor.page.IOutcomeObject;

import kz.nextbase.script.outline._Outline;
import kz.nextbase.script.outline._OutlineEntry;

public class MainNavigator extends _DoPage {

	@Override
	public void doGET(_Session session, _WebFormData formData) {
		List<IOutcomeObject> list = new ArrayList<IOutcomeObject>();

		_Outline common_outline = new _Outline(getLocalizedWord("claimswork", session.getLang()), "common");
		_OutlineEntry claims_outline = new _OutlineEntry(getLocalizedWord("claims_work", session.getLang()), "claim-view");
		claims_outline.addEntry(new _OutlineEntry(getLocalizedWord("civil_proceedings", session.getLang()), "civilproceeding-view"));
		common_outline.addEntry(claims_outline);

		list.add(common_outline);

		addValue("outline_current", formData.getValueSilently("id").replace("-form", "-view"));
		addContent(list);
	}
}
