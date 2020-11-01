package fr.audentia.protect.domain.model.house;

import fr.audentia.protect.domain.model.HouseBloc;
import fr.audentia.protect.domain.model.Location;

import java.util.ArrayList;
import java.util.List;

public class HouseCreationModel {

    public Location tempLocation;
    public List<HouseBloc> blocs = new ArrayList<>();
    public Location signLocation;
    public char signFace;
    public int level;
    public int price;

    public HouseCreationModel() {
        this.signLocation = null;
        this.signFace = 'S';
        this.level = -1;
        this.price = -1;
    }

    public HouseCreationModel addBloc(Location location) {
        if (tempLocation == null) {
            tempLocation = location;
            return this;
        }
        blocs.add(new HouseBloc(tempLocation, location));
        tempLocation = null;
        return this;
    }

    public HouseCreationModel withSignLocation(Location location) {
        this.signLocation = location;
        return this;
    }

    public HouseCreationModel withSignFace(char signFace) {
        this.signFace = signFace;
        return this;
    }

    public HouseCreationModel withLevel(int level) {
        this.level = level;
        return this;
    }

    public HouseCreationModel withPrice(int price) {
        this.price = price;
        return this;
    }

}
