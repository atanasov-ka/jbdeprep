package org.vb.backend.jpa.service;

import org.vb.backend.jpa.dao.LanguageDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class LanguageService {

    @EJB
    private LanguageDAO languageDAO;

    public void registerNewLanguage(String abbreviation) {
        languageDAO.registerNewLanguage(abbreviation);
    }
}
