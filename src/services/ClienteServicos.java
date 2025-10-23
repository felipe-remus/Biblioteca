/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.DAOFactory;
import dao.ClienteDAO;
import dao.EnderecoDAO;
import dao.TelefoneDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import model.ClienteVO;
import model.EnderecoVO;
import model.TelefoneVO;

/**
 *
 * @author WILLIANDORNELESRODRI
 */
public class ClienteServicos {
    public void cadastrarCliente(ClienteVO cVO, long id_fone, long id_endereco) throws SQLException{
        ClienteDAO cDAO = DAOFactory.getClienteDAO();
        cDAO.cadastrarCliente(cVO, id_fone, id_endereco);
    }

    public ArrayList<ClienteVO> buscarCliente() throws SQLException {
        ClienteDAO cDAO = DAOFactory.getClienteDAO();
        return cDAO.buscarCliente();
    }
    
    public ArrayList<ClienteVO> filtrarCliente(String query) throws SQLException {
        ClienteDAO cDAO = DAOFactory.getClienteDAO();
        return cDAO.filtrarCliente(query);
    }

    public void deletarCliente(int id_cliente) throws SQLException {
        ClienteDAO cDAO = DAOFactory.getClienteDAO();

        // 1. Busca os IDs de telefone e endereço ANTES de deletar o cliente
        ClienteVO cliente = cDAO.buscarClientePorId(id_cliente);
        int id_fone = (int) cliente.getId_fone();
        int id_endereco = (int) cliente.getId_endereco();

        // 2. Deleta o cliente
        cDAO.deletarCliente(id_cliente);

        // 3. Deleta telefone e endereço
        TelefoneDAO tDAO = DAOFactory.getTelefoneDAO();
        tDAO.deletarTelefone(id_fone);

        EnderecoDAO eDAO = DAOFactory.getEnderecoDAO();
        eDAO.deletarEndereco(id_endereco);
    }
    
    public void confirmarAlteracao(
        ClienteVO cVO,
        TelefoneVO tVO,
        EnderecoVO eVO,
        int id_cliente
        ) throws SQLException {
            ClienteDAO cDAO = DAOFactory.getClienteDAO();

            // 1. Atualiza os dados do cliente (nome e email)
            cDAO.confirmarAlteracao(cVO, id_cliente);

            // 2. Busca os IDs atuais de telefone e endereço do cliente
            ClienteVO clienteAtual = cDAO.buscarClientePorId(id_cliente);
            int id_fone = (int) clienteAtual.getId_fone();
            int id_endereco = (int) clienteAtual.getId_endereco();

            // 3. Atualiza telefone e endereço
            TelefoneDAO tDAO = DAOFactory.getTelefoneDAO();
            tDAO.atualizarTelefone(tVO, id_fone);

            EnderecoDAO eDAO = DAOFactory.getEnderecoDAO();
            eDAO.atualizarEndereco(eVO, id_endereco);
        }
    public ClienteVO buscarClientePorId(int id_cliente) throws SQLException {
        ClienteDAO cDAO = DAOFactory.getClienteDAO();
        return cDAO.buscarClientePorId(id_cliente);
    }
}