package com.kapphk.pms.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kapphk.pms.bean.BeanMenuCategory;
import com.kapphk.pms.bean.MenuFirstCategory;
import com.kapphk.pms.bean.PaginationCondition;

/**
 * 的数据操作接口
 * @author zoneyu 2016-01-20
*/
public interface BeanMenuCategoryMapper extends BaseMapper<BeanMenuCategory, Long> {

	/**
	 * 查询菜谱二级分类数据列表
	 * @param searchName 分类名称
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	public List<Map<String, Object>> findSecondList(@Param(value="searchName")String searchName,
			@Param(value="recommend")String recommend,
			@Param(value="page")Integer page, 
			@Param(value="rows")Integer rows);

	
	public int findSecondListCount(@Param(value="searchName")String searchName,@Param(value="recommend")String recommend);

	/**
	 * 查询一级分类下拉框数据
	 * @return List
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	public List<Map<String, Object>> getFirstCombox();


	public List<Map<String, Object>> getSecondCombox(Long firstId);
	
	
	public List<Map<String,Object>> findByCondition(@Param("param") Map<String,Object> condition);
	public List<Map<String,Object>> findPagination(@Param("param") Map<String,Object> condition,@Param("pc") PaginationCondition pc);
	
	
	/**   
	* @Title: findTowMenuCategory    
	* @Description:  查询一级，二级分类关系   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-26 下午4:03:37
	*/
	public List<MenuFirstCategory> findTowMenuCategory(@Param("conditionId")Long conditionId,@Param("pc") PaginationCondition pc);
	
	/**
	 * * 暂停或者使用菜谱一级或者二级分类
	 * @param ids id
	 * @param type 状态
	 * @param rank 级别
	 * @return
	 */
	public int upCategoryStatus(@Param("ids")List<Long> ids,@Param("type")String type,@Param("rank")String rank);


	/**
	 * 查询一级菜谱状态
	 * @param asList
	 * @return
	 */
	public List<String> getCategoryStatus(@Param("ids")List<Long> ids);
	
	
}