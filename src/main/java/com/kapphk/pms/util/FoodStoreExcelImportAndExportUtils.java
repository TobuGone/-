package com.kapphk.pms.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.kapphk.pms.bean.BeanFoodStore;
import com.kapphk.pms.bean.BeanFoodstoreCategory;
import com.kapphk.pms.dao.mapper.BeanFoodstoreCategoryMapper;
import com.kapphk.pms.service.web.BeanFoodStoreService;

/**
 * 食材数据 批量导入导出 工具类
 * 
 * @author 胡子 2018-03-23
 */
public class FoodStoreExcelImportAndExportUtils extends FileConstant{

	/**
	 * 食材表格表头数组
	 */
	public final static String[] foodStoreArray = new String[] { 
			"食材id", //
			"食材UID", //
			"食材学名", //
			"食材别名", //
			"是否是标准食材(0=不是；1=是)", //牛牛bi
			"产地", //
			"热销地", //
			"使用频率（%）", //
			"建议用量（g", //
			"图片路径", //
			"备注", //
			"状态", //
			"创建时间" };
	
	/**
	 * 食材-食材分类表格表头数组
	 */
	public final static String[] foodstoreCategoryArray = new String[] { 
			"关系id", //
			"食材id", //
			"食材分类Id", //
			"创建时间"//
	};

	/**
	 * 批量导出								2018-03-23 huzi
	 * @param foodStoreList					食材集合
	 * @param userMenuCategoryListData 	食材-食材分类关系集合
	 * @return
	 */
	public static HSSFWorkbook export(@SuppressWarnings("rawtypes") List<Map> foodStoreList, List<List<BeanFoodstoreCategory>> userMenuCategoryListData) {
 
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第三步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();

		// 创建一个居中格式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFSheet sheet = null;
		
		// 生成第1个sheet 写入食材表数据
		sheet = wb.createSheet("食材表");
		fillDataInSheet1(foodStoreList,wb,sheet, style);
		
		// 生成第2个sheet 写入食材-食材分类关系表数据
		sheet = wb.createSheet("食材-食材分类关系表");
		fillDataInSheet2(userMenuCategoryListData,wb,sheet, style);
		
		return wb;
	}
	
	
	
	/**
	 * 生成第1个sheet
	 * @param foodStoreList	食材集合
	 * @param wb				
	 * @param sheet
	 * @param style
	 */
	private static void fillDataInSheet1(List<Map> foodStoreList, HSSFWorkbook wb, HSSFSheet sheet, HSSFCellStyle style) {
		
		// 在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		
		
		for (int i = 0; i < foodStoreArray.length; i++) {
			// 设置表头
			setCellValueAndStyle(style, row.createCell(i), foodStoreArray[i]);
		}

		for (int i = 0; i < foodStoreList.size(); i++) {
			row = sheet.createRow(i + 1);
			Map foodsMap = foodStoreList.get(i);

			//创建单元格，并设置值
			setCellStyle(wb, row.createCell(0), sheet, foodsMap.get("id"));
			setCellStyle(wb, row.createCell(1), sheet, foodsMap.get("uid"));
			setCellStyle(wb, row.createCell(2), sheet, foodsMap.get("name"));
			setCellStyle(wb, row.createCell(3), sheet, foodsMap.get("alias"));
			setCellStyle(wb, row.createCell(4), sheet, foodsMap.get("standardStatus"));
			setCellStyle(wb, row.createCell(5), sheet, foodsMap.get("productPlace"));
			setCellStyle(wb, row.createCell(6), sheet, foodsMap.get("sellingPlace"));
			setCellStyle(wb, row.createCell(7), sheet, foodsMap.get("useFrequence"));
			setCellStyle(wb, row.createCell(8), sheet, foodsMap.get("dosage"));
			setCellStyle(wb, row.createCell(9), sheet, foodsMap.get("logoPath"));
			setCellStyle(wb, row.createCell(10), sheet, foodsMap.get("remark"));
			setCellStyle(wb, row.createCell(11), sheet, foodsMap.get("status"));
			setCellStyle(wb, row.createCell(12), sheet, foodsMap.get("createTime"));

		}
	}
	
	/**
	 * 生成第2个sheet
	 * @param foodstoreCategoryListData	 食材-食材分类关系集合
	 * @param wb
	 * @param sheet
	 * @param style  
	 */
	private static void fillDataInSheet2(List<List<BeanFoodstoreCategory>> foodstoreCategoryListData, HSSFWorkbook wb, HSSFSheet sheet, HSSFCellStyle style) {

		//在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		
		for (int i = 0; i < foodstoreCategoryArray.length; i++) {
			// 设置表头
			setCellValueAndStyle(style, row.createCell(i), foodstoreCategoryArray[i]);
		}
		
		int count = 0;
		for (int i = 0; i < foodstoreCategoryListData.size(); i++) {
			List<BeanFoodstoreCategory> foodstoreCategoryList = foodstoreCategoryListData.get(i);
			for (BeanFoodstoreCategory foodstoreCategory : foodstoreCategoryList) {
				
				//创建行数
				row = sheet.createRow(count+1);
				count++;
				
				//创建单元格，并设置值
				setCellStyle(wb, row.createCell(0), sheet, foodstoreCategory.getId());
				setCellStyle(wb, row.createCell(1), sheet, foodstoreCategory.getStoreId());
				setCellStyle(wb, row.createCell(2), sheet, foodstoreCategory.getCategoryId());
				setCellStyle(wb, row.createCell(3), sheet, foodstoreCategory.getCreateTime());
			}
		}
	}


	/**
	 * 设置表头及格式		2018-03-23 huzi
	 * @param column 	列号
	 * @param value		列名 
	 */
	public static void setCellValueAndStyle(HSSFCellStyle styles, Cell cell, String value) {
		cell.setCellValue(value);
		cell.setCellStyle(styles);
	}

	/**
	 * 设置单元格样式		2018-03-23 huzi
	 * @param wb 		对应一个Excel文件
	 * @param cell		单元格
	 * @param sheet 	表
	 * @param value 	每列单元格对应参数 2018-03-23 huzi
	 */
	public static void setCellStyle(HSSFWorkbook wb, Cell cell, Sheet sheet, Object value) {
		String temp = null;// 存放值字符串
		if (value == null || value instanceof String) {// 如果类型是String类型，则进入判断
			temp = (String) value;
			cell.setCellValue(temp);

		} else if (value instanceof Short) {
			temp = ((Short) value).toString();
			cell.setCellValue(temp);

		} else if (value instanceof Integer) {
			temp = ((Integer) value).toString();
			cell.setCellValue(temp);

		} else {
			temp = ((Long) value).toString();
			cell.setCellValue(temp);
		}

		// 宽度自适应
		sheet.setColumnWidth(cell.getColumnIndex(), temp == null ? 15 * 256 : (temp.length() + 10) * 256);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		cell.setCellStyle(style);
	}
	
	
	/**
	 * 将第一个sheet写入数据库
	 * @param foodStoreservice	食材表service
	 * @param sheet				excel表
	 * @param importMessage 
	 * @throws Exception
	 */
	private static void getSheet1(BeanFoodStoreService foodStoreservice,Sheet sheet, ImportMessage importMessage) throws Exception {
			
		int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
		if (rows == 0) {
			throw new Exception("请填写数据");
		}
		//判断列标题是否一致
		Row row0 = sheet.getRow(0);
		for (int i = 0; i < foodStoreArray.length; i++) {
			if(!foodStoreArray[i].equals(getCellValue(row0.getCell(i)).toString())){
				throw new Exception("该文件不是食材数据文件,请检查后重新导入");
			}
		}
		for (int i = 1; i < rows + 1; i++) {
			
			// **读取cell**
			BeanFoodStore foodStore = new BeanFoodStore();
			
			// 读取左上端单元格
			Row row = sheet.getRow(i);
			
			// 行不为空
			if (row != null) {
		
				// 食材编号
				Long id = Long.valueOf(getCellValue(row.getCell(0)).toString());// 不能通过强转来转换成Long类型
				foodStore.setId(id);
		
				// 食材序号
				String uid = (String) getCellValue(row.getCell(1));
				foodStore.setUid(uid);
		
				// 食材学名
				String name = (String) getCellValue(row.getCell(2));
				foodStore.setName(name);
		
				// 食材别名
				String alias = (String) getCellValue(row.getCell(3));
				foodStore.setAlias(alias);
		
				// 是否是标准食材(0=不是；1=是) short类型
				Short standardStatus = Short.valueOf(getCellValue(row.getCell(4)).toString());
				foodStore.setStandardStatus(standardStatus);
		
				// 产地
				String productPlace = (String) getCellValue(row.getCell(5));
				foodStore.setProductPlace(productPlace);
		
				// 热销地
				String sellingPlace = (String) getCellValue(row.getCell(6));
				foodStore.setSellingPlace(sellingPlace);
		
				// 使用频率（%）
				String useFrequence = (String) getCellValue(row.getCell(7));
				foodStore.setUseFrequence(useFrequence);
		
				// 建议用量（g）
				String dosage = (String) getCellValue(row.getCell(8));
				foodStore.setDosage(dosage);
		
				// 图片路径
				String logoPath = (String) getCellValue(row.getCell(9));
				foodStore.setLogoPath(logoPath);
		
				// 备注
				String remark = (String) getCellValue(row.getCell(10));
				foodStore.setRemark(remark);
		
				// 数据状态 (1:正常 2:已失效 -1:已删除)
				Integer status = Integer.valueOf(getCellValue(row.getCell(11)).toString());
				foodStore.setStatus(status);
		
				// 创建时间
				String createTime = (String) getCellValue(row.getCell(12));
				foodStore.setCreateTime(createTime);
				
				//用于判断数据是否重复导入
				BeanFoodStore fs = foodStoreservice.findById(id);
				if (fs == null) {
					try {
						foodStoreservice.insert(foodStore);
						importMessage.setImportSuccess(importMessage.getImportSuccess() + 1);
					} catch (Exception e) {
						e.printStackTrace();
						importMessage.setImportFail(importMessage.getImportFail() + 1);
					}
				}else{
					importMessage.setImportRepeat(importMessage.getImportRepeat() + 1);
				}
			}
		}
	}
				
	
	/**
	 * 将第二个sheet写入数据库				2018-03-23 	huzi
	 * @param foodstoreCategoryMapper	关系表DAO
	 * @param sheet						excel表		
	 * @param importMessage
	 * @throws Exception
	 */
	private static void getSheet2(BeanFoodstoreCategoryMapper foodstoreCategoryMapper,Sheet sheet, ImportMessage importMessage) throws Exception {
		
		int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
		if (rows == 0) {
			throw new Exception("请填写数据");
		}
		for (int i = 1; i < rows + 1; i++) {
			
			// **读取cell**
			BeanFoodstoreCategory foodstoreCategory = new BeanFoodstoreCategory();
			
			// 读取左上端单元格
			Row row = sheet.getRow(i);
			
			// 行不为空
			if (row != null) {

				// 关系id
				Long id = Long.valueOf(getCellValue(row.getCell(0)).toString());// 不能通过强转来转换成Long类型
				foodstoreCategory.setId(id);

				// 食材id
				Long storeId = Long.valueOf(getCellValue(row.getCell(1)).toString());
				foodstoreCategory.setStoreId(storeId);

				// 食材分类id
				Long categoryId = Long.valueOf(getCellValue(row.getCell(2)).toString());
				foodstoreCategory.setCategoryId(categoryId);
				
				// 创建时间
				String createTime = (String) getCellValue(row.getCell(3));
				foodstoreCategory.setCreateTime(createTime);
				
				
				BeanFoodstoreCategory fsc = foodstoreCategoryMapper.findById(id);
				
				if (fsc == null) {
					foodstoreCategoryMapper.insert(foodstoreCategory);
				}
			}
			
		}
	}	


	/**
	 * 批量导入Excel，兼容xls和xlsx 		2018-03-23 huzi
	 * @param foodStoreservice			食材表service
	 * @param foodstoreCategoryMapper	关系表DAO
	 * @param rs 
	 * @param foodStore					食材对象
	 * @param myFile					导入的excel文件					
	 * @return
	 * @throws Exception
	 */
	public static Result importExcel(BeanFoodStoreService foodStoreservice, BeanFoodstoreCategoryMapper foodstoreCategoryMapper,Workbook workbook, Result rs) throws Exception {
		ImportMessage importMessage = new ImportMessage();
		//导入第一个sheet
		Sheet sheet = workbook.getSheetAt(0);
		getSheet1(foodStoreservice, sheet,importMessage);
		
		//导入第二个sheet
		sheet = workbook.getSheetAt(1);
		getSheet2(foodstoreCategoryMapper, sheet,importMessage);
		
		rs.setMsg(importMessage.getMessage());
		return rs;
	}

	
	/**
	 * 获取Long类型参数 如果为空则赋值null，不为空时解析成Long类型		2018-03-23 	huzi
	 * @param cell   单元格
	 * @return 
	 */
	public static Long getParseLong(Cell cell) {
		return "".equals(getCellValue(cell)) ? null : Long.valueOf(getCellValue(cell).toString());
	}

	
	
	/**
	 * 获得Cell内容	2018-03-23 huzi
	 * @param cell	单元格
	 * @return 
	 */
	public static Object getCellValue(Cell cell) {
		String value = "";
		if (cell != null) {
			// 以下是判断数据的类型
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字
				value = cell.getNumericCellValue() + "";
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date != null) {
						value = new SimpleDateFormat("yyyy-MM-dd").format(date);
					} else {
						value = "";
					}
				} else {
					value = new DecimalFormat("0").format(cell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				value = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				value = cell.getBooleanCellValue() + "";
				break;
			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				value = cell.getCellFormula() + "";
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				value = "";
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				value = "非法字符";
				break;
			default:
				value = "未知类型";
				break;
			}
		}
		return value.trim();
	}
}
