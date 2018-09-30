package org.vb.backend.jpa.dao;

import org.vb.backend.jpa.pojos.Language;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LanguageDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public void registerNewLanguage(String abbreviation) {
        Language newLang = new Language();
        newLang.setAbbreviation(abbreviation);
        entityManager.persist(newLang);
    }
}
