package com.kapphk.pms.service.interFace;

import java.util.List;
import java.util.Map;

import com.kapphk.pms.bean.BeanErrorProblem;
import com.kapphk.pms.util.Result;

/**   
* @ClassName: InterfaceErrorProblemService    
* @Description: 电磁炉错误信息管理  
* @author XS  
* @date 2016-1-25 下午2:49:28    
*         
*/
public interface InterfaceBeanErrorProblemService {
	
	public Result findError(String errorNumber,String errorName,Result rs);
	
	public List<BeanErrorProblem> errorDetail(String errorNumber, String errorName);

	public List<BeanErrorProblem> findByCondition(Map<String, Object> condition);

}
