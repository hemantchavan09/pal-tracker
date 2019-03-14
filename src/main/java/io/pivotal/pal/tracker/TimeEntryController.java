package io.pivotal.pal.tracker;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping( value = "/time-entries")
public class TimeEntryController {
    @Autowired
    private TimeEntryRepository timeEntryRepository;
    private final DistributionSummary timeEntrySummary;
    private final Counter actionCounter;
    ResponseEntity response=null;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, MeterRegistry meterRegistry) {
        this.timeEntryRepository=timeEntryRepository;
        timeEntrySummary = meterRegistry.summary("timeEntry.summary");
        actionCounter = meterRegistry.counter("timeEntry.actionCounter");
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry)
    {

        TimeEntry entry=timeEntryRepository.create(timeEntry);

        if(null!=entry) {
            actionCounter.increment();
            timeEntrySummary.record(timeEntryRepository.list().size());
            response = new ResponseEntity(entry, HttpStatus.CREATED);
        }
        return response;
    }
    @GetMapping(value = "/{TIME_ENTRY_ID}")
    public ResponseEntity<TimeEntry> read(@PathVariable long TIME_ENTRY_ID)
    {
        TimeEntry entry= timeEntryRepository.find(TIME_ENTRY_ID);
        if (entry != null) {
            actionCounter.increment();
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list()
    {
        List<TimeEntry> entry= timeEntryRepository.list();
        actionCounter.increment();
        return new ResponseEntity(entry,HttpStatus.OK);
    }
    @PutMapping(value = "/{TIME_ENTRY_ID}")
    public ResponseEntity update(@PathVariable long TIME_ENTRY_ID, @RequestBody TimeEntry timeEntry)
    {
        TimeEntry entry=timeEntryRepository.update(TIME_ENTRY_ID,timeEntry);
        if (entry != null) {
            actionCounter.increment();
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping(value="/{TIME_ENTRY_ID}")
    public ResponseEntity delete(@PathVariable long TIME_ENTRY_ID)
    {
        timeEntryRepository.delete(TIME_ENTRY_ID);
        actionCounter.increment();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
