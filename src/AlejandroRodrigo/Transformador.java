package AlejandroRodrigo;

import java.io.IOException;
import java.util.List;

public class Transformador{
    private String programa;

    public static void main(String[] args) throws IOException {
        Transformador proceso = new Transformador(args[2]); //args[2]
        TransformadorTexto procesoConcreto = proceso.seleccion_proceso();
        procesoConcreto.introducirLineas(Integer.parseInt(args[0]),Integer.parseInt(args[1])); //Integer.parseInt(args[0]),Integer.parseInt(args[1])
        procesoConcreto.transformador(EjercicioLibro.lecturaArchivo(Integer.parseInt(args[0]),Integer.parseInt(args[1])));
    }

    public Transformador(String programa){
        this.programa=programa;
    }

    public TransformadorTexto seleccion_proceso(){
        switch (this.programa){
            case "minus" -> {return new Minusculas();}
            case "mayus" -> {return new Mayusculas();}
            case "paco" -> {return new Reemplazador();}
            default -> {return null;}
        }
    }
}
