package fr.audentia.protect.domain.model;

public class House {

    public final int id;
    public final int price;
    public final int level;
    public final Location block1Location;
    public final Location block2Location;
    public final Location signLocation;
    public final char blockFace;

    public House(int id, int price, int level, Location block1Location, Location block2Location, Location signLocation, char blockFace) {
        this.id = id;
        this.price = price;
        this.level = level;
        this.block1Location = block1Location;
        this.block2Location = block2Location;
        this.signLocation = signLocation;
        this.blockFace = blockFace;
    }

}
