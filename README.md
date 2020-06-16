# Interfaz Gráfica en Java

Curso propuesto por el grupo de trabajo Semana de Ingenio y Diseño (**SID**) de la Universidad Distrital Francisco Jose de Caldas.

## Monitor

**Cristian Felipe Patiño Cáceres** - Estudiante de Ingeniería de Sistemas de la Universidad Distrital Francisco Jose de Caldas

# Clase 9

## Objetivos

* Identificar los usos principales de los eventos del Mouse para añadirle interactividad a nuestra interfaz gráfica hacia el usuario.
* Reconocer el enfoque de discriminación por tipo de clases para manejar la interactividad con diferentes objetos gráficos a la vez desde un método implementado de eventos.
* Examinar la posibilidad de cambiar el estado de diferentes objetos gráficos desde un objeto gráfico que ha activado un evento.
* Comprender el uso combinado de diferentes métodos implementados de eventos para realizar acciónes de interactividad a nuestras interfaces gráficas.

# Antes de Comenzar

Para continuar con el ejercicio deberá actualizar la carpeta **resources/img** ya que se han agregado nuevas imágenes. Estas las puede descargar en este mismo repositorio entrando a la carpeta **Clase9** seguido de **resources/img**.

* Vamos a crear un nuevo color en el servicio **RecursosService**, recordamos el proceso de creación de un objeto decorador dentro de este servicio:

**Declaración:**
```javascript
private Color colorAzulOscuro;
```

**Ejemplificación:**
```javascript
// Dentro del constructor
colorAzulOscuro = new Color(30, 48, 90);
```

**Método get:**
```javascript
public Color getColorAzulOscuro(){
    return colorAzulOscuro;
}
```

* También vamos a crear un borde gris inferior para ser usados en nuestros campos de texto:

**Declaración:**
```javascript
private Border borderInferiorGris;
```

**Ejemplificación:**
```javascript
// Dentro del Constructor
borderInferiorGris = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY);
```

**Método get:**
```javascript
public Border getBorderInferiorGris(){
    return borderInferiorGris;
}
```

* Finalmente vamos a añadir una imagen nueva:

**Declaración:**
```javascript
private ImageIcon iMinimizar;
```

**Ejemplificación:**
```javascript
// Dentro del Constructor
iMinimizar = new ImageIcon("Clase9/resources/img/minimizar.png");
```

**Método get:**
```javascript
public ImageIcon getIMinimizar(){
    return iMinimizar;
}
```

***Nota:** Recordemos que estos objetos decoradores dentro del servicio **RecursosService** se crean pensando en que serán utilizados en varias partes del proyecto, si ese no es el caso entonces el objeto decorador debe ser creado unicamente en el componente gráfico donde se necesita.*

Recordando un poco nuestro recorrido hemos construido nuestro componente **Configuraciones** el cual cuenta con la implementación de todos los **eventos del Mouse** y con el que el usuario puede interactuar para comprobar el funcionamiento correcto del Mouse. Gracias a esto aprendimos las principales características de cada uno de los eventos e identificamos aspectos como la activación de estos, los momentos en que dejan de funcionar y casos particulares.

<div align='center'>
    <img  src='https://i.imgur.com/ZIWVIB7.png'>
    <p>Componente Configuraciones con la implementación de todos los eventos de Mouse</p>
</div>

# Implementación de eventos de Mouse

En esta clase vamos a ver la implementación de los diferentes eventos del Mouse dentro de todo nuestro proyecto, paralelamente vamos a ver algunos temas de importancia que están relacionados con el uso de estos eventos. Los items de esta clase serán entonces: 

* **Representación única para objetos gráficos de una misma Clase**
* **Discriminación de Clases**
* **Efectos hacia otros objetos Gráficos**
* **Uso combinado de varios Métodos implementados de eventos**

# Representación única para objetos gráficos de una misma Clase

Vamos a empezar ubicándonos en nuestro componente **NavegaciónUsuario** y específicamente en su clase **NavegacionUsuarioComponent**. En este caso queremos darle algo de interactividad a los botones de la navegación ya que estos realmente están estáticos. Queremos cambiar su color de fondo una vez el usuario pase con el mouse por encima de ellos. Lo primero que vamos a hacer es añadir la implementación de la interfaz **MouseListener**:

<div align='center'>
    <img  src='https://i.imgur.com/ZDHg7Aa.png'>
    <p>implementación de MouseListener en el componente Navegación Usuario</p>
</div>

Debemos recordar nuevamente que esta implementación exige que se implementen los métodos que por defecto tiene la interfaz **MouseListener**:

```javascript
@Override
public void mouseClicked(MouseEvent e) {}

@Override
public void mousePressed(MouseEvent e) {}

@Override
public void mouseReleased(MouseEvent e) {}

@Override
public void mouseEntered(MouseEvent e) {}

@Override
public void mouseExited(MouseEvent e) {}
```

Ahora nos ubicamos en la clase **NavegacionUsuarioTemplate** y vamos a añadirle a los botones de la navegación la capacidad de escuchar los eventos de tipo **MouseListener** para eso usamos el método **addMouseListener**:

```javascript
this.bInicio.addMouseListener(navegacionUsuarioComponent);
this.bPerfil.addMouseListener(navegacionUsuarioComponent);
this.bAmigos.addMouseListener(navegacionUsuarioComponent);
this.bProductos.addMouseListener(navegacionUsuarioComponent);
this.bConfiguracion.addMouseListener(navegacionUsuarioComponent);
this.bCerrarSesion.addMouseListener(navegacionUsuarioComponent);
```

***Nota:** Recordemos que en el anterior código se ven todas estas lineas juntas sin embargo estos son métodos de configuración por lo que cada una de ellas debe estar organizada junto al proceso de creación de su respectivo botón.*

Como nuestra clase **NavegacionUsuarioComponent** ahora implementa a la interfaz **MouseListener** es posible ingresar como argumento al objeto que representa esta clase dentro del método **addMouseListener**.

Vamos a probar el evento con un solo botón por ahora, para ello primero debemos realizar el método **get** del botón **bInicio** en la clase **Template** del componente:

```javascript
public JButton getBInicio(){
    return this.bInicio;
}
```

Ahora dentro de la clase **NavegaciónUsuarioComponent** vamos a ubicarnos dentro del método **mouseEntered** para cambiar el color de fondo del botón **bInicio**.

* Primero creamos una discriminación por objetos, para esto realizamos un if en el cual preguntamos si el objeto que ha activado el evento es igual al botón **bInicio**:

```javascript
public void mouseEntered(MouseEvent e) {
    if(e.getSource() == navegacionUsuarioTemplate.getBInicio()){
    } 
}
```

* Para obtener el botón debemos usar el objeto de la clase **Template** y llamar al método **get** correspondiente: 

```javascript
public void mouseEntered(MouseEvent e) {
    if(e.getSource() == navegacionUsuarioTemplate.getBInicio()){
        navegacionUsuarioTemplate.getBInicio()
    } 
}
```

* Una vez obtenido el botón podemos agregarle color de fondo, para poder agregarle color a nuestro botón debemos agregarle antes las propiedades de contenedor de botón nuevamente y luego si cambiar el color de fondo ya que recordemos que en el momento de su construcción se configuró este con características de transparencia por lo que si le cambiamos simplemente el color no sera reflejado:

```javascript
public void mouseEntered(MouseEvent e) {
    if(e.getSource() == navegacionUsuarioTemplate.getBInicio()){
        navegacionUsuarioTemplate.getBInicio().setContentAreaFilled(true);
        navegacionUsuarioTemplate.getBInicio().setBackground(RecursosService.getService().getColorAzulOscuro());
    } 
}
```
Noten que para poder agregarle el color azul oscuro, necesitamos llamar al servicio **RecursosService**, en este caso se tomo el enfoque en el cual se llama directamente al servicio, se obtiene dicho servicio y se llama al método **get** correspondiente, aunque no es la única forma de hacerse, mas adelante veremos otro enfoque que también es valido.
Nuestro Botón ahora cambiará de color a un **Azul mas oscuro** una vez pasemos sobre el, sin embargo recordemos que esto implica que debemos configurar la acción una vez salimos de la zona del botón para que este vuelva a la normalidad, para eso configuramos el método **mouseExited**:

* Nuevamente realizamos una discriminación de objetos para indicar que se active solo cuando este salga del botón **bInicio**:

```javascript
public void mouseExited(MouseEvent e) {
    if(e.getSource() == navegacionUsuarioTemplate.getBInicio())
}
```

* Obtenemos el botón mediante el objeto de la clase **Template** y su método **get** correspondiente:
```javascript
public void mouseExited(MouseEvent e) {
    if(e.getSource() == navegacionUsuarioTemplate.getBInicio())
        navegacionUsuarioTemplate.getBInicio()
}
```

* Para que el botón vuelva a su estado normal solo hace falta con volver a configurarle sus características de transparencia, de ese modo el botón eliminara cualquier color de fondo que contenga:
```javascript
public void mouseExited(MouseEvent e) {
    if(e.getSource() == navegacionUsuarioTemplate.getBInicio())
        navegacionUsuarioTemplate.getBInicio().setContentAreaFilled(false);
}
```

Una vez ejecutamos nuestra aplicación podemos ver el siguiente resultado:

<div align='center'>
    <img  src='./resources/MouseEvent1.gif'>
    <p>Interacción con eventos a un solo botón</p>
</div>

Ahora podríamos realizar el anterior proceso  con los demás botones dentro de la navegación, sin embargo note las siguientes particularidades:

* La anterior acción del cambio de color de fondo se quiere realizar con los demás botónes, es decir que a diferencia de un **ActionListener** donde cada botón generalmente busca realizar una acción distinta en estos eventos es común que varios objetos gráficos puedan compartir los mismos comportamientos.
* En esta navegación solo contamos con 6 botones sin embargo si un menu contara con 20 botónes o incluso más habría que crear mucho código para representar el mismo comportamiento para cada uno de los botones lo cual hace que nuestro código sea mas difícil de entender y mantener. Esto implicaría crear un método **get** dentro de la clase Template por cada botón, crear un nuevo if dentro de los métodos de los eventos usados para realizar la discriminación por objeto y realizar exactamente las mismas lineas de código por cada botón. **!Esto es demasiado código para una misma acción!**.
* Este caso particular se diferencia al caso en que todos los botones realizarían una misma acción y en la cual simplemente se escribía el código dentro del método sin ningún tipo de discriminación. En este caso eso no es posible, ya que es necesario indicar a que botón se le va a cambiar el color y en que momento. Si por otro lado el evento buscará que al pasar por cada botón se abriera una ventana emergente con el mismo mensaje para todos los botones esto seria posible. Sin embargo, como vamos a cambiar configuraciónes de un objeto gráfico en especifico en un momento especifico se debe realizar una discriminación.

Si tenemos que realizar una discriminación de objetos obligatoriamente pero no queremos tener una cantidad extensa de código **¿Cómo podemos resolver esta situación?**. Aquí entra en juego la **Representación única para objetos gráficos de una misma Clase**. Para entender este concepto vamos a implementarlo en el código y lo iremos explicando en el proceso.

Nos ubicamos dentro de la clase **NavegacionUsuarioComponent**, nos posicionamos en el método **mouseEntered** y vamos a crear una variable tipo **JButton** en este caso la llamaremos boton:

```javascript
public void mouseEntered(MouseEvent e) {
    JButton boton;
    // if(e.getSource() == navegacionUsuarioTemplate.getBInicio()){
    //     navegacionUsuarioTemplate.getBInicio().setContentAreaFilled(true);
    //     navegacionUsuarioTemplate.getBInicio().setBackground(RecursosService.getService().getColorAzulOscuro());
    // } 
}
```
Noten que el resto del código se comento ya que no sera necesario por ahora. Vamos a implementar a continuación la **Representación única para objetos gráficos de una misma Clase** y luego a explicarla:

```javascript
public void mouseEntered(MouseEvent e) {
    JButton boton = ((JButton) e.getSource());
    // if(e.getSource() == navegacionUsuarioTemplate.getBInicio()){
    //     navegacionUsuarioTemplate.getBInicio().setContentAreaFilled(true);
    //     navegacionUsuarioTemplate.getBInicio().setBackground(RecursosService.getService().getColorAzulOscuro());
    // } 
}
```

En el anterior código se realizo lo siguiente:
* A traves del objeto de evento **e** se obtiene el objeto que ha activado el evento, esto mediante el método **getSource**.
* El método **getSource** va a retornar al objeto con el tipo de dato **Object** es decir su forma mas general del objeto, por eso es necesario especificar el tipo de clase a la cual pertenece  el objeto que activo el evento. Para esto realizamos un **Cast** o cambio de tipado obligatorio de objeto, convirtiéndolo a un **JButton**.
* Una vez se convirtió el objeto se iguala al objeto variable que creamos previamente.

Esto implica que nuestra variable **boton** va tener la capacidad de representar cualquier botón que active el evento. Este es un concepto muy poderoso y una solución perfecta a la situación planteada. Ahora solo basta con indicarle a nuestro botón que le vamos a cambiar el color de fondo:

***Nota:** Podemos borrar el anterior código comentado ya que no lo necesitamos.*

```javascript
public void mouseEntered(MouseEvent e) {
    JButton boton = ((JButton) e.getSource());
    boton.setContentAreaFilled(true);
    boton.setBackground(RecursosService.getService().getColorAzulOscuro()); 
}
```

Lo mismo vamos a realizar con el método **mouseExited** para indicar que queremos a los botones en su estado inicial una vez se sale de ellos:
```javascript
public void mouseExited(MouseEvent e) {
    JButton boton = ((JButton) e.getSource());
    boton.setContentAreaFilled(false);
}
```

***Nota:** También podemos borrar el método **get** del primer botón creado con anterioridad en la clase **NavegacionUsuarioTemplate** ya que no lo usaremos para nada.*

Ahora hemos representado e implementado el mismo comportamiento para varios botones en unas cuantas lineas de código y para comprobarlo podemos abrir nuestra aplicación:

<div align='center'>
    <img  src='./resources/MouseEvent2.gif'>
    <p>Implementación de Representación única para objetos gráficos de una misma Clase</p>
</div>

# Discriminación de Clases

Ahora vamos a posicionarnos con nuestro componente **login** específicamente en la clase **LoginComponent** ya que vamos a darle interactividad a nuestro Login de usuario. Nos ubicamos en la declaración de la clase y vamos a implementar la interfaz **MouseListener**.

<div align='center'>
    <img  src='https://i.imgur.com/pepSD9W.png'>
    <p>Implementación de la interfaz MouseListener en la clase LoginComponent</p>
</div>

Se recuerda una vez más la importación de los métodos de la interfaz:
```javascript
@Override
public void mouseClicked(MouseEvent e) {}

@Override
public void mousePressed(MouseEvent e) {}

@Override
public void mouseReleased(MouseEvent e) {}

@Override
public void mouseEntered(MouseEvent e) {}

@Override
public void mouseExited(MouseEvent e) {}
```

En este caso queremos que al pasar sobre los botones **bEntrar** y **bRegistrarse** se cambie el color de fondo a un Azul mas oscuro, pero también se quiere que al pasar por encima de los labels **lTituloLogin**, **lEslogan** y **lNotificaciones** se cambie el color de la fuente a un color Morado. Para esto nos ubicaremos en la clase **LoginTemplate** y vamos a añadir la escucha de este tipo de eventos a estos objetos gráficos.

```javascript
bEntrar.addMouseListener(loginComponent);
bRegistrarse.addMouseListener(loginComponent);
lEslogan.addMouseListener(this.loginComponent);
lTituloLogin.addMouseListener(this.loginComponent);
lNotificaciones.addMouseListener(this.loginComponent);
```

***Nota:** Recordemos que en el anterior código se ven todas estas lineas juntas sin embargo estos son métodos de configuración por lo que cada una de ellas debe estar organizada junto al proceso de creación de su respectivo botón y label.*

Ademas vamos a crear un nuevo método **get** donde vamos a disponer al servicio **RecursosService** y de esta forma la clase **LoginComponent** pueda interactuar con el servicio sin la necesidad de obtenerlo:
```javascript
public RecursosService getRecursosService(){
    return sRecursos;
}
```

Cuando realizábamos eventos de acción mediante un **ActionListener** solo debíamos preocuparnos por la discriminación a nivel de objetos ya que solo utilizábamos a los botones para gestionar estos eventos. Sin embargo, cuando queremos que diferentes objetos de distintos tipos de clases hagan un comportamiento basado en un mismo evento como es este caso donde los botones y los Labels realizarán una acción basada en un mismo evento, es necesario realizar una **Discriminación de clases**.

Vamos a ubicarnos en el método **mouseEntered** y dentro, vamos a configurar el comportamiento para los botones y labels. Vamos a realizar la **Discriminación de clases**.

* Primero crearemos los if necesarios para realizar la discriminación:

```javascript
@Override
public void mouseEntered(MouseEvent e) {
    if(/* Discriminación para botones */){
    }
    if(/* Discriminación para Labels */){
    }
}
```

* Ahora vamos a obtener el objeto que ha activado el evento para ambos casos:

```javascript
@Override
public void mouseEntered(MouseEvent e) {
    if(e.getSource()){
    }
    if(e.getSource()){
    }
}
```

* Vamos a realizar a continuación la comparación con la clase:
```javascript
@Override
public void mouseEntered(MouseEvent e) {
    if(e.getSource() instanceof JButton){
    }
    if(e.getSource() instanceof JLabel){
    }
}
```
En el anterior código estamos realizando lo siguiente:
* Obtenemos el objeto que esta activando el evento.
* Mediante la palabra clave **instanceof** se esta preguntando si el objeto que ha activado el evento es una **ejemplificación** de la clase **JButton** o **JLabel**.

Ya hemos realizado la **Discriminación de clases** ahora en cada if se puede escribir el código correspondiente. Primero vamos a crear dos atributos en la clase **LoginComponent** para realizar una **Representación única para objetos gráficos de una misma Clase** tanto para un botón como para un label:

* **Declaración:**
```javascript
// Al Inicio de la Clase
private JButton boton;
private JLabel label;
```
* **Uso de los atributos:**
```javascript
@Override
public void mouseEntered(MouseEvent e) {
    if(e.getSource() instanceof JButton){
        boton = ((JButton) e.getSource());
        boton.setBackground(loginTemplate.getRecursosService().getColorAzulOscuro()); 
    }
    if(e.getSource() instanceof JLabel){
        label = ((JLabel) e.getSource());
        label.setForeground(loginTemplate.getRecursosService().getColorMorado());
    }
}
```

Se pueden observar varias cosas a mencionar:

* Noten que para realizar el cambio de color es necesario llamar al servicio **RecursosService**, en este caso se opto por el enfoque en el cual el recurso es pasado desde la clase **Template** del componente y asi la clase **LoginComponent** no se ve en la necesidad de obtener el servicio, este enfoque al igual que el usado en el componente **NavegacionUsuario** son validos ya que al final de cuentas se esta utilizando un único objeto en memoria del servicio. 
* También se puede notar que se realizó una **Representación única para objetos gráficos de una misma Clase** tanto para los botones como para los labels, ya que con el caso de los botones todos tenían el mismo comportamiento de cambiar de color de fondo, para los labels es el mismo caso cambiando el color de fuente. 
* Noten que ademas solo se agrego la escucha de estos eventos a los botónes y labels que iban a tener un mismo comportamiento, es decir que botones como el **bCerrar** o **bOpcion 1,2 o 3** no están incluidos por que no queremos que realicen este comportamiento y lo mismo pasa con los labels que no se incluyeron.
* Se utilizo esta vez atributos en lugar de variables para estos botónes y labels, sin embargo tanto este enfoque como el de el uso de variables funciona de igual manera, ya que estos objetos no van a adicionar nada en memoria, estos objetos son conocidos como **Objetos contenedores** y lo único que hacen es apuntar en la memoria al objeto que ya fue previamente creado y que activo el evento.

Recordemos que ahora debemos configurar el método **mouseExited** para que los objetos gráficos regresen a su estado inicial una vez se sale del area del objeto, realizamos de nuevo la **Discriminación de clases**, **Representación única para objetos gráficos de una misma Clase**.

```javascript
@Override
public void mouseExited(MouseEvent e) {
    if(e.getSource() instanceof JButton){
        boton = ((JButton) e.getSource());
        boton.setBackground(loginTemplate.getRecursosService().getColorAzul());  
    } 
    if(e.getSource() instanceof JLabel){
        label = ((JLabel) e.getSource());
        label.setForeground(loginTemplate.getRecursosService().getColorAzul());
    }
}
```

Una vez ejecutamos nuestra aplicación podemos ver el resultado esperado:

<div align='center'>
    <img  src='./resources/MouseEvent3.gif'>
    <p>Implementación de discriminación de clases en el componente Login</p>
</div>

# Efectos hacia otros objetos Gráficos

Cuando usamos eventos de Mouse también podemos realizar efectos hacia otros objetos gráficos a traves de un objeto que activo el evento. Para este caso queremos modificar un poco los cuadros de texto es decir el **JTextField y JPasswordField** de nuestro Login de Usuario. Si nos damos cuenta, cuando una persona quiere escribir su nombre de usuario o su contraseña, tendrá que borrar primero el contenido que está escrito en la caja de texto, esto en realidad es algo molesto para nuestros usuarios, quizás con el uso de los eventos del Mouse podamos ayudarles un poco: cada vez que el usuario de un click al **JTextfield o al JPassworldField** podríamos borrar automáticamente su contenido para que el usuario solamente deba escribir sus datos. También podríamos dar un toque de interacción con el color del texto y los bordes de las cajas de texto poniendo de azul la caja de texto que se este usando mientras que la otra podría quedar en gris. Sin embargo realizar todo esto implica varias cosas:

* Primero vamos a cambiar los bordes por defecto y el color de fuente de nuestras cajas de texto por grises usando el servicio **RecursosService**:
<div align='center'>
    <img  src='https://i.imgur.com/GP1PMIO.png'>
    <p>Cambio de bordes y color de texto por gris por defecto</p>
</div>

<div align='center'>
    <img  src='https://i.imgur.com/AzwQLP0.png'>
    <p>Cambio de bordes y color de texto por gris por defecto</p>
</div>

<div align='center'>
    <img  src='https://i.imgur.com/T4Oy6AX.png'>
    <p>Login de usuario con cajas de texto de color gris en bordes y fuentes</p>
</div>

Vamos a posicionarnos en el método **MouseClicked** y en este caso vamos a utilizar un enfoque de **discriminación por objetos**, esto por varias razones:
* Solo existe un JTextField y un JPasswordField por lo que un enfoque de discriminación por objetos solo nos llevara a crear dos If.
* No es posible realizar discriminación por clases para estos dos objetos y esto es debido a que un **JPasswordField** hereda de un **JTextField** asi que si intentamos hacer este enfoque de discriminación el lenguaje va a tomar a los dos como si fueran una ejemplificación de un **JTextField**:

```javascript
@Override
public void mouseClicked(MouseEvent e) {
    if(e.getSource() == loginTemplate.getTNombreUsuario()){
    }
    if(e.getSource() == loginTemplate.getTClaveUsuario()){
    }
}
```
una vez realizada la discriminación podemos empezar con nuestro código en ambos casos:

* Lo primero que queremos es que una vez el usuario de click sobre la caja de texto en especifico se bore lo que tenga escrito, para esto debemos obtener el objeto de las cajas de texto en cuestión a traves del objeto de la clase **Template** y el método **get** correspondiente y seguido del método **setText**, dentro del método basta con ponerle unas comillas vaciás representando que ahora el texto que contendrá estará vació:

```javascript
@Override
public void mouseClicked(MouseEvent e) {
    if(e.getSource() == loginTemplate.getTNombreUsuario()){
        loginTemplate.getTNombreUsuario().setText("");
    }
    if(e.getSource() == loginTemplate.getTClaveUsuario()){
        loginTemplate.getTClaveUsuario().setText("");
    }
}
```

Si probamos este código funciona, sin embargo tenemos un problema, si el usuario se posiciona en el JTextField de nombre de usuario se borrara lo que esta escrito y el usuario podrá escribir tranquilamente su nombre de usuario, pero si mas adelante vuelve a dar click al JTextField **tNombreUsuario** para corregir una parte de lo que escribió, este de nuevo se borrara completamente:

<div align='center'>
    <img  src='./resources/MouseEvent4.gif'>
    <p>Primera prueba de la implementación de MouseClicked</p>
</div>

Para corregir esto debemos indicar que solamente se borrara el contenido de la caja de texto cuando el contendió sea el **placeholder** es decir el contenido inicial, para el caso del JTextField **tNombreUsuario** es *"Nombre Usuario"* y para el JPasswordField **tClaveUsuario** es *"clave Usuario"*:

```javascript
@Override
public void mouseClicked(MouseEvent e) {
    if(e.getSource() == loginTemplate.getTNombreUsuario()){
        if(loginTemplate.getTNombreUsuario().getText().equals("Nombre Usuario")) 
            loginTemplate.getTNombreUsuario().setText("");
    }
    if(e.getSource() == loginTemplate.getTClaveUsuario()){
        if(new String(loginTemplate.getTClaveUsuario().getPassword()).equals("clave Usuario")) 
            loginTemplate.getTClaveUsuario().setText("");
    }
}
```

Comprobamos ejecutando nuestra aplicación:

<div align='center'>
    <img  src='./resources/MouseEvent5.gif'>
    <p>Arreglo de placeholder en el Login de usuario</p>
</div>

Ahora, una forma de añadir interactividad en nuestro login sería colocando de color azul la caja de texto en la cual se esta escribiendo actualmente, específicamente cambiando el color de fuente y el borde inferior de uno gris a azul:

```javascript
@Override
public void mouseClicked(MouseEvent e) {
    if(e.getSource() == loginTemplate.getTNombreUsuario()){
        if(loginTemplate.getTNombreUsuario().getText().equals("Nombre Usuario")) 
            loginTemplate.getTNombreUsuario().setText("");

        loginTemplate.getTNombreUsuario().setBorder(
            loginTemplate.getRecursosService().getBorderInferiorAzul()
        );
        loginTemplate.getTNombreUsuario().setForeground(
            loginTemplate.getRecursosService().getColorAzul()
        );
    }
    if(e.getSource() == loginTemplate.getTClaveUsuario()){
        if(new String(loginTemplate.getTClaveUsuario().getPassword()).equals("clave Usuario")) 
            loginTemplate.getTClaveUsuario().setText("");

        loginTemplate.getTClaveUsuario().setBorder(
            loginTemplate.getRecursosService().getBorderInferiorAzul()
        );
        loginTemplate.getTClaveUsuario().setForeground(
            loginTemplate.getRecursosService().getColorAzul()
        );
    }
}
```

<div align='center'>
    <img  src='./resources/MouseEvent6.gif'>
    <p>Cambio de color a caja de texto donde se escribe actualmente</p>
</div>

Nuestro anterior código funciona de maravilla, pero si le queremos añadir un mayor control a nuestro evento podríamos indicarle a nuestro programa que la caja de texto quede con el color azul solamente si el usuario ha escrito algo, es decir que si la caja de texto se dejo vaciá vuelva a tomar el color gris y esto le dará un indicio al usuario que aun debe llenar esa parte del formulario para continuar. Esto se podría hacer de distintas maneras, pero en este caso vamos a realizar el control a traves del objeto gráfico opuesto para asi realizar **efectos hacia otros objetos Gráficos**.

Por ejemplo desde nuestra discriminación del JTextField **tNombreUsuario** podríamos preguntar si el contenido de la clave esta vació, si ese es el caso entonces que vuelva a pintar la caja de gris para indicarle al usuario que aun no esta lista esa parte. Lo mismo podemos hacer en la discriminación del JPasswordField **tClaveUsuario** hacia la caja de texto del nombre del usuario:

```javascript
@Override
public void mouseClicked(MouseEvent e) {
    if(e.getSource() == loginTemplate.getTNombreUsuario()){
        // BORRA EL PLACEHOLDER
        if(loginTemplate.getTNombreUsuario().getText().equals("Nombre Usuario")) 
            loginTemplate.getTNombreUsuario().setText("");
        // CAMBIA EL COLOR A GRIS DEL JPASSWORLDFIELD SI ESTA VACIÓ
        if(new String(loginTemplate.getTClaveUsuario().getPassword()).equals("")){
            loginTemplate.getTClaveUsuario().setBorder(
                loginTemplate.getRecursosService().getBorderInferiorGris()
            );
            loginTemplate.getTClaveUsuario().setForeground(
                loginTemplate.getRecursosService().getColorGrisOscuro()
            );
        }
        // CAMBIA EL COLOR A AZUL DEL JTEXTFIELD 
        loginTemplate.getTNombreUsuario().setBorder(
            loginTemplate.getRecursosService().getBorderInferiorAzul()
        );
        loginTemplate.getTNombreUsuario().setForeground(
            loginTemplate.getRecursosService().getColorAzul()
        );
    }
    if(e.getSource() == loginTemplate.getTClaveUsuario()){
        // BORRA EL PLACEHOLDER
        if(new String(loginTemplate.getTClaveUsuario().getPassword()).equals("clave Usuario")) 
            loginTemplate.getTClaveUsuario().setText("");
        // CAMBIA EL COLOR A GRIS DEL JTEXTFIELD SI ESTA VACIÓ
        if(loginTemplate.getTNombreUsuario().getText().equals("")){
            loginTemplate.getTNombreUsuario().setBorder(
                loginTemplate.getRecursosService().getBorderInferiorGris()
            );
            loginTemplate.getTNombreUsuario().setForeground(
                loginTemplate.getRecursosService().getColorGrisOscuro()
            );
        }
        // CAMBIA EL COLOR A AZUL DEL JPASSWORLDFIELD  
        loginTemplate.getTClaveUsuario().setBorder(
            loginTemplate.getRecursosService().getBorderInferiorAzul()
        );
        loginTemplate.getTClaveUsuario().setForeground(
            loginTemplate.getRecursosService().getColorAzul()
        );
    }
}
```

Ejecutamos nuestra aplicación para comprobar:

<div align='center'>
    <img  src='./resources/MouseEvent7.gif'>
    <p>Implementación de efectos hacia otros objetos</p>
</div>

Queda evidenciado en el anterior código que desde un objeto gráfico que ha activado el evento se pueden crear efectos a otros objetos gráficos ajenos a la activación del evento, incluso si se quisiera cambiar algún otro objeto que no tenga agregado la escucha de estos eventos como por ejemplo una imagen podría cambiar algún estado de estas a traves del objeto que activo el evento. 

Un ejemplo puntual de esto podría ser el cambio de la imagen del usuario (el icono que acompaña al JTextField), podríamos tener una imagen de color gris al inicio y una vez se de click en el JTexField **tNombreUsuario** podríamos indicar que la imagen cambie por la imagen azul que esta actualmente, aunque el **ImageIcon** no tiene la escucha del evento activada se puede cambiar su estado a traves del JTextField que activo el evento.

# Uso combinado de varios Métodos implementados de eventos

Algunos eventos del Mouse pueden compartir y complementar un comportamiento, un ejemplo son los eventos **mouseEntered y mouseExited** que en la mayoría de los casos están relacionados. También podría ser el caso de los métodos **mousePressed y mouseReleased** ya que el oprimir el botón del mouse muchas veces se ve relacionado con el momento en que este es soltado. Sin embargo la combinación de varios eventos que no están relacionados de alguna forma podrían darse para completar un comportamiento deseado.

En este caso queremos darle la propiedad de arrastre a nuestra ventana principal, hasta el momento tanto el login como la ventana principal se mantienen en una posición en la pantalla y nosotros no podemos moverla, esto muchas veces puede resultar algo incomodo y seria bueno añadir este comportamiento a nuestra ventana principal por lo menos. Cuando el usuario mantenga oprimido el botón del mouse sobre el espacio de la barra superior de la ventana este tendrá la posibilidad de arrastrar nuestra ventana. Vamos a hacerlo:

Nos posicionamos en el componente **barraTitulo** y específicamente en su clase **BarraTituloComponent** y vamos a implementar las interfaces **MouseListener** y **MouseMotionListener**.

<div align='center'>
    <img  src='https://i.imgur.com/jjRQfCJ.png'>
    <p>Implementación de interfaces en la clase BarraTituloComponent</p>
</div>

Como se ha visto reiteradas veces, es necesario la importación de los métodos implementados:

```javascript
// MÉTODOS MOUSELISTENER
@Override
public void mouseClicked(MouseEvent e) {}

@Override
public void mousePressed(MouseEvent e) {}

@Override
public void mouseReleased(MouseEvent e) {}

@Override
public void mouseEntered(MouseEvent e) {}

@Override
public void mouseExited(MouseEvent e) {}

// MÉTODOS MOUSEMOTIONLISTENER
@Override
public void mouseDragged(MouseEvent e) {}

@Override
public void mouseMoved(MouseEvent e) {}
```

Tenemos que configurar la adición de la escucha de este tipo de eventos en nuestra clase **BarraTituloTemplate**, como en este caso todo el panel escuchara el evento lo realizaremos en las configuraciones de la clase:
```javascript
// DENTRO DEL CONSTRUCTOR
this.addMouseListener(this.barraTituloComponent);
this.addMouseMotionListener(this.barraTituloComponent);
```

Ahora bien, el evento de arrastre se va a activar desde nuestro componente **barraTitulo**, sin embargo, como se va a mover toda la ventana principal el componente que se debe encargar en ultimas del movimiento de esta es la misma **vistaPrincipal**. Por este motivo vamos a entrar al código de la clase **VistaPrincipalComponent** y vamos a crear un método que se encargara de mover la ventana:

```javascript
// DENTRO DE LA CLASE VISTA PRINCIPAL COMPONENT
public void moverVentana(){
}
```

* Como vamos a modificar la posición de la ventana principal, es necesario que nuestro evento reciba por parámetros la nueva posición en X y la nueva posición en Y:

```javascript
// DENTRO DE LA CLASE VISTA PRINCIPAL COMPONENT
public void moverVentana(int posicionX, int posicionY){
}
```

* Ahora para cambiar de posición a la ventana principal debemos indicarle a la parte del componente que representa las características gráficas del mismo que va a tener una nueva locación, es decir la clase **VistaPrincipalTemplate** y lo haremos con el método **setLocation**:
```javascript
public void moverVentana(int posicionX, int posicionY){
    this.vistaPrincipalTemplate.setLocation();
}
```

* Por ultimo dentro del método es necesario indicarle la posición nueva por lo que ingresamos los parámetros recibidos **posicionX y posicionY**:
```javascript
public void moverVentana(int posicionX, int posicionY){
    this.vistaPrincipalTemplate.setLocation(posicionX, posicionY);
}
```

Ya tenemos listo nuestro método que se encarga de mover nuestra ventana, sin embargo para que el componente **barraTitulo** pueda comunicarse con la **vistaPrincipal** es necesario crear una comunicación Bidireccional, para esto debemos crear la inyección de dependencias entre estos dos.

En la clase **VistaPrincipalComponent** nos ubicamos en la **ejemplificación** del componente **BarraTitulo** previamente creada y dentro de esta ejemplificación vamos a enviar como argumento una referencia a esta misma clase:
```javascript
// DENTRO DEL CONSTRUCTOR
this.barraTituloComponent = new BarraTituloComponent(this);
```

Ahora nos ubicamos en nuestra clase **BarraTituloComponent** y obtenemos por parámetro la inyección de la **VistaPrincipal**:
* **Declaración:** 
```javascript
private VistaPrincipalComponent vistaPrincipalComponent;
```
* **Obtención de la inyección por parámetro:**
```javascript
public BarraTituloComponent(VistaPrincipalComponent vistaPrincipalComponent) {
        this.vistaPrincipalComponent = vistaPrincipalComponent;
        ...
}
```


Ya tenemos lista nuestra comunicación bidireccional, ahora vamos a configurar nuestros eventos para que este movimiento de la ventana principal sea posible. Seguimos en la clase **BarraTituloComponent**, ahora bien, en la sesión anterior nos dimos cuenta que el método **mouseDragged** se activa una vez el usuario mantiene el botón del mouse oprimido y a su vez va arrastrando el mouse. Justamente ese es el comportamiento que buscamos asi que es en este método donde codificaremos.

Dentro de este método implementado vamos a llamar al método **moverVentana** del la **VentanaPrincipal**:
```javascript
@Override
public void mouseDragged(MouseEvent e) {
    this.vistaPrincipalComponent.moverVentana();
}
```

Ahora, sabemos que este método exige por parámetros las nuevas coordenadas, recordando un poco la clase anterior habíamos hablado de dos tipos de coordenadas que podemos tomar:
* **getX()** Coordenadas con respecto al objeto gráfico que activo el evento.
* **getXOnScreen()** Coordenadas con respecto al monitor. 

Si lo pensamos bien, lo que queremos es mover nuestra ventana sobre el espacio del monitor por lo que en este caso estas son las coordenadas que podemos utilizar.

```javascript
@Override
public void mouseDragged(MouseEvent e) {
    this.vistaPrincipalComponent.moverVentana(e.getXOnScreen(), e.getYOnScreen());
}
```

Vamos a correr nuestra aplicación y ver que sucede:

<div align='center'>
    <img  src='./resources/MouseEvent8.gif'>
    <p>Intento de arrastre de la ventana utilizando el evento mouseDragged</p>
</div>

Podemos observar que efectivamente nuestra ventana puede ser arrastrada y dejada en la posicion que queramos, sin embargo hay un inconveniente, cada vez que oprimimos el botón del Mouse y arrastramos la ventana principal va a cambiar su posicion inmediatamente a donde esta el puntero y va a empezar a seguir al puntero desde la esquina superior izquierda. Esto se ve muy extraño y antinatural, veamos como podemos corregir este problema.

Para empezar, como nosotros le estamos indicando a nuestra ventana principal que su nueva posicion va a ser justamente donde se encuentra nuestro puntero del Mouse con respecto al Monitor esta inmediatamente se posicionara en esa locación, es por eso que se da ese movimiento antinatural y ademas esto provoca que la ventana este siguiendo al Mouse desde su esquina superior izquierda. 

Debemos tener en cuenta también la posición del puntero del Mouse con respecto a la ventana y ya veremos por que con un ejemplo:

Supongamos que el puntero del Mouse se encuentra en:
*  La posicion **300 en el eje X con respecto al Monitor** del usuario. 
* La posicion **100 en el eje X con respecto a la ventana**. 
* Nuestra ventana por su parte esta en la posicion **200 en el eje X con respecto al monitor**.

<div align='center'>
    <img  src='https://i.imgur.com/rHbSYjt.png'>
    <p>Ejemplo de posicionamiento con el eje X</p>
</div>

Estando en esa posicion el usuario oprime el botón del Mouse y mientras lo mantiene oprimido avanza 5 pixeles hacia la derecha, esto quiere decir que ahora esta en la posicion 305 **en el eje X con respecto al monitor**. Si le decimos a nuestra ventana que su nueva posicion sera la del puntero con respecto al monitor con el método **getXOnScreen()** su posicion en el eje X cambiara abruptamente **de 200 a 305** en cuestión de milisegundos. 

Si en cambio le decimos que su posicion va a ser igual a la posición del puntero con respecto al monitor **menos la posicion de este con respecto a la ventana** la nueva posicion de la ventana sera:
* **305 - 100 = 205**.

Podemos ver que la ventana avanzaría los 5 pixeles que buscamos a la derecha, esto es justo lo que buscamos y funciona de igual manera con el eje Y. Ahora vamos a implementar esta solución en nuestro código:

```javascript
@Override
    public void mouseDragged(MouseEvent e) {
        this.vistaPrincipalComponent.moverVentana(e.getXOnScreen() - e.getX(), e.getYOnScreen() - e.getY());
    }
```

Vamos a ejecutar nuestra aplicación a ver que sucede:

<div align='center'>
    <img  src='./resources/MouseEvent9.gif'>
    <p>Intento de arrastre de la ventana utilizando el evento mouseDragged</p>
</div>

Al parecer algo muy raro ha ocurrido, una vez oprimimos el botón del Mouse y arrastramos nuestra ventana desaparece, bueno esto en realidad ocurre por que la **posicion en el eje X con respecto a la ventana** esta mal configurada y esto confunde al programa, recordemos que el método **getX() o getY()** nos da la posicion desde la perspectiva del objeto gráfico que ha activado el evento, en este caso ha sido el componente **barraTitulo** y si recordamos este panel esta posicionado a 250 pixeles en el eje X de la ventana principal:

<div align='center'>
    <img  src='https://i.imgur.com/UfjZbNq.png'>
    <p>Posición en el eje X del componente panelBarra en la ventana principal</p>
</div>

Sin embargo el método **getX()** no esta tomando en cuenta estos 250 pixeles asi que hay que añadirlos, con respecto al eje Y no hay problemas ya que el componente inicia desde la posición 0 de nuestra Ventana principal.

```javascript
@Override
public void mouseDragged(MouseEvent e) {
    this.vistaPrincipalComponent.moverVentana(
        e.getXOnScreen() - (e.getX() + 250), e.getYOnScreen() - e.getY()
    );
}
```

Vamos a ejecutar nuestra aplicación una vez más a ver que sucede:

<div align='center'>
    <img  src='./resources/MouseEvent10.gif'>
    <p>Intento de arrastre de la ventana utilizando el evento mouseDragged</p>
</div>

Ahora tenemos otro problema, al parecer nuestra ventana no se mueve de nuevo aunque nosotros oprimamos el botón del Mouse y lo arrastremos. Esto se debe a que tanto **la posición con respecto al monitor** como **la posición con respecto a la ventana** se están actualizando constantemente, volviendo al anterior ejemplo, suponiendo estas mismas condiciones:

*  La posicion **300 en el eje X con respecto al Monitor** del usuario. 
* La posicion **100 en el eje X con respecto a la ventana**. 
* Nuestra ventana por su parte esta en la posicion **200 en el eje X con respecto al monitor**.

Si el usuario avanza 5 posiciones a la derecha y actualizamos la posición del puntero con respecto al monitor y de la posicion del puntero con respecto a la ventana ahora estas serán:
* **305 en el eje X con respecto al Monitor** del usuario. 
* **105 en el eje X con respecto a la ventana**. 

Y si restamos estos dos valores la nueva posición de la ventana sera entonces:
* **305 - 105 = 200**

Esto quiere decir que la posición no cambio en nada, ahora si el usuario se mueve otros 5 pixeles a la derecha y actualizamos ambas posiciones estas serán:

* **310 en el eje X con respecto al Monitor** del usuario. 
* **110 en el eje X con respecto a la ventana**. 

Si restamos estos dos nuevos valores la nueva posición de la ventana sera entonces:
* **310 - 110 = 200**

Es por eso que nuestra ventana nunca se mueve con esta solución y aquí se resalta algo muy importante **Se debe actualizar la posicion del puntero del Mouse con respecto al Monitor** pero **la posicion con respecto a la ventana solo se debe capturar una vez al inicio y no actualizarse más**. ¿Cómo podemos realizar esto? Si recordamos un poco nuestra clase anterior, el método **mousePressed** realiza una acción una vez el usuarió presiona el botón del Mouse y no le importa que pueda ocurrir de ahi en adelante, asi que podemos aprovechar esta propiedad.

Primero vamos a declarar dos atributos ya que serán necesarios para poder manipularlos desde diferentes métodos, estos representaran la posicion inicial tanto en X como en Y:

```javascript
private int posicionInicialX, posicionInicialY;
```

Ahora vamos al método **mousePressed** y vamos a indicar que una vez se oprima el botón del Mouse va a capturar la posición del puntero del Mouse **con respecto a la Ventana** y la va a guardar en los atributos anteriormente creados:

```javascript
@Override
public void mousePressed(MouseEvent e) {
    posicionInicialX = e.getX()+250;
    posicionInicialY = e.getY();
}
```

***Nota:** Recordamos que en el eje X se le suman los 250 pixeles que no tiene en cuenta el método **getX()** y que es la posicion del componente barraTitulo con respecto a la ventana Principal.*

Una vez ya tenemos nuestra posición inicial vamos a restarla con la posicion que se actualizara constantemente y es la posición con respecto al monitor:

```javascript
@Override
public void mouseDragged(MouseEvent e) {
    this.vistaPrincipalComponent.moverVentana(
        e.getXOnScreen() - posicionInicialX, e.getYOnScreen() - posicionInicialY
    );
}
```

Vamos a correr nuestra aplicación y ver que sucede:

<div align='center'>
    <img  src='./resources/MouseEvent11.gif'>
    <p>Implementación de arrastre de la ventana utilizando el evento mouseDragged y MousePressed</p>
</div>

En este caso se vio la importancia que tiene combinar varios métodos de eventos que pareciera que no tienen ninguna relación, cuando se tienen más acciones con los eventos seguramente habrán mas combinaciones entre estos para lograr lo que se quiere.

Para finalizar y para darle un toque mas a nuestra ventana vamos a agregarle el botón para **Minimizar** la pantalla ya que este es de vital importancia en cualquier aplicación de escritorio.

Vamos a la clase **BarraTituloTemplate** y vamos a crear este botón:

**Declaración:**
```javascript
private JButton bMinimizar;
```

**Construcción y adición:**
```javascript
// DENTRO DEL MÉTODO CREARJBUTTONS
iDimAux = new ImageIcon(
    sRecursos.getIMinimizar().getImage().getScaledInstance(28, 28, Image.SCALE_AREA_AVERAGING)
);
bMinimizar = sObjGraficos.construirJButton(
    null, 750, 10, 45, 30, sRecursos.getCMano(), iDimAux, null, 
    null, null, null, "c", false
);
bMinimizar.addActionListener(barraTituloComponent);
this.add(bMinimizar);
```
Podemos notar que hemos redimensionado una imagen que hemos traído a traves del servicio **RecursosService** y también que de una vez hemos añadido la escucha a los eventos tipo **ActionListener**.

Como ahora es necesario crear una discrimiación de objetos para los dos botones vamos a crear el método **get** para que los botones pueda ser obtenidos desde la clase **Component** de la **barraTitulo**:
```javascript
public JButton getBCerrar (){
    return bCerrar;
}

public JButton getBMinimizar (){
    return bMinimizar;
}
```

Ahora solo basta con configurarle el evento de acción, para esto nos ubicamos en la clase **BarraTituloComponent** en el método **ActionPerformed**:

* Como ahora hay dos botones que realizan acciones distintas debemos realizar una discriminación de objeto, esto llamando al objeto de la clase **Template** seguido del método **get** Correspondiente y comparándolo con el método **getSource** que nos indica que objeto ha activado el evento:

```javascript
@Override
public void actionPerformed(ActionEvent e) {
    if(e.getSource() == barraTituloTemplate.getBMinimizar())

    if(e.getSource() == barraTituloTemplate.getBCerrar())
        System.exit(0);
}
```

* Ahora como queremos minimizar toda la ventana de esto se debe encargar el componente **ventanaPrincipal** por lo que realizaremos un nuevo método encargado de esto dentro de la clase **VistaPrincipalComponent**:

```javascript
// Dentro de la clase VistaPrincipalComponent
public void minimizar(){
}
```

* Dentro de este vamos a indicarle a la ventana que se minimize, para esto llamamos al objeto de la clase Template y el siguiente método:

```javascript
public void minimizar(){
    this.vistaPrincipalTemplate.setExtendedState(Frame.ICONIFIED);
}
```

El anterior código usa el método:
* **setExtendedState:** Que recibe como parámetro un estado de la clase Frame y que cambia la perspectiva de la ventana haciendo posible acciónes como minimizar, expandir etc.
    * El Argumento **Frame.ICONIFIED** le indica a nuestra ventana que se minimize.

Es necesario para llamar a este estado importar la librería del **Frame** dentro de la clase **VistaPrincipalComponent**:
```javascript
import java.awt.Frame;
```

Ahora dentro de la clase **BarraTituloComponent** solo debemos llamar a ese método desde la discriminación realizada para el botón minimizar en el **ActionPerformed**:

```javascript
@Override
public void actionPerformed(ActionEvent e) {
    if(e.getSource() == barraTituloTemplate.getBMinimizar())
        vistaPrincipalComponent.minimizar();
    if(e.getSource() == barraTituloTemplate.getBCerrar())
        System.exit(0);
}
```

Y ya tenemos una ventana que se puede arrastrar a la posición que queramos y ademas se puede minimizar:

<div align='center'>
    <img  src='./resources/Minimizar.gif'>
    <p>Minimización de la ventana principal</p>
</div>

# Resultado

Si llegaste hasta aquí **!Felicidades!** has aprendido como utilizar los eventos del Mouse para darle mayor interactividad a nuestro proyecto aprendiste ademas varios aspectos importantes como **Representación única para objetos gráficos de una misma Clase** para representar un mismo comportamiento a varios objetos gráficos, **Discriminación de Clases** para los casos en que varios objetos de distintas clases quieren usar un mismo evento, **Efectos hacia otros objetos Gráficos** para cambiar cualquier parte de nuestra interfaz mediante un objeto que activa el evento y el **Uso combinado de varios Métodos implementados de eventos** para lograr ciertos comportamientos.

En la siguiente clase nos vamos a centrar nuevamente en la arquitectura del proyecto y esta vez vamos a hablar de los **Servicios**.

# Actividad

Implementar los eventos del Mouse a todo el proyecto para que este tenga mas interactividad con los usuarios.