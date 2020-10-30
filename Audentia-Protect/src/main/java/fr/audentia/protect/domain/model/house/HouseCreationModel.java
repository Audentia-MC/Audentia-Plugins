package fr.audentia.protect.domain.model.house;

import fr.audentia.protect.domain.model.Location;

import java.util.Optional;

public class HouseCreationModel {

    public final Optional<Location> location1;
    public final Optional<Location> location2;
    public final Optional<Location> signLocation;
    public final char signFace;
    public final int level;
    public final int price;

    public HouseCreationModel() {
        this.location1 = Optional.empty();
        this.location2 = Optional.empty();
        this.signLocation = Optional.empty();
        this.signFace = 'S';
        this.level = -1;
        this.price = -1;
    }

    public HouseCreationModel(Optional<Location> location1, Optional<Location> location2, Optional<Location> signLocation, char signFace, int level, int price) {
        this.location1 = location1;
        this.location2 = location2;
        this.signLocation = signLocation;
        this.signFace = signFace;
        this.level = level;
        this.price = price;
    }

    public HouseCreationModel withLocation1(Location location) {
        return new HouseCreationModel(Optional.of(location), location2, signLocation, signFace, level, price);
    }

    public HouseCreationModel withLocation2(Location location) {
        return new HouseCreationModel(location1, Optional.of(location), signLocation, signFace, level, price);
    }

    public HouseCreationModel withSignLocation(Location location) {
        return new HouseCreationModel(location1, location2, Optional.of(location), signFace, level, price);
    }

    public HouseCreationModel withSignFace(char signFace) {
        return new HouseCreationModel(location1, location2, signLocation, signFace, level, price);
    }

    public HouseCreationModel withLevel(int level) {
        return new HouseCreationModel(location1, location2, signLocation, signFace, level, price);
    }

    public HouseCreationModel withPrice(int price) {
        return new HouseCreationModel(location1, location2, signLocation, signFace, level, price);
    }

}
