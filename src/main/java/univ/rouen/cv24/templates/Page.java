package univ.rouen.cv24.templates;

import univ.rouen.cv24.entity.Competences;
import univ.rouen.cv24.entity.Divers;
import univ.rouen.cv24.entity.Identite;
import univ.rouen.cv24.entity.Prof;
import univ.rouen.cv24.entity.Objectif;

public interface Page {

    String getInformationOnIdentity(Identite id, boolean displayAllInformation);

    String getInformationOnObjective(Objectif objectif);

    String getInformationOnProfession(Prof prof);

    String getInformationOnMaxDiploma(Competences competences);

    String getAllInformationOnDiplomas(Competences competences);

    String getAllInformationOnCertificate(Competences competences);

    String getAllInformationOnLanguages(Divers divers);

    String getAllComplementaryInformation(Divers divers);
}
