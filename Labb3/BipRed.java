import java.util.Collections;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Exempel på in- och utdatahantering för maxflödeslabben i kursen
 * ADK.
 *
 * Använder Kattio.java för in- och utläsning.
 * Se http://kattis.csc.kth.se/doc/javaio
 *
 * @author: Per Austrin
 */

public class BipRed {
    Kattio io;
    ArrayList<LinkedList<Integer>> flowGraph = new ArrayList<LinkedList<Integer>>();
    LinkedList<Integer> maxiMatch = new LinkedList<Integer>();

    int x;
    int y;
    int e;

    void readBipartiteGraph() {
        // Läs antal hörn och kanter
        x = io.getInt();
        y = io.getInt();
        e = io.getInt();
        for(int i = 0; i < x + y + 2 ; i++){
            flowGraph.add(new LinkedList<Integer>());
        }
        // Läs in kanterna

        for (int i = 0; i < e; ++i) { //implementera en DaG
            int a = io.getInt();
            int b = io.getInt();

            flowGraph.get(a+1).add(b+1);
        }

    }


    void writeFlowGraph() {
        int v = x + y + 2 , eFlow = e + x + y, s = 1, t = x + y + 2;
        // Skriv ut antal hörn och kanter samt källa och sänka
        io.println(v);
        io.println(s + " " + t);
        io.println(eFlow);

        for(int i = 2; i < x+2; i++){
            flowGraph.get(1).add(i);
        }

        for (int i = x + 2; i < v ; i++){
            flowGraph.get(i).add(x + y + 2);
        }

        for (int i = 1; i < v; ++i) {

            int a, b, c = 0, adjacent = flowGraph.get(i).size() ;

            for (int iTmp = 0; iTmp < adjacent; iTmp++) {

                a = i;
                b = flowGraph.get(i).getFirst();
                flowGraph.get(i).removeFirst();

                // Kant från a till b med kapacitet c
                io.println(a + " " + b + " " + c);
            }
        }

        // Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
        io.flush();

        // Debugutskrift
        //System.err.println("Skickade iväg flödesgrafen");
    }


    void readMaxFlowSolution() {
        // Läs in antal hörn, kanter, källa, sänka, och totalt flöde
        // (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
        // skickade iväg)
        int v = io.getInt();
        int s = io.getInt();
        int t = io.getInt();
        int totflow = io.getInt();
        int e = io.getInt();

        for (int i = 0; i < e; ++i) {
            // Flöde f från a till b
            int a = io.getInt();
            int b = io.getInt();
            int f = io.getInt();
            if((a != s) && (b != t)){ //Remove edges connecting from source and to sink.
                maxiMatch.add(a);
                maxiMatch.add(b);
            }

        }
    }


    void writeBipMatchSolution() {
        int maxMatch = (maxiMatch.size()/2);

        // Skriv ut antal hörn och storleken på matchningen
        io.println(x + " " + y);
        io.println(maxMatch);

        for (int i = 0; i < maxMatch; ++i) {

            int a = maxiMatch.getFirst();
            maxiMatch.removeFirst();

            int b = maxiMatch.getFirst();
            maxiMatch.removeFirst();

            // Kant mellan a och b ingår i vår matchningslösning

            io.println((a-1) + " " + (b-1));

        }

    }

    BipRed() {
        io = new Kattio(System.in, System.out);

        readBipartiteGraph();

        writeFlowGraph();

        readMaxFlowSolution();

        writeBipMatchSolution();


        // debugutskrift
        //System.err.println("Bipred avslutar\n");

        // Kom ihåg att stänga ner Kattio-klassen
        io.close();
    }

    public static void main(String args[]) {

        new BipRed();

    }
}
