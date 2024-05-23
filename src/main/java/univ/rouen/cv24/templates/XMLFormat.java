package univ.rouen.cv24.templates;

import univ.rouen.cv24.entity.*;
import java.util.List;

public class XMLFormat implements Page {

    @Override
    public String getInformationOnIdentity(Identite id, boolean displayAllInformation) {
        String identiteResult = "<identite>";
        identiteResult += "<genre>" + id.getGenre().getValue() + "</genre>";
        identiteResult += "<prenom>" + id.getPrenom() + "</prenom>";
        identiteResult += "<nom>" + id.getNom() + "</nom>";

        if (displayAllInformation) {
            if (id.getMel() != null) {
                identiteResult += "<mail>" + id.getMel() + "</mail>";
            }
            if (id.getTel() != null) {
                identiteResult += "<tel>" + id.getTel() + "</tel>";
            }
        }

        return identiteResult + "</identite>";
    }

    @Override
    public String getInformationOnObjective(Objectif objectif) {
        String objectifResult = "<objectif statut=\"" + objectif.getStatut().getValue() + "\">";
        objectifResult += objectif.getDescription() + "</objectif>";

        return objectifResult;
    }

    @Override
    public String getInformationOnProfession(Prof prof) {
        if (prof == null) {
            return "";
        }

        StringBuilder informationsDetails = new StringBuilder("<details>");

        for (Detail d : prof.getDetail()) {
            String detail = "<detail> ";
            detail += "<titre>" + d.getTitreDetail() + "</titre>";
            detail += "<date>" + d.getDatedeb();
            if (d.getDatefin() != null) {
                detail += "|" + d.getDatefin();
            }
            detail += "</p>";
            detail += "</detail>";
            informationsDetails.append(detail);
        }
        informationsDetails.append("</details>");
        return informationsDetails.toString();
    }

    @Override
    public String getInformationOnMaxDiploma(Competences competences) {
        Diplome maxDiplome = competences.getDiplome().getFirst();
        for (Diplome d : competences.getDiplome()) {
            if (d.getNiveau() > maxDiplome.getNiveau()) {
                maxDiplome = d;
            }
        }

        return "<diplome>" + maxDiplome.getTitre() + "</diplome>";
    }

    @Override
    public String getAllInformationOnDiplomas(Competences competences) {
        StringBuilder informationsDiplomas = new StringBuilder("<diplomes>");
        for (Diplome d : competences.getDiplome()) {
            String diplome = "<diplome> ";
            diplome += "<titre>" + d.getTitre() + "</titre>";
            diplome += "<niveau>" + d.getNiveau() + "</niveau>";
            diplome += "<date>" + d.getDate() + "</date>";
            if (d.getInstitut() != null) {
                diplome += "<institut>" + d.getInstitut() + "</institut>";
            }
            diplome += "</diplome>";
            informationsDiplomas.append(diplome);
        }

        informationsDiplomas.append("</diplomes>");

        return informationsDiplomas.toString();
    }

    @Override
    public String getAllInformationOnCertificate(Competences competences) {
        StringBuilder informationsDiplomas = new StringBuilder("<certifs>");

        for (Certif d : competences.getCertif()) {
            String certif = "<certif> ";
            certif += "<titre>" + d.getTitreCert() + "</titre>";
            certif += "<date>" + d.getDatedeb();
            if (d.getDatefin() != null) {
                certif += "|" + d.getDatefin();
            }
            certif += "</date>";
            certif += "</certif>";
            informationsDiplomas.append(certif);
        }
        informationsDiplomas.append("</certifs>");
        return informationsDiplomas.toString();
    }

    @Override
    public String getAllInformationOnLanguages(Divers divers) {
        StringBuilder informationsLanguages = new StringBuilder("<langs>");

        for (Lv l : divers.getLv()) {
            String lang = "<lang> ";
            lang += "<nom>" + l.getLang() +  "</nom>";
            lang += "<cert>" + l.getCert().getValue() +  "</cert>";

            if (l.getNivi() != null) {
                lang += "<nivi>" + l.getNivi() + "</nivi>";
            }
            if (l.getNivs() != null) {
                lang += "<nivs>" + l.getNivs().getValue() + "</nivs>";
            }

            lang += "</lang>";
            informationsLanguages.append(lang);
        }
        informationsLanguages.append("</langs>");
        return informationsLanguages.toString();
    }

    @Override
    public String getAllComplementaryInformation(Divers divers) {
        if (divers.getAutre().isEmpty()) {
            return "";
        }

        StringBuilder informationsComments = new StringBuilder("<comments>");
        for (Autre a : divers.getAutre()) {
            String autre = "<comment> ";
            autre += "<titre>" + a.getTitre() + "</titre>";
            if (a.getComment() != null) {
                autre += "<c>" + a.getComment() + "</c>";
            }
            autre += "</comment>";
            informationsComments.append(autre);
        }
        informationsComments.append("</comments>");
        return informationsComments.toString();
    }

    public String getAllCv(List<Cv> cvs) {
        StringBuilder result = new StringBuilder("<response>");
        for (Cv cv : cvs) {
            String idResult = "<id>" + cv.getId() + "</id>";

            String identityResult = this.getInformationOnIdentity(cv.getIdentite(), false);
            String objectiveResult = this.getInformationOnObjective(cv.getObjectif());
            String diplomaResult = this.getInformationOnMaxDiploma(cv.getCompetences());

            result.append("<cv>").append(idResult).append(identityResult).append(objectiveResult)
                    .append(diplomaResult).append("</cv>");
        }
        result.append("</response>");
        return result.toString();
    }
}