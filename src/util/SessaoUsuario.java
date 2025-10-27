/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import model.LoginVO;

/**
 *
 * @author Felipe Remus
 */
public class SessaoUsuario {
    private static LoginVO usuarioLogado = null;
    
    // Método para definir o usuário logado
    public static void setUsuarioLogado(LoginVO usuario) {
        usuarioLogado = usuario;
    }
    
    // Método para obter o usuário logado
    public static LoginVO getUsuarioLogado() {
        return usuarioLogado;
    }
    
    // Método para verificar se há algum usuário logado
    public static boolean isUsuarioLogado() {
        return usuarioLogado != null && usuarioLogado.getIdLogin() > 0;
    }
    
    // Método para verificar se o usuário logado é admin (perfil ID = 3)
    public static boolean isAdministrador() {
        if (isUsuarioLogado()) {
            return usuarioLogado.getPerfil() == 3;
        }
        return false;
    }
    
    // Método para limpar a sessão (ex: ao sair do sistema)
    public static void limparSessao() {
        usuarioLogado = null;
    }
}
