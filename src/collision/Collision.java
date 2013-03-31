package collision;

import gameengine.GameObject;

public class Collision {

	GameObject a;
	GameObject b;
	double t;
	
	public Collision(GameObject a, GameObject b, double t)
	{
		this.a = a;
		this.b = b;
		this.t = t;
	}
	
}
