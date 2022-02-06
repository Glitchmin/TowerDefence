package Code.map_handling;

import Code.gui.IMeteorObserver;

import java.util.ArrayList;
import java.util.List;


public class MeteorHandler {
    private List<Meteor> meteorList;
    private long lastTimeCalc;
    private final Map map;
    private final List <IMeteorObserver> meteorObserverList;
    public MeteorHandler(long time, Map map){
        lastTimeCalc = time;
        this.map = map;
        meteorList = new ArrayList<>();
        meteorObserverList = new ArrayList<>();
    }

    public void addObserver(IMeteorObserver meteorObserver){
        meteorObserverList.add(meteorObserver);
    }

    public void meteorChanged(Meteor meteor){
        for (IMeteorObserver meteorObserver: meteorObserverList){
            meteorObserver.MeteorChanged(meteor);
        }
    }

    public void calcMeteors(long time, List<Meteor> meteorList1){
        meteorList.addAll(meteorList1);
        for (Meteor meteor: meteorList){
            meteor.h -= 5*(double)(time-lastTimeCalc)/1000;
            if (meteor.h<=0){
                for(Enemy enemy: map.getEnemies()){
                    if (enemy.isInRange(meteor.position, meteor.radius)){
                        enemy.decreaseHp(meteor.dmg);
                        enemy.freeze(meteor.freezeTime);
                    }
                }
            }
            meteorChanged(meteor);
        }
        lastTimeCalc = time;
        meteorList.removeIf(meteor -> (meteor.h<=0));
    }
}
