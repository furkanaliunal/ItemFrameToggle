package os.Triggers;

import org.bukkit.entity.Player;

public class Trigger<T> implements ITrigger<T>{
    private T type;
    private Boolean enabled;
    private String permission;
    private Boolean permissionBased;

    public Trigger(T type, Boolean enabled, Boolean permissionBased, String permission){
        this.type = type;
        this.permission = permission;
        this.enabled = enabled;
        this.permissionBased = permissionBased;
    }



    @Override
    public String getPermission() {
        return this.permission;
    }

    @Override
    public Boolean hasPermission(Player p) {
        if (!permissionBased){
            return true;
        }
        return p.hasPermission(this.permission);
    }

    @Override
    public Boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public T getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return type.toString() + "| isEnabled:" + enabled + "| permission:" + permission + "| permissionBased:" + permissionBased;
    }
}
