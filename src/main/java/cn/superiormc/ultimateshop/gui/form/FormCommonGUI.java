package cn.superiormc.ultimateshop.gui.form;

import cn.superiormc.ultimateshop.gui.FormGUI;
import cn.superiormc.ultimateshop.managers.LanguageManager;
import cn.superiormc.ultimateshop.objects.buttons.AbstractButton;
import cn.superiormc.ultimateshop.objects.menus.ObjectMenu;
import cn.superiormc.ultimateshop.utils.CommonUtil;
import cn.superiormc.ultimateshop.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.geysermc.cumulus.component.ButtonComponent;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.util.FormImage;

import java.util.Objects;

public class FormCommonGUI extends FormGUI {

    private ObjectMenu commonMenu = null;

    private String fileName;

    public FormCommonGUI(Player owner, String fileName) {
        super(owner);
        this.fileName = fileName;
        constructGUI();
    }

    @Override
    protected void constructGUI() {
        commonMenu = ObjectMenu.commonMenus.get(fileName);
        if (commonMenu == null) {
            LanguageManager.languageManager.sendStringText(owner,
                    "error.menu-not-found",
                    "menu",
                    fileName);
            return;
        }
        menuButtons = commonMenu.getMenu();
        SimpleForm.Builder tempVal2 = SimpleForm.builder();
        for (int slot : menuButtons.keySet()) {
            AbstractButton button = menuButtons.get(slot);
            ItemStack displayItem = button.getDisplayItem(owner, 1);
            if (CommonUtil.getItemNameWithoutVanilla(displayItem).trim().isEmpty() ||
                    button.getButtonConfig().getBoolean("bedrock.hide", false)) {
                continue;
            }
            String icon = button.getButtonConfig().getString("bedrock.icon",
                    button.getButtonConfig().getString("bedrock-icon"));
            String tempVal3 = TextUtil.parse(CommonUtil.getItemName(displayItem), owner);
            ButtonComponent tempVal1 = null;
            if (icon != null && icon.split(";;").length == 2) {
                String type = icon.split(";;")[0].toLowerCase();
                if (type.equals("url")) {
                    tempVal1 = ButtonComponent.of(tempVal3, FormImage.Type.URL, icon.split(";;")[1]);
                } else if (type.equals("path")) {
                    tempVal1 = ButtonComponent.of(tempVal3, FormImage.Type.PATH, icon.split(";;")[1]);
                }
            } else {
                tempVal1 = ButtonComponent.of(tempVal3);
            }
            if (tempVal1 != null) {
                tempVal2.button(tempVal1);
            }
            menuItems.put(tempVal1, slot);
        }
        tempVal2.title(TextUtil.parse(commonMenu.getString("title", "Shop")));
        tempVal2.validResultHandler(response -> {
            menuButtons.get(menuItems.get(response.clickedButton())).clickEvent(ClickType.LEFT, owner);
        });
        form = tempVal2.build();
    }

}
