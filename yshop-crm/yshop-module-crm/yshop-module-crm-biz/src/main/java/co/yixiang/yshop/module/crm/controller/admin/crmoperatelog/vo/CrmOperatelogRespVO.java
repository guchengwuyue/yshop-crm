package co.yixiang.yshop.module.crm.controller.admin.crmoperatelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 操作日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmOperatelogRespVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4476")
    @ExcelProperty("ID")
    private Long id;

    @Schema(description = "客户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17340")
    @ExcelProperty("客户ID")
    private Long customerId;

    @Schema(description = "合同ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21099")
    @ExcelProperty("合同ID")
    private Long contractId;

    @Schema(description = "回款ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10316")
    @ExcelProperty("回款ID")
    private Long receivableId;

    @Schema(description = "姓名", example = "赵六")
    @ExcelProperty("姓名")
    private String nickname;

    @Schema(description = "管理员账号", example = "王五")
    @ExcelProperty("管理员账号")
    private String username;

    @Schema(description = "操作页面", example = "https://www.yixiang.co")
    @ExcelProperty("操作页面")
    private String url;

    @Schema(description = "日志标题")
    @ExcelProperty("日志标题")
    private String title;

    @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("内容")
    private String content;

    @Schema(description = "IP")
    @ExcelProperty("IP")
    private String ip;

    @Schema(description = "User-Agent")
    @ExcelProperty("User-Agent")
    private String useragent;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}