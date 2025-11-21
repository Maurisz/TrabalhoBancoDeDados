package Projeto.DAO;

import Projeto.Entities.Animal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AnimalDAO {
    private Connection conexao;

    public AnimalDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Animal animal, String cpfProprietario) {
        String sql = "INSERT INTO animais(nome, especie, raca, data_nascimento, peso, cpf_proprietario) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, animal.getNome());
            statement.setString(2, animal.getEspecie());
            statement.setString(3, animal.getRaca());
            statement.setDate(4, animal.getDataNascimento());
            statement.setBigDecimal(5, animal.getPeso());
            statement.setString(6, cpfProprietario);

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    animal.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir animal. Verifique se o CPF do dono existe.", e);
        }
    }
}