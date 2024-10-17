package org.example;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

public class Connect_4_main
{
    public static void main(String[] args) {
        Scanner be = new Scanner(System.in);
        System.out.println("Kérem adja meg a játékos nevét!");
        String nev = be.nextLine();

        //itt határozuk meg a méreteit (sor és oszlop)
        char[][] mezo = new char[6][7];

        for (int sor = 0; sor < mezo.length; sor++){
            for (int oszlop = 0; oszlop < mezo[0].length; oszlop++){
                mezo[sor][oszlop] = ' ';
            }
        }

        int kor = 1;
        char jatekos = 'S';
        boolean nyertes = false;

        //egy kör lejátszása
        while (nyertes == false && kor <= 42){
            boolean helyesLepes;
            int lepes;
            if (jatekos == 'S') {
                do {
                    megjelenit(mezo);

                    System.out.print("Válasszon egy mezőt: ");
                    lepes = be.nextInt();
                    while (lepes >= 7) {
                        System.out.println("Helytelen, lépés, lépjen újra");
                        lepes = be.nextInt();
                    }

                    //megnézi hogy helyes-e a lépés
                    helyesLepes = ellenorzes(lepes, mezo);

                } while (!helyesLepes);
            }else{

                //átvált a gépre
                System.out.println("A gép köre következik");
                lepes = AILepes(mezo);
                System.out.println("A gép a " + lepes + " oszlopba lépett.");
            }
            //"beledobja" a korongot
            for (int sor = mezo.length-1; sor >= 0; sor--){
                if(mezo[sor][lepes] == ' '){
                    mezo[sor][lepes] = jatekos;
                    break;
                }
            }

            //megnézi van-e nyertes
            nyertes = gyoztes(jatekos,mezo);

            //játékos váltás
            jatekos = (jatekos == 'S') ? 'P' : 'S';

            kor++;
        }
        megjelenit(mezo);

        if (nyertes){
            if (jatekos=='S'){
                System.out.println("A gép nyert");
            }else{
                System.out.println(nev + " nyert");
            }
        }else{ //igen, lehet döntetlen is
            System.out.println("Döntetlen");
        }
        try {
            mezoFileba(mezo);
        } catch (IOException e) {
            System.out.println("Hiba történt a fájl írásakor: " + e.getMessage());
        }

    }

    //ez a rész felel a mező megjelenítéséért
    public static void megjelenit(char[][] mezo){
        System.out.println(" 0 1 2 3 4 5 6");
        //mivel itt nem tudom beállítani neki hogy "ha az 1-es számot ütjuk be, a bal szélső (első) oszlopba dobja"
        //ezért csak úgy tudtam megoldani hogy 0-val kezdődik
        //ezért kelle korábban letesztelnem hogy nagyobb számot írtak-e be mint 6
        System.out.println("---------------");
        for (int sor = 0; sor < mezo.length; sor++){
            System.out.print("|");
            for (int oszlop = 0; oszlop < mezo[0].length; oszlop++){
                System.out.print(mezo[sor][oszlop]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("---------------");
        }
        System.out.println(" 0 1 2 3 4 5 6");
        System.out.println();
    }

    public static boolean ellenorzes(int oszlop, char[][] mezo){
        //leteszteli hogy megfelel-e az oszlop
        if (oszlop < 0 || oszlop > mezo[0].length){
            System.out.println("Helytelen, lépés, lépjen újra");
            return false;
        }


        //leteszteli hogy tele van-e az oszlop
        if (mezo[0][oszlop] != ' '){
            System.out.println("Helytelen, lépés, lépjen újra");
            return false;
        }
        //ha mind a kettő megbukott, akkor az érték igaz lesz, és tovább lép
        return true;
    }

    public static boolean gyoztes(char jatekos, char[][] mezo){
        //megnézi hogy van-e 4 korong vízszintesen
        for(int sor = 0; sor<mezo.length; sor++){
            for (int oszlop = 0;oszlop < mezo[0].length - 3;oszlop++){
                if (mezo[sor][oszlop] == jatekos   &&
                        mezo[sor][oszlop+1] == jatekos &&
                        mezo[sor][oszlop+2] == jatekos &&
                        mezo[sor][oszlop+3] == jatekos){
                    return true;
                }
            }
        }
        //ugyan ez függőre
        for(int sor = 0; sor < mezo.length - 3; sor++){
            for(int oszlop = 0; oszlop < mezo[0].length; oszlop++){
                if (mezo[sor][oszlop] == jatekos   &&
                        mezo[sor+1][oszlop] == jatekos &&
                        mezo[sor+2][oszlop] == jatekos &&
                        mezo[sor+3][oszlop] == jatekos){
                    return true;
                }
            }
        }
        //bal fentről jobb lentre
        for(int sor = 3; sor < mezo.length; sor++){
            for(int oszlop = 0; oszlop < mezo[0].length - 3; oszlop++){
                if (mezo[sor][oszlop] == jatekos   &&
                        mezo[sor-1][oszlop+1] == jatekos &&
                        mezo[sor-2][oszlop+2] == jatekos &&
                        mezo[sor-3][oszlop+3] == jatekos){
                    return true;
                }
            }
        }
        //bal lentről jobb fentre
        for(int sor = 0; sor < mezo.length - 3; sor++){
            for(int oszlop = 0; oszlop < mezo[0].length - 3; oszlop++){
                if (mezo[sor][oszlop] == jatekos   &&
                        mezo[sor+1][oszlop+1] == jatekos &&
                        mezo[sor+2][oszlop+2] == jatekos &&
                        mezo[sor+3][oszlop+3] == jatekos){
                    return true;
                }
            }
        }
        return false;
    }

    //az "AI" lépései
    //egyedüli hátul ütő, hogy nem tudtam megoldani azt az esetet mikor illegális (nem helyes lépést) hajt végre
    public static int AILepes(char[][] mezo){
        //a Random-mal később fog foglalkozni, lényegében arra van hogy valahova ledobja a korongot
        Random rand = new Random();
        //ezzel dobja le a korongot a gépnek
        //ezek mellet egy picit csal, mert a fő feladata hogy blokkolja a játékost, és persze keres egy helyes (nyerő) lépést
        for (int oszlop = 0; oszlop < 7; oszlop++) {
            if (ellenorzes(oszlop,mezo)){
                //szimulál egy lépést
                for (int sor = mezo.length-1; sor >= 0; sor--) {
                    if (mezo[sor][oszlop] == ' '){
                        mezo[sor][oszlop] = 'P'; //ideiglenesen lerakja a korongját
                        if (gyoztes('P', mezo)){
                            mezo[sor][oszlop] = ' '; //vissza vonja a lépést
                            return sor; //megtalálta a helyes lépést
                        }
                        mezo[sor][oszlop] = ' ';
                        break;
                    }
                }
            }
        }
        //ez a rész felel a játékos blokkolásáért
        for (int oszlop = 0; oszlop < 7; oszlop++) {
            if (ellenorzes(oszlop, mezo)){
                //a játékos lépésének szimulálása
                for (int sor = mezo.length-1; sor >= 0 ; sor--) {
                    if (mezo[sor][oszlop] == ' '){
                        mezo[sor][oszlop] = 'S'; //ideiglenesen lerakja (magának) a játékos korongját
                        if (gyoztes('S', mezo)){
                            mezo[sor][oszlop] = ' '; //visszavonja a lépést
                            return oszlop; //blokkoló lépés megtalálva
                        }
                        mezo[sor][oszlop] = ' ';
                        break;
                    }
                }
            }
        }

        //kiválaszt egy oszlpot
        int lepes;
        do {
            lepes = rand.nextInt(7);
        } while (!ellenorzes(lepes, mezo));

        return lepes;
    }

    //file-ba írás
    //kiírja a pálya végső állapotát
    public static void mezoFileba(char[][] mezo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("vegso_allas.txt"))) {
            writer.write(" 0 1 2 3 4 5 6\n");
            writer.write("---------------\n");
            for (int sor = 0; sor < mezo.length; sor++) {
                writer.write("|");
                for (int oszlop = 0; oszlop < mezo[0].length; oszlop++) {
                    writer.write(mezo[sor][oszlop]);
                    writer.write("|");
                }
                writer.write("\n---------------\n");
            }
            writer.write(" 0 1 2 3 4 5 6\n");
        }
    }
}