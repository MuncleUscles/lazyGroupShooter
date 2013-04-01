package collision;

import java.util.ArrayList;

import main.Game;

import org.newdawn.slick.geom.Vector2f;

import gameengine.CircleObject;
import gameengine.GameObject;

public class Collider {
	
	
	public static void doCollisions(ArrayList<GameObject> objects, int delta)
	{
		//resolveAll(objects);
		//System.out.println("resolved");
		
		double stepPercentage = delta/Game.FRAME_TIME;
		ArrayList<Collision> collisions = new ArrayList<Collision>();
        double time = 0;
        
        Collision soonestCollision = null;
        
        
        //Do all collisions in the frame
        while(time<stepPercentage)
        {
            collisions.clear();
            soonestCollision = null;
            double soonestCollTime = stepPercentage+1;
            //Find the first collision    
            for(int i=0;i<objects.size();i++)
            {
            	if(objects.get(i).isCollidable())
            	{
            		GameObject o = objects.get(i);
            		
	                for(int j = i+1; j<objects.size();j++)
	                {
	                	if(objects.get(j).isCollidable() && o!=objects.get(j))
	                	{
		                    GameObject oj = objects.get(j);
		                    
		                    boolean exists=false;
		                    for(int k=0;k<collisions.size();k++)
		                    {
		                        Collision temp=collisions.get(k);
		                        if((( o==temp.a )||(o==temp.b))&&((oj==temp.a)||(oj==temp.b))) exists=true;
		                    }
		                    if(!exists)
		                    {    
		                        
		                        double collisionTime = Collider.testCollide(o, oj, delta)+time;
		                        
		                        //System.out.println(time + " "+collisionTime+" "+o.type()+" "+oj.type());
		                        
		                        if(collisionTime - time < 1E-5 && collisionTime - time >=0)
		                        {
		                        	Collider.collide(o, oj);
		                        }
		                        else if((collisionTime >= time)&&(collisionTime <= stepPercentage))
		                        {
		                        	
		                             if(collisionTime < soonestCollTime)  
		                             {
		                            	 //System.out.println(collisionTime+"");
		                                 soonestCollTime = collisionTime;
		                                 soonestCollision  = new Collision(o,oj,collisionTime);
		                                 collisions.add(soonestCollision);
		                             }
		                        }
		                    }
	                	}
	                }    
            	}

            }
            
            if(soonestCollTime - time == 0)
            {
            	//System.out.println(soonestCollTime+"");
            	//soonestCollTime = time + 0.0000001;
            }
            
            //Do collisons
            if(soonestCollTime<stepPercentage)
            {
            	//Move the objects
                for(int i=0;i<objects.size();i++)
                {
                    GameObject oo = objects.get(i);
                    //oo.move((soonestCollTime - time));
                }

                //Perform collision
                /*
                for(int j=0;j<collisions.size();j++)
                {
                    
                    Collision temp = collisions.get(j);
                    
                    Collider.collide(temp.a,temp.b);
                }
                */
                if(soonestCollision!= null)
                {
                	Collider.collide(soonestCollision.a, soonestCollision.b);
                }
                
                
                time = soonestCollTime;
            
            }
            else
            {
            	
            	
            	//Move the objects
                for(int i=0;i<objects.size();i++)
                {
                    GameObject oo = objects.get(i);
                    //oo.move((stepPercentage - time));
                }

                //Perform collision
                /*
                for(int j=0;j<collisions.size();j++)
                {
                    
                    Collision temp = collisions.get(j);
                    
                    Collider.collide(temp.a,temp.b);
                }
                */
                if(soonestCollision!= null)
                {
                	Collider.collide(soonestCollision.a, soonestCollision.b);
                }
                
                time = stepPercentage;   
            }
        }
	}
	
	
	
	public static double testCollide(GameObject a, GameObject b, int delta)
	{
		if(!movingTowards(a,b))
		{
			//System.out.println("asd");
			return -1;
		}
		
		if(a.collisionType().equals("Circle"))
        {
            if(b.collisionType().equals("Circle"))
            {
                return circleCircleTestCollide((CircleObject)a, (CircleObject)b, delta);
            }
        }
		
		
		return -1;
        
	}
	
	private static double circleCircleTestCollide(CircleObject a, CircleObject b, int delta)
	{
		
		double t=-1;
		
		Vector2f aPosition = a.getPosition().copy();
		Vector2f bPosition = b.getPosition().copy();
		Vector2f aVelocity = a.getVelocity().copy();
		Vector2f bVelocity = b.getVelocity().copy();
		
		double distanceSq = aPosition.distanceSquared(bPosition);
		double radiusSumSq = (a.getRadius() + b.getRadius())*(a.getRadius() + b.getRadius());
		
		
		
		//If overlapping
		if(distanceSq < radiusSumSq)
		{
			//System.out.println("Overlap");
		}
		//Preventative detection
		else
		{
			Line l = new Line(aPosition, aVelocity.sub(bVelocity));
            CircleObject c = new CircleObject(bPosition, a.getRadius()+b.getRadius());

            ArrayList<Float> collisions = l.intersect(c);

            if(collisions.size() == 1)
            {
                float tempt = (Float)collisions.get(0);
                if(tempt>0) t=tempt;
            }
            else if(collisions.size()==2)
            {
                float tempt1 = (Float)collisions.get(0);
                float tempt2 = (Float)collisions.get(1);
                if((tempt1<0)&&(tempt2>=0)) t = tempt2;
                else if((tempt2<0)&&(tempt1>=0)) t = tempt1;
                else if((tempt1>=0)&&(tempt2>=0)) t = Math.min(tempt1, tempt2);
            }
		}
		
		//System.out.println(t);
		
		return t;
	}
	
	
	public static void collide(GameObject a, GameObject b)
	{
		if(a.collisionType().equals("Circle"))
        {
            if(b.collisionType().equals("Circle"))
            {
                circleCircleCollide((CircleObject)a, (CircleObject)b);
            }
        }
		
        
	}
	
	private static void circleCircleCollide(CircleObject a, CircleObject b)
	{
		Vector2f aPosition = a.getPosition().copy();
		Vector2f bPosition = b.getPosition().copy();
		Vector2f aVelocity = a.getVelocity().copy();
		Vector2f bVelocity = b.getVelocity().copy();
    	
    	Vector2f cc = bPosition.sub(aPosition);
    	Vector2f normal = cc.getPerpendicular().normalise();
    	
    	Vector2f newAVelocity = new Vector2f();
    	Vector2f newBVelocity = new Vector2f();
    	
    	aVelocity.projectOntoUnit(normal, newAVelocity);
    	bVelocity.projectOntoUnit(normal, newBVelocity);
    	
    	if(newAVelocity.length() < 0.1) newAVelocity = new Vector2f();
    	if(newBVelocity.length() < 0.1) newBVelocity = new Vector2f();
    	
    	a.setVelocity(newAVelocity);
    	b.setVelocity(newBVelocity);
	}
	
	
	public static ShootingResult shoot(ArrayList<GameObject> objects, GameObject shooter, Vector2f origin, Vector2f direction)
	{
		GameObject target = null;
		double firstCollTime = Double.MAX_VALUE;
		ShootingResult result = new ShootingResult();
		
		
		Line line = new Line(origin, direction);
		
		for(int i=0; i<objects.size(); i++)
		{
			GameObject o = objects.get(i);
			if(o!=shooter && o.isCollidable())
			{
				if(o.collisionType() == "Circle")
				{
					float t = -1;
					
					ArrayList<Float> collisions = line.intersect((CircleObject) o);

		            if(collisions.size() == 1)
		            {
		                float tempt = (Float)collisions.get(0);
		                if(tempt>0) t=tempt;
		            }
		            else if(collisions.size()==2)
		            {
		                float tempt1 = (Float)collisions.get(0);
		                float tempt2 = (Float)collisions.get(1);
		                if((tempt1<0)&&(tempt2>=0)) t = tempt2;
		                else if((tempt2<0)&&(tempt1>=0)) t = tempt1;
		                else if((tempt1>=0)&&(tempt2>=0)) t = Math.min(tempt1, tempt2);
		            }
		            
		            //Is Collision
		            if(t>=0)
		            {
		            	if(t < firstCollTime)
		            	{
		            		firstCollTime = t;
		            		target = o;
		            		
		            		result.t = t;
		            		result.target = target;
		            	}
		            }
				}
			}
		}
		
		return result;
	}
	
	private static boolean movingTowards(GameObject a, GameObject b)
	{
		double dot = b.getPosition().copy().sub(a.getPosition()).dot(a.getVelocity().copy().sub(b.getVelocity()));
		
		return dot > 0;
	}
	
	
	public static void resolveAll(ArrayList<GameObject> objects)
	{
		boolean hadIssue = true;
		
		for(int k=0; k<3; k++)
		{
			hadIssue = false;
			
			for(int i=0;i<objects.size();i++)
            {
            	if(objects.get(i).isCollidable())
            	{
            		GameObject o = objects.get(i);
            		
	                for(int j = i+1; j<objects.size();j++)
	                {
	                	if(objects.get(j).isCollidable() && o!=objects.get(j))
	                	{
		                    GameObject oj = objects.get(j);
		                    
		                    if(resolve(o, oj))
		                    {
		                    	
		                    	//hadIssue = true;
		                    }
	                	}
	                }    
            	}
            }
		}
	}
	
	
	public static boolean resolve(GameObject a, GameObject b)
	{
		
		
		if(a.collisionType().equals("Circle"))
        {
            if(b.collisionType().equals("Circle"))
            {
                return circleCircleResolve((CircleObject)a, (CircleObject)b);
            }
        }
		
		
		return false;
        
	}
	
	private static boolean circleCircleResolve(CircleObject a, CircleObject b)
	{
		
		double t=-1;
		
		Vector2f aPosition = a.getPosition().copy();
		Vector2f bPosition = b.getPosition().copy();
		Vector2f aVelocity = a.getVelocity().copy();
		Vector2f bVelocity = b.getVelocity().copy();
		
		double distanceSq = aPosition.distanceSquared(bPosition);
		double radiusSumSq = (a.getRadius() + b.getRadius())*(a.getRadius() + b.getRadius());
		
		
		
		//If overlapping
		if(distanceSq < radiusSumSq)
		{
			if(a.isStatic() && b.isStatic())
			{
				//Do Nothing
				return false;
			}
			else if((a.isStatic() && !b.isStatic()) || (!a.isStatic() && !b.isStatic()))
			{
				//System.out.println("resolving "+a.getPosition()+" "+b.getPosition());
				Vector2f dist = bPosition.sub(aPosition).normalise();
				
				Vector2f newPos = dist.scale((float) (a.getRadius() + b.getRadius()+1));
				
				b.setPosition(aPosition.add(newPos));
				return true;
			}
			else if(!a.isStatic() && b.isStatic())
			{
				//System.out.println("resolving "+a.getPosition()+" "+b.getPosition());
				Vector2f dist = aPosition.sub(bPosition).normalise();
				
				Vector2f newPos = dist.scale((float) (a.getRadius() + b.getRadius()+1));
				
				a.setPosition(bPosition.add(newPos));
				return true;
			}
			

			
		}
		
		return false;
	}
}
