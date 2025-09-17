package no.oslomet.cs.algdat;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.*;

class Oppgave1Test {
    @Test
    void antall() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        tre.leggInn(1); tre.leggInn(2); tre.leggInn(3);
        assertEquals(3, tre.antall(), "Feil antall etter å ha lagt inn element.");
    }

    @Test
    void leggInnNull() {
        SøkeBinærTre<String> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        assertThrows(NullPointerException.class, () -> tre.leggInn(null), "Kaster ingen eller gal feilmelding når vi prøver legge inn null-verdier");
    }

    @Test
    void element() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        tre.leggInn(1); tre.leggInn(2); tre.leggInn(3);
        assertTrue(tre.inneholder(1), "Treet inneholder ikke innlagte element.");
        assertTrue(tre.inneholder(2), "Treet inneholder ikke innlagte element.");
        assertTrue(tre.inneholder(3), "Treet inneholder ikke innlagte element.");
        assertFalse(tre.inneholder(0), "Treet inneholder ikke-innlagte element.");
    }
}

class Oppgave2Test {
    @Test
    void finnNull() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        int[] a = {0, 1, 2, 3, 4};
        for (int verdi : a) tre.leggInn(verdi);
        assertDoesNotThrow(() -> tre.antall(null), "Gir feilmelding om jeg prøver finne null");
        assertEquals(0, tre.antall(null), "Gir feil antall null-verdier.");
    }
    @Test
    void ettElement() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        assertEquals(0, tre.antall(1), "Finner verdier i tomt tre.");
        tre.leggInn(1);
        assertEquals(1, tre.antall(1), "Finner ikke verdi lagt inn i tre.");
        tre.leggInn(1);
        assertEquals(2, tre.antall(1), "Finner ikke begge verdier lagt inn i tre.");
    }

    @Test
    void blandetTre() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        int[] a = {1, 7, 1, 6, 1, 5, 1, 4, 1, 1, 1, 3};
        for (int verdi : a) tre.leggInn(verdi);
        int[] b = {0, 7, 0, 1, 1, 1, 1, 1};
        for (int i = 0; i < 8; i++)
            assertEquals(b[i], tre.antall(i), "Feil antall av "+i);
    }
}

class Oppgave3Test {
    @Test
    void postTom() {
        SøkeBinærTre<Integer> s = new SøkeBinærTre<>(Comparator.naturalOrder());
        assertDoesNotThrow(s::toStringPostOrder, "Får feilmelding på enten førstePostorden eller nestePostorden på tomt tre.");
        assertEquals("[]", s.toStringPostOrder(), "Enten førstePostorden eller nestePostorden gir feil svar på tomt tre.");
    }

    @Test
    void postEttElement() {
        SøkeBinærTre<Integer> s = new SøkeBinærTre<>(Comparator.naturalOrder());
        s.leggInn(10);
        assertDoesNotThrow(s::toStringPostOrder, "Får feilmelding på enten førstePostorden eller nestePostorden på tre med ett element.");
        assertEquals("[10]", s.toStringPostOrder(), "Enten førstePostorden eller nestePostorden gir feil svar for tre med ett element.");
    }

    @Test
    void postOrdenFlereElement() {
        SøkeBinærTre<Integer> s = new SøkeBinærTre<>(Comparator.naturalOrder());
        int[] tabell = {10, 6, 14, 1, 8, 12, 3, 7, 9, 11, 13, 2, 5, 4};
        for (int i : tabell) s.leggInn(i);
        assertDoesNotThrow(s::toStringPostOrder, "Får feilmelding på enten førstePostorden eller nestePostorden på større tre.");
        assertEquals("[2, 4, 5, 3, 1, 7, 9, 8, 6, 11, 13, 12, 14, 10]", s.toStringPostOrder(), "Feil postorden på større tre.");
    }

    @Test
    void postOrdenRepetert() {
        SøkeBinærTre<Integer> s = new SøkeBinærTre<>(Comparator.naturalOrder());
        for (int i = 0; i < 4; i++) s.leggInn(10);
        assertDoesNotThrow(s::toStringPostOrder, "Får feilmelding på tre med repeterte elementer.");
        assertEquals("[10, 10, 10, 10]", s.toStringPostOrder(), "Postorden er feil i tre med repeterte elementer. Feilen ligger muligens i leggInn");
    }

    @Test
    void postOrdenOmstokking() {
        SøkeBinærTre<Integer> s = new SøkeBinærTre<>(Comparator.naturalOrder());
        int[] tabell = {5, 4, 3, 2, 1};
        for (int i : tabell) s.leggInn(i);
        assertDoesNotThrow(s::toStringPostOrder, "Får feilmelding på tre med element lagt inn i 5, 4, 3, 2, 1-rekkefølge.");
        assertEquals("[1, 2, 3, 4, 5]", s.toStringPostOrder(), "Gir feil svar på ");
    }
}

class Oppgave4Test {
    @Test
    void postordenVanlig() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        int[] a = {10, 6, 14, 1, 8, 12, 3, 7, 9, 11, 13, 2, 5, 4};
        for (int verdi : a) tre.leggInn(verdi);

        StringJoiner postorden = new StringJoiner(":");
        Oppgave<Integer> oppgave = c -> postorden.add(c.toString());

        tre.postOrden(oppgave);
        assertEquals("2:4:5:3:1:7:9:8:6:11:13:12:14:10", postorden.toString(),
                "Postorden-metoden gjør ikke oppgaven rett");
    }

    @Test
    void postordenRekursiv() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());

        int[] a = {10, 6, 14, 1, 8, 12, 3, 7, 9, 11, 13, 2, 5, 4};
        for (int verdi : a) tre.leggInn(verdi);

        StringJoiner postordenRekursiv = new StringJoiner(":");
        Oppgave<Integer> oppgave = c -> postordenRekursiv.add(c.toString());

        tre.postOrdenRekursiv(oppgave);
        assertEquals("2:4:5:3:1:7:9:8:6:11:13:12:14:10", postordenRekursiv.toString(),
                "Den rekursive postorden-metoden gjør ikke oppgaven rett.");
    }
}

class Oppgave5Test {
    @Test
    void leggInnFjern() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        tre.leggInn(6);
        tre.fjern(6);
        assertEquals("[]", tre.toStringPostOrder(), "Fjerner ikke element riktig.");
    }

    @Test
    void fjernTomt() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        assertDoesNotThrow(() -> tre.fjern(0), "Kaster feilmelding om vi fjerner fra tomt tre.");
    }

    @Test
    void fjernIkkeeksisterende() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        int[] a = {6, 3, 9, 1, 5, 7, 10, 2, 4, 8, 11, 6, 8};
        for (int verdi : a) tre.leggInn(verdi);

        assertFalse(tre.fjern(12), "Gir feil svar når vi fjerner ikke-eksisterende tall.");
        assertEquals("[2, 1, 4, 5, 3, 6, 8, 8, 7, 11, 10, 9, 6]", tre.toStringPostOrder(),
                "Endrer på treet når vi fjerner ikke-eksisterende verdi.");
        assertEquals(13, tre.antall(), "Endrer på antall elementer når vi prøver fjerne ikke-eksisterende element.");
    }

    @Test
    void fjernEksisterende() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        int[] a = {6, 3, 9, 1, 5, 7, 10, 2, 4, 8, 11, 6, 8};
        for (int verdi : a) tre.leggInn(verdi);

        assertTrue(tre.fjern(2), "Gir feil svar når vi fjerner eksisterende element");
        assertEquals("[1, 4, 5, 3, 6, 8, 8, 7, 11, 10, 9, 6]", tre.toStringPostOrder(), "Fjerner ikke element eller gjør noe annet galt med treet.");
        assertEquals(12, tre.antall(), "Minker ikke antall elementer når et blir fjernet.");
        assertFalse(tre.inneholder(2), "Finner verdi i treet etter fjerning.");
    }

    @Test
    void fjernLike() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        int[] a = {6, 3, 9, 1, 5, 7, 10, 2, 4, 8, 11, 6, 8};
        for (int verdi : a) tre.leggInn(verdi);

        assertTrue(tre.fjern(8), "Gir feil svar når vi fjerner verdi det er flere av.");
        assertEquals("[2, 1, 4, 5, 3, 6, 8, 7, 11, 10, 9, 6]", tre.toStringPostOrder(), "Element fjernet for mange ganger eller annet galt gjort med treet.");
        assertEquals(12, tre.antall(), "Minker ikke antall elementer eller minker for mye når element det finnes flere av fjernes.");
        assertTrue(tre.inneholder(8), "Finner ikke andre verdi i treet etter at første er fjernet.");
    }

    @Test
    void fjernAlleTomt() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        assertDoesNotThrow(() -> tre.fjernAlle(0), "Gir feilmelding om vi fjerner alle fra tomt tre.");
    }

    @Test
    void fjernAlleIkkeeksisterende() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        int[] a = {1, 4, 1, 3, 1, 2, 1, 1};
        for (int verdi : a) tre.leggInn(verdi);
        assertDoesNotThrow(() -> tre.fjernAlle(5), "Gir feilmelding når vi ferner ikke-eksisterende verdi.");
        assertEquals(0, tre.fjernAlle(5), "Gir feil svar på antall fjernede verdier for ikke-eksisterende verdi.");
        assertEquals(8, tre.antall(), "Endrer antall når ingenting blir fjernet.");
    }

    @Test
    void fjernAlleKunEn() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        int[] a = {1, 4, 1, 3, 1, 2, 1, 1};
        for (int verdi : a) tre.leggInn(verdi);
        assertEquals(1, tre.fjernAlle(4), "Gir feil svar når vi fjerner unik verdi.");
        assertEquals(7, tre.antall(), "Feil antall når ett element fjernes.");
        assertFalse(tre.inneholder(4), "Finner fremdeles fjernet verdi.");
    }

    @Test
    void fjernAlleFlere() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        int[] a = {1, 4, 1, 3, 1, 2, 1, 1};
        for (int verdi : a) tre.leggInn(verdi);
        assertEquals(5, tre.fjernAlle(1), "Gir feil svar når vi fjerner flere elementer.");
        assertEquals(3, tre.antall(), "Feil antall etter å ha fjernet flere verdier.");
        assertFalse(tre.inneholder(1), "Finner fremdeles fjernet verdi etter å ha fjernet flere.");
    }

    @Test
    void nullstilling() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        int[] a = {6, 3, 9, 1, 5, 7, 10, 2, 4, 8, 11, 6, 8};
        for (int verdi : a) tre.leggInn(verdi);

        assertDoesNotThrow(tre::nullstill, "Å nullstille tre skal ikke gi feilmelding.");
        assertEquals(0, tre.antall(), "Feil antall i nullstilt tre.");
        assertEquals("[]", tre.toStringPostOrder(), "Ikke-tomt nullstilt tre.");
    }

    @Test
    void nullstillTomt() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        assertDoesNotThrow(tre::nullstill, "Å  nullstille tomt tre skal ikke gi feilmelding.");
    }

    @Test
    void nullstillingPekere() {
        SøkeBinærTre<Integer> tre =
                new SøkeBinærTre<>(Comparator.naturalOrder());
        int[] a = {2, 1, 3, 2, 4, 5};
        for (int verdi : a) tre.leggInn(verdi);

        Iterator<Integer> it = tre.iterator();
        it.next(); it.next();
        tre.nullstill(); it.next();

        assertFalse(it.hasNext(),
                "Nodeverdier og pekere i tre skal alle nullstilles når man nullstiller. Har du kun fjernet hode og satt antall til 0?");
    }
}