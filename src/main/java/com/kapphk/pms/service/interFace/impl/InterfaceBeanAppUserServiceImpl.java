/**   
 * @Title: InterfaceBeanAppUserServiceImpl.java 
 * @Package com.kapphk.pms.service.interFace.impl 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author A18ccms A18ccms_gmail_com   
 * @date 2016-1-25 上午10:32:16 
 * @version V1.0   
 */
package com.kapphk.pms.service.interFace.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.kapphk.pms.bean.BeanEquipment;
import com.kapphk.pms.bean.BeanMenuBook;
import com.kapphk.pms.bean.BeanPhoneCode;
import com.kapphk.pms.bean.BeanUser;
import com.kapphk.pms.bean.BeanUserEquipment;
import com.kapphk.pms.bean.BeanUserMenuCategory;
import com.kapphk.pms.bean.BeanUserThirdParty;
import com.kapphk.pms.dao.mapper.BeanEquipmentMapper;
import com.kapphk.pms.dao.mapper.BeanMenuBookMapper;
import com.kapphk.pms.dao.mapper.BeanPhoneCodeMapper;
import com.kapphk.pms.dao.mapper.BeanUserEquipmentMapper;
import com.kapphk.pms.dao.mapper.BeanUserMapper;
import com.kapphk.pms.dao.mapper.BeanUserMenuCategoryMapper;
import com.kapphk.pms.dao.mapper.BeanUserThirdPartyMapper;
import com.kapphk.pms.service.BaseServiceImpl;
import com.kapphk.pms.service.interFace.InterfaceBeanAppUserService;
import com.kapphk.pms.util.Contents;
import com.kapphk.pms.util.DateUtils;
import com.kapphk.pms.util.MD5;
import com.kapphk.pms.util.Result;
import com.kapphk.pms.util.ValidateUtils;

/**
 * @ClassName: InterfaceBeanAppUserServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author XS
 * @date 2016-1-25 上午10:32:16
 * 
 * 修改于2018-01-29  huzi
 * 修改内容：extends BaseServiceImpl<BeanUser, Long>
 * 
 */

@Service
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class InterfaceBeanAppUserServiceImpl extends BaseServiceImpl<BeanUser, Long> implements
		InterfaceBeanAppUserService {

	@Autowired
	private BeanUserMapper userMapper;

	@Autowired
	private BeanEquipmentMapper equipmentMapper;

	@Autowired
	private BeanUserEquipmentMapper userEquipmentMapper;

	@Autowired
	private BeanUserThirdPartyMapper userThirdPartyMapper;

	@Autowired
	private BeanPhoneCodeMapper phoneCodeMapper;
	
	@Autowired
	private BeanMenuBookMapper menuBookMapper;

	@Autowired
	private BeanUserMenuCategoryMapper userMenuCategoryMapper;
	
	
	/**
	 * 2018-01-29  huzi
	 */
	@Override
	public void init() {
		super.setMapper(userMapper);
	}

	
	/**
	 * @Title: findByCondition
	 * @Description: 查询用户信息
	 * @param @param Condition
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public List<BeanUser> findByCondition(Map<String, Object> Condition) {
		return userMapper.findByCondition(Condition);
	}

	/**
	 * @Title: findByUserId
	 * @Description:根据用户id查询
	 * @param @param userId
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public Map<String, Object> findByUserId(Long userId) {
		return userMapper.findByUserId(userId);
	}

	/**
	 * 添加用户
	 * @Title: insert
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param user
	 * @param @param eList 设定文件
	 * @throws
	 */
	@Override
	public void insert(BeanUser user, String[] equipments) {
		//添加用户
		userMapper.insert(user);

		if (!ValidateUtils.isempty(equipments)) {
			for (String e : equipments) {

				BeanEquipment equipment = new BeanEquipment();

				// 保存设备
				equipment.setCreateTime(user.getCreateTime());
				equipment.setUid(user.getId());
				equipment.setStatus(1);
				equipment.setNumber(e);
				equipmentMapper.insert(equipment);

				BeanUserEquipment ue = new BeanUserEquipment();
				ue.setEquipmentId(equipment.getId());
				ue.setUserId(user.getId());
				ue.setCreateTime(user.getCreateTime());

				userEquipmentMapper.insert(ue);

			}
		}

	}

	/**
	 * 修改用户信息
	 * @Title: update
	 * @Description: 修改用户信息
	 * @param @param user 设定文件
	 * @throws
	 * 
	 *	修改于 2018-01-29  huzi
	 */
	@Override
	public int update(BeanUser user) {

		return userMapper.update(user);

	}

	/**
	 * Lotus 2016-04-11 用户登录
	 */
	@Override
	public void login(Integer type, String userName, String password, Result rs) {
		
		try {
			
			Map<String, Object> condition = new HashMap<String, Object>();
			BeanUser beanUser;
			// 一般登录
			if (type == 1) {
				if (ValidateUtils.isBlank(userName)
						|| ValidateUtils.isBlank(password)) {
					rs.setStatus(Contents.PARAMS_ERROR);
					return;
				}
				condition.put("userName", userName);
				condition.put("password", password);
				List<BeanUser> list = userMapper.findByCondition(condition);
				
				if (CollectionUtils.isEmpty(list)) {
					rs.setStatus(Contents.NOT_EXIST);
					rs.setMsg("用户名或密码有误");
					return;
				}

				beanUser = list.get(0);
				
				// 第三方登录(第三方登录密码格式： 第三方平台+第三方标识 再MD5加密，如
				// MD5.getMD5(qq+1234567890))
			} else if (type == 2) {
				
				BeanUserThirdParty userThirdParty = new BeanUserThirdParty();
				userThirdParty.setUid(password);//密码
				List<BeanUserThirdParty> list = userThirdPartyMapper.findByPage(userThirdParty, 0, 1);
				
				if (CollectionUtils.isEmpty(list)) {
					rs.setStatus("20010");
					rs.setMsg("第三方未注册");
					return;
				}
				
				beanUser = userMapper.findById(list.get(0).getUserId());
				
			} else {
				rs.setStatus(Contents.PARAMS_ERROR);
				return;
			}
			
			// 禁用用户  	2018-04-20  huzi
			if (beanUser.getStatus() == null || beanUser.getStatus() == 2) {
				rs.setStatus(Contents.TOKEN_FAILURE);
				rs.setMsg("该账户已被暂停使用");
				return;
			}
			
			// 设置用户最新登录时间   	2018-06-21  huzi
			beanUser.setLastTime(DateUtils.getLocalDate("yyyy-MM-dd HH:mm:ss"));
			userMapper.update(beanUser);
			
			Map<String, Object> user = new HashMap<String, Object>();
			user.put("id", beanUser.getId());
			user.put("nickName", beanUser.getNickName());
			user.put("logoPath", beanUser.getLogoPath());
			user.put("autograph", beanUser.getAutograph());
			BeanMenuBook menuBook=new BeanMenuBook();
			menuBook.setUid(beanUser.getId());
			Integer count = menuBookMapper.findByPageCount(menuBook);
			user.put("menuBookAmount", count);
			rs.put("user", user);
			
			rs.setStatus(Contents.OK);
			rs.setMsg("登录成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
	}

	// Lotus 2016-04-11 注册
	@Override
	public void register(Integer type, String platform, String uid,
			String userName, String password, String validCode,
			String nickName, String logoPath, String cuisineMenuCategoryIds,
			String flavorMenuCategoryIds, String[] equipments, Result rs) {
		// TODO Auto-generated method stub
		try {
			if (ValidateUtils.isBlank(userName)
					|| ValidateUtils.isBlank(password)
					|| ValidateUtils.isBlank(validCode)
					|| ValidateUtils.isBlank(nickName)
					|| ValidateUtils.isBlank(logoPath)
					|| ValidateUtils.isBlank(type)) {
				rs.setStatus(Contents.PARAMS_ERROR);
				return;
			}
			Map<String, Object> condition = new HashMap<String, Object>();

			// 判断验证码是否正确
			condition.put("code", validCode);
			condition.put("phone", userName);
			List<BeanPhoneCode> pcList = phoneCodeMapper
					.findByCondition(condition);
			if (CollectionUtils.isEmpty(pcList)) {
				rs.setStatus(Contents.EXIST);
				rs.setMsg("验证码有误");
				return;
			}

			// 第三方注册
			if (type == 2 && (ValidateUtils.isBlank(platform) || ValidateUtils.isBlank(uid))) {
				rs.setStatus(Contents.PARAMS_ERROR);
				return;
			} else {
				uid = MD5.getMD5(platform + uid);
			}

			
			// 判断用户是否存在
			condition.clear();
			condition.put("userName", userName);
			List<BeanUser> list = userMapper.findByCondition(condition);
			BeanUser user ;
			if (!CollectionUtils.isEmpty(list)) {
				// 一般注册
				if (type == 1) {
					rs.setStatus(Contents.EXIST);
					rs.setMsg("该号已经注册了");
					return;	
				}
				//第三方注册存在用户
				else if(type==2){
					user = list.get(0);
				}else{
					rs.setStatus(Contents.PARAMS_ERROR);
					return;
				}
			}else{
				// 构建用户对象
				user = new BeanUser();
				user.setUserName(userName);
				user.setPassword(password);
				user.setNickName(nickName);
				user.setLogoPath(logoPath);
				user.setCuisineMenuCategoryIds(cuisineMenuCategoryIds);
				user.setFlavorMenuCategoryIds(flavorMenuCategoryIds);
				user.setCreateTime(DateUtils.getLocalDate());
				user.setStatus(1);
				// 保存用户
				insert(user, equipments);
				//保存用户的菜系和口味
				BeanUserMenuCategory bumc = null;
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
			}
			
			//保存第三方登录信息
			if(type == 2){
				BeanUserThirdParty userThirdParty=new BeanUserThirdParty();
				userThirdParty.setUserId(user.getId());
				userThirdParty.setUid(uid);
				userThirdParty.setCreateTime(DateUtils.getLocalDate("yyyy-MM-dd"));
				userThirdPartyMapper.insert(userThirdParty);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(Contents.ERROR);
		}
	}

	/**
	 * 验证手机号
	 * Lotus 2016-04-25
	 */
	@Override
	public void validatePhone(String phone, String validCode, Result rs)
			throws Exception {
		if (ValidateUtils.isBlank(phone)
				|| ValidateUtils.isBlank(validCode)) {
			rs.setStatus(Contents.PARAMS_ERROR);
			return;
		}
		Map<String, Object> condition = new HashMap<String, Object>();

		// 判断验证码是否正确
		condition.put("code", validCode);
		condition.put("phone", phone);
		List<BeanPhoneCode> pcList = phoneCodeMapper
				.findByCondition(condition);
		if (CollectionUtils.isEmpty(pcList)) {
			rs.setStatus(Contents.EXIST);
			rs.setMsg("验证码有误");
			return;
		}
		
	}
	
	/**
	 *  修改于 2018-01-29  huzi
	 *	修改内容：inserts 改为  insertss
	 */
	@Override
	public void insertss(List<BeanUserMenuCategory> bumcList) throws Exception {
		// TODO Auto-generated method stub
		userMenuCategoryMapper.inserts(bumcList);
	}
	

}
