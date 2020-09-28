package app.client.login;

import app.client.vistaPrincipal.VistaPrincipalComponent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class LoginComponent extends MouseAdapter implements ActionListener {
  private LoginTemplate loginTemplate;
  private VistaPrincipalComponent vistaPrincipal;
  private JButton boton;
  private JTextField text;
  private JLabel label;
  private String[] placeholders = { "Nombre Usuario", "Clave Usuario" };

  public LoginComponent() {
    this.loginTemplate = new LoginTemplate(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == loginTemplate.getBEntrar()) {
      this.mostrarDatosUsuario();
      this.entrar();
    }
    if (e.getSource() == loginTemplate.getBCerrar())
      System.exit(0);
    if (e.getSource() == loginTemplate.getBOpcion1())
      JOptionPane.showMessageDialog(null, "Boton Opcion", "Advertencia", 1);
    if (e.getSource() == loginTemplate.getBRegistrarse())
      JOptionPane.showMessageDialog(null, "Boton Registro", "Advertencia", 1);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getSource() instanceof JTextField) {
      text = ((JTextField) e.getSource());
      label = loginTemplate.getLabels(text);
      label.setIcon(loginTemplate.getIAzul(label));
      text.setForeground(loginTemplate.getRecursosService().getColorPrincipal());
      text.setBorder(loginTemplate.getRecursosService().getBInferiorAzul());
      if (
        text.getText().equals(placeholders[0]) || 
        text.getText().equals(placeholders[1])
      ) 
        text.setText("");
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    if (e.getSource() instanceof JButton) {
      boton = ((JButton) e.getSource());
      boton.setBackground(loginTemplate.getRecursosService().getColorPrincipalOscuro());
    }
    if (e.getSource() instanceof JLabel) {
      label = ((JLabel) e.getSource());
      label.setIcon(loginTemplate.getINaranja(label));
    }
  }

  @Override
  public void mouseExited(MouseEvent e) {
    if (e.getSource() instanceof JButton) {
      boton = ((JButton) e.getSource());
      boton.setBackground(loginTemplate.getRecursosService().getColorPrincipal());
    }
    if (e.getSource() instanceof JLabel) {
      label = ((JLabel) e.getSource());
      label.setIcon(loginTemplate.getIBlanca(label));
    }
  }

  public void mostrarDatosUsuario() {
    String nombreUsuario = loginTemplate.getTNombreUsuario().getText();
    String claveUsuario = new String(loginTemplate.getTClaveUsuario().getPassword());
    String tipoUsuario = ((String) loginTemplate.getCbTipoUsuario().getSelectedItem());
    String check = "";
    if (loginTemplate.getCheckSi().isSelected())
      check = "si";
    if (loginTemplate.getCheckNo().isSelected())
      check = "no";
    JOptionPane.showMessageDialog(
      null, 
      "Nombre Usuario: " + nombreUsuario + 
      "\n Clave Usuario: " + claveUsuario + 
      "\nTipo Usuario: " + tipoUsuario + 
      "\n¿Notificaciones?: " + check, 
      "Advertencia", 
      1
    );
  }

  public void entrar() {
    if (vistaPrincipal == null)
      this.vistaPrincipal = new VistaPrincipalComponent(this);
    else
      this.vistaPrincipal.getVistaPrincipalTemplate().setVisible(true);
    loginTemplate.setVisible(false);
  }

  public LoginTemplate getLoginTemplate() {
    return this.loginTemplate;
  }
}