/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.DAOFactory;
import dao.EnderecoDAO;
import dao.FuncionarioDAO;
import dao.TelefoneDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import model.EnderecoVO;
import model.FuncionarioVO;
import model.TelefoneVO;

/**
 *
 * @author Felipe Remus
 */
public class FuncionarioServicos {

    public void cadastrarFuncionario(FuncionarioVO fVO, int id_fone, int id_endereco) throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionarioDAO();
        fDAO.cadastrarFuncionario(fVO, id_fone, id_endereco);
    }

    public ArrayList<FuncionarioVO> buscarFuncionario() throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionarioDAO();
        return fDAO.buscarFuncionario();
    }

    public ArrayList<FuncionarioVO> filtrarFuncionario(String query) throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionarioDAO();
        return fDAO.filtrarFuncionario(query);
    }

    public FuncionarioVO buscarFuncionarioPorId(int id_funcionario) throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionarioDAO();
        return fDAO.buscarFuncionarioPorId(id_funcionario);
    }

    public void deletarFuncionario(int id_funcionario) throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionarioDAO();
        
        // Busca IDs de fone/endereco antes de deletar
        FuncionarioVO funcionario = fDAO.buscarFuncionarioPorId(id_funcionario);
        int id_fone = (int) funcionario.getId_fone();
        int id_endereco = (int) funcionario.getId_endereco();
        
        // Deleta funcionário
        fDAO.deletarFuncionario(id_funcionario);
        
        // Deleta telefone e endereço
        TelefoneDAO tDAO = DAOFactory.getTelefoneDAO();
        tDAO.deletarTelefone(id_fone);
        
        EnderecoDAO eDAO = DAOFactory.getEnderecoDAO();
        eDAO.deletarEndereco(id_endereco);
    }

    public void confirmarAlteracao(
        FuncionarioVO fVO,
        TelefoneVO tVO,
        EnderecoVO eVO,
        int id_funcionario
    ) throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionarioDAO();
        fDAO.confirmarAlteracao(fVO, id_funcionario);
        
        FuncionarioVO funcAtual = fDAO.buscarFuncionarioPorId(id_funcionario);
        int id_fone = (int) funcAtual.getId_fone();
        int id_endereco = (int) funcAtual.getId_endereco();
        
        TelefoneDAO tDAO = DAOFactory.getTelefoneDAO();
        tDAO.atualizarTelefone(tVO, id_fone);
        
        EnderecoDAO eDAO = DAOFactory.getEnderecoDAO();
        eDAO.atualizarEndereco(eVO, id_endereco);
    }
}