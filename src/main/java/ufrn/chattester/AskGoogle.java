package ufrn.chattester;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zju.cst.aces.api.config.Config;
import zju.cst.aces.api.config.ModelConfig;
import zju.cst.aces.dto.ChatResponse;
import zju.cst.aces.util.AskGPT;
import zju.cst.aces.dto.Message;


import java.io.IOException;
import java.util.*;
import java.util.List;
import io.github.cdimascio.dotenv.Dotenv;



public class AskGoogle extends AskGPT {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public AskGoogle(Config config) {
        super(config);
    }

    
    @Override
    public ChatResponse askChatGPT(List<Message> chatMessages) {
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("G_KEY");
        int maxTry = 1;
        config.getLog().info(chatMessages.toString());
        while (maxTry > 0) {
            Response response = null;
            try {
                Map<String, Object> payload = new HashMap<>();

//                if (Objects.equals(config.getModel(), "code-llama") || Objects.equals(config.getModel(), "code-llama-13B")) {
//                    payload.put("max_tokens", 8092);
//                }

                ModelConfig modelConfig = config.getModel().getDefaultConfig();
                StringBuilder inputText = new StringBuilder();
                
                // Initialize the array-like structure
                List<Map<String, Object>> arrayMessageOb = new ArrayList<>();

                for (Message message : chatMessages) {
                    String messageS = message.getContent().trim()
                    if(messageS!=""){
                        Map<String, Object> messageOb = new HashMap<>();
                        messageOb.put("role",message.getRole());
                        Map<String, Object> text = new HashMap<>();
                        text.put("text", messageS);
                        messageOb.put("parts", text);
                        arrayMessageOb.add(messageOb);
                    }
                }
                payload.put("contents", arrayMessageOb.toArray());
                payload.put("temperature", config.getTemperature());
                
                payload.put("frequency_penalty", config.getFrequencyPenalty());
                payload.put("presence_penalty", config.getPresencePenalty());
                
                payload.put("maxTokens", config.getMaxResponseTokens());
                
                payload.put("stream", false);
                String jsonPayload = GSON.toJson(payload);
                config.getLog().info(jsonPayload);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonPayload);
                
                Request request = new Request.Builder().url("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key="+apiKey).post(body).addHeader("Content-Type", "application/json").build();
                
                response = config.getClient().newCall(request).execute();
                
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                try {
                    Thread.sleep(config.sleepTime);
                } catch (InterruptedException ie) {
                    throw new RuntimeException("In AskGPT.askChatGPT: " + ie);
                }
                if (response.body() == null) throw new IOException("Response body is null.");
                //config.getLog().info(response.body().string());
                String responseString = ResponseAdapter.AdaptGoogle(response.body().string());
                config.getLog().error(responseString);
                ChatResponse chatResponse = GSON.fromJson(responseString, ChatResponse.class);
                config.getLog().info(chatResponse.toString());
                response.close();
                return chatResponse;
                
            } catch (IOException e) {
                if (response != null) {
                    response.close();
                }
                config.getLog().error("In AskGoogle.askChatGPT: " + e);
                maxTry--;
            }
            catch (Exception e) {
                config.getLog().error("Error in conversion: " + e);
            }
        }
        config.getLog().debug("AskGoogle: Failed to get response\n");
        return null;
    }

}
