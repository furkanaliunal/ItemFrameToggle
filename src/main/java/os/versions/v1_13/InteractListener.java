package os.versions.v1_13;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import os.ItemFrameToggle;
import os.Triggers.ITrigger;
import os.Triggers.TriggerManagers.BlockTriggerManager;
import os.versions.BaseInteractListener;

public class InteractListener extends BaseInteractListener {

    public InteractListener(ItemFrameToggle plugin, BlockTriggerManager trigger){
        super(plugin, trigger);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void hideItemFrameWhileSneaking(PlayerInteractAtEntityEvent e) {
        if (e.isCancelled()){
            return;
        }
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
        if (plugin.permissionBased){
            if (!(p.hasPermission("itemframetoggle.toggle"))){
                return;
            }
        }
        ItemFrame itemFrame = (ItemFrame)clicked;
        if (!(itemFrame.getItem().getType() == Material.AIR && p.getInventory().getItemInMainHand().getType() == Material.AIR)){
            itemFrame.setRotation(itemFrame.getRotation().rotateCounterClockwise());
        }
        Boolean isVisible = itemFrame.isVisible();
        if (isVisible){
            plugin.sendMessage(p, plugin.messageOnHide);
        }
        itemFrame.setVisible(!isVisible);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void openContainerWhenFrameInvisible(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (e.isCancelled()){
            return;
        }
        if (!(e.getHand() == (EquipmentSlot.HAND))){
            return;
        }
        if (p.isSneaking()){
            return;
        }
        if (!isItemFrame(e.getRightClicked().getType())) {
            return;
        }
        if (((ItemFrame)e.getRightClicked()).isVisible()){
            return;
        }
        Block block = e.getRightClicked().getLocation().getBlock().getRelative(((ItemFrame)e.getRightClicked()).getAttachedFace());
        if (block == null){
            return;
        }

        Material blockType = block.getType();
        if (block.getState() instanceof ShulkerBox){
            blockType = Material.SHULKER_BOX;
        }
        ITrigger trigger = this.trigger.getTrigger(blockType);
        if (trigger == null){
            return;
        }
        if (!openContainer(p, block)){
            return;
        }
        Inventory i = getInventory(p, block);
        if (i == null){
            return;
        }
        if (plugin.permissionBased){
            if (!(trigger.hasPermission(p) && p.hasPermission("itemframetoggle.toggle"))){
                return;
            }
        }



        p.openInventory(i);
        e.setCancelled(true);


    }

    public Inventory getInventory(Player p, Block block){
        if (block.getType().equals(Material.ENDER_CHEST)){
            p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);
            return p.getEnderChest();
        }
        if (block.getState() instanceof BlockInventoryHolder){
            return ((BlockInventoryHolder) block.getState()).getInventory();
        }
        return null;
    }

    @EventHandler(priority = EventPriority.HIGH)
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


        if (plugin.permissionBased && !(player.hasPermission("itemframetoggle.toggle"))){
            return;
        }

        if (!(player.isSneaking())){
            return;
        }
        e.setCancelled(true);
        rotateItemFrame((ItemFrame)e.getEntity());
    }

    @Override
    public boolean openContainer(Player player, Block block) {
        PlayerInteractEvent playerInteractEvent = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK, new ItemStack(Material.AIR), block, BlockFace.UP);
        Bukkit.getPluginManager().callEvent(playerInteractEvent);
        return !playerInteractEvent.isCancelled();
    }

    @Override
    public void rotateItemFrame(ItemFrame itemFrame){
        itemFrame.setRotation(itemFrame.getRotation().rotateCounterClockwise());
    }
    @Override
    public boolean isItemFrame(EntityType item){
        if (item == null){
            return false;
        }
        return item == EntityType.ITEM_FRAME;
    }
}
