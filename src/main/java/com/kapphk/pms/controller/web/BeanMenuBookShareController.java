package com.kapphk.pms.controller.web;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kapphk.pms.service.web.BeanMenuBookShareService;
import com.kapphk.pms.util.Result;

/**
 * 用户分享菜谱 控制层
 * @author huzi 2018-05-22
 *
 */
@RestController
@RequestMapping("/menuBookShare/")
public class BeanMenuBookShareController {

	@Autowired
	private BeanMenuBookShareService service;
	
	/**
	 * 分页查询（条件查询/条件排序）
	 * @param userName		用户名
	 * @param shareName		菜谱名
	 * @param sort			sort参数存放前端传递过来的 作为排序条件的字段createTime	
	 * @param order			order参数存放前端传递过来的排序规则(desc/asc)
	 * @param page			页数
	 * @param rows			每页显示的行数
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getMenuBookShareList.htm")
	public Result getMenuBookShareList(
				String userName,
				String shareName, 
				@RequestParam(value = "sort" ,required = false)String sort,
				@RequestParam(value = "order",required = false)String order,
				Integer page,
				Integer rows
		)throws Exception{
		
		String orderby = "";
		
		/**判断sort参数和order参数不为null时，拼接排序条件*/
		if(!StringUtils.isEmpty(sort)){	
			switch(sort){
			case "createTime":  orderby = "create_time"; break;
			default : break;
			}
		}
		
		if (order != null) {
			orderby = "a." + orderby + " " + order;	//a.create_time desc/asc
		}else {
			orderby = null;							//a.create_time desc
		}
		return service.getMenuBookShareList(userName, shareName, orderby, page, rows);
	}
	
	
	/**
	 * 批量删除
	 * @param ids  id集合
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delMenuBookShare.htm")
	public Result delMenuBookShare(Long[] ids)throws Exception{
		
		return  service.delMenuBookShare(Arrays.asList(ids));
	}
	
	
	
}
