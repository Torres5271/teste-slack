package SlackConfig;

import org.json.JSONObject;
import java.io.IOException;



public class EnvioSlack {
    public static void main(String[] args) throws IOException, InterruptedException {
        JSONObject json = new JSONObject();


        Double cpu = 89.03;
        String mensagem = String.format("AAAAAAAAAAAAAAAAAAAAA");



        json.put("text", mensagem);
        Slack.enviarMensagem(json);
    }
}
