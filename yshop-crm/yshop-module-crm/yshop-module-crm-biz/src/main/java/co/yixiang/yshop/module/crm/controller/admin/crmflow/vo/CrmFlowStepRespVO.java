package co.yixiang.yshop.module.crm.controller.admin.crmflow.vo;

import co.yixiang.yshop.framework.mybatis.core.dataobject.BaseDO;
import co.yixiang.yshop.module.system.controller.admin.user.vo.user.UserRespVO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;

/**
 * 审批步骤 CrmFlowStepRespVO
 *
 * @author yshop
 */
@Data
public class CrmFlowStepRespVO{

    /**
     * id
     */
    private Long id;
    /**
     * 审批流程ID
     */
    private Long flowId;
    /**
     * 1上级管理员，2指定用户（任意一人）3-指定用户（多人会签）
     */
    private Integer type;
    /**
     * 审批人ID,
     */
    private String adminIds;

    /**
     * 审批流程顺序
     */
    private Integer relation;

    // '0-待审核 1-审核通过,2-审核拒绝',
    private Integer status;

    /**
     * 用户信息
     */
    private List<UserRespVO> userRespVOList;

}