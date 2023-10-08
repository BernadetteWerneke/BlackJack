//Auf gehts eine Runde BlackJack! Hier ist ein vollständiges Kartendeck fuer BlackJack
val kartendeck : List<String> = listOf(
    "Pik Ass", "Pik Koenig", "Pik Dame", "Pik Bube", "Pik 10", "Pik 9",
    "Pik 8" , "Pik 7", "Pik 6", "Pik 5", "Pik 4", "Pik 3", "Pik 2",
    "Kreuz Ass", "Kreuz Koenig", "Kreuz Dame", "Kreuz Bube", "Kreuz 10", "Kreuz 9",
    "Kreuz 8" , "Kreuz 7", "Kreuz 6", "Kreuz 5", "Kreuz 4", "Kreuz 3", "Kreuz 2",
    "Herz Ass", "Herz Koenig", "Herz Dame", "Herz Bube", "Herz 10", "Herz 9",
    "Herz 8" , "Herz 7", "Herz 6", "Herz 5", "Herz 4", "Herz 3", "Herz 2",
    "Karo Ass", "Karo Koenig", "Karo Dame", "Karo Bube", "Karo 10", "Karo 9",
    "Karo 8" , "Karo 7", "Karo 6", "Karo 5", "Karo 4", "Karo 3", "Karo 2",
)

fun main(){

    /* Die Variable meinDeck ist euer Deck mit den ihr arbeiten koennt.
    Mit der Funktion mischen() könnt ihr das Deck mische
    Mit der Funktion eineKarteZiehen() könnt ihr aus eurem Deck die oberste Karte bekommen,
    beachtet das die Funktion die Karte auch aus eurem Deck entfernt.
    Mit der Funktion kartenWert() bekommst du den Wert der Karte als Int zurück.
    Unter dem Kommentar siehst du wie du die Funktionen benutzen kannst.
     */


 /*   mischen(meinDeck)
    var karte = eineKarteZiehen(meinDeck)
    var beispielwert = kartenWert(karte)
        println(beispielwert) */

    var konto: Double = 100.0

    do {
        var spielerHand: MutableList<String> = mutableListOf<String>()
        var dealerHand: MutableList<String> = mutableListOf()
        var meinDeck = kartendeck.toMutableList()
        spielRundeStarten(spielerHand, dealerHand, meinDeck)

        //Einsatz eingeben
        println("Aktueller Kontostand: $konto $")
        println("Wie hoch ist dein Einsatz?")
        var einsatz =0.0
        //Abfangen falscher Eingabe + Guthaben checken, ob genug Geld vorhanden
            try {
                einsatz = readln().toDouble()
                if (einsatz > konto) {
                    println("Zu hoher Einsatz, dein aktuelles Konto ist $konto $.")
                } else {
                    println("Dein Einsatz beträgt $einsatz $.")
                }
            } catch (e: Exception) {
                println("keine Zahleneingabe")
            }

            //Spieler zieht zuerst
            hitAndStand(spielerHand, meinDeck)

            //Dealer an der Reihe zu ziehen
            if(wertEinerHand(spielerHand) <= 21) {
                println("Nun ist der Dealer am Ziehen.")
                dealerZieht(dealerHand, meinDeck)
            }

            if (wertEinerHand(spielerHand) > 21){
                konto -= einsatz
            } else if (wertEinerHand(dealerHand) > 21){
                konto += einsatz
            }

            //Gewinner checken
            when (werHatGewonnen(spielerHand, dealerHand)) {
                0 -> {
                    println("Du hast verloren. Einsatz ist weg.")
                    konto -= einsatz
                }
                1 -> println("Du bekommst den Einsatz zurück.")
                2 -> {
                    println("Dein Einsatz wird gutgeschreiben!")
                    konto += einsatz
                }
            }

            println("Du hast aktuell $konto auf dem Konto.")
            println("Weiterspielen? j/n")
            var weiter = readln().lowercase()
        }while (weiter == "j" && konto > 0)
        println("Spiel ist zu Ende.")
}

fun wertEinerHand(hand: MutableList<String>): Int{
    var ergHand = 0
    for(i in hand){
        ergHand += kartenWert(i)
    }
    return ergHand
}

fun punkteHoher21(hand:MutableList<String>): Boolean{
    //zu hohe Pktzahl ist return true
    return wertEinerHand(hand) > 21
}

fun hitAndStand(hand: MutableList<String>, deck: MutableList<String>){
    var gameNotOver = true
    do{
        println("Neue Karte ziehen mit 'hit', keine weitere Karte mit 'stand':")
        var input = readln()
        if(input == "hit"){
           hand.add(eineKarteZiehen(deck))
            println("Deine aktuelle Hand: $hand, gesamt: ${wertEinerHand(hand)} Punkte")
            if(punkteHoher21(hand)){
                println("Deine Punktzahl ist überschritten.")
                break
                }
        } else if(input == "stand"){
            gameNotOver = false
        }
    }
        while(gameNotOver)
}

fun dealerZieht(hand: MutableList<String>, deck: MutableList<String>){
    do{
        if (wertEinerHand(hand) < 17){
            hand.add(eineKarteZiehen(deck))
            if(punkteHoher21(hand)){
                println("Dealer hat die Punktzahl überschritten.")
                println("Du hast gewonnen.")
                break
            }
        }

    } while (wertEinerHand(hand) < 17)
    println("Hand des Dealer: $hand, gesamt ${wertEinerHand(hand)} Punkte")
}

fun werHatGewonnen(sHand: MutableList<String>, dHand: MutableList<String>): Int{
    //Dealer gewinnt
    if(wertEinerHand(sHand) < (wertEinerHand(dHand)) && wertEinerHand(dHand) <= 21){
        println("Dealer/PC hat gewonnen!")
        return 0
    }
    //unentschieden
    else if (wertEinerHand(sHand) == wertEinerHand(dHand) && wertEinerHand(dHand) <= 21){
        println("unentschieden!")
        return 1
    }
    //Spieler gewinnt
    else if (wertEinerHand(sHand) > wertEinerHand(dHand) && wertEinerHand(sHand) <= 21){
        println("Du hast gewonnen !!")
        return 2
    }
    return -1
}


fun spielRundeStarten(sHand: MutableList<String>, dHand: MutableList<String>, deck:MutableList<String> ){
    mischen(deck)

    //Spieler + Dealer je 2 Karten zuweisen
    for (i in 1..2){
        sHand.add(eineKarteZiehen(deck))
        dHand.add(eineKarteZiehen(deck))
    }

    //Zeige höhere Karte des Dealers an
    if (kartenWert(dHand[0]) < kartenWert(dHand[1])) {
            println("Erste Dealerkarte ist ${dHand[1]}")
        }else {
            println("Erste Dealerkarte ist ${dHand[0]}")
        }

    //Spielerkarten aufdecken
    println("Deine Hand ist ${sHand[0]}, ${sHand[1]}, gesamt ${wertEinerHand(sHand)}")
}

fun mischen(deck: MutableList<String>){
    deck.shuffle()
}

fun eineKarteZiehen(deck: MutableList<String>): String {
    var karte = deck.first()
    deck.remove(deck.first())
    return karte
}

fun kartenWert(karte: String) :Int {
    var kartenWert = karte.split(" ")[1]

    return when(kartenWert){
        "2" -> 2
        "3" -> 3
        "4" -> 4
        "5" -> 5
        "6" -> 6
        "7" -> 7
        "8" -> 8
        "9" -> 9
        in listOf("10","Koenig","Dame","Bube" ) -> 10
        "Ass" -> 11
        else -> 0
    }
}
