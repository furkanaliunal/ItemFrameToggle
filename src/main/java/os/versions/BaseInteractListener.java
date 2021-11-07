package os.versions;

import org.bukkit.entity.Player;
import os.ItemFrameToggle;
import os.Triggers.TriggerManagers.BlockTriggerManager;

public abstract class BaseInteractListener implements IBaseInteractListener{
    public final ItemFrameToggle plugin;
    public final BlockTriggerManager trigger;
    public BaseInteractListener(ItemFrameToggle plugin, BlockTriggerManager trigger){
        this.plugin = plugin;
        this.trigger = trigger;
    }

    public boolean hasPermission(Player p, String permission){
        if(!plugin.permissionBased){
            return true;
        }
        if(p.hasPermission(permission)){
            return true;
        }
        return false;
    }
}
