package os.Triggers.TriggerManagers;

import os.Triggers.ITrigger;

import java.util.Set;

public interface ITriggerManager<T> {


    public void clearTriggers();
    public T getObjectFromName(String objectName);
    public ITrigger<T> getTrigger(ITrigger trigger);
    public ITrigger<T> getTrigger(T trigger);
    public Boolean hasTrigger(ITrigger trigger);
    public Boolean hasTrigger(T trigger);
    public Set<ITrigger> getAllTriggers();
    public void addTrigger(ITrigger trigger);

}
