package Model;

public class Species 
{
	private String _name;
	private String _genome;
	
	/*
	 * Constructors
	 */
	public Species(String name, String genome)
	{
		_name = name;
		_genome = genome;
	}
	
	/*
	 * Functions
	 */
	public String toString()
	{
		return String.format("%s : %s", _name, _genome);
	}
	
	/*
	 * Get 
	 */
	public String getName()
	{
		return _name;
	}
	
	public String getGenome()
	{
		return _genome;
	}
}
