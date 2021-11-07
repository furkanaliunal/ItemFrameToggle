package os;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import os.Triggers.TriggerManagers.BlockTriggerManager;
import os.versions.IBaseInteractListener;

import java.util.ArrayList;

public final class ItemFrameToggle extends JavaPlugin {
    public String messageOnHide = "";
    public BlockTriggerManager blockTriggerManager;
    public Boolean permissionBased = true;

    @Override
    public void onEnable() {
        int version = Integer.parseInt((getServer().getBukkitVersion().split("-")[0]).replace(".", ""));
        if (version < 1130){
            getServer().getLogger().warning("ItemFrameToggle doesn't support current version ("+version+")");
            getServer().getLogger().warning("ItemFrameToggle disabling...");
            getServer().getPluginManager().disablePlugin(this);
        }
        IBaseInteractListener listener = null;
        getConfig().options().copyDefaults();
        loadConfig();
        blockTriggerManager = new BlockTriggerManager(this);
        if (version >= 1170){
            listener = new os.versions.v1_17.InteractListener(this, blockTriggerManager);
        }else if (version >= 1160){
            listener = new os.versions.v1_16.InteractListener(this, blockTriggerManager);
        }else if (version >= 1130){
            listener = new os.versions.v1_13.InteractListener(this, blockTriggerManager);
        }
        this.permissionBased = getConfig().getBoolean("PermissionsEnabled");
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public void loadConfig() {
        reloadConfig();
        saveDefaultConfig();

        this.messageOnHide = getConfig().getString("messageOnHide");
    }

    public void sendMessage(Player p, String message){
        if (message.equals("")){
            return;
        }
        ArrayList<String> lines = new ArrayList<>();
        String tempLine = "";
        for(int i = 0; i < message.length(); i++){
            if (message.charAt(i) == '/'){
                if (i < message.length() - 1 && message.charAt(i + 1) == '/'){
                    i++;
                    tempLine += '/';
                }else{//go for next line
                    lines.add(tempLine);
                    tempLine = "";
                }
            }else{
                tempLine += message.charAt(i);
            }
        }
        if (tempLine != ""){
            lines.add(tempLine);
        }
        /*String[] lines = message.split("/");
        Bukkit.getServer().broadcastMessage(String.valueOf(lines.length));
        for(String line : lines){
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }*/
        for (String line : lines){
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }
    }
}
