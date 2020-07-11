package fr.audentia.plugin.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class ItemStackBuilder {

    private String name;
    private Material material;
    private byte data;
    private int amount;
    private List<String> lore;

    private ItemStackBuilder() {
        this.name = "";
        this.amount = 1;
        this.lore = new ArrayList<>();
    }

    public static ItemStackBuilder anItemStack() {
        return new ItemStackBuilder();
    }

    public ItemStackBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ItemStackBuilder withMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemStackBuilder withData(byte data) {
        this.data = data;
        return this;
    }

    public ItemStackBuilder withAmount(int quantity) {
        this.amount = quantity;
        return this;
    }

    public ItemStackBuilder addLore(String lore) {
        this.lore.add(lore);
        return this;
    }

    public ItemStackBuilder addLore(List<String> lore) {
        lore.forEach(this::addLore);
        return this;
    }

    public ItemStackBuilder withLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount);
        itemStack.setData(new MaterialData(material, data));
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        }

        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
