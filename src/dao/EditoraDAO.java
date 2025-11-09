/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.EditoraVO;
import persistencia.ConexaoBanco;

/**
 *
 * @author WILLIAN
 */
public class EditoraDAO {
    public void cadEditora(EditoraVO eVO)throws SQLException{
        Connection con=new ConexaoBanco().getConexao();
        try {
            String sql="insert into editora values(null, ?)";
            
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, eVO.getNomeeditora());
            
            pstm.execute();
            pstm.close();
        } catch (SQLException se) {
            throw new SQLException("Erro no cadastro"+se.getMessage());
        }finally{
            con.close();
        }
    }
    
    public ArrayList<EditoraVO> buscar() throws SQLException{
        Connection con= new ConexaoBanco().getConexao();
        try {
            String sql="Select * from editora";
            PreparedStatement pstm = con.prepareStatement(sql);
            
            ResultSet rs = pstm.executeQuery();
            ArrayList<EditoraVO> pro = new ArrayList<>();
            while(rs.next()){
                EditoraVO eVO= new EditoraVO();
                eVO.setIdeditora((int) rs.getLong("id_editora"));
                eVO.setNomeeditora(rs.getString("nome_editora"));
                pro.add(eVO);
            }
            pstm.close();
            return pro;
        } catch (SQLException se) {
             throw new SQLException("Erro ao buscar Editora! " +se.getMessage());
        }finally{
            con.close();
        }
    }
    
    public EditoraVO buscarEditoraPorId(int id_editora) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_editora, nome_editora FROM editora WHERE id_editora = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, id_editora);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                EditoraVO eVO = new EditoraVO();
                eVO.setIdeditora(rs.getInt("id_editora"));
                eVO.setNomeeditora(rs.getString("nome_editora"));
                return eVO;
            }
            throw new SQLException("Editora não encontrado.");
        } finally {
            con.close();
        }
    }

    public ArrayList<EditoraVO> filtrar(String query) throws SQLException{
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT * FROM editora " + query;
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            
            ArrayList<EditoraVO> pro= new ArrayList<>();
            while(rs.next()){
                EditoraVO eVO = new EditoraVO();
                eVO.setIdeditora((int) rs.getLong("id_editora"));
                eVO.setNomeeditora(rs.getString("nome_editora"));
                pro.add(eVO);
            }
            pstm.close();
            return pro;
        } catch (SQLException se) {
            throw new SQLException("Erro ao filtrar Editora"+se.getMessage());
        }finally{
            con.close();
        }
    }
    
    public void deletar (long id_editora) throws SQLException{
        Connection con = new ConexaoBanco().getConexao();
        
        try {
            String sql = "Delete from editora where id_editora = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            
            pstm.setLong(1, id_editora);
            
            pstm.execute();
            pstm.close();
        } catch (SQLException se) {
            throw new SQLException("Erro ao deletar DAO" + se.getMessage());
        }finally{
            con.close();
        }
    }
    public void confirmarAlteracao(EditoraVO eVO, long id_editora)throws SQLException{
        Connection con= new ConexaoBanco().getConexao();
        
        try {
            String sql = "UPDATE editora SET nome_editora = ? WHERE id_editora = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, eVO.getNomeeditora());
            pstm.setLong(2, id_editora);
            
            
            pstm.executeUpdate();
        } catch (SQLException se) {
            throw new SQLException("Erro ao alterar! DAO" +se.getMessage());
        }finally{
            con.close();
        }
    }
}