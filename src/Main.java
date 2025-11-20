import javax.swing.text.DateFormatter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conexao = BancoConexao.getConnection()) {
            BancoController bc = new BancoController(conexao);
            Scanner scan = new Scanner(System.in);
            Veterinario vets;
            Proprietario props;
            Animal animais;
            Consulta cons;

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
                                System.out.print("Insira os dados do veterinário\nNome : ");
                                String nome = scan.nextLine();
                                System.out.print("CRMV : ");
                                String crmv = scan.nextLine();
                                System.out.print("Especialidade : ");
                                String especialidade = scan.nextLine();
                                System.out.print("Telefone : ");
                                String telefone = scan.nextLine();
                                System.out.println();
                                bc.inserirVeterinario(new Veterinario(nome, crmv, especialidade, telefone));
                                break;
                            case 2:
                                System.out.print("Insira os dados do proprietário\nNome : ");
                                nome = scan.nextLine();

                                System.out.print("CPF : ");
                                String cpf = scan.nextLine();

                                System.out.print("Telefone : ");
                                telefone = scan.nextLine();

                                System.out.print("Endereço : ");
                                String endereco = scan.nextLine();

                                System.out.print("Email : ");
                                String email = scan.nextLine();

                                System.out.println();
                                try {
                                    bc.inserirProprietario(new Proprietario(cpf, nome, telefone, endereco, email));
                                    System.out.println("Proprietario inserido com sucesso!");
                                } catch (Exception e) {

                                }
                                break;
                            case 3:
                                System.out.print("Insira os dados do animal\nNome : ");
                                nome = scan.nextLine();

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
                                cpf = scan.nextLine();

                                System.out.println();
                                Animal novoAnimal = new  Animal(nome, especie, raca, dataNascimento, peso);
                                try {
                                    bc.inserirAnimal(novoAnimal, cpf);
                                    System.out.println("Animal inserido com sucesso! ID : "+novoAnimal.getId());
                                } catch (Exception e) {
                                    System.out.println("Erro: Não foi possível cadastrar. O CPF informado existe?");
                                }
                                break;
                        }
                        break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
