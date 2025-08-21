package org.pioupiou.model;

public class Sprite {

    private int[][] pixels;
    private int xcoord;
    private int ycoord;
    private int height;

    public Sprite(int[][] pixels, int xcoord, int ycoord, int height) {
        this.pixels = pixels;
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.height = height;
    }

    public int[][] getPixels() {
        return pixels;
    }

    public int getXcoord() {
        return xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }

    public int getHeight() {
        return height;
    }

    public void setPixels(int[][] pixels) {
        this.pixels = pixels;
    }

    public void setXcoord(int xcoord) {
        this.xcoord = xcoord;
    }

    public void setYcoord(int ycoord) {
        this.ycoord = ycoord;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
