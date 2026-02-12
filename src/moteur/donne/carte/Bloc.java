package moteur.donne.carte;

import java.util.Objects;

public class Bloc 
{
	
	private int x;
	private int y;
	
	
	public Bloc(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}

	public int getX() 
	{
		return x;
	}
	
	public int getY() 
	{
		return y;
	}
	@Override
	public boolean equals (Object o)
	{
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bloc that = (Bloc) o;
        return getX()==that.getX() && getY()==that.getY();
    }

    @Override
    public int hashCode() 
	{
		//pas utile mais bon 
        return Objects.hash(getX(),getY());
    }
	
	@Override
	public String toString() {
		return "Bloc [x=" + x + ", y=" + y + "]";
	}
	
	
}
