package univ.rouen.cv24.service;

import org.springframework.stereotype.Service;
import univ.rouen.cv24.entity.Cv;
import univ.rouen.cv24.repository.CvRepository;
import univ.rouen.cv24.util.XML;
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
}