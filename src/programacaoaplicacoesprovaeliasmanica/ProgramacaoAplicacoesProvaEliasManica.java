/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package programacaoaplicacoesprovaeliasmanica;

import apoio.ConexaoBD;
import javax.swing.JOptionPane;
import telas.FramePrincipal;

/**
 *
 * @author Elias
 */
public class ProgramacaoAplicacoesProvaEliasManica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(ConexaoBD.getInstance().getConnection() != null) {
            new FramePrincipal().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o banco de dados!");
        }
    }
    
}
