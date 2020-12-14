package com.labdistibuido.sonidos;

import javax.sound.sampled.Clip;

public class EfectosDeSonido {
    public static Clip title, select, start, walk1, walk2, bomb, explosion, powerup, dead, endgame;

    public static void init() {
        title = Cargador.cargarSonido("src\\data\\soundeffects\\title.wav");
        select = Cargador.cargarSonido("src\\data\\soundeffects\\select.wav");
        start = Cargador.cargarSonido("src\\data\\soundeffects\\start.wav");
        walk1 = Cargador.cargarSonido("src\\data\\soundeffects\\walk1.wav");
        walk2 = Cargador.cargarSonido("src\\data\\soundeffects\\walk2.wav");
        bomb = Cargador.cargarSonido("src\\data\\soundeffects\\bomb.wav");
        explosion = Cargador.cargarSonido("src\\data\\soundeffects\\explosion.wav");
        powerup = Cargador.cargarSonido("src\\data\\soundeffects\\powerup.wav");
        dead = Cargador.cargarSonido("src\\data\\soundeffects\\dead.wav");
        endgame = Cargador.cargarSonido("src\\data\\soundeffects\\endgame.wav");
    }
}
