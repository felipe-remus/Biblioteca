/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.DAOFactory;
import dao.GeneroDAO;
import model.GeneroVO;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 *
 * @author WILLIAN
 */
public class GeneroServicos {
    public void cadGenero(GeneroVO gVO) throws SQLException{
        GeneroDAO gDAO = DAOFactory.getGeneroDAO();
        gDAO.cadastrarGenero(gVO);
    }
    public ArrayList<GeneroVO> buscarGenero() throws SQLException{
        GeneroDAO gDAO = DAOFactory.getGeneroDAO();
        return gDAO.buscarGenero();
    }
    public GeneroVO buscarGeneroPorId(int id_genero)  throws SQLException{
        GeneroDAO gDAO = DAOFactory.getGeneroDAO();
        return gDAO.buscarGeneroPorId(id_genero);
    }
    public ArrayList<GeneroVO> filtarGenero(String query)throws SQLException{
        GeneroDAO gDAO = DAOFactory.getGeneroDAO();
        return gDAO.filtrarGenero(query);
    }
    public void deletarGenero(int id_genero)throws SQLException{
        GeneroDAO gDAO = DAOFactory.getGeneroDAO();
        gDAO.deletarGenero(id_genero);
    }
    public void confirmarAlteracao(GeneroVO gVO, long id_genero)throws SQLException{
        GeneroDAO gDAO = DAOFactory.getGeneroDAO();
        gDAO.confirmarAlteracao(gVO, id_genero);
    }
}