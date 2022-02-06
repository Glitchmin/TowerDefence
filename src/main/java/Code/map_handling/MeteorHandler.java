package Code.map_handling;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class MeteorHandler {
    private List<Meteor> meteorList;
    private long lastTimeCalc;
    private final Map map;
    public MeteorHandler(long time, Map map){
        lastTimeCalc = time;
        this.map = map;
        meteorList = new ArrayList<>();
    }
    public void calcMeteors(long time, List<Meteor> meteorList1){
        meteorList.addAll(meteorList1);
        for (Meteor meteor: meteorList){
            meteor.h -= 2*(double)(time-lastTimeCalc)/1000;
            if (meteor.h<=0){
                for(Enemy enemy: map.getEnemies()){
                    if (enemy.isInRange(meteor.position, meteor.radius)){
                        enemy.decreaseHp(meteor.dmg);
                        enemy.freeze(meteor.freezeTime);
                    }
                }
            }
        }
        lastTimeCalc = time;
        meteorList.removeIf(meteor -> (meteor.h<=0));
    }
}
