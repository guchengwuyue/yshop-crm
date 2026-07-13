package co.yixiang.yshop.module.crm.controller.admin.crmoperatelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 操作日志新增/修改 Request VO")
@Data
public class CrmOperatelogSaveReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4476")
    private Long id;

    @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17340")
    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @Schema(description = "合同ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21099")
    @NotNull(message = "合同ID不能为空")
    private Long contractId;

    @Schema(description = "回款ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10316")
    @NotNull(message = "回款ID不能为空")
    private Long receivableId;

    @Schema(description = "姓名", example = "赵六")
    private String nickname;

    @Schema(description = "管理员账号", example = "王五")
    private String username;

    @Schema(description = "操作页面", example = "https://www.yixiang.co")
    private String url;

    @Schema(description = "日志标题")
    private String title;

    @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "内容不能为空")
    private String content;

    @Schema(description = "IP")
    private String ip;

    @Schema(description = "User-Agent")
    private String useragent;

}