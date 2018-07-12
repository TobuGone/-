package com.kapphk.pms.service.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.kapphk.pms.bean.BeanFoodStore;
import com.kapphk.pms.service.BaseService;
import com.kapphk.pms.util.Result;

public interface BeanFoodStoreService extends BaseService<BeanFoodStore, Long>{

	/**
	 * 查询食材库数据列表
	 * @param searchName 食材学名
	 * @param page 页数
	 * @param rows 每页显示的行数
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-25
	 */
	public Result searchFoodStoreList(String searchName, int page, int rows)throws Exception;

	/**
	 * 新增或修改食材库
	 * @param foodStore 食材库对象
	 * @param firstId 一级分类id
	 * @param secondId 二级分类id
	 * @param file 文件流
	 * @param req
	 * @return
	 * @throws Exception
	 * @author huangzh 2016-01-25
	 */
	public Result saveFoodStore(BeanFoodStore foodStore, Long firstId,
			Long secondId, Long[] menuId, MultipartFile file,
			HttpServletRequest req)throws Exception;

	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 * huangzh 2015-08-14
	 * @throws Exception
	 */
	public Map<String, Object> findMapById(Long id)throws Exception;

	/**
	 * 删除食材库
	 * @param ids
	 * @return
	 * @throws Exception
	 * huangzh 2015-08-14
	 */
	public Result delFoodStore(Long[] ids)throws Exception;

	/**
	 * 食材下拉框数据
	 * @return List
	 * @throws Exception
	 * @author huangzh 2016-01-20
	 */
	public List<Map<String, Object>> getSecondCombox(Long foodId);

}
