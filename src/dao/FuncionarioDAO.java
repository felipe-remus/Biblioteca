/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import java.sql.*;
import java.util.ArrayList;
import model.FuncionarioVO;
import persistencia.ConexaoBanco;

/**
 *
 * @author Felipe Remus
 */

public class FuncionarioDAO {

    public void cadastrarFuncionario(FuncionarioVO fVO, int id_fone, int id_endereco) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "INSERT INTO funcionario (nome_funcionario, email_funcionario, id_fone, id_endereco) VALUES (?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, fVO.getNome_funcionario());
            pstm.setString(2, fVO.getEmail_funcionario());
            pstm.setInt(3, id_fone);
            pstm.setInt(4, id_endereco);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }

    public ArrayList<FuncionarioVO> buscarFuncionario() throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_funcionario, nome_funcionario, email_funcionario, id_fone, id_endereco FROM funcionario";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            ArrayList<FuncionarioVO> funcionarios = new ArrayList<>();
            while (rs.next()) {
                FuncionarioVO fVO = new FuncionarioVO();
                fVO.setId_funcionario(rs.getInt("id_funcionario"));
                fVO.setNome_funcionario(rs.getString("nome_funcionario"));
                fVO.setEmail_funcionario(rs.getString("email_funcionario"));
                fVO.setId_fone(rs.getInt("id_fone"));
                fVO.setId_endereco(rs.getInt("id_endereco"));
                funcionarios.add(fVO);
            }
            return funcionarios;
        } finally {
            con.close();
        }
    }

    public ArrayList<FuncionarioVO> filtrarFuncionario(String query) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_funcionario, nome_funcionario, email_funcionario, id_fone, id_endereco FROM funcionario " + query;
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            ArrayList<FuncionarioVO> funcionarios = new ArrayList<>();
            while (rs.next()) {
                FuncionarioVO fVO = new FuncionarioVO();
                fVO.setId_funcionario(rs.getInt("id_funcionario"));
                fVO.setNome_funcionario(rs.getString("nome_funcionario"));
                fVO.setEmail_funcionario(rs.getString("email_funcionario"));
                fVO.setId_fone(rs.getInt("id_fone"));
                fVO.setId_endereco(rs.getInt("id_endereco"));
                funcionarios.add(fVO);
            }
            return funcionarios;
        } finally {
            con.close();
        }
    }

    public FuncionarioVO buscarFuncionarioPorId(int id_funcionario) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_funcionario, nome_funcionario, email_funcionario, id_fone, id_endereco FROM funcionario WHERE id_funcionario = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_funcionario);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                FuncionarioVO fVO = new FuncionarioVO();
                fVO.setId_funcionario(rs.getInt("id_funcionario"));
                fVO.setNome_funcionario(rs.getString("nome_funcionario"));
                fVO.setEmail_funcionario(rs.getString("email_funcionario"));
                fVO.setId_fone(rs.getInt("id_fone"));
                fVO.setId_endereco(rs.getInt("id_endereco"));
                return fVO;
            }
            throw new SQLException("Funcionário não encontrado.");
        } finally {
            con.close();
        }
    }

    public void deletarFuncionario(int id_funcionario) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "DELETE FROM funcionario WHERE id_funcionario = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_funcionario);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }

    public void confirmarAlteracao(FuncionarioVO fVO, int id_funcionario) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "UPDATE funcionario SET nome_funcionario = ?, email_funcionario = ? WHERE id_funcionario = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, fVO.getNome_funcionario());
            pstm.setString(2, fVO.getEmail_funcionario());
            pstm.setInt(3, id_funcionario);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
}
