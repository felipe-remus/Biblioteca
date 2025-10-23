/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author WILLIANDORNELESRODRI
 */
public class ServicosFactory {
    
    
    private static LoginServicos loginServicos = new LoginServicos();
    
    public static LoginServicos getLoginServicos(){
        return loginServicos;
    }
    private static AutorServicos autorServicos = new AutorServicos();
    
    public static AutorServicos getAutorServicos(){
        return autorServicos;
    }
    private static GeneroServicos generoServicos = new GeneroServicos();
    
    public static GeneroServicos getGeneroServicos(){
        return generoServicos;
    }
    private static EditoraServicos editoraServicos =new EditoraServicos();
    
    public static EditoraServicos getEditoraServicos(){
        return editoraServicos;
    }
    private static final LivroServicos lServicos = new LivroServicos();

    public static LivroServicos getLivroServicos() {
        return lServicos;
    }
    
    private static ClienteServicos clienteServicos = new ClienteServicos();
    
    public static ClienteServicos getClienteServicos(){
        return clienteServicos;
    }
    
    private static TelefoneServicos telefoneServicos = new TelefoneServicos();
    
    public static TelefoneServicos getTelefoneServicos(){
        return telefoneServicos;
    }

    private static EnderecoServicos endercoServicos = new EnderecoServicos();
    
    public static EnderecoServicos getEnderecoServicos(){
        return endercoServicos;
    }
    
    private static EmprestimoServicos emprestimoServicos = new EmprestimoServicos();
    
    public static EmprestimoServicos getEmprestimoServicos(){
        return emprestimoServicos;
    }   
    
    private static FuncionarioServicos funcionarioServicos = new FuncionarioServicos();
    
    public static FuncionarioServicos getFuncionarioServicos(){
        return funcionarioServicos;
    }
    
    private static DevolucaoServicos devolucaoServicos = new DevolucaoServicos();
    
    public static DevolucaoServicos getDevolucaoServicos(){
        return devolucaoServicos;
    }
    
    private static ReservaServicos reservaServicos = new ReservaServicos();
    
    public static ReservaServicos getReservaServicos(){
        return reservaServicos;
    }
}