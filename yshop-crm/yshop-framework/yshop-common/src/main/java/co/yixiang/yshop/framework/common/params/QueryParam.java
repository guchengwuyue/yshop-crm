/**
 * Copyright (C) 2018-2022
 * All rights reserved, Designed By www.yixiang.co

 */
package co.yixiang.yshop.framework.common.params;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;


@Data
@Schema(description = "用户 APP - 查询参数对象")
public abstract class QueryParam implements Serializable{
    private static final long serialVersionUID = -3263921252635611410L;

    @Schema(description = "页码,默认为1", required = true)
	private Integer page =1;

    @Schema(description = "页大小,默认为10", required = true)
	private Integer limit = 10;

    @Schema(description = "搜索字符串", required = true)
    private String keyword;

//    @Schema(description = "当前第几页", required = true)
//    public void setCurrent(Integer current) {
//	    if (current == null || current <= 0){
//	        this.page = 1;
//        }else{
//            this.page = current;
//        }
//    }
//
//    public void setSize(Integer size) {
//	    if (size == null || size <= 0){
//	        this.limit = 10;
//        }else{
//            this.limit = size;
//        }
//    }

}
