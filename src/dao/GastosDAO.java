/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import apoio.ConexaoBD;
import apoio.IDAOT;
import entidades.Gastos;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Elias
 */
public class GastosDAO implements IDAOT<Gastos>{

    @Override
    public String salvar(Gastos o) {
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "INSERT INTO gastos (data, descricao_gasto, valor) VALUES (" +
                         "'" + o.getData() + "', " +
                         "'" + o.getDescricao_gasto() + "', " +
                         o.getValor() + ");";

            System.err.println("Sql: " + sql);

            int retorno = st.executeUpdate(sql);

            return null;
        } catch (Exception e) {
            System.out.println("Erro ao inserir o gasto: " + e);
            return e.toString();
        }
    }


    @Override
    public String atualizar(Gastos o) {
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "UPDATE gastos SET data='" + o.getData() + "', descricao_gasto='" + o.getDescricao_gasto()+ "', valor=" + o.getValor() + " WHERE id=" + o.getId();

            System.err.println("Sql: " + sql);

            int retorno = st.executeUpdate(sql);

            return null;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar o gasto: " + e);
            return e.toString();
        }
    }

    @Override
    public String excluir(int id) {
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();
            
            String sql = "delete from gastos where id='"+ id + "'";
            
            System.err.println("Sql: " + sql);
            
            int retorno = st.executeUpdate(sql);
            
            return null;
        } catch(Exception e) {
            System.out.println("Erro ao excluir o gasto" + e);
            return e.toString();
        }
    }

    @Override
    public ArrayList<Gastos> consultarTodos() {
        ArrayList<Gastos> clientes = new ArrayList();
        
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();
            
            String sql = "SELECT * FROM cliente;";
            
            System.err.println("Sql: " + sql);
            
            ResultSet retorno = st.executeQuery(sql);
            
            while(retorno.next()) {
                Gastos cliente = new Gastos();
                
                cliente.setId(retorno.getInt("id"));
                
                clientes.add(cliente);
            }
        } catch(Exception e) {
            System.out.println("Erro ao consultar os clientes" + e);
        } 
        
        return clientes;
    }

    @Override
    public ArrayList<Gastos> consultar(String criterio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Gastos consultarId(int id) {
        Gastos gasto = null;
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "SELECT * FROM gastos WHERE id = " + id;

            System.err.println("Sql: " + sql);

            ResultSet retorno = st.executeQuery(sql);

            if (retorno.next()) {
                gasto = new Gastos();

                gasto.setId(retorno.getInt("id"));
                gasto.setData(retorno.getString("data"));
                gasto.setDescricao_gasto(retorno.getString("descricao_gasto"));
                gasto.setValor(retorno.getDouble("valor"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar o gasto: " + e);
        }
        return gasto;
    }

    
   public void popularTabela(JTable tabela, String criterio) {

        ResultSet resultadoQ;

        // dados da tabela
        Object[][] dadosTabela = null;

        // cabeçalho da tabela
        Object[] cabecalho = new Object[4];
        cabecalho[0] = "Id";
        cabecalho[1] = "Data";
        cabecalho[2] = "Descrição";
        cabecalho[3] = "Valor";

        // cria matriz de acordo com nº de registros da tabela
        try {
            resultadoQ = ConexaoBD.getInstance().getConnection().createStatement().executeQuery(""
                    + "SELECT count(*) "
                    + "FROM gastos "
                    + "WHERE "
                    + "descricao_gasto ILIKE '%" + criterio + "%'");

            resultadoQ.next();

            dadosTabela = new Object[resultadoQ.getInt(1)][4];

        } catch (Exception e) {
            System.out.println("Erro ao consultar gastos: " + e);
        }

        int lin = 0;

        // efetua consulta na tabela
        try {
            resultadoQ = ConexaoBD.getInstance().getConnection().createStatement().executeQuery(""
                    + "SELECT * "
                    + "FROM gastos "
                    + "WHERE "
                    + "descricao_gasto ILIKE '%" + criterio + "%' ORDER BY id");

            while (resultadoQ.next()) {

                dadosTabela[lin][0] = resultadoQ.getInt("id");
                dadosTabela[lin][1] = resultadoQ.getDate("data");
                dadosTabela[lin][2] = resultadoQ.getString("descricao_gasto");
                dadosTabela[lin][3] = resultadoQ.getDouble("valor");

                lin++;
            }
        } catch (Exception e) {
            System.out.println("Problemas para popular tabela...");
            System.out.println(e);
        }

        // configurações adicionais no componente tabela
        tabela.setModel(new DefaultTableModel(dadosTabela, cabecalho) {
            @Override
            // quando retorno for FALSE, a tabela não é editável
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            // alteração no método que determina a coluna em que o objeto ImageIcon deverá aparecer
            @Override
            public Class<?> getColumnClass(int column) {
                return Object.class;
            }
        });

        // permite seleção de apenas uma linha da tabela
        tabela.setSelectionMode(0);

        // redimensiona as colunas de uma tabela
        TableColumn column = null;
        for (int i = 0; i < tabela.getColumnCount(); i++) {
            column = tabela.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(17);
                    break;
                case 1:
                    column.setPreferredWidth(140);
                    break;
                case 2:
                    column.setPreferredWidth(140);
                    break;
                case 3:
                    column.setPreferredWidth(70);
                    break;
            }
        }
    }

}
