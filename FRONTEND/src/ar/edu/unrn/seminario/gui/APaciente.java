package ar.edu.unrn.seminario.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.Paciente;

import ar.edu.unrn.seminario.exception.DataEmptyException;


import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class APaciente extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    private JTextField txtDNI;
    private JTextField txtApellido;
    private JTextField txtNombre;

    private MemoryApi api;

    public APaciente(MemoryApi api) {
        this.api = api;

        setTitle("REGISTRAR PACIENTE");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 371, 276);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(160, 208, 89, 23);
        contentPane.add(btnAceptar);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(259, 208, 89, 23);
        contentPane.add(btnCancelar);

        txtDNI = new JTextField();
        txtDNI.setColumns(10);
        txtDNI.setBounds(136, 103, 158, 20);
        contentPane.add(txtDNI);

        JLabel lblObraSocial = new JLabel("Obra social:");
        lblObraSocial.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblObraSocial.setBounds(51, 135, 75, 25);
        contentPane.add(lblObraSocial);

        JComboBox<String> comboObraSocial = new JComboBox<String>();
        comboObraSocial.setBounds(136, 135, 158, 25);
        comboObraSocial.addItem("IProSS");
        comboObraSocial.addItem("IOMA");
        comboObraSocial.addItem("OSDE");
        contentPane.add(comboObraSocial);

        JLabel lblDNI = new JLabel("DNI:");
        lblDNI.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDNI.setBounds(51, 99, 75, 25);
        contentPane.add(lblDNI);

        JLabel lblApellido = new JLabel("Apellido:");
        lblApellido.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblApellido.setBounds(51, 63, 75, 25);
        contentPane.add(lblApellido);

        txtApellido = new JTextField();
        txtApellido.setColumns(10);
        txtApellido.setBounds(136, 67, 158, 20);
        contentPane.add(txtApellido);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNombre.setBounds(51, 27, 75, 25);
        contentPane.add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setColumns(10);
        txtNombre.setBounds(136, 31, 158, 20);
        contentPane.add(txtNombre);

        btnAceptar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    try {
        	        String nombre = txtNombre.getText().trim();
        	        String apellido = txtApellido.getText().trim();
        	        String dniTexto = txtDNI.getText().trim();

        	        int dni = Integer.parseInt(dniTexto);

        	        if (dniTexto.length() > 8 || dni <= 10000000) {
        	            throw new DataEmptyException(
        	                "El DNI debe ser válido y tener hasta 8 dígitos."
        	            );
        	        }

        	        if (comboObraSocial.getSelectedItem() == null) {
        	            throw new DataEmptyException(
        	                "Debe seleccionar una obra social."
        	            );
        	        }

        	        String obraSocial = comboObraSocial.getSelectedItem().toString();

        	        Paciente paciente = new Paciente(
        	            nombre,
        	            apellido,
        	            dni,
        	            obraSocial,
        	            true
        	        );

        	        api.registrarPaciente(paciente);

        	        JOptionPane.showMessageDialog(
        	            APaciente.this,
        	            "Paciente registrado correctamente."
        	        );

        	        dispose();

        	    } catch (NumberFormatException ex) {
        	        JOptionPane.showMessageDialog(
        	            APaciente.this,
        	            "El DNI debe contener solamente números.",
        	            "Error de formato",
        	            JOptionPane.ERROR_MESSAGE
        	        );

        	    } catch (DataEmptyException ex) {
        	        JOptionPane.showMessageDialog(
        	            APaciente.this,
        	            ex.getMessage(),
        	            "Error de validación",
        	            JOptionPane.ERROR_MESSAGE
        	        );
        	    }
        	}
        	});

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
