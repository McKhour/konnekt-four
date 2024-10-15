package org.example;
import java.util.Scanner;

public class Connect_4_main
{
    public static void main( String[] args )
    {
        Scanner be = new Scanner(System.in);
        String jatekosNev;

        System.out.println("Kérem adja meg a játékos (sárga figura) nevét!");
        jatekosNev = be.nextLine();

        char[][] mezo = new char[6][7];

        for (int sor = 0; sor < mezo.length; sor++) {
            for (int oszlop = 0; oszlop < mezo[0].length; oszlop++) {
                mezo[sor][oszlop] = ' ';
            }
        }

        int kor = 1;
        char jatekos = 'S';
        boolean nyertes = false;

        while (nyertes == false && kor <= 42){
            boolean helyesLepes;
            int megjatsz = 0;
            do {
                megJelenites(mezo);

                System.out.println("A " + jatekosNev + " nevű játékos következik! Válasszon egy mezőt: ");
                helyesLepes = ervenyese(megjatsz,mezo);
            }while (helyesLepes == false);

            for (int sor = mezo.length-1; sor >= 0; sor--) {
                if (mezo[sor][megjatsz] == ' '){
                    mezo[sor][megjatsz] = jatekos;
                    break;
                }
            }

            nyertes = nyerte(mezo,jatekos);

            if (jatekos == 'S'){
                System.out.println("plész hólder");
            }else{
                System.out.println("audio jungle");
            }

            kor++;
        }
        megJelenites(mezo);

        if (nyertes){
            if (jatekos == 'S'){
                System.out.println("A " + jatekosNev + " nevű játékos nyert!");
            }else{
                System.out.println("A gép nyert");
            }
        }else{
            System.out.println("Döntetlen");
        }

    }

    public static void megJelenites(char[][] mezo){
        System.out.println(" 1 2 3 4 5 6 7");
        System.out.println("---------------");
        for (int sor = 0; sor < mezo.length; sor++) {
            System.out.println("|");
            for (int oszlop = 0; oszlop < mezo.length; oszlop++) {
                System.out.println(mezo[sor][oszlop]);
                System.out.println("|");
            }
            System.out.println();
            System.out.println("---------------");
        }
        System.out.println(" 1 2 3 4 5 6 7");
        System.out.println();
    }

    public static boolean ervenyese(int oszlop, char[][] mezo){
        if (oszlop < 1 || oszlop > mezo[0].length){
            return false;
        }

        if (mezo[0][oszlop] != ' '){
            return false;
        }

        return true;
    }

    public static boolean nyerte(char[][] mezo, char jatekos){
        for (int sor = 0; sor < mezo.length; sor++) {
            for (int oszlop = 0; oszlop < mezo.length - 3; oszlop++) {
                if (mezo[sor][oszlop] == jatekos &&
                        mezo[sor][oszlop+1] == jatekos &&
                        mezo[sor][oszlop+2] == jatekos &&
                        mezo[sor][oszlop+3] == jatekos){
                    return true;
                }
            }
        }
        for (int sor = 0; sor < mezo.length - 3; sor++) {
            for (int oszlop = 0; oszlop < mezo.length; oszlop++) {
                if (mezo[sor][oszlop] == jatekos &&
                        mezo[sor+1][oszlop] == jatekos &&
                        mezo[sor+2][oszlop] == jatekos &&
                        mezo[sor+3][oszlop] == jatekos){
                    return true;
                }
            }
        }
        for (int sor = 0; sor < mezo.length; sor++) {
            for (int oszlop = 0; oszlop < mezo.length - 3; oszlop++) {
                if (mezo[sor][oszlop] == jatekos &&
                        mezo[sor-1][oszlop+1] == jatekos &&
                        mezo[sor-2][oszlop+2] == jatekos &&
                        mezo[sor-3][oszlop+3] == jatekos){
                    return true;
                }
            }
        }
        for (int sor = 0; sor < mezo.length - 3; sor++) {
            for (int oszlop = 0; oszlop < mezo.length - 3; oszlop++) {
                if (mezo[sor][oszlop] == jatekos &&
                        mezo[sor+1][oszlop+1] == jatekos &&
                        mezo[sor+2][oszlop+2] == jatekos &&
                        mezo[sor+3][oszlop+3] == jatekos){
                    return true;
                }
            }
        }
        return false;
    }
}
