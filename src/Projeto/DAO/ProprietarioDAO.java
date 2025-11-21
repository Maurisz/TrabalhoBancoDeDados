package Projeto.DAO;

import Projeto.Entities.Proprietario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProprietarioDAO {
    private Connection conexao;

    public ProprietarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void inserir(Proprietario proprietario) {
        String sql = "INSERT INTO proprietarios(nome, cpf, telefone, endereco, email) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, proprietario.getNome());
            statement.setString(2, proprietario.getCpf());
            statement.setString(3, proprietario.getTelefone());
            statement.setString(4, proprietario.getEndereco());
            statement.setString(5, proprietario.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir proprietário: " + e.getMessage(), e);
        }
    }
}