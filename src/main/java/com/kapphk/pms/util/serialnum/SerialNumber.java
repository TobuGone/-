package com.kapphk.pms.util.serialnum;

public abstract class SerialNumber {
	public synchronized String getSerialNumber() {  
        return process();  
    }  
  
    protected abstract String process();
}
