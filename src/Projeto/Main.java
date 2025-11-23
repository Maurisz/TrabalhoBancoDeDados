package Projeto;

import Projeto.DAO.*;
import Projeto.Entities.Animal;
import Projeto.Entities.Consulta;
import Projeto.Entities.Proprietario;
import Projeto.Entities.Veterinario;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static final Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws SQLException {
        try (Connection conexao = BancoConexao.getConnection()) {

            DebugDAO debug = new DebugDAO(conexao);
            VeterinarioDAO vetDAO = new VeterinarioDAO(conexao);
            ProprietarioDAO propDAO = new ProprietarioDAO(conexao);
            AnimalDAO animalDAO = new AnimalDAO(conexao);
            ConsultaDAO consultaDAO = new ConsultaDAO(conexao);


            int escolha = 0;

            while (escolha != 5) {

                System.out.println("\nEscolha o que deseja realizar : \n1 - Create\n2 - Read\n3 - Update\n4 - Delete\n5 - Sair do programa\n6 - Debug");
                escolha = scan.nextInt();
                scan.nextLine();

                switch (escolha) {
                    case 1:
                        create(vetDAO, propDAO, animalDAO, consultaDAO);
                        break;
                    case 2:
                        read(vetDAO, propDAO, animalDAO, consultaDAO);
                        break;
                    case 3:
                        update(vetDAO, propDAO, animalDAO, conexao);
                        break;
                    case 4:
                        delete(vetDAO, propDAO, animalDAO, consultaDAO, conexao);
                        break;
                    case 5:
                        System.out.println("Saindo do programa...");
                        break;
                    case 6:
                        System.out.println("Insira a opção de debug :\n1 - Preencher banco\n2 - Limpar banco\n3 - Reinicar banco");
                        escolha = scan.nextInt();
                        scan.nextLine();
                        switch (escolha) {
                            case 1:
                                debug.popularBanco(vetDAO, propDAO, animalDAO, consultaDAO);
                                break;
                            case 2:
                                debug.limparTudo();
                                break;
                                case 3:
                                    debug.limparTudo();
                                    debug.popularBanco(vetDAO, propDAO, animalDAO, consultaDAO);
                                    break;
                            default:
                                System.out.println("Escolha novamente!");
                        }
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            }
        }
    }

    public static void create(VeterinarioDAO vetDAO, ProprietarioDAO propDAO, AnimalDAO animalDAO, ConsultaDAO consultaDAO) {
        Scanner scan = new Scanner(System.in);
        System.out.println("O que deseja criar?\n1 - Veterinario\n2 - Proprietario\n3 - Animal\n4 - Consulta\n5 - Voltar");
        int escolha = scan.nextInt();
        scan.nextLine();

        switch (escolha) {
            case 1:
                createVeterinario(vetDAO);
                break;
            case 2:
                createProprietario(propDAO);
                break;
            case 3:
                createAnimal(animalDAO);
                break;
            case 4:
                createConsulta(consultaDAO);
                break;
            case 5:
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    public static void createVeterinario(VeterinarioDAO vetDAO) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Insira os dados do veterinário\nNome : ");
        String nome = scan.nextLine();

        System.out.print("CRMV : ");
        String crmv = scan.nextLine();

        System.out.print("Especialidade : ");
        String especialidade = scan.nextLine();

        System.out.print("Telefone : ");
        String telefone = scan.nextLine();

        System.out.println();

        try {
            vetDAO.create(new Veterinario(nome, crmv, especialidade, telefone));
            System.out.println("Veterinário inserido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao create veterinário: " + e.getMessage());
        }
    }

    public static void createProprietario(ProprietarioDAO propDAO) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Insira os dados do proprietário\nNome : ");
        String nome = scan.nextLine();

        System.out.print("CPF : ");
        String cpf = scan.nextLine();

        System.out.print("Telefone : ");
        String telefone = scan.nextLine();

        System.out.print("Endereço : ");
        String endereco = scan.nextLine();

        System.out.print("Email : ");
        String email = scan.nextLine();

        System.out.println();
        try {
            propDAO.create(new Proprietario(cpf, nome, telefone, endereco, email));
            System.out.println("Proprietário inserido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao create proprietário: " + e.getMessage());
        }
    }

    public static void createAnimal(AnimalDAO animalDAO) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Insira os dados do animal\nNome : ");
        String nome = scan.nextLine();

        System.out.print("Espécie : ");
        String especie = scan.nextLine();

        System.out.print("Raça : ");
        String raca = scan.nextLine();

        System.out.print("Data de nascimento (DD/MM/AAAA) : ");
        String dataTexto = scan.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataLocal = LocalDate.parse(dataTexto, formatter);
        Date dataNascimento = Date.valueOf(dataLocal);

        System.out.print("Peso : ");
        BigDecimal peso = scan.nextBigDecimal();
        scan.nextLine();

        System.out.print("CPF do proprietário : ");
        String cpf = scan.nextLine();

        System.out.println();

        Animal novoAnimal = new Animal(nome, especie, raca, dataNascimento, peso);
        try {
            animalDAO.create(novoAnimal, cpf);
            System.out.println("Animal inserido com sucesso! ID : " + novoAnimal.getId());
        } catch (Exception e) {
            System.out.println("Erro: Não foi possível cadastrar. Verifique o CPF.");
        }
    }

    public static void createConsulta(ConsultaDAO consultaDAO) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Insira os dados da consulta\nData e Hora (dd/MM/yyyy HH:mm) : ");
        String dataTexto = scan.nextLine();

        if (dataTexto.isEmpty()) {
            dataTexto = scan.nextLine();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dataTexto, formatter);
        Timestamp dataParaBanco = Timestamp.valueOf(dateTime);

        System.out.print("Diagnóstico : ");
        String diagnostico = scan.nextLine();

        System.out.print("Valor : ");
        BigDecimal valor = scan.nextBigDecimal();

        System.out.print("ID do animal : ");
        int idAnimal = scan.nextInt();
        scan.nextLine();

        System.out.print("CRMV do veterinário : ");
        String idCrmv = scan.nextLine();

        System.out.println();
        try {
            consultaDAO.create(dataParaBanco, diagnostico, valor, idAnimal, idCrmv);
            System.out.println("Entities.createConsulta agendada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao create consulta: " + e.getMessage());
        }
    }

    public static void read(VeterinarioDAO vetDAO, ProprietarioDAO propDAO, AnimalDAO animalDAO, ConsultaDAO consultaDAO) {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nEscolha o que deseja ler : ");
        System.out.println("1 - Veterinário\n2 - Proprietário\n3 - Animais\n4 - Consultas");
        int escolha = scan.nextInt();
        switch (escolha) {
            case 1:
                readVeterinario(vetDAO);
                break;
            case 2:
                readProprietario(propDAO);
                break;
            case 3:
                readAnimais(animalDAO);
                break;
            case 4:
                readConsultas(consultaDAO);
                break;
        }
    }

    public static void readVeterinario(VeterinarioDAO vetDAO) {
        for (Veterinario vet : vetDAO.read()) {
            System.out.println("\nNome : " + vet.getNome() + "\nCRMV : " + vet.getCRMV() + "\nEspecialidade : " + vet.getEspecialidade() + "\nTelefone : " + vet.getTelefone());
        }
    }

    public static void readProprietario(ProprietarioDAO propDAO) {
        for (Proprietario prop : propDAO.read()) {
            System.out.println("\nNome : " + prop.getNome() + "\nCPF : " + prop.getCpf() + "\nTelefone : " + prop.getTelefone() + "\nEndereco : " + prop.getEndereco() + "\nEmail : " + prop.getEmail());
        }
    }

    public static void readAnimais(AnimalDAO animalDAO) {
        for (Animal animal : animalDAO.read()) {
            System.out.println("\nID : " + animal.getId() + "\nNome : " + animal.getNome() + "\nEspécie : " + animal.getEspecie() + "\nRaça : " + animal.getRaca() + "\nData de nascimento : " + animal.getDataNascimento() + "\nPeso : " + animal.getPeso() + "\nProprietário : " + animal.getNomeDono());
        }
    }

    public static void readConsultas(ConsultaDAO consDAO) {
        for (Consulta consulta : consDAO.read()) {
            System.out.println("\nID : " + consulta.getId() + "\nData e Hora : " + consulta.getDataHora() + "\nDiagnostico : " + consulta.getDiagnostico() + "\nValor : " + consulta.getValor() + "\nAnimal : " + consulta.getNomeAnimal() + "\nVeterinário : " + consulta.getNomeVeterinario());
        }
    }

    public static void update(VeterinarioDAO vetDAO, ProprietarioDAO propDAO, AnimalDAO animalDAO, Connection conexao) {
        Scanner scan = new Scanner(System.in);
        System.out.println("\nEm qual das entidades deseja atualizar as informações?");
        System.out.println("1 - Veterinário\n2 - Proprietário\n3 - Animal\n4 - Voltar");
        int escolha = scan.nextInt();
        scan.nextLine();
        switch (escolha) {
            case 1:
                updateVeterinario(vetDAO);
                break;
            case 2:
                updateProprietario(propDAO);
                break;
            case 3:
                updateAnimal(animalDAO, conexao);
                break;
            case 4:
                break;
            default:
        }
    }

    public static void updateVeterinario(VeterinarioDAO vetDAO) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Insira o CRMV do veterinário que deseja atualizar : ");
        String crmvAlvo = scan.nextLine();

        if (vetDAO.existe(crmvAlvo)) {

            System.out.print("O que deseja atualizar?\n1 - Nome\n2 - Especialidade\n3 - Telefone\nOpção : ");
            int opcaoCampo = scan.nextInt();
            scan.nextLine();
            boolean sucessoVet = false;

            try {
                switch (opcaoCampo) {
                    case 1:
                        System.out.print("Digite o NOVO Nome : ");
                        String novoNome = scan.nextLine();
                        sucessoVet = vetDAO.updateNome(crmvAlvo, novoNome);
                        break;
                    case 2:
                        System.out.print("Digite a NOVA Especialidade: ");
                        String novaEspec = scan.nextLine();
                        sucessoVet = vetDAO.updateEspecialidade(crmvAlvo, novaEspec);
                        break;
                    case 3:
                        System.out.print("Digite o NOVO Telefone: ");
                        String novoTel = scan.nextLine();
                        sucessoVet = vetDAO.updateTelefone(crmvAlvo, novoTel);
                        break;
                    default:
                        System.out.println("Opção de campo inválida!");
                }
                if (sucessoVet) {
                    System.out.println("Registro atualizado com sucesso!");
                } else if (opcaoCampo >= 1 && opcaoCampo <= 3) {
                    System.out.println("Erro: CRMV não encontrado no banco.");
                }
            } catch (Exception e) {
                System.out.println("Erro ao atualizar: " + e.getMessage());
            }
        } else {
            System.out.println("CRMV não encontrado no banco.");
        }
    }

    public static void updateProprietario(ProprietarioDAO propDAO) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Insira os dados para validação: ");
        System.out.print("CPF do proprietário: ");
        String cpfAlvo = scan.nextLine();
        if (propDAO.existe(cpfAlvo)) {

            System.out.println("O que deseja atualizar do proprietário?");
            System.out.println("[1] - Nome\n[2] - Telefone\n[3] - Endereço\n[4] - Email");
            int updateProprietarioEscolha = scan.nextInt();
            scan.nextLine();

            switch (updateProprietarioEscolha) {
                case 1:
                    System.out.print("Insira um novo nome: ");
                    String novoNome = scan.nextLine();
                    propDAO.updateNome(cpfAlvo, novoNome);
                    break;
                case 2:
                    System.out.print("Novo telefone (DDD e número): ");
                    String novoNumero = scan.nextLine();
                    propDAO.updateTelefone(cpfAlvo, novoNumero);
                    break;
                case 3:
                    System.out.print("Atualize o seu endereço: ");
                    String novoEndereco = scan.nextLine();
                    propDAO.updateEndereco(cpfAlvo, novoEndereco);
                    break;
                case 4:
                    System.out.print("Insira o novo email: ");
                    String novoEmail = scan.nextLine();
                    propDAO.updateEmail(cpfAlvo, novoEmail);
                    break;
                default:
                    System.out.println("Entre com um número válido dentre as opções!");
            }
        }
    }

    public static void updateAnimal(AnimalDAO animalDAO, Connection conexao) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Animais cadastrados");
        String sql = "SELECT * FROM animais";
        try {
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("Insira o ID do animal :");
            while (resultSet.next()) {
                int idAnimal = resultSet.getInt("id");
                String nomeAnimal = resultSet.getString("nome");
                System.out.println(idAnimal + " - " + nomeAnimal);
            }
        } catch (RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        }
        int animalID = scan.nextInt();
        if (animalDAO.existe(animalID)) {
            System.out.println("O que deseja atualizar do animal?");
            System.out.println("1 - Nome\n2 - Espécie\n3 - Raça\n4 - Peso");
            int updateAnimalSwitch = scan.nextInt();
            scan.nextLine();
            switch (updateAnimalSwitch) {
                case 1:
                    System.out.print("Insira um novo nome: ");
                    String novoAnimalNome = scan.nextLine();
                    animalDAO.updateNome(animalID, novoAnimalNome);
                    break;
                case 2:
                    System.out.print("Atualize o nome da espécie: ");
                    String novoAnimalEspecie = scan.nextLine();
                    animalDAO.updateEspecie(animalID, novoAnimalEspecie);
                    break;
                case 3:
                    System.out.print("Atualize o nome da raça: ");
                    String novoAnimalRaca = scan.nextLine();
                    animalDAO.updateRaca(animalID, novoAnimalRaca);
                    break;
                case 4:
                    System.out.println("Insira o novo peso (em kg): ");
                    BigDecimal novoAnimalPeso = scan.nextBigDecimal();
                    animalDAO.updatePeso(animalID, novoAnimalPeso);
                    break;
                default:
                    System.out.println("Insira uma opção válida!");
                    break;
            }
        }
    }

    public static void delete(VeterinarioDAO vetDAO, ProprietarioDAO propDAO, AnimalDAO animalDAO, ConsultaDAO consultaDAO, Connection conexao) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Escolha o qual deseja excluir?");
        System.out.println("1 - Veterinário\n2 - Proprietário\n3 - Animal\n4 - Consulta");
        int escolha = scan.nextInt();

        switch (escolha) {
            case 1:
                System.out.print("Insira o CRMV do veterinário : ");
                String CRMV = scan.nextLine();
                vetDAO.delete(CRMV);
                break;
            case 2:
                System.out.print("Insira o CPF do proprietário : ");
                String CPF = scan.nextLine();
                propDAO.delete(CPF);
                break;
            case 3:
                String sql = "SELECT * FROM animais";
                try {
                    PreparedStatement statement = conexao.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        int idAnimal = resultSet.getInt("id");
                        String nomeAnimal = resultSet.getString("nome");
                        System.out.println("Insira o ID do animal :\n" + idAnimal + " - " + nomeAnimal);
                    }
                } catch (RuntimeException | SQLException e) {
                    throw new RuntimeException(e);
                }
                int idAnimal = scan.nextInt();
                animalDAO.delete(idAnimal);
                break;
            case 4:
                System.out.print("Insira o ID da consulta : ");
                int idConsulta = scan.nextInt();
                consultaDAO.delete(idConsulta);
                break;
            default:
                System.out.println("Opção inválida");

        }
    }
}