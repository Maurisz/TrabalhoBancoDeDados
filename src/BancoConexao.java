import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BancoConexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/PetCare";
    private static final String USER = "postgres";
    private static final String PWD = "postgres12022007";

    public static Connection getConnection() throws SQLException {
        try {
            DriverManager.getConnection(URL, USER, PWD);
            System.out.println("Conexão efetuada com sucesso!");
            return DriverManager.getConnection(URL, USER, PWD);
        } catch (SQLException e) {
            System.err.println("Problema na conexao com o banco de dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
