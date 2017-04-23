package cn.it.bos.web.action.bc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.it.bos.domain.bc.DecidedZone;
import cn.it.bos.domain.bc.Region;
import cn.it.bos.domain.bc.Staff;
import cn.it.bos.domain.bc.Standard;
import cn.it.bos.domain.bc.Subarea;
import cn.it.bos.utils.FileUtils;
import cn.it.bos.web.action.base.GenericAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@ParentPackage("basicstruts2")
@Namespace("/")
public class SubareaAction extends GenericAction<Subarea> {

	private final Logger logger = Logger.getLogger(SubareaAction.class);
	// private String contentType = "application/vnd.ms-excel"; //
	// 用于设置下载文件的mimeType类型
	// private String contentDisposition = "attachment";// 用于设置进行下载操作以及下载文件的名称
	private InputStream inputStream; // 用于读取要下载的文件。
	private Subarea subarea = model;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;

	@Action(value = "subarea_ajaxId", results = { @Result(name = "ajaxId", type = "json") })
	public String ajaxId() throws Exception {
		String id = subarea.getId();
		Subarea subarea = subareaService.ajaxId(id);
		boolean flag = true;
		if (subarea != null) {
			flag = false;
		}
		pushRootValueStack(flag);
		return "ajaxId";
	}
	
	/**
	 * 解析文件上传的xls文件
	 */
	@Action(value = "subarea_importXls", results = { @Result(name = "importXls", type = "json") })
	public String importXls() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(upload));
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			// 表头跳过
			if (row.getRowNum() == 0)
				continue;

			if (row.getCell(0) == null || "".equals(row.getCell(0).getStringCellValue().trim()))
				continue;
			// 从第二行开始解析
			Subarea subarea = new Subarea();
			// 保存信息到区域中心
			
			//设置统一文本格式
			for(int i=0;i<8;i++){
				row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			}
			
			subarea.setId(row.getCell(0).getStringCellValue()); // id
			subarea.setAddresskey(row.getCell(1).getStringCellValue()); // 关键字
			subarea.setStartnum(row.getCell(2).getStringCellValue()); // 开始号
			subarea.setEndnum(row.getCell(3).getStringCellValue()); // 结束号
			subarea.setSingle(row.getCell(4).getStringCellValue()); // 单双号
			subarea.setPosition(row.getCell(5).getStringCellValue()); // 位置信息

			// subarea数据中需要保存关联region和decidedzone的主键id
			Region region = new Region();
			region.setId(row.getCell(6).getStringCellValue()); // 区域id
																// 表格中必须填写，这是行政区域
			subarea.setRegion(region);
			if (row.getCell(7) != null && !"".equals(row.getCell(7).getStringCellValue().trim())) {
				DecidedZone decidedZone = new DecidedZone();
				decidedZone.setId(row.getCell(7).getStringCellValue()); // 定区id
																		// 表格不一定需要填写，这是逻辑定义区域
				subarea.setDecidedZone(decidedZone);
			}
			try {
				subareaService.save(subarea);
			} catch (Exception e) {
				map.put("msg", subarea.getId() + "条导入数据失败，请检查格式是否正确");
				logger.error("第" + subarea.getId() + "条导入数据失败，请检查格式是否正确", e);
			}

		}
		pushRootValueStack(map);
		return "importXls";
	}

	/**
	 * 组合条件查询结果导出excel表格
	 * 
	 */
	
	public InputStream getInputStream() throws IOException {
		// 需要把内存中的excel信息转换成inputstrem输入流
		Map pageResponseBean = (Map) ServletActionContext.getRequest().getSession()
				.getAttribute("SubareaPageResponseBean");
		List<Subarea> subareas = (List<Subarea>) pageResponseBean.get("rows");

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

		// inputStream 压入值栈
		pushMapValueStack("inputStream", new ByteArrayInputStream(byteArray));
		return new ByteArrayInputStream(byteArray);

	}
	@Action(value = "subarea_exportXLS", results = { @Result(name = "exportXLS", type = "stream",params={"contentType","application/vnd.ms-excel","contentDisposition","attachment;filename=${downloadFileName}"})})
	public String exportXLS() throws IOException {
		String downloadFileName = "分区数据.xls";
		// 获得用户使用浏览器类型
		String agent = ServletActionContext.getRequest().getHeader("user-agent");

		// 对下载文件名编码
		downloadFileName = FileUtils.encodeDownloadFilename(downloadFileName, agent);
		// 将结果放入值栈
		pushMapValueStack("downloadFileName", downloadFileName);
//		pushRootValueStack(downloadFileName);

		return "exportXLS";
	}

	/**
	 * 分页查询，把请求数据封装到PageRequestBean中，查询结果封装 到PageResopnebean中压入值栈。
	 */

	// TODO 这个方法需要重写
	@Action(value = "subarea_pageQuery")
	public String pageQuery() throws Exception {
		Specification specification = new Specification<Subarea>() {
			public Predicate toPredicate(Root<Subarea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				// root 代表查询主表对象 也就是 接口泛型对象 Staff CriteriaBuilder cb 添加条件 = Restrictions
				// 根据model请求参数 决定 查询where后面添加 select * from staff where name = ? and telephone = ?
				List<Predicate> list = new ArrayList<Predicate>();
				if (subarea.getRegion()!=null&&StringUtils.isNotBlank(subarea.getRegion().getProvince())) {
//					root.get("region");
					Predicate c1 = cb.like( root.get("region").get("province").as(String.class), "%" + subarea.getRegion().getProvince() + "%");
					list.add(c1);
				}
				if (subarea.getRegion()!=null&&StringUtils.isNotBlank(subarea.getRegion().getCity())) {
					Predicate c2 = cb.like(root.get("region").get("city").as(String.class), "%" + subarea.getRegion().getCity() + "%");
					list.add(c2);
				}
				if (subarea.getRegion()!=null&&StringUtils.isNotBlank(subarea.getRegion().getDistrict())) {
					Predicate c3 = cb.like(root.get("region").get("district").as(String.class), "%" + subarea.getRegion().getDistrict() + "%");
					list.add(c3);
				}
				if (subarea.getAddresskey() != null) {
					Predicate c5 = cb.like(root.get("addresskey").as(String.class), "%" + subarea.getAddresskey()+ "%");
					list.add(c5);
				}
				if (subarea.getDecidedZone()!=null&&subarea.getDecidedZone().getId() != null) {
					Predicate c4 = cb.equal(root.get("decidedZone").as(DecidedZone.class), subarea.getDecidedZone());
					list.add(c4);
				}
				// 收集 可能出现 0-5个条件 and
				Predicate[] predicates = new Predicate[list.size()];
				return cb.and(list.toArray(predicates));// 条件and 连接
				
			}
		};
		
		Page<Subarea> pageQyery = subareaService.pageQuery(specification, getPageRequest());
		setPageQyery(pageQyery);
		// // 保存查询结果的session中，导出数据时候使用
		getSession().setAttribute("SubareaPageResponseBean", getPageResponseBean());
		return "pageQuery";
	}
	@Action(value = "subarea_findStandard", results = { @Result(name = "findStandard", type = "json") })
	public String findStandard() throws Exception {
		
		List<Subarea> staffList = subareaService.findStandardByDecidedZone();
		pushRootValueStack(staffList);
		staffList = null;
		// // 保存查询结果的session中，导出数据时候使用
//		ServletActionContext.getRequest().getSession().setAttribute("SubareaPageResponseBean", pageResponseBean);
		return "findStandard";
	}
	@Action(value = "subarea_saveOrUpdate", results = { @Result(name = "save", location = "/WEB-INF/pages/base/subarea.jsp") })
	public String saveOrUpdate() throws Exception {
		/**
		 * 获取region的id,利用id查询region信息，封装到subarea中。
		 */
		// Region queryRegion = regionService.findRegion(subarea.getRegion());
		// subarea.setRegion(queryRegion);
		subareaService.save(subarea);
		return "save";
	}
	@Action(value = "subarea_delete", results = { @Result(name = "delete", location = "/WEB-INF/pages/base/subarea.jsp") })
	public String delete() {
		String[] ids = subarea.getId().split(", ");
		subareaService.deleteById(ids);

		return "delete";
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
