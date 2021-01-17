package persons;

import javafx.scene.canvas.GraphicsContext;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Mediator object
 */
public class People implements IPeople{
    private final ArrayList<IPerson> freePeople = new ArrayList<>();
    private final ArrayList<Conversation> talkingPeople = new ArrayList<>();
    private final ArrayList<IPerson> grave = new ArrayList<>();
    private final double spreadFactor;
    private final double mortalityRate;
    private int population = 0;

    @Override
    public double getMortalityRate() {
        return mortalityRate;
    }

    public People(double spreadFactor, double mortalityRate) {
        this.spreadFactor = spreadFactor;
        this.mortalityRate = mortalityRate;
    }

    @Override
    public double getSpreadFactor(){
        return spreadFactor;
    }

    @Override
    public void addPersonList(List<IPerson> people){
        population+=people.size();
        freePeople.addAll(people);
    }
    @Override
    public void update(Town t, GraphicsContext context)
    {

        cleanConversationList();
        updateFreePeopleList();
        makeMatches();

        /*if( population != talkingPeople.size()*2+freePeople.size()+grave.size()){
            System.out.println(population);
            System.out.println(talkingPeople.size());
            System.out.println(freePeople.size());
            System.exit(-1);
        }*/
        for (IPerson p : freePeople){
            p.update(t,context);
        }
        for(Conversation c: talkingPeople){
            c.update(t,context);
        }
    }
    private void updateFreePeopleList(){

    }
    private void cleanConversationList(){
        /*int  a = talkingPeople.size()*2+freePeople.size();*/
        ArrayList<Conversation> toRemove = new ArrayList<>();
        talkingPeople.forEach((c) -> {
            if(c.isOver){
                freePeople.add(c.a);freePeople.add(c.b);
                toRemove.add(c);
            };
        });
        talkingPeople.removeAll(toRemove);
        /*int  b = talkingPeople.size()*2+freePeople.size();
        if(a != b){
            System.out.println("LMAO");
            System.exit(-1);
        }*/

    }
    private void makeMatches(){
        /*int  a = talkingPeople.size()*2+freePeople.size();
        System.out.println("Before");
        System.out.println(talkingPeople.size());
        System.out.println(freePeople.size());*/
        HashSet<IPerson> includes = new HashSet<>();
        for(IPerson p1 : freePeople){
            for(IPerson p2: freePeople){

                /*if (p1.inSocialField(p2)!=p2.inSocialField(p1)){
                    System.out.println("HERE");
                    System.exit(-1);
                }*/
                if(!includes.contains(p1) && !includes.contains(p2) && p1.inSocialField(p2) && p1 != p2){
                    includes.add(p1);
                    includes.add(p2);
                    talkingPeople.add(new Conversation(p1,p2));
                }
            }
        }
        for(Conversation c: talkingPeople){
            freePeople.remove(c.a);freePeople.remove(c.b);
        }
        /*int  b = talkingPeople.size()*2+freePeople.size();
        if(a != b){
            System.out.println(talkingPeople.size());
            System.out.println(freePeople.size());
            System.out.println("MAKE MATCHES IS BUSTED");
            System.exit(-1);
        }*/

    }
    private class  Conversation{
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
            if(a.getHealthState().isInfected() ^ b.getHealthState().isInfected()){
                double p = getSpreadFactor() * (1+timeLeftToConversation/10) * a.getHealthState().getComponent().getMaskCoefficient()
                        * b.getHealthState().getComponent().getMaskCoefficient() * (1-commonSocialDistance/10.0);
                p = Math.min(1,p);

                /*If Got Infected*/
                if (p > ThreadLocalRandom.current().nextDouble()){
                    if(!a.getHealthState().isInfected()){
                        a.getHealthState().setInfected(true);
                        a.getHealthState().setTimeToDie(100*(1-getMortalityRate()));
                    } else{
                        b.getHealthState().setInfected(true);
                        b.getHealthState().setTimeToDie(100*(1-getMortalityRate()));
                    }
                }
            }
            assert timeLeftToConversation < 0 || commonSocialDistance >= 0;

            timeLeftToConversation = timeLeftToConversation*60;
        }
        void update(Town town, GraphicsContext gc){
            if(!isOver){
                if (timeLeftToConversation > 0 &&
                    !a.getHealthState().isDead() &&
                    !b.getHealthState().isDead()
                ){
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
                    if (!a.getHealthState().isDead()){
                        a.getPhysicalState().setEnabled(true);
                    }
                    if(!b.getHealthState().isDead()){
                        b.getPhysicalState().setEnabled(true);
                    }
                }
            }
            a.update(town,gc);
            b.update(town,gc);
        }
    }
}
