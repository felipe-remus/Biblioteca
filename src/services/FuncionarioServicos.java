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

    public void deletarFuncionario(int idFuncionario) throws SQLException {
        FuncionarioDAO fDAO = DAOFactory.getFuncionarioDAO();

        // Buscar email do funcionário antes de deletar
        FuncionarioVO funcionario = fDAO.buscarFuncionarioPorId(idFuncionario);
        String email = funcionario.getEmail_funcionario();

        // Deletar funcionário
        fDAO.deletarFuncionario(idFuncionario);

        // Deletar login associado
        fDAO.deletarLoginPorEmail(email);
    }
    public void confirmarAlteracao(
        FuncionarioVO fVO,
        TelefoneVO tVO,
        EnderecoVO eVO,
        int id_funcionario
    ) throws SQLException {

        // 1. Buscar funcionário atual para comparar email
        FuncionarioDAO fDAO = DAOFactory.getFuncionarioDAO();
        FuncionarioVO funcionarioAntigo = fDAO.buscarFuncionarioPorId(id_funcionario);
        String emailAntigo = funcionarioAntigo.getEmail_funcionario();
        String emailNovo = fVO.getEmail_funcionario();

        // 2. Atualizar funcionário
        fDAO.confirmarAlteracao(fVO, id_funcionario);

        // 3. Atualizar telefone e endereço
        FuncionarioVO funcAtual = fDAO.buscarFuncionarioPorId(id_funcionario);
        int id_fone = funcAtual.getId_fone();
        int id_endereco = funcAtual.getId_endereco();

        TelefoneDAO tDAO = DAOFactory.getTelefoneDAO();
        tDAO.atualizarTelefone(tVO, id_fone);

        EnderecoDAO eDAO = DAOFactory.getEnderecoDAO();
        eDAO.atualizarEndereco(eVO, id_endereco);

        // 4. ✅ Atualizar login se email mudou
        if (!emailAntigo.equals(emailNovo)) {
            // Atualiza o login para o novo email
            fDAO.atualizarLoginPorEmail(emailAntigo, emailNovo);
        }
    }
}