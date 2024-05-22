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
        String head = "<head> <meta charset=\"UTF-8\">" +
                " <title> Liste de CV </title> " +
                " <link rel=\"stylesheet\" href=\"/web/styles/cv_list.css\"> " +
                "</head>";

        StringBuilder body = new StringBuilder("<body>");
        for (Cv cv : this.cvService.getAllCvs()) {
            body.append("<div class=\"cv\"><a href=\"http://localhost:8080/cv24/html?id=")
                .append(cv.getId()).append("\" id=\"cv_id\">").append(cv.getId()).append("</a>");

            body.append(p.getInformationOnIdentity(cv.getIdentite(), false));
            body.append(p.getInformationOnObjective(cv.getObjectif()));
            body.append(p.getInformationOnMaxDiploma(cv.getCompetences()));
            body.append("</div>");
        }

        String result = "<html>" + head + body + "</body>";
        return ResponseEntity.status(HttpStatus.OK.value()).body(result);
    }

    @GetMapping(value = "resume/xml", produces = "application/xml")
    public ResponseEntity<String> getAllCVInXMLFormat() {
        Page p = new XMLFormat();
        List<Cv> cvs = this.cvService.getAllCvs();
        StringBuilder result = new StringBuilder("<response>");
        for (Cv cv : cvs) {
            String idResult = "<id>" + cv.getId() + "</id>";

            String identityResult = p.getInformationOnIdentity(cv.getIdentite(), false);
            String objectiveResult = p.getInformationOnObjective(cv.getObjectif());
            String diplomaResult = p.getInformationOnMaxDiploma(cv.getCompetences());

            result.append("<cv>").append(idResult).append(identityResult).append(objectiveResult)
                .append(diplomaResult).append("</cv>");
        }
        result.append("</response>");
        return ResponseEntity.status(HttpStatus.OK.value()).body(result.toString());
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
            body.append("<h2>Les professions</h2>");
            body.append(p.getInformationOnProfession(cv.getProf()));
            body.append("<h2>Les diplomes</h2>");
            body.append(p.getAllInformationOnDiplomas(cv.getCompetences()));
            body.append("<h2>Les certificats</h2>");
            body.append(p.getAllInformationOnCertificate(cv.getCompetences()));
            body.append("<h2>Les langues</h2>");
            body.append(p.getAllInformationOnLanguages(cv.getDivers()));
            body.append("<h2>Les informations complémentaires</h2>");
            body.append(p.getAllComplementaryInformation(cv.getDivers()));
            body.append("</div>");
        } else {
            body.append("<div id=\"error\"> ").append(id).append(" | ERROR </div>");
        }
        String result = "<html>" + head + body + "</body>";

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
                    + p.getInformationOnObjective(cv.getObjectif())
                    + p.getInformationOnProfession(cv.getProf())
                    + p.getAllInformationOnDiplomas(cv.getCompetences())
                    + p.getAllInformationOnCertificate(cv.getCompetences())
                    + p.getAllInformationOnLanguages(cv.getDivers())
                    + p.getAllComplementaryInformation(cv.getDivers());
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
}