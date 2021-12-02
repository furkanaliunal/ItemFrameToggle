package os.versions;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public interface IBaseInteractListener extends Listener {
    public void hideItemFrameWhileSneaking(PlayerInteractAtEntityEvent e);
    public void openContainerWhenFrameInvisible(PlayerInteractEntityEvent e);
    public void rotateFrameItemWhileSneaking(EntityDamageByEntityEvent e);

    public boolean openContainer(Player player, Block block);
    public void rotateItemFrame(ItemFrame itemFrame);
    boolean isItemFrame(EntityType item);
    //public boolean hasPermission(Player p, String permission);
}
