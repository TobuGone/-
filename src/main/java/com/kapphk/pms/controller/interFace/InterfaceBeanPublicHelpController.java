package com.kapphk.pms.controller.interFace;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.kapphk.pms.bean.BeanPublicHelp;
import com.kapphk.pms.service.interFace.InterfaceBeanPublicHelpService;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.Result.MSG;

/**
 * 详情控制层
 * @author zoneyu 15-12-29
 */
@RestController
@RequestMapping("/public/")
public class InterfaceBeanPublicHelpController {

	@Autowired
	private InterfaceBeanPublicHelpService service ;
	
	
	@RequestMapping("findPublicHelpDetails.do")
	public Result findPublicHelpDetails(BeanPublicHelp help,Result rs){
		try {
			String memo = service.findPublicDetail(help) ;
			rs.put("memo", memo);
		} catch (Exception e) {
			rs.setStatus(MSG.ERROR.getStatus()) ;
			rs.setMsg("出现错误") ;
			e.printStackTrace();
		}
		return  rs;
	}
	
}
