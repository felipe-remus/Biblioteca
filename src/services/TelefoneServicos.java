/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.TelefoneDAO;
import dao.DAOFactory;
import java.sql.SQLException;
import model.TelefoneVO;

/**
 *
 * @author FELIPEREMUSDEALMEIDA
 */
public class TelefoneServicos {
    public void cadadtrarTelefone(TelefoneVO tVO) throws SQLException{
        TelefoneDAO tDAO = DAOFactory.getTelefoneDAO();
        tDAO.cadastrarTelefone(tVO);
    }
    
    public int getidTelefone(TelefoneVO tVO) throws SQLException{
        TelefoneDAO tDAO = DAOFactory.getTelefoneDAO();
        return tDAO.getIdTelefone(tVO);
    }
    
    public TelefoneVO buscarTelefonePorId(int id_fone) throws SQLException {
        return DAOFactory.getTelefoneDAO().buscarTelefonePorId(id_fone);
    }
}
