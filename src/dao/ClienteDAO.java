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
import model.ClienteVO;
import persistencia.ConexaoBanco;
/**
 *
 * @author WILLIANDORNELESRODRI
 */
public class ClienteDAO {
    public void cadastrarCliente(ClienteVO cVO, long id_fone, long id_endereco) throws SQLException{
        Connection con= new ConexaoBanco().getConexao();
        
        try {
            String sql= "Insert into cliente values (null, ?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, cVO.getNome_cliente());
            pstm.setString(2, cVO.getEmail_cliente());
            pstm.setLong(3, id_fone);
            pstm.setLong(4, id_endereco);
            
            pstm.execute();
            pstm.close();
            
            
        } catch (SQLException se) {
            throw new SQLException("Erro no cadastro");
        }finally{
            con.close();
        }
    }
    
    public ArrayList<ClienteVO> buscarCliente() throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_cliente, nome_cliente, email_cliente, id_fone, id_endereco FROM cliente";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            ArrayList<ClienteVO> clientes = new ArrayList<>();
            while (rs.next()) {
                ClienteVO cVO = new ClienteVO();
                cVO.setId_cliente(rs.getInt("id_cliente"));
                cVO.setNome_cliente(rs.getString("nome_cliente"));
                cVO.setEmail_cliente(rs.getString("email_cliente"));
                cVO.setId_fone(rs.getInt("id_fone"));
                cVO.setId_endereco(rs.getInt("id_endereco"));
                clientes.add(cVO);
            }
             return clientes;
        } finally {
            con.close();
        }
    }
    
    public ClienteVO buscarClientePorId(int id_cliente) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_cliente, nome_cliente, email_cliente, id_fone, id_endereco FROM cliente WHERE id_cliente = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_cliente);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                ClienteVO cVO = new ClienteVO();
                cVO.setId_cliente(rs.getInt("id_cliente"));
                cVO.setNome_cliente(rs.getString("nome_cliente"));
                cVO.setEmail_cliente(rs.getString("email_cliente"));
                cVO.setId_fone(rs.getInt("id_fone"));
                cVO.setId_endereco(rs.getInt("id_endereco"));
                return cVO;
            }
            throw new SQLException("Cliente não encontrado.");
        } finally {
            con.close();
        }
    }
    
    public ArrayList<ClienteVO> filtrarCliente(String query) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_cliente, nome_cliente, email_cliente, id_fone, id_endereco FROM cliente " + query;
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            ArrayList<ClienteVO> clientes = new ArrayList<>();
            while (rs.next()) {
                ClienteVO cVO = new ClienteVO();
                cVO.setId_cliente(rs.getInt("id_cliente"));
                cVO.setNome_cliente(rs.getString("nome_cliente"));
                cVO.setEmail_cliente(rs.getString("email_cliente"));
                cVO.setId_fone(rs.getInt("id_fone"));
                cVO.setId_endereco(rs.getInt("id_endereco"));
                clientes.add(cVO);
                 }
            return clientes;
        } finally {
            con.close();
        }
    }
    public void deletarCliente(int id_cliente) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "DELETE FROM cliente WHERE id_cliente = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_cliente);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    
    public void confirmarAlteracao(ClienteVO cVO, int id_cliente) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "UPDATE cliente SET nome_cliente = ?, email_cliente = ? WHERE id_cliente = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, cVO.getNome_cliente());
            pstm.setString(2, cVO.getEmail_cliente());
            pstm.setInt(3, id_cliente);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
}
