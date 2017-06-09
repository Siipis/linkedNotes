package fi.siipis.linkednotes.ui.view;

import fi.siipis.linkednotes.core.*;
import fi.siipis.linkednotes.data.*;
import fi.siipis.linkednotes.ui.View;
import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 *
 * @author Ami
 */
public class EditorView extends VBox {

    private View view;

    private TextArea content;

    private TextField keywords;

    private Navigator navigator;

    private Library library;

    public EditorView(View view) {
        this.view = view;

        this.navigator = Navigator.getInstance();

        this.library = Library.getInstance();

        this.init();
    }

    private void init() {
        Button button = new Button("Save");

        button.setOnMouseClicked((event) -> {
            view.getApplication().saveArticle(library.getCurrentArticle());
        });

        this.content = new TextArea();
        content.setWrapText(true);

        content.textProperty().addListener((obs, oldContent, newContent) -> {
            Article article = library.getCurrentArticle();

            article.setContent(newContent);
        });

        this.keywords = new TextField();
        keywords.textProperty().addListener((obs, oldKeywords, newKeywords) -> {
            Article article = library.getCurrentArticle();

            ArrayList<Keyword> list = new ArrayList<>();

            for (String k : newKeywords.split(",")) {
                list.add(Parser.getInstance().toKeyword(k, article));
            }

            article.setKeywords(list);
        });

        this.getChildren().add(button);
        this.getChildren().add(content);
        this.getChildren().add(keywords);
    }

    public void view(Article article) {
        library.setCurrentArticle(article);
        
        this.content.setText(article.getContent());
        this.keywords.setText(article.getKeywordsAsString());

        this.view.setContent(this);
    }
}