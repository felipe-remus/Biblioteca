/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.DAOFactory;
import dao.EnderecoDAO;
import java.sql.SQLException;
import model.EnderecoVO;

/**
 *
 * @author Felipe Remus
 */
public class EnderecoServicos {
    public void cadadtrarEndereco(EnderecoVO eVO) throws SQLException{
        EnderecoDAO eDAO = DAOFactory.getEnderecoDAO();
        eDAO.cadastrarEndereco(eVO);
    }
    
    public int getidEndereco(EnderecoVO eVO) throws SQLException{
        EnderecoDAO eDAO = DAOFactory.getEnderecoDAO();
        return eDAO.getIdEndereco(eVO);
    }
    
    public EnderecoVO buscarEnderecoPorId(int id_endereco) throws SQLException {
        return DAOFactory.getEnderecoDAO().buscarEnderecoPorId(id_endereco);
    }
}
