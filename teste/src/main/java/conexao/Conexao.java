package conexao;

import org.apache.commons.dbcp2.BasicDataSource;
;
import org.springframework.jdbc.core.JdbcTemplate;
public class Conexao {

    private JdbcTemplate conexao;

    public Conexao(){
        BasicDataSource data = new BasicDataSource();
        data.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Alterar aqui caso o seu banco não esteja como safesync
        data.setUrl("jdbc:mysql://localhost:3306/safesync");

        data.setUsername("root");
        data.setPassword("sptech");

        conexao = new JdbcTemplate(data);
    }
    public JdbcTemplate getConexao(){
        return conexao;
    }
}
