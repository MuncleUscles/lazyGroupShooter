package collision;

import gameengine.CircleObject;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class Line  {
    
    private Vector2f origin;
    private Vector2f v;
    
    public Line()
    {
        origin = new Vector2f();
        v = new Vector2f();
    }
    
    public Line(float x1, float y1, float x2, float y2)
    {
    	origin = new Vector2f(x1, y1);
        v = new Vector2f(x2, y2).sub(origin);
    }
    
    public Line(Vector2f origin, Vector2f v)
    {
        this.origin = origin;
        this.v = v;
    }
    
    public Vector2f getOrigin()
    {
        return origin;
    }
    
    public Vector2f getVector()
    {
        return v;
    }
    
    public void add(Vector2f b)
    {
       origin.add(b);
    }
    
    public float getX()
    {
    	return origin.getX();
    }
    
    public float getY()
    {
    	return origin.getY();
    }
    
    /*
    public boolean xOverlap(PhysLine b)
    {
        if((getX()<=b.getX())&&(getX2()>=b.getX())) return true;
        else if((getX2()>=b.getX2())&&(getX()<=b.getX2())) return true;
        else if((getX()>=b.getX())&&(getX2()<=b.getX2())) return true;
        else return false;
    }
    
    public boolean yOverlap(PhysLine b)
    {
        if((getY()<=b.getY())&&(getY2()>=b.getY())) return true;
        else if((getY2()>=b.getY2())&&(getY()<=b.getY2())) return true;
        else if((getY()>=b.getY())&&(getY()<=b.getY2())) return true;
        else return false;
    }
    */
    
    public ArrayList<Float> intersect(Line b)
    {
    	ArrayList<Float> result = new ArrayList();
        
        double cos = v.dot(b.getVector())/(v.length()*b.getVector().length());
        
        //If not parallel
        if((cos!=1)||(cos!=(-1)))
        {
            //Collision time on the 2nd line
            Float t2 = (v.getX()*(getY()-b.getY()) - v.getY()*(getX()-b.getX()))/
                    (b.getVector().getY()*(v.getX()) - b.getVector().getX()*(v.getY()));
            
        //Calculate and return collision point of first line
            Float t1 = (b.getVector().getX()*(getY()-b.getY()) - b.getVector().getY()*(getX()-b.getX()))/
                    (b.getVector().getY()*(v.getX()) - b.getVector().getX()*(v.getY()));

            result.add(t1);
            
        }
        
        
        
        return result;
    }
    
    public ArrayList<Float> intersectSegment(Line b)
    {
        ArrayList<Float> result = new ArrayList();
        
        double cos = v.dot(b.getVector())/(v.length()*b.getVector().length());
        
        //If not parallel
        if((cos!=1)||(cos!=(-1)))
        {
            //Collision time on the 2nd line
            Float t2 = (v.getX()*(getY()-b.getY()) - v.getY()*(getX()-b.getX()))/
                    (b.getVector().getY()*(v.getX()) - b.getVector().getX()*(v.getY()));
            
            //If that in the segment
            if((t2>=0)&&(t2<=1))
            {
                //Calculate and return collision point of first line
                Float t1 = (b.getVector().getX()*(getY()-b.getY()) - b.getVector().getY()*(getX()-b.getX()))/
                        (b.getVector().getY()*(v.getX()) - b.getVector().getX()*(v.getY()));

                result.add(t1);
            }
        }
        
        
        
        return result;
    }
    
    
    public ArrayList<Float> intersect(CircleObject circ)
    {
        ArrayList<Float> result = new ArrayList();
        
        float r = circ.getRadius();
        Vector2f f = origin.copy().sub(circ.getPosition());
        
        
        float a = v.dot(v);
        float b = 2*f.dot(v);
        float c = f.dot(f) - r*r;
        
        float discriminant = b*b-4*a*c;
        
        
        //Two intersections
        if(discriminant>0)
        {
            discriminant = (float) Math.sqrt( discriminant );
            Float t1 = (-b + discriminant)/(2*a);
            Float t2 = (-b - discriminant)/(2*a);
            
            result.add(t1);
            result.add(t2);
        }
        //tangent
        else if(discriminant==0)
        {
            discriminant = (float) Math.sqrt( discriminant );
            Float t = (-b + discriminant)/(2*a);
            result.add(t);
        }
      
        
        return result;
        
    }
    
    
    
}
