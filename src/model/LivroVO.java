/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author WILLIAN
 */
public class LivroVO {
    private int idLivro, quantidade;
    private String nomeLivro;
    private Date dtLancamento;
    private String isbn;
    private String edicao;
    private int idEditora;
    private String nomeEditora;
    private List<String> nomesGeneros = new ArrayList<>();

    public List<String> getNomesGeneros() {
        return nomesGeneros;
    }

    public void setNomesGeneros(List<String> nomesGeneros) {
        this.nomesGeneros = nomesGeneros;
    }
    
    private List<String> nomesAutores = new ArrayList<>();
    private List<Integer> autoresIds = new ArrayList<>();  
    private List<Integer> generosIds = new ArrayList<>();

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public Date getDtLancamento() {
        return dtLancamento;
    }

    public void setDtLancamento(Date dtLancamento) {
        this.dtLancamento = dtLancamento;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public int getIdEditora() {
        return idEditora;
    }

    public void setIdEditora(int idEditora) {
        this.idEditora = idEditora;
    }

    public String getNomeEditora() {
        return nomeEditora;
    }

    public void setNomeEditora(String nomeEditora) {
        this.nomeEditora = nomeEditora;
    }

    public List<String> getNomesAutores() {
        return nomesAutores;
    }

    public void setNomesAutores(List<String> nomesAutores) {
        this.nomesAutores = nomesAutores;
    }

    public List<Integer> getAutoresIds() {
        return autoresIds;
    }

    public void setAutoresIds(List<Integer> autoresIds) {
        this.autoresIds = autoresIds;
    }

    public List<Integer> getGenerosIds() {
        return generosIds;
    }

    public void setGenerosIds(List<Integer> generosIds) {
        this.generosIds = generosIds;
    }
    
    @Override
    public String toString() {
        if (edicao != null && !edicao.trim().isEmpty()) {
            return nomeLivro + " (ed. " + edicao + ")";
        }
        return nomeLivro;
    }
}
