
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ApplicationRunner {

    public static String[][] grid = new String[5][5];
    public static String[][] grid2 = new String[5][5];

    public static void main(String[] args) {
        boolean ShowTheWord = false;

        int points = 25;
        char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        String stringAplphabet = String.valueOf(alphabet);
        String[] arrayAlphabet = stringAplphabet.split("");

        String fileLocation = System.getProperty("user.dir");
        String dataPath = fileLocation + File.separator + "wordlist.txt";
        for(int i=0;i<5;i++){
            for(int j =0;j<5;j++){
                grid[i][j] = "?";
                grid2[i][j] = arrayAlphabet[getRandomNumber(0, arrayAlphabet.length)];
            }
        }

        ArrayList<String> wordList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int randomNumber;
        String randomWord;
        do{
            randomNumber = getRandomNumber(0,wordList.size());
            randomWord = wordList.get(randomNumber);
        }while (!checkWord(randomWord));

        int checkColumnOrRow = randomNumber % 2;
        String position;
        if(checkColumnOrRow == 1){
            position = "column";
        }else{
            position = "row";
        }
        int positionOnGrid = getRandomNumber(0,5);

        if(position.equals("row")){
            for(int j=0;j<5;j++){
                grid2[positionOnGrid][j]=randomWord.split("")[j];
            }
        }else{
            for(int j=0;j<5;j++){
                grid2[j][positionOnGrid]=randomWord.split("")[j];
            }
        }
        Scanner scanner = new Scanner(System.in);
        int choice;
        String check;
        if(ShowTheWord){
            showWord(grid,position,positionOnGrid);
        }
        while(true){
            System.out.print("Guess word (2) or Reveal letter (1), or Give up (0) > ");
            choice = scanner.nextInt();
            scanner.nextLine();
            if(choice == 0){
                break;
            }else if(choice == 1){
                points--;
                System.out.print("Enter a cell position as a two-digit index (e.g., \"24\" for row 2 column 4) > ");
                check = scanner.nextLine();
                drawGrid(grid,Integer.parseInt(check.split("")[0]),Integer.parseInt(check.split("")[1]));
            }else{
                System.out.print("Guess a 5-letter word (lower case) > ");
                check = scanner.nextLine();
                System.out.println(check);
                System.out.println(randomWord);
                if (check.equals(randomWord)){
                    System.out.println("Correct");
                    System.out.println("You made "+(25-points)+" reveals before your correct guess");
                    System.out.println("You scored "+points +" points for that game");
                    System.out.println("The hidden word was "+"\""+randomWord+"\"");
                    break;
                }else{
                    points--;
                    System.out.println("Incorrect");
                }
            }
        }
    }

    public static boolean checkWord(String word){
        for(int i =0;i<word.length();i++){
            for(int j=0;j<word.length();j++){
                if(i!=j){
                    if(word.charAt(i)==word.charAt(j)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
    public static void drawGrid(String[][] array,int row,int column){
        System.out.println("  | 0 | 1 | 2 | 3 | 4 |\n"+
                "-----------------------");
        for(int i=0;i<5;i++){
            System.out.print(i+" |");
            for(int j=0;j<5;j++){
                if(row== i && column == j){
                    System.out.print(" "+grid2[i][j]+" |");
                }else{
                    System.out.print(" "+array[i][j]+" |");
                }
            }
            System.out.println();
            System.out.println("-----------------------");
        }
        System.out.println();
    }
    public static void showWord(String[][] array,String position,int posOnGrid){
        System.out.println("  | 0 | 1 | 2 | 3 | 4 |\n"+
                "-----------------------");
        for(int i=0;i<5;i++){
            System.out.print(i+" |");
            for(int j=0;j<5;j++){
                if(position.equals("row") && i==posOnGrid){
                    System.out.print(" "+grid2[i][j]+" |");
                }else if(position.equals("column") && j==posOnGrid){
                    System.out.print(" "+grid2[i][j]+" |");
                }else{
                    System.out.print(" "+array[i][j]+" |");
                }
            }
            System.out.println();
            System.out.println("-----------------------");
        }
        System.out.println();
    }
}
