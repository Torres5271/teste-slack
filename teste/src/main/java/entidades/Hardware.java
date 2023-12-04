package entidades;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import conexao.Conexao;
import conexao.ConexaoSql;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class Hardware {
    Conexao conectar = new Conexao();
    JdbcTemplate con = conectar.getConexao();

    ConexaoSql conectar2 = new ConexaoSql();
    JdbcTemplate con2 = conectar2.getConexaosql();
    Looca looca = new Looca();
    entidades.Sistema sistema = new entidades.Sistema();


    private String sistemaOperacional;
    private Double totalDisco;
    private Double totalCpu;
    private Double totalRam;

    private String getSistemaOperacional() {
        Sistema sistema = new Sistema();
        return sistema.getSistemaOperacional();
    }

    private Double getTotalDisco() {
        DiscoGrupo disco = new DiscoGrupo();
        Volume volume = disco.getVolumes().get(0);
        return volume.getTotal().doubleValue() / 1000000000;
    }

    private Double getTotalCpu() {
        Processador processador = new Processador();
        return processador.getFrequencia().doubleValue() / 1000000000;
    }

    private Double getTotalRam() {
        Memoria memoria = new Memoria();
        return memoria.getTotal().doubleValue() / 1000000000;
    }

    public void setInfoHardware(String email, Integer fkEmpresa, Integer fkFuncionario) {
        try {
            con.queryForObject("SELECT hardwares.fkFuncionario FROM funcionarios INNER JOIN hardwares ON idFuncionario = fkFuncionario WHERE funcionarios.email = ?", Integer.class, email);

            System.out.println("Você já cadastrou as informações do hardware no sistema!");
            sistema.mensagemOpcoesHardware();
        } catch (EmptyResultDataAccessException e) {
            con.update("INSERT INTO hardwares (sistemaOperacional, totalCpu, totalDisco, totalRam, fkEmpresa, fkFuncionario) VALUES (?, ?, ?, ?, ?, ?)",
                    getSistemaOperacional(), getTotalCpu(), getTotalDisco(), getTotalRam(), fkEmpresa, fkFuncionario);
            System.out.println("Informações cadastradas!");
            sistema.mensagemOpcoesHardware();

        }

        try {
            con2.queryForObject("SELECT hardwares.fkFuncionario FROM funcionarios INNER JOIN hardwares ON idFuncionario = fkFuncionario WHERE funcionarios.email = ?", Integer.class, email);

            System.out.println("Você já cadastrou as informações do hardware no sistema!");
            sistema.mensagemOpcoesHardware();
        } catch (EmptyResultDataAccessException e) {
            con2.update("INSERT INTO hardwares (sistemaOperacional, totalCpu, totalDisco, totalRam, fkEmpresa, fkFuncionario) VALUES (?, ?, ?, ?, ?, ?)",
                    getSistemaOperacional(), getTotalCpu(), getTotalDisco(), getTotalRam(), fkEmpresa, fkFuncionario);
            System.out.println("Informações cadastradas!");
            sistema.mensagemOpcoesHardware();

        }
    }

    @Override
    public String toString() {
        return String.format("""
                Hardware: {
                    Sistema Operacional: %s
                    Velocidade Cpu: %.2f GHz
                    Total disco: %.2f Gb
                    Total Ram: %.2f Gb
                }
                """, sistemaOperacional, totalCpu, totalDisco, totalRam);
    }
}
