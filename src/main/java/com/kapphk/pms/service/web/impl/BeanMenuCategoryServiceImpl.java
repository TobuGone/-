package com.kapphk.pms.service.web.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanMenuCategory;
import com.kapphk.pms.dao.mapper.BeanMenuCategoryMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.web.BeanMenuCategoryService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.FileUploadUtils;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
@Service("beanMenuCategoryService")
public class BeanMenuCategoryServiceImpl extends BaseServiceImpl<BeanMenuCategory, Long>implements BeanMenuCategoryService {
	
	@Autowired
	private BeanMenuCategoryMapper categoryMapper;

	@Override
	public void init() {
		super.setMapper(categoryMapper);
	}

	/**
	 * 查询菜谱一级分类数据列表
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
		
		BeanMenuCategory menuCategory = new BeanMenuCategory();
		menuCategory.setPid((long)-1);
		menuCategory.setLevel("1");
		menuCategory.setName(searchName);
		
		List<BeanMenuCategory> list = categoryMapper.findByPage(menuCategory,(page-1)*rows, rows);
		
		int count = categoryMapper.findByPageCount(menuCategory);
		
		rs.put("total",count);
		rs.put("rows", list);
		
		return rs;
	}

	/**
	 * 新增或修改菜谱一级分类
	 * @param menuCategory 对象
	 * @return result
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@Override
	public Result saveFirstCategory(BeanMenuCategory menuCategory,
			MultipartFile file, HttpServletRequest req) throws Exception {
		
		Result rs = new Result();
		
		//新增
		if(ValidateUtils.isBlank(menuCategory.getId())){
			
			BeanMenuCategory category = new BeanMenuCategory();
			category.setName(menuCategory.getName());
			category.setPid((long) -1);
			category.setLevel("1");
			
			int count = categoryMapper.findByPageCount(category);
			
			if(count != 0){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("“"+menuCategory.getName()+"”名称已重复，请重新输入");
				return rs;
			}
			
			//上传图片
			if(!file.isEmpty()){
				byte[] bytes = file.getBytes() ;
				String logPath = FileUploadUtils.uploadFile(bytes,"upload/menuCategory","2",req) ;
				menuCategory.setLogoPath("/"+logPath);
			}
			
			menuCategory.setPid((long)-1);
			menuCategory.setLevel("1");
			menuCategory.setCreateTime(DateUtils.getLocalDate("yyyy-MM-dd HH:mm:ss"));
			
			categoryMapper.insert(menuCategory);
		}
		//修改
		else{
			
			BeanMenuCategory category = new BeanMenuCategory();
			category.setName(menuCategory.getName());
			category.setPid((long) -1);
			category.setLevel("1");
			
			List<BeanMenuCategory> list = categoryMapper.findByPage(category, 0, 1);
			
			if(ValidateUtils.isEmptyForCollection(list) || list.get(0).getId().equals(menuCategory.getId())){
				//上传图片
				if(!file.isEmpty()){
					byte[] bytes = file.getBytes() ;
					String logPath = FileUploadUtils.uploadFile(bytes,"upload/menuCategory","2",req) ;
					menuCategory.setLogoPath("/"+logPath);
				}
				
				categoryMapper.update(menuCategory);
			}else{
				rs.setStatus(Contents.EXIST);
				rs.setMsg("“"+menuCategory.getName()+"”名称已重复，请重新输入");
				return rs;
			}
		
		}
		
		return rs;
	}

	
	/**
	 * 查询菜谱二级分类数据列表
	 * @param searchName 分类名称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@Override
	public Result getSecondCategoryList(String searchName,String recommend, int page, int rows)
			throws Exception {
		Result rs = new Result();
		
		List<Map<String, Object>> list = categoryMapper.findSecondList(searchName,recommend,(page-1)*rows,rows);
		
		int count = categoryMapper.findSecondListCount(searchName,recommend);
		
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
	public List<Map<String, Object>> getFirstCombox() throws Exception {
		return  categoryMapper.getFirstCombox();
	}

	/**
	 * 新增或修改菜谱二级分类
	 * @param menuCategory 对象
	 * @return result
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@Override
	public Result saveTowCategory(BeanMenuCategory menuCategory,
			MultipartFile file, HttpServletRequest req) throws Exception {
		Result rs = new Result();
		
		//新增分类
		if(ValidateUtils.isBlank(menuCategory.getId())){
			
			BeanMenuCategory category = new BeanMenuCategory();
			category.setName(menuCategory.getName());
			category.setLevel("2");
			
			int count = categoryMapper.findByPageCount(category);
			
			if(count != 0){
				rs.setStatus(Contents.EXIST);
				rs.setMsg("“"+menuCategory.getName()+"”名称已重复，请重新输入");
				return rs;
			}
			
			//上传图片
			if(!file.isEmpty()){
				byte[] bytes = file.getBytes() ;
				String logPath = FileUploadUtils.uploadFile(bytes,"upload/menuCategory","2",req) ;
				menuCategory.setLogoPath("/"+logPath);
			}
			
			menuCategory.setLevel("2");
			menuCategory.setCreateTime(DateUtils.getLocalDate("yyyy-MM-dd HH:mm:ss"));
			
			categoryMapper.insert(menuCategory);
		}
		//修改
		else{
			
			BeanMenuCategory category = new BeanMenuCategory();
			category.setName(menuCategory.getName());
			category.setLevel("2");
			
			List<BeanMenuCategory> list = categoryMapper.findByPage(category, 0, 1);
			
			if(ValidateUtils.isEmptyForCollection(list) || list.get(0).getId().equals(menuCategory.getId())){
				//上传图片
				if(!file.isEmpty()){
					byte[] bytes = file.getBytes() ;
					String logPath = FileUploadUtils.uploadFile(bytes,"upload/menuCategory","2",req) ;
					menuCategory.setLogoPath("/"+logPath);
				}
				
				categoryMapper.update(menuCategory);
			}else{
				rs.setStatus(Contents.EXIST);
				rs.setMsg("“"+menuCategory.getName()+"”名称已重复，请重新输入");
				return rs;
			}
		
		}
		
		return rs;
	}

	/**
	 * 是否推荐
	 * @param id 分类id
	 * @param recommend 推荐标记字段
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@Override
	public Result recommend(Long id, String recommend) throws Exception {
		Result rs = new Result();
		
		BeanMenuCategory menuCategory = new BeanMenuCategory();
		menuCategory.setId(id);
		
		if(recommend.equals("1")){
			menuCategory.setRecommend("0");
		}
		
		if(recommend.equals("0")){
			menuCategory.setRecommend("1");
		}
	
		categoryMapper.update(menuCategory);
		
		return rs;
	}

	
	/**
	 * 根据一级id查询二级列表
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	@Override
	public List<Map<String, Object>> getSecondCombox(Long firstId) throws Exception {
		return categoryMapper.getSecondCombox(firstId);
	}
	

	/**
	 * * 暂停或者使用菜谱一级或者二级分类
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
			int count = categoryMapper.upCategoryStatus(ids,type,rank);
			rs.put("count", count);
			return rs;
		}
			rs.setStatus(Contents.PARAMS_ERROR);
			rs.setMsg("参数错误!!");
			return rs;
	}

	/**
	 * 查询一级菜谱状态
	 * @param asList
	 * @return
	 */
	@Override
	public Result getCategoryStatus(List<Long> ids) {
		Result rs = new Result();
			
		if(!ValidateUtils.isEmptyForCollection(ids)){
			List<String> list = categoryMapper.getCategoryStatus(ids);
			if(!ValidateUtils.isEmptyForCollection(list)){
				String str = "一级菜谱[ ";
				for (String s : list) {
					str += s +" ";
				}
				str += "]已停用,操作失败";
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
