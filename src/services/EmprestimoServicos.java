/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.DAOFactory;
import dao.EmprestimoDAO;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import model.EmprestimoVO;
/**
 *
 * @author FELIPEREMUSDEALMEIDA
 */
public class EmprestimoServicos {

    public void cadastrarEmprestimo(EmprestimoVO eVO) throws SQLException {
        EmprestimoDAO eDAO = DAOFactory.getEmprestimoDAO();
        eDAO.cadastrarEmprestimo(eVO);
    }

    public ArrayList<EmprestimoVO> buscarEmprestimos() throws SQLException {
        EmprestimoDAO eDAO = DAOFactory.getEmprestimoDAO();
        return eDAO.buscarEmprestimos();
    }

    public ArrayList<EmprestimoVO> buscarEmprestimosPorStatus(String status) throws SQLException {
        if (status == null) {
            return buscarEmprestimos();
        }
        return new EmprestimoDAO().buscarEmprestimosPorStatus(status);
    }

    public ArrayList<EmprestimoVO> filtrarEmprestimos(String nomeCliente, String nomeLivro, String status) throws SQLException {
        EmprestimoDAO eDAO = DAOFactory.getEmprestimoDAO();
        return eDAO.filtrarEmprestimos(nomeCliente, nomeLivro, status);
    }

    public void devolverLivro(int idEmprestimo, java.util.Date dtDevolucao, double multa) throws SQLException {
        EmprestimoDAO eDAO = new EmprestimoDAO();

        eDAO.devolverLivro(idEmprestimo);

        eDAO.registrarDevolucao(idEmprestimo, (Date) dtDevolucao, multa);
    }
    
    public ArrayList<EmprestimoVO> filtrarPorCliente(String nomeCliente, String status) throws SQLException {
        return new EmprestimoDAO().filtrarEmprestimos(nomeCliente, null, status);
    }

    public ArrayList<EmprestimoVO> filtrarPorLivro(String nomeLivro, String status) throws SQLException {
        return new EmprestimoDAO().filtrarEmprestimos(null, nomeLivro, status);
    }
    
    public void deletarEmprestimo(int idEmprestimo) throws SQLException {
    EmprestimoDAO eDAO = DAOFactory.getEmprestimoDAO();
    eDAO.deletarEmprestimo(idEmprestimo);
}

    public EmprestimoVO buscarEmprestimoPorId(int idEmprestimo) throws SQLException {
        EmprestimoDAO eDAO = DAOFactory.getEmprestimoDAO();
        return eDAO.buscarEmprestimoPorId(idEmprestimo);
    }

    public void atualizarEmprestimo(EmprestimoVO eVO) throws SQLException {
        EmprestimoDAO eDAO = DAOFactory.getEmprestimoDAO();
        eDAO.atualizarEmprestimo(eVO);
    }
    
    public double calcularMulta(Date dtPrazo, Date dtDevolucao) {
        if (dtDevolucao == null || dtPrazo == null) {
            return 0.0;
        }

        long diffMillis = dtDevolucao.getTime() - dtPrazo.getTime();
        long diffDays = diffMillis / (24 * 60 * 60 * 1000);

        if (diffDays <= 0) {
            return 0.0;
        }
        return 2.0 + Math.max(0, diffDays - 1) * 1.0;
    }
}