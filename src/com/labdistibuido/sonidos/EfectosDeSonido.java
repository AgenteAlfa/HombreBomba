package com.labdistibuido.sonidos;

import javax.sound.sampled.Clip;

import java.awt.image.*;

public class EfectosDeSonido {
    public static Clip title, select, start, walk1, walk2, bomb, explosion, powerup, dead, endgame;

    // Graficos
    public static BufferedImage player1;

    public static void init() {
        title = Cargador.cargarSonido("/home/billy/Documents/Programación Concurrente y Distribuida/Lab_02/Proyecto_Bomberman/HombreBomba-Mezclado1/src/data/soundeffects/title.wav");
        select = Cargador.cargarSonido("/home/billy/Documents/Programación Concurrente y Distribuida/Lab_02/Proyecto_Bomberman/HombreBomba-Mezclado1/src/data/soundeffects/select.wav");
        start = Cargador.cargarSonido("/home/billy/Documents/Programación Concurrente y Distribuida/Lab_02/Proyecto_Bomberman/HombreBomba-Mezclado1/src/data/soundeffects/start.wav");
        walk1 = Cargador.cargarSonido("/home/billy/Documents/Programación Concurrente y Distribuida/Lab_02/Proyecto_Bomberman/HombreBomba-Mezclado1/src/data/soundeffects/walk1.wav");
        walk2 = Cargador.cargarSonido("/home/billy/Documents/Programación Concurrente y Distribuida/Lab_02/Proyecto_Bomberman/HombreBomba-Mezclado1/src/data/soundeffects/walk2.wav");
        bomb = Cargador.cargarSonido("/home/billy/Documents/Programación Concurrente y Distribuida/Lab_02/Proyecto_Bomberman/HombreBomba-Mezclado1/src/data/soundeffects/bomb.wav");
        explosion = Cargador.cargarSonido("/home/billy/Documents/Programación Concurrente y Distribuida/Lab_02/Proyecto_Bomberman/HombreBomba-Mezclado1/src/data/soundeffects/explosion.wav");
        powerup = Cargador.cargarSonido("/home/billy/Documents/Programación Concurrente y Distribuida/Lab_02/Proyecto_Bomberman/HombreBomba-Mezclado1/src/data/soundeffects/powerup.wav");
        dead = Cargador.cargarSonido("/home/billy/Documents/Programación Concurrente y Distribuida/Lab_02/Proyecto_Bomberman/HombreBomba-Mezclado1/src/data/soundeffects/dead.wav");
        endgame = Cargador.cargarSonido("/home/billy/Documents/Programación Concurrente y Distribuida/Lab_02/Proyecto_Bomberman/HombreBomba-Mezclado1/src/data/soundeffects/endgame.wav");

        // Graficos
        player1 = Cargador.cargarImagen("/data/sheets/P1stand.png");
    }
}
