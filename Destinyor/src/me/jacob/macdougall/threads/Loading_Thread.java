package me.jacob.macdougall.threads;

import static me.jacob.macdougall.Destinyor.create;
import static me.jacob.macdougall.Destinyor.read;
import static me.jacob.macdougall.Destinyor.write;
import me.jacob.macdougall.files.Default;
import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.files.FileLoader;
import me.jacob.macdougall.files.ReadDefault;
import me.jacob.macdougall.files.Reader;
import me.jacob.macdougall.files.Writer;

public class Loading_Thread implements Runnable { 
	
	protected Thread LoadingThread;	
	
	private boolean running = false;
	
    int creator = 0;
    int writer = 0;
    int reader = 0;
    
    boolean setShadows = true;
    
    //int max = 20;
    
    int fps;
    int ups;
    
    public void start() {
    	running = true;
        LoadingThread = new Thread(this, "LoadingHandler");
        LoadingThread.start();
    }
    
    public void resume() {
        Thread_Controller.doneLoading = false;
        start();
    }
    
    public void pause() {
        running = false;
        Thread_Controller.doneLoading = true;
    }
    
    public void stop() {
        this.running = false;
		try {
			this.LoadingThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    private void reader() {
        switch(reader) {
        	
        	case 0: Reader.readEntities(Files.Entities);
        	reader++;
        	break;
        	
            case 1: FileLoader.ReadFromFiles(Files.Spells);
            reader++;
            break;
            
            case 2: Reader.ReadItems(Files.Items);
            reader++;
            break;
            
            case 3:
            	ReadDefault.addPlayers(Files.Characters);
            	reader++;
            	break;
            
            case 4: 
            	ReadDefault.addEnemies(Files.Enemies);
            	reader++;
            	break;
            
            case 5: 
            	ReadDefault.addNpcs(Files.Npcs);
            	reader++;
            	break;
                
            
                
            
            
            default: read = false;
                reader = 10;
                break;
        }
    }
    
    private void writer() {
        switch(writer) {
        	
        	case 0: Writer.WriteDefault(Files.Entities, "Entities", Default.getEntities(), Default.getEntitiesFormat());
        	writer++;
        	break;
        	
        	case 1: FileLoader.WriteToFiles(Files.Spells);
            writer++;
            break;
        	
        	case 2: Writer.WriteDefault(Files.Items, "Items", Default.getItems(), Default.getItemsFormat());
        	writer++;
        	break;
            
            case 3: //FileLoader.WriteToFiles(Files.Characters);
            writer++;
            break;
            
            case 4: //Writer.WriteDefault(Files.Enemies, "Enemies", Default.getEnemies(), Default.getEnemiesFormat());
            writer++;
            break;
            
            case 5: FileLoader.WriteToFiles(Files.Npcs);
            writer++;
            break;
            
            default: write = false;
                writer = 10;
                read = true;
                break;
        }
    }
    
    private void creater() {
        switch(creator) {
        	
        	case 0: FileLoader.CreateDefaultFile(Files.Entities, "Entities", Default.getEntities(), Default.getEntitiesFormat());
        	creator++;
        	break;
        	
        	case 1: FileLoader.CreateFile(Files.Spells);
            creator++;
            break;
            
        	case 2: FileLoader.CreateDefaultFile(Files.Items, "Items", Default.getItems(), Default.getItemsFormat());
        	creator++;
        	break;
        	
            case 3: FileLoader.CreateFile(Files.Characters);
            creator++;
            break;
            
            case 4: // FileLoader.CreateDefaultFile(Files.Enemies, "Enemies", Default.getEnemies(), Default.getEnemiesFormat());
            creator++;
            break;
            
            case 5: FileLoader.CreateFile(Files.Npcs);
            creator++;
            break;
            
            default: create = false;
            creator = 10;
            read = true;
            break;
        }
    }
    
    protected void update() {
        if(create)
        creater();
        if(write)
        writer();
        if(read)
        reader();
        
        ups++;
    }
    
    protected void render() {
        fps++;
    }
    
    public synchronized void run() {
        while(running) {
            update();
            render();
            if(!create) {
                if(!write) {
                    if(!read) {
                        creator = 0;
                        writer = 0;
                        reader = 0;
                        pause();
                        //System.out.printf("\n Loading: %d fps, %d updates", fps, ups);
                    }
                }
            }
        }
    }
    
}
