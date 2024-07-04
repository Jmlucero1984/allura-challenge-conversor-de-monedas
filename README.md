# alura-challenge-conversor-de-monedas
Segundo desafío de Java Backend de Alura Latam - Conversor de monedas implementando API

## 🎯 Funcionalidades implementadas
- Menú de opciones listando las operaciones más recurrentes del usuario
- Ordenamiento de las opciones según ranking de solicitudes (información almacenada en un archivo JSON local)
- Posibilidad de elegir otras monedas de orígen y destino de la conversión a través de un menú ampliado, que toma de un JSON local las opciones disponibles y sus nomenclaturas
- Incorporación al ranking de operaciones más solicitadas a las definidas a traves del menú ampliado
- Ruta de acceso a la API configurable a través de un JSON local
- Seguimiento de las operaciones históricas
- EXTRA: Utilidad para convertir un listado de monedas en texto plano tomado de la web de la API a formato JSON para su incorporación en la aplicación

## 🎥 Captura de Video

https://github.com/Jmlucero1984/allura-challenge-conversor-de-monedas/assets/91501518/4a0f65c2-a70a-4397-b430-e7a29a940755

## 📷 Screenshots

config.json: Se define la URL de la API y se cargan el resto de las monedas disponibles

![Config](https://github.com/Jmlucero1984/allura-challenge-conversor-de-monedas/blob/4cb2e5200785e212905d5f241c5c30b8bb006c9a/config.JPG)

currencies.txt: Modelo del listado en texto tomado de la web de la API para transformarlo a JSON a través de una utilidad provista en el código fuente de esta aplicación

![Currencies](https://github.com/Jmlucero1984/allura-challenge-conversor-de-monedas/blob/798e5bf087e42da3d032b2b3287c37b4d498faee/currencies.jpg)

topRequested.json: Contiene el listado de las opciones (no todas las operaciones) elegidas hasta el momento con el dato de la cantidad de veces que han sido solicitadas para establecer su orden de aparición en el menu principal de acuerdo a este criterio de rankeo.

![Raking](https://github.com/Jmlucero1984/allura-challenge-conversor-de-monedas/blob/798e5bf087e42da3d032b2b3287c37b4d498faee/topRequested.JPG)

history.json: Contiene el historial de todas las operaciones realizadas, incluyendo día y hora, monedas de orígen y destino, importe ingresado, el ratio de conversión y el importe final luego de la conversión.

![History](https://github.com/Jmlucero1984/allura-challenge-conversor-de-monedas/blob/798e5bf087e42da3d032b2b3287c37b4d498faee/history.JPG)

## 📂 Ejecución del JAR a través de una desktop CLI 

Al momento de la confección de este README queda pendiente la implementación de la posibilidad para el usuario de configurar la ruta de guardado de los JSON locales y el despliegue automático de los archivos requeridos mínimos al momento de la primera ejecución si no se contara con los mismos en el entorno donde se ejecuta la aplicación.
Por ende, para la ejecución de esta apliación por medio de una linea de comandos se sugiere copiar el contenido de la carpeta "resources", con su ruta completa, es decir, una carpeta src, que contenga a otra main y esta a su vez a resources, en el mismo directorio donde se encuente el paquete jar.
La ejecución se realiza mediante los comandos:   java -jar AlluraConversorDeMoneda.jar

![JAR](https://github.com/Jmlucero1984/allura-challenge-conversor-de-monedas/blob/0166c153b307153fc4d9120249cc4eacbde6cfd5/jar.JPG)
