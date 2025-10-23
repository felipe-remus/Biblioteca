/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.*;
import java.util.ArrayList;
import model.EmprestimoVO;
import persistencia.ConexaoBanco;
import java.sql.SQLException;

/**
 *
 * @author FELIPEREMUSDEALMEIDA
 */
public class EmprestimoDAO {
    public void cadastrarEmprestimo(EmprestimoVO eVO) throws SQLException {
       
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "INSERT INTO emprestimo (dt_retirada, dt_prazo, status, id_cliente, id_livro) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setDate(1, new java.sql.Date(eVO.getDtRetirada().getTime()));
            pstm.setDate(2, new java.sql.Date(eVO.getDtPrazo().getTime()));
            pstm.setString(3, "Nao Devolvido"); 
            pstm.setInt(4, eVO.getIdCliente());
            pstm.setInt(5, eVO.getIdLivro());
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }

    public ArrayList<EmprestimoVO> buscarEmprestimos() throws SQLException {
        return buscarEmprestimosPorStatus(null);
    }

    public ArrayList<EmprestimoVO> buscarEmprestimosPorStatus(String status) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            StringBuilder sql = new StringBuilder("""
                SELECT 
                    e.id_emprestimo,
                    e.dt_retirada,
                    e.dt_prazo,
                    e.status,
                    e.id_cliente,
                    e.id_livro,
                    c.nome_cliente,
                    l.nome_livro
                FROM emprestimo e
                INNER JOIN cliente c ON e.id_cliente = c.id_cliente
                INNER JOIN livro l ON e.id_livro = l.id_livro
                """);

            if (status != null && !status.equals("Todos")) {
                sql.append(" WHERE e.status = ?");
            }

            PreparedStatement pstm = con.prepareStatement(sql.toString());
            if (status != null && !status.equals("todos")) {
                pstm.setString(1, status);
            }

            ResultSet rs = pstm.executeQuery();
            ArrayList<EmprestimoVO> emprestimos = new ArrayList<>();
            while (rs.next()) {
                EmprestimoVO eVO = new EmprestimoVO();
                eVO.setIdEmprestimo(rs.getInt("id_emprestimo"));
                eVO.setDtRetirada(rs.getDate("dt_retirada"));
                eVO.setDtPrazo(rs.getDate("dt_prazo"));
                eVO.setStatus(rs.getString("status"));
                eVO.setIdCliente(rs.getInt("id_cliente"));
                eVO.setIdLivro(rs.getInt("id_livro"));
                eVO.setNomeCliente(rs.getString("nome_cliente"));
                eVO.setNomeLivro(rs.getString("nome_livro"));
                emprestimos.add(eVO);
            }
            return emprestimos;
        } finally {
            con.close();
        }
    }

    public ArrayList<EmprestimoVO> filtrarEmprestimos(String nomeCliente, String nomeLivro, String status) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            StringBuilder sql = new StringBuilder("""
                SELECT 
                    e.id_emprestimo,
                    e.dt_retirada,
                    e.dt_prazo,
                    e.status,
                    e.id_cliente,
                    e.id_livro,
                    c.nome_cliente,
                    l.nome_livro
                FROM emprestimo e
                INNER JOIN cliente c ON e.id_cliente = c.id_cliente
                INNER JOIN livro l ON e.id_livro = l.id_livro
                WHERE 1=1
                """);

            int paramIndex = 1;
            if (nomeCliente != null && !nomeCliente.isEmpty()) {
                sql.append(" AND c.nome_cliente LIKE ?");
            }
            if (nomeLivro != null && !nomeLivro.isEmpty()) {
                sql.append(" AND l.nome_livro LIKE ?");
            }
            if (status != null && !status.equals("todos")) {
                sql.append(" AND e.status = ?");
            }

            PreparedStatement pstm = con.prepareStatement(sql.toString());
            paramIndex = 1;
            if (nomeCliente != null && !nomeCliente.isEmpty()) {
                pstm.setString(paramIndex++, "%" + nomeCliente + "%");
            }
            if (nomeLivro != null && !nomeLivro.isEmpty()) {
                pstm.setString(paramIndex++, "%" + nomeLivro + "%");
            }
            if (status != null && !status.equals("todos")) {
                pstm.setString(paramIndex++, status);
            }

            ResultSet rs = pstm.executeQuery();
            ArrayList<EmprestimoVO> emprestimos = new ArrayList<>();
            while (rs.next()) {
                EmprestimoVO eVO = new EmprestimoVO();
                eVO.setIdEmprestimo(rs.getInt("id_emprestimo"));
                eVO.setDtRetirada(rs.getDate("dt_retirada"));
                eVO.setDtPrazo(rs.getDate("dt_prazo"));
                eVO.setStatus(rs.getString("status"));
                eVO.setIdCliente(rs.getInt("id_cliente"));
                eVO.setIdLivro(rs.getInt("id_livro"));
                eVO.setNomeCliente(rs.getString("nome_cliente"));
                eVO.setNomeLivro(rs.getString("nome_livro"));
                emprestimos.add(eVO);
            }
            return emprestimos;
        } finally {
            con.close();
        }
    }

    public void devolverLivro(int idEmprestimo) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "UPDATE emprestimo SET status = 'Devolvido' WHERE id_emprestimo = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idEmprestimo);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }

    public void registrarDevolucao(int idEmprestimo, Date dtDevolucao, double multa) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "INSERT INTO devolucao (id_emprestimo, dt_devolucao, multa) VALUES (?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idEmprestimo);
            pstm.setDate(2, new java.sql.Date(dtDevolucao.getTime()));
            pstm.setDouble(3, multa);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    
    public ArrayList<EmprestimoVO> filtrarPorCliente(String nomeCliente, String status) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            StringBuilder sql = new StringBuilder("""
                SELECT 
                    e.id_emprestimo,
                    e.dt_retirada,
                    e.dt_prazo,
                    e.status,
                    e.id_cliente,
                    e.id_livro,
                    c.nome_cliente,
                    l.nome_livro
                FROM emprestimo e
                INNER JOIN cliente c ON e.id_cliente = c.id_cliente
                INNER JOIN livro l ON e.id_livro = l.id_livro
                WHERE c.nome_cliente LIKE ?
                """);

            if (status != null) {
                sql.append(" AND e.status = ?");
            }

            PreparedStatement pstm = con.prepareStatement(sql.toString());
            pstm.setString(1, "%" + nomeCliente + "%");
            if (status != null) {
                pstm.setString(2, status);
            }

            ResultSet rs = pstm.executeQuery();
            ArrayList<EmprestimoVO> emprestimos = new ArrayList<>();
            while (rs.next()) {
                EmprestimoVO eVO = new EmprestimoVO();
                eVO.setIdEmprestimo(rs.getInt("id_emprestimo"));
                eVO.setDtRetirada(rs.getDate("dt_retirada"));
                eVO.setDtPrazo(rs.getDate("dt_prazo"));
                eVO.setStatus(rs.getString("status"));
                eVO.setIdCliente(rs.getInt("id_cliente"));
                eVO.setIdLivro(rs.getInt("id_livro"));
                eVO.setNomeCliente(rs.getString("nome_cliente"));
                eVO.setNomeLivro(rs.getString("nome_livro"));
                emprestimos.add(eVO);
            }
            return emprestimos;
        } finally {
            con.close();
        }
    }

    public ArrayList<EmprestimoVO> filtrarPorLivro(String nomeLivro, String status) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            StringBuilder sql = new StringBuilder("""
                SELECT 
                    e.id_emprestimo,
                    e.dt_retirada,
                    e.dt_prazo,
                    e.status,
                    e.id_cliente,
                    e.id_livro,
                    c.nome_cliente,
                    l.nome_livro
                FROM emprestimo e
                INNER JOIN cliente c ON e.id_cliente = c.id_cliente
                INNER JOIN livro l ON e.id_livro = l.id_livro
                WHERE l.nome_livro LIKE ?
                """);

            if (status != null) {
                sql.append(" AND e.status = ?");
            }

            PreparedStatement pstm = con.prepareStatement(sql.toString());
            pstm.setString(1, "%" + nomeLivro + "%");
            if (status != null) {
                pstm.setString(2, status);
            }

            ResultSet rs = pstm.executeQuery();
            ArrayList<EmprestimoVO> emprestimos = new ArrayList<>();
            while (rs.next()) {
                EmprestimoVO eVO = new EmprestimoVO();
                eVO.setIdEmprestimo(rs.getInt("id_emprestimo"));
                eVO.setDtRetirada(rs.getDate("dt_retirada"));
                eVO.setDtPrazo(rs.getDate("dt_prazo"));
                eVO.setStatus(rs.getString("status"));
                eVO.setIdCliente(rs.getInt("id_cliente"));
                eVO.setIdLivro(rs.getInt("id_livro"));
                eVO.setNomeCliente(rs.getString("nome_cliente"));
                eVO.setNomeLivro(rs.getString("nome_livro"));
                emprestimos.add(eVO);
                emprestimos.add(eVO);
            }
            return emprestimos;
        } finally {
            con.close();
        }
    }
    
    public void deletarEmprestimo(int idEmprestimo) throws SQLException {
    Connection con = new ConexaoBanco().getConexao();
    try {
        String sql = "DELETE FROM emprestimo WHERE id_emprestimo = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, idEmprestimo);
        pstm.executeUpdate();
    } finally {
        con.close();
    }
}
    
    public EmprestimoVO buscarEmprestimoPorId(int idEmprestimo) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = """
                SELECT 
                    e.id_emprestimo,
                    e.dt_retirada,
                    e.dt_prazo,
                    e.status,
                    e.id_cliente,
                    e.id_livro,
                    c.nome_cliente,
                    l.nome_livro
                FROM emprestimo e
                INNER JOIN cliente c ON e.id_cliente = c.id_cliente
                INNER JOIN livro l ON e.id_livro = l.id_livro
                WHERE e.id_emprestimo = ?
                """;
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idEmprestimo);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                EmprestimoVO eVO = new EmprestimoVO();
                eVO.setIdEmprestimo(rs.getInt("id_emprestimo"));
                eVO.setDtRetirada(rs.getDate("dt_retirada"));
                eVO.setDtPrazo(rs.getDate("dt_prazo"));
                eVO.setStatus(rs.getString("status"));
                eVO.setIdCliente(rs.getInt("id_cliente"));
                eVO.setIdLivro(rs.getInt("id_livro"));
                eVO.setNomeCliente(rs.getString("nome_cliente"));
                eVO.setNomeLivro(rs.getString("nome_livro"));
                return eVO;
            }
            throw new SQLException("Empréstimo não encontrado.");
        } finally {
            con.close();
        }
    }
    
    public void atualizarEmprestimo(EmprestimoVO eVO) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "UPDATE emprestimo SET dt_retirada = ?, dt_prazo = ?, id_cliente = ?, id_livro = ? WHERE id_emprestimo = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setDate(1, new java.sql.Date(eVO.getDtRetirada().getTime()));
            pstm.setDate(2, new java.sql.Date(eVO.getDtPrazo().getTime()));
            pstm.setInt(3, eVO.getIdCliente());
            pstm.setInt(4, eVO.getIdLivro());
            pstm.setInt(5, eVO.getIdEmprestimo());
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
}