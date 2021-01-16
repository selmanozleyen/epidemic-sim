package persons;


import javafx.scene.canvas.GraphicsContext;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Mediator object
 */
public class People implements IPeople{
    private final ArrayList<IPerson> freePeople = new ArrayList<>();
    private final ArrayList<Conversation> talkingPeople = new ArrayList<>();
    private final ArrayList<IPerson> grave = new ArrayList<>();

    @Override
    public void addPersonList(List<IPerson> people){
        freePeople.addAll(people);
    }
    @Override
    public void update(Town t, GraphicsContext context)
    {

        makeMatches();
        updateConversationList();
        updateFreePeopleList();
        for (IPerson p : freePeople){
            p.update(t,context);
        }
        for(Conversation c: talkingPeople){
            c.update(t,context);
        }
    }
    private void updateFreePeopleList(){

    }
    private void updateConversationList(){
        ArrayList<Conversation> toRemove = new ArrayList<>();
        talkingPeople.forEach((c) -> {
            if(c.isOver){
                freePeople.add(c.a);freePeople.add(c.b);
                toRemove.add(c);
            };
        });
        talkingPeople.removeAll(toRemove);
    }
    private void makeMatches(){
        HashSet<IPerson> availablePersons = freePeople.stream().filter(IPerson::canTalk).collect(Collectors.toCollection(HashSet::new));
        HashSet<IPerson> toRemove = new HashSet<>();

        for(IPerson p : freePeople){
            if (availablePersons.contains(p)){
                for(IPerson ap : availablePersons){
                    if (p.inSocialField(ap)){
                        /*try {
                            Thread.sleep(10000000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                        availablePersons.remove(ap);
                        availablePersons.remove(p);
                        toRemove.add(ap);
                        toRemove.add(p);
                        talkingPeople.add(new Conversation(p,ap));
                        break;
                    }
                }
            }
        }
        for(IPerson toDel: toRemove){
            freePeople.remove(toDel);
        }
        toRemove.clear();
    }
    private static class  Conversation{
        IPerson a;
        IPerson b;
        double timeLeftToConversation;
        double commonSocialDistance;
        boolean isOver = false;
        Conversation(IPerson a, IPerson b){
            this.a = a; this.b = b;
            timeLeftToConversation = Math.max(
                    a.getHealthState().getComponent().getSocialTimeout(),
                    b.getHealthState().getComponent().getSocialTimeout()
            );
            commonSocialDistance = Math.min(
                    a.getHealthState().getComponent().getSocialDistance(),
                    b.getHealthState().getComponent().getSocialDistance()
            );
            assert timeLeftToConversation > 0 && commonSocialDistance >= 0;
        }
        void update(Town town, GraphicsContext gc){
            if(!isOver){
                if (timeLeftToConversation > 0){
                    timeLeftToConversation--;
                    if (a.getPhysicalState().isEnabled()){
                        a.getPhysicalState().setEnabled(false);
                    }
                    if (b.getPhysicalState().isEnabled()){
                        b.getPhysicalState().setEnabled(false);
                    }
                }
                else{
                    isOver = true;
                    a.getPhysicalState().setEnabled(true);
                    b.getPhysicalState().setEnabled(true);
                }
            }
            a.update(town,gc);
            b.update(town,gc);
        }
    }
}
