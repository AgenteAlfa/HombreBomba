package com.labdistibuido.sonidos;

import javax.sound.sampled.Clip;

public class EfectosDeSonido {
    public static Clip title, select, start, walk1, walk2, bomb, explosion, powerup, dead, endgame;

    public static void init() {
        title = Cargador.cargarSonido("/soundeffects/title.wav");
        select = Cargador.cargarSonido("/soundeffects/select.wav");
        start = Cargador.cargarSonido("/soundeffects/start.wav");
        walk1 = Cargador.cargarSonido("/soundeffects/walk1.wav");
        walk2 = Cargador.cargarSonido("/soundeffects/walk2.wav");
        bomb = Cargador.cargarSonido("/soundeffects/bomb.wav");
        explosion = Cargador.cargarSonido("/soundeffects/explosion.wav");
        powerup = Cargador.cargarSonido("/soundeffects/powerup.wav");
        dead = Cargador.cargarSonido("/soundeffects/dead.wav");
        endgame = Cargador.cargarSonido("/soundeffects/endgame.wav");
    }
}
