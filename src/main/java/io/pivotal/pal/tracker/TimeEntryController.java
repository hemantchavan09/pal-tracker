package io.pivotal.pal.tracker;

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
    ResponseEntity response=null;
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository=timeEntryRepository;
    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntry)
    {

        TimeEntry entry=timeEntryRepository.create(timeEntry);
        if(null!=entry) {
            response = new ResponseEntity(entry, HttpStatus.CREATED);
        }
        return response;
    }
    @GetMapping(value = "/{TIME_ENTRY_ID}")
    public ResponseEntity<TimeEntry> read(@PathVariable long TIME_ENTRY_ID)
    {
        TimeEntry entry= timeEntryRepository.find(TIME_ENTRY_ID);
        if (entry != null) {
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list()
    {
        List<TimeEntry> entry= timeEntryRepository.list();
        return new ResponseEntity(entry,HttpStatus.OK);
    }
    @PutMapping(value = "/{TIME_ENTRY_ID}")
    public ResponseEntity update(@PathVariable long TIME_ENTRY_ID, @RequestBody TimeEntry timeEntry)
    {
        TimeEntry entry=timeEntryRepository.update(TIME_ENTRY_ID,timeEntry);
        if (entry != null) {
            return new ResponseEntity<>(entry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping(value="/{TIME_ENTRY_ID}")
    public ResponseEntity delete(@PathVariable long TIME_ENTRY_ID)
    {
        timeEntryRepository.delete(TIME_ENTRY_ID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
