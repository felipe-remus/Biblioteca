/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import model.AutorVO;

import persistencia.ConexaoBanco;


/**
 *
 * @author WILLIAN
 */
public class AutorDAO {

    public void cadastrarAutor(AutorVO aVO) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "INSERT INTO autor (nome_autor, sobrenome_autor) VALUES (?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, aVO.getNomeautor());
            pstm.setString(2, aVO.getSobrenomeautor());
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }

    public ArrayList<AutorVO> buscarAutor() throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_autor, nome_autor, sobrenome_autor FROM autor";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            ArrayList<AutorVO> autores = new ArrayList<>();
            while (rs.next()) {
                AutorVO aVO = new AutorVO();
                aVO.setIdautor(rs.getInt("id_autor"));           
                aVO.setNomeautor(rs.getString("nome_autor"));    
                aVO.setSobrenomeautor(rs.getString("sobrenome_autor"));
                autores.add(aVO);
            }
            return autores;
        } finally {
            con.close();
        }
    }
    
    public AutorVO buscarAutorPorId(int id_autor) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_autor, nome_autor, sobrenome_autor FROM autor WHERE id_autor = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_autor);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                AutorVO aVO = new AutorVO();
                aVO.setIdautor(rs.getInt("id_autor"));
                aVO.setNomeautor(rs.getString("nome_autor"));
                aVO.setSobrenomeautor(rs.getString("sobrenome_autor"));
                return aVO;
            }
            throw new SQLException("Autor não encontrado.");
        } finally {
            con.close();
        }
    }

    public ArrayList<AutorVO> filtrarAutor(String nome) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = """
                SELECT id_autor, nome_autor, sobrenome_autor 
                FROM autor 
                WHERE nome_autor LIKE ? OR sobrenome_autor LIKE ?
                ORDER BY nome_autor
                """;
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,  nome + "%");
            pstm.setString(2,  nome + "%");
            ResultSet rs = pstm.executeQuery();

            ArrayList<AutorVO> autores = new ArrayList<>();
            while (rs.next()) {
                AutorVO aVO = new AutorVO();
                aVO.setIdautor(rs.getInt("id_autor"));
                aVO.setNomeautor(rs.getString("nome_autor"));
                aVO.setSobrenomeautor(rs.getString("sobrenome_autor"));
                autores.add(aVO);
            }
            return autores;
        } finally {
            con.close();
        }
    }

    public void deletarAutor(int id_autor) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "DELETE FROM autor WHERE id_autor = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_autor);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }

    public void confirmarAlteracao(AutorVO aVO, int id_autor) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "UPDATE autor SET nome_autor = ?, sobrenome_autor = ? WHERE id_autor = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, aVO.getNomeautor());
            pstm.setString(2, aVO.getSobrenomeautor());
            pstm.setInt(3, id_autor);
            pstm.executeUpdate();
        }catch (SQLException se) {
            throw new SQLException("Erro ao alterar! DAO" +se.getMessage());
        } finally {
            con.close();
        }
    }
}
