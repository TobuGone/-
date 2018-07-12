package com.kapphk.pms.controller.interFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kapphk.pms.bean.BeanErrorProblem;
import com.kapphk.pms.service.interFace.InterfaceBeanErrorProblemService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;

/**   
 * @ClassName: InterfaceErrorProblemController    
 * @Description: 电磁炉错误管理
 * @author XS  
 * @date 2016-1-25 下午2:54:55    
 *         
 */
@RestController
@RequestMapping("/error/")
public class InterfaceErrorProblemController {

	@Autowired
	private InterfaceBeanErrorProblemService errorProblemService;
	
	
	@RequestMapping("findError.aspf")
	public Result findError(String errorNumber,String errorName,Result rs){
		errorProblemService.findError(errorNumber,errorName,rs);
		return rs;
	}
	
	@RequestMapping("errorDetail.aspf")
	public ModelAndView errorDetail(String errorNumber,String errorName,HttpServletRequest request){
		try {
			List<BeanErrorProblem> list = errorProblemService.errorDetail(errorNumber,errorName);
			if(!ValidateUtils.isBlank(list)){
				BeanErrorProblem bean= list.get(0);
				String memo = "";
				if(!ValidateUtils.isBlank(bean))
				{
					memo = bean.getContent();
				}
				request.setAttribute("memo", memo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("/jsp/detail.jsp");
	}
	
	
	
}
