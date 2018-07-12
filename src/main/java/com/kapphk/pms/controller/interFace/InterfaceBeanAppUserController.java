/**   
* @Title: InterfaceBeanAppUserController.java 
* @Package com.kapphk.pms.controller.interFace 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2016-1-25 上午10:59:46 
* @version V1.0   
*/
package com.kapphk.pms.controller.interFace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kapphk.pms.bean.BeanErrorProblem;
import com.kapphk.pms.bean.BeanMessagePush;
import com.kapphk.pms.bean.BeanPhoneCode;
import com.kapphk.pms.bean.BeanPublicHelp;
import com.kapphk.pms.bean.BeanUser;
import com.kapphk.pms.bean.BeanUserMenuCategory;
import com.kapphk.pms.dao.mapper.BeanUserMenuCategoryMapper;
import com.kapphk.pms.service.interFace.InterfaceBeanAppUserService;
import com.kapphk.pms.service.interFace.InterfaceBeanEquipmentService;
import com.kapphk.pms.service.interFace.InterfaceBeanErrorProblemService;
import com.kapphk.pms.service.interFace.InterfaceBeanPhoneCodeService;
import com.kapphk.pms.service.interFace.InterfaceBeanPublicHelpService;
import com.kapphk.pms.service.interFace.InterfaceBeanSystemMessageService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DataUtils;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.MD5;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;
import com.kapphk.pms.util.XMLRegionUtils;

/**   
 * @ClassName: InterfaceBeanAppUserController    
 * @Description: 用户管理 
 * @author XS  
 * @date 2016-1-25 上午10:59:46    
 *         
 */

@RestController
@RequestMapping("/appuser/")
public class InterfaceBeanAppUserController {
	
	@Autowired
	private InterfaceBeanAppUserService appUserService;
	
	@Autowired
	private InterfaceBeanEquipmentService equipmentService;
	
	@Autowired
	private InterfaceBeanPhoneCodeService phoneCodeService;
	
	@Autowired
	private InterfaceBeanPublicHelpService publicHelpService;
	
	@Autowired
	private InterfaceBeanErrorProblemService errorProblemService;
	
	@Autowired
	private InterfaceBeanSystemMessageService  systemMessageService;
	
	@Autowired
	private BeanUserMenuCategoryMapper userMenuCategoryMapper;
	
	@RequestMapping("login.aspf")
    public Result login(Integer type ,String userName,String password,Result rs){
		appUserService.login(type, userName, password, rs);
		return rs;
	}
	
	/**   
	* @Title: register    
	* @Description: 注册   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-27 下午3:58:02
	*/
	@RequestMapping("register.aspf")
	public Result register(Integer type,String platform,String uid,String userName,String password,String validCode,String nickName,String logoPath, String cuisineMenuCategoryIds , String flavorMenuCategoryIds,String[]equipments,Result rs)throws Exception{
		appUserService.register(type, platform, uid, userName, password, validCode, nickName, logoPath, cuisineMenuCategoryIds, flavorMenuCategoryIds, equipments, rs);
		return rs;
	}
	
	
	
	/**   
	* @Title: updateUserInfo    
	* @Description: 更新用户信息
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-28 下午2:34:25
	*/
	@RequestMapping("updateUserInfo.aspf")
	public Result updateUserInfo(Long userId,String nickName,String logoPath,String cuisineMenuCategoryIds,
			                    String flavorMenuCategoryIds,String sex,String email,String autograph,
			                    Long birthProvinceId,Long birthCityId,
			                    Long dwellingProvinceId,Long dwellingCityId,Long dwellingDistrictId,String dwellingAddress,
			                    String contacter,String mobilePhone,
			                    Long contacterProviceId,Long contacterCityId,Long contacterDistrictId,String contacterAddress,Result rs){
		try {
			
			if(ValidateUtils.isBlank(userId) || ValidateUtils.isBlank(logoPath) || ValidateUtils.isBlank(nickName)){
				return rs;
			}
			
			BeanUser user = new BeanUser();
			user.setId(userId);
			user.setNickName(nickName);
			user.setLogoPath(logoPath);
			user.setCuisineMenuCategoryIds(cuisineMenuCategoryIds);
			user.setFlavorMenuCategoryIds(flavorMenuCategoryIds);
			user.setSex(sex);
			user.setEmail(email);
			user.setAutograph(autograph);
			user.setBirthProvince(birthProvinceId);
			user.setBirthCity(birthCityId);
			user.setProvince(dwellingProvinceId);
			user.setCity(dwellingCityId);
			user.setDistrict(dwellingDistrictId);
			user.setAddress(dwellingAddress);
			user.setContacter(contacter);
			user.setMobilePhone(mobilePhone);
			user.setContacterProvice(contacterProviceId);
			user.setContacterCity(contacterCityId);
			user.setContacterDistrict(contacterDistrictId);
			user.setContacterAddress(contacterAddress);
			
			appUserService.update(user);
			//更新用户的菜系和口味
			//先删除用户的菜系和口味
			BeanUserMenuCategory bumc = new BeanUserMenuCategory();
			bumc.setUid(userId);
			userMenuCategoryMapper.deleteByEntity(bumc);
			
			List<BeanUserMenuCategory> bumcList = new ArrayList<BeanUserMenuCategory>();
			if(!ValidateUtils.isBlank(cuisineMenuCategoryIds)){
				String[] cmc = cuisineMenuCategoryIds.split(",");
				for (String s : cmc) {
					bumc = new BeanUserMenuCategory();
					bumc.setUid(user.getId());
					bumc.setCategoryId(Long.parseLong(s));
					bumc.setCreateTime(user.getCreateTime());
					bumcList.add(bumc);
				}
			}
			if(!ValidateUtils.isBlank(flavorMenuCategoryIds)){
				String[] fmc = flavorMenuCategoryIds.split(",");
				for (String s : fmc) {
					bumc = new BeanUserMenuCategory();
					bumc.setUid(user.getId());
					bumc.setCategoryId(Long.parseLong(s));
					bumc.setCreateTime(user.getCreateTime());
					bumcList.add(bumc);
				}
			}
			if(!ValidateUtils.isEmptyForCollection(bumcList)){
				userMenuCategoryMapper.inserts(bumcList);	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	/**   
	* @Title: resetPassword    
	* @Description: 重置密码  type= 忘记密码；type=2 修改密码  
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-27 下午2:52:16
	*/
	@RequestMapping("resetPassword.aspf")
	public Result resetPassword(Integer type,String phone,String password,String newPassword,String validCode,Result rs){
		try {
			if(ValidateUtils.isBlank(type)  || ValidateUtils.isBlank(phone)  || ValidateUtils.isBlank(newPassword) ){
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			 Map<String,Object> condition = new HashMap<String, Object>();
			 List<BeanUser> list = null;
			//忘记密码
			if(type == 1){
					if(ValidateUtils.isBlank(validCode)){
						rs.setStatus(Contents.PARAMS_ERROR);
						return rs;
					}
					//判断用户是否存在
					 condition.put("userName", phone);
					 list = appUserService.findByCondition(condition);
					 if(CollectionUtils.isEmpty(list)){
						 rs.setStatus(Contents.EXIST);
						 rs.setMsg("该手机号还没有注册");
						 return rs;
					 }
					//判断验证码
					 condition.put("code", validCode);
					 condition.put("phone", phone);
					 List<BeanPhoneCode> pcList = phoneCodeService.findByCondition(condition);
					 if(CollectionUtils.isEmpty(pcList)){
						 rs.setStatus(Contents.EXIST);
						 rs.setMsg("验证码有误");
						 return rs;
					 }
			  }else if(type == 2){
				    if(ValidateUtils.isBlank(password)){
						rs.setStatus(Contents.PARAMS_ERROR);
						return rs;
					 }
				    //判断用户是否存在
					 condition.put("userName", phone);
					 condition.put("password", password);
					 list = appUserService.findByCondition(condition);
					 if(CollectionUtils.isEmpty(list)){
						 rs.setStatus(Contents.EXIST);
						 rs.setMsg("用户名或密码有误");
						 return rs;
					 }
			  }else{
				     rs.setStatus(Contents.PARAMS_ERROR);
				     return rs;
			  }
			  //修改密码
			  BeanUser user = new BeanUser();
			  user.setId(list.get(0).getId());
			  user.setPassword(newPassword);
			  appUserService.update(user);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	/**   
	* @Title: getAgreement    
	* @Description:获取用户协议
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-27 下午3:45:23
	*/
	@RequestMapping("getDocument.aspf")
	public Result getAgreement(Long id,Integer type,String errorName ,Result rs){
		try {
			 Map<String,Object> condition = new HashMap<String, Object>();
			 String content = "";
			 if(ValidateUtils.isBlank(type)){
				 rs.setStatus(Contents.PARAMS_ERROR);
				 return rs;
			 }
			 if(type == 1){
				 condition.put("title", "用户协议");
				 List<BeanPublicHelp> phList = publicHelpService.findByCondition(condition);
				 if(!CollectionUtils.isEmpty(phList)){
					 content = phList.get(0).getMemo();
				 }
			}else if(type == 2){
				condition.put("title", "关于我们");
				List<BeanPublicHelp> phList = publicHelpService.findByCondition(condition);
				if(!CollectionUtils.isEmpty(phList)){
					 content = phList.get(0).getMemo();
				 }
			}else if(type == 3){//常见错误
				if(ValidateUtils.isBlank(id) && ValidateUtils.isBlank(errorName)){
					rs.setStatus(Contents.PARAMS_ERROR);
					return rs;
				}
				condition.put("id", id);
				condition.put("errorName", errorName);
				List<BeanErrorProblem> list = errorProblemService.findByCondition(condition);
				if(!CollectionUtils.isEmpty(list)){
					 content = list.get(0).getContent();
				 }
			}else if(type == 4){//系统消息
				if(ValidateUtils.isBlank(id)){
					rs.setStatus(Contents.PARAMS_ERROR);
					return rs;
				}
				 condition.put("id", id);
				 BeanMessagePush mp = systemMessageService.findById(id);
				 content = mp != null  && !ValidateUtils.isBlank(mp.getContent())?mp.getContent():"";
			}else{
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			 
			 rs.put("content", content);
		} catch (Exception e) {
           e.printStackTrace();
           rs.setStatus(Contents.ERROR);
		 }
		return rs;
	}
	
	
	/**   
	* @Title: findUserDetails    
	* @Description:  查询用户详情   
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-28 上午9:51:23
	*/
	@RequestMapping("findUserDetails.aspf")
    public Result findUserDetails(Long userId,Result rs){
		try {
			if(ValidateUtils.isBlank(userId)){
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			
			Map<String,Object> user = appUserService.findByUserId(userId);
			rs.put("user", user != null ? user : new HashMap<String, Object>());
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}	
	/**   
	* @Title: getValidateCode    
	* @Description: 获取验证码  
	* @param     type = 1 注册 ; type = 2  忘记密码或更改手机号码
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-27 上午9:29:25
	*/
	@RequestMapping("getValidateCode.aspf")
	public Result getValidateCode(String phone,Integer type,Result rs){
		try {
			if(ValidateUtils.isBlank(phone) || ValidateUtils.isBlank(type)){
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			if(!phone.startsWith("1")|| phone.length() != 11 || !ValidateUtils.isNumeric(phone)){
				rs.setStatus(Contents.PARAMS_ERROR);
				rs.setMsg("你的手机号不正确,请验证后重新输入");
				return rs;
			}
			Map<String,Object> condition = new HashMap<String, Object>();
			condition.put("userName", phone);
		    List<BeanUser> list =	appUserService.findByCondition(condition);
			//注册
			if(type == 1){
				 if(!CollectionUtils.isEmpty(list)){
					 rs.setStatus(Contents.EXIST);
					 rs.setMsg("你的手机号已经注册,请直接登录");
					 return rs;
				 }
		    //忘记密码或者更改手机号码
			}else if(type == 2){
				 if(CollectionUtils.isEmpty(list)){
					 rs.setStatus(Contents.EXIST);
					 rs.setMsg("你的手机号还未注册");
					 return rs;
				 }
			}//绑定手机号
			else if(type == 3){
				
			}
			else{
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			//保存、发送验证码
			 String code = DataUtils.getRadom(4);
			 BeanPhoneCode pc = new BeanPhoneCode();
			 pc.setCode(code);
			 pc.setPhone(phone);
			 pc.setCreateTime(DateUtils.getLocalDate());
			 phoneCodeService.insert(pc);
			 rs.put("code", code);
		} catch (Exception e) {
			 e.printStackTrace();
			 rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	
	/**   
	* @Title: findEquipments    
	* @Description: 查询用户设备  
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-29 下午3:53:39
	*/
	@RequestMapping("findUserEquipments.aspf")
    public Result 	findEquipments(Long userId,Result rs){
		try {
			if(ValidateUtils.isBlank(userId)){
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			  //查询用户设备
			Map<String,Object> condition = new HashMap<String, Object>();
			condition.put("uid", userId);
		    List<Map<String, Object>> eList =    equipmentService.findMyEquipmentByCondition(condition);
		    rs.put("equipments", eList != null ? eList : new ArrayList<Object>());
		} catch (Exception e) {
		   e.printStackTrace();
		  rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	
	@RequestMapping("saveEquipments.aspf")
	public Result  saveEquipments(Long userId ,String number,Result rs){
		
		try {
			if(ValidateUtils.isBlank(userId) || ValidateUtils.isBlank(number)){
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			equipmentService.save(userId, number);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
		
	}
	
	
	
	/**   
	* @Title: deleteEquipment    
	* @Description:  删除设备
	* @param     设定文件    
	* @return     返回类型    
	* @author xs  
	* @date 2016-1-27 下午3:54:32
	*/
	@RequestMapping("deleteEquipment.aspf")
	public Result deleteEquipment(Long equipmentId,Result rs){
		try {
			if(ValidateUtils.isBlank(equipmentId)){
				rs.setStatus(Contents.PARAMS_ERROR);
				return rs;
			}
			equipmentService.delete(equipmentId);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
	/**
	 * 获取省市区列表
	 * @author huangzh 2016-02-25
	 * @return
	 */
	@RequestMapping("getRegion.aspf")
	public Result getRegion(){
		Result rs = new Result();
		
		List<Map<String, Object>> list = XMLRegionUtils.read();
		
		rs.put("provinces", list);
		
		return rs;
	}
	
	/**
	 * 验证手机号
	 * Lotus 2016-04-25
	 */
	@RequestMapping("validatePhone.aspf")
	public Result validatePhone(String phone,String validCode, Result rs){
		try {
			appUserService.validatePhone(phone,validCode,rs);
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
		return rs;
	}
	
}
