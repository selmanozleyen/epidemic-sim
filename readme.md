## HOW TO RUN
Make sure you have maven and javac then you can type:
```
mvn clean javafx:run
```
## GUI
## Visual
Red squares: People currently in social interaction.  
Blue squares: People walking around without social interaction.  
Squares with green circles: Infected people.  
![fig1](media/1.png)
## Real-Time Plot
Stats are gather by a decorated component of the simulation to demonstrate the decoupling of components.
![fig2](media/2.png)

## Design
Used [Component Pattern](https://gameprogrammingpatterns.com/component.html). Similar to what Unity has. See ```media/diagram``` for more.  
Why so over-engineered? Because the main purpose was to practice...