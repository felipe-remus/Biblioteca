 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import java.security.MessageDigest;
import java.sql.ResultSet;
import java.sql.Connection;
import model.LoginVO;
import java.sql.SQLException;
import persistencia.ConexaoBanco;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import util.Criptografar;
import view.GUIMenuPrincipal;

public class LoginDAO {
    Connection con;
    
    public ResultSet autenticarLogin(LoginVO lVO) throws SQLException{
        con = (Connection) new ConexaoBanco().getConexao();
        try {
            
            Criptografar crip = new Criptografar();
            String senhaCriptografada = crip.CriptografarSenha(lVO.getSenha());
            
            String sql = "Select * from login where login=? and senha=? and perfil=?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, lVO.getLogin());
            pstm.setString(2, senhaCriptografada);
            pstm.setInt(3, lVO.getPerfil());
            
            ResultSet rs = pstm.executeQuery();
            
           return rs;
            
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Erro! LoginDAO" +se.getMessage());
            return null;
        }
    }
    
    public ResultSet listarPerfil(){
        Connection con=new ConexaoBanco().getConexao();
        try {
            String sql = "select * from perfil order by nome_perfil;";
            PreparedStatement pstm = con.prepareStatement(sql);
            
            return pstm.executeQuery();
        } catch (SQLException se) {
            JOptionPane.showMessageDialog(null, "Erro LoginDAO.listarPerfil" + se.getMessage());
            return null;
        }
    }
    
    public Integer getPerfil(String nomeperfil)throws SQLException{
        con = (Connection) new ConexaoBanco().getConexao();
        try {
            String sql = "select id_perfil from perfil where nome_perfil=?;";
            PreparedStatement pstm = con.prepareStatement(sql);
            
            pstm.setString(1, nomeperfil);
            
            ResultSet rs = pstm.executeQuery();
            
            if(rs.next()){
                return rs.getInt("id_perfil");
            }else{
                return null;
            }
        } catch (SQLException se) {
            throw new SQLException("Erro LoginDAO.getPerfil: " + se.getMessage());
        }
    }
}
