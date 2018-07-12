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

import com.kapphk.pms.bean.BeanUser;
import com.kapphk.pms.bean.BeanUserMenuCategory;
import com.kapphk.pms.dao.mapper.BeanUserMenuCategoryMapper;
import com.kapphk.pms.service.web.BeanUserService;
/**
 * 用户数据   批量导入导出  工具类
 * @author 胡子
 * 2018-03-23
 */
public class UserExcelImportAndExportUtils extends FileConstant{
	
	/**
	 * 用户表格标题数组
	 * add by baba
	 * since 2018-04-16 15:18
	 */
	public final static String[] userExcelTitleArray = new String[] { //行末如果有// 代表就是结尾 下一行另起一行
			"用户id", //
			"用户账号（手机号码）", //
			"密码", //
			"用户昵称", //
			"头像路径", //
			"联系人", //
			"手机号", //
			"联系人所在省份id", //
			"联系人所在城市id", //
			"联系人所在区id", //
			"联系人详细地址", //
			"居住省ID", //
			"居住市ID", //
			"居住区ID", //
			"居住详细地址", //
			"性别", //
			"邮箱", //
			"签名", //
			"出生省id", //
			"出生市id", //
			"状态", //
			"用户菜系 (菜谱类型id)", //
			"用户口味 (菜谱类型id)", //
			"注册时间"//
	};
	
	
	public final static String[] userMenuCategoryArray = new String[] { 
			"关系id", //
			"用户id（手机用户）", //
			"菜谱分类id", //
			"创建时间"//
	};
	
	
	/**
	 * 批量导出
	 * 2018-03-23  huzi
	 * @param list
	 * @return
	 */
	public static HSSFWorkbook export(@SuppressWarnings("rawtypes") List<Map> userList, List<List<BeanUserMenuCategory>> userMenuCategoryListData) {
		
				// 第一步，创建一个webbook，对应一个Excel文件
				HSSFWorkbook wb = new HSSFWorkbook();
				// 第三步，创建单元格，并设置值表头 设置表头居中
				HSSFCellStyle style = wb.createCellStyle();

				// 创建一个居中格式
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				
				HSSFSheet sheet = null;
				
				// 生成第1个sheet 写入食材表数据
				sheet = wb.createSheet("用户表");	
				fillDataInSheet1(userList,wb,sheet, style);
				
				// 生成第2个sheet 写入食材-食材分类关系表数据
				sheet = wb.createSheet("用户-菜谱分类关系表");
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
	private static void fillDataInSheet1(List<Map> userList, HSSFWorkbook wb, HSSFSheet sheet, HSSFCellStyle style) {
		
		// 在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		
		
		for (int i = 0; i < userExcelTitleArray.length; i++) {
			// 设置表头
			setCellValueAndStyle(style, row.createCell(i), userExcelTitleArray[i]);
		}

		for (int i = 0; i < userList.size(); i++) {
			row = sheet.createRow(i + 1);
			Map userMap = userList.get(i);

			//创建单元格，并设置值
			setCellStyle(wb, row.createCell(0), sheet, userMap.get("id"));
			setCellStyle(wb, row.createCell(1), sheet, userMap.get("userName"));
			setCellStyle(wb, row.createCell(2), sheet, userMap.get("password"));
			setCellStyle(wb, row.createCell(3), sheet, userMap.get("nickName"));
			setCellStyle(wb, row.createCell(4), sheet, userMap.get("logoPath"));
			setCellStyle(wb, row.createCell(5), sheet, userMap.get("contacter"));
			setCellStyle(wb, row.createCell(6), sheet, userMap.get("mobilePhone"));
			setCellStyle(wb, row.createCell(7), sheet, userMap.get("contacterProvice"));
			setCellStyle(wb, row.createCell(8), sheet, userMap.get("contacterCity"));
			setCellStyle(wb, row.createCell(9), sheet, userMap.get("contacterDistrict"));
			setCellStyle(wb, row.createCell(10), sheet, userMap.get("contacterAddress"));
			setCellStyle(wb, row.createCell(11), sheet, userMap.get("province"));
			setCellStyle(wb, row.createCell(12), sheet, userMap.get("city"));
			setCellStyle(wb, row.createCell(13), sheet, userMap.get("district"));
			setCellStyle(wb, row.createCell(14), sheet, userMap.get("address"));
			setCellStyle(wb, row.createCell(15), sheet, userMap.get("sex"));
			setCellStyle(wb, row.createCell(16), sheet, userMap.get("email"));
			setCellStyle(wb, row.createCell(17), sheet, userMap.get("autograph"));
			setCellStyle(wb, row.createCell(18), sheet, userMap.get("birthProvince"));
			setCellStyle(wb, row.createCell(19), sheet, userMap.get("birthCity"));
			setCellStyle(wb, row.createCell(20), sheet, userMap.get("status"));
			setCellStyle(wb, row.createCell(21), sheet, userMap.get("cuisineMenuCategoryIds"));
			setCellStyle(wb, row.createCell(22), sheet, userMap.get("flavorMenuCategoryIds"));
			setCellStyle(wb, row.createCell(23), sheet, userMap.get("createTime"));

		}
	}
	
	/**
	 * 生成第2个sheet
	 * @param userMenuCategoryList	 用户-菜谱分类关系集合
	 * @param wb
	 * @param sheet
	 * @param style  
	 */
	private static void fillDataInSheet2(List<List<BeanUserMenuCategory>> userMenuCategoryListData, HSSFWorkbook wb, HSSFSheet sheet, HSSFCellStyle style) {

		//在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		
		for (int i = 0; i < userMenuCategoryArray.length; i++) {
			// 设置表头
			setCellValueAndStyle(style, row.createCell(i), userMenuCategoryArray[i]);
		}
		
		int count = 0;
		for (int i = 0; i < userMenuCategoryListData.size(); i++) {
			List<BeanUserMenuCategory> userMenuCategoryList = userMenuCategoryListData.get(i);
			for (BeanUserMenuCategory userMenuCategory : userMenuCategoryList) {
				
				//创建行数
				row = sheet.createRow(count+1);
				count++;
				
				//创建单元格，并设置值
				setCellStyle(wb, row.createCell(0), sheet, userMenuCategory.getId());
				setCellStyle(wb, row.createCell(1), sheet, userMenuCategory.getUid());
				setCellStyle(wb, row.createCell(2), sheet, userMenuCategory.getCategoryId());
				setCellStyle(wb, row.createCell(3), sheet, userMenuCategory.getCreateTime());
			}
		}
	}


	
	/**
	 * 设置表头及格式
	 * @param column  列号
	 * @param value	     列名
	 */
	public static void setCellValueAndStyle(HSSFCellStyle styles ,Cell cell,String value){
		  cell.setCellValue(value); 
		  cell.setCellStyle(styles); 
	}
	
	/**
	 * 设置单元格样式    
	 * @param wb  	  对应一个Excel文件 
	 * @param cell   单元格
	 * @param sheet	  表
	 * @param value  每列单元格对应参数
	 * 2018-03-23  huzi
	 * 
	 */
	public static void setCellStyle(HSSFWorkbook wb,Cell cell,Sheet sheet,Object value){
		String temp=null;//存放值字符串
		if(value==null || value instanceof String){//如果类型是String类型，则进入判断
			temp = (String)value;
			cell.setCellValue(temp);
		}else{
			temp = ((Long)value).toString();
			cell.setCellValue(temp);
		}
		
		//宽度自适应
		sheet.setColumnWidth(cell.getColumnIndex(), temp==null? 15 * 256 : (temp.length()+10) * 256);
		HSSFCellStyle style = wb.createCellStyle(); 
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //创建一个居中格式 
		cell.setCellStyle(style);  
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
	public static Result importExcel(BeanUserService userService, BeanUserMenuCategoryMapper userMenuCategoryMapper,Workbook workbook, Result rs) throws Exception {
		ImportMessage importMessage = new ImportMessage();

		//导入第一个sheet
		Sheet sheet = workbook.getSheetAt(0);
		getSheet1(userService, sheet,importMessage);
		
		//导入第二个sheet
		sheet = workbook.getSheetAt(1);
		getSheet2(userMenuCategoryMapper, sheet,importMessage);
		
		rs.setMsg(importMessage.getMessage());
		return rs;
	}
	
	
	/**
	 * 将第一个sheet写入数据库
	 * @param foodStoreservice	食材表service
	 * @param sheet				excel表
	 * @param importMessage 
	 * @throws Exception
	 */
	private static void getSheet1(BeanUserService userService, Sheet sheet, ImportMessage importMessage) throws Exception {
			
		int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
		if (rows == 0) {
			throw new Exception("请填写数据");	
		}
		//判断列标题是否一致
		Row row0 = sheet.getRow(0);
		for (int i = 0; i < userExcelTitleArray.length; i++) {
			if(!userExcelTitleArray[i].equals(getCellValue(row0.getCell(i)).toString())){
				throw new Exception("该文件不是用户数据文件,请检查后重新导入");
			}
		}
		
		for (int i = 1; i < rows + 1; i++) {
		// 读取左上端单元格
		Row row = sheet.getRow(i);
		// 行不为空
		if (row != null) {

			// **读取cell**
			BeanUser user = new BeanUser();

			// 用户id
			Long id = Long.valueOf(getCellValue(row.getCell(0)).toString());// 不能通过强转来转换成Long类型
			user.setId(id);

			// 用户名
			String userName = (String) getCellValue(row.getCell(1));
			user.setUserName(userName);

			// 密码
			String password = (String) getCellValue(row.getCell(2));
			user.setPassword(password);

			// 用户昵称
			String nickName = (String) getCellValue(row.getCell(3));
			user.setNickName(nickName);

			// 用户头像路径
			String logoPath = (String) getCellValue(row.getCell(4));
			user.setLogoPath(logoPath);

			// 联系人
			String contacter = (String) getCellValue(row.getCell(5));
			user.setContacter(contacter);

			// 手机号
			String mobilePhone = (String) getCellValue(row.getCell(6));
			user.setMobilePhone(mobilePhone);

			// 联系人所在省份id
			Long contacterProvice = getParseLong(row.getCell(7));
			user.setContacterProvice(contacterProvice);

			// 联系人所在城市id
			Long contacterCity = getParseLong(row.getCell(8));
			user.setContacterCity(contacterCity);

			// 联系人所在区id
			Long contacterDistrict = getParseLong(row.getCell(9));
			user.setContacterDistrict(contacterDistrict);

			// 联系人详细地址
			String contacterAddress = (String) getCellValue(row.getCell(10));
			user.setContacterAddress(contacterAddress);

			// 居住省ID
			Long province = getParseLong(row.getCell(11));
			user.setProvince(province);

			// 居住市ID
			Long city = getParseLong(row.getCell(12));
			user.setCity(city);

			// 居住区ID
			Long district = getParseLong(row.getCell(13));
			user.setDistrict(district);

			// 居住详细地址
			String address = (String) getCellValue(row.getCell(14));
			user.setAddress(address);

			// 性别（1、男；2、女; 3:保密）
			String sex = (String) getCellValue(row.getCell(15));
			if (sex.equals("男")) {
				user.setSex("1");
			} else if (sex.equals("女")) {
				user.setSex("2");
			} else {
				user.setSex("3");
			}

			// 邮箱
			String email = (String) getCellValue(row.getCell(16));
			user.setEmail(email);

			// 签名
			String autograph = (String) getCellValue(row.getCell(17));
			user.setAutograph(autograph);

			// 出生省id
			Long birthProvince = getParseLong(row.getCell(18));
			user.setBirthProvince(birthProvince);

			// 出生市id
			Long birthCity = getParseLong(row.getCell(19));
			user.setBirthCity(birthCity);

			// 状态
			Integer status = Integer.parseInt(getParseLong(row.getCell(20)).toString());
			user.setStatus(status);

			// 用户菜系 (菜谱类型id) 多个菜系用逗号分隔
			String cuisineMenuCategoryIds = (String) getCellValue(row.getCell(21));
			user.setCuisineMenuCategoryIds(cuisineMenuCategoryIds);

			// 用户口味 (菜谱类型id) 多个口味用逗号分隔
			String flavorMenuCategoryIds = (String) getCellValue(row.getCell(22));
			user.setFlavorMenuCategoryIds(flavorMenuCategoryIds);

			// 注册时间
			String createTime = (String) getCellValue(row.getCell(23));
			user.setCreateTime(createTime);
			
			//用于判断数据是否重复
			BeanUser u = userService.findById(id);
			if (u == null) {
				try {
					userService.insert(user);
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
	 * @param userMenuCategoryMapper	关系表DAO
	 * @param sheet						excel表		
	 * @param importMessage				消息提示
	 * @throws Exception	BeanMenuCategoryServiceImpl MenuCategoryService,
	 */
	private static void getSheet2(BeanUserMenuCategoryMapper userMenuCategoryMapper, Sheet sheet, ImportMessage importMessage) throws Exception {
		
//		List<BeanMenuCategory> menuCategoryList = null ;
		
		int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
		if (rows == 0) {
			throw new Exception("请填写数据");
		}
		for (int i = 1; i < rows + 1; i++) {
			
			// **读取cell**
			BeanUserMenuCategory userMenuCategory = new BeanUserMenuCategory();
			
			// 读取左上端单元格
			Row row = sheet.getRow(i);
			
			// 行不为空
			if (row != null) {

				// 关系id
				Long id = Long.valueOf(getCellValue(row.getCell(0)).toString());// 不能通过强转来转换成Long类型
				userMenuCategory.setId(id);

				// 食材id
				Long uid = Long.valueOf(getCellValue(row.getCell(1)).toString());
				userMenuCategory.setUid(uid);

				// 食材分类id
				Long categoryId = Long.valueOf(getCellValue(row.getCell(2)).toString());
				userMenuCategory.setCategoryId(categoryId);
				
				// 创建时间
				String createTime = (String) getCellValue(row.getCell(3));
				userMenuCategory.setCreateTime(createTime);
				
				
				BeanUserMenuCategory umc = userMenuCategoryMapper.findById(id);
				
				if (umc == null) {
//					BeanMenuCategory beanMenuCategory = new BeanMenuCategory();
//					beanMenuCategory
					userMenuCategoryMapper.insert(userMenuCategory);
				}
			}
		}
	}	
	
	 
	 /**
	  * 获取Long类型参数
	  * 如果为空则赋值null，不为空时解析成Long类型
	  * @param cell  单元格
	  * @return
	  * 2018-03-23  huzi
	  */
	 public static Long getParseLong(Cell cell){
		 return "".equals(getCellValue(cell)) ? null : Long.valueOf(getCellValue(cell).toString());
	 }
	 
	 
	 /**
	  * 获得Cell内容
	  * @param cell  单元格
	  * @return
	  * 2018-03-23  huzi
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
