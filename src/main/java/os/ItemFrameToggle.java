package os;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class ItemFrameToggle extends JavaPlugin {
    public String messageOnHide = "";

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new InteractListener(this), this);
        getConfig().options().copyDefaults();
        loadConfig();
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
