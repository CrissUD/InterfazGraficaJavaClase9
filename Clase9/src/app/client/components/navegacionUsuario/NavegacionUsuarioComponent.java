package app.client.components.navegacionUsuario;

import app.client.vistaPrincipal.VistaPrincipalComponent;
import app.services.RecursosService;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NavegacionUsuarioComponent implements ActionListener, MouseListener {

    private NavegacionUsuarioTemplate navegacionUsuarioTemplate;
    private VistaPrincipalComponent vistaPrincipalComponent;

    public NavegacionUsuarioComponent(VistaPrincipalComponent vistaPrincipalComponent) {
        this.vistaPrincipalComponent = vistaPrincipalComponent;
        this.vistaPrincipalComponent.getClass();
        this.navegacionUsuarioTemplate = new NavegacionUsuarioTemplate(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.vistaPrincipalComponent.mostrarComponente(e.getActionCommand().trim());
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

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