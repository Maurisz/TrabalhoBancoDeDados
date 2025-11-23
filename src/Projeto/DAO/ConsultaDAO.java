package Projeto.DAO;

import Projeto.Entities.Consulta;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {
    private final Connection conexao;
    private static final String sql = "INSERT INTO consultas(data_hora, diagnostico, valor, id_animal, crmv_veterinario) VALUES (?, ?, ?, ?, ?)";
    private static final String sqlGeral =
            "SELECT c.*, a.nome AS nome_animal, v.nome AS nome_vet " +
                    "FROM consultas c " +
                    "INNER JOIN animais a ON c.id_animal = a.id " +
                    "INNER JOIN veterinarios v ON c.crmv_veterinario = v.crmv";

    public ConsultaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void create(Timestamp dataHora, String diagnostico, BigDecimal valor, int idAnimal, String crmvVeterinario) {

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setTimestamp(1, dataHora);
            statement.setString(2, diagnostico);
            statement.setBigDecimal(3, valor);
            statement.setInt(4, idAnimal);
            statement.setString(5, crmvVeterinario);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao agendar consulta. Verifique ID do Entities.Animal e CRMV.", e);
        }
    }

    public List<Consulta> read() {
        List<Consulta> consultas = new ArrayList<>();

        try (PreparedStatement statement = conexao.prepareStatement(sqlGeral)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idBanco = resultSet.getInt("id");
                Timestamp dataHoraBanco = resultSet.getTimestamp("data_hora");
                String diagnosticoBanco = resultSet.getString("diagnostico");
                BigDecimal valorBanco = resultSet.getBigDecimal("valor");
                int idAnimalBanco = resultSet.getInt("id_animal");
                String crmvVeterinarioBanco = resultSet.getString("crmv_veterinario");

                Consulta consulta = new Consulta(dataHoraBanco, idAnimalBanco, crmvVeterinarioBanco, diagnosticoBanco, valorBanco);
                consulta.setId(idBanco);

                consulta.setNomeAnimal(resultSet.getString("nome_animal"));
                consulta.setNomeVeterinario(resultSet.getString("nome_vet"));

                consultas.add(consulta);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return consultas;
    }

    public void delete(int chave) {
        String sql = "DELETE FROM consultas WHERE id=?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setInt(1, chave);
            statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}