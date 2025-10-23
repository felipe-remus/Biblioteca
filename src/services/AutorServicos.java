/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.AutorDAO;
import dao.DAOFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import model.AutorVO;

/**
 *
 * @author WILLIAN
 */
public class AutorServicos {
    public void cadastrarAutor(AutorVO aVO) throws SQLException {
        AutorDAO aDAO = DAOFactory.getAutorDAO();
        aDAO.cadastrarAutor(aVO);
    }

    public ArrayList<AutorVO> buscarAutor() throws SQLException {
        AutorDAO aDAO = DAOFactory.getAutorDAO();
        return aDAO.buscarAutor();
    }

    public ArrayList<AutorVO> filtrarAutor(String nome) throws SQLException {
        AutorDAO aDAO = DAOFactory.getAutorDAO();
        return aDAO.filtrarAutor(nome);
    }

    public void deletarAutor(int id_autor) throws SQLException {
        AutorDAO aDAO = DAOFactory.getAutorDAO();
        aDAO.deletarAutor(id_autor);
    }

    public void confirmarAlteracao(AutorVO aVO, int id_autor) throws SQLException {
        AutorDAO aDAO = DAOFactory.getAutorDAO();
        aDAO.confirmarAlteracao(aVO, id_autor);
    }
    
    public AutorVO buscarAutorPorId(int id_autor) throws SQLException {
        AutorDAO aDAO = DAOFactory.getAutorDAO();
        return aDAO.buscarAutorPorId(id_autor);
    }
}
