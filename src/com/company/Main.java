package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.io.File;
import java.awt.Graphics2D;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;
import java.util.LinkedList;

class pixelData {
    int r;
    int g;
    int b;
    File graphic;

    public pixelData(int R, int G, int B, File texture) {
        r = R;
        g = G;
        b = B;
        graphic = texture;
    }
}

public class Main {
    public static void main(String[] args) {
        BufferedImage pixelArtOutputImage = null;
        BufferedImage pixelartInputImage = null;
        int pixel;
        int R,G,B;
        int deviation;
        int smallestDeviation = 0;
        File bestFitting = null;
        BufferedImage bestFittingImage = null;
        double loading;
        try{
            File texturFile = new File("./inputTextures");

            File[] textureFileArray = texturFile.listFiles();
            LinkedList <pixelData>pixelList = new LinkedList<pixelData>();  //Linked List welche die RGB Werte und File Paths speichert
            for(int i=0; i<textureFileArray.length;i++){            //Einlesen der Average RGB Werte der Minecraft Texturen
                pixelList.add(averageRGB(textureFileArray[i]));
            }

            File pixelArtInput = new File("./pixelArtInput.png");     //Input file
            pixelartInputImage = ImageIO.read(pixelArtInput);
            int widthPixelart = pixelartInputImage.getWidth();
            int heightPixelart = pixelartInputImage.getHeight();
            pixelArtOutputImage = new BufferedImage(16*widthPixelart,16*heightPixelart,BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < heightPixelart; i++) {
                for (int j = 0; j < widthPixelart; j++) {
                    pixel = pixelartInputImage.getRGB(j, i);
                    R = ((pixel >> 16) & 0xff);
                    G = ((pixel >> 8) & 0xff);
                    B = ((pixel) & 0xff);
                    for(int k = 0; k < pixelList.size(); k++){
                        deviation = Math.abs(R-pixelList.get(k).r) + Math.abs(G-pixelList.get(k).g) + Math.abs(B-pixelList.get(k).b);   //Abweichung von der Textur ausrechnen
                        if(k==0){
                            smallestDeviation = deviation;              //Init
                            bestFitting = pixelList.get(k).graphic;
                        }
                        else{
                            if(deviation < smallestDeviation){
                                smallestDeviation = deviation;
                                bestFitting = pixelList.get(k).graphic; //Speichern der besten Textur
                            }
                        }
                    }
                    bestFittingImage = ImageIO.read(bestFitting);
                    Graphics2D g2 = pixelArtOutputImage.createGraphics();
                    g2.drawImage(bestFittingImage,null,j*16,i*16);  //An die entsprechende Stelle die Textur kopieren
                    g2.dispose();
                }
                loading = ((double)i / (double)heightPixelart) * 100;
                loading = Math.round(loading);
                System.out.println("Abgeschlossen: " + loading + "%" );
            }
            ImageIO.write(pixelArtOutputImage, "png", new File("./logoOutput.png"));     //Output file
            System.out.println("Done");
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }

    public static pixelData averageRGB(File block){
        BufferedImage image = null;
        try {
            image = ImageIO.read(block);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        int width = image.getWidth();
        int height = image.getHeight();
        int pixel;
        int allPixels = width*height;
        int R = 0;
        int G = 0;
        int B = 0;
        try {
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
            pixelData values = new pixelData(R, G, B, block);
            return values;
        }
        catch (Exception e){
            pixelData values = new pixelData(0, 0,0, null);
            return values;
        }
    }

    public static void printPixelARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
    }
}