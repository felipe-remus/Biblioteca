/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.GeneroVO;
import persistencia.ConexaoBanco;

/**
 *
 * @author WILLIAN
 */
public class GeneroDAO {
    public void cadastrarGenero(GeneroVO gVO) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "INSERT INTO genero (nome_genero) VALUES (?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, gVO.getNomegenero());
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    public ArrayList<GeneroVO> buscarGenero() throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_genero, nome_genero FROM genero ORDER BY nome_genero";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            ArrayList<GeneroVO> generos = new ArrayList<>();
            while (rs.next()) {
                GeneroVO gVO = new GeneroVO();
                gVO.setIdgenero(rs.getInt("id_genero"));
                gVO.setNomegenero(rs.getString("nome_genero"));
                generos.add(gVO);
            }
            return generos;
        } finally {
            con.close();
        }
    }
    public GeneroVO buscarGeneroPorId(int id_genero) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_genero, nome_genero FROM genero WHERE id_genero = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_genero);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                GeneroVO gVO = new GeneroVO();
                gVO.setIdgenero(rs.getInt("id_genero"));
                gVO.setNomegenero(rs.getString("nome_genero"));
                return gVO;
            }
            throw new SQLException("Gênero não encontrado.");
        } finally {
            con.close();
        }
    }
    public ArrayList<GeneroVO> filtrarGenero(String query) throws SQLException{
            Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_genero, nome_genero FROM genero WHERE nome_genero LIKE ? ORDER BY nome_genero";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, "%" + query + "%");
            ResultSet rs = pstm.executeQuery();

            ArrayList<GeneroVO> generos = new ArrayList<>();
            while (rs.next()) {
                GeneroVO gVO = new GeneroVO();
                gVO.setIdgenero(rs.getInt("id_genero"));
                gVO.setNomegenero(rs.getString("nome_genero"));
                generos.add(gVO);
            }
            return generos;
        } finally {
            con.close();
        }
    }
    public void deletarGenero(int idGenero) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "DELETE FROM genero WHERE id_genero = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idGenero);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    public void confirmarAlteracao(GeneroVO gVO, long id_genero)throws SQLException{
        Connection con= new ConexaoBanco().getConexao();
        
        try {
            String sql = "UPDATE genero SET nome_genero = ? WHERE id_genero = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            
            pstm.setString(1, gVO.getNomegenero());
            pstm.setLong(2, id_genero);
            
            
            pstm.execute();
            pstm.close();
            
        } catch (SQLException se) {
            throw new SQLException("Erro ao alterar! DAO" +se.getMessage());
        }finally{
            con.close();
        }
    }
}
