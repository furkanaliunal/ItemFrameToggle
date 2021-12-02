package os.Triggers.TriggerManagers;

import os.ItemFrameToggle;
import os.Triggers.ITrigger;
import os.Triggers.Trigger;

import java.util.HashSet;
import java.util.Set;

public abstract class TriggerManager<T> implements ITriggerManager<T>{
    final ItemFrameToggle plugin;
    Set<ITrigger> triggers;


    public TriggerManager(ItemFrameToggle plugin, String path){
        triggers = new HashSet<>();
        this.plugin = plugin;
        readTriggers(path);
    }

    public void readTriggers(String path){
        Set<String> elements = plugin.getConfig().getConfigurationSection(path).getKeys(false);
        for (String element : elements){
            Boolean isEnabled = plugin.getConfig().getBoolean(path + "." + element + "." + "enabled");
            Boolean permissionBased = plugin.getConfig().getBoolean(path + "." + element + "." + "permissionBased");
            String permission = plugin.getConfig().getString(path + "." + element + "." + "permission");
            T type = getObjectFromName(element);
            Trigger<T> newTrigger = new Trigger(type, isEnabled, permissionBased, permission);
            this.triggers.add(newTrigger);
        }
    }



    @Override
    public void clearTriggers() {
        triggers.clear();
    }

    @Override
    public ITrigger<T> getTrigger(ITrigger trigger) {
        for(ITrigger<T> t : triggers){
            if (trigger.getType().equals(t.getType())){
                return t;
            }
        }
        return null;
    }

    @Override
    public ITrigger<T> getTrigger(T trigger){
        for(ITrigger<T> t : triggers){
            if (trigger.equals(t.getType())){
                return t;
            }
        }
        return null;
    }

    @Override
    public Boolean hasTrigger(ITrigger trigger){
        for(ITrigger<T> t : triggers){
            if (trigger.getType().equals(t.getType())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean hasTrigger(T trigger){
        for(ITrigger<T> t : triggers){
            if (trigger.equals(t.getType())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<ITrigger> getAllTriggers() {
        return triggers;
    }

    @Override
    public void addTrigger(ITrigger trigger) {
        this.triggers.add(trigger);

    }
}
