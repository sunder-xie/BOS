package cn.it.bos.web.action.qp;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.it.bos.domain.bc.Standard;
import cn.it.bos.domain.qp.NoticeBill;
import cn.it.bos.domain.user.User;
import cn.it.bos.web.action.base.GenericAction;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

@SuppressWarnings("serial")
public class NoticeBillAction extends GenericAction<NoticeBill> {

	private NoticeBill noticeBill = model;

	/**
	 * 查询所有通知单信息
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String findAllNoticeBills() throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(NoticeBill.class);
//		需要把无效的数据进行过滤
		PageRequestBean pageRequestBean = super.pageQuery(detachedCriteria);
		PageResponseBean pageResponseBean = noticeBillService.pageQuery(pageRequestBean);
		pushMapValueStack("pageResponseBean", pageResponseBean);
		
		return "noticeBill_findAllNoticeBills_success";
	}

	/**
	 * 保存通知单和关联的工单分单
	 * 
	 * @return
	 */
	public String saveOrUpdate() {

		Map<String, Object> map = null;
		if (noticeBill == null)
			return null;
		// 数据用封装
		User loginUser = (User) getSession().getAttribute("user");
		noticeBill.setUser(loginUser);
		noticeBillService.save(noticeBill);
		return "noticeBill_saveOrUpdate_success";
	}

}
