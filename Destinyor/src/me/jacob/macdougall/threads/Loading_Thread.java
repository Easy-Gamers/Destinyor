package me.jacob.macdougall.threads;

import static me.jacob.macdougall.Destinyor.create;
import static me.jacob.macdougall.Destinyor.read;
import static me.jacob.macdougall.Destinyor.write;
import me.jacob.macdougall.files.Default;
import me.jacob.macdougall.files.Files;
import me.jacob.macdougall.files.FileLoader;
import me.jacob.macdougall.files.Reader;
import me.jacob.macdougall.files.Writer;

public class Loading_Thread extends Thread_Controller implements Runnable { 
	
    int creator = 0;
    int writer = 0;
    int reader = 0;
    
    boolean setShadows = true;;
    
    //int max = 20;
    
    int fps;
    int ups;
    
    @Override
    public void start() {
        this.Loading = true;
        this.LoadingThread = new Thread(this, "LoadingHandler");
        this.LoadingThread.start();
    }
    
    @Override
    public void resume() {
        doneLoading = false;
        start();
    }
    
    @Override
    public void pause() {
        Loading = false;
        doneLoading = true;
    }
    
    @Override
    public void stop() {
        this.Loading = false;
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
            
            case 3: FileLoader.ReadFromFiles(Files.Characters);
            reader++;
            break;
            
            case 4: FileLoader.ReadFromFiles(Files.Enemies);
            reader++;
            break;
            
            case 5: FileLoader.ReadFromFiles(Files.Npcs);
            reader++;
            break;
                
            
                
            
            
            default: read = false;
                reader = 10;
                break;
        }
        //System.out.println(reader);
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
            
            case 3: FileLoader.WriteToFiles(Files.Characters);
            writer++;
            break;
            
            case 4: Writer.WriteDefault(Files.Enemies, "Enemies", Default.getEnemies(), Default.getEnemiesFormat());
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
            
            case 4: FileLoader.CreateDefaultFile(Files.Enemies, "Enemies", Default.getEnemies(), Default.getEnemiesFormat());
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
    
    @Override
    protected void update() {
        if(create)
        creater();
        if(write)
        writer();
        if(read)
        reader();
        
        ups++;
        
//        doneLoading = true;
//        pause();
    }
    
    @Override
    protected void render() {
        fps++;
    }
    
    @Override
    public synchronized void run() {
        while(Loading) {
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
        try {
			Thread.sleep(60);
		} catch (InterruptedException e) {
			return; // If interrupted continue from the start and check if loading is true else try again
		}
    }
    
}
