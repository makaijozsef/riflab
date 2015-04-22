package hu.bme.mit.cjwc0f.labor5.drools;

public interface IDroolsListener {
	// Should call the poll() static method of the EventQueue class
	void process();
}
