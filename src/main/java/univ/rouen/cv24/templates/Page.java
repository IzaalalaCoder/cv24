package univ.rouen.cv24.templates;

import univ.rouen.cv24.entity.*;
import java.util.List;

public interface Page {

    String getInformationOnIdentity(Identite id, boolean displayAllInformation);

    String getAllInformationOnObjective(Objectif objectif);

    String getAllInformationOnProfession(Prof prof);

    String getAllInformationOnMaxDiploma(Competences competences);

    String getAllInformationOnDiplomas(Competences competences);

    String getAllInformationOnCertificate(Competences competences);

    String getAllInformationOnLanguages(Divers divers);

    String getAllComplementaryInformation(Divers divers);

    String getAllCv(List<Cv> cvs);

    String getAllInformationOnCv(Cv cv, int id);
}