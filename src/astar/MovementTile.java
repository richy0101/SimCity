package astar;
import java.util.*;
import java.lang.Math;
import java.util.concurrent.*;

public class MovementTile extends Semaphore{
	public int type;
	public MovementTile(int type) {
    	super(1, true);
    	this.type = type;
    }
}