package Projeto.DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ConsultaDAO {
    private Connection conexao;

    public ConsultaDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Timestamp dataHora, String diagnostico, BigDecimal valor, int idAnimal, String crmvVeterinario) {
        String sql = "INSERT INTO consultas(data_hora, diagnostico, valor, id_animal, crmv_veterinario) VALUES (?, ?, ?, ?, ?)";

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
}