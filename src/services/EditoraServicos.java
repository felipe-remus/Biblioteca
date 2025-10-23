/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.DAOFactory;
import dao.EditoraDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import model.EditoraVO;

/**
 *
 * @author WILLIAN
 */
public class EditoraServicos {
    public void cadastroeditora(EditoraVO eVO)throws SQLException{
        EditoraDAO eDAO= DAOFactory.getEditoraDAO();
        eDAO.cadEditora(eVO);
    }
    public ArrayList<EditoraVO> buscar() throws SQLException{
        EditoraDAO eDAO = DAOFactory.getEditoraDAO();
        return eDAO.buscar();
    }
    public ArrayList<EditoraVO> filtar(String query)throws SQLException{
        EditoraDAO eDAO = DAOFactory.getEditoraDAO();
        return eDAO.filtrar(query);
    }
    public void deletar(int id_editora)throws SQLException{
        EditoraDAO eDAO = DAOFactory.getEditoraDAO();
        eDAO.deletar(id_editora);
    }
    public void confirmarAlteracao(EditoraVO eVO, long id_editora)throws SQLException{
        EditoraDAO eDAO = DAOFactory.getEditoraDAO();
        eDAO.confirmarAlteracao(eVO, id_editora);
    }
    
    public EditoraVO buscarEditoraPorId(int id_editora) throws SQLException {
        EditoraDAO eDAO = DAOFactory.getEditoraDAO();
        return eDAO.buscarEditoraPorId(id_editora);
    }
}