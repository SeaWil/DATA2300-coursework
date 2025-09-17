import java.util.NoSuchElementException;

public class Oblig1 {
    //0
    int gruppeMedlemmer() {
        return 1;
    }

    //oppgave 1
    public static int maks(int[] a) {
        // Metoden skal kaste en NoSuchElementException med passende tekst på en tom tabell
        if (a.length == 0) {
            throw new NoSuchElementException("Arrayet er tomt, ingen største verdi");
        }
        //Sammenlikn de to første elementene i tabellen. Hvis det første elementet er størst,
        int temp;
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) {
                // bytt plass på de to verdiene. Fortsett til du har gått gjennom hele tabellen
                temp = a[i - 1];
                a[i - 1] = a[i];
                a[i] = temp;
            }
        }
        return a[a.length - 1];
    }
    //samme algoritme som maks-metoden, men som i stedet returnerer antall ombyttinger som er gjort
    public static int ombyttinger(int[] a) {
        if (a.length == 0) {
            throw new NoSuchElementException("Arrayet er tomt, ingen største verdi");
        }
        int temp;
        int swaps = 0;
        for (int i = 1; i < a.length; i++) {
            if (a[i] < a[i - 1]) {
                temp = a[i - 1];
                a[i - 1] = a[i];
                a[i] = temp;
                swaps++;
            }
        }
        return swaps;
    }

    //a, b, c, d teorispørsmål

    //Oppgave2  Antall ulike (sortert)
    public static int antallUlikeSortert(int[] a) {

}
