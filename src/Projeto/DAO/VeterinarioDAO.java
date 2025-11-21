package Projeto.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Projeto.Entities.Veterinario;

public class VeterinarioDAO {
    private Connection conexao;

    public VeterinarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Veterinario veterinario) {
        String sql = "INSERT INTO veterinarios(nome, crmv, especialidade, telefone) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, veterinario.getNome());
            statement.setString(2, veterinario.getCRMV());
            statement.setString(3, veterinario.getEspecialidade());
            statement.setString(4, veterinario.getTelefone());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir veterinário: " + e.getMessage(), e);
        }
    }
}