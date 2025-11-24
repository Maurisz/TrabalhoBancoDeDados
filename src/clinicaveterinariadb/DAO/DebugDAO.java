package clinicaveterinariadb.DAO;

import clinicaveterinariadb.Entities.*;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDateTime;

public class DebugDAO {
    private Connection conexao;

    public DebugDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void limparTudo() {
        String sql = "TRUNCATE TABLE consultas, animais, veterinarios, proprietarios RESTART IDENTITY CASCADE";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.executeUpdate();
            System.out.println("Banco de dados limpo e IDs resetados!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao resetar banco: " + e.getMessage(), e);
        }
    }

    public void popularBanco(VeterinarioDAO vDao, ProprietarioDAO pDao, AnimalDAO aDao, ConsultaDAO cDao) {
        try {
            System.out.println("Iniciando população de dados...");

            Veterinario v1 = new Veterinario("Dr. House", "01", "Diagnostico", "119999-0001");
            Veterinario v2 = new Veterinario("Dra. Dolittle", "02", "Cirurgia", "119999-0002");

            vDao.create(v1);
            vDao.create(v2);
            System.out.println("• Veterinários inseridos.");

            Proprietario p1 = new Proprietario("1", "João da Silva", "719999-1111", "Rua A", "joao@email.com");
            Proprietario p2 = new Proprietario("2", "Maria Souza", "719999-2222", "Rua B", "maria@email.com");

            pDao.create(p1);
            pDao.create(p2);
            System.out.println("• Proprietários inseridos.");

            Animal a1 = new Animal("Rex", "Cachorro", "Pastor Alemao", Date.valueOf("2020-05-10"), new BigDecimal("25.5"));
            aDao.create(a1, "1");

            Animal a2 = new Animal("Mimi", "Gato", "Siames", Date.valueOf("2021-08-15"), new BigDecimal("4.2"));
            aDao.create(a2, "1");

            Animal a3 = new Animal("Thor", "Cachorro", "Pug", Date.valueOf("2022-01-20"), new BigDecimal("8.0"));
            aDao.create(a3, "2");

            System.out.println("• Animais inseridos.");

            Timestamp agora = Timestamp.valueOf(LocalDateTime.now());

            cDao.create(agora, "Dor de ouvido", new BigDecimal("150.00"), 1, "01");

            cDao.create(agora, "Vacinacao", new BigDecimal("80.00"), 3, "02");

            System.out.println("• Consultas agendadas.");
            System.out.println("DEBUG: Dados carregados com sucesso!");

        } catch (Exception e) {
            System.out.println("❌ Erro ao popular banco: " + e.getMessage());
            e.printStackTrace();
        }
    }
}