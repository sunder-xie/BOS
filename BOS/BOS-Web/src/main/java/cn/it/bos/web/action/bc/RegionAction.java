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

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.it.bos.domain.bc.Region;
import cn.it.bos.utils.FileUtils;
import cn.it.bos.utils.PinYin4jUtils;
import cn.it.bos.web.action.base.GenericAction;
import cn.it.page.PageRequestBean;
import cn.it.page.PageResponseBean;

public class RegionAction extends GenericAction<Region> {

	private final Logger logger = Logger.getLogger(RegionAction.class);

	private Region region = model;
	private File upload;
	private String uploadFileName;
	private String uploadContentType;

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	/**
	 * 文件下载
	 */
	public InputStream getInputStream() throws IOException {
		// 需要把内存中的excel信息转换成inputstrem输入流
		PageResponseBean pageResponseBean = (PageResponseBean) ServletActionContext.getRequest().getSession()
				.getAttribute("RegionPageResponseBean");
		List<Region> regions = pageResponseBean.getRows();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook();

		HSSFSheet sheet = hssfworkbook.createSheet("区域信息");
		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("分区编号");
		row.createCell(1).setCellValue("省");
		row.createCell(2).setCellValue("市");
		row.createCell(3).setCellValue("区");
		row.createCell(4).setCellValue("邮编");
		row.createCell(5).setCellValue("简码");
		row.createCell(6).setCellValue("城市编码");

		// 到处excel的数据需要查询数据库，按照条件查询
		// 遍历设置数据到表格里面
		for (Region region : regions) {
			// 在表格中追加数据，
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
			dataRow.createCell(0).setCellValue(region.getId());
			dataRow.createCell(1).setCellValue(region.getProvince());
			dataRow.createCell(2).setCellValue(region.getCity());
			dataRow.createCell(3).setCellValue(region.getDistrict());
			dataRow.createCell(4).setCellValue(region.getPostcode());
			dataRow.createCell(5).setCellValue(region.getShortcode());
			dataRow.createCell(6).setCellValue(region.getCitycode());

		}
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		hssfworkbook.write(byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();

		// inputStream 压入值栈
		pushMapValueStack("inputStream", new ByteArrayInputStream(byteArray));
		return new ByteArrayInputStream(byteArray);

	}

	public String exportXLS() throws IOException {
		String downloadFileName = "区域数据.xls";
		// 获得用户使用浏览器类型
		String agent = ServletActionContext.getRequest().getHeader("user-agent");

		// 对下载文件名编码
		downloadFileName = FileUtils.encodeDownloadFilename(downloadFileName, agent);
		// 将结果放入值栈
		pushMapValueStack("downloadFileName", downloadFileName);

		return "region_exportXLS_success";
	}

	/**
	 * 解析文件上传的xls文件
	 */
	@SuppressWarnings("all")
	public String importXls() throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(upload));
		HSSFSheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			// 表头跳过
			if (row.getRowNum() == 0)
				continue;

			if ("".equals(row.getCell(0).getStringCellValue().trim()))
				continue;
			// 从第二行开始解析
			Region region = new Region();
			// 保存信息到区域中心
			region.setId(row.getCell(0).getStringCellValue());// region主键策略是uuid，所以不能设置id，否则会保存报错
			region.setProvince(row.getCell(1).getStringCellValue());
			region.setCity(row.getCell(2).getStringCellValue());
			region.setDistrict(row.getCell(3).getStringCellValue());
			region.setPostcode(row.getCell(4).getStringCellValue());

			// 通过拼音进行设置
			// region.setShortcode(row.getCell(5).getStringCellValue());
			// region.setCitycode(row.getCell(6).getStringCellValue());
			String str = region.getProvince() + region.getCity() + region.getDistrict();
			String[] strs = PinYin4jUtils
					.getHeadByString(str.replaceAll("省", "").replaceAll("市", "").replaceAll("区", ""));
			StringBuffer sb = new StringBuffer();
			for (String string : strs) {
				sb.append(string);
			}
			region.setShortcode(sb.toString());
			region.setCitycode(PinYin4jUtils.hanziToPinyin(region.getCity(), ""));
			try {
				regionService.save(region);
			} catch (Exception e) {
				logger.error("第" + region.getId() + "条导入数据失败，请检查格式是否正确", e);
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", "success");
		map.put("msg", "区域导入完成");
		pushMapValueStack("map", map);
		return "region_importXls_success";
	}

	/**
	 * ajax关联查询所有的收派标准
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryRegion() throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Region.class);
		List<Region> regionList = regionService.queryRegion(detachedCriteria);
		pushMapValueStack("regionList", regionList);
		return "region_queryRegion_success";
	}

	/**
	 * 分页查询，把请求数据封装到PageRequestBean中，查询结果封装 到PageResopnebean中压入值栈。
	 * 
	 * @return
	 * @throws Exception
	 */
	public String pageQuery() throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Region.class);
		PageRequestBean pageRequestBean = super.pageQuery(detachedCriteria);
		PageResponseBean pageResponseBean = regionService.pageQuery(pageRequestBean);
		pushMapValueStack("pageResponseBean", pageResponseBean);
		ServletActionContext.getRequest().getSession().setAttribute("RegionPageResponseBean", pageResponseBean);
		return "region_pageQuery_success";
	}

	public String saveOrUpdate() throws Exception {
		// 保存数据到数据库
		// 主键策略设置为assigned，需要手动设置
		regionService.save(region);
		return "region_save_success";
	}

	//
	public String delete() {
		String[] ids = region.getId().split(", ");
		regionService.deleteById(ids);

		return "region_delete_success";
	}

}
