/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author WILLIANDORNELESRODRI
 */
public class DAOFactory {
    
    
    private static LoginDAO loginDAO = new LoginDAO();
    
    public static LoginDAO getLoginDAO(){
        return loginDAO;
    }
    private static AutorDAO autorDAO = new AutorDAO();
    
    public static AutorDAO getAutorDAO(){
        return autorDAO;
    }
    private static GeneroDAO generoDAO = new GeneroDAO();
    
    public static GeneroDAO getGeneroDAO(){
        return generoDAO;
    }
    private static EditoraDAO editoraDAO = new EditoraDAO();
    
    public static EditoraDAO getEditoraDAO(){
        return editoraDAO;
    }
    private static LivroDAO  livroDAO = new LivroDAO();
    
    public static LivroDAO getLivroDAO() {
        return livroDAO;
    }
    
    private static ClienteDAO clienteDAO = new ClienteDAO();
    
    public static ClienteDAO getClienteDAO() {
        return clienteDAO;
    }
    
    private static TelefoneDAO telefoneDAO = new TelefoneDAO();
    
    public static TelefoneDAO getTelefoneDAO() {
        return telefoneDAO;
    }
    
    private static EnderecoDAO enderecoDAO = new EnderecoDAO();
    
    public static EnderecoDAO getEnderecoDAO() {
        return enderecoDAO;
    }
    
    private static FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    
    public static FuncionarioDAO getFuncionarioDAO() {
        return funcionarioDAO;
    }

    private static EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    
    public static EmprestimoDAO getEmprestimoDAO() {
        return emprestimoDAO;
    }
    
    private static ReservaDAO reservaDAO = new ReservaDAO();
    
    public static ReservaDAO getReservaDAO(){
        return reservaDAO;
    }
}
