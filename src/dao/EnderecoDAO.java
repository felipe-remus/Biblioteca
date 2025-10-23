/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.EnderecoVO;
import persistencia.ConexaoBanco;

/**
 *
 * @author FELIPEREMUSDEALMEIDA
 */
public class EnderecoDAO {
    public void cadastrarEndereco(EnderecoVO eVO) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "INSERT INTO endereco VALUES (NULL, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, eVO.getRua());
            pstm.setString(2, eVO.getNumero());
            pstm.setString(3, eVO.getComplemento());
            pstm.setString(4, eVO.getBairro());
            pstm.setString(5, eVO.getCidade());
            pstm.setString(6, eVO.getCep());
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    
    public int getIdEndereco(EnderecoVO eVO) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_endereco FROM endereco WHERE rua = ? AND numero = ? AND complemento = ? ORDER BY id_endereco DESC LIMIT 1";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, eVO.getRua());
            pstm.setString(2, eVO.getNumero());
            pstm.setString(3, eVO.getComplemento());

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_endereco");
            } else {
                throw new SQLException("Endereço não encontrado no banco.");
            }
        } finally {
            con.close();
        }
    }
    
    public EnderecoVO buscarEnderecoPorId(int id_endereco) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT * FROM endereco WHERE id_endereco = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_endereco);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                EnderecoVO eVO = new EnderecoVO();
                eVO.setId_endereco(rs.getInt("id_endereco"));
                eVO.setRua(rs.getString("rua"));
                eVO.setNumero(rs.getString("numero"));
                eVO.setComplemento(rs.getString("complemento"));
                eVO.setBairro(rs.getString("bairro"));
                eVO.setCidade(rs.getString("cidade"));
                eVO.setCep(rs.getString("cep"));
                return eVO;
            }
            throw new SQLException("Endereço não encontrado.");
        } finally {
            con.close();
        }
    }
    
    public void atualizarEndereco(EnderecoVO eVO, int id_endereco) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "UPDATE endereco SET rua = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, cep = ? WHERE id_endereco = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, eVO.getRua());
            pstm.setString(2, eVO.getNumero());
            pstm.setString(3, eVO.getComplemento());
            pstm.setString(4, eVO.getBairro());
            pstm.setString(5, eVO.getCidade());
            pstm.setString(6, eVO.getCep());
            pstm.setInt(7, id_endereco);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    public void deletarEndereco(int id_endereco) throws SQLException {
    Connection con = new ConexaoBanco().getConexao();
    try {
        String sql = "DELETE FROM endereco WHERE id_endereco = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setInt(1, id_endereco);
        pstm.executeUpdate();
    } finally {
        con.close();
    }
}
}
