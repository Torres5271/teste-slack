package conexao;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class ConexaoSql {

    private JdbcTemplate conexaosql;

    public ConexaoSql(){
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");



        dataSource.setUrl("jdbc:sqlserver://ec2-44-208-19-238.compute-1.amazonaws.com;" +
                "database=safesync;" +
                "user=sa;" +
                "password=urubu100;" +
                "trustServerCertificate=true;");
        dataSource.setUsername("sa");
        dataSource.setPassword("urubu100");



        conexaosql = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getConexaosql() {
        return conexaosql;
    }
}
