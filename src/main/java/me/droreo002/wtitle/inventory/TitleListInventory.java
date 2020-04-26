package me.droreo002.wtitle.inventory;

import me.droreo002.oreocore.inventory.button.GUIButton;
import me.droreo002.oreocore.inventory.paginated.PaginatedInventory;
import me.droreo002.oreocore.title.OreoTitle;
import me.droreo002.oreocore.utils.item.ItemStackBuilder;
import me.droreo002.oreocore.utils.item.complex.UMaterial;
import me.droreo002.wtitle.database.Title;
import me.droreo002.wtitle.database.TitleDatabase;

public class TitleListInventory extends PaginatedInventory {

    public TitleListInventory(TitleDatabase titleDatabase) {
        super(27, "Title List");

        setItemRow(0, 1);
        setSearchRow(2, true, ItemStackBuilder.GRAY_GLASS_PANE);

        for (Title title : titleDatabase) {
            OreoTitle oreoTitle = title.getOreoTitle();
            addPaginatedButton(new GUIButton(ItemStackBuilder.of(UMaterial.OAK_SIGN.getMaterial())
                    .setDisplayName("&7[&a" + title.getName() + "&7]")
                    .setLore(
                            "&7This title is currently registered",
                            "&7to region &c" + title.getRegion(),
                            "&r",
                            "&a&lTITLE INFORMATION",
                            "&7title: &e" + oreoTitle.getTitle(),
                            "&7sub-title: &e" + oreoTitle.getSubTitle(),
                            "&7fade-in: &e" + oreoTitle.getFadeIn(),
                            "&7fade-out: &e" + oreoTitle.getFadeOut(),
                            "&7stay: &e" + oreoTitle.getStay(),
                            "&7animated: &e" + title.isUseAnimation()
                    )
                    .build()));
        }
    }
}
