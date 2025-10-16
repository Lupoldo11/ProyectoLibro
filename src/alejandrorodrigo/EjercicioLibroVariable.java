package alejandrorodrigo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EjercicioLibroVariable {

    public static String direccion_proceso = "alejandrorodrigo.Transformador"; //luego aquí +Transformador
    public static File salida = new File("salidaLibroAlicia.txt");

    public static List<String> lecturaArchivo(int line_init, int line_final) throws IOException {
        Path path = Paths.get("../../../src/alice29.txt").toAbsolutePath();//"../../../src/alice29.txt" (normal url)
        List<String> totalLineas = Files.readAllLines(path); //src/alice29.txt (test_url)

        return totalLineas.subList(line_init,line_final+1);
    }

    public static Process lanzadorProcesos(Integer line_init, Integer line_final, String programa, int num_proceso) throws InterruptedException, IOException {
        ProcessBuilder pb = new ProcessBuilder("java", direccion_proceso, line_init.toString(), line_final.toString(), programa);
        pb.directory(new File("./out/production/ProyectoLibro"));
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(new File("salida"+String.valueOf(num_proceso)+".txt")));
        return pb.start();
    }

    public static String randomProceso(){
        switch ((int) (Math.random() * 3)){
            case 0 -> {return "minus";}
            case 1 -> {return "mayus";}
            case 2 -> {return "paco";}
            default -> {return null;}
        }
    }

    public static int[] listTrozos(int numProcesos) throws IOException {
        Path path = Paths.get("src/alice29.txt").toAbsolutePath();
        List<String> totalLineas = Files.readAllLines(path);
        int numInit= 0;
        int[] salida= new int[numProcesos+1];
        for(int i = 0; i <= numProcesos; i++){
            salida[i] = numInit;
            numInit += (totalLineas.size()/numProcesos);
        }
        return salida;
    }

    public static List<Process> lista_procesos = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        new File("salidaLibroAlicia.txt").delete();
        //Thread.sleep(2000);
        int[] numLineas= listTrozos(Integer.parseInt(args[0]));//6
        int i;
        for (i=0; i < 6; i++){
            lista_procesos.add(lanzadorProcesos(numLineas[i],numLineas[i+1]-1,randomProceso(),i));
        }
        escritura(i);
    }

    public static void escritura(int num_archivos) throws IOException, InterruptedException {
        for (Process p: lista_procesos){
            p.waitFor();
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("salidaLibroAlicia.txt"))){
            for(int i = 0; i < num_archivos; i++){
                File tempFile = new File("salida"+i+".txt");
                try(BufferedReader reader = new BufferedReader(new FileReader(tempFile))){
                    String linea;
                    while ((linea = reader.readLine())!=null){
                        writer.write(linea);
                        writer.newLine();
                    }
                    reader.close();
                }
            }
            writer.close();
        }
        for(int i =0; i< num_archivos; i++){
            new File("salida"+i+".txt").delete();
        }
    }
}