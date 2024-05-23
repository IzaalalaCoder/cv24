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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        String head = "<head> <meta charset=\"UTF-8\">" +
                " <title> Détail d'un CV </title> " +
                " <link rel=\"stylesheet\" href=\"/web/styles/detail_cv.css\"> " +
                "</head>";

        StringBuilder body = new StringBuilder("<body>");
        Cv cv = cvService.getCv(id);

        if (cv != null) {
            body.append("<div class=\"cv\">");
            body.append(p.getInformationOnIdentity(cv.getIdentite(), true));
            body.append(p.getInformationOnObjective(cv.getObjectif()));

            if (cv.getProf() != null) {
                body.append("<h2>Les professions</h2>");
                body.append(p.getInformationOnProfession(cv.getProf()));
            }
            body.append("<h2>Les diplomes</h2>");
            body.append(p.getAllInformationOnDiplomas(cv.getCompetences()));
            if (cv.getCompetences().getCertif() != null && !cv.getCompetences().getCertif().isEmpty()) {
                body.append("<h2>Les certificats</h2>");
                body.append(p.getAllInformationOnCertificate(cv.getCompetences()));
            }

            if (cv.getDivers() != null) {
                body.append("<h2>Les langues</h2>");
                body.append(p.getAllInformationOnLanguages(cv.getDivers()));
                if (cv.getDivers().getAutre() != null && !cv.getDivers().getAutre().isEmpty()) {
                    body.append("<h2>Les informations complémentaires</h2>");
                    body.append(p.getAllComplementaryInformation(cv.getDivers()));
                }
            }
            body.append("</div>");
        } else {
            body.append("<div id=\"error\"> ").append(id).append(" | ERROR </div>");
        }
        String returnHome = "<footer><a href=\"/\">Retour sur la page d'accueil</a></footer>";
        String result = "<html>" + head + body + returnHome + "</body>";

        int valueStatus = cv != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(valueStatus).body(result);
    }

    @GetMapping(value = "xml", produces = "application/xml")
    public ResponseEntity<String> getAllDetailInXMLFormat(@RequestParam(value = "id") Integer id) {
        Cv cv = cvService.getCv(id);

        String result = "<response><id>" + id + "</id>";
        if (cv != null) {
            Page p = new XMLFormat();
            result += p.getInformationOnIdentity(cv.getIdentite(), true)
                    + p.getInformationOnObjective(cv.getObjectif());
            if (cv.getProf() != null) {
                result += p.getInformationOnProfession(cv.getProf());
            }
            result += p.getAllInformationOnDiplomas(cv.getCompetences());

            if (cv.getCompetences().getCertif() != null && !cv.getCompetences().getCertif().isEmpty()) {
                result += p.getAllInformationOnCertificate(cv.getCompetences());
            }
            if (cv.getDivers() != null) {
                result += p.getAllInformationOnLanguages(cv.getDivers());
                if (cv.getDivers().getAutre() != null && !cv.getDivers().getAutre().isEmpty()) {
                    result += p.getAllComplementaryInformation(cv.getDivers());
                }
            }
        } else {
            result += "<status>ERROR</status>";
        }
        result += "</response>";

        int valueStatus = cv != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(valueStatus).body(result);
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
        if (deletedCv != null) {
            this.cvService.deleteCv(deletedCv);
             response += "<id>" + deletedCv.getId() + "</id>";
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
        final LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(date);
        } catch (Exception e) {
            return "<response><status>ERROR</status></response>";
        }

        List<Cv> cvSearch = new ArrayList<>();
        for (Cv cv : this.cvService.getAllCvs()) {
            if (cv.getObjectif().getDescription().contains(obj)) {
                cvSearch.add(cv);
            } else if (!cvSearch.contains(cv) && cv.getProf() != null) {
                for (Detail d : cv.getProf().getDetail()) {
                    if (d.getDatedeb().isAfter(dateTime) || d.getDatedeb().equals(dateTime)) {
                        cvSearch.add(cv);
                    }
                }
            } else if (!cvSearch.contains(cv)) {
                for (Certif certif : cv.getCompetences().getCertif()) {
                    if (certif.getDatedeb().isAfter(dateTime) || certif.getDatedeb().equals(dateTime)) {
                        cvSearch.add(cv);
                    }
                }
            }
            if (!cvSearch.contains(cv)) {
                List<Diplome> diplomas = cv.getCompetences().getDiplome();
                for (Diplome diplome : diplomas) {
                    Date dateOfObtention = diplome.getDate();
                    LocalDateTime localDateDiplome = dateOfObtention.toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDateTime();
                    if (localDateDiplome.isAfter(dateTime) || localDateDiplome.equals(dateTime)) {
                        cvSearch.add(cv);
                        break;
                    }
                }
            }
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