package com.kapphk.pms.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kapphk.pms.bean.BeanMenuBookComment;
import com.kapphk.pms.service.web.BeanMenuBookCommentService;
import com.kapphk.pms.util.Result;

/**
 * 评论 控制层
 * @author 胡子
 * 2018/03/12
 *
 */

@RestController
@RequestMapping({"/comment/"})
public class BeanMenuBookCommentController {
	
	  @Autowired
	  private BeanMenuBookCommentService service;
	  
	  /**
	   * 查询所有评论
	   * @param bean
	   * @param name
	   * @param page
	   * @param rows
	   * @return
	   */
	  @RequestMapping("getList.htm")
	  public Result getList(BeanMenuBookComment bean, String name, int page, int rows)
	  {
	    Result rs = new Result();
	    try
	    {
	      rs = service.getList(bean, name, page, rows, rs);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      rs.setStatus(Result.MSG.ERROR.getStatus());
	      rs.setMsg("出现错误");
	    }
	    return rs;
	  }
	  
	  /**
	   * 删除评论
	   * @param ids
	   * @return
	   */
	  @RequestMapping("delete.htm")
	  public Result delete(String ids)
	  {
	    Result rs = new Result();
	    try
	    {
	      rs = service.delete(ids, rs);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      rs.setStatus(Result.MSG.ERROR.getStatus());
	      rs.setMsg("出现错误");
	    }
	    return rs;
	  }
	  
	  
	  /**
	   * 更新评论
	   * @param bean
	   * @return
	   */
	  @RequestMapping("operater.htm")
	  public Result operater(BeanMenuBookComment bean)
	  {
	    Result rs = new Result();
	    try
	    {
	      rs = service.operater(bean, rs);
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	      rs.setStatus(Result.MSG.ERROR.getStatus());
	      rs.setMsg("出现错误");
	    }
	    return rs;
	  }
}
