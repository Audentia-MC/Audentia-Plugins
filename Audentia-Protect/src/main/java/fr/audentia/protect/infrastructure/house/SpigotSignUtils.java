package fr.audentia.protect.infrastructure.house;

import fr.audentia.players.domain.model.teams.Team;
import fr.audentia.players.utils.ChatUtils;
import fr.audentia.players.utils.ColorsUtils;
import fr.audentia.protect.domain.house.HouseAction;
import fr.audentia.protect.domain.house.SignUtils;
import fr.audentia.protect.domain.model.House;
import fr.audentia.protect.domain.model.Location;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class SpigotSignUtils implements SignUtils {

    private final HouseAction houseAction;

    public SpigotSignUtils(HouseAction houseAction) {
        this.houseAction = houseAction;
    }

    @Override
    public void reloadSign(House house) {

        World world = Bukkit.getWorld("world");

        if (world == null) {
            return;
        }

        Location signLocation = house.signLocation;
        org.bukkit.Location spigotLocation = new org.bukkit.Location(world, signLocation.x, signLocation.y, signLocation.z);

        Block block = world.getBlockAt(spigotLocation).getRelative(getBlockFace(house.signFace));

        world.getNearbyEntities(block.getLocation(), 3, 3, 3)
                .stream()
                .filter(entity -> entity.getType() == EntityType.ARMOR_STAND)
                .forEach(Entity::remove);

        placeSign(house, block);

        houseAction.getTeamOwner(signLocation)
                .ifPresent(team -> placeArmorStand(block, team));
    }

    private void placeArmorStand(Block block, Team team) {
        block.setType(Material.AIR);

        ArmorStand armorstand = (ArmorStand) block.getWorld().spawnEntity(block.getLocation(), EntityType.ARMOR_STAND);
        armorstand.setHealth(20);
        armorstand.setGravity(true);
        armorstand.setCustomName("Team " + ColorsUtils.fromColorToHexadecimal(team.color) + team.name);
        armorstand.setCustomNameVisible(true);
        armorstand.setVisible(false);
    }

    private void placeSign(House house, Block block) {
        block.setType(Material.OAK_WALL_SIGN);
        WallSign signData = (WallSign) block.getBlockData();
        signData.setFacing(getBlockFace(house.signFace));

        Sign sign = (Sign) block.getState();
        sign.setLine(0, "A vendre");
        sign.setLine(1, ChatUtils.format("#ECADd11Prix : <green>" + house.price));
        sign.setLine(2, "#ECADd11Niveau : <green>" + house.level);
        sign.setLine(3, "");
        sign.setBlockData(signData);
        sign.update();
    }

    private BlockFace getBlockFace(char blockFace) {

        switch (blockFace) {

            case 'N':
                return BlockFace.NORTH;

            case 'E':
                return BlockFace.EAST;

            case 'W':
                return BlockFace.WEST;

            default:
                return BlockFace.SOUTH;

        }

    }

}
