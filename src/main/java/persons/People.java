package persons;


import javafx.scene.canvas.GraphicsContext;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Mediator object
 */
public class People {
    private final ArrayList<Person> freePeople = new ArrayList<Person>();
    private final ArrayList<Conversation> talkingPeople = new ArrayList<Conversation>();
    private final ArrayList<Person> grave = new ArrayList<Person>();

    public void addPerson(Person p){
        freePeople.add(p);
    }
    void addPeople(List<Person> people){
        freePeople.addAll(people);
    }

    void update(Town t, GraphicsContext context)
    {
        for (Person p : freePeople){
            p.update(t,context);
        }
        //makeMatches();
        for(Conversation c: talkingPeople){
            c.update();
        }

    }
    private void makeMatches(){
        HashSet<Person> availablePersons = freePeople.stream().filter(Person::canTalk).collect(Collectors.toCollection(HashSet::new));
        HashSet<Person> toRemove = new HashSet<>();

        for(Person p : freePeople){
            if (availablePersons.contains(p)){
                for(Person ap : availablePersons){
                    if (p.inSocialField(ap)){
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
        for(Person toDel: toRemove){
            freePeople.remove(toDel);
        }
        toRemove.clear();
    }
    private static class  Conversation{
        Person a;
        Person b;
        Conversation(Person a, Person b){
            this.a = a; this.b = b;
        }
        void update(){

        }
    }
}
