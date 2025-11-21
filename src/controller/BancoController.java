import java.sql.*;

public class BancoController {
    private Connection conexao;

    public BancoController(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserirVeterinario(Veterinario veterinario) {
        String sql = "INSERT INTO veterinarios(nome, crmv, especialidade, telefone) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, veterinario.getNome());
            statement.setString(2, veterinario.getCRMV());
            statement.setString(3, veterinario.getEspecialidade());
            statement.setString(4, veterinario.getTelefone());
            statement.executeUpdate();
            System.out.println("Veterinário inserido com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void inserirProprietario(Proprietario proprietario) {
        String sql = "INSERT INTO proprietarios(nome, cpf, telefone, endereco, email) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, proprietario.getNome());
            statement.setString(2, proprietario.getCpf());
            statement.setString(3, proprietario.getTelefone());
            statement.setString(4, proprietario.getEndereco());
            statement.setString(5, proprietario.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void inserirAnimal(Animal animal, String cpfDono) {
        String sql = "INSERT INTO animais(nome, especie, raca, data_nascimento, peso, cpf_proprietario) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, animal.getNome());
            statement.setString(2, animal.getEspecie());
            statement.setString(3, animal.getRaca());
            statement.setDate(4, animal.getDataNascimento());
            statement.setDouble(5, animal.getPeso());
            statement.setString(6, cpfDono);
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    animal.setId(resultSet.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void inserirConsulta(Consulta consulta) {
        String sql = "INSERT INTO consultas(data_hora, diagnostico, valor, id_animal, crmv_veterinario) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setTimestamp(1, new Timestamp(consulta.getDataHora().getTime()));
            statement.setString(2, consulta.getDiagnostico());
            statement.setBigDecimal(3, consulta.getValor());
            statement.setInt(4, consulta.getAnimal().getId());
            statement.setString(5, consulta.getVeterinario().getCRMV());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
