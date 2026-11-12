package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNoteImpl;

class TestDeathNote {

    private static final String KIRA = "Light";
    private static final String ELLE = "Ryuzaki";
    private static final int SLEEP_CAUSE = 100;
    private static final int SLEEP_DETAILS = 6100;
    private static final String ACC = "karting accident";
    private final DeathNoteImpl deathNote = new DeathNoteImpl();

        @Test
        void testRuleNumber() {
            try {
                this.deathNote.getRule(0);
                fail("The test_RuleRumber has failed");
            } catch (final IllegalArgumentException exception) {
                IO.println("Correct exception thrown");
                final String mess = exception.getMessage();
                assertNotNull(mess);
                assertFalse(mess.isBlank() || mess.isEmpty());
            }
        }

        @Test
        void testIsRuleEmpty() {
            final int numRul = 14;
            for (int i = 1; i < numRul; i++) {
                final String string = this.deathNote.getRule(i);
                assertNotNull(string);
                assertFalse(string.isBlank());
            }
        }

        @Test
        void testVictim() {
            assertFalse(this.deathNote.isNameWritten(KIRA));
            this.deathNote.writeName(KIRA);
            assertTrue(this.deathNote.isNameWritten(KIRA));
            assertFalse(this.deathNote.isNameWritten(ELLE));
            assertFalse(this.deathNote.isNameWritten(""));
        }

        @Test
        void testCauseOfDeath() throws InterruptedException {
            try {
                this.deathNote.writeDeathCause("shot");
                fail("The test_CauseOfDeath has failed");
            } catch (final IllegalStateException exception) {
                IO.println("testCauseOfDeath IllegalStateException catched");
            }
            this.deathNote.writeName(KIRA);
            assertEquals("heart attack", this.deathNote.getDeathCause(KIRA));
            this.deathNote.writeName(ELLE);
            assertTrue(this.deathNote.writeDeathCause(ACC));
            assertEquals(ACC, this.deathNote.getDeathCause(ELLE));
            Thread.sleep(SLEEP_CAUSE);
            assertFalse(this.deathNote.writeDeathCause("stabbed"));
            assertEquals(ACC, this.deathNote.getDeathCause(ELLE));
        }

        @Test
        void testDeathDetails() throws InterruptedException {
            try {
                this.deathNote.writeDetails("wrong enemy");
                fail("The test_DeathDetails has failed");
            } catch (final IllegalStateException exception) {
                IO.println("testDeathDetails IllegalStateException catched");
            }
            this.deathNote.writeName(KIRA);
            assertTrue(this.deathNote.getDeathDetails(KIRA).isEmpty());
            assertTrue(this.deathNote.writeDetails("ran for too long"));
            assertEquals("ran for too long", this.deathNote.getDeathDetails(KIRA));
            this.deathNote.writeName(ELLE);
            Thread.sleep(SLEEP_DETAILS);
            assertFalse(this.deathNote.writeDetails("plot"));
            assertTrue(this.deathNote.getDeathDetails(ELLE).isEmpty());
        }
}
