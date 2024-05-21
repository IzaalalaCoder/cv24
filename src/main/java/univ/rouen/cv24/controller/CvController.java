package univ.rouen.cv24.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univ.rouen.cv24.entity.Cv;
import univ.rouen.cv24.entity.Diplome;
import univ.rouen.cv24.service.CvService;

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

    @GetMapping(value = "resume/xml", produces = "application/xml")
    public ResponseEntity<String> getAllCVInXMLFormat() {
        List<Cv> cvs = this.cvService.getAllCvs();
        StringBuilder result = new StringBuilder("<response>");
        for (Cv cv : cvs) {
            String idResult = "<id>" + cv.getId() + "</id>";

            // Identite
            String identiteResult = getMinimalInformationOnIdentity(cv);

            // Objectif
            String objectifResult = getInformationOnObjectif(cv);

            // Diplome le plus elevé
            String diplomeResult = getInformationOnMaxDiplome(cv);

            result.append("<cv>").append(idResult).append(identiteResult).append(objectifResult)
                .append(diplomeResult).append("</cv>");
        }
        result.append("</response>");
        return ResponseEntity.status(HttpStatus.OK.value()).body(result.toString());
    }

    @GetMapping(value = "xml", produces = "application/xml")
    public ResponseEntity<String> getAllCVInXMLFormat(@RequestParam(value = "id") Integer id) {
        Cv cv = cvService.getCv(id);

        String result = "<response><id>" + id + "</id>";
        if (cv != null) {
            result += getMinimalInformationOnIdentity(cv) + getInformationOnObjectif(cv) + getInformationOnMaxDiplome(cv);
        } else {
            result += "<status>ERROR</status>";
        }
        result += "</response>";

        Integer valueStatut = cv != null ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(valueStatut).body(result);
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

    // UTILS

    private String getMinimalInformationOnIdentity(Cv cv) {
        String identiteResult = "<identite>";
        identiteResult += "<genre>" + cv.getIdentite().getGenre().getValue() + "</genre>";
        identiteResult += "<prenom>" + cv.getIdentite().getPrenom() + "</prenom>";
        identiteResult += "<nom>" + cv.getIdentite().getNom() + "</nom></identite>";

        return identiteResult;
    }

    private String getInformationOnObjectif(Cv cv) {
        String objectifResult = "<objectif statut=\"" + cv.getObjectif().getStatut().getValue() + "\">";
        objectifResult += cv.getObjectif().getDescription() + "</objectif>";

        return objectifResult;
    }

    private String getInformationOnMaxDiplome(Cv cv) {
        Diplome maxDiplome = cv.getCompetences().getDiplome().getFirst();
        for (Diplome d : cv.getCompetences().getDiplome()) {
            if (d.getNiveau() > maxDiplome.getNiveau()) {
                maxDiplome = d;
            }
        }

        return "<diplome>" + maxDiplome.getTitre() + "</diplome>";
    }
}