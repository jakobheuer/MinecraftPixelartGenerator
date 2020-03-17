package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.Component;
import java.awt.image.BufferedImage;
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
        BufferedImage pixelArtOutput = null;
        int pixel;
        int R,G,B;
        try{
            File block = new File("C:/Users/jakob/Documents/Programme/MinecraftGeneratorRaw/crafting_table_green.png");
            File texturFile = new File("C:/Users/jakob/Documents/Programme/MinecraftGeneratorRaw/inputTextures");

            File[] textureFileArray = texturFile.listFiles();
            LinkedList <pixelData>pixelList = new LinkedList<pixelData>();
            for(int i=0; i<textureFileArray.length;i++){            //Einlesen der Average RGB Werte
                pixelList.add(averageRGB(textureFileArray[i]));
            }

            File pixelArtInput = new File("C:/Users/jakob/Documents/Programme/MinecraftGeneratorRaw/pixelArtInput.png");
            LinkedList <pixelData>pixelListInput = new LinkedList<pixelData>();
            pixelListInput.add(averageRGB(pixelArtInput)); //Einlesen der Average RGB Werte
            pixelArtOutput = ImageIO.read(pixelArtInput);
            int widthPixelart = pixelArtOutput.getWidth();
            int heightPixelart = pixelArtOutput.getHeight();
            for (int i = 0; i < heightPixelart; i++) {
                for (int j = 0; j < widthPixelart; j++) {
                    pixel = pixelArtOutput.getRGB(j, i);
                    R = ((pixel >> 16) & 0xff);
                    G = ((pixel >> 8) & 0xff);
                    B = ((pixel) & 0xff);
                    pixelListInput.add(new pixelData(R, G, B, pixelArtInput));
                }
            }

            //BufferedImage output = new BufferedImage(16*)
            System.out.println(pixelList.get(1).g);
            System.out.println(pixelListInput.get(1).g);
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
            System.out.println("R " + R + ", G " + G + ", B " + B);
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