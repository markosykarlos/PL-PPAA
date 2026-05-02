/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cliente;

import javax.swing.*;
import monitores.*;
import main.*;
import rmi.*;
public class Maincliente {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new VentanaClienteRMI();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                    "Error al iniciar el cliente:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
