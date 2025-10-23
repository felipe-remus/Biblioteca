/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author WILLIANDORNELESRODRI
 */
public class ClienteVO {
    private int id_cliente, id_fone, id_endereco;
    private String nome_cliente;
    private String email_cliente;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_fone() {
        return id_fone;
    }

    public void setId_fone(int id_fone) {
        this.id_fone = id_fone;
    }

    public int getId_endereco() {
        return id_endereco;
    }

    public void setId_endereco(int id_endereco) {
        this.id_endereco = id_endereco;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public String getEmail_cliente() {
        return email_cliente;
    }

    public void setEmail_cliente(String email_cliente) {
        this.email_cliente = email_cliente;
    }
    
    @Override
    public String toString() {
        if (id_cliente == -1) {
            return "Selecione"; // para o item dummy
        }
        return nome_cliente; // para os clientes reais
    }
}


