package edu.eci.arsw.blueprints.services;

import org.springframework.stereotype.Component;


@Component("spanishSpellChecker")
public class SpanishSpellChecker implements SpellChecker {

    @Override
    public boolean check(String word) {
        System.out.println("Checking Spanish spelling...");
        return word != null && !word.contains("zz");
    }
}


