package clinicaveterinariadb.DAO;

import clinicaveterinariadb.Entities.Proprietario;

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

                Proprietario p = new Proprietario(cpf, nome, telefone, endereco, email);
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

    public boolean updateNome(String cpf, String novoNome) {
        return executarUpdate("UPDATE proprietarios SET nome = ? WHERE cpf = ?", novoNome, cpf);
    }

    public boolean updateTelefone(String cpf, String novoTelefone) {
        return executarUpdate("UPDATE proprietarios SET telefone = ? WHERE cpf = ?", novoTelefone, cpf);
    }

    public boolean updateEndereco(String cpf, String novoEndereco) {
        return executarUpdate("UPDATE proprietarios SET endereco = ? WHERE cpf = ?", novoEndereco, cpf);
    }

    public boolean updateEmail(String cpf, String novoEmail) {
        return executarUpdate("UPDATE proprietarios SET email = ? WHERE cpf = ?", novoEmail, cpf);
    }

    private boolean executarUpdate(String sql, String novoValor, String chave) {
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, novoValor);
            stmt.setString(2, chave);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String cpfProprietario) {
        String sqlConsultas = "DELETE FROM consultas WHERE id_animal IN (SELECT id FROM animais WHERE cpf_proprietario = ?)";
        String sqlAnimais = "DELETE FROM animais WHERE cpf_proprietario = ?";
        String sqlProprietario = "DELETE FROM proprietarios WHERE cpf = ?";

        try {
            conexao.setAutoCommit(false);
            try (PreparedStatement stmt = conexao.prepareStatement(sqlConsultas)) {
                stmt.setString(1, cpfProprietario);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sqlAnimais)) {
                stmt.setString(1, cpfProprietario);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conexao.prepareStatement(sqlProprietario)) {
                stmt.setString(1, cpfProprietario);
                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas == 0) {
                    throw new SQLException("Proprietário não encontrado para exclusão.");
                }
            }
            conexao.commit();
            System.out.println("Proprietário, seus animais e consultas foram deletados!");

        } catch (SQLException e) {
            try {
                conexao.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Erro ao deletar proprietário (Cascata): " + e.getMessage(), e);
        } finally {
            try {
                conexao.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}