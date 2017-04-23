package cn.it.bos.web.action.qp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.it.bos.domain.qp.WorkOrderManage;
import cn.it.bos.utils.FileUtils;
import cn.it.bos.web.action.base.GenericAction;

@SuppressWarnings("all")
@Controller
@Scope("prototype")
@ParentPackage("basicstruts2")
@Namespace("/")
public class WorkOrderManageAction extends GenericAction<WorkOrderManage> {

	private final Logger logger = Logger.getLogger(WorkOrderManageAction.class);
	private WorkOrderManage workOrderManage = model;
	private String conditionName;
	private String conditionValue;
	private InputStream inputStream; // 用于读取要下载的文件。
	private File upload;
	private String uploadFileName;
	private String uploadContentType;

	/**
	 * 解析文件上传的xls文件
	 */
	@Action(value = "workOrderManage_batchImport", results = { @Result(name = "batchImport", type = "json") })
	public String batchImport() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(upload));
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			// 设置格式,统一用文本格式
			for (int i = 0; i < 18; i++) {
				row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
			}
			// 表头跳过
			if (row.getRowNum() == 0)
				continue;

			// 保存信息到区域中心
			if (row.getCell(0) == null || "".equals(row.getCell(0).getStringCellValue().trim()))
				continue;
			// 从第二行开始解析
			WorkOrderManage workOrderManage = new WorkOrderManage();

			workOrderManage.setWid(row.getCell(0).getStringCellValue()); // 工作单号
			workOrderManage.setArrivecity(row.getCell(1).getStringCellValue()); // 到达地
			workOrderManage.setProduct(row.getCell(2).getStringCellValue()); // 产品
			workOrderManage.setNum(Integer.parseInt(row.getCell(3).getStringCellValue())); // 件数
			workOrderManage.setWeight(Double.parseDouble(row.getCell(4).getStringCellValue())); // 重量
			workOrderManage.setFloadreqr(row.getCell(5).getStringCellValue()); // 配载要求
			workOrderManage.setProdtimelimit(row.getCell(6).getStringCellValue()); // 产品时限
			workOrderManage.setProdtype(row.getCell(7).getStringCellValue()); // 产品类型
			workOrderManage.setSendername(row.getCell(8).getStringCellValue()); // 寄件人姓名
			workOrderManage.setSenderphone(row.getCell(9).getStringCellValue()); // 寄件人电话
			workOrderManage.setSenderaddr(row.getCell(10).getStringCellValue()); // 寄件人地址
			workOrderManage.setReceivername(row.getCell(11).getStringCellValue()); // 收货人名称
			workOrderManage.setReceiverphone(row.getCell(12).getStringCellValue()); // 收货人电话
			workOrderManage.setReceiveraddr(row.getCell(13).getStringCellValue()); // 收货地址
			workOrderManage.setFeeitemnum(Integer.parseInt(row.getCell(14).getStringCellValue())); // 计费件数（原件数）
			workOrderManage.setActlweit(Double.parseDouble(row.getCell(15).getStringCellValue())); // 实际重量
			workOrderManage.setVol(row.getCell(16).getStringCellValue()); // 体积（尺寸）
			workOrderManage.setManagerCheck(row.getCell(17).getStringCellValue()); // //核查

			try {
				workOrderManageService.save(workOrderManage);
			} catch (Exception e) {
				map.put("msg", workOrderManage.getId() + "条导入数据失败，请检查格式是否正确");
				logger.error("第" + workOrderManage.getId() + "条导入数据失败，请检查格式是否正确", e);
			}

		}
		pushRootValueStack(map);
		return "batchImport";
	}

	/**
	 * 组合条件查询结果导出excel表格
	 * 
	 */

	public InputStream getInputStream() throws IOException {
		// 需要把内存中的excel信息转换成inputstrem输入流
		HSSFWorkbook hssfworkbook = new HSSFWorkbook();

		HSSFSheet sheet = hssfworkbook.createSheet("工作单导入模板");
		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("工作单编号");
		row.createCell(1).setCellValue("到达地");
		row.createCell(2).setCellValue("产品");
		row.createCell(3).setCellValue("件数");
		row.createCell(4).setCellValue("重量");
		row.createCell(5).setCellValue("配载要求");
		row.createCell(6).setCellValue("产品时限");
		row.createCell(7).setCellValue("产品类型");
		row.createCell(8).setCellValue("寄件人姓名");
		row.createCell(9).setCellValue("寄件人电话");
		row.createCell(10).setCellValue("寄件人地址");
		row.createCell(11).setCellValue("收货人名称");
		row.createCell(12).setCellValue("收货人电话");
		row.createCell(13).setCellValue("收货地址");
		row.createCell(14).setCellValue("计费件数（原件数）");
		row.createCell(15).setCellValue("实际重量");
		row.createCell(16).setCellValue("体积（尺寸）");
		row.createCell(17).setCellValue("核查");

		// 到处excel的数据需要查询数据库，按照条件查询
		// 遍历设置数据到表格里面
		// 在表格中追加数据，
		HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
		for (int i = 0; i < 18; i++) {
			dataRow.createCell(i).setCellValue("");
		}

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		hssfworkbook.write(byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();

		// inputStream 压入值栈
		pushMapValueStack("inputStream", new ByteArrayInputStream(byteArray));
		return new ByteArrayInputStream(byteArray);

	}

	@Action(value = "download", results = { @Result(name = "download", type = "stream", params = { "contentType",
			"application/vnd.ms-excel", "contentDisposition", "attachment;filename=${downloadFileName}" }) })
	public String exportXLS() throws IOException {
		String downloadFileName = "分区工作单导入模板.xls";
		// 获得用户使用浏览器类型
		String agent = ServletActionContext.getRequest().getHeader("user-agent");

		// 对下载文件名编码
		downloadFileName = FileUtils.encodeDownloadFilename(downloadFileName, agent);
		// 将结果放入值栈
		pushMapValueStack("downloadFileName", downloadFileName);
		// pushRootValueStack(downloadFileName);

		return "download";
	}

	/**
	 * 工作单的快速录入
	 * 
	 * @return
	 */
	@Action(value = "workOrderManage_save", results = {
			@Result(name = "save", location = "/WEB-INF/pages/qupai/quickworkorder.jsp") })
	public String save() {
		workOrderManageService.save(workOrderManage);
		return "save";
	}

	/**
	 * 工作单查询
	 * 
	 * @return
	 */
	@Action(value = "page_zhongzhuan_check", results = {
			@Result(name = "check", location = "/WEB-INF/pages/zhongzhuan/check.jsp") })
	public String check() {
		List<WorkOrderManage> workOrderManages = workOrderManageService.check();
		ActionContext.getContext().put("workOrderManages", workOrderManages);
		return "check";
	}
	/**
	 * 工作单审核
	 * 
	 * @return
	 */
	@Action(value = "workordermanage_check", results = {
			@Result(name = "workordermanage_check", type="redirectAction",location = "page_zhongzhuan_check") })
	public String workordermanage_check() {
		
		workOrderManageService.workordermanage_check(workOrderManage);
		return "workordermanage_check";
	}

	/**
	 * 分页查询所有有工作单， lucene搜索功能
	 * 
	 * @throws Exception
	 */
	@Action(value = "workOrderManage_pageQuery")
	public String pageQuery() throws Exception {
		if (StringUtils.isNotBlank(conditionValue)) {
			setPageResponseBean(
					workOrderManageService.pageQuery(null, getPageRequest(), conditionName, conditionValue));
		} else {
			Page<WorkOrderManage> pageQyery = workOrderManageService.pageQuery(null, getPageRequest());
			setPageQyery(pageQyery);
		}
		return "pageQuery";
	}

	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
