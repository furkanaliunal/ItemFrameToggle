package os.Triggers;

import org.bukkit.entity.Player;

public class Trigger<T> implements ITrigger<T>{
    private T type;
    private String permission;
    private Boolean enabled;

    public Trigger(T type, String permission, Boolean enabled){
        this.type = type;
        this.permission = permission;
        this.enabled = enabled;
    }



    @Override
    public String getPermission() {
        return this.permission;
    }

    @Override
    public Boolean hasPermission(Player p) {
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
}
