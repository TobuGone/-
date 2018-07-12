package com.kapphk.pms.util;

/**
 * excel导入  消息提示
 * @author 胡子  2018-04-16
 *
 */
public class ImportMessage{
	/**导入成功*/
	private int importSuccess;
	/**导入失败*/
	private int importFail;	
	/**重复导入*/
	private int importRepeat;
	
	public int getImportSuccess() {
		return importSuccess;
	}
	public void setImportSuccess(int importSuccess) {
		this.importSuccess = importSuccess;
	}
	public int getImportFail() {
		return importFail;
	}
	public void setImportFail(int importFail) {
		this.importFail = importFail;
	}
	public int getImportRepeat() {
		return importRepeat;
	}
	public void setImportRepeat(int importRepeat) {
		this.importRepeat = importRepeat;
	}
	public String getMessage() {
		if(this.getImportFail() == 0){
			return "本次成功导入：" + this.getImportSuccess() + "条，重复数据：" + this.getImportRepeat() + "条。";
		}
		return "本次成功导入：" + this.getImportSuccess() + "条，本次导入失败：" + this.getImportFail() + "条，重复数据：" + this.getImportRepeat() + "条，已忽略。";
	}
}