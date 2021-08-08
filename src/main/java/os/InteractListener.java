package os;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    private final ItemFrameToggle plugin;
    public InteractListener(ItemFrameToggle plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void hideItemFrameWhileSneaking(PlayerInteractAtEntityEvent e) {
        Entity clicked = e.getRightClicked();
        Player p = e.getPlayer();
        if (!(e.getHand().equals(EquipmentSlot.HAND))){
            return;
        }
        if (!isItemFrame(clicked.getType())){
            return;
        }
        if (!(p.isSneaking())){
            return;
        }
        if (!(p.hasPermission("itemframetoggle.toggle"))){
            return;
        }
        ItemFrame itemFrame = (ItemFrame)clicked;
        if (!(itemFrame.getItem().getType() == Material.AIR && p.getItemInHand().getType() == Material.AIR)){
            itemFrame.setRotation(itemFrame.getRotation().rotateCounterClockwise());
        }
        if (itemFrame.isVisible()){
            plugin.sendMessage(p, plugin.messageOnHide);
        }
        itemFrame.setVisible(!(itemFrame).isVisible());
    }


    @EventHandler
    public void openContainerWhenFrameInvisible(PlayerInteractEntityEvent e) {
        if (e.isCancelled()){
            return;
        }
        if (!(e.getHand() == (EquipmentSlot.HAND))){
            return;
        }
        if (e.getPlayer().isSneaking()){
            return;
        }
        if (!isItemFrame(e.getRightClicked().getType())) {
            return;
        }
        if (((ItemFrame)e.getRightClicked()).isVisible()){
            return;
        }
        if (!(e.getPlayer().hasPermission("itemframetoggle.toggle"))){
            return;
        }
        Block block = e.getRightClicked().getLocation().getBlock().getRelative(((ItemFrame)e.getRightClicked()).getAttachedFace());
        if (block == null){
            return;
        }
        if (block.getState() instanceof Container) {
            Container container = (Container)block.getState();
            if (openContainer(e.getPlayer(), container.getBlock())){
                e.getPlayer().openInventory(container.getInventory());
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void rotateFrameItemWhileSneaking(EntityDamageByEntityEvent e){
        if (e.isCancelled()){
            return;
        }
        if (!isItemFrame(e.getEntityType())){
            return;
        }
        if (e.getDamager() == null || e.getDamager().getType() != EntityType.PLAYER){
            return;
        }

        Player player = (Player)e.getDamager();


        if (!(player.hasPermission("itemframetoggle.toggle"))){
            return;
        }

        if (!(player.isSneaking())){
            return;
        }
        e.setCancelled(true);
        rotateItemFrame((ItemFrame)e.getEntity());
    }

    boolean openContainer(Player player, Block block) {
        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK, new ItemStack(Material.AIR), block, BlockFace.UP);
        Bukkit.getPluginManager().callEvent(playerInteractEvent);
        return !playerInteractEvent.isCancelled();
    }
    void rotateItemFrame(ItemFrame itemFrame){
        itemFrame.setRotation(itemFrame.getRotation().rotateCounterClockwise());
    }
    boolean isItemFrame(EntityType item){
        if (item == null){
            return false;
        }
        return item == EntityType.ITEM_FRAME || item == EntityType.GLOW_ITEM_FRAME;
    }
}
