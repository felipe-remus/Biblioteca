/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.TelefoneVO;
import persistencia.ConexaoBanco;
/**
 *
 * @author FELIPEREMUSDEALMEIDA
 */
public class TelefoneDAO {
    public void cadastrarTelefone(TelefoneVO tVO) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "INSERT INTO telefone VALUES (NULL, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, tVO.getFone());
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    
    public int getIdTelefone(TelefoneVO tVO) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_fone FROM telefone WHERE telefone = ? ORDER BY id_fone DESC LIMIT 1";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, tVO.getFone());

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_fone");
            } else {
                throw new SQLException("Telefone não encontrado no banco.");
            }
        } finally {
            con.close();
        }
    }
    
    // TelefoneDAO
    public TelefoneVO buscarTelefonePorId(int id_fone) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT * FROM telefone WHERE id_fone = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_fone);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                TelefoneVO tVO = new TelefoneVO();
                tVO.setId_fone(rs.getInt("id_fone"));
                tVO.setFone(rs.getString("telefone"));
                return tVO;
            }
            throw new SQLException("Telefone não encontrado.");
        } finally {
            con.close();
        }
    }
    public void atualizarTelefone(TelefoneVO tVO, int id_fone) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "UPDATE telefone SET telefone = ? WHERE id_fone = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, tVO.getFone());
            pstm.setInt(2, id_fone);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    public void deletarTelefone(int id_fone) throws SQLException {
    Connection con = new ConexaoBanco().getConexao();
    try {
        String sql = "DELETE FROM telefone WHERE id_fone = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id_fone);
        pstm.executeUpdate();
    } finally {
        con.close();
    }
}
}
