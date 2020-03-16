package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {


    public static void main(String[] args) {
        try{
            File block = new File("C:/Users/jakob/Documents/Programme/MinecraftGeneratorRaw/crafting_table_top.png");
            BufferedImage image = ImageIO.read(block);
            ImageIO.write(image, "bmp",new File("C:/Users/jakob/Documents/Programme/MinecraftGeneratorRaw/output.bmp"));
            System.out.println("Done");
        }
        catch (Exception e){
            System.out.println("Error");
        }
    }
}