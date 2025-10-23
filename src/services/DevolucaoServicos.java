/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.DevolucaoDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import model.DevolucaoVO;

/**
 *
 * @author Felipe Remus
 */
public class DevolucaoServicos {
    public ArrayList<DevolucaoVO> buscarTodasDevolucoes() throws SQLException {
        DevolucaoDAO dDAO = new DevolucaoDAO();
        return dDAO.buscarTodasDevolucoes();
    }

    public void deletarDevolucao(int idDevolucao) throws SQLException {
        DevolucaoDAO dDAO = new DevolucaoDAO();
        dDAO.deletarDevolucao(idDevolucao);
    }
    
    public ArrayList<DevolucaoVO> filtrarPorCliente(String nomeCliente) throws SQLException {
        DevolucaoDAO dDAO = new DevolucaoDAO();
        return dDAO.filtrarPorCliente(nomeCliente);
    }

    public ArrayList<DevolucaoVO> filtrarPorLivro(String nomeLivro) throws SQLException {
        DevolucaoDAO dDAO = new DevolucaoDAO();
        return dDAO.filtrarPorLivro(nomeLivro);
    }
}
