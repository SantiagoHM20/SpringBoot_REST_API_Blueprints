package edu.eci.arsw.blueprints.services;

import org.springframework.stereotype.Component;


@Component("englishSpellChecker")
public class EnglishSpellChecker implements SpellChecker {
    @Override
    public boolean check(String word) {
        System.out.println(word);
        return word != null && !word.contains("xx");
    }
}
