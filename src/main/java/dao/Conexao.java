package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexao {
    
    // Altere 'nome_do_seu_banco' para o nome exato do banco de dados que você criou no MySQL
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/Lavanderia";
    private static final String USER = "root"; // Usuário padrão do MySQL
    private static final String PASSWORD = ""; // Sem senha, como você especificou

    public static Connection conectar() {
        Connection conexao = null;
        try {
            // Carrega o driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Estabelece a conexão
            conexao = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver do MySQL não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conexao;
    }

    Connection getConexao() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}