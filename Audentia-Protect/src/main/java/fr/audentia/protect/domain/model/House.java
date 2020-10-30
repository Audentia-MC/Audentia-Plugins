package fr.audentia.protect.domain.model;

import fr.audentia.protect.domain.model.house.HouseCreationModel;

public class House {

    public final int id;
    public final int price;
    public final int level;
    public final Location block1Location;
    public final Location block2Location;
    public final Location signLocation;
    public final char signFace;

    public House(int id, int price, int level, Location block1Location, Location block2Location, Location signLocation, char signFace) {
        this.id = id;
        this.price = price;
        this.level = level;
        this.block1Location = block1Location;
        this.block2Location = block2Location;
        this.signLocation = signLocation;
        this.signFace = signFace;
    }

    public House(HouseCreationModel creation) {
        this.id = -1;
        this.price = creation.price;
        this.level = creation.level;
        this.block1Location = creation.location1.get();
        this.block2Location = creation.location2.get();
        this.signLocation = creation.signLocation.get();
        this.signFace = creation.signFace;
    }

}
