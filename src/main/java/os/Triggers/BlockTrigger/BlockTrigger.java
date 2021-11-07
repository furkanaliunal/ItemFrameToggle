package os.Triggers.BlockTrigger;

import org.bukkit.Material;
import os.Triggers.Trigger;

public class BlockTrigger extends Trigger<Material> {

    public BlockTrigger(Material type, String permission, Boolean enabled) {
        super(type, permission, enabled);
    }
}
