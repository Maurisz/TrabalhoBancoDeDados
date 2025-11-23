package Projeto.DAO;

import Projeto.Entities.Proprietario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDAO {
    private Connection conexao;

    public ProprietarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void create(Proprietario proprietario) {
        String sql = "INSERT INTO proprietarios(nome, cpf, telefone, endereco, email) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, proprietario.getNome());
            statement.setString(2, proprietario.getCpf());
            statement.setString(3, proprietario.getTelefone());
            statement.setString(4, proprietario.getEndereco());
            statement.setString(5, proprietario.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao create proprietário: " + e.getMessage(), e);
        }
    }

    public List<Proprietario> read() {
        String sql = "SELECT * FROM proprietarios";
        List<Proprietario> proprietarios = new ArrayList<>();
        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String cpf = resultSet.getString("cpf");
                String telefone = resultSet.getString("telefone");
                String endereco = resultSet.getString("endereco");
                String email = resultSet.getString("email");

                Proprietario p = new Proprietario(nome, cpf, telefone, endereco, email);
                proprietarios.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proprietarios;
    }

    public boolean existe(String cpf) {
        String sql = "SELECT 1 FROM proprietarios WHERE cpf = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateNome(String cpf, String novoNome) {
        executarUpdate("UPDATE proprietarios SET nome = ? WHERE cpf = ?", novoNome, cpf);
    }

    public void updateTelefone(String cpf, String novoTelefone) {
        executarUpdate("UPDATE proprietarios SET telefone = ? WHERE cpf = ?", novoTelefone, cpf);
    }

    public void updateEndereco(String cpf, String novoEndereco) {
        executarUpdate("UPDATE proprietarios SET endereco = ? WHERE cpf = ?", novoEndereco, cpf);
    }

    public void updateEmail(String cpf, String novoEmail) {
        executarUpdate("UPDATE proprietarios SET email = ? WHERE cpf = ?", novoEmail, cpf);
    }

    private void executarUpdate(String sql, String novoValor, String chave) {
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, novoValor);
            stmt.setString(2, chave);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String chave) {
        String sql = "DELETE FROM proprietarios WHERE cpf=?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)){
            statement.setString(1, chave);
            statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}