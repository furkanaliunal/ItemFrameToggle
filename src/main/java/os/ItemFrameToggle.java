package os;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import os.Triggers.TriggerManagers.BlockTriggerManager;
import os.versions.IBaseInteractListener;

import java.util.*;
import java.util.stream.Collectors;

public final class ItemFrameToggle extends JavaPlugin {
    public String messageOnHide = "";
    public BlockTriggerManager blockTriggerManager;
    public Boolean permissionBased = false;

    @Override
    public void onEnable() {
        Version currentVersion = new Version(Bukkit.getBukkitVersion().split("-")[0]);

        if (currentVersion.compareTo(new Version("1.13")) < 0){
            getServer().getLogger().warning("ItemFrameToggle doesn't support current version ("+currentVersion+")");
            getServer().getLogger().warning("ItemFrameToggle disabling...");
            getServer().getPluginManager().disablePlugin(this);
        }
        IBaseInteractListener listener = null;
        getConfig().options().copyDefaults();
        loadConfig();
        blockTriggerManager = new BlockTriggerManager(this);
        if (currentVersion.compareTo(new Version("1.19")) >= 0){
            listener = new os.versions.v1_19.InteractListener(this, blockTriggerManager);
        }else if (currentVersion.compareTo(new Version("1.18")) >= 0){
            listener = new os.versions.v1_18.InteractListener(this, blockTriggerManager);
        }else if (currentVersion.compareTo(new Version("1.17")) >= 0){
            listener = new os.versions.v1_17.InteractListener(this, blockTriggerManager);
        }else if (currentVersion.compareTo(new Version("1.16")) >= 0){
            listener = new os.versions.v1_16.InteractListener(this, blockTriggerManager);
        }else if (currentVersion.compareTo(new Version("1.13")) >= 0){
            listener = new os.versions.v1_13.InteractListener(this, blockTriggerManager);
        }

        this.permissionBased = getConfig().getBoolean("PermissionsEnabled", false);
        if (listener != null) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public void loadConfig() {
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
