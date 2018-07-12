package com.kapphk.pms.bean;

import java.util.List;
/**
 * 标准资源
 * @author 胡子
 *
 */
public class BeanStandardResource {
	
	private Integer id;								//主键id
	private String text;							//文本内容
	private String state;							//国家
	private String url;								//路径
	private List<BeanStandardResource> children;	//集合
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<BeanStandardResource> getChildren() {
		return children;
	}
	public void setChildren(List<BeanStandardResource> children) {
		this.children = children;
	}

}
