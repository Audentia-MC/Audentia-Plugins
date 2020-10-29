package fr.audentia.protect.application.tasks;

import fr.audentia.protect.domain.house.BuyHouseAction;
import org.bukkit.scheduler.BukkitRunnable;

public class BuyHouseClicksTasks extends BukkitRunnable {

    private final BuyHouseAction buyHouseAction;

    public BuyHouseClicksTasks(BuyHouseAction buyHouseAction) {
        this.buyHouseAction = buyHouseAction;
    }

    @Override
    public void run() {
        this.buyHouseAction.clearOld();
    }

}
