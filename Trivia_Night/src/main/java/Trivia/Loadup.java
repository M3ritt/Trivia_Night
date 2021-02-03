package Trivia;

import java.util.Scanner;

public class Loadup {
    private database db;
    private Player[] players;

    public Loadup(){
        this.db = new database();
        File_Reader fr = new File_Reader(db);
        fr.read_file();
        this.db = fr.getDb();
    }


    /**
     * Reads the user input to figure out whether to play/keep playing or exit.
     */
    public void start_game(){
        Scanner sc = new Scanner(System.in);
        String mode;
        while(true) {
            System.out.print("Enter game mode:");
            mode = sc.nextLine();
            if(mode.equalsIgnoreCase("--exit"))
                break;
            if(mode.equalsIgnoreCase("--help")){
                System.out.println("List of commands: \n" +
                        "--help : prints these commands. \n" +
                        "Head to head: runs the head to head game. \n" +
                        "--check db : prints out the number of categories and number of questions per category. \n" +
                        "--exit :  ends the program. ");
            } else if (mode.equalsIgnoreCase("--check db")) {
                db.get_all_categories();
                System.out.println(db);
            } else if (mode.equalsIgnoreCase("startup")){
                this.players = new Player[2];
                this.players[0] = new Player("Josh");
                this.players[1] = new Player("die");
                Head_Head game = new Head_Head(this.players,this.db, 6, 20);
                game.start();
            } else if (mode.equalsIgnoreCase("Head to head")){
                create_players();
                int points_needed = points_needed();
                int time = is_timer();
                Head_Head game = new Head_Head(this.players, this.db, points_needed, time);
                game.start();
            }
            else
                System.out.println("Did not understand. --help for options");
        }
        System.out.println("Thanks for playing!");
    }


    /**
     * Main method to create players. Takes user input to figure out how many players to create.
     */
    public void create_players(){
        Scanner sc = new Scanner(System.in);
        int num_of_players;
        boolean accepted = false;
        String check = "";
        while(!accepted){
            System.out.print("Enter number of players:");
            try{
                check = sc.nextLine();
                if(Integer.parseInt(check) > 0)
                    accepted = true;
            } catch (NumberFormatException e){
                System.out.println("[Error] Try a number.\n");
            }
        }
        num_of_players = Integer.parseInt(check);
        this.players = new Player[num_of_players];
        initialize_players(num_of_players);
    }


    /**
     * Creates the players to play the game.
     * @param num_of_players : The number of players to create.
     */
    public void initialize_players(int num_of_players){
        Scanner sc = new Scanner(System.in);
        int pos = 0;
        for(int i = 1; i < num_of_players+1; i++){
            System.out.print("Enter player " + i+ " name:");
            String name = sc.nextLine();
            this.players[pos] = new Player(name);
            pos++;
        }
    }


    /**
     * Takes user input to determine the number of points needed to win.
     * @return int : number of points needed to win. Must be greater than 0.
     */
    public int points_needed(){
        Scanner sc = new Scanner(System.in);
        boolean accepted = false;
        String check = "";
        while(!accepted){
            System.out.print("Enter number of points needed to win:");
            try{
                check = sc.nextLine();
                if(Integer.parseInt(check) > 0)
                    accepted = true;
            } catch (NumberFormatException e){
                System.out.println("[Error] Try a number.\n");
            }
        }
        return Integer.parseInt((check));
    }


    /**
     * Gets the desired time to answer questions in. <=0 defaults to 20 seconds.
     * @return the desired time.
     */
    public int is_timer(){
        String check = "";
        Scanner sc = new Scanner(System.in);
        boolean accepted = false;
        while(!accepted){
            System.out.print("Enter a time in seconds to answer questions in: (<= 0 will default to 20 seconds)");
            try{
                check = sc.nextLine();
                if(Integer.parseInt(check) <= 0)
                    check = "20";
                accepted = true;
            } catch (NumberFormatException e){
                System.out.println("[Error] Try a number.\n");
            }
        }
        return Integer.parseInt(check);
    }
}

