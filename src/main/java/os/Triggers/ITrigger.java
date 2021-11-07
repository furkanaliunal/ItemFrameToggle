package os.Triggers;


import org.bukkit.entity.Player;

public interface ITrigger<T>{
    public String getPermission();
    public Boolean hasPermission(Player p);
    public Boolean isEnabled();
    public T getType();
}
