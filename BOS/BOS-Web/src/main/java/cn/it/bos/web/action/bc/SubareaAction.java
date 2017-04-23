package cn.it.bos.web.action.bc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.it.bos.domain.bc.DecidedZone;
import cn.it.bos.domain.bc.Region;
import cn.it.bos.domain.bc.Staff;
import cn.it.bos.domain.bc.Subarea;
import cn.it.bos.utils.FileUtils;
import cn.it.bos.utils.PinYin4jUtils;
import cn.it.bos.web.action.base.GenericAction;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;
@SuppressWarnings("unchecked")
public class SubareaAction extends GenericAction<Subarea> {

	private final Logger logger = Logger.getLogger(SubareaAction.class);
//	private String contentType = "application/vnd.ms-excel"; // 用于设置下载文件的mimeType类型
//	private String contentDisposition = "attachment";// 用于设置进行下载操作以及下载文件的名称
	private InputStream inputStream; // 用于读取要下载的文件。
	private Subarea subarea = model;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	
	/**
	 * 解析文件上传的xls文件
	 */
	
	public String importXls() throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(upload));
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			// 表头跳过
			if(row.getRowNum()==0)continue;
			
			if (row.getCell(0)==null||"".equals(row.getCell(0).getStringCellValue().trim()))
				continue;
			// 从第二行开始解析
			Subarea subarea = new Subarea();
			// 保存信息到区域中心
			
			subarea.setId(row.getCell(0).getStringCellValue());			//id
			subarea.setAddresskey(row.getCell(1).getStringCellValue()); //关键字
			subarea.setStartnum(row.getCell(2).getStringCellValue());   //开始号
			subarea.setEndnum(row.getCell(3).getStringCellValue());     //结束号
			subarea.setSingle(row.getCell(4).getStringCellValue());		//单双号
			subarea.setPosition(row.getCell(5).getStringCellValue());	//位置信息
			
			//subarea数据中需要保存关联region和decidedzone的主键id
			Region region = new Region();
			region.setId(row.getCell(6).getStringCellValue());			//区域id  表格中必须填写，这是行政区域
			subarea.setRegion(region);	
			if(row.getCell(7)!=null&&!"".equals(row.getCell(7).getStringCellValue().trim())){
				DecidedZone decidedZone = new DecidedZone();
				decidedZone.setId(row.getCell(7).getStringCellValue());		//定区id  表格不一定需要填写，这是逻辑定义区域
				subarea.setDecidedZone(decidedZone);
			}
			try {
				subareaService.save(subarea);
			} catch (Exception e) {
				logger.error("第"+subarea.getId()+"条导入数据失败，请检查格式是否正确", e);
			}
				
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("msg", "区域导入完成");
		pushMapValueStack("map", map);
		return "subarea_importXls_success";
	}
	
	/**
	 * 组合条件查询结果导出excel表格 
	 * 
	 */
	public InputStream getInputStream() throws IOException {
		// 需要把内存中的excel信息转换成inputstrem输入流
		PageResponseBean pageResponseBean = (PageResponseBean) ServletActionContext.getRequest().getSession()
				.getAttribute("SubareaPageResponseBean");
		List<Subarea> subareas = pageResponseBean.getRows();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook();

		HSSFSheet sheet = hssfworkbook.createSheet("区域信息");
		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("分区编号");
		row.createCell(1).setCellValue("关键字");
		row.createCell(2).setCellValue("起始号");
		row.createCell(3).setCellValue("终止号");
		row.createCell(4).setCellValue("单双号");
		row.createCell(5).setCellValue("位置信息");

		// 到处excel的数据需要查询数据库，按照条件查询
		// 遍历设置数据到表格里面
		for (Subarea subarea : subareas) {
			// 在表格中追加数据，
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getAddresskey());
			dataRow.createCell(2).setCellValue(subarea.getStartnum());
			dataRow.createCell(3).setCellValue(subarea.getEndnum());
			dataRow.createCell(4).setCellValue(subarea.getSingle());
			dataRow.createCell(5).setCellValue(subarea.getPosition());

		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		hssfworkbook.write(byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		
		//inputStream 压入值栈
		pushMapValueStack("inputStream",new ByteArrayInputStream(byteArray));
		return new ByteArrayInputStream(byteArray);

	}

	public String exportXLS() throws IOException {
		String downloadFileName = "分区数据.xls";
		// 获得用户使用浏览器类型
		String agent = ServletActionContext.getRequest().getHeader("user-agent");

		// 对下载文件名编码
		downloadFileName = FileUtils.encodeDownloadFilename(downloadFileName, agent);
		// 将结果放入值栈
		pushMapValueStack("downloadFileName", downloadFileName);

		return "subarea_exportXLS_success";
	}

	/**
	 * 分页查询，把请求数据封装到PageRequestBean中，查询结果封装 到PageResopnebean中压入值栈。
	 */
	public String pageQuery() throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		detachedCriteria.createAlias("region", "r");
		// 判断查询条件，根据查询条件进行查询数据封装
		if (subarea.getRegion() != null && !"".equals(subarea.getRegion().getProvince())
				&& subarea.getRegion().getProvince() != null) {
			detachedCriteria.add(Restrictions.like("r.province", "%" + subarea.getRegion().getProvince() + "%"));
		}
		if (subarea.getRegion() != null && !"".equals(subarea.getRegion().getCity())
				&& subarea.getRegion().getCity() != null) {
			detachedCriteria.add(Restrictions.like("r.city", "%" + subarea.getRegion().getCity() + "%"));
		}
		if (subarea.getRegion() != null && !"".equals(subarea.getRegion().getDistrict())
				&& subarea.getRegion().getDistrict() != null) {
			detachedCriteria.add(Restrictions.like("r.district", "%" + subarea.getRegion().getDistrict() + "%"));
		}
		if (subarea.getDecidedZone() != null && subarea.getDecidedZone().getId() != null) {
			detachedCriteria.add(Restrictions.eq("decidedZone", subarea.getDecidedZone()));
		}
		if (subarea.getAddresskey() != null) {
			detachedCriteria.add(Restrictions.like("addresskey", "%" + subarea.getAddresskey() + "%"));
		}

		PageRequestBean pageRequestBean = super.pageQuery(detachedCriteria);
		PageResponseBean pageResponseBean = subareaService.pageQuery(pageRequestBean);
		// 保存查询结果的session中，导出数据时候使用
		ServletActionContext.getRequest().getSession().setAttribute("SubareaPageResponseBean", pageResponseBean);
		pushMapValueStack("pageResponseBean", pageResponseBean);
		return "subarea_pageQuery_success";
	}
	
	public String findStandard() throws Exception {
//		需要查询的收派标准是，可使用的收派标准
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
//		需要把无效的数据进行过滤
		detachedCriteria.add(Restrictions.isNull("decidedZone"));
		
		PageRequestBean pageRequestBean = super.pageQuery(detachedCriteria);
		PageResponseBean pageResponseBean = subareaService.pageQuery(pageRequestBean);
		// 保存查询结果的session中，导出数据时候使用
		ServletActionContext.getRequest().getSession().setAttribute("SubareaPageResponseBean", pageResponseBean);
		pushMapValueStack("pageResponseBean", pageResponseBean);
		return "subarea_findStandard_success";
	}

	public String saveOrUpdate() throws Exception {
		/**
		 * 获取region的id,利用id查询region信息，封装到subarea中。
		 */
		// Region queryRegion = regionService.findRegion(subarea.getRegion());
		// subarea.setRegion(queryRegion);
		subareaService.save(subarea);
		return "subarea_save_success";
	}

	public String delete() {
		String[] ids = subarea.getId().split(", ");
		subareaService.deleteById(ids);
		
		return "subarea_delete_success";
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

}
