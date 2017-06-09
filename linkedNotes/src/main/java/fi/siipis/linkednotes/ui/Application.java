/**
 * Application
 * 
 * Controller class.
 * Handles events and updates the view as needed.
 * May also be called by event handlers in the view layer.
 */
package fi.siipis.linkednotes.ui;

import fi.siipis.linkednotes.core.*;
import fi.siipis.linkednotes.data.*;
import javafx.stage.Stage;

/**
 *
 * @author Amalia Surakka
 */
public class Application extends javafx.application.Application {

    private View view;
    
    private Navigator navigator;

    public Application() {
        navigator = Navigator.getInstance();
        
        navigator.setRootPath(Utils.testRootPath); // Use the test root path for now
    }

    /**
     * JavaFX launch method
     * 
     * @param stage Stage
     */
    @Override
    public void start(Stage stage) {
        Library library = Library.getInstance();

        library.sync();
        
        view = new View(this, stage);

        view.welcome();

        stage.show();
    }

    /**
     * Display the reading window
     * 
     * @param path Path to article to read
     */
    public void readArticle(String path) {
        this.readArticle(this.getArticle(path));
    }

    /**
     * Display the reading window
     * 
     * @param article Article to read
     */
    public void readArticle(Article article) {
        navigator.open(article.getFilepath() + "/..");
        
        view.reader(new SplitMap(article));
    }

    /**
     * Display the editing window
     * 
     * @param path Path to article to edit
     */
    public void editArticle(String path) {
        this.editArticle(this.getArticle(path));
    }

    /**
     * Display the editing window
     * 
     * @param article Article to edit
     */
    public void editArticle(Article article) {
        navigator.open(article.getFilepath() + "/..");

        view.editor(article);        
    }
    
    /**
     * Store the article as a file
     * 
     * @param article Article to store
     */
    public void saveArticle(Article article) {
        Parser parser = Parser.getInstance();
        FileHandler fileHandler = FileHandler.getInstance();
        
        String content = parser.toFile(article);
        
        if (fileHandler.writeFile(article.getFilepath(), content)) {
            article.touchSaved();
            
            Library.getInstance().sync();
            
            this.readArticle(article);        
        } else {
            // TODO: add error alert
            System.out.println("Could not store article " + article.getPlainName());
        }
    }
    
    /**
     * Fetch the article object
     * 
     * @param path Article file location
     * @return Article from library
     */
    private Article getArticle(String path) {
        Library library = Library.getInstance();

        return library.findArticle(path);
    }
}
