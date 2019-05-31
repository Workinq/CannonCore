package kr.kieran.cannoncore.tickcounter.utils;

import de.tr7zw.itemnbtapi.NBTItem;
import kr.kieran.cannoncore.CannonCore;
import kr.kieran.cannoncore.utils.Chat;
import kr.kieran.cannoncore.utils.configuration.Configuration;
import kr.kieran.cannoncore.utils.enums.ConfigType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TickCounterItem {

    public ItemStack tickCounter(int amount) {
        Configuration config = CannonCore.getInstance().getConfigByName(ConfigType.TICKCOUNTER_CONFIG);
        ItemStack item = new ItemStack(Material.getMaterial(config.getConfig().getString("item.material")));
        item.setAmount(amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Chat.color(config.getConfig().getString("item.name")));
        List<String> lore = new ArrayList<>();
        for (String var1 : config.getConfig().getStringList("item.lore")) {
            lore.add(Chat.color(var1));
        }
        meta.setLore(lore);
        if (config.getConfig().getBoolean("item.enchanted")) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setBoolean("tickcounter", true);
        return nbtItem.getItem();
    }

    public boolean isTickCounter(ItemStack itemStack) {
        NBTItem item = new NBTItem(itemStack);
        return item.hasKey("tickcounter");
    }

}
