package co.yixiang.yshop.module.system.api.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserSaveDTO {

    private Long id;

    private String username;

    private String nickname;

    private String remark;

    private Long deptId;

    private Set<Long> postIds;

    private String email;

    private String mobile;

    private Integer sex;

    private String avatar;

    private String password;



}
