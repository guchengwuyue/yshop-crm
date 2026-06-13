package co.yixiang.yshop.module.crm.controller.admin.crmindex;

import co.yixiang.yshop.framework.common.pojo.CommonResult;
import co.yixiang.yshop.module.crm.assistant.Assistant;
import co.yixiang.yshop.module.crm.controller.admin.crmindex.vo.ChatVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static co.yixiang.yshop.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 首页agent")
@RestController
@RequestMapping("/crm/agent")
@Validated
public class AgentController {

    @Resource
    private Assistant assistant;


    @GetMapping("/chat")
    @Operation(summary = "agent聊天")
    public CommonResult<String> chat(@RequestBody ChatVO chatVO) {
       String msg = assistant.chat(chatVO.getMemoryId(), chatVO.getMessage());
        return success("");
    }







}