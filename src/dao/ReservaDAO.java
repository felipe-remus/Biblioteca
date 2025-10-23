/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import model.LivroVO;

import model.ReservaVO;
import persistencia.ConexaoBanco;
import services.LivroServicos;
import services.ServicosFactory;

/**
 *
 * @author WILLIANDORNELESRODRI
 */
public class ReservaDAO {

     public void cadastrarReserva(ReservaVO rVO) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "INSERT INTO reserva (dt_reserva, status_reserva, id_cliente, id_livro) VALUES (?, ?, ?, ?)";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setDate(1, new java.sql.Date(rVO.getDtReserva().getTime()));
            pstm.setString(2, "Ativa");
            pstm.setInt(3, rVO.getIdCliente());
            pstm.setInt(4, rVO.getIdLivro());
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }

    public ArrayList<ReservaVO> buscarReservas() throws SQLException {
        return buscarReservasPorStatus(null);
    }

    public ArrayList<ReservaVO> buscarReservasPorStatus(String status) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            StringBuilder sql = new StringBuilder("""
                SELECT 
                    r.id_reserva,
                    r.dt_reserva,
                    r.status_reserva,
                    r.id_cliente,
                    r.id_livro,
                    c.nome_cliente,
                    l.nome_livro
                FROM reserva r
                INNER JOIN cliente c ON r.id_cliente = c.id_cliente
                INNER JOIN livro l ON r.id_livro = l.id_livro
                """);

            if (status != null && !status.equalsIgnoreCase("Todos")) {
                sql.append(" WHERE r.status_reserva = ?");
            }

            PreparedStatement pstm = con.prepareStatement(sql.toString());
            if (status != null && !status.equalsIgnoreCase("Todos")) {
                pstm.setString(1, status);
            }

            ResultSet rs = pstm.executeQuery();
            ArrayList<ReservaVO> reservas = new ArrayList<>();
            while (rs.next()) {
                ReservaVO rVO = new ReservaVO();
                rVO.setIdReserva(rs.getInt("id_reserva"));
                rVO.setDtReserva(rs.getDate("dt_reserva"));
                rVO.setStatusReserva(rs.getString("status_reserva"));
                rVO.setIdCliente(rs.getInt("id_cliente"));
                rVO.setIdLivro(rs.getInt("id_livro"));
                rVO.setNomeCliente(rs.getString("nome_cliente"));
                rVO.setNomeLivro(rs.getString("nome_livro"));
                reservas.add(rVO);
            }
            return reservas;
        } finally {
            con.close();
        }
    }

    public ArrayList<ReservaVO> filtrarPorCliente(String nomeCliente, String status) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            StringBuilder sql = new StringBuilder("""
                SELECT 
                    r.id_reserva,
                    r.dt_reserva,
                    r.status_reserva,
                    r.id_cliente,
                    r.id_livro,
                    c.nome_cliente,
                    l.nome_livro
                FROM reserva r
                INNER JOIN cliente c ON r.id_cliente = c.id_cliente
                INNER JOIN livro l ON r.id_livro = l.id_livro
                WHERE c.nome_cliente LIKE ?
                """);

            if (status != null) {
                sql.append(" AND r.status_reserva = ?");
            }

            PreparedStatement pstm = con.prepareStatement(sql.toString());
            pstm.setString(1, "%" + nomeCliente + "%");
            if (status != null) {
                pstm.setString(2, status);
            }

            ResultSet rs = pstm.executeQuery();
            ArrayList<ReservaVO> reservas = new ArrayList<>();
            while (rs.next()) {
                ReservaVO rVO = new ReservaVO();
                rVO.setIdReserva(rs.getInt("id_reserva"));
                rVO.setDtReserva(rs.getDate("dt_reserva"));
                rVO.setStatusReserva(rs.getString("status_reserva"));
                rVO.setIdCliente(rs.getInt("id_cliente"));
                rVO.setIdLivro(rs.getInt("id_livro"));
                rVO.setNomeCliente(rs.getString("nome_cliente"));
                rVO.setNomeLivro(rs.getString("nome_livro"));
                reservas.add(rVO);
            }
            return reservas;
        } finally {
            con.close();
        }
    }

    public ArrayList<ReservaVO> filtrarPorLivro(String nomeLivro, String status) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            StringBuilder sql = new StringBuilder("""
                SELECT 
                    r.id_reserva,
                    r.dt_reserva,
                    r.status_reserva,
                    r.id_cliente,
                    r.id_livro,
                    c.nome_cliente,
                    l.nome_livro
                FROM reserva r
                INNER JOIN cliente c ON r.id_cliente = c.id_cliente
                INNER JOIN livro l ON r.id_livro = l.id_livro
                WHERE l.nome_livro LIKE ?
                """);

            if (status != null) {
                sql.append(" AND r.status_reserva = ?");
            }

            PreparedStatement pstm = con.prepareStatement(sql.toString());
            pstm.setString(1, "%" + nomeLivro + "%");
            if (status != null) {
                pstm.setString(2, status);
            }

            ResultSet rs = pstm.executeQuery();
            ArrayList<ReservaVO> reservas = new ArrayList<>();
            while (rs.next()) {
                ReservaVO rVO = new ReservaVO();
                rVO.setIdReserva(rs.getInt("id_reserva"));
                rVO.setDtReserva(rs.getDate("dt_reserva"));
                rVO.setStatusReserva(rs.getString("status_reserva"));
                rVO.setIdCliente(rs.getInt("id_cliente"));
                rVO.setIdLivro(rs.getInt("id_livro"));
                rVO.setNomeCliente(rs.getString("nome_cliente"));
                rVO.setNomeLivro(rs.getString("nome_livro"));
                reservas.add(rVO);
            }
            return reservas;
        } finally {
            con.close();
        }
    }

    public ReservaVO buscarReservaPorId(int idReserva) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = """
                SELECT 
                    r.id_reserva,
                    r.dt_reserva,
                    r.status_reserva,
                    r.id_cliente,
                    r.id_livro,
                    c.nome_cliente,
                    l.nome_livro
                FROM reserva r
                INNER JOIN cliente c ON r.id_cliente = c.id_cliente
                INNER JOIN livro l ON r.id_livro = l.id_livro
                WHERE r.id_reserva = ?
                """;
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idReserva);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                ReservaVO rVO = new ReservaVO();
                rVO.setIdReserva(rs.getInt("id_reserva"));
                rVO.setDtReserva(rs.getDate("dt_reserva"));
                rVO.setStatusReserva(rs.getString("status_reserva"));
                rVO.setIdCliente(rs.getInt("id_cliente"));
                rVO.setIdLivro(rs.getInt("id_livro"));
                rVO.setNomeCliente(rs.getString("nome_cliente"));
                rVO.setNomeLivro(rs.getString("nome_livro"));
                return rVO;
            }
            throw new SQLException("Reserva não encontrada.");
        } finally {
            con.close();
        }
    }

    public void atualizarReserva(ReservaVO rVO) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "UPDATE reserva SET dt_reserva = ?, id_cliente = ?, id_livro = ? WHERE id_reserva = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setDate(1, new java.sql.Date(rVO.getDtReserva().getTime()));
            pstm.setInt(2, rVO.getIdCliente());
            pstm.setInt(3, rVO.getIdLivro());
            pstm.setInt(4, rVO.getIdReserva());
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    
    public void deletarReserva(int idReserva) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "DELETE FROM reserva WHERE id_reserva = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idReserva);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }

    public void atualizarStatusReserva(int idReserva, String novoStatus) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "UPDATE reserva SET status_reserva = ? WHERE id_reserva = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, novoStatus);
            pstm.setInt(2, idReserva);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
}
