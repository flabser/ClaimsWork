package claimswork.page.action;

import com.exponentus.dataengine.jpa.ViewPage;
import com.exponentus.scripting._POJOListWrapper;
import com.exponentus.scripting._Session;
import com.exponentus.scripting._WebFormData;
import com.exponentus.scripting.event._DoPage;
import reference.dao.LawArticleDAO;
import reference.model.LawArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class GetLawArticleAction extends _DoPage {

    @Override
    public void doGET(_Session ses, _WebFormData formData) {
        String keyword = formData.getValueSilently("keyword");
        int pageNum = formData.getNumberValueSilently("page", 1);
        int pageSize = ses.pageSize;
        LawArticleDAO dao = new LawArticleDAO(ses);
        ViewPage<LawArticle> vp;

        String[] ids = formData.getListOfValuesSilently("ids");
        if (ids.length > 0) {
            List<UUID> uids = new ArrayList<>();
            for (String id : ids) {
                uids.add(UUID.fromString(id));
            }
            vp = dao.findAllByIds(uids, pageNum, pageSize);
        } else {
            vp = dao.findAllByKeyword(keyword, pageNum, pageSize);
        }

        addContent(new _POJOListWrapper(vp.getResult(), vp.getMaxPage(), vp.getCount(), vp.getPageNum(), ses));
    }
}
