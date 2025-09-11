package edu.eci.arsw.blueprints.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GrammarChecker {

    private final SpellChecker spellChecker;

    @Autowired
    public GrammarChecker(@Qualifier("englishSpellChecker") SpellChecker spellChecker) {
        this.spellChecker = spellChecker;
    }

    public boolean check(String text) {
        return spellChecker.check(text);
    }
}
