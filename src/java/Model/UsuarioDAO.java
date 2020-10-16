package Model;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Aplicacao.Usuario; //Modelo que representa um registro do BD

public class UsuarioDAO {

    private Connection conexao;

    public UsuarioDAO() {
        try {
            // Executa a conexão com o banco de dados
            conexao = Conexao.criaConexao();
        } catch (Exception e) {
            System.out.println("Erro criação de conexao DAO");
            System.out.println(e);
        }
    }

    public ArrayList<Usuario> getListaUsuarios() {
        //Cria o objeto resultado que irá armazenar os registros retornados do BD
        ArrayList<Usuario> resultado = new ArrayList<>();
        try {
            // Cria o objeto para quer será utilizado para enviar comandos SQL
            // para o BD
            Statement stmt = conexao.createStatement();
            // Armazena o resultado do comando enviado para o banco de dados
            ResultSet rs = stmt.executeQuery("select * from usuarios");
            // rs.next() Aponta para o próximo registro do BD, se houver um 
            while (rs.next()) {
                //Cria o objeto da classe Contato para armazenar os dados
                //que vieram do BD
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                resultado.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
        }

        // Retorna a lista de Contatos encontrados no banco de dados.
        return resultado;
    }

    public Usuario getUsuarioPorID(int id) {
        Usuario Contato = new Usuario();
        try {
            String sql = "SELECT * FROM usuarios WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Contato.setId(rs.getInt("id"));
                Contato.setNome(rs.getString("nome"));
                Contato.setCpf(rs.getString("cpf"));
                Contato.setTipo(rs.getString("tipo"));
            }

        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        return Contato;
    }
    
    public Usuario getUsuarioPorLoginSenha(String cpf, String senha) {
        Usuario Contato = new Usuario();
        try {
            Contato.setId(0);
            
            String sql = "SELECT * FROM usuarios WHERE cpf = ? and senha = ? limit 1";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, cpf);
            ps.setString(1, senha);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Contato.setId(rs.getInt("id"));
                Contato.setNome(rs.getString("nome"));
                Contato.setCpf(rs.getString("cpf"));
                Contato.setTipo(rs.getString("tipo"));
            }

        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
        }
        return Contato;
    }

    public boolean gravar(Usuario usuario) {
        try {
            String sql;
            if (usuario.getId() == 0) {
                // Realizar uma inclusão
                sql = "INSERT INTO usuarios (nome, cpf, tipo, senha) VALUES (?,?,?,?)";
            } else {
                // Realizar uma alteração
                sql = "UPDATE contato SET nome=?, cpf=?,  tipo=? senha=? WHERE id=?";
            }

            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getTipo());
            ps.setString(4, usuario.getSenha());

            if (usuario.getId() > 0) {
                ps.setInt(5, usuario.getId());
            }

            ps.execute();

            return true;
        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        try {
            String sql = "DELETE FROM usuarios WHERE id = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro de SQL: " + e.getMessage());
            return false;
        }
    }

}
