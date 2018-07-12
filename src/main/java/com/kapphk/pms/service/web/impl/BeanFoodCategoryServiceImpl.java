package com.kapphk.pms.service.web.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanFoodCategory;
import com.kapphk.pms.dao.mapper.BeanFoodCategoryMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanFoodCategoryService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.FileUploadUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanFoodCategoryService")
public class BeanFoodCategoryServiceImpl extends BaseServiceImpl<BeanFoodCategory,Long>implements BeanFoodCategoryService {
	
	@Autowired
	private BeanFoodCategoryMapper foodCategoryMapper;

	@Override
	public void init() {
		super.setMapper(foodCategoryMapper);
	}

	/**
	 * 查询食材一级分类数据列表
	 * @param searchName 分类名称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@Override
	public Result getFirstCategoryList(String searchName, int page, int rows)
			throws Exception {
		Result rs = new Result();
		
		BeanFoodCategory foodCategory = new BeanFoodCategory();
		foodCategory.setPid((long)-1);
		foodCategory.setLevel("1");
		foodCategory.setName(searchName);
		
		List<BeanFoodCategory> list = foodCategoryMapper.findByPage(foodCategory,(page-1)*rows, rows);
		
		int count = foodCategoryMapper.findByPageCount(foodCategory);
		
		rs.put("total",count);
		rs.put("rows", list);
		
		return rs;
	}

	/**
	 * 新增或修改食材一级分类
	 * @param menuCategory 对象
	 * @return result
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@Override
	public Result saveFirstCategory(BeanFoodCategory foodCategory)
			throws Exception {
		Result rs = new Result();
		
		BeanFoodCategory category = new BeanFoodCategory();
		category.setName(foodCategory.getName());
		category.setPid((long) -1);
		category.setLevel("1");
		
		List<BeanFoodCategory> list = foodCategoryMapper.findByCategory(category);
		
		//新增
		if(ValidateUtils.isBlank(foodCategory.getId())){
			
			if(!ValidateUtils.isEmptyForCollection(list) && list.size() > 0){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("“"+foodCategory.getName()+"”名称已重复，请重新输入");
				return rs;
			}
			
			foodCategory.setPid((long)-1);
			foodCategory.setLevel("1");
			foodCategory.setCreateTime(DateUtils.getLocalDate());
			
			foodCategoryMapper.insert(foodCategory);
		}
		//修改
		else{
			
			if(!ValidateUtils.isEmptyForCollection(list) && list.get(0).getId() - foodCategory.getId() != 0){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("“"+foodCategory.getName()+"”名称已重复，请重新输入");
				return rs;
			}else{
				foodCategoryMapper.update(foodCategory);
			}
		
		}
		
		return rs;
	}

	/**
	 * 查询食材二级分类数据列表
	 * @param searchName 分类名称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@Override
	public Result getSecondCategoryList(String searchName, int page, int rows)
			throws Exception {
		
		Result rs = new Result();
		
		List<Map<String, Object>> list = foodCategoryMapper.findSecondList(searchName,(page-1)*rows,rows);
		
		int count = foodCategoryMapper.findSecondListCount(searchName);
		
		rs.put("total",count);
		rs.put("rows", list);
		
		return rs;
	}

	/**
	 * 查询一级分类下拉框数据
	 * @return List
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@Override
	public List<Map<String, Object>> getFirstCombox() throws Exception{
		return  foodCategoryMapper.getFirstCombox();
	}

	/**
	 * 新增或修改食材二级分类
	 * @param menuCategory 对象
	 * @return result
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@Override
	public Result saveTowCategory(BeanFoodCategory foodCategory,
			MultipartFile file, HttpServletRequest req) throws Exception {
		Result rs = new Result();
		
		BeanFoodCategory category = new BeanFoodCategory();
		category.setName(foodCategory.getName());
		category.setLevel("2");
		List<BeanFoodCategory> list = foodCategoryMapper.findByCategory(category);
		
		//新增分类
		if(ValidateUtils.isBlank(foodCategory.getId())){
			
			if(!ValidateUtils.isEmptyForCollection(list) && list.size() > 0){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("“"+foodCategory.getName()+"”名称已重复，请重新输入");
				return rs;
			}
			
			//上传图片
			if(!file.isEmpty()){
				byte[] bytes = file.getBytes() ;
				String logPath = FileUploadUtils.uploadFile(bytes,"upload/foodCategory","2",req) ;
				foodCategory.setLogoPath("/"+logPath);
			}
			
			foodCategory.setLevel("2");
			foodCategory.setCreateTime(DateUtils.getLocalDate());
			
			foodCategoryMapper.insert(foodCategory);
		}
		//修改
		else{
			
			if(!ValidateUtils.isEmptyForCollection(list) && list.get(0).getId() - foodCategory.getId() != 0){
				
				rs.setStatus(Contents.EXIST);
				rs.setMsg("“"+foodCategory.getName()+"”名称已重复，请重新输入");
				return rs;
			}else{
				
				//上传图片
				if(!file.isEmpty()){
					byte[] bytes = file.getBytes() ;
					String logPath = FileUploadUtils.uploadFile(bytes,"upload/foodCategory","2",req) ;
					foodCategory.setLogoPath("/"+logPath);
				}
				
				foodCategoryMapper.update(foodCategory);
			}
		
		}
		
		return rs;
	}

	/**
	 * 查询二级级分类下拉框数据
	 * @return List
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@Override
	public List<Map<String, Object>> getSecondCombox(Long firstId)
			throws Exception {
		return foodCategoryMapper.getSecondCombox(firstId);
	}


	/**
	 * * 暂停或者使用食材一级或者二级分类
	 * @param ids id
	 * @param type 状态
	 * @param rank 级别 为空时默认是二级
	 * @return
	 */
	@Override
	public Result upCategoryStatus(List<Long> ids,String type,String rank) {
		Result rs = new Result();
		if(!ValidateUtils.isEmptyForCollection(ids)){
			if(ValidateUtils.isBlank(rank)){
				
			}
			int count = foodCategoryMapper.upCategoryStatus(ids,type,rank);
			rs.put("count", count);
			return rs;
		}
			rs.setStatus(Contents.PARAMS_ERROR);
			rs.setMsg("参数错误!!");
			return rs;
	}

	/**
	 * 查询一级食材状态
	 * @param asList
	 * @return
	 */
	@Override
	public Result getCategoryStatus(List<Long> ids) {
		Result rs = new Result();
			
		if(!ValidateUtils.isEmptyForCollection(ids)){
			List<String> list = foodCategoryMapper.getCategoryStatus(ids);
			if(!ValidateUtils.isEmptyForCollection(list)){
				String str = "一级食材[ ";
				for (String s : list) {
					str += s +" ";
				}
				str += " ]已停用,操作失败";
				rs.setStatus(Contents.PARAMS_ERROR);
				rs.setMsg(str);
			}
			return rs;
		}
		
		rs.setStatus(Contents.PARAMS_ERROR);
		rs.setMsg("参数错误!!");
		return rs;
	}
	
}
