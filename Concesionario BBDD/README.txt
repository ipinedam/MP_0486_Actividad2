- Para crear el entorno de BB.DD. que necesita este proyecto, hay que ejecutar el fichero
  "Concesionario.sql" situado en la carpeta "sql".
  
- Se ha asumido, por lógica, que en la tabla de COCHES no se debe permitir matrículas duplicadas.
  Es decir, no podrá haber dos (o más) coches que tengan la misma matrícula. En la práctica 
  se controla esta posibilidad dando los mensajes oportunos y controlando el acceso a la BB.DD.
  
- Igualmente, también se ha asumido que en la tabla de PERSONAS no se debe permitir nombres duplicados.
  Es decir, no podrá haber dos (o más) personas que tenga el mismo nombre. También se controla 
  en la práctica esta circunstancia.
  
- Por último, se ha asumido que un pasajero no pueda estar en más de un coche. Un coche podrá
  contener "n" pasajeros, pero un pasajero no podrá estar en más de 1 coche. Esto también se 
  controla en la práctica.
  
- Cuando uno o varios pasajeros están asignados a un coche, no podrá borrarse el coche 
  hasta que todas las relaciones (coche->pasajero) hayan sido también borradas.

- Siguiendo las instrucciones de la práctica, el punto de entrada de ejecución es
  "Principal.java", que a su vez llama (cuando corresponde) a "GestionPasajeros.java".
  No obstante, a efectos de prueba, tanto "Principal.java" como "GestionPasajeros.java" pueden 
  invocarse de forma independiente.
  
 - En el directorio "test" de la aplicación se ha creado el fichero de lotes "Concesionario BBDD.bat" que 
  ejecuta la clase Principal.java

- Aunque la práctica estaba pensada para dos personas, está práctica ha sido realizada sólo por una.