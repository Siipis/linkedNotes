/**
 * Reader View
 * 
 * Builds the UI for reading articles.
 */
package fi.siipis.linkednotes.ui.view;

import fi.siipis.linkednotes.data.*;
import fi.siipis.linkednotes.ui.View;
import fi.siipis.linkednotes.ui.elements.KeywordText;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Ami
 */
public class ReaderView extends VBox {

    private View view;

    private TextFlow textFlow;

    /**
     * Constructor
     * 
     * @param view View container
     */
    public ReaderView(View view) {
        this.view = view;

        this.init();
    }

    /**
     * Initialise the class
     */
    private void init() {
        Button button = new Button("Edit");

        button.setOnMouseClicked((event) -> {
            view.getApplication().editArticle(Library.getInstance().getCurrentArticle());
        });

        textFlow = new TextFlow();

        this.getChildren().add(button);
        this.getChildren().add(textFlow);

        this.getStyleClass().add("view");
    }

    /**
     * Render the view
     * 
     * @param splitMap Article content map
     */
    public void view(SplitMap splitMap) {
        textFlow.getChildren().clear();

        for (Object o : splitMap.parts()) {
            if (o.getClass().equals(String.class)) {
                textFlow.getChildren().add(new Text((String) o));
            } else if (o.getClass().equals(Keyword.class)) {
                KeywordText text = new KeywordText();
                Keyword keyword = (Keyword) o;

                text.setKeyword(keyword);
                text.setText(keyword.getName());
                text.setFill(Color.BLUE);

                text.setOnMouseClicked((event) -> {
                    view.getApplication().readArticle(keyword.getArticle());
                });

                textFlow.getChildren().add(text);
            }
        }

        this.view.setContent(this);
    }
}
