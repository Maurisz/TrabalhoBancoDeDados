package Projeto.DAO;

import Projeto.Entities.Animal;

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

    public void updateNome(int id, String novoNome) {
        exectuarUpdate("UPDATE animais SET nome = ? WHERE id = ?", id, novoNome);

    }

    public void updatePeso(int id, BigDecimal novoPeso) {
        String sql = "UPDATE animais SET peso = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setBigDecimal(1, novoPeso);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEspecie(int id, String novaEspecie) {
        exectuarUpdate("UPDATE animais SET especie = ? WHERE id = ?", id, novaEspecie);

    }

    public void updateRaca(int id, String novaRaca) {
        exectuarUpdate("UPDATE animais SET raca = ? WHERE id = ?", id, novaRaca);

    }

    public void exectuarUpdate(String sql, int chave, String novoValor) {
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, novoValor);
            stmt.setInt(2, chave);
            stmt.executeUpdate();
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

    public void delete(int chave) {
        String sql = "DELETE FROM animais WHERE id=?";

        try (PreparedStatement statement = conexao.prepareStatement(sql)){
            statement.setInt(1, chave);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}