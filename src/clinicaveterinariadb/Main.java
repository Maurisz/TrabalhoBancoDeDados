package clinicaveterinariadb;

import clinicaveterinariadb.DAO.*;
import clinicaveterinariadb.Entities.Animal;
import clinicaveterinariadb.Entities.Proprietario;
import clinicaveterinariadb.Entities.Veterinario;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static final Scanner scan = new Scanner(System.in);
    private static final Connection conexao;

    static {
        try {
            conexao = BancoConexao.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final DebugDAO debugDAO = new DebugDAO(conexao);
    private static final VeterinarioDAO vetDAO = new VeterinarioDAO(conexao);
    private static final ProprietarioDAO propDAO = new ProprietarioDAO(conexao);
    private static final AnimalDAO animalDAO = new AnimalDAO(conexao);
    private static final ConsultaDAO consultaDAO = new ConsultaDAO(conexao);

    public static void main(String[] args) throws SQLException {
        try (conexao) {
            int escolha = 0;
            while (escolha != 6) {
                exibirMenuPrincipal();
                escolha = lerInt("Sua opção : ");

                switch (escolha) {
                    case 1 -> menuCreate();
                    case 2 -> menuRead();
                    case 3 -> menuUpdate();
                    case 4 -> menuDelete();
                    case 5 -> menuDebug();
                    case 6 -> System.out.println("Saindo do programa...");
                }
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n----------- MENU -----------");
        System.out.println("1 - Create");
        System.out.println("2 - Read");
        System.out.println("3 - Update");
        System.out.println("4 - Delete");
        System.out.println("5 - Debug");
        System.out.println("6 - Sair");
    }

    private static int lerInt(String msg) {
        while (true) {
            try {
                return Integer.parseInt(ler(msg));
            } catch (NumberFormatException e) {
                System.out.print("Inválido. Digite um número inteiro: ");
            }
        }
    }

    private static BigDecimal lerDecimal(String msg) {
        while (true) {
            try {
                return new BigDecimal(ler(msg).replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.print("Inválido. Digite um valor decimal (ex: 10.50): ");
            }
        }
    }

    private static String ler(String msg) {
        System.out.print(msg);
        return scan.nextLine();
    }

    private static Timestamp lerDataHora(String msg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        while (true) {
            try {
                String dataTexto = ler(msg);
                LocalDateTime dateTime = LocalDateTime.parse(dataTexto, formatter);
                return Timestamp.valueOf(dateTime);
            } catch (Exception e) {
                System.out.println("Erro: Formato inválido. Use dd/MM/yyyy HH:mm (Ex: 25/12/2025 14:30)");
            }
        }
    }

    private static void menuCreate() {
        System.out.println("\n--- CADASTRO ---");
        System.out.println("1 - Veterinário\n2 - Proprietário\n3 - Animal\n4 - Consulta");
        int op = lerInt("Escolha: ");

        switch (op) {
            case 1 -> createVeterinario();
            case 2 -> createProprietario();
            case 3 -> createAnimal();
            case 4 -> createConsulta();
            default -> System.out.println("Opção inválida.");
        }
    }

    public static void createVeterinario() {

        System.out.println("Insira os dados do veterinário");
        String nome = ler("Nome: ");
        String crmv = ler("CRMV: ");
        String especialidade = ler("Especialidade: ");
        String telefone = ler("Telefone: ");
        System.out.println();

        try {
            vetDAO.create(new Veterinario(nome, crmv, especialidade, telefone));
            System.out.println("Veterinário inserido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao create veterinário: " + e.getMessage());
        }
    }

    public static void createProprietario() {
        System.out.println("Insira os dados do proprietário");

        String nome = ler("Nome: ");
        String cpf = ler("CPF : ");
        String telefone = ler("Telefone: ");
        String endereco = ler("Endereco: ");
        String email = ler("Email: ");
        System.out.println();

        try {
            propDAO.create(new Proprietario(cpf, nome, telefone, endereco, email));
            System.out.println("Proprietário inserido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao create proprietário: " + e.getMessage());
        }
    }

    public static void createAnimal() {
        System.out.println("Insira os dados do animal");

        String nome = ler("Nome : ");
        String especie = ler("Espécie : ");
        String raca = ler("Raça : ");
        String dataTexto = ler("Data de nascimento (DD/MM/AAAA) : ");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataLocal = LocalDate.parse(dataTexto, formatter);
        Date dataNascimento = Date.valueOf(dataLocal);

        BigDecimal peso = lerDecimal("Peso : ");
        String cpf = ler("CPF do proprietário : ");
        System.out.println();

        Animal novoAnimal = new Animal(nome, especie, raca, dataNascimento, peso);

        try {
            animalDAO.create(novoAnimal, cpf);
            System.out.println("Animal inserido com sucesso! ID : " + novoAnimal.getId());
        } catch (Exception e) {
            System.out.println("Erro: Não foi possível cadastrar. Verifique o CPF.");
        }
    }

    public static void createConsulta() {
        System.out.println("Insira os dados da consulta");

        Timestamp dataHora = lerDataHora("Data e Hora (dd/MM/yyyy HH:mm) : ");
        String diagnostico = ler("Diagnóstico : ");
        BigDecimal valor = lerDecimal("Valor : ");
        int idAnimal = lerInt("ID do animal : ");
        String idCrmv = ler("CRMV do veterinário : ");
        System.out.println();

        try {
            consultaDAO.create(dataHora, diagnostico, valor, idAnimal, idCrmv);
            System.out.println("Consulta agendada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao create consulta: " + e.getMessage());
        }
    }

    private static void menuRead() {
        System.out.println("\n--- READ ---");
        System.out.println("1 - Veterinários\n2 - Proprietários\n3 - Animais\n4 - Consultas");
        int op = lerInt("Escolha : ");

        switch (op) {
            case 1 -> vetDAO.read().forEach(System.out::println);
            case 2 -> propDAO.read().forEach(System.out::println);
            case 3 -> animalDAO.read().forEach(System.out::println);
            case 4 -> consultaDAO.read().forEach(System.out::println);
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void menuUpdate() {
        System.out.println("\n--- ATUALIZAÇÃO ---");
        System.out.println("1 - Veterinário\n2 - Proprietário\n3 - Animal");
        int op = lerInt("Escolha : ");

        switch (op) {
            case 1 -> updateVeterinario();
            case 2 -> updateProprietario();
            case 3 -> updateAnimal();
            default -> System.out.println("Opção inválida.");
        }
    }

    public static void updateVeterinario() {
        System.out.println("\n--- ATUALIZAÇÃO ---");
        String crmvAlvo = ler("Insira o CRMV do veterinário que deseja atualizar : ");

        if (vetDAO.existe(crmvAlvo)) {

            System.out.print("O que deseja atualizar?\n1 - Nome\n2 - Especialidade\n3 - Telefone\nOpção : ");
            int opcaoCampo = lerInt("Escolha : ");
            boolean sucessoVet = false;

            try {
                switch (opcaoCampo) {
                    case 1:
                        String novoNome = ler("Digite o NOVO Nome : ");
                        sucessoVet = vetDAO.updateNome(crmvAlvo, novoNome);
                        break;
                    case 2:
                        String novaEspecialidade = ler("Digite a NOVA Especialidade : ");
                        sucessoVet = vetDAO.updateEspecialidade(crmvAlvo, novaEspecialidade);
                        break;
                    case 3:
                        String novoTelefone = ler("Digite o NOVO Telefone : ");
                        sucessoVet = vetDAO.updateTelefone(crmvAlvo, novoTelefone);
                        break;
                    default:
                        System.out.println("Opção de campo inválida!");
                }
                if (sucessoVet) {
                    System.out.println("Registro atualizado com sucesso!");
                }
            } catch (Exception e) {
                System.out.println("Erro ao atualizar: " + e.getMessage());
            }
        } else {
            System.out.println("CRMV não encontrado no banco.");
        }
    }

    public static void updateProprietario() {
        System.out.println("\n--- ATUALIZAÇÃO ---");
        System.out.println("Insira os dados para validação: ");
        String cpfAlvo = ler("CPF do proprietário: ");
        if (propDAO.existe(cpfAlvo)) {
            System.out.println("O que deseja atualizar do proprietário?");
            System.out.println("1 - Nome\n2 - Telefone\n3 - Endereço\n4 - Email");
            int updateProprietarioEscolha = lerInt("Escolha : ");
            boolean sucessoProprietario = false;


            try {
                switch (updateProprietarioEscolha) {
                    case 1:
                        String novoNome = ler("Insira o novo nome: ");
                        sucessoProprietario = propDAO.updateNome(cpfAlvo, novoNome);
                        break;
                    case 2:
                        String novoNumero = ler("Insira o novo telefone (DDD e número): ");
                        sucessoProprietario = propDAO.updateTelefone(cpfAlvo, novoNumero);
                        break;
                    case 3:
                        String novoEndereco = ler("Insira o novo endereço: ");
                        sucessoProprietario = propDAO.updateEndereco(cpfAlvo, novoEndereco);
                        break;
                    case 4:
                        String novoEmail = ler("Insira o novo email: ");
                        sucessoProprietario = propDAO.updateEmail(cpfAlvo, novoEmail);
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
                if (sucessoProprietario) {
                    System.out.println("Dados atualizados com sucesso!");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            System.out.println("Proprietário não encontrado!");
        }
    }

    public static void updateAnimal() {
        System.out.println("\n--- ATUALIZAÇÃO ---");
        System.out.println("Animais cadastrados");
        String sql = "SELECT * FROM animais";
        try {
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Insira o ID do animal : ");
            while (resultSet.next()) {
                int idAnimal = resultSet.getInt("id");
                String nomeAnimal = resultSet.getString("nome");
                System.out.println(idAnimal + " - " + nomeAnimal);
            }
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
        int animalID = lerInt("ID : ");
        if (animalDAO.existe(animalID)) {
            System.out.println("\n--- ATUALIZAÇÃO ---");
            System.out.println("O que deseja atualizar do animal?");
            System.out.println("1 - Nome\n2 - Espécie\n3 - Raça\n4 - Peso");
            int updateAnimalSwitch = lerInt("Escolha : ");
            boolean sucessoAnimal = false;
            try {
                switch (updateAnimalSwitch) {
                    case 1:
                        String novoAnimalNome = ler("Insira o novo nome : ");
                        sucessoAnimal = animalDAO.updateNome(animalID, novoAnimalNome);
                        break;
                    case 2:
                        String novoAnimalEspecie = ler("Atualize o nome da espécie: ");
                        sucessoAnimal = animalDAO.updateNome(animalID, novoAnimalEspecie);
                        break;
                    case 3:
                        String novoAnimalRaca = ler("Atualize o nome da raça: ");
                        sucessoAnimal = animalDAO.updateNome(animalID, novoAnimalRaca);
                        break;
                    case 4:
                        BigDecimal novoAnimalPeso = lerDecimal("Insira o novo peso (em kg): ");
                        sucessoAnimal = animalDAO.updatePeso(animalID, novoAnimalPeso);
                        break;
                    default:
                        System.out.println("Insira uma opção válida!");
                        break;
                }
                if (sucessoAnimal) {
                    System.out.println("Dados atualizados com sucesso!");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Animal não encontrado!");
        }
    }

    private static void menuDelete() {
        System.out.println("\n--- DELETE ---");
        System.out.println("1 - Veterinário\n2 - Proprietário\n3 - Animal\n4 - Consulta");
        int op = lerInt("Escolha: ");

        switch (op) {
            case 1 -> vetDAO.delete(ler("CRMV do Veterinário: "));
            case 2 -> propDAO.delete(ler("CPF do Proprietário: "));
            case 3 -> {
                System.out.println("--- Animais Disponíveis ---");
                animalDAO.read().forEach(a -> System.out.println(a.getId() + " - " + a.getNome()));
                animalDAO.delete(lerInt("ID do Animal para deletar: "));
            }
            case 4 -> consultaDAO.delete(lerInt("ID da Consulta: "));
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void menuDebug() {
        System.out.println("\n--- DEBUG ---");
        System.out.println("1 - Popular Banco\n2 - Limpar Banco\n3 - Reiniciar Banco");
        int op = lerInt("Escolha: ");
        switch (op) {
            case 1 -> debugDAO.popularBanco(vetDAO, propDAO, animalDAO, consultaDAO);
            case 2 -> debugDAO.limparTudo();
            case 3 -> {
                debugDAO.limparTudo();
                debugDAO.popularBanco(vetDAO, propDAO, animalDAO, consultaDAO);
            }
            default -> System.out.println("Opção inválida.");
        }
    }
}