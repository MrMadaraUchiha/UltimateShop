package cn.superiormc.ultimateshop.hooks;

import cn.superiormc.mythicchanger.manager.MatchItemManager;
import cn.superiormc.ultimateshop.managers.HookManager;
import cn.superiormc.ultimateshop.utils.ItemUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemPriceUtil {

    public static boolean getPrice(Inventory inventory, Player player, String pluginName, String item, int value, boolean take) {
        if (value < 0) {
            return false;
        }
        ItemStack[] storage = inventory.getStorageContents();
        int amount = getItemAmount(inventory, pluginName, item);
        if (amount >= value) {
            if (take) {
                for (ItemStack itemStack : storage) {
                    if (itemStack == null || itemStack.getType().isAir()) {
                        continue;
                    }
                    ItemStack temItem = itemStack.clone();
                    temItem.setAmount(1);
                    String tempVal10 = HookManager.hookManager.getHookItemID(pluginName, temItem);
                    if (tempVal10 != null && tempVal10.equals(item)) {
                        if (itemStack.getAmount() >= value) {
                            itemStack.setAmount(itemStack.getAmount() - value);
                            break;
                        } else {
                            value -= itemStack.getAmount();
                            itemStack.setAmount(0);
                        }
                    }
                }
                if (inventory instanceof PlayerInventory) {
                    player.getInventory().setStorageContents(storage);
                }
                else {
                    inventory.setStorageContents(storage);
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    public static int getItemAmount(Inventory inventory, String pluginName, String item) {
        if (item == null) {
            return 0;
        }
        int amount = 0;
        ItemStack[] storage = inventory.getStorageContents();
        for (ItemStack tempVal1 : storage) {
            if (tempVal1 == null || tempVal1.getType().isAir()) {
                continue;
            }
            ItemStack temItem = tempVal1.clone();
            temItem.setAmount(1);
            String tempVal10 = HookManager.hookManager.getHookItemID(pluginName, temItem);
            if (tempVal10 != null && tempVal10.equals(item)) {
                amount += tempVal1.getAmount();
            }
        }
        return amount;
    }

    public static boolean getPrice(Inventory inventory, Player player, ItemStack item, int value, boolean take) {
        if (value < 0) {
            return false;
        }
        ItemStack[] storage = inventory.getStorageContents();
        int amount = getItemAmount(inventory, item);
        if (amount >= value) {
            if (take) {
                for (ItemStack itemStack : storage) {
                    if (itemStack == null || itemStack.getType().isAir()) {
                        continue;
                    }
                    ItemStack temItem = itemStack.clone();
                    temItem.setAmount(1);
                    if (ItemUtil.isSameItem(temItem, item)) {
                        if (itemStack.getAmount() >= value) {
                            itemStack.setAmount(itemStack.getAmount() - value);
                            break;
                        } else {
                            value -= itemStack.getAmount();
                            itemStack.setAmount(0);
                        }
                    }
                }
                if (inventory instanceof PlayerInventory) {
                    player.getInventory().setStorageContents(storage);
                }
                else {
                    inventory.setStorageContents(storage);
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    public static int getItemAmount(Inventory inventory, ConfigurationSection section) {
        if (section == null) {
            return 0;
        }
        ItemStack[] storage = inventory.getStorageContents();
        int amount = 0;
        for (ItemStack tempVal1 : storage) {
            if (tempVal1 == null || tempVal1.getType().isAir()) {
                continue;
            }
            ItemStack temItem = tempVal1.clone();
            temItem.setAmount(1);
            if (MatchItemManager.matchItemManager.getMatch(section.getConfigurationSection("match-item"), tempVal1)) {
                amount += tempVal1.getAmount();
            }
        }
        return amount;
    }

    public static boolean getPrice(Inventory inventory, Player player, ConfigurationSection section, int value, boolean take) {
        if (value < 0) {
            return false;
        }
        ItemStack[] storage = inventory.getStorageContents();
        int amount = getItemAmount(inventory, section);
        if (amount >= value) {
            if (take) {
                for (ItemStack itemStack : storage) {
                    if (itemStack == null || itemStack.getType().isAir()) {
                        continue;
                    }
                    ItemStack temItem = itemStack.clone();
                    temItem.setAmount(1);
                    if (MatchItemManager.matchItemManager.getMatch(section.getConfigurationSection("match-item"), temItem)) {
                        if (itemStack.getAmount() >= value) {
                            itemStack.setAmount(itemStack.getAmount() - value);
                            break;
                        } else {
                            value -= itemStack.getAmount();
                            itemStack.setAmount(0);
                        }
                    }
                }
                if (inventory instanceof PlayerInventory) {
                    player.getInventory().setStorageContents(storage);
                }
                else {
                    inventory.setStorageContents(storage);
                }
            }
            return true;
        }
        else {
            return false;
        }
    }

    public static int getItemAmount(Inventory inventory, ItemStack item) {
        if (item == null) {
            return 0;
        }
        ItemStack[] storage = inventory.getStorageContents();
        int amount = 0;
        for (ItemStack tempVal1 : storage) {
            if (tempVal1 == null || tempVal1.getType().isAir()) {
                continue;
            }
            ItemStack temItem = tempVal1.clone();
            temItem.setAmount(1);
            if (ItemUtil.isSameItem(temItem, item)) {
                amount += tempVal1.getAmount();
            }
        }
        return amount;
    }

}