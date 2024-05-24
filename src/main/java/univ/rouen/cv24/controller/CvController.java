package univ.rouen.cv24.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.rouen.cv24.entity.*;
import univ.rouen.cv24.service.CvService;
import univ.rouen.cv24.templates.HTMLFormat;
import univ.rouen.cv24.templates.Page;
import univ.rouen.cv24.templates.XMLFormat;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/cv24/")
public class CvController {

    // ATTRIBUTES

    private final CvService cvService;

    // CONSTRUCTOR

    public CvController(CvService cvService) {
        this.cvService = cvService;
    }

    // REQUESTS

    @GetMapping(value = "resume", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getAllCVInHTMLFormat() {
        Page p = new HTMLFormat();
        return ResponseEntity.status(HttpStatus.OK.value()).body(p.getAllCv(cvService.getAllCvs()));
    }

    @GetMapping(value = "resume/xml", produces = "application/xml")
    public ResponseEntity<String> getAllCVInXMLFormat() {
        Page p = new XMLFormat();
        List<Cv> cvs = this.cvService.getAllCvs();

        return ResponseEntity.status(HttpStatus.OK.value()).body(p.getAllCv(cvs));
    }

    @GetMapping(value = "html", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> getAllDetailInHTMLFormat(@RequestParam(value = "id") Integer id) {
        Page p = new HTMLFormat();
        Cv cv = cvService.getCv(id);

        int valueStatus = cv != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(valueStatus).body(p.getAllInformationOnCv(cv, id));
    }

    @GetMapping(value = "xml", produces = "application/xml")
    public ResponseEntity<String> getAllDetailInXMLFormat(@RequestParam(value = "id") Integer id) {
        Page p = new XMLFormat();
        Cv cv = cvService.getCv(id);

        int valueStatus = cv != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(valueStatus).body(p.getAllInformationOnCv(cv, id));
    }

    @PostMapping(value = "insert", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<String> insertCv(@RequestBody String cvXml) {
        String status = "<status>";
        String detail = "<detail>";
        String id = null;
        try {
            Cv createdCv = this.cvService.addCv(cvXml);
            status +=  "INSERTED";
            id = "<id>" + createdCv.getId().toString() + "</id>";
        } catch (Exception e) {
            status +=  "ERROR";
            detail += e.getMessage() + "</detail>";
        }
        status += "</status>";

        String response = "<response>" + (id == null ? "" : id) + status + (id == null ? detail : "") + "</response>";
        int valueStatus = id == null ? HttpStatus.NOT_FOUND.value() : HttpStatus.CREATED.value();
        return ResponseEntity.status(valueStatus).body(response);
    }

    @DeleteMapping(value = "delete", produces = "application/xml")
    public ResponseEntity<String> deleteCv(@RequestParam(value = "id") Integer id) {
        Cv deletedCv = this.cvService.getCv(id);
        String response = "<response>";
        response += "<id>" + id + "</id>";
        if (deletedCv != null) {
            this.cvService.deleteCv(deletedCv);
        }

        response += "<status>" + (deletedCv == null ? "ERROR" : "DELETED")  + "</status>";
        response += "</response>";

        int valueStatus = deletedCv == null ? HttpStatus.NOT_FOUND.value() : HttpStatus.OK.value();
        return ResponseEntity.status(valueStatus).body(response);
    }

    @GetMapping(value = "search", produces = "application/xml")
    public String search(@RequestParam(value = "obj") String obj, @RequestParam(value = "date") String date) {
        if (obj.isEmpty() || date.isEmpty() ||
                (!(date.length() == "yyyy-MM-dd'T'HH:mm:ss".length()
                || date.length() == "yyyy-MM-dd".length()))
            ) {
            return "<response><status>ERROR</status></response>";
        }
        if (date.length() == "yyyy-MM-dd".length()) {
            date += "T00:00:00";
        }

        List<Cv> cvSearch;
        final LocalDateTime dateTime;
        try {
            cvSearch = this.cvService.searchCv(obj, date);
        } catch (Exception e) {
            return "<response><status>ERROR</status></response>";
        }

        // Return appropriate response if no CVs are found
        if (cvSearch.isEmpty()) {
            return "<response><status>NONE</status></response>";
        }

        // Format and return the results in XML
        Page p = new XMLFormat();
        return p.getAllCv(cvSearch);
    }
}