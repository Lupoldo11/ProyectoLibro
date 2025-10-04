package AlejandroRodrigo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class EjercicioLibroVariable {

    //public static int num_procesos = 5;
    //public static int[] lines_init = {0,721,1443,2164,2885,3609};
    //public static String[] list_procesos = {"mayus","minus","paco","minus","mayus"};
    public static String direccion_proceso = "AlejandroRodrigo.Transformador"; //luego aquí +Transformador
    public static File salida = new File("salidaLibroAlicia.txt");

    public static List<String> lecturaArchivo(int line_init, int line_final) throws IOException {
        Path path = Paths.get("../../../src/alice29.txt").toAbsolutePath();//"../../../src/alice29.txt" (normal url)
        List<String> totalLineas = Files.readAllLines(path); //src/alice29.txt (test_url)

        return totalLineas.subList(line_init,line_final+1);
    }

    public static void lanzadorProcesos(Integer line_init, Integer line_final, String programa) throws InterruptedException, IOException {
        ProcessBuilder pb = new ProcessBuilder("java", direccion_proceso, line_init.toString(), line_final.toString(), programa);
        pb.directory(new File("./out/production/ProyectoLibro"));
        pb.redirectOutput(ProcessBuilder.Redirect.appendTo(salida));
        Process p = pb.start();
        p.waitFor();
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
    public static void main(String[] args) throws IOException, InterruptedException {
        if (salida.exists()) salida.delete();
        //Thread.sleep(2000);
        int[] numLineas= listTrozos(Integer.parseInt(args[0]));
        for (int i = 0; i < Integer.parseInt(args[0]); i++){
            lanzadorProcesos(numLineas[i],numLineas[i+1]-1,randomProceso());
        }
    }
}