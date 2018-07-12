package com.kapphk.pms.bean;

/**
 * 错误类
 * 主键：sys_error_problem
 * @author zoneyu 2016-01-13
*/
public class BeanErrorProblem extends BaseModel {

    /**
     * 表字段：sys_error_problem.id 注释：主键id
     * @author zoneyu 2016-01-13
     */
    private Long id;
    /**
     * 表字段：sys_error_problem.error_name 注释：错误名称
     * @author zoneyu 2016-01-13
     */
    private String errorName;
    /**
     * 表字段：sys_error_problem.error_number 注释：错误编号
     * @author zoneyu 2016-01-13
     */
    private String errorNumber;
    /**
     * 表字段：sys_error_problem.content 注释：错误内容
     * @author zoneyu 2016-01-13
     */
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    public String getErrorNumber() {
        return errorNumber;
    }

    public void setErrorNumber(String errorNumber) {
        this.errorNumber = errorNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}