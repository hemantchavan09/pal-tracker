package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {
   // private TimeEntry timeEntry;

    HashMap<Long,TimeEntry> entry= new HashMap<>();
    private long currentId = 1L;
    public TimeEntry create(TimeEntry timeEntry)
    {
        timeEntry.setId(currentId++);
        if(null!=timeEntry)
        {
            entry.put(timeEntry.getId(),timeEntry);
        }
        return entry.get(timeEntry.getId());
    }


    @Override
    public TimeEntry find(long id) {
        return entry.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        List<TimeEntry> entries= new ArrayList<>(entry.values());
        return entries;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {

        if (find(id) == null) return null;

        TimeEntry updatedEntry = new TimeEntry(
                id,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );

        entry.replace(id, updatedEntry);
        return updatedEntry;
    }

    @Override
    public void delete(long id) {
      entry.remove(id);
    }
}
