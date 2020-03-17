package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

class Node {
    int r;
    int g;
    int b;
    File graphic;

    public Node(int a, int b, int c, File picture) {
        r = a;
        g = b;
        b = c;
        graphic = picture;
    }
}

public class Main {


    public static void main(String[] args) {
        BufferedImage image = null;
        try{
            File block = new File("C:/Users/jakob/Documents/Programme/MinecraftGeneratorRaw/crafting_table_green.png");
            image = ImageIO.read(block);
            /*int width = image.getWidth();
            int height = image.getHeight();
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    System.out.println("x,y: " + j + ", " + i);
                    int pixel = image.getRGB(j, i);
                    printPixelARGB(pixel);
                    System.out.println("");
                }
            }
            System.out.println(width);
            ImageIO.write(image, "bmp",new File("C:/Users/jakob/Documents/Programme/MinecraftGeneratorRaw/output.bmp"));*/
            averageRGB(image);
            LinkedList <Node>linky = new LinkedList<Node>();
            linky.addFirst(new Node(2, 22, 25, block));
            Node n = new Node(2, 22, 25, block);
            linky.addFirst(n);
            System.out.println(linky.get(0).g);
            System.out.println("Done");
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }

    public static void averageRGB(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();
        int pixel;
        int allPixels = width*height;
        int R = 0;
        int G = 0;
        int B = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixel = image.getRGB(j, i);
                R = R + ((pixel >> 16) & 0xff);
                G = G + ((pixel >> 8) & 0xff);
                B = B + ((pixel) & 0xff);
            }
        }
        R = R / allPixels;
        G = G / allPixels;
        B = B / allPixels;
        System.out.println("R " + R + ", G " + G + ", B " + B);
    }

    public static void printPixelARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
    }
}