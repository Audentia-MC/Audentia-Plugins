package fr.audentia.protect.domain.model;

import fr.audentia.protect.domain.model.house.HouseCreationModel;

import java.util.List;

public class House {

    public final long id;
    public final int price;
    public final int level;
    public final List<HouseBloc> blocs;
    public final Location signLocation;
    public final char signFace;

    public House(long id, int price, int level, List<HouseBloc> blocs, Location signLocation, char signFace) {
        this.id = id;
        this.price = price;
        this.level = level;
        this.blocs = blocs;
        this.signLocation = signLocation;
        this.signFace = signFace;
    }

    public House(HouseCreationModel creation) {
        this.id = -1;
        this.price = creation.price;
        this.level = creation.level;
        this.blocs = creation.blocs;
        this.signLocation = creation.signLocation;
        this.signFace = creation.signFace;
    }

}
