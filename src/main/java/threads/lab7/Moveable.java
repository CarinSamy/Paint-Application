/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package threads.lab7;
import java.awt.Point;
/**
 *
 * @author omarelshobky
 */
public interface Moveable {
    void setDraggingPoint(Point point);//momkin tekoon deafult??
    Point getDraggingPoint();
    boolean contains(Point point);
    void moveTo(Point point);
}
