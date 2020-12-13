package com.labdistibuido.escenario;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.labdistibuido.sonidos.EfectosDeSonido;
import com.labdistibuido.sonidos.ReproducirSonido;

public class Ventana extends JFrame implements Runnable {
    private Canvas canvas;
    private Thread thread;
    private boolean corriendo;

    private BufferStrategy bs;
    private Graphics g;

    private final int FPS = 60;
    private double tiempoObjetivo = 1000000000/FPS;
    private double delta = 0;
    private double FPSPROMEDIO = FPS;

    public Ventana() {
        setTitle("Hombre Bomba");
        setSize(Constantes.ANCHO, Constantes.ALTO);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(Constantes.ANCHO, Constantes.ALTO));
        canvas.setMaximumSize(new Dimension(Constantes.ANCHO, Constantes.ALTO));
        canvas.setMinimumSize(new Dimension(Constantes.ANCHO, Constantes.ALTO));
        canvas.setFocusable(true);

        add(canvas);
        setVisible(true);
    }

    public void init() {
        EfectosDeSonido.init();
    }

    public void actualizar() {
        // estadoJuego.actualizar();
        // teclado.actualizar();
    }

    private void dibujar() {
        bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        /*------------ Dibujar Gráficos ------------*/
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Constantes.ANCHO, Constantes.ALTO);

        // estadoJuego.dibujar()
        

        g.setColor(Color.WHITE);
        g.drawString("FPS: " + FPSPROMEDIO, 0, 10);
        /*------------------------------------------*/
        g.dispose();
        bs.show();        
    }

    @Override
    public void run() {
        long ahora = 0;
        long ultimoTiempo = System.nanoTime();
        long tiempo = 0;
        int cuadros = 0;        

        init();

        /*---- Ejemplo sonido (cargando) ----*/
        long tiempoTitulo = 0;
        ReproducirSonido title = new ReproducirSonido(EfectosDeSonido.title);
        /*------------------------*/

        while (corriendo) {
            ahora = System.nanoTime();
            delta += (ahora - ultimoTiempo)/tiempoObjetivo;
            tiempo += ahora - ultimoTiempo;
            ultimoTiempo = ahora;
            
            if (delta > 1) {
                actualizar();
                dibujar();
                delta--;
                cuadros++;
            }

            if (tiempo > 1000000000) {
                FPSPROMEDIO = cuadros;
                cuadros = 0;
                tiempo = 0;

                /*---- Ejemplo sonido (reproduciendo) ----*/
                tiempoTitulo++;
                if (tiempoTitulo == 2) {
                    title.reproducir();
                }
                /*----------------------------------------*/
            }        

        }

        parar();

    }

    public void inicia() {
        thread = new Thread(this);
        thread.start();
        corriendo = true;
    }

    private void parar() {
        try {
            thread.join();
            corriendo = false;            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
