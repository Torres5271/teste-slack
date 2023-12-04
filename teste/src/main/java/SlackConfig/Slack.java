package SlackConfig;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;

public class Slack {
    private static HttpClient client = HttpClient.newHttpClient();
    private static String tokenCriptografado = "T065YAV8EJE/B068XEMUERF/MPmE3L2LWGqftWRjrnE0F6gb";
    private static final String url;

    static {
        try {
            url = "https://hooks.slack.com/services/" + descriptografar(tokenCriptografado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void enviarMensagem(JSONObject content) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .header("accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(String.format("Status: %s", response.statusCode()));
        System.out.println(String.format("Response: %s", response.body()));
    }

    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "hello-world12345";
    private static final String CHARSET = "UTF-8";

    public static String descriptografar(String textoCriptografado) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(CHARSET), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(textoCriptografado));
        return new String(decrypted, CHARSET);
    }
}
