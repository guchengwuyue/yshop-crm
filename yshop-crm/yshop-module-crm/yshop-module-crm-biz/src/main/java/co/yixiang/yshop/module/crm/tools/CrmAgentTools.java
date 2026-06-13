package co.yixiang.yshop.module.crm.tools;

import co.yixiang.yshop.module.crm.service.crmcustomer.CrmCustomerService;
import dev.langchain4j.agent.tool.Tool;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class CrmAgentTools {

    @Resource
    private CrmCustomerService crmCustomerService;
    @Tool
    double String (double a, double b){
        return a + b;
    }

    @Tool
    String String (String a, String b){
        return a + b;
    }
}
