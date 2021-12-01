package os.versions.v1_18;

import org.bukkit.entity.EntityType;
import os.ItemFrameToggle;
import os.Triggers.TriggerManagers.BlockTriggerManager;

public class InteractListener extends os.versions.v1_13.InteractListener {

    public InteractListener(ItemFrameToggle plugin, BlockTriggerManager trigger){
        super(plugin, trigger);
    }

    @Override
    public boolean isItemFrame(EntityType item){
        if (item == null){
            return false;
        }
        return item == EntityType.ITEM_FRAME || item == EntityType.GLOW_ITEM_FRAME;
    }
}
