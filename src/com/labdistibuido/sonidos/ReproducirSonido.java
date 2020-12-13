package com.labdistibuido.sonidos;

import javax.sound.sampled.Clip;

public class ReproducirSonido {
    private Clip clip;

    public ReproducirSonido(Clip clip) {
        this.clip = clip;
    }

    public void reproducir() {
        clip.setFramePosition(0);
        clip.start();
    }

    public void parar() {
        clip.stop();
    }

}
