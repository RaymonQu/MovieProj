import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else if (option.equals("r"))
    {
      listHighestRated();
    }
    else if (option.equals("h"))
    {
      listHighestRevenue();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }

  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();

      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();

      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }

  private void searchCast()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();

    searchTerm = searchTerm.toLowerCase();

    ArrayList<Movie> results = new ArrayList<Movie>();

    for (Movie movie : movies)
    {
      if (movie.getCast().toLowerCase().contains(searchTerm))
      {
        results.add(movie);
      }
    }

    sortResults(results);

    ArrayList<String> actors = new ArrayList<String>();

    for (Movie result : results)
    {
      String[] cast = result.getCast().split("\\|");
      for (String actor : cast)
      {
        if (actor.toLowerCase().contains(searchTerm) && !actors.contains(actor))
        {
          actors.add(actor);
        }
      }
    }
    sortNames(actors);

    for (int i = 0; i < actors.size(); i++)
    {
      System.out.println("" + (i + 1) + ". " + actors.get(i));
    }

    System.out.println("Which actor are you think of?");
    System.out.print("Enter number: ");

    int actorChoice = scanner.nextInt();
    scanner.nextLine();

    String selectedActor = actors.get(actorChoice - 1);

    ArrayList<Movie> desiredMovies = new ArrayList<Movie>();

    for (Movie movie : movies)
    {
      if (movie.getCast().contains(selectedActor))
      {
        desiredMovies.add(movie);
      }
    }

    for (int i = 0; i < desiredMovies.size(); i++)
    {
      System.out.println("" + (i + 1) + ". " + desiredMovies.get(i).getTitle());
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int movieChoice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = desiredMovies.get(movieChoice - 1);
    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }


  private void searchKeywords()
  {
    System.out.print("Enter a Keyword search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String keywords = movies.get(i).getKeywords();
      keywords = keywords.toLowerCase();

      if (keywords.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listGenres()
  {
    int counter = 1;
    ArrayList<String> genres = new ArrayList<String>();
    for (Movie mov: movies){
      String[] genreForThisMov = mov.getGenres().split("\\|");
        for(String genre: genreForThisMov){
          if(!genres.contains(genre)){
            genres.add(genre);
          }
        }
    }
    sortNames(genres);
    for(String genre: genres){
      System.out.println(counter + ". "+ genre);
      counter++;
    }
    System.out.println("Which genre would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();
    String selectedGen = genres.get(choice - 1);
    ArrayList<Movie> movWithGen = new ArrayList<Movie>();
    for(Movie mov: movies){
      if(mov.getGenres().contains(selectedGen)){
        movWithGen.add(mov);
      }
    }
    sortResults(movWithGen);
    counter = 1;
    for(Movie mov: movWithGen){
      System.out.println(counter + ". " + mov.getTitle());
      counter++;
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    choice = scanner.nextInt();
    scanner.nextLine();
    displayMovieInfo(movWithGen.get(choice - 1));
  }
  
  private void listHighestRated()
  {
    ArrayList<Movie> goodMovs = new ArrayList<Movie>();
    sortMovsByRating(movies);
    for (int i = 0; i < 50; i++){
      goodMovs.add(movies.get(i));
      System.out.println(movies.get(i).getRevenue());
    }
    int counter = 1;
    for(Movie mov: goodMovs){
      System.out.println(counter + ". " + mov.getTitle());
      counter++;
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();
    displayMovieInfo(goodMovs.get(choice - 1));
  }
  
  private void listHighestRevenue()
  {
    ArrayList<Movie> richMovs = new ArrayList<Movie>();
    sortMovsByRev(movies);
    for (int i = 0; i < 50; i++){
      richMovs.add(movies.get(i));
      System.out.println(movies.get(i).getRevenue());
    }
    int counter = 1;
    for(Movie mov: richMovs){
      System.out.println(counter + ". " + mov.getTitle());
      counter++;
    }
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();
    displayMovieInfo(richMovs.get(choice - 1));
  }

  // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary
  private void sortNames(ArrayList<String> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      String temp = listToSort.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(listToSort.get(possibleIndex - 1)) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }

  private void sortMovsByRev(ArrayList<Movie> movs)
  {
    for (int j = 1; j < movs.size(); j++)
    {
      Movie temp = movs.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.getRevenue() > movs.get(possibleIndex - 1).getRevenue())
      {
        movs.set(possibleIndex, movs.get(possibleIndex - 1));
        possibleIndex--;
      }
      movs.set(possibleIndex, temp);
    }
  }

  private void sortMovsByRating(ArrayList<Movie> movs)
  {
    for (int j = 1; j < movs.size(); j++)
    {
      Movie temp = movs.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.getUserRating() > movs.get(possibleIndex - 1).getUserRating())
      {
        movs.set(possibleIndex, movs.get(possibleIndex - 1));
        possibleIndex--;
      }
      movs.set(possibleIndex, temp);
    }
  }

  private void importMovieList(String fileName)
  {

    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();

      movies = new ArrayList<Movie>();

      while ((line = bufferedReader.readLine()) != null)
      {
        // import all cells for a single row as an array of Strings,
        // then convert to ints as needed
        String[] movies_dataFromCSV = line.split(",");

        // pull out the data for this cereal
        String title = movies_dataFromCSV[0];
        String cast = movies_dataFromCSV[1];
        String director = movies_dataFromCSV[2];
        String tagline = movies_dataFromCSV[3];
        String keywords = movies_dataFromCSV[4];
        String overview = movies_dataFromCSV[5];
        int runtime = Integer.parseInt(movies_dataFromCSV[6]);
        String genres = movies_dataFromCSV[7];
        double userrating = Double.parseDouble(movies_dataFromCSV[8]);
        int year = Integer.parseInt(movies_dataFromCSV[9]);
        int revenue = Integer.parseInt(movies_dataFromCSV[10]);



        // create Cereal object to store values
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userrating, year, revenue);

        // adding Cereal object to the arraylist
        movies.add(nextMovie);
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());
    }
  }
  
  // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

}