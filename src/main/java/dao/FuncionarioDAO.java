package dao;

import dao.Conexao;
import model.EPI;
import model.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FuncionarioDAO {

 // 1. ATUALIZAÇÃO DO MÉTODO SALVAR COMPLETO
public boolean salvarCompleto(Funcionario func, EPI epiEntrada, EPI epiSaida) {
    // Adicionado 'atendente' e mais um '?' no final
    String sqlFunc = "INSERT INTO funcionario (matricula, nome, setor, box, atendente) VALUES (?, ?, ?, ?, ?)";
    
    String sqlEPI = "INSERT INTO EPI (funcionario_matricula, dataEntrada, dataSaida, "
            + "bone, camiseta, calca, jaleco, moleton, macacao, calca_pvc, capa_pvc, sapatenis, botina) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = Conexao.conectar()) {
        con.setAutoCommit(false);

        // 1. SALVAR FUNCIONÁRIO
        try (PreparedStatement stmtFunc = con.prepareStatement(sqlFunc)) {
            stmtFunc.setString(1, func.getMatricula());
            stmtFunc.setString(2, func.getNome());
            stmtFunc.setString(3, func.getSetor());
            stmtFunc.setString(4, func.getBox()); 
            stmtFunc.setString(5, func.getAtendente()); // <-- NOVO: Salva o atendente aqui
            stmtFunc.executeUpdate();
        }

        // 2. SALVAR EPI ENTRADA
        if (epiEntrada != null) {
            try (PreparedStatement stmtEntrada = con.prepareStatement(sqlEPI)) {
                stmtEntrada.setString(1, func.getMatricula());
                stmtEntrada.setObject(2, epiEntrada.getDataEntrada());
                stmtEntrada.setObject(3, null);
                stmtEntrada.setInt(4, epiEntrada.getBone());
                stmtEntrada.setInt(5, epiEntrada.getCamiseta());
                stmtEntrada.setInt(6, epiEntrada.getCalca());
                stmtEntrada.setInt(7, epiEntrada.getJaleco());
                stmtEntrada.setInt(8, epiEntrada.getMoleton());
                stmtEntrada.setInt(9, epiEntrada.getMacacao());
                stmtEntrada.setInt(10, epiEntrada.getCalca_pvc());
                stmtEntrada.setInt(11, epiEntrada.getCapa_pvc());
                stmtEntrada.setInt(12, epiEntrada.getSapatenis());
                stmtEntrada.setInt(13, epiEntrada.getBotina());
                stmtEntrada.executeUpdate();
            }
        }

        // 3. SALVAR EPI SAÍDA
        if (epiSaida != null) {
            try (PreparedStatement stmtSaida = con.prepareStatement(sqlEPI)) {
                stmtSaida.setString(1, func.getMatricula());
                stmtSaida.setObject(2, null);
                stmtSaida.setObject(3, epiSaida.getDataSaida());
                stmtSaida.setInt(4, epiSaida.getBone());
                stmtSaida.setInt(5, epiSaida.getCamiseta());
                stmtSaida.setInt(6, epiSaida.getCalca());
                stmtSaida.setInt(7, epiSaida.getJaleco());
                stmtSaida.setInt(8, epiSaida.getMoleton());
                stmtSaida.setInt(9, epiSaida.getMacacao());
                stmtSaida.setInt(10, epiSaida.getCalca_pvc());
                stmtSaida.setInt(11, epiSaida.getCapa_pvc());
                stmtSaida.setInt(12, epiSaida.getSapatenis());
                stmtSaida.setInt(13, epiSaida.getBotina());
                stmtSaida.executeUpdate();
            }
        }

        con.commit();
        return true;

    } catch (SQLException e) {
        // Código 1062 é o erro do MySQL para registros duplicados
        if (e.getErrorCode() == 1062) {
            JOptionPane.showMessageDialog(null, 
                "Aviso: Não foi possível salvar. Já existe um funcionário cadastrado com esta mesma matrícula, ou com a mesma combinação de Nome, Setor e Box!", 
                "Dados Duplicados", 
                JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Erro ao salvar no banco de dados: " + e.getMessage(), 
                "Erro de Sistema", 
                JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}



    public void carregarApenasFuncionarios(DefaultTableModel modeloTabela) {
        modeloTabela.setRowCount(0);
        String sql = "SELECT * FROM funcionario"; 
        
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("matricula"));
                linha.add(rs.getString("nome"));
                linha.add(rs.getString("setor")); 
                linha.add(rs.getString("box"));
                linha.add(rs.getString("atendente"));
                
                modeloTabela.addRow(linha);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar funcionários: " + e.getMessage());
        }
    }

    public void filtrarFuncionariosPorNome(DefaultTableModel modeloTabela, String nomePesquisa) {
        modeloTabela.setRowCount(0);
        String sql = "SELECT * FROM funcionario WHERE nome LIKE ?";
        
        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nomePesquisa + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vector<Object> linha = new Vector<>();
                    linha.add(rs.getString("matricula"));
                    linha.add(rs.getString("nome"));
                    linha.add(rs.getString("setor")); 
                    linha.add(rs.getString("box"));
                    linha.add(rs.getString("atendente"));
                    
                    modeloTabela.addRow(linha);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao filtrar funcionários: " + e.getMessage());
        }
    }

   public EPI buscarSaldoEpis(int matricula) {
    EPI saldo = new EPI();
    String sql = "SELECT * FROM EPI WHERE funcionario_matricula = ?";
    
    try (Connection con = Conexao.conectar();
         PreparedStatement stmt = con.prepareStatement(sql)) {
        
        stmt.setInt(1, matricula);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Mapeia cada coluna do banco para o objeto EPI
                saldo.setBone(rs.getInt("bone"));
                saldo.setCamiseta(rs.getInt("camiseta"));
                saldo.setCalca(rs.getInt("calca"));
                saldo.setJaleco(rs.getInt("jaleco"));
                saldo.setMoleton(rs.getInt("moleton"));
                saldo.setMacacao(rs.getInt("macacao"));
                saldo.setCalca_pvc(rs.getInt("calca_pvc"));
                saldo.setCapa_pvc(rs.getInt("capa_pvc"));
                saldo.setSapatenis(rs.getInt("sapatenis"));
                saldo.setBotina(rs.getInt("botina"));
            } else {
                // Caso não encontre o registro, zera os campos para evitar erros
                saldo.setBone(0); saldo.setCamiseta(0); saldo.setCalca(0); saldo.setJaleco(0);
                saldo.setMoleton(0); saldo.setMacacao(0); saldo.setCalca_pvc(0);
                saldo.setCapa_pvc(0); saldo.setSapatenis(0); saldo.setBotina(0);
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar EPIs lavados em estoque: " + e.getMessage());
    }
    
    return saldo;
}
  public void excluirFuncionario(String matricula) { // MUDOU PARA STRING AQUI
    String sql = "DELETE FROM funcionario WHERE matricula = ?";
    
    try (java.sql.Connection conn = new Conexao().conectar(); // Use o nome correto do seu método de conexão aqui
         java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, matricula); // MUDOU PARA setString AQUI
        stmt.executeUpdate();
        
        javax.swing.JOptionPane.showMessageDialog(null, "Funcionário excluído com sucesso!");
        
    } catch (java.sql.SQLException ex) {
        javax.swing.JOptionPane.showMessageDialog(null, "Erro ao excluir funcionário: " + ex.getMessage());
    }
}
public void buscarEpisSaida(String matricula, javax.swing.JTable tabelaSaida) {
    String sql = "SELECT * FROM EPI WHERE funcionario_matricula = ?";
    
    // USANDO Conexao.conectar() direto, que fecha automaticamente no try-with-resources
    try (Connection conn = Conexao.conectar(); 
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, matricula);
        try (ResultSet rs = stmt.executeQuery()) {
            
            DefaultTableModel modelo = (DefaultTableModel) tabelaSaida.getModel();
            
            if (rs.next()) {
                modelo.setValueAt(rs.getInt("bone"), 0, 0);
                modelo.setValueAt(rs.getInt("camiseta"), 0, 1);
                modelo.setValueAt(rs.getInt("calca"), 0, 2);
                modelo.setValueAt(rs.getInt("jaleco"), 0, 3);
                modelo.setValueAt(rs.getInt("moleton"), 0, 4);
                modelo.setValueAt(rs.getInt("macacao"), 0, 5);
                modelo.setValueAt(rs.getInt("capa_pvc"), 0, 6); 
                modelo.setValueAt(rs.getInt("calca_pvc"), 0, 7);
                modelo.setValueAt(rs.getInt("sapatenis"), 0, 8);
                modelo.setValueAt(rs.getInt("botina"), 0, 9);
            } else {
                for (int i = 0; i < modelo.getColumnCount(); i++) {
                    modelo.setValueAt(0, 0, i);
                }
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Erro ao buscar EPIs de saída: " + ex.getMessage());
    }
}
public Funcionario buscarPorMatricula(String matricula) {
    String sql = "SELECT * FROM funcionario WHERE matricula = ?";
    Funcionario func = null;
    
    try (Connection con = Conexao.conectar();
         PreparedStatement stmt = con.prepareStatement(sql)) {
        
        stmt.setString(1, matricula);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                func = new Funcionario();
                func.setMatricula(rs.getString("matricula"));
                func.setNome(rs.getString("nome"));
                func.setSetor(rs.getString("setor"));
                func.setBox(rs.getString("box"));
                func.setAtendente(rs.getString("atendente"));
            }
        }
    } catch (SQLException e) {
        System.out.println("Erro ao buscar funcionário por matrícula: " + e.getMessage());
    }
    
    return func; // Retorna o funcionário preenchido ou null se não encontrar
}
public boolean zerarEstoqueEpiSaida(String matricula, String dataSaida) {
    // Dá um UPDATE na tabela definindo todos os EPIs como 0 para a matrícula informada
    String sql = "UPDATE EPI SET dataSaida = ?, bone = 0, camiseta = 0, calca = 0, jaleco = 0, "
               + "moleton = 0, macacao = 0, capa_pvc = 0, calca_pvc = 0, sapatenis = 0, botina = 0 "
               + "WHERE funcionario_matricula = ?";
               
    try (Connection con = Conexao.conectar();
         PreparedStatement stmt = con.prepareStatement(sql)) {
        
        stmt.setString(1, dataSaida);
        stmt.setString(2, matricula);
        
        int linhasAfetadas = stmt.executeUpdate();
        return linhasAfetadas > 0; // Retorna true se encontrou o registro e atualizou
        
    } catch (SQLException e) {
        System.out.println("Erro ao zerar estoque de EPI no banco: " + e.getMessage());
        return false;
    }
}
}