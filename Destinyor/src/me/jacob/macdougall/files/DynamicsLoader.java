package me.jacob.macdougall.files;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.w3c.dom.*;

import me.jacob.macdougall.Destinyor;
import me.jacob.macdougall.npcs.body.Limb;
import me.jacob.macdougall.world.*;

public class DynamicsLoader {
	
    public static void init() {
    	loadLimbs();
		loadTiles();
        loadLevels();
        loadElements();
        loadObjects();
	}
    
        private static void loadLevels() {
		XMLFile mapXML = new XMLFile("/map.xml");
		Document doc = mapXML.asDocument();
		
		NodeList pxlDefine1 = ((Element) ((Element) doc.getElementsByTagName("maps").item(0)).getElementsByTagName("define").item(0)).getElementsByTagName("pixel");
		List<Color> pxlCol1 = new ArrayList<>();
		List<String> bind1 = new ArrayList<>();
		
		BufferedImage mp = null;
		
		try {
			mp = ImageIO.read(Destinyor.class.getResource(Files.DestinyorMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int lw1 = mp.getWidth() / LevelMap.FloorWidth;
		int lh1 = mp.getHeight() / LevelMap.FloorHeight;
		
		BufferedImage[][] maps = new BufferedImage[lw1][lh1];
		LevelMap[] mps = new LevelMap[lw1 * lh1];
		
		int cw1 = 0;
		
		// Gets map Size
			for(int i = 0; i < maps.length; i++){
				for(int j = 0; j < maps[i].length; j++){
					maps[i][j] = mp.getSubimage(i * LevelMap.FloorWidth, j * LevelMap.FloorHeight, LevelMap.FloorWidth, LevelMap.FloorHeight);
					mps[cw1] = new LevelMap();
					mps[cw1].floor = cw1 + 1;
					cw1++;
					}
				}
		// Gets red, blue, green pixel data	
		for(int i = 0; i < pxlDefine1.getLength(); i++){
			Element e = (Element) pxlDefine1.item(i);
			String[] rgb = e.getAttribute("rgb").split(",");
			if(rgb.length != 3) throw new RuntimeException("RGB Lenght is not 3!");
			int[] rgbi = new int[3];
			rgbi[0] = Integer.parseInt(rgb[0]);//Red
			rgbi[1] = Integer.parseInt(rgb[1]);//Green
			rgbi[2] = Integer.parseInt(rgb[2]);//Blue
			Color col = new Color(rgbi[0], rgbi[1], rgbi[2]);
			pxlCol1.add(col);
			bind1.add(e.getAttribute("bind"));
		}
		
		cw1 = 0;
                for(BufferedImage[] m : maps) {
                        for(BufferedImage map : m) {
				int w = map.getWidth();
				int h = map.getHeight();
				for(int i = 0; i < w; i++) {
					for(int j = 0; j < h; j++) {
						int rgb = map.getRGB(i, j);
						int rr = (rgb >> 16) & 0xFF;
						int gg = (rgb >> 8) & 0xFF;
						int bb = rgb & 0xFF;
						for(int k = 0; k < pxlCol1.size(); k++){
							Color c = pxlCol1.get(k);
							if(rr == c.getRed() && gg == c.getGreen() && bb == c.getBlue()) {
								mps[cw1].tiles[i][j] = Tile.tiles[Integer.parseInt(bind1.get(k))].id;
							}
						}
					}
				}
				cw1++;
                                map.flush();
			}
                        for(int i = 0; i < m.length; i++) {
                            m[i].flush();
                        }
                }
                mp.flush();
                for(int i = 0; i < maps.length; i++) {
                    for(int j = 0; j < maps[i].length; j++) {
                        maps[i][j].flush();
                    }
                }
                
                maps = null;
                
		//Register all levels
                for(LevelMap map : mps)
			LevelMap.maps.put(map.floor, map);
	}

    //@SuppressWarnings("ResultOfObjectAllocationIgnored")
	private static void loadTiles() {
		XMLFile tileXML = new XMLFile("/tiles.xml");
		Document doc = tileXML.asDocument();
		NodeList tileNodes = doc.getElementsByTagName("tiles");
		for(int i = 0; i < tileNodes.getLength(); i++) {
			Node n = tileNodes.item(i);
			NodeList tiles = ((Element) n).getElementsByTagName("tile");
			for(int j = 0; j < tiles.getLength(); j++) {
				Element e = (Element) tiles.item(j);
				new Tile(Integer.parseInt(e.getAttribute("id")), e.getAttribute("name"), Integer.parseInt(e.getAttribute("solid")) == 0 ? false : true, e.getAttribute("sprite"));
			}
		}
	}
	
	private static void loadElements() {
		XMLFile objectXML = new XMLFile("/elements.xml");
		Document doc = objectXML.asDocument();
		NodeList mapNodes = doc.getElementsByTagName("elements");
		for(int i = 0; i < mapNodes.getLength(); i++) {
			Node n = mapNodes.item(i);
			NodeList defineNodes = ((Element) n).getElementsByTagName("element");

			for(int j = 0; j < defineNodes.getLength(); j++) {
				Element e = (Element) defineNodes.item(j);
				me.jacob.macdougall.magic.Element element = new me.jacob.macdougall.magic.Element
						(e.getAttribute("type"),
								me.jacob.macdougall.magic.Element.get(e.getAttribute("firstElement")), 
								me.jacob.macdougall.magic.Element.get(e.getAttribute("secondElement")), 
								me.jacob.macdougall.magic.Element.get(e.getAttribute("weakness")));
				me.jacob.macdougall.magic.Element.put(element);
			}
		}
	}
	
	private static void loadObjects() {
		XMLFile objectXML = new XMLFile("/objects.xml");
		Document doc = objectXML.asDocument();
		NodeList mapNodes = doc.getElementsByTagName("objects");
		for(int i = 0; i < mapNodes.getLength(); i++) {
			Node n = mapNodes.item(i);
			NodeList defineNodes = ((Element) n).getElementsByTagName("object");

			for(int j = 0; j < defineNodes.getLength(); j++) {
				Element e = (Element) defineNodes.item(j);
				LevelMap.maps.get(i + 1).putObjects(Objects.newInstance(
						e.getAttribute("name"), Integer.parseInt(e.getAttribute("x")),
						Integer.parseInt(e.getAttribute("y")), Boolean.parseBoolean(e.getAttribute("animated")),
						e.getAttribute("frame")));
			}
		}
	}
	
	private static void loadLimbs() {
		XMLFile limbXML = new XMLFile("/limbs.xml");
		Document doc = limbXML.asDocument();
		NodeList limbNodes = doc.getElementsByTagName("limbs");
		for(int i = 0; i < limbNodes.getLength(); i++) {
			Node n = limbNodes.item(i);
			NodeList limbs = ((Element) n).getElementsByTagName("limb");
			for(int j = 0; j < limbs.getLength(); j++) {
				Element e = (Element) limbs.item(j);
				new Limb(e.getAttribute("name"), false);
			}
		}
	}

	
}
