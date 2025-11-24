package clinicaveterinariadb.DAO;

import clinicaveterinariadb.Entities.Animal;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {
    private static Connection conexao;

    public AnimalDAO(Connection conexao) {
        AnimalDAO.conexao = conexao;
    }

    public void create(Animal animal, String cpfProprietario) {
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
            throw new RuntimeException("Erro ao create animal. Verifique se o CPF do dono existe.", e);
        }
    }

    public boolean existe(int id) {
        String sql = "SELECT 1 FROM animais WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateNome(int id, String novoNome) {
        return exectuarUpdate("UPDATE animais SET nome = ? WHERE id = ?", id, novoNome);

    }

    public boolean updatePeso(int id, BigDecimal novoPeso) {
        String sql = "UPDATE animais SET peso = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setBigDecimal(1, novoPeso);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateEspecie(int id, String novaEspecie) {
        return exectuarUpdate("UPDATE animais SET especie = ? WHERE id = ?", id, novaEspecie);

    }

    public boolean updateRaca(int id, String novaRaca) {
        return exectuarUpdate("UPDATE animais SET raca = ? WHERE id = ?", id, novaRaca);

    }

    public boolean exectuarUpdate(String sql, int chave, String novoValor) {
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, novoValor);
            stmt.setInt(2, chave);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Animal> read() {
        List<Animal> animais = new ArrayList<>();
        String sql = "SELECT * FROM animais";

        try (PreparedStatement statement = conexao.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idBanco = resultSet.getInt("id");
                String nomeBanco = resultSet.getString("nome");
                String especieBanco = resultSet.getString("especie");
                String racaBanco = resultSet.getString("raca");
                Date dataBanco = resultSet.getDate("data_nascimento");
                BigDecimal pesoBanco = resultSet.getBigDecimal("peso");
                String cpfDonoBanco = resultSet.getString("cpf_proprietario");

                Animal lerAnimal = new Animal(nomeBanco, especieBanco, racaBanco, dataBanco, pesoBanco);

                try (PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM proprietarios WHERE cpf = ?")) {
                    stmt.setString(1, cpfDonoBanco);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        lerAnimal.setNomeDono(rs.getString("nome"));
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                lerAnimal.setId(idBanco);

                animais.add(lerAnimal);

            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return animais;
    }

    public void delete(int idAnimal) {
        String sqlConsultas = "DELETE FROM consultas WHERE id_animal = ?";
        String sqlAnimal = "DELETE FROM animais WHERE id = ?";

        try {
            conexao.setAutoCommit(false);
            try (PreparedStatement stmt = conexao.prepareStatement(sqlConsultas)) {
                stmt.setInt(1, idAnimal);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conexao.prepareStatement(sqlAnimal)) {
                stmt.setInt(1, idAnimal);
                stmt.executeUpdate();
            }
            conexao.commit();
            System.out.println("Animal e suas consultas deletados com sucesso!");

        } catch (SQLException e) {
            try {
                conexao.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Erro ao deletar animal e suas consultas: " + e.getMessage(), e);
        } finally {
            try {
                conexao.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}