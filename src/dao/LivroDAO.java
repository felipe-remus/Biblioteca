 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.LivroVO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import persistencia.ConexaoBanco;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author WILLIAN
 */

public class LivroDAO {
    public void cadastrarLivro(LivroVO lVO) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            con.setAutoCommit(false);

            String sqlLivro = """
            INSERT INTO livro (nome_livro, dt_lancamento, isbn, edicao, quantidade, id_editora)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        PreparedStatement pstm = con.prepareStatement(sqlLivro, Statement.RETURN_GENERATED_KEYS);
        
        pstm.setString(1, lVO.getNomeLivro());
        pstm.setDate(2, new java.sql.Date(lVO.getDtLancamento().getTime()));
        pstm.setString(3, lVO.getIsbn());
        pstm.setString(4, lVO.getEdicao());     
        pstm.setInt(5, lVO.getQuantidade());   
        pstm.setInt(6, lVO.getIdEditora());

        pstm.executeUpdate();

            ResultSet rs = pstm.getGeneratedKeys();
            int idLivro = 0;
            if (rs.next()) {
                idLivro = rs.getInt(1);
            } else {
                throw new SQLException("Falha ao obter ID do livro.");
            }

            // Inserir autores (livro_autor)
            if (lVO.getAutoresIds() != null && !lVO.getAutoresIds().isEmpty()) {
                String sqlAutor = "INSERT INTO livro_autor (id_livro, id_autor) VALUES (?, ?)";
                PreparedStatement pstmAutor = con.prepareStatement(sqlAutor);
                for (Integer idAutor : lVO.getAutoresIds()) {
                    pstmAutor.setInt(1, idLivro);
                    pstmAutor.setInt(2, idAutor);
                    pstmAutor.addBatch();
                }
                pstmAutor.executeBatch();
            }

            // Inserir gêneros (livro_genero)
            if (lVO.getGenerosIds() != null && !lVO.getGenerosIds().isEmpty()) {
                String sqlGenero = "INSERT INTO livro_genero (id_livro, id_genero) VALUES (?, ?)";
                PreparedStatement pstmGenero = con.prepareStatement(sqlGenero);
                for (Integer idGenero : lVO.getGenerosIds()) {
                    pstmGenero.setInt(1, idLivro);
                    pstmGenero.setInt(2, idGenero);
                    pstmGenero.addBatch();
                }
                pstmGenero.executeBatch();
            }

            con.commit();

        } catch (SQLException e) {
            con.rollback();
            throw new SQLException("Erro ao cadastrar livro: " + e.getMessage());
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }

    public ArrayList<LivroVO> buscarLivros() throws SQLException {
    Connection con = new ConexaoBanco().getConexao();
    try {
        String sql = """
            SELECT 
                l.id_livro,
                l.nome_livro,
                l.dt_lancamento,
                l.isbn,
                l.edicao,          
                l.quantidade,      
                e.nome_editora,
                GROUP_CONCAT(DISTINCT CONCAT(a.nome_autor, ' ', a.sobrenome_autor) SEPARATOR ', ') AS autores,
                GROUP_CONCAT(DISTINCT g.nome_genero SEPARATOR ', ') AS generos
                FROM livro l
                INNER JOIN editora e ON l.id_editora = e.id_editora
                LEFT JOIN livro_autor la ON l.id_livro = la.id_livro
                LEFT JOIN autor a ON la.id_autor = a.id_autor
                LEFT JOIN livro_genero lg ON l.id_livro = lg.id_livro
                LEFT JOIN genero g ON lg.id_genero = g.id_genero
                GROUP BY l.id_livro, l.nome_livro, l.dt_lancamento, l.isbn, l.edicao, l.quantidade, e.nome_editora
                ORDER BY l.nome_livro
                """;
            PreparedStatement pstm = con.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            ArrayList<LivroVO> livros = new ArrayList<>();
            while (rs.next()) {
                LivroVO lVO = new LivroVO();
                lVO.setIdLivro(rs.getInt("id_livro"));
                lVO.setNomeLivro(rs.getString("nome_livro"));
                lVO.setDtLancamento(rs.getDate("dt_lancamento"));
                lVO.setIsbn(rs.getString("isbn"));
                lVO.setEdicao(rs.getString("edicao"));        // ✅
                lVO.setQuantidade(rs.getInt("quantidade"));   // ✅
                lVO.setNomeEditora(rs.getString("nome_editora"));

                // Autores e gêneros (mesmo código)
                String autoresStr = rs.getString("autores");
                if (autoresStr != null) {
                    lVO.setNomesAutores(List.of(autoresStr.split(", ")));
                }
                String generosStr = rs.getString("generos");
                if (generosStr != null) {
                    lVO.setNomesGeneros(List.of(generosStr.split(", ")));
                }

                livros.add(lVO);
            }
            return livros;
        } finally {
            con.close();
        }
    }
    
    public LivroVO buscarLivroPorId(int idLivro) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = """
            SELECT 
                l.id_livro, l.nome_livro, l.dt_lancamento, l.isbn, l.edicao, l.quantidade, l.id_editora,
                e.nome_editora
                FROM livro l
                INNER JOIN editora e ON l.id_editora = e.id_editora
                WHERE l.id_livro = ?
                """;
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idLivro);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                LivroVO lVO = new LivroVO();
                lVO.setIdLivro(rs.getInt("id_livro"));
                lVO.setNomeLivro(rs.getString("nome_livro"));
                lVO.setDtLancamento(rs.getDate("dt_lancamento"));
                lVO.setIsbn(rs.getString("isbn"));
                lVO.setEdicao(rs.getString("edicao"));        
                lVO.setQuantidade(rs.getInt("quantidade"));                   lVO.setIdEditora(rs.getInt("id_editora"));
                lVO.setNomeEditora(rs.getString("nome_editora"));

                // Buscar autores do livro
                List<Integer> autoresIds = new ArrayList<>();
                String sqlAutores = "SELECT id_autor FROM livro_autor WHERE id_livro = ?";
                PreparedStatement pstmA = con.prepareStatement(sqlAutores);
                pstmA.setInt(1, idLivro);
                ResultSet rsA = pstmA.executeQuery();
                while (rsA.next()) {
                    autoresIds.add(rsA.getInt("id_autor"));
                }
                lVO.setAutoresIds(autoresIds);

                // Buscar gêneros do livro
                List<Integer> generosIds = new ArrayList<>();
                String sqlGeneros = "SELECT id_genero FROM livro_genero WHERE id_livro = ?";
                PreparedStatement pstmG = con.prepareStatement(sqlGeneros);
                pstmG.setInt(1, idLivro);
                ResultSet rsG = pstmG.executeQuery();
                while (rsG.next()) {
                    generosIds.add(rsG.getInt("id_genero"));
                }
                lVO.setGenerosIds(generosIds);

                return lVO;
            }
            throw new SQLException("Livro não encontrado.");
        } finally {
            con.close();
        }
    }

    public void atualizarLivro(LivroVO lVO) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = """
                UPDATE livro 
                SET nome_livro = ?, dt_lancamento = ?, isbn = ?, edicao = ?, quantidade = ?, id_editora = ?
                WHERE id_livro = ?
                """;
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, lVO.getNomeLivro());
            pstm.setDate(2, new java.sql.Date(lVO.getDtLancamento().getTime()));
            pstm.setString(3, lVO.getIsbn());
            pstm.setString(4, lVO.getEdicao());   
            pstm.setInt(5, lVO.getQuantidade());    
            pstm.setInt(6, lVO.getIdEditora());
            pstm.setInt(7, lVO.getIdLivro());
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }
    
    public void deletarLivro(int idLivro) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = "DELETE FROM livro WHERE id_livro = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setInt(1, idLivro);
            pstm.executeUpdate();
        } finally {
            con.close();
        }
    }

    public ArrayList<LivroVO> filtrarLivros(String nome) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String sql = """
                SELECT 
                    l.id_livro,
                    l.nome_livro,
                    l.dt_lancamento,
                    l.isbn,
                    l.edicao,          
                    l.quantidade,      
                    e.nome_editora,
                    GROUP_CONCAT(DISTINCT CONCAT(a.nome_autor, ' ', a.sobrenome_autor) SEPARATOR ', ') AS autores,
                    GROUP_CONCAT(DISTINCT g.nome_genero SEPARATOR ', ') AS generos
                FROM livro l
                INNER JOIN editora e ON l.id_editora = e.id_editora
                LEFT JOIN livro_autor la ON l.id_livro = la.id_livro
                LEFT JOIN autor a ON la.id_autor = a.id_autor
                LEFT JOIN livro_genero lg ON l.id_livro = lg.id_livro
                LEFT JOIN genero g ON lg.id_genero = g.id_genero
                WHERE l.nome_livro LIKE ?
                GROUP BY 
                    l.id_livro, 
                    l.nome_livro, 
                    l.dt_lancamento, 
                    l.isbn, 
                    l.edicao,          
                    l.quantidade,      
                    e.nome_editora
                ORDER BY l.nome_livro
                """;
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, "%" + nome + "%");
                ResultSet rs = pstm.executeQuery();

                ArrayList<LivroVO> livros = new ArrayList<>();
                while (rs.next()) {
                    LivroVO lVO = new LivroVO();
                    lVO.setIdLivro(rs.getInt("id_livro"));
                    lVO.setNomeLivro(rs.getString("nome_livro"));
                    lVO.setDtLancamento(rs.getDate("dt_lancamento"));
                    lVO.setIsbn(rs.getString("isbn"));
                    lVO.setEdicao(rs.getString("edicao"));                    
                    lVO.setQuantidade(rs.getInt("quantidade"));   
                    lVO.setNomeEditora(rs.getString("nome_editora"));

                    String autoresStr = rs.getString("autores");
                    if (autoresStr != null) {
                        lVO.setNomesAutores(List.of(autoresStr.split(", ")));
                    }

                    String generosStr = rs.getString("generos");
                    if (generosStr != null) {
                        lVO.setNomesGeneros(List.of(generosStr.split(", ")));
                    }

                    livros.add(lVO);
                }
                return livros;
            } finally {
                con.close();
            }
        }

    public ArrayList<LivroVO> buscarLivrosComFiltro(String nome, String disponibilidade) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            StringBuilder sql = new StringBuilder("""
                SELECT 
                    l.id_livro,
                    l.nome_livro,
                    l.dt_lancamento,
                    l.isbn,
                    l.edicao,
                    l.quantidade,
                    e.nome_editora,
                    COALESCE(emp.emprestados, 0) AS emprestados,
                    GROUP_CONCAT(DISTINCT CONCAT(a.nome_autor, ' ', a.sobrenome_autor) SEPARATOR ', ') AS autores,
                    GROUP_CONCAT(DISTINCT g.nome_genero SEPARATOR ', ') AS generos
                FROM livro l
                INNER JOIN editora e ON l.id_editora = e.id_editora
                LEFT JOIN livro_autor la ON l.id_livro = la.id_livro
                LEFT JOIN autor a ON la.id_autor = a.id_autor
                LEFT JOIN livro_genero lg ON l.id_livro = lg.id_livro
                LEFT JOIN genero g ON lg.id_genero = g.id_genero
                LEFT JOIN (
                    SELECT id_livro, COUNT(*) AS emprestados
                    FROM emprestimo
                    WHERE status = 'Nao Devolvido'
                    GROUP BY id_livro
                ) emp ON l.id_livro = emp.id_livro
                WHERE 1=1
                """);

            // Filtro por nome (opcional)
            if (nome != null && !nome.trim().isEmpty()) {
                sql.append(" AND l.nome_livro LIKE ?");
            }

            // Filtro por disponibilidade
            if ("Disponível".equals(disponibilidade)) {
                sql.append(" AND l.quantidade > COALESCE(emp.emprestados, 0)");
            } else if ("Indisponível".equals(disponibilidade)) {
                sql.append(" AND l.quantidade = COALESCE(emp.emprestados, 0)");
            }

            sql.append("""
                GROUP BY 
                    l.id_livro, 
                    l.nome_livro, 
                    l.dt_lancamento, 
                    l.isbn, 
                    l.edicao, 
                    l.quantidade, 
                    e.nome_editora,
                    emp.emprestados
                ORDER BY l.nome_livro
                """);

            PreparedStatement pstm = con.prepareStatement(sql.toString());

            // Define parâmetros
            int paramIndex = 1;
            if (nome != null && !nome.trim().isEmpty()) {
                pstm.setString(paramIndex++, "%" + nome.trim() + "%");
            }

            ResultSet rs = pstm.executeQuery();

            ArrayList<LivroVO> livros = new ArrayList<>();
            while (rs.next()) {
                LivroVO lVO = new LivroVO();
                lVO.setIdLivro(rs.getInt("id_livro"));
                lVO.setNomeLivro(rs.getString("nome_livro"));
                lVO.setDtLancamento(rs.getDate("dt_lancamento"));
                lVO.setIsbn(rs.getString("isbn"));
                lVO.setEdicao(rs.getString("edicao"));
                lVO.setQuantidade(rs.getInt("quantidade"));
                lVO.setNomeEditora(rs.getString("nome_editora"));

                // Autores
                String autoresStr = rs.getString("autores");
                if (autoresStr != null) {
                    lVO.setNomesAutores(List.of(autoresStr.split(", ")));
                }

                // Gêneros
                String generosStr = rs.getString("generos");
                if (generosStr != null) {
                    lVO.setNomesGeneros(List.of(generosStr.split(", ")));
                }

                livros.add(lVO);
            }
            return livros;
        } finally {
            con.close();
        }
    }
    
    public ArrayList<LivroVO> filtrarLivrosAvancado(String termo, String tipoFiltro, String disponibilidade) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            StringBuilder sql = new StringBuilder("""
                SELECT 
                    l.id_livro,
                    l.nome_livro,
                    l.dt_lancamento,
                    l.isbn,
                    l.edicao,
                    l.quantidade,
                    e.nome_editora,
                    COALESCE(emp.emprestados, 0) AS emprestados,
                    GROUP_CONCAT(DISTINCT CONCAT(a.nome_autor, ' ', a.sobrenome_autor) SEPARATOR ', ') AS autores,
                    GROUP_CONCAT(DISTINCT g.nome_genero SEPARATOR ', ') AS generos
                FROM livro l
                INNER JOIN editora e ON l.id_editora = e.id_editora
                LEFT JOIN livro_autor la ON l.id_livro = la.id_livro
                LEFT JOIN autor a ON la.id_autor = a.id_autor
                LEFT JOIN livro_genero lg ON l.id_livro = lg.id_livro
                LEFT JOIN genero g ON lg.id_genero = g.id_genero
                LEFT JOIN (
                    SELECT id_livro, COUNT(*) AS emprestados
                    FROM emprestimo
                    WHERE status = 'Nao Devolvido'
                    GROUP BY id_livro
                ) emp ON l.id_livro = emp.id_livro
                WHERE 1=1
                """);

            // Adicionar filtro de pesquisa conforme o tipo
            if (termo != null && !termo.trim().isEmpty()) {
                switch (tipoFiltro) {
                    case "Nome" -> sql.append(" AND l.nome_livro LIKE ?");
                    case "Editora" -> sql.append(" AND e.nome_editora LIKE ?");
                    case "Gênero" -> sql.append(" AND g.nome_genero LIKE ?");
                    case "Autor" -> sql.append(" AND (a.nome_autor LIKE ? OR a.sobrenome_autor LIKE ?)");
                    default -> sql.append(" AND l.nome_livro LIKE ?");
                }
            }

            // Filtro por disponibilidade
            if ("Disponível".equals(disponibilidade)) {
                sql.append(" AND l.quantidade > COALESCE(emp.emprestados, 0)");
            } else if ("Indisponível".equals(disponibilidade)) {
                sql.append(" AND l.quantidade = COALESCE(emp.emprestados, 0)");
            }

            sql.append("""
                GROUP BY 
                    l.id_livro, 
                    l.nome_livro, 
                    l.dt_lancamento, 
                    l.isbn, 
                    l.edicao, 
                    l.quantidade, 
                    e.nome_editora,
                    emp.emprestados
                ORDER BY l.nome_livro
                """);

            PreparedStatement pstm = con.prepareStatement(sql.toString());
            int paramIndex = 1;

            // Definir parâmetros de pesquisa
            if (termo != null && !termo.trim().isEmpty()) {
                String termoLike = termo.trim() + "%";
                switch (tipoFiltro) {
                    case "Autor":
                        // Para autor, precisamos de 2 parâmetros (nome e sobrenome)
                        pstm.setString(paramIndex++, termoLike);
                        pstm.setString(paramIndex++, termoLike);
                        break;
                    default:
                        pstm.setString(paramIndex++, termoLike);
                        break;
                }
            }

            ResultSet rs = pstm.executeQuery();

            ArrayList<LivroVO> livros = new ArrayList<>();
            while (rs.next()) {
                LivroVO lVO = new LivroVO();
                lVO.setIdLivro(rs.getInt("id_livro"));
                lVO.setNomeLivro(rs.getString("nome_livro"));
                lVO.setDtLancamento(rs.getDate("dt_lancamento"));
                lVO.setIsbn(rs.getString("isbn"));
                lVO.setEdicao(rs.getString("edicao"));
                lVO.setQuantidade(rs.getInt("quantidade"));
                lVO.setNomeEditora(rs.getString("nome_editora"));

                String autoresStr = rs.getString("autores");
                if (autoresStr != null) {
                    lVO.setNomesAutores(List.of(autoresStr.split(", ")));
                }

                String generosStr = rs.getString("generos");
                if (generosStr != null) {
                    lVO.setNomesGeneros(List.of(generosStr.split(", ")));
                }

                livros.add(lVO);
            }
            return livros;
        } finally {
            con.close();
        }
    }
    
    public void atualizarAutoresDoLivro(int idLivro, List<Integer> novosAutores) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            con.setAutoCommit(false);

            String deleteSql = "DELETE FROM livro_autor WHERE id_livro = ?";
            PreparedStatement delPstm = con.prepareStatement(deleteSql);
            delPstm.setInt(1, idLivro);
            delPstm.executeUpdate();

            if (novosAutores != null && !novosAutores.isEmpty()) {
                String insertSql = "INSERT INTO livro_autor (id_livro, id_autor) VALUES (?, ?)";
                PreparedStatement insPstm = con.prepareStatement(insertSql);
                for (Integer idAutor : novosAutores) {
                    insPstm.setInt(1, idLivro);
                    insPstm.setInt(2, idAutor);
                    insPstm.addBatch();
                }
                insPstm.executeBatch();
            }

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }
    
    public void atualizarGenerosDoLivro(int idLivro, List<Integer> novosGeneros) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            con.setAutoCommit(false);

            String deleteSql = "DELETE FROM livro_genero WHERE id_livro = ?";
            PreparedStatement deletePstm = con.prepareStatement(deleteSql);
            deletePstm.setInt(1, idLivro);
            deletePstm.executeUpdate();

            if (novosGeneros != null && !novosGeneros.isEmpty()) {
                String insertSql = "INSERT INTO livro_genero (id_livro, id_genero) VALUES (?, ?)";
                PreparedStatement insertPstm = con.prepareStatement(insertSql);

                for (Integer idGenero : novosGeneros) {
                    if (idGenero != null && idGenero > 0) {
                        insertPstm.setInt(1, idLivro);
                        insertPstm.setInt(2, idGenero);
                        insertPstm.addBatch();
                    }
                }
                insertPstm.executeBatch();
            }

            con.commit();

        } catch (SQLException e) {
            con.rollback();
            throw new SQLException("Erro ao atualizar gêneros do livro: " + e.getMessage());
        } finally {
            con.setAutoCommit(true);
            con.close();
        }
    }

    public ArrayList<LivroVO> filtrarLivrosPorNomeEEdicao(String termoCompleto) throws SQLException {
        Connection con = new ConexaoBanco().getConexao();
        try {
            String[] partes = termoCompleto.trim().split("\\s+", 2);

            String nomeParte = partes[0];
            String edicaoParte = partes.length > 1 ? partes[1] : null;

            StringBuilder sql = new StringBuilder("""
                SELECT 
                    l.id_livro,
                    l.nome_livro,
                    l.dt_lancamento,
                    l.isbn,
                    l.edicao,
                    l.quantidade,
                    e.nome_editora,
                    GROUP_CONCAT(DISTINCT CONCAT(a.nome_autor, ' ', a.sobrenome_autor) SEPARATOR ', ') AS autores,
                    GROUP_CONCAT(DISTINCT g.nome_genero SEPARATOR ', ') AS generos
                FROM livro l
                INNER JOIN editora e ON l.id_editora = e.id_editora
                LEFT JOIN livro_autor la ON l.id_livro = la.id_livro
                LEFT JOIN autor a ON la.id_autor = a.id_autor
                LEFT JOIN livro_genero lg ON l.id_livro = lg.id_livro
                LEFT JOIN genero g ON lg.id_genero = g.id_genero
                WHERE l.nome_livro LIKE ?
                """);

            // Se houver segunda parte, adiciona filtro de edição
            if (edicaoParte != null && !edicaoParte.isEmpty()) {
                sql.append(" AND l.edicao LIKE ?");
            }

            sql.append("""
                GROUP BY 
                    l.id_livro, l.nome_livro, l.dt_lancamento, l.isbn, l.edicao, l.quantidade, e.nome_editora
                ORDER BY l.nome_livro
                """);

            PreparedStatement pstm = con.prepareStatement(sql.toString());
            pstm.setString(1, "%" + nomeParte + "%");

            if (edicaoParte != null && !edicaoParte.isEmpty()) {
                pstm.setString(2, edicaoParte + "%"); // começa com...
            }

            ResultSet rs = pstm.executeQuery();

            ArrayList<LivroVO> livros = new ArrayList<>();
            while (rs.next()) {
                LivroVO lVO = new LivroVO();
                lVO.setIdLivro(rs.getInt("id_livro"));
                lVO.setNomeLivro(rs.getString("nome_livro"));
                lVO.setDtLancamento(rs.getDate("dt_lancamento"));
                lVO.setIsbn(rs.getString("isbn"));
                lVO.setEdicao(rs.getString("edicao"));
                lVO.setQuantidade(rs.getInt("quantidade"));
                lVO.setNomeEditora(rs.getString("nome_editora"));

                String autoresStr = rs.getString("autores");
                if (autoresStr != null) {
                    lVO.setNomesAutores(List.of(autoresStr.split(", ")));
                }

                String generosStr = rs.getString("generos");
                if (generosStr != null) {
                    lVO.setNomesGeneros(List.of(generosStr.split(", ")));
                }

                livros.add(lVO);
            }
            return livros;
        } finally {
            con.close();
        }
    }
}