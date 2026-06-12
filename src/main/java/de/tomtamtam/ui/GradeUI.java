package de.tomtamtam.ui;

import de.tomtamtam.core.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Main JavaFX UI for GradeApplication.
 *
 * Usage in main():
 *   GradeUI.launch(GradeUI.class);
 *
 * Or with a pre-populated GradeApplication (singleton pattern):
 *   GradeApplication.AddSubject("Mathe");
 *   GradeUI.launch(GradeUI.class);
 */
public class GradeUI extends Application {

    // ── Palette ──────────────────────────────────────────────────────────────
    private static final String BG_DARK   = "#1E1E2E";
    private static final String BG_PANEL  = "#2A2A3E";
    private static final String ACCENT    = "#7C9EFF";
    private static final String TEXT_MAIN = "#E8E8F0";
    private static final String TEXT_DIM  = "#8888AA";
    private static final String GOOD      = "#6FCF97";
    private static final String WARN      = "#F2994A";
    private static final String BAD       = "#EB5757";

    // ── State ─────────────────────────────────────────────────────────────────
    private ListView<String> subjectList;
    private VBox  detailPane;
    private Label avgLabel;

    // ── Entry point ───────────────────────────────────────────────────────────
    public static void launch(String[] args) {
        Application.launch(GradeUI.class, args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Notenübersicht");

        // Root layout: sidebar + detail
        HBox root = new HBox();
        root.setStyle("-fx-background-color: " + BG_DARK + ";");

        // ── Left sidebar ──────────────────────────────────────────────────────
        VBox sidebar = buildSidebar();
        sidebar.setPrefWidth(220);

        // ── Right detail pane ─────────────────────────────────────────────────
        detailPane = new VBox(16);
        detailPane.setPadding(new Insets(24));
        detailPane.setStyle("-fx-background-color: " + BG_DARK + ";");
        HBox.setHgrow(detailPane, Priority.ALWAYS);

        Label placeholder = styledLabel("← Fach auswählen", TEXT_DIM, 14);
        detailPane.getChildren().add(placeholder);

        root.getChildren().addAll(sidebar, detailPane);

        Scene scene = new Scene(root, 860, 560);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ── Sidebar ───────────────────────────────────────────────────────────────
    private VBox buildSidebar() {
        VBox box = new VBox(12);
        box.setPadding(new Insets(20, 12, 20, 12));
        box.setStyle("-fx-background-color: " + BG_PANEL + ";");

        Label title = styledLabel("Fächer", TEXT_MAIN, 18);
        title.setFont(Font.font("System", FontWeight.BOLD, 18));

        subjectList = new ListView<>();
        subjectList.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;" +
                        "-fx-text-fill: " + TEXT_MAIN + ";"
        );
        subjectList.setPrefHeight(400);
        refreshSubjectList();

        subjectList.getSelectionModel().selectedItemProperty().addListener(
                (obs, old, selected) -> { if (selected != null) showSubjectDetail(selected); }
        );

        Button addBtn    = sidebarButton("+ Fach hinzufügen");
        Button removeBtn = sidebarButton("– Fach entfernen");

        addBtn.setOnAction(e -> showAddSubjectDialog());
        removeBtn.setOnAction(e -> {
            String sel = subjectList.getSelectionModel().getSelectedItem();
            if (sel != null) {
                GradeApplication.RemoveSubject(sel);
                refreshSubjectList();
                detailPane.getChildren().setAll(styledLabel("← Fach auswählen", TEXT_DIM, 14));
            }
        });

        VBox.setVgrow(subjectList, Priority.ALWAYS);
        box.getChildren().addAll(title, new Separator(), subjectList, addBtn, removeBtn);
        return box;
    }

    // ── Detail view for a subject ─────────────────────────────────────────────
    private void showSubjectDetail(String subjectName) {
        Subject subject = GradeApplication.GetSubject(subjectName);
        detailPane.getChildren().clear();

        if (subject == null) return;

        // Header row
        Label nameLabel = styledLabel(subjectName, TEXT_MAIN, 22);
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 22));

        float overallGrade = subject.GetGrade();
        avgLabel = styledLabel(formatGrade(overallGrade), gradeColor(overallGrade), 22);
        avgLabel.setFont(Font.font("System", FontWeight.BOLD, 22));

        HBox header = new HBox(nameLabel);
        HBox.setHgrow(nameLabel, Priority.ALWAYS);
        header.getChildren().add(avgLabel);
        header.setAlignment(Pos.CENTER_LEFT);

        Label subAvg = styledLabel("Gesamtschnitt", TEXT_DIM, 12);
        subAvg.setAlignment(Pos.CENTER_RIGHT);
        header.getChildren().add(new VBox(avgLabel, subAvg));

        detailPane.getChildren().add(header);
        detailPane.getChildren().add(new Separator());

        // One card per CollectionType
        for (CollectionType type : CollectionType.values()) {
            GradeCollection col = subject.GetCollection(type);
            detailPane.getChildren().add(buildCollectionCard(subject, type, col));
        }
    }

    // ── Collection card ───────────────────────────────────────────────────────
    private VBox buildCollectionCard(Subject subject, CollectionType type, GradeCollection col) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(14));
        card.setStyle(
                "-fx-background-color: " + BG_PANEL + ";" +
                        "-fx-background-radius: 8;"
        );

        // Card header
        HBox cardHeader = new HBox();
        Label typeLabel = styledLabel(typeName(type), TEXT_MAIN, 14);
        typeLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        HBox.setHgrow(typeLabel, Priority.ALWAYS);
        cardHeader.getChildren().add(typeLabel);

        if (col != null) {
            float colGrade = col.GetGrade();
            Label colAvg = styledLabel("Ø " + formatGrade(colGrade), gradeColor(colGrade), 13);
            cardHeader.getChildren().add(colAvg);
        }
        card.getChildren().add(cardHeader);

        // Grades table or empty hint
        if (col != null && col.GetAllGrades().length > 0) {
            GridPane grid = new GridPane();
            grid.setHgap(16);
            grid.setVgap(4);
            int row = 0;
            for (Grade g : col.GetAllGrades()) {
                Label idLbl  = styledLabel(g.GetID(), TEXT_DIM, 12);
                Label valLbl = styledLabel(String.valueOf(g.GetValue()), gradeColor(g.GetValue()), 12);
                valLbl.setFont(Font.font("System", FontWeight.BOLD, 12));
                grid.add(idLbl, 0, row);
                grid.add(valLbl, 1, row++);
            }
            card.getChildren().add(grid);
        } else if (col == null) {
            card.getChildren().add(styledLabel("Keine Sammlung vorhanden", TEXT_DIM, 12));
        } else {
            card.getChildren().add(styledLabel("Noch keine Noten", TEXT_DIM, 12));
        }

        // Action buttons
        HBox actions = new HBox(8);
        if (col == null) {
            Button createBtn = accentButton("Sammlung anlegen");
            createBtn.setOnAction(e -> showAddCollectionDialog(subject, type));
            actions.getChildren().add(createBtn);
        } else {
            Button addGradeBtn = accentButton("+ Note");
            addGradeBtn.setOnAction(e -> showAddGradeDialog(subject, type, col));
            actions.getChildren().add(addGradeBtn);
        }
        card.getChildren().add(actions);
        return card;
    }

    // ── Dialogs ───────────────────────────────────────────────────────────────
    private void showAddSubjectDialog() {
        Stage dlg = dialog("Fach hinzufügen");
        VBox body = dialogBody();

        TextField nameField = styledTextField("Fachname, z. B. Mathe");
        Button ok = accentButton("Hinzufügen");
        ok.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                GradeApplication.AddSubject(name);
                refreshSubjectList();
                dlg.close();
            }
        });
        body.getChildren().addAll(styledLabel("Fachname", TEXT_DIM, 12), nameField, ok);
        dlg.setScene(new Scene(body, 300, 160));
        dlg.showAndWait();
    }

    private void showAddCollectionDialog(Subject subject, CollectionType type) {
        Stage dlg = dialog("Sammlung anlegen – " + typeName(type));
        VBox body = dialogBody();

        Label percLabel = styledLabel("Gewichtung (z. B. 0.5 für 50 %)", TEXT_DIM, 12);
        TextField percField = styledTextField("0.5");

        Button ok = accentButton("Anlegen");
        ok.setOnAction(e -> {
            try {
                float perc = Float.parseFloat(percField.getText().trim());
                subject.AddCollection(new GradeCollection(perc, type));
                showSubjectDetail(subject.GetName());
                dlg.close();
            } catch (NumberFormatException ex) {
                percField.setStyle(percField.getStyle() + "-fx-border-color: " + BAD + ";");
            }
        });
        body.getChildren().addAll(percLabel, percField, ok);
        dlg.setScene(new Scene(body, 320, 170));
        dlg.showAndWait();
    }

    private void showAddGradeDialog(Subject subject, CollectionType type, GradeCollection col) {
        Stage dlg = dialog("Note hinzufügen – " + typeName(type));
        VBox body = dialogBody();

        TextField idField  = styledTextField("Bezeichnung, z. B. Arbeit 1");
        TextField valField = styledTextField("Note, z. B. 2.5");

        Button ok = accentButton("Hinzufügen");
        ok.setOnAction(e -> {
            try {
                String id  = idField.getText().trim();
                float  val = Float.parseFloat(valField.getText().trim());
                if (!id.isEmpty()) {
                    col.AddGrade(new Grade(val, id));
                    showSubjectDetail(subject.GetName());
                    dlg.close();
                }
            } catch (NumberFormatException ex) {
                valField.setStyle(valField.getStyle() + "-fx-border-color: " + BAD + ";");
            }
        });
        body.getChildren().addAll(
                styledLabel("Bezeichnung", TEXT_DIM, 12), idField,
                styledLabel("Note (1.0 – 6.0)", TEXT_DIM, 12), valField,
                ok
        );
        dlg.setScene(new Scene(body, 320, 230));
        dlg.showAndWait();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private void refreshSubjectList() {
        Subject[] arr = GradeApplication.GetSubjects();
        var names = FXCollections.<String>observableArrayList();
        if (arr != null) for (Subject s : arr) names.add(s.GetName());
        subjectList.setItems(names);
    }

    private Stage dialog(String title) {
        Stage dlg = new Stage();
        dlg.initModality(Modality.APPLICATION_MODAL);
        dlg.setTitle(title);
        return dlg;
    }

    private VBox dialogBody() {
        VBox b = new VBox(10);
        b.setPadding(new Insets(20));
        b.setStyle("-fx-background-color: " + BG_DARK + ";");
        return b;
    }

    private Label styledLabel(String text, String hexColor, double size) {
        Label l = new Label(text);
        l.setStyle("-fx-text-fill: " + hexColor + "; -fx-font-size: " + size + ";");
        return l;
    }

    private TextField styledTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle(
                "-fx-background-color: " + BG_PANEL + ";" +
                        "-fx-text-fill: " + TEXT_MAIN + ";" +
                        "-fx-prompt-text-fill: " + TEXT_DIM + ";" +
                        "-fx-border-color: #44446A; -fx-border-radius: 4; -fx-background-radius: 4;" +
                        "-fx-padding: 6 10 6 10;"
        );
        return tf;
    }

    private Button accentButton(String text) {
        Button b = new Button(text);
        b.setStyle(
                "-fx-background-color: " + ACCENT + ";" +
                        "-fx-text-fill: #1E1E2E; -fx-font-weight: bold;" +
                        "-fx-background-radius: 5; -fx-padding: 6 14 6 14; -fx-cursor: hand;"
        );
        return b;
    }

    private Button sidebarButton(String text) {
        Button b = new Button(text);
        b.setMaxWidth(Double.MAX_VALUE);
        b.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: " + ACCENT + ";" +
                        "-fx-border-color: " + ACCENT + "; -fx-border-radius: 4;" +
                        "-fx-background-radius: 4; -fx-padding: 6 10 6 10; -fx-cursor: hand;"
        );
        return b;
    }

    private String gradeColor(float grade) {
        if (grade <= 2.0f) return GOOD;
        if (grade <= 3.5f) return WARN;
        return BAD;
    }

    private String formatGrade(float g) {
        return String.format("%.2f", g);
    }

    private String typeName(CollectionType t) {
        return switch (t) {
            case ClassTest  -> "Schulaufgaben";
            case OralGrade  -> "Mündliche Noten";
            case SmallTest  -> "Kurzarbeiten";
            case Other      -> "Sonstiges";
        };
    }
}