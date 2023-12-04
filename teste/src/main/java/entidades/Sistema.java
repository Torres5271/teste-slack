package entidades;

import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sistema {
    private Funcionario funcionario;
    private String emailFuncAtual;
    private Integer fkEmpresaFuncAtual;
    private Integer idFuncAtual;
    public void mensagemBoasVindas() {
        Scanner leitor = new Scanner(System.in);
        Integer opcao;
        do {
            System.out.printf("""
                    *------------------------------------*
                    | Olá, Seja bem-vindo(a) à DataSync! |
                    *------------------------------------*
                    | Informe o que deseja fazer!        |
                    |                                    |
                    | 0 - Sair                           |
                    | 1 - Login                          |
                    *------------------------------------* 
                    """);
            opcao = leitor.nextInt();

            if(opcao == 0) {
                System.out.printf("Encerrando aplicação, até logo!");
            } else if(opcao == 1) {

                mensagemSolicitarEmailSenha();
            } else {
                System.out.println("Digite uma opção válida!");
            }
        } while (opcao != 1 && opcao != 0);
    }

    public void mensagemSolicitarEmailSenha() {
        Scanner leitor = new Scanner(System.in);

        System.out.printf("""
                *-----------*
                | E-mail:   |
                *-----------*
                """);
        String email = leitor.nextLine();
        System.out.printf("""
                *-----------*
                | Senha:    |
                *-----------*
                """);
        String senha = leitor.nextLine();

        this.funcionario = new Funcionario(email, senha);
    }

    public void mensagemLoginValido(String nome) {
        System.out.printf("""
            *------------------------------------*
            Login efetuado!
            Bem-vindo(a) %s
            """, nome);

        registrarLogin(nome);
        mensagemOpcoesHardware();
    }

    private void registrarLogin(String nome) {
        String caminho = System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "Logs";
        String filePath = caminho + File.separator + "logins.txt";
        File directory = new File(caminho);
        if (!directory.exists()) {
            boolean criada = directory.mkdirs();
            if (!criada) {
                System.err.println("Erro ao criar o diretório de logs.");
                return;
            }
        }

        try {
            FileWriter writer = new FileWriter(filePath, true);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String dataFormatada = formatter.format(date);
            writer.write("Login bem-sucedido para: " + nome + " em " + dataFormatada + "\n");
            writer.close();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }
    }



    public void mensagemLoginInvalido() {
        Scanner leitor = new Scanner(System.in);
        System.out.printf("""
                 Nenhum funcionário encontrado com o email e senha fornecidos.
                 Deseja tentar novamente? (Y/N)
                 """);
        String opcao = leitor.nextLine();
        if(opcao.equalsIgnoreCase("y")) {
            mensagemSolicitarEmailSenha();
        } else {
            System.out.println("Você pode tentar fazer login novamente quando quiser, até mais!");
        }
    }


    public void setFuncAtual(String email, Integer fkEmpresa, Integer idFuncionario) {
        this.emailFuncAtual = email;
        this.fkEmpresaFuncAtual = fkEmpresa;
        this.idFuncAtual = idFuncionario;
    }

    public void mensagemOpcoesHardware() {
        Scanner leitor = new Scanner(System.in);
        Hardware hardware = new Hardware();
        DadosVolateis dadosVolateis = new DadosVolateis();

        Integer opcao;

        do {
            System.out.printf("""
                    *------------------------------------*
                    | Selecione o que deseja fazer:      |
                    |                                    |
                    | 1 - Cadastrar máquina              |
                    | 2 - Coletar dados da máquina       |
                    | 3 - Sair                           |
                    *------------------------------------*
                    """);
            opcao = leitor.nextInt();

            if(opcao == 1) {
                hardware.setInfoHardware(emailFuncAtual, fkEmpresaFuncAtual, idFuncAtual);
            } else if(opcao == 2) {
                dadosVolateis.inserirVolateis(idFuncAtual);
            } else if(opcao == 3) {
                System.out.println("Obrigado por utilizar a DataSync, Até mais!");
            } else {
                System.out.println("Digite uma opção válida!");
            }
        } while (opcao != 1 && opcao != 2 && opcao != 3);
    }
}
