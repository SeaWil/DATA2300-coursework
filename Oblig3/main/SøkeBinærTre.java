package no.oslomet.cs.algdat;

import java.util.*;

public class SøkeBinærTre<T> implements Beholder<T> {

    // En del kode er ferdig implementert, hopp til linje 91 for Oppgave 1

    private static final class Node<T> { // En indre nodeklasse
        private T verdi; // Nodens verdi
        private Node<T> venstre, høyre, forelder; // barn og forelder

        // Konstruktører
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> f) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            forelder = f;
        }

        private Node(T verdi, Node<T> f) {
            this(verdi, null, null, f);
        }

        @Override
        public String toString() {
            return verdi.toString();
        }
    } // class Node

    private final class SBTIterator implements Iterator<T> {
        Node<T> neste;

        public SBTIterator() {
            neste = førstePostorden(rot);
        }

        public boolean hasNext() {
            return (neste != null);
        }

        public T next() {
            Node<T> denne = neste;
            neste = nestePostorden(denne);
            return denne.verdi;
        }
    }

    public Iterator<T> iterator() {
        return new SBTIterator();
    }

    private Node<T> rot;
    private int antall;
    private int endringer;

    private final Comparator<? super T> comp;

    public SøkeBinærTre(Comparator<? super T> c) {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;
        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }
        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot);
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    // Oppgave 1
    public boolean leggInn(T verdi) {
//        throw new UnsupportedOperationException();
        if (verdi == null) throw new NullPointerException("Nullverdi er ikke lov");
        if (rot == null) { //hvis treet er tomt, oppretter rot
            rot = new Node<>(verdi, null);
            antall++;
            endringer++;
            return true;
        }
        Node<T> peker = rot;
        while (true) {
            if (comp.compare(verdi, peker.verdi) < 0) {//hvis input er mindre enn rot
                if (peker.venstre != null) { //sjekker at det finnes et venstrebarn
                    peker = peker.venstre; //går til venstre
                } else {
                    peker.venstre = new Node(verdi, peker); //ellers lager ny node
                    break;
                }
            } else { //om input er større enn rot
                if (peker.høyre != null) { //sjekker høyre
                    peker = peker.høyre; //går til høyre
                } else {
                    peker.høyre = new Node(verdi, peker); //ellers lager ny node
                    break;
                }
            }
        }
        antall++;
        endringer++;
        return true;
    }


    // Oppgave 2
    public int antall(T verdi) {
//        throw new UnsupportedOperationException();

        if (rot == null || verdi == null) return 0; //sjekker om input eller rot er tom

        //initierer teller og peker
        int duplikanter = 0;
        Node<T> peker = rot;
        //for sortert tre
        while (peker != null) { //sjekker om tallet er til venstre/høyre for noden
            if (comp.compare(verdi, peker.verdi) < 0) {//input er mindre enn peker
                peker = peker.venstre; //går til venstre
            } else if (comp.compare(verdi, peker.verdi) > 0) { //input er større enn peker
                peker = peker.høyre; //går til høyre
            } else { //er like
                duplikanter++;
                peker = peker.høyre; //går til høyre for å sjekke om det er flere på rad
            }
        }
        return duplikanter;
    }

    // Oppgave 3
    private Node<T> førstePostorden(Node<T> p) {
//        throw new UnsupportedOperationException();
        Node<T> peker = p; //initierer peker
        while (peker.venstre != null || peker.høyre != null) {
            if (peker.venstre != null) { //så lenge det finnes venstrebarn
                peker = peker.venstre; //flytter peker
            } else { //om det finnes høyrebarn og ikke et venstrebarn
                peker = peker.høyre;
            }
        }
        return peker;
    }

    private Node<T> nestePostorden(Node<T> p) {
//        throw new UnsupportedOperationException();
        if (p.forelder == null) //sjekker om jeg er i roten
            return null; //returnerer rot
        else { //forelder er finnes (ikke i rot)
            if (p.forelder.høyre == null || p.forelder.høyre == p) { //om forelderen ikke har høyrebarn eller første er høyre
                return p.forelder;
            } else { //det finnes et subtre

                p = p.forelder.høyre;
                while (p.høyre != null || p.venstre != null) {
                    if (p.venstre != null) {
                        p = p.venstre;
                    } else p = p.høyre;
                }
                return p;
            }
        }
    }

    // Oppgave 4
    public void postOrden(Oppgave<? super T> oppgave) {
//        throw new UnsupportedOperationException();
        if (rot == null) return;  // Hvis treet er tomt, gjør ingenting

        Node<T> peker = førstePostorden(rot);
        while (peker != null) {
            oppgave.utførOppgave(peker.verdi);
            peker = nestePostorden(peker);
        }
    }

    public void postOrdenRekursiv(Oppgave<? super T> oppgave) {
        postOrdenRekursiv(rot, oppgave); // Ferdig implementert
    }

    private void postOrdenRekursiv(Node<T> p, Oppgave<? super T> oppgave) {
//        throw new UnsupportedOperationException();
        if (p == null) return;
        postOrdenRekursiv(p.venstre, oppgave);
        postOrdenRekursiv(p.høyre, oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    // Oppgave 5
    public boolean fjern(T verdi) {
//        throw new UnsupportedOperationException();
        if (verdi == null) return false;  //sjekker at input er ulik null

        Node<T> p = rot, q = null;   // q skal være forelder til p


        //traverserer treet i inorden for å finne input verdi - lagrer i p
        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);      // sammenligner
            if (cmp < 0) {
                q = p;
                p = p.venstre;
            }      // går til venstre
            else if (cmp > 0) {
                q = p;
                p = p.høyre;
            }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // fant ikke verdien

        if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            //if-else =(betingelse) ? (verdi hvis sann) : (verdi hvis falsk);
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  //finner barnet til p

            //fjerner pekere til p
            if (p == rot) {
                rot = b; //om i rot: flytter roten til b
                if (b != null) b.forelder = null;  //flytter forelderpeker
            } else if (p == q.venstre) {
                q.venstre = b; //om p er venstrebarn
                if (b != null) b.forelder = q; //flytter forelderpeker
            } else {
                q.høyre = b; //om p er høyrebarn
                if (b != null) b.forelder = q; //flytter forelderpeker
            }
        } else  // Tilfelle 3) p er en av verdiene som skal fjernes?
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden til p for å bytte dem
            while (r.venstre != null) //søker i høyre subtre
            {
                s = r;    // s er forelder til r
                r = r.venstre; //går lengst til venstre i subtre
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            //fjerner p
            if (s != p) s.venstre = r.høyre;
            else {
                s.høyre = r.høyre; //flytter forelderens høyrepeker til r sitt barn
                r.høyre.forelder = s; //flytter foreldrepekeren til høyrebarn
            }
        }

        antall--;   // det er nå én node mindre i treet
        return true;
    }

    public int fjernAlle(T verdi) {
//        throw new UnsupportedOperationException();
        int antallfjernet = 0;
        if (verdi == null) return antallfjernet; //sjekker input
        while (fjern(verdi)) { //så lenge det metoden får fjernet
            antallfjernet++;
        }
        return antallfjernet;
    }

    public void nullstill() {
//        throw new UnsupportedOperationException();
        if (rot == null) return; //sjekker rot
        nullstillRekursiv(rot);
        rot = null;

    }

    public void nullstillRekursiv(Node<T> p) {
        if (p == null) return; //fortsetter til når null

        //kaller for begge barna
        nullstillRekursiv(p.venstre);
        nullstillRekursiv(p.høyre);

        //setter alle pekere og verdier til null
        p.verdi = null;
        p.venstre = null;
        p.høyre = null;
        p.forelder = null;


        antall--;
        endringer++; //oppdaterer
    }


}


//øving spørre om test 1:
/*Note: /Users/seanwilliams/Desktop/DATS2300/obligatorisk-oppgave-3-SeaWil/src/main/java/no/oslomet/cs/algdat/SøkeBinærTre.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.*/