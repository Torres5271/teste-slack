package entidades;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import conexao.Conexao;
import conexao.ConexaoSql;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class Funcionario {
    Conexao conectar = new Conexao();
    JdbcTemplate con = conectar.getConexao();

    ConexaoSql conectar2 = new ConexaoSql();
    JdbcTemplate con2 = conectar2.getConexaosql();
    Sistema sistema = new Sistema();

    private Integer idFuncionario;
    private String nome;
    private String email;
    private Integer fkEmpresa;

    public Funcionario(String email, String senha) {
        try {
            String sql = "SELECT idFuncionario, nomeFuncionario, email, fkEmpresa FROM funcionarios WHERE email = ? AND senha = ?";
            con.queryForObject(sql, new Object[]{email, senha}, (rs, rowN) -> {
                this.idFuncionario = rs.getInt(1);
                this.nome = rs.getString(2);
                this.email = rs.getString(3);
                this.fkEmpresa = rs.getInt(4);
                return null;
            });

            sistema.setFuncAtual(this.email, this.fkEmpresa, this.idFuncionario);
            sistema.mensagemLoginValido(this.nome);
        } catch (EmptyResultDataAccessException e) {
            sistema.mensagemLoginInvalido();
            criarLogErroBancoDados(email + " " + "Erro ao tentar fazer login: " + e.getMessage());
        }

        try {
            String sql = "SELECT idFuncionario, nomeFuncionario, email, fkEmpresa FROM funcionarios WHERE email = ? AND senha = ?";
            con2.queryForObject(sql, new Object[]{email, senha}, (rs, rowN) -> {
                this.idFuncionario = rs.getInt(1);
                this.nome = rs.getString(2);
                this.email = rs.getString(3);
                this.fkEmpresa = rs.getInt(4);
                return null;
            });

            sistema.setFuncAtual(this.email, this.fkEmpresa, this.idFuncionario);
            sistema.mensagemLoginValido(this.nome);
        } catch (EmptyResultDataAccessException e) {
            sistema.mensagemLoginInvalido();
            criarLogErroBancoDados(email + " " + "Erro ao tentar fazer login: " + e.getMessage());
        }
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    @Override
    public String toString() {
        return String.format("""
                Funcionário: {
                idFuncionario: %d
                fkEmpresa: %d
                Nome: %s
                email: %s
                }
                """, idFuncionario, fkEmpresa, nome, email);
    }



    public void criarLogErroBancoDados(String mensagem) {
        String caminho = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "Logs";
        String nomeArquivo = caminho + File.separator + "erros_banco_dados.txt";

        File diretorioLogs = new File(caminho);
        if (!diretorioLogs.exists()) {
            boolean criada = diretorioLogs.mkdirs();
            if (!criada) {
                System.err.println("Erro ao criar o diretório de logs.");
                return;
            }
        }

        try {
            FileWriter writer = new FileWriter(nomeArquivo, true);
            writer.write(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " - " + mensagem + "\n");
            writer.close();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }
    }



}
