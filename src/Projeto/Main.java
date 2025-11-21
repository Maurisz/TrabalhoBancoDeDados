package Projeto;

import Projeto.DAO.AnimalDAO;
import Projeto.DAO.ConsultaDAO;
import Projeto.DAO.ProprietarioDAO;
import Projeto.DAO.VeterinarioDAO;
import Projeto.Entities.Animal;
import Projeto.Entities.Proprietario;
import Projeto.Entities.Veterinario;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection conexao = BancoConexao.getConnection()) {

            VeterinarioDAO vetDAO = new VeterinarioDAO(conexao);
            ProprietarioDAO propDAO = new ProprietarioDAO(conexao);
            AnimalDAO animalDAO = new AnimalDAO(conexao);
            ConsultaDAO consultaDAO = new ConsultaDAO(conexao);

            Scanner scan = new Scanner(System.in);

            int escolha = 0;
            int escolha2 = 0;

            while (escolha != 5) {

                System.out.println("Escolha o que deseja realizar : \n1 - Create\n2 - Read\n3 - Update\n4 - Delete\n5 - Sair do programa");
                escolha = scan.nextInt();

                switch (escolha) {
                    case 1:
                        System.out.println("O que deseja inserir?\n1 - Veterinario\n2 - Proprietario\n3 - Animal\n4 - Consulta");
                        escolha2 = scan.nextInt();
                        scan.nextLine();

                        switch (escolha2) {
                            case 1:
                                criarVeterinario(vetDAO);
                                break;
                            case 2:
                                criarProprietario(propDAO);
                                break;
                            case 3:
                                criarAnimal(animalDAO);
                                break;
                            case 4:
                                criarConsulta(consultaDAO);
                                break;
                            default:
                                System.out.println("Opção inválida");
                        }
                        break;
                    case 2:
                        System.out.println();


                    case 5:
                        System.out.println("Saindo do programa...");
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void criarVeterinario(VeterinarioDAO vetDAO) {
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
            vetDAO.inserir(new Veterinario(nome, crmv, especialidade, telefone));
            System.out.println("Veterinário inserido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao inserir veterinário: " + e.getMessage());
        }
    }
    public static void criarProprietario(ProprietarioDAO propDAO) {
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
            propDAO.inserir(new Proprietario(cpf, nome, telefone, endereco, email));
            System.out.println("Proprietário inserido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao inserir proprietário: " + e.getMessage());
        }
    }
    public static void criarAnimal(AnimalDAO animalDAO) {
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
            animalDAO.inserir(novoAnimal, cpf);
            System.out.println("Entities.Animal inserido com sucesso! ID : " + novoAnimal.getId());
        } catch (Exception e) {
            System.out.println("Erro: Não foi possível cadastrar. Verifique o CPF.");
        }
    }
    public static void criarConsulta(ConsultaDAO consultaDAO) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Insira os dados da consulta\nData e Hora (dd/MM/yyyy HH:mm) : ");
        String dataTexto = scan.nextLine();

        if(dataTexto.isEmpty()) {
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
            consultaDAO.inserir(dataParaBanco, diagnostico, valor, idAnimal, idCrmv);
            System.out.println("Entities.Consulta agendada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao inserir consulta: " + e.getMessage());
        }
    }

}