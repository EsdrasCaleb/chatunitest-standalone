package ufrn;
import com.llama4j.Llama3;
import com.llama4j.Qwen2;
import java.nio.file.Path;

public class ModelHelpers {

    public static String generateResponseLlama(String modelPath, String prompt, String previousContext) {
        try {
            // Load the model
            Llama model = ModelLoader.loadModel(Path.of(modelPath), 512);

            // Initialize the state for generation
            Llama.State state = model.createNewState(Llama3.BATCH_SIZE);

            // Prepare the conversation context
            String fullContext = (previousContext == null ? "" : previousContext + "\n") + "USER: " + prompt + "\nASSISTANT: ";
            var tokenizer = model.tokenizer();
            var inputTokens = tokenizer.encode(fullContext);

            // Generate tokens
            var outputTokens = Llama.generateTokens(model, state, 0, inputTokens, null, 128, null, false, null);

            // Decode and return the response
            return tokenizer.decode(outputTokens);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating response: " + e.getMessage();
        }
    }

    public static String generateResponseQwen(String modelPath, String prompt, String previousContext) {
        try {
            // Load the Qwen2 model
            Llama model = ModelLoader.loadModel(Path.of(modelPath), 512);

            // Initialize the state for generation
            Llama.State state = model.createNewState(16);

            // Prepare the conversation context
            String fullContext = (previousContext == null ? "" : previousContext + "\n") + "USER: " + prompt + "\nASSISTANT: ";
            var tokenizer = model.tokenizer();
            var inputTokens = tokenizer.encode(fullContext);

            // Generate tokens
            var outputTokens = Llama.generateTokens(model, state, 0, inputTokens, null, 128, null, false, null);

            // Decode and return the response
            return tokenizer.decode(outputTokens);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating response: " + e.getMessage();
        }
    }


}
