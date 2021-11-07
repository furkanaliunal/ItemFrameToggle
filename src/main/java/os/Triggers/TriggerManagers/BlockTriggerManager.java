package os.Triggers.TriggerManagers;

import org.bukkit.Material;
import os.ItemFrameToggle;

public class BlockTriggerManager extends TriggerManager<Material>{
    public BlockTriggerManager(ItemFrameToggle plugin){
        super(plugin, "TriggerBlocks");
    }


    @Override
    public Material getObjectFromName(String objectName){
        return Material.getMaterial(objectName);
    }
}
