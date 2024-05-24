package univ.rouen.cv24.service;

import org.springframework.stereotype.Service;
import univ.rouen.cv24.entity.Certif;
import univ.rouen.cv24.entity.Cv;
import univ.rouen.cv24.entity.Detail;
import univ.rouen.cv24.entity.Diplome;
import univ.rouen.cv24.repository.CvRepository;
import univ.rouen.cv24.util.XML;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CvService {

    private final CvRepository cvRepository;

    public CvService(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    public List<Cv> getAllCvs() {
        return cvRepository.findAll();
    }

    public Cv getCv(int id) {
        return this.cvRepository.findById(id).orElse(null);
    }

    public Cv addCv(String input) throws Exception {
        if (!XML.validateXMLSchema(input)) {
            throw new IllegalArgumentException("INVALID");
        }

        Cv cv = XML.convertXmlToCv(input);

        if (cvRepository.existsByIdentiteGenreAndIdentiteNomAndIdentitePrenomAndIdentiteTel(
                cv.getIdentite().getGenre(),
                cv.getIdentite().getNom(),
                cv.getIdentite().getPrenom(),
                cv.getIdentite().getTel())) {
            throw new IllegalArgumentException("DUPLICATED");
        }
        return cvRepository.save(cv);
    }

    public void deleteCv(Cv deletedCv) {
        this.cvRepository.delete(deletedCv);
    }

    public List<Cv> searchCv(String objectif, String date) throws Exception {
        List<Cv> cvSearch = new ArrayList<>();

        final LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(date);
        } catch (Exception e) {
            throw new Exception();
        }

        for (Cv cv : this.getAllCvs()) {
            if (cv.getObjectif().getDescription().contains(objectif)) {
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

        return cvSearch;
    }
}