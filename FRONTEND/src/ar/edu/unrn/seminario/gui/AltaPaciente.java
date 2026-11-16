
package ar.edu.unrn.seminario.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ObraSocialDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DniErroneoException;
import ar.edu.unrn.seminario.exception.DniUnicoException;

public class AltaPaciente extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField tfNombre;
    private JTextField tfApellido;
    private JTextField tfDni;
    private JComboBox<String> cbObraSocial;

    private List<ObraSocialDTO> obrasSociales = new ArrayList<>();

    public AltaPaciente(IApi api, JPanel panelContenedor, CardLayout cardLayout) {

        setLayout(new GridBagLayout());

        this.obrasSociales = api.obtenerObrasSociales();

        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

       
        JLabel lblRegistrarPaciente = new JLabel("Registrar Paciente");
        lblRegistrarPaciente.setHorizontalAlignment(SwingConstants.CENTER);
        lblRegistrarPaciente.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 30, 10);
        formulario.add(lblRegistrarPaciente, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

    
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 15));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formulario.add(lblNombre, gbc);

        tfNombre = new JTextField();
        tfNombre.setPreferredSize(new Dimension(250, 30));

        gbc.gridx = 1;
        gbc.weightx = 1;
        formulario.add(tfNombre, gbc);

     
        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 15));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        formulario.add(lblApellido, gbc);

        tfApellido = new JTextField();
        tfApellido.setPreferredSize(new Dimension(250, 30));

        gbc.gridx = 1;
        gbc.weightx = 1;
        formulario.add(tfApellido, gbc);

        JLabel lblDNI = new JLabel("DNI:");
        lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 15));

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        formulario.add(lblDNI, gbc);

        tfDni = new JTextField();
        tfDni.setPreferredSize(new Dimension(250, 30));

        gbc.gridx = 1;
        gbc.weightx = 1;
        formulario.add(tfDni, gbc);

       
        JLabel lblObraSocial = new JLabel("Obra social:");
        lblObraSocial.setFont(new Font("Tahoma", Font.PLAIN, 15));

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        formulario.add(lblObraSocial, gbc);

        cbObraSocial = new JComboBox<String>();
        cbObraSocial.setPreferredSize(new Dimension(250, 30));

        for (ObraSocialDTO os : this.obrasSociales) {
            cbObraSocial.addItem(os.getNombre());
        }

        gbc.gridx = 1;
        gbc.weightx = 1;
        formulario.add(cbObraSocial, gbc);

       
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));

        JButton btnRegistrar = new JButton("Registrar");

        btnRegistrar.addActionListener(e -> {

            if (cbObraSocial.getSelectedIndex() < 0) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una obra social.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ObraSocialDTO oSocial = obrasSociales.get(cbObraSocial.getSelectedIndex());

            try {
                String nombre = tfNombre.getText().trim();
                String apellido = tfApellido.getText().trim();
                String dniTxt = tfDni.getText().trim();

                int dni;

                try {
                    dni = Integer.parseInt(dniTxt);
                } catch (NumberFormatException ex) {
                    throw new DniErroneoException("El DNI debe contener solamente números.");
                }

                api.registrarPaciente(nombre, apellido, dni, oSocial.getCodigo());

                limpiarCampos();

                JOptionPane.showMessageDialog(null, "Paciente registrado con éxito!", "Info", JOptionPane.INFORMATION_MESSAGE);

                cardLayout.show(panelContenedor, "PANELDEFAULT");

            } catch (DataEmptyException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            } catch (DniErroneoException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            } catch (DniUnicoException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelBotones.add(btnRegistrar);

        JButton btnCancelar = new JButton("Cancelar");

        btnCancelar.addActionListener(e -> {
            limpiarCampos();
            cardLayout.show(panelContenedor, "PANELDEFAULT");
        });

        panelBotones.add(btnCancelar);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.insets = new Insets(25, 10, 10, 10);

        formulario.add(panelBotones, gbc);

      
        GridBagConstraints gbcFormulario = new GridBagConstraints();
        gbcFormulario.gridx = 0;
        gbcFormulario.gridy = 0;
        gbcFormulario.anchor = GridBagConstraints.CENTER;

        add(formulario, gbcFormulario);
    }

    private void limpiarCampos() {
        tfNombre.setText("");
        tfApellido.setText("");
        tfDni.setText("");
    }
}
