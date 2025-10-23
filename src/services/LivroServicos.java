/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.DAOFactory;
import dao.LivroDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.LivroVO;

/**
 *
 * @author WILLIAN
 */
public class LivroServicos {
    public void cadastrarLivro(LivroVO lVO) throws SQLException {
        LivroDAO lDAO = DAOFactory.getLivroDAO();
        lDAO.cadastrarLivro(lVO);
    }

    public ArrayList<LivroVO> buscarLivros() throws SQLException {
        LivroDAO lDAO = DAOFactory.getLivroDAO();
        return lDAO.buscarLivros();
    }

    public LivroVO buscarLivroPorId(int idLivro) throws SQLException {
        LivroDAO lDAO = DAOFactory.getLivroDAO();
        return lDAO.buscarLivroPorId(idLivro);
    }

    public void atualizarLivro(LivroVO lVO) throws SQLException {
        LivroDAO lDAO = DAOFactory.getLivroDAO();
        lDAO.atualizarLivro(lVO);
    }

    public void deletarLivro(int idLivro) throws SQLException {
        LivroDAO lDAO = DAOFactory.getLivroDAO();
        lDAO.deletarLivro(idLivro);
    }

    public ArrayList<LivroVO> filtrarLivros(String nome) throws SQLException {
        LivroDAO lDAO = DAOFactory.getLivroDAO();
        return lDAO.filtrarLivros(nome);
    }
    
    public ArrayList<LivroVO> buscarLivrosComFiltro(String nome, String disponibilidade) throws SQLException {
        LivroDAO lDAO = DAOFactory.getLivroDAO();
        return lDAO.buscarLivrosComFiltro(nome, disponibilidade);
    }
    
    public void atualizarAutoresDoLivro(int idLivro, List<Integer> autores) throws SQLException {
    LivroDAO lDAO = DAOFactory.getLivroDAO();
    lDAO.atualizarAutoresDoLivro(idLivro, autores);
}

    public void atualizarGenerosDoLivro(int idLivro, List<Integer> generos) throws SQLException {
        LivroDAO lDAO = DAOFactory.getLivroDAO();
        lDAO.atualizarGenerosDoLivro(idLivro, generos);
    }
    
    public ArrayList<LivroVO> filtrarLivrosPorNomeEEdicao(String termo) throws SQLException {
        LivroDAO lDAO = DAOFactory.getLivroDAO();
        return lDAO.filtrarLivrosPorNomeEEdicao(termo);
    }
    
    public ArrayList<LivroVO> filtrarLivrosAvancado(String termo, String tipoFiltro, String disponibilidade) throws SQLException {
        LivroDAO lDAO = DAOFactory.getLivroDAO();
        return lDAO.filtrarLivrosAvancado(termo, tipoFiltro, disponibilidade);
    }
}
