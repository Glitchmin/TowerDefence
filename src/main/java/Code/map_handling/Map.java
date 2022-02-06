package Code.map_handling;

import Code.Vector2d;
import Code.gui.IEnemyChangeObserver;
import Code.gui.ITurretChangeObserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.System.out;

public class Map {
    private Integer width;
    private Integer height;
    private Landscape[][] landscapesArray;
    private final Vector2d startPoint;
    private final Vector2d endPoint;
    private final AbstractTurret[][] turretsArray;
    private final List <AbstractTurret> turretsList;
    private final List<ITurretChangeObserver> observersList;
    private final List<Enemy> enemies;
    private final List<PathTile> pathNoSwimming;
    private final List<PathTile> pathSwimming;

    public Map(int file_number) {
        getLandscapeFromFile(file_number);
        startPoint = new Vector2d(0.0, 4.0);
        endPoint = new Vector2d(0.0, 11.0);
        turretsArray = new AbstractTurret[width][height];
        turretsList = new ArrayList<>();
        observersList = new ArrayList<>();
        enemies = new Vector<>();
        pathNoSwimming = new ArrayList<>();
        pathSwimming = new ArrayList<>();
        findPaths(pathNoSwimming, false);
        findPaths(pathSwimming, true);
    }

    private void findPaths(List<PathTile> path, boolean swimming) {
        MoveDirection[][] visited = new MoveDirection[width][height];
        visited[startPoint.IntX()][startPoint.IntY()] = MoveDirection.RIGHT;
        DFS(startPoint.IntX(), startPoint.IntY(), swimming, visited);
        Vector2d pos = new Vector2d(endPoint.x, endPoint.y);
        path.add(new PathTile(pos, visited[pos.IntX()][pos.IntY()]));
        while (!pos.equals(startPoint)) {
            int newX = pos.IntX() - visited[pos.IntX()][pos.IntY()].getVector2d().IntX();
            int newY = pos.IntY() - visited[pos.IntX()][pos.IntY()].getVector2d().IntY();
            pos = new Vector2d((double) newX, (double) newY);
            path.add(new PathTile(pos, visited[pos.IntX()][pos.IntY()]));
        }
        Collections.reverse(path);
    }

    private void DFS(int x, int y, boolean swimming, MoveDirection[][] visited) {
        if (visited[endPoint.IntX()][endPoint.IntY()] != null) {
            return;
        }
        for (MoveDirection moveDirection : MoveDirection.values()) {
            int newX = x + moveDirection.getVector2d().IntX();
            int newY = y + moveDirection.getVector2d().IntY();
            if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                if (swimming && landscapesArray[newX][newY].getLandscapeType() == LandscapeType.RIVER
                        && visited[newX][newY] == null) {
                    visited[newX][newY] = moveDirection;
                    DFS(newX, newY, swimming, visited);
                }
            }
        }

        for (MoveDirection moveDirection : MoveDirection.values()) {
            int newX = x + moveDirection.getVector2d().IntX();
            int newY = y + moveDirection.getVector2d().IntY();
            if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
                if (landscapesArray[newX][newY].getLandscapeType() == LandscapeType.PATH && visited[newX][newY] == null) {
                    visited[newX][newY] = moveDirection;
                    DFS(newX, newY, swimming, visited);
                }
            }
        }
    }

    private void getLandscapeFromFile(int file_number) {
        //based on https://techvidvan.com/tutorials/read-csv-file-in-java/
        try {
            File file = new File("src/main/resources/landscape/landscape" + file_number + ".csv");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = " ";
            String[] tempArr;
            String size_line = br.readLine();
            String[] size_array = size_line.split(";");
            width = Integer.parseInt(size_array[0]);
            height = Integer.parseInt(size_array[1]);
            landscapesArray = new Landscape[width][height];

            int j = 0;
            while ((line = br.readLine()) != null) {
                tempArr = line.split(";");
                int i = 0;
                for (String tempStr : tempArr) {
                    landscapesArray[i++][j] = new Landscape(LandscapeType.values()[Integer.parseInt(tempStr)]);
                }
                j++;
            }
            br.close();
        } catch (IOException ioe) {
            out.println("couldn't find src/main/resources/landscape/landscape" + file_number + ".csv");
            ioe.printStackTrace();
        }
    }

    public void addObserver(ITurretChangeObserver turretChangeObserver) {
        observersList.add(turretChangeObserver);
    }

    private void turretChanged(int x, int y) {
        for (ITurretChangeObserver observer : observersList) {
            observer.turretChanged(x, y);
        }
    }

    public void addEnemy(Enemy enemy) {
        synchronized (this) {
            enemies.add(enemy);
        }
    }

    public void removeEnemy(Enemy enemy) {
        synchronized (this) {
            enemies.remove(enemy);
        }
    }

    public void buildTurret(int x, int y, AbstractTurret turret) {
        out.println(turretsArray[x][y]);
        if (turretsArray[x][y] == null) {
            turretsArray[x][y] = turret;
            turretsList.add(turret);
            for (Enemy enemy: getEnemies()){
                enemy.addObserver(turret);
            }
            turretChanged(x, y);
        }
    }

    public Landscape getLandscape(int x, int y) {
        return landscapesArray[x][y];
    }

    public AbstractTurret getTurret(int x, int y) {
        return turretsArray[x][y];
    }

    public List<Enemy> getEnemies() {
        synchronized (this) {
            return enemies;
        }
    }

    public List<PathTile> getPathNoSwimming() {
        return pathNoSwimming;
    }

    public List<PathTile> getPathSwimming() {
        return pathSwimming;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public List<AbstractTurret> getTurretsList() {
        return turretsList;
    }
}
