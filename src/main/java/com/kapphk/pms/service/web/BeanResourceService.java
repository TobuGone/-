package com.kapphk.pms.service.web;

import java.util.List;

import com.kapphk.pms.bean.BeanResource;

public interface BeanResourceService {

	public List<BeanResource> findUserResourcesByUserId(Long userId);

}
