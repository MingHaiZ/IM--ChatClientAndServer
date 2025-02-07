package com.easychat.utils;

import com.alibaba.fastjson.JSONObject;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DeepSeekApi {

    @Value("${deepseek.apiKey}")
    private String apiKey;

    private static final Logger log = LoggerFactory.getLogger(DeepSeekApi.class);

    public String useApi(String requestContent) {
        String responseContent = "调用服务器失败";
        try {

            HttpResponse<String> response = Unirest.post("https://api.siliconflow.cn/v1/chat/completions")
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .body("{\n  \"model\": \"deepseek-ai/DeepSeek-V3\",\n  \"messages\": [\n    {\n      \"content\": \"" + requestContent + "\",\n      \"role\": \"user\"\n    }\n  ]\n}")
                    .asString();
            if (response.getStatus() == 200) {
                JSONObject jsonObject = JSONObject.parseObject(response.getBody());
                String string = jsonObject
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content");
                responseContent = string;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("调用服务器失败");

        }
        return responseContent;
    }
}
