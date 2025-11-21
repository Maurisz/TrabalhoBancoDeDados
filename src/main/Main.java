package main;

import controller.BancoConexao;
import controller.BancoController;
import model.entities.Animal;
import model.entities.Consulta;
import model.entities.Proprietario;
import model.entities.Veterinario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conexao = BancoConexao.getConnection()) {
            BancoController bc = new BancoController(conexao);
            Scanner scan = new Scanner(System.in);
            Veterinario vets = null;
            Proprietario props = null;
            Animal animais = null;
            Consulta cons = null;

            int escolha = 0;

            while (escolha != 5) {
                menuPrincipal();
                escolha = scan.nextInt();
                switch (escolha) {
                    case 1:
                        System.out.println("O que deseja inserir?\n1 - Veterinario\n2 - Proprietario\n3 - Animal\n4 - Consulta");
                        int escolha2 = scan.nextInt();
                        scan.nextLine();
                        switch (escolha2) {
                            case 1:
                                System.out.print("Insira os dados do veterinário\nNome : ");
                                String vetNome = scan.nextLine();
                                System.out.print("CRMV : ");
                                String crmv = scan.nextLine();
                                System.out.print("Especialidade : ");
                                String especialidade = scan.nextLine();
                                System.out.print("Telefone : ");
                                String vetTelefone = scan.nextLine();
                                System.out.println();
                                bc.inserirVeterinario(new Veterinario(vetNome, crmv, especialidade, vetTelefone));
                                break;
                            case 2:
                                System.out.print("Insira os dados do proprietário\nNome : ");
                                vetNome = scan.nextLine();

                                System.out.print("CPF : ");
                                String propCpf = scan.nextLine();

                                System.out.print("Telefone : ");
                                vetTelefone = scan.nextLine();

                                System.out.print("Endereço : ");
                                String endereco = scan.nextLine();

                                System.out.print("Email : ");
                                String email = scan.nextLine();

                                System.out.println();
                                try {
                                    bc.inserirProprietario(new Proprietario(propCpf, vetNome, vetTelefone, endereco, email));
                                    System.out.println("Proprietario inserido com sucesso!");
                                } catch (Exception e) {
                                    System.out.println("Erro: " + e.getMessage());
                                }
                                break;
                            case 3:
                                System.out.print("Insira os dados do animal\nNome : ");
                                vetNome = scan.nextLine();

                                System.out.print("Especie : ");
                                String especie = scan.nextLine();

                                System.out.print("Raça : ");
                                String raca = scan.nextLine();

                                System.out.print("Data de nascimento (DD/MM/AAAA) : ");
                                String dataTexto = scan.nextLine();

                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                LocalDate dataLocal = LocalDate.parse(dataTexto, formatter);
                                Date dataNascimento = Date.valueOf(dataLocal);

                                System.out.print("Peso : ");
                                double peso = scan.nextDouble();
                                scan.nextLine();

                                System.out.print("CPF do proprietario : ");
                                propCpf = scan.nextLine();

                                System.out.println();
                                Animal novoAnimal = new Animal(vetNome, especie, raca, dataNascimento, peso);
                                try {
                                    bc.inserirAnimal(novoAnimal, propCpf);
                                    System.out.println("Animal inserido com sucesso! ID : " + novoAnimal.getId());
                                } catch (Exception e) {
                                    System.out.println("Erro: Não foi possível cadastrar. O CPF informado existe?");
                                }
                                break;
                        }
                        break;
                    case 3:
                        System.out.println("Em qual das entidades deseja atualizar as informações?");
                        System.out.println("1 - Veterinário\n2 - Proprietário\n3 - Animal\n4 - Sair do menu");
                        int escolhaCase3 = scan.nextInt();
                        switch (escolhaCase3) {
                            case 1:
                                if (vets == null) {
                                    throw new NullPointerException("Não há veterinário(s) cadastrados!");
                                } else {
                                    System.out.print("Insira o CRMV do veterinário: ");
                                    String crmVeterinario = scan.nextLine();
                                    if (crmVeterinario.equals(vets.getCRMV())) {
                                        System.out.println("O que deseja atualizar do veterinário?");
                                        System.out.println("[1] Nome\n[2] CRMV\n[3] Especialidade\n[4] Telefone");
                                        int subEscolhaCase3 = scan.nextInt();
                                        switch (subEscolhaCase3) {
                                            case 1:
                                                System.out.print("Insira um novo nome: ");
                                                String novoVetNome = scan.nextLine();
                                                vets.setNome(novoVetNome);
                                                System.out.println("Nome atualizado com sucesso!");
                                                break;
                                            case 2:
                                                System.out.print("Insira um novo CRMV: ");
                                                String novoVetCRMV = scan.nextLine();
                                                vets.setCRMV(novoVetCRMV);
                                                System.out.println("CRMV atualizado com sucesso!");
                                                break;
                                            case 3:
                                                System.out.print("Atualize a sua especialidade: ");
                                                String novoVetEspecialidade = scan.nextLine();
                                                vets.setEspecialidade(novoVetEspecialidade);
                                                System.out.println("Especialiade atualizada com sucesso!");
                                                break;
                                            case 4:
                                                System.out.print("Novo telefone (DDD e número): ");
                                                String novoVetNumero = scan.nextLine();
                                                vets.setTelefone(novoVetNumero);
                                                System.out.println("Telefone atualizado com sucesso!");
                                                break;
                                            default:
                                                System.out.println("Entre com um número válido dentre as opções!");
                                                menuUpdate();
                                        }
                                    }
                                }
                            case 2:
                                if (props == null) {
                                    throw new NullPointerException("Não há proprietários registrados!");
                                } else {
                                    System.out.println("Insira os dados para validação: ");
                                    System.out.print("CPF do proprietário: ");
                                    String propCPF = scan.nextLine();
                                    if (propCPF.equals(props.getCpf())) {
                                        System.out.println("O que deseja atualizar do proprietário?");
                                        System.out.println("[1] Nome\n[2] Telefone\n[3] Endereço\n[4] Email");
                                        int updateProprietarioEscolha = scan.nextInt();
                                        switch (updateProprietarioEscolha) {
                                            case 1:
                                                System.out.print("Insira um novo nome: ");
                                                String novoPropNome = scan.nextLine();
                                                props.setNome(novoPropNome);
                                                System.out.println("Nome atualizado com sucesso!");
                                                break;
                                            case 2:
                                                System.out.print("Novo telefone (DDD e número): ");
                                                String novoPropNumero = scan.nextLine();
                                                props.setTelefone(novoPropNumero);
                                                System.out.println("Telefone atualizado com sucesso!");
                                                break;
                                            case 3:
                                                System.out.print("Atualize o seu endereço: ");
                                                String novoPropEndereco = scan.nextLine();
                                                props.setEndereco(novoPropEndereco);
                                                System.out.println("Endereço atualizado com sucesso!");
                                                break;
                                            case 4:
                                                System.out.print("Insira o novo email: ");
                                                String novoPropEmail = scan.nextLine();
                                                props.setEmail(novoPropEmail);
                                                System.out.println("Email atualizado com sucesso!");
                                                break;
                                            default:
                                                System.out.println("Entre com um número válido dentre as opções!");
                                                menuUpdate();
                                        }
                                    }
                                }
                            case 3:
                                if (animais == null) {
                                    throw new NullPointerException("Não há animais registrados!");
                                } else {
                                    System.out.print("Insira o ID do animal: ");
                                    int animalID = scan.nextInt();
                                    if (animalID == animais.getId()) {
                                        System.out.println("O que deseja atualizar do animal?");
                                        System.out.println("[1] Nome\n[2] Espécie\n[3] Raça\n[4] Data de nascimento\n[5] Peso");
                                        int updateAnimalSwitch = scan.nextInt();
                                        switch (updateAnimalSwitch) {
                                            case 1:
                                                System.out.print("Insira um novo nome: ");
                                                String novoAnimalNome = scan.nextLine();
                                                animais.setNome(novoAnimalNome);
                                                System.out.println("Nome atualizado com sucesso!");
                                                break;
                                            case 2:
                                                System.out.print("Atualize o nome da espécie: ");
                                                String novoAnimalEspecie = scan.nextLine();
                                                animais.setEspecie(novoAnimalEspecie);
                                                System.out.println("Nome de espécie atualizado com sucesso!");
                                                break;
                                            case 3:
                                                System.out.print("Atualize o nome da raça: ");
                                                String novoAnimalRaca = scan.nextLine();
                                                animais.setRaca(novoAnimalRaca);
                                                System.out.println("Nome de raça atualizado com sucesso!");
                                                break;
                                            case 4:
                                                System.out.print("Atualize a data de nascimento: ");
                                                Date animalNascData = Date.valueOf(scan.nextLine());
                                                animais.setDataNascimento(animalNascData);
                                                System.out.println("Data de nascimento do animal atualizado com sucesso!");
                                                break;
                                            case 5:
                                                System.out.println("Insira o novo peso (em kg): ");
                                                double novoAnimalPeso = scan.nextDouble();
                                                animais.setPeso(novoAnimalPeso);
                                                System.out.println("Peso do animal atualizado com sucesso!");
                                                break;
                                            default:
                                                System.out.println("Insira uma opção válida!");
                                                break;
                                        }
                                    }
                                }
                        }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void menuPrincipal() {
        System.out.println("Escolha o que deseja realizar : \n1 - Create\n2 - Read\n3 - Update\n4 - Delete\n5 - Sair do programa");
    }

    public static void menuUpdate() {
        System.out.println("Em qual das entidades deseja atualizar as informações?");
        System.out.println("1 - Veterinário\n2 - Proprietário\n3 - model.entities.Animal\n 4 - Sair do menu");
    }
}