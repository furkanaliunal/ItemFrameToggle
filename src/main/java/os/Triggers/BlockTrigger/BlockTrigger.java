package os.Triggers.BlockTrigger;

import org.bukkit.Material;
import os.Triggers.Trigger;

public class BlockTrigger extends Trigger<Material> {

    public BlockTrigger(Material type, Boolean enabled, Boolean permissionBased, String permission) {
        super(type, enabled, permissionBased, permission);
    }
}
