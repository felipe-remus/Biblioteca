/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import model.DevolucaoVO;
import persistencia.ConexaoBanco;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author Felipe Remus
 * 
 */
public class DevolucaoDAO {
    public ArrayList<DevolucaoVO> buscarTodasDevolucoes() throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = """
                SELECT 
                    d.id_devolucao,
                    d.id_emprestimo,
                    d.dt_devolucao,
                    d.multa,
                    c.nome_cliente,
                    l.nome_livro
                FROM devolucao d
                INNER JOIN emprestimo e ON d.id_emprestimo = e.id_emprestimo
                INNER JOIN cliente c ON e.id_cliente = c.id_cliente
                INNER JOIN livro l ON e.id_livro = l.id_livro
                ORDER BY d.dt_devolucao DESC
                """;

            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            ArrayList<DevolucaoVO> devolucoes = new ArrayList<>();
            while (rs.next()) {
                DevolucaoVO dVO = new DevolucaoVO();
                dVO.setId_devolucao(rs.getInt("id_devolucao"));
                dVO.setId_emprestimo(rs.getInt("id_emprestimo"));
                
                java.sql.Date sqlDate = rs.getDate("dt_devolucao");
                dVO.setDt_devolucao(sqlDate != null ? new java.util.Date(sqlDate.getTime()) : null);
                
                dVO.setMulta(rs.getDouble("multa"));
                dVO.setNome_cliente(rs.getString("nome_cliente"));
                dVO.setNome_livro(rs.getString("nome_livro"));    
                devolucoes.add(dVO);
            }
            return devolucoes;
        } finally {
            con.close();
        }
    }
    
    public void deletarDevolucao(int idDevolucao) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "DELETE FROM devolucao WHERE id_devolucao = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idDevolucao);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    
    private ArrayList<DevolucaoVO> mapearResultSetParaLista(ResultSet rs) throws SQLException {
        ArrayList<DevolucaoVO> devolucoes = new ArrayList<>();
        while (rs.next()) {
            DevolucaoVO dVO = new DevolucaoVO();
            dVO.setId_devolucao(rs.getInt("id_devolucao"));
            dVO.setId_emprestimo(rs.getInt("id_emprestimo"));
            dVO.setDt_devolucao(rs.getDate("dt_devolucao"));
            dVO.setMulta(rs.getDouble("multa"));
            dVO.setNome_cliente(rs.getString("nome_cliente"));
            dVO.setNome_livro(rs.getString("nome_livro"));
            devolucoes.add(dVO);
        }
        return devolucoes;
    }
    
    public ArrayList<DevolucaoVO> filtrarPorCliente(String nomeCliente) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = """
                SELECT 
                    d.id_devolucao,
                    d.id_emprestimo,
                    d.dt_devolucao,
                    d.multa,
                    c.nome_cliente,
                    l.nome_livro
                FROM devolucao d
                INNER JOIN emprestimo e ON d.id_emprestimo = e.id_emprestimo
                INNER JOIN cliente c ON e.id_cliente = c.id_cliente
                INNER JOIN livro l ON e.id_livro = l.id_livro
                WHERE c.nome_cliente LIKE ?
                ORDER BY d.dt_devolucao DESC
                """;

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, "%" + nomeCliente + "%");
            ResultSet rs = pstm.executeQuery();

            return mapearResultSetParaLista(rs);
        } finally {
            con.close();
        }
    }
    
    public ArrayList<DevolucaoVO> filtrarPorLivro(String nomeLivro) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = """
                SELECT 
                    d.id_devolucao,
                    d.id_emprestimo,
                    d.dt_devolucao,
                    d.multa,
                    c.nome_cliente,
                    l.nome_livro
                FROM devolucao d
                INNER JOIN emprestimo e ON d.id_emprestimo = e.id_emprestimo
                INNER JOIN cliente c ON e.id_cliente = c.id_cliente
                INNER JOIN livro l ON e.id_livro = l.id_livro
                WHERE l.nome_livro LIKE ?
                ORDER BY d.dt_devolucao DESC
                """;

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, "%" + nomeLivro + "%");
            ResultSet rs = pstm.executeQuery();

            return mapearResultSetParaLista(rs);
        } finally {
            con.close();
        }
    }
}
    

