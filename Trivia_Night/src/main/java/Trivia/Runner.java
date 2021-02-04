package Trivia;

public class Runner {

    public static void main(String[] args) {
        System.out.println(
                " _____          _           _         \n" +
                        "|_   _|        (_)         (_)        \n" +
                        "  | |    _ __   _  __   __  _    __ _ \n" +
                        "  | |   | '__| | | \\ \\ / / | |  / _` |\n" +
                        "  | |   | |    | |  \\ V /  | | | (_| |\n" +
                        "  \\_/   |_|    |_|   \\_/   |_|  \\__,_|\n" +
                        "                                      ");
        Loadup l = new Loadup();
        l.start_game();
        System.out.println("Closed");
    }
}
