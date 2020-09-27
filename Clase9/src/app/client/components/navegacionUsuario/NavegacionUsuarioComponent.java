package app.client.components.navegacionUsuario;

import app.client.vistaPrincipal.VistaPrincipalComponent;
import app.services.RecursosService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

import javax.swing.JButton;

public class NavegacionUsuarioComponent extends MouseAdapter implements ActionListener {
  private NavegacionUsuarioTemplate navegacionUsuarioTemplate;
  private VistaPrincipalComponent vistaPrincipalComponent;

  public NavegacionUsuarioComponent(VistaPrincipalComponent vistaPrincipalComponent) {
    this.vistaPrincipalComponent = vistaPrincipalComponent;
    this.navegacionUsuarioTemplate = new NavegacionUsuarioTemplate(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    this.vistaPrincipalComponent.mostrarComponente(e.getActionCommand().trim());
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    JButton boton = ((JButton) e.getSource());
    boton.setContentAreaFilled(true);
    boton.setBackground(RecursosService.getService().getColorAzulOscuro());
  }

  @Override
  public void mouseExited(MouseEvent e) {
    JButton boton = ((JButton) e.getSource());
    boton.setContentAreaFilled(false);
  }

  public NavegacionUsuarioTemplate getNavegacionUsuarioTemplate() {
    return this.navegacionUsuarioTemplate;
  }
}