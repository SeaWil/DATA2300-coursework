package no.oslomet.cs.algdat;

import javax.swing.text.rtf.RTFEditorKit;
import java.util.*;

public class DobbeltLenketListe<T> implements Liste<T> {
    // Innebygd (Trenger ikke endres)

    /**
     * Node class
     *
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    private Node<T> hode;
    private Node<T> hale;
    private int antall;
    private int endringer;

    public void fraTilKontroll(int fra, int til) {
        if (fra < 0) throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ.");
        if (til > antall) throw new IndexOutOfBoundsException("til(" + til + ") er større enn antall(" + antall + ")");
        if (fra > til)
            throw new IllegalArgumentException("fra(" + fra + ") er større enn til(" + til + ") - Ulovlig intervall.");
    }

    // Oppgave 0
    public static int gruppeMedlemmer() {
        return 1; // Returner hvor mange som er i gruppa deres
    }

    // Oppgave 1
    public DobbeltLenketListe() {
//        throw new UnsupportedOperationException();
        hode = null;
        hale = null;
        antall = 0;
        endringer = 0;
    }

    public DobbeltLenketListe(T[] a) {
        if (a == null) {
            throw new NullPointerException("a er tom");
        }
        hode = null; //initierer listen
        hale = null;
        antall = 0;
        endringer = 0;
        for (T value : a) {
            if (value != null) { //Dersom a inneholder null-verdier, skal disse hoppes over, sitter igjen med tom liste
                //om a kun består av null verdier
                Node<T> newNode = new Node<>(value);
                if (hode == null) { //sjekker om listen er tom
                    hode = hale = newNode;
                } else {
                    hale.neste = newNode;
                    newNode.forrige = hale;
                    hale = newNode;
                }
                antall++;
                endringer++;
            }

        }
    }

    @Override
    public int antall() {
//        throw new UnsupportedOperationException();
        return antall;
    }

    @Override
    public boolean tom() {
//        throw new UnsupportedOperationException();
        return antall <= 0;
    }


    // Oppgave 2
    @Override
    public String toString() {
//        throw new UnsupportedOperationException();
        StringBuilder output = new StringBuilder(); //initierer strengen min
        Node<T> peker = hode;
        output.append("[");
        while (peker != null) {
            output.append(peker.verdi); //setter verdien i pekeren til å være med i listen
            peker = peker.neste;
            if (peker != null) {
                output.append(", ");
            } //formatering
        }
        output.append("]");
        return output.toString();
    }

    public String omvendtString() {
//        throw new UnsupportedOperationException();
        StringBuilder output = new StringBuilder();
        Node<T> peker = hale;
        output.append("[");
        while (peker != null) {
            output.append(peker.verdi); //setter verdien i pekeren til å være med i listen
            peker = peker.forrige;
            if (peker != null) {
                output.append(", ");
            } //formatering
        }
        output.append("]");
        return output.toString();

    }

    @Override
    public boolean leggInn(T verdi) {
        //        throw new UnsupportedOperationException();
        if (verdi == null) {
            throw new NullPointerException("null verdi");
        }  //
        Node<T> newNode = new Node<>(verdi);
        if (hode == null) {
            hode = newNode;
            hale = newNode;
        } else {
            hale.neste = newNode;
            newNode.forrige = hale;
            hale = newNode;
        }
        antall++;
        endringer++;
        return true;
    }

    // Oppgave 3
    private Node<T> finnNode(int indeks) {
//        throw new UnsupportedOperationException();
        Node<T> output;
        if (indeks < antall / 2) {
            output = hode;
            for (int i = 0; i < indeks; i++) {
                output = output.neste;
            }
        } else {
            output = hale;
            for (int i = antall - 1; i > indeks; i--) {
                output = output.forrige;
            }
        }
        return output;
    }

    @Override
    public T hent(int indeks) {
//        throw new UnsupportedOperationException();
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        indeksKontroll(indeks, false);
        if (nyverdi == null) throw new NullPointerException("Null verdi ikke tillatt");
        T gammelVerdi = finnNode(indeks).verdi;
        finnNode(indeks).verdi = nyverdi;
        endringer++;
        return gammelVerdi;
    }


    public Liste<T> subliste(int fra, int til) {
//        throw new UnsupportedOperationException();
        fraTilKontroll(fra, til);
        DobbeltLenketListe<T> sublist = new DobbeltLenketListe<>();
        if (fra == til) {
            return sublist;
        }
        Node<T> sublistNode = finnNode(fra);
        for (int i = fra; i < til; i++) {
            sublist.leggInn(sublistNode.verdi);
            sublistNode = sublistNode.neste;
        }
        return sublist;
    }

    // Oppgave 4
    @Override
    public int indeksTil(T verdi) {
//        throw new UnsupportedOperationException();
        int indeks = -1;
        for (int i = 0; i < antall; i++) {
            if (verdi.equals(finnNode(i).verdi)) return i;
        }
        return indeks;
    }

    @Override
    public boolean inneholder(T verdi) {
//        throw new UnsupportedOperationException();
        if (indeksTil(verdi) == -1) return false;
        else return true;
    }

    // Oppgave 5
    @Override
    public void leggInn(int indeks, T verdi) { //Klarer ikkke leggInnDiverse
        if (verdi == null) throw new NullPointerException("Kan ikke legge inn nullverdi");
        indeksKontroll(indeks, true);

        if (antall == 0) { //hvis listen er tom
            Node<T> nyNode = new Node<>(verdi, null, null);
            hode = hale = nyNode;
        } else if (indeks == 0) { //hvis jeg legger inn først
            Node<T> nyNode = new Node<>(verdi, null, hode);  //kanskje endre neste til nyNode.neste
            hode.forrige = nyNode;
            hode = nyNode;
        } else if (indeks == antall) { //hvis jeg legger innn sist lik antall, siden det ikke teller med nytt
            Node<T> nyNode = new Node<>(verdi, hale, null);
            hale.neste = nyNode;
            hale = nyNode;
        } else {
            Node<T> peker = finnNode(indeks); //lager peker
            Node<T> nyNode = new Node<T>(verdi);

            //flytter pekerne tl nyNode
            nyNode.forrige = peker.forrige;
            nyNode.neste = peker;

            //flytternabopekere
            peker.forrige = nyNode;
            nyNode.forrige.neste = nyNode;
        }
        endringer++;
        antall++;
    }

    // Oppgave 6
    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);
//        throw new UnsupportedOperationException();
        Node<T> peker = hode;
        for (int i = 0; i < indeks; i++) { //går ett steg når peker er en før indeks
            peker = peker.neste;
        }
        if (antall == 1) {
            hode = hale = null;
        } else if (indeks == antall - 1) { //hvis siste fjernes
            hale = peker.forrige;
            peker.forrige.neste = null;

        } else if (indeks == 0) { //hvis første fjernes
            hode = peker.neste;
            peker.neste.forrige = null;
        } else { //hvis en i midten fjernes
            peker.neste.forrige = peker.forrige; //´kobler pekerne til nabonodene på hverandre
            peker.forrige.neste = peker.neste;
        }
        peker.neste = null;
        peker.forrige = null;
        endringer++;
        antall--;
        return peker.verdi;
    }

    @Override
    public boolean fjern(T verdi) {
//        throw new UnsupportedOperationException();
        Node<T> peker = hode;
        while (peker != null) { //så lenge den ikke går utenfor listen
            if (verdi.equals(peker.verdi)) {
                if (antall == 1) {
                    hode = hale = null;
                } else if (peker == hode) {
                    peker.forrige.neste = null;
                    hode = peker.neste;
                } else if (peker == hale) {
                    peker.neste.forrige = null;
                    hale = peker.forrige;
                } else { //i midten
                    peker.forrige.neste = peker.neste; //kobler sammen naboer
                    peker.neste.forrige = peker.forrige;
                }
                endringer++;
                antall--; //korrigerer antall og endringer
                peker.neste = null; //setter fjernet element pekere til null
                peker.forrige = null;
                return true;
            }
            peker = peker.neste;
        }
        return false;
    }


    // =====Oppgave 7 //trenger ikke å gjøre==============
    @Override
    public void nullstill() {
        throw new UnsupportedOperationException();
    }

    // Oppgave 8

    @Override
    public Iterator<T> iterator() {
//        throw new UnsupportedOperationException();
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
//        throw new UnsupportedOperationException();
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {
        private Node<T> denne;
        private boolean kanFjerne;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {//ikke endre
            denne = hode;                   // Starter på første i lista
            kanFjerne = false;              // Settes true når next() kalles
            iteratorendringer = endringer;  // Teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
//            throw new UnsupportedOperationException();
            int teller = 0; //holder styring på når den er på indeks plassen
            Node<T> peker = hode;
            indeksKontroll(indeks, false);
            while (teller < indeks) {//finner noden på indeks plassen
                peker = peker.neste;
                teller++;
            }
            denne = peker; //setter indeksnoden til "denne"
            kanFjerne = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext() {
            return denne != null;
        }

        @Override
        public T next() {
//            throw new UnsupportedOperationException();
            T returVerdi;
            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException("Endringer stemmer ikke overens");
            }
            if (!hasNext()) { //om den går utenfor
                throw new NoSuchElementException("ingen flere elementer");
            }
            kanFjerne = true;
            returVerdi = denne.verdi;
            denne = denne.neste;
            return returVerdi;

        }

        // Oppgave 9: //trenger ikke gjøre
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // Oppgave 10 //trenger ikke å gjøre
    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException();
    }
}


