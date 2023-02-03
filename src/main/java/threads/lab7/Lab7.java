/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package threads.lab7;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import org.json.*;


/**
 *
 * @author omarelshobky
 */
abstract class AbstractShape implements Shape,Moveable,Cloneable{

    private Point position;
    private Point draggingPoint;
    private Color color = Color.BLACK;
    private Color fillColor = Color.BLACK ;
    private String name;
    private boolean filled;
    private int resizePoint;
    
    public AbstractShape(String name){
        this.name = name;
    }

    public int getResizePoint() {
        return resizePoint;
    }

    public void setResizePoint(int resizePoint) {
        this.resizePoint = resizePoint;
    }
    
    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getDraggingPoint() {
        return draggingPoint;
    }

    public void setDraggingPoint(Point draggingPoint) {
        this.draggingPoint = draggingPoint;
    }
    @Override
    public abstract void draw (java.awt.Graphics canvas);

    @Override
    public void moveTo(Point point) {
        setPosition(new Point(getPosition().x + (point.x - getDraggingPoint().x),getPosition().y + (point.y - getDraggingPoint().y)));
    }
    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }
    public AbstractShape retshapeObject() throws CloneNotSupportedException{
        return (AbstractShape) this.clone();
    }
    public void drawpoints(java.awt.Graphics canvas){
        canvas.setColor(Color.BLACK);
        canvas.fillRect(getPosition().x-4,getPosition().y -4, 7, 7);
    }
    public abstract boolean doResize(Point point);
    public abstract void Resize(Point point);
    public abstract JSONObject getlinRep();
    
}


class Circles extends AbstractShape{
    private int radius1;
    private int radius2;
    private boolean filled = false;
    public Circles(String name,int radius1,int radius2, Point point){
        super(name);
        this.radius1 = radius1;
        this.radius2 = radius2;
        setPosition(point);
        
    }
    public int gethorizRadius() {
        return radius1;
    }
    public int getvertRadius() {
        return radius2;
    }
    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(this.getColor());
        canvas.drawOval(getPosition().x, getPosition().y, 2*radius1, 2*radius2);
        if(isFilled()){
                    canvas.setColor(getFillColor());
                    canvas.fillOval(getPosition().x, getPosition().y, 2*gethorizRadius() ,2*getvertRadius());
                }
    }
    @Override
    public boolean contains(Point point) {
         if((point.x <= (this.getPosition().x + 2*gethorizRadius()) && point.x >= this.getPosition().x ) && (point.y <= (this.getPosition().y + 2*getvertRadius()) && point.y >= this.getPosition().y)){
         return true;
         }
         return false;
    }
    public void drawpoints(java.awt.Graphics canvas){
        super.drawpoints(canvas);
        canvas.fillRect(getPosition().x+(2*gethorizRadius())-4,getPosition().y -4, 7, 7);
        canvas.fillRect(getPosition().x-4,getPosition().y+(2*getvertRadius()) -4, 7, 7);
        canvas.fillRect(getPosition().x+(2*gethorizRadius())-4,getPosition().y+(2*getvertRadius()) -4, 7, 7);
    }
    @Override
    public boolean doResize(Point point) {
        if((Math.abs(point.x-this.getPosition().x)<= 7) && (Math.abs(point.y-this.getPosition().y) <= 7)){
            setResizePoint(1);
            return true;
         }
        else if((Math.abs(point.x-(this.getPosition().x+2*gethorizRadius()))<= 7) && (Math.abs(point.y-this.getPosition().y) <= 7)){
            setResizePoint(2);
            return true;
         }
        else if((Math.abs(point.x-this.getPosition().x)<= 7) && (Math.abs(point.y-(this.getPosition().y+2*getvertRadius())) <= 7)){
            setResizePoint(3);
            return true;
         }
        else if((Math.abs(point.x-(this.getPosition().x+2*gethorizRadius()))<= 7) && (Math.abs(point.y-(this.getPosition().y+2*getvertRadius())) <= 7)){
            setResizePoint(4);
            return true;
         }
        setResizePoint(0);
         return  false; }
    @Override
    public void Resize(Point point) {
        int c = getResizePoint();
        switch (c) {
            case 1:{
                boolean f1= false,f2 = false;
                if((radius1 -(0.5*(point.x - getPosition().x))) >= 0){
                radius1 = (int)(radius1 -(0.5*(point.x - getPosition().x)));}
                else{
                    f1= true;
                }
                if((radius2 -(0.5*(point.y - getPosition().y))) >= 0){
                radius2 = (int)(radius2 -(0.5*(point.y - getPosition().y)));} 
                else{
                    f2 = true;
                }
                if(!f1 && !f2){
                    setPosition(point);}
                if(f1 && f2){
                    setResizePoint(4);
                }
                else if(f1){
                    setResizePoint(2);
                }
                else if(f2){
                    setResizePoint(3);
                }
                break;}
            case 2:{
                boolean f1 = false,f2 =false;
                if((radius1 - (0.5*(getPosition().x+2*gethorizRadius() - point.x))) >= 0 ){
                radius1 = (int)(radius1 - (0.5*(getPosition().x+2*gethorizRadius() - point.x)));}
                else{
                    f1=true;
                }
                if((radius2 -(0.5*(point.y - getPosition().y)))>=0){
                radius2 = (int)(radius2 -(0.5*(point.y - getPosition().y))); }
                else{
                    f2 = true;
                }
                if(!f1 && !f2){
                    setPosition(new Point(getPosition().x,point.y));
                }
                else if(f1 && f2){
                    setResizePoint(3);
                }
                else if(f1){
                    setResizePoint(1);
                }
                else if(f2){
                    setResizePoint(4);
                }
                setPosition(new Point(getPosition().x,point.y));
                break;}
            case 3:{
                boolean f1 = false, f2 = false;
                if((radius1 - (0.5*(point.x-getPosition().x)))>=0){
                radius1 = (int)(radius1 - (0.5*(point.x-getPosition().x)));}
                else{
                    f1 = true;
                }
                if((radius2 -(0.5*(getPosition().y+2*getvertRadius()- point.y)))>=0){
                radius2 = (int)(radius2 -(0.5*(getPosition().y+2*getvertRadius()- point.y)));}
                else{
                    f2 = true;
                }
                if(!f1 && !f2){
                    setPosition(new Point(point.x,getPosition().y));
                }
                else if(f1 && f2){
                    setResizePoint(2);
                }
                else if(f1){
                    setResizePoint(4);
                }
                else if(f2){
                    setResizePoint(1);
                }
                break;}
            case 4:{
                boolean f1 = false,f2 = false;
                if((radius1 - (0.5*(getPosition().x+2*gethorizRadius() - point.x))) >= 0){
                radius1 = (int)(radius1 - (0.5*(getPosition().x+2*gethorizRadius() - point.x)));}
                else{
                    f1 = true;
                }
                if((radius2 -(0.5*(getPosition().y+2*getvertRadius()- point.y))) >= 0 ){
                radius2 = (int)(radius2 -(0.5*(getPosition().y+2*getvertRadius()- point.y)));}
                else{
                    f2 = true;
                }
                if(!f1 && !f2){
                    
                }
                else if(f1 && f2){
                    setResizePoint(1);
                }
                else if(f1){
                    setResizePoint(3);
                }
                else if(f2){
                    setResizePoint(2);
                }
                break;}
            default:
                throw new AssertionError();
        }
    }
        @Override
    public JSONObject getlinRep() {
        JSONObject j = new JSONObject();
        j.put("type","Oval");
        j.put("name",getName());
        j.put("width",radius1);
        j.put("length",radius2);
        j.put("px",getPosition().x);
        j.put("py",getPosition().y);
        j.put("color",getColor().getRGB());
        j.put("Fill_Color",getFillColor().getRGB());
        j.put("status",isFilled());
        return j;}
    
}


class Line extends AbstractShape{
    private Point endposition;
    
    public Line(String name,Point point1,Point point2){
        super(name);
        setPosition(point1);
        setEndposition(point2);
    }

    public Point getEndposition() {
        return endposition;
    }

    public void setEndposition(Point point) {
        this.endposition = point;
    }
    
    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(getColor());
        canvas.drawLine(getPosition().x, getPosition().y, getEndposition().x, getEndposition().y);
        }

    @Override
    public boolean contains(Point point) {
        if((Math.abs(getslope(getPosition(), point)) == getslope(getPosition(), getEndposition()))  && (point.x<= Math.max(getEndposition().x, getPosition().x)) && (point.y<= Math.max(getEndposition().y, getPosition().y)) && (point.x>=Math.min(getEndposition().x, getPosition().x)) && (point.y>= Math.min(getEndposition().y, getPosition().y))){
            return true;
        }
        return false;
    }

    public int getslope(Point point1,Point point2){
        
        try{
            int slope = (point2.y-point1.y)/(point2.x-point1.x);
            return slope;
        }
        catch(ArithmeticException e){
            return 100;
        }   
    }
    @Override
    public void moveTo(Point point) {
        super.moveTo(point);
        setEndposition(new Point(getEndposition().x + (point.x - getDraggingPoint().x),(getEndposition().y + (point.y - getDraggingPoint().y ))));
        
    }
   public void drawpoints(java.awt.Graphics canvas){
       super.drawpoints(canvas);
       canvas.fillRect(getEndposition().x-4,getEndposition().y -4, 7, 7);
   }

    @Override
    public boolean doResize(Point point) {
        if((Math.abs(point.x-this.getPosition().x)<= 7) && (Math.abs(point.y-this.getPosition().y) <= 7)){
            setResizePoint(1);
            return true;
         }
        if((Math.abs(point.x-getEndposition().x)<= 7) && (Math.abs(point.y-getEndposition().y) <= 7)){
            setResizePoint(2);
            return true;
        }
         setResizePoint(0);
         return  false; }

    @Override
    public void Resize(Point point) {
        int c = getResizePoint();
        switch (c) {
            case 1:{
                setPosition(point);
                break;}
            case 2:{
                this.endposition = point;
                break;}
            default:
                throw new AssertionError();
        }
    }

    @Override
    public JSONObject getlinRep() {
        JSONObject j = new JSONObject();
        j.put("type","Line");
        j.put("name",getName());
        j.put("px",getPosition().x);
        j.put("py",getPosition().y);
        j.put("p2x",getEndposition().x);
        j.put("p2y",getEndposition().y);
        j.put("color",getColor().getRGB());
        j.put("status",isFilled());
        return j;
    }
    
}


class Triangle extends AbstractShape{
    private Point point2,point3;
    private boolean filled = false;
    public Triangle(String name,Point point1, Point point2,Point point3 ){
        super(name);
        setPosition(point1);
        setPoint2(point2);
        setPoint3(point3);
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public Point getPoint3() {
        return point3;
    }

    public void setPoint3(Point point3) {
        this.point3 = point3;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public int [] retxs(){
        int[]xs = {getPosition().x, point2.x,point3.x};
        return xs;
    }
    public int [] retys(){
        int[]ys = {getPosition().y, point2.y,point3.y};
        return ys;
    }
    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(getColor());
        canvas.drawPolygon(retxs(),retys(), 3);
        if(isFilled()){
            canvas.setColor(getFillColor());
            canvas.fillPolygon(retxs(),retys(),3);
        }
    }
    @Override
    public boolean contains(Point point) {
        if(getArea(point,this.getPosition(), point2)+ getArea(point,this.getPosition(), point3)+getArea(point, point3 , point2) == getArea(getPosition(), point2, point3)){
            return true;
        }
        return false;
    }
    public int getArea(Point p1, Point p2,Point p3){
        int x = Math.abs(((p2.x-p1.x)*(p3.y-p1.y) )- ((p2.y-p1.y)*(p3.x-p1.x)))/2;
        return Math.abs((((p2.x-p1.x)*(p3.y-p1.y) )- ((p2.y-p1.y)*(p3.x-p1.x)))/2); 
    }
    @Override
    public void moveTo(Point point) {
        super.moveTo(point);
        setPoint2(new Point(point2.x+ (point.x - getDraggingPoint().x),point2.y+ (point.y - getDraggingPoint().y)));
        setPoint3(new Point(point3.x+ (point.x - getDraggingPoint().x),point3.y+ (point.y - getDraggingPoint().y)));
        
    }
    public void drawpoints(java.awt.Graphics canvas){
       super.drawpoints(canvas);
       canvas.fillRect(point2.x-4,point2.y -4, 7, 7);
       canvas.fillRect(point3.x-4,point3.y -4, 7, 7);
   }

    @Override
    public boolean doResize(Point point) {
       if((Math.abs(point.x-this.getPosition().x)<= 7) && (Math.abs(point.y-this.getPosition().y) <= 7)){
           setResizePoint(1);
           return true;
         }
       else if((Math.abs(point.x-point2.x)<= 7) && (Math.abs(point.y-point2.y) <= 7)){
           setResizePoint(2);
           return true;
        }
       else if((Math.abs(point.x-point3.x)<= 7) && (Math.abs(point.y-point3.y) <= 7)){
           setResizePoint(3);
           return true;
       }
        setResizePoint(0);
       return false;
    }

    @Override
    public void Resize(Point point) {
        int c = getResizePoint();
        switch (c) {
            case 1:{
                setPosition(point);
                break;
            }
            case 2:{
                point2 = point;
                break;
            }
            case 3:{
                point3 = point;
                break;
            }
            default:
                throw new AssertionError();
        }
    }

    @Override
    public JSONObject getlinRep() {
        JSONObject j = new JSONObject();
        j.put("type","Triangle");
        j.put("name",getName());
        j.put("px",getPosition().x);
        j.put("py",getPosition().y);
        j.put("p2x",point2.x);
        j.put("p2y",point2.y);
        j.put("p3x",point3.x);
        j.put("p3y",point3.y);
        j.put("color",getColor().getRGB());
        j.put("Fill_Color",getFillColor().getRGB());
        j.put("status",isFilled());
        return j;
    }
}


class Rectangle extends AbstractShape{
    private int length;
    private int width;
    private boolean filled = false;

    public Rectangle(int length, int width, String name,Point point) {
        super(name);
        this.length = length;
        this.width = width;
        setPosition(point);
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }
    

    
    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(getColor());
        canvas.drawRect(getPosition().x, getPosition().y,width,length);
        if(isFilled()){
            canvas.setColor(getFillColor());
            canvas.fillRect(getPosition().x, getPosition().y, getWidth(),getLength());
        }
    }

    @Override
    public boolean contains(Point point) {
        if((point.x <= (this.getPosition().x + getWidth()) && point.x >= this.getPosition().x ) && (point.y <= (this.getPosition().y + getLength()) && point.y >= this.getPosition().y)){
         return true;
         }
        return false;
    }

    public boolean doResize(Point point){
        if((Math.abs(point.x-this.getPosition().x)<= 7) && (Math.abs(point.y-this.getPosition().y) <= 7)){
            setResizePoint(1);
            return true;
         }
        else if((Math.abs(point.x-(this.getPosition().x+getWidth()))<= 7) && (Math.abs(point.y-this.getPosition().y) <= 7)){
         setResizePoint(2);
            return true;
         }
        else if((Math.abs(point.x-this.getPosition().x)<= 7) && (Math.abs(point.y-(this.getPosition().y+getLength())) <= 7)){
         setResizePoint(3);
            return true;
        }
        else if((Math.abs(point.x-(this.getPosition().x+getWidth()))<= 7) && (Math.abs(point.y-(this.getPosition().y+getLength())) <= 7)){
         setResizePoint(4);
            return true;
         }
         setResizePoint(0);
        return  false; 
    }
    
    public void drawpoints(java.awt.Graphics canvas){
        super.drawpoints(canvas);
        canvas.fillRect(getPosition().x+(getWidth())-4,getPosition().y -4, 7, 7);
        canvas.fillRect(getPosition().x-4,getPosition().y+getLength()-4, 7, 7);
        canvas.fillRect(getPosition().x+getWidth()-4,getPosition().y+getLength()-4, 7, 7);
    }

    @Override
    public void Resize(Point point) {
        int c = getResizePoint();
        switch (c) {
            case 1:{
                boolean f1=false,f2=false;
                if((getWidth() - ((point.x - getPosition().x))) >= 0 ){
                width = (int)(getWidth() -((point.x - getPosition().x)));}
                else{
                   f1= true;
                }
                if((getLength() -((point.y - getPosition().y))) >= 0){
                length = (int)(getLength() -((point.y - getPosition().y))); }
                else{
                    f2= true;
                }
                if(!f1 && !f2){
                    setPosition(point);
                }
                else if (f1&&f2){
                    setResizePoint(4);
                }
                else if(f1){
                    setResizePoint(2);
                }
                else if(f2){
                    setResizePoint(3);
                }
                break;}
            case 2:{
                boolean f1 = false,f2 = false;
                if((getWidth() - ((getPosition().x+getWidth() - point.x))) >= 0){
                width = (int)(getWidth() - ((getPosition().x+getWidth() - point.x)));}
                else{
                    f1= true;
                }
                if((getLength() -((point.y - getPosition().y)))>= 0){
                length = (int)(getLength() -((point.y - getPosition().y))); }
                else{
                    f2 = true;
                }
                if(!f1 && !f2){
                setPosition(new Point(getPosition().x,point.y));}
                else if(f1 && f2){
                    setResizePoint(3);
                }
                else if(f1){
                    setResizePoint(1);
                }
                else if(f2){
                    setResizePoint(4);
                }
                break;}
            case 3:{
                boolean f1 = false, f2 = false; 
                if((getWidth() - ((point.x - getPosition().x)))>= 0 ){
                width = (int)(getWidth() - ((point.x - getPosition().x)));}
                else{
                f1 = true;
                }
                if((getLength() -((getPosition().y+getLength()- point.y)))>= 0){
                length = (int)(getLength() -((getPosition().y+getLength()- point.y)));}
                else{
                  f2=true;
                }
                if(!f1 && !f2 ){
                    setPosition(new Point(point.x,getPosition().y));}
                else if(f1 && f2){
                    setResizePoint(2);
                }
                else if(f1){
                    setResizePoint(4);
                }
                else if(f2){
                    setResizePoint(1);
                }
                break;}
            case 4:{
                boolean f1 = false, f2 = false;
                if((getWidth() - ((getPosition().x+getWidth() - point.x))) >= 0){
                width = (int)(getWidth() - ((getPosition().x+getWidth() - point.x)));}
                else{
                    f1= true;
                }
                if((getLength() -((getPosition().y+getLength()- point.y))) >= 0 ){
                length = (int)(getLength() -((getPosition().y+getLength()- point.y)));}
                else{
                    f2 = true;
                }
                if(!f1 && !f2){
                    
                }
                else if(f1 && f2){
                    setResizePoint(1);
                }
                else if(f1){
                    setResizePoint(3);
                }
                else if(f2){
                    setResizePoint(2);
                }
                break;}
            default:{
                throw new AssertionError();}
        }
    }

    @Override
    public JSONObject getlinRep() {
        JSONObject j = new JSONObject();
        j.put("type","Rectangle");
        j.put("name",getName());
        j.put("width",width);
        j.put("length",length);
        j.put("px",getPosition().x);
        j.put("py",getPosition().y);
        j.put("color",getColor().getRGB());
        j.put("Fill_Color",getFillColor().getRGB());
        j.put("status",isFilled());
        return j;}
}

public class Lab7{

    public static void main(String[] args) {
        new Paint().setVisible(true);
    }
}
