package Projeto.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Projeto.Entities.Veterinario;

public class VeterinarioDAO {
    private final Connection conexao;

    public VeterinarioDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void create(Veterinario vet) {
        String sql = "INSERT INTO veterinarios(nome, crmv, especialidade, telefone) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, vet.getNome());
            statement.setString(2, vet.getCRMV());
            statement.setString(3, vet.getEspecialidade());
            statement.setString(4, vet.getTelefone());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao create veterinário: " + e.getMessage(), e);
        }
    }

    public boolean updateNome(String crmv, String novoNome) {
        return executarUpdate("UPDATE veterinarios SET nome=? WHERE crmv=?", novoNome, crmv);
    }

    public boolean updateEspecialidade(String crmv, String novaEspec) {
        return executarUpdate("UPDATE veterinarios SET especialidade=? WHERE crmv=?", novaEspec, crmv);
    }

    public boolean updateTelefone(String crmv, String novoTel) {
        return executarUpdate("UPDATE veterinarios SET telefone=? WHERE crmv=?", novoTel, crmv);
    }

    public boolean executarUpdate(String sqlUpdate, String novoValor, String chave) {
        try (PreparedStatement stmt = conexao.prepareStatement(sqlUpdate)) {
            stmt.setString(1, novoValor);
            stmt.setString(2, chave);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Veterinario> read() {
        List<Veterinario> veterinarios = new ArrayList<>();
        String sql = "SELECT * FROM veterinarios";

        try {
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String nomeBanco = resultSet.getString("nome");
                String crmvBanco = resultSet.getString("crmv");
                String especialidadeBanco = resultSet.getString("especialidade");
                String telefoneBanco = resultSet.getString("telefone");

                Veterinario vet = new Veterinario(nomeBanco, crmvBanco, especialidadeBanco, telefoneBanco);

                veterinarios.add(vet);

            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return veterinarios;
    }

    public boolean existe(String crmv) {
        String sql = "SELECT 1 FROM veterinarios WHERE crmv = ?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            statement.setString(1, crmv);
            try (ResultSet rs = statement.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar existência: " + e.getMessage(), e);
        }
    }

    public void delete(String chave) {
        String sql = "DELETE FROM veterinarios WHERE crmv=?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)){
            statement.setString(1, chave);
            statement.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}