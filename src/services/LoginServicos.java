/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;
import dao.DAOFactory;
import dao.LoginDAO;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import model.LoginVO;
/**
 *
 * @author WILLIANDORNELESRODRI
 */
public class LoginServicos {
    public LoginVO buscarLoginPorLogin(String login) throws SQLException {
    LoginDAO lDAO = DAOFactory.getLoginDAO();
    return lDAO.buscarLoginPorLogin(login);
}
    public LoginVO autenticarLogin(LoginVO lVO) throws SQLException {
        LoginDAO lDAO = DAOFactory.getLoginDAO();
        return lDAO.autenticarLogin(lVO);
    }
    
    public List<Map<String, Object>> listaPerfil() throws SQLException {
        LoginDAO lDAO = DAOFactory.getLoginDAO();
        return lDAO.listarPerfil();
    }
    
    public Integer getPerfil(String nomeperfil) throws SQLException {
        LoginDAO lDAO = DAOFactory.getLoginDAO();
        return lDAO.getPerfil(nomeperfil);
    }
    
    public void cadastrarLogin(String login, String senha, int perfil) throws SQLException {
        LoginDAO lDAO = DAOFactory.getLoginDAO();
        lDAO.cadastrarLogin(login, senha, perfil);
    }
    
    public boolean validarSenha(int idLogin, String senha) throws SQLException {
        LoginDAO lDAO = DAOFactory.getLoginDAO();
        return lDAO.validarSenha(idLogin, senha);
    }
    
    public void atualizarLogin(int idLogin, String login, String senha, int perfil) throws SQLException {
        LoginDAO lDAO = DAOFactory.getLoginDAO();
        lDAO.atualizarLogin(idLogin, login, senha, perfil);
    }
    
    public LoginVO buscarLoginPorId(int idLogin) throws SQLException {
        LoginDAO lDAO = DAOFactory.getLoginDAO();
        return lDAO.buscarLoginPorId(idLogin);
    }
}
