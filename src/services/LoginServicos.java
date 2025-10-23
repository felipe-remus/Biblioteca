/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;
import dao.DAOFactory;
import dao.LoginDAO;
import java.sql.SQLException;
import java.sql.ResultSet;
import model.LoginVO;
/**
 *
 * @author WILLIANDORNELESRODRI
 */
public class LoginServicos {
    public ResultSet autenticarLogin(LoginVO lVO) throws SQLException{
        LoginDAO lDAO = DAOFactory.getLoginDAO();
        return lDAO.autenticarLogin(lVO);
    }
    public ResultSet listaPerfil() throws SQLException{
        LoginDAO lDAO = DAOFactory.getLoginDAO();
        return lDAO.listarPerfil();
    }
    public Integer getPerfil(String nomeperfil) throws SQLException{
        LoginDAO lDAO = DAOFactory.getLoginDAO();
        return lDAO.getPerfil(nomeperfil);
    }
}
