package com.kapphk.pms.bean;

public class PaginationCondition {

	private Integer page = 0;
	private Integer rows = 15 ;
	
	private Integer p = 0;


	public void setPage(Integer page) {
		p = page;
		this.page = page == null || page <= 0 ? 1 :page;
		if(this.rows != null && this.rows > 0){
			this.page =  (this.page - 1)*this.rows;
		}
	}
	public void setRows(Integer rows) {
		this.rows = rows == null || rows < 1 ? 10 :rows;
		if(this.p != null && this.p > 0){  //防止 先调用setPage儿page为空的情况
			this.page =  (this.p - 1)*this.rows;
		}
	}
	
 
	public Integer getPage() {
		return page;
	}
	
	public Integer getRows() {
		return rows;
	}



}
