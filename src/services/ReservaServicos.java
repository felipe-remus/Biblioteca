/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.DAOFactory;
import dao.ReservaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import model.ReservaVO;

/**
 *
 * @author WILLIANDORNELESRODRI
 */
public class ReservaServicos {
    public void cadastrarReserva(ReservaVO rVO) throws SQLException {
        ReservaDAO rDAO = DAOFactory.getReservaDAO();
        rDAO.cadastrarReserva(rVO);
    }

    public ArrayList<ReservaVO> buscarReservas() throws SQLException {
        ReservaDAO rDAO = DAOFactory.getReservaDAO();
        return rDAO.buscarReservas();
    }

    public ArrayList<ReservaVO> buscarReservasPorStatus(String status) throws SQLException {
        if (status == null || "Todos".equals(status)) {
            return buscarReservas();
        }
        return new ReservaDAO().buscarReservasPorStatus(status);
    }

    public ArrayList<ReservaVO> filtrarPorCliente(String nomeCliente, String status) throws SQLException {
        return new ReservaDAO().filtrarPorCliente(nomeCliente, status);
    }

    public ArrayList<ReservaVO> filtrarPorLivro(String nomeLivro, String status) throws SQLException {
        return new ReservaDAO().filtrarPorLivro(nomeLivro, status);
    }

    public ReservaVO buscarReservaPorId(int idReserva) throws SQLException {
        ReservaDAO rDAO = DAOFactory.getReservaDAO();
        return rDAO.buscarReservaPorId(idReserva);
    }

    public void atualizarReserva(ReservaVO rVO) throws SQLException {
        ReservaDAO rDAO = DAOFactory.getReservaDAO();
        rDAO.atualizarReserva(rVO);
    }

    public void atualizarStatusReserva(ReservaVO rVO) throws SQLException {
        DAOFactory.getReservaDAO().atualizarStatusReserva(rVO.getIdReserva(), rVO.getStatusReserva());
    }
}
