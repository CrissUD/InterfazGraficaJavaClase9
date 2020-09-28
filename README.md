# Interfaz Gráfica en Java

Curso propuesto por el grupo de trabajo Semana de Ingenio y Diseño (**SID**) de la Universidad Distrital Francisco Jose de Caldas.

## Monitor

**Cristian Felipe Patiño Cáceres** - Estudiante de Ingeniería de Sistemas de la Universidad Distrital Francisco Jose de Caldas

# Clase 9

## Objetivos

* Identificar los usos principales de los eventos del Mouse para añadirle interactividad al proyecto de interfaz gráfica de usuario.
* Reconocer el enfoque de discriminación de clases para gestionar la interactividad con diferentes tipos de objetos gráficos a la vez desde un método implementado de eventos.
* Examinar el cambio indirecto de estado en objetos gráficos causado por la activación de un evento.
* Comprender el uso combinado de diferentes interfaces implementadas de eventos para realizar acciónes de interactividad a las interfaces gráficas.

# Antes de Comenzar

### **Actualización de Imágenes en recursos**
Para continuar con la lección deberá actualizar la carpeta **resources/images** ya que se han agregado nuevas imágenes. Estas las puede descargar en este mismo repositorio entrando a la carpeta **Clase9** seguido de **resources/images**.


### **Ajustes en el servicio Recursos Service**
* Se crea un nuevo color en el servicio **RecursosService**, y se recuerda el proceso de creación de un objeto decorador dentro de este servicio:

**Declaración:**
```javascript
private Color colorPrincipalOscuro;
```

**Ejemplificación:**
```javascript
// Dentro del método crearColores
colorPrincipalOscuro = new Color(30, 48, 90);
```

**Método get:**
```javascript
public Color getColorPrincipalOscuro() { return colorPrincipalOscuro; }
```

* También se crea un borde gris inferior para ser usados en los campos de texto:

**Declaración:**
```javascript
private Border bInferiorGris;
```

**Ejemplificación:**
```javascript
// Dentro del método crearBordes
bInferiorGris = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY);
```

**Método get:**
```javascript
public Border getBInferiorGris() { return bInferiorGris; }
```

***Nota:** Recordar que estos objetos decoradores dentro del servicio **RecursosService** se crean pensando en que serán utilizados en varias partes del proyecto, si ese no es el caso entonces el objeto decorador debe ser creado unicamente en el componente gráfico donde se necesita.*

### **Ajustes en el componente Login**

Dentro de esta lección se usarán nuevas imágenes para ilustrar los ejemplos del uso de eventos, dentro de la clase **LoginTemplate** se van a crear estas imágenes: 

**Declaración:**
```javascript
private ImageIcon iUsuario1, iClave1;
private ImageIcon iFacebook2, iTwitter2, iYoutube2;
```

* **Ejemplificación:**
```javascript
// Dentro del método crearObjetosDecoradores
iUsuario1 = new ImageIcon("Clase9/resources/images/usuario1.png");
iClave1 = new ImageIcon("Clase9/resources/images/clave1.png");
iFacebook2 = new ImageIcon("Clase9/resources/images/facebook2.png");
iTwitter2 = new ImageIcon("Clase9/resources/images/twitter2.png");
iYoutube2 = new ImageIcon("Clase9/resources/images/youtube2.png");
```

### **Recordatorio**
Recordando un poco el recorrido, se ha construido el componente **Configuraciones** el cual cuenta con la implementación de todos los **eventos del Mouse** y con el que el usuario puede interactuar para comprobar el funcionamiento correcto del Mouse. Gracias a esto se exploraron las principales características de cada uno de los eventos y se identificaron aspectos como la activación de estos, los momentos en que dejan de funcionar y casos particulares.

<div align='center'>
    <img  src='https://i.imgur.com/TZcDfdl.png'>
    <p>Componente Configuraciones con la implementación de todos los eventos de Mouse</p>
</div>

# Implementación de eventos de Mouse

En esta sesión se va a realizar la implementación de los diferentes eventos del Mouse dentro de distintas partes el proyecto, paralelamente se verán a ver algunas particularidades de importancia que están relacionadas con el uso de estos eventos:

* **Representación única para objetos gráficos de una misma Clase**.
* **Discriminación de Clases**.
* **Uso de MouseAdapter**.
* **Efectos hacia otros objetos Gráficos**.
* **Uso combinado de varias interfaces implementadas de eventos**.

# Representación única para objetos gráficos de una misma Clase

Para empezar se puede proporcionar algo de interactividad a los **botones** de la navegación ya que estos realmente están estáticos, una buena manera de hacer esto es cambiar el color de fondo una vez el usuario pase con el mouse por encima de ellos.

Dentro del componente **NavegaciónUsuario**, específicamente en la clase **NavegacionUsuarioComponent** se añade la implementación de la interfaz **MouseListener**:

<div align='center'>
    <img  src='https://i.imgur.com/ZDHg7Aa.png'>
    <p>implementación de MouseListener en el componente Navegación Usuario</p>
</div>

Recordar nuevamente que esta implementación exige que se implementen también los métodos que por defecto tiene la interfaz **MouseListener**:

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

Por otro lado, en la clase **NavegacionUsuarioTemplate** se va a añadir a los botones de la navegación la capacidad de escuchar los eventos de tipo **MouseListener**, para eso se usa el método **addMouseListener**:

```javascript
// En el respectivo espacio de configuración de cada objeto
this.bInicio.addMouseListener(navegacionUsuarioComponent);
this.bPerfil.addMouseListener(navegacionUsuarioComponent);
this.bAmigos.addMouseListener(navegacionUsuarioComponent);
this.bProductos.addMouseListener(navegacionUsuarioComponent);
this.bConfiguracion.addMouseListener(navegacionUsuarioComponent);
this.bCerrarSesion.addMouseListener(navegacionUsuarioComponent);
```

***Nota:** Recordar que en el anterior código se ven todas estas lineas juntas, sin embargo, estos son métodos de configuración por lo que cada una de ellas debe estar organizada junto al proceso de creación de su respectivo botón.*

Como la clase **NavegacionUsuarioComponent** ahora implementa a la interfaz **MouseListener** es posible ingresar como argumento al objeto que representa esta clase dentro del método **addMouseListener**.

Se va a probar el evento con un solo botón por ahora, para ello primero se debe realizar el método **get** del botón **bInicio** en la clase **Template** del componente:

```javascript
public JButton getBInicio(){ return this.bInicio; }
```

Ahora dentro de la clase **NavegaciónUsuarioComponent**, dentro del método **mouseEntered** se configura el cambio del color de fondo del botón **bInicio**.

* Primero se crea una discriminación por objetos, para esto se crea un condicional en el cual se pregunta si el objeto que ha activado el evento es igual al botón **bInicio**:

```javascript
public void mouseEntered(MouseEvent e) {
  if(e.getSource() == navegacionUsuarioTemplate.getBInicio()) {
  } 
}
```

* Para obtener el botón se debe usar el objeto de la clase **Template** y llamar al método **get** correspondiente: 

```javascript
public void mouseEntered(MouseEvent e) {
  if(e.getSource() == navegacionUsuarioTemplate.getBInicio()) {
    navegacionUsuarioTemplate.getBInicio()
  } 
}
```

* Una vez obtenido el botón se agrega el color de fondo, pero antes se deben habilitar las propiedades de contenedor de botón nuevamente ya que en el momento de su construcción se configuró este con características de transparencia por lo que si cambia simplemente el color no será reflejado:

```javascript
public void mouseEntered(MouseEvent e) {
  if(e.getSource() == navegacionUsuarioTemplate.getBInicio()) {
    navegacionUsuarioTemplate.getBInicio().setContentAreaFilled(true);
    navegacionUsuarioTemplate.getBInicio()
      .setBackground(RecursosService.getService().getColorPrincipalOscuro());
  } 
}
```
Note que para poder agregarle el color azul oscuro, se debe llamar al servicio **RecursosService**, en este caso se tomo el enfoque en el cual se llama directamente al servicio, se obtiene dicho servicio y se llama al método **get** correspondiente, aunque no es la única forma de hacerse, más adelante se verá otro enfoque igualmente valido.

El botón ahora cambiará de color a un **Azul mas oscuro** una vez se pasa sobre el, sin embargo, esto implica que se debe configurar la acción una vez se sale de la zona del botón para que este vuelva a la normalidad, para eso se configura el método **mouseExited**:

* Nuevamente se realiza una discriminación de objetos para indicar que se active solo cuando este salga del botón **bInicio**:

```javascript
public void mouseExited(MouseEvent e) {
  if(e.getSource() == navegacionUsuarioTemplate.getBInicio())
}
```

* Se obtiene el botón mediante el objeto de la clase **Template** y su método **get** correspondiente:
```javascript
public void mouseExited(MouseEvent e) {
  if(e.getSource() == navegacionUsuarioTemplate.getBInicio())
    navegacionUsuarioTemplate.getBInicio()
}
```

* Para que el botón vuelva a su estado normal solo hace falta con volver a configurar sus características de transparencia, de ese modo el botón eliminara cualquier color de fondo que contenga:
```javascript
public void mouseExited(MouseEvent e) {
  if(e.getSource() == navegacionUsuarioTemplate.getBInicio())
    navegacionUsuarioTemplate.getBInicio().setContentAreaFilled(false);
}
```

Una vez se ejecuta la la aplicación es posible ver el siguiente resultado:

<div align='center'>
    <img  src='./gifs/MouseEvent1.gif'>
    <p>Interacción con eventos a un solo botón</p>
</div>

***Nota:** Con este ejemplo se evidencia la importancia del uso del **Borde Vació** ya que cuando se pasa por encima del botón y este hace un cambio de color de fondo es posible notar que ni la imágen ni el texto están pegados con el borde del botón lo que hace que luzca estéticamente mucho mejor, en caso de no usar un borde vació la imágen escaria pegada con el borde de la izquierda.*

Ahora se podría realizar el anterior proceso  con los demás botones dentro de la navegación, sin embargo, note las siguientes particularidades:

* La anterior acción del cambio de color de fondo se quiere realizar con los demás botónes, es decir que a diferencia de un **ActionListener** donde cada botón generalmente busca realizar una acción distinta en estos eventos es común que varios objetos gráficos puedan compartir los mismos comportamientos.
* Esta navegación solo cuenta con 6 botones, sin embargo, si un menu contiene 20 botónes o incluso más, habría que crear una gran cantidad de código para representar el mismo comportamiento en cada botón lo cual hace que el código sea mas difícil de entender y mantener. Esto implicaría crear un método **get** dentro de la clase Template por cada botón, crear un nuevo condicional dentro de los métodos de los eventos para realizar la discriminación por objeto y realizar exactamente las mismas lineas de código por cada botón **resultando en demasiado código para representar la misma acción**.
* Una posible solución a pensar frente a esta situación es no realizar ninguna discriminación y escribir el código directamente en el método del evento, de esta forma todos los botónes que escuchen dicho evento realizarán el mismo comportamiento. Sin embargo, en esta ocasión no es posible ya que se están configurando características a un objeto gráfico en un tiempo determinado, esto quiere decir que a diferencia de mostrar un mensaje por pantalla el evento debe saber de algún modo a que botón se le cambiará el color y en que momento.

**¿Cómo se puede resolver esta situación?**. 

Aquí entra en juego la **Representación única para objetos gráficos de una misma Clase**. Para entender este concepto se va a implementar en el código a la par que se va explicando en el proceso.

Dentro de la clase **NavegacionUsuarioComponent**, específicamente en el método **mouseEntered** se crea una variable tipo **JButton** en este caso se llamará **boton**:

```javascript
public void mouseEntered(MouseEvent e) {
  JButton boton;
  // if(e.getSource() == navegacionUsuarioTemplate.getBInicio()){
  //     navegacionUsuarioTemplate.getBInicio().setContentAreaFilled(true);
  //     navegacionUsuarioTemplate.getBInicio().setBackground(RecursosService.getService().getColorPrincipalOscuro());
  // } 
}
```
Note que el resto del código se comento ya que no será necesario por ahora. Se implementa a continuación la **Representación única para objetos gráficos de una misma Clase**:

```javascript
public void mouseEntered(MouseEvent e) {
  JButton boton = ((JButton) e.getSource());
  // if(e.getSource() == navegacionUsuarioTemplate.getBInicio()){
  //     navegacionUsuarioTemplate.getBInicio().setContentAreaFilled(true);
  //     navegacionUsuarioTemplate.getBInicio().setBackground(RecursosService.getService().getColorPrincipalOscuro());
  // } 
}
```

En el anterior código se realiza lo siguiente:
* A traves del objeto de evento **e** se obtiene el objeto gráfico que ha activado el evento, esto mediante el método **getSource**.
* El método **getSource** va a retornar al objeto que activo el evento con el tipo de dato **Object** es decir la forma mas general del objeto, por eso es necesario especificar el tipo de clase a la cual pertenece el objeto que activo el evento. Para esto se realiza un **Cast** o cambio de tipado obligatorio de objeto, convirtiéndolo a un **JButton** en este caso.
* Una vez se convierte el objeto se iguala al objeto variable que se creó previamente.

Esto implica que la variable **boton** va tener la capacidad de representar cualquier botón que active el evento. Este es un concepto muy poderoso y una solución perfecta a la situación planteada. Ahora solo basta con indicarle al botón que le se va a cambiar el color de fondo:

***Nota:** Se puede borrar el anterior código comentado ya que no será necesario en el futuro.*

```javascript
public void mouseEntered(MouseEvent e) {
  JButton boton = ((JButton) e.getSource());
  boton.setContentAreaFilled(true);
  boton.setBackground(RecursosService.getService().getColorPrincipalOscuro()); 
}
```

Lo mismo se realiza con el método **mouseExited** para indicar que se dejarán los botones en su estado inicial una vez se sale de ellos:
```javascript
public void mouseExited(MouseEvent e) {
  JButton boton = ((JButton) e.getSource());
  boton.setContentAreaFilled(false);
}
```

***Nota:** También se puede borrar el método **get** del primer botón creado con anterioridad en la clase **NavegacionUsuarioTemplate** ya que no se usará.*

Ahora se ha representado e implementado el mismo comportamiento para varios botones en unas cuantas lineas de código y para comprobarlo se abre la aplicación:

<div align='center'>
    <img  src='./gifs/MouseEvent2.gif'>
    <p>Implementación de Representación única para objetos gráficos de una misma Clase</p>
</div>

# Uso de MouseAdapter

Se puede observar que en la clase **NavegacionUsuarioComponent** se implemento la interfaz **MouseListener** y esto trajo consigo la implementación automática de los 5 métodos que exige la interfaz. Sin embargo, en este componente solo se hizo uso de dos de los cinco métodos implementados haciendo que los métodos no usados ocupen código y estorben:

<div align='center'>
    <img  src='https://i.imgur.com/5uKF6Ed.png'>
    <p>Métodos sin usar que se implementaron de MouseListener</p>
</div>

El problema está en que como la interfaz **MouseListener** contiene estos métodos exige que se implementen cada uno de ellos, si se intenta borrar alguno de estos métodos el IDE o editor de código notificará un error. Para esto es posible hacer uso de una clase que proporciona Java llamada **MouseAdapter**, esta es una clase que tiene implementadas todas las interfaces relacionadas con los eventos del Mouse:

<div align='center'>
    <img  src='https://i.imgur.com/XDQ9Qew.png'>
    <p>Definición de la clase MouseAdapter proporcionada por Java.</p>
</div>

Esto quiere decir que ya tiene implementado todos los métodos que controlan los eventos del Mouse, gracias a esto podemos hacer uso de esta clase realizando una herencia y de esta forma es posible implementar a los eventos del Mouse que se necesiten sin la obligación de implementar otros métodos que realmente no serán untados. Dentro de la clase **NavegaciónUsuarioComponent** se procede a realizar esta acción:

<div align='center'>
    <img  src='https://i.imgur.com/wb4IVxi.png'>
    <p>Realizando herencia a la clase MouseAdapter</p>
</div>

Note que como **MouseAdapter** es una clase y no una interfaz se debe hacer una **Extension** de ella y no una **implementación**, también es necesario tener en cuenta que se debe importar la librería para soportar esta clase, por ultimo se puede observar que de todas formas es posible implementar de otras interfaces como es el caso de **ActionListener**.

Gracias a esto es posible borrar los métodos **mouseClicked, mousePressed y mouseReleased** sin que haya conflicto alguno quedando unicamente los eventos del Mouse que necesariamente serán usados.

# Discriminación de Clases

A continuación se va a realizar la configuración de eventos del mouse al componente **login**, para esto dentro de la clase **LoginComponent** específicamente en la declaración se va a extender de la clase **MouseAdapter** como se acabo de hacer con el anterior componente.

<div align='center'>
    <img  src='https://i.imgur.com/ubhYKQB.png'>
    <p>Extensión de la clase MouseAdapter en la clase LoginComponent</p>
</div>

En este caso solo se usarán los siguientes métodos de eventos de Mouse:
```javascript
@Override
public void mouseClicked(MouseEvent e) {}

@Override
public void mouseEntered(MouseEvent e) {}

@Override
public void mouseExited(MouseEvent e) {}
```

En este caso se quiere que al pasar sobre los botones **bEntrar** y **bRegistrarse** se cambie el color de fondo a un Azul más oscuro, pero también se quiere que al pasar por encima de los labels **lFacebook**, **lTwitter** y **lYoutube** se cambie la imágen que contienen los labels para darle un aspecto naranja. Para esto dentro de la clase **LoginTemplate** es necesario añadir la escucha de este tipo de eventos a estos objetos gráficos.

```javascript
bEntrar.addMouseListener(loginComponent);
bRegistrarse.addMouseListener(loginComponent);
lFacebook.addMouseListener(loginComponent);
lTwitter.addMouseListener(loginComponent);
lYoutube.addMouseListener(loginComponent);
```

***Nota:** Recordar que en el anterior código se ven todas estas lineas juntas, sin embargo, estos son métodos de configuración por lo que cada una de ellas debe estar organizada junto al proceso de creación de su respectivo botón y label.*

Ademas se van a crear nuevos métodos **get** que a continuación serán explicados:

* Se va a realizar un cambio de imágen desde la clase **LoginComponent** esto quiere decir que se debén obtener estas imágenes de algún modo, sin embargo para no realizar un método get por cada imágen y aprovechando el uso de eventos se crearán dos métodos get separados por dos categorías: Imágenes blancas, Imágenes Naranjas:
```javascript
public ImageIcon getIBlanca(JLabel label) {
  if (label == lFacebook) 
    iDimAux = new ImageIcon(
      iFacebook1.getImage()
        .getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)
    );
  if (label == lTwitter) 
    iDimAux = new ImageIcon(
      iTwitter1.getImage()
        .getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)
    );
  if (label == lYoutube) 
    iDimAux = new ImageIcon(
      iYoutube1.getImage()
        .getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)
    );
  return iDimAux;
}

public ImageIcon getINaranja(JLabel label) {
  if (label == lFacebook) 
    iDimAux = new ImageIcon(
      iFacebook2.getImage()
        .getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)
    );
  if (label == lTwitter) 
    iDimAux = new ImageIcon(
      iTwitter2.getImage()
        .getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)
    );
  if (label == lYoutube) 
    iDimAux = new ImageIcon(
      iYoutube2.getImage()
        .getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)
    );
  return iDimAux;
}
```
Note que ambos métodos reciben un Label como parámetro y de acuerdo a este devolverán la imágen correspondiente, dicha imágen se retorna escalada de una vez.

* Se va a disponer al servicio **RecursosService** para que la clase **LoginComponent** pueda interactuar con el servicio sin la necesidad de obtenerlo ni depender de el:
```javascript
public RecursosService getRecursosService(){ return sRecursos; }
```

Cuando se gestionaban eventos de acción mediante un **ActionListener** solo existía la  preocupación por realizar discriminación a nivel de objetos ya que solo se utilizaban a los botones para gestionar estos eventos. Sin embargo, cuando se quiere que diferentes objetos de distintos tipos hagan un comportamiento basado en un mismo evento es necesario realizar una **Discriminación de clases**. 
Tal es el caso actual donde se quiere que los botones y los Labels realicen una acción todos estos dentro de un **mouseEntered / mouseExited** (Al pasar y salir de la zona del objeto).

De hecho es posible simplemente realizar una discriminación por objetos y especificar la acción por cada botón y por cada label, sin embargo, en este caso todos los botónes realizarán un mismo comportamiento, mientras que los labels también tendrán su comportamiento similar, asi que de nuevo se crearía código de mas para repetir un mismo comportamiento.

Dentro del método **mouseEntered** se va a configurar el comportamiento para los botones y labels. Primero se realiza la **Discriminación de clases**.

* Primero se crean los condicionales necesarios para realizar la discriminación:

```javascript
@Override
public void mouseEntered(MouseEvent e) {
  if(/* Discriminación para botones */){
  }

  if(/* Discriminación para Labels */){
  }
}
```

* Ahora se va a obtener el objeto que ha activado el evento para ambos casos:

```javascript
@Override
public void mouseEntered(MouseEvent e) {
  if(e.getSource()){
  }
  
  if(e.getSource()){
  }
}
```

* A continuación se realiza la comparación con la clase:
```javascript
@Override
public void mouseEntered(MouseEvent e) {
  if(e.getSource() instanceof JButton){
  }

  if(e.getSource() instanceof JLabel){
  }
}
```
En el anterior código se esta realizando lo siguiente:
* Se obtiene el objeto que esta activando el evento.
* Mediante la palabra clave **instanceof** se esta preguntando si el objeto que ha activado el evento es una **ejemplificación** de la clase **JButton** o **JLabel**.

Ya se ha realizado la **Discriminación de clases** ahora en cada condición se puede escribir el código correspondiente. Primero se crean dos atributos en la clase **LoginComponent** para realizar una **Representación única para objetos gráficos de una misma Clase** tanto para un botón como para un label:

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
    boton.setBackground(loginTemplate.getRecursosService().getColorPrincipalOscuro()); 
  }
  if(e.getSource() instanceof JLabel){
    label = ((JLabel) e.getSource());
    label.setIcon(loginTemplate.getINaranja(label));
  }
}
```

Se pueden mencionar varios aspectos:

* Note que para realizar el cambio de color es necesario llamar al servicio **RecursosService**, en este caso se opto por el enfoque en el cual el recurso es pasado desde la clase **Template** y asi la clase **LoginComponent** no se ve en la necesidad de obtener el servicio, este enfoque al igual que el usado en el componente **NavegacionUsuario** es valido ya que al final de cuentas se esta utilizando un único objeto en memoria del servicio. 
* Para realizar el cambio de icono se llama a al método get **getINaranja** y este pide por parámetro un label asi que se enviá como argumento la representación única de los labels.
* También se puede observar que se realizó una **Representación única para objetos gráficos de una misma Clase** tanto para los botones como para los labels, ya que con el caso de los botones todos tenían el mismo comportamiento de cambiar de color de fondo, para los labels es el mismo caso cambiando la imágen. 
* Note además que solo se agrego la escucha de estos eventos a los botónes y labels que iban a tener un mismo comportamiento, es decir que botones como el **bCerrar** o **bOpcion 1,2 o 3** no están incluidos por que no se planea que realicen este comportamiento y lo mismo pasa con los labels que no se incluyeron.
* Se utilizo esta vez atributos en lugar de variables para la representación única de botónes y labels, sin embargo, tanto este enfoque como el de el uso de variables funciona de igual manera, ya que estos objetos no van a adicionar nada en memoria, estos objetos son conocidos como **Objetos contenedores** y lo único que hacen es apuntar en la memoria al objeto que ya fue previamente creado y que activo el evento.

Ahora se debe configurar el método **mouseExited** para que los objetos gráficos regresen a su estado inicial una vez se sale del area del objeto, realizamos de nuevo la **Discriminación de clases** y  **Representación única para objetos gráficos de una misma Clase**.

```javascript
@Override
public void mouseExited(MouseEvent e) {
  if(e.getSource() instanceof JButton){
    boton = ((JButton) e.getSource());
    boton.setBackground(loginTemplate.getRecursosService().getColorPrincipal());  
  } 
  if(e.getSource() instanceof JLabel){
    label = ((JLabel) e.getSource());
    label.setIcon(loginTemplate.getIBlanca(label));
  }
}
```

Una vez ejecutamos la aplicación podemos ver el resultado esperado:

<div align='center'>
    <img  src='./gifs/MouseEvent3.gif'>
    <p>Implementación de discriminación de clases en el componente Login</p>
</div>

# Efectos hacia otros objetos Gráficos

Cuando se usan eventos de Mouse también es posible generar efectos hacia otros objetos gráficos a traves de un objeto que activo el evento. Para explicar lo anterior planteado se van a generar unas modificaciones al componente **login** específicamente en la clase **LoginTemplate**:

* Primero se va a cambiar los bordes por defecto y el color de fuente de los campos de texto por grises usando el servicio **RecursosService**:
<div align='center'>
    <img  src='https://i.imgur.com/6w3T2gD.png'>
    <p>Cambio de bordes y color de texto por gris por defecto</p>
</div>

<div align='center'>
    <img  src='https://i.imgur.com/dIoWeDZ.png'>
    <p>Cambio de bordes y color de texto por gris por defecto</p>
</div>

* También se va a cambiar la imágen que viene por defecto en los labels **lUsuario y lClave**:
<div align='center'>
    <img  src='https://i.imgur.com/esera9s.png'>
    <p>Cambio de imágenes en los labels lUsuario y lClave</p>
</div>

El Login de usuario se ve ahora así:
<div align='center'>
    <img  src='https://i.imgur.com/r2EVgkA.png'>
    <p>Login de usuario con campos de texto e iconos que los acompañan de color gris</p>
</div>

* Además se crean nuevos método **get** que serán explicados a continuación:

```javascript
public JLabel getLabels(JTextField text) {
  if (text == tNombreUsuario) return lUsuario;
  if (text == tClaveUsuario) return lClave;
  return null;
}

public ImageIcon getIAzul(JLabel label) {
  if (label == lUsuario) 
    iDimAux = new ImageIcon(
      iUsuario2.getImage()
        .getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)
    );
  if (label == lClave) 
    iDimAux = new ImageIcon(
      iClave2.getImage()
        .getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)
    );
  return iDimAux;
}
```

* El método **getLabels** va a retornar el label correspondiente que acompaña a cada campo de texto, siendo el label **lUsuario** quien acompaña al TextField **tNombreUsuario** y el label **lClave** quien acompaña al passwordField **tClaveUsuario** para esto recibe como parámetro un TextField y esto es posible por que un objeto **JPasswordField** en realidad hereda de un **JTextField** asi que se puede tratar también como si fuere este ultimo. 
* El método **getIAzul** va a retornar las imágenes de color azul que corresponden al Label adecuado y para esto reciben un Label como parámetro, la imágen retornada esta redimensionada de una vez.

En este caso se quiere usar los eventos de Mouse para realizar lo siguiente:
* Una vez se oprima click en el campo de texto correspondiente, este cambie el color del borde y texto a color azul.
* A su vez cuando se oprima click sore el campo de texto este borre automáticamente el placeholder o texto que trae por defecto para que el usuario pueda escribir sus datos sin la engorrosa tarea de borrar el contenido que estaba antes.
* Adicionalmente y como item a resaltar se quiere cambiar la imágen del label que acompaña al campo de texto correspondiente una vez se oprima click en dicho campo.

Dentro de la clase **loginComponent**, específicamente en el método **MouseClicked** se inicia realizando una **discriminación por clases** para TextField, esto debido a varíos factores:
* Aprovechando que un **JPasswordField** puede ser tratado como un **JTextField** esta discriminación abarcará el comportamiento de los dos campos de texto que es justo lo que se quiere.
* Aunque dentro del método **MouseClicked** solo se van a manipular los campos de texto, es necesarió aclarar que ya existen ademas unos **botones y labels** que están escuchando a eventos tipo **MouseListener**, esto quiere decir que cada uno de estos botones o labels van a revisar el código presente en los 5 métodos que por defecto trae un **MouseListener** y esto incluye al **MouseClicked** y si por ejemplo se quiere cambiar alguna característica de un JTextField un Botón no va a entender esa linea de código y saltará un error. Por otro lado si hay una discriminación el objeto de botón no podrá entrar al código dentro de esa condición y por ende no habrá errores.
```javascript
@Override
public void mouseClicked(MouseEvent e) {
  if (e.getSource() instanceof JTextField) {
  }
}
```

Dentro de la condición se realiza ahora una **representación única** ya que el comportamiento deseado para ambos campos de texto es similar, para esto se crea un nuevo atributo tipo **JTextField**:

**Declaración de objeto**:
```javascript
// Al Inicio de la Clase
private JTextField text;
```
**Representación Única:**
```javascript
@Override
public void mouseClicked(MouseEvent e) {
  if (e.getSource() instanceof JTextField) {
    text = ((JTextField) e.getSource());
  }
}
```

* Lo primero que se configurará es el placeholder en los campos de texto para que el contenido sea borrado cada vez el usuario de click sobre el campo. Para esto basta con llamar al método **setText** y dentro colocar unas comillas vaciás representando que ahora el texto que contendrá estará vació:

```javascript
@Override
public void mouseClicked(MouseEvent e) {
  if (e.getSource() instanceof JTextField) {
    text = ((JTextField) e.getSource());
    text.setText("");
  }
}
```

Él anterior código funciona, sin embargo, existe un problema, si el usuario se posiciona en el JTextField de nombre de usuario se borrara lo que esta escrito previamente y el usuario podrá escribir tranquilamente su nombre de usuario, pero si mas adelante se da cuenta que se equivoco en una pequeña parte y vuelve a dar click al JTextField **tNombreUsuario** para corregir dicha parte de lo que escribió, este de nuevo se borrara completamente:

<div align='center'>
    <img  src='./gifs/MouseEvent4.gif'>
    <p>Primera prueba de la implementación de MouseClicked</p>
</div>

Para corregir esto se debe indicar que solamente se borrará el contenido de la caja de texto cuando el contendió sea el **placeholder** es decir el contenido inicial que viene por defecto, para el caso del JTextField **tNombreUsuario** es *"Nombre Usuario"* y para el JPasswordField **tClaveUsuario** es *"Clave Usuario"*, con ayuda de un atributo tipo arreglo podemos realizar esta condición:
```javascript
private String[] placeholders = { "Nombre Usuario", "Clave Usuario" };
```

Gracias a este arreglo que contiene los placeholder es posible crear una condición preguntando si el campo de texto contiene alguno de esos placeholder, en ese caso sea borrado su contenido:
```javascript
@Override
public void mouseClicked(MouseEvent e) {
  if (e.getSource() instanceof JTextField) {
    text = ((JTextField) e.getSource());
    if (
      text.getText().equals(placeholders[0]) || 
      text.getText().equals(placeholders[1])
    ) 
      text.setText("");
  }
}
```

Se comprueba ejecutando la aplicación:

<div align='center'>
    <img  src='./gifs/MouseEvent5.gif'>
    <p>Arreglo de placeholder en el Login de usuario</p>
</div>

Lo siguiente a configurar es el cambio de color dentro del campo de texto una vez se oprima click sobre el, como ya se tiene la representación única, basta con indicarle el cambio a este objeto:

```javascript
@Override
public void mouseClicked(MouseEvent e) {
  if (e.getSource() instanceof JTextField) {
    text = ((JTextField) e.getSource());
    text.setForeground(loginTemplate.getRecursosService().getColorPrincipal());
    text.setBorder(loginTemplate.getRecursosService().getBInferiorAzul());
    if (
      text.getText().equals(placeholders[0]) || 
      text.getText().equals(placeholders[1])
    ) 
      text.setText("");
  }
}
```

<div align='center'>
    <img  src='./gifs/MouseEvent6.gif'>
    <p>Cambio de color a caja de texto donde se escribe actualmente</p>
</div>

Finalmente para dar un toque más de interactividad se configura el cambio de imágen a azul para el label que acompaña al campo de texto, primero se debe obtener el label correspondiente dependiendo del textField que active el evento, para esto se usa el método **getLabels** de la clase **LoginTemplate**:
```javascript
@Override
public void mouseClicked(MouseEvent e) {
  if (e.getSource() instanceof JTextField) {
    text = ((JTextField) e.getSource());
    label = loginTemplate.getLabels(text);
    text.setForeground(loginTemplate.getRecursosService().getColorPrincipal());
    text.setBorder(loginTemplate.getRecursosService().getBInferiorAzul());
    if (
      text.getText().equals(placeholders[0]) || 
      text.getText().equals(placeholders[1])
    ) 
      text.setText("");
  }
}
```
Note que para obtener el label se debe enviar por parámetro un JTextField y para esto se enviá como argumento a la representación única de los campos de texto, ademas el objeto que se usa para representar al label es el atributo antes utilizado **label** una vez se ha obtenido el label, se puede hacer el cambio de imágen con ayuda del método **getIAzul** creado en el **LoginTemplate**:
```javascript
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
```

Se ejecuta la aplicación para comprobar la funcionalidad del evento:

<div align='center'>
    <img  src='./gifs/MouseEvent7.gif'>
    <p>Implementación de efectos hacia otros objetos</p>
</div>

Queda evidenciado en el anterior código que se puede cambiar el estado de otros objetos gráficos incluso si estos ni siquiera están a la escucha de eventos del Mouse, en este caso los campos de texto **tNombreUsuario o tClaveUsuario** activaron el evento para realizar cambios de estado en si mismos pero a su vez realizaron cambios en el estado de los labels **lUsuario y lClave** note que ninguno de estos dos labels tiene activada la escucha a ningún evento pero gracias a la activación de otro objeto gráfico se cambio el estado de estos.

# Uso combinado de varias interfaces implementadas de eventos

Algunos eventos del Mouse pueden compartir y complementar un comportamiento, un ejemplo son los eventos **mouseEntered y mouseExited** que en la mayoría de los casos están relacionados. También podría ser el caso de los métodos **mousePressed y mouseReleased** ya que el oprimir el botón del mouse muchas veces se ve relacionado con el momento en que este es soltado. Sin embargo, puede existir también combinaciones entre eventos de mouse que normalmente no están relacionados y que incluso pueden ser de diferentes interfaces.

En este caso se quiere dar la propiedad de arrastre a la ventana principal, hasta el momento tanto el login como la ventana principal se mantienen en una posición en la pantalla y el usuario no es capaz moverla, esto muchas veces puede resultar algo incomodo, sería bueno para la experiencia del usuario añadir este comportamiento a la ventana principal. Cuando el usuario mantenga oprimido el botón del mouse sobre el espacio de la barra superior de la ventana tendrá la posibilidad de arrastrarla.

Se procede a configurar el componente **barraTitulo**, específicamente su clase **BarraTituloComponent** y se va a extender de la clase **MouseAdapter**.

<div align='center'>
    <img  src='https://i.imgur.com/IOHHLZs.png'>
    <p>Extension de la clase MouseAdapter</p>
</div>

Esta vez se van a implementar los siguientes métodos:

```javascript
// MÉTODOS MOUSELISTENER
@Override
public void mousePressed(MouseEvent e) {}

// MÉTODOS MOUSEMOTIONLISTENER
@Override
public void mouseDragged(MouseEvent e) {}
```

Note que los métodos a usar son de diferentes interfaces siendo **mousePressed** parte de la interfaz **MouseListener** mientras que **mouseDragged** es parte de **MouseMotionListener**. 

Se debe configurar la adición de la escucha de estos tipos de eventos en la clase **BarraTituloTemplate**, como en este caso todo el panel escuchará a los eventos se realizan en las configuraciones de la clase:
```javascript
// DENTRO DEL CONSTRUCTOR
this.addMouseListener(barraTituloComponent);
this.addMouseMotionListener(barraTituloComponent);
```

Ahora bien, el evento de arrastre se va a activar desde el componente **barraTitulo**, sin embargo, como se va a mover toda la ventana principal el componente que se debe encargar en ultima instancia del movimiento de esta es la misma **vistaPrincipal**. Por este motivo se va a entrar al código de la clase **VistaPrincipalComponent** y se crea un método que se encargara de mover la ventana:

```javascript
// DENTRO DE LA CLASE VISTA PRINCIPAL COMPONENT
public void moverVentana(){
}
```

* Como se modificará la posición de la ventana principal, es necesario que el evento reciba por parámetros la nueva posición en X y la nueva posición en Y:
```javascript
// DENTRO DE LA CLASE VISTA PRINCIPAL COMPONENT
public void moverVentana(int posicionX, int posicionY){
}
```

* Ahora para cambiar de posición a la ventana principal se debe indicar a la parte del componente que representa las características gráficas del mismo que va a tener una nueva locación, es decir la clase **VistaPrincipalTemplate** y se realiza con el método **setLocation**:
```javascript
public void moverVentana(int posicionX, int posicionY){
    this.vistaPrincipalTemplate.setLocation();
}
```

* Por ultimo dentro del método es necesario indicarle la posición nueva por lo que se ingresan los parámetros recibidos **posicionX y posicionY**:
```javascript
public void moverVentana(int posicionX, int posicionY){
    this.vistaPrincipalTemplate.setLocation(posicionX, posicionY);
}
```

El método que se encarga de mover la ventana esta listo y además ya existe una comunicación bidireccional entre los componentes **vistaPrincipal y barraTitulo**, ahora se procede a configurar los eventos para que el movimiento de la ventana principal sea posible. En la sesión anterior se pudo observar que el método **mouseDragged** se activa una vez el usuario mantiene el botón del mouse oprimido y a su vez va arrastrando el mouse. Justamente ese es el comportamiento que se busca asi que es en este método donde se configura el comportamiento.

Dentro de este método se va a llamar al método **moverVentana** del la **VentanaPrincipal**:
```javascript
@Override
public void mouseDragged(MouseEvent e) {
  this.vistaPrincipalComponent.moverVentana();
}
```

Ahora, se sabe que este método exige por parámetros las nuevas coordenadas, recordando un poco la sesión anterior se habían mencionado dos tipos de coordenadas que se pueden obtener:
* **getX()** Coordenadas con respecto al objeto gráfico que activo el evento.
* **getXOnScreen()** Coordenadas con respecto al monitor. 

Como en este caso se quiere mover la ventana sobre el espacio del monitor es necesario obtener las coordenadas con la perspectiva de toda la pantalla.
```javascript
@Override
public void mouseDragged(MouseEvent e) {
  this.vistaPrincipalComponent.moverVentana(
    e.getXOnScreen(), 
    e.getYOnScreen()
  );
}
```

Al correr la aplicación sucede lo siguiente:

<div align='center'>
    <img  src='./gifs/MouseEvent8.gif'>
    <p>Intento de arrastre de la ventana utilizando el evento mouseDragged</p>
</div>

Se puede observar que efectivamente la ventana puede ser arrastrada y movida en la posicion que se quiere, sin embargo, hay un inconveniente, cada vez que se oprime el botón del Mouse y se arrastra la ventana principal, esta va a cambiar su posicion inmediatamente a donde esta el puntero del Mouse y va a empezar a seguir al puntero desde la esquina superior izquierda. Esto se ve muy extraño y poco natural, debe haber algún modo de corregir esto.

Para empezar, como se está indicando a la ventana principal que su nueva posicion va a ser justamente donde se encuentra el puntero del Mouse con respecto al Monitor esta inmediatamente se posicionara en esa locación, es por eso que se da ese movimiento antinatural y ademas esto provoca que la ventana este siguiendo al Mouse desde su esquina superior izquierda. 

Se debe tener en cuenta también la posición del puntero del Mouse con respecto a la ventana y para comprobar esto se verá un ejemplo:

Suponga que el puntero del Mouse se encuentra en:
*  La posicion **300px en el eje X con respecto al Monitor** del usuario. 
* La posicion **100px en el eje X con respecto a la ventana**. 
* La ventana por su parte esta en la posicion **200px en el eje X con respecto al monitor**.

<div align='center'>
    <img  src='https://i.imgur.com/Rxkqwel.png'>
    <p>Ejemplo de posicionamiento con el eje X</p>
</div>

Con estas condiciones iniciales, suponga que el usuario oprime el botón del Mouse y mientras lo mantiene oprimido avanza 5 pixeles hacia la derecha, esto quiere decir que ahora el puntero esta en la posicion 305px **en el eje X con respecto al monitor**. Si se le indica a la ventana que su nueva posicion será la del puntero con respecto al monitor con el método **getXOnScreen()** su posicion en el eje X cambiara abruptamente **de 200px a 305px** en cuestión de milisegundos. 

Si en cambio se le indica que su posicion va a ser igual a la posición del puntero con respecto al monitor **menos la posicion inicial del mismo con respecto a la ventana** la nueva posicion de la ventana sera:
* **305px - 100px = 205px**.

Se puede ver que la ventana avanzaría los 5 pixeles que corresponde a la derecha, esto es justo lo que se busca, esta lógica funciona de igual manera con el eje Y. Ahora se va a implementar esta solución en el código:

```javascript
@Override
public void mouseDragged(MouseEvent e) {
  this.vistaPrincipalComponent.moverVentana(
    e.getXOnScreen() - e.getX(), 
    e.getYOnScreen() - e.getY()
  );
}
```

Al ejecutar la aplicación sucede lo siguiente:

<div align='center'>
    <img  src='./gifs/MouseEvent9.gif'>
    <p>Intento de arrastre de la ventana utilizando el evento mouseDragged</p>
</div>

Al parecer algo muy raro ha ocurrido, una vez se oprime el botón del Mouse y se arrastra la ventana, esta desaparece de inmediato, bueno esto en realidad ocurre por que la **posicion en el eje X con respecto a la ventana** esta mal configurada y esto confunde al programa, recuerde que el método **getX() o getY()** da la posicion desde la perspectiva del objeto gráfico que ha activado el evento, en este caso ha sido el componente **barraTitulo** y si se entra a detalle este panel esta posicionado a 250px en el eje X de la ventana principal:

<div align='center'>
    <img  src='https://i.imgur.com/UfjZbNq.png'>
    <p>Posición en el eje X del componente panelBarra en la ventana principal</p>
</div>

Sin embargo, el método **getX()** no esta tomando en cuenta estos 250px asi que hay que añadirlos, con respecto al eje Y no hay problemas ya que el componente inicia desde la posición 0 de la Ventana principal.

```javascript
@Override
public void mouseDragged(MouseEvent e) {
  this.vistaPrincipalComponent.moverVentana(
    e.getXOnScreen() - (e.getX() + 250), 
    e.getYOnScreen() - e.getY()
  );
}
```

Al ejecutar la aplicación una vez más sucede lo siguiente:

<div align='center'>
    <img  src='./gifs/MouseEvent10.gif'>
    <p>Intento de arrastre de la ventana utilizando el evento mouseDragged</p>
</div>

Ahora existe otro problema, al parecer la ventana no se mueve de nuevo aunque se este oprimiendo el botón del Mouse y se arrastre. Esto se debe a que tanto **la posición con respecto al monitor** como **la posición con respecto a la ventana** se están actualizando constantemente, volviendo al anterior ejemplo, suponiendo estas mismas condiciones:

*  La posicion **300px en el eje X con respecto al Monitor** del usuario. 
* La posicion **100px en el eje X con respecto a la ventana**. 
* La ventana por su parte esta en la posicion **200px en el eje X con respecto al monitor**.

Si el usuario avanza 5 posiciones a la derecha y se actualiza la posición del puntero con respecto al monitor y también la posicion del puntero con respecto a la ventana ahora estas serán:
* **305 en el eje X con respecto al Monitor** del usuario. 
* **105 en el eje X con respecto a la ventana**. 

Y si se restan estos dos valores la nueva posición de la ventana será entonces:
* **305 - 105 = 200**

Esto quiere decir que la posición no cambio en nada, ahora si el usuario se mueve otros 5 pixeles a la derecha y se actualizan ambas posiciones estas serán:

* **310 en el eje X con respecto al Monitor** del usuario. 
* **110 en el eje X con respecto a la ventana**. 

Si se restan nuevamente estos dos nuevos valores la nueva posición de la ventana será entonces:
* **310 - 110 = 200**

Es por esto que la ventana nunca se mueve con la solución antes planteada y aquí se resalta algo muy importante **Se debe actualizar la posicion del puntero del Mouse con respecto al Monitor** con cada movimiento del Mouse pero **la posicion con respecto a la ventana solo se debe capturar una vez al inicio y no actualizarse más**. 

**¿Cómo es posible realizar esto?**

Si se recuerda la lección anterior, el método **mousePressed** realiza una acción una vez el usuarió presiona el botón del Mouse y no le importa que pueda ocurrir de ahi en adelante, asi que se puede aprovechar esta propiedad.

Primero se declaran dos atributos ya que será necesario poder manipularlos desde diferentes métodos, estos representaran la posicion inicial tanto en X como en Y:

```javascript
private int posicionInicialX, posicionInicialY;
```

Ahora en el método **mousePressed** y se va a indicar que una vez se oprima el botón del Mouse va a capturar la posición del puntero del Mouse **con respecto a la Ventana** y la va a guardar en los atributos anteriormente creados:

```javascript
@Override
public void mousePressed(MouseEvent e) {
  posicionInicialX = e.getX()+250;
  posicionInicialY = e.getY();
}
```

***Nota:** Recordar que en el eje X se le suman los 250 pixeles que no tiene en cuenta el método **getX()** y que es la posicion del componente barraTitulo con respecto a la ventana Principal.*

Una vez ya se tiene la posición inicial se debe restar con la posicion que se actualizará constantemente y es la posición con respecto al monitor:

```javascript
@Override
public void mouseDragged(MouseEvent e) {
  this.vistaPrincipalComponent.moverVentana(
      e.getXOnScreen() - posicionInicialX, 
      e.getYOnScreen() - posicionInicialY
  );
}
```

Una vez se correr la aplicación sucede lo siguiente:

<div align='center'>
    <img  src='./gifs/MouseEvent11.gif'>
    <p>Implementación de arrastre de la ventana utilizando el evento mouseDragged y MousePressed</p>
</div>

En este caso se vio la importancia que tiene combinar varios métodos de eventos que pareciera que no tienen ninguna relación, incluso entre eventos que hacen parte de diferentes interfaces de escucha de eventos. Cuando se tienen más acciones con los eventos seguramente habrán más combinaciones entre estos para lograr efectos avanzados.

# Resultado

Si llegaste hasta aquí **!Felicidades!** se ha aprendido como utilizar los eventos del Mouse para darle mayor interactividad al proyecto, se aprendió ademas varios aspectos importantes como **Representación única para objetos gráficos de una misma Clase** para representar un mismo comportamiento a varios objetos gráficos, **Uso de la clase MouseAdapter** para el uso de métodos que unicamente serán utilizados, **Discriminación de Clases** para los casos en que varios objetos de distintas clases quieren usar un mismo evento, **Efectos hacia otros objetos Gráficos** para cambiar cualquier parte de la interfaz mediante un objeto que activa el evento y el **Uso combinado de varias interfaces implementadas de eventos** para lograr ciertos comportamientos corelacionados.

La siguiente clase se va a centrar nuevamente en la arquitectura del proyecto y esta vez se va a explicar que son los **Servicios** y el uso de **Servicios Lógicos**.

# Actividad

Implementar los eventos del Mouse a todo el proyecto para que este tenga una mayor interactividad con los usuarios.