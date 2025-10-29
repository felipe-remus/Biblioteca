 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import java.security.MessageDigest;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.sql.Connection;
import model.LoginVO;
import java.sql.SQLException;
import persistencia.ConexaoBanco;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import util.Criptografar;
import view.GUIMenuPrincipal;

public class LoginDAO {
    public LoginVO autenticarLogin(LoginVO lVO) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            Criptografar crip = new Criptografar();
            String senhaCriptografada = crip.CriptografarSenha(lVO.getSenha());

            String sql = "SELECT id_login, login, perfil FROM login WHERE login = ? AND senha = ? AND perfil = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, lVO.getLogin());
            pstm.setString(2, senhaCriptografada);
            pstm.setInt(3, lVO.getPerfil());

            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                LoginVO usuarioAutenticado = new LoginVO();
                usuarioAutenticado.setIdLogin(rs.getInt("id_login"));
                usuarioAutenticado.setLogin(rs.getString("login"));
                usuarioAutenticado.setPerfil(rs.getInt("perfil"));
                usuarioAutenticado.setAutenticado(true);
                return usuarioAutenticado;
            }
            return null;

        } finally {
            con.close(); // ← AGORA FECHA A CONEXÃO!
        }
    }
    
    public List<Map<String, Object>> listarPerfil() throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_perfil, nome_perfil FROM perfil ORDER BY nome_perfil";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            List<Map<String, Object>> perfis = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> perfil = new HashMap<>();
                perfil.put("id", rs.getInt("id_perfil"));
                perfil.put("nome", rs.getString("nome_perfil"));
                perfis.add(perfil);
            }
            return perfis;
        } finally {
            con.close();
        }
    }
    
    public Integer getPerfil(String nomeperfil) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_perfil FROM perfil WHERE nome_perfil = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, nomeperfil);
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("id_perfil");
            }
            return null;
        } finally {
            con.close();
        }
    }
    
    public void cadastrarLogin(String login, String senha, int perfil) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            // Verificar duplicidade
            String checkSql = "SELECT id_login FROM login WHERE login = ?";
            PreparedStatement checkPstm = con.prepareStatement(checkSql);
            checkPstm.setString(1, login);
            ResultSet rs = checkPstm.executeQuery();
            
            if (rs.next()) {
                throw new SQLException("Login já existe");
            }
            
            String sql = "INSERT INTO login (login, senha, perfil) VALUES (?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, login);
            pstm.setString(2, new Criptografar().CriptografarSenha(senha));
            pstm.setInt(3, perfil);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    
    public boolean validarSenha(int idLogin, String senha) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT senha FROM login WHERE id_login = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idLogin);
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()) {
                String senhaBanco = rs.getString("senha");
                String senhaCriptografada = new Criptografar().CriptografarSenha(senha);
                return senhaBanco.equals(senhaCriptografada);
            }
            return false;
        } finally {
            con.close();
        }
    }
    
    public void atualizarLogin(int idLogin, String login, String senha, int perfil) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            // Verificar se a senha é nula ou vazia
            if (senha == null || senha.trim().isEmpty()) {
                throw new SQLException("Senha não pode ser nula ou vazia");
            }

            // Verificar duplicidade (exceto para o próprio usuário)
            String checkSql = "SELECT id_login FROM login WHERE login = ? AND id_login != ?";
            PreparedStatement checkPstm = con.prepareStatement(checkSql);
            checkPstm.setString(1, login);
            checkPstm.setInt(2, idLogin);
            ResultSet rs = checkPstm.executeQuery();

            if (rs.next()) {
                throw new SQLException("Login já existe");
            }

            String sql = "UPDATE login SET login = ?, senha = ?, perfil = ? WHERE id_login = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, login);
            pstm.setString(2, new Criptografar().CriptografarSenha(senha)); // ← agora senha não é null
            pstm.setInt(3, perfil);
            pstm.setInt(4, idLogin);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    
    public void atualizarPerfil(int idLogin, int novoPerfil) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "UPDATE login SET perfil = ? WHERE id_login = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, novoPerfil);
            pstm.setInt(2, idLogin);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    
    public LoginVO buscarLoginPorId(int idLogin) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_login, login, perfil FROM login WHERE id_login = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idLogin);
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()) {
                LoginVO lVO = new LoginVO();
                lVO.setIdLogin(rs.getInt("id_login"));
                lVO.setLogin(rs.getString("login"));
                lVO.setPerfil(rs.getInt("perfil"));
                return lVO;
            }
            throw new SQLException("Login não encontrado");
        } finally {
            con.close();
        }
    }
    
    public LoginVO buscarLoginPorLogin(String login) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "SELECT id_login, login, perfil FROM login WHERE login = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, login);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                LoginVO lVO = new LoginVO();
                lVO.setIdLogin(rs.getInt("id_login"));
                lVO.setLogin(rs.getString("login"));
                lVO.setPerfil(rs.getInt("perfil"));
                return lVO;
            }
            return null;
        } finally {
            con.close();
        }
    }
    
    public ArrayList<LoginVO> buscarTodosLogins() throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            con = new ConexaoBanco().getConexao();
            String sql = "SELECT id_login, login, perfil FROM login ORDER BY login";
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            ArrayList<LoginVO> logins = new ArrayList<>();
            while (rs.next()) {
                LoginVO lVO = new LoginVO();
                lVO.setIdLogin(rs.getInt("id_login"));
                lVO.setLogin(rs.getString("login"));
                lVO.setPerfil(rs.getInt("perfil"));
                logins.add(lVO);
            }
            return logins;
        } finally {
            if (con != null) con.close();
        }
    }

    public void deletarLogin(int idLogin) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            con = new ConexaoBanco().getConexao();
            String sql = "DELETE FROM login WHERE id_login = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idLogin);
            pstm.executeUpdate();
        } finally {
            if (con != null) con.close();
        }
    }
    
    public ArrayList<LoginVO> filtrarLoginsPorLogin(String termo) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            con = new ConexaoBanco().getConexao();
            String sql = "SELECT id_login, login, perfil FROM login WHERE login LIKE ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, termo + "%");
            ResultSet rs = pstm.executeQuery();

            ArrayList<LoginVO> logins = new ArrayList<>();
            while (rs.next()) {
                LoginVO lVO = new LoginVO();
                lVO.setIdLogin(rs.getInt("id_login"));
                lVO.setLogin(rs.getString("login"));
                lVO.setPerfil(rs.getInt("perfil"));
                logins.add(lVO);
            }
            return logins;
        } finally {
            if (con != null) con.close();
        }
    }
    
    
}