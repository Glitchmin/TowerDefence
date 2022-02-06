package Code.gui;

import Code.Vector2d;
import Code.map_handling.*;
import Code.map_handling.spells.MeteorSpell;
import Code.map_handling.spells.SnowballRain;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;

public class MapVisualizer implements ITurretChangeObserver, IEnemyChangeObserver, IMeteorObserver, Runnable {
    private final GridPane mapGridPane;
    private final Pane paneOfEverything;
    private final Map map;
    private final MapTileBox[][] landscapeBoxes;
    private final MapTileBox[][] turretBoxes;
    private final Shop shop;
    private final Label landscapeNameOnCursorLabel;
    private final TurretBuilder turretBuilder;
    private final SpellCaster spellCaster;
    private VBox tmpTurretVBox;
    private Circle tmpSpellCircle;
    private final TreeMap<Integer, ImageView> enemyImages;
    private final Double tileSize;
    private final List<Enemy> enemiesToRender;
    private final List<Line> linesList;
    private final List<Long> linesExpTimeList;
    private long time;
    private Vector2d mousePosition;
    private Vector2d lastMousePosition;
    private final List<Meteor> meteorsToRender;
    private final TreeMap<Integer, ImageView> meteorImages;
    private final TreeMap<Integer, Circle> meteorShades;


    public MapVisualizer(Map map, Pane paneOfEverything, Shop shop, TurretBuilder turretBuilder, SpellCaster spellCaster, Integer guiElementBoxWidth, Integer guiElementBoxHeight, Double tileSize) {
        enemyImages = new TreeMap<>();
        meteorImages = new TreeMap<>();
        meteorShades = new TreeMap<>();
        this.tileSize = tileSize;
        this.paneOfEverything = paneOfEverything;
        landscapeNameOnCursorLabel = new Label("");
        MapTileBox.setWidth(guiElementBoxWidth);
        MapTileBox.setHeight(guiElementBoxHeight);
        this.map = map;
        this.shop = shop;
        this.turretBuilder = turretBuilder;
        this.spellCaster = spellCaster;
        this.enemiesToRender = new ArrayList<>();
        this.meteorsToRender = new ArrayList<>();
        this.linesList = new ArrayList<>();
        this.linesExpTimeList = new ArrayList<>();
        mapGridPane = new GridPane();
        this.mousePosition = new Vector2d(0.0, 0.0);
        this.lastMousePosition = new Vector2d(0.0, 0.0);
        paneOfEverything.setOnMouseMoved(event -> mousePosition =
                new Vector2d(event.getX() / tileSize, event.getY() / tileSize));

        landscapeBoxes = new MapTileBox[map.getWidth()][map.getHeight()];
        turretBoxes = new MapTileBox[map.getWidth()][map.getHeight()];

        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                MapTileBox mapTileBox = new MapTileBox(map.getLandscape(i, j));
                mapGridPane.add(mapTileBox.getVBox(), i, j);
                landscapeBoxes[i][j] = mapTileBox;
                mapTileBox.getVBox().setOnMouseEntered(Action ->
                {
                    handleCursorOnMapTile(mapTileBox);
                });
            }
        }
    }

    public void updateTime(long time) {
        this.time = time;
    }

    public void addLine(Vector2d start, Vector2d end, Color color) {
        synchronized (this) {
            Line line = new Line();
            line.setStartX(start.x * tileSize);
            line.setStartY(start.y * tileSize);
            line.setEndX(end.x * tileSize);
            line.setEndY(end.y * tileSize);
            linesList.add(line);
            line.setStroke(color);
            linesExpTimeList.add(time + 100);
        }
    }

    private void drawLines() {
        synchronized (this) {
            for (int i = 0; i < linesList.size(); i++) {
                Line line = linesList.get(i);
                if (!paneOfEverything.getChildren().contains(line)) {
                    paneOfEverything.getChildren().add(line);
                }
                if (time > linesExpTimeList.get(i)) {
                    paneOfEverything.getChildren().remove(linesList.get(i));
                    linesList.remove(i);
                    linesExpTimeList.remove(i);
                    i--;
                }
            }
        }
    }

    @Override
    public void MeteorChanged(Meteor meteor) {
        meteorsToRender.add(meteor);
    }

    private void handleCursorOnMapTile(MapTileBox mapTileBox) {
        for (int i = 0; i < map.getWidth(); i++) {
            for (int j = 0; j < map.getHeight(); j++) {
                if (mapTileBox == landscapeBoxes[i][j]) {
                    landscapeNameOnCursorLabel.setText
                            (map.getLandscape(i, j).landscapeType.toString() + " (" + i + "," + j + ")");
                }
            }
        }
    }

    private void showSpell() {
        if (shop.getSelectedSpell() != null && mousePosition.x < map.getWidth() && mousePosition.y < map.getHeight()) {
            if (tmpSpellCircle == null) {
                tmpSpellCircle = new Circle();
                tmpSpellCircle.setRadius(shop.getSelectedSpell().getNewSpell(mousePosition).getRadius() * tileSize);
                tmpSpellCircle.setOpacity(0.3);
                paneOfEverything.getChildren().add(tmpSpellCircle);
                if (shop.getSelectedSpell() == SpellType.METEOR) {
                    MeteorSpell meteorSpell = (MeteorSpell) shop.getSelectedSpell().getNewSpell(mousePosition);
                    tmpSpellCircle.setOnMouseClicked(Action -> spellCaster.castSpell(meteorSpell, mousePosition));
                }
                if (shop.getSelectedSpell() == SpellType.SNOWBALL_RAIN) {
                    SnowballRain snowballRain = (SnowballRain) shop.getSelectedSpell().getNewSpell(mousePosition);
                    tmpSpellCircle.setOnMouseClicked(Action -> spellCaster.castSpell(snowballRain, mousePosition));
                }
            }
            tmpSpellCircle.toFront();
            tmpSpellCircle.setCenterX(mousePosition.x * tileSize);
            tmpSpellCircle.setCenterY(mousePosition.y * tileSize);
        } else {
            paneOfEverything.getChildren().remove(tmpSpellCircle);
            tmpSpellCircle = null;
        }
    }


    private void showTmpTurret() {
        if (shop.getSelectedTurret() != null && mousePosition.x < map.getWidth() && mousePosition.y < map.getHeight()) {
            if (tmpTurretVBox == null) {
                tmpTurretVBox = new MapTileBox(
                        shop.getSelectedTurret().getNewTurret(new Vector2d(mousePosition.IntX() + 0.5,
                                mousePosition.IntY() + 0.5), false)).getVBox();
                tmpTurretVBox.setOpacity(0.5);
                mapGridPane.add(tmpTurretVBox, mousePosition.IntX(), mousePosition.IntY());
                tmpTurretVBox.setOnMouseClicked(Action -> addTurret());
            }

        }
        if (shop.getSelectedTurret() == null || !Objects.equals(mousePosition.IntX(), lastMousePosition.IntX())
                || !Objects.equals(mousePosition.IntY(), lastMousePosition.IntY())) {
            lastMousePosition = new Vector2d(mousePosition.x, mousePosition.y);
            mapGridPane.getChildren().remove(tmpTurretVBox);
            tmpTurretVBox = null;
        }

    }

    private void addTurret() {
        int i = mousePosition.IntX();
        int j = mousePosition.IntY();
        if (map.getLandscape(i, j).landscapeType == LandscapeType.GRASS
                || map.getLandscape(i, j).landscapeType == LandscapeType.HILL) {
            turretBuilder.build(i, j,
                    shop.getSelectedTurret().getNewTurret(new Vector2d(i + 0.5, j + 0.5),
                            map.getLandscape(i, j).landscapeType == LandscapeType.HILL));
        }
    }

    public Label getLandscapeNameOnCursorLabel() {
        return landscapeNameOnCursorLabel;
    }

    public GridPane getMapGridPane() {
        return mapGridPane;
    }

    @Override
    public void enemyChanged(Enemy enemy) {
        synchronized (map) {
            enemiesToRender.add(enemy);
        }
    }

    public void updateEnemies() {
        synchronized (map) {
            for (Enemy enemy : enemiesToRender) {
                ImageView enemyImageView = enemyImages.get(enemy.getID());
                if (enemyImageView == null) {
                    enemyImageView = new ImageView(ImageLoader.loadImage(enemy.getEnemyType().getResourcePath()));
                    enemyImages.put(enemy.getID(), enemyImageView);
                    paneOfEverything.getChildren().add(enemyImages.get(enemy.getID()));
                    enemyImageView.setFitHeight(26);
                    enemyImageView.setFitWidth(26);
                }
                enemyImageView.setX(enemy.getPosition().x * tileSize - 13);
                enemyImageView.setY(enemy.getPosition().y * tileSize - 13);
                if (enemy.reachedEnd() || enemy.isDead()) {
                    paneOfEverything.getChildren().remove(enemyImageView);
                    enemyImages.remove(enemy.getID());
                }
            }
            enemiesToRender.clear();
        }
    }

    public void updateMeteors() {
        synchronized (map) {
            for (Meteor meteor : meteorsToRender) {
                ImageView meteorImageView = meteorImages.get(meteor.getID());
                Circle meteorShade = meteorShades.get(meteor.getID());
                if (meteorImageView == null) {
                    meteorImageView = new ImageView(ImageLoader.loadImage(meteor.getResourcePath()));
                    meteorShade = new Circle();
                    meteorImages.put(meteor.getID(), meteorImageView);
                    meteorShades.put(meteor.getID(), meteorShade);
                    meteorImageView.setFitHeight(tileSize * meteor.getRadius() * 2);
                    meteorImageView.setFitWidth(tileSize * meteor.getRadius() * 2);
                    meteorShade.setRadius(meteor.getRadius() * tileSize);
                    meteorImageView.setX((meteor.getPosition().x - meteor.getRadius()) * tileSize);
                    meteorShade.setCenterX((meteor.getPosition().x) * tileSize);
                    meteorShade.setOpacity(0.3);
                    paneOfEverything.getChildren().add(meteorShades.get(meteor.getID()));
                    paneOfEverything.getChildren().add(meteorImages.get(meteor.getID()));
                }
                meteorImageView.setY(((meteor.getPosition().y - meteor.getH() - meteor.getRadius()) * tileSize));
                meteorShade.setCenterY(meteor.getPosition().y * tileSize);
                if (meteor.getH() <= 0) {
                    paneOfEverything.getChildren().remove(meteorImageView);
                    paneOfEverything.getChildren().remove(meteorShade);
                    meteorImages.remove(meteor.getID());
                    meteorShades.remove(meteor.getID());
                }
            }
            meteorsToRender.clear();
        }
    }

    @Override
    public void run() {
        updateEnemies();
        updateMeteors();
        drawLines();
        showSpell();
        showTmpTurret();
    }

    @Override
    public void turretChanged(int x, int y) {
        if (turretBoxes[x][y] != null) {
            mapGridPane.getChildren().remove(turretBoxes[x][y].getVBox());
        }
        turretBoxes[x][y] = new MapTileBox(map.getTurret(x, y));
        mapGridPane.add(turretBoxes[x][y].getVBox(), x, y);
        turretBoxes[x][y].getVBox().setOnMouseClicked(Action -> shop.shopItemsTracker.updateVBox(map.getTurret(x, y)));
    }
}
