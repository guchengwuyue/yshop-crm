package co.yixiang.yshop.module.crm.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        //chatMemory = "chatMemory",
        chatMemoryProvider = "chatMemoryProvider"
        //tools = "crmAgentTools"
)
public interface Assistant {
    String chat(String message);

    @SystemMessage(fromResource = "crm-prompt.txt")
    String chat(@MemoryId Long memoryId, @UserMessage String userMessage);
}
