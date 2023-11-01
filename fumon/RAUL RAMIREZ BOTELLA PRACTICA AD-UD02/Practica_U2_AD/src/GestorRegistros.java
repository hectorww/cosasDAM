import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class GestorRegistros {
    private File archivo; // Representa el archivo de registros.
    private List<Indice> indices = new ArrayList<>(); // Lista de índices para registros.
    private long posicionActual = 0; // Para mantener un seguimiento de la posición actual al agregar registros.

    // Constructor de la clase GestorRegistros.
    public GestorRegistros(String ruta) {
        archivo = new File(ruta, "registro.txt");
        if (!archivo.exists()) {
            System.out.println("El archivo no existe. Creando archivo con datos por defecto...");
            crearArchivoPorDefecto();
        }
    }

    // Método para agregar un nuevo registro al archivo.
    public void agregarRegistro(Registro registro) {
        Scanner scanner = new Scanner(System.in);
        boolean claveValida = false;

        do {
            String claveNueva = registro.getCodigo();
            boolean claveExiste = verificarClaveExistente(claveNueva);

            if (claveExiste) {
                System.out.println("La clave ya está en uso. Por favor, ingrese una clave diferente:");
                claveNueva = scanner.nextLine();
                registro.setCodigo(claveNueva); // Actualiza la clave en el registro.
            } else {
                claveValida = true;
            }
        } while (!claveValida);
        try (FileWriter fw = new FileWriter(archivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            if (archivo.length() > 0) {
                bw.newLine(); // Agregar un salto de línea si el archivo no está vacío.
            }
            bw.write(registro.getCodigo() + "," + registro.getNombre() + "," + registro.getApellido());
        } catch (IOException e) {
            System.err.println("Error al agregar un nuevo registro al archivo. Por favor, inténtelo de nuevo más tarde.");
            e.printStackTrace();
        }
    }
    // Método para verificar si una clave (código) ya existe en el archivo de registros.
    public boolean verificarClaveExistente(String clave) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Dividir la línea en campos utilizando ',' como delimitador.
                String[] campos = linea.split(",");

                // Verificar que la línea tenga al menos 3 campos y que el primer campo coincida con la clave proporcionada.
                if (campos.length >= 3 && campos[0].trim().equals(clave.trim())) {
                    return true; // La clave ya existe en el archivo.
                }
            }
        } catch (IOException e) {
            System.err.println("Error al verificar la clave.");
            e.printStackTrace();
        }
        return false; // La clave no existe en el archivo.
    }

    // Método para crear un archivo por defecto con registros de ejemplo.
    public void crearArchivoPorDefecto() {
        try (FileWriter fw = new FileWriter(archivo);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write("0001,Raul,Botella");
            bw.newLine();
            bw.write("0002,Alvarito,Galvez");
            bw.newLine();
            bw.write("0003,Javier,Roca");
            System.out.println("Se ha creado un archivo por defecto con registros de ejemplo.");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo por defecto.");
            e.printStackTrace();
        }
    }

    // Método para recuperar un registro por su código.
    public Registro recuperarRegistro(String codigoBuscar) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 3 && campos[0].trim().equals(codigoBuscar.trim())) {
                    return new Registro(campos[0], campos[1], campos[2]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al recuperar el registro.");
            e.printStackTrace();
        }
        return null; // Retornar null si el registro no se encuentra.
    }

    // Método para modificar un registro.
    public void modificarRegistro(String codigoModificar, Registro nuevoRegistro) {
        List<String> lineas = new ArrayList<>();
        boolean encontrado = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 3 && campos[0].equals(codigoModificar)) {
                    lineas.add(nuevoRegistro.getCodigo() + "," + nuevoRegistro.getNombre() + "," + nuevoRegistro.getApellido());
                    encontrado = true;
                    System.out.println("Registro modificado con éxito.");
                } else {
                    lineas.add(linea);
                }
            }

            if (!encontrado) {
                System.out.println("Registro no encontrado.");
            } else {
                try (PrintWriter writer = new PrintWriter(archivo)) {
                    for (String nuevaLinea : lineas) {
                        writer.println(nuevaLinea);
                    }
                } catch (IOException e) {
                    System.err.println("Error al escribir en el archivo.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al modificar el registro.");
            e.printStackTrace();
        }

        if (encontrado && !codigoModificar.equals(nuevoRegistro.getCodigo())) {
            actualizarIndice(codigoModificar, nuevoRegistro.getCodigo());
        }
    }

    // Método para actualizar un índice después de modificar un registro.
    public void actualizarIndice(String claveAntigua, String claveNueva) {
        // Recorre la lista de índices y actualiza la clave si se encuentra.
        for (Indice indice : indices) {
            if (indice.getClave().equals(claveAntigua)) {
                indice.setClave(claveNueva);
                break; // Rompe el bucle una vez que se actualiza el índice.
            }
        }
    }

    // Método para mostrar los códigos de registros disponibles.
    public void mostrarCodigosRegistros() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 3 && !campos[0].equalsIgnoreCase("Código")) {
                    System.out.println("Código: " + campos[0]);
                    System.out.println(); // Separación en blanco entre registros.
                }
            }
        } catch (IOException e) {
            System.err.println("Error al mostrar los códigos de registros.");
            e.printStackTrace();
        }
    }

    // Método para mostrar todos los registros insertados sin los marcados como borrados.
    public void mostrarRegistrosInsertados() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("Registros insertados:");
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().startsWith("#")) { // Omitir registros marcados como borrados.
                    String[] campos = linea.split(",");
                    if (campos.length >= 3) {
                        System.out.println("Código: " + campos[0]);
                        System.out.println("Nombre: " + campos[1]);
                        System.out.println("Apellido: " + campos[2]);
                        System.out.println(); // Separación en blanco entre registros.
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al mostrar los registros insertados.");
            e.printStackTrace();
        }
    }

    // Método para marcar los registros como borrados.
    public void marcarRegistroComoBorrado(String codigoBorrar) {
        List<String> lineas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean encontrado = false;
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 3 && campos[0].equals(codigoBorrar)) {
                    lineas.add("#" + linea);
                    encontrado = true;
                    System.out.println("Registro marcado como borrado con éxito.");
                } else {
                    lineas.add(linea);
                }
            }

            if (!encontrado) {
                System.out.println("Registro no encontrado.");
            } else {
                try (PrintWriter writer = new PrintWriter(archivo)) {
                    for (String nuevaLinea : lineas) {
                        writer.println(nuevaLinea);
                    }
                } catch (IOException e) {
                    System.err.println("Error al escribir en el archivo.");
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al marcar el registro como borrado.");
            e.printStackTrace();
        }
    }

    // Método para mostrar registros marcados como borrados.
    public void mostrarRegistrosMarcadosComoBorrados() {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("Registros marcados como borrados:");
            while ((linea = reader.readLine()) != null) {
                String[] campos = linea.split(",");
                if (campos.length >= 3 && campos[0].startsWith("#")) {
                    System.out.println("Código: " + campos[0].substring(1));
                    System.out.println("Nombre: " + campos[1]);
                    System.out.println("Apellido: " + campos[2]);
                    System.out.println();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al mostrar los registros marcados como borrados.");
            e.printStackTrace();
        }
    }

    // Método para compactar el archivo eliminando registros marcados como borrados.
    public void compactarArchivo() {
        File archivoTemporal = new File(archivo.getParent(), "registro_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo));
             BufferedWriter writer = new BufferedWriter(new FileWriter(archivoTemporal))) {

            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().startsWith("#")) {
                    writer.write(linea);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error al compactar el archivo.");
            e.printStackTrace();
        }

        // Renombrar el archivo original a "registro.bak".
        File archivoBak = new File(archivo.getParent(), "registro.bak");
        archivo.renameTo(archivoBak);

        // Renombrar el archivo temporal a "registro.txt".
        archivoTemporal.renameTo(archivo);

        // Actualizar los índices si es necesario.
        actualizarIndicesDespuesDeCompactar();

        System.out.println("Archivo compactado exitosamente.");
    }

    // Método para actualizar los índices después de compactar el archivo.
    public void actualizarIndicesDespuesDeCompactar() {
        List<Indice> nuevosIndices = new ArrayList<>();
        long nuevaPosicion = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().startsWith("#")) {
                    String[] campos = linea.split(",");
                    if (campos.length >= 3) {
                        String clave = campos[0];
                        Indice nuevoIndice = new Indice();
                        nuevoIndice.setClave(clave);
                        nuevoIndice.setPosicion(nuevaPosicion);
                        nuevosIndices.add(nuevoIndice);
                    }
                }
                nuevaPosicion += linea.length() + System.lineSeparator().length(); // Actualizar la posición.
            }
        } catch (IOException e) {
            System.err.println("Error al actualizar los índices después de compactar.");
            e.printStackTrace();
        }

        indices.clear();
        indices.addAll(nuevosIndices);
    }
}