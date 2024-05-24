package univ.rouen.cv24.templates;

import univ.rouen.cv24.entity.*;
import java.util.List;

public class HTMLFormat implements Page {
    @Override
    public String getInformationOnIdentity(Identite id, boolean displayAllInformation) {
        String informationOnIdentity = "<div class=\"identity\">" +
                "<h2>Identité</h2>" +
                "<p>" + id.getGenre().getValue() + " </p>" +
                "<p>" + id.getNom() + "</p>" +
                "<p>" + id.getPrenom() + "</p>";

        if (displayAllInformation) {
            if (id.getTel() != null) {
                informationOnIdentity += "<p> Numéro de téléphone : " + id.getTel() + "</p>";
            }
            if (id.getMel() != null) {
                informationOnIdentity += "<p> Adresse mail : " + id.getMel() + "</p>";
            }
        }
        informationOnIdentity += "</div>";

        return informationOnIdentity;
    }

    @Override
    public String getAllInformationOnObjective(Objectif objectif) {
        String informationOnObjectif = "<div class=\"objectif\">" +
                "<h2>Poste recherché</h2>" +
                "<p> Un " + objectif.getStatut().getValue() + " en tant que " + objectif.getDescription() + "</p>";

        informationOnObjectif += "</div>";

        return informationOnObjectif;
    }

    @Override
    public String getAllInformationOnProfession(Prof prof) {
        if (prof == null) {
            return "";
        }

        StringBuilder informationsDetails = new StringBuilder("<div class=\"details\">");

        for (Detail d : prof.getDetail()) {
            String detail = "<div> ";
            detail += "<h3>" + d.getTitreDetail() + "</h3>";
            detail += "<p>" + d.getDatedeb();
            if (d.getDatefin() != null) {
                detail += " | " + d.getDatefin();
            }
            detail += "</p>";
            detail += "</div>";
            informationsDetails.append(detail);
        }
        informationsDetails.append("</div>");
        return informationsDetails.toString();
    }

    @Override
    public String getAllInformationOnMaxDiploma(Competences competences) {
        Diplome maxDiplome = competences.getDiplome().getFirst();
        for (Diplome d : competences.getDiplome()) {
            if (d.getNiveau() > maxDiplome.getNiveau()) {
                maxDiplome = d;
            }
        }

        return "<div class=\"diplomes\"> <h2>Diplome</h2><p>Diplome le plus haut : "
                + maxDiplome.getTitre() + "</p></div>";
    }

    @Override
    public String getAllInformationOnDiplomas(Competences competences) {
        StringBuilder informationsDiplomas = new StringBuilder("<div class=\"diplomes\">");

        for (Diplome d : competences.getDiplome()) {
            String diplome = "<div> ";
            diplome += "<h3>" + d.getTitre() + "</h3>";
            diplome += "<p>" + d.getNiveau() + " | " + d.getDate().toString() + "</p>";
            if (d.getInstitut() != null) {
                diplome += "<p>" + d.getInstitut() + "</p>";
            }
            diplome += "</div>";
            informationsDiplomas.append(diplome);
        }
        informationsDiplomas.append("</div>");
        return informationsDiplomas.toString();
    }

    @Override
    public String getAllInformationOnCertificate(Competences competences) {
        StringBuilder informationsDiplomas = new StringBuilder("<div class=\"certifs\">");

        for (Certif d : competences.getCertif()) {
            String diplome = "<div> ";
            diplome += "<h3>" + d.getTitreCert() + "</h3>";
            diplome += "<p>" + d.getDatedeb();
            if (d.getDatefin() != null) {
                diplome += " | " + d.getDatefin();
            }
            diplome += "</p>";
            diplome += "</div>";
            informationsDiplomas.append(diplome);
        }
        informationsDiplomas.append("</div>");
        return informationsDiplomas.toString();
    }

    @Override
    public String getAllInformationOnLanguages(Divers divers) {
        StringBuilder informationsLanguages = new StringBuilder("<div class=\"langs\">");

        for (Lv l : divers.getLv()) {
            String lang = "<div> ";
            lang += "<h3>" + l.getLang() + " | " + l.getCert().getValue() +  "</h3>";

            if (l.getNivi() != null) {
                lang += "<p> Niveau : " + l.getNivi() + "</p>";
            }
            if (l.getNivs() != null) {
                lang += "<p> Niveau : " + l.getNivs().getValue() + "</p>";
            }

            lang += "</div>";
            informationsLanguages.append(lang);
        }
        informationsLanguages.append("</div>");
        return informationsLanguages.toString();
    }

    @Override
    public String getAllComplementaryInformation(Divers divers) {
        if (divers.getAutre().isEmpty()) {
            return "";
        }

        StringBuilder informationsComments = new StringBuilder("<div class=\"comments\">");
        for (Autre a : divers.getAutre()) {
            String autre = "<div> ";
            autre += "<h2>" + a.getTitre() + "</h2>";
            if (a.getComment() != null) {
                autre += "<p>" + a.getComment() + "</p>";
            }
            autre += "</div>";
            informationsComments.append(autre);
        }
        informationsComments.append("</div>");
        return informationsComments.toString();
    }

    public String getAllCv(List<Cv> cvs) {
        String head = "<!DOCTYPE html><html lang=\"fr\"><head> <meta charset=\"UTF-8\">" +
                " <title> Liste de CV </title> " +
                " <link rel=\"stylesheet\" href=\"/web/styles/detail_cv.css\"> " +
                "</head>";

        StringBuilder body = new StringBuilder("<body>");
        for (Cv cv : cvs) {
            body.append("<div class=\"info\"><a href=\"/cv24/html?id=")
                    .append(cv.getId()).append("\" class=\"cv_id\">").append(cv.getId()).append("</a>");
            body.append("<div>");
            body.append(this.getInformationOnIdentity(cv.getIdentite(), false));
            body.append(this.getAllInformationOnObjective(cv.getObjectif()));
            body.append(this.getAllInformationOnMaxDiploma(cv.getCompetences()));
            body.append("</div></div>");
        }

        String returnHome ="<footer><a href=\"/\">Retour sur la page d'accueil</a></footer>";
        return head + body + returnHome + "</body></html>";
    }

    @Override
    public String getAllInformationOnCv(Cv cv, int id) {
        String head = "<!DOCTYPE html><html lang=\"fr\"><head> <meta charset=\"UTF-8\">" +
                " <title> Détail d'un CV </title> " +
                " <link rel=\"stylesheet\" href=\"/web/styles/detail_cv.css\"> " +
                "</head>";

        StringBuilder body = new StringBuilder("<body>");


        if (cv != null) {
            body.append("<div class=\"cv\">");
            body.append(this.getInformationOnIdentity(cv.getIdentite(), true));
            body.append(this.getAllInformationOnObjective(cv.getObjectif()));

            if (cv.getProf() != null) {
                body.append("<h2>Les professions</h2>");
                body.append(this.getAllInformationOnProfession(cv.getProf()));
            }
            body.append("<h2>Les diplomes</h2>");
            body.append(this.getAllInformationOnDiplomas(cv.getCompetences()));
            if (cv.getCompetences().getCertif() != null && !cv.getCompetences().getCertif().isEmpty()) {
                body.append("<h2>Les certificats</h2>");
                body.append(this.getAllInformationOnCertificate(cv.getCompetences()));
            }
            if (cv.getDivers() != null) {
                body.append("<h2>Les langues</h2>");
                body.append(this.getAllInformationOnLanguages(cv.getDivers()));
                if (cv.getDivers().getAutre() != null && !cv.getDivers().getAutre().isEmpty()) {
                    body.append("<h2>Les informations complémentaires</h2>");
                    body.append(this.getAllComplementaryInformation(cv.getDivers()));
                }
            }
            body.append("</div>");
        } else {
            body.append("<div id=\"error\"> ").append(id).append(" | ERROR </div>");
        }
        String returnHome = "<footer><a href=\"/\">Retour sur la page d'accueil</a></footer>";
        return head + body + returnHome + "</body></html>";
    }
}