package dungeonraider.save;

import java.io.File;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import dungeonraider.character.Player;
import dungeonraider.engine.Engine;

/**
 *
 * @author Jono Yan
 *
 */
public class Storage {
	//
	// /**
	// * Reads a XML file
	// * @param arrayList
	// * @param file: XML file of game instance.
	// */
	// public static Engine read(File inputFile){
	// Player p = new Player("placeholder","blue");
	// HashMap<String, ArrayList<Tile>> roomItems = new
	// HashMap<String,ArrayList<Tile>>();
	// HashMap<String, ArrayList<Tile>> roomChests = new
	// HashMap<String,ArrayList<Tile>>();
	// try{
	// DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	// DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	// Document doc = docBuilder.parse(inputFile);
	// doc.getDocumentElement().normalize();
	// Element root = doc.getDocumentElement();
	// NodeList rootChildren = root.getChildNodes();
	// ArrayList<Element> elements = NodeListToArrayList(rootChildren);
	//
	// if(!root.getTagName().equals("Game")) throw new Exception("Not valid XML
	// file");//Check if valid gameState
	// for(Element e: elements){
	// if(e.getTagName().equals("Player")) p = readPlayer(e);
	// else if(e.getTagName().equals("World")){
	// roomItems = readItems(e);
	// roomChests = readChests(e);
	// }
	// else throw new Exception("Not valid XML file.");
	// }
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	//
	// GameOfDungees game = new GameOfDungees(p);
	// System.out.println(p.getRoom().getRoomName());
	// setPlayerRoom(p,game);
	// ArrayList<Room> rooms = game.getRooms();
	// removeItems(rooms);
	// placeItems(rooms, roomItems);
	// placeChests(rooms, roomChests);
	// return game;
	// }
	//
	// private static void setPlayerRoom(Player p, GameOfDungees game) {
	// // TODO Auto-generated method stub
	// ArrayList<Room> rooms = game.getRooms();
	// for(Room r:rooms){
	// if(r.getRoomName().equals(p.getRoom().getRoomName())){
	// p.setRoom(r);
	//
	// }
	// }
	// }
	//
	// /**
	// * Places Chest world objects into rooms
	// */
	// private static void placeChests(ArrayList<Room> rooms, HashMap<String,
	// ArrayList<Tile>> roomChests) {
	// for(Room r: rooms){
	// for(Map.Entry<String, ArrayList<Tile>> entry: roomChests.entrySet()){
	// if(entry.getKey().equals(r.getRoomName())){
	// for(Tile t:entry.getValue()){
	// Tile targetTile = r.getTileAt(t.getCol(), t.getRow());
	// t.getWorldObject().setRoom(r);
	// targetTile.placeWorldObject(t.getWorldObject());
	// }
	// }
	// }
	// }
	// }
	//
	// /**
	// * Reads chests from xml, places chests in tile objects
	// */
	// private static HashMap<String, ArrayList<Tile>> readChests(Element world) {
	// HashMap<String, ArrayList<Tile>> pseudoRooms = new HashMap<String,
	// ArrayList<Tile>>();
	//
	// NodeList roomNodes = world.getChildNodes();
	// ArrayList<Element> rooms = NodeListToArrayList(roomNodes);
	// for(Element e:rooms){
	// String roomName = e.getTagName();
	// ArrayList<Tile> chestTiles = new ArrayList<Tile>();
	//
	// Element chestRoot = (Element)e.getLastChild();
	// NodeList chestNodes = chestRoot.getChildNodes();
	// ArrayList<Element> chests = NodeListToArrayList(chestNodes);
	//
	// for(Element chest:chests){
	// NodeList itemNodes = chest.getChildNodes();
	// ArrayList<Element> items = NodeListToArrayList(itemNodes);
	// int code = stringToInt(items.remove(0).getTextContent());
	// int col = stringToInt(items.remove(0).getTextContent());
	// int row = stringToInt(items.remove(0).getTextContent());
	// Chest c = new Chest(code,null);
	// //TODO: Set chest room to accordingly
	//
	// for(Element item:items){
	// String name = item.getTagName();
	// Item i;
	//
	//
	// if(name.equalsIgnoreCase("key")) i = new
	// Key(stringToInt(item.getTextContent()));
	// //if(name.equalsIgnoreCase(name of items)){ //in case more items types
	// else i = new Weapon(name);
	// c.addItem(i);
	// }
	// //gets col and row from the children nodes of items.
	// Tile t = new Tile(null, null, null, col, row);
	// t.placeWorldObject(c);
	// chestTiles.add(t);
	// }
	// pseudoRooms.put(roomName, chestTiles);
	// }
	// return pseudoRooms;
	// }
	//
	// /**
	// * Removes all defaults chests and items on map
	// */
	// private static void removeItems(ArrayList<Room> rooms){
	// for(Room r:rooms){
	// Tile[][] tiles = r.getTiles();
	// for(int i = 0; i < 15; ++i){
	// for(int j = 0; j < 15; ++j){
	// tiles[j][i].placeItem(null);
	// if(tiles[j][i].getWorldObject() instanceof
	// Chest)tiles[j][i].placeWorldObject(null);
	// }
	// }
	// }
	// }
	//
	// /**
	// * Places items into rooms
	// */
	// private static void placeItems(ArrayList<Room> rooms, HashMap<String,
	// ArrayList<Tile>> roomItems) {
	// for(Room r: rooms){
	// for(Map.Entry<String, ArrayList<Tile>> entry: roomItems.entrySet()){
	// if(entry.getKey().equals(r.getRoomName())){
	// for(Tile t:entry.getValue()){
	// Tile targetTile = r.getTileAt(t.getCol(), t.getRow());
	// targetTile.placeItem(t.getItem());
	// }
	// }
	// }
	// }
	// }
	//
	// /**
	// * Reads all items on map from xml and creates items objects.
	// */
	// private static HashMap<String, ArrayList<Tile>> readItems(Element world) {
	// HashMap<String, ArrayList<Tile>> pseudoRooms = new HashMap<String,
	// ArrayList<Tile>>();
	//
	// NodeList roomNodes = world.getChildNodes();
	// ArrayList<Element> rooms = NodeListToArrayList(roomNodes);
	// for(Element e:rooms){
	// String roomName = e.getTagName();
	// ArrayList<Tile> itemTiles = new ArrayList<Tile>();
	//
	// Element itemRoot = (Element)e.getFirstChild();
	// NodeList itemNodes = itemRoot.getChildNodes();
	// ArrayList<Element> items = NodeListToArrayList(itemNodes);
	//
	// for(Element item:items){
	// String name = item.getTagName();
	// Item i;
	// if(name.equalsIgnoreCase("sword")) i = new Weapon(name);
	// //if(name.equalsIgnoreCase(name of items)){ //in case more items types
	// else i = new
	// Key(stringToInt(item.getFirstChild().getNextSibling().getTextContent()));
	//
	// //gets col and row from the children nodes of items.
	// int col = stringToInt(item.getFirstChild().getTextContent());
	// int row = stringToInt(item.getLastChild().getTextContent());
	// Tile t = new Tile(null, null, null, col, row);
	// t.placeItem(i);
	// itemTiles.add(t);
	// }
	// pseudoRooms.put(roomName, itemTiles);
	// }
	// return pseudoRooms;
	// }
	//
	// /**
	// * Converts a NodeList into an ArrayList of Elements
	// */
	// private static ArrayList<Element> NodeListToArrayList(NodeList rootChildren)
	// {
	// ArrayList<Element> elements = new ArrayList<Element>();
	// for(int i=0;i<rootChildren.getLength();i++){
	// Element e =(Element)rootChildren.item(i);
	// elements.add(i,e);
	// }
	// return elements;
	// }
	//
	// /**
	// * Reads player attributes from xml and creates new player
	// */
	// private static Player readPlayer(Element fileType) {
	// NodeList nodes = fileType.getChildNodes();
	// ArrayList<Element> elements =NodeListToArrayList(nodes);
	// String name = elements.get(0).getTextContent();
	// String color = elements.get(1).getTextContent();
	// Item[] items = readInventory(elements.get(2).getChildNodes());
	// String[] location = readLocation(elements.get(3).getChildNodes());
	// String room = location[1];
	// Room r = new Room(room);
	// int col = stringToInt(location[2]);
	// int row = stringToInt(location[3]);
	// String[] visited = readVisited(elements.get(4).getChildNodes());
	// int health = stringToInt(elements.get(5).getFirstChild().getTextContent());
	// Player p = new Player(name, color);
	// p.setCol(col);
	// p.setRow(row);
	// p.setInventory(items);
	// p.setHealth(health);
	// p.setRoom(r);
	// return p;
	// }
	//
	// /**
	// * Iterates through rooms visited nodes and puts their text into String array
	// * @param NodeList: All nodes visited
	// * @return String[]: Array of names of rooms player has been to.
	// */
	// private static String[] readVisited(NodeList nodeVisited) {
	// String[] visited = new String[nodeVisited.getLength()];
	// ArrayList<Element> rooms = NodeListToArrayList(nodeVisited);
	// for(int i=0;i<rooms.size();i++){
	// visited[i] = rooms.get(i).getTextContent();
	// }
	// return visited;
	// }
	//
	// /**
	// * Converts String to int
	// * @param String: String containing single integer.
	// * @return int: Integer in string. If no integer, returns 0.
	// */
	// private static int stringToInt(String s){
	// Scanner sc = new Scanner(s);
	// if(sc.hasNextInt()){
	// return sc.nextInt();
	// }
	// else{
	// return 0;
	// }
	// }
	//
	// /**
	// * Reads information about location of an object from xml
	// */
	// private static String[] readLocation(NodeList nodeLocations) {
	// String[] location = new String[4];
	// ArrayList<Element> l = NodeListToArrayList(nodeLocations);
	// location[0] = l.get(0).getTextContent();
	// location[1] = l.get(1).getTextContent();
	// Element x = (Element) l.get(2).getFirstChild();
	// Element y = (Element) l.get(2).getLastChild();
	// location[2] = x.getTextContent();
	// location[3] = y.getTextContent();
	// return location;
	// }
	//
	// /**
	// * Reads information about player inventory from xml
	// */
	// private static Item[] readInventory(NodeList nodeItems) {
	// Item[] inventory = new Item[9];
	// ArrayList<Element> items =NodeListToArrayList(nodeItems);
	// for(int i=0;i<items.size();i++){
	// if(items.get(i).getTagName().equalsIgnoreCase("key")){
	// inventory[i] = new Key(stringToInt(items.get(i).getTextContent()));
	// }
	// else{
	// inventory[i] = new Weapon(items.get(i).getTagName());
	// }
	// }
	// return inventory;
	// }
	//
	// /**
	// * Takes game and writes all data into XML file format
	// * @param game: GameOfDungees object.
	// */
	// public static void write(GameOfDungees game){
	// try{
	// DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	// DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	// Document doc = docBuilder.newDocument();
	//
	// Element rootElement = doc.createElement("Game");
	// doc.appendChild(rootElement);
	//
	// Element player = doc.createElement("Player");
	// rootElement.appendChild(player);
	//
	// Element world = doc.createElement("World");
	// rootElement.appendChild(world);
	//
	// Player p = game.getUser();
	// writePlayer(p,player,doc);
	//
	// ArrayList<Room> rooms = game.getRooms();
	// for(Room r:rooms){
	// writeRoom(r,world,doc);
	// }
	//
	// //Write document to xml
	// TransformerFactory transformerFactory = TransformerFactory.newInstance();
	// Transformer transformer = transformerFactory.newTransformer();
	// DOMSource source = new DOMSource(doc);
	// StreamResult result = new StreamResult("gamestate.xml");//creates an xml file
	// in source directory
	// transformer.transform(source, result);
	// }catch(Exception e){
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * Writes room information to xml file
	// */
	// private static void writeRoom(Room r, Element world, Document doc) {
	// Tile[][] tiles = r.getTiles();
	// ArrayList<Tile> itemTiles = new ArrayList<Tile>();
	// ArrayList<Tile> chestTiles = new ArrayList<Tile>();
	//
	// //Iterate through all tiles on board and keep tiles with items
	// for(int i = 0; i < 15; ++i){
	// for(int j = 0; j < 15; ++j){
	// if (tiles[j][i].hasItem()){
	// itemTiles.add(tiles[j][i]);
	// }
	// if(tiles[j][i].getWorldObject() instanceof Chest){
	// chestTiles.add(tiles[j][i]);
	// }
	// }
	// }
	//
	// //Room
	// Element room = doc.createElement(r.getRoomName());
	// world.appendChild(room);
	//
	// //Floor Items
	// Element items = doc.createElement("Items");
	// room.appendChild(items);
	//
	// Element chests = doc.createElement("Chests");
	// room.appendChild(chests);
	//
	// //Iterate through all tiles with items
	// for(Tile t:itemTiles){
	// //Name of Item
	// Element itemName = doc.createElement(t.getItem().getName());
	// items.appendChild(itemName);
	// //col and row
	// Element col = doc.createElement("Col");
	// itemName.appendChild(col);
	// col.appendChild(doc.createTextNode(t.getCol()+""));
	// //add if key, add code
	// if(t.getItem() instanceof Key){
	// Element code = doc.createElement("Code");
	// itemName.appendChild(code);
	// Key k = (Key) t.getItem();
	// code.appendChild(doc.createTextNode(k.getCode()+""));
	// }
	// Element row = doc.createElement("Row");
	// itemName.appendChild(row);
	// row.appendChild(doc.createTextNode(t.getRow()+""));
	// }
	//
	// for(Tile t:chestTiles){
	// Chest c = (Chest)t.getWorldObject();
	// int code = c.getChestId();
	// int col = t.getCol();
	// int row = t.getRow();
	// ArrayList<Item> chestItems = c.getContents();
	// Element currentChest = doc.createElement(c.getName());
	// chests.appendChild(currentChest);
	// Element codeElement = doc.createElement("code");
	// currentChest.appendChild(codeElement);
	// Element colElement = doc.createElement("col");
	// currentChest.appendChild(colElement);
	// Element rowElement = doc.createElement("row");
	// currentChest.appendChild(rowElement);
	// codeElement.appendChild(doc.createTextNode(code+""));
	// colElement.appendChild(doc.createTextNode(col+""));
	// rowElement.appendChild(doc.createTextNode(row+""));
	//
	// for(Item i:chestItems){
	// Element itemElement = doc.createElement(i.getName());
	// currentChest.appendChild(itemElement);
	// if(i instanceof Key){
	// Key k = (Key)i;
	// itemElement.appendChild(doc.createTextNode(k.getCode()+""));
	// }
	// }
	// }
	//
	//
	// }
	//
	// /**
	// * Writes player information to xml file
	// */
	// private static void writePlayer(Player p, Element player, Document doc){
	// String playerName = p.getName();
	// String playerColor = p.getColor();
	// String room = p.getRoom().getRoomName();
	// int col = p.getTile().getCol();
	// int row = p.getTile().getRow();
	// String[] playerLocation = {"Stage1",room,col+"",row+""};
	//
	// //Name
	// Element name = doc.createElement("Name");
	// player.appendChild(name);
	// name.appendChild(doc.createTextNode(playerName));
	//
	// //Color
	// Element color = doc.createElement("Color");
	// player.appendChild(color);
	// color.appendChild(doc.createTextNode(playerColor));
	//
	// //Inventory
	// Element inventory = doc.createElement("Inventory");
	// player.appendChild(inventory);
	// writeInventory(inventory,doc,p.getInventory());
	//
	// //Location
	// Element location = doc.createElement("Location");
	// player.appendChild(location);
	// writeLocation(location,doc,playerLocation);
	//
	// //Rooms Visited
	// Element visited = doc.createElement("RoomsVisited");
	// player.appendChild(visited);
	//
	// //Player health
	// Element health = doc.createElement("Health");
	// player.appendChild(health);
	// health.appendChild(doc.createTextNode(p.getHealth()+""));
	// }
	//
	// /**
	// * Writes player location information to xml file
	// */
	// private static void writeLocation(Element location, Document doc,String[]
	// playerLocation) {
	// //Current Stage
	// Element stage = doc.createElement("Stage");
	// location.appendChild(stage);
	// stage.appendChild(doc.createTextNode(playerLocation[0]));
	//
	// //Current Room
	// Element room = doc.createElement("Room");
	// location.appendChild(room);
	// room.appendChild(doc.createTextNode(playerLocation[1]));
	//
	// //Position col
	// Element position = doc.createElement("Position");
	// location.appendChild(position);
	// Element col = doc.createElement("col");
	// position.appendChild(col);
	// //Position row
	// Element row = doc.createElement("row");
	// position.appendChild(row);
	// col.appendChild(doc.createTextNode(playerLocation[2]));
	// row.appendChild(doc.createTextNode(playerLocation[3]));
	// }
	//
	// /**
	// * Writes player inventory information to xml file
	// */
	// private static void writeInventory(Element inventory, Document doc, Item[]
	// items) {
	// for(int i=1;i<10;i++){
	// if(items[i-1]!=null){
	// Element slot = doc.createElement(items[i-1].getName());
	// inventory.appendChild(slot);
	// if(items[i-1]instanceof Key){
	// Key k = (Key)items[i-1];
	// slot.appendChild(doc.createTextNode(k.getCode()+""));
	// }
	// }
	// }
	// }
	//
	// public static void main(String[] args){
	//// Player player = new Player("Fredrick","Blue");
	//// GameOfDungees game = new GameOfDungees(player);
	//// write(game);
	// GameOfDungees game2 = read(new File("gamestate.xml"));
	// }

}