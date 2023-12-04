package entidades;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import conexao.Conexao;
import conexao.ConexaoSql;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class DadosVolateis {
    Conexao conectar = new Conexao();
    JdbcTemplate con = conectar.getConexao();

    ConexaoSql conectar2 = new ConexaoSql();
    JdbcTemplate con2 = conectar2.getConexaosql();
    Sistema sistema = new Sistema();

    private Double consumoDisco;
    private Double consumoCpu;
    private Double consumoRam;
    private Integer totalJanelas;
    private Timer tempoInsercao = new Timer();

    private Double getConsumoDisco() {
        DiscoGrupo disco = new DiscoGrupo();
        Volume volume = disco.getVolumes().get(0);

        return (volume.getTotal().doubleValue() / 1000000000) - (volume.getDisponivel().doubleValue() / 1000000000);
    }

    private Double getConsumoCpu() {
        Processador processador = new Processador();
        return processador.getUso();
    }

    private Double getConsumoRam() {
        Memoria memoria = new Memoria();
        return memoria.getEmUso().doubleValue() / 1000000000;
    }

    private Integer getTotalJanelas() {
        Looca looca = new Looca();

        return looca.getGrupoDeJanelas().getTotalJanelas();
    }

    public void inserirVolateis(Integer fkFuncionario) {
        try {
            String sql = "SELECT idHardware FROM hardwares WHERE fkFuncionario = ?";
            con.queryForObject(sql, new Object[]{fkFuncionario}, (rs, rowN) -> {
                Integer fkHardware = rs.getInt(1);

                tempoInsercao.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Date data = new Date();
                        con.update("INSERT INTO volateis (consumoCpu, consumoDisco, consumoRam, totalJanelas, dataHora, fkHardware) VALUES (?, ?, ?, ?, ?, ?)",
                                getConsumoCpu(), getConsumoDisco(), getConsumoRam(), getTotalJanelas(), new Timestamp(data.getTime()), fkHardware);

                        System.out.printf("""
                        *------------------------------------*
                        | Captura de dados voláteis          |
                        *------------------------------------*
                        | Data coleta: %s
                        |
                        | Uso do processador: %.2f GHz
                        | Uso do disco: %.2f Gb
                        | Uso da memória RAM: %.2f Gb
                        *-------------------------------------
                        """, new Timestamp(data.getTime()), getConsumoCpu(), getConsumoDisco(), getConsumoRam());
                    }
                },10000, 10000);
                return null;
            });
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Não foi possível coletar dados, o funcionário não tem uma máquina cadastrada!");
        }

        try {
            String sql = "SELECT idHardware FROM hardwares WHERE fkFuncionario = ?";
            con2.queryForObject(sql, new Object[]{fkFuncionario}, (rs, rowN) -> {
                Integer fkHardware = rs.getInt(1);

                tempoInsercao.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Date data = new Date();
                        con2.update("INSERT INTO volateis (consumoCpu, consumoDisco, consumoRam, totalJanelas, dataHora, fkHardware) VALUES (?, ?, ?, ?, ?, ?)",
                                getConsumoCpu(), getConsumoDisco(), getConsumoRam(), getTotalJanelas(), new Timestamp(data.getTime()), fkHardware);
                    }
                },10000, 10000);
                return null;
            });
        } catch (EmptyResultDataAccessException e) {
            System.out.println("Não foi possível coletar dados, o funcionário não tem uma máquina cadastrada!");
        }
    }

    private void dadosVolateisColetados() {

    }
}
