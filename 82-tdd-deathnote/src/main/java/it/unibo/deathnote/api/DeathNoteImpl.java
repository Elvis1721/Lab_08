package it.unibo.deathnote.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A DeathNote is a special book intended to be used by Shinigamis (Death gods) to kill humans,
 * thereby extending their own lives. Each DeathNote has a set of rules that must be followed
 * in order to use it properly.
 * Implementation of the interface DeatNote.
 */
public class DeathNoteImpl implements DeathNote {

    private final Map<String, Death> victims;
    private Death last;

    /**
     * Constructor of the class DeathNoteImpl.
     */
    public DeathNoteImpl() {
        victims = new HashMap<>();
        last = null;
    }

    /**
     * Returns the rule with the given number.
     *
     * @param ruleNumber the number of the rule to return. The first rule has number one
     * @return the rule with the given number
     * @throws IllegalArgumentException if the given rule number is smaller than 1 or larger
     *     than the number of rules
     */
    @Override 
    public String getRule(final int ruleNumber) {
        final int numRul = 14;
        if (ruleNumber < 1 || ruleNumber > numRul) {
            throw new IllegalArgumentException("Ciao, this è an errore");
        }
        return RULES.get(ruleNumber - 1);
    }

    /**
     * The human whose name is written in this DeathNote will die.
     *
     * @param name the name of the human to kill
     * @throws NullPointerException if the given name is null.
     */
    @Override 
    public void writeName(final String name) {
        Objects.requireNonNull(name);
        if (!isNameWritten(name)) {
            victims.put(name, new Death());
            victims.get(name).timer = System.currentTimeMillis();
            last = victims.get(name);
        }
    }

    /**
     * If the cause of death is written within the next 40 milliseconds of writing the person's
     * name, it will happen.
     *
     * @param cause the cause of the human's death
     * @return true if the cause was written within 40 milliseconds, false otherwise
     * @throws IllegalStateException if there is no name written in this DeathNote,
     *     or the cause is null
     */
    @Override
    public boolean writeDeathCause(final String cause) {
        if (victims.keySet().isEmpty()) {
            throw new IllegalStateException();
        }
        final long causeDelay = 40;
        if ((System.currentTimeMillis() - last.timer) > causeDelay) {
            return false;
        }
        last.cause = cause;
        return true;
    }

    /**
     * After writing the cause of death, details of the death should be written in the next
     * 6 seconds and 40 milliseconds.
     *
     * @param details the details of the human's death
     * @return true if the details were written within 6 seconds and 40 milliseconds, false otherwise
     * @throws IllegalStateException if there is no name written in this DeathNote,
     *     or the details are null
     */
    @Override
    public boolean writeDetails(final String details) {
        if (victims.keySet().isEmpty() || last.details == null) {
            throw new IllegalStateException();
        }
        final long detailsDelay = 6040;
        if ((System.currentTimeMillis() - last.timer) > detailsDelay) {
            return false;
        }
        last.details = details;
        return true;
    }

    /**
     * Provides the cause of death of the person with the given name.
     *
     * @param name the name of the person whose death cause to return
     * @return the death cause of the person with the given name.
     *     If the cause of death is not specified, the method will return "heart attack".
     * @throws IllegalArgumentException if the provider name is not written in this DeathNote
     */
    @Override 
    public String getDeathCause(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException();
        }
        return victims.get(name).cause;
    }

    /**
     * Provides the details of the death of the person with the given name.
     *
     * @param name the name of the person whose death cause to return
     * @return the death details of the person with the given name,
     *     or an empty string if no details have been provided.
     * @throws IllegalArgumentException if the provider name is not written in this DeathNote.
     */
    @Override 
    public String getDeathDetails(final String name) {
        if (!isNameWritten(name)) {
            throw new IllegalArgumentException();
        }
        return victims.get(name).details;
    }

    /**
     * Checks if the given name is written in this DeathNote.
     *
     * @param name the name of the person
     * @return true if the given name is written in this DeathNote, false otherwise
     */
    @Override 
    public boolean isNameWritten(final String name) {
        for (final String victim : victims.keySet()) {
            if (victim.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private static class Death {
        private String cause;
        private String details;
        private long timer;

        Death() {
            this.cause = "heart attack";
            this.details = "";
            this.timer = System.currentTimeMillis();
        }
    }
}
