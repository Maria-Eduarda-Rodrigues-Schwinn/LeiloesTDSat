/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB();

        try {
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + error.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        conn = new conectaDAO().connectDB();

        try {
            String sql = "SELECT * FROM produtos";

            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + error.getMessage());
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar os recursos: " + e.getMessage());
            }
        }
        return listagem;
    }

    public void venderProduto(Integer id) {
        conn = new conectaDAO().connectDB();

        try {
            String sql = "UPDATE produtos SET status = ? WHERE id = ?";

            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            prep.setInt(2, id);
            
            prep.executeUpdate();

        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + error.getMessage());
        } finally {
            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar os recursos: " + e.getMessage());
            }
        }
    }
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        conn = new conectaDAO().connectDB();
        ArrayList<ProdutosDTO> produtosVendidos = new ArrayList<>();

        try {
            String sql = "SELECT * FROM produtos WHERE status = ?";

            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                produtosVendidos.add(produto);
            }
        } catch (SQLException error) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos vendidos: " + error.getMessage());
        } finally {
            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao fechar os recursos: " + e.getMessage());
            }
        }
        return produtosVendidos;
    }

}
